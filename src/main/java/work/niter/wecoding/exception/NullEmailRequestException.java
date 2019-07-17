package work.niter.wecoding.exception;

/**
 * @Author: Cangwu
 * @Date: 2019/7/14 1:26
 * @Description:
 */
public class NullEmailRequestException extends RuntimeException {
    public NullEmailRequestException() {
        System.out.println("The mailbox is empty or formatted incorrectly.");
    }
}
