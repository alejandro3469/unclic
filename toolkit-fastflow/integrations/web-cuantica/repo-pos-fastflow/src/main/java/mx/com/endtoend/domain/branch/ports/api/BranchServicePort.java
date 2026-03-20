package mx.com.endtoend.domain.branch.ports.api;

import mx.com.endtoend.domain.branch.dto.BranchDto;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

public interface BranchServicePort {

	ResponseModel createBranch(BranchDto branchDto, String companyCode, String idOperation);
	
	ResponseModel updateBranch(BranchDto branchDto, String companyCode, String idOperation);
	
	ResponseModel getBranchListByCompanyCode(String companyCode, String idOperation);
	
	ResponseModel findById(Long id, String idOperation);
	
}
