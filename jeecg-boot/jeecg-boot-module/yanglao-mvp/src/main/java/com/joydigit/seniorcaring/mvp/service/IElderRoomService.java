package com.joydigit.seniorcaring.mvp.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.joydigit.seniorcaring.mvp.entity.ElderRoom;
import com.joydigit.seniorcaring.mvp.vo.RoomCascaderVo;

import java.util.List;

/**
 * @Description: elder_room
 * @Author: jeecg-boot
 * @Date:   2026-04-17
 * @Version: V1.0
 */
public interface IElderRoomService extends IService<ElderRoom> {

    IPage<ElderRoom> pageList(Page<ElderRoom> page, ElderRoom elderRoom);
    List<ElderRoom> getRoomListByFloorId(String floorId);

    List<RoomCascaderVo> getRoomCascaderList(String type,String projectId);
}
