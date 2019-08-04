package work.niter.wecoding.entity;

import lombok.Data;

import java.util.Date;

/**
 * @Author: Cangwu
 * @Date: 2019/8/2 16:07
 * @Description:
 */
@Data
public class MyMessage {
    private int msgId;
    private String msgType;
    private String msgHead;
    private String msgContent;
    private String msgSender;
    private String msgTime;
}
