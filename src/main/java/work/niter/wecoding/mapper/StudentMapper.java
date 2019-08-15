package work.niter.wecoding.mapper;

import java.util.List;

import org.apache.ibatis.annotations.*;
import org.springframework.transaction.annotation.Transactional;
import work.niter.wecoding.entity.Student;

/**
 * @Author: Cangwu
 * @Date: 2019/7/14 1:19
 * @Description:
 */
@Mapper
@Transactional(readOnly = true,rollbackFor = Exception.class)
public interface StudentMapper {
    @Select({"select * from tb_stu where stu_id = #{stuId}"})
    Student getStudentById(Integer stuId);

    @Select({"select * from tb_stu where stu_username=#{stuUsername}"})
    Student getStudentByUsername(String stuUsername);

    @Select({"select * from tb_stu"})
    List<Student> getAllStudents();

    @Transactional(rollbackFor = Exception.class)
    @Insert({"insert into tb_stu(stu_id,stu_name,stu_username," +
            "stu_phone,stu_gender,stu_email,stu_birthday,stu_area,stu_info) " +
            "values(#{stuId},#{stuName},#{stuUsername},#{stuPhone},#{stuGender}," +
            "#{stuEmail},#{stuBirthday},#{stuArea},#{stuInfo})"})
    int addStudent(Student student);

    @Transactional(rollbackFor = Exception.class)
    @Delete({"delete from tb_stu where stu_id=#{stuId}"})
    int deleteStudentById(Integer stuId);

    @Transactional(rollbackFor = Exception.class)
    @Update({"update tb_stu set stu_name=#{stuName}," +
            "stu_username=#{stuUsername},stu_phone=#{stuPhone},stu_gender=#{stuGender}," +
            "stu_email=#{stuEmail},stu_birthday=#{stuBirthday},stu_area=#{stuArea}," +
            "stu_info=#{stuInfo},stu_img=#{stuImg},stu_big_img=#{stuBigImg} where stu_id=#{stuId}"})
    void updateStudent(Student student);
}
