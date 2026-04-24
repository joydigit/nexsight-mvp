package com.joydigit.seniorcaring.mvp.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.joydigit.seniorcaring.mvp.entity.ElderProjectFeeConfig;
import com.joydigit.seniorcaring.mvp.mapper.ElderProjectFeeConfigMapper;
import com.joydigit.seniorcaring.mvp.service.IElderProjectFeeConfigService;
import com.joydigit.seniorcaring.mvp.service.IElderProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: elder_project_fee_config
 * @Author: jeecg-boot
 * @Date:   2026-04-17
 * @Version: V1.0
 */
@Service
public class ElderProjectFeeConfigServiceImpl extends ServiceImpl<ElderProjectFeeConfigMapper, ElderProjectFeeConfig> implements IElderProjectFeeConfigService {

    @Autowired
    private IElderProjectService elderProjectService;
    @Override
    public IPage<ElderProjectFeeConfig> pageList(Page<ElderProjectFeeConfig> page, ElderProjectFeeConfig elderProjectFeeConfig) {
        List<String> projectIds = elderProjectService.getProjectIdByUserId();
        elderProjectFeeConfig.setProjectIds(projectIds);
        if (CollectionUtil.isEmpty(projectIds)){
            page.setRecords(new ArrayList<>());
            return page;
        }
        return this.baseMapper.pageList(page,elderProjectFeeConfig);
    }
}
