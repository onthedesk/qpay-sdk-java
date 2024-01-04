package com.benlaiyun.qpay.exception;

/**
 * 无效请求异常
 * @author jmdhappy
 * @site https://www.jeepay.vip
 * @date 2021-06-08 11:00
 */
public class InvalidRequestException extends QPayException {

    private static final long serialVersionUID = 3163726141488238321L;

    public InvalidRequestException(String message, int statusCode, Throwable e) {
        super(message, statusCode, e);
    }

}
