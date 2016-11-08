package com.iotsens.democallback.api;

import com.iotsens.democallback.dto.Measure;
import com.iotsens.democallback.exceptions.IotSensAPIException;import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigInteger;
import java.net.URISyntaxException;
import java.security.MessageDigest;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@Component
public class IoTSensReader {

    public static final String REQUESTER_APP_HEADER_NAME = "GG-Requester-Application";

    public static final String REQUEST_SIGNATURE_HEADER_NAME = "GG-Request-Signature";

    public static final String REQUEST_TIMESTAMP_HEADER_NAME = "GG-Request-Timestamp";

    public static final String REQUEST_USER_HEADER_NAME = "IOT-Authorized-User";

    private static final String TEMP_URL = "http://api.iotsens.com/v1/sensors/%s/variables/%s/measures";

    private static final String VARIABLE_NAME = "TEMP";

    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");


    @Autowired
    IoTSensConfiguration configuration;

    @Autowired
    SensorsConfiguration sensorsConfiguration;

    public Measure readTempMeasure() throws Exception {
        CloseableHttpResponse response = makeHttpCall();

        return buildMeasureFromResponse(response);
    }

    private Measure buildMeasureFromResponse(CloseableHttpResponse response) throws IOException, JSONException, ParseException {
        HttpEntity entity = response.getEntity();
        JSONObject jsonResponse = new JSONObject(EntityUtils.toString(entity));

        JSONArray data = jsonResponse.getJSONArray("data");
        JSONObject firstMeasure = (JSONObject) data.get(0);
        String tempValue = firstMeasure.getString("value");
        String tempTimestampString = firstMeasure.getString("timestamp");

        response.close();

        return new Measure(sensorsConfiguration.getTemperatureSensorId(), VARIABLE_NAME, tempValue, sdf.parse(tempTimestampString));
    }

    private CloseableHttpResponse makeHttpCall() throws URISyntaxException, IOException, IotSensAPIException {
        CloseableHttpClient httpclient = HttpClients.createDefault();

        URIBuilder builder = new URIBuilder(String.format(TEMP_URL, sensorsConfiguration.getTemperatureSensorId(), VARIABLE_NAME));
        builder.setParameter("limit", "1");
        HttpRequestBase requestBase = new HttpGet(builder.toString());

        addAuthenticationHeaders(requestBase);

        CloseableHttpResponse response = httpclient.execute(requestBase);

        if (response.getStatusLine().getStatusCode() >=400){
            throw new IotSensAPIException();
        }

        return response;
    }

    private void addAuthenticationHeaders(HttpRequestBase requestBase) {
        requestBase.setHeader(REQUEST_USER_HEADER_NAME, configuration.getUsername());

        String requestTimestamp = String.valueOf(System.currentTimeMillis());
        String hashSignature = buildExpectedDigest(requestTimestamp, configuration.getClientAppCode(), configuration.getClientAppSecret());

        requestBase.setHeader(REQUESTER_APP_HEADER_NAME, configuration.getClientAppCode());
        requestBase.setHeader(REQUEST_TIMESTAMP_HEADER_NAME, requestTimestamp);
        requestBase.setHeader(REQUEST_SIGNATURE_HEADER_NAME, hashSignature);
    }


    public String buildExpectedDigest(String requestTimestamp, String requesterApp, String requestSecret) {
        String rootDigestString = requestTimestamp
                + requestSecret
                + requesterApp;

        return cipherPassword(rootDigestString);
    }

    private int RESULT_LENGTH = 16;


    public String cipherPassword(String plainPassword) {
        MessageDigest cript;
        String cripted;
        try {
            cript = MessageDigest.getInstance("MD5");

            cript.reset();
            cript.update(plainPassword.getBytes("utf8"));
            cripted = new BigInteger(1, cript.digest()).toString(RESULT_LENGTH);

            while (cripted.length() < 32)
                cripted = "0" + cripted;

            return cripted;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

    }

}
