package mx.com.endtoend.domain.cash.closingInstruments.business.validations;

import mx.com.endtoend.domain.cash.closingInstruments.dto.ClosePaymentInstrumentDto;
import mx.com.endtoend.domain.cash.closingInstruments.ports.spi.ClosingInstrumentPersistencePort;
import mx.com.endtoend.domain.commons.constants.ClosingIncomeEnum;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

public class GenericCloseInstrumentValidation {

	/**
	 * Método para la validación de los datos operativos y reglas de negocio en la
	 * creación/actualización de instrumentos de pago
	 * 
	 * @param closingInstrumentPersistencePort puerto de comunicación con la capa de
	 *                                         infraestructura
	 * @param closePaymentInstrumentDto        datos operativos del instrumento de
	 *                                         pago
	 * @param create                           indicador para determinar si las
	 *                                         validaciones son de creación o
	 *                                         actulización, true/false,
	 *                                         respectivamente
	 * @return cadena String, si pasa todas las validaciones retorna una cadena
	 *         vacia, en caso contrario indica las validaciones que no cumplen con
	 *         las condiciones necesarias
	 */
	public String validDataToSave(ClosingInstrumentPersistencePort closingInstrumentPersistencePort,
			ClosePaymentInstrumentDto closePaymentInstrumentDto, boolean create, String companyCode,
			String idOperation) {

		String validRequieredField = validRequieredFields(closePaymentInstrumentDto);
		String validExistRecord = create
				? validExistRecordToCreate(closingInstrumentPersistencePort, closePaymentInstrumentDto, companyCode,
						idOperation)
				: validExistRecordToUpdate(closingInstrumentPersistencePort, closePaymentInstrumentDto, companyCode,
						idOperation);

		return validRequieredField + validExistRecord;
	}

	/**
	 * Métod que que obtiene los datos operativos para el proceso de validacion en
	 * la creación de instrumentos de pago de cierres
	 * 
	 * @param closingInstrumentPersistencePort
	 * @param closePaymentInstrumentDto
	 * @param companyCode
	 * @param idOperation
	 * @return
	 */
	public String validExistRecordToCreate(ClosingInstrumentPersistencePort closingInstrumentPersistencePort,
			ClosePaymentInstrumentDto closePaymentInstrumentDto, String companyCode, String idOperation) {
		ResponseModel responseSearch = closingInstrumentPersistencePort
				.validExisteByCodeOrNameByCompanyCodeToCreate(closePaymentInstrumentDto, companyCode, idOperation);
		ClosePaymentInstrumentDto closePaymentInstrumentSearch = (ClosePaymentInstrumentDto) responseSearch.getData();
		return validFieldsToSaveData(closePaymentInstrumentDto, closePaymentInstrumentSearch);
	}

	/**
	 * Métod que que obtiene los datos operativos para el proceso de validacion en
	 * la actualización de instrumentos de pago de cierres
	 * 
	 * @param closingInstrumentPersistencePort
	 * @param closePaymentInstrumentDto
	 * @param companyCode
	 * @param idOperation
	 * @return
	 */
	public String validExistRecordToUpdate(ClosingInstrumentPersistencePort closingInstrumentPersistencePort,
			ClosePaymentInstrumentDto closePaymentInstrumentDto, String companyCode, String idOperation) {
		ResponseModel responseSearch = closingInstrumentPersistencePort
				.validExisteByCodeOrNameByCompanyCodeToUpdate(closePaymentInstrumentDto, companyCode, idOperation);
		ClosePaymentInstrumentDto closePaymentInstrumentSearch = (ClosePaymentInstrumentDto) responseSearch.getData();
		return validFieldsToSaveData(closePaymentInstrumentDto, closePaymentInstrumentSearch);
	}

	private String validFieldsToSaveData(ClosePaymentInstrumentDto closePaymentInstrumentDto,
			ClosePaymentInstrumentDto closePaymentInstrumentSearch) {
		if (closePaymentInstrumentSearch == null) {
			return "";
		} else {
			String validations = "";
			validations = closePaymentInstrumentSearch.getCode().trim().equals(closePaymentInstrumentDto.getCode().trim())
					? validations + "CODE ALREADY EXISTS - "
					: validations + "";
			validations = closePaymentInstrumentSearch.getName().trim().equals(closePaymentInstrumentDto.getName().trim())
					? validations + "NAME ALREADY EXISTS - "
					: validations + "";
			return validations;
		}
	}

	/**
	 * Método que valida que los datos operativos sean correctos
	 * 
	 * @param closePaymentInstrumentDto datos operativos
	 * 
	 * @return cadena String, si pasa todas las validaciones retorna una cadena
	 *         vacia, en caso contrario indica las validaciones que no cumplen con
	 *         las condiciones necesarias
	 */
	public String validRequieredFields(ClosePaymentInstrumentDto closePaymentInstrumentDto) {
		String validations = "";
		if (closePaymentInstrumentDto == null) {
			validations = validations + "INVALID OBJECT";
		} else {
			validations = validCode(closePaymentInstrumentDto, validations);
			validations = validIncomeType(closePaymentInstrumentDto, validations);
			validations = validName(closePaymentInstrumentDto, validations);
		}
		return validations;
	}

	private String validName(ClosePaymentInstrumentDto closePaymentInstrumentDto, String validations) {
		validations = closePaymentInstrumentDto.getName() == null ? validations + "NAME REQUIRED -" : validations + "";
		validations = closePaymentInstrumentDto.getName() != null
				? closePaymentInstrumentDto.getName().isEmpty() ? validations + " NAME CAN NOT BE EMPTY -"
						: validations + ""
				: validations + "";
		return validations;
	}

	private String validIncomeType(ClosePaymentInstrumentDto closePaymentInstrumentDto, String validations) {
		validations = closePaymentInstrumentDto.getIncomeType() == null ? validations + " INCOME TYPE REQUIRED -"
				: validations + "";
		validations = closePaymentInstrumentDto.getIncomeType() != null
				? closePaymentInstrumentDto.getIncomeType().isEmpty() ? validations + " INCOME TYPE CAN NOT BE EMPTY -"
						: validations + ""
				: validations + "";
		validations = closePaymentInstrumentDto.getIncomeType() != null
				? !ClosingIncomeEnum.isValid(closePaymentInstrumentDto.getIncomeType())
						? validations + "INVALID INCOME TYPE"
						: validations + ""
				: validations + "";
		return validations;
	}

	private String validCode(ClosePaymentInstrumentDto closePaymentInstrumentDto, String validations) {
		validations = closePaymentInstrumentDto.getCode() == null ? validations + " CODE REQUIRED -"
				: closePaymentInstrumentDto.getCode().isEmpty() ? validations + " CODE CAN NOT BE EMPTY -"
						: validations + "";
		return validations;
	}
}
