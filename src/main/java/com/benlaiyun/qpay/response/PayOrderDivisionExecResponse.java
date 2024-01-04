package com.benlaiyun.qpay.response;

import com.benlaiyun.qpay.model.PayOrderDivisionExecResModel;


/***
* 发起分账响应实现
*
* @author terrfly
* @site https://www.jeepay.vip
* @date 2021/8/27 10:22
*/
public class PayOrderDivisionExecResponse extends QPayResponse {

    private static final long serialVersionUID = 7419683269497002904L;

    public PayOrderDivisionExecResModel get() {
        if(getData() == null) return new PayOrderDivisionExecResModel();
        return getData().toJavaObject(PayOrderDivisionExecResModel.class);
    }

    @Override
    public boolean isSuccess(String apiKey) {
        if(super.isSuccess(apiKey)) {
            int state = get().getState();
            return state == 1;
        }
        return false;
    }

}
