package com.hoctuan.codingforum.constant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class GlobalVariables {
    public static String CLIENT_URL;
    @Value("${client.url}")
    public void setClienUrl(String value) {
        CLIENT_URL = value;
    }

    public static String SERVER_URL;
    @Value("${server.url}")
    public void setServerUrl(String value) {
        SERVER_URL = value;
    }

    public static String JUDGE0_URL;
    @Value("${judge0.url}")
    public void setJudge0Url(String value) {
        JUDGE0_URL = value;
    }
}
