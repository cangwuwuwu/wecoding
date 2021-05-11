package work.niter.wecoding.msg.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import work.niter.wecoding.msg.entity.MyMessage;
import work.niter.wecoding.msg.service.RabbitService;

import java.util.List;

/**
 * @author Cangwu
 * @date 2019/8/2 23:25
 * @description
 */
@RestController
@RequestMapping("/mymsg")
public class RabbitController {

    private static final String CONSTANT_KEY_STRING = "allhistorymessage";

    @Autowired
    private RabbitService rabbitService;

    @PostMapping
    public void send2AllService(MyMessage message) {
        rabbitService.send2AllClient(message);
        rabbitService.insertNewHistoryMessageInService(message);
    }

    @GetMapping
    public ResponseEntity<List<MyMessage>> getAllHistoryMsg() {
        return ResponseEntity.ok(rabbitService.getAllHistoryMessageInService(CONSTANT_KEY_STRING));
    }

}
