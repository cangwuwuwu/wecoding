package work.niter.wecoding.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.common.Mapper;
import work.niter.wecoding.entity.MyMessage;

import java.util.List;

/**
 * @Author: Cangwu
 * @Date: 2019/8/3 23:55
 * @Description:
 */
public interface MessageMapper extends Mapper<MyMessage> {

    @Insert("insert into fanout_msg values(#{msgId}, #{msgType}, #{msgHead}, #{msgContent}, #{msgSender}, #{msgTime})")
    int insertNewHistoryMessage(MyMessage message);
}
