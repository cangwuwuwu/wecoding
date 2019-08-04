package work.niter.wecoding.controller;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import work.niter.wecoding.entity.Account;
import work.niter.wecoding.entity.Student;
import work.niter.wecoding.entity.UserList;
import work.niter.wecoding.service.SignupService;

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

    @GetMapping("/getFive")
    public UserList toSignUpPage1() {
        return this.signupService.getFiveStudentsInService();
    }

    @PostMapping("/regist")
    public String addStudentForREST(Student stu, Account account,
                                    @RequestParam String stuRePassword,
                                    @RequestParam String stuCode) throws IOException {
        return this.signupService.registerInService(stu, account, stuRePassword, stuCode);
    }
}
