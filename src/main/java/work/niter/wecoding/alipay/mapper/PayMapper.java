package work.niter.wecoding.alipay.mapper;

import org.apache.ibatis.annotations.*;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.common.Mapper;
import work.niter.wecoding.alipay.entity.Payment;

import java.util.List;

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

    /**
     * 模糊查询会费缴纳信息，并根据学号查询学生姓名
     * @return
     */
    @Select("select * from payment where CONCAT( user_id,payment,payment_type) LIKE CONCAT('%',#{search},'%') order by finish_time desc")
    @ResultMap("payment_results")
    List<Payment> searchPayment(String search);

    /**
     * 不模糊查询
     */
    @Select("select * from payment order by finish_time desc")
    @Results(id = "payment_results", value = {
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "orderNo", column = "order_no"),
            @Result(property = "payment", column = "payment"),
            @Result(property = "paymentType", column = "payment_type"),
            @Result(property = "status", column = "status"),
            @Result(property = "createTime", column = "create_time"),
            @Result(property = "closeTime", column = "close_time"),
            @Result(property = "finishTime", column = "finish_time"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "stuName", column = "user_id",
                    one = @One(select = "work.niter.wecoding.user.mapper.CompMapper.findStuName")),
    })
    List<Payment> selectPayment();

}
