package work.niter.wecoding.alipay.entity;

import lombok.Data;

import javax.persistence.Table;
import java.util.Date;

/**
 * @Author: Cangwu
 * @Date: 2019/10/30 13:22
 * @Description: 会费支付订单表
 */
@Data
@Table(name = "payment")
public class Payment {
    private Integer id;
    private String orderNo;
    private Integer userId;
    private Double payment;
    private Integer paymentType;
    private Integer status;
    private Date createTime;
    private Date closeTime;
    private Date finishTime;
}
