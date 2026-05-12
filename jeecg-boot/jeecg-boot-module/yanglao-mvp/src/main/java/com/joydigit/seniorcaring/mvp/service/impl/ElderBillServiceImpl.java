package com.joydigit.seniorcaring.mvp.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.joydigit.seniorcaring.mvp.entity.*;
import com.joydigit.seniorcaring.mvp.enums.*;
import com.joydigit.seniorcaring.mvp.mapper.*;
import com.joydigit.seniorcaring.mvp.service.IElderBillService;
import com.joydigit.seniorcaring.mvp.service.IElderProjectService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jeecg.common.api.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

/**
 * @Description: elder_bill
 * @Author: jeecg-boot
 * @Date:   2026-04-17
 * @Version: V1.0
 */
@Service
@Slf4j
public class ElderBillServiceImpl extends ServiceImpl<ElderBillMapper, ElderBill> implements IElderBillService {

    @Autowired
    private ElderCustomerMapper elderCustomerMapper;
    @Autowired
    private ElderCustomerCheckinMapper elderCustomerCheckinMapper;
    @Autowired
    private ElderBillDetailMapper elderBillDetailMapper;
    @Autowired
    private IElderProjectService elderProjectService;
    @Autowired
    private ElderCustomerCheckinFeeMapper elderCustomerCheckinFeeMapper;
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

    /**
     *
     * @param date
     * @return
     */
    private int getDaysInMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    @Override
    public void calBillInfo(ElderCustomerCheckin checkin, ChangeTypeEnum checkout) throws Exception {
        Date endDate = null;
        if (checkout.getKey().equals(ChangeTypeEnum.CHECKOUT.getKey())){
            endDate = checkin.getRealCheckoutTime();
        } else if(checkout.getKey().equals(ChangeTypeEnum.CHANGEROOM.getKey())){
            endDate = DateUtil.parse(DateUtil.format(new Date(),DatePattern.NORM_DATE_PATTERN),DatePattern.NORM_DATE_PATTERN);
        } else {
            return;
        }
        Date startDate = checkin.getCheckinTime();
        Date nowDate = new Date();
        String nowMM = DateUtil.format(nowDate, DatePattern.NORM_MONTH_PATTERN);
        String startMM = DateUtil.format(startDate, DatePattern.NORM_MONTH_PATTERN);

        int totalDays = getDaysInMonth(nowDate);
        if (!startMM.equals(nowMM)){
            startDate = DateUtil.beginOfMonth(nowDate);
        }
        long days = DateUtil.between(startDate, endDate, DateUnit.DAY);
        if (days <=0){
            log.error("计算账单日期天数为"+days);
            return;
        }
        List<ElderCustomerCheckinFee> feeList = elderCustomerCheckinFeeMapper.selectList(Wrappers.lambdaQuery(ElderCustomerCheckinFee.class)
                .eq(ElderCustomerCheckinFee::getCheckinId, checkin.getId()));
        List<ElderCustomerCheckinFee> monthList = feeList.stream().filter(s -> s.getUnitCode().equals(FeeUnitEnum.MONTH.getKey())).collect(Collectors.toList());
        List<ElderCustomerCheckinFee> dayList = feeList.stream().filter(s -> s.getUnitCode().equals(FeeUnitEnum.DAY.getKey())).collect(Collectors.toList());
        if (CollectionUtil.isEmpty(monthList) && CollectionUtil.isEmpty(dayList)){
            log.error("计算账单没有找到月费");
            return;
        }

        List<ElderBillDetail> list = new ArrayList<>();
        ElderBill bill = new ElderBill();
        bill.setId(IdWorker.getIdStr());
        bill.setBillNo(getBillNo());
        bill.setProjectId(checkin.getProjectId());
        BigDecimal totalAmount = BigDecimal.ZERO;
        if (CollectionUtil.isNotEmpty(monthList)){
            for (ElderCustomerCheckinFee fee : monthList) {
                ElderBillDetail detail = new ElderBillDetail();
                PaymentTypeEnum paymentTypeEnum = PaymentTypeEnum.getInstance(fee.getPaymentTypeCode());
                if (Objects.isNull(paymentTypeEnum)){
                    throw new Exception("费用类型未配置全");
                }
                detail.setItemType(paymentTypeEnum.getKey());
                detail.setItemName(paymentTypeEnum.getMsg());
                detail.setProjectId(fee.getProjectId());
                detail.setQuantity(new BigDecimal("1"));
                BigDecimal amount = fee.getAmount().multiply(new BigDecimal(days)).divide(new BigDecimal(totalDays),2,RoundingMode.HALF_UP);
                detail.setUnitPrice(amount);
                detail.setAmount(amount);
                detail.setBillId(bill.getId());
                totalAmount = totalAmount.add(amount);
                list.add(detail);
            }
        }
        if (CollectionUtil.isNotEmpty(dayList)){
            for (ElderCustomerCheckinFee fee : dayList) {
                ElderBillDetail detail = new ElderBillDetail();
                PaymentTypeEnum paymentTypeEnum = PaymentTypeEnum.getInstance(fee.getPaymentTypeCode());
                if (Objects.isNull(paymentTypeEnum)){
                    throw new Exception("费用类型未配置全");
                }
                detail.setItemType(paymentTypeEnum.getKey());
                detail.setItemName(paymentTypeEnum.getMsg());
                detail.setProjectId(fee.getProjectId());
                detail.setQuantity(new BigDecimal(days));
                BigDecimal amount = fee.getAmount().multiply(new BigDecimal(days)).setScale(2, RoundingMode.HALF_UP);
                detail.setUnitPrice(fee.getAmount());
                detail.setAmount(amount);
                detail.setBillId(bill.getId());
                totalAmount = totalAmount.add(amount);
                list.add(detail);
            }
        }
        bill.setBillMonth(nowMM);
        bill.setStatus(BillStatusEnum.UNPAYMENT.getKey());
        bill.setCheckinId(checkin.getId());
        bill.setCustomerId(checkin.getCustomerId());
        bill.setDiscountAmount(BigDecimal.ZERO);
        bill.setTotalAmount(totalAmount);
        bill.setPaidAmount(BigDecimal.ZERO);
        bill.setUnpaidAmount(totalAmount);
        bill.setDueDate(endDate);
        save(bill);
        elderBillDetailMapper.insert(list);
    }

    @Override
    public void calMothBillList() {
        // 入住列表
        List<ElderCustomerCheckin> elderCustomerCheckins = elderCustomerCheckinMapper.selectList(Wrappers.lambdaQuery(ElderCustomerCheckin.class)
                .eq(ElderCustomerCheckin::getStatus, CheckinStatusEnum.CHECKIN.getKey()));
        // 每一条入住再计算账单


    }
}
