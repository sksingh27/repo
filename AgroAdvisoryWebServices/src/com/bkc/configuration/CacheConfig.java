/*package com.bkc.configuration;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.CacheResolver;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.interceptor.SimpleKeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import com.bkc.model.GridWiseCola;

import net.sf.ehcache.config.CacheConfiguration;

@Configurable
@EnableCaching
public class CacheConfig  {

	@Bean
	public net.sf.ehcache.CacheManager ehCacheManager() {
         
		CacheConfiguration configuration= new CacheConfiguration();
		configuration.setName("gridwiseColaCache");
		configuration.setMemoryStoreEvictionPolicy("LRU");
		configuration.setMaxEntriesLocalDisk(500);
		configuration.setMaxBytesLocalHeap((long) 1000);
		configuration.setOverflowToOffHeap(false);
		 net.sf.ehcache.config.Configuration config = new net.sf.ehcache.config.Configuration();
		 config.addCache(configuration);
		 return net.sf.ehcache.CacheManager.newInstance(config);
		
	}


    @Bean
    @Override
    public KeyGenerator keyGenerator() {
        return new SimpleKeyGenerator();
    }

	@Override
	public CacheResolver cacheResolver() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CacheErrorHandler errorHandler() {
		// TODO Auto-generated method stub
		return null;
	}

    @Bean
	@Override
	public CacheManager cacheManager() {
		// TODO Auto-generated method stub
		return new EhCacheCacheManager(ehCacheManager());
	}

	
	@Bean
	public GridWiseCola getGridWiseCola(){
		return new GridWiseCola();
	}
	
	@Bean
	public CacheManager getEhCacheManager(){
	        return  new EhCacheCacheManager(getEhCacheFactory().getObject());
	}
	@Bean
	public EhCacheManagerFactoryBean getEhCacheFactory(){
		
		System.out.println("inside ehcache congig !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		EhCacheManagerFactoryBean factoryBean = new EhCacheManagerFactoryBean();
		factoryBean.setConfigLocation(new ClassPathResource("ehcache.xml"));
		factoryBean.setShared(true);
		return factoryBean;
	}

}
*/