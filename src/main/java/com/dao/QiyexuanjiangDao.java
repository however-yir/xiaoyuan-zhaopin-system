package com.dao;

import com.entity.QiyexuanjiangEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import org.apache.ibatis.annotations.Param;
import com.entity.vo.QiyexuanjiangVO;
import com.entity.view.QiyexuanjiangView;


/**
 * 企业宣讲
 * 
 * @author 
 * @email 
 * @date 2025-02-07 12:22:17
 */
public interface QiyexuanjiangDao extends BaseMapper<QiyexuanjiangEntity> {
	
	List<QiyexuanjiangVO> selectListVO(@Param("ew") Wrapper<QiyexuanjiangEntity> wrapper);
	
	QiyexuanjiangVO selectVO(@Param("ew") Wrapper<QiyexuanjiangEntity> wrapper);
	
	List<QiyexuanjiangView> selectListView(@Param("ew") Wrapper<QiyexuanjiangEntity> wrapper);

	List<QiyexuanjiangView> selectListView(Pagination page,@Param("ew") Wrapper<QiyexuanjiangEntity> wrapper);

	
	QiyexuanjiangView selectView(@Param("ew") Wrapper<QiyexuanjiangEntity> wrapper);
	

}
