package com.joydigit.seniorcaring.mvp.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import com.joydigit.seniorcaring.mvp.entity.ElderRoomFeeConfig;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: elder_room_fee_config
 * @Author: jeecg-boot
 * @Date:   2026-04-17
 * @Version: V1.0
 */
public interface ElderRoomFeeConfigMapper extends BaseMapper<ElderRoomFeeConfig> {

    IPage<ElderRoomFeeConfig> pageList(Page<ElderRoomFeeConfig> page, @Param("data") ElderRoomFeeConfig elderRoomFeeConfig);
}
