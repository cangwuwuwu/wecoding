package work.niter.wecoding.entity;

import lombok.Data;
import work.niter.wecoding.enums.ExceptionEnum;

/**
 * @Author: Cangwu
 * @Date: 2019/9/6 12:59
 * @Description:
 */
@Data
public class ExceptionResult {
    private int status;
    private String message;
    private Long timestamp;

    public ExceptionResult(ExceptionEnum em) {
        this.status = em.getCode();
        this.message = em.getMsg();
        this.timestamp = System.currentTimeMillis();
    }
}
