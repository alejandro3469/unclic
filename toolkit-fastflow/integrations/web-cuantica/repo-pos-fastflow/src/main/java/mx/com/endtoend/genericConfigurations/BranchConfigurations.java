package mx.com.endtoend.genericConfigurations;

import mx.com.endtoend.infrastructure.branch.common.converters.BranchConverter;
import mx.com.endtoend.infrastructure.branch.common.repositories.BranchRepository;
import mx.com.endtoend.infrastructure.company.common.converters.CompanyConverter;
import mx.com.endtoend.infrastructure.company.common.repositories.CompanyRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import mx.com.endtoend.domain.branch.ports.api.BranchServicePort;
import mx.com.endtoend.domain.branch.ports.spi.BranchPersistencePort;
import mx.com.endtoend.domain.branch.services.BranchServiceImpl;
import mx.com.endtoend.infrastructure.branch.common.adapters.BranchJpaAdapter;

@Configuration
public class BranchConfigurations {

	private final CompanyRepository companyRepository;
	private final CompanyConverter companyConverter;
	private final BranchConverter branchConverter;
	private final BranchRepository branchRepository;
	public BranchConfigurations(CompanyRepository _companyRepository,
								CompanyConverter _companyConverter,
								BranchConverter _branchConverter,
								BranchRepository _branchRepository) {
		companyRepository = _companyRepository;
		companyConverter = _companyConverter;
		branchConverter = _branchConverter;
		branchRepository = _branchRepository;
	}

	@Bean
	public BranchPersistencePort branchPersistence() {
		return new BranchJpaAdapter(companyRepository, companyConverter, branchConverter, branchRepository);
	}
	
	@Bean
	public BranchServicePort branchServicePort() {
		return new BranchServiceImpl(branchPersistence());
	}
}
