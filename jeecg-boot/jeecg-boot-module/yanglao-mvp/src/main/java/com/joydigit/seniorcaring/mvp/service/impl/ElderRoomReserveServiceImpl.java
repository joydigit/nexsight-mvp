package com.joydigit.seniorcaring.mvp.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.joydigit.seniorcaring.mvp.entity.ElderBed;
import com.joydigit.seniorcaring.mvp.entity.ElderCustomer;
import com.joydigit.seniorcaring.mvp.entity.ElderRoom;
import com.joydigit.seniorcaring.mvp.entity.ElderRoomReserve;
import com.joydigit.seniorcaring.mvp.enums.RoomStatusEnum;
import com.joydigit.seniorcaring.mvp.mapper.ElderBedMapper;
import com.joydigit.seniorcaring.mvp.mapper.ElderCustomerMapper;
import com.joydigit.seniorcaring.mvp.mapper.ElderRoomMapper;
import com.joydigit.seniorcaring.mvp.mapper.ElderRoomReserveMapper;
import com.joydigit.seniorcaring.mvp.service.IElderRoomReserveService;
import org.jeecg.common.api.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * @Description: 房间预定
 * @Author: jeecg-boot
 * @Date:   2026-04-28
 * @Version: V1.0
 */
@Service
public class ElderRoomReserveServiceImpl extends ServiceImpl<ElderRoomReserveMapper, ElderRoomReserve> implements IElderRoomReserveService {

    @Autowired
    private ElderCustomerMapper elderCustomerMapper;
    @Autowired
    private ElderBedMapper elderBedMapper;
    @Autowired
    private ElderRoomMapper elderRoomMapper;
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<String> saveInfo(ElderRoomReserve elderRoomReserve) {
        ElderCustomer elderCustomer = elderCustomerMapper.selectById(elderRoomReserve.getCustomerId());
        if (Objects.isNull(elderCustomer)){
            return Result.error("客户不存在");
        }
        elderRoomReserve.setConsultingId(elderCustomer.getConsultingId());
        long checkCount = count(Wrappers.lambdaQuery(ElderRoomReserve.class)
                .and(s -> s.eq(ElderRoomReserve::getBedId, elderRoomReserve.getBedId()).or()
                        .eq(ElderRoomReserve::getCustomerId, elderRoomReserve.getCustomerId()))
                .eq(ElderRoomReserve::getStatus, RoomStatusEnum.RESERVED.getKey()));
        if (checkCount > 0){
            return Result.error("不可重复预定");
        }
        // 判断床位状态是否空闲
        Long bedCount = elderBedMapper.selectCount(Wrappers.lambdaQuery(ElderBed.class)
                .eq(ElderBed::getId, elderRoomReserve.getBedId())
                .eq(ElderBed::getStatus, RoomStatusEnum.FREE.getKey()));
        if (bedCount != 1){
            return Result.error("床位占用");
        }
        save(elderRoomReserve);
        ElderBed elderBed = new ElderBed();
        elderBed.setId(elderRoomReserve.getBedId());
        elderBed.setStatus(RoomStatusEnum.RESERVED.getKey());
        elderBedMapper.updateById(elderBed);

        elderRoomMapper.update(Wrappers.lambdaUpdate(ElderRoom.class)
                .eq(ElderRoom::getId,elderRoomReserve.getRoomId())
                .eq(ElderRoom::getStatus,RoomStatusEnum.FREE.getKey())
                .set(ElderRoom::getStatus,RoomStatusEnum.RESERVED.getKey()));
        return Result.ok("添加成功");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<String> updateInfo(ElderRoomReserve elderRoomReserve) {
        ElderRoomReserve queryReserve = getById(elderRoomReserve.getId());
        if (Objects.isNull(queryReserve)){
            return Result.error("记录不存在");
        }
        if (!elderRoomReserve.getStatus().equals("2")){
            return Result.error("只支持取消预定");
        }
        updateById(elderRoomReserve);
        ElderBed elderBed = new ElderBed();
        elderBed.setId(elderRoomReserve.getBedId());
        elderBed.setStatus(RoomStatusEnum.FREE.getKey());
        elderBedMapper.updateById(elderBed);
        elderRoomMapper.update(Wrappers.lambdaUpdate(ElderRoom.class)
                .eq(ElderRoom::getId,elderRoomReserve.getRoomId())
                .eq(ElderRoom::getStatus,RoomStatusEnum.RESERVED.getKey())
                .set(ElderRoom::getStatus,RoomStatusEnum.FREE.getKey()));
        return Result.OK("取消预定成功");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<String> delete(String id) {
        ElderRoomReserve reserve = getById(id);
        if (Objects.isNull(reserve)){
            return Result.OK();
        }
        removeById(id);
        if (reserve.getStatus().equals("1")){
            ElderBed elderBed = elderBedMapper.selectById(reserve.getBedId());
            if (Objects.nonNull(elderBed) && elderBed.getStatus().equals(RoomStatusEnum.RESERVED.getKey())){
                elderBed.setStatus(RoomStatusEnum.FREE.getKey());
                elderBedMapper.updateById(elderBed);
            }
        }
        return Result.OK("删除成功");
    }

    @Override
    public IPage<ElderRoomReserve> pageList(Page<ElderRoomReserve> page, ElderRoomReserve elderRoomReserve) {
        return this.baseMapper.pageList(page,elderRoomReserve);
    }
}
