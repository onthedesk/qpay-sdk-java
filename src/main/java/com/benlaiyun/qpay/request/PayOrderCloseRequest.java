package com.benlaiyun.qpay.request;

import com.benlaiyun.qpay.QPay;
import com.benlaiyun.qpay.model.QPayObject;
import com.benlaiyun.qpay.net.RequestOptions;
import com.benlaiyun.qpay.response.PayOrderCloseResponse;

/**
 * Jeepay支付 订单关闭请求实现
 *
 * @author xiaoyu
 * @site https://www.jeequan.com
 * @date 2022/1/25 9:56
 */
public class PayOrderCloseRequest implements QPayRequest<PayOrderCloseResponse> {

    private String apiVersion = QPay.VERSION;
    private String apiUri = "api/pay/close";
    private RequestOptions options;
    private QPayObject bizModel = null;

    @Override
    public String getApiUri() {
        return this.apiUri;
    }

    @Override
    public String getApiVersion() {
        return this.apiVersion;
    }

    @Override
    public void setApiVersion(String apiVersion) {
        this.apiVersion = apiVersion;
    }

    @Override
    public RequestOptions getRequestOptions() {
        return this.options;
    }

    @Override
    public void setRequestOptions(RequestOptions options) {
        this.options = options;
    }

    @Override
    public QPayObject getBizModel() {
        return this.bizModel;
    }

    @Override
    public void setBizModel(QPayObject bizModel) {
        this.bizModel = bizModel;
    }

    @Override
    public Class<PayOrderCloseResponse> getResponseClass() {
        return PayOrderCloseResponse.class;
    }

}
