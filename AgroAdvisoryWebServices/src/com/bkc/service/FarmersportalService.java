package com.bkc.service;

import java.util.List;

import com.bkc.bean.AddShopBean;
import com.bkc.bean.AddShopBeanJsp;
import com.bkc.bean.CorrectedShopBean;
import com.bkc.bean.FarmersportalBean;
import com.bkc.bean.OrganicFarmingBean;

public interface FarmersportalService {

	public List<FarmersportalBean> getFertilizersDealer(float lat,float lon)throws Exception;
	public List<FarmersportalBean> getSeedDealer(float lat,float lon)throws Exception;
	public List<FarmersportalBean> getPesticideDealer(float lat,float lon)throws Exception;
	public List<FarmersportalBean> getMachineryDetails(int villageId)throws Exception;
	public List<OrganicFarmingBean> getOrganicFarmingList();
	public void saveShop(AddShopBean bean) throws Exception;
	public boolean verifyShop(CorrectedShopBean bean);
	public List<AddShopBeanJsp> getShopList();
}
