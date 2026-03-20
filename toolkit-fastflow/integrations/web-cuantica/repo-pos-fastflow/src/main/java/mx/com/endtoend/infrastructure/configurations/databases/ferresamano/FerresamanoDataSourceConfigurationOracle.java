package mx.com.endtoend.infrastructure.configurations.databases.ferresamano;

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
@ConditionalOnProperty(name = "app.ferresamano.oracle.enabled", havingValue = "true", matchIfMissing = false)
@EnableJpaRepositories(basePackages = { 
		"mx.com.endtoend.infrastructure.warehouse.ferresamano.repositories",
		"mx.com.endtoend.infrastructure.articles.ferresamano.oracle.repositories",
		"mx.com.endtoend.infrastructure.services.jde.catalogue.ferresamano.common.repository",
        "mx.com.endtoend.infrastructure.services.jde.clients.ferresamano.repository",
		"mx.com.endtoend.infrastructure.services.jde.orders.ferresamano.common.repository",
		"mx.com.endtoend.infrastructure.services.jde.payments.ferresamano.common.repository",

}, entityManagerFactoryRef = "ferresamanoOracleEntityManagerFactory", transactionManagerRef = "ferresamanoOracleDataTransactionManager")
public class FerresamanoDataSourceConfigurationOracle {

	@Value("${app.ferresamano.oracle.datasource.driver-class-name}")
	private String driver;
	@Value("${app.ferresamano.oracle.jpa.database-platform}")
	private String platform;
	@Value("${app.ferresamano.oracle.datasource.url}")
	private String url;
	@Value("${app.ferresamano.oracle.datasource.username}")
	private String username;
	@Value("${app.ferresamano.oracle.datasource.password}")
	private String password;

	// ESQUEMA PARA PRUEBAS (QAS) FERRESAMANO
	public static final String dynamicSchema = "CRPCTL";
	public static final String dynamicSchema2 = "CRPDTA";

	// ESQUEMA PARA PRODUCTIVO FERRESAMANO
	// public static final String dynamicSchema = "PRODCTL";
	// public static final String dynamicSchema2 = "PRODDTA";

	@Bean(name = "ferresamanoOracleDataSource")
	public DataSource ferresamanoOracleDataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(driver);
		dataSource.setUrl(url);
		dataSource.setUsername(username);
		dataSource.setPassword(password);
		return dataSource;
	}

	@Bean(name = "ferresamanoOracleEntityManagerFactory")
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
					"mx.com.endtoend.infrastructure.articles.ferresamano.oracle.entities",
					"mx.com.endtoend.infrastructure.services.jde.catalogue.ferresamano.common.entities",
					"mx.com.endtoend.infrastructure.services.jde.clients.ferresamano.entities",
					"mx.com.endtoend.infrastructure.services.jde.orders.ferresamano.common.entities",
					"mx.com.endtoend.infrastructure.services.jde.payments.ferresamano.common.entities");
			factory.setDataSource(ferresamanoOracleDataSource());

			return factory;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Bean(name = "ferresamanoOracleDataTransactionManager")
	public PlatformTransactionManager ferresamanoOracleDataTransactionManager(
			@Qualifier("ferresamanoOracleEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
		return new JpaTransactionManager(entityManagerFactory);
	}
}

