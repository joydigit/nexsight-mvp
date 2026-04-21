package com.joydigit.seniorcaring.mvp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.joydigit.seniorcaring.mvp.entity.ElderProject;
import com.joydigit.seniorcaring.mvp.mapper.ElderProjectMapper;
import com.joydigit.seniorcaring.mvp.service.IElderProjectService;
import com.joydigit.seniorcaring.mvp.vo.ProjectVo;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.vo.LoginUser;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @Description: elder_project
 * @Author: jeecg-boot
 * @Date:   2026-04-17
 * @Version: V1.0
 */
@Service
public class ElderProjectServiceImpl extends ServiceImpl<ElderProjectMapper, ElderProject> implements IElderProjectService {

    @Override
    public Result<List<ProjectVo>> getProjectListAll(Integer type) {
        if (Objects.nonNull(type) && type==1){
            List<ProjectVo> list = this.baseMapper.getProjectListAll();
            return Result.OK(list);
        }
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        List<ProjectVo> list = this.baseMapper.getProjectListByUser(sysUser.getId());
        return Result.OK(list);
    }
}
