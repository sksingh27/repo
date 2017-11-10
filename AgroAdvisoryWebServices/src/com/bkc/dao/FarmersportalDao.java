package com.bkc.dao;

import java.util.List;

import com.bkc.bean.AddShopBean;
import com.bkc.bean.AddShopBeanJsp;
import com.bkc.bean.CorrectedShopBean;
import com.bkc.bean.FarmersportalBean;
import com.bkc.bean.OrganicFarmingBean;

public interface FarmersportalDao {
	public List<FarmersportalBean> getFertilizersDealer(float lat,float lon);
	public List<FarmersportalBean> getSeedDealer(float lat,float lon);
	public List<FarmersportalBean> getPesticideDealer(float lat,float lon);
	public List<FarmersportalBean> getMachineryDetails(int villageId);
	//organic farming
	public List<OrganicFarmingBean> getOrganicFarmingList();
	//save shop
	public void saveShop(AddShopBean bean) throws Exception;
	public boolean verifyShop(CorrectedShopBean bean);
	public List<AddShopBeanJsp> getShopList();
}
