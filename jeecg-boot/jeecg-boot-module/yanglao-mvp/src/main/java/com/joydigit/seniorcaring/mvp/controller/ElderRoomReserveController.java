package com.joydigit.seniorcaring.mvp.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.joydigit.seniorcaring.mvp.entity.ElderRoomReserve;
import com.joydigit.seniorcaring.mvp.service.IElderRoomReserveService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.common.system.query.QueryGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
 /**
 * @Description: 房间预定
 * @Author: jeecg-boot
 * @Date:   2026-04-28
 * @Version: V1.0
 */
@Tag(name="房间预定")
@RestController
@RequestMapping("/com/joydigit/seniorcaring/mvp/elderRoomReserve")
@Slf4j
public class ElderRoomReserveController extends JeecgController<ElderRoomReserve, IElderRoomReserveService> {
	@Autowired
	private IElderRoomReserveService elderRoomReserveService;
	
	/**
	 * 分页列表查询
	 *
	 * @param elderRoomReserve
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "房间预定-分页列表查询")
	@Operation(summary="房间预定-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<ElderRoomReserve>> queryPageList(ElderRoomReserve elderRoomReserve,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {

		Page<ElderRoomReserve> page = new Page<ElderRoomReserve>(pageNo, pageSize);
		IPage<ElderRoomReserve> pageList = elderRoomReserveService.pageList(page, elderRoomReserve);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param elderRoomReserve
	 * @return
	 */
	@AutoLog(value = "房间预定-添加")
	@Operation(summary="房间预定-添加")
	@RequiresPermissions("elder_room_reserve:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody ElderRoomReserve elderRoomReserve) {
		return elderRoomReserveService.saveInfo(elderRoomReserve);
	}
	
	/**
	 *  编辑
	 *
	 * @param elderRoomReserve
	 * @return
	 */
	@AutoLog(value = "取消预定")
	@Operation(summary="取消预定")
	@RequiresPermissions("elder_room_reserve:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody ElderRoomReserve elderRoomReserve) {
		return elderRoomReserveService.updateInfo(elderRoomReserve);
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "房间预定-通过id删除")
	@Operation(summary="房间预定-通过id删除")
	@RequiresPermissions("elder_room_reserve:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		return elderRoomReserveService.delete(id);
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "房间预定-批量删除")
	@Operation(summary="房间预定-批量删除")
	@RequiresPermissions("elder_room_reserve:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		for (String id : Arrays.asList(ids.split(","))) {
			elderRoomReserveService.delete(id);
		}
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "房间预定-通过id查询")
	@Operation(summary="房间预定-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<ElderRoomReserve> queryById(@RequestParam(name="id",required=true) String id) {
		ElderRoomReserve elderRoomReserve = elderRoomReserveService.getById(id);
		if(elderRoomReserve==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(elderRoomReserve);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param elderRoomReserve
    */
    @RequiresPermissions("elder_room_reserve:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, ElderRoomReserve elderRoomReserve) {
        return super.exportXls(request, elderRoomReserve, ElderRoomReserve.class, "房间预定");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("elder_room_reserve:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, ElderRoomReserve.class);
    }

}
