package work.niter.wecoding.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import work.niter.wecoding.entity.MyMessage;

import java.util.List;

/**
 * @Author: Cangwu
 * @Date: 2019/8/3 23:55
 * @Description:
 */
@Mapper
public interface MessageMapper {

    @Transactional(readOnly = true)
    @Select("select * from fanout_msg")
    List<MyMessage> getAllHistoryMessage();

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Insert("insert into fanout_msg values(#{msgId}, #{msgType}, #{msgHead}, #{msgContent}, #{msgSender}, #{msgTime})")
    int insertNewHistoryMessage(MyMessage message);
}
