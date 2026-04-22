package com.joydigit.seniorcaring.mvp.controller;

import java.util.*;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.query.QueryRuleEnum;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.common.util.oConvertUtils;
import com.joydigit.seniorcaring.mvp.entity.ElderConsulting;
import com.joydigit.seniorcaring.mvp.service.IElderConsultingService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.config.JeecgBaseConfig;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
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
 * @Description: elder_consulting
 * @Author: jeecg-boot
 * @Date:   2026-04-17
 * @Version: V1.0
 */
@Tag(name="elder_consulting")
@RestController
@RequestMapping("/elderConsulting")
@Slf4j
public class ElderConsultingController extends JeecgController<ElderConsulting, IElderConsultingService> {
	@Autowired
	private IElderConsultingService elderConsultingService;
	@Autowired
	private JeecgBaseConfig jeecgBaseConfig;
	/**
	 * 分页列表查询
	 *
	 * @param elderConsulting
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "elder_consulting-分页列表查询")
	@Operation(summary="elder_consulting-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<ElderConsulting>> queryPageList(ElderConsulting elderConsulting,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		Page<ElderConsulting> page = new Page<ElderConsulting>(pageNo, pageSize);
		IPage<ElderConsulting> pageList = elderConsultingService.pageList(page, elderConsulting);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param elderConsulting
	 * @return
	 */
	@AutoLog(value = "elder_consulting-添加")
	@Operation(summary="elder_consulting-添加")
	@RequiresPermissions("elder_consulting:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody ElderConsulting elderConsulting) {
		LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		elderConsulting.setReceptionistId(sysUser.getId());
		elderConsulting.setReceptionistName(sysUser.getRealname());
		elderConsultingService.save(elderConsulting);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param elderConsulting
	 * @return
	 */
	@AutoLog(value = "elder_consulting-编辑")
	@Operation(summary="elder_consulting-编辑")
	@RequiresPermissions("elder_consulting:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody ElderConsulting elderConsulting) {
		elderConsultingService.updateById(elderConsulting);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "elder_consulting-通过id删除")
	@Operation(summary="elder_consulting-通过id删除")
	@RequiresPermissions("elder_consulting:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		elderConsultingService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "elder_consulting-批量删除")
	@Operation(summary="elder_consulting-批量删除")
	@RequiresPermissions("elder_consulting:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.elderConsultingService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "elder_consulting-通过id查询")
	@Operation(summary="elder_consulting-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<ElderConsulting> queryById(@RequestParam(name="id",required=true) String id) {
		ElderConsulting elderConsulting = elderConsultingService.getById(id);
		if(elderConsulting==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(elderConsulting);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param elderConsulting
    */
    @RequiresPermissions("elder_consulting:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, ElderConsulting elderConsulting) {
		LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		// 过滤选中数据
		String selections = request.getParameter("selections");
		List<String> selectionList = Arrays.asList(selections.split(","));
		List<ElderConsulting> exportList = elderConsultingService.getList(elderConsulting,selectionList);
		// Step.3 AutoPoi 导出Excel
		ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
		//此处设置的filename无效 ,前端会重更新设置一下
		String title = "咨询接待";
		mv.addObject(NormalExcelConstants.FILE_NAME, title);
		mv.addObject(NormalExcelConstants.CLASS, ElderConsulting.class);
		//update-begin--Author:liusq  Date:20210126 for：图片导出报错，ImageBasePath未设置--------------------
		ExportParams exportParams=new ExportParams(title + "报表", "导出人:" + sysUser.getRealname(), title);
		exportParams.setImageBasePath(jeecgBaseConfig.getPath().getUpload());
		//update-end--Author:liusq  Date:20210126 for：图片导出报错，ImageBasePath未设置----------------------
		mv.addObject(NormalExcelConstants.PARAMS,exportParams);
		mv.addObject(NormalExcelConstants.DATA_LIST, exportList);
		return mv;
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("elder_consulting:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, ElderConsulting.class);
    }

}
