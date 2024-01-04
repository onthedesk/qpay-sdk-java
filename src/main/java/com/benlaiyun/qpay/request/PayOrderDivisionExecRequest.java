package com.benlaiyun.qpay.request;

import com.benlaiyun.qpay.QPay;
import com.benlaiyun.qpay.model.QPayObject;
import com.benlaiyun.qpay.net.RequestOptions;
import com.benlaiyun.qpay.response.PayOrderDivisionExecResponse;

/***
* 分账发起
*
* @author terrfly
* @site https://www.jeepay.vip
* @date 2021/8/27 10:19
*/
public class PayOrderDivisionExecRequest implements QPayRequest<PayOrderDivisionExecResponse> {

    private String apiVersion = QPay.VERSION;
    private String apiUri = "api/division/exec";
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
    public Class<PayOrderDivisionExecResponse> getResponseClass() {
        return PayOrderDivisionExecResponse.class;
    }

}
