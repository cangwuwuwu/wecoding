package work.niter.wecoding.msg.entity;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author Cangwu
 * @date 2019/8/2 16:07
 * @description
 */
@Data
@Table(name = "fanout_msg")
public class MyMessage {
    @Id
    private Integer msgId;
    private String msgType;
    private String msgHead;
    private String msgContent;
    private String msgSender;
    private String msgTime;
}
