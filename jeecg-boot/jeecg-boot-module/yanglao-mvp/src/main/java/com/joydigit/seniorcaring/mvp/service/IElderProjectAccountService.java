package com.joydigit.seniorcaring.mvp.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.joydigit.seniorcaring.mvp.entity.ElderProjectAccount;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: 项目账户配置
 * @Author: jeecg-boot
 * @Date:   2026-04-28
 * @Version: V1.0
 */
public interface IElderProjectAccountService extends IService<ElderProjectAccount> {

    IPage<ElderProjectAccount> pageList(Page<ElderProjectAccount> page, ElderProjectAccount elderProjectAccount);
}
