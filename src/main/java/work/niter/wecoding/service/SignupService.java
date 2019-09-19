package work.niter.wecoding.service;

import java.util.*;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    /**
     * 获取所有用户和随机挑选8个用户展示
     * @return userlist
     */
    public UserList getEightStudentsInService() {
        List<Student> allStudents = studentService.getAllStudentsInService();
        int len = allStudents.size();
        int displaynum;
        HashSet set = new HashSet();
        if (len < 8) {
            displaynum = len;
        } else {
            displaynum = 8;
        }

        while(set.size() < displaynum) {
            set.add((int)(Math.random() * (double)len));
        }

        List<Student> randomLists = new LinkedList<>();
        set.forEach((e) -> randomLists.add(allStudents.get((Integer) e)));
        UserList userList = new UserList();
        userList.setRandomList(randomLists);
        userList.setLen(len);
        userList.setAllList(allStudents);
        return userList;
    }

    /**
     * 用户注册
     * @param student 用户基本信息
     * @param account 账户信息
     * @param stuRePassword 注册时填写的确认密码
     * @param stuCode 注册时填写的验证码
     * @return 返回注册是否成功的信息
     */
    @Transactional(rollbackFor = Exception.class)
    public String registerInService(Student student, Account account, String stuRePassword, String stuCode) {
        Map<String,Object> map = new HashMap<>(4);

        String stuPassword = account.getStuPassword();
        if (!stuPassword.equals(stuRePassword)) {
            map.put("message", "确认密码和密码不一致");
            return JSON.toJSONString(map);
        } else {
            String email = student.getStuEmail();
            String code = this.redisTemplate.opsForValue().get(email);
            if (stuCode.equals(code)) {
                int addResult = this.studentMapper.addStudent(student);
                account.setStuPassword((new BCryptPasswordEncoder()).encode(account.getStuPassword()));
                int addUserPassword = this.accountMapper.addUserPassword(account);
                if (addResult == 1 && addUserPassword == 1) {
                    map.put("status", 200);
                    map.put("message", "注册成功");
                    return JSON.toJSONString(map);
                } else {
                    map.put("status", 500);
                    map.put("message", "服务器异常");
                    return JSON.toJSONString(map);
                }
            } else {
                map.put("status", 401);
                map.put("message", "验证码错误!");
                return JSON.toJSONString(map);
            }
        }
    }
}

