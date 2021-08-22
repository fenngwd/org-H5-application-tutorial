package com.dingtalk.config;

import org.gitlab4j.api.GitLabApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

@Configuration
public class GitlabConfig {

    @Value("${gitlab.host_url}")
    private String gitlab_host_url;

    @Value("${gitlab.personal_access_token}")
    private String gitlab_personal_access_token;

    @Bean
    public GitLabApi getGitLabApi() {
        return new GitLabApi(gitlab_host_url, gitlab_personal_access_token);
    }

    public String getGitlabPersonalAccessToken() {
        return gitlab_personal_access_token;
    }

    public void setGitlabPersonalAccessToken(String gitlab_personal_access_token) {
        this.gitlab_personal_access_token = gitlab_personal_access_token;
    }

    public String getGitlabHostUrl() {
        return gitlab_host_url;
    }

    public void setGitlabHostUrl(String gitlab_host_url) {
        this.gitlab_host_url = gitlab_host_url;
    }

    @Bean
    public CommonsRequestLoggingFilter requestLoggingFilter() {
        CommonsRequestLoggingFilter loggingFilter = new CommonsRequestLoggingFilter();
        loggingFilter.setIncludeClientInfo(true);
        loggingFilter.setIncludeQueryString(true);
        loggingFilter.setIncludePayload(true);
        loggingFilter.setMaxPayloadLength(64000);
        return loggingFilter;
    }
}
