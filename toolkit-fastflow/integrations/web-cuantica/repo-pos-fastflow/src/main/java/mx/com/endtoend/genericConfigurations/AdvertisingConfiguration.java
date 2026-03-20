package mx.com.endtoend.genericConfigurations;

import mx.com.endtoend.infrastructure.advertising.common.factory.AdversitingRepositoryFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import mx.com.endtoend.domain.advertising.ports.AdvertisingPersistencePort;
import mx.com.endtoend.domain.advertising.ports.AdvertisingServicePort;
import mx.com.endtoend.domain.advertising.services.AdvertisingServiceImpl;
import mx.com.endtoend.infrastructure.advertising.common.adapter.AdversitingJpaAdapter;

@Configuration
public class AdvertisingConfiguration {
	private final AdversitingRepositoryFactory adversitingRepositoryFactory;

	public AdvertisingConfiguration(AdversitingRepositoryFactory _adversitingRepositoryFactory) {
		adversitingRepositoryFactory =  _adversitingRepositoryFactory;
	}

	@Bean
	public AdvertisingPersistencePort advertisingPersistencePort() {
		return new AdversitingJpaAdapter(adversitingRepositoryFactory);
	}
	
	@Bean
	public AdvertisingServicePort advertisingServicePort() {
		return new AdvertisingServiceImpl(advertisingPersistencePort());
	}

}
