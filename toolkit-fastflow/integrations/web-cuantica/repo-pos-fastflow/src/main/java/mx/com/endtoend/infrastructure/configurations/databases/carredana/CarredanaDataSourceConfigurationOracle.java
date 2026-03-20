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
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

@Component
@ComponentScan("mx.com.endtoend")
@Configuration
@PropertySource("classpath:application.properties")
@ConditionalOnProperty(name = "app.carredana.oracle.enabled", havingValue = "true", matchIfMissing = false)
@EnableJpaRepositories(basePackages = { "mx.com.endtoend.infrastructure.warehouse.carredana.repositories",
		"mx.com.endtoend.infrastructure.warehouse.carredana.zapata.repositories",
		"mx.com.endtoend.infrastructure.articles.carredana.oracle.repositories",
		"mx.com.endtoend.infrastructure.services.jde.catalogue.carredana.common.repository",
        "mx.com.endtoend.infrastructure.services.jde.clients.carredana.repository",
		"mx.com.endtoend.infrastructure.services.jde.orders.carredana.common.repository",
		"mx.com.endtoend.infrastructure.services.jde.payments.carredana.common.repository",

}, entityManagerFactoryRef = "carredanaOracleEntityManagerFactory", transactionManagerRef = "carredanaOracleDataTransactionManager")
public class CarredanaDataSourceConfigurationOracle {

	@Value("${app.carredana.oracle.datasource.driver-class-name}")
	private String driver;
	@Value("${app.carredana.oracle.jpa.database-platform}")
	private String platform;
	@Value("${app.carredana.oracle.datasource.url}")
	private String url;
	@Value("${app.carredana.oracle.datasource.username}")
	private String username;
	@Value("${app.carredana.oracle.datasource.password}")
	private String password;

	// ESQUEMA PARA DESARROLLO CARREDANA
	public static final String dynamicSchema = "CARRPYCTL";
	public static final String dynamicSchema2 = "CARRPYDTA";

	// ESQUEMA PARA PRODUCTIVO CARREDANA
	// public static final String dynamicSchema = "CARRCTL";
	// public static final String dynamicSchema2 = "CARRDTA";

	@Bean(name = "carredanaOracleDataSource")
	DataSource carredanaOracleDataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(driver);
		dataSource.setUrl(url);
		dataSource.setUsername(username);
		dataSource.setPassword(password);
		return dataSource;
	}

	@Bean(name = "carredanaOracleEntityManagerFactory")
	LocalContainerEntityManagerFactoryBean itemEntityManager() {

		try {

			HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();

			vendorAdapter.setShowSql(true);
			vendorAdapter.setDatabase(Database.ORACLE);
			vendorAdapter.setGenerateDdl(false);
			vendorAdapter.setDatabasePlatform(platform);

			LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
			factory.setJpaVendorAdapter(vendorAdapter);
			factory.setPackagesToScan("mx.com.endtoend.infrastructure.warehouse.common.entities",
					"mx.com.endtoend.infrastructure.articles.carredana.oracle.entities",
					"mx.com.endtoend.infrastructure.services.jde.catalogue.carredana.common.entities",
					"mx.com.endtoend.infrastructure.services.jde.clients.carredana.entities",
					"mx.com.endtoend.infrastructure.services.jde.orders.carredana.common.entities",
					"mx.com.endtoend.infrastructure.services.jde.payments.carredana.common.entities");
			factory.setDataSource(carredanaOracleDataSource());

			return factory;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Bean(name = "carredanaOracleDataTransactionManager")
	PlatformTransactionManager calzadaOracleDataTransactionManager(
			@Qualifier("carredanaOracleEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
		return new JpaTransactionManager(entityManagerFactory);
	}
}
