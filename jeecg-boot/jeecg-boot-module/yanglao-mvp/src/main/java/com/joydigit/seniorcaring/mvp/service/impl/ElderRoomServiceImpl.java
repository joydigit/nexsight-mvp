package com.joydigit.seniorcaring.mvp.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.joydigit.seniorcaring.mvp.entity.ElderRoom;
import com.joydigit.seniorcaring.mvp.mapper.ElderRoomMapper;
import com.joydigit.seniorcaring.mvp.service.IElderProjectService;
import com.joydigit.seniorcaring.mvp.service.IElderRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.ArrayList;
import java.util.List;

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
}
