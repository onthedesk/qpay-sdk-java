package com.benlaiyun.qpay;

import com.benlaiyun.qpay.exception.QPayException;
import com.benlaiyun.qpay.model.ChannelUserReqModel;
import com.benlaiyun.qpay.model.TransferOrderCreateReqModel;
import com.benlaiyun.qpay.model.TransferOrderQueryReqModel;
import com.benlaiyun.qpay.request.ChannelUserRequest;
import com.benlaiyun.qpay.request.TransferOrderCreateRequest;
import com.benlaiyun.qpay.request.TransferOrderQueryRequest;
import com.benlaiyun.qpay.response.TransferOrderCreateResponse;
import com.benlaiyun.qpay.response.TransferOrderQueryResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

class TransferOrderTest {

    final static Logger _log = LoggerFactory.getLogger(TransferOrderTest.class);

    @BeforeAll
    public static void initApiKey() {
        QPay.setApiBase(QPayTestData.getApiBase());
        QPay.apiKey = QPayTestData.getApiKey();
        QPay.mchNo = QPayTestData.getMchNo();
        QPay.appId = QPayTestData.getAppId();
    }

    @Test
    public void testTransferOrderCreate() {
        // 转账接口文档：https://docs.jeequan.com/docs/jeepay/transfer_api
        QPayClient qPayClient = QPayClient.getInstance(QPay.appId, QPay.apiKey,
            QPay.getApiBase());
        TransferOrderCreateRequest request = new TransferOrderCreateRequest();
        TransferOrderCreateReqModel model = new TransferOrderCreateReqModel();
        model.setMchNo(QPay.mchNo);                       // 商户号
        model.setAppId(qPayClient.getAppId());            // 应用ID
        model.setMchOrderNo("mho" + new Date().getTime());                // 商户转账单号
        model.setAmount(1L);
        model.setCurrency("CNY");
        model.setIfCode("wxpay");
        model.setEntryType("WX_CASH");
        model.setAccountNo("a6BcIwtTvIqv1zXZohc61biryWok");
        model.setAccountName("");
        model.setTransferDesc("测试转账");
        model.setClientIp("192.166.1.132");                 // 发起转账请求客户端的IP地址
        request.setBizModel(model);

        try {
            TransferOrderCreateResponse response = qPayClient.execute(request);
            _log.info("验签结果：{}", response.checkSign(QPay.apiKey));
            // 判断转账发起是否成功（并不代表转账成功）
            if (response.isSuccess(QPay.apiKey)) {
                String transferId = response.get().getTransferId();
                _log.info("transferId：{}", transferId);
                _log.info("mchOrderNo：{}", response.get().getMchOrderNo());
            } else {
                _log.info("下单失败：mchOrderNo={}, msg={}", model.getMchOrderNo(), response.getMsg());
                _log.info("通道错误码：{}", response.get().getErrCode());
                _log.info("通道错误信息：{}", response.get().getErrMsg());
            }
        } catch (QPayException e) {
            _log.error(e.getMessage());
        }

    }

    @Test
    public void testTransferOrderQuery() {
        // 转账接口文档：https://docs.jeequan.com/docs/jeepay/transfer_api
        QPayClient qPayClient = QPayClient.getInstance(QPay.appId, QPay.apiKey,
            QPay.getApiBase());
        TransferOrderQueryRequest request = new TransferOrderQueryRequest();
        TransferOrderQueryReqModel model = new TransferOrderQueryReqModel();
        model.setMchNo(QPay.mchNo);                                          // 商户号
        model.setAppId(qPayClient.getAppId());                               // 应用ID
        model.setTransferId("T202108121543441860003");                         // 转账单号
        request.setBizModel(model);
        try {
            TransferOrderQueryResponse response = qPayClient.execute(request);
            _log.info("验签结果：{}", response.checkSign(QPay.apiKey));
            if (response.isSuccess(QPay.apiKey)) {
                _log.info("订单信息：{}", response);
                _log.info("转账状态：{}", response.get().getState());
                _log.info("转账金额：{}", response.get().getAmount());
            }
        } catch (QPayException e) {
            e.printStackTrace();
        }

    }


    @Test
    public void getChannelUserIdUrl() {
        QPayClient qPayClient = QPayClient.getInstance(QPay.appId, QPay.apiKey,
            QPay.getApiBase());
        ChannelUserRequest request = new ChannelUserRequest();
        ChannelUserReqModel model = new ChannelUserReqModel();
        model.setAppId(qPayClient.getAppId());
        model.setMchNo(QPay.mchNo);
        model.setRedirectUrl("https://httpdump.io/30cbe");
        model.setIfCode("AUTO");
        request.setBizModel(model);

        try {
            String url = qPayClient.getRequestUrl(request);
            _log.info("跳转 URL: {}", url);
        } catch (QPayException e) {
            e.printStackTrace();
        }

    }
}
