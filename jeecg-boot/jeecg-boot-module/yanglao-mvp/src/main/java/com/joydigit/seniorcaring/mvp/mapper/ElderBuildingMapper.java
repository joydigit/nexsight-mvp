package com.joydigit.seniorcaring.mvp.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.joydigit.seniorcaring.mvp.entity.ElderConsulting;
import org.apache.ibatis.annotations.Param;
import com.joydigit.seniorcaring.mvp.entity.ElderBuilding;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: elder_building
 * @Author: jeecg-boot
 * @Date:   2026-04-17
 * @Version: V1.0
 */
public interface ElderBuildingMapper extends BaseMapper<ElderBuilding> {
    IPage<ElderBuilding> pageList(Page<ElderBuilding> page, @Param("data") ElderBuilding elderBuilding);
}
