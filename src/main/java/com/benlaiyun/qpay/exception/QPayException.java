package com.benlaiyun.qpay.exception;

/**
 * Jeepay异常抽象类
 * @author jmdhappy
 * @site https://www.benlaiyun.com
 * @date 2021-06-08 11:00
 */
public abstract class QPayException extends Exception {

    private static final long serialVersionUID = 2566087783987900120L;

    private int statusCode;

    public QPayException(String message) {
        super(message, null);
    }

    public QPayException(String message, Throwable e) {
        super(message, e);
    }

    public QPayException(String message, int statusCode, Throwable e) {
        super(message, e);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(super.toString());
                return sb.toString();
    }
}
