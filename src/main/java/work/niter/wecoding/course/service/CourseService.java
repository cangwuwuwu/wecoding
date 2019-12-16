package work.niter.wecoding.course.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import work.niter.wecoding.course.entity.Apply;
import work.niter.wecoding.course.entity.Course;
import work.niter.wecoding.res.entity.ResWeb;
import work.niter.wecoding.exception.enums.ExceptionEnum;
import work.niter.wecoding.exception.RestException;
import work.niter.wecoding.course.mapper.ApplyMapper;
import work.niter.wecoding.course.mapper.CourseMapper;
import work.niter.wecoding.res.mapper.ResWebMapper;

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
    private RedisTemplate<Object, Object> redisTemplate;

    private static final String COURSE_KEY = "course";
    private static final String WEB_KEY = "courseWeb";

    /**
     * 获取所有课程列表并缓存到redis
     * @param page
     * @param size
     * @return
     */
    public Map<String, Object> selectAllCourseList(Integer page, Integer size) {
        List<Course> courses;
        List<ResWeb> resWebs;
        if(!redisTemplate.hasKey(COURSE_KEY) || !redisTemplate.hasKey(WEB_KEY)) {
            courses = courseMapper.selectAllCourse();
            courses.forEach(course -> redisTemplate.opsForList().rightPush(COURSE_KEY, course));
            resWebs = resWebMapper.selectAll();
            resWebs.forEach(web -> redisTemplate.opsForList().rightPush(WEB_KEY, web));
        }
        PageHelper.startPage(page, size);
        courses = object2Course();
        resWebs = object2ResWeb();
        Map<String, Object> map = new HashMap<>(4);
        map.put("courseList", new PageInfo<>(courses));
        map.put("recommend", resWebs);
        return map;
    }

    /**
     * 定时自动持久化到数据库 并删除缓存
     */
    @Scheduled(cron = "0 0 */5 * * *")
    public void updateAndDelStarAndApply() {
        List<Course> courses = object2Course();
        List<ResWeb> resWebs = object2ResWeb();
        if(courses != null && resWebs != null) {
            courses.forEach(c -> courseMapper.updateCourseByPrimaryKey(c));
            resWebs.forEach(w -> resWebMapper.updateByPrimaryKeySelective(w));
            redisTemplate.delete(COURSE_KEY);
            redisTemplate.delete(WEB_KEY);
        }
    }

    /**
     * 播放量处理
     * @param id
     */
    public void incrPlayNum(Integer id) {
        List<ResWeb> resWebs = object2ResWeb();
        resWebs.forEach(web -> {
            if (id.equals(web.getId())) {
                int index = resWebs.indexOf(web);
                web.setResWebNum(web.getResWebNum() + 1);
                redisTemplate.opsForList().set(WEB_KEY, index, web);
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
        List<Course> courses = object2Course();
        courses.forEach(c -> {
            if (c.getId().equals(id)) {
                int index = courses.indexOf(c);
                if (course != null) {
                    c.setStar(course.getStar());
                } else {
                    c.setApply(c.getApply() + 1);
                }
                redisTemplate.opsForList().set(COURSE_KEY, index, c);
            }
        });
    }

    /**
     * 缓存中查询所有课程并解析
     * @return
     */
    private List<Course> object2Course() {
        String s = JSON.toJSONString(redisTemplate.opsForList().range(COURSE_KEY, 0, -1));
        return JSONObject.parseArray(s, Course.class);
    }

    /**
     * 缓存中查询所有在线课程并解析
     * @return
     */
    private List<ResWeb> object2ResWeb() {
        String s = JSON.toJSONString(redisTemplate.opsForList().range(WEB_KEY, 0, -1));
        return JSONObject.parseArray(s, ResWeb.class);
    }
}
