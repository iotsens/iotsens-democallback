package com.iotsens.democallback.api;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "iotsens")
public class IoTSensConfiguration {

    String username;
    String clientAppCode;
    String clientAppSecret;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getClientAppCode() {
        return clientAppCode;
    }

    public void setClientAppCode(String clientAppCode) {
        this.clientAppCode = clientAppCode;
    }

    public String getClientAppSecret() {
        return clientAppSecret;
    }

    public void setClientAppSecret(String clientAppSecret) {
        this.clientAppSecret = clientAppSecret;
    }
}
