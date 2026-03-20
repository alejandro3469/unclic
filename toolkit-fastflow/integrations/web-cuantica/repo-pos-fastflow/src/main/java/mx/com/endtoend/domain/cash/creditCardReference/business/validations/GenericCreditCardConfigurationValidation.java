package mx.com.endtoend.domain.cash.creditCardReference.business.validations;

import java.math.BigDecimal;
import java.util.List;

import mx.com.endtoend.domain.cash.creditCardReference.dto.CreditCardDto;
import mx.com.endtoend.domain.cash.creditCardReference.dto.PaymentOptionDto;
import mx.com.endtoend.domain.cash.creditCardReference.ports.CreditCardConfigurationPersistencePort;
import mx.com.endtoend.smart.bussiness.constants.CreditCardTypeEnum;
import mx.com.endtoend.smart.bussiness.constants.PaymentPeriodEnum;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

public class GenericCreditCardConfigurationValidation {

	/**
	 * Método para la validacion de reglas de negocio en la creacion de datos del
	 * catálogo de tarjetas de crédito
	 * 
	 * @param creditCardConfigurationPersistencePort
	 * @param creditCardDto
	 * @param companyCode
	 * @param idOperation
	 * 
	 * @return
	 */
	public String cardReferenceValidationOnCreate(
			CreditCardConfigurationPersistencePort creditCardConfigurationPersistencePort, CreditCardDto creditCardDto,
			String companyCode, String idOperation) {
		String validations = "";
		if (creditCardDto == null) {
			return "INVALID OBJECT";
		}
		validations = requiredField(creditCardDto);
		validations += validCommissionApply(creditCardDto);
		validations = validations.isEmpty()
				? validExistsByCodeOrInstitution(creditCardConfigurationPersistencePort, creditCardDto, validations,
						companyCode, idOperation)
				: validations;
		validations = validations.isEmpty()
				? validExistsByPeriodAndInstitution(creditCardConfigurationPersistencePort, creditCardDto, validations,
						companyCode, idOperation)
				: validations;
		return validations;
	}

	/**
	 * Método para la validacion de reglas de negocio en la actualización de datos
	 * del catálogo de tarjetas de crédito
	 * 
	 * @param creditCardConfigurationPersistencePort
	 * @param creditCardDto
	 * @param companyCode
	 * @param idOperation
	 * @return
	 */
	public String cardReferenceValidationOnUpdate(
			CreditCardConfigurationPersistencePort creditCardConfigurationPersistencePort, CreditCardDto creditCardDto,
			String companyCode, String idOperation) {
		String validations = "";
		if (creditCardDto == null || creditCardDto.getId() == null) {
			return "INVALID OBJECT";
		}
		validations = requiredField(creditCardDto);
		validations += validCommissionApply(creditCardDto);
		validations = validations.isEmpty()
				? validExistsByCodeOrInstitutionAndIdNot(creditCardConfigurationPersistencePort, creditCardDto,
						validations, companyCode, idOperation)
				: validations;
		validations = validations.isEmpty()
				? validExistsByPeriodAndInstitutionAndIdNot(creditCardConfigurationPersistencePort, creditCardDto,
						validations, companyCode, idOperation)
				: validations;
		return validations;

	}

	/**
	 * Método que valida si la tarjeta aplica comisión bancaria, en caso de ser así,
	 * valida que el monto minimo no se negativo o cero
	 * 
	 * @param creditCardDto
	 * @return String
	 */
	private String validCommissionApply(CreditCardDto creditCardDto) {
		String validations = "";
		if (creditCardDto.getIsCommissionApply() != null) {
			if (creditCardDto.getIsCommissionApply()) {
				validations += creditCardDto.getMinimumAmount() == null ? "MINIMUM AMOUNT CAN NOT BE 0 OR NEGATIVE"
						: "";
				validations += creditCardDto.getMinimumAmount() != null
						? creditCardDto.getMinimumAmount().compareTo(BigDecimal.ZERO) <= 0 ? "MINIMUM AMOUNT CAN NOT BE 0 OR NEGATIVE" : ""
						: "";
			}
		}
		return validations;
	}

	/**
	 * Método que valida si existe un registro por código o institución bancaria
	 * 
	 * @param creditCardConfigurationPersistencePort
	 * @param creditCardDto
	 * @param validations
	 * @param companyCode
	 * @param idOperation
	 * @return
	 */
	private String validExistsByCodeOrInstitution(
			CreditCardConfigurationPersistencePort creditCardConfigurationPersistencePort, CreditCardDto creditCardDto,
			String validations, String companyCode, String idOperation) {
		ResponseModel responseSearch = creditCardConfigurationPersistencePort
				.findCreditCardReferenceByCodeOrInstitutionOrTypeAndCompanyCode(creditCardDto, companyCode,
						idOperation);
		CreditCardDto creditCardSearch = (CreditCardDto) responseSearch.getData();
		if (creditCardSearch == null) {
			return validations;
		} else {

			validations = creditCardSearch.getCode().equals(creditCardDto.getCode())
					? validations + " CODE ALREADY EXISTS "
					: validations;

			validations = creditCardSearch.getBankingInstitution().equals(creditCardDto.getBankingInstitution())
					? validations + " BANKING INSTITUTION ALREADY EXISTS BY CREDIT CARD TYPE "
							+ creditCardSearch.getType()
					: validations;
			return validations;
		}
	}

	/**
	 * Método que valida si existe un periodo de pago por institución bancaria
	 * 
	 * @param creditCardConfigurationPersistencePort
	 * @param creditCardDto
	 * @param companyCode
	 * @param idOperation
	 * @return
	 */
	private String validExistsByPeriodAndInstitution(
			CreditCardConfigurationPersistencePort creditCardConfigurationPersistencePort, CreditCardDto creditCardDto,
			String validations, String companyCode, String idOperation) {
		for (PaymentOptionDto paymentOption : creditCardDto.getPaymentOptionDetail()) {
			ResponseModel responseSearch = creditCardConfigurationPersistencePort
					.findPaymentOptionByPeriodAndInstitutionAndTypeCompanyCode(paymentOption.getPeriod(),
							creditCardDto.getBankingInstitution(), creditCardDto.getType(), companyCode, idOperation);
			PaymentOptionDto paymentOptionDto = (PaymentOptionDto) responseSearch.getData();
			if (paymentOptionDto != null) {
				validations = validations + " PERIOD ALREADY EXISTS BY BANKING INSTITUTION: "
						+ creditCardDto.getBankingInstitution();
			}
		}
		return validations;
	}

	/**
	 * Método que valida si existe un registro distinto a la entidad a actualizar
	 * 
	 * @param creditCardConfigurationPersistencePort
	 * @param creditCardDto
	 * @param companyCode
	 * @param idOperation
	 * @return
	 */
	private String validExistsByCodeOrInstitutionAndIdNot(
			CreditCardConfigurationPersistencePort creditCardConfigurationPersistencePort, CreditCardDto creditCardDto,
			String validations, String companyCode, String idOperation) {
		ResponseModel responseSearch = creditCardConfigurationPersistencePort
				.findCreditCardReferenceByCodeOrInstitutionOrTypeAndIdNotAndCompanyCode(creditCardDto, companyCode,
						idOperation);
		CreditCardDto creditCardSearch = (CreditCardDto) responseSearch.getData();
		if (creditCardSearch == null) {
			return validations;
		} else {

			validations = creditCardSearch.getCode().equals(creditCardDto.getCode())
					? validations + " CODE ALREADY EXISTS "
					: validations;

			validations = creditCardSearch.getBankingInstitution().equals(creditCardDto.getBankingInstitution())
					? validations + " BANKING INSTITUTION ALREADY EXISTS BY CREDIT CARD TYPE "
							+ creditCardSearch.getType()
					: validations;
			return validations;
		}
	}

	/**
	 * Método que valida si existe un periodo de pago por institución bancaria
	 * distintos a la entidad a actualizar
	 * 
	 * @param creditCardConfigurationPersistencePort
	 * @param creditCardDto
	 * @param companyCode
	 * @param idOperation
	 * @return
	 */
	private String validExistsByPeriodAndInstitutionAndIdNot(
			CreditCardConfigurationPersistencePort creditCardConfigurationPersistencePort, CreditCardDto creditCardDto,
			String validations, String companyCode, String idOperation) {
		for (PaymentOptionDto paymentOption : creditCardDto.getPaymentOptionDetail()) {
			ResponseModel responseSearch = creditCardConfigurationPersistencePort
					.findPaymentOptionByPeriodAndInstitutionAndTypeAndIdNotAndCompanyCode(paymentOption.getId(),
							paymentOption.getPeriod(), creditCardDto.getBankingInstitution(), creditCardDto.getType(),
							companyCode, idOperation);
			PaymentOptionDto paymentOptionDto = (PaymentOptionDto) responseSearch.getData();
			if (paymentOptionDto != null) {
				validations = validations + " PERIOD ALREADY EXISTS BY BANKING INSTITUTION: "
						+ creditCardDto.getBankingInstitution();
			}
		}
		return validations;
	}

	/**
	 * Método que valida que los campos ingresdos sean validos para la operación
	 * 
	 * @param creditCardDto
	 * @return
	 */
	public String requiredField(CreditCardDto creditCardDto) {
		String validRequiredFields = "";
		validRequiredFields = validBankingInstitution(creditCardDto.getBankingInstitution(), validRequiredFields);
		validRequiredFields = validCode(creditCardDto.getCode(), validRequiredFields);
		validRequiredFields = validType(creditCardDto.getType(), validRequiredFields);
		validRequiredFields = creditCardDto.getPaymentOptionDetail() != null
				? validRequiredPaymentOptionField(creditCardDto.getPaymentOptionDetail(), validRequiredFields)
				: validRequiredFields + "PAYMENT OPTION IS REQUIRED";
		return validRequiredFields;
	}

	private String validType(String type, String validRequiredFields) {
		validRequiredFields = type == null ? validRequiredFields + "TYPE IS REQUIRED" : validRequiredFields;
		validRequiredFields = type != null
				? type.isEmpty() ? validRequiredFields + "TYPE CAN NOT BE EMPTY, " : validRequiredFields
				: validRequiredFields;
		validRequiredFields = type != null ? CreditCardTypeEnum.isValid(type) ? validRequiredFields
				: validRequiredFields + " INVALID CREDIT TYPE, " : validRequiredFields;
		return validRequiredFields;
	}

	private String validBankingInstitution(String bankingInstitution, String validRequiredFields) {
		validRequiredFields = bankingInstitution == null ? "bankingInstitution IS REQUIRED, "
				: validRequiredFields + "";
		validRequiredFields = bankingInstitution != null
				? bankingInstitution.isEmpty() ? validRequiredFields + "bankingInstitution CAN NOT BE EMPTY, "
						: validRequiredFields
				: validRequiredFields;
		return validRequiredFields;
	}

	private String validCode(String code, String validRequiredFields) {
		validRequiredFields = code == null ? "code IS REQUIRED, " : validRequiredFields;
		validRequiredFields = code != null
				? code.isEmpty() ? validRequiredFields + "code CAN NOT BE EMPTY, " : validRequiredFields
				: validRequiredFields;
		return validRequiredFields;
	}

	private String validRequiredPaymentOptionField(List<PaymentOptionDto> paymentOptionList,
			String validRequiredFields) {
		for (PaymentOptionDto paymentOption : paymentOptionList) {
			validRequiredFields = validPeriod(paymentOption.getPeriod(), validRequiredFields);
			validRequiredFields = validCommission(paymentOption.getCommission(), validRequiredFields);
		}
		return validRequiredFields;
	}

	private String validPeriod(String period, String validRequiredFields) {
		validRequiredFields = period == null ? validRequiredFields + " period IS REQUIRED, " : validRequiredFields;
		validRequiredFields = period != null
				? period.isEmpty() ? validRequiredFields + "period CAN NOT BE EMPTY, " : validRequiredFields
				: validRequiredFields;
		validRequiredFields = period != null
				? !PaymentPeriodEnum.isValid(period) ? validRequiredFields + " INVALID PERIOD: " + period
						: validRequiredFields
				: validRequiredFields;

		return validRequiredFields;
	}

	private String validCommission(BigDecimal commission, String validRequiredFields) {
		validRequiredFields = commission != null && commission.compareTo(BigDecimal.ZERO) < 0 ? validRequiredFields + "COMMISSION CAN NOT BE NEGATIVE, "
				: validRequiredFields;
		return validRequiredFields;
	}

}
