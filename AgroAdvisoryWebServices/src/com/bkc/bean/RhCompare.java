package com.bkc.bean;

import java.util.Comparator;

import com.bkc.model.GridWiseCola;

public class RhCompare implements Comparator<GridWiseCola>{

	@Override
	public int compare(GridWiseCola o1, GridWiseCola o2) {
	if(o1.getMax_rh()>o2.getMax_rh()){
		return -1;
	}
	else{
		return 1;
	}
	}

}
