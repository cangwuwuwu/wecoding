package work.niter.wecoding.controller;

import java.util.concurrent.TimeUnit;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import work.niter.wecoding.entity.FeedBack;
import work.niter.wecoding.exception.NullEmailRequestException;
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

    @PostMapping({"/sign"})
    public String sendMail(HttpServletRequest request) {
        try {
            String email = request.getParameter("email");
            if ("".equals(email)) {
                throw new NullEmailRequestException();
            } else {
                mailService.sendMailForSign(email);
                return "已发送,验证码五分钟内有效!";
            }
        } catch (Exception var4) {
            var4.printStackTrace();
            return "发送失败,请填写邮箱!";
        }
    }

    @PostMapping("/feedback")
    public String feedBackMail(FeedBack feedBack) {
        try {
            mailService.sendMailForFeedBack(feedBack);
        } catch (Exception var10) {
            var10.printStackTrace();;
            return "反馈发送出错...";
        }
        return "谢谢您的反馈";
    }
}

