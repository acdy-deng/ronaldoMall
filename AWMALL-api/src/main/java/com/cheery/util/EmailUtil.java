package com.cheery.util;

import com.cheery.service.IMailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

/**
 * @desc: 邮件发送工具类
 * @className: EmailUtil
 * @author: RONALDO
 * @date: 2019-03-09 14:22
 */
@Component
@ConditionalOnProperty(prefix = "scheduling", name = "enabled", havingValue = "true")
public class EmailUtil {

    private Logger logger = LoggerFactory.getLogger(EmailUtil.class);

    @Autowired
    private IMailService mailService;

    @Autowired
    private TemplateEngine templateEngine;

    /**
     * desc: 每周 周一、周二、周三、周四、周五的中午12：00触发
     *
     * @param
     * @return
     * @auther RONALDO
     * @date: 2019-03-09 14:56
     */
    @Scheduled(cron = "0 00 12 ? * MON-FRI")
    public void pushEmail() {
        try {
            Context context = new Context();
            context.setVariable("author", "AWMALL官网");
            context.setVariable("tips", "-收到该邮件请勿回复");
            String emailContent = templateEngine.process("Email", context);
            mailService.sendHtmlMail("1781884292@qq.com", "AWMALL官网", emailContent);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("**********邮件发送失败**********");
        }
        logger.info("**********邮件发送成功**********");
    }

}
