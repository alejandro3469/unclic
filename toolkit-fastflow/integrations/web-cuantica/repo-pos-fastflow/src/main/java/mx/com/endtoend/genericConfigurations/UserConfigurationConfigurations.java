package mx.com.endtoend.genericConfigurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import mx.com.endtoend.domain.userConfigurations.ports.api.UserConfigurationServicePort;
import mx.com.endtoend.domain.userConfigurations.ports.spi.UserConfigurationPersistencePort;
import mx.com.endtoend.domain.userConfigurations.services.UserConfigurationServiceImpl;
import mx.com.endtoend.infrastructure.userConfiguration.common.adapter.UserConfigurationJpaAdapter;

/**
 * 
 * @author ddcasas
 *
 */

@Configuration
public class UserConfigurationConfigurations {

	
	@Bean 
	public UserConfigurationPersistencePort userConfigurationPersistence() {
		return new UserConfigurationJpaAdapter();
	}
	
	
	@Bean
	public UserConfigurationServicePort userConfiguration() {
		return new UserConfigurationServiceImpl(userConfigurationPersistence());
	}
}
