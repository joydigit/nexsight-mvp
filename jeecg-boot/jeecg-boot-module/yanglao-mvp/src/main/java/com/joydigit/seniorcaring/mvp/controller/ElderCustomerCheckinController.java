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
import com.joydigit.seniorcaring.mvp.entity.ElderCustomerCheckin;
import com.joydigit.seniorcaring.mvp.service.IElderCustomerCheckinService;

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
 * @Description: elder_customer_checkin
 * @Author: jeecg-boot
 * @Date:   2026-04-17
 * @Version: V1.0
 */
@Tag(name="elder_customer_checkin")
@RestController
@RequestMapping("/elderCustomerCheckin")
@Slf4j
public class ElderCustomerCheckinController extends JeecgController<ElderCustomerCheckin, IElderCustomerCheckinService> {
	@Autowired
	private IElderCustomerCheckinService elderCustomerCheckinService;
	
	/**
	 * 分页列表查询
	 *
	 * @param elderCustomerCheckin
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "elder_customer_checkin-分页列表查询")
	@Operation(summary="elder_customer_checkin-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<ElderCustomerCheckin>> queryPageList(ElderCustomerCheckin elderCustomerCheckin,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {

		Page<ElderCustomerCheckin> page = new Page<ElderCustomerCheckin>(pageNo, pageSize);
		IPage<ElderCustomerCheckin> pageList = elderCustomerCheckinService.pageList(page, elderCustomerCheckin);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param elderCustomerCheckin
	 * @return
	 */
	@AutoLog(value = "elder_customer_checkin-添加")
	@Operation(summary="elder_customer_checkin-添加")
	@RequiresPermissions("elder_customer_checkin:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody ElderCustomerCheckin elderCustomerCheckin) {
		return elderCustomerCheckinService.saveInfo(elderCustomerCheckin);
	}
	
	/**
	 *  编辑
	 *
	 * @param elderCustomerCheckin
	 * @return
	 */
	@AutoLog(value = "elder_customer_checkin-编辑")
	@Operation(summary="elder_customer_checkin-编辑")
	@RequiresPermissions("elder_customer_checkin:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody ElderCustomerCheckin elderCustomerCheckin) {
		return elderCustomerCheckinService.updateInfo(elderCustomerCheckin);
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "elder_customer_checkin-通过id查询")
	@Operation(summary="elder_customer_checkin-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<ElderCustomerCheckin> queryById(@RequestParam(name="id",required=true) String id) {
		ElderCustomerCheckin elderCustomerCheckin = elderCustomerCheckinService.getById(id);
		if(elderCustomerCheckin==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(elderCustomerCheckin);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param elderCustomerCheckin
    */
    @RequiresPermissions("elder_customer_checkin:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, ElderCustomerCheckin elderCustomerCheckin) {
        return super.exportXls(request, elderCustomerCheckin, ElderCustomerCheckin.class, "elder_customer_checkin");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("elder_customer_checkin:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, ElderCustomerCheckin.class);
    }

}
