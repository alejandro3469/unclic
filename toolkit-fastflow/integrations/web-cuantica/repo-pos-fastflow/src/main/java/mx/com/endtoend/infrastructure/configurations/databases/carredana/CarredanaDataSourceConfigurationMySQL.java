package mx.com.endtoend.infrastructure.configurations.databases.carredana;

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
@EnableJpaRepositories(basePackages = { "mx.com.endtoend.infrastructure.accountingRecord.carredana.repositories",
		"mx.com.endtoend.infrastructure.articles.carredana.customArticles.repositories",
		"mx.com.endtoend.infrastructure.cash.bankReference.carredana.repositories",
		"mx.com.endtoend.infrastructure.cash.closingInstruments.carredana.repositories",
		"mx.com.endtoend.infrastructure.cash.creditCardReference.carredana.repositories",
		"mx.com.endtoend.infrastructure.cash.emailReport.carredana.repositories",
		"mx.com.endtoend.infrastructure.cash.openingInstruments.carredana.repositories",
		"mx.com.endtoend.infrastructure.catalogue.address.carredana.repositories",
		"mx.com.endtoend.infrastructure.catalogue.article.carredana.repositories",
		"mx.com.endtoend.infrastructure.catalogue.client.carredana.repositories",
		"mx.com.endtoend.infrastructure.catalogue.orders.carredana.repositories",
		"mx.com.endtoend.infrastructure.client.carredana.repositories",
		"mx.com.endtoend.infrastructure.closings.carredana.repositories",
		"mx.com.endtoend.infrastructure.creditNote.carredana.repositories",
		"mx.com.endtoend.infrastructure.openings.carredana.repositories",
		"mx.com.endtoend.infrastructure.orderConfigurations.carredana.repositories",
		"mx.com.endtoend.infrastructure.orders.carredana.repositories",
		"mx.com.endtoend.infrastructure.payments.carredana.repositories",
		"mx.com.endtoend.infrastructure.userConfiguration.carredana.repositories",
		"mx.com.endtoend.infrastructure.debug.carredana.repositories",
		"mx.com.endtoend.infrastructure.logs.orders.carredana.repositories",

}, entityManagerFactoryRef = "carredanaDataEntityManagerFactory", transactionManagerRef = "carredanaDataTransactionManager")
public class CarredanaDataSourceConfigurationMySQL {

	@Value("${app.carredana.mysql.datasource.driver-class-name}")
	private String driver;
	@Value("${app.carredana.mysql.datasource.url}")
	private String url;
	@Value("${app.carredana.mysql.username}")
	private String username;
	@Value("${app.carredana.mysql.password}")
	private String password;

	@Bean(name = "carredanaDataSource")
	DataSource carredanaDataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(driver);
		dataSource.setUrl(url);
		dataSource.setUsername(username);
		dataSource.setPassword(password);

		return dataSource;
	}

	@Bean(name = "carredanaDataEntityManagerFactory")
	LocalContainerEntityManagerFactoryBean calzadaDataEntityManagerFactory() {
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
		factory.setPackagesToScan("mx.com.endtoend.infrastructure.accountingRecord.common.entities",
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
		factory.setDataSource(carredanaDataSource());
		return factory;

	}

	@Bean(name = "carredanaDataTransactionManager")
	PlatformTransactionManager carredanaDataTransactionManager(
			@Qualifier("carredanaDataEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
		return new JpaTransactionManager(entityManagerFactory);
	}
}
