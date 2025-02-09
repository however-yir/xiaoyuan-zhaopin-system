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


import com.dao.ZhaopinjieguoDao;
import com.entity.ZhaopinjieguoEntity;
import com.service.ZhaopinjieguoService;
import com.entity.vo.ZhaopinjieguoVO;
import com.entity.view.ZhaopinjieguoView;

@Service("zhaopinjieguoService")
public class ZhaopinjieguoServiceImpl extends ServiceImpl<ZhaopinjieguoDao, ZhaopinjieguoEntity> implements ZhaopinjieguoService {
	
	
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<ZhaopinjieguoEntity> page = this.selectPage(
                new Query<ZhaopinjieguoEntity>(params).getPage(),
                new EntityWrapper<ZhaopinjieguoEntity>()
        );
        return new PageUtils(page);
    }
    
    @Override
	public PageUtils queryPage(Map<String, Object> params, Wrapper<ZhaopinjieguoEntity> wrapper) {
		  Page<ZhaopinjieguoView> page =new Query<ZhaopinjieguoView>(params).getPage();
	        page.setRecords(baseMapper.selectListView(page,wrapper));
	    	PageUtils pageUtil = new PageUtils(page);
	    	return pageUtil;
 	}

    
    @Override
	public List<ZhaopinjieguoVO> selectListVO(Wrapper<ZhaopinjieguoEntity> wrapper) {
 		return baseMapper.selectListVO(wrapper);
	}
	
	@Override
	public ZhaopinjieguoVO selectVO(Wrapper<ZhaopinjieguoEntity> wrapper) {
 		return baseMapper.selectVO(wrapper);
	}
	
	@Override
	public List<ZhaopinjieguoView> selectListView(Wrapper<ZhaopinjieguoEntity> wrapper) {
		return baseMapper.selectListView(wrapper);
	}

	@Override
	public ZhaopinjieguoView selectView(Wrapper<ZhaopinjieguoEntity> wrapper) {
		return baseMapper.selectView(wrapper);
	}


}
