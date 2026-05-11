package com.joydigit.seniorcaring.mvp.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.joydigit.seniorcaring.mvp.entity.ElderCustomerCheckin;
import com.joydigit.seniorcaring.mvp.entity.ElderResidenceHistory;
import com.joydigit.seniorcaring.mvp.enums.ChangeTypeEnum;
import com.joydigit.seniorcaring.mvp.enums.CheckinStatusEnum;
import com.joydigit.seniorcaring.mvp.mapper.ElderResidenceHistoryMapper;
import com.joydigit.seniorcaring.mvp.service.IElderCustomerCheckinService;
import com.joydigit.seniorcaring.mvp.service.IElderResidenceHistoryService;
import org.jeecg.common.api.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * @Description: elder_residence_history
 * @Author: jeecg-boot
 * @Date:   2026-04-17
 * @Version: V1.0
 */
@Service
public class ElderResidenceHistoryServiceImpl extends ServiceImpl<ElderResidenceHistoryMapper, ElderResidenceHistory> implements IElderResidenceHistoryService {

    @Autowired
    private IElderCustomerCheckinService elderCustomerCheckinService;
    @Override
    public IPage<ElderResidenceHistory> pageList(Page<ElderResidenceHistory> page, ElderResidenceHistory elderResidenceHistory) {
        return this.baseMapper.pageList(page,elderResidenceHistory);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<String> saveInfo(ElderResidenceHistory elderResidenceHistory) throws Exception {
        ElderCustomerCheckin checkin = elderCustomerCheckinService.getById(elderResidenceHistory.getCheckinId());
        if (Objects.isNull(checkin)){
            return Result.error("入住记录不存在");
        }
        if (elderResidenceHistory.getChangeTypeCode().equals(ChangeTypeEnum.CHECKOUT.getKey())){
            elderResidenceHistory.setChangeTypeName(ChangeTypeEnum.CHECKOUT.getMsg());
            checkin.setRealCheckoutTime(elderResidenceHistory.getCheckoutTime());
            checkin.setStatus(CheckinStatusEnum.CHECKOUT.getKey());
            elderCustomerCheckinService.checkOut(checkin);
        } else if(elderResidenceHistory.getChangeTypeCode().equals(ChangeTypeEnum.CHANGEROOM.getKey())){
            elderResidenceHistory.setChangeTypeName(ChangeTypeEnum.CHANGEROOM.getMsg());
            elderCustomerCheckinService.changeRoom(checkin,elderResidenceHistory.getNewRoomId(),elderResidenceHistory.getNewBedId());
        } else {
            return Result.error("变更类型错误");
        }
        save(elderResidenceHistory);
        return Result.OK("变更成功");
    }
}
