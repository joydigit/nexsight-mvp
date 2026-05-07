package com.joydigit.seniorcaring.mvp.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.joydigit.seniorcaring.mvp.entity.ElderResidenceHistory;
import com.joydigit.seniorcaring.mvp.mapper.ElderResidenceHistoryMapper;
import com.joydigit.seniorcaring.mvp.service.IElderResidenceHistoryService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: elder_residence_history
 * @Author: jeecg-boot
 * @Date:   2026-04-17
 * @Version: V1.0
 */
@Service
public class ElderResidenceHistoryServiceImpl extends ServiceImpl<ElderResidenceHistoryMapper, ElderResidenceHistory> implements IElderResidenceHistoryService {

    @Override
    public IPage<ElderResidenceHistory> pageList(Page<ElderResidenceHistory> page, ElderResidenceHistory elderResidenceHistory) {
        return this.baseMapper.pageList(page,elderResidenceHistory);
    }
}
