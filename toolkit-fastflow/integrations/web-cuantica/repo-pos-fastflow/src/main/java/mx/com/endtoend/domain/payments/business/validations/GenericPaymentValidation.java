package mx.com.endtoend.domain.payments.business.validations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.com.endtoend.domain.commons.constants.StatusOrder;
import mx.com.endtoend.domain.creditNote.dto.CreditNoteHeaderDto;
import mx.com.endtoend.domain.creditNote.ports.CreditNotePersistencePort;
import mx.com.endtoend.domain.openings.dto.OpeningOperationDto;
import mx.com.endtoend.domain.payments.dto.GenericSearchPaymentDto;
import mx.com.endtoend.domain.userConfigurations.dto.EmployeeDto;
import mx.com.endtoend.smart.bussiness.constants.PaymentPeriodEnum;
import mx.com.endtoend.smart.bussiness.model.catalogue.dto.StatusDto;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;
import mx.com.endtoend.smart.bussiness.model.orders.dto.OrderDto;
import mx.com.endtoend.smart.bussiness.model.payments.CheckPaymentDto;
import mx.com.endtoend.smart.bussiness.model.payments.CreditCardPaymentDto;
import mx.com.endtoend.smart.bussiness.model.payments.CreditNotePaymentDto;
import mx.com.endtoend.smart.bussiness.model.payments.CreditPaymentDto;
import mx.com.endtoend.smart.bussiness.model.payments.PaymentCashDto;
import mx.com.endtoend.smart.bussiness.model.payments.PaymentDto;
import mx.com.endtoend.smart.bussiness.model.payments.TransferPaymentDto;

/**
 * Clase encargada para la validación de reglas de negocio y datos operativos de
 * los cobros
 * 
 * @author ddcasas
 *
 */
public class GenericPaymentValidation {

	private static final String BAD_USER_CONFIGURATION = "-USER CONFIGURATION NOT FOUND BY CURRENT BRANCH";

	private final Logger LOG = LoggerFactory.getLogger(GenericPaymentValidation.class);

	/**
	 * Método que se encarga de validar que el estado de la orden sea valido para
	 * ser cobrada
	 * 
	 * @param orderDto          datos operacionales de la orden de venta
	 * @param employeBranchCode código de sucursal del empleado logeado
	 * @param idOperation       identificador de traza de operación
	 * 
	 * @return cadena de tipo String, si se retorna vacia la orden paso todas las
	 *         validaciones, de lo contrario se indica las validaciones que no se
	 *         cumplen
	 */
	public String validOrderStatusMathodOne(OrderDto orderDto, String employeBranchCode, String idOperation) {
		if (orderDto == null)
			return "ORDER NOT FOUND";
		String validationStatus = validStatusOrder(orderDto, idOperation);
		String validationIsActive = orderDto.getIsUpdated() != true ? "" : "- ORDER ACTIVE -";
		String validationBranch = isValidBranch(orderDto, employeBranchCode, idOperation) != false ? ""
				: "- INVALID ORIGIN BRANCH -";
		return validationStatus + validationBranch + validationIsActive;

	}

	public String validOperativeDataToCancelPaymentProcess(EmployeeDto employeeDto, OrderDto orderDto,
			String idOperation) {
		String validations = "";
		String validationBranch = isValidBranch(orderDto, employeeDto.getBranchCode(), idOperation) != false ? ""
				: "- INVALID ORIGIN BRANCH -";
		return validations + validationBranch;
	}

	/**
	 * Método que valida los datos operativos para el proceso de cobro
	 * 
	 * @param employeeDto      datos operativos del empleado
	 * @param paymentDto       datos operativos del pago de la orden
	 * @param statusDto        estado de la orden a cobrar
	 * @param openingOperation datos operativos de la apertura de operación del
	 *                         empleado
	 * @param idOperation      identificador de traza de operación
	 * @return cadena de tipo String, si se retorna vacia la orden paso todas las
	 *         validaciones, de lo contrario se indica las validaciones que no se
	 *         cumplen
	 */
	public String validOperativeDataToPaymentProcessMethodOne(EmployeeDto employeeDto, PaymentDto paymentDto,
			CreditNotePersistencePort creditNotePersistencePort, StatusDto statusDto,
			OpeningOperationDto openingOperation, String companyCode, String idOperation) {

		String validations = "";
		validations = validOrderStatus(statusDto, validations);
		validations = validRequiredPaymentFields(paymentDto, validations);
		validations = validOpeningOperation(openingOperation, validations);

		if (paymentDto.getPaymentCashList() != null)
			validations = validPaymentCashList(paymentDto.getPaymentCashList(), validations);

		if (paymentDto.getCreditCardPaymentList() != null)
			validations = validPaymentCreditCard(paymentDto.getCreditCardPaymentList(), validations);

		if (paymentDto.getTransferPaymentList() != null)
			validations = validPaymentTransfer(paymentDto.getTransferPaymentList(), validations);

		if (paymentDto.getCreditNotePaymentList() != null)
			validations = validPaymentCreditNote(paymentDto.getCreditNotePaymentList(), creditNotePersistencePort,
					companyCode, idOperation, validations);

		if (paymentDto.getCheckPaymentList() != null)
			validations = validPaymentCheck(paymentDto.getCheckPaymentList(), validations);

		if (paymentDto.getCreditPaymentList() != null)
			validations = validPaymentCredit(paymentDto.getCreditPaymentList(), validations);

		return validations;
	}

	/**
	 * Método que valida los datos operativos de la apertura con la que se
	 * relacionarán los cobros del sistema
	 * 
	 * @param openingOperation datos operativos de la apertura de operación
	 * @param validations      conjunto de validaciones a sumar
	 * @return cadena de tipo String, si se retorna vacia la orden paso todas las
	 *         validaciones, de lo contrario se indica las validaciones que no se
	 *         cumplen
	 */
	public String validOpeningOperation(OpeningOperationDto openingOperation, String validations) {
		validations = openingOperation == null ? validations + "OPENING OPERATION DO NOT EXSIST" : validations;
		validations = openingOperation != null ? isValidDate(openingOperation.getCreationDate()) ? validations
				: validations + "OPENING OPERATION NOT CORRESPOND TO THE CURRENT DAY - OPERATION ACTIVE FROM "
						+ openingOperation.getCreationDate()
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

	/**
	 * Método que valida el estado de la orden para el proceso de cobro
	 * 
	 * @param statusDto estado de la orden
	 * 
	 * @return cadena de tipo String, si se retorna vacia la orden paso las
	 *         validaciones, de lo contrario se indica las validaciones que no se
	 *         cumplen
	 */
	public String validOrderStatus(StatusDto statusDto, String validations) {
		String valid = !statusDto.getCode().equals(StatusOrder.CREATE_SALE_ORDER.getValue())
				? "INVALID STATUS ORDER: " + statusDto.getDescription().toUpperCase()
				: "";
		return validations + valid;
	}

	/**
	 * Método que valida los campos requeridos en la lista de cobros en efectivo
	 * 
	 * @param paymentCashList lista con los datos operartivos de los cobros en
	 *                        efectivo recibidos
	 * @param validations     cadena de validaciones generales
	 * @return cadena de tipo String, si se retorna vacia la orden paso todas las
	 *         validaciones, de lo contrario se indica las validaciones que no se
	 *         cumplen
	 */
	public String validPaymentCashList(List<PaymentCashDto> paymentCashList, String validations) {
		String validPaymentCashList = "";
		if (!paymentCashList.isEmpty()) {
			for (PaymentCashDto paymentCashDto : paymentCashList) {
				String validFields = "";
				validFields = paymentCashDto.getCurrency().isEmpty() ? validFields + " - CURRENCY IS REQUIRED - "
						: validFields + "";
				validFields = paymentCashDto.getAmountApplied().compareTo(BigDecimal.ZERO) <= 0
						? validFields + " - INVAID AMOUNT APPLIED - "
						: validFields + "";
				validFields = paymentCashDto.getAmountReceived().compareTo(BigDecimal.ZERO) <= 0
						? validFields + " - INVAID AMOUNT RECEIVED - "
						: validFields + "";
				validPaymentCashList = !validFields.isEmpty()
						? validPaymentCashList + validFields + "BY LINE " + paymentCashDto.getLine() + " "
						: validPaymentCashList;
			}
		}
		return validations + validPaymentCashList;
	}

	public String validPaymentCredit(List<CreditPaymentDto> creditPaymentList, String validations) {
		String validCreditPaymentList = "";
		if (!creditPaymentList.isEmpty()) {
			for (CreditPaymentDto creditPaymentDto : creditPaymentList) {
				String validFields = "";

				validFields += creditPaymentDto.getCurrency() == null || creditPaymentDto.getCurrency().isEmpty()
						? validFields + " - CURRENCY IS REQUIRED - "
						: "";

				validFields += creditPaymentDto.getExchangeRate() == null ? " - EXCHANGE RATE IS REQUIRED" : "";

				validFields += creditPaymentDto.getReferenceId() == null || creditPaymentDto.getReferenceId().isEmpty()
						? " - CREDIT REFERENCE IS REQUIRED"
						: "";

				validFields += creditPaymentDto.getAmountApplied() == null ? " - AMOUNT APPLIED IS REQUIRED " : "";
				validFields += creditPaymentDto.getAmountApplied() != null
						? creditPaymentDto.getAmountApplied().compareTo(BigDecimal.ZERO) <= 0 ? " - AMOUNT APPLIED CAN NOT BE ZERO" : ""
						: "";

				validCreditPaymentList = !validFields.isEmpty()
						? validCreditPaymentList + validFields + "BY LINE " + creditPaymentDto.getLine() + " "
						: validCreditPaymentList;
			}
		}
		return validations + validCreditPaymentList;

	}

	/**
	 * Método que valida los datos operativos para los cobros con cheques
	 * 
	 * @param checkPaymentList
	 * @param validations
	 * @return
	 */
	public String validPaymentCheck(List<CheckPaymentDto> checkPaymentList, String validations) {
		String validFields = "";
		if (!checkPaymentList.isEmpty()) {
			for (CheckPaymentDto checkPaymentDto : checkPaymentList) {

				validFields = (checkPaymentDto.getCurrency() == null || checkPaymentDto.getCurrency().isEmpty())
						? validFields + "- CURRENCY IS REQUIRED - "
						: validFields;

				validFields = checkPaymentDto.getExchangeRate() == null ? validFields + "- EXCHANGE RATE IS REQUIRED - "
						: validFields;
				validFields = (checkPaymentDto.getBankingInstitution() == null
						|| checkPaymentDto.getBankingInstitution().isEmpty())
								? validFields + "- BANKING INSTITUTION IS REQUIRED - "
								: validFields;
				validFields = (checkPaymentDto.getCheckNumber() == null || checkPaymentDto.getCheckNumber().isEmpty())
						? validFields + "- CHECK NUMBER IS REQUIRED - "
						: validFields;

				validFields = checkPaymentDto.getAmountApplied().compareTo(BigDecimal.ZERO) <= 0
						? validFields + " - INVAID AMOUNT APPLIED -"
						: validFields;

				validations = !validFields.isEmpty() ? validations + "BY LINE " + checkPaymentDto.getLine()
						: validations;

			}
		}
		return validations;
	}

	/**
	 * Método que valida los campos requeridos en la lista de cobros con notas de
	 * crédito
	 * 
	 * @param creditNotePaymentList lista con los datos operativos de las notas de
	 *                              crédito a aplicar
	 * @param validations           cadena de validaciones generales
	 * 
	 * @return cadena de tipo String, si se retorna vacia la orden paso todas las
	 *         validaciones, de lo contrario se indica las validaciones que no se
	 *         cumplen
	 */
	private String validPaymentCreditNote(List<CreditNotePaymentDto> creditNotePaymentList,
			CreditNotePersistencePort creditNotePersistencePort, String companyCode, String idOperation,
			String validations) {
		String validCreditNoteList = "";
		if (!creditNotePaymentList.isEmpty()) {
			for (CreditNotePaymentDto creditNotePaymentDto : creditNotePaymentList) {
				validCreditNoteList = validRequieredFields(validCreditNoteList, creditNotePaymentDto);
			}

			if (validCreditNoteList.isEmpty()) {
				for (CreditNotePaymentDto creditNotePaymentDto : creditNotePaymentList) {
					ResponseModel responseSearchCN = creditNotePersistencePort
							.searchCreditNoteHeaderByFolioAndCodeAndCompanyCode(creditNotePaymentDto.getFolio(),
									creditNotePaymentDto.getCreditNoteCode(), companyCode, idOperation);
					CreditNoteHeaderDto creditNoteHeaderDto = (CreditNoteHeaderDto) responseSearchCN.getData();
					validCreditNoteList = creditNoteHeaderDto == null
							? validCreditNoteList + "INVALID FOLIO: " + creditNotePaymentDto.getFolio().longValue()
							: validCreditNoteList;
					validCreditNoteList = creditNoteHeaderDto != null
							? (creditNoteHeaderDto.getPendingAmount().compareTo(creditNotePaymentDto.getAmountApplied()) < 0
									? validCreditNoteList + "INSUFFICIENT AMOUNT AVAILABLE "
									: validCreditNoteList + "")
							: validCreditNoteList;
				}
			}
		}
		return validations + validCreditNoteList;
	}

	private String validRequieredFields(String validCreditNoteList, CreditNotePaymentDto creditNotePaymentDto) {
		String validFields = "";
		validFields = creditNotePaymentDto.getFolio() == null ? validFields + " - FOLIO IS REQUIRED -" : validFields;
		validFields = (creditNotePaymentDto.getCreditNoteCode() == null
				|| creditNotePaymentDto.getCreditNoteCode().isEmpty())
						? validFields + " - CREDIT NOTE CODE IS REQUIRED -"
						: validFields;
		validFields = (creditNotePaymentDto.getCurrency() == null || creditNotePaymentDto.getCurrency().isEmpty())
				? validFields + " - CURRENCY IS REQUIRED -"
				: validFields;

		validFields = creditNotePaymentDto.getAmountApplied().compareTo(BigDecimal.ZERO) <= 0
				? validFields + " - INVAID AMOUNT APPLIED -"
				: validFields;

		validCreditNoteList = !validFields.isEmpty()
				? validCreditNoteList + validFields + "BY LINE " + creditNotePaymentDto.getLine() + " "
				: validCreditNoteList;
		return validCreditNoteList;
	}

	/**
	 * Método que valida los campos requeridos en la lista de cobro por
	 * transferencia
	 * 
	 * @param transferPaymentList lista con los datos operativos del registro de
	 *                            cobro por transferencia
	 * @param validations         cadena de validaciones generales
	 * @return cadena de tipo String, si se retorna vacia la orden paso todas las
	 *         validaciónes , de lo contrario se indica las validaciones que no se
	 *         cumplen
	 */
	public String validPaymentTransfer(List<TransferPaymentDto> transferPaymentList, String validations) {
		if (!transferPaymentList.isEmpty()) {
			for (TransferPaymentDto transferPaymentDto : transferPaymentList) {

				validations = (transferPaymentDto.getCurrency() == null || transferPaymentDto.getCurrency().isEmpty())
						? validations + "- CURRENCY IS REQUIRED - "
						: validations;

				validations = (transferPaymentDto.getAmountApplied() == null
						|| transferPaymentDto.getAmountApplied().compareTo(BigDecimal.ZERO) <= 0)
								? validations + " - INVAID AMOUNT APPLIED - "
								: validations;

				validations = (transferPaymentDto.getBankingInstitution() == null
						|| transferPaymentDto.getBankingInstitution().isEmpty())
								? validations + " - INVALID BANKING-INSTITUTION - "
								: validations;

				validations = (transferPaymentDto.getReferenceNumber() == null
						|| transferPaymentDto.getReferenceNumber().isEmpty())
								? validations + " - REFERENCE NUMBER IS REQUIRED - "
								: validations;

				validations = (transferPaymentDto.getTrackingNumber() == null
						|| transferPaymentDto.getTrackingNumber().isEmpty())
								? validations + " - TRACKING NUMBER IS REQUIRED - "
								: validations;
			}
		}
		return validations;
	}

	/**
	 * Método que valida los campos requeridos en la lista de cobro con tarjetas
	 * 
	 * @param creditCardPaymentList lista con los datos operativos del registro de
	 *                              cobros por tarjetas
	 * @param validations           cadena de validaciones generales
	 * @return cadena de tipo String, si se retorna vacia la orden paso todas las
	 *         validaciones, de lo contrario se indica las validaciones que no se
	 *         cumplen
	 */
	public String validPaymentCreditCard(List<CreditCardPaymentDto> creditCardPaymentList, String validations) {
		if (!creditCardPaymentList.isEmpty()) {
			for (CreditCardPaymentDto creditCardPaymentDto : creditCardPaymentList) {

				validations = (creditCardPaymentDto.getCurrency() == null
						|| creditCardPaymentDto.getCurrency().isEmpty()) ? validations + "- CURRENCY IS REQUIRED - "
								: validations;

				validations = (creditCardPaymentDto.getAmountApplied() == null
						|| creditCardPaymentDto.getAmountApplied().compareTo(BigDecimal.ZERO) <= 0)
								? validations + " - INVAID AMOUNT APPLIED - "
								: validations;

				validations = (creditCardPaymentDto.getBankingInstitution() == null
						|| creditCardPaymentDto.getBankingInstitution().isEmpty())
								? validations + " - INVALID BANKING-INSTITUTION - "
								: validations;

				validations = (creditCardPaymentDto.getPeriod() == null || creditCardPaymentDto.getPeriod().isEmpty()
						|| !PaymentPeriodEnum.isValid(creditCardPaymentDto.getPeriod()))
								? validations + " - INVALID PERIOD - "
								: validations;

				validations = creditCardPaymentDto.getCommissionApplied() == null
						? validations + " INVALID COMMISSION APPLIED - "
						: validations;

				validations = (creditCardPaymentDto.getValidityDate() == null
						|| creditCardPaymentDto.getValidityDate().isEmpty())
								? validations + " - INVALID VALIDITY DATE - "
								: validations;

				validations = (creditCardPaymentDto.getCardNumber() == null
						|| creditCardPaymentDto.getCardNumber().isEmpty())
								? validations + " - INVALID CREDIT CARD NUMBER - "
								: validations;

			}
		}

		return validations;
	}

	/**
	 * Método para validar que se reciban los datos necesarios para el proceso del
	 * cobro
	 * 
	 * @param paymentDto datos operacionales del cobro
	 * 
	 * @return cadena de tipo String, si se retorna vacia la orden paso todas las
	 *         validaciones, de lo contrario se indica las validaciones que no se
	 *         cumplen
	 */
	public String validRequiredPaymentFields(PaymentDto paymentDto, String validations) {
		String valid = "";
		valid = validExistsInstrumentPayment(paymentDto, valid);
		valid = paymentDto.getEmployeEmail() == null || paymentDto.getEmployeEmail().isEmpty()
				? valid + " - EMPLOYEE EMAIL IS REQUIRED - "
				: valid + "";
		valid = paymentDto.getOrderCode() == null || paymentDto.getOrderCode().isEmpty()
				? valid + " - ORDER TYPE IS REQUIRED - "
				: valid + "";
		valid = paymentDto.getOrderNumber() == null ? valid + " - ORDER NUMBER IS REQUIRED - " : valid + "";
		return validations + valid;
	}

	/**
	 * Método para validar que existe al menos una línea con datos para el proceso
	 * de cobro. Por cada instrumento de cobro se deberá de incorporar en la
	 * validación
	 * 
	 * @param paymentDto datos operativos del cobro
	 * @param valid      cadena String con el conjunto de validaciones
	 * @return valid
	 */
	private String validExistsInstrumentPayment(PaymentDto paymentDto, String valid) {
		int lines = 0;
		if (paymentDto.getPaymentCashList() != null)
			lines = lines + paymentDto.getPaymentCashList().size();

		if (paymentDto.getCreditCardPaymentList() != null)
			lines = lines + paymentDto.getCreditCardPaymentList().size();

		if (paymentDto.getTransferPaymentList() != null)
			lines = lines + paymentDto.getTransferPaymentList().size();

		if (paymentDto.getCreditNotePaymentList() != null)
			lines = lines + paymentDto.getCreditNotePaymentList().size();

		if (paymentDto.getCheckPaymentList() != null)
			lines = lines + paymentDto.getCheckPaymentList().size();

		if (paymentDto.getCreditPaymentList() != null)
			lines += paymentDto.getCreditPaymentList().size();

		valid = (lines == 0) ? valid + " - PAYMENT INSTRUMENT REQUIRED - " : valid;

		return valid;
	}

	/**
	 * Método que valida que el estado de la orden sea valido para el proceso
	 * visualización de datos antes del cobro
	 * 
	 * @param orderDto    orden de venta a validar
	 * @param idOperation identificador de traza de operación
	 * 
	 * @return String, si la orden tiene un estado de cobro de creación retorna una
	 *         cadena vacia, para todo estado diferente, retorna una cadena de texto
	 *         con el estado de la orden
	 */
	public String validStatusOrder(OrderDto orderDto, String idOperation) {
		StatusDto statusDto = orderDto.getStatus();
		if (orderDto.getIsUpdated()) {
			LOG.warn(String.format("%s PAYMENT IS UPDATED ", idOperation));
			return "- ORDER IS ACTIVE UPDATE - ";
		}
		if (!statusDto.getCode().equals(StatusOrder.CREATE_SALE_ORDER.getValue())) {
			return "- INVALID STATUS ORDER: " + statusDto.getDescription().toUpperCase() + "-";
		}
		return "";
	}

	/**
	 * Metodo que valida que la orden a cobrar sea ejecutada por un empleado de la
	 * sucursal de origen
	 * 
	 * @param orderDto          datos operacionales de la orden de venta
	 * @param employeBranchCode código de sucursal del empleado logeado
	 * @param idOperation
	 * @return retorna un valor true si la sucursal del empleado es la misma que la
	 *         sucursal de origen de la orden de venta, y false en caso contrario
	 */

	public boolean isValidBranch(OrderDto orderDto, String employeBranchCode, String idOperation) {
		boolean isValid = true;
		System.out.println(employeBranchCode);
		if (!orderDto.getBranchCode().equals(employeBranchCode)) {
			LOG.warn(String.format("%s BRANCH PAYYMENT IS NOT DE SAME OF THE ORDER ", idOperation));
			System.out.println(employeBranchCode);
			isValid = false;
		}
		return isValid;
	}

	/**
	 * Método que valida que el total de la orden de venta sea cubierto en su
	 * totalidad.
	 * 
	 * @param pendingPayment monto pendiente despues de la resta de los cobros
	 *                       registrados
	 * 
	 * @return cadena de texto que indica si el monto pendiente ha sido saldado, de
	 *         ser así retorna una cadena vacia, en caso de recibir cantidades
	 *         negativas o mayores a cero se indicara la causa
	 */
	public String validPendingPayment(double pendingPayment) {
		return pendingPayment == 0 ? ""
				: (pendingPayment > 0) ? "INSUFFICIENT AMOUNT ENTERED " : "AMOUNT ENTERED GREATER THAN REQUESTED ";

	}

	/**
	 * Método que valida los datos minimos para la consulta de cobros
	 * 
	 * @param genericSearchPaymentDto
	 * @return
	 */
	public String validInputParamsToSearchPayments(GenericSearchPaymentDto genericSearchPaymentDto) {
		String validations = "";
		validations = genericSearchPaymentDto.getEmployeeEmail() == null ? validations + " - EMAIL REQUIRED -"
				: genericSearchPaymentDto.getEmployeeEmail().isEmpty() ? validations + " - EMAIL REQUIRED -"
						: validations;

		validations = genericSearchPaymentDto.getBranchCode() == null ? validations + " - BRANCH REQUIRED -"
				: genericSearchPaymentDto.getBranchCode().isEmpty() ? validations + " - BRANCH REQUIRED -"
						: validations;

		return validations;
	}

	/**
	 * Método que valida los datos requeridos para la consulta del detalle de ordene
	 * 
	 * @param genericSearchPaymentDto
	 * @return
	 */
	public String validInputParamsToGetOrderDetail(GenericSearchPaymentDto genericSearchPaymentDto) {

		String validations = "";

		validations = genericSearchPaymentDto.getOrderCode() == null ? validations + " - ORDER CODE REQUIRED -"
				: genericSearchPaymentDto.getOrderCode().isEmpty() ? validations + " - ORDER CODE REQUIRED -"
						: validations;

		validations = genericSearchPaymentDto.getOrderNumber() == null ? " - ORDER NUMBER IS REQUIRED" : validations;

		return validations;

	}

	/**
	 * Método que valida el rol operativio para aprobaciones de cobros
	 * 
	 * @param employeeDto
	 * @return
	 */
	public String validOperativeRoleJob(EmployeeDto employeeDto) {
		String validations = "";

		if (employeeDto == null)
			return BAD_USER_CONFIGURATION;

//		String roleJob = employeeDto.getRoleJob().getCode();
//		validations = roleJob.equals(OperationalLevelEnum.OPERATIONAL.toString())
//				|| roleJob.equals(OperationalLevelEnum.SUPERVISION_III.toString())
//						? validations += " -INVALID OPERATIVE ROLE JOB"
//						: validations;

		return validations;
	}
}
