package com.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.List;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.utils.PageUtils;
import com.utils.Query;


import com.dao.QiyexuanjiangDao;
import com.entity.QiyexuanjiangEntity;
import com.service.QiyexuanjiangService;
import com.entity.vo.QiyexuanjiangVO;
import com.entity.view.QiyexuanjiangView;

@Service("qiyexuanjiangService")
public class QiyexuanjiangServiceImpl extends ServiceImpl<QiyexuanjiangDao, QiyexuanjiangEntity> implements QiyexuanjiangService {
	
	
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<QiyexuanjiangEntity> page = this.selectPage(
                new Query<QiyexuanjiangEntity>(params).getPage(),
                new EntityWrapper<QiyexuanjiangEntity>()
        );
        return new PageUtils(page);
    }
    
    @Override
	public PageUtils queryPage(Map<String, Object> params, Wrapper<QiyexuanjiangEntity> wrapper) {
		  Page<QiyexuanjiangView> page =new Query<QiyexuanjiangView>(params).getPage();
	        page.setRecords(baseMapper.selectListView(page,wrapper));
	    	PageUtils pageUtil = new PageUtils(page);
	    	return pageUtil;
 	}

    
    @Override
	public List<QiyexuanjiangVO> selectListVO(Wrapper<QiyexuanjiangEntity> wrapper) {
 		return baseMapper.selectListVO(wrapper);
	}
	
	@Override
	public QiyexuanjiangVO selectVO(Wrapper<QiyexuanjiangEntity> wrapper) {
 		return baseMapper.selectVO(wrapper);
	}
	
	@Override
	public List<QiyexuanjiangView> selectListView(Wrapper<QiyexuanjiangEntity> wrapper) {
		return baseMapper.selectListView(wrapper);
	}

	@Override
	public QiyexuanjiangView selectView(Wrapper<QiyexuanjiangEntity> wrapper) {
		return baseMapper.selectView(wrapper);
	}


}
