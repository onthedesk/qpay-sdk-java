package com.benlaiyun.qpay.response;

import com.benlaiyun.qpay.model.PayOrderQueryResModel;

/**
 * Jeepay支付查单响应实现
 * @author jmdhappy
 * @site https://www.jeepay.vip
 * @date 2021-06-08 11:00
 */
public class PayOrderQueryResponse extends QPayResponse {

    private static final long serialVersionUID = 7654172640802954221L;

    public PayOrderQueryResModel get() {
        if(getData() == null) return new PayOrderQueryResModel();
        return getData().toJavaObject(PayOrderQueryResModel.class);
    }

}
