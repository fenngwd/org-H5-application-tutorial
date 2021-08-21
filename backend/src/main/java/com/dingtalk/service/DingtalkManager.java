package com.dingtalk.service;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiMessageCorpconversationAsyncsendV2Request;
import com.dingtalk.api.response.OapiMessageCorpconversationAsyncsendV2Response;
import com.dingtalk.config.AppConfig;
import com.dingtalk.domain.ServiceResult;
import com.taobao.api.ApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.dingtalk.config.UrlConstant.URL_CORP_CONVERSION;

@Service
public class DingtalkManager {
    public static final Logger log = LoggerFactory.getLogger(DingtalkManager.class);

    private final AppConfig appConfig;

    private final TokenService tokenService;

    private final DingTalkClient dingTalkClient;

    @Autowired
    public DingtalkManager(AppConfig appConfig, TokenService tokenService) {
        this.appConfig = appConfig;
        this.tokenService = tokenService;
        this.dingTalkClient = new DefaultDingTalkClient(URL_CORP_CONVERSION);
    }

    public void sendCorpMessage(String message, String receiver_uid_list) {
        OapiMessageCorpconversationAsyncsendV2Request request = new OapiMessageCorpconversationAsyncsendV2Request();
        request.setAgentId(Long.parseLong(appConfig.getAgentId()));
        request.setUseridList(receiver_uid_list);
//        request.setUseridList("154729416720743372");

        OapiMessageCorpconversationAsyncsendV2Request.Msg msg = new OapiMessageCorpconversationAsyncsendV2Request.Msg();
        msg.setMsgtype("text");
        OapiMessageCorpconversationAsyncsendV2Request.Text text = new OapiMessageCorpconversationAsyncsendV2Request.Text();
        text.setContent(message);
        msg.setText(text);

        request.setMsg(msg);

        // 获取accessToken
        ServiceResult<String> accessTokenSr = tokenService.getAccessToken();
        if (!accessTokenSr.isSuccess()) {
            return;
        }

        String accessToken = accessTokenSr.getResult();

        OapiMessageCorpconversationAsyncsendV2Response response = null;
        try {
            response = dingTalkClient.execute(request, accessToken);
        } catch (ApiException e) {
            log.error(e.getErrMsg());
        }

        if (response != null) {
            log.info(response.getBody());
        }
    }
}
