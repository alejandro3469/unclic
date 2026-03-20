package mx.com.endtoend.domain.payments.business;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import mx.com.endtoend.smart.bussiness.model.orders.dto.OrderDetailDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.com.endtoend.domain.accountingRecord.dto.AccountingRecordDto;
import mx.com.endtoend.domain.accountingRecord.ports.AccountingRecordPersistencePort;
import mx.com.endtoend.domain.accountingRecord.services.GenerateAccountingRecordService;
import mx.com.endtoend.domain.branch.dto.BranchDto;
import mx.com.endtoend.domain.commons.constants.ActionOrder;
import mx.com.endtoend.domain.commons.constants.OperationalLevelEnum;
import mx.com.endtoend.domain.commons.constants.StatusOrder;
import mx.com.endtoend.domain.company.dto.CompanyDto;
import mx.com.endtoend.domain.company.ports.spi.CompanyPersistencePort;
import mx.com.endtoend.domain.creditNote.dto.AuthorizationParmasDto;
import mx.com.endtoend.domain.creditNote.dto.CreditNoteHeaderDto;
import mx.com.endtoend.domain.creditNote.ports.CreditNotePersistencePort;
import mx.com.endtoend.domain.openings.dto.OpeningOperationDto;
import mx.com.endtoend.domain.orders.dto.GenericSerchParamsOrderDto;
import mx.com.endtoend.domain.payments.business.validations.GenericPaymentValidation;
import mx.com.endtoend.domain.payments.dto.GenericSearchPaymentDto;
import mx.com.endtoend.domain.payments.dto.PaidOrderSummaryDto;
import mx.com.endtoend.domain.payments.dto.PaymentCustomParams;
import mx.com.endtoend.domain.payments.dto.ticket.PaymentTicketDetailDto;
import mx.com.endtoend.domain.payments.dto.ticket.PaymentTicketDto;
import mx.com.endtoend.domain.payments.ports.PaymentPersistencePort;
import mx.com.endtoend.domain.payments.services.PaymentMathService;
import mx.com.smartbussiness.generic.utils.DecimalPrecisionUtils;
import mx.com.endtoend.domain.userConfigurations.dto.EmployeeDto;
import mx.com.endtoend.domain.users.dto.UserDto;
import mx.com.endtoend.genericCommonsFileds.utilities.WrittenCurrency;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.GlobalError;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.SemiFullFunction;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.ValidationError;
import mx.com.endtoend.infrastructure.services.jde.payments.common.serviceport.PaymentJDEServicePort;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;
import mx.com.endtoend.smart.bussiness.model.orders.dto.OrderDto;
import mx.com.endtoend.smart.bussiness.model.orders.dto.OrderHistoryDto;
import mx.com.endtoend.smart.bussiness.model.payments.CreditCardPaymentDto;
import mx.com.endtoend.smart.bussiness.model.payments.CreditNotePaymentDto;
import mx.com.endtoend.smart.bussiness.model.payments.InvoiceReferenceDto;
import mx.com.endtoend.smart.bussiness.model.payments.OrderPaymentDto;
import mx.com.endtoend.smart.bussiness.model.payments.PaymentDetailDto;
import mx.com.endtoend.smart.bussiness.model.payments.PaymentDto;
import mx.com.endtoend.smart.bussiness.model.payments.PaymentOrderJDE;

/**
 * Implemenmtación concreta de la interfaz PaymentInterface con la lógica de
 * negocio del método PAYMENT_ONE
 *
 * @author ddcasas
 *
 */
public class PaymentMethodOne implements PaymentInterface {

	private static final int GENERIC_CLIENT_ID = 1;

	private static final String INVALID_AUTHORIZATION_CODE = "INVALID AUTHORIZATION CODE";

	private PaymentJDEServicePort paymentJDEServicePort;

	private AccountingRecordPersistencePort accountingRecordPersistencePort;

	private CreditNotePersistencePort creditNotePersistencePort;

	private CompanyPersistencePort companyPersistencePort;

	public PaymentMethodOne(PaymentCustomParams paymentCustomParams) {
		this.paymentJDEServicePort = paymentCustomParams.getPaymentJDEServicePort();
		this.accountingRecordPersistencePort = paymentCustomParams.getAccountingRecordPersistencePort();
		this.creditNotePersistencePort = paymentCustomParams.getCreditNotePersistencePort();
		this.companyPersistencePort = paymentCustomParams.getCompanyPersistencePort();
	}

	private final Logger LOG = LoggerFactory.getLogger(PaymentMethodOne.class);

	private GenericPaymentValidation paymentValidation = new GenericPaymentValidation();

	private PaymentMathService paymentMathService = new PaymentMathService();

	private WrittenCurrency writtenCurrency = new WrittenCurrency();

	private GenerateAccountingRecordService accountingRecordService = new GenerateAccountingRecordService();

	/**
	 * Método para la recuperación de ordenes de venta con el resumen de los datos a
	 * emplear en el proceso de cobro
	 *
	 * @param paymentPersistencePort
	 * @param paymentCustomParams
	 * @param genericSerchParamsOrderDto
	 *
	 * @return ResponseModel, objeto que contiene el código de resultado de la
	 *         operación y los datos solicitados
	 *
	 */
	@Override
	public ResponseModel getOrderByCompanyCodeAndParams(PaymentPersistencePort paymentPersistencePort,
														PaymentCustomParams paymentCustomParams, GenericSerchParamsOrderDto genericSerchParamsOrderDto) {
		String companyCode = paymentCustomParams.getCompanyCode();
		String idOperation = paymentCustomParams.getIdOperation();
		LOG.info(String.format("%s INIT getOrderByCompanyCodeAndParams() ", idOperation));
		OrderDto orderDto = (OrderDto) paymentPersistencePort
				.getOrderByCompanyCodeAndParams(genericSerchParamsOrderDto.getOrderNumber(),
						genericSerchParamsOrderDto.getOrderCode(), companyCode, idOperation)
				.getData();

		boolean orderHasPayments = (boolean) paymentPersistencePort
				.orderHasPayment(genericSerchParamsOrderDto.getOrderNumber(),
						genericSerchParamsOrderDto.getOrderCode(), idOperation, companyCode)
				.getData();

		if (orderHasPayments) {
			LOG.warn(String.format("%s BAD VALIDATIONS: %s", idOperation, "- INVALID STATUS ORDER : LA ORDEN YA CUENTA CON PAGOS -"));
			throw new ValidationError("- INVALID STATUS ORDER : LA ORDEN YA CUENTA CON PAGOS -");
		}

		String validations = paymentValidation.validOrderStatusMathodOne(orderDto,
				genericSerchParamsOrderDto.getBranchCode(), idOperation);
		if (!validations.isEmpty()) {
			LOG.warn(String.format("%s BAD VALIDATIONS: %s", idOperation, validations));
			throw new ValidationError(validations);
		}

		boolean orderIsActive = (boolean) paymentPersistencePort
				.updateFlagByCompanyCodeAndParams(genericSerchParamsOrderDto.getOrderNumber(),
						genericSerchParamsOrderDto.getOrderCode(), true, idOperation, companyCode)
				.getData();

		if (!orderIsActive) {
			LOG.warn(String.format("%s ERROR IN CHANGE STATUS %s", idOperation, validations));
			throw new GlobalError();
		}

		OrderPaymentDto orderPaymentDto = new OrderPaymentDto(orderDto.getOrderNumber(), orderDto.getOrderCode(),
				orderDto.getBranchCode(), orderDto.getOrderTotal(), orderDto.getPendingPayment(),
				orderDto.getCfdiType(), orderDto.getClient().getNoClient());

		orderPaymentDto.setIsRetentionOrder(orderDto.getIsRetentionOrder());

		LOG.info(String.format("%s RETURN OPERATIVE DATA", idOperation));
		return new ResponseModel(orderPaymentDto);
	}


	@Override
	public ResponseModel saveAllOrders(PaymentPersistencePort paymentPersistencePort) {
		List<PaymentOrderJDE> paymentOrderJDEList = (List<PaymentOrderJDE>) paymentPersistencePort
				.getOrdersToSendToQueueV2(null,  " ", "FCAL", null);

		for (PaymentOrderJDE paymentOrder : paymentOrderJDEList) {
			Long batchFolio = (Long) paymentJDEServicePort.getBathcFolioByCompanyCode("47", "FCAL", "0")
					.getData();

			PaymentDto paymentDto = paymentOrder.getPaymentDto();
			OrderDto orderDto = paymentOrder.getOrder();

			paymentPersistencePort.updateOrderStatusByOrderNumberAndCompanyCode(paymentDto.getOrderNumber(),
					paymentDto.getOrderCode(), paymentDto.getPaymentState(), paymentDto.getPendingPayment(),
					batchFolio, "FCAL", "0");
			orderDto.setBatchFolio(batchFolio);
		}

		paymentJDEServicePort.sendOrdersToSaveToQueue(paymentOrderJDEList, "FCAL", null);
		return new ResponseModel(true);
	}

	@Override
	public ResponseModel createPayment(PaymentPersistencePort paymentPersistencePort,
									   PaymentCustomParams paymentCustomParams, PaymentDto paymentDto) {

		String companyCode = paymentCustomParams.getCompanyCode();
		String idOperation = paymentCustomParams.getIdOperation();
		LOG.info(String.format("%s INIT createPayment() ", idOperation));

		EmployeeDto employeeDto = (EmployeeDto) paymentPersistencePort
				.getEmployeConfigurationByEmailAndCompanyCode(paymentDto.getEmployeEmail(), companyCode, idOperation)
				.getData();

		OrderDto orderDto = (OrderDto) paymentPersistencePort.getOrderByCompanyCodeAndParams(
				paymentDto.getOrderNumber(), paymentDto.getOrderCode(), companyCode, idOperation).getData();

		OpeningOperationDto openingOperation = (OpeningOperationDto) paymentPersistencePort
				.getOpeningOperationByEmailAndCompanyCode(paymentDto.getEmployeEmail(), companyCode, idOperation)
				.getData();

		String validations = paymentValidation.validOperativeDataToPaymentProcessMethodOne(employeeDto, paymentDto,
				creditNotePersistencePort, orderDto.getStatus(), openingOperation, companyCode, idOperation);
		if (!validations.isEmpty()) {
			LOG.warn(String.format("%s BAD VALIDATIONS: %s", idOperation, validations));
			throw new ValidationError(validations);
		}

		paymentDto = new PaymentDto(paymentDto, orderDto.getOrderTotal(), orderDto.getPendingPayment(),
				openingOperation.getOpeningId(), StatusOrder.FULL_PAYMENT.getValue(), employeeDto.getUserId(),
				employeeDto.getUserNumber());
		PaymentDto paymentProcess = paymentMathService.processPayment(paymentCustomParams, paymentDto);

		String validPendingPayment = paymentValidation.validPendingPayment(paymentProcess.getPendingPayment().doubleValue());
		if (!validPendingPayment.isEmpty()) {
			LOG.warn(String.format("%s BAD VALIDATIONS: %s", idOperation, validPendingPayment));
			throw new ValidationError(validPendingPayment);
		}

		paymentProcess = (PaymentDto) paymentPersistencePort.createPayment(paymentDto, companyCode, idOperation)
				.getData();

		processCreditNoteBalance(paymentProcess, companyCode, idOperation);

		Long batchFolio = (Long) paymentJDEServicePort.getBathcFolioByCompanyCode("47", companyCode, idOperation)
				.getData();

		paymentPersistencePort.updateOrderStatusByOrderNumberAndCompanyCode(paymentProcess.getOrderNumber(),
				paymentProcess.getOrderCode(), paymentProcess.getPaymentState(), paymentProcess.getPendingPayment(),
				batchFolio, companyCode, idOperation);
		orderDto.setBatchFolio(batchFolio);

		PaymentOrderJDE paymentOrderJDE = new PaymentOrderJDE(orderDto, paymentProcess);
		paymentJDEServicePort.sendOrderToSave(paymentOrderJDE, companyCode, idOperation);
		// paymentOrderJDE (orderDto, paymentProcess)

		// orderDto(OrderCode, orderNumber, companyCode)
		//paymentProcess(PaymentDto, companyCode)

		String errorMessage = "";

		CompanyDto companyDto = (CompanyDto) companyPersistencePort.findByCode(companyCode, idOperation).getData();

		if (companyDto.getApplyInvoiceProcess() && orderDto.getClient().getId() != GENERIC_CLIENT_ID){
			InvoiceReferenceDto invoiceReferenceDto = generateInvoiceRecord(paymentPersistencePort, companyCode, idOperation, orderDto, paymentProcess,
					errorMessage, companyDto);
			paymentProcess.setInvoiceReference(invoiceReferenceDto);
		}

		createAccountingRecords(paymentProcess, openingOperation, employeeDto.getUserId(), companyCode, errorMessage,
				idOperation);

		saveChangeInOrderHistory(paymentPersistencePort, companyCode, idOperation, employeeDto, orderDto,
				paymentProcess);

		if (!errorMessage.isEmpty()) {
			LOG.warn(String.format("%s ERROR IN PROCESS: %s", idOperation, errorMessage));
			throw new SemiFullFunction(errorMessage, paymentProcess);
		}
		return new ResponseModel(paymentProcess);
	}

	private InvoiceReferenceDto generateInvoiceRecord(PaymentPersistencePort paymentPersistencePort, String companyCode,
													  String idOperation, OrderDto orderDto, PaymentDto paymentProcess, String errorMessage,
													  CompanyDto companyDto) {

		try {
			ResponseModel responseInvoiceRecord = paymentJDEServicePort.getInvoiceRecordByBranchCodeAndCompanyCode(
					orderDto.getBranchCode(), companyCode, companyDto.getCompanyNumber(), idOperation); // aqui tenemos el invoice reference
			InvoiceReferenceDto invoiceReference = (InvoiceReferenceDto) responseInvoiceRecord.getData();

			LOG.info(String.format("%s INVOICE REFERENCE CREATED: orderNumber=%s, orderCode=%s", 
					idOperation, orderDto.getOrderNumber(), orderDto.getOrderCode()));

			ResponseModel responseInvoiceReference = paymentPersistencePort.saveInvoiceReferenceByCompanyCode(
					invoiceReference, paymentProcess, orderDto.getBranchCode(), companyCode, idOperation);
			boolean isSavedInvoiceReference = (boolean) responseInvoiceReference.getData();

			errorMessage = !isSavedInvoiceReference
					? errorMessage + " ERROR IN SAVE INVOICE REFERENCE. CONTACT YOUR ADMINISTRATOR"
					: errorMessage;

			if (isSavedInvoiceReference) { // facturacion
				LOG.info(String.format("%s SEND INVOICE DATA TO JDE", idOperation));
				paymentProcess.setInvoiceReference(invoiceReference);
				PaymentOrderJDE paymentInvoiceOrderJDE = new PaymentOrderJDE(orderDto, paymentProcess,
						companyDto.getRfc());
				paymentJDEServicePort.sendOrderToSaveInvoiceByCompanyCoode(paymentInvoiceOrderJDE, companyCode,
						idOperation);
			}

			return invoiceReference;

		} catch (Exception e) {
			LOG.error(String.format("%s ERROR IN SAVE INVOICE REFERENCE: %s", idOperation, e.getMessage()));
			errorMessage = " ERROR IN SAVE INVOICE REFERENCE. CONTACT YOUR ADMINISTRATOR";
		}

		return null;
	}

	private void saveChangeInOrderHistory(PaymentPersistencePort paymentPersistencePort, String companyCode,
										  String idOperation, EmployeeDto employeeDto, OrderDto orderDto, PaymentDto paymentProcess) {
		LOG.info(String.format("%s NEW RECORD IN ORDER HISTORY", idOperation));
		OrderHistoryDto orderHistoryDto = new OrderHistoryDto(employeeDto.getBranchCode(),
				paymentProcess.getOrderCode(), paymentProcess.getOrderNumber(), new Date(),
				ActionOrder.UPDATE_PYMENT.name(), paymentProcess.getEmployeEmail(), employeeDto.getUserNumber(), "",
				orderDto.getIsRetentionOrder());
		paymentPersistencePort.saveRecordInOrderHistory(orderHistoryDto, companyCode, idOperation);
	}

	private void processCreditNoteBalance(PaymentDto paymentDto, String companyCode, String idOperation) {
		if (paymentDto.getCreditNotePaymentList() != null) {
			if (!paymentDto.getCreditNotePaymentList().isEmpty()) {

				for (CreditNotePaymentDto creditNotePayment : paymentDto.getCreditNotePaymentList()) {

					ResponseModel responseGetCreditNote = creditNotePersistencePort
							.searchCreditNoteHeaderByFolioAndCodeAndCompanyCode(creditNotePayment.getFolio(),
									creditNotePayment.getCreditNoteCode(), companyCode, idOperation);
					CreditNoteHeaderDto creditNoteHeaderDto = (CreditNoteHeaderDto) responseGetCreditNote.getData();

					BigDecimal usedAmountM = creditNoteHeaderDto.getUsedAmountM();
					BigDecimal pendingAmount = BigDecimal.ZERO;

					usedAmountM = usedAmountM.add(creditNotePayment.getAmountApplied());
					usedAmountM = usedAmountM.setScale(2, java.math.RoundingMode.HALF_UP);

					pendingAmount = creditNoteHeaderDto.getTotalAmount().subtract(usedAmountM);
					pendingAmount = pendingAmount.setScale(2, java.math.RoundingMode.HALF_UP);

					creditNoteHeaderDto.setUsedAmountM(usedAmountM);
					creditNoteHeaderDto.setPendingAmount(pendingAmount);

					creditNotePersistencePort.updteCreditNoteHeaderBalanceByParamsAndCompanyCode(creditNoteHeaderDto,
							companyCode, idOperation);
				}
			}
		}
	}

	private void createAccountingRecords(PaymentDto paymentProcess, OpeningOperationDto openingOperation, Long userId,
										 String companyCode, String errorMessage, String idOperation) {

		try {

			LOG.info(String.format("%s INIT GENERATE ACCOUNTING RECORDS", idOperation));
			List<AccountingRecordDto> accountingRecordList = new ArrayList<>();

			if (paymentProcess.getPaymentCashList() != null)
				accountingRecordList = accountingRecordService.generateByPaymentCashList(paymentProcess,
						openingOperation, userId, accountingRecordList);

			if (paymentProcess.getCreditCardPaymentList() != null)
				accountingRecordList = accountingRecordService.generateByCreditCardPaymentList(paymentProcess,
						openingOperation, userId, accountingRecordList);

			if (paymentProcess.getTransferPaymentList() != null)
				accountingRecordList = accountingRecordService.generateByPaymentTransferList(paymentProcess,
						openingOperation, userId, accountingRecordList);

			if (paymentProcess.getCreditNotePaymentList() != null)
				accountingRecordList = accountingRecordService.generateByPaymentCreditNoteList(paymentProcess,
						openingOperation, userId, accountingRecordList);

			if (paymentProcess.getCheckPaymentList() != null)
				accountingRecordList = accountingRecordService.generateByPaymentCheckList(paymentProcess,
						openingOperation, userId, accountingRecordList);

			if (paymentProcess.getCreditPaymentList() != null)
				accountingRecordList = accountingRecordService.generateByCreditPaymentList(paymentProcess,
						openingOperation, userId, accountingRecordList);

			LOG.info(String.format("%s INIT SAVE ACCOUNTING RECORDS", idOperation));
			ResponseModel responseCreateAccountingRecord = accountingRecordPersistencePort
					.createAccountingRecordMovementByCompanyCode(accountingRecordList, companyCode, idOperation);

			boolean created = (boolean) responseCreateAccountingRecord.getData();

			errorMessage = !created ? errorMessage + " ERROR SAVING ACCOUNTING RECORDS. CONTACT YOUR ADMINISTRATOR"
					: errorMessage;

		} catch (Exception e) {
			errorMessage += " ERROR SAVING ACCOUNTING RECORDS. CONTACT YOUR ADMINISTRATOR";
		}

	}

	@Override
	public ResponseModel cancelPaymentByParams(PaymentPersistencePort paymentPersistencePort,
											   PaymentCustomParams paymentCustomParams, GenericSerchParamsOrderDto genericSerchParamsOrderDto) {

		String companyCode = paymentCustomParams.getCompanyCode();
		String idOperation = paymentCustomParams.getIdOperation();
		LOG.info(String.format("%s INIT cancelPaymentByParams() ", idOperation));

		EmployeeDto employeeDto = (EmployeeDto) paymentPersistencePort.getEmployeConfigurationByEmailAndCompanyCode(
				genericSerchParamsOrderDto.getEmployeeEmail(), companyCode, idOperation).getData();

		OrderDto orderDto = (OrderDto) paymentPersistencePort
				.getOrderByCompanyCodeAndParams(genericSerchParamsOrderDto.getOrderNumber(),
						genericSerchParamsOrderDto.getOrderCode(), companyCode, idOperation)
				.getData();

		String validations = paymentValidation.validOperativeDataToCancelPaymentProcess(employeeDto, orderDto,
				idOperation);

		if (!validations.isEmpty()) {
			LOG.warn(String.format("%s BAD VALIDATIONS: %s", idOperation, validations));
			throw new ValidationError(validations);
		}

		ResponseModel responseUpdate = paymentPersistencePort.updateFlagByCompanyCodeAndParams(
				genericSerchParamsOrderDto.getOrderNumber(), genericSerchParamsOrderDto.getOrderCode(), false,
				idOperation, companyCode);

		boolean changeStatus = (boolean) responseUpdate.getData();

		return new ResponseModel(changeStatus);
	}

	@Override
	public ResponseModel createPaymentTicketByOrderCodeAndType(PaymentPersistencePort paymentPersistencePort,
															   PaymentCustomParams paymentCustomParams, BigDecimal orderNumber, String orderCode) {

		String companyCode = paymentCustomParams.getCompanyCode();
		String idOperation = paymentCustomParams.getIdOperation();
		LOG.info(String.format("%s INIT createPaymentTicketByOrderCodeAndType() ", idOperation));

		ResponseModel responseGetPayment = paymentPersistencePort
				.getPaymentDetailByOrderNumberAndOrderCodeAndCompanyCode(orderNumber, orderCode, companyCode,
						idOperation);
		PaymentDto paymentDto = (PaymentDto) responseGetPayment.getData();

		ResponseModel responseGetOrder = paymentPersistencePort.getOrderByCompanyCodeAndParams(orderNumber, orderCode,
				companyCode, idOperation);
		OrderDto orderDto = (OrderDto) responseGetOrder.getData();

		for (OrderDetailDto detail : orderDto.getOrderDetail()) {
			detail.setSubTotal(detail.getSubTotal());
			detail.setUnitPriceTax(detail.getUnitPriceTax());
			detail.setSubTotalTax(detail.getSubTotalTax());
			detail.setArticleTax(detail.getArticleTax());
		}

		ResponseModel responseGetBranch = paymentPersistencePort
				.getBranchDetailByBranchCodeAndCompanyCode(orderDto.getBranchCode(), companyCode, idOperation);
		BranchDto branchDto = (BranchDto) responseGetBranch.getData();

		ResponseModel responseGetUser = paymentPersistencePort
				.getUserInformationByEmailAndCompanyCode(orderDto.getEmployeeEmail(), companyCode, idOperation);
		UserDto userVendor = (UserDto) responseGetUser.getData();

		ResponseModel responseGetSeller = paymentPersistencePort
				.getSellerInformationByEmailAndCompanyCode(orderDto.getUserNumber(), companyCode, idOperation);
		UserDto userSeller = (UserDto) responseGetSeller.getData();

		ResponseModel responseGetUserVendor = paymentPersistencePort
				.getUserInformationByEmailAndCompanyCode(paymentDto.getEmployeEmail(), companyCode, idOperation);
		UserDto userSale = (UserDto) responseGetUserVendor.getData();

		PaymentTicketDto paymentTicketDto = new PaymentTicketDto(paymentDto, orderDto, branchDto, userSeller, userSeller,
				paymentDto.getIsPrinted());
		List<PaymentTicketDetailDto> paymentTicketDetailList = new ArrayList<>();
		for (OrderDetailDto orderDetail : orderDto.getOrderDetail()) {
			paymentTicketDetailList.add(new PaymentTicketDetailDto(orderDetail));
		}

		BigDecimal commision = getBankCommision(paymentDto);
		if (commision.compareTo(BigDecimal.ZERO) > 0) {
			paymentTicketDetailList.add(new PaymentTicketDetailDto(commision));
		}

		paymentTicketDto.setDetail(paymentTicketDetailList);

		String amountLetter = writtenCurrency.enterNumber(orderDto.getOrderTotal().toString());
		paymentTicketDto.setAmountLetter(amountLetter);

		ResponseModel paymentTicket = paymentPersistencePort.getPaymentTicketByCompanyCode(paymentTicketDto,
				companyCode, idOperation);

		if (!paymentDto.getIsPrinted())
			paymentPersistencePort.updatePrintStateByPaymentIdAndCompanyCode(paymentDto.getPaymentId(), true,
					companyCode, idOperation);

		return paymentTicket;
	}

	private BigDecimal getBankCommision(PaymentDto paymentDto) {
		BigDecimal commision = BigDecimal.ZERO;
		if (paymentDto.getCreditCardPaymentList() != null) {
			if (paymentDto.getCreditCardPaymentList().size() > 0) {
				for (CreditCardPaymentDto creditCardPaymentDto : paymentDto.getCreditCardPaymentList()) {
					if (creditCardPaymentDto.getApplyComission())
						commision = commision.add(creditCardPaymentDto.getCommission());
				}
			}
		}
		return DecimalPrecisionUtils.roundToTwoDecimals(commision);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ResponseModel searchPaidOrderSummaryByCompanyCode(PaymentPersistencePort paymentPersistencePort,
															 PaymentCustomParams paymentCustomParams, GenericSearchPaymentDto genericSearchPaymentDto) {
		String companyCode = paymentCustomParams.getCompanyCode();
		String idOperation = paymentCustomParams.getIdOperation();
		LOG.info(String.format("%s INIT searchPaidOrderSummaryByCompanyCode() ", idOperation));

		String validInputParams = paymentValidation.validInputParamsToSearchPayments(genericSearchPaymentDto);
		if (!validInputParams.isEmpty()) {
			LOG.warn(String.format("%s BAD VALIDATIONS: %s", idOperation, validInputParams));
			throw new ValidationError(validInputParams);
		}

		ResponseModel responseEmployee = paymentPersistencePort.getEmployeConfigurationByEmailAndCompanyCode(
				genericSearchPaymentDto.getEmployeeEmail(), companyCode, idOperation);

		if (responseEmployee == null) {
			throw new ValidationError("EMPLOYEE NOT FOUND");
		}

		EmployeeDto employeeDto = (EmployeeDto) responseEmployee.getData();

		if (!employeeDto.getRoleJob().getCode().equals(OperationalLevelEnum.OPERATIONAL.toString())) {
			genericSearchPaymentDto.setEmployeeEmail("");
			genericSearchPaymentDto.setBranchCode(employeeDto.getBranchCode());
		}

		ResponseModel responseSearch = paymentPersistencePort
				.searchPaidOrderSummaryByCompanyCode(genericSearchPaymentDto, companyCode, idOperation);
		List<PaidOrderSummaryDto> paidOrderSummaryList = new ArrayList<>();

		if (responseSearch.getData() != null) {
			paidOrderSummaryList = (List<PaidOrderSummaryDto>) responseSearch.getData();
		}
		return new ResponseModel(paidOrderSummaryList);
	}

	@Override
	public ResponseModel searchPaidOrderDetailByCompanyCode(PaymentPersistencePort paymentPersistencePort,
															PaymentCustomParams paymentCustomParams, GenericSearchPaymentDto genericSearchPaymentDto) {
		String companyCode = paymentCustomParams.getCompanyCode();
		String idOperation = paymentCustomParams.getIdOperation();
		LOG.info(String.format("%s INIT searchPaidOrderDetailByCompanyCode() ", idOperation));

		String validInputParams = paymentValidation.validInputParamsToGetOrderDetail(genericSearchPaymentDto);
		if (!validInputParams.isEmpty()) {
			LOG.warn(String.format("%s BAD VALIDATIONS: %s", idOperation, validInputParams));
			throw new ValidationError(validInputParams);
		}

		ResponseModel responseSearch = paymentPersistencePort.getPaymentDetailByOrderNumberAndOrderCodeAndCompanyCode(
				genericSearchPaymentDto.getOrderNumber(), genericSearchPaymentDto.getOrderCode(), companyCode,
				idOperation);

		if (responseSearch.getData() == null) {
			LOG.warn(String.format("%s ORDER DETAIL NOT FOUNT", idOperation, validInputParams));
			throw new ValidationError("ORDER DETAIL NOT FOUNT");
		}
		PaymentDto paymentDto = (PaymentDto) responseSearch.getData();

		PaymentDetailDto paymentDetail = new PaymentDetailDto(paymentDto);

		CompanyDto companyDto = (CompanyDto) companyPersistencePort.findByCode(companyCode, idOperation).getData();
		if (companyDto.getApplyInvoiceProcess()) {
			ResponseModel responseInvoiceReference = paymentPersistencePort
					.getInvoiceReferenceByPaymentIdAndCompanyCode(paymentDto.getPaymentId(), companyCode, idOperation);
			InvoiceReferenceDto invoiceReference = (InvoiceReferenceDto) responseInvoiceReference.getData();
			paymentDetail.setInvoiceReference(invoiceReference);
		}

		return new ResponseModel(paymentDetail);
	}

	@Override
	public ResponseModel approvalPaymentByParamsAndCompanyCode(PaymentPersistencePort paymentPersistencePort,
															   PaymentCustomParams paymentCustomParams, AuthorizationParmasDto authorizationParmasDto) {
		String companyCode = paymentCustomParams.getCompanyCode();
		String idOperation = paymentCustomParams.getIdOperation();
		LOG.info(String.format("%s INIT approvalPaymentByParamsAndCompanyCode() ", idOperation));

		EmployeeDto employeeDto = (EmployeeDto) paymentPersistencePort.getEmployeConfigurationByEmailAndCompanyCode(
				authorizationParmasDto.getEmployeeEmail(), companyCode, idOperation).getData();

		String validOperativeRoleJobe = paymentValidation.validOperativeRoleJob(employeeDto);
		if (!validOperativeRoleJobe.isEmpty()) {
			throw new ValidationError(validOperativeRoleJobe);
		}

		OrderDto orderDto = (OrderDto) paymentPersistencePort
				.getOrderByCompanyCodeAndParams(authorizationParmasDto.getOrderNumber(),
						authorizationParmasDto.getOrderCode(), companyCode, idOperation)
				.getData();

		if (orderDto == null)
			throw new ValidationError("ORDER NOT EXISTS");

		boolean aprobeProcess = authorizationParmasDto.getAuthorizationCode()
				.equals(employeeDto.getUserConfiguration().getAuthorizationCode());

		if (!aprobeProcess)
			throw new ValidationError(INVALID_AUTHORIZATION_CODE);

		LOG.info(String.format("%s NEW RECORD IN ORDER HISTORY", idOperation));
		OrderHistoryDto orderHistoryDto = new OrderHistoryDto(employeeDto.getBranchCode(), orderDto.getOrderCode(),
				orderDto.getOrderNumber(), new Date(), ActionOrder.APPROVE_CREDIT_NOTE_PAYMENT.name(),
				employeeDto.getEmployeeEmail(), employeeDto.getUserNumber(), "", orderDto.getIsRetentionOrder());
		paymentPersistencePort.saveRecordInOrderHistory(orderHistoryDto, companyCode, idOperation);

		return new ResponseModel(aprobeProcess);
	}

}