package com.joydigit.seniorcaring.mvp.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.joydigit.seniorcaring.mvp.entity.ElderAssessment;
import com.joydigit.seniorcaring.mvp.entity.ElderCustomer;
import com.joydigit.seniorcaring.mvp.entity.ElderCustomerCheckin;
import com.joydigit.seniorcaring.mvp.enums.CheckinStatusEnum;
import com.joydigit.seniorcaring.mvp.mapper.ElderAssessmentMapper;
import com.joydigit.seniorcaring.mvp.mapper.ElderCustomerCheckinMapper;
import com.joydigit.seniorcaring.mvp.mapper.ElderCustomerMapper;
import com.joydigit.seniorcaring.mvp.service.IElderAssessmentService;
import org.apache.commons.lang3.StringUtils;
import org.jeecg.common.api.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;
import java.util.Objects;

/**
 * @Description: elder_assessment
 * @Author: jeecg-boot
 * @Date:   2026-04-17
 * @Version: V1.0
 */
@Service
public class ElderAssessmentServiceImpl extends ServiceImpl<ElderAssessmentMapper, ElderAssessment> implements IElderAssessmentService {

    @Autowired
    private ElderCustomerMapper elderCustomerMapper;
    @Autowired
    private ElderCustomerCheckinMapper elderCustomerCheckinMapper;
    @Override
    public IPage<ElderAssessment> pageList(Page<ElderAssessment> page, ElderAssessment elderAssessment) {
        return this.baseMapper.pageList(page,elderAssessment);
    }

    @Override
    public Result<String> saveInfo(ElderAssessment elderAssessment) {
        ElderCustomer elderCustomer = elderCustomerMapper.selectById(elderAssessment.getCustomerId());
        if (Objects.isNull(elderCustomer)){
            return Result.error("客户不存在");
        }
        List<ElderCustomerCheckin> elderCustomerCheckins = elderCustomerCheckinMapper.selectList(Wrappers.lambdaQuery(ElderCustomerCheckin.class)
                .eq(ElderCustomerCheckin::getCustomerId, elderAssessment.getCustomerId())
                .eq(ElderCustomerCheckin::getStatus, CheckinStatusEnum.CHECKIN.getKey()));
        if (CollectionUtil.isNotEmpty(elderCustomerCheckins)){
            elderAssessment.setCheckinId(elderCustomerCheckins.get(0).getId());
        }
        save(elderAssessment);
        return Result.OK("添加成功");
    }
}
