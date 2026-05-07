package com.joydigit.seniorcaring.mvp.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import com.joydigit.seniorcaring.mvp.entity.ElderResidenceHistory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: elder_residence_history
 * @Author: jeecg-boot
 * @Date:   2026-04-17
 * @Version: V1.0
 */
public interface ElderResidenceHistoryMapper extends BaseMapper<ElderResidenceHistory> {

    IPage<ElderResidenceHistory> pageList(Page<ElderResidenceHistory> page,@Param("data") ElderResidenceHistory elderResidenceHistory);
}
