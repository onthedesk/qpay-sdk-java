package com.benlaiyun.qpay.response;

import com.alibaba.fastjson.JSONObject;
import com.benlaiyun.qpay.util.QPayKit;
import com.benlaiyun.qpay.util.QPayRSA2Kit;
import com.benlaiyun.qpay.util.StringUtils;

import java.io.Serializable;

/**
 * QPay响应抽象类
 * @author jmdhappy
 * @site https://www.benlaiyun.com
 * @date 2021-06-08 11:00
 */
public abstract class QPayResponse implements Serializable  {

    private static final long serialVersionUID = -2637191198247207952L;

    private Integer code;
    private String msg;
    private String sign;
    private JSONObject data;

    /**
     * 校验响应数据签名是否正确
     * @param apiKey
     * @return
     */
    public boolean checkSign(String apiKey) {
        if (data == null && StringUtils.isEmpty(getSign())) return true;
        return sign.equals(QPayKit.getSign(getData(), apiKey));
    }

    public boolean checkSignByRsa2(String apiKey) {
        if (this.data == null && StringUtils.isEmpty(this.getSign())) {
            return true;
        } else {
            try {
                return QPayRSA2Kit.verify(this.data, apiKey, this.getSign());
            } catch (Exception var3) {
                return false;
            }
        }
    }


    /**
     * 校验是否成功(只判断code为0，具体业务要看实际情况)
     * @param apiKey
     * @return
     */
    public boolean isSuccess(String apiKey) {
        if (StringUtils.isEmpty(apiKey)) return code == 0;
        return code == 0 && checkSign(apiKey);
    }

    public boolean isSuccessByRsa2(String apiKey) {
        if (StringUtils.isEmpty(apiKey)) {
            return this.code == 0;
        } else {
            return this.code == 0 && this.checkSignByRsa2(apiKey);
        }
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public JSONObject getData() {
        return data;
    }

    public void setData(JSONObject data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
