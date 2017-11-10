package com.bkc.dao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.crypto.Cipher;
import javax.transaction.Transactional;

import org.dom4j.bean.BeanAttributeList;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.bkc.bean.AddShopBean;
import com.bkc.bean.AddShopBeanJsp;
import com.bkc.bean.CorrectedShopBean;
import com.bkc.bean.FarmersportalBean;
import com.bkc.bean.OrganicFarmingBean;
import com.bkc.bean.VillageBean;
import com.bkc.model.AddShopModel;
import com.bkc.model.FertilizersDealers;
import com.bkc.model.Machinery;
import com.bkc.model.OrganicFarming;
import com.bkc.model.PesticideDealers;
import com.bkc.model.SeedDealers;
import com.bkc.model.SeedDealersId;
import com.bkc.model.ShopType;
import com.bkc.service.StationDetailsService;

@Repository
@Transactional
public class FarmersportalDaoImpl implements FarmersportalDao {

	@Autowired
	@Qualifier("agroSessionFactory")
	SessionFactory agroSessionFactory;

	@Autowired
	@Qualifier("farmersPortalSessionFactory")
	SessionFactory sessionFactory;

	@Autowired
	StationDetailsService stationDetailsService;

	@Override
	public List<FarmersportalBean> getFertilizersDealer(float lat, float lon) {

		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(FertilizersDealers.class);
		criteria.add(Restrictions.between("lat", lat - 0.25f, lat + 0.25f));
		criteria.add(Restrictions.between("lon", lon - 0.25f, lon + 0.25f));
		/*
		 * criteria.add(Restrictions.ne("nameOfDealer",""));
		 * criteria.add(Restrictions.ne("address",""));
		 * criteria.add(Restrictions.ne("contact",""));
		 * criteria.add(Restrictions.ne("contact","0000000000"));
		 */
		ProjectionList plist = Projections.projectionList();
		plist.add(Projections.distinct(Projections.property("nameOfDealer")));
		plist.add(Projections.property("address"));
		plist.add(Projections.property("contact"));
		criteria.setProjection(plist);
		List dealerList = criteria.list();
		return convertToBeanList(dealerList);
	}

	@Override
	public List<FarmersportalBean> getSeedDealer(float lat, float lon) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(SeedDealers.class);
		criteria.add(Restrictions.between("lat", lat - 0.25f, lat + 0.25f));
		criteria.add(Restrictions.between("lon", lon - 0.25f, lon + 0.25f));
		/*
		 * criteria.add(Restrictions.ne("contactPerson",""));
		 * criteria.add(Restrictions.ne("address",""));
		 * criteria.add(Restrictions.ne("phone",""));
		 * criteria.add(Restrictions.ne("phone","0000000000"));
		 */
		ProjectionList plist = Projections.projectionList();
		plist.add(Projections.distinct(Projections.property("contactPerson")));
		plist.add(Projections.property("address"));
		plist.add(Projections.property("phone"));
		criteria.setProjection(plist);
		List dealerList = criteria.list();

		return convertToBeanList(dealerList);
	}

	@Override
	public List<FarmersportalBean> getPesticideDealer(float lat, float lon) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(PesticideDealers.class);
		criteria.add(Restrictions.between("lat", lat - 0.25f, lat + 0.25f));
		criteria.add(Restrictions.between("lon", lon - 0.25f, lon + 0.25f));
		/*
		 * criteria.add(Restrictions.ne("dealerName",""));
		 * criteria.add(Restrictions.ne("address",""));
		 * criteria.add(Restrictions.ne("mobile",""));
		 * criteria.add(Restrictions.ne("mobile","0000000000"));
		 */
		ProjectionList plist = Projections.projectionList();
		plist.add(Projections.distinct(Projections.property("dealerName")));
		plist.add(Projections.property("address"));
		plist.add(Projections.property("mobile"));
		criteria.setProjection(plist);
		List dealerList = criteria.list();
		return convertToBeanList(dealerList);
	}

	public List<FarmersportalBean> convertToBeanList(List list) {
		List<FarmersportalBean> beanList = new ArrayList<>();
		FarmersportalBean bean = null;
		if (list.size() > 0) {

			for (Object obj : list) {
				Object[] arr = (Object[]) obj;
				if (arr.length > 2) {
					bean = new FarmersportalBean();
					bean.setName(arr[0].toString());
					bean.setAddress(arr[1].toString());
					String usrNum = arr[2].toString();
					usrNum = usrNum.replaceAll("Mob.:", "").replaceAll("Mob:", "").replaceAll("Ph.:", "")
							.replaceAll("Ph:", "").replaceAll("\n", "").replaceAll("\n\n", "").replaceAll(" ", "");
					bean.setContact(usrNum);
					// System.out.println(bean.getContact());
					beanList.add(bean);
				}
			}

		}
		return beanList;
	}

	@Override
	public List<FarmersportalBean> getMachineryDetails(int villageId) {

		String state = "";
		Session agroSession = agroSessionFactory.getCurrentSession();
		List stateList = agroSession
				.createSQLQuery("select sd.state from stateDetails as sd,tehsilVillageDetails as tvd where tvd.id="
						+ villageId + " and tvd.stateId=sd.stateId;")
				.list();
		state = stateList.size() > 0 ? stateList.get(0).toString() : "null";
		// System.out.println("state inside machinery"+state);
		Session session = sessionFactory.getCurrentSession();
		String sql = "select company,address,Case  when (mobile ='null' and phone !='null') then phone when (mobile !='null' and phone ='null') then mobile else mobile end from Machinery1 where state regexp '"
				+ state + "' and (phone!='null' or mobile!='null')";
		List machineryList = session.createSQLQuery(sql).list();
		List<FarmersportalBean> beanList = new ArrayList<>();
		for (Object obj : machineryList) {
			FarmersportalBean bean = new FarmersportalBean();

			Object[] arr = (Object[]) obj;
			bean.setName(arr[0].toString());
			bean.setAddress(arr[1].toString());
			String usrNum = arr[2].toString();
			usrNum = usrNum.replaceAll("Mob.:", "").replaceAll("Mob:", "").replaceAll("Ph.:", "").replaceAll("Ph:", "")
					.replaceAll("\n", "").replaceAll("\n\n", "").replaceAll(" ", "");
			bean.setContact(usrNum);
			// System.out.println(bean.getContact());
			beanList.add(bean);
		}

		return beanList;
	}

	@Override
	public List<OrganicFarmingBean> getOrganicFarmingList() {
		Session session = agroSessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(OrganicFarming.class);
		criteria.addOrder(Order.desc("postDate"));
		criteria.setMaxResults(10);
		List<OrganicFarming> tempList = criteria.list();
		List<OrganicFarmingBean> finalList = new ArrayList<>();
		for (OrganicFarming og : tempList) {
			OrganicFarmingBean bean = new OrganicFarmingBean();
			bean.setCropName(og.getCropName());
			bean.setDescription(og.getDescription());
			bean.setImage(og.getImage());
			bean.setLanguageId(og.getLanguageId());
			bean.setLink(og.getLink());
			bean.setPostDate(og.getPostDate());
			bean.setVideo(og.getVideo());
			bean.setId(og.getId());
			finalList.add(bean);

		}
		tempList = null;
		return finalList;
	}

	@Override
	public void saveShop(AddShopBean bean) throws Exception {

		Session session = sessionFactory.getCurrentSession();
		AddShopModel model = new AddShopModel();
		model.setAddress(bean.getAddress());
		model.setImageName(bean.getImageName());
		model.setDescription(bean.getDescription());
		model.setName(bean.getName());
		model.setPhoneNo(bean.getPhoneNo());
		model.setShopName(bean.getShopName());
		model.setStationId(bean.getStationId());
		Set<ShopType> typeSet = new HashSet<>();
		for (String type : bean.getShopType()) {
			if (type.equals("Seed")) {
				typeSet.add(ShopType.Seed);
			} else if (type.equals("Fertilizer")) {
				typeSet.add(ShopType.Fertilizer);
			} else if (type.equals("Pesticide")) {
				typeSet.add(ShopType.Pesticide);
			} else if (type.equals("Machinery")) {
				typeSet.add(ShopType.Machinery);
			}
		}
		model.setType(typeSet);
		// getting lat lon on village Id
		VillageBean vBean = stationDetailsService.getVillageOnId(bean.getStationId());
		model.setLat(vBean.getLat());
		model.setLon(vBean.getLon());

		session.save(model);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bkc.dao.FarmersportalDao#verifyShop(int)
	 */
	@Override
	public boolean verifyShop(CorrectedShopBean bean) {

		Session session = sessionFactory.getCurrentSession();
		AddShopModel toVerify = (AddShopModel) session.createCriteria(AddShopModel.class)
				.add(Restrictions.eq("id", bean.getId())).uniqueResult();
		System.out.println("types are :-" + toVerify.getType());
		String typeString = toVerify.getType().toString();
		typeString = typeString.substring(1, typeString.length() - 1);
		String []arr=typeString.split(",");
		
		session.createQuery("update AddShopModel set verified =1 where id=:id").setInteger("id",bean.getId()).executeUpdate();
		
			for (String temp :arr ) {
				temp=temp.trim();
				System.out.println("array is=-"+temp);		
				if (temp.equals("Seed")) {
	           System.out.println("inside seed saving");
					SeedDealers seed= new SeedDealers();
					SeedDealersId seedId= new SeedDealersId();
					seedId.setBcod("Seed");
					seedId.setDealerId(toVerify.getPhoneNo().trim());
					seed.setId(seedId);
					seed.setLat(toVerify.getLat());
					seed.setLon(toVerify.getLon());
					seed.setAddress(bean.getAddress());
					seed.setPhone(toVerify.getPhoneNo());
					seed.setContactPerson(toVerify.getName());
					System.out.println("session . save seed");
					try{
						session.save(seed);
					}catch(Exception e){
						e.printStackTrace();
					}
					
					//session.clear();
					
				}
				if (temp.equals("Fertilizer")) {
					System.out.println("inside f saving");
	                FertilizersDealers fDealer= new FertilizersDealers();
	                fDealer.setAddress(bean.getAddress());
	                fDealer.setNameOfDealer(toVerify.getName());
	                fDealer.setContact(toVerify.getPhoneNo());
	                fDealer.setLat(toVerify.getLat());
	                fDealer.setLon(toVerify.getLon());
	                //fDealer.setSno(Integer.parseInt(toVerify.getPhoneNo()));
	                try{
						session.save(fDealer);
					}catch(Exception e){
						e.printStackTrace();
					}
					//session.clear();
					
				}
				if (temp.equals("Pesticide")) {
					System.out.println("inside p saving");
	                 PesticideDealers pDealer= new PesticideDealers();
					// pDealer.setSrN(Integer.parseInt(toVerify.getPhoneNo().trim()));
					 pDealer.setLat(toVerify.getLat());
					 pDealer.setLon(toVerify.getLon());
					 pDealer.setAddress(bean.getAddress());
					 pDealer.setDealerName(toVerify.getName());
					 pDealer.setMobile(toVerify.getPhoneNo());
					 try{
							session.save(pDealer);
						}catch(Exception e){
							e.printStackTrace();
						}
					// session.clear();
				}
				if (temp.equals("Machinery")) {
					System.out.println("M");
					VillageBean b=stationDetailsService.getVillageOnId(toVerify.getStationId());
					Machinery mDealer= new Machinery();
					mDealer.setState(b.getState());
					mDealer.setMobile(toVerify.getPhoneNo());
					mDealer.setCompany(bean.getShopName());
					mDealer.setAddress(bean.getAddress());
					try{
						session.save(mDealer);
					}catch(Exception e){
						e.printStackTrace();
					}
					//session.clear();
					
				}
			}	
			return true;
		
		//return true;
	}

	@Override
	public List<AddShopBeanJsp> getShopList(){
		Session session=sessionFactory.getCurrentSession();
		Criteria c= session.createCriteria(AddShopModel.class);
		c.add(Restrictions.eq("verified", false));
		c.addOrder(Order.asc("id"));
		List<AddShopModel> modelList=c.list();
		List<AddShopBeanJsp> beanList= new ArrayList<>();
		for(AddShopModel m:modelList){
			AddShopBeanJsp bean = new AddShopBeanJsp();
			bean.setId(m.getId());
			bean.setAddress(m.getAddress());
			bean.setName(m.getName());
			bean.setPhoneNo(m.getPhoneNo());
			bean.setShopName(m.getShopName());
			beanList.add(bean);
			bean.setType(m.getType().toString());
			bean.setDescription(m.getDescription());
			bean.setImageName(m.getImageName());
		}
	  return beanList;
	}
}
