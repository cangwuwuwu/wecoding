package work.niter.wecoding.controller;

import java.util.concurrent.TimeUnit;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import work.niter.wecoding.exception.NullEmailRequestException;
import work.niter.wecoding.service.MailService;

/**
 * @Author: Cangwu
 * @Date: 2019/7/14 1:25
 * @Description:
 */
@RestController
public class MailController {
    @Autowired
    private MailService mailService;
    @Autowired
    private StringRedisTemplate redisTemplate;

    @PostMapping({"/sendmail"})
    public String sendMail(HttpServletRequest request) {
        try {
            String email = request.getParameter("email");
            if ("".equals(email)) {
                throw new NullEmailRequestException();
            } else {
                int code = (int)((Math.random() * 9.0D + 1.0D) * 100000.0D);
                this.redisTemplate.opsForValue().set(email, String.valueOf(code), 5L, TimeUnit.MINUTES);
                this.mailService.sendMail(email, "http://39.106.85.24:8082", code);
                return "已发送,验证码五分钟内有效!";
            }
        } catch (Exception var4) {
            var4.printStackTrace();
            return "发送失败,请填写邮箱!";
        }
    }
}

