package com.joydigit.seniorcaring.mvp.enums;

/**
 * @Author: sukang
 * @CreateTime: 2026-04-30
 * @Description: TODO
 * @Version: 1.0
 */
public enum PaymentTypeEnum {
    BED("1","床位费"),
    NURSING("2","护理费"),
    CATERING("3","餐费"),
    MEDICAL_CARE("4","医疗费"),
    OTHER("5","其它"),
    ROOM("6","房费"),
    ;

    private String key;
    private String msg;

    PaymentTypeEnum(String key, String msg) {
        this.key = key;
        this.msg = msg;
    }

    public String getKey() {
        return key;
    }

    public String getMsg() {
        return msg;
    }

    /**
     *
     * @param key
     * @return
     */
    public static PaymentTypeEnum getInstance(String key){
        for (PaymentTypeEnum value : values()) {
            if (value.getKey().equals(key)){
                return value;
            }
        }
        return null;
    }
}
