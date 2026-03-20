package mx.com.endtoend.infrastructure.configurations.databases.calzada.fragua;

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
@ConditionalOnProperty(name = "app.fraguaSQL.enabled", havingValue = "true", matchIfMissing = false)
@EnableJpaRepositories(basePackages = {
		"mx.com.endtoend.infrastructure.services.posLegacy.clients.calzada.fragua.repositories",
		"mx.com.endtoend.infrastructure.services.posLegacy.clients.common.repository",
		"mx.com.endtoend.infrastructure.services.posLegacy.orders.calzada.fragua.repositories",
		"mx.com.endtoend.infrastructure.services.posLegacy.orders.common.repository"
		},
	entityManagerFactoryRef = "fraguaSQLDataEntityManagerFactory", 
	transactionManagerRef = "fraguaSQLDataTransactionManager")
public class FraguaDataSourceConfigurationSQL {
	
	@Value("${app.fraguaSQL.datasource.driver-class-name}")
	private String driver;
	@Value("${app.fraguaSQL.datasource.url}")
	private String url;
	@Value("${app.fraguaSQL.datasource.username}")
	private String username;
	@Value("${app.fraguaSQL.datasource.password}")
	private String password;
	
	@Bean(name = "fraguaSQLDataSource")
	public DataSource calzadaSQLDataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(driver);
		dataSource.setUrl(url);
		dataSource.setUsername(username);
		dataSource.setPassword(password);
		
		
		
		return dataSource;
	}
	
	@Bean(name = "fraguaSQLDataEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean calzadaSQLDataEntityManagerFactory() {
        
		
		 HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		 vendorAdapter.setShowSql(true);
		 vendorAdapter.setDatabase(Database.SQL_SERVER);
		 vendorAdapter.setGenerateDdl(false);
		 
		 LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		 factory.setJpaVendorAdapter(vendorAdapter);
		 factory.setPackagesToScan(
				"mx.com.endtoend.infrastructure.services.posLegacy.clients.common.entities",
				"mx.com.endtoend.infrastructure.services.posLegacy.orders.common.entities"
				);
		 factory.setDataSource(calzadaSQLDataSource());
		 return factory;
		
	
    }
	
    @Bean(name = "fraguaSQLDataTransactionManager")
    public PlatformTransactionManager calzadaSQLDataTransactionManager(
            @Qualifier("fraguaSQLDataEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
	

}
