package com.joydigit.seniorcaring.mvp.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.joydigit.seniorcaring.mvp.entity.ElderProjectAccount;
import com.joydigit.seniorcaring.mvp.service.IElderProjectAccountService;
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
import org.jeecg.common.system.query.QueryRuleEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
 /**
 * @Description: 项目账户配置
 * @Author: jeecg-boot
 * @Date:   2026-04-28
 * @Version: V1.0
 */
@Tag(name="项目账户配置")
@RestController
@RequestMapping("/com/joydigit/seniorcaring/mvp/elderProjectAccount")
@Slf4j
public class ElderProjectAccountController extends JeecgController<ElderProjectAccount, IElderProjectAccountService> {
	@Autowired
	private IElderProjectAccountService elderProjectAccountService;
	
	/**
	 * 分页列表查询
	 *
	 * @param elderProjectAccount
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "项目账户配置-分页列表查询")
	@Operation(summary="项目账户配置-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<ElderProjectAccount>> queryPageList(ElderProjectAccount elderProjectAccount,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {

		Page<ElderProjectAccount> page = new Page<>(pageNo, pageSize);
		IPage<ElderProjectAccount> pageList = elderProjectAccountService.pageList(page, elderProjectAccount);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param elderProjectAccount
	 * @return
	 */
	@AutoLog(value = "项目账户配置-添加")
	@Operation(summary="项目账户配置-添加")
	@RequiresPermissions("elder_project_account:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody ElderProjectAccount elderProjectAccount) {
		elderProjectAccountService.save(elderProjectAccount);

		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param elderProjectAccount
	 * @return
	 */
	@AutoLog(value = "项目账户配置-编辑")
	@Operation(summary="项目账户配置-编辑")
	@RequiresPermissions("elder_project_account:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody ElderProjectAccount elderProjectAccount) {
		elderProjectAccountService.updateById(elderProjectAccount);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "项目账户配置-通过id删除")
	@Operation(summary="项目账户配置-通过id删除")
	@RequiresPermissions("elder_project_account:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		elderProjectAccountService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "项目账户配置-批量删除")
	@Operation(summary="项目账户配置-批量删除")
	@RequiresPermissions("elder_project_account:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.elderProjectAccountService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "项目账户配置-通过id查询")
	@Operation(summary="项目账户配置-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<ElderProjectAccount> queryById(@RequestParam(name="id",required=true) String id) {
		ElderProjectAccount elderProjectAccount = elderProjectAccountService.getById(id);
		if(elderProjectAccount==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(elderProjectAccount);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param elderProjectAccount
    */
    @RequiresPermissions("elder_project_account:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, ElderProjectAccount elderProjectAccount) {
        return super.exportXls(request, elderProjectAccount, ElderProjectAccount.class, "项目账户配置");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("elder_project_account:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, ElderProjectAccount.class);
    }

}
