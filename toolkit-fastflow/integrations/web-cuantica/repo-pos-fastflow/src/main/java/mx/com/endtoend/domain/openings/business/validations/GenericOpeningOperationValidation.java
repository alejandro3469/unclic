package mx.com.endtoend.domain.openings.business.validations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

import mx.com.endtoend.domain.cash.openingInstruments.dto.OpenPaymentInstrumentDto;
import mx.com.endtoend.domain.openings.dto.OpeningOperationDetailDto;
import mx.com.endtoend.domain.openings.dto.OpeningOperationDto;
import mx.com.endtoend.domain.openings.ports.OpeningOperationPersistencePort;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

public class GenericOpeningOperationValidation {

	/**
	 * Método para validar los datos operativos y reglas de negocio para el proceso
	 * de creación de apertura de operación del método uno
	 * 
	 * @param openingOperationPersistencePort puerto de comnunicación con la capa de
	 *                                        infraestructura
	 * @param openingOperationDto             datos operativos de la apertura de
	 *                                        operación
	 * @param companyCode                     código de compañía
	 * @param idOperation                     identificador de traza de operación
	 * 
	 * @return cadena String, si pasa todas las validaciones retorna una cadena
	 *         vacía, en caso contrariol, se indican las validaciones que no cumplen
	 *         con las condiciones necesarias para la operación
	 */
	public String validOperativeDataToCreateOpeningOperation(
			OpeningOperationPersistencePort openingOperationPersistencePort, OpeningOperationDto openingOperationDto,
			String companyCode, String idOperation) {

		String validRequiredFields = validRequiredField(openingOperationDto);

		String validOpeningOperationDetail = "";
		for (OpeningOperationDetailDto openingOperationDetailDto : openingOperationDto.getOpeningOperationDetail()) {
			validOpeningOperationDetail = validOpeningOperationDetail + validOpeningOperationDetailRequiredField(
					openingOperationPersistencePort, openingOperationDetailDto, companyCode, idOperation);
		}

		String validNoOpeningOperationActive = validNoOpeningOperationActive(openingOperationPersistencePort,
				openingOperationDto.getEmployeeEmail(), openingOperationDto.getBranchCode(), companyCode, idOperation);

		return validRequiredFields + validOpeningOperationDetail + validNoOpeningOperationActive;
	}

	/**
	 * Métodoq que valida los datos operativos del cabecero de la apertura
	 * 
	 * @param openingOperationDto datos operativos de la apertura de operación
	 * @return cadena String, si pasa todas las validaciones retorna una cadena
	 *         vacía, en caso contrariol, se indican las validaciones que no cumplen
	 *         con las condiciones necesarias para la operación
	 */
	public String validRequiredField(OpeningOperationDto openingOperationDto) {
		String validations = "";
		if (openingOperationDto == null) {
			validations = validations + "INVALID OBJETC";
		} else {
			validations = validBranchCode(openingOperationDto, validations);
			validations = validEmployeeEmail(openingOperationDto, validations);
			validations = validOpeningOperationDetail(openingOperationDto, validations);
		}
		return validations;
	}

	private String validBranchCode(OpeningOperationDto openingOperationDto, String validations) {
		validations = openingOperationDto.getBranchCode() == null ? validations + " BRANCH CODE REQUIRED - "
				: validations + "";
		validations = openingOperationDto.getBranchCode() != null
				? openingOperationDto.getBranchCode().isEmpty() ? validations + "BRANCH CODE CAN NOT BE EMPTY"
						: validations + ""
				: validations + "";
		return validations;
	}

	private String validEmployeeEmail(OpeningOperationDto openingOperationDto, String validations) {
		validations = openingOperationDto.getEmployeeEmail() == null ? validations + " EMPLOYEE EMAIL REQUIRED - "
				: validations + "";
		validations = openingOperationDto.getEmployeeEmail() != null
				? openingOperationDto.getEmployeeEmail().isEmpty() ? validations + "EMPLOYEE EMAIL CAN NOT BE EMPTY"
						: validations + ""
				: validations + "";
		return validations;
	}

	private String validOpeningOperationDetail(OpeningOperationDto openingOperationDto, String validations) {
		validations = openingOperationDto.getOpeningOperationDetail() == null
				? validations + "OPENING OPERATION DETAIL IS REQUIRED"
				: validations + "";

		validations = openingOperationDto.getOpeningOperationDetail() != null
				? openingOperationDto.getOpeningOperationDetail().size() == 0
						? validations + "OPENING OPERATIONS DETAIL IS EMPTY"
						: validations + ""
				: validations + "";
		return validations;
	}

	/**
	 * Métodoq que valida los datos operativos del detalle de apertura de operación
	 * 
	 * @param openingOperationDetailDto       datos operativos del detalle de la
	 *                                        apertura de operación
	 * @param openingOperationPersistencePort puerto de comunicación con la capa de
	 *                                        persistencia de datos
	 * @return cadena String, si pasa todas las validaciones retorna una cadena
	 *         vacía, en caso contrariol, se indican las validaciones que no cumplen
	 *         con las condiciones necesarias para la operación
	 */
	public String validOpeningOperationDetailRequiredField(
			OpeningOperationPersistencePort openingOperationPersistencePort,
			OpeningOperationDetailDto openingOperationDetailDto, String companyCode, String idOperation) {
		String validations = "";
		if (openingOperationDetailDto == null) {
			validations = validations + "INVALID DETAIL OBJETC";
		} else {
			validations = validOpenPaymentInstrument(openingOperationPersistencePort, openingOperationDetailDto,
					validations, companyCode, idOperation);
			validations = validAmount(openingOperationDetailDto, validations);
		}
		return validations;
	}

	private String validOpenPaymentInstrument(OpeningOperationPersistencePort openingOperationPersistencePort,
			OpeningOperationDetailDto openingOperationDetailDto, String validations, String companyCode,
			String idOperation) {
		if (openingOperationDetailDto.getOpenPaymentInstrument() != null) {
			if (openingOperationDetailDto.getOpenPaymentInstrument().getId() == null)
				return validations + "OPEN PAYMENT INSTRUMENT IS REQUIRED";
		} else {
			return validations + "OPEN PAYMENT INSTRUMENT IS REQUIRED";
		}
		ResponseModel responseModel = openingOperationPersistencePort.findOpenPaymentInstrumentByIdAndCompanyCode(
				openingOperationDetailDto.getOpenPaymentInstrument().getId(), companyCode, idOperation);
		OpenPaymentInstrumentDto openPaymentInstrumentDto = (OpenPaymentInstrumentDto) responseModel.getData();
		validations = openPaymentInstrumentDto == null ? validations + " INVALID OPEN PAYMENT INSTRUMENT"
				: validations + "";
		return validations;
	}

	private String validAmount(OpeningOperationDetailDto openingOperationDetailDto, String validations) {
		validations = openingOperationDetailDto.getAmount() == null ? validations + "AMOUNT REQUIRED BY INCOME: "
				+ openingOperationDetailDto.getOpenPaymentInstrument().getName() : validations + "";
		validations = openingOperationDetailDto.getAmount() != null
				? openingOperationDetailDto.getAmount().compareTo(BigDecimal.ZERO) < 0 ? validations + "AMOUNT CAN NOT BE NEGATIVE BY INCOME: "
						+ openingOperationDetailDto.getOpenPaymentInstrument().getName() : validations + ""
				: validations + "";
		return validations;
	}

	/**
	 * Método que valida que no exista una apertura activa por empleado y sucursal
	 * 
	 * @param openingOperationPersistencePort puerto de comunicación con la capa de
	 *                                        persistencia de datos
	 * @param employeeEmail                   correo de empleado registrado en el
	 *                                        sistema
	 * @param branchCode                      código de sucursal del empleado
	 * @param companyCode                     código de compañía
	 * @param idOperation                     identificador de traza de operación
	 * @return cadena String, si pasa todas las validaciones retorna una cadena
	 *         vacía, en caso contrariol, se indican las validaciones que no cumplen
	 *         con las condiciones necesarias para la operación
	 */
	public String validNoOpeningOperationActive(OpeningOperationPersistencePort openingOperationPersistencePort,
			String employeeEmail, String branchCode, String companyCode, String idOperation) {
		String validations = "";
		ResponseModel responseModel = openingOperationPersistencePort
				.findOpeningOperationActiveByEmployeeEmailAndCompanyCode(employeeEmail, companyCode, idOperation);
		OpeningOperationDto openingOperationActive = (OpeningOperationDto) responseModel.getData();
		validations = openingOperationActive != null
				? "EXISTE ONE OPENING BY USER: " + employeeEmail + " IN BRANCH: " + branchCode + " WITH DATE: "
						+ openingOperationActive.getCreationDate()
				: validations;
		return validations;
	}

	@SuppressWarnings("deprecation")
	public boolean isValidDate(Date creationDate) {
		Date date = new Date();
		LocalDate localDate = LocalDate.of(date.getYear(), date.getMonth() + 1, date.getDate());
		LocalDate openingDate = LocalDate.of(creationDate.getYear(), creationDate.getMonth() + 1,
				creationDate.getDate());
		return openingDate.isEqual(localDate) ? true : false;
	}

}
