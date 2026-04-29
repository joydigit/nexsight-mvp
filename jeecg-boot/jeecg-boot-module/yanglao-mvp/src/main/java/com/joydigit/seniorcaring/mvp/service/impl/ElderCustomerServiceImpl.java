package com.joydigit.seniorcaring.mvp.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.joydigit.seniorcaring.mvp.entity.*;
import com.joydigit.seniorcaring.mvp.mapper.ElderCustomerCheckinMapper;
import com.joydigit.seniorcaring.mvp.mapper.ElderCustomerMapper;
import com.joydigit.seniorcaring.mvp.mapper.ElderProjectMapper;
import com.joydigit.seniorcaring.mvp.mapper.ElderRoomReserveMapper;
import com.joydigit.seniorcaring.mvp.service.IElderCustomerAccountService;
import com.joydigit.seniorcaring.mvp.service.IElderCustomerService;
import com.joydigit.seniorcaring.mvp.service.IElderProjectAccountService;
import com.joydigit.seniorcaring.mvp.vo.ElderCustomerVo;
import org.jeecg.common.api.vo.Result;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Description: elder_customer
 * @Author: jeecg-boot
 * @Date:   2026-04-17
 * @Version: V1.0
 */
@Service
public class ElderCustomerServiceImpl extends ServiceImpl<ElderCustomerMapper, ElderCustomer> implements IElderCustomerService {

    @Autowired
    private IElderProjectAccountService elderProjectAccountService;
    @Autowired
    private IElderCustomerAccountService elderCustomerAccountService;
    @Autowired
    private ElderCustomerCheckinMapper elderCustomerCheckinMapper;
    @Autowired
    private ElderRoomReserveMapper elderRoomReserveMapper;
    @Autowired
    private ElderProjectMapper elderProjectMapper;
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<String> saveInfo(ElderCustomer elderCustomer) {
        Long existCount = this.baseMapper.selectCount(Wrappers.lambdaQuery(ElderCustomer.class)
                .and(s->s.eq(ElderCustomer::getIdCard,elderCustomer.getIdCard()).or().eq(ElderCustomer::getPhone,elderCustomer.getPhone())));
        if (existCount > 0){
            return Result.error("证件号码或手机号重复");
        }
        List<ElderProjectAccount> projectAccountList = elderProjectAccountService
                .list(Wrappers.lambdaQuery(ElderProjectAccount.class)
                        .eq(ElderProjectAccount::getStatus,"1")
                        .eq(ElderProjectAccount::getProjectId, elderCustomer.getProjectId()));
        if (CollectionUtil.isEmpty(projectAccountList)){
            return Result.error("项目账户未配置");
        }
        elderCustomer.setId(IdWorker.getIdStr());
        save(elderCustomer);
        List<ElderCustomerAccount> list = new ArrayList<>();
        for (ElderProjectAccount elderProjectAccount : projectAccountList) {
            ElderCustomerAccount account = new ElderCustomerAccount();
            account.setCustomerId(elderCustomer.getId());
            account.setAmount(BigDecimal.ZERO);
            account.setAccountTypeCode(elderProjectAccount.getAccountTypeCode());
            account.setProjectId(elderProjectAccount.getProjectId());
            list.add(account);
        }
        elderCustomerAccountService.saveBatch(list);
        return Result.OK("添加成功");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<String> updateInfo(ElderCustomer elderCustomer) {
        Long existCount = this.baseMapper.selectCount(Wrappers.lambdaQuery(ElderCustomer.class)
                        .ne(ElderCustomer::getId,elderCustomer.getId())
                .and(s->s.eq(ElderCustomer::getIdCard,elderCustomer.getIdCard()).or().eq(ElderCustomer::getPhone,elderCustomer.getPhone())));
        if (existCount > 0){
            return Result.error("证件号码或手机号重复");
        }
        List<ElderProjectAccount> projectAccountList = elderProjectAccountService
                .list(Wrappers.lambdaQuery(ElderProjectAccount.class)
                        .eq(ElderProjectAccount::getStatus,"1")
                        .eq(ElderProjectAccount::getProjectId, elderCustomer.getProjectId()));
        if (CollectionUtil.isEmpty(projectAccountList)){
            return Result.error("项目账户未配置");
        }
        updateById(elderCustomer);
        List<ElderCustomerAccount> customerAccountList = elderCustomerAccountService
                .list(Wrappers.lambdaQuery(ElderCustomerAccount.class)
                        .eq(ElderCustomerAccount::getCustomerId, elderCustomer.getId()));
        List<String> accountTypes = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(customerAccountList)){
            accountTypes = customerAccountList.stream().map(ElderCustomerAccount::getAccountTypeCode).collect(Collectors.toList());
        }
        List<ElderCustomerAccount> list = new ArrayList<>();
        for (ElderProjectAccount elderProjectAccount : projectAccountList) {
            if (!accountTypes.contains(elderProjectAccount.getAccountTypeCode())){
                ElderCustomerAccount account = new ElderCustomerAccount();
                account.setCustomerId(elderCustomer.getId());
                account.setAmount(BigDecimal.ZERO);
                account.setAccountTypeCode(elderProjectAccount.getAccountTypeCode());
                account.setProjectId(elderProjectAccount.getProjectId());
                list.add(account);
            }
        }
        if (CollectionUtil.isNotEmpty(list)){
            elderCustomerAccountService.saveBatch(list);
        }
        return Result.OK("编辑成功！");
    }

    @Override
    public IPage<ElderCustomer> pageList(Page<ElderCustomer> page, ElderCustomer elderCustomer) {
        return this.baseMapper.pageList(page,elderCustomer);
    }

    @Override
    public Result<ElderCustomerVo> getCustomerLastInfo(String id) {
        ElderCustomer customer = getById(id);
        if (Objects.isNull(customer)){
            return Result.OK();
        }
        ElderCustomerVo vo = new ElderCustomerVo();
        BeanUtils.copyProperties(customer,vo);
        ElderProject elderProject = elderProjectMapper.selectById(customer.getProjectId());
        if (Objects.nonNull(elderProject)){
            vo.setProjectName(elderProject.getProjectName());
        }
        // 查询最新的入住记录
        ElderCustomerCheckin checkin = elderCustomerCheckinMapper.getLastInfoByCustomerId(id);
        if (Objects.nonNull(checkin) && checkin.getStatus().equals("1")){
            vo.setBedId(checkin.getBedId());
            vo.setBedNo(checkin.getBedNo());
            vo.setRoomId(checkin.getRoomId());
            vo.setRoomNo(checkin.getRoomNo());
            vo.setFloorId(checkin.getFloorId());
            vo.setFloorName(checkin.getFloorName());
            vo.setBuildingId(checkin.getBuildingId());
            vo.setBuildingName(checkin.getBuildingName());
            vo.setCheckinStatus(checkin.getStatus());
        } else {
            // 查询最新的预定记录
            ElderRoomReserve reserve = elderRoomReserveMapper.getRoomReserveByCustomerId(id);
            if (Objects.nonNull(reserve) && reserve.getStatus().equals("1")
                    &&( Objects.isNull (checkin) ||
                    (Objects.nonNull(checkin.getCreateTime())&&checkin.getCreateTime().before(reserve.getCreateTime())))){
                vo.setBedId(reserve.getBedId());
                vo.setBedNo(reserve.getBedNo());
                vo.setRoomId(reserve.getRoomId());
                vo.setRoomNo(reserve.getRoomNo());
                vo.setFloorId(reserve.getFloorId());
                vo.setFloorName(reserve.getFloorName());
                vo.setBuildingId(reserve.getBuildingId());
                vo.setBuildingName(reserve.getBuildingName());
                vo.setReserveStatus(reserve.getStatus());
            }
        }
        return Result.OK(vo);
    }
}
