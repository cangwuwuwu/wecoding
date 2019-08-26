package work.niter.wecoding.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import work.niter.wecoding.entity.MyMessage;
import work.niter.wecoding.service.RabbitService;

import java.util.List;

/**
 * @Author: Cangwu
 * @Date: 2019/8/2 23:25
 * @Description:
 */
@RestController
public class RabbitController {

    private static final String CONSTANT_KEY_STRING = "allhistorymessage";

    @Autowired
    private RabbitService rabbitService;

    @PostMapping("/mymsg")
    public void send2AllService(MyMessage message) {
        rabbitService.send2AllClient(message);
        rabbitService.insertNewHistoryMessageInService(message);
    }

    @GetMapping("/mymsg")
    public List<MyMessage> getAllHistoryMsg() {
        return rabbitService.getAllHistoryMessageInService(CONSTANT_KEY_STRING);
    }

}
