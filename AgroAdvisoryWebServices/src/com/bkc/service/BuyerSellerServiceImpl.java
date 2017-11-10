package com.bkc.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bkc.bean.AllPostBean;
import com.bkc.bean.buyerseller.AllPostBeanWithId;
import com.bkc.bean.buyerseller.BuyerRentResultBean;
import com.bkc.bean.buyerseller.BuyerResultBean;
import com.bkc.bean.buyerseller.CategoryBean;
import com.bkc.bean.buyerseller.ItemBean;
import com.bkc.bean.buyerseller.RenterBean;
import com.bkc.bean.buyerseller.SellerBean;
import com.bkc.dao.BuyerSellerDao;

@Service
@Transactional
public class BuyerSellerServiceImpl implements BuyerSellerService {

	@Autowired
	BuyerSellerDao buyerSellerDao;
	
	
	@Override
	public List<CategoryBean> getCategoryList(String text,String phoneNo)throws Exception {
		
		return this.buyerSellerDao.getCategoryList(text,phoneNo);
	}

	@Override
	public List<ItemBean> getItemList(int categoryId,int subCatId,String phoneNo) throws Exception {
		
		return this.buyerSellerDao.getItemList(categoryId,subCatId,phoneNo);
	}

	@Override
	public void saveSellerPost(SellerBean bean) throws Exception {
		this.buyerSellerDao.saveSellerPost(bean);
		
	}

	@Override
	public List<BuyerResultBean> getBuyerResult(String phoneNo, int itemId, int categoryId,int subcategoryId) throws Exception {
		
		return this.buyerSellerDao.getBuyerResult(phoneNo, itemId, categoryId,subcategoryId);
	}

	@Override
	public List<CategoryBean> getRentedCategoryList(String phoneNo) throws Exception {
		
		return this.buyerSellerDao.getRentedCategoryList(phoneNo);
	}

	@Override
	public List<BuyerRentResultBean> getBuyerRentResult(String phoneNo, int itemId, int categoryId,int subcategoryId) throws Exception {
		
		return this.buyerSellerDao.getBuyerRentResult(phoneNo, itemId, categoryId,subcategoryId);
	}

	@Override
	public void saveRenterPost(RenterBean bean) {
		this.buyerSellerDao.saveRenterPost(bean);
		
	}

	@Override
	public List<CategoryBean> getSubCategoryList(int subCatId,String phoneNo) throws Exception {
		
		return this.buyerSellerDao.getSubCategoryList(subCatId,phoneNo);
	}

	@Override
	public List<ItemBean> getItemListOnCategory(int categoryId) throws Exception {
		// TODO Auto-generated method stub
		return this.buyerSellerDao.getItemListOnCategory(categoryId);
	}

	@Override
	public List<AllPostBean> getAllPost(String phoneNo) {
		
		return this.buyerSellerDao.getAllPost(phoneNo);
	}

	@Override
	public List<AllPostBeanWithId> getAllPostWithId(String phoneNo,String date) {
		// TODO Auto-generated method stub
		return this.buyerSellerDao.getAllPostWithId(phoneNo,date);
	}

	@Override
	public boolean updateSellerRenterPost(int id, String type) {
	
		return buyerSellerDao.updateSellerRenterPost(id, type);
	}

	@Override
	public boolean disapproveSellerRenterPost(int id, String type) {
	
		return buyerSellerDao.disapproveSellerRenterPost(id, type);
	}

	@Override
	public List<String> getDistinctDatesOfPosts() {
		// TODO Auto-generated method stub
		return this.buyerSellerDao.getDistinctDatesOfPosts();
	}
	
}
