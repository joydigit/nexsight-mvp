package com.joydigit.seniorcaring.mvp.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: sukang
 * @CreateTime: 2026-04-21
 * @Description: TODO
 * @Version: 1.0
 */
@Data
public class ProjectVo implements Serializable {

    private String id;
    private String projectName;
    private String projectCode;
}
