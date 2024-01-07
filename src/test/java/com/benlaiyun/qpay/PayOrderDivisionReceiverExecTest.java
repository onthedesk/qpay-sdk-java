package com.benlaiyun.qpay;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.benlaiyun.qpay.exception.QPayException;
import com.benlaiyun.qpay.model.PayOrderDivisionExecReqModel;
import com.benlaiyun.qpay.request.PayOrderDivisionExecRequest;
import com.benlaiyun.qpay.response.PayOrderDivisionExecResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class PayOrderDivisionReceiverExecTest {

    final static Logger _log = LoggerFactory.getLogger(PayOrderDivisionReceiverExecTest.class);

    @BeforeAll
    public static void initApiKey() {
        QPay.setApiBase(QPayTestData.getApiBase());
        QPay.apiKey = QPayTestData.getApiKey();
        QPay.mchNo = QPayTestData.getMchNo();
        QPay.appId = QPayTestData.getAppId();
    }

    @Test
    public void testPayOrderDivisionExec() {
        // 分账接口文档：https://docs.jeequan.com/docs/jeepay/division_api
        QPayClient qPayClient = QPayClient.getInstance(QPay.appId, QPay.apiKey, QPay.getApiBase());
        PayOrderDivisionExecRequest request = new PayOrderDivisionExecRequest();
        PayOrderDivisionExecReqModel model = new PayOrderDivisionExecReqModel();
        request.setBizModel(model);
        model.setMchNo(QPay.mchNo);                       // 商户号
        model.setAppId(qPayClient.getAppId());            // 应用ID
        model.setPayOrderId("P1470667876906389505");
        model.setUseSysAutoDivisionReceivers((byte) 0);

        JSONArray receviers = new JSONArray();
        receviers.add(JSONObject.parseObject("{receiverId: '800004', receiverGroupId: '', divisionProfit: '0.1'}"));
        receviers.add(JSONObject.parseObject("{receiverId: '800005', receiverGroupId: '', divisionProfit: '0.2'}"));

        model.setReceivers(receviers.toJSONString());

        try {
            PayOrderDivisionExecResponse response = qPayClient.execute(request);
            _log.info("验签结果：{}", response.checkSign(QPay.apiKey));
            // 判断转账发起是否成功（并不代表转账成功）
            if (response.isSuccess(QPay.apiKey)) {
                _log.info("渠道分账订单号：{}， 分账成功", response.get().getChannelBatchOrderId());
            } else {
                _log.info("分账失败：payOrderId：{}", model.getPayOrderId());
                _log.info("通道错误码：{}", response.get().getErrCode());
                _log.info("通道错误信息：{}", response.get().getErrMsg());
            }
        } catch (QPayException e) {
            _log.error(e.getMessage());
        }

    }

}
