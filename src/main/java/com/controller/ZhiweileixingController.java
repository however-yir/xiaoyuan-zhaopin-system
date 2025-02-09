package com.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Date;
import java.util.List;
import java.util.Collections;

import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import com.utils.ValidatorUtils;
import com.utils.DeSensUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.annotation.IgnoreAuth;

import com.entity.ZhiweileixingEntity;
import com.entity.view.ZhiweileixingView;

import com.service.ZhiweileixingService;
import com.service.TokenService;
import com.utils.PageUtils;
import com.utils.R;
import com.utils.MPUtil;
import com.utils.MapUtils;
import com.utils.CommonUtil;
import java.io.IOException;

/**
 * 职位类型
 * 后端接口
 * @author 
 * @email 
 * @date 2025-02-07 12:22:17
 */
@RestController
@RequestMapping("/zhiweileixing")
public class ZhiweileixingController {
    @Autowired
    private ZhiweileixingService zhiweileixingService;






    



    /**
     * 后台列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params,ZhiweileixingEntity zhiweileixing,
		HttpServletRequest request){
        EntityWrapper<ZhiweileixingEntity> ew = new EntityWrapper<ZhiweileixingEntity>();



		PageUtils page = zhiweileixingService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, zhiweileixing), params), params));
				Map<String, String> deSens = new HashMap<>();
				DeSensUtil.desensitize(page,deSens);
        return R.ok().put("data", page);
    }
    
    /**
     * 前台列表
     */
	@IgnoreAuth
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params,ZhiweileixingEntity zhiweileixing, 
		HttpServletRequest request){
        EntityWrapper<ZhiweileixingEntity> ew = new EntityWrapper<ZhiweileixingEntity>();

		PageUtils page = zhiweileixingService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, zhiweileixing), params), params));
		
				Map<String, String> deSens = new HashMap<>();
				DeSensUtil.desensitize(page,deSens);
        return R.ok().put("data", page);
    }



	/**
     * 列表
     */
    @RequestMapping("/lists")
    public R list( ZhiweileixingEntity zhiweileixing){
       	EntityWrapper<ZhiweileixingEntity> ew = new EntityWrapper<ZhiweileixingEntity>();
      	ew.allEq(MPUtil.allEQMapPre( zhiweileixing, "zhiweileixing")); 
        return R.ok().put("data", zhiweileixingService.selectListView(ew));
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(ZhiweileixingEntity zhiweileixing){
        EntityWrapper< ZhiweileixingEntity> ew = new EntityWrapper< ZhiweileixingEntity>();
 		ew.allEq(MPUtil.allEQMapPre( zhiweileixing, "zhiweileixing")); 
		ZhiweileixingView zhiweileixingView =  zhiweileixingService.selectView(ew);
		return R.ok("查询职位类型成功").put("data", zhiweileixingView);
    }
	
    /**
     * 后台详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        ZhiweileixingEntity zhiweileixing = zhiweileixingService.selectById(id);
				Map<String, String> deSens = new HashMap<>();
				DeSensUtil.desensitize(zhiweileixing,deSens);
        return R.ok().put("data", zhiweileixing);
    }

    /**
     * 前台详情
     */
	@IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        ZhiweileixingEntity zhiweileixing = zhiweileixingService.selectById(id);
				Map<String, String> deSens = new HashMap<>();
				DeSensUtil.desensitize(zhiweileixing,deSens);
        return R.ok().put("data", zhiweileixing);
    }
    



    /**
     * 后台保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody ZhiweileixingEntity zhiweileixing, HttpServletRequest request){
        if(zhiweileixingService.selectCount(new EntityWrapper<ZhiweileixingEntity>().eq("zhiweileixing", zhiweileixing.getZhiweileixing()))>0) {
            return R.error("职位类型已存在");
        }
    	//ValidatorUtils.validateEntity(zhiweileixing);
        zhiweileixingService.insert(zhiweileixing);
        return R.ok().put("data",zhiweileixing.getId());
    }
    
    /**
     * 前台保存
     */
    @RequestMapping("/add")
    public R add(@RequestBody ZhiweileixingEntity zhiweileixing, HttpServletRequest request){
        if(zhiweileixingService.selectCount(new EntityWrapper<ZhiweileixingEntity>().eq("zhiweileixing", zhiweileixing.getZhiweileixing()))>0) {
            return R.error("职位类型已存在");
        }
    	//ValidatorUtils.validateEntity(zhiweileixing);
        zhiweileixingService.insert(zhiweileixing);
        return R.ok().put("data",zhiweileixing.getId());
    }





    /**
     * 修改
     */
    @RequestMapping("/update")
    @Transactional
    public R update(@RequestBody ZhiweileixingEntity zhiweileixing, HttpServletRequest request){
        //ValidatorUtils.validateEntity(zhiweileixing);
        if(zhiweileixingService.selectCount(new EntityWrapper<ZhiweileixingEntity>().ne("id", zhiweileixing.getId()).eq("zhiweileixing", zhiweileixing.getZhiweileixing()))>0) {
            return R.error("职位类型已存在");
        }
        //全部更新
        zhiweileixingService.updateById(zhiweileixing);

        return R.ok();
    }



    

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        zhiweileixingService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }
    
	











}
