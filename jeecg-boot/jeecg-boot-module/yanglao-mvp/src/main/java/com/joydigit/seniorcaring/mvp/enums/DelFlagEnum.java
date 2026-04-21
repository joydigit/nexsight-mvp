package com.joydigit.seniorcaring.mvp.enums;

/**
 * @Author: sukang
 * @CreateTime: 2026-04-21
 * @Description: TODO
 * @Version: 1.0
 */
public enum DelFlagEnum {
    YES(1),
    NO(0);
    private int key;

    DelFlagEnum(int key) {
        this.key = key;
    }

    public int getKey() {
        return key;
    }
}
