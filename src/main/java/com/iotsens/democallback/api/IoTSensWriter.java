package com.iotsens.democallback.api;

import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class IoTSensWriter {

    @Autowired
    IoTSensConfiguration configuration;

    @Autowired
    SensorsConfiguration sensorsConfiguration;

    private static final String INBOX_URL = "http://inbox.iotsens.com/services/rawmessage";

    private static final String GATHERING_VARIABLE_NAME = "GATHERING_REQUIRED";


    public void sendGatheringMeasure(boolean gatherContainer) throws Exception {
        CloseableHttpClient httpclient = HttpClients.createDefault();

        URIBuilder builder = new URIBuilder(INBOX_URL);
        builder.setParameter("appName", configuration.getClientAppCode());
        builder.setParameter("pass", configuration.getClientAppSecret());

        HttpPost requestBase = new HttpPost(builder.toString());
        requestBase.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");

        JSONObject rawMessage = new JSONObject();
        rawMessage.put("node", sensorsConfiguration.getGarbageSensorId());
        rawMessage.put("data", GATHERING_VARIABLE_NAME + ":" + String.valueOf(gatherContainer));

        StringEntity params = new StringEntity(rawMessage.toString());
        requestBase.setEntity(params);

        CloseableHttpResponse response = httpclient.execute(requestBase);
        response.close();
    }
}
