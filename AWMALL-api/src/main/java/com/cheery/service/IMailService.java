package com.cheery.service;

import javax.mail.MessagingException;

/**
 * @desc: 邮件服务逻辑层接口
 * @className: IMailService
 * @author: RONALDO
 * @date: 2019-03-09 13:41
 */
public interface IMailService {

    /**
     * desc: 发送HTML邮件
     *
     * @param to      发送给谁
     * @param subject 发送的主题
     * @param content 发送的内容
     * @return
     * @throws MessagingException
     * @auther RONALDO
     * @date: 2019-03-09 13:41
     */
    void sendHtmlMail(String to, String subject, String content) throws MessagingException;

}
