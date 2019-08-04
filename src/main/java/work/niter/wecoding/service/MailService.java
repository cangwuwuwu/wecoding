package work.niter.wecoding.service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import work.niter.wecoding.entity.FeedBack;

import java.util.concurrent.TimeUnit;

/**
 * @Author: Cangwu
 * @Date: 2019/7/14 1:25
 * @Description:
 */
@Service
public class MailService {
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private TemplateEngine templateEngine;
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Value("${spring.mail.username}")
    private String from;
    @Value("${spring.mail.url}")
    private String url;

    @Async("taskExecutor")
    public void sendMailForSign(String to) {
        int code = (int)((Math.random() * 9.0D + 1.0D) * 100000.0D);
        redisTemplate.opsForValue().set(to, String.valueOf(code), 5L, TimeUnit.MINUTES);
        MimeMessage mimeMessage = this.mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject("From Wecoding");
            Context context = new Context();
            context.setVariable("address", url);
            context.setVariable("codes", code);
            String template = this.templateEngine.process("email-code", context);
            helper.setText(template, true);
            this.mailSender.send(mimeMessage);
        } catch (MessagingException var8) {
            var8.printStackTrace();
        }
    }

    @Async("taskExecutor")
    public void sendMailForFeedBack(FeedBack feedBack) {
        MimeMessage mimeMessage = this.mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
            helper.setFrom(from);
            helper.setTo("294352178@qq.com");
            helper.setSubject("Wecoding : " + feedBack.getType());
            Context context = new Context();
            context.setVariable("type", feedBack.getType());
            context.setVariable("content", feedBack.getContent());
            context.setVariable("address", url);
            String template = this.templateEngine.process("email-feedback", context);
            helper.setText(template, true);
            this.mailSender.send(mimeMessage);
        } catch (MessagingException var9) {
            var9.printStackTrace();
        }
    }
}

