package com.joydigit.seniorcaring.mvp.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import com.joydigit.seniorcaring.mvp.vo.CustomerSelectVo;
import com.joydigit.seniorcaring.mvp.vo.ElderCustomerVo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.query.QueryRuleEnum;
import org.jeecg.common.util.oConvertUtils;
import com.joydigit.seniorcaring.mvp.entity.ElderCustomer;
import com.joydigit.seniorcaring.mvp.service.IElderCustomerService;

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
 * @Description: elder_customer
 * @Author: jeecg-boot
 * @Date:   2026-04-17
 * @Version: V1.0
 */
@Tag(name="elder_customer")
@RestController
@RequestMapping("/elderCustomer")
@Slf4j
public class ElderCustomerController extends JeecgController<ElderCustomer, IElderCustomerService> {
	@Autowired
	private IElderCustomerService elderCustomerService;
	
	/**
	 * 分页列表查询
	 *
	 * @param elderCustomer
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "elder_customer-分页列表查询")
	@Operation(summary="elder_customer-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<ElderCustomer>> queryPageList(ElderCustomer elderCustomer,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		Page<ElderCustomer> page = new Page<ElderCustomer>(pageNo, pageSize);
		IPage<ElderCustomer> pageList = elderCustomerService.pageList(page, elderCustomer);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param elderCustomer
	 * @return
	 */
	@AutoLog(value = "elder_customer-添加")
	@Operation(summary="elder_customer-添加")
	@RequiresPermissions("elder_customer:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody ElderCustomer elderCustomer) {
		return elderCustomerService.saveInfo(elderCustomer);
	}
	
	/**
	 *  编辑
	 *
	 * @param elderCustomer
	 * @return
	 */
	@AutoLog(value = "elder_customer-编辑")
	@Operation(summary="elder_customer-编辑")
	@RequiresPermissions("elder_customer:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody ElderCustomer elderCustomer) {
		return elderCustomerService.updateInfo(elderCustomer);
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "elder_customer-通过id删除")
	@Operation(summary="elder_customer-通过id删除")
	@RequiresPermissions("elder_customer:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		elderCustomerService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "elder_customer-批量删除")
	@Operation(summary="elder_customer-批量删除")
	@RequiresPermissions("elder_customer:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.elderCustomerService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "elder_customer-通过id查询")
	@Operation(summary="elder_customer-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<ElderCustomer> queryById(@RequestParam(name="id",required=true) String id) {
		ElderCustomer elderCustomer = elderCustomerService.getById(id);
		if(elderCustomer==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(elderCustomer);
	}

	 /**
	  * 通过项目id查询客户列表
	  *
	  * @param projectId
	  * @return
	  */
	 @Operation(summary="通过项目id查询客户列表")
	 @GetMapping(value = "/getCustomerListByProjectId")
	 public Result<List<CustomerSelectVo>> getCustomerListByProjectId(@RequestParam String projectId) {
		 return elderCustomerService.getCustomerListByProjectId(projectId);
	 }

	 /**
	  * 通过id查询
	  *
	  * @param id
	  * @return
	  */
	 //@AutoLog(value = "elder_customer-通过id查询")
	 @Operation(summary="查询客户最新状态信息")
	 @GetMapping(value = "/getCustomerLastInfo")
	 public Result<ElderCustomerVo> getCustomerLastInfo(@RequestParam(name="id",required=true) String id) {
		 return elderCustomerService.getCustomerLastInfo(id);
	 }
    /**
    * 导出excel
    *
    * @param request
    * @param elderCustomer
    */
    @RequiresPermissions("elder_customer:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, ElderCustomer elderCustomer) {
        return super.exportXls(request, elderCustomer, ElderCustomer.class, "elder_customer");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("elder_customer:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, ElderCustomer.class);
    }

}
