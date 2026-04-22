package com.joydigit.seniorcaring.mvp.service;

import com.joydigit.seniorcaring.mvp.entity.ElderProject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.joydigit.seniorcaring.mvp.vo.ProjectVo;
import org.jeecg.common.api.vo.Result;

import java.util.List;

/**
 * @Description: elder_project
 * @Author: jeecg-boot
 * @Date:   2026-04-17
 * @Version: V1.0
 */
public interface IElderProjectService extends IService<ElderProject> {

    Result<List<ProjectVo>> getProjectListAll(Integer type);

    List<String> getProjectIdByUserId();
}
