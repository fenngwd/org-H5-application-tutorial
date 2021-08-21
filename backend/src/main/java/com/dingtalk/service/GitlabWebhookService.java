package com.dingtalk.service;

import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.webhook.Event;
import org.gitlab4j.api.webhook.WebHookManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class GitlabWebhookService {
    public static final Logger log = LoggerFactory.getLogger(GitlabWebhookService.class);

    private WebHookManager webHookManager;

    public GitlabWebhookService() {
        this.webHookManager = new WebHookManager();
        this.webHookManager.addListener(new DingtalkNotificationListener());
    }

    public void handleEvent(Event event) throws GitLabApiException {
        this.webHookManager.handleEvent(event);
    }

}
