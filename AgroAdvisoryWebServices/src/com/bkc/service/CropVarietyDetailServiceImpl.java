package com.bkc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.bkc.dao.CropVarietyDetailDao;
import com.bkc.model.CropVarietyDetail;

@Service("rservice")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class CropVarietyDetailServiceImpl implements CropVarityDetailService {

	@Autowired
	private CropVarietyDetailDao rDao;
	

	@Override
	public List<CropVarietyDetail> listData() {
		// TODO Auto-generated method stub
		
		return rDao.listData() ;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	@Override
	public List<String> getState() {
		// TODO Auto-generated method stub
		return rDao.getState();
	}

	@Override
	public List<CropVarietyDetail> getVariety(String state,int cropId) {
		// TODO Auto-generated method stub
		return rDao.getVariety(state,cropId);
	}

}
