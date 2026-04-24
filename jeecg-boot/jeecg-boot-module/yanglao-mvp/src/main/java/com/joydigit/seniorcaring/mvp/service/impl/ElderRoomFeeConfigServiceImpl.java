package com.joydigit.seniorcaring.mvp.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.joydigit.seniorcaring.mvp.entity.ElderRoomFeeConfig;
import com.joydigit.seniorcaring.mvp.mapper.ElderRoomFeeConfigMapper;
import com.joydigit.seniorcaring.mvp.service.IElderProjectService;
import com.joydigit.seniorcaring.mvp.service.IElderRoomFeeConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: elder_room_fee_config
 * @Author: jeecg-boot
 * @Date:   2026-04-17
 * @Version: V1.0
 */
@Service
public class ElderRoomFeeConfigServiceImpl extends ServiceImpl<ElderRoomFeeConfigMapper, ElderRoomFeeConfig> implements IElderRoomFeeConfigService {

    @Autowired
    private IElderProjectService elderProjectService;
    @Override
    public IPage<ElderRoomFeeConfig> pageList(Page<ElderRoomFeeConfig> page, ElderRoomFeeConfig elderRoomFeeConfig) {
        List<String> projectIds = elderProjectService.getProjectIdByUserId();
        elderRoomFeeConfig.setProjectIds(projectIds);
        if (CollectionUtil.isEmpty(projectIds)){
            page.setRecords(new ArrayList<>());
            return page;
        }
        return this.baseMapper.pageList(page,elderRoomFeeConfig);
    }
}
