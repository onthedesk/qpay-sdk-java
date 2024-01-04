package com.benlaiyun.qpay.response;

import com.benlaiyun.qpay.model.TransferOrderCreateResModel;

/***
* Jeepay转账响应实现
*
* @author terrfly
* @site https://www.jeepay.vip
* @date 2021/8/13 16:25
*/
public class TransferOrderCreateResponse extends QPayResponse {

    private static final long serialVersionUID = 7419683269497002904L;

    public TransferOrderCreateResModel get() {
        if(getData() == null) {
            return new TransferOrderCreateResModel();
        }
        return getData().toJavaObject(TransferOrderCreateResModel.class);
    }

    @Override
    public boolean isSuccess(String apiKey) {
        if(super.isSuccess(apiKey)) {
            int state = get().getState();
            return state == 0 || state == 1 || state == 2;
        }
        return false;
    }

}
