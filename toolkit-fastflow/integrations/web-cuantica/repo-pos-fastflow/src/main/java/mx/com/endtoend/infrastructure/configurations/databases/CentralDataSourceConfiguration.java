package mx.com.endtoend.infrastructure.configurations.databases;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
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
@EntityScan("mx.com.endtoend")
@ComponentScan("mx.com.endtoend")
@Configuration
@PropertySource("classpath:application.properties")
@EnableJpaRepositories(basePackages = { "mx.com.endtoend.infrastructure.roles",
		"mx.com.endtoend.infrastructure.permissions", "mx.com.endtoend.infrastructure.company",
		"mx.com.endtoend.infrastructure.users", "mx.com.endtoend.infrastructure.strategy",
		"mx.com.endtoend.infrastructure.branch",
		"mx.com.endtoend.infrastructure.mail",
		"mx.com.endtoend.infrastructure.logs.security.repositories"

}, entityManagerFactoryRef = "centralDataEntityManagerFactory", transactionManagerRef = "centralDataTransactionManager")
public class CentralDataSourceConfiguration {

	@Value("${app.datasource.driver-class-name}")
	private String driver;
	@Value("${app.datasource.url}")
	private String url;
	@Value("${app.datasource.username}")
	private String username;
	@Value("${app.datasource.password}")
	private String password;

	@Primary
	@Bean(name = "centralDataSource")
	DataSource centralDataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(driver);
		dataSource.setUrl(url);
		dataSource.setUsername(username);
		dataSource.setPassword(password);

		return dataSource;
	}

	@Primary
	@Bean(name = "centralDataEntityManagerFactory")
	LocalContainerEntityManagerFactoryBean centralDataEntityManagerFactory() {
		// Detectar automáticamente el tipo de base de datos desde la URL
		// Si es H2 (desarrollo local), usar H2, de lo contrario MySQL
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
		factory.setPackagesToScan("mx.com.endtoend.infrastructure.roles", "mx.com.endtoend.infrastructure.permissions",
				"mx.com.endtoend.infrastructure.company", "mx.com.endtoend.infrastructure.users",
				"mx.com.endtoend.infrastructure.strategy", "mx.com.endtoend.infrastructure.branch",
				"mx.com.endtoend.infrastructure.mail", "mx.com.endtoend.infrastructure.logs.security.entities");
		factory.setDataSource(centralDataSource());
		return factory;

	}

	@Primary
	@Bean(name = "centralDataTransactionManager")
	PlatformTransactionManager centralDataTransactionManager(
			@Qualifier("centralDataEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
		return new JpaTransactionManager(entityManagerFactory);
	}

}
