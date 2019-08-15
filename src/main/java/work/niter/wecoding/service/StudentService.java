package work.niter.wecoding.service;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import work.niter.wecoding.entity.Student;
import work.niter.wecoding.mapper.StudentMapper;

import java.util.List;

/**
 * @Author: Cangwu
 * @Date: 2019/7/14 1:21
 * @Description:
 */
@Service("studentService")
public class StudentService {
    @Autowired
    private StudentMapper studentMapper;

    @Cacheable(
            cacheNames = {"stu"},
            key = "#stuId"
    )
    public Student getOne(Integer stuId) {
        return this.studentMapper.getStudentById(stuId);
    }

    @CachePut(
            cacheNames = {"stu"},
            key = "#result.stuId"
    )
    public Student getByUsername(String stuUsername) {
        return this.studentMapper.getStudentByUsername(stuUsername);
    }

    @Cacheable(
            cacheNames = {"stu"},
            key = "#root.method.name"
    )
    public List<Student> getAllStudentsInService() {
        return this.studentMapper.getAllStudents();
    }

    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = {"stu"}, key = "#stuId")
    public int deleteOneStudent(Integer stuId) {
        return this.studentMapper.deleteStudentById(stuId);
    }

    @Transactional(rollbackFor = Exception.class)
    @CachePut(cacheNames = {"stu"}, key = "#student.stuId")
    public void updateStudent(Student student) {
        this.studentMapper.updateStudent(student);
    }

    @Transactional(rollbackFor = Exception.class)
    public int addAStudent(Student student) {
        return this.studentMapper.addStudent(student);
    }
}
