package work.niter.wecoding.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import work.niter.wecoding.entity.Apply;
import work.niter.wecoding.entity.Course;
import work.niter.wecoding.entity.ResWeb;
import work.niter.wecoding.enums.ExceptionEnum;
import work.niter.wecoding.exception.RestException;
import work.niter.wecoding.mapper.ApplyMapper;
import work.niter.wecoding.mapper.CourseMapper;
import work.niter.wecoding.mapper.ResWebMapper;

import java.util.*;

/**
 * @Author: Cangwu
 * @Date: 2019/10/11 17:03
 * @Description:
 */
@Service
public class CourseService {
    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private ResWebMapper resWebMapper;
    @Autowired
    private ApplyMapper applyMapper;

    @Autowired
    private RedisTemplate<String, Course> courseRedisTemplate;
    @Autowired
    private RedisTemplate<String, ResWeb> webRedisTemplate;

    private static final String COURSE_KEY = "course";
    private static final String WEB_KEY = "courseWeb";

    /**
     * 获取所有课程列表并缓存到redis
     * @param page
     * @param size
     * @return
     */
    public Map<String, Object> selectAllCourseList(Integer page, Integer size) {
        List<Course> range;
        List<ResWeb> resWebs;
        if (courseRedisTemplate.hasKey(COURSE_KEY)) {
            PageHelper.startPage(page, size);
            range = courseRedisTemplate.opsForList().range(COURSE_KEY, 0, -1);
        } else {
            range = courseMapper.selectAllCourse();
            courseRedisTemplate.opsForList().rightPushAll(COURSE_KEY, range);
        }
        if (webRedisTemplate.hasKey(WEB_KEY)) {
            resWebs = webRedisTemplate.opsForList().range(WEB_KEY, 0, -1);
        } else {
            resWebs = resWebMapper.selectAll();
            webRedisTemplate.opsForList().rightPushAll(WEB_KEY, resWebs);
        }
        Map<String, Object> map = new HashMap<>(4);
        map.put("courseList", new PageInfo<>(range));
        map.put("recommend", resWebs);
        return map;
    }

    /**
     * 定时自动持久化到数据库 并删除缓存
     */
    @Scheduled(cron = "0 0 */5 * * *")
    public void updateAndDelStarAndApply() {
        List<Course> courses = courseRedisTemplate.opsForList().range(COURSE_KEY, 0, -1);
        List<ResWeb> resWebs = webRedisTemplate.opsForList().range(WEB_KEY, 0, -1);
        if(courses != null && resWebs != null) {
            courses.forEach(c -> courseMapper.updateCourseByPrimaryKey(c));
            resWebs.forEach(w -> resWebMapper.updateByPrimaryKeySelective(w));
            courseRedisTemplate.delete(COURSE_KEY);
            webRedisTemplate.delete(WEB_KEY);
        }
    }

    /**
     * 播放量处理
     * @param id
     */
    public void incrPlayNum(Integer id) {
        List<ResWeb> resWebs = webRedisTemplate.opsForList().range(WEB_KEY, 0, -1);
        resWebs.forEach(web -> {
            if (id.equals(web.getId())) {
                int index = resWebs.indexOf(web);
                web.setResWebNum(web.getResWebNum() + 1);
                webRedisTemplate.opsForList().set(WEB_KEY, index, web);
            }
        });
    }

    /**
     * 点赞处理
     * @param course
     */
    public void updateStarRedisMethod(Course course) {
        resetRedisCourseInfo(course.getId(), course);
    }

    /**
     * 报名课程处理
     * @param apply
     */
    @Transactional(rollbackFor = Exception.class)
    public void insertApplyMethod(Apply apply) {
        int i = applyMapper.insert(apply);
        if (i == 1) {
            resetRedisCourseInfo(apply.getCourseId(), null);
        } else {
            throw new RestException(ExceptionEnum.UNKNOWN_ERROR);
        }
    }

    /**
     * 获取该课程报名列表
     * @param id
     * @return
     */
    public List<Apply> getApplyListMethod(String id) {
        Example example = new Example(Apply.class);
        example
                .createCriteria()
                .andEqualTo("courseId", id);
        return applyMapper.selectByExample(example);
    }

    private void resetRedisCourseInfo(String id, Course course) {
        List<Course> courses = courseRedisTemplate.opsForList().range(COURSE_KEY, 0, -1);
        courses.forEach(c -> {
            if (c.getId().equals(id)) {
                int index = courses.indexOf(c);
                if (course != null) {
                    c.setStar(course.getStar());
                } else {
                    c.setApply(c.getApply() + 1);
                }
                courseRedisTemplate.opsForList().set(COURSE_KEY, index, c);
            }
        });
    }
}
