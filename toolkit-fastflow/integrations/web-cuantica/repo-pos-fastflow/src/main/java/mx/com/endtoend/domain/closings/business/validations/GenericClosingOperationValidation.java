package mx.com.endtoend.domain.closings.business.validations;

import java.math.BigDecimal;

import mx.com.endtoend.domain.cash.closingInstruments.dto.ClosePaymentInstrumentDto;
import mx.com.endtoend.domain.closings.dto.AccountingOperatingSummaryDto;
import mx.com.endtoend.domain.closings.dto.ClosingOperationDetailDto;
import mx.com.endtoend.domain.closings.dto.ClosingOperationDto;
import mx.com.endtoend.domain.closings.ports.ClosingOperationPersistencePort;
import mx.com.endtoend.domain.openings.dto.OpeningOperationDto;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

public class GenericClosingOperationValidation {

	/**
	 * Método para validar los datos operativos y reglas de negocio para el proceso
	 * de cierre de operacion
	 * 
	 * @param closingOperationPersistencePort
	 * @param closingOperationDto
	 * @param companyCode
	 * @param idOperation
	 * @return
	 */
	public String validOperativeDataToCreate(ClosingOperationPersistencePort closingOperationPersistencePort,
			ClosingOperationDto closingOperationDto, String companyCode, String idOperation) {

		String validRequiredFields = validRequiredFields(closingOperationDto);
		String validClosingOperationDetail = "";
		for (ClosingOperationDetailDto closingOperationDetailDto : closingOperationDto.getClosingOperationDetail()) {
			validClosingOperationDetail = validClosingOperationDetailRequiredFields(closingOperationPersistencePort,
					closingOperationDetailDto, companyCode, idOperation);
		}
		return validRequiredFields + validClosingOperationDetail;
	}

	/**
	 * Método que valida que los datos operativos del cabecero del cierre sean
	 * válidos para la operación
	 * 
	 * @param closingOperationDto datos operativos del cierre de operación
	 * 
	 * @return cadena String, si pasa todas las validaciones retorna una cadena
	 *         vacía, en caso contrariol, se indican las validaciones que no cumplen
	 *         con las condiciones necesarias para la operación
	 */
	public String validRequiredFields(ClosingOperationDto closingOperationDto) {
		String validations = "";
		if (closingOperationDto == null) {
			validations = validations + "INVALID OBJETC";
		} else {
			validations = validBranchCode(closingOperationDto, validations);
			validations = validEmployeeEmail(closingOperationDto, validations);
			validations = validOpeningOperationDetail(closingOperationDto, validations);
		}
		return validations;
	}

	private String validBranchCode(ClosingOperationDto closingOperationDto, String validations) {
		validations = closingOperationDto.getBranchCode() == null ? validations + " BRANCH CODE REQUIRED - "
				: validations + "";
		validations = closingOperationDto.getBranchCode() != null
				? closingOperationDto.getBranchCode().isEmpty() ? validations + "BRANCH CODE CAN NOT BE EMPTY"
						: validations + ""
				: validations + "";
		return validations;
	}

	private String validEmployeeEmail(ClosingOperationDto closingOperationDto, String validations) {
		validations = closingOperationDto.getEmployeeEmail() == null ? validations + " EMPLOYEE EMAIL REQUIRED - "
				: validations + "";
		validations = closingOperationDto.getEmployeeEmail() != null
				? closingOperationDto.getEmployeeEmail().isEmpty() ? validations + "EMPLOYEE EMAIL CAN NOT BE EMPTY"
						: validations + ""
				: validations + "";
		return validations;
	}

	private String validOpeningOperationDetail(ClosingOperationDto closingOperationDto, String validations) {
		validations = closingOperationDto.getClosingOperationDetail() == null
				? validations + "OPENING OPERATION DETAIL IS REQUIRED"
				: validations + "";

		validations = closingOperationDto.getClosingOperationDetail() != null
				? closingOperationDto.getClosingOperationDetail().size() == 0
						? validations + "OPENING OPERATIONS DETAIL IS EMPTY"
						: validations + ""
				: validations + "";
		return validations;
	}

	/**
	 * Método que valida los datos operativos del detalle del cierre de operación
	 * 
	 * @param closingOperationPersistencePort puerto de comunicación con la capa de
	 *                                        infraestructura
	 * @param closingOperationDetail          datos operativos del cierre de
	 *                                        operación
	 * @param companyCode                     códgo de compañia
	 * @param idOperation                     identificador de traza de operación
	 * 
	 * @return cadena String, si pasa todas las validaciones retorna una cadena
	 *         vacía, en caso contrariol, se indican las validaciones que no cumplen
	 *         con las condiciones necesarias para la operación
	 */
	public String validClosingOperationDetailRequiredFields(
			ClosingOperationPersistencePort closingOperationPersistencePort,
			ClosingOperationDetailDto closingOperationDetail, String companyCode, String idOperation) {
		String validations = "";
		if (closingOperationDetail == null) {
			validations = validations + "INVALID DETAIL OBJETC";
		} else {
			validations = validClosePaymentInstrument(closingOperationPersistencePort, closingOperationDetail,
					validations, companyCode, idOperation);
			validations = validAmount(closingOperationDetail, validations);
		}

		return validations;
	}

	private String validClosePaymentInstrument(ClosingOperationPersistencePort closingOperationPersistencePort,
			ClosingOperationDetailDto closingOperationDetailDto, String validations, String companyCode,
			String idOperation) {
		if (closingOperationDetailDto.getClosePaymentInstrument() != null) {
			if (closingOperationDetailDto.getClosePaymentInstrument().getId() == null)
				return validations + "CLOSE PAYMENT INSTRUMENT IS REQUIRED";
		} else {
			return validations + "CLOSE PAYMENT INSTRUMENT IS REQUIRED";
		}
		ResponseModel responseModel = closingOperationPersistencePort.findClosePaymentInstrumentByIdAndCompanyCode(
				closingOperationDetailDto.getClosePaymentInstrument().getId(), companyCode, idOperation);
		ClosePaymentInstrumentDto closePaymentInstrumentDto = (ClosePaymentInstrumentDto) responseModel.getData();
		validations = closePaymentInstrumentDto == null ? validations + " INVALID CLOSE PAYMENT INSTRUMENT"
				: validations + "";
		return validations;
	}

	private String validAmount(ClosingOperationDetailDto closingOperationDetailDto, String validations) {
		validations = closingOperationDetailDto.getAmount() == null ? validations + "AMOUNT REQUIRED BY INCOME: "
				+ closingOperationDetailDto.getClosePaymentInstrument().getName() : validations + "";
		validations = closingOperationDetailDto.getAmount() != null
				? closingOperationDetailDto.getAmount().compareTo(BigDecimal.ZERO) < 0 ? validations + "AMOUNT CAN NOT BE NEGATIVE "
						+ closingOperationDetailDto.getClosePaymentInstrument().getName() : validations + ""
				: validations + "";
		return validations;
	}

	/**
	 * Método que valida que la operación activa sea valida para el proceso de
	 * cierre
	 * 
	 * @param openingOperationDto datos operativos de la operación activa
	 * @return cadena String, si pasa todas las validaciones retorna una cadena
	 *         vacía, en caso contrariol, se indican las validaciones que no cumplen
	 *         con las condiciones necesarias para la operación
	 */
	public String validOpeningOperationActive(OpeningOperationDto openingOperationDto) {
		String validations = "";
		if (openingOperationDto == null) {
			validations = validations + "OPENING OPERATION IS REQUIRED";
		} else {
			validations = openingOperationDto.getIsActive() ? validations : validations + " OPENING OPERATION INACTIVE";
			validations = openingOperationDto.getCloseAttempts() > 3 ? validations + " LIMIT OF ATTEMPTS ALLOWED"
					: validations;
		}
		return validations;
	}

	/**
	 * Método que valida los datos teoricos menos los datos ingresados para el
	 * proceso de cierre contable de la operación
	 * 
	 * @param theoreticalRecords datos teoricos calculados por el sistema
	 * @param physicalRecords    datos fisicos ingresados por el usuario
	 * @param closeAttempts      número de intentos para el proceso de cierre
	 * @return cadena String, si pasa todas las validaciones retorna una cadena
	 *         vacía, en caso contrariol, se indican las validaciones que no cumplen
	 *         con las condiciones necesarias para la operación
	 */
	public String validIncomingVersusOutgoingAccountingOperation(AccountingOperatingSummaryDto theoreticalRecords,
			AccountingOperatingSummaryDto physicalRecords, int closeAttempts, Integer closeConfigAttempts) {

		BigDecimal cash = theoreticalRecords.getSummaryCash().subtract(physicalRecords.getSummaryCash());
		BigDecimal credit = theoreticalRecords.getSummaryCredit().subtract(physicalRecords.getSummaryCredit());
		BigDecimal creditNote = theoreticalRecords.getSummaryCreditNote().subtract(physicalRecords.getSummaryCreditNote());
		BigDecimal creditCard = theoreticalRecords.getSummaryCreditCard().subtract(physicalRecords.getSummaryCreditCard());
		BigDecimal check = theoreticalRecords.getSummaryCheck().subtract(physicalRecords.getSummaryCheck());
		BigDecimal transfer = theoreticalRecords.getSummaryTransfer().subtract(physicalRecords.getSummaryTransfer());

		BigDecimal totalSummaryClose = cash.add(credit).add(creditNote).add(creditCard).add(check).add(transfer);

		String validations = totalSummaryClose.compareTo(BigDecimal.ZERO) != 0
				? "THERE ARE DIFFERENCES IN THE OPERATION, YOU HAVE " + ((closeConfigAttempts + 1) - closeAttempts)
						+ " ATTEMPTS TO COMPLETE THE CLOSURE"
				: "";

		return validations;
	}

}
