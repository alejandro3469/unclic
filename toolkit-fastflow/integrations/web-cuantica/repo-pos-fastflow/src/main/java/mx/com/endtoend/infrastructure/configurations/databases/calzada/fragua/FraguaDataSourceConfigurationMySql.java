package mx.com.endtoend.infrastructure.configurations.databases.calzada.fragua;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Properties;

@Component
@Configuration(proxyBeanMethods = false)
@ComponentScan("mx.com.endtoend")
@PropertySource("classpath:application.properties")
@EnableJpaRepositories(basePackages = { 
		"mx.com.endtoend.infrastructure.accountingRecord.calzada.fragua.repositories",
		"mx.com.endtoend.infrastructure.articles.calzada.fragua",
		"mx.com.endtoend.infrastructure.cash.bankReference.calzada.fragua.repositories",
		"mx.com.endtoend.infrastructure.cash.closingInstruments.calzada.fragua.repositories",
		"mx.com.endtoend.infrastructure.cash.creditCardReference.calzada.fragua.repositories",
		"mx.com.endtoend.infrastructure.cash.emailReport.calzada.fragua.repositories",
		"mx.com.endtoend.infrastructure.cash.openingInstruments.calzada.fragua.repositories",
		"mx.com.endtoend.infrastructure.catalogue.address.calzada.fragua.repositories",
		"mx.com.endtoend.infrastructure.catalogue.article.calzada.fragua.repositories",
		"mx.com.endtoend.infrastructure.catalogue.client.calzada.fragua.repositories",
		"mx.com.endtoend.infrastructure.catalogue.orders.calzada.fragua.repositories",
		"mx.com.endtoend.infrastructure.client.Calzada.fragua",
		"mx.com.endtoend.infrastructure.closings.calzada.fragua.repositories",
		"mx.com.endtoend.infrastructure.creditNote.calzada.fragua.repositories",
		"mx.com.endtoend.infrastructure.openings.calzada.fragua.repositories",
		"mx.com.endtoend.infrastructure.orderConfigurations.calzada.fragua",
		"mx.com.endtoend.infrastructure.orders.calzada.fragua",
		"mx.com.endtoend.infrastructure.payments.calzada.fragua.repositories",
		"mx.com.endtoend.infrastructure.userConfiguration.calzada.fragua", 
		"mx.com.endtoend.infrastructure.debug.calzada.fragua",
		"mx.com.endtoend.infrastructure.logs.orders.calzada.fragua.repositories",
		
}, entityManagerFactoryRef = "fraguaDataEntityManagerFactory", transactionManagerRef = "fraguaDataTransactionManager")
public class FraguaDataSourceConfigurationMySql {
	
	@Value("${app.datasource.fragua.driver-class-name}")
	private String driver;
	@Value("${app.datasource.fragua.url}")
	private String url;
	@Value("${app.datasource.fragua.username}")
	private String username;
	@Value("${app.datasource.fragua.password}")
	private String password;
	
	@Bean(name = "fraguaDataSource")
	public DataSource calzadaDataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(driver);
		dataSource.setUrl(url);
		dataSource.setUsername(username);
		dataSource.setPassword(password);

		return dataSource;
	}

	@Bean(name = "fraguaDataEntityManagerFactory")
	public LocalContainerEntityManagerFactoryBean calzadaDataEntityManagerFactory() {
		// Detect database type from URL
		boolean isH2 = url != null && url.contains("jdbc:h2:");
		
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		vendorAdapter.setShowSql(true);
		vendorAdapter.setDatabase(isH2 ? Database.H2 : Database.MYSQL);
		vendorAdapter.setGenerateDdl(true);

		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setJpaVendorAdapter(vendorAdapter);
		
		// Set JPA properties for dialect and DDL auto
		Properties jpaProperties = new Properties();
		if (isH2) {
			jpaProperties.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
			jpaProperties.put("hibernate.ddl-auto", "create-drop");
			jpaProperties.put("hibernate.hbm2ddl.auto", "create-drop");
			jpaProperties.put("hibernate.hbm2ddl.create_namespaces", "true");
		} else {
			jpaProperties.put("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
			jpaProperties.put("hibernate.ddl-auto", "update");
			jpaProperties.put("hibernate.hbm2ddl.auto", "update");
		}
		factory.setJpaProperties(jpaProperties);
		factory.setPackagesToScan(
				"mx.com.endtoend.infrastructure.accountingRecord.common.entities",
				"mx.com.endtoend.infrastructure.articles.common.entities",
				"mx.com.endtoend.infrastructure.cash.bankReference.common.entities",
				"mx.com.endtoend.infrastructure.cash.closingInstruments.common.entities",
				"mx.com.endtoend.infrastructure.cash.creditCardReference.common.entities",
				"mx.com.endtoend.infrastructure.cash.emailReport.common.entities",
				"mx.com.endtoend.infrastructure.cash.openingInstruments.common.entities",
				"mx.com.endtoend.infrastructure.catalogue.address.entities",
				"mx.com.endtoend.infrastructure.catalogue.article.common.entities",
				"mx.com.endtoend.infrastructure.catalogue.client.common.entities",
				"mx.com.endtoend.infrastructure.catalogue.orders.common.entities",
				"mx.com.endtoend.infrastructure.client.common.entities",
				"mx.com.endtoend.infrastructure.closings.common.entities",
				"mx.com.endtoend.infrastructure.creditNote.common.entities",
				"mx.com.endtoend.infrastructure.openings.common.entities",
				"mx.com.endtoend.infrastructure.orderConfigurations.common.entities",
				"mx.com.endtoend.infrastructure.orders.common.entities",
				"mx.com.endtoend.infrastructure.payments.common.entities",
				"mx.com.endtoend.infrastructure.payments.calzada.entities",
				"mx.com.endtoend.infrastructure.userConfiguration.common.entities",
				"mx.com.endtoend.infrastructure.debug.calzada.mysql",
				"mx.com.endtoend.infrastructure.logs.orders.entities");
		factory.setDataSource(calzadaDataSource());
		return factory;

	}

	@Bean(name = "fraguaDataTransactionManager")
	public PlatformTransactionManager calzadaDataTransactionManager(
			@Qualifier("fraguaDataEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
		return new JpaTransactionManager(entityManagerFactory);
	}

}
