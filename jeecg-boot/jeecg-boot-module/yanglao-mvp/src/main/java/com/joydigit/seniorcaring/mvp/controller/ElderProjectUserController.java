package com.joydigit.seniorcaring.mvp.controller;

import java.util.*;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.joydigit.seniorcaring.mvp.enums.DelFlagEnum;
import com.joydigit.seniorcaring.mvp.vo.ProjectUserVo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.query.QueryRuleEnum;
import org.jeecg.common.util.oConvertUtils;
import com.joydigit.seniorcaring.mvp.entity.ElderProjectUser;
import com.joydigit.seniorcaring.mvp.service.IElderProjectUserService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.apache.shiro.authz.annotation.RequiresPermissions;
 /**
 * @Description: elder_project_user
 * @Author: jeecg-boot
 * @Date:   2026-04-17
 * @Version: V1.0
 */
@Tag(name="elder_project_user")
@RestController
@RequestMapping("/elderProjectUser")
@Slf4j
public class ElderProjectUserController extends JeecgController<ElderProjectUser, IElderProjectUserService> {
	@Autowired
	private IElderProjectUserService elderProjectUserService;
	
	/**
	 *   添加
	 *
	 * @param projectUserVo
	 * @return
	 */
	@AutoLog(value = "elder_project_user-添加")
	@Operation(summary="elder_project_user-添加")
	@PostMapping(value = "/add")
	@Transactional(rollbackFor = Exception.class)
	public Result<String> add(@RequestBody ProjectUserVo projectUserVo) {
		if (Objects.isNull(projectUserVo) || StringUtils.isBlank(projectUserVo.getUserId())){
			return Result.error("参数不能为空");
		}
		// 删除历史的
		elderProjectUserService.
				remove(Wrappers.lambdaQuery(ElderProjectUser.class).eq(ElderProjectUser::getUserId,projectUserVo.getUserId()));
		// 添加本次
		String[] projectIdArr = projectUserVo.getProjectIds().split(",");
		if (Objects.nonNull(projectIdArr) && projectIdArr.length > 0) {
			List<ElderProjectUser> list = new ArrayList<>();
			for (String projectId : projectIdArr) {
				ElderProjectUser projectUser = new ElderProjectUser();
				projectUser.setId(IdWorker.getIdStr());
				projectUser.setProjectId(projectId);
				projectUser.setUserId(projectUserVo.getUserId());
				projectUser.setDelFlag(DelFlagEnum.NO.getKey());
				list.add(projectUser);
			}
			elderProjectUserService.saveBatch(list);
		}
		return Result.OK("添加成功！");
	}
	 @Operation(summary="查询用户项目配置")
	@GetMapping("/getProjectUserInfo")
	public Result<ProjectUserVo> getProjectUserInfo(@RequestParam String userId){
		ProjectUserVo vo = new ProjectUserVo();
		vo.setUserId(userId);
		List<ElderProjectUser> list = elderProjectUserService.list(Wrappers.lambdaQuery(ElderProjectUser.class).eq(ElderProjectUser::getUserId, userId));
		if (CollectionUtil.isNotEmpty(list)){
			List<String> projectIds = list.stream().map(ElderProjectUser::getProjectId).collect(Collectors.toList());
			vo.setProjectIds(String.join(",",projectIds));
		}
		return Result.OK(vo);
	}


}
