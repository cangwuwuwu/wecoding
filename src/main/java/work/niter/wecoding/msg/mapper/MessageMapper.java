package work.niter.wecoding.msg.mapper;

import org.apache.ibatis.annotations.Insert;
import tk.mybatis.mapper.common.Mapper;
import work.niter.wecoding.msg.entity.MyMessage;

/**
 * @Author: Cangwu
 * @Date: 2019/8/3 23:55
 * @Description:
 */
public interface MessageMapper extends Mapper<MyMessage> {

    @Insert("insert into fanout_msg values(#{msgId}, #{msgType}, #{msgHead}, #{msgContent}, #{msgSender}, #{msgTime})")
    int insertNewHistoryMessage(MyMessage message);
}
