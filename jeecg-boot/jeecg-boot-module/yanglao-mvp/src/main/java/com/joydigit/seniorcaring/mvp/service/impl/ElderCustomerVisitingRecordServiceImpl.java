package com.joydigit.seniorcaring.mvp.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.joydigit.seniorcaring.mvp.entity.ElderCustomer;
import com.joydigit.seniorcaring.mvp.entity.ElderCustomerCheckin;
import com.joydigit.seniorcaring.mvp.entity.ElderCustomerVisitingRecord;
import com.joydigit.seniorcaring.mvp.enums.CheckinStatusEnum;
import com.joydigit.seniorcaring.mvp.mapper.ElderCustomerCheckinMapper;
import com.joydigit.seniorcaring.mvp.mapper.ElderCustomerMapper;
import com.joydigit.seniorcaring.mvp.mapper.ElderCustomerVisitingRecordMapper;
import com.joydigit.seniorcaring.mvp.service.IElderCustomerVisitingRecordService;
import org.jeecg.common.api.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * @Description: elder_customer_visiting_record
 * @Author: jeecg-boot
 * @Date:   2026-04-17
 * @Version: V1.0
 */
@Service
public class ElderCustomerVisitingRecordServiceImpl extends ServiceImpl<ElderCustomerVisitingRecordMapper, ElderCustomerVisitingRecord> implements IElderCustomerVisitingRecordService {

    @Autowired
    private ElderCustomerMapper elderCustomerMapper;
    @Autowired
    private ElderCustomerCheckinMapper elderCustomerCheckinMapper;
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<String> saveInfo(ElderCustomerVisitingRecord elderCustomerVisitingRecord) {
        ElderCustomer elderCustomer = elderCustomerMapper.selectById(elderCustomerVisitingRecord.getCustomerId());
        if (Objects.isNull(elderCustomer)){
            return Result.error("客户不存在");
        }
        List<ElderCustomerCheckin> elderCustomerCheckins = elderCustomerCheckinMapper.selectList(Wrappers.lambdaQuery(ElderCustomerCheckin.class)
                .eq(ElderCustomerCheckin::getCustomerId, elderCustomerVisitingRecord.getCustomerId())
                .eq(ElderCustomerCheckin::getStatus, CheckinStatusEnum.CHECKIN.getKey()));
        if (CollectionUtil.isNotEmpty(elderCustomerCheckins)){
            elderCustomerVisitingRecord.setCheckinId(elderCustomerCheckins.get(0).getId());
        }
        save(elderCustomerVisitingRecord);
        return Result.OK("添加成功");
    }

    @Override
    public IPage<ElderCustomerVisitingRecord> pageList(Page<ElderCustomerVisitingRecord> page, ElderCustomerVisitingRecord elderCustomerVisitingRecord) {
        return this.baseMapper.pageList(page,elderCustomerVisitingRecord);
    }
}
