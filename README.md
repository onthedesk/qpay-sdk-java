# qpay-sdk-java

## 接口文档

接口文档：详见商户后台

## 快速开始

引入sdk依赖（最新发布版本1.6.0），支持：支付、退款、转账、分账等接口。

```xml
  <dependency>
      <groupId>com.benlaiyun</groupId>
      <artifactId>qpay-sdk-java</artifactId>
      <version>1.6.0</version>
  </dependency>
```

客户端调用代码可参考：

完整支付测试代码 `com.benlaiyun.qpay.PayOrderTest`

```java
    // 创建客户端
    QPayClient QPayClient = QPayClient.getInstance(QPay.appId, QPay.apiKey);

    // 构建请求数据
    String wayCode = "WX_BAR";                           // 支付方式
    PayOrderCreateRequest request = new PayOrderCreateRequest();
    PayOrderCreateReqModel model = new PayOrderCreateReqModel();
    model.setMchNo(QPay.mchNo);                       // 商户号
    model.setAppId(QPayClient.getAppId());            // 应用ID
    String orderNo = "mho" + new Date().getTime();
    model.setMchOrderNo(orderNo);                       // 商户订单号
    model.setWayCode(wayCode);                          // 支付方式
    model.setAmount(1l);                                // 金额，单位分
    model.setCurrency("CNY");                           // 币种，目前只支持cny
    model.setClientIp("192.166.1.132");                 // 发起支付请求客户端的IP地址
    model.setSubject("商品标题");                         // 商品标题
    model.setBody("商品描述");                            // 商品描述
    model.setNotifyUrl("https://www.jeequan.com");      // 异步通知地址
    model.setReturnUrl("");                             // 前端跳转地址
    model.setChannelExtra(channelExtra(wayCode));       // 渠道扩展参数
    model.setExtParam("");                              // 商户扩展参数,回调时原样返回
    request.setBizModel(model);
    
    // 发起统一下单
    PayOrderCreateResponse response = QPayClient.execute(request);

    // 验证返回数据签名
    response.checkSign(QPay.apiKey);

    // 判断下单是否返回成功
    response.isSuccess(QPay.apiKey)
```

完整退款测试代码 `com.benlaiyun.qpay.RefundOrderTest`

```java
    // 创建客户端
    QPayClient QPayClient = QPayClient.getInstance(QPay.appId, QPay.apiKey);

    // 构建请求数据
    RefundOrderCreateRequest request = new RefundOrderCreateRequest();
    RefundOrderCreateReqModel model = new RefundOrderCreateReqModel();
    model.setMchNo(QPay.mchNo);                       // 商户号
    model.setAppId(QPay.appId);                       // 应用ID
    model.setMchOrderNo("");                            // 商户支付单号(与支付订单号二者传一)
    model.setPayOrderId("P202106181104177050002");      // 支付订单号(与商户支付单号二者传一)
    String refundOrderNo = "mho" + new Date().getTime();
    model.setMchRefundNo(refundOrderNo);                // 商户退款单号
    model.setRefundAmount(4l);                          // 退款金额，单位分
    model.setCurrency("cny");                           // 币种，目前只支持cny
    model.setClientIp("192.166.1.132");                 // 发起支付请求客户端的 IP 地址，格式为 IPV4，如: 127.0.0.1
    model.setRefundReason("退款测试");                    // 退款原因
    model.setNotifyUrl("https://www.jeequan.com");      // 异步通知地址
    model.setChannelExtra("");                          // 渠道扩展参数
    model.setExtParam("");                              // 商户扩展参数,回调时原样返回
    request.setBizModel(model);
    
    // 发起统一退款
    RefundOrderCreateResponse response = QPayClient.execute(request);

    // 验证返回数据签名
    response.checkSign(QPay.apiKey);

    // 判断退款发起是否成功（并不代表退款成功）退款状态 0-订单生成 1-退款中 2-退款成功 3-退款失败 4-退款关闭
    // 如果 response.get().getState()==2 表示退款成功
    response.isSuccess(QPay.apiKey)
```

完整转账测试代码 `com.benlaiyun.qpay.TransferOrderTest`

```java
    // 创建客户端
    QPayClient QPayClient = QPayClient.getInstance(Jeepay.appId, Jeepay.apiKey);
    TransferOrderCreateRequest request = new TransferOrderCreateRequest();
    TransferOrderCreateReqModel model = new TransferOrderCreateReqModel();
    model.setMchNo(QPay.mchNo);                           // 商户号
    model.setAppId(QPay.appId);                           // 应用ID
    model.setMchOrderNo("mho" + new Date().getTime());      // 商户转账单号
    model.setAmount(1L);
    model.setCurrency("CNY");
    model.setIfCode("wxpay");
    model.setEntryType("WX_CASH");
    model.setAccountNo("a6BcIwtTvIqv1zXZohc61biryWok");
    model.setAccountName("");
    model.setTransferDesc("测试转账");
    model.setClientIp("192.166.1.132");                     // 发起转账请求客户端的IP地址
    request.setBizModel(model);
    try {
        TransferOrderCreateResponse response = QPayClient.execute(request);
        _log.info("验签结果：{}", response.checkSign(Jeepay.apiKey));
        // 判断转账发起是否成功（并不代表转账成功）
        if(response.isSuccess(Jeepay.apiKey)) {
            String transferId = response.get().getTransferId();
            _log.info("transferId：{}", transferId);
            _log.info("mchOrderNo：{}", response.get().getMchOrderNo());
        }else {
            _log.info("下单失败：mchOrderNo={}, msg={}", model.getMchOrderNo(), response.getMsg());
            _log.info("通道错误码：{}", response.get().getErrCode());
            _log.info("通道错误信息：{}", response.get().getErrMsg());
        }
    } catch (JeepayException e) {
        _log.error(e.getMessage());
    }
```

