package work.niter.wecoding.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import work.niter.wecoding.entity.FeedBack;
import work.niter.wecoding.enums.ExceptionEnum;
import work.niter.wecoding.exception.RestException;
import work.niter.wecoding.service.MailService;

/**
 * @Author: Cangwu
 * @Date: 2019/7/14 1:25
 * @Description:
 */
@RestController
@RequestMapping("/sendmail")
public class MailController {
    @Autowired
    private MailService mailService;

    @PostMapping("/sign")
    public ResponseEntity<Void> sendMail(@RequestParam String email) {
        if ("".equals(email)) {
            throw new RestException(ExceptionEnum.EMAIL_NULL_EXCEPTION);
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
}

