package com.bkc.configuration;

import java.beans.PropertyVetoException;
import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.core.env.Environment;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.mchange.v2.c3p0.ComboPooledDataSource;

@Configuration
@EnableTransactionManagement
@PropertySource(value = { "classpath:resources/application.properties" })
public class HibernateConfiguration {

	@Autowired
	Environment enviornment;

	@Bean(name = "agroSessionFactory")
	public LocalSessionFactoryBean sessionFactory() {
		LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
		sessionFactoryBean.setDataSource(agroMgntDataSource());
		sessionFactoryBean.setHibernateProperties(hibernateProperties());
		sessionFactoryBean.setPackagesToScan(new String[] { "com.bkc.model" });
		return sessionFactoryBean;
	}

	@Bean(name = "webkcSessionFactory")
	public LocalSessionFactoryBean webkcSessionFactory() {
		LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
		sessionFactoryBean.setDataSource(WebkcDataSource());
		sessionFactoryBean.setHibernateProperties(hibernateProperties());
		sessionFactoryBean.setPackagesToScan(new String[] { "com.bkc.model" });
		return sessionFactoryBean;
	}

	@Bean(name = "mandiOilSessionFactory")
	public LocalSessionFactoryBean MandiOilSessionFactory() {

		LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
		sessionFactoryBean.setDataSource(MandiOilDataSource());
		sessionFactoryBean.setHibernateProperties(hibernateProperties());
		sessionFactoryBean.setPackagesToScan(new String[] { "com.bkc.model" });
		return sessionFactoryBean;
	}

	@Bean(name = "MandiOilDataSource")
	public DataSource MandiOilDataSource() {
		System.out.println("inside madni oil rates");
		DriverManagerDataSource source = new DriverManagerDataSource();
		source.setDriverClassName(enviornment.getRequiredProperty("jdbc.driverClassName"));
		source.setUrl(enviornment.getRequiredProperty("jdbc.MandiOil.url"));
		source.setUsername(enviornment.getRequiredProperty("jdbc.username"));
		source.setPassword(enviornment.getRequiredProperty("jdbc.password"));

		return source;

	}

	@Bean(name = "agroDataSource")
	public DataSource agroMgntDataSource() {
		DriverManagerDataSource source = new DriverManagerDataSource();
		source.setDriverClassName(enviornment.getRequiredProperty("jdbc.driverClassName"));
		source.setUrl(enviornment.getRequiredProperty("jdbc.url"));
		source.setUsername(enviornment.getRequiredProperty("jdbc.username"));
		source.setPassword(enviornment.getRequiredProperty("jdbc.password"));

		return source;

	}

	@Bean
	public DataSource WebkcDataSource() {
		DriverManagerDataSource source = new DriverManagerDataSource();
		source.setDriverClassName(enviornment.getRequiredProperty("jdbc.driverClassName"));
		source.setUrl(enviornment.getRequiredProperty("jdbc.webkc-url"));
		source.setUsername(enviornment.getRequiredProperty("jdbc.username"));
		source.setPassword(enviornment.getRequiredProperty("jdbc.password"));
		return source;
	}

	@Bean(name = "farmersPortalSessionFactory")
	public LocalSessionFactoryBean farmersPortalSessionFactory() {

		LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
		sessionFactoryBean.setDataSource(farmerPortalDataSource());
		sessionFactoryBean.setHibernateProperties(hibernateProperties());
		sessionFactoryBean.setPackagesToScan(new String[] { "com.bkc.model" });
		return sessionFactoryBean;
	}

	@Bean
	public DataSource farmerPortalDataSource() {
		DriverManagerDataSource source = new DriverManagerDataSource();
		source.setDriverClassName(enviornment.getRequiredProperty("jdbc.driverClassName"));
		source.setUrl(enviornment.getRequiredProperty("jdbc.farmersPortal-url"));
		source.setUsername(enviornment.getRequiredProperty("jdbc.username"));
		source.setPassword(enviornment.getRequiredProperty("jdbc.password"));
		return source;
	}

	@Bean(name = "diseaseDiagnosisSessionFactory")
	public LocalSessionFactoryBean diseaseDiagnosisSessionFactory() {

		LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
		sessionFactoryBean.setDataSource(diseaseDiagnosisDataSource());
		sessionFactoryBean.setHibernateProperties(hibernateProperties());
		sessionFactoryBean.setPackagesToScan(new String[] { "com.bkc.model" });
		return sessionFactoryBean;
	}

	@Bean
	public DataSource diseaseDiagnosisDataSource() {
		DriverManagerDataSource source = new DriverManagerDataSource();
		source.setDriverClassName(enviornment.getRequiredProperty("jdbc.driverClassName"));
		source.setUrl(enviornment.getRequiredProperty("jdbc.diseasediagnosis-url"));
		source.setUsername(enviornment.getRequiredProperty("jdbc.username"));
		source.setPassword(enviornment.getRequiredProperty("jdbc.password"));
		return source;
	}
	
	/*@Bean(destroyMethod="close")
	public ComboPooledDataSource agroMgntDataSource() {

		ComboPooledDataSource dataSource = new ComboPooledDataSource("agroMgnt");
		try {
			dataSource.setDriverClass(enviornment.getProperty("jdbc.driverClassName"));
		} catch (PropertyVetoException pve) {
			pve.printStackTrace();
			System.out.println("Cannot load datasource driver (com.mysql.jdbc.Driver) : " + pve.getMessage());
			return null;
		}
		dataSource.setJdbcUrl(enviornment.getProperty("jdbc.url"));
		dataSource.setUser(enviornment.getRequiredProperty("jdbc.username"));
		dataSource.setPassword(enviornment.getProperty("jdbc.password"));
		dataSource.setMinPoolSize(1);
		dataSource.setMaxPoolSize(2);
		dataSource.setMaxIdleTime(300);

		return dataSource;
	}

	@Bean(destroyMethod="close")
	public ComboPooledDataSource MandiOilDataSource() {
		ComboPooledDataSource dataSource = new ComboPooledDataSource("mandiOilRates");
		try {
			dataSource.setDriverClass(enviornment.getProperty("jdbc.driverClassName"));
		} catch (PropertyVetoException pve) {
			pve.printStackTrace();
			System.out.println("Cannot load datasource driver (com.mysql.jdbc.Driver) : " + pve.getMessage());
			return null;
		}
		dataSource.setJdbcUrl(enviornment.getProperty("jdbc.MandiOil.url"));
		dataSource.setUser(enviornment.getRequiredProperty("jdbc.username"));
		dataSource.setPassword(enviornment.getProperty("jdbc.password"));
		dataSource.setMinPoolSize(1);
		dataSource.setMaxPoolSize(2);
		dataSource.setMaxIdleTime(300);
		return dataSource;
	}

	@Bean(destroyMethod="close")
	public ComboPooledDataSource WebkcDataSource() {
		ComboPooledDataSource dataSource = new ComboPooledDataSource("webkc");
		try {
			dataSource.setDriverClass(enviornment.getProperty("jdbc.driverClassName"));
		} catch (PropertyVetoException pve) {
			pve.printStackTrace();
			System.out.println("Cannot load datasource driver (com.mysql.jdbc.Driver) : " + pve.getMessage());
			return null;
		}
		dataSource.setJdbcUrl(enviornment.getProperty("jdbc.webkc-url"));
		dataSource.setUser(enviornment.getRequiredProperty("jdbc.username"));
		dataSource.setPassword(enviornment.getProperty("jdbc.password"));
		dataSource.setMinPoolSize(1);
		dataSource.setMaxPoolSize(2);
		dataSource.setMaxIdleTime(300);
		return dataSource;
	}
	
	@Bean(destroyMethod="close")
	public ComboPooledDataSource farmerPortalDataSource() {
		ComboPooledDataSource dataSource = new ComboPooledDataSource("farmerPortal");
		try {
			dataSource.setDriverClass(enviornment.getProperty("jdbc.driverClassName"));
		} catch (PropertyVetoException pve) {
			pve.printStackTrace();
			System.out.println("Cannot load datasource driver (com.mysql.jdbc.Driver) : " + pve.getMessage());
			return null;
		}
		dataSource.setJdbcUrl(enviornment.getProperty("jdbc.farmersPortal-url"));
		dataSource.setUser(enviornment.getRequiredProperty("jdbc.username"));
		dataSource.setPassword(enviornment.getProperty("jdbc.password"));
		dataSource.setMinPoolSize(1);
		dataSource.setMaxPoolSize(2);
		dataSource.setMaxIdleTime(300);
		return dataSource;
	}*/
	
	@Bean
	public Properties hibernateProperties() {
		System.out.println("inside hibernate properties");
		Properties properties = new Properties();
		properties.put("hibernate.dialect", enviornment.getRequiredProperty("hibernate.dialect"));
		properties.put("hibernate.show_sql", enviornment.getRequiredProperty("hibernate.show_sql"));
		properties.put("hibernate.format_sql", enviornment.getRequiredProperty("hibernate.format_sql"));
		
		// properties.put("hibernate.c3p0.max_size", 50);
		// properties.put("hibernate.c3p0.min_size", 5);
		// properties.put("hibernate.c3p0.timeout", 5000);
		// properties.put("hibernate.c3p0.max_statements", 100);
		// properties.put("hibernate.c3p0.idle_test_period", 300);
		// properties.put("hibernate.c3p0.acquire_increment", 10);
		properties.put("hibernate.connection.CharSet", enviornment.getRequiredProperty("hibernate.connection.CharSet"));
		properties.put("hibernate.connection.characterEncoding",
				enviornment.getRequiredProperty("hibernate.connection.characterEncoding"));
		properties.put("hibernate.connection.useUnicode",
				enviornment.getRequiredProperty("hibernate.connection.useUnicode"));
		properties.put("hibernate.connection.defaultAutoCommit", "false");
		properties.put("hibernate.cache.use_second_level_cache", "true");
		properties.put("hibernate.cache.region.factory_class", "org.hibernate.cache.ehcache.EhCacheRegionFactory");
		properties.put("hibernate.cache.use_query_cache", "true");
       // properties.put("hibernate.hbm2ddl.auto", "update");
		properties.put("net.sf.ehcache.configurationResourceName", "/ehcache.xml");
		return properties;

	}

	@Bean
	@Autowired
	public HibernateTransactionManager transactionManger(@Autowired @Qualifier("agroSessionFactory") SessionFactory s) {
		HibernateTransactionManager tx = new HibernateTransactionManager();
		tx.setSessionFactory(s);
		return tx;
	}

}