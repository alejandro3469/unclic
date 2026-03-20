package mx.com.endtoend.domain.creditNote.business;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import mx.com.endtoend.smart.bussiness.model.orders.dto.OrderDetailDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.com.endtoend.domain.branch.dto.BranchDto;
import mx.com.endtoend.domain.commons.constants.ActionOrder;
import mx.com.endtoend.domain.commons.constants.OperationalLevelEnum;
import mx.com.endtoend.domain.creditNote.business.validations.CreditNoteValidation;
import mx.com.endtoend.domain.creditNote.dto.AuthorizationParmasDto;
import mx.com.endtoend.domain.creditNote.dto.CreditNoteCustomParams;
import mx.com.endtoend.domain.creditNote.dto.CreditNoteDto;
import mx.com.endtoend.domain.creditNote.dto.CreditNoteHeaderDto;
import mx.com.endtoend.domain.creditNote.dto.CreditNoteSearchParamsDto;
import mx.com.endtoend.domain.creditNote.dto.CreditNoteSummary;
import mx.com.endtoend.domain.creditNote.dto.InvoiceRecordDto;
import mx.com.endtoend.domain.creditNote.dto.ticket.CreditNoteTickteDto;
import mx.com.endtoend.domain.creditNote.ports.CreditNotePersistencePort;
import mx.com.endtoend.domain.creditNote.services.CreditNoteInterface;
import mx.com.endtoend.domain.orderConfigurations.ports.spi.OrderConfigurationPersistencePort;
import mx.com.endtoend.domain.orders.ports.OrderPersistencePort;
import mx.com.endtoend.domain.payments.ports.PaymentPersistencePort;
import mx.com.endtoend.domain.userConfigurations.dto.EmployeeDto;
import mx.com.endtoend.domain.users.dto.UserDto;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.ValidationError;
import mx.com.endtoend.infrastructure.services.jde.payments.common.serviceport.PaymentJDEServicePort;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;
import mx.com.endtoend.smart.bussiness.model.orders.dto.OrderConfigurationDto;
import mx.com.endtoend.smart.bussiness.model.orders.dto.OrderDto;
import mx.com.endtoend.smart.bussiness.model.orders.dto.OrderHistoryDto;
import mx.com.endtoend.smart.bussiness.model.payments.PaymentDto;
import mx.com.endtoend.smart.bussiness.model.payments.PaymentOrderJDE;

public class CreditNoteMethodOne implements CreditNoteInterface {

	private static final String INVALID_AUTHORIZATION_CODE = "INVALID AUTHORIZATION CODE";

	private PaymentPersistencePort paymentPersistencePort;

	private PaymentJDEServicePort paymentJDEServicePort;

	private CreditNotePersistencePort creditNotePersistencePort;

	private OrderConfigurationPersistencePort orderConfigurationPersistencePort;

	private OrderPersistencePort orderPersistencePort;

	public CreditNoteMethodOne(CreditNoteCustomParams creditNoteCustomParams) {
		this.orderPersistencePort = creditNoteCustomParams.getOrderPersistencePort();
		this.paymentJDEServicePort = creditNoteCustomParams.getPaymentJDEServicePort();
		this.creditNotePersistencePort = creditNoteCustomParams.getCreditNotePersistencePort();
		this.orderConfigurationPersistencePort = creditNoteCustomParams.getOrderConfigurationPersistencePort();
		this.paymentPersistencePort = creditNoteCustomParams.getPaymentPersistencePort();
	}

	private final Logger LOG = LoggerFactory.getLogger(CreditNoteMethodOne.class);

	private OrderHistoryGenerator orderHistoryGenerator = new OrderHistoryGenerator();

	private CreditNoteGenerator creditNoteGenerator = new CreditNoteGenerator();

	private CreditNoteValidation creditNoteValidation = new CreditNoteValidation();

	private OrderSummaryGenerator orderSummaryGenerator = new OrderSummaryGenerator();

	/**
	 * Método para la creación de notas de crédito y su envío al sistema JDE
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ResponseModel creteCreditNoteByOrder(CreditNoteCustomParams noteCustomParams, String companyCode, String idOperation) {
		LOG.info(String.format("%s INIT creteCreditNoteByOrder()", idOperation));

		ResponseModel responseOrderConfig = orderConfigurationPersistencePort
				.viewOrderConfigurationDetailByOrderCodeAndCompanyCode(noteCustomParams.getOrderDto().getOrderCode(), companyCode, idOperation);
		OrderConfigurationDto orderConfigurationDto = (OrderConfigurationDto) responseOrderConfig.getData();

		String validConfiguration = creditNoteValidation.validOrderConfiguration(orderConfigurationDto);
		if (!validConfiguration.isEmpty()) {
			LOG.warn(String.format("%s BAD PARAM BY ORDER CONFIGURATION", idOperation));
			throw new ValidationError(validConfiguration);
		}

		CompletableFuture<ResponseModel> responseSearchOrderFuture = CompletableFuture.supplyAsync(() ->
				orderPersistencePort.vieworderDetailByOrderNumber(noteCustomParams.getOrderDto().getOrderNumber(), noteCustomParams.getOrderDto().getOrderCode(), companyCode, idOperation));
		CompletableFuture<ResponseModel> responseSearchPaymentsFuture = CompletableFuture.supplyAsync(() ->
				paymentPersistencePort.getPaymentDetailByOrderNumberAndOrderCodeAndCompanyCode(noteCustomParams.getOrderDto().getOrderNumber(), noteCustomParams.getOrderDto().getOrderCode(), companyCode, idOperation));
		CompletableFuture<ResponseModel> responseSearchCreditNoteFuture = CompletableFuture.supplyAsync(() ->
				creditNotePersistencePort.searchCreditNoteByOrderNumberAndCodeAndCompanyCode(noteCustomParams.getOrderDto().getOrderNumber(), noteCustomParams.getOrderDto().getOrderCode(), companyCode, idOperation));
		CompletableFuture<ResponseModel> responseUserConfigurationFuture = CompletableFuture.supplyAsync(() ->
				orderPersistencePort.getUserConfigurationByBranchAndEmail(noteCustomParams.getOrderDto().getBranchCode(), noteCustomParams.getEmployeeEmail(), companyCode, idOperation));

		CompletableFuture.allOf(responseSearchOrderFuture, responseSearchPaymentsFuture, responseSearchCreditNoteFuture, responseUserConfigurationFuture).join();

		OrderDto originalOrder = (OrderDto) responseSearchOrderFuture.join().getData();
		PaymentDto paymentDto = (PaymentDto) responseSearchPaymentsFuture.join().getData();
		CreditNoteDto creditNote = (CreditNoteDto) responseSearchCreditNoteFuture.join().getData();
		EmployeeDto employeeDto = (EmployeeDto) responseUserConfigurationFuture.join().getData();

		OrderDto orderSummary = (creditNote != null) ? orderSummaryGenerator.generateOrderSummary(originalOrder, creditNote) : originalOrder;

		String validOperativeData = creditNoteValidation.validOperativeDataToCreate(originalOrder, creditNote, employeeDto, orderSummary, noteCustomParams.getOrderDto());
		if (!validOperativeData.isEmpty()) {
			LOG.warn(String.format("%s BAD OPERATIVE DATA", idOperation));
			throw new ValidationError(validOperativeData);
		}

		ResponseModel responseInvoice = paymentJDEServicePort.searchInvoiceRecordsByOrderAndCompanyCode(noteCustomParams.getOrderDto(), orderConfigurationDto, companyCode, idOperation);
		List<InvoiceRecordDto> invoiceRecordList = (List<InvoiceRecordDto>) responseInvoice.getData();

		String invoiceRecords = creditNoteValidation.validInvoiceRecordAndAvailability(noteCustomParams.getOrderDto(), invoiceRecordList);
		if (!invoiceRecords.isEmpty()) {
			LOG.warn(String.format("%s ORDER WHITOUT INVOICE RECORDS", idOperation));
			throw new ValidationError(invoiceRecords);
		}

		ResponseModel responseFolio = paymentJDEServicePort.getConsecutiveOrderNumberByCompanyCode(orderConfigurationDto.getCreditNoteCode(), companyCode, originalOrder.getCompanyNumber(), idOperation);
		BigDecimal folio = (BigDecimal) responseFolio.getData();

		CreditNoteDto creditNoteCreated = creditNoteGenerator.generateCreditNoteByOrderAndCreditNote(employeeDto.getUserId(), noteCustomParams.getOrderDto(), creditNote, folio, orderConfigurationDto.getCreditNoteCode());
		Date creditNoteCrerationDate = creditNoteCreated.getHeaderCreationDate();
		ResponseModel responseCNCreated = creditNotePersistencePort.createCreditNoteByCompanyCode(creditNoteCreated, companyCode, idOperation);
		creditNoteCreated = (CreditNoteDto) responseCNCreated.getData();

		OrderHistoryDto orderHistoryDto = orderHistoryGenerator.generateOrderHistory(originalOrder, ActionOrder.GENERATE_CREDIT_NOTE.toString(), employeeDto);
		orderPersistencePort.saveRecordInOrderHistory(orderHistoryDto, companyCode, idOperation);

		PaymentOrderJDE paymentOrderJDE = generateDataToSendJDEService(noteCustomParams, orderConfigurationDto, paymentDto, folio, creditNoteCrerationDate);
		paymentJDEServicePort.sendCreditNoteToSaveByCompanyCoode(paymentOrderJDE, companyCode, idOperation);

		CreditNoteHeaderDto creditNoteHeaderDto = creditNoteCreated.getCreditNoteHeaderList().stream()
				.filter(header -> folio.toString().equals(header.getFolio().toString()))
				.findFirst()
				.orElse(new CreditNoteHeaderDto());

		return new ResponseModel(creditNoteHeaderDto);
	}

	@Override
	public ResponseModel resendNotesToQueue(CreditNoteCustomParams noteCustomParams, String companyCode, String idOperation) {
		return null;
	}

	@Override
	public ResponseModel resendNotesToQueue(CreditNoteCustomParams noteCustomParams, String companyCode,
											String idOperation, BigDecimal folio) {
		/*LOG.info(String.format("%s INIT creteCreditNoteByOrder()", idOperation));

		ResponseModel responseOrderConfig = orderConfigurationPersistencePort
				.viewOrderConfigurationDetailByOrderCodeAndCompanyCode(noteCustomParams.getOrderDto().getOrderCode(),
						companyCode, idOperation);
		OrderConfigurationDto orderConfigurationDto = (OrderConfigurationDto) responseOrderConfig.getData();

		String validConfiguration = creditNoteValidation.validOrderConfiguration(orderConfigurationDto);
		if (!validConfiguration.isEmpty()) {
			LOG.warn(String.format("%s BAD PARAM BY ORDER CONFIGURATION", idOperation));
			throw new ValidationError(validConfiguration);
		}

		ResponseModel responseSearchOrder = orderPersistencePort.vieworderDetailByOrderNumber(
				noteCustomParams.getOrderDto().getOrderNumber(), noteCustomParams.getOrderDto().getOrderCode(),
				companyCode, idOperation);
		OrderDto originalOrder = (OrderDto) responseSearchOrder.getData();

		ResponseModel responseSeachPayments = paymentPersistencePort
				.getPaymentDetailByOrderNumberAndOrderCodeAndCompanyCode(
						noteCustomParams.getOrderDto().getOrderNumber(), noteCustomParams.getOrderDto().getOrderCode(),
						companyCode, idOperation);
		PaymentDto paymentDto = (PaymentDto) responseSeachPayments.getData();

		ResponseModel responseSearchCreditNote = creditNotePersistencePort
				.searchCreditNoteByOrderNumberAndCodeAndCompanyCode(noteCustomParams.getOrderDto().getOrderNumber(),
						noteCustomParams.getOrderDto().getOrderCode(), companyCode, idOperation);
		CreditNoteDto creditNote = (CreditNoteDto) responseSearchCreditNote.getData();

		ResponseModel responseInvoice = paymentJDEServicePort.searchInvoiceRecordsByOrderAndCompanyCode(
				noteCustomParams.getOrderDto(), orderConfigurationDto, companyCode, idOperation);
		List<InvoiceRecordDto> invoiceRecordList = (List<InvoiceRecordDto>) responseInvoice.getData();

		String invoiceRecords = creditNoteValidation.validInvoiceRecordAndAvailability(noteCustomParams.getOrderDto(),
				invoiceRecordList);

		if (!invoiceRecords.isEmpty()) {
			LOG.warn(String.format("%s ORDER WHITOUT INVOICE RECORDS", idOperation));
			throw new ValidationError(invoiceRecords);
		}


		PaymentOrderJDE paymentOrderJDE = generateDataToSendJDEService(noteCustomParams, orderConfigurationDto,
				paymentDto, folio);  //TODO: Fix method

		paymentJDEServicePort.resendNotesToQueue(paymentOrderJDE, "FCAL", idOperation);
        return responseOrderConfig;*/
        return null;
    }

	/**
	 * Método para la pre-aprobación de creación de notas de crédito por medio de
	 * correo y código. Solo los roles operativos de SUPERVISION_II, SUPERVISION_I y
	 * MANAGEMENT con un código válido, podran ejecutar el proceso como exitoso.
	 * 
	 * @param noteCustomParams       objeto con parámetros de operación
	 * @param authorizationParmasDto datos para el proceso de autorización
	 * @param companyCode            código de compañía
	 * @param idOperation            identificador de traza de operación
	 * 
	 * @return objeto ResponseModel con el resultado de las validaciones, true en
	 *         caso de ser aprobada o false en caso de no ser aprobado el proceso
	 */
	@Override
	public ResponseModel aproveCreditNoteByParamsAndCompanyCode(CreditNoteCustomParams noteCustomParams,
			AuthorizationParmasDto authorizationParmasDto, String companyCode, String idOperation) {

		ResponseModel responseOrderConfig = orderConfigurationPersistencePort
				.viewOrderConfigurationDetailByOrderCodeAndCompanyCode(authorizationParmasDto.getOrderCode(),
						companyCode, idOperation);
		OrderConfigurationDto orderConfigurationDto = (OrderConfigurationDto) responseOrderConfig.getData();

		String validConfiguration = creditNoteValidation.validOrderConfiguration(orderConfigurationDto);
		if (!validConfiguration.isEmpty()) {
			LOG.warn(String.format("%s BAD PARAM BY ORDER CONFIGURATION", idOperation));
			throw new ValidationError(validConfiguration);
		}

		ResponseModel responseSearchOrder = orderPersistencePort.vieworderDetailByOrderNumber(
				authorizationParmasDto.getOrderNumber(), authorizationParmasDto.getOrderCode(), companyCode,
				idOperation);
		OrderDto originalOrder = (OrderDto) responseSearchOrder.getData();

		if (originalOrder == null) {
			LOG.warn(String.format("%s ORDER NOT FOUND", idOperation));
			throw new ValidationError("ORDER NOT FOUND");
		}

		ResponseModel responseUserConfiguration = orderPersistencePort.getUserConfigurationByBranchAndEmail(
				originalOrder.getBranchCode(), authorizationParmasDto.getEmployeeEmail(), companyCode, idOperation);
		EmployeeDto employeeDto = (EmployeeDto) responseUserConfiguration.getData();

		boolean aprobeProcess = authorizationParmasDto.getAuthorizationCode()
				.equals(employeeDto.getUserConfiguration().getAuthorizationCode());

		if (!aprobeProcess)
			throw new ValidationError(INVALID_AUTHORIZATION_CODE);

		OrderHistoryDto orderHistoryDto = orderHistoryGenerator.generateOrderHistory(originalOrder,
				ActionOrder.APPROVE_CREDIT_NOTE.toString(), employeeDto);
		orderPersistencePort.saveRecordInOrderHistory(orderHistoryDto, companyCode, idOperation);

		return new ResponseModel(aprobeProcess);
	}

	/**
	 * Método privado para el acondicionamiento de los datos operativos de las notas
	 * de crédito para su envío al sistema JDE
	 * 
	 * @param noteCustomParams
	 * @param orderConfigurationDto
	 * @param paymentDto
	 * @param folio
	 * 
	 * @return PaymentOrderJDE
	 */
	private PaymentOrderJDE generateDataToSendJDEService(CreditNoteCustomParams noteCustomParams,
			OrderConfigurationDto orderConfigurationDto, PaymentDto paymentDto, BigDecimal folio, Date headerCreationDate) {

		OrderDto orderDto = noteCustomParams.getOrderDto();
		orderDto.setOrderCode(orderConfigurationDto.getCreditNoteCode());
		orderDto.setOrderNumber(folio);

		for (OrderDetailDto orderDetailDto : orderDto.getOrderDetail()) {
			orderDetailDto.setLineCodeOne(orderConfigurationDto.getStateOneCreditNote());
			orderDetailDto.setLineCodeTwo(orderConfigurationDto.getStateTwoCreditNote());
		}

		orderDto.setCreationDate(headerCreationDate);
		orderDto.setRequestDate(headerCreationDate);

		PaymentOrderJDE paymentOrderJDE = new PaymentOrderJDE();
		paymentOrderJDE.setOrder(orderDto);
		paymentOrderJDE.setPaymentDto(paymentDto);

		return paymentOrderJDE;
	}

	/**
	 * Método para la recuperación de órdenes de venta que tengan un registro de
	 * cobro para el alta de notas de crédito
	 */
	@Override
	public ResponseModel searchOrderSummaryByNumberAndCode(CreditNoteCustomParams noteCustomParams, String companyCode,
			String idOperation) {
		LOG.info(String.format("%s INIT searchOrderSummaryByNumberAndCode()", idOperation));
		ResponseModel responseOrderConfig = orderConfigurationPersistencePort
				.viewOrderConfigurationDetailByOrderCodeAndCompanyCode(noteCustomParams.getOrderCode(), companyCode,
						idOperation);
		OrderConfigurationDto orderConfigurationDto = (OrderConfigurationDto) responseOrderConfig.getData();

		String validConfiguration = creditNoteValidation.validOrderConfiguration(orderConfigurationDto);
		if (!validConfiguration.isEmpty()) {
			LOG.warn(String.format("%s BAD PARAM BY ORDER CONFIGURATION", idOperation));
			throw new ValidationError(validConfiguration);
		}

		ResponseModel responseSearchOrder = orderPersistencePort.vieworderDetailByOrderNumber(
				noteCustomParams.getOrderNumber(), noteCustomParams.getOrderCode(), companyCode, idOperation);
		OrderDto order = (OrderDto) responseSearchOrder.getData();
		String validOrderState = creditNoteValidation.validOrderStatus(order);
		if (!validOrderState.isEmpty()) {
			LOG.warn(String.format("%s BAD ORDER STATE", idOperation));
			throw new ValidationError(validOrderState);
		}

		ResponseModel responseSearchCreditNote = creditNotePersistencePort
				.searchCreditNoteByOrderNumberAndCodeAndCompanyCode(noteCustomParams.getOrderNumber(),
						noteCustomParams.getOrderCode(), companyCode, idOperation);
		CreditNoteDto creditNote = (CreditNoteDto) responseSearchCreditNote.getData();

		if (creditNote != null) {
			String validIsTotalCreditNote = creditNoteValidation.isTotalCreditNote(creditNote);
			if (!validIsTotalCreditNote.isEmpty()) {
				LOG.warn(String.format("%s TOTAL CREDIT NOTE", idOperation));
				throw new ValidationError(validIsTotalCreditNote);
			}
			OrderDto orderSummary = orderSummaryGenerator.generateOrderSummary(order, creditNote);
			return new ResponseModel(orderSummary);
		}
		return new ResponseModel(order);
	}

	/**
	 * Método para la busqueda de notas de crédito por párametros y tipo de compañía
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ResponseModel searchByParamsAndCompanyCode(CreditNoteSearchParamsDto noteSearchParamsDto, String companyCode,
			String idOperation) {
		LOG.info(String.format("%s INIT searchByParamsAndCompanyCode()", idOperation));

		ResponseModel responseUserConfiguration = orderPersistencePort.getUserConfigurationByBranchAndEmail(
				noteSearchParamsDto.getBranchCode(), noteSearchParamsDto.getEmployeeEmail(), companyCode, idOperation);
		EmployeeDto employeeDto = (EmployeeDto) responseUserConfiguration.getData();

		String validParams = creditNoteValidation.validParamsToSearchData(noteSearchParamsDto, employeeDto);
		if (!validParams.isEmpty()) {
			LOG.warn(String.format("%s BAD PARAMS TO SEACH CREDIT NOTES", idOperation));
			throw new ValidationError(validParams);
		}
		List<Long> idEmploeeList = new ArrayList<Long>();
		if (employeeDto.getRoleJob().getCode().equals(OperationalLevelEnum.OPERATIONAL.toString())) {
			LOG.info(String.format("%s USER HAVE ROLE OPERATIONAL", idOperation));
			idEmploeeList.add(employeeDto.getUserId());
			noteSearchParamsDto.setUserIdLits(idEmploeeList);
		} else {
			LOG.info(String.format("%s USER HAVE ROLE SUPERVISION", idOperation));
			idEmploeeList.add(employeeDto.getUserId());
			if (!(employeeDto.getEmployees() == null ? true : (employeeDto.getEmployees().isEmpty() ? true : false))) {
				for (EmployeeDto employee : employeeDto.getEmployees()) {
					LOG.info(String.format("%s AD ID EMPLOYEE ", idOperation));
					idEmploeeList.add(employee.getUserId());
				}
			}
			noteSearchParamsDto.setUserIdLits(idEmploeeList);
		}

		List<CreditNoteSummary> creditNoteSummaryList = new ArrayList<>();
		LOG.info(String.format("%s CALL creditNotePersistencePort", idOperation));
		ResponseModel searchCreditNote = creditNotePersistencePort
				.searchCreditNoteListByParamsAndCompanyCode(noteSearchParamsDto, companyCode, idOperation);
		LOG.info(String.format("%s CALL VALID DATA", idOperation));
		if (searchCreditNote.getData() != null) {
			creditNoteSummaryList = (List<CreditNoteSummary>) searchCreditNote.getData();
		}
		return new ResponseModel(creditNoteSummaryList);
	}

	@Override
	public ResponseModel obtainCreditNoteSummaryByNumberAndCode(CreditNoteCustomParams noteCustomParams,
			String companyCode, String idOperation) {

		LOG.info(String.format("%s INIT obtainCreditNoteSummaryByNumberAndCode()", idOperation));
		ResponseModel responseSearchCreditNote = creditNotePersistencePort
				.searchCreditNoteHeaderByFolioAndCodeAndCompanyCode(noteCustomParams.getFolio(),
						noteCustomParams.getCreditNoteCode(), companyCode, idOperation);
		CreditNoteHeaderDto creditNoteHeaderDto = (CreditNoteHeaderDto) responseSearchCreditNote.getData();

		if (creditNoteHeaderDto == null) {
			LOG.warn(String.format("%s NOT FOUNT", idOperation));
			throw new ValidationError("CREDIT NOTE " + noteCustomParams.getFolio().longValue() + " NOT FOUND");
		}

		CreditNoteSummary creditNoteSummary = creditNoteGenerator.generateCreditNoteSummary(creditNoteHeaderDto);

		return new ResponseModel(creditNoteSummary);
	}

	@Override
	public ResponseModel viewDetailByCodeAndFolioAndCompanyCode(BigDecimal folio, String creditNoteCode,
			String companyCode, String idOperation) {

		LOG.info(String.format("%s INIT viewDetailByCodeAndFolioAndCompanyCode()", idOperation));
		ResponseModel responseSearchCreditNote = creditNotePersistencePort
				.searchCreditNoteHeaderByFolioAndCodeAndCompanyCode(folio, creditNoteCode, companyCode, idOperation);
		CreditNoteHeaderDto creditNoteHeaderDto = (CreditNoteHeaderDto) responseSearchCreditNote.getData();

		if (creditNoteHeaderDto == null) {
			LOG.warn(String.format("%s NOT FOUNT", idOperation));
			throw new ValidationError("CREDIT NOTE " + folio.longValue() + " NOT FOUND");
		}

		return new ResponseModel(creditNoteHeaderDto);
	}

	@Override
	public ResponseModel generateTicketByFolioAndCompanyCode(CreditNoteSearchParamsDto noteSearchParamsDto,
			String companyCode, String idOperation) {

		LOG.info(String.format("%s INIT generateTicketByFolioAndCompanyCode()", idOperation));

		ResponseModel responseSearchCreditNoteHeader = creditNotePersistencePort
				.searchCreditNoteHeaderByFolioAndCodeAndCompanyCode(noteSearchParamsDto.getFolio(),
						noteSearchParamsDto.getCreditNoteCode(), companyCode, idOperation);
		CreditNoteHeaderDto creditNoteHeaderDto = (CreditNoteHeaderDto) responseSearchCreditNoteHeader.getData();

		if (creditNoteHeaderDto == null) {
			LOG.warn(String.format("%s NOT FOUNT", idOperation));
			throw new ValidationError("CREDIT NOTE " + noteSearchParamsDto.getFolio().longValue() + " NOT FOUND");
		}

		ResponseModel responseSearchCreditNote = creditNotePersistencePort
				.searchCreditNoteByIdAndCompanyCode(creditNoteHeaderDto.getCreditNoteId(), companyCode, idOperation);
		CreditNoteDto creditNote = (CreditNoteDto) responseSearchCreditNote.getData();

		ResponseModel responseUserInfo = orderPersistencePort.getPersonalUserConfigurationByUserIdAndCompanyCode(
				creditNoteHeaderDto.getSaleEmployeeId(), companyCode, idOperation);
		UserDto userDto = (UserDto) responseUserInfo.getData();

		ResponseModel responseBranch = orderPersistencePort.getBranchConfigurationByBranchCodeAndCompanyCode(
				noteSearchParamsDto.getBranchCode(), companyCode, idOperation);
		BranchDto branchDto = (BranchDto) responseBranch.getData();

		CreditNoteTickteDto creditNoteTickte = creditNoteGenerator.generateTicketModel(userDto, creditNoteHeaderDto,
				creditNote, branchDto);

		LOG.info(creditNoteTickte.toString());
		ResponseModel generateTicket = creditNotePersistencePort.generateTicketByCompanyCode(creditNoteTickte,
				companyCode, idOperation);

		if (!creditNoteHeaderDto.getIsPrinted())
			creditNotePersistencePort.updatePrintStatusByIdAndCompanyCode(creditNoteHeaderDto.getId(), true,
					companyCode, idOperation);

		return generateTicket;
	}

}