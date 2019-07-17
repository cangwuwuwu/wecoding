package work.niter.wecoding.service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

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
    @Value("${spring.mail.username}")
    private String from;

    @Async("taskExecutor")
    public void sendMail(String to, String url, int code) {
        MimeMessage mimeMessage = this.mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
            helper.setFrom(this.from);
            helper.setTo(to);
            helper.setSubject("From WeCoding");
            Context context = new Context();
            context.setVariable("address", url);
            context.setVariable("codes", code);
            String template = this.templateEngine.process("email", context);
            helper.setText(template, true);
            this.mailSender.send(mimeMessage);
        } catch (MessagingException var8) {
            var8.printStackTrace();
        }

    }
}

