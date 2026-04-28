package com.joydigit.seniorcaring.mvp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.joydigit.seniorcaring.mvp.entity.ElderProjectAccount;
import org.apache.ibatis.annotations.Param;

/**
 * @Description: 项目账户配置
 * @Author: jeecg-boot
 * @Date:   2026-04-28
 * @Version: V1.0
 */
public interface ElderProjectAccountMapper extends BaseMapper<ElderProjectAccount> {

    IPage<ElderProjectAccount> pageList(Page<ElderProjectAccount> page,@Param("data") ElderProjectAccount elderProjectAccount);
}
