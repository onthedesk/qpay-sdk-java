package com.benlaiyun.qpay;

import com.alibaba.fastjson.JSONObject;
import com.benlaiyun.qpay.exception.QPayException;
import com.benlaiyun.qpay.net.APIQPayRequest;
import com.benlaiyun.qpay.net.APIResource;
import com.benlaiyun.qpay.net.RequestOptions;
import com.benlaiyun.qpay.request.QPayRequest;
import com.benlaiyun.qpay.response.QPayResponse;
import com.benlaiyun.qpay.util.JSONWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * QPay sdk客户端
 *
 * @author jmdhappy
 * @site https://www.benlaiyun.com
 * @date 2021-06-08 11:00
 */
public class QPayClient extends APIResource {

    private static final Map<String, QPayClient> clientMap = new HashMap<String, QPayClient>();

    private String appId;
    private String signType = QPay.DEFAULT_SIGN_TYPE;
    private String apiKey = QPay.apiKey;
    private String apiBase = QPay.getApiBase();

    public QPayClient(String apiBase, String signType, String apiKey) {
        this.apiBase = apiBase;
        this.signType = signType;
        this.apiKey = apiKey;
    }

    public QPayClient(String apiBase, String apiKey) {
        this.apiBase = apiBase;
        this.apiKey = apiKey;
    }

    public QPayClient(String apiKey) {
        this.apiKey = apiKey;
    }

    public QPayClient() {
    }

    public static synchronized QPayClient getInstance(String appId, String apiKey,
                                                      String apiBase) {
        QPayClient client = clientMap.get(appId);
        if (client != null) {
            return client;
        }
        client = new QPayClient();
        clientMap.put(appId, client);
        client.appId = appId;
        client.apiKey = apiKey;
        client.apiBase = apiBase;
        return client;
    }

    public static synchronized QPayClient getInstance(String appId, String apiKey) {
        QPayClient client = clientMap.get(appId);
        if (client != null) {
            return client;
        }
        client = new QPayClient();
        clientMap.put(appId, client);
        client.appId = appId;
        client.apiKey = apiKey;
        return client;
    }

    public static synchronized QPayClient getInstance(String appId) {
        QPayClient client = clientMap.get(appId);
        if (client != null) {
            return client;
        }
        client = new QPayClient();
        clientMap.put(appId, client);
        client.appId = appId;
        return client;
    }

    public String getAppId() {
        return appId;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getApiBase() {
        return apiBase;
    }

    public void setApiBase(String apiBase) {
        this.apiBase = apiBase;
    }

    public <T extends QPayResponse> T execute(QPayRequest<T> request) throws QPayException {

        // 支持用户自己设置RequestOptions
        if (request.getRequestOptions() == null) {
            RequestOptions options = RequestOptions.builder()
                .setVersion(request.getApiVersion())
                .setUri(request.getApiUri())
                .setAppId(this.appId)
                .setApiKey(this.apiKey)
                .build();
            request.setRequestOptions(options);
        }

        return execute(request, RequestMethod.POST, this.apiBase);
    }

    public <T extends QPayResponse> T executeByRSA2(QPayRequest<T> request) throws QPayException {

        if (request.getRequestOptions() == null) {
            RequestOptions options = RequestOptions.builder().setVersion(request.getApiVersion()).setUri(request.getApiUri()).setAppId(this.appId).setApiKey(this.apiKey).setSignType("RSA2").build();
            request.setRequestOptions(options);
        }

        return execute(request, RequestMethod.POST, this.apiBase);
    }


    public String getRequestUrl(QPayRequest request) throws QPayException {
        // 支持用户自己设置RequestOptions
        if (request.getRequestOptions() == null) {
            RequestOptions options = RequestOptions.builder()
                .setVersion(request.getApiVersion())
                .setUri(request.getApiUri())
                .setAppId(this.appId)
                .setApiKey(this.apiKey)
                .build();
            request.setRequestOptions(options);
        }
        String jsonParam = new JSONWriter().write(request.getBizModel(), true);

        JSONObject params = JSONObject.parseObject(jsonParam);
        request.getRequestOptions();

        return APIQPayRequest.buildURLWithSign(this.apiBase, params, request.getRequestOptions()).toString();
    }
}
