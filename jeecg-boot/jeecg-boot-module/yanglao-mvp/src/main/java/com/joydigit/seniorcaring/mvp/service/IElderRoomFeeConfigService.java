package com.joydigit.seniorcaring.mvp.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.joydigit.seniorcaring.mvp.entity.ElderRoomFeeConfig;

/**
 * @Description: elder_room_fee_config
 * @Author: jeecg-boot
 * @Date:   2026-04-17
 * @Version: V1.0
 */
public interface IElderRoomFeeConfigService extends IService<ElderRoomFeeConfig> {

    IPage<ElderRoomFeeConfig> pageList(Page<ElderRoomFeeConfig> page, ElderRoomFeeConfig elderRoomFeeConfig);
}
