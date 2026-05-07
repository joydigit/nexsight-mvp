package com.joydigit.seniorcaring.mvp.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.query.QueryRuleEnum;
import org.jeecg.common.util.oConvertUtils;
import com.joydigit.seniorcaring.mvp.entity.ElderCustomerAccount;
import com.joydigit.seniorcaring.mvp.service.IElderCustomerAccountService;

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
 * @Description: elder_customer_account
 * @Author: jeecg-boot
 * @Date:   2026-04-17
 * @Version: V1.0
 */
@Tag(name="elder_customer_account")
@RestController
@RequestMapping("/elderCustomerAccount")
@Slf4j
public class ElderCustomerAccountController extends JeecgController<ElderCustomerAccount, IElderCustomerAccountService> {
	@Autowired
	private IElderCustomerAccountService elderCustomerAccountService;
	
	/**
	 * 分页列表查询
	 *
	 * @param elderCustomerAccount
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "elder_customer_account-分页列表查询")
	@Operation(summary="elder_customer_account-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<ElderCustomerAccount>> queryPageList(ElderCustomerAccount elderCustomerAccount,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {


        QueryWrapper<ElderCustomerAccount> queryWrapper = QueryGenerator.initQueryWrapper(elderCustomerAccount, req.getParameterMap());
		Page<ElderCustomerAccount> page = new Page<ElderCustomerAccount>(pageNo, pageSize);
		IPage<ElderCustomerAccount> pageList = elderCustomerAccountService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	 /**
	  * 查询客户账户
	  *
	  * @param customerId
	  * @return
	  */
	 @Operation(summary="查询客户账户")
	 @GetMapping(value = "/getListByCustomerId")
	 public Result<List<ElderCustomerAccount>> getListByCustomerId(@RequestParam String customerId) {
		 List<ElderCustomerAccount> pageList = elderCustomerAccountService
				 .list(Wrappers.lambdaQuery(ElderCustomerAccount.class)
						 .eq(ElderCustomerAccount::getCustomerId,customerId)
						 .orderByDesc(ElderCustomerAccount::getAccountTypeCode));
		 return Result.OK(pageList);
	 }
	/**
	 *   添加
	 *
	 * @param elderCustomerAccount
	 * @return
	 */
	@AutoLog(value = "elder_customer_account-添加")
	@Operation(summary="elder_customer_account-添加")
	@RequiresPermissions("elder_customer_account:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody ElderCustomerAccount elderCustomerAccount) {
		elderCustomerAccountService.save(elderCustomerAccount);

		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param elderCustomerAccount
	 * @return
	 */
	@AutoLog(value = "elder_customer_account-编辑")
	@Operation(summary="elder_customer_account-编辑")
	@RequiresPermissions("elder_customer_account:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody ElderCustomerAccount elderCustomerAccount) {
		elderCustomerAccountService.updateById(elderCustomerAccount);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "elder_customer_account-通过id删除")
	@Operation(summary="elder_customer_account-通过id删除")
	@RequiresPermissions("elder_customer_account:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		elderCustomerAccountService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "elder_customer_account-批量删除")
	@Operation(summary="elder_customer_account-批量删除")
	@RequiresPermissions("elder_customer_account:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.elderCustomerAccountService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "elder_customer_account-通过id查询")
	@Operation(summary="elder_customer_account-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<ElderCustomerAccount> queryById(@RequestParam(name="id",required=true) String id) {
		ElderCustomerAccount elderCustomerAccount = elderCustomerAccountService.getById(id);
		if(elderCustomerAccount==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(elderCustomerAccount);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param elderCustomerAccount
    */
    @RequiresPermissions("elder_customer_account:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, ElderCustomerAccount elderCustomerAccount) {
        return super.exportXls(request, elderCustomerAccount, ElderCustomerAccount.class, "elder_customer_account");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("elder_customer_account:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, ElderCustomerAccount.class);
    }

}
