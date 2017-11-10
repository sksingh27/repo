package com.bkc.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.bkc.bean.AllPostBean;
import com.bkc.bean.ResultUser;
import com.bkc.bean.buyerseller.BuyerRentResultBean;
import com.bkc.bean.buyerseller.BuyerResultBean;
import com.bkc.bean.buyerseller.CategoryBean;
import com.bkc.bean.buyerseller.ItemBean;
import com.bkc.bean.buyerseller.RenterBean;
import com.bkc.bean.buyerseller.SellerBean;
import com.bkc.service.BuyerSellerService;

@Controller
@RequestMapping("/buyerseller")  
public class BuyerSellerController {

	@Autowired
	
	BuyerSellerService buyerSellerService;
	
	@RequestMapping(value={"/getcategorylist/{phoneNo}","/getservice/{phoneNo}"})
	public ResponseEntity<List<CategoryBean>> getCategoryList(HttpServletRequest request,@PathVariable String phoneNo){
	@SuppressWarnings("unused")
	String text;
		try{
			
			if(request.getRequestURI().contains("service")){
	      
			return new ResponseEntity<List<CategoryBean>>(buyerSellerService.getCategoryList("service",phoneNo),HttpStatus.OK);
			}
			else {
				return new ResponseEntity<List<CategoryBean>>(buyerSellerService.getCategoryList(" ",phoneNo),HttpStatus.OK);
			}
			
		}
		catch(Exception e){
			
			e.printStackTrace();
			return new ResponseEntity<List<CategoryBean>>(new ArrayList<CategoryBean>(),HttpStatus.OK);
			
		}
		
		
	}
	
	@RequestMapping("/getsubcategorylist/{catId}/{phoneNo}")
	public ResponseEntity<List<CategoryBean>> getSubCategoryList(@PathVariable int catId,@PathVariable String phoneNo){
	
		
		try{
	      
			return new ResponseEntity<List<CategoryBean>>(buyerSellerService.getSubCategoryList(catId,phoneNo),HttpStatus.OK);
			
		}
		catch(Exception e){
			
			e.printStackTrace();
			return new ResponseEntity<List<CategoryBean>>(new ArrayList<CategoryBean>(),HttpStatus.OK);
			
		}
		
		
	}
	
	@RequestMapping("/getrentcategorylist/{phoneNo}")
	public ResponseEntity<List<CategoryBean>> getRentedCategoryList(@PathVariable String phoneNo){
	
		try{
	      
			return new ResponseEntity<List<CategoryBean>>(buyerSellerService.getRentedCategoryList( phoneNo),HttpStatus.OK);
			
		}
		catch(Exception e){
			
			e.printStackTrace();
			return new ResponseEntity<List<CategoryBean>>(new ArrayList<CategoryBean>(),HttpStatus.OK);
			
		}
		
		
	}
	
	@RequestMapping("/getitemlist/{categoryId}/{subCatId}/{phoneNo}")
	public ResponseEntity<List<ItemBean>> getItemList(@PathVariable int categoryId,@PathVariable int subCatId,@PathVariable String phoneNo){
		
		//System.out.println("inside get item list"+categoryId);
		
	
		try{
	      
			return new ResponseEntity<List<ItemBean>>(buyerSellerService.getItemList(categoryId,subCatId,phoneNo),HttpStatus.OK);
			
		}
		catch(Exception e){
			
			e.printStackTrace();
			return new ResponseEntity<List<ItemBean>>(new ArrayList<ItemBean>(),HttpStatus.OK);
			
		}
		
		
	}
	
	
	@RequestMapping("/getitemlistoncategory/{categoryId}")
	public ResponseEntity<List<ItemBean>> getItemListOnCategory(@PathVariable int categoryId){
		
		//System.out.println("inside get item list"+categoryId);
		
	
		try{
	      
			return new ResponseEntity<List<ItemBean>>(buyerSellerService.getItemListOnCategory(categoryId),HttpStatus.OK);
			
		}
		catch(Exception e){
			
			e.printStackTrace();
			return new ResponseEntity<List<ItemBean>>(new ArrayList<ItemBean>(),HttpStatus.OK);
			
		}
		
		
	}
/*	@RequestMapping("/savesellerpost")
	public ResponseEntity saveUserPost(@RequestBody SellerBean bean){
		SellerBean bean= new SellerBean();
		bean.setCategoryId(1);
		bean.setDescription("this is test description");
		bean.setImageName("hello.jpg");
		bean.setItemId(1);
		bean.setPhoneNo("9873468844");
		bean.setPackaging("test package");
		bean.setPrice("1000");
		bean.setQuantity("infinite");
		bean.setStationid(41127);
		
		try{
			buyerSellerService.saveSellerPost(bean);
			return new ResponseEntity(HttpStatus.OK);
		}
		catch(Exception e){
			e.printStackTrace();
			return new ResponseEntity(HttpStatus.OK);
		}
		
		
	}
	@RequestMapping("/saverenterpost")
	public ResponseEntity saveRenterPost(@RequestBody RenterBean bean){
		RenterBean bean= new RenterBean();
		bean.setCategoryId(1);
		bean.setDescription("this is test description");
		bean.setImageName("hello.jpg");
		bean.setItemId(1);
		bean.setPhoneNo("9873468844");
		
		bean.setPrice("1000");

		bean.setStationid(41127);
		
		System.out.println("category id :-"+ bean.getCategoryId());
		
		try{
			buyerSellerService.saveRenterPost(bean);
			return new ResponseEntity(HttpStatus.OK);
		}
		catch(Exception e){
			e.printStackTrace();
			return new ResponseEntity(HttpStatus.OK);
		}
		
		
	}*/
	
	@RequestMapping("/getbuyerposts/{phoneNo}/{categoryId}/{subcategoryId}/{itemId}")
	public ResponseEntity<List<BuyerResultBean>> getBuyerResult(@PathVariable String phoneNo,@PathVariable int categoryId,@PathVariable int itemId,@PathVariable int subcategoryId){
		//System.out.println("get buyer post--------------->"+phoneNo);
		
		try{
			
			return new ResponseEntity<List<BuyerResultBean>>(buyerSellerService.getBuyerResult(phoneNo, itemId, categoryId,subcategoryId),HttpStatus.OK);
		}
		catch(IndexOutOfBoundsException e){
			//System.out.println("inside index ou of bounds catch");
			e.printStackTrace();
			return new ResponseEntity<List<BuyerResultBean>>(new ArrayList<BuyerResultBean>(),HttpStatus.OK);
		}
		catch(Exception e){
			//System.out.println("inside main exception catch");
			e.printStackTrace();
			return new ResponseEntity<List<BuyerResultBean>>(new ArrayList<BuyerResultBean>(),HttpStatus.OK);
		}
		
	}
	
	@RequestMapping("/getbuyerrentposts/{phoneNo}/{categoryId}/{subcategoryId}/{itemId}")
	public ResponseEntity<List<BuyerRentResultBean>> getBuyerRentResult(@PathVariable String phoneNo,@PathVariable int categoryId,@PathVariable int itemId,@PathVariable int subcategoryId){
		try{
			
			return new ResponseEntity<List<BuyerRentResultBean>>(buyerSellerService.getBuyerRentResult(phoneNo, itemId, categoryId,subcategoryId),HttpStatus.OK);
		}
		catch(Exception e){
			e.printStackTrace();
			return new ResponseEntity<List<BuyerRentResultBean>>(new ArrayList<BuyerRentResultBean>(),HttpStatus.OK);
		}
		
	}
	
	@RequestMapping(value = "/sellerimagepost", method = RequestMethod.POST, consumes = { "multipart/mixed" })
	public @ResponseBody String sellerImagePost(
			@RequestParam(value = "audioFile", required = false) MultipartFile imageFile) {
		//System.out.println("inside this.....");
		try {
			String name = imageFile.getOriginalFilename();
			//System.out.println(name);
			//System.out.println(imageFile.getContentType() + "----" + imageFile.getBytes().length);
			byte[] bytes = imageFile.getBytes();
			File serverFile = new File("/home/dmdd/Desktop/fasalsalah/seller/" + name);
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
	
	@RequestMapping(value = "/renterimagepost", method = RequestMethod.POST, consumes = { "multipart/mixed" })
	public @ResponseBody String renterImagePost(
			@RequestParam(value = "audioFile", required = false) MultipartFile imageFile) {
		System.out.println("inside this.....");
		try {
			String name = imageFile.getOriginalFilename();
			//System.out.println(name);
			//System.out.println(imageFile.getContentType() + "----" + imageFile.getBytes().length);
			byte[] bytes = imageFile.getBytes();
			File serverFile = new File("/home/dmdd/Desktop/fasalsalah/renter/" + name);
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
	
	@RequestMapping("/getallposts/{phoneNo}")
	public ResponseEntity<List<AllPostBean>> getAllPosts(@PathVariable String phoneNo){
		
		try {
		return	new ResponseEntity<List<AllPostBean>>(buyerSellerService.getAllPost(phoneNo),HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return	new ResponseEntity<List<AllPostBean>>(new ArrayList<AllPostBean>(),HttpStatus.OK);
		}
	}
	
	
	@RequestMapping("/savesellerpost")
	public ResponseEntity<ResultUser> saveUserPost(@RequestBody SellerBean bean){
		/*SellerBean bean= new SellerBean();
		bean.setCategoryId(1);
		bean.setDescription("this is test description");
		bean.setImageName("hello.jpg");
		bean.setItemId(1);
		bean.setPhoneNo("9873468844");
		bean.setPackaging("test package");
		bean.setPrice("1000");
		bean.setQuantity("infinite");
		bean.setStationid(41127);*/
		ResultUser user = new ResultUser();
		
		try{
			buyerSellerService.saveSellerPost(bean);			
			user.setRes("saved");
			return new ResponseEntity<ResultUser>(user,HttpStatus.OK);
		}
		catch(Exception e){
			e.printStackTrace();			
			user.setRes("not saved");					
			return new ResponseEntity<ResultUser>(user,HttpStatus.OK);
		}
		
		
		
	}
	
	@RequestMapping("/saverenterpost")
	public ResponseEntity<ResultUser> saveRenterPost(@RequestBody RenterBean bean){
		/*RenterBean bean= new RenterBean();
		bean.setCategoryId(1);
		bean.setDescription("this is test description");
		bean.setImageName("hello.jpg");
		bean.setItemId(1);
		bean.setPhoneNo("9873468844");
		
		bean.setPrice("1000");

		bean.setStationid(41127);*/
		
		//System.out.println("category id :-"+ bean.getCategoryId());
		ResultUser user = new ResultUser();
		try{
			buyerSellerService.saveRenterPost(bean);
			user.setRes("saved");
			return new ResponseEntity<ResultUser>(user,HttpStatus.OK);
		}
		catch(Exception e){
			e.printStackTrace();
			user.setRes("not saved");	
			return new ResponseEntity<ResultUser>(user,HttpStatus.OK);
		}
		
		
	}
	
	//-------these two methods are not used by app
	@GetMapping("/updateapproval/{id}/{type}")
	public @ResponseBody String updatePostApproval(@PathVariable int id,@PathVariable String type){
		
		buyerSellerService.updateSellerRenterPost(id, type);;
		return "completed";
	}
	
	@GetMapping("/disapproval/{id}/{type}")
	public @ResponseBody String PostDisapproval(@PathVariable int id,@PathVariable String type){
		buyerSellerService.disapproveSellerRenterPost(id, type);
		return "completed";		
	}
	//---------
}