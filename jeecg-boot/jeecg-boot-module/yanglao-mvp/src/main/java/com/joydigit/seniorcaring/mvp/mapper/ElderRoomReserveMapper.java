package com.joydigit.seniorcaring.mvp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.joydigit.seniorcaring.mvp.entity.ElderRoomReserve;
import org.apache.ibatis.annotations.Param;

/**
 * @Description: 房间预定
 * @Author: jeecg-boot
 * @Date:   2026-04-28
 * @Version: V1.0
 */
public interface ElderRoomReserveMapper extends BaseMapper<ElderRoomReserve> {

    ElderRoomReserve getRoomReserveByCustomerId(@Param("customerId") String customerId);
}
