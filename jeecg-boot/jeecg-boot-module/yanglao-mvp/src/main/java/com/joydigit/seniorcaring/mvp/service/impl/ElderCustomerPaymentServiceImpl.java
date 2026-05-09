package com.joydigit.seniorcaring.mvp.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.joydigit.seniorcaring.mvp.entity.*;
import com.joydigit.seniorcaring.mvp.enums.*;
import com.joydigit.seniorcaring.mvp.mapper.*;
import com.joydigit.seniorcaring.mvp.service.IElderCustomerPaymentService;
import com.joydigit.seniorcaring.mvp.service.IElderProjectService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.vo.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Description: elder_customer_payment
 * @Author: jeecg-boot
 * @Date:   2026-04-17
 * @Version: V1.0
 */
@Service
public class ElderCustomerPaymentServiceImpl extends ServiceImpl<ElderCustomerPaymentMapper, ElderCustomerPayment> implements IElderCustomerPaymentService {
    @Autowired
    private ElderCustomerMapper elderCustomerMapper;
    @Autowired
    private ElderCustomerCheckinMapper elderCustomerCheckinMapper;
    @Autowired
    private IElderProjectService elderProjectService;
    @Autowired
    private ElderCustomerAccountMapper elderCustomerAccountMapper;
    @Autowired
    private ElderBillMapper elderBillMapper;
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<String> saveInfo(ElderCustomerPayment elderCustomerPayment) {
        ElderCustomer elderCustomer = elderCustomerMapper.selectById(elderCustomerPayment.getCustomerId());
        if (Objects.isNull(elderCustomer)){
            return Result.error("客户不存在");
        }
        List<ElderCustomerCheckin> elderCustomerCheckins = elderCustomerCheckinMapper.selectList(Wrappers.lambdaQuery(ElderCustomerCheckin.class)
                .eq(ElderCustomerCheckin::getCustomerId, elderCustomerPayment.getCustomerId())
                .eq(ElderCustomerCheckin::getStatus, CheckinStatusEnum.CHECKIN.getKey()));
        if (CollectionUtil.isNotEmpty(elderCustomerCheckins)){
            elderCustomerPayment.setCheckinId(elderCustomerCheckins.get(0).getId());
        }
        PaymentMethodEnum paymentMethodEnum = PaymentMethodEnum.getInstance(elderCustomerPayment.getPaymentMethodCode());
        if (Objects.nonNull(paymentMethodEnum)){
            elderCustomerPayment.setPaymentMethodName(paymentMethodEnum.getMsg());
        }
        if (TransactionTypeEnum.REFUND.getKey().equals(elderCustomerPayment.getTransactionTypeCode()) ||
                TransactionTypeEnum.CONSUMPTION.getKey().equals(elderCustomerPayment.getTransactionTypeCode())){
            elderCustomerPayment.setAmount(elderCustomerPayment.getAmount().abs().multiply(new BigDecimal("-1")));
        }
        save(elderCustomerPayment);
        return Result.OK("添加成功");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<String> updateInfo(ElderCustomerPayment elderCustomerPayment) {
        ElderCustomerPayment query = getById(elderCustomerPayment.getId());
        if (Objects.isNull(query)){
            return Result.error("记录不存在");
        }
        if (query.getConfirmFlag().equals(PaymentConfirmFlagEnum.YES.getKey())){
            return Result.error("确认记录不能修改");
        }
        PaymentMethodEnum paymentMethodEnum = PaymentMethodEnum.getInstance(elderCustomerPayment.getPaymentMethodCode());
        if (Objects.nonNull(paymentMethodEnum)){
            elderCustomerPayment.setPaymentMethodName(paymentMethodEnum.getMsg());
        }
        if (TransactionTypeEnum.REFUND.getKey().equals(elderCustomerPayment.getTransactionTypeCode()) ||
                TransactionTypeEnum.CONSUMPTION.getKey().equals(elderCustomerPayment.getTransactionTypeCode())){
            elderCustomerPayment.setAmount(elderCustomerPayment.getAmount().abs().multiply(new BigDecimal("-1")));
        }
        updateById(elderCustomerPayment);
        return Result.OK("修改成功");
    }

    @Override
    public Result<String> removeInfo(String id) {
        ElderCustomerPayment query = getById(id);
        if (Objects.isNull(query)){
            return Result.error("记录不存在");
        }
        if (query.getConfirmFlag().equals(PaymentConfirmFlagEnum.YES.getKey())) {
            return Result.error("确认过的记录不能删除");
        }
        removeById(id);
        return Result.ok("删除成功");
    }

    @Override
    public IPage<ElderCustomerPayment> pageList(Page<ElderCustomerPayment> page, ElderCustomerPayment elderCustomerPayment) {
        List<String> projectIds = elderProjectService.getProjectIdByUserId();
        elderCustomerPayment.setProjectIds(projectIds);
        if (CollectionUtil.isEmpty(projectIds)){
            page.setRecords(new ArrayList<>());
            return page;
        }
        return this.baseMapper.pageList(page,elderCustomerPayment);
    }

    @Override
    public List<ElderCustomerPayment> getList(ElderCustomerPayment elderCustomerPayment, List<String> selectionList) {
        List<String> projectIds = elderProjectService.getProjectIdByUserId();
        elderCustomerPayment.setProjectIds(projectIds);
        if (CollectionUtil.isEmpty(projectIds)){
            return new ArrayList<>();
        }
        return this.baseMapper.getList(elderCustomerPayment,selectionList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<String> confirm(String ids) throws Exception {
        List<ElderCustomerPayment> list = list(Wrappers.lambdaQuery(ElderCustomerPayment.class).in(ElderCustomerPayment::getId, Arrays.asList(ids.split(","))));
        List<ElderCustomerPayment> confirmedList = list.stream().filter(s -> s.getConfirmFlag().equals(PaymentConfirmFlagEnum.YES.getKey())).collect(Collectors.toList());
        if (CollectionUtil.isNotEmpty(confirmedList)){
            return Result.error("不能包含已确认记录");
        }
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        Date confirmTime = new Date();
        for (ElderCustomerPayment payment : list) {
            updateAccount(payment);
            updateBill(payment);
            payment.setConfirmFlag(PaymentConfirmFlagEnum.YES.getKey());
            payment.setConfirmTime(confirmTime);
            payment.setConfirmUserId(sysUser.getId());
            updateById(payment);
        }
        return Result.OK("确认成功");
    }

    private void updateBill(ElderCustomerPayment payment) throws Exception {
        if (payment.getTransactionTypeCode().equals(TransactionTypeEnum.PAY_FEES.getKey()) && StringUtils.isNotBlank(payment.getBillId())){
            ElderBill elderBill = elderBillMapper.selectById(payment.getBillId());
            if (Objects.isNull(elderBill)){
                throw new Exception("账单不存在");
            }
            ElderBill updateBill = new ElderBill();
            BigDecimal total = elderBill.getPaidAmount().add(elderBill.getDiscountAmount()).add(payment.getAmount().abs());
            if (total.compareTo(elderBill.getTotalAmount()) == -1){
                updateBill.setStatus(BillStatusEnum.PARTIAL_PAYMENT.getKey());
            } else if(total.compareTo(elderBill.getTotalAmount()) ==0){
                updateBill.setStatus(BillStatusEnum.SETTLED.getKey());
            } else {
                throw new Exception("账单金额和支付金额不符");
            }
            updateBill.setPaidAmount(elderBill.getPaidAmount().add(payment.getAmount().abs()));
            updateBill.setId(payment.getBillId());
            elderBillMapper.updateById(updateBill);
        }
    }

    /**
     *
     * @param payment
     * @throws Exception
     */
    private void updateAccount(ElderCustomerPayment payment) throws Exception {
        int updateType = 1;
        if (payment.getTransactionTypeCode().equals(TransactionTypeEnum.RECHARGE.getKey()) && StringUtils.isNotBlank(payment.getAccountId())) {
            updateType = 1;
        } else  if (payment.getTransactionTypeCode().equals(TransactionTypeEnum.CONSUMPTION.getKey()) && StringUtils.isNotBlank(payment.getAccountId())) {
            // 更新账户金额
            updateType = 2;
        } else  if (payment.getTransactionTypeCode().equals(TransactionTypeEnum.REFUND.getKey()) && StringUtils.isNotBlank(payment.getAccountId())) {
            // 更新账户金额
            updateType = 2;
        } else  if (payment.getTransactionTypeCode().equals(TransactionTypeEnum.PAY_FEES.getKey()) && StringUtils.isNotBlank(payment.getAccountId())) {
            // 更新账户金额
            updateType = 2;
        } else {
            return;
        }
        // 给账户添加金额
        ElderCustomerAccount account = elderCustomerAccountMapper.selectById(payment.getAccountId());
        if (Objects.isNull(account)){
            throw new Exception("账户不存在");
        }
        if (updateType == 2 && account.getAmount().compareTo(payment.getAmount().abs()) == -1){
            throw new Exception("账户余额不存在");
        }
        BigDecimal amount = BigDecimal.ZERO;
        if (updateType == 2){
            amount = account.getAmount().subtract(payment.getAmount().abs());
        } else {
            amount = account.getAmount().add(payment.getAmount().abs());
        }
        LambdaUpdateWrapper<ElderCustomerAccount> eq = Wrappers.lambdaUpdate(ElderCustomerAccount.class)
                .set(ElderCustomerAccount::getAmount,amount )
                .eq(ElderCustomerAccount::getAmount, account.getAmount())
                .eq(ElderCustomerAccount::getId, payment.getAccountId());
        int update = elderCustomerAccountMapper.update(eq);
        if (update == 0){
            throw new Exception("更新失败");
        }
    }
}
