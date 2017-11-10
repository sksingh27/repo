package com.bkc.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@EnableAspectJAutoProxy
@EnableWebMvc
@Configuration
@ComponentScan(basePackages={"com.bkc"})
public class AppConfig extends WebMvcConfigurerAdapter {
	
	@Bean(name="viewResolver")
	public ViewResolver resolver(){
		System.out.println("inside view resolver");
		InternalResourceViewResolver invr= new InternalResourceViewResolver();
		invr.setPrefix("/WEB-INF/views/");
		invr.setSuffix(".jsp");
		
		return invr;
	}
	@Bean
	public CommonsMultipartResolver multipartResolver() {
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
		commonsMultipartResolver.setDefaultEncoding("utf-8");
		commonsMultipartResolver.setMaxUploadSize(50000000);
		return commonsMultipartResolver;
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		System.out.println("inside resource handler");
		
		
		registry.addResourceHandler("/forecast/**").addResourceLocations("file:/home/dmdd/Desktop/forecast/"); /*gets static images from system drive at local systems*/
		registry.addResourceHandler("/feedback/**").addResourceLocations("file:/home/dmdd/Desktop/fasalsalah/feedback/");
		registry.addResourceHandler("/seller/**").addResourceLocations("file:/home/dmdd/Desktop/fasalsalah/seller/");
		registry.addResourceHandler("/renter/**").addResourceLocations("file:/home/dmdd/Desktop/fasalsalah/renter/");
		registry.addResourceHandler("/advertise/**").addResourceLocations("file:/home/dmdd/Desktop/fasalsalah/advertisement/");
		registry.addResourceHandler("/category/**").addResourceLocations("file:/home/dmdd/Desktop/fasalsalah/category/");
		registry.addResourceHandler("/subcategory/**").addResourceLocations("file:/home/dmdd/Desktop/fasalsalah/subcategory/");
		registry.addResourceHandler("/item/**").addResourceLocations("file:/home/dmdd/Desktop/fasalsalah/item/");
		registry.addResourceHandler("/organicfarming/**").addResourceLocations("file:/home/dmdd/Desktop/fasalsalah/organicfarming/");
		registry.addResourceHandler("/farmerportal/feedback/**").addResourceLocations("file:/home/dmdd/Desktop/fasalsalah/feedback/");
		//registry.addResourceHandler("/getNews/newsImage/**").addResourceLocations("file:/root/Desktop/news/");  /*gets static images from system drive at Server*/
		//registry.addResourceHandler("/getNews/news/**").addResourceLocations("file:/home/dmdd/Desktop/forecast/");
		}
	
}
