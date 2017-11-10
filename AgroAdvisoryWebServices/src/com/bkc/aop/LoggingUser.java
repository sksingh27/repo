package com.bkc.aop;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.bkc.bean.BuyerSellerPojo;
import com.bkc.bean.UserProfileRegistrationBean;

@Component
@Aspect
public class LoggingUser {
	
	SimpleDateFormat timeFormat= new SimpleDateFormat("HH:MM:ss");

/*	@AfterReturning(pointcut = "execution(* com.bkc.controller.TestController.writingFile(..))", returning = "result")
	public void AfterReturning(JoinPoint joinPoint, Object result) {
		try {
			result = result + "\r\n";
			System.out.println("***AspectJ*** DoAfterReturning() is running!! intercepted : "
					+ joinPoint.getSignature().getName());
			System.out.println("Method returned value is : " + result);
			System.out.println("This is Running Completed.... Paassword");
			StringBuffer strbuf = new StringBuffer(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
			System.out.println("today Date :- " + strbuf.toString());
			String filename = "/home/dmdd/Desktop/fasalsalah/log_" + strbuf.toString().replaceAll("-", "_") + ".txt";
			Path filePath = Paths.get(filename);
			if (!Files.exists(filePath)) {
				Files.createFile(filePath);
			}
			Files.write(filePath, result.toString().getBytes(), StandardOpenOption.APPEND);

		} catch (Exception ex) {
			ex.printStackTrace();

		}

	}*/
	
	@AfterThrowing(pointcut = "execution(* com.bkc.controller.RegistrationController.saveUser(..))", throwing = "e")
	public void afterThrowingAdvicefromsaveuser(JoinPoint jp, Exception e ) {
		try {
		
			
		   UserProfileRegistrationBean bean=(UserProfileRegistrationBean)jp.getArgs()[0];
			StringBuffer strbuf = new StringBuffer(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
			strbuf=strbuf.append("\n"+bean.getPhoneNo()+" village id is "+bean.getVillageId());
			StackTraceElement []ste = e.getStackTrace();
			System.out.println("Exception is " + e+" "+strbuf.toString().replaceAll("-", "_"));			
			String result = "Date Occur "+strbuf.toString().replaceAll("-", "_")+ "\r\nException Occur in this Methed :- " + jp.getSignature().getName() + "()\r\n"+ e + "\r\n";
			System.out.println("message is :- "+e.getMessage());
			System.out.println("localozed message is "+e.getLocalizedMessage());
			
			System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");e.printStackTrace();System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
			for(StackTraceElement st : ste){
				result = result + st + "\r\n";
				
				System.out.println(st.toString());
			}
			result=result+" \n ------------------------------------------------------------------------------------------------------------------- \n";
			System.out.println("Annotation driven:After throwing " + jp.getSignature().getName() + "()");
			
			
			System.out.println("today Date :- " + strbuf.toString());
			String filename = "/home/dmdd/Desktop/fasalsalah/logException_" + (new SimpleDateFormat("yyyy-MM-dd").format(new Date())).replaceAll("-", "_")  + ".txt";
			Path filePath = Paths.get(filename);
			if (!Files.exists(filePath)) {
				Files.createFile(filePath);
			}
			Files.write(filePath, result.toString().getBytes(), StandardOpenOption.APPEND);

		} catch (Exception ex) {
			ex.printStackTrace();

		}
	}
		@AfterThrowing(pointcut = "execution(* com.bkc.controller.RegistrationController.saveBuyerSeller(..))", throwing = "e")
		public void afterThrowingAdvicefromsaveBuyerSeller(JoinPoint jp, Exception e ) {
			try {
			
				
			   //UserProfileRegistrationBean bean=(UserProfileRegistrationBean)jp.getArgs()[0];
				StringBuffer strbuf = new StringBuffer(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
				//strbuf=strbuf.append("\n"+bean.getPhoneNo());
				StackTraceElement []ste = e.getStackTrace();
				System.out.println("Exception is " + e+" "+strbuf.toString().replaceAll("-", "_"));			
				String result = "Date Occur "+strbuf.toString().replaceAll("-", "_")+ "\r\nException Occur in this Methed :- " + jp.getSignature().getName() + "()\r\n"+ e + "\r\n";
				for(StackTraceElement st : ste){
					result = result + st + "\r\n";
					System.out.println(st.toString());
				}
				result=result+" \n ------------------------------------------------------------------------------------------------------------------- \n";
				System.out.println("Annotation driven:After throwing " + jp.getSignature().getName() + "()");
				
				
				System.out.println("today Date :- " + strbuf.toString());
				String filename = "/home/dmdd/Desktop/fasalsalah/logException_" + (new SimpleDateFormat("yyyy-MM-dd").format(new Date())).replaceAll("-", "_")  + ".txt";
				Path filePath = Paths.get(filename);
				if (!Files.exists(filePath)) {
					Files.createFile(filePath);
				}
				Files.write(filePath, result.toString().getBytes(), StandardOpenOption.APPEND);

			} catch (Exception ex) {
				ex.printStackTrace();

			}
		
		
		
		
	}

		
		@AfterThrowing(pointcut = "execution(* com.bkc.controller.TestController.weatherForecast(..))", throwing = "e")
		public void afterThrowingAdvicefromWeatherforecast(JoinPoint jp, Exception e ) {
			try {
			
				
			  String bean=(String)jp.getArgs()[0];
				StringBuffer strbuf = new StringBuffer(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
				strbuf=strbuf.append("\n"+bean);
				StackTraceElement []ste = e.getStackTrace();
				System.out.println("Exception is " + e+" "+strbuf.toString().replaceAll("-", "_"));			
				String result = "Date Occur "+strbuf.toString().replaceAll("-", "_")+ "\r\nException Occur in this Methed :- " + jp.getSignature().getName() + "()\r\n"+ e + "\r\n";
				for(StackTraceElement st : ste){
					result = result + st + "\r\n";
					System.out.println(st.toString());
				}
				result=result+" \n ------------------------------------------------------------------------------------------------------------------- \n";
				System.out.println("Annotation driven:After throwing " + jp.getSignature().getName() + "()");
				
				
				System.out.println("today Date :- " + strbuf.toString());
				String filename = "/home/dmdd/Desktop/fasalsalah/logException_" + (new SimpleDateFormat("yyyy-MM-dd").format(new Date())).replaceAll("-", "_")  + ".txt";
				Path filePath = Paths.get(filename);
				if (!Files.exists(filePath)) {
					Files.createFile(filePath);
				}
				Files.write(filePath, result.toString().getBytes(), StandardOpenOption.APPEND);

			} catch (Exception ex) {
				ex.printStackTrace();

			}
		
		
		
		
	}	
		
		@AfterThrowing(pointcut = "execution(* com.bkc.service.BuyerSellerServiceImpl.*(..))", throwing = "e")
		public void afterThrowingAdvicefromBuyerSeller(JoinPoint jp, Exception e ) {
		System.out.println("inside aop when exeption in buyer seller controller........!!!");
			try {
			
				
			  //String bean=(String)jp.getArgs()[0];
				StringBuffer strbuf = new StringBuffer(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
				//strbuf=strbuf.append("\n"+bean);
				StackTraceElement []ste = e.getStackTrace();
				System.out.println("Exception is " + e+" "+strbuf.toString().replaceAll("-", "_"));			
				String result = "Date Occur "+strbuf.toString().replaceAll("-", "_")+ "\r\nException Occur in this Methed :- " + jp.getSignature().getName() + "()\r\n"+ e + "\r\n";
				for(StackTraceElement st : ste){
					result = result + st + "\r\n";
					System.out.println(st.toString());
				}
				result=result+" \n ------------------------------------------------------------------------------------------------------------------- \n";
				System.out.println("Annotation driven:After throwing " + jp.getSignature().getName() + "()");
				
				
				System.out.println("today Date :- " + strbuf.toString());
				String filename = "/home/dmdd/Desktop/fasalsalah/logException_BuyerSeller_" + (new SimpleDateFormat("yyyy-MM-dd").format(new Date())).replaceAll("-", "_")  + ".txt";
				Path filePath = Paths.get(filename);
				if (!Files.exists(filePath)) {
					Files.createFile(filePath);
				}
				Files.write(filePath, result.toString().getBytes(), StandardOpenOption.APPEND);

			} catch (Exception ex) {
				ex.printStackTrace();

			}
		
		
		
		
	}		
		
		
	/*
	@Before("execution(* com.bkc.controller.NewsController.getNewsForUser(..))")
	public void BeforeExecutingNews(JoinPoint pointcut) {
		StringBuffer strbuf = new StringBuffer(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		String filename = "/home/dmdd/Desktop/fasalsalah/log_" + strbuf.toString().replaceAll("-", "_") + ".txt";
		try {
			Object[] args=pointcut.getArgs();
			String phoneNo=args[0].toString();
			System.out.println("phone no inside args !!!"+phoneNo+" at "+new Date()+". \n");
			String result="user with phone no. "+phoneNo+" inside news section at "+new Date()+". \n";
			//StringBuffer strbuf = new StringBuffer(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
			System.out.println("today Date :- " + strbuf.toString());
			//String filename = "/home/dmdd/Desktop/fasalsalah/log_News" + strbuf.toString().replaceAll("-", "_") + ".txt";
			Path filePath = Paths.get(filename);
			if (!Files.exists(filePath)) {
				Files.createFile(filePath);
			}
			Files.write(filePath, result.toString().getBytes(), StandardOpenOption.APPEND);
			
            
		} catch (Exception ex) {
			ex.printStackTrace();

		}

	}*/
	
	@Before("execution(* com.bkc.controller.AdvisoryController.genAdvisoryApp(..))")
	public void beforeExecutingAdvisory(JoinPoint pointcut) {
		StringBuffer strbuf = new StringBuffer(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		String filename = "/home/dmdd/Desktop/fasalsalah/log_" + strbuf.toString().replaceAll("-", "_") + ".txt";
		try {
			
			Object[] args=pointcut.getArgs();
			String phoneNo=args[0].toString();
			String cropId=args[1].toString();
		    String result=phoneNo+"&advisory&cropId&"+cropId+"&"+timeFormat.format(new Date())+"\n";
		    System.out.println(result);
			Path filePath = Paths.get(filename);
			if (!Files.exists(filePath)) {
				Files.createFile(filePath);
			}
			Files.write(filePath, result.toString().getBytes(), StandardOpenOption.APPEND);
			
            
		} catch (Exception ex) {
			ex.printStackTrace();

		}

	}
	
	@Before("execution(* com.bkc.controller2.AdvisoryController2.genAdvisoryApp(..))")
	public void beforeExecutingAdvisory2(JoinPoint pointcut) {
		StringBuffer strbuf = new StringBuffer(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		String filename = "/home/dmdd/Desktop/fasalsalah/log_" + strbuf.toString().replaceAll("-", "_") + ".txt";
		try {
			
			Object[] args=pointcut.getArgs();
			String phoneNo=args[0].toString();
			String cropId=args[1].toString();
		    String result=phoneNo+"&advisory&cropId&"+cropId+"&"+timeFormat.format(new Date())+"\n";
		    System.out.println(result);
			Path filePath = Paths.get(filename);
			if (!Files.exists(filePath)) {
				Files.createFile(filePath);
			}
			Files.write(filePath, result.toString().getBytes(), StandardOpenOption.APPEND);
			
            
		} catch (Exception ex) {
			ex.printStackTrace();

		}

	}
	@Before("execution(* com.bkc.controller.TestController.displayMandi(..))")
	public void beforeExecutingMandi(JoinPoint pointcut) {
		StringBuffer strbuf = new StringBuffer(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		String filename = "/home/dmdd/Desktop/fasalsalah/log_" + strbuf.toString().replaceAll("-", "_") + ".txt";
		try {
			System.out.println("inside display mandi lon!!");
			Object[] args=pointcut.getArgs();
			String phoneNo=args[0].toString();
			String cropId=args[1].toString();
		    String result=phoneNo+"&Mandi&crop&"+cropId+"&"+timeFormat.format(new Date())+"\n";
		    System.out.println(result);
			Path filePath = Paths.get(filename);
			if (!Files.exists(filePath)) {
				Files.createFile(filePath);
			}
			Files.write(filePath, result.toString().getBytes(), StandardOpenOption.APPEND);
			
            
		} catch (Exception ex) {
			ex.printStackTrace();

		}

	}
	
	@Before("execution(* com.bkc.controller.TestController.weatherForecast(..))")
	public void beforeExecutingForecast(JoinPoint pointcut) {
		StringBuffer strbuf = new StringBuffer(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		String filename = "/home/dmdd/Desktop/fasalsalah/log_" + strbuf.toString().replaceAll("-", "_") + ".txt";
		try {String result="";
			
			
			Object[] args=pointcut.getArgs();
			String phoneNo=args[0].toString();
			int noOfDays=Integer.parseInt(args[1].toString());
			if(noOfDays>5){
		     result=phoneNo+"&Weather&"+timeFormat.format(new Date())+"\n";
			}
			else {
				//result=phoneNo+"|app|"+timeFormat.format(new Date())+"\n";
			}
		    Path filePath = Paths.get(filename);
			if (!Files.exists(filePath)) {
				Files.createFile(filePath);
			}
			Files.write(filePath, result.toString().getBytes(), StandardOpenOption.APPEND);
			
            
		} catch (Exception ex) {
			ex.printStackTrace();

		}

	}
	

	@Before("execution(* com.bkc.controller.FarmerportalController.getDealers(..))")
	public void beforeExecutingFarmersportal(JoinPoint pointcut) {
		StringBuffer strbuf = new StringBuffer(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		String filename = "/home/dmdd/Desktop/fasalsalah/log_" + strbuf.toString().replaceAll("-", "_") + ".txt";
		try {String result="";
			
			
			Object[] args=pointcut.getArgs();
			String phoneNo=args[0].toString();
			HttpServletRequest req=(HttpServletRequest)args[1];
			String url=req.getServletPath();
			if(url.contains("fertilizer")){
				
				 result=phoneNo+"&fertilizer dealers&"+timeFormat.format(new Date())+"\n";
			}
			else if(url.contains("seed")){
				result=phoneNo+"&seed dealers&"+timeFormat.format(new Date())+"\n";
				
			}
			else if(url.contains("pesticide")){
				result=phoneNo+"&pesticide dealers&"+timeFormat.format(new Date())+"\n";
				
			}
			else if (url.contains("machinery")) {
				result=phoneNo+"&machinery dealers&"+timeFormat.format(new Date())+"\n";
				
			}
			
		    Path filePath = Paths.get(filename);
			if (!Files.exists(filePath)) {
				Files.createFile(filePath);
			}
			Files.write(filePath, result.toString().getBytes(), StandardOpenOption.APPEND);
			
            
		} catch (Exception ex) {
			ex.printStackTrace();

		}

	}
	
	@Before("execution(* com.bkc.controller.NewsController.getNewsForUser(..))")
	public void beforeExecutingNews(JoinPoint pointcut) {
	
		StringBuffer strbuf = new StringBuffer(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		String filename = "/home/dmdd/Desktop/fasalsalah/log_" + strbuf.toString().replaceAll("-", "_") + ".txt";
		try {
		
			Object[] args=pointcut.getArgs();
			String phoneNo=args[0].toString();
		    String result=phoneNo+"&News&"+timeFormat.format(new Date())+"\n";
		    System.out.println(result);
			Path filePath = Paths.get(filename);
			if (!Files.exists(filePath)) {
				Files.createFile(filePath);
			}
			Files.write(filePath, result.toString().getBytes(), StandardOpenOption.APPEND);
			
            
		} catch (Exception ex) {
			ex.printStackTrace();

		}

	}
	
	@Before("execution(* com.bkc.controller.RegistrationController.saveBuyerSeller(..))")
	public void beforeExecutingBuyerSeller(JoinPoint pointcut) {
    System.out.println("inside buyer seller pojo !!");
    StringBuffer strbuf = new StringBuffer(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
	String filename = "/home/dmdd/Desktop/fasalsalah/log_" + strbuf.toString().replaceAll("-", "_") + ".txt";
    
    try {
		
			Object[] args=pointcut.getArgs();
			BuyerSellerPojo pojo=(BuyerSellerPojo)args[0];
		    String result=pojo.getContact()+"&"+pojo.getName()+"&"+pojo.getBuyer_seller()+"&"+timeFormat.format(new Date())+"\n";
		    System.out.println(result);
			Path filePath = Paths.get(filename);
			if (!Files.exists(filePath)) {
				Files.createFile(filePath);
			}
			Files.write(filePath, result.toString().getBytes(), StandardOpenOption.APPEND);
			
            
		} catch (Exception ex) {
			ex.printStackTrace();

		}

	}
	
	
	@AfterReturning(pointcut = "execution(* com.bkc.controller.TestController.handleFileUpload(..))", returning = "result")
	public void AfterReturning(JoinPoint joinPoint, Object result) {
		
		StringBuffer strbuf = new StringBuffer(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		String filename = "/home/dmdd/Desktop/fasalsalah/log_" + strbuf.toString().replaceAll("-", "_") + ".txt";
		System.out.println("inside feedback log");
		try {
			String res=result.toString()+"&feedback&"+timeFormat.format(new Date())+"\n";
			Path filePath = Paths.get(filename);
			if (!Files.exists(filePath)) {
				Files.createFile(filePath);
			}
			Files.write(filePath, res.toString().getBytes(), StandardOpenOption.APPEND);

		} catch (Exception ex) {
			ex.printStackTrace();

		}

	}
	
	
	@Before("execution(* com.bkc.controller.RegistrationController.saveUser(..))")
	public void beforeExecutingSaveUser(JoinPoint pointcut) {
    System.out.println("inside buyer seller pojo !!");
    StringBuffer strbuf = new StringBuffer(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
	String filename = "/home/dmdd/Desktop/fasalsalah/log_" + strbuf.toString().replaceAll("-", "_") + ".txt";
    
    try {
		
			Object[] args=pointcut.getArgs();
			UserProfileRegistrationBean pojo=(UserProfileRegistrationBean)args[0];
		    String result=pojo.getPhoneNo()+"&registration&"+pojo.getVillageId()+"&"+pojo.getName()+" \n";
		    System.out.println(result);
			Path filePath = Paths.get(filename);
			if (!Files.exists(filePath)) {
				Files.createFile(filePath);
			}
			Files.write(filePath, result.toString().getBytes(), StandardOpenOption.APPEND);
			
            
		} catch (Exception ex) {
			ex.printStackTrace();

		}

	}
	
	
	//new save user from 1.12 version
	@Before("execution(* com.bkc.controller2.RegistrationController2.saveUser(..))")
	public void beforeExecutingSaveUser2(JoinPoint pointcut) {
    System.out.println("inside buyer seller pojo !!");
    StringBuffer strbuf = new StringBuffer(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
	String filename = "/home/dmdd/Desktop/fasalsalah/log_" + strbuf.toString().replaceAll("-", "_") + ".txt";
    
    try {
		
    	
			Object[] args=pointcut.getArgs();
			UserProfileRegistrationBean pojo=(UserProfileRegistrationBean)args[0];
		    String result=pojo.getPhoneNo()+"&registration&"+pojo.getVillageId()+"&"+pojo.getName()+" \n";
		    System.out.println(result);
			Path filePath = Paths.get(filename);
			if (!Files.exists(filePath)) {
				Files.createFile(filePath);
			}
			Files.write(filePath, result.toString().getBytes(), StandardOpenOption.APPEND);
			System.out.println("v 1.12 user logged......!!");
            
		} catch (Exception ex) {
			ex.printStackTrace();

		}

	}
	
	
	@Before("execution(* com.bkc.controller2.NewsController2.getNewsForUser(..))")
	public void beforeExecutingNews2(JoinPoint pointcut) {
	
		StringBuffer strbuf = new StringBuffer(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		String filename = "/home/dmdd/Desktop/fasalsalah/log_" + strbuf.toString().replaceAll("-", "_") + ".txt";
		try {
		
			Object[] args=pointcut.getArgs();
			String phoneNo=args[0].toString();
		    String result=phoneNo+"&News&"+timeFormat.format(new Date())+"\n";
		    System.out.println(result);
			Path filePath = Paths.get(filename);
			if (!Files.exists(filePath)) {
				Files.createFile(filePath);
			}
			Files.write(filePath, result.toString().getBytes(), StandardOpenOption.APPEND);
			
            
		} catch (Exception ex) {
			ex.printStackTrace();

		}

	}
	
	@Before("execution(* com.bkc.controller2.NewsController2.getIncomeListForUser(..))")
	public void beforeExecutingIncomeNews2(JoinPoint pointcut) {
	
		StringBuffer strbuf = new StringBuffer(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		String filename = "/home/dmdd/Desktop/fasalsalah/log_" + strbuf.toString().replaceAll("-", "_") + ".txt";
		try {
		
			Object[] args=pointcut.getArgs();
			String phoneNo=args[0].toString();
		    String result=phoneNo+"&Income&"+timeFormat.format(new Date())+"\n";
		    System.out.println(result);
			Path filePath = Paths.get(filename);
			if (!Files.exists(filePath)) {
				Files.createFile(filePath);
			}
			Files.write(filePath, result.toString().getBytes(), StandardOpenOption.APPEND);
			
            
		} catch (Exception ex) {
			ex.printStackTrace();

		}

	}
	
	/*@AfterThrowing(pointcut = "execution(* com.bkc.controller.NewsController.*(..))",throwing="nullPointEx")
	public void afterThrowingAdviceNews(JoinPoint jp, Exception nullPointEx) {
		try {
			StringBuffer strbuf = new StringBuffer(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
			StackTraceElement []ste = nullPointEx.getStackTrace();
			System.out.println("Exception is " + nullPointEx+" "+strbuf.toString().replaceAll("-", "_"));			
			String result = "Date Occur "+strbuf.toString().replaceAll("-", "_")+ "\r\nException Occur in this Methed :- " + jp.getSignature().getName() + "()\r\n"+ nullPointEx + "\r\n";
			for(StackTraceElement st : ste){
				result = result + st + "\r\n";
				System.out.println(st.toString());
			}
			System.out.println("Annotation driven:After throwing " + jp.getSignature().getName() + "()");
			
			
			System.out.println("today Date :- " + strbuf.toString());
			String filename = "/home/dmdd/Desktop/fasalsalah/logException_News" + strbuf.toString().replaceAll("-", "_") + ".txt";
			Path filePath = Paths.get(filename);
			if (!Files.exists(filePath)) {
				Files.createFile(filePath);
			}
			Files.write(filePath, result.toString().getBytes(), StandardOpenOption.APPEND);

		} catch (Exception ex) {
			ex.printStackTrace();

		}*/
		
		
		
		
		
	}
	

