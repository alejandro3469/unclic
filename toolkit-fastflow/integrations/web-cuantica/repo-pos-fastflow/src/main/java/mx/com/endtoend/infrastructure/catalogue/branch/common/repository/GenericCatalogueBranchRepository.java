package mx.com.endtoend.infrastructure.catalogue.branch.common.repository;

import mx.com.endtoend.domain.branch.dto.BranchDto;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

public interface GenericCatalogueBranchRepository {

	ResponseModel getBranchListByCompanyCode(String companyCode, String idOperation);

	ResponseModel updateBranchByCompanyCode(BranchDto branchDto, String companyCode, String idOperation);

}
