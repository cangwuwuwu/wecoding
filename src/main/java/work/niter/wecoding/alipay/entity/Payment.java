package work.niter.wecoding.alipay.entity;

import lombok.Data;

import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

/**
 * @author Cangwu
 * @date 2019/10/30 13:22
 * @description 会费支付订单表
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

    @Transient
    private String stuName;
}
