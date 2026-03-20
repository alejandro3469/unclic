package mx.com.endtoend.infrastructure.advertising.common.factory;

import mx.com.endtoend.infrastructure.advertising.common.repository.BaseAdversitingRespositoryFactory;
import org.springframework.stereotype.Component;

import mx.com.endtoend.infrastructure.advertising.calzada.AdvertisingCalzadaRepository;

@Component
public class AdversitingRepositoryFactory extends BaseAdversitingRespositoryFactory {

	 public AdversitingRepositoryFactory(AdvertisingCalzadaRepository adversitingCalzadaRepository) {
		 super(AdversitingRepositoryFactory.class,
				 adversitingCalzadaRepository);
	 }
}
