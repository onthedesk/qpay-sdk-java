package com.benlaiyun.qpay.request;

import com.benlaiyun.qpay.model.QPayObject;
import com.benlaiyun.qpay.net.RequestOptions;
import com.benlaiyun.qpay.response.QPayResponse;

/**
 * Jeepay请求接口
 * @author jmdhappy
 * @site https://www.jeepay.vip
 * @date 2021-06-08 11:00
 */
public interface QPayRequest<T extends QPayResponse> {

    /**
     * 获取当前接口的路径
     * @return
     */
    String getApiUri();

    /**
     * 获取当前接口的版本
     * @return
     */
    String getApiVersion();

    /**
     * 设置当前接口的版本
     * @return
     */
    void setApiVersion(String apiVersion);

    RequestOptions getRequestOptions();

    void setRequestOptions(RequestOptions options);

    QPayObject getBizModel();

    void setBizModel(QPayObject bizModel);

    Class<T> getResponseClass();

}
