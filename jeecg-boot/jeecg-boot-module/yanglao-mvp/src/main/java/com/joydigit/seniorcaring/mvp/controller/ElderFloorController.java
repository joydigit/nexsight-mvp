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
import com.joydigit.seniorcaring.mvp.entity.ElderFloor;
import com.joydigit.seniorcaring.mvp.service.IElderFloorService;

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
 * @Description: elder_floor
 * @Author: jeecg-boot
 * @Date:   2026-04-17
 * @Version: V1.0
 */
@Tag(name="elder_floor")
@RestController
@RequestMapping("/elderFloor")
@Slf4j
public class ElderFloorController extends JeecgController<ElderFloor, IElderFloorService> {
	@Autowired
	private IElderFloorService elderFloorService;
	
	/**
	 * 分页列表查询
	 *
	 * @param elderFloor
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "elder_floor-分页列表查询")
	@Operation(summary="elder_floor-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<ElderFloor>> queryPageList(ElderFloor elderFloor,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {

		Page<ElderFloor> page = new Page<ElderFloor>(pageNo, pageSize);
		IPage<ElderFloor> pageList = elderFloorService.pageList(page, elderFloor);
		return Result.OK(pageList);
	}
	@GetMapping("/getFloorListByBuildingId")
	 public Result<List<ElderFloor>> getFloorListByBuildingId(@RequestParam String buildingId){
		return Result.OK(elderFloorService.getFloorListByBuildingId(buildingId));
	 }
	/**
	 *   添加
	 *
	 * @param elderFloor
	 * @return
	 */
	@AutoLog(value = "elder_floor-添加")
	@Operation(summary="elder_floor-添加")
	@RequiresPermissions("elder_floor:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody ElderFloor elderFloor) {
		elderFloorService.save(elderFloor);

		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param elderFloor
	 * @return
	 */
	@AutoLog(value = "elder_floor-编辑")
	@Operation(summary="elder_floor-编辑")
	@RequiresPermissions("elder_floor:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody ElderFloor elderFloor) {
		elderFloorService.updateById(elderFloor);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "elder_floor-通过id删除")
	@Operation(summary="elder_floor-通过id删除")
	@RequiresPermissions("elder_floor:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		elderFloorService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "elder_floor-批量删除")
	@Operation(summary="elder_floor-批量删除")
	@RequiresPermissions("elder_floor:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.elderFloorService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "elder_floor-通过id查询")
	@Operation(summary="elder_floor-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<ElderFloor> queryById(@RequestParam(name="id",required=true) String id) {
		ElderFloor elderFloor = elderFloorService.getById(id);
		if(elderFloor==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(elderFloor);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param elderFloor
    */
    @RequiresPermissions("elder_floor:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, ElderFloor elderFloor) {
        return super.exportXls(request, elderFloor, ElderFloor.class, "elder_floor");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("elder_floor:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, ElderFloor.class);
    }

}
