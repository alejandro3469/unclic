package mx.com.endtoend.infrastructure.catalogue.branch.carredana.zapata.business;

import mx.com.endtoend.infrastructure.catalogue.branch.common.business.BaseCatalogueBranchBusinessRepository;
import org.springframework.stereotype.Service;

import mx.com.endtoend.infrastructure.branch.common.converters.BranchConverter;
import mx.com.endtoend.infrastructure.branch.common.repositories.BranchRepository;
import mx.com.endtoend.infrastructure.company.common.converters.CompanyConverter;

@Service
public class CatalogueBranchCZapataRepository extends BaseCatalogueBranchBusinessRepository {
	public CatalogueBranchCZapataRepository(BranchRepository _branchRepository,
											 BranchConverter _branchConverter,
											 CompanyConverter _companyConverter){
		super(CatalogueBranchCZapataRepository.class, _branchRepository,_branchConverter, _companyConverter);
	}
}
