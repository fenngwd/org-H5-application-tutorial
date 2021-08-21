package com.dingtalk.service;

import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.User;
import org.gitlab4j.api.webhook.EventMergeRequest;
import org.gitlab4j.api.webhook.NoteEvent;
import org.gitlab4j.api.webhook.WebHookListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DingtalkNotificationListener implements WebHookListener {

    @Autowired
    private GitLabApi gitLabApi;

    @Autowired
    private DingtalkManager dingtalkManager;

    @Override
    public void onNoteEvent(NoteEvent noteEvent) {
        // 1. we only care about note on merge request
        EventMergeRequest mergeRequest = noteEvent.getMergeRequest();
        if (mergeRequest == null) {
            return;
        }

        Integer note_receiver_id = mergeRequest.getAuthorId();
        String receiver_uid_list = "";
        try {
            User note_receiver = this.gitLabApi.getUserApi().getUser(note_receiver_id);
            if (note_receiver.getPublicEmail().equals("weidong.feng@shanbay.com")) {
                receiver_uid_list = "154729416720743372";
            } else {
                return;
            }
        } catch (GitLabApiException e) {
            e.printStackTrace();
        }

        User note_sender = noteEvent.getUser();

        UUID uuid = UUID.randomUUID();
        String msg = String.format("%s 在Gitlab上给你的PR添加了一条评论，快去看看他说了什么吧！ %s", note_sender.getPublicEmail(), uuid.toString());

        dingtalkManager.sendCorpMessage(msg, receiver_uid_list);
    }
}
