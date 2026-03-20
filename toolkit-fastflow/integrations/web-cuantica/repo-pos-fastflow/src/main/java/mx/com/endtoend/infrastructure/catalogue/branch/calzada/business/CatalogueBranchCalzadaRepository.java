package mx.com.endtoend.infrastructure.catalogue.branch.calzada.business;

import mx.com.endtoend.infrastructure.catalogue.branch.common.business.BaseCatalogueBranchBusinessRepository;
import org.springframework.stereotype.Service;
import mx.com.endtoend.infrastructure.branch.common.converters.BranchConverter;
import mx.com.endtoend.infrastructure.branch.common.repositories.BranchRepository;
import mx.com.endtoend.infrastructure.company.common.converters.CompanyConverter;

@Service
public class CatalogueBranchCalzadaRepository extends BaseCatalogueBranchBusinessRepository {
	public CatalogueBranchCalzadaRepository(BranchRepository _branchRepository,
											BranchConverter _branchConverter,
											CompanyConverter _companyConverter){
		super(CatalogueBranchCalzadaRepository.class, _branchRepository,_branchConverter, _companyConverter);
	}
}
