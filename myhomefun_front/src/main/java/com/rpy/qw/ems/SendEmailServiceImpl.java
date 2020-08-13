package com.rpy.qw.ems;

import com.rpy.qw.data.SysStaticData;
import com.rpy.qw.post.vo.SimpleCommentVo;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.time.Duration;
import java.util.UUID;

/**
 * @program: myhomefun
 * @description:
 * @author: 任鹏宇
 * @create: 2020-08-03 14:49
 **/
@Service
public class  SendEmailServiceImpl  implements SendEmailService{


    /**
     * 从配置文件中获取发件人
     */
    @Value("${spring.mail.username}")
    private String sender;


    @Autowired
    TemplateEngine templateEngine;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Override
    public void sendCode(String toEmail) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(toEmail);
            helper.setFrom(sender);
            helper.setSubject("我的趣屋");
            helper.setText(getContext(toEmail), true);
            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private String getContext(String toEmail){
        String code= RandomUtils.nextInt(100000,999999)+"";
        Context ctx = new Context();
        ctx.setVariable("code", code);
        // 使用TemplateEngine 对模版进行渲染
        String mail = templateEngine.process("email-client.html", ctx);

        // 存入 redis
        redisTemplate.opsForValue().set(SysStaticData.REGITSER_CODE_REDIS_KEY_PRE+toEmail,code, Duration.ofMinutes(15));
        return mail;
    }
}
