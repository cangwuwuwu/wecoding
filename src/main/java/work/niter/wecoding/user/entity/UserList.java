package work.niter.wecoding.user.entity;

import lombok.Data;

import java.util.List;

/**
 * @Author: Cangwu
 * @Date: 2019/7/14 1:23
 * @Description:
 */
@Data
public class UserList {
    private List<CompStudent> randomList;
    private List<CompStudent> allList;
    private int len;
}
