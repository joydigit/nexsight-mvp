package com.joydigit.seniorcaring.mvp.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.joydigit.seniorcaring.mvp.entity.ElderAssessment;
import com.joydigit.seniorcaring.mvp.service.IElderAssessmentService;
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
 * @Description: elder_assessment
 * @Author: jeecg-boot
 * @Date:   2026-04-17
 * @Version: V1.0
 */
@Tag(name="elder_assessment")
@RestController
@RequestMapping("/elderAssessment")
@Slf4j
public class ElderAssessmentController extends JeecgController<ElderAssessment, IElderAssessmentService> {
	@Autowired
	private IElderAssessmentService elderAssessmentService;
	
	/**
	 * 分页列表查询
	 *
	 * @param elderAssessment
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "elder_assessment-分页列表查询")
	@Operation(summary="elder_assessment-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<ElderAssessment>> queryPageList(ElderAssessment elderAssessment,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {

		Page<ElderAssessment> page = new Page<ElderAssessment>(pageNo, pageSize);
		IPage<ElderAssessment> pageList = elderAssessmentService.pageList(page, elderAssessment);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param elderAssessment
	 * @return
	 */
	@AutoLog(value = "elder_assessment-添加")
	@Operation(summary="elder_assessment-添加")
	@RequiresPermissions("elder_assessment:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody ElderAssessment elderAssessment) {
		return elderAssessmentService.saveInfo(elderAssessment);
	}
	
	/**
	 *  编辑
	 *
	 * @param elderAssessment
	 * @return
	 */
	@AutoLog(value = "elder_assessment-编辑")
	@Operation(summary="elder_assessment-编辑")
	@RequiresPermissions("elder_assessment:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody ElderAssessment elderAssessment) {
		elderAssessmentService.updateById(elderAssessment);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "elder_assessment-通过id删除")
	@Operation(summary="elder_assessment-通过id删除")
	@RequiresPermissions("elder_assessment:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		elderAssessmentService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "elder_assessment-批量删除")
	@Operation(summary="elder_assessment-批量删除")
	@RequiresPermissions("elder_assessment:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.elderAssessmentService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "elder_assessment-通过id查询")
	@Operation(summary="elder_assessment-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<ElderAssessment> queryById(@RequestParam(name="id",required=true) String id) {
		ElderAssessment elderAssessment = elderAssessmentService.getById(id);
		if(elderAssessment==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(elderAssessment);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param elderAssessment
    */
    @RequiresPermissions("elder_assessment:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, ElderAssessment elderAssessment) {
        return super.exportXls(request, elderAssessment, ElderAssessment.class, "elder_assessment");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("elder_assessment:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, ElderAssessment.class);
    }

}
