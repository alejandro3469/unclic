package mx.com.endtoend.domain.cash.emailReport.business.validations;

import java.util.regex.Pattern;

import mx.com.endtoend.domain.cash.emailReport.dto.EmailReportCashDto;
import mx.com.endtoend.domain.cash.emailReport.ports.EmailCashConfigurationPersistencePort;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

public class GenericEmailCashConfigurationValidation {

	public String emailReportValidation(EmailCashConfigurationPersistencePort emailCashConfigurationPersistencePort,
			EmailReportCashDto emailReportCashDto, String companyCode, String idOperation) {

		String validations = "";

		if (emailReportCashDto == null)
			return "INVALID DATA";

		validations = emailReportCashDto.getEmail() != null
				? emailReportCashDto.getEmail().isEmpty() ? "EMAIL IS REQUIED" : ""
				: "EMAIL IS REQUIRED";

		validations = emailReportCashDto.getId() == null
				? validations + validExsisteEmail(emailCashConfigurationPersistencePort, companyCode, idOperation)
				: validations;

		validations = emailReportCashDto.getEmail() != null ? validations + validFormat(emailReportCashDto.getEmail())
				: validations;
		return validations;
	}

	private String validExsisteEmail(EmailCashConfigurationPersistencePort emailCashConfigurationPersistencePort,
			String companyCode, String idOperation) {
		ResponseModel responseExists = emailCashConfigurationPersistencePort.getEmailReportByCompanyCode(companyCode,
				idOperation);
		EmailReportCashDto emailReportCashDto = (EmailReportCashDto) responseExists.getData();
		return emailReportCashDto == null ? "" : " -EXISTS EMAIL TO REPORTS- ";
	}

	/**
	 * Método que valida el formato del email ingresado con la expreción regular del
	 * RFC 5322
	 * 
	 * @param email correo electrónico a ingresar
	 * 
	 * @return cadena String
	 */
	private String validFormat(String email) {
		String validFormat = "";
		String regexPattern = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
		boolean isValid = Pattern.compile(regexPattern).matcher(email).matches();
		validFormat = isValid ? "" : "-INVALID EMAIL FORMAT-";
		return validFormat;
	}

}
