package mx.com.endtoend.genericConfigurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import mx.com.endtoend.domain.catalogue.ports.api.CatalogueBranchServicePort;
import mx.com.endtoend.domain.catalogue.ports.spi.CatalogueBranchPersistencePort;
import mx.com.endtoend.domain.catalogue.services.CatalogueBranchServicelmpl;
import mx.com.endtoend.infrastructure.catalogue.branch.common.adapter.CatalogueBranchJpaAdapter;

@Configuration
public class CatalogueBranchConfiguration {

	@Bean
	public CatalogueBranchPersistencePort catalogueBranchPersistencePort() {
		return new CatalogueBranchJpaAdapter();
	}

	@Bean
	public CatalogueBranchServicePort catalogueBranchServicePort() {
		return new CatalogueBranchServicelmpl(catalogueBranchPersistencePort());
	}
}
