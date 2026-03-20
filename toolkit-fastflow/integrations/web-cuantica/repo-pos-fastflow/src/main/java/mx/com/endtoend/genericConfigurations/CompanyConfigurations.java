package mx.com.endtoend.genericConfigurations;

import mx.com.endtoend.infrastructure.company.common.converters.CompanyConverter;
import mx.com.endtoend.infrastructure.company.common.converters.MethodConverter;
import mx.com.endtoend.infrastructure.company.common.converters.PermissionCompanyConverter;
import mx.com.endtoend.infrastructure.company.common.repositories.CompanyRepository;
import mx.com.endtoend.infrastructure.company.common.repositories.MethodRepository;
import mx.com.endtoend.infrastructure.company.common.repositories.PermissionCompanyRepository;
import mx.com.endtoend.infrastructure.permissions.common.coverters.PermissionConverter;
import mx.com.endtoend.infrastructure.permissions.common.repository.PermissionRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import mx.com.endtoend.domain.company.ports.api.CompanyServicePort;
import mx.com.endtoend.domain.company.ports.spi.CompanyPersistencePort;
import mx.com.endtoend.domain.company.services.CompanyServicelmpl;
import mx.com.endtoend.infrastructure.company.common.adapters.CompanyJpaAdapter;

@Configuration
public class CompanyConfigurations {

	private final CompanyRepository companyRepository;
	private final CompanyConverter companyConverter;
	private final MethodRepository methodRepository;
	private final MethodConverter methodConverter;
	private final PermissionCompanyConverter permissionCompanyConverter;
	private final PermissionCompanyRepository permissionCompanyRepository;
	private final PermissionRepository permissionRepository;
	private final PermissionConverter permissionConverter;

	public CompanyConfigurations(CompanyRepository _companyRepository,
								 CompanyConverter _companyConverter,
								 MethodRepository _methodRepository,
								 MethodConverter _methodConverter,
								 PermissionCompanyConverter _permissionCompanyConverter,
								 PermissionCompanyRepository _permissionCompanyRepository,
								 PermissionRepository _permissionRepository,
								 PermissionConverter _permissionConverter){
		companyRepository = _companyRepository;
		companyConverter = _companyConverter;
		methodRepository = _methodRepository;
		methodConverter = _methodConverter;
		permissionCompanyConverter = _permissionCompanyConverter;
		permissionCompanyRepository = _permissionCompanyRepository;
		permissionRepository = _permissionRepository;
		permissionConverter = _permissionConverter;
	}

	@Bean
	public CompanyPersistencePort companyPersistence() {
		return new CompanyJpaAdapter(companyRepository,
				companyConverter,
				methodRepository,
				methodConverter,
				permissionCompanyConverter,
				permissionCompanyRepository,
				permissionRepository,
				permissionConverter);
	}
	
	@Bean
	public CompanyServicePort companyServicePort() {
		return new CompanyServicelmpl(companyPersistence());
	}
}
