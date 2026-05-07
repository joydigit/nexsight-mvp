package com.joydigit.seniorcaring.mvp.enums;

/**
 * @Author: sukang
 * @CreateTime: 2026-05-06
 * @Description: TODO
 * @Version: 1.0
 */
public enum PaymentMethodEnum {
    GENERAL_BALANCE("1","通用余额"),
    DEPOSIT("2","押金账户"),
    WEIXIN("3","微信"),
    ALIPAY("4","支付宝"),
    OTHER("5","其它"),
    ;

    private String key;
    private String msg;

    PaymentMethodEnum(String key, String msg) {
        this.key = key;
        this.msg = msg;
    }

    public String getKey() {
        return key;
    }

    public String getMsg() {
        return msg;
    }

    public static  PaymentMethodEnum getInstance(String key){
        for (PaymentMethodEnum value : values()) {
            if (value.getKey().equals(key)){
                return value;
            }
        }
        return null;
    }
}
