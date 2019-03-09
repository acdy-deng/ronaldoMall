package com.cheery.controller;

import com.cheery.common.ServerResponse;
import com.cheery.service.IMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import static com.cheery.util.TipsUtil.serverError;

/**
 * @desc: 邮件服务控制器
 * @className: EmailController
 * @author: RONALDO
 * @date: 2019-03-09 13:48
 */
@RestController
public class EmailController {

    @Autowired
    private IMailService mailService;

    @Autowired
    private TemplateEngine templateEngine;

    /**
     * desc: 发送邮件推送
     *
     * @param
     * @return
     * @auther RONALDO
     * @date: 2019-03-09 13:58
     */
    @RequestMapping("/email")
    public ServerResponse<?> templateMail() {
        try {
            Context context = new Context();
            context.setVariable("author", "AWMALL官网");
            context.setVariable("tips", "-收到该邮件请勿回复");
            String emailContent = templateEngine.process("Email", context);
            mailService.sendHtmlMail("1781884292@qq.com", "AWMALL官网", emailContent);
        } catch (Exception e) {
            e.printStackTrace();
            return serverError();
        }
        return ServerResponse.createBySuccessMsg("邮件发送成功");
    }

}
