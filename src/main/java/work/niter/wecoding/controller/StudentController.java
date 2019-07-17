package work.niter.wecoding.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.SpelEvaluationException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import work.niter.wecoding.entity.Student;
import work.niter.wecoding.service.StudentService;

/**
 * @Author: Cangwu
 * @Date: 2019/7/14 1:18
 * @Description:
 */
@RestController
@RequestMapping({"/stu"})
public class StudentController {
    @Autowired
    private StudentService studentService;

    @GetMapping({"/stu-id/{id}"})
    public Student checkStudentAccountById(@PathVariable(value = "id",required = false) Integer id) {
        Student result = null;

        try {
            result = this.studentService.getOne(id);
        } catch (NullPointerException var4) {
            var4.printStackTrace();
        }

        return result;
    }

    /*@GetMapping({"/{stuId}/allInfo"})
    public Student findOneStudentById(@PathVariable("stuId") Integer stuId) {
        return this.studentService.getOne(stuId);
    }*/

    @GetMapping({"/stu-username/{username}"})
    public Student checkStudentAccountByUsername(@PathVariable(value = "username",required = false) String username) {
        Student result = null;

        try {
            result = this.studentService.getByUsername(username);
        } catch (SpelEvaluationException var4) {
            System.out.println("用户名 " + username + " 正在被注册");
        }

        return result;
    }

    @GetMapping
    public List<Student> findAllStudentsForREST() {
        return this.studentService.getAllStudentsInService();
    }

    @PostMapping
    public int addAStudentForREST(Student student) {
        return this.studentService.addAStudent(student);
    }
}
