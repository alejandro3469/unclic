package mx.com.endtoend.domain.cash.openingInstruments.business.validations;

import mx.com.endtoend.domain.cash.openingInstruments.dto.OpenPaymentInstrumentDto;
import mx.com.endtoend.domain.cash.openingInstruments.ports.spi.OpenigInstrumentPersistencePort;
import mx.com.endtoend.domain.commons.constants.OpeningIncomeEnum;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

public class GenericOpeningInstrumentValidation {

	/**
	 * Método para la validación de los datos operativos y reglas de negocio en la
	 * creación/actualización de instrumentos de pago
	 * 
	 * @param openigInstrumentPersistencePort puerto de comunicación con la capa de
	 *                                        infraestructura
	 * @param openPaymentInstrumentDto        datos operativos del instrumento de
	 * @param create                          indicador para determinar si las
	 *                                        validaciones son de creación o
	 *                                        actulización, true/false,
	 *                                        respectivamente
	 * @return cadena String, si pasa todas las validaciones retorna una cadena
	 *         vacia, en caso contrario indica las validaciones que no cumplen con
	 *         las condiciones necesarias
	 */
	public String validDataToSave(OpenigInstrumentPersistencePort openigInstrumentPersistencePort,
			OpenPaymentInstrumentDto openPaymentInstrumentDto, boolean create, String companyCode, String idOperation) {

		String validRequieredField = validRequieredFields(openPaymentInstrumentDto);
		String validExistRecord = create
				? validExistRecordToCreate(openigInstrumentPersistencePort, openPaymentInstrumentDto, companyCode,
						idOperation)
				: validExistRecordToUpdate(openigInstrumentPersistencePort, openPaymentInstrumentDto, companyCode,
						idOperation);

		return validRequieredField + validExistRecord;
	}

	/**
	 * Métod que que obtiene los datos operativos para el proceso de validacion en
	 * la creación de instrumentos de pago de las aperturas
	 * 
	 * @param openigInstrumentPersistencePort
	 * @param openPaymentInstrumentDto
	 * @param companyCode
	 * @param idOperation
	 * @return
	 */
	public String validExistRecordToCreate(OpenigInstrumentPersistencePort openigInstrumentPersistencePort,
			OpenPaymentInstrumentDto openPaymentInstrumentDto, String companyCode, String idOperation) {
		ResponseModel responseSearch = openigInstrumentPersistencePort
				.validExisteByCodeOrNameByCompanyCodeToCreate(openPaymentInstrumentDto, companyCode, idOperation);
		OpenPaymentInstrumentDto openPaymentInstrumentSearch = (OpenPaymentInstrumentDto) responseSearch.getData();
		return validFieldsToSaveData(openPaymentInstrumentDto, openPaymentInstrumentSearch);
	}

	/**
	 * Métod que que obtiene los datos operativos para el proceso de validacion en
	 * la actualización de instrumentos de pago de las aperturas
	 * 
	 * @param openigInstrumentPersistencePort
	 * @param openPaymentInstrumentDto
	 * @param companyCode
	 * @param idOperation
	 * @return
	 */
	public String validExistRecordToUpdate(OpenigInstrumentPersistencePort openigInstrumentPersistencePort,
			OpenPaymentInstrumentDto openPaymentInstrumentDto, String companyCode, String idOperation) {
		ResponseModel responseSearch = openigInstrumentPersistencePort
				.validExisteByCodeOrNameByCompanyCodeToUpdate(openPaymentInstrumentDto, companyCode, idOperation);
		OpenPaymentInstrumentDto openPaymentInstrumentSearch = (OpenPaymentInstrumentDto) responseSearch.getData();
		return validFieldsToSaveData(openPaymentInstrumentDto, openPaymentInstrumentSearch);
	}

	private String validFieldsToSaveData(OpenPaymentInstrumentDto openPaymentInstrumentDto,
			OpenPaymentInstrumentDto openPaymentInstrumentSearch) {
		if (openPaymentInstrumentSearch == null) {
			return "";
		} else {
			String validations = "";
			validations = openPaymentInstrumentSearch.getCode().trim().equals(openPaymentInstrumentDto.getCode().trim())
					? validations + "CODE ALREADY EXISTS - "
					: validations + "";
			validations = openPaymentInstrumentSearch.getName().trim().equals(openPaymentInstrumentDto.getName().trim())
					? validations + "NAME ALREADY EXISTS - "
					: validations + "";
			return validations;
		}
	}

	/**
	 * Método que valida que los datos operativos sean correctos
	 * 
	 * @param openPaymentInstrumentDto datos operativos
	 * 
	 * @return cadena String, si pasa todas las validaciones retorna una cadena
	 *         vacia, en caso contrario indica las validaciones que no cumplen con
	 *         las condiciones necesarias
	 */
	public String validRequieredFields(OpenPaymentInstrumentDto openPaymentInstrumentDto) {
		String validations = "";
		if (openPaymentInstrumentDto == null) {
			validations = validations + "INVALID OBJECT";
		} else {
			validations = validCode(openPaymentInstrumentDto, validations);
			validations = validIncomeType(openPaymentInstrumentDto, validations);
			validations = validName(openPaymentInstrumentDto, validations);
		}
		return validations;
	}

	private String validName(OpenPaymentInstrumentDto openPaymentInstrumentDto, String validations) {
		validations = openPaymentInstrumentDto.getName() == null ? validations + "NAME REQUIRED -" : validations + "";
		validations = openPaymentInstrumentDto.getName() != null
				? openPaymentInstrumentDto.getName().isEmpty() ? validations + " NAME CAN NOT BE EMPTY -"
						: validations + ""
				: validations + "";
		return validations;
	}

	private String validIncomeType(OpenPaymentInstrumentDto openPaymentInstrumentDto, String validations) {
		validations = openPaymentInstrumentDto.getIncomeType() == null ? validations + " INCOME TYPE REQUIRED -"
				: validations + "";
		validations = openPaymentInstrumentDto.getIncomeType() != null
				? openPaymentInstrumentDto.getIncomeType().isEmpty() ? validations + " INCOME TYPE CAN NOT BE EMPTY -"
						: validations + ""
				: validations + "";
		validations = openPaymentInstrumentDto.getIncomeType() != null
				? !OpeningIncomeEnum.isValid(openPaymentInstrumentDto.getIncomeType())
						? validations + "INVALID INCOME TYPE"
						: validations + ""
				: validations + "";
		return validations;
	}

	private String validCode(OpenPaymentInstrumentDto openPaymentInstrumentDto, String validations) {
		validations = openPaymentInstrumentDto.getCode() == null ? validations + " CODE REQUIRED -"
				: openPaymentInstrumentDto.getCode().isEmpty() ? validations + " CODE CAN NOT BE EMPTY -"
						: validations + "";
		return validations;
	}

}
