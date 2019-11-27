package work.niter.wecoding.exception.entity;

import com.alipay.api.response.AlipayTradeQueryResponse;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import work.niter.wecoding.exception.enums.ExceptionEnum;

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

    private static final String ALI_PAY_SUCCESS_CODE = "10000";

    public ExceptionResult(ExceptionEnum em) {
        this.status = em.getCode();
        this.message = em.getMsg();
        this.timestamp = System.currentTimeMillis();
    }

    public ExceptionResult(AlipayTradeQueryResponse response) {
        if (ALI_PAY_SUCCESS_CODE.equals(response.getCode())) {
            this.status = 200;
        } else {
            this.status = 500;
        }
        this.message = response.getSubMsg();
        this.timestamp = System.currentTimeMillis();
    }
}
