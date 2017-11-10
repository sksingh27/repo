package com.bkc.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.bkc.bean.AddNewCropPojo;
import com.bkc.bean.AdvertisementBean;
import com.bkc.bean.CropBean;
import com.bkc.bean.CropBean2;
import com.bkc.bean.CropCategoryBean;
import com.bkc.bean.CropDetailsBeanList;
import com.bkc.bean.ForecastBean;
import com.bkc.bean.MandiBean;
import com.bkc.bean.MandiChartBean;
import com.bkc.bean.MandiRateBean;
import com.bkc.bean.NcdexBean;
import com.bkc.bean.RegistrationPojo;
import com.bkc.bean.UserFeedbackBean;
import com.bkc.bean.UserProfileRegistrationBean;
import com.bkc.bean.VillageBean;
import com.bkc.bean.VillageLevelBean;
import com.bkc.bean.buyerseller.AllPostBeanWithId;
import com.bkc.bean.buyerseller.MandiChartMap;
import com.bkc.model.CropDetails;
import com.bkc.model.CropVarietyDetail;
import com.bkc.model.StationDetails;
import com.bkc.model.UserDetails;
import com.bkc.service.AdvisoryService;
import com.bkc.service.BuyerSellerService;
import com.bkc.service.CropDetailsService;
import com.bkc.service.CropVarityDetailService;
import com.bkc.service.ForecastService;
import com.bkc.service.MandiOilService;
import com.bkc.service.StateService;
import com.bkc.service.StationDetailsService;
import com.bkc.service.UserDetailsService;
import com.bkc.staticclasses.GetRoundedLatLon;

@RestController
public class TestController {

	HttpSession session;

	public void setSession(HttpSession session) {
		this.session = session;
	}

	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat ddMMyyyy = new SimpleDateFormat("dd-MM-yyyy");

	@Resource
	private TestController foo;

	@Autowired(required = true)
	UserDetailsService userDetailsService;
	@Autowired
	AdvisoryService advisoryService;

	@Autowired
	ForecastService forecastService;

	@Autowired
	private CropVarityDetailService rservice;

	@Autowired
	private StationDetailsService stationdService;

	@Autowired
	private UserDetailsService uService;

	@Autowired
	private CropDetailsService cropdService;

	@Autowired
	private StateService stateService;

	@Autowired
	private MandiOilService mandiOilService;

	@Autowired
	private BuyerSellerService buyerSellerService;

	@RequestMapping(value = { "/" })
	public @ResponseBody ModelAndView testPage(HttpServletRequest request) {
		ModelAndView m = new ModelAndView("buyerseller");

		/*
		 * System.out.println("remte address is " + request.getRemoteAddr());
		 * System.out.println("remote host is" + request.getRemoteHost());
		 * System.out.println("remote user" + request.getRemoteUser());
		 * 
		 * 
		 * File folder = new File("/home/dmdd/Desktop/fasalsalah/feedback");
		 * File[] listOfFiles = folder.listFiles(); List<String>
		 * datesInFeedbaclFile = new ArrayList<>(); for (int i = 0; i <
		 * listOfFiles.length; i++) { String dateInFileName; if
		 * (listOfFiles[i].isFile()) { String fileName =
		 * listOfFiles[i].getName(); // System.out.println(fileName); if
		 * (!fileName.contains("null")) { if (fileName.contains("Advisory")) {
		 * System.out.println("date is when file name contains advisory" +
		 * fileName.substring(22, 30)); dateInFileName = fileName.substring(20,
		 * 30); dateInFileName = dateInFileName.replaceAll("_", "-"); if
		 * (!datesInFeedbaclFile.contains(dateInFileName)) {
		 * datesInFeedbaclFile.add(dateInFileName); } } else {
		 * System.out.println("date is " + fileName.substring(17, 25));
		 * dateInFileName = fileName.substring(15, 25); dateInFileName =
		 * dateInFileName.replaceAll("_", "-"); if
		 * (!datesInFeedbaclFile.contains(dateInFileName)) {
		 * datesInFeedbaclFile.add(dateInFileName); } } }
		 * 
		 * } else if (listOfFiles[i].isDirectory()) { System.out.println(
		 * "Directory " + listOfFiles[i].getName()); } }
		 * Collections.sort(datesInFeedbaclFile);
		 * Collections.reverse(datesInFeedbaclFile); m.addObject("dates",
		 * datesInFeedbaclFile);
		 */

		String date = dateFormat.format(new Date());

		List<AllPostBeanWithId> beanList = buyerSellerService.getAllPostWithId("0000000000", date);
		List<String> dates = buyerSellerService.getDistinctDatesOfPosts();

		/*
		 * count.getListOfUsers(); count.printUserMapResult();
		 */

		m.addObject("dates", dates);
		m.addObject("beanlist", beanList);

		return m;
	}

	@RequestMapping(value = { "getposts/{date}" })
	public @ResponseBody JSONArray getPostOnDate(HttpServletRequest request, @PathVariable String date) {

		SimpleDateFormat format = new SimpleDateFormat("dd-MMM yyyy");

		List<AllPostBeanWithId> beanList = buyerSellerService.getAllPostWithId("0000000000", date);
		JSONArray array = new JSONArray();
		for (AllPostBeanWithId temp : beanList) {

			JSONObject obj = new JSONObject();
			obj.put("id", temp.getId());
			obj.put("desc", temp.getDescription());
			obj.put("phoneNo", temp.getPhoneNo());
			obj.put("date", format.format(temp.getPostDate()).toString());
			obj.put("name", temp.getName());
			obj.put("imageName", temp.getImageName());
			obj.put("itemName", temp.getItemName());
			obj.put("price", temp.getPrice());
			obj.put("type", temp.getType());
			obj.put("approval", temp.isApproval());
			array.add(obj);
		}

		return array;
	}

	@RequestMapping("/advertisement/{phoneNo}")
	public @ResponseBody ResponseEntity<AdvertisementBean> returnAdvertisementNames(@PathVariable String phoneNo) {
		try {

			// return new ResponseEntity<List<String>>(new
			// ArrayList<String>(),HttpStatus.OK);
			return new ResponseEntity<AdvertisementBean>(userDetailsService.getAdvertisementOnUserLocation(phoneNo),
					HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<AdvertisementBean>(new AdvertisementBean(), HttpStatus.OK);
		}

	}

	@RequestMapping("/feedbackOnDate/{date}")
	public @ResponseBody ResponseEntity<Map<String, UserFeedbackBean>> returnFeedback(@PathVariable String date) {
		date = date.replaceAll("-", "_");
		File folder = new File("/home/dmdd/Desktop/fasalsalah/feedback");
		File[] listOfFiles = folder.listFiles();
		Map<String, UserFeedbackBean> feedbackMap = new HashMap<>();
		for (int i = 0; i < listOfFiles.length; i++) {

			if (listOfFiles[i].isFile()) {
				String fileName = listOfFiles[i].getName();
				String phoneNo;
				if (fileName.contains(date) && !fileName.contains("null")) {

					phoneNo = fileName.substring(0, 10);
					//System.out.println(phoneNo);
					if (feedbackMap.containsKey(phoneNo)) {
						if (fileName.contains("Advisory")) {
							feedbackMap.get(phoneNo).getAudioFile().add(fileName);
						} else {
							feedbackMap.get(phoneNo).getImageFile().add(fileName);
						}

					} else {
						List<String> audioFile = new ArrayList<>();
						List<String> imageFile = new ArrayList<>();
						UserFeedbackBean bean = new UserFeedbackBean();
						bean.setAudioFile(audioFile);
						bean.setImageFile(imageFile);
						bean.setDate(date);
						feedbackMap.put(phoneNo, bean);
						if (fileName.contains("Advisory")) {
							feedbackMap.get(phoneNo).getAudioFile().add(fileName);
						} else {
							feedbackMap.get(phoneNo).getImageFile().add(fileName);
						}
					}
				}

			} else if (listOfFiles[i].isDirectory()) {
				//System.out.println("Directory " + listOfFiles[i].getName());
			}
		}

		return new ResponseEntity<>(feedbackMap, HttpStatus.OK);
	}

	@GetMapping("/getUsers/{phoneNo}")
	public ResponseEntity<List<CropBean>> getName(@PathVariable String phoneNo) {

		List<CropBean> beanList = advisoryService.getUserCrops(phoneNo);

		return new ResponseEntity<List<CropBean>>(beanList, HttpStatus.OK);
	}

	@GetMapping("/sowingdate/{phoneNo}")
	public ResponseEntity<List<CropBean2>> getCrop(@PathVariable String phoneNo)
	
	{
		
		return new ResponseEntity<List<CropBean2>>(advisoryService.getUserCrops2(phoneNo),HttpStatus.OK);
		
	}
	@SuppressWarnings("unchecked")
	@GetMapping("/getRegistration/{phoneNo}/{tokenNo}")
	public ResponseEntity<List<CropDetails>> getRegistration(@PathVariable String phoneNo, @PathVariable String tokenNo,
			HttpSession session) {

		List<UserDetails> userList = userDetailsService.getUserDetailsOnPhoneNumber(phoneNo);
		userDetailsService.tokenRegistration(phoneNo, tokenNo);

		if (!userList.isEmpty()) {
			UserDetails details = userList.get(0);
			List<CropDetails> de = details.getCropDetails();
			CropDetailsBeanList list = new CropDetailsBeanList();
			list.setCropList(de);
			//System.out.println("inside webservice getUsers with phone no" + phoneNo);
			//System.out.println("size of list" + de.size());
			setSession(session);
			this.session.setAttribute("userId", details.getUserId());

			return new ResponseEntity<List<CropDetails>>(de, HttpStatus.OK);

		} else {
		//	System.out.println("user not found");
			return null;
		}
	}

	@GetMapping("/getVersion")
	public ResponseEntity<String> getVersion() {
		String version = "1.12";

		return new ResponseEntity<String>(version, HttpStatus.OK);
	}

	@GetMapping("/getState")
	public ResponseEntity<List<String>> getState() {
		List<String> stateList = rservice.getState();
		return new ResponseEntity<List<String>>(stateList, HttpStatus.OK);
	}

	@RequestMapping("/mandiCrops/{phoneNo}")
	public ResponseEntity<CropCategoryBean> getMandiCropNames(@PathVariable String phoneNo) {
		CropCategoryBean crops = forecastService.getMandiCrops(phoneNo);
		return new ResponseEntity<CropCategoryBean>(crops, HttpStatus.OK);
	}

	@SuppressWarnings({ "unused", "unchecked" })
	@GetMapping("/getMandi/{phoneNo}/{cropName}")
	public ResponseEntity<MandiRateBean> displayMandi(@PathVariable String phoneNo, @PathVariable String cropName,
			HttpSession httpSession) {
		
		
		float lat = 0, lon = 0;
		UserProfileRegistrationBean bean = userDetailsService.getUserProfileOnPhoneNo(phoneNo);
		VillageBean villageBean = stationdService.getVillageOnId(bean.getVillageId());
		lat = villageBean.getLat();
		lon = villageBean.getLon();
	//	System.out.println("state is --------->"+villageBean.getState());
		//System.out.println("lat in mandi :-" + lat);
	//	System.out.println("l0t in mandi :-" + lon);
		String url = "getMandi : Phone No :- " + phoneNo;
		foo.writingFile(url);
		List<MandiBean> mandifinallist = forecastService.getMandi(lat, lon, cropName, phoneNo,villageBean.getState());
		MandiRateBean xlBean = mandiOilService.getmandiOilDetailsCropSpecific(cropName, lat, lon);
		xlBean.setAgmarkList(mandifinallist);
		xlBean.setMandiDescription(GetRoundedLatLon.priceDescriptionOfCrop(cropName));

		return new ResponseEntity<MandiRateBean>(xlBean, HttpStatus.OK);
	}

	@SuppressWarnings({ "unused", "unchecked" })
	@GetMapping("/getchartmandi/{phoneNo}/{cropName}")
	public ResponseEntity<MandiChartMap> displayMandiChart(@PathVariable String phoneNo, @PathVariable String cropName,
			HttpSession httpSession) {
		try {
			Map<String, List<MandiChartBean>> beanList = mandiOilService.mandiRatesOnChart(phoneNo, cropName);
			Set<Entry<String, List<MandiChartBean>>> entrySet = beanList.entrySet();
			List<Entry<String, List<MandiChartBean>>> listEntry = new ArrayList<>(entrySet);
			int size = 0;
			List<MandiChartBean> tempList = new ArrayList<>();
			List<String> dates = new ArrayList<>();
			for (Entry<String, List<MandiChartBean>> e : listEntry) {

				if (e.getValue().size() > size) {
					tempList = e.getValue();
					size = e.getValue().size();
				}

			}
			for (MandiChartBean temp : tempList) {

				String d = ddMMyyyy.format(dateFormat.parse(temp.getDate()));
				//System.out.println(d);
				dates.add(d);
			}
			MandiChartMap map = new MandiChartMap();
			map.setMandiMap(beanList);
			map.setDates(dates);
			return new ResponseEntity<MandiChartMap>(map, HttpStatus.OK);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<MandiChartMap>(new MandiChartMap(), HttpStatus.OK);

		}

	}

	private List<StationDetails> fetchStation(String state) {

		List<StationDetails> stateList = new ArrayList<StationDetails>();
		stateList = stationdService.getStation(state);
		//System.out.println(" station size: " + stateList.size());

		return stateList;
	}

	private List<CropVarietyDetail> fetchVariety(String state, int cropId) {

		//System.out.println("-- fetch state ----");

		List<CropVarietyDetail> varList = new ArrayList<CropVarietyDetail>();
		varList = rservice.getVariety(state, cropId);

		//System.out.println(" state size: " + varList.size());
		return varList;
	}

	// for new app
	@SuppressWarnings({ "unused", "unchecked" })
	@GetMapping("/getForecast/{phoneNo}/{noOfDays}")
	public ResponseEntity<List<ForecastBean>> weatherForecast(@PathVariable String phoneNo,
			@PathVariable String noOfDays, HttpSession httpSession) {

		UserProfileRegistrationBean bean = userDetailsService.getUserProfileOnPhoneNo(phoneNo);
		List<ForecastBean> forecastfinallist = new ArrayList<>();
		if (!bean.equals(null)) {
			VillageBean villageBean = stationdService.getVillageOnId(bean.getVillageId());
			float lat = villageBean.getLat();
			float lon = villageBean.getLon();
			//System.out.println(lat + " ***** " + lon);
			String url = "getForecast : Phone No :- " + phoneNo + " lat:lon " + lat + ":" + lon;
			foo.writingFile(url);
			try {
			
				forecastfinallist = forecastService.getForecast(lat, lon, bean.getVillageId(), Integer.parseInt(noOfDays));
				return new ResponseEntity<List<ForecastBean>>(forecastfinallist, HttpStatus.OK);
			} catch (Exception e) {
				//System.out.println("-------------------------->>error in generating forecast<<--------------------------");
				e.printStackTrace();
			}
			
			
		}
		return new ResponseEntity<List<ForecastBean>>(forecastfinallist, HttpStatus.OK);

	}

	@GetMapping("/getCrops")
	public ResponseEntity<List<CropDetails>> getAllCrops() {
		List<CropDetails> list = advisoryService.getCrop();
		return new ResponseEntity<List<CropDetails>>(list, HttpStatus.OK);
	}

	@GetMapping("/getNewCrops/{phoneNo}")
	public ResponseEntity<List<CropBean>> getAllNewCrops(@PathVariable String phoneNo) {
		//System.out.println("get new crops,,,,,,,,,,,,,!!!");
		List<CropBean> list = advisoryService.getNewCrop(phoneNo);
		return new ResponseEntity<List<CropBean>>(list, HttpStatus.OK);
	}

	@RequestMapping(value = "/getUserRegistration", method = RequestMethod.POST)
	public ResponseEntity<String> createEmployee(@RequestBody RegistrationPojo pojo) {
		String url = "getRegistration : Phone No :- " + pojo.getPhone();
		foo.writingFile(url);
		//System.out.println(pojo.getCrop());
		userDetailsService.addUser(pojo);
		return new ResponseEntity(HttpStatus.CREATED);
	}

	@RequestMapping(value = "/getUserRegistrations", method = RequestMethod.POST)
	public String createEmployeeViaBrowser(@ModelAttribute RegistrationPojo pojo) {
		String url = "getRegistration : Phone No :- " + pojo.getPhone();
		foo.writingFile(url);
		//System.out.println(pojo.getCrop());
		int result = userDetailsService.addUserFromPage(pojo);
		if (result == 0) {
			//System.out.println("Save successfully");
			return "Save Succesfully";
		} else {
			//System.out.println("Not Save successfully");
			return "Already Registered...";
		}
	}

	@RequestMapping(value = "/getAddNewCrop", method = RequestMethod.POST)
	public ResponseEntity<String> addNewCrop(@RequestBody AddNewCropPojo pojo) {
		//System.out.println(pojo.getCrop() + " " + pojo.getSowingdate() + " " + pojo.getState() + " " + pojo.getPhone());
		String url = "getAddNewCrop : Phone No :- " + pojo.getPhone() + " Crop Name :- " + pojo.getCrop();
		//System.out.println(pojo.getVillageId());
		foo.writingFile(url);
		userDetailsService.addUserCrop(pojo);

		return new ResponseEntity(HttpStatus.CREATED);
	}

	@RequestMapping(value = "/getDists", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public String getDistrictsOnStates(@RequestParam("dist") String state) {
		System.out.println("Cropid :- " + state);
		StringBuilder getstate = new StringBuilder();
		List<String> finaList = stationdService.getDistrictonState(state);
		getstate.append("<option value='Please Select Station'>Please Select Station</option>");
		for (String str : finaList) {
			getstate.append("<option value='" + str + "'>" + str + "</option>");
		}
		//System.out.println("return stae " + getstate);
		return getstate.toString();
	}

	@GetMapping("/getDist/{state}")
	public ResponseEntity<List<String>> getDistrictsOnState(@PathVariable String state) {
		List<String> finaList = stationdService.getDistrictonState(state);

		return new ResponseEntity<List<String>>(finaList, HttpStatus.OK);
	}

	@GetMapping("/getVillage/{state}/{district}/{tehsil:.+}")
	public ResponseEntity<List<VillageLevelBean>> getVillageOnTehsil(@PathVariable String state,
			@PathVariable String district, @PathVariable String tehsil) {
		//System.out.println("tehsil------------->"+tehsil);
		List<VillageLevelBean> finaList = stationdService.getTehsil(state, district, tehsil);
		return new ResponseEntity<List<VillageLevelBean>>(finaList, HttpStatus.OK);
	}

	@RequestMapping(value = "/getVillages", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public String getVillageOnTehsils(@RequestParam("statn") String statn, @RequestParam("dists") String dists,
			@RequestParam("tehsil") String tehsils) {
		List<VillageLevelBean> finaList = stationdService.getTehsil(statn, dists, tehsils);
		StringBuilder getvillage = new StringBuilder();
		getvillage.append("<option value='Please Select Village'>Please Select Village</option>");
		for (VillageLevelBean t : finaList) {
			getvillage.append("<option value='" + t.getVillageId() + "'>" + t.getVillage().trim() + "</option>");
		}
		return getvillage.toString();
	}

	@RequestMapping(value = "/getStns", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public String getTehsilOnDistrists(@RequestParam("statn") String statn, @RequestParam("dists") String dists) {
		//System.out.println(dists + "Cropid :- " + statn);
		StringBuilder getstate = new StringBuilder();
		List<String> list = stationdService.getStationsOnDist(statn, dists);
		getstate.append("<option value='Please Select Tehsil'>Please Select Tehsil</option>");
		for (String t : list) {
			getstate.append("<option value='" + t.trim() + "'>" + t.trim() + "</option>");
		}
		getstate.append("<option value='Others'>Others</option>");
		//System.out.println("return stae " + getstate);
		return getstate.toString();
	}

	@GetMapping("/getStn/{state}/{district}")
	public ResponseEntity<List<String>> getTehsilOnDistrist(@PathVariable String state, @PathVariable String district) {
		List<String> list = stationdService.getStationsOnDist(state, district);
		return new ResponseEntity<List<String>>(list, HttpStatus.OK);
	}

	public String writingFile(String url) {
		//System.out.println("sssddd " + url);
		return url;
	}

	@RequestMapping("/mandiOilDetails")
	public ResponseEntity<MandiRateBean> mandiOilDetails() {
		//System.out.println("mandiOilDetails");

		MandiRateBean bean = mandiOilService.getmandiOilDetails("Mustard");
		List<NcdexBean> bLIst = bean.getNcdexBeanList();
		/*System.out.println("size is .....................>>!!!" + bLIst.size());
		for (NcdexBean b : bLIst) {
			System.out.println(b.getMonth());
			System.out.println(b.getValue());
			System.out.println(b.getChange());
		}*/

		return new ResponseEntity<MandiRateBean>(bean, HttpStatus.OK);
	}

	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST, consumes = { "multipart/mixed" })
	public @ResponseBody String handleFileUpload(
			@RequestParam(value = "audioFile", required = false) MultipartFile imageFile) {
		System.out.println("inside this.....");
		try {
			
			String name = imageFile.getOriginalFilename();
			//System.out.println(name);
			//System.out.println(imageFile.getContentType() + "----" + imageFile.getBytes().length);
			byte[] bytes = imageFile.getBytes();
			File serverFile = new File("/home/dmdd/Desktop/fasalsalah/feedback/" + name);
			BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
			stream.write(bytes);
			stream.close();
			/*
			 * BufferedWriter w = Files.newBufferedWriter(Paths.get(
			 * "/home/dmdd/fasalsalah/feedback/" + name)); w.write(new
			 * String(imageFile.getBytes())); w.flush();
			 */
			/*
			 * BufferedWriter w = new BufferedWriter(new
			 * FileWriter("/home/dmdd/Desktop/fasalsalah/feedback/"+name));
			 * w.write(new String(imageFile.getBytes())); w.flush();
			 */
			return name.split("_")[0]; // @afterreturning it returns phone no
										// for logging purpose
		} catch (IOException e) {
			e.printStackTrace();
			return "Error Occur";
		}

	}

	
	@RequestMapping(value = "/savecropimage", method = RequestMethod.POST, consumes = { "multipart/mixed" })
	public @ResponseBody String SaveUserCropFieldImage(
			@RequestParam(value = "audioFile", required = false) MultipartFile imageFile) {
		System.out.println("inside this.....");
		try {
			
			String name = imageFile.getOriginalFilename();
			//System.out.println(name);
			//System.out.println(imageFile.getContentType() + "----" + imageFile.getBytes().length);
			byte[] bytes = imageFile.getBytes();
			File serverFile = new File("/home/dmdd/Desktop/fasalsalah/cropfield/" + name);
			BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
			stream.write(bytes);
			stream.close();
			/*
			 * BufferedWriter w = Files.newBufferedWriter(Paths.get(
			 * "/home/dmdd/fasalsalah/feedback/" + name)); w.write(new
			 * String(imageFile.getBytes())); w.flush();
			 */
			/*
			 * BufferedWriter w = new BufferedWriter(new
			 * FileWriter("/home/dmdd/Desktop/fasalsalah/feedback/"+name));
			 * w.write(new String(imageFile.getBytes())); w.flush();
			 */
			return name.split("_")[0]; // @afterreturning it returns phone no
										// for logging purpose
		} catch (IOException e) {
			e.printStackTrace();
			return "Error Occur";
		}

	}

	
	@GetMapping("/getCropCount")
	public ResponseEntity<Integer> getNoOfCrops() {

		return new ResponseEntity<Integer>(cropdService.listdata().size(), HttpStatus.OK);

	}

	

}