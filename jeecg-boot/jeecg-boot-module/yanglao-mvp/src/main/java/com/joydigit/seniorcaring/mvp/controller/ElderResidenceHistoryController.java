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
import com.joydigit.seniorcaring.mvp.entity.ElderResidenceHistory;
import com.joydigit.seniorcaring.mvp.service.IElderResidenceHistoryService;

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
 * @Description: elder_residence_history
 * @Author: jeecg-boot
 * @Date:   2026-04-17
 * @Version: V1.0
 */
@Tag(name="elder_residence_history")
@RestController
@RequestMapping("/elderResidenceHistory")
@Slf4j
public class ElderResidenceHistoryController extends JeecgController<ElderResidenceHistory, IElderResidenceHistoryService> {
	@Autowired
	private IElderResidenceHistoryService elderResidenceHistoryService;
	
	/**
	 * 分页列表查询
	 *
	 * @param elderResidenceHistory
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "elder_residence_history-分页列表查询")
	@Operation(summary="elder_residence_history-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<ElderResidenceHistory>> queryPageList(ElderResidenceHistory elderResidenceHistory,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {


        QueryWrapper<ElderResidenceHistory> queryWrapper = QueryGenerator.initQueryWrapper(elderResidenceHistory, req.getParameterMap());
		Page<ElderResidenceHistory> page = new Page<ElderResidenceHistory>(pageNo, pageSize);
		IPage<ElderResidenceHistory> pageList = elderResidenceHistoryService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param elderResidenceHistory
	 * @return
	 */
	@AutoLog(value = "elder_residence_history-添加")
	@Operation(summary="elder_residence_history-添加")
	@RequiresPermissions("elder_residence_history:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody ElderResidenceHistory elderResidenceHistory) {
		elderResidenceHistoryService.save(elderResidenceHistory);

		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param elderResidenceHistory
	 * @return
	 */
	@AutoLog(value = "elder_residence_history-编辑")
	@Operation(summary="elder_residence_history-编辑")
	@RequiresPermissions("elder_residence_history:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody ElderResidenceHistory elderResidenceHistory) {
		elderResidenceHistoryService.updateById(elderResidenceHistory);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "elder_residence_history-通过id删除")
	@Operation(summary="elder_residence_history-通过id删除")
	@RequiresPermissions("elder_residence_history:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		elderResidenceHistoryService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "elder_residence_history-批量删除")
	@Operation(summary="elder_residence_history-批量删除")
	@RequiresPermissions("elder_residence_history:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.elderResidenceHistoryService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "elder_residence_history-通过id查询")
	@Operation(summary="elder_residence_history-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<ElderResidenceHistory> queryById(@RequestParam(name="id",required=true) String id) {
		ElderResidenceHistory elderResidenceHistory = elderResidenceHistoryService.getById(id);
		if(elderResidenceHistory==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(elderResidenceHistory);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param elderResidenceHistory
    */
    @RequiresPermissions("elder_residence_history:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, ElderResidenceHistory elderResidenceHistory) {
        return super.exportXls(request, elderResidenceHistory, ElderResidenceHistory.class, "elder_residence_history");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("elder_residence_history:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, ElderResidenceHistory.class);
    }

}
