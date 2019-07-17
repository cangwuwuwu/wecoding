package work.niter.wecoding.service;

import java.util.Map;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

/**
 * @Author: Cangwu
 * @Date: 2019/7/14 1:39
 * @Description:
 */
@Service
public class QueueService {

    @RabbitListener(
            queues = {"message"}
    )
    public void receive(Map<String, Object> map) {
        System.out.println("收到消息：" + map);
    }
}

