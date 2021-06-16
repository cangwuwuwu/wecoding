package work.niter.wecoding.msg.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import work.niter.wecoding.msg.entity.FeedBack;
import work.niter.wecoding.exception.enums.ExceptionEnum;
import work.niter.wecoding.exception.RestException;
import work.niter.wecoding.msg.service.MailService;

/**
 * @author Cangwu
 * @date 2019/7/14 1:25
 * @description
 */
@RestController
@RequestMapping("/sendmail")
public class MailController {
    @Autowired
    private MailService mailService;

    @PostMapping("/sign")
    public ResponseEntity<Void> sendMail(@RequestParam String email) {
        if ("".equals(email)) {
            throw new RestException(ExceptionEnum.EMAIL_MUST_NOT_EMPTY);
        } else {
            mailService.sendMailForSign(email);
            return ResponseEntity.ok().build();
        }
    }

    @PostMapping("/feedback")
    public ResponseEntity<Void> feedBackMail(@RequestBody FeedBack feedBack) {
        mailService.sendMailForFeedBack(feedBack);
        return ResponseEntity.ok().build();
    }

    /**
     * 测试用户输入的邮件是否能收到系统发送的邮件
     */
    @GetMapping("/test")
    public ResponseEntity<Void> sendMailTest(@RequestParam String email) {
        mailService.sendMailForTest(email);
        return ResponseEntity.ok().build();
    }
}

