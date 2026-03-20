package mx.com.endtoend.genericConfigurations;

import mx.com.endtoend.infrastructure.company.common.repositories.CompanyRepository;
import mx.com.endtoend.infrastructure.permissions.common.coverters.PermissionConverter;
import mx.com.endtoend.infrastructure.permissions.common.repository.PermissionRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import mx.com.endtoend.domain.roles.ports.api.PermissionServicePort;
import mx.com.endtoend.domain.roles.ports.spi.PermissionPersistencePort;
import mx.com.endtoend.domain.roles.services.PermissionServiceImpl;
import mx.com.endtoend.infrastructure.permissions.common.adapters.PermissionJpaAdapter;

@Configuration
public class PermissionConfigurations {

	private final PermissionConverter permissionConverter;
	private final PermissionRepository permissionRepository;
	private final CompanyRepository companyRepository;

	public PermissionConfigurations(PermissionConverter _permissionConverter, PermissionRepository _permissionRepository, CompanyRepository _companyRepository) {
		permissionConverter = _permissionConverter;
		permissionRepository = _permissionRepository;
		companyRepository = _companyRepository;
	}

	@Bean
	public PermissionPersistencePort permissionPersistence() {
		return new PermissionJpaAdapter(permissionConverter, permissionRepository, companyRepository);
	}
	
	@Bean
	public PermissionServicePort permissionServicePort() {
		return new PermissionServiceImpl(permissionPersistence());
	}

}
