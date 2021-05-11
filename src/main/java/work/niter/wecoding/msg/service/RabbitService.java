package work.niter.wecoding.msg.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import work.niter.wecoding.msg.entity.MyMessage;
import work.niter.wecoding.msg.mapper.MessageMapper;

import java.util.List;

/**
 * @author Cangwu
 * @date 2019/8/2 23:12
 * @description
 */
@Service
public class RabbitService {

    @Autowired
    private FanoutExchange fanoutExchange;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private MessageMapper messageMapper;

    /**
     * 获取所有历史消息并缓存
     * @param key
     * @return
     */
    @Cacheable(cacheNames = "msg", key = "#key")
    public List<MyMessage> getAllHistoryMessageInService(String key) {
        return messageMapper.selectAll();
    }

    /**
     * 新的消息持久化到数据库
     * @param message
     * @return
     */
    public int insertNewHistoryMessageInService(MyMessage message) {
        return messageMapper.insertNewHistoryMessage(message);
    }

    /**
     * 发送消息到消息队列
     * @param message
     */
    public void send2AllClient(MyMessage message) {
        rabbitTemplate.convertAndSend(fanoutExchange.getName(), "", message);
    }

    /**
     * 查询所有历史消息，并分页。后台消息管理会调用
     * @param page
     * @param size
     * @return
     */
   public PageInfo<MyMessage> getAllMessageAndPage(int page, int size){
       String orderBy = "msg_time" + " desc";
       PageHelper.startPage(page, size, orderBy);
       List<MyMessage> messages = messageMapper.selectAll();
       PageInfo<MyMessage> pageInfo = new PageInfo<>(messages);
       return pageInfo;
   }
}
