package work.niter.wecoding.mapper;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;
import work.niter.wecoding.entity.Apply;
import work.niter.wecoding.entity.Course;

import java.util.List;

/**
 * @Author: Cangwu
 * @Date: 2019/10/11 17:02
 * @Description:
 */
public interface CourseMapper extends Mapper<Course> {

    @Update("UPDATE course SET id = #{id},star = #{star} WHERE id = #{id}")
    int updateCourseByPrimaryKey(Course course);

    @Select("SELECT" +
            " *," +
            " ( SELECT count( * ) FROM apply a WHERE a.course_id = c.id ) AS apply " +
            "FROM " +
            "course c")
    List<Course> selectAllCourse();

}
