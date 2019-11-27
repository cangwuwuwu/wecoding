package work.niter.wecoding.course.mapper;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;
import work.niter.wecoding.course.entity.Course;

import java.util.List;

/**
 * @Author: Cangwu
 * @Date: 2019/10/11 17:02
 * @Description:
 */
public interface CourseMapper extends Mapper<Course> {

    /**
     * 持久化点赞报名数据到数据库
     * @param course
     * @return
     */
    @Update("UPDATE course SET id = #{id},star = #{star} WHERE id = #{id}")
    int updateCourseByPrimaryKey(Course course);

    /**
     * 倒叙查询课程列表，连接查询点赞报名数
     * @return
     */
    @Select("SELECT" +
            " *," +
            " ( SELECT count( * ) FROM apply a WHERE a.course_id = c.id ) AS apply " +
            "FROM " +
            "course c ORDER BY id DESC")
    List<Course> selectAllCourse();

}
