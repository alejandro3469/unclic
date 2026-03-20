package mx.com.endtoend.domain.branch.ports.spi;

import mx.com.endtoend.domain.branch.dto.BranchDto;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

public interface BranchPersistencePort {

	ResponseModel createBranch(BranchDto branchDto, String companyCode, String idOperation);

	ResponseModel updateBranch(BranchDto branchDto, String idOperation);

	ResponseModel getBranchListByCompanyCode(String companyCode, String idOperation);

	ResponseModel getBranchById(Long id, String idOperation);

	ResponseModel existsNameByCompanyCode(String name, String companyCode, String idOperation);

	ResponseModel existsCodeByCompanyCode(String code, String companyCode, String idOperation);

	ResponseModel existsNameByCompanyCodeAndIdNot(String name, String companyCode, Long id, String idOperation);

	ResponseModel existsCodeByCompanyCodeAndIdNot(String code, String companyCode, Long id, String idOperation);

	ResponseModel getBranchDetailByBranchCodeAndCompanyCode(String branchCode, String companyCode, String idOperation);

}
