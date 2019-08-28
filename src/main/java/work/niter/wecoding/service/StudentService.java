package work.niter.wecoding.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import work.niter.wecoding.entity.Student;
import work.niter.wecoding.mapper.StudentMapper;

import java.util.List;

/**
 * @Author: Cangwu
 * @Date: 2019/7/14 1:21
 * @Description:
 */
@Service("studentService")
@CacheConfig(cacheNames = "stu")
public class StudentService {
    @Autowired
    private StudentMapper studentMapper;

    public Student getOne(String stuId) {
        return studentMapper.selectByPrimaryKey(stuId);
    }

    public Student getByUsername(String stuUsername) {
        Example example = new Example(Student.class);
        example.createCriteria()
                .andEqualTo("stuUsername", stuUsername);
        Student student = studentMapper.selectOneByExample(example);
        return student;
    }

    @Cacheable(key = "#root.method.name")
    public List<Student> getAllStudentsInService() {
        return studentMapper.selectAll();
    }

    @Transactional(rollbackFor = Exception.class)
    public int deleteOneStudent(String stuId) {
        return studentMapper.deleteStudentById(stuId);
    }

    @Transactional(rollbackFor = Exception.class)
    public int updateStudent(Student student) {
        return studentMapper.updateStudent(student);
    }

    @Transactional(rollbackFor = Exception.class)
    public int addOneStudent(Student student) {
        return studentMapper.addStudent(student);
    }

    @Transactional(rollbackFor = Exception.class)
    public int updateStuInfoById(Student student) {
        return studentMapper.updateByPrimaryKeySelective(student);
    }
}
