package com.joydigit.seniorcaring.mvp.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
	 * 分页列表查询
	 *
	 * @param elderProjectUser
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "elder_project_user-分页列表查询")
	@Operation(summary="elder_project_user-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<ElderProjectUser>> queryPageList(ElderProjectUser elderProjectUser,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {


        QueryWrapper<ElderProjectUser> queryWrapper = QueryGenerator.initQueryWrapper(elderProjectUser, req.getParameterMap());
		Page<ElderProjectUser> page = new Page<ElderProjectUser>(pageNo, pageSize);
		IPage<ElderProjectUser> pageList = elderProjectUserService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param elderProjectUser
	 * @return
	 */
	@AutoLog(value = "elder_project_user-添加")
	@Operation(summary="elder_project_user-添加")
	@RequiresPermissions("elder_project_user:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody ElderProjectUser elderProjectUser) {
		elderProjectUserService.save(elderProjectUser);

		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param elderProjectUser
	 * @return
	 */
	@AutoLog(value = "elder_project_user-编辑")
	@Operation(summary="elder_project_user-编辑")
	@RequiresPermissions("elder_project_user:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody ElderProjectUser elderProjectUser) {
		elderProjectUserService.updateById(elderProjectUser);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "elder_project_user-通过id删除")
	@Operation(summary="elder_project_user-通过id删除")
	@RequiresPermissions("elder_project_user:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		elderProjectUserService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "elder_project_user-批量删除")
	@Operation(summary="elder_project_user-批量删除")
	@RequiresPermissions("elder_project_user:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.elderProjectUserService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "elder_project_user-通过id查询")
	@Operation(summary="elder_project_user-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<ElderProjectUser> queryById(@RequestParam(name="id",required=true) String id) {
		ElderProjectUser elderProjectUser = elderProjectUserService.getById(id);
		if(elderProjectUser==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(elderProjectUser);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param elderProjectUser
    */
    @RequiresPermissions("elder_project_user:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, ElderProjectUser elderProjectUser) {
        return super.exportXls(request, elderProjectUser, ElderProjectUser.class, "elder_project_user");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("elder_project_user:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, ElderProjectUser.class);
    }

}
