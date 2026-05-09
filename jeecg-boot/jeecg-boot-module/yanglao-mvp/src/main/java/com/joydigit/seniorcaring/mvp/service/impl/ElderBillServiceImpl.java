package com.joydigit.seniorcaring.mvp.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.joydigit.seniorcaring.mvp.entity.ElderBill;
import com.joydigit.seniorcaring.mvp.entity.ElderCustomer;
import com.joydigit.seniorcaring.mvp.entity.ElderCustomerCheckin;
import com.joydigit.seniorcaring.mvp.enums.BillStatusEnum;
import com.joydigit.seniorcaring.mvp.enums.CheckinStatusEnum;
import com.joydigit.seniorcaring.mvp.mapper.ElderBillMapper;
import com.joydigit.seniorcaring.mvp.mapper.ElderCustomerCheckinMapper;
import com.joydigit.seniorcaring.mvp.mapper.ElderCustomerMapper;
import com.joydigit.seniorcaring.mvp.service.IElderBillService;
import com.joydigit.seniorcaring.mvp.service.IElderProjectService;
import org.apache.commons.lang3.StringUtils;
import org.jeecg.common.api.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @Description: elder_bill
 * @Author: jeecg-boot
 * @Date:   2026-04-17
 * @Version: V1.0
 */
@Service
public class ElderBillServiceImpl extends ServiceImpl<ElderBillMapper, ElderBill> implements IElderBillService {

    @Autowired
    private ElderCustomerMapper elderCustomerMapper;
    @Autowired
    private ElderCustomerCheckinMapper elderCustomerCheckinMapper;
    @Autowired
    private IElderProjectService elderProjectService;
    @Override
    public IPage<ElderBill> pageList(Page<ElderBill> page, ElderBill elderBill) {
        List<String> projectIds = elderProjectService.getProjectIdByUserId();
        elderBill.setProjectIds(projectIds);
        if (CollectionUtil.isEmpty(projectIds)){
            page.setRecords(new ArrayList<>());
            return page;
        }
        return this.baseMapper.pageList(page,elderBill);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<String> saveInfo(ElderBill elderBill) {
        ElderCustomer elderCustomer = elderCustomerMapper.selectById(elderBill.getCustomerId());
        if (Objects.isNull(elderCustomer)){
            return Result.error("客户不存在");
        }
        List<ElderCustomerCheckin> elderCustomerCheckins = elderCustomerCheckinMapper.selectList(Wrappers.lambdaQuery(ElderCustomerCheckin.class)
                .eq(ElderCustomerCheckin::getCustomerId, elderBill.getCustomerId())
                .eq(ElderCustomerCheckin::getStatus, CheckinStatusEnum.CHECKIN.getKey()));
        if (CollectionUtil.isNotEmpty(elderCustomerCheckins)){
            elderBill.setCheckinId(elderCustomerCheckins.get(0).getId());
        }

        elderBill.setBillNo(getBillNo());
        if (StringUtils.isBlank(elderBill.getStatus())){
            elderBill.setStatus(BillStatusEnum.UNPAYMENT.getKey());
        }
        save(elderBill);
        return Result.OK("保存成功！");
    }

    /**
     *
     * @return
     */
    private String getBillNo(){
        String billNoPre = DateUtil.format(new Date(), DatePattern.PURE_DATETIME_PATTERN);
        String format = String.format("%04d", ThreadLocalRandom.current().nextInt(10000));
        return billNoPre+format;
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<String> updateInfo(ElderBill elderBill) {
        ElderBill query = getById(elderBill.getId());
        if (Objects.isNull(query)){
            return Result.error("记录不存在");
        }
        if (!query.getStatus().equals(BillStatusEnum.UNPAYMENT.getKey())){
            return Result.error("未支付账单才能修改");
        }
        updateById(elderBill);
        return Result.OK("修改成功");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<String> deleteInfo(String id) {
        ElderBill query = getById(id);
        if (Objects.isNull(query)){
            return Result.error("记录不存在");
        }
        if (query.getStatus().equals(BillStatusEnum.PARTIAL_PAYMENT.getKey()) || query.getStatus().equals(BillStatusEnum.SETTLED.getKey())){
            return Result.error("支付账单不能删除");
        }
        removeById(id);
        return Result.OK("删除成功");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<String> deleteInfos(List<String> list) {
        List<ElderBill> listBill = list(Wrappers.lambdaQuery(ElderBill.class).eq(ElderBill::getId, list));
        for (ElderBill elderBill : listBill) {
            if (elderBill.getStatus().equals(BillStatusEnum.PARTIAL_PAYMENT.getKey()) || elderBill.getStatus().equals(BillStatusEnum.SETTLED.getKey())){
                return Result.error("支付账单不能删除");
            }
        }
        removeBatchByIds(list);
        return Result.OK("删除成功");
    }

    @Override
    public Result<List<ElderBill>> getBillListByCustomerId(String customerId) {
        List<ElderBill> list = list(Wrappers.lambdaQuery(ElderBill.class).eq(ElderBill::getCustomerId, customerId)
                .in(ElderBill::getStatus, Arrays.asList(BillStatusEnum.UNPAYMENT.getKey(), BillStatusEnum.PARTIAL_PAYMENT.getKey()))
                .orderByDesc(ElderBill::getCreateTime));
        return Result.ok(list);
    }
}
