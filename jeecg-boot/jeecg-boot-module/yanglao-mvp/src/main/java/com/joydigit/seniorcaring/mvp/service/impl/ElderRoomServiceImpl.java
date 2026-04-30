package com.joydigit.seniorcaring.mvp.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.joydigit.seniorcaring.mvp.entity.ElderRoom;
import com.joydigit.seniorcaring.mvp.mapper.ElderBedMapper;
import com.joydigit.seniorcaring.mvp.mapper.ElderRoomMapper;
import com.joydigit.seniorcaring.mvp.service.IElderProjectService;
import com.joydigit.seniorcaring.mvp.service.IElderRoomService;
import com.joydigit.seniorcaring.mvp.vo.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Description: elder_room
 * @Author: jeecg-boot
 * @Date:   2026-04-17
 * @Version: V1.0
 */
@Service
public class ElderRoomServiceImpl extends ServiceImpl<ElderRoomMapper, ElderRoom> implements IElderRoomService {

    @Autowired
    private IElderProjectService elderProjectService;
    @Autowired
    private ElderBedMapper elderBedMapper;
    @Override
    public IPage<ElderRoom> pageList(Page<ElderRoom> page, ElderRoom elderRoom) {
        List<String> projectIds = elderProjectService.getProjectIdByUserId();
        elderRoom.setProjectIds(projectIds);
        if (CollectionUtil.isEmpty(projectIds)){
            page.setRecords(new ArrayList<>());
            return page;
        }
        return this.baseMapper.pageList(page,elderRoom);
    }

    @Override
    public List<ElderRoom> getRoomListByFloorId(String floorId) {
        List<ElderRoom> list = list(Wrappers.lambdaQuery(ElderRoom.class).eq(ElderRoom::getFloorId, floorId).orderByDesc(ElderRoom::getCreateTime));
        return list;
    }

    @Override
    public List<RoomSelectVo> getRoomListByProjectId(String projectId) {
        List<RoomSelectVo> roomSelectVoList = this.baseMapper.getRoomSelectByProjectId(projectId);
        if (CollectionUtil.isNotEmpty(roomSelectVoList)){
            for (RoomSelectVo roomSelectVo : roomSelectVoList) {
                roomSelectVo.setRoomNo(roomSelectVo.getBuildingName()+"/"+roomSelectVo.getFloorName()+"/"+roomSelectVo.getRoomNo());
            }
        }
        return roomSelectVoList;
    }

    @Override
    public List<RoomCascaderVo> getRoomCascaderList(String type,String projectId) {
        List<RoomCascaderVo> list = new ArrayList<>();
        if ("1".equals(type)){
            List<RoomSelectVo> roomSelectVoList = this.baseMapper.getRoomSelectByProjectId(projectId);
            setRoomCascader(roomSelectVoList, list,type);
        } else if("2".equals(type)){
            List<RoomSelectVo> roomSelectVoList = this.baseMapper.getBedSelectByProjectId(projectId);
            setRoomCascader(roomSelectVoList, list,type);
        }
        return list;
    }

    @Override
    public IPage<FloorDataVo> getRoomStatusPageList(Integer pageNo, Integer pageSize, RoomStatusQueryVo roomStatusQueryVo) {
        Page<FloorDataVo> pageList = new Page<FloorDataVo>(pageNo, pageSize);
        if (Objects.isNull(roomStatusQueryVo)){
            roomStatusQueryVo = new RoomStatusQueryVo();
        }
        List<String> projectIds = elderProjectService.getProjectIdByUserId();
        roomStatusQueryVo.setProjectIds(projectIds);
        if (CollectionUtil.isEmpty(projectIds)){
            pageList.setRecords(new ArrayList<>());
            return pageList;
        }
        if (Objects.nonNull(roomStatusQueryVo) && StringUtils.isNotBlank(roomStatusQueryVo.getCustomerName())){
            List<BedStatusVo> bedStatusVos = elderBedMapper.getBedStatusListByParams(null, roomStatusQueryVo.getCustomerName());
            if (CollectionUtil.isNotEmpty(bedStatusVos)){
                roomStatusQueryVo.setRoomIds(bedStatusVos.stream().map(BedStatusVo::getRoomId).distinct().collect(Collectors.toList()));
            } else {
                pageList.setRecords(new ArrayList<>());
                return pageList;
            }
        }
        Page<RoomStatusVo> page = new Page<>(pageNo, pageSize);
        List<RoomStatusVo> roomStatusVoList = this.baseMapper.getRoomStatusListByParams(page, roomStatusQueryVo);
        List<FloorDataVo> list = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(roomStatusVoList)){
            List<String> roomIds = roomStatusVoList.stream().map(RoomStatusVo::getRoomId).distinct().collect(Collectors.toList());
            List<BedStatusVo> bedStatusVos = elderBedMapper.getBedStatusListByParams(roomIds, null);
            Map<String, List<BedStatusVo>> bedMap = new HashMap<>();
            if (CollectionUtil.isNotEmpty(bedStatusVos)){
                bedMap = bedStatusVos.stream().collect(Collectors.groupingBy(BedStatusVo::getRoomId));
            }
            Map<String, List<RoomStatusVo>> roomMap = roomStatusVoList.stream().collect(Collectors.groupingBy(RoomStatusVo::getFloorId));
            for (String floorId : roomMap.keySet()) {
                List<RoomStatusVo> roomStatusVos = roomMap.get(floorId);
                FloorDataVo floorDataVo = new FloorDataVo();
                BeanUtils.copyProperties(roomStatusVos.get(0),floorDataVo);
                List<RoomDataVo> rooms = roomStatusVos.stream().map(s -> {
                    RoomDataVo ro = new RoomDataVo();
                    BeanUtils.copyProperties(s, ro);
                    return ro;
                }).collect(Collectors.toList());
                for (RoomDataVo roomDataVo : rooms) {
                    List<BedStatusVo> statusVoList = bedMap.get(roomDataVo.getRoomId());
                    if (CollectionUtil.isNotEmpty(statusVoList)){
                        roomDataVo.setBeds(statusVoList);
                    }
                }
                floorDataVo.setRooms(rooms);
                list.add(floorDataVo);
            }
        }
        pageList.setRecords(list);
        pageList.setTotal(page.getTotal());
        return pageList;
    }

    private static void setRoomCascader(List<RoomSelectVo> roomSelectVoList, List<RoomCascaderVo> list,String type) {
        if (CollectionUtil.isNotEmpty(roomSelectVoList)){
            Map<String, List<RoomSelectVo>> buildMap = roomSelectVoList.stream().collect(Collectors.groupingBy(RoomSelectVo::getBuildingId));
            for (String b : buildMap.keySet()) {
                RoomCascaderVo build = new RoomCascaderVo();
                build.setValue(b);
                List<RoomSelectVo> roomSelectVoList1 = buildMap.get(b);
                build.setLabel(roomSelectVoList1.get(0).getBuildingName());
                List<RoomCascaderVo> floorList = new ArrayList<>();
                Map<String, List<RoomSelectVo>> floorMap = roomSelectVoList1.stream().collect(Collectors.groupingBy(RoomSelectVo::getFloorId));
                for (String f : floorMap.keySet()) {
                    RoomCascaderVo floor = new RoomCascaderVo();
                    floor.setValue(f);
                    List<RoomSelectVo> roomSelectVoList2 = floorMap.get(f);
                    floor.setLabel(roomSelectVoList2.get(0).getFloorName());
                    List<RoomCascaderVo> roomList = new ArrayList<>();
                    if ("2".equals(type)){
                        Map<String, List<RoomSelectVo>> roomMap = roomSelectVoList2.stream().collect(Collectors.groupingBy(RoomSelectVo::getRoomId));
                        for (String r : roomMap.keySet()) {
                            RoomCascaderVo rom = new RoomCascaderVo();
                            rom.setValue(r);
                            List<RoomSelectVo> roomSelectVoList3 = roomMap.get(r);
                            rom.setLabel(roomSelectVoList3.get(0).getRoomNo());
                            List<RoomCascaderVo> bedList = new ArrayList<>();
                            for (RoomSelectVo roomSelectVo : roomSelectVoList3) {
                                RoomCascaderVo bed = new RoomCascaderVo();
                                bed.setValue(roomSelectVo.getBedId());
                                bed.setLabel(roomSelectVo.getBedNo());
                                bedList.add(bed);
                            }
                            rom.setChildren(bedList);
                            roomList.add(rom);
                        }
                    } else {
                        for (RoomSelectVo roomSelectVo : roomSelectVoList2) {
                            RoomCascaderVo rom = new RoomCascaderVo();
                            rom.setValue(roomSelectVo.getRoomId());
                            rom.setLabel(roomSelectVo.getRoomNo());
                            roomList.add(rom);
                        }
                    }
                    floor.setChildren(roomList);
                    floorList.add(floor);
                }
                build.setChildren(floorList);
                list.add(build);
            }
        }
    }
}
