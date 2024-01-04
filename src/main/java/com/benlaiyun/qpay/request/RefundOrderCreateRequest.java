package com.benlaiyun.qpay.request;

import com.benlaiyun.qpay.QPay;
import com.benlaiyun.qpay.model.QPayObject;
import com.benlaiyun.qpay.net.RequestOptions;
import com.benlaiyun.qpay.response.RefundOrderCreateResponse;

/**
 * Jeepay退款请求实现
 * @author jmdhappy
 * @site https://www.jeepay.vip
 * @date 2021-06-18 09:00
 */
public class RefundOrderCreateRequest implements QPayRequest<RefundOrderCreateResponse> {

    private String apiVersion = QPay.VERSION;
    private String apiUri = "api/refund/refundOrder";
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
    public Class<RefundOrderCreateResponse> getResponseClass() {
        return RefundOrderCreateResponse.class;
    }

}
