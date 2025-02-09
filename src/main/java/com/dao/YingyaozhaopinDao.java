package com.dao;

import com.entity.YingyaozhaopinEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import org.apache.ibatis.annotations.Param;
import com.entity.vo.YingyaozhaopinVO;
import com.entity.view.YingyaozhaopinView;


/**
 * 应邀招聘
 * 
 * @author 
 * @email 
 * @date 2025-02-07 12:22:18
 */
public interface YingyaozhaopinDao extends BaseMapper<YingyaozhaopinEntity> {
	
	List<YingyaozhaopinVO> selectListVO(@Param("ew") Wrapper<YingyaozhaopinEntity> wrapper);
	
	YingyaozhaopinVO selectVO(@Param("ew") Wrapper<YingyaozhaopinEntity> wrapper);
	
	List<YingyaozhaopinView> selectListView(@Param("ew") Wrapper<YingyaozhaopinEntity> wrapper);

	List<YingyaozhaopinView> selectListView(Pagination page,@Param("ew") Wrapper<YingyaozhaopinEntity> wrapper);

	
	YingyaozhaopinView selectView(@Param("ew") Wrapper<YingyaozhaopinEntity> wrapper);
	

}
