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
import com.joydigit.seniorcaring.mvp.entity.ElderBill;
import com.joydigit.seniorcaring.mvp.service.IElderBillService;

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
 * @Description: elder_bill
 * @Author: jeecg-boot
 * @Date:   2026-04-17
 * @Version: V1.0
 */
@Tag(name="elder_bill")
@RestController
@RequestMapping("/elderBill")
@Slf4j
public class ElderBillController extends JeecgController<ElderBill, IElderBillService> {
	@Autowired
	private IElderBillService elderBillService;
	
	/**
	 * 分页列表查询
	 *
	 * @param elderBill
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "elder_bill-分页列表查询")
	@Operation(summary="elder_bill-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<ElderBill>> queryPageList(ElderBill elderBill,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {


        QueryWrapper<ElderBill> queryWrapper = QueryGenerator.initQueryWrapper(elderBill, req.getParameterMap());
		Page<ElderBill> page = new Page<ElderBill>(pageNo, pageSize);
		IPage<ElderBill> pageList = elderBillService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param elderBill
	 * @return
	 */
	@AutoLog(value = "elder_bill-添加")
	@Operation(summary="elder_bill-添加")
	@RequiresPermissions("elder_bill:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody ElderBill elderBill) {
		elderBillService.save(elderBill);

		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param elderBill
	 * @return
	 */
	@AutoLog(value = "elder_bill-编辑")
	@Operation(summary="elder_bill-编辑")
	@RequiresPermissions("elder_bill:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody ElderBill elderBill) {
		elderBillService.updateById(elderBill);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "elder_bill-通过id删除")
	@Operation(summary="elder_bill-通过id删除")
	@RequiresPermissions("elder_bill:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		elderBillService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "elder_bill-批量删除")
	@Operation(summary="elder_bill-批量删除")
	@RequiresPermissions("elder_bill:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.elderBillService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "elder_bill-通过id查询")
	@Operation(summary="elder_bill-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<ElderBill> queryById(@RequestParam(name="id",required=true) String id) {
		ElderBill elderBill = elderBillService.getById(id);
		if(elderBill==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(elderBill);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param elderBill
    */
    @RequiresPermissions("elder_bill:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, ElderBill elderBill) {
        return super.exportXls(request, elderBill, ElderBill.class, "elder_bill");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("elder_bill:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, ElderBill.class);
    }

}
