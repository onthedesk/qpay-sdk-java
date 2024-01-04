package com.benlaiyun.qpay.exception;

/**
 * API异常
 * @author jmdhappy
 * @site https://www.jeepay.vip
 * @date 2021-06-08 11:00
 */
public class APIException extends QPayException {

    private static final long serialVersionUID = -2753719317464278319L;

    public APIException(String message, String type, String code, int statusCode, Throwable e) {
        super(message, statusCode, e);
    }
}
