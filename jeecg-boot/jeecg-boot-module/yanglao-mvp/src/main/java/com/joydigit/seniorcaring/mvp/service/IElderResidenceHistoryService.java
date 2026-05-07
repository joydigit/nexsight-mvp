package com.joydigit.seniorcaring.mvp.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.joydigit.seniorcaring.mvp.entity.ElderResidenceHistory;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: elder_residence_history
 * @Author: jeecg-boot
 * @Date:   2026-04-17
 * @Version: V1.0
 */
public interface IElderResidenceHistoryService extends IService<ElderResidenceHistory> {

    IPage<ElderResidenceHistory> pageList(Page<ElderResidenceHistory> page, ElderResidenceHistory elderResidenceHistory);

}
