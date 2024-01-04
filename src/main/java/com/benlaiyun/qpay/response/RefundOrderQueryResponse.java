package com.benlaiyun.qpay.response;

import com.benlaiyun.qpay.model.RefundOrderQueryResModel;

/**
 * Jeepay退款查单响应实现
 * @author jmdhappy
 * @site https://www.jeepay.vip
 * @date 2021-06-18 12:00
 */
public class RefundOrderQueryResponse extends QPayResponse {

    private static final long serialVersionUID = 7654172640802954221L;

    public RefundOrderQueryResModel get() {
        if(getData() == null) return new RefundOrderQueryResModel();
        return getData().toJavaObject(RefundOrderQueryResModel.class);
    }

}
