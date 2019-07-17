package work.niter.wecoding.entity;

import lombok.Data;

import java.util.List;

/**
 * @Author: Cangwu
 * @Date: 2019/7/14 1:23
 * @Description:
 */
@Data
public class UserList {
    private List<Student> randomList;
    private List<Student> allList;
    private int len;
}
