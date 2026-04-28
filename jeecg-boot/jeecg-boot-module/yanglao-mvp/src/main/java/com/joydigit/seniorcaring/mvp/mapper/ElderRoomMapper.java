package com.joydigit.seniorcaring.mvp.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.joydigit.seniorcaring.mvp.vo.RoomSelectVo;
import com.joydigit.seniorcaring.mvp.vo.RoomStatusQueryVo;
import com.joydigit.seniorcaring.mvp.vo.RoomStatusVo;
import org.apache.ibatis.annotations.Param;
import com.joydigit.seniorcaring.mvp.entity.ElderRoom;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: elder_room
 * @Author: jeecg-boot
 * @Date:   2026-04-17
 * @Version: V1.0
 */
public interface ElderRoomMapper extends BaseMapper<ElderRoom> {

    IPage<ElderRoom> pageList(Page<ElderRoom> page, @Param("data") ElderRoom elderRoom);

    List<RoomSelectVo> getRoomSelectByProjectId(@Param("projectId")  String projectId);
    List<RoomSelectVo> getBedSelectByProjectId(@Param("projectId")  String projectId);

    List<RoomStatusVo> getRoomStatusListByParams(Page<RoomStatusVo> page, @Param("data") RoomStatusQueryVo roomStatusQueryVo);
}
