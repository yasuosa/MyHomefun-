package com.rpy.qw.ems;

import javax.mail.MessagingException;

/**
 * @program: myhomefun
 * @description: 发送邮箱验证码
 * @author: 任鹏宇
 * @create: 2020-08-03 14:48
 **/
public interface SendEmailService {
    void  sendCode(String toEmail) throws MessagingException;

}
