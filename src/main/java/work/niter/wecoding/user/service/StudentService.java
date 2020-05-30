package work.niter.wecoding.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import work.niter.wecoding.user.entity.CompStudent;
import work.niter.wecoding.user.entity.UserList;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

/**
 * @Author: Cangwu
 * @Date: 2019/9/27 15:24
 * @Description:
 */
@RestController
public class StudentService {

    @Autowired
    private CompService compService;

    /**
     * 获取所有用户和随机挑选8个用户展示
     * @return userlist
     */
    public UserList findEightStudents() {
        List<CompStudent> allStudents = compService.findAllStuMsg();
        int len = allStudents.size();
        int displaynum;

        HashSet<Integer> set = new HashSet<>();
        displaynum = Math.min(len, 8);
        while(set.size() < displaynum) {
            set.add((int)(Math.random() * (double)len));
        }

        List<CompStudent> randomLists = new LinkedList<>();
        set.forEach((e) -> randomLists.add(allStudents.get((Integer) e)));
        UserList userList = new UserList();
        userList.setRandomList(randomLists);
        userList.setLen(len);
        userList.setAllList(allStudents);
        return userList;
    }
}
