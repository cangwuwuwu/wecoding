package work.niter.wecoding.alipay.mapper;

import org.apache.ibatis.annotations.Update;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.common.Mapper;
import work.niter.wecoding.alipay.entity.Payment;

/**
 * @Author: Cangwu
 * @Date: 2019/10/30 13:34
 * @Description:
 */
public interface PayMapper extends Mapper<Payment> {

    /**
     * 更新已支付的订单的状态
     * @param payment
     * @return
     */
    @Update("update payment set status=#{status}, finish_time=#{finishTime} where order_no=#{orderNo}")
    void updateStatusAndFinishTime(Payment payment);

    /**
     * 更新已关闭的订单状态
     * @param payment
     * @return
     */
    @Update("update payment set status=#{status}, close_time=#{closeTime} where order_no=#{orderNo}")
    void updateStatusAndCloseTime(Payment payment);
}
