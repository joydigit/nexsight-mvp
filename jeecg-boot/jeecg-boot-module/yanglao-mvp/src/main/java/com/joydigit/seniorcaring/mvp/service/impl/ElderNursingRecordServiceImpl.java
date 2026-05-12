package com.joydigit.seniorcaring.mvp.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.joydigit.seniorcaring.mvp.entity.ElderCustomer;
import com.joydigit.seniorcaring.mvp.entity.ElderCustomerCheckin;
import com.joydigit.seniorcaring.mvp.entity.ElderNursingRecord;
import com.joydigit.seniorcaring.mvp.enums.CheckinStatusEnum;
import com.joydigit.seniorcaring.mvp.mapper.ElderCustomerCheckinMapper;
import com.joydigit.seniorcaring.mvp.mapper.ElderCustomerMapper;
import com.joydigit.seniorcaring.mvp.mapper.ElderNursingRecordMapper;
import com.joydigit.seniorcaring.mvp.service.IElderNursingRecordService;
import org.jeecg.common.api.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * @Description: elder_nursing_record
 * @Author: jeecg-boot
 * @Date:   2026-04-17
 * @Version: V1.0
 */
@Service
public class ElderNursingRecordServiceImpl extends ServiceImpl<ElderNursingRecordMapper, ElderNursingRecord> implements IElderNursingRecordService {
    @Autowired
    private ElderCustomerMapper elderCustomerMapper;
    @Autowired
    private ElderCustomerCheckinMapper elderCustomerCheckinMapper;
    @Override
    public IPage<ElderNursingRecord> pageList(Page<ElderNursingRecord> page, ElderNursingRecord elderNursingRecord) {
        return this.baseMapper.pageList(page,elderNursingRecord);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<String> saveInfo(ElderNursingRecord elderNursingRecord) {
        ElderCustomer elderCustomer = elderCustomerMapper.selectById(elderNursingRecord.getCustomerId());
        if (Objects.isNull(elderCustomer)){
            return Result.error("客户不存在");
        }
        List<ElderCustomerCheckin> elderCustomerCheckins = elderCustomerCheckinMapper.selectList(Wrappers.lambdaQuery(ElderCustomerCheckin.class)
                .eq(ElderCustomerCheckin::getCustomerId, elderNursingRecord.getCustomerId())
                .eq(ElderCustomerCheckin::getStatus, CheckinStatusEnum.CHECKIN.getKey()));
        if (CollectionUtil.isNotEmpty(elderCustomerCheckins)){
            elderNursingRecord.setCheckinId(elderCustomerCheckins.get(0).getId());
        }
        save(elderNursingRecord);
        return Result.OK("添加成功");
    }
}
