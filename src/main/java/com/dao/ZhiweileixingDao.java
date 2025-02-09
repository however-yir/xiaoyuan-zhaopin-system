package com.dao;

import com.entity.ZhiweileixingEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import org.apache.ibatis.annotations.Param;
import com.entity.vo.ZhiweileixingVO;
import com.entity.view.ZhiweileixingView;


/**
 * 职位类型
 * 
 * @author 
 * @email 
 * @date 2025-02-07 12:22:17
 */
public interface ZhiweileixingDao extends BaseMapper<ZhiweileixingEntity> {
	
	List<ZhiweileixingVO> selectListVO(@Param("ew") Wrapper<ZhiweileixingEntity> wrapper);
	
	ZhiweileixingVO selectVO(@Param("ew") Wrapper<ZhiweileixingEntity> wrapper);
	
	List<ZhiweileixingView> selectListView(@Param("ew") Wrapper<ZhiweileixingEntity> wrapper);

	List<ZhiweileixingView> selectListView(Pagination page,@Param("ew") Wrapper<ZhiweileixingEntity> wrapper);

	
	ZhiweileixingView selectView(@Param("ew") Wrapper<ZhiweileixingEntity> wrapper);
	

}
