package com.joydigit.seniorcaring.mvp.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.joydigit.seniorcaring.mvp.entity.ElderRoomReserve;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.common.api.vo.Result;

/**
 * @Description: 房间预定
 * @Author: jeecg-boot
 * @Date:   2026-04-28
 * @Version: V1.0
 */
public interface IElderRoomReserveService extends IService<ElderRoomReserve> {

    Result<String> saveInfo(ElderRoomReserve elderRoomReserve);
    Result<String> updateInfo(ElderRoomReserve elderRoomReserve);
    Result<String> delete(String id);

    IPage<ElderRoomReserve> pageList(Page<ElderRoomReserve> page, ElderRoomReserve elderRoomReserve);
}
