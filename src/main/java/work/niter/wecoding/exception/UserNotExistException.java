package work.niter.wecoding.exception;

/**
 * @Author: Cangwu
 * @Date: 2019/7/14 1:33
 * @Description:
 */
public class UserNotExistException extends RuntimeException {
    public UserNotExistException() {
        super("用户不存在");
    }
}
