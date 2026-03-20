package mx.com.endtoend.infrastructure.configurations.databases.calzada;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

@Component
@ComponentScan("mx.com.endtoend")
@Configuration
@PropertySource("classpath:application.properties")
@ConditionalOnProperty(name = "app.calzadaOracle.enabled", havingValue = "true", matchIfMissing = false)
@EnableJpaRepositories(basePackages = { "mx.com.endtoend.infrastructure.services.jde.clients.calzada.repository",
		"mx.com.endtoend.infrastructure.services.jde.catalogue.calzada.common.repository",
		"mx.com.endtoend.infrastructure.warehouse.calzada",
		"mx.com.endtoend.infrastructure.articles.calzada.oracle.repositories",
		"mx.com.endtoend.infrastructure.services.jde.orders.calzada.common.repository",
		"mx.com.endtoend.infrastructure.services.jde.payments.calzada.common.repository" }, entityManagerFactoryRef = "calzadaOracleEntityManagerFactory", transactionManagerRef = "calzadaOracleDataTransactionManager")
public class CalzadaDataSourceConfigurationOracle {

	@Value("${app.calzadaOracle.datasource.driver-class-name}")
	private String driver;
	@Value("${app.calzadaOracle.jpa.database-platform}")
	private String platform;
	@Value("${app.calzadaOracle.datasource.url}")
	private String url;
	@Value("${app.calzadaOracle.datasource.username}")
	private String username;
	@Value("${app.calzadaOracle.datasource.password}")
	private String password;

	// ESQUEMA PARA DESARROLLO CALZADA
	//public static final String dynamicSchema="CRPCTL";
	//public static final String dynamicSchema2="CRPDTA";

	// ESQUEMA PARA DESARROLLO CALZADA PRD - PRODDTA / DESARROLLO CRPDTA
	// ESQUEMA PARA CALZADA PRD - PRODCTL
	public static final String dynamicSchema = "PRODCTL";
	public static final String dynamicSchema2 = "PRODDTA";

	@Bean(name = "calzadaOracleDataSource")
	public DataSource calzadaOracleDataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(driver);
		dataSource.setUrl(url);
		dataSource.setUsername(username);
		dataSource.setPassword(password);
		return dataSource;
	}

	@Bean(name = "calzadaOracleEntityManagerFactory")
	public LocalContainerEntityManagerFactoryBean itemEntityManager() {

		try {

			HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();

			vendorAdapter.setShowSql(true);
			vendorAdapter.setDatabase(Database.ORACLE);
			vendorAdapter.setGenerateDdl(false);
			vendorAdapter.setDatabasePlatform(platform);

			LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
			factory.setJpaVendorAdapter(vendorAdapter);
			factory.setPackagesToScan("mx.com.endtoend.infrastructure.services.jde.clients.calzada.entities",
					"mx.com.endtoend.infrastructure.services.jde.catalogue.calzada.common.entities",
					"mx.com.endtoend.infrastructure.warehouse.common.entities",
					"mx.com.endtoend.infrastructure.articles.calzada.oracle",
					"mx.com.endtoend.infrastructure.services.jde.orders.calzada.common.entities",
					"mx.com.endtoend.infrastructure.services.jde.payments.calzada.common.entities");
			factory.setDataSource(calzadaOracleDataSource());

			return factory;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Bean(name = "calzadaOracleDataTransactionManager")
	public PlatformTransactionManager calzadaOracleDataTransactionManager(
			@Qualifier("calzadaOracleEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
		return new JpaTransactionManager(entityManagerFactory);
	}
}
