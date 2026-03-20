package mx.com.endtoend.infrastructure.branch.common.adapters;


import mx.com.endtoend.infrastructure.branch.common.converters.BranchConverter;
import mx.com.endtoend.infrastructure.branch.common.repositories.BranchRepository;
import mx.com.endtoend.infrastructure.company.common.converters.CompanyConverter;
import mx.com.endtoend.infrastructure.company.common.repositories.CompanyRepository;

public class BranchJpaAdapter extends BaseBranchJpaAdapter {


	public BranchJpaAdapter(CompanyRepository companyRepository,
							CompanyConverter companyConverter,
							BranchConverter branchConverter,
							BranchRepository branchRepository) {
		super(BranchJpaAdapter.class,
				companyRepository,
				companyConverter,
				branchConverter,
				branchRepository
				);
	}
}
