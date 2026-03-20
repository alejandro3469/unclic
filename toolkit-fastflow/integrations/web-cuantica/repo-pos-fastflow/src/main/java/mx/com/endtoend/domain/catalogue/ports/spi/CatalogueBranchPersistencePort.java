package mx.com.endtoend.domain.catalogue.ports.spi;

import mx.com.endtoend.domain.branch.dto.BranchDto;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

public interface CatalogueBranchPersistencePort {

	ResponseModel getBranchListByCompanyCode(String companyCode, String idOperation);

	ResponseModel updateBranchByCompanyCode(BranchDto branchDto, String companyCode, String idOperation);
}
