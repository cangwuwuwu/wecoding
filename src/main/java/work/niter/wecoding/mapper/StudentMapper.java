package work.niter.wecoding.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import work.niter.wecoding.entity.Student;

/**
 * @Author: Cangwu
 * @Date: 2019/7/14 1:19
 * @Description:
 */

@Mapper
public interface StudentMapper {
    @Select({"select * from tb_stu where stu_id = #{stuId}"})
    Student getStudentById(Integer stuId);

    @Select({"select * from tb_stu where stu_username=#{stuUsername}"})
    Student getStudentByUsername(String stuUsername);

    @Select({"select * from tb_stu"})
    List<Student> getAllStudents();

    @Insert({"insert into tb_stu(stu_id,stu_name,stu_username,stu_phone,stu_email,stu_birthday,stu_province,stu_city,stu_info) values(#{stuId},#{stuName},#{stuUsername},#{stuPhone},#{stuEmail},#{stuBirthday},#{stuProvince},#{stuCity},#{stuInfo})"})
    int addStudent(Student student);

    @Delete({"delete from tb_stu where stu_id=#{stuId}"})
    int deleteStudentById(Integer stuId);

    @Update({"update tb_stu set stu_name=#{stuName},stu_username=#{stuUsername},stu_phone=#{stuPhone},stu_email=#{stuEmail},stu_birthday=#{stuBirthday},stu_province=#{stuProvince},stu_city=#{stuCity},stu_info=#{stuInfo} where stu_id=#{stuId}"})
    void updateStudent(Student student);
}
