package com.joydigit.seniorcaring.mvp.controller;

import java.util.Arrays;
import java.util.List;

import com.joydigit.seniorcaring.mvp.vo.FloorDataVo;
import com.joydigit.seniorcaring.mvp.vo.RoomCascaderVo;
import com.joydigit.seniorcaring.mvp.vo.RoomSelectVo;
import com.joydigit.seniorcaring.mvp.vo.RoomStatusQueryVo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import com.joydigit.seniorcaring.mvp.entity.ElderRoom;
import com.joydigit.seniorcaring.mvp.service.IElderRoomService;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.apache.shiro.authz.annotation.RequiresPermissions;
 /**
 * @Description: elder_room
 * @Author: jeecg-boot
 * @Date:   2026-04-17
 * @Version: V1.0
 */
@Tag(name="elder_room")
@RestController
@RequestMapping("/elderRoom")
@Slf4j
public class ElderRoomController extends JeecgController<ElderRoom, IElderRoomService> {
	@Autowired
	private IElderRoomService elderRoomService;
	
	/**
	 * 分页列表查询
	 *
	 * @param elderRoom
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "elder_room-分页列表查询")
	@Operation(summary="elder_room-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<ElderRoom>> queryPageList(ElderRoom elderRoom,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {

		Page<ElderRoom> page = new Page<ElderRoom>(pageNo, pageSize);
		IPage<ElderRoom> pageList = elderRoomService.pageList(page, elderRoom);
		return Result.OK(pageList);
	}
	 /**
	  * 分页列表查询
	  *
	  * @param roomStatusQueryVo
	  * @param pageNo
	  * @param pageSize
	  * @param req
	  * @return
	  */
	 @Operation(summary="房态-分页列表查询")
	 @GetMapping(value = "/getRoomStatusPageList")
	 public Result<IPage<FloorDataVo>> getRoomStatusPageList(RoomStatusQueryVo roomStatusQueryVo,
															 @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
															 @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
															 HttpServletRequest req) {

		 IPage<FloorDataVo> pageList = elderRoomService.getRoomStatusPageList(pageNo,pageSize, roomStatusQueryVo);
		 return Result.OK(pageList);
	 }
	@Operation(summary="elder_room-查询楼层下的房间")
	@GetMapping("/getRoomListByFloorId")
	public Result<List<ElderRoom>> getRoomListByFloorId(@RequestParam String floorId){
		 List<ElderRoom> list = elderRoomService.getRoomListByFloorId(floorId);
		 return Result.OK(list);
	 }

	 @Operation(summary="elder_room-查询项目下的房间")
	 @GetMapping("/getRoomListByProjectId")
	 public Result<List<RoomSelectVo>> getRoomListByProjectId(@RequestParam String projectId){
		 List<RoomSelectVo> list = elderRoomService.getRoomListByProjectId(projectId);
		 return Result.OK(list);
	 }

	 @Operation(summary="elder_room-查询楼层下的房间或床位")
	 @GetMapping("/getRoomCascaderList")
	 public Result<List<RoomCascaderVo>> getRoomCascaderList(@RequestParam String type,@RequestParam String projectId){
		 List<RoomCascaderVo> roomCascaderList = elderRoomService.getRoomCascaderList(type, projectId);
		 return Result.OK(roomCascaderList);
	 }
	/**
	 *   添加
	 *
	 * @param elderRoom
	 * @return
	 */
	@AutoLog(value = "elder_room-添加")
	@Operation(summary="elder_room-添加")
	@RequiresPermissions("elder_room:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody ElderRoom elderRoom) {
		elderRoomService.save(elderRoom);

		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param elderRoom
	 * @return
	 */
	@AutoLog(value = "elder_room-编辑")
	@Operation(summary="elder_room-编辑")
	@RequiresPermissions("elder_room:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody ElderRoom elderRoom) {
		elderRoomService.updateById(elderRoom);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "elder_room-通过id删除")
	@Operation(summary="elder_room-通过id删除")
	@RequiresPermissions("elder_room:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		elderRoomService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "elder_room-批量删除")
	@Operation(summary="elder_room-批量删除")
	@RequiresPermissions("elder_room:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.elderRoomService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "elder_room-通过id查询")
	@Operation(summary="elder_room-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<ElderRoom> queryById(@RequestParam(name="id",required=true) String id) {
		ElderRoom elderRoom = elderRoomService.getById(id);
		if(elderRoom==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(elderRoom);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param elderRoom
    */
    @RequiresPermissions("elder_room:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, ElderRoom elderRoom) {
        return super.exportXls(request, elderRoom, ElderRoom.class, "elder_room");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("elder_room:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, ElderRoom.class);
    }

}
