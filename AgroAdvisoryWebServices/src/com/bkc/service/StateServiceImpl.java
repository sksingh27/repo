package com.bkc.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bkc.dao.StateDao;
import com.bkc.model.StateDetails;
@Service("stateService")
@Transactional
public class StateServiceImpl implements StateService {
    @Autowired
	StateDao stateDao;
	
	@Override
	public List<StateDetails> getDistinctStates() {
		
		return this.stateDao.getDistinctStates();
	}

}
