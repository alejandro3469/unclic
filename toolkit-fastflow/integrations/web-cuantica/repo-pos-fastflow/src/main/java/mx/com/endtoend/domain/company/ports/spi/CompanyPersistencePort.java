package mx.com.endtoend.domain.company.ports.spi;

import mx.com.endtoend.domain.company.dto.CompanyDto;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

/**
 * 
 * @author labucio, ddcasas
 *
 */

public interface CompanyPersistencePort {
	
	ResponseModel companyList(String idOperation);
	
	ResponseModel saveToModifyCompany(CompanyDto companyDto, String idOperation);
	
	ResponseModel existsCompany(CompanyDto companyDto, String idOperation);
	
	ResponseModel existsCompanyByCodeAndIdNot(CompanyDto companyDto, String idOperation);
	 
	ResponseModel findByCode(String companyCode, String idOperation);
	
	ResponseModel findByCompanyCodeAndModule(String companyCode, String module, String idOperation);
	
	ResponseModel findAllByModule(String module, String idOperation);
	
	ResponseModel findById(Long id, String idOperation);
	
	void savePermissionToCompany(CompanyDto companyDto, String idOperation);
	
	void updatePermissionCompanyList(CompanyDto companyDto, String idOperation);
	
}