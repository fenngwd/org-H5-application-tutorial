package com.dingtalk.service;

import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.webhook.Event;
import org.gitlab4j.api.webhook.WebHookManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class GitlabWebhookService {
    public static final Logger log = LoggerFactory.getLogger(GitlabWebhookService.class);

    private WebHookManager webHookManager;

    @Autowired
    public GitlabWebhookService(DingtalkNotificationListener listener) {
        this.webHookManager = new WebHookManager();
        this.webHookManager.addListener(listener);
    }

    public void handleEvent(Event event) throws GitLabApiException {
        this.webHookManager.handleEvent(event);
    }

    public void handleRequest(HttpServletRequest request) throws GitLabApiException {
        this.webHookManager.handleRequest(request);
    }
}
