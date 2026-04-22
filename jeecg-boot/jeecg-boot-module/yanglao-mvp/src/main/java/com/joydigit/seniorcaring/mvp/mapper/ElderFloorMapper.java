package com.joydigit.seniorcaring.mvp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.joydigit.seniorcaring.mvp.entity.ElderFloor;
import org.apache.ibatis.annotations.Param;

/**
 * @Description: elder_floor
 * @Author: jeecg-boot
 * @Date:   2026-04-17
 * @Version: V1.0
 */
public interface ElderFloorMapper extends BaseMapper<ElderFloor> {

    IPage<ElderFloor> pageList(Page<ElderFloor> page, @Param("data") ElderFloor elderFloor);

}
