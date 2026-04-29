package com.joydigit.seniorcaring.mvp.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Author: sukang
 * @CreateTime: 2026-04-28
 * @Description: TODO
 * @Version: 1.0
 */
@Data
public class ElderCustomerVo {

    private String id;
    private String projectId;
    private String name;
    private String gender;
    private String idCard;
    private String idCardType;
    private String address;
    private String phone;
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date birthDate;
    private String remark;
    private String projectName;

    private String roomId;
    private String roomNo;
    private String bedId;
    private String bedNo;
    private String reserveStatus;
    private String checkinStatus;
    private String buildingId;
    private String buildingName;
    private String floorId;
    private String floorName;
}
