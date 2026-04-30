package com.joydigit.seniorcaring.mvp.controller;

import java.util.*;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.joydigit.seniorcaring.mvp.entity.ElderRoom;
import com.joydigit.seniorcaring.mvp.enums.RoomStatusEnum;
import com.joydigit.seniorcaring.mvp.service.IElderRoomService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.query.QueryRuleEnum;
import org.jeecg.common.util.oConvertUtils;
import com.joydigit.seniorcaring.mvp.entity.ElderBed;
import com.joydigit.seniorcaring.mvp.service.IElderBedService;

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
 * @Description: elder_bed
 * @Author: jeecg-boot
 * @Date:   2026-04-17
 * @Version: V1.0
 */
@Tag(name="elder_bed")
@RestController
@RequestMapping("/elderBed")
@Slf4j
public class ElderBedController extends JeecgController<ElderBed, IElderBedService> {
	@Autowired
	private IElderBedService elderBedService;
	@Autowired
	private IElderRoomService elderRoomService;
	
	/**
	 * 分页列表查询
	 *
	 * @param elderBed
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "elder_bed-分页列表查询")
	@Operation(summary="elder_bed-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<ElderBed>> queryPageList(ElderBed elderBed,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {

		Page<ElderBed> page = new Page<ElderBed>(pageNo, pageSize);
		IPage<ElderBed> pageList = elderBedService.pageList(page, elderBed);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param elderBed
	 * @return
	 */
	@AutoLog(value = "elder_bed-添加")
	@Operation(summary="elder_bed-添加")
	@RequiresPermissions("elder_bed:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody ElderBed elderBed) {
		elderBedService.save(elderBed);

		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param elderBed
	 * @return
	 */
	@AutoLog(value = "elder_bed-编辑")
	@Operation(summary="elder_bed-编辑")
	@RequiresPermissions("elder_bed:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody ElderBed elderBed) {
		if (elderBed.getStatus().equals(RoomStatusEnum.RESERVED.getKey()) ||
				elderBed.getStatus().equals(RoomStatusEnum.OCCUPIED.getKey())){
			return Result.error("床位占用，不能修改");
		}
		elderBedService.updateById(elderBed);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "elder_bed-通过id删除")
	@Operation(summary="elder_bed-通过id删除")
	@RequiresPermissions("elder_bed:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		ElderBed elderBed = elderBedService.getById(id);
		if (elderBed.getStatus().equals(RoomStatusEnum.RESERVED.getKey()) ||
				elderBed.getStatus().equals(RoomStatusEnum.OCCUPIED.getKey())){
			return Result.error("床位占用，不能删除");
		}
		elderBedService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "elder_bed-批量删除")
	@Operation(summary="elder_bed-批量删除")
	@RequiresPermissions("elder_bed:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		List<String> idList = Arrays.asList(ids.split(","));
		List<ElderBed> list = elderBedService.list(Wrappers.lambdaQuery(ElderBed.class).in(ElderBed::getId, idList));
		if (CollectionUtil.isNotEmpty(list)){
			for (ElderBed elderBed : list) {
				if (elderBed.getStatus().equals(RoomStatusEnum.RESERVED.getKey()) ||
						elderBed.getStatus().equals(RoomStatusEnum.OCCUPIED.getKey())){
					return Result.error("床位占用，不能删除");
				}
			}
		}
		this.elderBedService.removeByIds(idList);
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "elder_bed-通过id查询")
	@Operation(summary="elder_bed-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<ElderBed> queryById(@RequestParam(name="id",required=true) String id) {
		ElderBed elderBed = elderBedService.getById(id);
		if(elderBed==null) {
			return Result.error("未找到对应数据");
		}
		ElderRoom room = elderRoomService.getById(elderBed.getRoomId());
		if (Objects.nonNull(room)){
			elderBed.setBuildingId(room.getBuildingId());
			elderBed.setFloorId(room.getFloorId());
		}
		return Result.OK(elderBed);
	}

	 @Operation(summary="通过房间id查询床位列表")
	 @GetMapping(value = "/getBedListByRoomId")
	 public Result<List<ElderBed>> getBedListByRoomId(@RequestParam String roomId) {
		 List<ElderBed> list = elderBedService.list(Wrappers.lambdaQuery(ElderBed.class)
				 .eq(ElderBed::getRoomId, roomId));
		 return Result.OK(list);
	 }
    /**
    * 导出excel
    *
    * @param request
    * @param elderBed
    */
    @RequiresPermissions("elder_bed:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, ElderBed elderBed) {
        return super.exportXls(request, elderBed, ElderBed.class, "elder_bed");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("elder_bed:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, ElderBed.class);
    }

}
