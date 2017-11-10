package com.bkc.bean;

import java.util.Comparator;

public class RainfallCompare implements Comparator<RainfallBean> {

	@Override
	public int compare(RainfallBean o1, RainfallBean o2) {
               
		if(o1.getRainfall()>o2.getRainfall()){
			return -1;
		}
		else {
		 return 1;	
		}
		
	}

}
