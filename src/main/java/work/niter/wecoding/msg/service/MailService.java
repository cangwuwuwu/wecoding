package work.niter.wecoding.msg.service;

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
import work.niter.wecoding.msg.entity.FeedBack;
import work.niter.wecoding.exception.enums.ExceptionEnum;
import work.niter.wecoding.exception.RestException;

import java.util.concurrent.TimeUnit;

/**
 * @author Cangwu
 * @date 2019/7/14 1:25
 * @description
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
    @Value("${spring.mail.accDept")
    private String accDept;

    private void sendMailMethod(
            String from, String to,
            Context context, String subject,
            String tempName) throws MessagingException {
        MimeMessage mimeMessage = this.mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        helper.setFrom(from);
        helper.setTo(to);
        helper.setSubject(subject);
        String template = this.templateEngine.process(tempName, context);
        helper.setText(template, true);
        this.mailSender.send(mimeMessage);
    }

    /**
     * 修改密码时发送验证码
     */
    @Async("taskExecutor")
    public void sendMailForSign(String to) {
        int code = (int) ((Math.random() * 9.0D + 1.0D) * 100000.0D);
        try {
            redisTemplate.opsForValue().set(to, String.valueOf(code), 5L, TimeUnit.MINUTES);
            Context context = new Context();
            context.setVariable("address", url);
            context.setVariable("codes", code);
            sendMailMethod(from, to, context, "From Wecoding", "email-code");
        } catch (Exception e) {
            throw new RestException(ExceptionEnum.UNKNOWN_ERROR);
        }
    }

    /**
     * 发送反馈
     */
    @Async("taskExecutor")
    public void sendMailForFeedBack(FeedBack feedBack) {
        try {
            Context context = new Context();
            context.setVariable("type", feedBack.getType());
            context.setVariable("content", feedBack.getContent());
            context.setVariable("address", url);
            sendMailMethod(from, "294352178@qq.com", context,
                    "Wecoding : " + feedBack.getType(),
                    "email-feedback");
        } catch (MessagingException e) {
            e.printStackTrace();
            throw new RestException(ExceptionEnum.UNKNOWN_ERROR);
        }
    }

    /**
     * 向财务部发一条邮件提醒
     */
    @Async("taskExecutor")
    public void sendMailForSpend(String name, Integer number) {
        try {
            Context context = new Context();
            context.setVariable("name", name);
            context.setVariable("number", number);
            sendMailMethod(from, accDept, context,
                    "计算机协会官网|Wecoding",
                    "email-spend");
        } catch (MessagingException e) {
            e.printStackTrace();
            throw new RestException(ExceptionEnum.UNKNOWN_ERROR);
        }
    }

    /**
     * 发送电费不足邮件通知
     */
    @Async("taskExecutor")
    public void sendMailForElectric(
            String roomName, String number,
            String email, String id) {
        try {
            Context context = new Context();
            context.setVariable("roomName", roomName);
            context.setVariable("id", id);
            context.setVariable("number", number);
            sendMailMethod(from, email, context,
                    "宿舍电量不足提醒-计算机协会官网|Wecoding",
                    "email-electric");
        } catch (MessagingException e) {
            e.printStackTrace();
            throw new RestException(ExceptionEnum.UNKNOWN_ERROR);
        }
    }

    /*审核资源-资源审核结果给资源上传者发送邮件*/
    @Async("taskExecutor")
    public void sendMailForRes(
            String uper, String resName, String resType, String result, String email) {
        try {
            Context context = new Context();
            context.setVariable("resName", resName);
            context.setVariable("uper", uper);
            context.setVariable("result", result);
            context.setVariable("type", resType);
            String subject = "资源审核-计算机协会官网|Wecoding";
            sendMailMethod(from, email, context, subject, "email-resourceAudit");
        } catch (MessagingException e) {
            e.printStackTrace();
            throw new RestException(ExceptionEnum.UNKNOWN_ERROR);
        }
    }

}

