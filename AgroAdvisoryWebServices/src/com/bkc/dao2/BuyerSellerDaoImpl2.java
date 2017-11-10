/**
 * 
 */
package com.bkc.dao2;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bkc.bean.AllPostBean;
import com.bkc.bean.AllPostBeanComperator;
import com.bkc.bean2.AllPostBean2;
import com.bkc.model.buyerseller.RentItemDetails;
import com.bkc.model.buyerseller.SellerItemDetails;
import com.bkc.service.UserDetailsService;

/**
 * @author Akash
 *
 * @Date 20-Sep-2017
 */

@Repository
@Transactional
public class BuyerSellerDaoImpl2 implements BuyerSellerDao2 {

	@Autowired
	@Qualifier(value = "agroSessionFactory")
	SessionFactory agroMgntSessionFactory;

	@Autowired
	UserDetailsService userService;

	SimpleDateFormat yyyyMMddhhmmss = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	SimpleDateFormat ddMMM = new SimpleDateFormat("dd MMM yyyy");

	@Override
	public AllPostBean2 getAllPost(String phoneNo, int sellerId, int renterId) {

		AllPostBean2 bean2 = new AllPostBean2();
		String languageId;
		//System.out.println("phone no inside getAllPosts" + phoneNo);

		List<AllPostBean> beanList = new ArrayList<>();
		Session session = agroMgntSessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(SellerItemDetails.class);
		criteria.add(Restrictions.eq("approval", true));
		criteria.addOrder(Order.desc("date"));
		criteria.setMaxResults(5);
		if (sellerId != 0) {
			criteria.add(Restrictions.lt("id", sellerId));
		}

		List<SellerItemDetails> sellerList = criteria.list();
		int sId;
		
		try{
			sId = sellerList.get(sellerList.size() - 1).getId();
			for (SellerItemDetails r : sellerList) {

				String district;
				String state;
				String itemName;
				String itemNameQuery;
			//	System.out.println(	"item id" + r.getItemId() + " subcat id" + r.getSubCatId() + " cat id " + r.getCategoryId());
				try {

					languageId = userService.getUserLanguage(phoneNo);
				} catch (Exception e) {

					languageId = "hi";
					e.printStackTrace();
				}

				if (r.getItemId() != 0) {
					itemNameQuery = "select icd.itemName from ItemCategoryDetails as icd where icd.id.categoryId="
							+ r.getCategoryId() + " and icd.id.itemId=" + r.getItemId() + " and icd.id.languageId='"
							+ languageId + "'";
				} else {
					itemNameQuery = "select csd.subCategoryName from CategorySubcategoryDetails as csd where csd.id.catId="
							+ r.getCategoryId() + " and csd.id.subCatId=" + r.getSubCatId() + " and csd.id.languageId='"
							+ languageId + "'";
				}

				try {
					String districtStateQuery = "select dd.district,sd.state from tehsilVillageDetails as tvd ,districtDetails as dd ,stateDetails as sd where tvd.id="
							+ r.getStationId()
							+ " and tvd.districtId=dd.districtId and tvd.stateId=dd.stateId and dd.stateId=sd.stateId";
					List ds = session.createSQLQuery(districtStateQuery).list();
					Object[] ar = (Object[]) ds.get(0);
					district = ar[0].toString();
					state = ar[1].toString();
				} catch (Exception e) {
					district = "";
					state = "";
					e.printStackTrace();
				}

				List itemNameList = session.createQuery(itemNameQuery).list();
				try {
					itemName = itemNameList.get(0).toString();
				} catch (Exception e) {

					itemName = "";
				}
				String ddMMDate = ddMMM.format(r.getDate()).toString();
				AllPostBean bean = new AllPostBean();
				bean.setDescription(r.getItemDescription() + "\n\nPosted on : " + ddMMDate);
				bean.setImageName(r.getImageName());
				bean.setPackaging(r.getPackaging());
				bean.setPhoneNo(r.getPhoneNo());
				bean.setPrice(r.getExpectedPrice());
				bean.setQuantity(r.getQuantity());
				bean.setPostDate(r.getDate());
				bean.setType("seller");
				bean.setItemName(itemName + "\n\n" + district + "," + state);
				try {
					bean.setName(userService.getUserOnPhoneNo(r.getPhoneNo()).getName());
				} catch (Exception e) {
					bean.setName("");
				}

				beanList.add(bean);

			}
		}catch(ArrayIndexOutOfBoundsException e){
			sId=0;
			e.printStackTrace();
		}
		 
	

		Criteria criteria1 = session.createCriteria(RentItemDetails.class);
		criteria1.addOrder(Order.desc("date"));
		criteria1.add(Restrictions.eq("approval", true));
		criteria1.setMaxResults(5);
		if (renterId != 0) {
			criteria1.add(Restrictions.lt("id", renterId));
		}
		List<RentItemDetails> renterList = criteria1.list();
		int rId;
		try{
			rId = renterList.get(renterList.size() - 1).getId();
			for (RentItemDetails r : renterList) {
				//System.out.println("item id" + r.getItemId() + " subcat id" + r.getSubCatId() + " cat id " + r.getCategoryId());
				String userName;
				String itemName;
				String itemNameQuery;
				String district;
				String state;
				try {
					userName = userService.getUserOnPhoneNo(phoneNo).getName();
					languageId = userService.getUserLanguage(phoneNo);
				} catch (Exception e) {
					userName = "";
					languageId = "hi";
					e.printStackTrace();
				}

				if (r.getItemId() != 0) {
					itemNameQuery = "select icd.itemName from ItemCategoryDetails as icd where icd.id.categoryId="
							+ r.getCategoryId() + " and icd.id.itemId=" + r.getItemId() + " and icd.id.languageId='"
							+ languageId + "'";
				} else {
					itemNameQuery = "select csd.subCategoryName from CategorySubcategoryDetails as csd where csd.id.catId="
							+ r.getCategoryId() + " and csd.id.subCatId=" + r.getSubCatId() + " and csd.id.languageId='"
							+ languageId + "'";
				}

			//	System.out.println("item query :-  " + itemNameQuery);

				try {
					String districtStateQuery = "select dd.district,sd.state from tehsilVillageDetails as tvd ,districtDetails as dd ,stateDetails as sd where tvd.id="
							+ r.getStationId()
							+ " and tvd.districtId=dd.districtId and tvd.stateId=dd.stateId and dd.stateId=sd.stateId";
					List ds = session.createSQLQuery(districtStateQuery).list();
					Object[] ar = (Object[]) ds.get(0);
					district = ar[0].toString();
					state = ar[1].toString();
				} catch (Exception e) {
					district = "";
					state = "";
					e.printStackTrace();
				}
				List itemNameList = session.createQuery(itemNameQuery).list();
				try {
					itemName = itemNameList.get(0).toString();
				} catch (Exception e) {

					itemName = "";
				}

				String ddMMDate = ddMMM.format(r.getDate()).toString();
				AllPostBean bean = new AllPostBean();
				bean.setDescription(r.getItemDescription() + "\n\nPosted on : " + ddMMDate);
				bean.setImageName(r.getImageName());
				bean.setPackaging("");
				bean.setPhoneNo(r.getPhoneNo());
				bean.setPrice(r.getExpectedPrice());
				bean.setQuantity("");
				bean.setPostDate(r.getDate());
				bean.setType("rent");
				bean.setItemName(itemName + "\n\n" + district + "," + state);
				try {
					bean.setName(userService.getUserOnPhoneNo(r.getPhoneNo()).getName());
				} catch (Exception e) {
					bean.setName("");
				}

				beanList.add(bean);

			}

		}catch(ArrayIndexOutOfBoundsException e){
		
			e.printStackTrace();
			rId=0;
		    	
		}
		 
		
		TreeSet<AllPostBean> allPostTree = new TreeSet<>(new AllPostBeanComperator());
		allPostTree.addAll(beanList);
		beanList = new ArrayList<>();
		beanList.addAll(allPostTree);
		if (beanList.size() < 10) {
			renterId = 0;
			sellerId = 0;
		}
		bean2.setPostList(beanList);

		bean2.setRenterId(rId);
		bean2.setSellerId(sId);
		return bean2;
	}

}
