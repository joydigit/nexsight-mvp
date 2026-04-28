package com.joydigit.seniorcaring.mvp.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.joydigit.seniorcaring.mvp.entity.ElderProjectAccount;
import com.joydigit.seniorcaring.mvp.mapper.ElderProjectAccountMapper;
import com.joydigit.seniorcaring.mvp.service.IElderProjectAccountService;
import com.joydigit.seniorcaring.mvp.service.IElderProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 项目账户配置
 * @Author: jeecg-boot
 * @Date:   2026-04-28
 * @Version: V1.0
 */
@Service
public class ElderProjectAccountServiceImpl extends ServiceImpl<ElderProjectAccountMapper, ElderProjectAccount> implements IElderProjectAccountService {

    @Autowired
    private IElderProjectService elderProjectService;
    @Override
    public IPage<ElderProjectAccount> pageList(Page<ElderProjectAccount> page, ElderProjectAccount elderProjectAccount) {
        List<String> projectIds = elderProjectService.getProjectIdByUserId();
        elderProjectAccount.setProjectIds(projectIds);
        if (CollectionUtil.isEmpty(projectIds)){
            page.setRecords(new ArrayList<>());
            return page;
        }
        return this.baseMapper.pageList(page,elderProjectAccount);
    }
}
