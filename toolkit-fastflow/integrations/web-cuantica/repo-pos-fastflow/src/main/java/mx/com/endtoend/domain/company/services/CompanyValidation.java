package mx.com.endtoend.domain.company.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.com.endtoend.domain.company.dto.CompanyDto;
import mx.com.endtoend.domain.company.ports.spi.CompanyPersistencePort;

/**
 * Clase para validacion de datos operativos en la administración de compañías
 * del sistema
 * 
 * @author ddcasas
 */
public class CompanyValidation {

	private final static Logger LOG = LoggerFactory.getLogger(CompanyValidation.class);

	public String validOperativeDataToCreate(CompanyPersistencePort companyPersistencePort, CompanyDto companyDto,
			String idOperation) {
		
		String validations = "";
		validations = validRequiredFields(companyDto, validations);

		boolean existsCompany = (boolean) companyPersistencePort.existsCompany(companyDto, idOperation).getData();
		if (existsCompany) {
			LOG.warn(String.format("%s THE COMPANY ALREADY EXISTS", idOperation));
			validations += "-COMPANY CODE ALREADY EXISTS ";
		}

		return validations;
	}
	
	public String validOperativeDataToUpdate(CompanyPersistencePort companyPersistencePort, CompanyDto companyDto,
			String idOperation) {
		
		String validations = "";
		validations = validRequiredFields(companyDto, validations);

		LOG.info(String.format("%s VALID EXIST COMPANY BY CODE AND ID NOT", idOperation));
		boolean existsCompany = (boolean) companyPersistencePort.existsCompanyByCodeAndIdNot(companyDto, idOperation)
				.getData();
		if (existsCompany) {
			LOG.warn(String.format("%s THE COMPANY ALREADY EXISTS", idOperation));
			validations += "-COMPANY CODE ALREADY EXISTS ";
		}

		return validations;
	}
	
	

	private String validRequiredFields(CompanyDto companyDto, String validations) {
		validations += companyDto.getName() == null ? " -NAME IS REQUIRED" : "";
		validations += companyDto.getName() != null ? companyDto.getName().isEmpty() ? " -NAME IS REQUIRED" : "" : "";

		validations += companyDto.getRfc() == null ? " -RFC IS REQUIRED" : "";
		validations += companyDto.getRfc() != null ? companyDto.getRfc().isEmpty() ? " -RFC IS REQUIRED" : "" : "";

		validations += companyDto.getCode() == null ? " -COMPANY CODE IS REQUIRED" : "";
		validations += companyDto.getCode() != null ? companyDto.getCode().isEmpty() ? " -COMPANY CODE IS REQUIRED" : ""
				: "";

		validations += companyDto.getCompanyNumber() == null ? " -COMPANY NUMBER IS REQUIRED" : "";
		validations += companyDto.getCompanyNumber() != null
				? companyDto.getCompanyNumber().isEmpty() ? " -COMPANY NUMBER IS REQUIRED" : ""
				: "";

		validations += companyDto.getMethods() == null ? " -METHOD LIST IS REQUIRED" : "";
		validations += companyDto.getMethods() != null
				? companyDto.getMethods().size() <= 0 ? " -METHOS LIST IS REQUIRED" : ""
				: "";

		validations += companyDto.getPermissions() == null ? " -PERMISSIONS LIST IS REQUIRED" : "";
		validations += companyDto.getPermissions() != null
				? companyDto.getPermissions().size() <= 0 ? " -PERMISSIONS LIST IS REQUIRED" : ""
				: "";
		return validations;
	}
}
