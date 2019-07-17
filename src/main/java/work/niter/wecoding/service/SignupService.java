package work.niter.wecoding.service;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import work.niter.wecoding.entity.Account;
import work.niter.wecoding.entity.Student;
import work.niter.wecoding.entity.UserList;
import work.niter.wecoding.mapper.AccountMapper;
import work.niter.wecoding.mapper.StudentMapper;

/**
 * @Author: Cangwu
 * @Date: 2019/7/14 1:27
 * @Description:
 */
@Service
public class SignupService {
    @Autowired
    private StudentService studentService;
    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private StringRedisTemplate redisTemplate;

    public UserList getFIveStudentsInService() {
        List<Student> allStudents = this.studentService.getAllStudentsInService();
        int len = allStudents.size();
        HashSet set = new HashSet();

        while(set.size() < 5) {
            set.add((int)(Math.random() * (double)len));
        }

        List<Student> randomLists = new LinkedList();
        set.forEach((e) -> randomLists.add(allStudents.get((Integer) e)));
        UserList userList = new UserList();
        userList.setRandomList(randomLists);
        userList.setLen(len);
        userList.setAllList(allStudents);
        return userList;
    }

    public String registerInService(Student student, Account account, String stuRePassword, String stuCode) {
        String stuPassword = account.getStuPassword();
        if (!stuPassword.equals(stuRePassword)) {
            return "确认密码和密码不一致!";
        } else {
            String email = student.getStuEmail();
            String code = this.redisTemplate.opsForValue().get(email);
            if (stuCode.equals(code)) {
                int addResult = this.studentMapper.addStudent(student);
                if (addResult == 1) {
                    account.setStuPassword((new BCryptPasswordEncoder()).encode(account.getStuPassword()));
                    this.accountMapper.addUserPassword(account);
                    return "注册成功!";
                } else {
                    return "注册失败，请重试!";
                }
            } else {
                return "验证码错误!";
            }
        }
    }
}

