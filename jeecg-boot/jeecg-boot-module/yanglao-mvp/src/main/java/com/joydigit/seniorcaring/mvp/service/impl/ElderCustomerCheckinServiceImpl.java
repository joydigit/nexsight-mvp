package com.joydigit.seniorcaring.mvp.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.joydigit.seniorcaring.mvp.entity.*;
import com.joydigit.seniorcaring.mvp.enums.*;
import com.joydigit.seniorcaring.mvp.mapper.*;
import com.joydigit.seniorcaring.mvp.service.IElderBillService;
import com.joydigit.seniorcaring.mvp.service.IElderCustomerCheckinFeeService;
import com.joydigit.seniorcaring.mvp.service.IElderCustomerCheckinService;
import org.apache.commons.lang3.StringUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.api.ISysBaseAPI;
import org.jeecg.common.system.vo.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @Description: elder_customer_checkin
 * @Author: jeecg-boot
 * @Date:   2026-04-17
 * @Version: V1.0
 */
@Service
public class ElderCustomerCheckinServiceImpl extends ServiceImpl<ElderCustomerCheckinMapper, ElderCustomerCheckin> implements IElderCustomerCheckinService {

    @Autowired
    private ElderCustomerMapper elderCustomerMapper;
    @Autowired
    private ISysBaseAPI sysBaseAPI;
    @Autowired
    private ElderBedMapper elderBedMapper;
    @Autowired
    private ElderRoomMapper elderRoomMapper;
    @Autowired
    private ElderProjectFeeConfigMapper elderProjectFeeConfigMapper;
    @Autowired
    private IElderCustomerCheckinFeeService elderCustomerCheckinFeeService;
    @Autowired
    private ElderRoomFeeConfigMapper elderRoomFeeConfigMapper;
    @Autowired
    private ElderResidenceHistoryMapper elderResidenceHistoryMapper;
    @Autowired
    private IElderBillService elderBillService;
    @Autowired
    private ElderRoomReserveMapper elderRoomReserveMapper;
    @Override
    public IPage<ElderCustomerCheckin> pageList(Page<ElderCustomerCheckin> page, ElderCustomerCheckin elderCustomerCheckin) {
        return this.baseMapper.pageList(page,elderCustomerCheckin);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<String> saveInfo(ElderCustomerCheckin elderCustomerCheckin) throws Exception {
        ElderCustomer elderCustomer = elderCustomerMapper.selectById(elderCustomerCheckin.getCustomerId());
        if (Objects.isNull(elderCustomer)){
            return Result.error("客户不存在");
        }
        elderCustomerCheckin.setConsultingId(elderCustomer.getConsultingId());
        if (StringUtils.isNotBlank(elderCustomerCheckin.getSalesId())){
            LoginUser user = sysBaseAPI.getUserById(elderCustomerCheckin.getSalesId());
            if (Objects.nonNull(user)){
                elderCustomerCheckin.setSalesName(user.getRealname());
            }
        }
        elderCustomerCheckin.setStatus(CheckinStatusEnum.CHECKIN.getKey());
        long existCount = count(Wrappers.lambdaQuery(ElderCustomerCheckin.class)
                .and(s -> s.eq(ElderCustomerCheckin::getBedId, elderCustomerCheckin.getBedId()).or()
                        .eq(ElderCustomerCheckin::getCustomerId, elderCustomerCheckin.getCustomerId()))
                .eq(ElderCustomerCheckin::getStatus, CheckinStatusEnum.CHECKIN.getKey()));
        if (existCount > 0){
            return Result.error("不可重复入住");
        }
        Long checkRes = elderRoomReserveMapper.selectCount(Wrappers.lambdaQuery(ElderRoomReserve.class)
                .eq(ElderRoomReserve::getCustomerId, elderCustomerCheckin.getCustomerId())
                .eq(ElderRoomReserve::getStatus, ReserveStatusEnum.RESERVE.getKey())
                .ne(ElderRoomReserve::getBedId, elderCustomerCheckin.getBedId()));
        if (checkRes > 0){
            return Result.error("预定和入住不符");
        }
        // 查询 床位 是否空闲 或者 是本人预定状态 ，否则不可以入住
        int checkBed = this.baseMapper.checkBedStatusByBedId(elderCustomerCheckin.getCustomerId(), elderCustomerCheckin.getBedId());
        if (checkBed != 1){
            return Result.error("床位不可用，检查床位状态");
        }
        // 入住后，取消预定
        elderRoomReserveMapper.update(Wrappers.lambdaUpdate(ElderRoomReserve.class)
                .eq(ElderRoomReserve::getId,elderCustomerCheckin.getBedId())
                .eq(ElderRoomReserve::getStatus,"1").set(ElderRoomReserve::getStatus,"0"));
        // 添加费用配置
        List<ElderProjectFeeConfig> projectFeeConfigs = elderProjectFeeConfigMapper.selectList(Wrappers.lambdaQuery(ElderProjectFeeConfig.class)
                .eq(ElderProjectFeeConfig::getProjectId, elderCustomerCheckin.getProjectId())
                .eq(ElderProjectFeeConfig::getCheckinType,elderCustomerCheckin.getCheckinType())
                .eq(ElderProjectFeeConfig::getStatus, StatusEnum.YES.getKey()));
        if (CollectionUtil.isEmpty(projectFeeConfigs)){
            return Result.error("费用未配置");
        }
        elderCustomerCheckin.setId(IdWorker.getIdStr());
        save(elderCustomerCheckin);
        // 更新房间状态
        checkinUpdateRoomStatus(elderCustomerCheckin.getRoomId(),elderCustomerCheckin.getBedId());

        List<ElderRoomFeeConfig> elderRoomFeeConfigs = elderRoomFeeConfigMapper.selectList(Wrappers.lambdaQuery(ElderRoomFeeConfig.class)
                .eq(ElderRoomFeeConfig::getItemType, "2")
                .eq(ElderRoomFeeConfig::getCheckinType,elderCustomerCheckin.getCheckinType())
                .eq(ElderRoomFeeConfig::getItemId, elderCustomerCheckin.getBedId()));
        ElderRoom room = elderRoomMapper.selectById(elderCustomerCheckin.getRoomId());
        List<ElderCustomerCheckinFee> feeList = new ArrayList<>();
        for (ElderProjectFeeConfig projectFeeConfig : projectFeeConfigs) {
            ElderCustomerCheckinFee fee = new ElderCustomerCheckinFee();
            fee.setCheckinId(elderCustomerCheckin.getId());
            fee.setPaymentTypeCode(projectFeeConfig.getPaymentTypeCode());
            fee.setUnitCode(projectFeeConfig.getUnitCode());
            if (projectFeeConfig.getPaymentTypeCode().equals(PaymentTypeEnum.BED.getKey())){
                fee.setFeeType(FeeTypeEnum.BED.getKey());
                fee.setAmount(projectFeeConfig.getPrice());
                if (CollectionUtil.isNotEmpty(elderRoomFeeConfigs)){
                    ElderRoomFeeConfig elderRoomFeeConfig = elderRoomFeeConfigs.get(0);
                    fee.setAmount(elderRoomFeeConfig.getPrice());
                }
            } else if (projectFeeConfig.getPaymentTypeCode().equals(PaymentTypeEnum.ROOM.getKey())){
                if (StringUtils.isBlank(projectFeeConfig.getRoomType()) || !projectFeeConfig.getRoomType().equals(room.getRoomType())){
                    continue;
                }
            } else {
                fee.setFeeType(FeeTypeEnum.OTHER.getKey());
                fee.setAmount(projectFeeConfig.getPrice());
            }
            fee.setFeeConfigId(projectFeeConfig.getId());
            fee.setProjectId(elderCustomerCheckin.getProjectId());
            feeList.add(fee);
        }
        elderCustomerCheckinFeeService.saveBatch(feeList);

        // 添加历史记录
        ElderResidenceHistory history = new ElderResidenceHistory();
        history.setCheckinId(elderCustomerCheckin.getId());
        history.setCustomerId(elderCustomerCheckin.getCustomerId());
        history.setProjectId(elderCustomerCheckin.getProjectId());
        history.setNewBedId(elderCustomerCheckin.getBedId());
        history.setNewRoomId(elderCustomerCheckin.getRoomId());
        history.setChangeTypeCode(ChangeTypeEnum.CHECKIN.getKey());
        history.setChangeTypeName(ChangeTypeEnum.CHECKIN.getMsg());
        elderResidenceHistoryMapper.insert(history);
        return Result.OK("入住成功");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<String> updateInfo(ElderCustomerCheckin elderCustomerCheckin) {
        ElderCustomerCheckin checkinQuery = getById(elderCustomerCheckin.getId());
        if (Objects.isNull(checkinQuery)){
            return Result.error("记录不存在");
        }
        if (StringUtils.isNotBlank(checkinQuery.getSalesId()) &&
                StringUtils.isNotBlank(elderCustomerCheckin.getSalesId()) &&
                checkinQuery.getSalesId().equals(elderCustomerCheckin.getSalesId())){

        } else if(StringUtils.isNotBlank(elderCustomerCheckin.getSalesId())){
            LoginUser user = sysBaseAPI.getUserById(elderCustomerCheckin.getSalesId());
            if (Objects.nonNull(user)){
                checkinQuery.setSalesName(user.getRealname());
            }
            checkinQuery.setSalesId(elderCustomerCheckin.getSalesId());
        }
        checkinQuery.setCheckinTime(elderCustomerCheckin.getCheckinTime());
        checkinQuery.setContractNo(elderCustomerCheckin.getContractNo());
        checkinQuery.setContractFileUrl(elderCustomerCheckin.getContractFileUrl());
        checkinQuery.setNursingLevel(elderCustomerCheckin.getNursingLevel());
        checkinQuery.setExpectCheckoutTime(elderCustomerCheckin.getExpectCheckoutTime());
        updateById(checkinQuery);
        return Result.OK("修改成功");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void checkOut(ElderCustomerCheckin checkin) throws Exception {
        // 修改状态
        updateById(checkin);
        // 修改房间状态
        checkoutUpdateRoomStatus(checkin.getRoomId(),checkin.getBedId());
        // 计算账单
        elderBillService.calBillInfo(checkin,ChangeTypeEnum.CHECKOUT);
    }

    /**
     *
     * @param roomId
     * @param bedId
     */
    private void checkoutUpdateRoomStatus(String roomId,String bedId) {
        Long bedCount = elderBedMapper.selectCount(Wrappers.lambdaQuery(ElderBed.class)
                .eq(ElderBed::getRoomId,roomId).eq(ElderBed::getStatus, RoomStatusEnum.OCCUPIED.getKey()));
        if (bedCount == 1){
             elderRoomMapper.update(Wrappers.lambdaUpdate(ElderRoom.class)
                    .eq(ElderRoom::getId, roomId)
                    .eq(ElderRoom::getStatus, RoomStatusEnum.OCCUPIED.getKey())
                    .set(ElderRoom::getStatus, RoomStatusEnum.FREE.getKey()));

        }
        ElderBed updateBed = new ElderBed();
        updateBed.setStatus(RoomStatusEnum.FREE.getKey());
        updateBed.setId(bedId);
        elderBedMapper.updateById(updateBed);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void changeRoom(ElderCustomerCheckin checkin, String newRoomId, String newBedId) throws Exception {

        // 修改原房间状态
        checkoutUpdateRoomStatus(checkin.getRoomId(),checkin.getBedId());
        // 修改新房间状态
        checkinUpdateRoomStatus(newRoomId, newBedId);
        // 修改房间/床位
        checkin.setBedId(newBedId);
        checkin.setRoomId(newRoomId);
        updateById(checkin);
        // 计算账单
        elderBillService.calBillInfo(checkin,ChangeTypeEnum.CHANGEROOM);
    }

    /**
     *
     * @param newRoomId
     * @param newBedId
     * @throws Exception
     */
    private void checkinUpdateRoomStatus(String newRoomId, String newBedId) throws Exception {
        int update = elderBedMapper.update(Wrappers.lambdaUpdate(ElderBed.class)
                .eq(ElderBed::getId, newBedId)
                .in(ElderBed::getStatus, Arrays.asList(RoomStatusEnum.FREE.getKey(),RoomStatusEnum.RESERVED.getKey()))
                .set(ElderBed::getStatus, RoomStatusEnum.OCCUPIED.getKey()));
        if (update == 0){
            throw new Exception("新床位被占用");
        }
        elderRoomMapper.update(Wrappers.lambdaUpdate(ElderRoom.class)
                .eq(ElderRoom::getId, newRoomId)
                .in(ElderRoom::getStatus, Arrays.asList(RoomStatusEnum.FREE.getKey(),RoomStatusEnum.RESERVED.getKey()))
                .set(ElderRoom::getStatus, RoomStatusEnum.OCCUPIED.getKey()));
    }
}
