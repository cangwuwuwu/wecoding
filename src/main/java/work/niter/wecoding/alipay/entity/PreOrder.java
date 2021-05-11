package work.niter.wecoding.alipay.entity;

import lombok.Data;

/**
 * @author Cangwu
 * @date 2019/10/31 15:53
 * @description
 */
@Data
public class PreOrder {
    private String out_trade_no;
    private String product_code;
    private String total_amount;
    private String subject;
    private String body;
}
