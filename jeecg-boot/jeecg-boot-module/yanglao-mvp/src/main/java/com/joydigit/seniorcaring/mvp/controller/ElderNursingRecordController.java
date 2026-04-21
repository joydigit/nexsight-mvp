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
import com.joydigit.seniorcaring.mvp.entity.ElderNursingRecord;
import com.joydigit.seniorcaring.mvp.service.IElderNursingRecordService;

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
 * @Description: elder_nursing_record
 * @Author: jeecg-boot
 * @Date:   2026-04-17
 * @Version: V1.0
 */
@Tag(name="elder_nursing_record")
@RestController
@RequestMapping("/elderNursingRecord")
@Slf4j
public class ElderNursingRecordController extends JeecgController<ElderNursingRecord, IElderNursingRecordService> {
	@Autowired
	private IElderNursingRecordService elderNursingRecordService;
	
	/**
	 * 分页列表查询
	 *
	 * @param elderNursingRecord
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "elder_nursing_record-分页列表查询")
	@Operation(summary="elder_nursing_record-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<ElderNursingRecord>> queryPageList(ElderNursingRecord elderNursingRecord,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {


        QueryWrapper<ElderNursingRecord> queryWrapper = QueryGenerator.initQueryWrapper(elderNursingRecord, req.getParameterMap());
		Page<ElderNursingRecord> page = new Page<ElderNursingRecord>(pageNo, pageSize);
		IPage<ElderNursingRecord> pageList = elderNursingRecordService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param elderNursingRecord
	 * @return
	 */
	@AutoLog(value = "elder_nursing_record-添加")
	@Operation(summary="elder_nursing_record-添加")
	@RequiresPermissions("elder_nursing_record:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody ElderNursingRecord elderNursingRecord) {
		elderNursingRecordService.save(elderNursingRecord);

		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param elderNursingRecord
	 * @return
	 */
	@AutoLog(value = "elder_nursing_record-编辑")
	@Operation(summary="elder_nursing_record-编辑")
	@RequiresPermissions("elder_nursing_record:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody ElderNursingRecord elderNursingRecord) {
		elderNursingRecordService.updateById(elderNursingRecord);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "elder_nursing_record-通过id删除")
	@Operation(summary="elder_nursing_record-通过id删除")
	@RequiresPermissions("elder_nursing_record:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		elderNursingRecordService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "elder_nursing_record-批量删除")
	@Operation(summary="elder_nursing_record-批量删除")
	@RequiresPermissions("elder_nursing_record:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.elderNursingRecordService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "elder_nursing_record-通过id查询")
	@Operation(summary="elder_nursing_record-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<ElderNursingRecord> queryById(@RequestParam(name="id",required=true) String id) {
		ElderNursingRecord elderNursingRecord = elderNursingRecordService.getById(id);
		if(elderNursingRecord==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(elderNursingRecord);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param elderNursingRecord
    */
    @RequiresPermissions("elder_nursing_record:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, ElderNursingRecord elderNursingRecord) {
        return super.exportXls(request, elderNursingRecord, ElderNursingRecord.class, "elder_nursing_record");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("elder_nursing_record:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, ElderNursingRecord.class);
    }

}
