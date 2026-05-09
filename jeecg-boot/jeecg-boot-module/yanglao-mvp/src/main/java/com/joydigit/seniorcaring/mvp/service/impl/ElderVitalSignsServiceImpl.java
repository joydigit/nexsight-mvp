package com.joydigit.seniorcaring.mvp.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.joydigit.seniorcaring.mvp.entity.ElderCustomer;
import com.joydigit.seniorcaring.mvp.entity.ElderCustomerCheckin;
import com.joydigit.seniorcaring.mvp.entity.ElderVitalSigns;
import com.joydigit.seniorcaring.mvp.enums.CheckinStatusEnum;
import com.joydigit.seniorcaring.mvp.mapper.ElderCustomerCheckinMapper;
import com.joydigit.seniorcaring.mvp.mapper.ElderCustomerMapper;
import com.joydigit.seniorcaring.mvp.mapper.ElderVitalSignsMapper;
import com.joydigit.seniorcaring.mvp.service.IElderVitalSignsService;
import org.apache.commons.lang3.StringUtils;
import org.jeecg.common.api.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;
import java.util.Objects;

/**
 * @Description: elder_vital_signs
 * @Author: jeecg-boot
 * @Date:   2026-04-17
 * @Version: V1.0
 */
@Service
public class ElderVitalSignsServiceImpl extends ServiceImpl<ElderVitalSignsMapper, ElderVitalSigns> implements IElderVitalSignsService {

    @Autowired
    private ElderCustomerMapper elderCustomerMapper;
    @Autowired
    private ElderCustomerCheckinMapper elderCustomerCheckinMapper;
    @Override
    public Result<String> saveInfo(ElderVitalSigns elderVitalSigns) {
        ElderCustomer elderCustomer = elderCustomerMapper.selectById(elderVitalSigns.getCustomerId());
        if (Objects.isNull(elderCustomer)){
            return Result.error("客户不存在");
        }
        if (StringUtils.isBlank(elderVitalSigns.getBloodPressureHigh()) &&
                StringUtils.isBlank(elderVitalSigns.getTemperature()) &&
                StringUtils.isBlank(elderVitalSigns.getBloodPressureLow()) &&
                StringUtils.isBlank(elderVitalSigns.getHeartRate()) &&
                StringUtils.isBlank(elderVitalSigns.getBloodSugar()) &&
                StringUtils.isBlank(elderVitalSigns.getRespiration())
        ){
            return Result.error("必须填一个指标");
        }
        List<ElderCustomerCheckin> elderCustomerCheckins = elderCustomerCheckinMapper.selectList(Wrappers.lambdaQuery(ElderCustomerCheckin.class)
                .eq(ElderCustomerCheckin::getCustomerId, elderVitalSigns.getCustomerId())
                .eq(ElderCustomerCheckin::getStatus, CheckinStatusEnum.CHECKIN.getKey()));
        if (CollectionUtil.isNotEmpty(elderCustomerCheckins)){
            elderVitalSigns.setCheckinId(elderCustomerCheckins.get(0).getId());
        }
        save(elderVitalSigns);
        return Result.OK("添加成功");
    }

    @Override
    public IPage<ElderVitalSigns> pageList(Page<ElderVitalSigns> page, ElderVitalSigns elderVitalSigns) {
        return this.baseMapper.pageList(page,elderVitalSigns);
    }
}
