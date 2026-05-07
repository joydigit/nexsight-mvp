package com.joydigit.seniorcaring.mvp.controller;

import java.util.*;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import com.joydigit.seniorcaring.mvp.entity.ElderConsulting;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.query.QueryRuleEnum;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.common.util.oConvertUtils;
import com.joydigit.seniorcaring.mvp.entity.ElderCustomerPayment;
import com.joydigit.seniorcaring.mvp.service.IElderCustomerPaymentService;

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
 * @Description: elder_customer_payment
 * @Author: jeecg-boot
 * @Date:   2026-04-17
 * @Version: V1.0
 */
@Tag(name="elder_customer_payment")
@RestController
@RequestMapping("/elderCustomerPayment")
@Slf4j
public class ElderCustomerPaymentController extends JeecgController<ElderCustomerPayment, IElderCustomerPaymentService> {
	@Autowired
	private IElderCustomerPaymentService elderCustomerPaymentService;
	 @Autowired
	 private JeecgBaseConfig jeecgBaseConfig;
	
	/**
	 * 分页列表查询
	 *
	 * @param elderCustomerPayment
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "elder_customer_payment-分页列表查询")
	@Operation(summary="elder_customer_payment-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<ElderCustomerPayment>> queryPageList(ElderCustomerPayment elderCustomerPayment,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		Page<ElderCustomerPayment> page = new Page<ElderCustomerPayment>(pageNo, pageSize);
		IPage<ElderCustomerPayment> pageList = elderCustomerPaymentService.pageList(page, elderCustomerPayment);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param elderCustomerPayment
	 * @return
	 */
	@AutoLog(value = "elder_customer_payment-添加")
	@Operation(summary="elder_customer_payment-添加")
	@RequiresPermissions("elder_customer_payment:custAdd")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody ElderCustomerPayment elderCustomerPayment) {
		return elderCustomerPaymentService.saveInfo(elderCustomerPayment);
	}

	 /**
	  *  确认流水
	  *
	  * @param id
	  * @return
	  */
	 @AutoLog(value = "elder_customer_payment-添加")
	 @Operation(summary="elder_customer_payment-添加")
	 @RequiresPermissions("elder_customer_payment:confirm")
	 @PostMapping(value = "/confirm")
	 public Result<String> confirm(@RequestParam(name="ids",required=true) String ids) throws Exception {
		 return elderCustomerPaymentService.confirm(ids);
	 }
	/**
	 *  编辑
	 *
	 * @param elderCustomerPayment
	 * @return
	 */
	@AutoLog(value = "elder_customer_payment-编辑")
	@Operation(summary="elder_customer_payment-编辑")
	@RequiresPermissions("elder_customer_payment:custedit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody ElderCustomerPayment elderCustomerPayment) {
		return elderCustomerPaymentService.updateInfo(elderCustomerPayment);
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "elder_customer_payment-通过id删除")
	@Operation(summary="elder_customer_payment-通过id删除")
	@RequiresPermissions("elder_customer_payment:custdelete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		return elderCustomerPaymentService.removeInfo(id);
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "elder_customer_payment-通过id查询")
	@Operation(summary="elder_customer_payment-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<ElderCustomerPayment> queryById(@RequestParam(name="id",required=true) String id) {
		ElderCustomerPayment elderCustomerPayment = elderCustomerPaymentService.getById(id);
		if(elderCustomerPayment==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(elderCustomerPayment);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param elderCustomerPayment
    */
    @RequiresPermissions("elder_customer_payment:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, ElderCustomerPayment elderCustomerPayment) {
		LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		// 过滤选中数据
		String selections = request.getParameter("selections");
		List<String> selectionList = new ArrayList<>();
		if (StringUtils.isNotBlank(selections)){
			selectionList = Arrays.asList(selections.split(","));
		}
		List<ElderCustomerPayment> exportList = elderCustomerPaymentService.getList(elderCustomerPayment,selectionList);
		// Step.3 AutoPoi 导出Excel
		ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
		//此处设置的filename无效 ,前端会重更新设置一下
		String title = "资金流水";
		mv.addObject(NormalExcelConstants.FILE_NAME, title);
		mv.addObject(NormalExcelConstants.CLASS, ElderCustomerPayment.class);
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
    @RequiresPermissions("elder_customer_payment:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, ElderCustomerPayment.class);
    }

}
