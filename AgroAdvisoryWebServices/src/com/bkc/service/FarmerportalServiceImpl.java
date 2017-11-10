package com.bkc.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bkc.bean.AddShopBean;
import com.bkc.bean.AddShopBeanJsp;
import com.bkc.bean.CorrectedShopBean;
import com.bkc.bean.FarmersportalBean;
import com.bkc.bean.OrganicFarmingBean;
import com.bkc.dao.FarmersportalDao;
@Service
@Transactional
public class FarmerportalServiceImpl implements FarmersportalService{

	@Autowired 
	FarmersportalDao fmDoa;
	
	@Override
	public List<FarmersportalBean> getFertilizersDealer(float lat, float lon){
		// TODO Auto-generated method stub
		return fmDoa.getFertilizersDealer(lat, lon);
	}

	@Override
	public List<FarmersportalBean> getSeedDealer(float lat, float lon) {
		// TODO Auto-generated method stub
		return fmDoa.getSeedDealer(lat, lon);
	}

	@Override
	public List<FarmersportalBean> getPesticideDealer(float lat, float lon) {
		// TODO Auto-generated method stub
		return fmDoa.getPesticideDealer(lat, lon);
	}

	@Override
	public List<FarmersportalBean> getMachineryDetails(int villageId) {
		// TODO Auto-generated method stub
		return fmDoa.getMachineryDetails( villageId);
	}

	@Override
	public List<OrganicFarmingBean> getOrganicFarmingList() {
		// TODO Auto-generated method stub
		return this.fmDoa.getOrganicFarmingList();
	}

	/* (non-Javadoc)
	 * @see com.bkc.service.FarmersportalService#saveShop(com.bkc.bean.AddShopBean)
	 */
	@Override
	public void saveShop(AddShopBean bean) throws Exception {
		this.fmDoa.saveShop(bean);
		
	}

	/* (non-Javadoc)
	 * @see com.bkc.service.FarmersportalService#verifyShop(int, com.bkc.bean.CorrectedShopBean)
	 */
	@Override
	public boolean verifyShop(CorrectedShopBean bean) {
		// TODO Auto-generated method stub
		return fmDoa.verifyShop(bean);
	}

	/* (non-Javadoc)
	 * @see com.bkc.service.FarmersportalService#getShopList()
	 */
	@Override
	public List<AddShopBeanJsp> getShopList() {
		// TODO Auto-generated method stub
		return fmDoa.getShopList();
	}

	
	
}
