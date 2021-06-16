package work.niter.wecoding.msg.service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.SimpleMailMessage;
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
@Slf4j
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

    /**
     * 用于邮箱校验的正则表达式
     */
    private static final String EMAIL_PATTERN = "[\\w\\.\\-]+@([\\w\\-]+\\.)+[\\w\\-]+";

    /**
     * 根据指定模板格式发送邮件
     */
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
        log.info(" {} 发送了一封 {} 类型的模板邮件给 {} ", from, tempName, to);
    }

    /**
     * 通用 只发送文字信息
     */
    private void sendMailText(String from, String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
        log.info(" {} 发送了一封文字类型的邮件给 {} ", from, to);
    }

    /**
     * 用于校验邮箱格式是否合法
     */
    private void checkMail(String... emails) {
        for (String email : emails) {
            if (StringUtils.isBlank(email)) {
                throw new RestException(ExceptionEnum.EMAIL_MUST_NOT_EMPTY);
            }
            if (!email.matches(EMAIL_PATTERN)) {
                throw new RestException(ExceptionEnum.EMAIL_FORMAT_ERROR);
            }
        }
    }

    @Async("taskExecutor")
    public void sendMailForTest(String to) {
        checkMail(from, to);
        try {
            sendMailText(from, to, "Wecoding邮件测试", "可以收到此邮件说明邮件服务正常，继续开通提醒服务吧！");
        } catch (Exception e) {
            throw new RestException(ExceptionEnum.EMAIL_SEND_FAILED);
        }
    }

    /**
     * 修改密码时发送验证码
     */
    @Async("taskExecutor")
    public void sendMailForSign(String to) {
        checkMail(from, to);
        int code = (int) ((Math.random() * 9.0D + 1.0D) * 100000.0D);
        try {
            redisTemplate.opsForValue().set(to, String.valueOf(code), 5L, TimeUnit.MINUTES);
            Context context = new Context();
            context.setVariable("address", url);
            context.setVariable("codes", code);
            sendMailMethod(from, to, context, "From Wecoding", "email-code");
        } catch (Exception e) {
            throw new RestException(ExceptionEnum.EMAIL_SEND_FAILED);
        }
    }

    /**
     * 发送反馈
     */
    @Async("taskExecutor")
    public void sendMailForFeedBack(FeedBack feedBack) {
        checkMail(from);
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
            throw new RestException(ExceptionEnum.EMAIL_SEND_FAILED);
        }
    }

    /**
     * 向财务部发一条邮件提醒
     */
    @Async("taskExecutor")
    public void sendMailForSpend(String name, Integer number) {
        checkMail(from);
        try {
            Context context = new Context();
            context.setVariable("name", name);
            context.setVariable("number", number);
            sendMailMethod(from, accDept, context,
                    "计算机协会官网|Wecoding",
                    "email-spend");
        } catch (MessagingException e) {
            e.printStackTrace();
            throw new RestException(ExceptionEnum.EMAIL_SEND_FAILED);
        }
    }

    /**
     * 发送电费不足邮件通知
     */
    @Async("taskExecutor")
    public void sendMailForElectric(
            String roomName, String number,
            String email, String id) {
        checkMail(from, email);
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
            throw new RestException(ExceptionEnum.EMAIL_SEND_FAILED);
        }
    }

    /*审核资源-资源审核结果给资源上传者发送邮件*/
    @Async("taskExecutor")
    public void sendMailForRes(
            String uper, String resName, String resType, String result, String email) {
        checkMail(from, email);
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
            throw new RestException(ExceptionEnum.EMAIL_SEND_FAILED);
        }
    }

}

