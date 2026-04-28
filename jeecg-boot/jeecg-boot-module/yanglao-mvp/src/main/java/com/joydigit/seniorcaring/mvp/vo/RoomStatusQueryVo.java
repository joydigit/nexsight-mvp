package com.joydigit.seniorcaring.mvp.vo;

import lombok.Data;

import java.util.List;

/**
 * @Author: sukang
 * @CreateTime: 2026-04-27
 * @Description: TODO
 * @Version: 1.0
 */
@Data
public class RoomStatusQueryVo {
    private String roomNo;
    private String roomStatus;
    private String projectId;
    private String floorId;
    private String buildingId;
    private String customerName;
    private List<String> projectIds;
    private List<String> roomIds;
}
