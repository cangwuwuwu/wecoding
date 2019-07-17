package work.niter.wecoding.controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import work.niter.wecoding.entity.Account;
import work.niter.wecoding.entity.Student;
import work.niter.wecoding.entity.UserList;
import work.niter.wecoding.service.SignupService;
import work.niter.wecoding.service.StudentService;

/**
 * @Author: Cangwu
 * @Date: 2019/7/14 1:23
 * @Description:
 */

@RestController
@RequestMapping({"/signup"})
public class SignupController {
    @Autowired
    private SignupService signupService;

    @GetMapping({"/getFive"})
    public UserList toSignUpPage1() {
        return this.signupService.getFIveStudentsInService();
    }

    @PostMapping({"/regist"})
    public String addStudentForREST(Student stu, Account account, HttpServletRequest request) throws IOException {
        String stuRePassword = request.getParameter("stuRePassword");
        String stuCode = request.getParameter("stuCode");
        return this.signupService.registerInService(stu, account, stuRePassword, stuCode);
    }
}
