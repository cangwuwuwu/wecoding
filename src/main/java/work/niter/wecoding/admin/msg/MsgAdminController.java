package work.niter.wecoding.admin.msg;

import com.alibaba.fastjson2.JSONObject;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.ResultMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import work.niter.wecoding.admin.access.annotation.PageView;
import work.niter.wecoding.msg.entity.MyMessage;
import work.niter.wecoding.msg.service.RabbitService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author xiaozhai
 * @date 2019/12/3 11:37
 * @description
 */
@PreAuthorize("hasRole('ADMIN')")
@RestController
@RequestMapping("/admin/comp/msg")
public class MsgAdminController {

    @Autowired
    private RabbitService rabbitService;

    @GetMapping("getMsgList")
    public ResponseEntity<PageInfo<MyMessage>> getAllMsgInfo(@RequestParam(value = "page", defaultValue = "1") int page,
                                                             @RequestParam(value = "size", defaultValue = "3") int size) {
        PageInfo<MyMessage> pageInfo = rabbitService.getAllMessageAndPage(page, size);
        return ResponseEntity.ok(pageInfo);
    }

    /**
     * 发送消息，并持久化到数据库
     *
     * @param message
     * @return
     */
    @PostMapping("/sendMsg")
    public ResponseEntity<Void> sendMsg(@RequestBody String message) {
        MyMessage myMessage = JSONObject.parseObject(message, MyMessage.class);
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String s = format.format(date);
        myMessage.setMsgTime(s);
        rabbitService.insertNewHistoryMessageInService(myMessage);
        rabbitService.send2AllClient(myMessage);
        return ResponseEntity.ok().build();
    }
}
