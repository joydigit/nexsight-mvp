package com.joydigit.seniorcaring.mvp.mapper;

import java.util.List;

import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.joydigit.seniorcaring.mvp.vo.ProjectVo;
import org.apache.ibatis.annotations.Param;
import com.joydigit.seniorcaring.mvp.entity.ElderProject;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: elder_project
 * @Author: jeecg-boot
 * @Date:   2026-04-17
 * @Version: V1.0
 */
public interface ElderProjectMapper extends BaseMapper<ElderProject> {

    @InterceptorIgnore(tenantLine = "true")
    List<ProjectVo> getProjectListByUser(@Param("userId") String userId);
    @InterceptorIgnore(tenantLine = "true")
    List<ProjectVo> getProjectListAll();
}
