package com.dingtalk.controller;

import com.dingtalk.service.GitlabWebhookService;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.webhook.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class GitlabWebhookController {
    private static final Logger log = LoggerFactory.getLogger(GitlabWebhookController.class);

    @Autowired
    private GitlabWebhookService gitlabWebhookService;

    @PostMapping("/gitlab/webhook")
    public String webhook(HttpServletRequest request) {
        try {
            this.gitlabWebhookService.handleRequest(request);
        } catch (Exception e) {
            e.printStackTrace();
//        } finally {
//            log.info(request.toString());
        }
        return "OK";
    }
}
