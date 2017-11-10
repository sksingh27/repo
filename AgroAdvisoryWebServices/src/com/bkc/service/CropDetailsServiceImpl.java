package com.bkc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bkc.bean.CropVarietyBean;
import com.bkc.dao.CropDetailsdao;
import com.bkc.model.CropDetails;

@Service("cropdService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class CropDetailsServiceImpl implements CropDetailsService {

	@Autowired
	private CropDetailsdao cropdeldao;

	@Override
	public List<CropDetails> listdata() {
		// TODO Auto-generated method stub
		
		return cropdeldao.listData();
		
	}

	@Override
	public Integer getCropId(String cropname) {
		// TODO Auto-generated method stub
		return cropdeldao.getCropId(cropname);
	}

	@Override
	public CropDetails getCropDetails(int cropId) {
		// TODO Auto-generated method stub
		return cropdeldao.getCropDetails(cropId);
	}

	/* (non-Javadoc)
	 * @see com.bkc.service.CropDetailsService#getVariety(int, java.lang.String)
	 */
	@Override
	public List<CropVarietyBean> getVariety(int cropId, String languageId) {
		
		return cropdeldao.getVariety(cropId, languageId);
	}
	
	
}
