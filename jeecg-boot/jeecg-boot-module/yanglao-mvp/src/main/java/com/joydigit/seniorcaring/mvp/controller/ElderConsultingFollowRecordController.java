package com.joydigit.seniorcaring.mvp.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import com.joydigit.seniorcaring.mvp.entity.ElderConsulting;
import com.joydigit.seniorcaring.mvp.service.IElderConsultingService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.api.ISysBaseAPI;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.query.QueryRuleEnum;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.common.util.oConvertUtils;
import com.joydigit.seniorcaring.mvp.entity.ElderConsultingFollowRecord;
import com.joydigit.seniorcaring.mvp.service.IElderConsultingFollowRecordService;

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
 * @Description: elder_consulting_follow_record
 * @Author: jeecg-boot
 * @Date:   2026-04-17
 * @Version: V1.0
 */
@Tag(name="elder_consulting_follow_record")
@RestController
@RequestMapping("/elderConsultingFollowRecord")
@Slf4j
public class ElderConsultingFollowRecordController extends JeecgController<ElderConsultingFollowRecord, IElderConsultingFollowRecordService> {
	@Autowired
	private IElderConsultingFollowRecordService elderConsultingFollowRecordService;
	@Autowired
	private IElderConsultingService elderConsultingService;
	@Autowired
	private ISysBaseAPI sysBaseAPI;
	/**
	 * 分页列表查询
	 *
	 * @param elderConsultingFollowRecord
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "elder_consulting_follow_record-分页列表查询")
	@Operation(summary="elder_consulting_follow_record-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<ElderConsultingFollowRecord>> queryPageList(ElderConsultingFollowRecord elderConsultingFollowRecord,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {

		Page<ElderConsultingFollowRecord> page = new Page<ElderConsultingFollowRecord>(pageNo, pageSize);
		IPage<ElderConsultingFollowRecord> pageList = elderConsultingFollowRecordService.pageList(page, elderConsultingFollowRecord);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param elderConsultingFollowRecord
	 * @return
	 */
	@AutoLog(value = "elder_consulting_follow_record-添加")
	@Operation(summary="elder_consulting_follow_record-添加")
	@RequiresPermissions("elder_consulting_follow_record:add")
	@PostMapping(value = "/add")
	@Transactional(rollbackFor = Exception.class)
	public Result<String> add(@RequestBody ElderConsultingFollowRecord elderConsultingFollowRecord) {
		LoginUser user = sysBaseAPI.getUserById(elderConsultingFollowRecord.getReceptionistId());
		elderConsultingFollowRecord.setReceptionistName(user.getRealname());
		elderConsultingFollowRecordService.save(elderConsultingFollowRecord);
		ElderConsulting consulting = new ElderConsulting();
		consulting.setFollowStatus(elderConsultingFollowRecord.getFollowStatus());
		consulting.setId(elderConsultingFollowRecord.getId());
		elderConsultingService.updateById(consulting);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param elderConsultingFollowRecord
	 * @return
	 */
	@AutoLog(value = "elder_consulting_follow_record-编辑")
	@Operation(summary="elder_consulting_follow_record-编辑")
	@RequiresPermissions("elder_consulting_follow_record:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody ElderConsultingFollowRecord elderConsultingFollowRecord) {
		LoginUser user = sysBaseAPI.getUserById(elderConsultingFollowRecord.getReceptionistId());
		elderConsultingFollowRecord.setReceptionistName(user.getRealname());
		elderConsultingFollowRecordService.updateById(elderConsultingFollowRecord);
		ElderConsulting consulting = new ElderConsulting();
		consulting.setFollowStatus(elderConsultingFollowRecord.getFollowStatus());
		consulting.setId(elderConsultingFollowRecord.getId());
		elderConsultingService.updateById(consulting);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "elder_consulting_follow_record-通过id删除")
	@Operation(summary="elder_consulting_follow_record-通过id删除")
	@RequiresPermissions("elder_consulting_follow_record:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		elderConsultingFollowRecordService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "elder_consulting_follow_record-批量删除")
	@Operation(summary="elder_consulting_follow_record-批量删除")
	@RequiresPermissions("elder_consulting_follow_record:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.elderConsultingFollowRecordService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "elder_consulting_follow_record-通过id查询")
	@Operation(summary="elder_consulting_follow_record-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<ElderConsultingFollowRecord> queryById(@RequestParam(name="id",required=true) String id) {
		ElderConsultingFollowRecord elderConsultingFollowRecord = elderConsultingFollowRecordService.getById(id);
		if(elderConsultingFollowRecord==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(elderConsultingFollowRecord);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param elderConsultingFollowRecord
    */
    @RequiresPermissions("elder_consulting_follow_record:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, ElderConsultingFollowRecord elderConsultingFollowRecord) {
        return super.exportXls(request, elderConsultingFollowRecord, ElderConsultingFollowRecord.class, "elder_consulting_follow_record");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("elder_consulting_follow_record:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, ElderConsultingFollowRecord.class);
    }

}
