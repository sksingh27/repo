package com.bkc.dao;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.test.annotation.Rollback;

import com.bkc.bean.AllPostBean;
import com.bkc.bean.AllPostBeanComperator;
import com.bkc.bean.buyerseller.AllPostBeanWithId;
import com.bkc.bean.buyerseller.AllPostBeanWithIdComparator;
import com.bkc.bean.buyerseller.BuyerRentResultBean;
import com.bkc.bean.buyerseller.BuyerResultBean;
import com.bkc.bean.buyerseller.CategoryBean;
import com.bkc.bean.buyerseller.ItemBean;
import com.bkc.bean.buyerseller.RenterBean;
import com.bkc.bean.buyerseller.SellerBean;
import com.bkc.model.UserProfile;
import com.bkc.model.buyerseller.RentItemDetails;
import com.bkc.model.buyerseller.SellerItemDetails;
import com.bkc.service.UserDetailsService;

import org.hibernate.Criteria;

@Repository("buyerSellerDao")
@Transactional
public class BuyerSellerDaoImpl implements BuyerSellerDao {

	@Autowired
	@Qualifier(value = "agroSessionFactory")
	SessionFactory agroMgntSessionFactory;

	@Autowired
	UserDetailsService userService;
    
	SimpleDateFormat yyyyMMddhhmmss=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")  ;
	SimpleDateFormat ddMMM=new SimpleDateFormat("dd MMM yyyy")  ;
	
	@Override
	public List<CategoryBean> getCategoryList(String text, String phoneNo) {
		String languageId;
		try {
			languageId = userService.getUserLanguage(phoneNo);

		} catch (Exception e) {
			e.printStackTrace();
			languageId = "hi";
		}

		List<CategoryBean> categoryBeanList = new ArrayList<>();
		Session session = agroMgntSessionFactory.getCurrentSession();
		String hql;
	
			if (text.contains("service")) {
				hql = "select distinct csd.id.catId,csd.categoryName from CategorySubcategoryDetails as csd where csd.id.catId=10 and csd.id.languageId='"+languageId+"'";
			} else {
				hql = "select distinct csd.id.catId,csd.categoryName from CategorySubcategoryDetails as csd where csd.id.catId<>10 and csd.id.languageId='"+languageId+"'";
			}
		List tempList = session.createQuery(hql).list();
		for (Object obj : tempList) {
			Object[] objArr = (Object[]) obj;
			CategoryBean bean = new CategoryBean();
			bean.setCategoryId(Integer.parseInt(String.valueOf(objArr[0])));
			bean.setCategoryName(String.valueOf(objArr[1]));
			bean.setNextLink(true);

			// bean.setColor(Integer.parseInt(String.valueOf(objArr[2])));
			categoryBeanList.add(bean);
		}

		return categoryBeanList;
	}

	@Override
	public List<CategoryBean> getSubCategoryList(int subCatId, String phoneNo) {
		List<CategoryBean> categoryBeanList = new ArrayList<>();
		Session session = agroMgntSessionFactory.getCurrentSession();
		String languageId;
		try {
			languageId = userService.getUserLanguage(phoneNo);

		} catch (Exception e) {
			e.printStackTrace();
			languageId = "hi";
		}
		String hql;

		
			hql = "select  csd.id.subCatId,csd.subCategoryName,csd.nextLink from CategorySubcategoryDetails as csd where csd.id.catId="
					+ subCatId+" and csd.id.languageId='"+languageId+"'";
		

		List tempList = session.createQuery(hql).list();
		for (Object obj : tempList) {
			Object[] objArr = (Object[]) obj;
			CategoryBean bean = new CategoryBean();
			bean.setCategoryId(Integer.parseInt(String.valueOf(objArr[0])));
			bean.setCategoryName(String.valueOf(objArr[1]));
			bean.setNextLink(Boolean.parseBoolean(String.valueOf(objArr[2])));
			// bean.setColor(Integer.parseInt(String.valueOf(objArr[2])));
			categoryBeanList.add(bean);
		}

		return categoryBeanList;
	}

	@Override
	public List<ItemBean> getItemList(int categoryId, int subCatId, String phoneNo) {
		List<ItemBean> itemBeanList = new ArrayList<>();
		Session session = agroMgntSessionFactory.getCurrentSession();

		String languageId;
		try {
			languageId = userService.getUserLanguage(phoneNo);

		} catch (Exception e) {
			e.printStackTrace();
			languageId = "hi";
		}
		String hql;
		

			hql = "select distinct icd.id.itemId,icd.itemName from ItemCategoryDetails as icd where icd.id.categoryId ="
					+ categoryId + " and icd.id.subCatId=" + subCatId +" and icd.id.languageId='"+languageId+"'";
		

		List tempList = session.createQuery(hql).list();
		for (Object obj : tempList) {
			Object[] objArr = (Object[]) obj;
			ItemBean bean = new ItemBean();
			bean.setItemId(Integer.parseInt(String.valueOf(objArr[0])));
			bean.setItemName(String.valueOf(objArr[1]));
			itemBeanList.add(bean);
		}

		return itemBeanList;
	}

	@Override
	public List<ItemBean> getItemListOnCategory(int categoryId) {
		List<ItemBean> itemBeanList = new ArrayList<>();

		Session session = agroMgntSessionFactory.getCurrentSession();

		String hql = "select distinct icd.id.itemId,icd.itemNameHindi from ItemCategoryDetails as icd where icd.id.categoryId ="
				+ categoryId;
		List tempList = session.createQuery(hql).list();
		for (Object obj : tempList) {
			Object[] objArr = (Object[]) obj;
			ItemBean bean = new ItemBean();
			bean.setItemId(Integer.parseInt(String.valueOf(objArr[0])));
			bean.setItemName(String.valueOf(objArr[1]));
			itemBeanList.add(bean);
		}

		return itemBeanList;
	}

	@Override
	@Rollback(true)
	public void saveSellerPost(SellerBean bean) {
		SellerItemDetails sellerPost = new SellerItemDetails();
		Session session = agroMgntSessionFactory.getCurrentSession();
		String sqlQuery = "select lat,lon from tehsilVillageDetails where id=" + bean.getStationid();
		List latLon = session.createSQLQuery(sqlQuery).list();
		sellerPost.setPhoneNo(bean.getPhoneNo());
		sellerPost.setStationId(bean.getStationid());
		sellerPost.setItemId(bean.getItemId());
		sellerPost.setCategoryId(bean.getCategoryId());
		sellerPost.setExpectedPrice(bean.getPrice());
		sellerPost.setItemDescription(bean.getDescription());
		sellerPost.setPackaging(bean.getPackaging());
		sellerPost.setSold(false);
		sellerPost.setImageName(bean.getImageName());
		sellerPost.setQuantity(bean.getQuantity());
		sellerPost.setSubCatId(bean.getSubcategoryId());
		sellerPost.setDate(new Date());
		sellerPost.setApproval(true);
		for (Object obj : latLon) {
			Object[] arr = (Object[]) obj;
			sellerPost.setLat(Float.parseFloat(String.valueOf(arr[0])));
			sellerPost.setLon(Float.parseFloat(String.valueOf(arr[1])));
		}

		session.save(sellerPost);

	}

	@Override
	public List<CategoryBean> getRentedCategoryList(String phoneNo) {
		List<CategoryBean> categoryBeanList = new ArrayList<>();
		Session session = agroMgntSessionFactory.getCurrentSession();
		String languageId;
		try {
			languageId = userService.getUserLanguage(phoneNo);

		} catch (Exception e) {
			e.printStackTrace();
			languageId = "hi";
		}

		String hql;

			hql = "select distinct csd.id.catId,csd.categoryName from CategorySubcategoryDetails as csd where csd.id.catId in (6,7)  and csd.id.languageId='"+languageId+"'";
		

		List tempList = session.createQuery(hql).list();
		for (Object obj : tempList) {
			Object[] objArr = (Object[]) obj;
			CategoryBean bean = new CategoryBean();
			bean.setCategoryId(Integer.parseInt(String.valueOf(objArr[0])));
			bean.setCategoryName(String.valueOf(objArr[1]));
			categoryBeanList.add(bean);
		}

		return categoryBeanList;
	}

	@Override
	public List<BuyerResultBean> getBuyerResult(String phoneNo, int itemId, int categoryId, int subcategoryId) {
		Session session = agroMgntSessionFactory.getCurrentSession();
		List<BuyerResultBean> buyerResult = new ArrayList<>();
		float lat = 0.0f;
		float lon = 0.0f;
		String itemName;
		String itemNameQuery;
		String languageId;

		try {
			languageId = userService.getUserLanguage(phoneNo);
		} catch (Exception e) {
			languageId = "hi";
			e.printStackTrace();
		}
		
			if (itemId != 0) {
				itemNameQuery = "select icd.itemName from ItemCategoryDetails as icd where icd.id.categoryId="
						+ categoryId + " and icd.id.itemId=" + itemId +" and icd.id.languageId='"+languageId+"'";
			} else {
				itemNameQuery = "select csd.subCategoryName from CategorySubcategoryDetails as csd where csd.id.catId="
						+ categoryId + " and csd.id.subCatId=" + subcategoryId+" and csd.id.languageId='"+languageId+"'";
			}

		

		List itemNameList = session.createQuery(itemNameQuery).list();
		itemName = itemNameList.get(0).toString();
		//System.out.println("item name is------------------->" + itemName);
		String buyersLatLonQUery = "select tvd.id.lat,tvd.id.lon from TehsilVillageDetailsNew as tvd,UserProfile as up where up.phoneNo='"
				+ phoneNo + "' and up.stationId=tvd.iid";
		List buyerLatLonList = session.createQuery(buyersLatLonQUery).list();
		for (Object obj : buyerLatLonList) {
			Object[] arr = (Object[]) obj;
			lat = Float.parseFloat(String.valueOf(arr[0]));
			lon = Float.parseFloat(String.valueOf(arr[1]));
		}
		//System.out.println("lat long is----------------------->" + lat + " " + lon);
		String buyersResultQuery;
		if (itemId != 0) {
			// buyersResultQuery = "select
			// up.name,sid.expectedPrice,sid.phoneNo,sid.packaging,sid.quantity,sid.itemDescription,sid.imageName
			// from SellerItemDetails as sid,UserProfile as up where
			// sid.phoneNo=up.phoneNo and sid.itemId="+ itemId+" and sid.lat
			// between "+(lat-0.5f)+" and "+(lat+0.5f)+" and sid.lon between
			// "+(lon-0.5f)+" and "+(lon+0.5f);
			buyersResultQuery = "select up.name,sid.expectedPrice,sid.phoneNo,sid.packaging,sid.quantity,sid.itemDescription,sid.imageName,sid.stationId,sid.date from SellerItemDetails as sid,UserProfile as up where sid.phoneNo=up.phoneNo and sid.itemId="
					+ itemId + " and sid.categoryId=" + categoryId + " and sid.approval=true order by sid.date";
		} else {
			//System.out.println("inside when item ud is 0");
			
			// buyersResultQuery = "select
			// up.name,sid.expectedPrice,sid.phoneNo,sid.packaging,sid.quantity,sid.itemDescription,sid.imageName
			// from SellerItemDetails as sid,UserProfile as up where
			// sid.phoneNo=up.phoneNo and sid.subCatId="+ subcategoryId+" and
			// sid.lat between "+(lat-0.5f)+" and "+(lat+0.5f)+" and sid.lon
			// between "+(lon-0.5f)+" and "+(lon+0.5f);
			buyersResultQuery = "select up.name,sid.expectedPrice,sid.phoneNo,sid.packaging,sid.quantity,sid.itemDescription,sid.imageName,sid.stationId,sid.date from SellerItemDetails as sid,UserProfile as up where sid.phoneNo=up.phoneNo and sid.subCatId="
					+ subcategoryId + " and sid.categoryId=" + categoryId + " and sid.approval=true order by sid.date";
		}
		List buyerResusltObjectList = session.createQuery(buyersResultQuery).list();
		for (Object obj : buyerResusltObjectList) {
			BuyerResultBean bean = new BuyerResultBean();
			Object[] arr = (Object[]) obj;
			
			int stationId=Integer.parseInt(arr[7].toString());
			String state;
			String district;
			try{
				String districtStateQuery="select dd.district,sd.state from tehsilVillageDetails as tvd ,districtDetails as dd ,stateDetails as sd where tvd.id="+stationId+" and tvd.districtId=dd.districtId and tvd.stateId=dd.stateId and dd.stateId=sd.stateId";
				   List ds=session.createSQLQuery(districtStateQuery).list();
				   Object[] ar=(Object[])ds.get(0);
					   district=ar[0].toString();
				   state=ar[1].toString();
			}
			catch(Exception e){
				district="";
				state="";
				e.printStackTrace();
			}
			String postDate=ddMMM.format((Date)arr[8]);
			//System.out.println("post date is ---------------------->"+postDate);
			bean.setName(arr[0].toString());
			bean.setPrice(arr[1].toString());
			bean.setPhoneNo(arr[2].toString());
			bean.setPackaging(arr[3].toString());
			bean.setQuantity(arr[4].toString());
			bean.setDescription(arr[5].toString()+"\n\n Posted on : "+postDate);
			bean.setImageName(arr[6].toString());
			bean.setItem(itemName+"\n\n"+district+","+state);
			buyerResult.add(bean);
		}

		return buyerResult;
	}

	@Override
	public void saveRenterPost(RenterBean bean) {
		RentItemDetails sellerPost = new RentItemDetails();
		Session session = agroMgntSessionFactory.getCurrentSession();
		String sqlQuery = "select lat,lon from tehsilVillageDetails where id=" + bean.getStationid();
		List latLon = session.createSQLQuery(sqlQuery).list();
		sellerPost.setPhoneNo(bean.getPhoneNo());
		sellerPost.setStationId(bean.getStationid());
		sellerPost.setItemId(bean.getItemId());
		sellerPost.setCategoryId(bean.getCategoryId());
		sellerPost.setExpectedPrice(bean.getPrice());
		sellerPost.setItemDescription(bean.getDescription());
		sellerPost.setRentStatus(false);
		sellerPost.setImageName(bean.getImageName());
		sellerPost.setDate(new Date());
		sellerPost.setSubCatId(bean.getSubcategoryId());
		sellerPost.setApproval(true);
		for (Object obj : latLon) {
			Object[] arr = (Object[]) obj;
			sellerPost.setLat(Float.parseFloat(String.valueOf(arr[0])));
			sellerPost.setLon(Float.parseFloat(String.valueOf(arr[1])));
		}

		session.save(sellerPost);

	}

	@Override
	public List<BuyerRentResultBean> getBuyerRentResult(String phoneNo, int itemId, int categoryId, int subcategoryId) {

		//System.out.println("item id" + itemId + "categ id " + categoryId + "sub cat id " + subcategoryId);
		Session session = agroMgntSessionFactory.getCurrentSession();
		List<BuyerRentResultBean> buyerResult = new ArrayList<>();
		float lat = 0.0f;
		float lon = 0.0f;
		String itemName;
		String itemNameQuery;
		String languageId;

		try {
			languageId = userService.getUserLanguage(phoneNo);
		} catch (Exception e) {
			languageId = "hi";
			e.printStackTrace();
		}
		
			if (itemId != 0) {
				itemNameQuery = "select icd.itemName from ItemCategoryDetails as icd where icd.id.categoryId="
						+ categoryId + " and icd.id.itemId=" + itemId+" and icd.id.languageId='"+languageId+"'";
			} else {
				itemNameQuery = "select csd.subCategoryName from CategorySubcategoryDetails as csd where csd.id.catId="
						+ categoryId + " and csd.id.subCatId=" + subcategoryId+" and csd.id.languageId='"+languageId+"'";
			}

		

		List itemNameList = session.createQuery(itemNameQuery).list();
		try{
			itemName = itemNameList.get(0).toString();
		}catch(Exception e){
			
			itemName = "";
			e.printStackTrace();
		}
		
		//System.out.println("item name is------------------->" + itemName);
		String buyersLatLonQUery = "select tvd.id.lat,tvd.id.lon from TehsilVillageDetailsNew as tvd,UserProfile as up where up.phoneNo='"
				+ phoneNo + "' and up.stationId=tvd.iid";
		List buyerLatLonList = session.createQuery(buyersLatLonQUery).list();
		for (Object obj : buyerLatLonList) {
			Object[] arr = (Object[]) obj;
			lat = Float.parseFloat(String.valueOf(arr[0]));
			lon = Float.parseFloat(String.valueOf(arr[1]));
		}
		//System.out.println("lat long is----------------------->" + lat + " " + lon);
		String buyersResultQuery;
		if (itemId != 0) {
			// buyersResultQuery = "select
			// up.name,rid.expectedPrice,rid.phoneNo,rid.itemDescription,rid.imageName
			// from RentItemDetails as rid,UserProfile as up where
			// rid.phoneNo=up.phoneNo and rid.itemId="+ itemId+" and rid.lat
			// between "+(lat-0.5f)+" and "+(lat+0.5f)+" and rid.lon between
			// "+(lon-0.5f)+" and "+(lon+0.5f);
			buyersResultQuery = "select up.name,rid.expectedPrice,rid.phoneNo,rid.itemDescription,rid.imageName,rid.stationId,rid.date from RentItemDetails as rid,UserProfile as up where rid.phoneNo=up.phoneNo and rid.itemId="
					+ itemId + " and rid.categoryId= " + categoryId + " and rid.approval=true order by rid.date";
		} else {
			// buyersResultQuery = "select
			// up.name,rid.expectedPrice,rid.phoneNo,rid.itemDescription,rid.imageName
			// from RentItemDetails as rid,UserProfile as up where
			// rid.phoneNo=up.phoneNo and rid.subCatId="+ subcategoryId+" and
			// rid.lat between "+(lat-0.5f)+" and "+(lat+0.5f)+" and rid.lon
			// between "+(lon-0.5f)+" and "+(lon+0.5f);
			buyersResultQuery = "select up.name,rid.expectedPrice,rid.phoneNo,rid.itemDescription,rid.imageName,rid.stationId,rid.date from RentItemDetails as rid,UserProfile as up where rid.phoneNo=up.phoneNo and rid.subCatId="
					+ subcategoryId + " and rid.categoryId=" + categoryId + " and rid.approval=true order by rid.date";
		}
		List buyerResusltObjectList = session.createQuery(buyersResultQuery).list();
		for (Object obj : buyerResusltObjectList) {
			String state;
			String district;
			BuyerRentResultBean bean = new BuyerRentResultBean();
			Object[] arr = (Object[]) obj;
			int stationId=Integer.parseInt(arr[5].toString());
			try{
				String districtStateQuery="select dd.district,sd.state from tehsilVillageDetails as tvd ,districtDetails as dd ,stateDetails as sd where tvd.id="+stationId+" and tvd.districtId=dd.districtId and tvd.stateId=dd.stateId and dd.stateId=sd.stateId";
				   List ds=session.createSQLQuery(districtStateQuery).list();
				   Object[] ar=(Object[])ds.get(0);
					   district=ar[0].toString();
				   state=ar[1].toString();
			}
			catch(Exception e){
				district="";
				state="";
				e.printStackTrace();
			}
			String postDate=ddMMM.format((Date)arr[6]);
			//System.out.println("post date is ---------------------->"+postDate);
			bean.setName(arr[0].toString());
			bean.setPrice(arr[1].toString());
			bean.setPhoneNo(arr[2].toString());
			bean.setDescription(arr[3].toString()+" \n\n Posted on : "+postDate);
			bean.setImageName(arr[4].toString());
			bean.setItem(itemName+"\n\n"+district+","+state);
			buyerResult.add(bean);
		}

		return buyerResult;
	}

	public List<AllPostBean> getAllPost(String phoneNo) {
		String languageId;
		//System.out.println("phone no inside getAllPosts" + phoneNo);
		
		List<AllPostBean> beanList = new ArrayList<>();
		Session session = agroMgntSessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(SellerItemDetails.class);
		criteria.add(Restrictions.eq("approval", true));
		criteria.addOrder(Order.desc("date"));
		criteria.setMaxResults(15);
		
		
		List<SellerItemDetails> sellerList = criteria.list();
		for (SellerItemDetails r : sellerList) {
		/*	String sqlQur;
			if(r.getItemId()!=0 && languageId.equals("en")){
				sqlQur="select up.name,icd.itemName from userProfile as up,itemCategoryDetails as icd where icd.itemId="+r.getItemId()+" and icd.categorId="+r.getCategoryId()+" and up.phoneNo='"+r.getPhoneNo()+"'";
				
			}*/
			String district;
			String state;
			String itemName;
			String itemNameQuery;
		//	System.out.println("item id" + r.getItemId() +" subcat id"+  r.getSubCatId()+" cat id "+r.getCategoryId());
			try {
				
				languageId = userService.getUserLanguage(phoneNo);
			} catch (Exception e) {
				
				languageId = "hi";
				e.printStackTrace();
			}
			
				if (r.getItemId() != 0) {
					itemNameQuery = "select icd.itemName from ItemCategoryDetails as icd where icd.id.categoryId="
							+ r.getCategoryId() + " and icd.id.itemId=" + r.getItemId()+ " and icd.id.languageId='"+languageId+"'";
				} else {
					itemNameQuery = "select csd.subCategoryName from CategorySubcategoryDetails as csd where csd.id.catId="
							+ r.getCategoryId() + " and csd.id.subCatId=" + r.getSubCatId()+" and csd.id.languageId='"+languageId+"'";
				}

				try{
					String districtStateQuery="select dd.district,sd.state from tehsilVillageDetails as tvd ,districtDetails as dd ,stateDetails as sd where tvd.id="+r.getStationId()+" and tvd.districtId=dd.districtId and tvd.stateId=dd.stateId and dd.stateId=sd.stateId";
					   List ds=session.createSQLQuery(districtStateQuery).list();
					   Object[] ar=(Object[])ds.get(0);
 					   district=ar[0].toString();
					   state=ar[1].toString();
				}
				catch(Exception e){
					district="";
					state="";
					e.printStackTrace();
				}
			   
			List itemNameList = session.createQuery(itemNameQuery).list();
			try{
				itemName = itemNameList.get(0).toString();
			}
			catch(Exception e){
				
				itemName="";
			}
			String ddMMDate=ddMMM.format(r.getDate()).toString();
			AllPostBean bean = new AllPostBean();
			bean.setDescription(r.getItemDescription()+"\n\nPosted on : "+ddMMDate);
			bean.setImageName(r.getImageName());
			bean.setPackaging(r.getPackaging());
			bean.setPhoneNo(r.getPhoneNo());
			bean.setPrice(r.getExpectedPrice());
			bean.setQuantity(r.getQuantity());
			bean.setPostDate(r.getDate());
			bean.setType("seller");
			bean.setItemName(itemName+"\n\n"+district+","+state);
			try{
				bean.setName(userService.getUserOnPhoneNo(r.getPhoneNo()).getName());	
			}
			catch(Exception e){
				bean.setName("");
			}
			
			beanList.add(bean);

		}

		
		Criteria criteria1 = session.createCriteria(RentItemDetails.class);
		criteria1.addOrder(Order.desc("date"));
		criteria1.add(Restrictions.eq("approval",true));
		criteria1.setMaxResults(15);
		List<RentItemDetails> renterList = criteria1.list();
		for (RentItemDetails r : renterList) {
			//System.out.println("item id" + r.getItemId() +" subcat id"+  r.getSubCatId()+" cat id "+r.getCategoryId());
			String userName;
			String itemName;
			String itemNameQuery;
			String district;
			String state;
			try {
				userName=userService.getUserOnPhoneNo(phoneNo).getName();
				languageId = userService.getUserLanguage(phoneNo);
			} catch (Exception e) {
				userName="";
				languageId = "hi";
				e.printStackTrace();
			}
		
				if (r.getItemId() != 0) {
					itemNameQuery = "select icd.itemName from ItemCategoryDetails as icd where icd.id.categoryId="
							+ r.getCategoryId() + " and icd.id.itemId=" + r.getItemId() +" and icd.id.languageId='"+languageId+"'";
				} else {
					itemNameQuery = "select csd.subCategoryName from CategorySubcategoryDetails as csd where csd.id.catId="
							+ r.getCategoryId() + " and csd.id.subCatId=" + r.getSubCatId()+" and csd.id.languageId='"+languageId+"'";
				}
     
			//	System.out.println("item query :-  "+itemNameQuery);
			
				try{
					String districtStateQuery="select dd.district,sd.state from tehsilVillageDetails as tvd ,districtDetails as dd ,stateDetails as sd where tvd.id="+r.getStationId()+" and tvd.districtId=dd.districtId and tvd.stateId=dd.stateId and dd.stateId=sd.stateId";
					   List ds=session.createSQLQuery(districtStateQuery).list();
					   Object[] ar=(Object[])ds.get(0);
 					   district=ar[0].toString();
					   state=ar[1].toString();
				}
				catch(Exception e){
					district="";
					state="";
					e.printStackTrace();
				}
			List itemNameList = session.createQuery(itemNameQuery).list();
		try{
			itemName = itemNameList.get(0).toString();
		}
		catch(Exception e){
			
			itemName="";
		}
			
			
		   String ddMMDate=ddMMM.format(r.getDate()).toString();
			AllPostBean bean = new AllPostBean();
			bean.setDescription(r.getItemDescription()+"\n\nPosted on : "+ddMMDate);
			bean.setImageName(r.getImageName());
			bean.setPackaging("");
			bean.setPhoneNo(r.getPhoneNo());
			bean.setPrice(r.getExpectedPrice());
			bean.setQuantity("");
			bean.setPostDate(r.getDate());
			bean.setType("rent");
			bean.setItemName(itemName+"\n\n"+district+","+state);
			try{
				bean.setName(userService.getUserOnPhoneNo(r.getPhoneNo()).getName());	
			}
			catch(Exception e){
				bean.setName("");
			}
			
			beanList.add(bean);

		}
		TreeSet<AllPostBean> allPostTree = new TreeSet<>(new AllPostBeanComperator());
		allPostTree.addAll(beanList);
		beanList = new ArrayList<>();
		beanList.addAll(allPostTree);
		return beanList;
	}
	
	

	public List<AllPostBeanWithId> getAllPostWithId(String phoneNo,String date) {
		String languageId;
		//System.out.println("phone no inside getAllPosts" + phoneNo);
		Date startDate;
		Date endDate;
		 try {
			startDate=yyyyMMddhhmmss.parse(date +" 00:00:00");
			endDate=yyyyMMddhhmmss.parse(date +" 23:59:59"); 		
		} catch (ParseException e1) {
			startDate= new Date();
			endDate= new Date();
			e1.printStackTrace();
		}
         
		List<AllPostBeanWithId> beanList = new ArrayList<>();
		Session session = agroMgntSessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(SellerItemDetails.class);
		criteria.add(Restrictions.ge("date", startDate));
		criteria.add(Restrictions.le("date", endDate));
		criteria.addOrder(Order.desc("date"));
		
		List<SellerItemDetails> sellerList = criteria.list();
		for (SellerItemDetails r : sellerList) {
		/*	String sqlQur;
			if(r.getItemId()!=0 && languageId.equals("en")){
				sqlQur="select up.name,icd.itemName from userProfile as up,itemCategoryDetails as icd where icd.itemId="+r.getItemId()+" and icd.categorId="+r.getCategoryId()+" and up.phoneNo='"+r.getPhoneNo()+"'";
				
			}*/
			
			String itemName;
			String itemNameQuery;
			//System.out.println("item id" + r.getItemId() +" subcat id"+  r.getSubCatId()+" cat id "+r.getCategoryId());
			try {
				
				languageId = userService.getUserLanguage(phoneNo);
			} catch (Exception e) {
				
				languageId = "hi";
				e.printStackTrace();
			}
			
				if (r.getItemId() != 0) {
					itemNameQuery = "select icd.itemName from ItemCategoryDetails as icd where icd.id.categoryId="
							+ r.getCategoryId() + " and icd.id.itemId=" + r.getItemId()+ " and icd.id.languageId='"+languageId+"'";
				} else {
					itemNameQuery = "select csd.subCategoryName from CategorySubcategoryDetails as csd where csd.id.catId="
							+ r.getCategoryId() + " and csd.id.subCatId=" + r.getSubCatId()+" and csd.id.languageId='"+languageId+"'";
				}

			
			
			List itemNameList = session.createQuery(itemNameQuery).list();
			try{
				itemName = itemNameList.get(0).toString();
			}
			catch(Exception e){
				
				itemName="";
			}
			AllPostBeanWithId bean = new AllPostBeanWithId();
			bean.setDescription(r.getItemDescription());
			bean.setImageName(r.getImageName());
			bean.setPackaging(r.getPackaging());
			bean.setPhoneNo(r.getPhoneNo());
			bean.setPrice(r.getExpectedPrice());
			bean.setQuantity(r.getQuantity());
			bean.setPostDate(r.getDate());
			bean.setType("seller");
			bean.setItemName(itemName);
			bean.setId(r.getId());
			bean.setApproval(r.isApproval());
			try{
				bean.setName(userService.getUserOnPhoneNo(r.getPhoneNo()).getName());	
			}
			catch(Exception e){
				bean.setName("");
			}
			
			beanList.add(bean);

		}

		
		Criteria criteria1 = session.createCriteria(RentItemDetails.class);
		criteria1.add(Restrictions.ge("date",startDate));
		criteria1.add(Restrictions.le("date", endDate));
		criteria1.addOrder(Order.desc("date"));
		List<RentItemDetails> renterList = criteria1.list();
		for (RentItemDetails r : renterList) {
			//System.out.println("item id" + r.getItemId() +" subcat id"+  r.getSubCatId()+" cat id "+r.getCategoryId());
			String userName;
			String itemName;
			String itemNameQuery;
			try {
				userName=userService.getUserOnPhoneNo(phoneNo).getName();
				languageId = userService.getUserLanguage(phoneNo);
			} catch (Exception e) {
				userName="";
				languageId = "hi";
				e.printStackTrace();
			}
		
				if (r.getItemId() != 0) {
					itemNameQuery = "select icd.itemName from ItemCategoryDetails as icd where icd.id.categoryId="
							+ r.getCategoryId() + " and icd.id.itemId=" + r.getItemId() +" and icd.id.languageId='"+languageId+"'";
				} else {
					itemNameQuery = "select csd.subCategoryName from CategorySubcategoryDetails as csd where csd.id.catId="
							+ r.getCategoryId() + " and csd.id.subCatId=" + r.getSubCatId()+" and csd.id.languageId='"+languageId+"'";
				}
     
			//	System.out.println("item query :-  "+itemNameQuery);
			
			
			List itemNameList = session.createQuery(itemNameQuery).list();
		try{
			itemName = itemNameList.get(0).toString();
		}
		catch(Exception e){
			
			itemName="";
		}
			
			
			
			AllPostBeanWithId bean = new AllPostBeanWithId();
			bean.setDescription(r.getItemDescription());
			bean.setImageName(r.getImageName());
			bean.setPackaging("");
			bean.setPhoneNo(r.getPhoneNo());
			bean.setPrice(r.getExpectedPrice());
			bean.setQuantity("");
			bean.setPostDate(r.getDate());
			bean.setType("rent");
			bean.setItemName(itemName);
			bean.setApproval(r.isApproval());
			bean.setId(r.getId());
			try{
				bean.setName(userService.getUserOnPhoneNo(r.getPhoneNo()).getName());	
			}
			catch(Exception e){
				bean.setName("");
			}
			
			beanList.add(bean);

		}
		TreeSet<AllPostBeanWithId> allPostTree = new TreeSet<>(new AllPostBeanWithIdComparator());
		allPostTree.addAll(beanList);
		beanList = new ArrayList<>();
		beanList.addAll(allPostTree);
		return beanList;
	}

	@Override
	public boolean updateSellerRenterPost(int id, String type) {
		String hql;
		if(type.equals("seller")){
			
			hql="update SellerItemDetails set approval=:approval where id =:id";
		}
		else {
			hql="update RentItemDetails set approval =:approval where id =:id";
		}
		try{
			Session session= agroMgntSessionFactory.getCurrentSession();
			session.createQuery(hql).setBoolean("approval", true).setParameter("id", id).executeUpdate();
			return true;
		}
		catch(Exception e){
			
			e.printStackTrace();
			return false;
		}
		
		
	}

	@Override
	public boolean disapproveSellerRenterPost(int id, String type) {
		String hql;
		if(type.equals("seller")){
			
			hql="update SellerItemDetails set approval=:approval where id =:id";
		}
		else {
			hql="update RentItemDetails set approval =:approval where id =:id";
		}
		try{
			Session session= agroMgntSessionFactory.getCurrentSession();
			session.createQuery(hql).setBoolean("approval", false).setParameter("id", id).executeUpdate();
			return true;
		}
		catch(Exception e){
			
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public List<String> getDistinctDatesOfPosts() {
		String hql="select distinct date(date) from SellerItemDetails order by date";
		Session session=agroMgntSessionFactory.getCurrentSession();
		List dateFromSeller=session.createQuery(hql).list();
		hql="select distinct date(date) from RentItemDetails order by date";
		List dateFromRent=session.createQuery(hql).list();
		Set<String> dateSet=new HashSet<>();
		dateSet.addAll(dateFromRent);
		dateSet.addAll(dateFromSeller);
		dateFromRent=new ArrayList<>(dateSet);
		Collections.sort(dateFromRent);
		return dateFromRent;
	}
	

}
