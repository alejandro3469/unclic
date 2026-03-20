package mx.com.endtoend.domain.company.ports.api;

import mx.com.endtoend.domain.company.dto.CompanyDto;
import mx.com.endtoend.domain.roles.ports.spi.RolePersistencePort;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

/**
 * 
 * @author ddcasas, labucio
 *
 */

public interface CompanyServicePort {

	ResponseModel companyList(String idOperation, String companyCode);

	ResponseModel saveCompany(CompanyDto companyDto, String idOperation);

	ResponseModel modifyCompany(CompanyDto companyDto, RolePersistencePort rolePersistencePort, String idOperation);

	ResponseModel findCompanyByCode(String companyCode, String idOperation);

	ResponseModel findMethodByCompanyCodeAndModule(String companyCode, String module, String idOperation);

	ResponseModel findAllMethodByModule(String module, String idOperation);

	ResponseModel findById(Long id, String idOperation);

}