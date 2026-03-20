package mx.com.endtoend.infrastructure.configurations.databases.demo;

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
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

@Component
@ComponentScan("mx.com.endtoend")
@Configuration
@PropertySource("classpath:application.properties")
@ConditionalOnProperty(name = "app.demo.oracle.enabled", havingValue = "true", matchIfMissing = false)
@EnableJpaRepositories(basePackages = { "mx.com.endtoend.infrastructure.warehouse.demo.repositories",
		"mx.com.endtoend.infrastructure.articles.demo.oracle.repositories",
		"mx.com.endtoend.infrastructure.services.jde.catalogue.demo.common.repository",
        "mx.com.endtoend.infrastructure.services.jde.clients.demo.repository",
		"mx.com.endtoend.infrastructure.services.jde.orders.demo.common.repository",
		"mx.com.endtoend.infrastructure.services.jde.payments.demo.common.repository",

}, entityManagerFactoryRef = "demoOracleEntityManagerFactory", transactionManagerRef = "demoOracleDataTransactionManager")
public class DemoDataSourceConfigurationOracle {

	@Value("${app.demo.oracle.datasource.driver-class-name}")
	private String driver;
	@Value("${app.demo.oracle.jpa.database-platform}")
	private String platform;
	@Value("${app.demo.oracle.datasource.url}")
	private String url;
	@Value("${app.demo.oracle.datasource.username}")
	private String username;
	@Value("${app.demo.oracle.datasource.password}")
	private String password;

	// ESQUEMA PARA DESARROLLO DEMO
	public static final String dynamicSchema="PRODCTL";
	public static final String dynamicSchema2="PRODDTA";

	// ESQUEMA PARA PRODUCTIVO DEMO
	// public static final String dynamicSchema = "DEMOCCTL";
	// public static final String dynamicSchema2 = "DEMODTA";

	@Bean(name = "demoOracleDataSource")
	public DataSource demoOracleDataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(driver);
		dataSource.setUrl(url);
		dataSource.setUsername(username);
		dataSource.setPassword(password);
		return dataSource;
	}

	@Bean(name = "demoOracleEntityManagerFactory")
	public LocalContainerEntityManagerFactoryBean itemEntityManager() {

		try {

			HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();

			vendorAdapter.setShowSql(true);
			vendorAdapter.setDatabase(Database.ORACLE);
			vendorAdapter.setGenerateDdl(false);
			vendorAdapter.setDatabasePlatform(platform);

			LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
			factory.setJpaVendorAdapter(vendorAdapter);
			factory.setPackagesToScan("mx.com.endtoend.infrastructure.warehouse.common.entities",
					"mx.com.endtoend.infrastructure.articles.demo.oracle.entities",
					"mx.com.endtoend.infrastructure.services.jde.catalogue.demo.common.entities",
					"mx.com.endtoend.infrastructure.services.jde.clients.demo.entities",
					"mx.com.endtoend.infrastructure.services.jde.orders.demo.common.entities",
					"mx.com.endtoend.infrastructure.services.jde.payments.demo.common.entities");
			factory.setDataSource(demoOracleDataSource());

			return factory;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Bean(name = "demoOracleDataTransactionManager")
	public PlatformTransactionManager demoOracleDataTransactionManager(
			@Qualifier("demoOracleEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
		return new JpaTransactionManager(entityManagerFactory);
	}
}
