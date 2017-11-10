package com.bkc.dao;

import java.util.List;

import com.bkc.bean.AllPostBean;
import com.bkc.bean.buyerseller.AllPostBeanWithId;
import com.bkc.bean.buyerseller.BuyerRentResultBean;
import com.bkc.bean.buyerseller.BuyerResultBean;
import com.bkc.bean.buyerseller.CategoryBean;
import com.bkc.bean.buyerseller.ItemBean;
import com.bkc.bean.buyerseller.RenterBean;
import com.bkc.bean.buyerseller.SellerBean;

public interface BuyerSellerDao {
   
	public List<CategoryBean> getCategoryList(String text,String phoneNo);
	public List<CategoryBean> getSubCategoryList(int subCatId,String phoneNo);
	public List<CategoryBean> getRentedCategoryList(String phoneNo);
	public List<ItemBean> getItemList(int categoryId,int subCatId,String phoneNo);
	public void saveSellerPost(SellerBean bean);
	public void saveRenterPost(RenterBean bean);
	public List<BuyerResultBean> getBuyerResult(String phoneNo,int itemId,int categoryId,int subcategoryId);
	public List<BuyerRentResultBean> getBuyerRentResult(String phoneNo,int itemId,int categoryId,int subcategoryId);
	public List<ItemBean> getItemListOnCategory(int categoryId);
	public List<AllPostBean> getAllPost(String phoneNo);
	public List<AllPostBeanWithId> getAllPostWithId(String phoneNo,String date);
	public boolean updateSellerRenterPost(int id,String type);
	public boolean disapproveSellerRenterPost(int id, String type);
	public List<String> getDistinctDatesOfPosts(); 
}
