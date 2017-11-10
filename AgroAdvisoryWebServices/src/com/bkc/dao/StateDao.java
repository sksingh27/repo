package com.bkc.dao;

import java.util.List;

import com.bkc.model.StateDetails;

public interface StateDao {
	
	public List<StateDetails> getDistinctStates();

}
