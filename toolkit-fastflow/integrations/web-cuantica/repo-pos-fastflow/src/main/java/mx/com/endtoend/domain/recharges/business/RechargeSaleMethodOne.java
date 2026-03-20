package mx.com.endtoend.domain.recharges.business;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.com.endtoend.domain.accountingRecord.dto.AccountingRecordDto;
import mx.com.endtoend.domain.accountingRecord.ports.AccountingRecordPersistencePort;
import mx.com.endtoend.domain.accountingRecord.services.GenerateAccountingRecordService;
import mx.com.endtoend.domain.branch.dto.BranchDto;
import mx.com.endtoend.domain.cash.creditCardReference.ports.CreditCardConfigurationPersistencePort;
import mx.com.endtoend.domain.clients.dto.ClientIdDto;
import mx.com.endtoend.domain.clients.ports.spi.ClientPersistencePort;
import mx.com.endtoend.domain.commons.constants.ActionOrder;
import mx.com.endtoend.domain.commons.constants.StatusOrder;
import mx.com.endtoend.domain.company.dto.CompanyDto;
import mx.com.endtoend.domain.company.ports.spi.CompanyPersistencePort;
import mx.com.endtoend.domain.openings.dto.OpeningOperationDto;
import mx.com.endtoend.domain.orders.ports.OrderPersistencePort;
import mx.com.endtoend.domain.payments.business.validations.GenericPaymentValidation;
import mx.com.endtoend.domain.payments.dto.PaymentCustomParams;
import mx.com.endtoend.domain.payments.ports.PaymentPersistencePort;
import mx.com.endtoend.domain.payments.services.PaymentMathService;
import mx.com.endtoend.domain.recharges.business.validations.RechargeSaleValidation;
import mx.com.endtoend.domain.recharges.dto.CompanyPhoneDto;
import mx.com.endtoend.domain.recharges.dto.CompanyRechargeConfigurationDto;
import mx.com.endtoend.domain.recharges.dto.CustomRechargeSaleParams;
import mx.com.endtoend.domain.recharges.dto.RechargeRequestDto;
import mx.com.endtoend.domain.recharges.dto.RechargeSaleDto;
import mx.com.endtoend.domain.recharges.dto.RechargeTickteDto;
import mx.com.endtoend.domain.recharges.ports.RechargeSalePersistencePort;
import mx.com.endtoend.domain.userConfigurations.dto.EmployeeDto;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.SemiFullFunction;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.ValidationError;
import mx.com.endtoend.infrastructure.services.jde.orders.common.serviceport.OrderJdeServicePort;
import mx.com.endtoend.infrastructure.services.jde.payments.common.serviceport.PaymentJDEServicePort;
import mx.com.endtoend.infrastructure.services.selia.models.SeliaRequest;
import mx.com.endtoend.infrastructure.services.selia.models.SeliaResponse;
import mx.com.endtoend.smart.bussiness.functions.orders.OrderMathOperationService;
import mx.com.endtoend.smart.bussiness.model.catalogue.dto.StatusDto;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;
import mx.com.endtoend.smart.bussiness.model.orders.dto.OrderConfigurationDto;
import mx.com.endtoend.smart.bussiness.model.orders.dto.OrderDto;
import mx.com.endtoend.smart.bussiness.model.orders.dto.OrderHistoryDto;
import mx.com.endtoend.smart.bussiness.model.payments.PaymentDto;
import mx.com.endtoend.smart.bussiness.model.payments.PaymentOrderJDE;

public class RechargeSaleMethodOne implements RechargeSaleInterface {

	private String companyCode;

	private String idOperation;

	private ClientPersistencePort clientPersistencePort;

	private AccountingRecordPersistencePort accountingRecordPersistencePort;

	private PaymentJDEServicePort paymentJDEServicePort;

	private RechargeSalePersistencePort rechargeSalePersistencePort;

	private OrderJdeServicePort orderJdeServicePort;

	private PaymentPersistencePort paymentPersistencePort;

	private OrderPersistencePort orderPersistencePort;

	private CompanyPersistencePort companyPersistencePort;

	private CreditCardConfigurationPersistencePort creditCardConfigurationPersistencePort;

	public RechargeSaleMethodOne(CustomRechargeSaleParams customRechargeSaleParams) {
		this.companyCode = customRechargeSaleParams.getCompanyCode();
		this.idOperation = customRechargeSaleParams.getIdOperation();
		this.accountingRecordPersistencePort = customRechargeSaleParams.getAccountingRecordPersistencePort();
		this.paymentJDEServicePort = customRechargeSaleParams.getPaymentJDEServicePort();
		this.rechargeSalePersistencePort = customRechargeSaleParams.getRechargeSalePersistencePort();
		this.orderJdeServicePort = customRechargeSaleParams.getOrderJdeServicePort();
		this.paymentPersistencePort = customRechargeSaleParams.getPaymentPersistencePort();
		this.orderPersistencePort = customRechargeSaleParams.getOrderPersistencePort();
		this.companyPersistencePort = customRechargeSaleParams.getCompanyPersistencePort();
		this.clientPersistencePort = customRechargeSaleParams.getClientPersistencePort();
		this.creditCardConfigurationPersistencePort = customRechargeSaleParams
				.getCreditCardConfigurationPersistencePort();
	}

	private OrderConfigurationDto orderConfigurationDto = null;
	private EmployeeDto employeeDto = null;
	private OpeningOperationDto openingOperation = null;
	private CompanyDto companyDto = null;
	private BigDecimal orderNumber = null;
	private CompanyRechargeConfigurationDto companyRechargeConfiguration = null;
	private StatusDto statusDto = null;
	private ClientIdDto clientDto = null;

	String statusCode = StatusOrder.FULL_PAYMENT.getValue();

	private final Logger LOG = LoggerFactory.getLogger(RechargeSaleMethodOne.class);

	private RechargeSaleValidation rechargeSaleValidation = new RechargeSaleValidation();
	private RechargeGeneratorService rechargeGeneratorService = new RechargeGeneratorService();
	private OrderMathOperationService orderMathOperationService = new OrderMathOperationService();
	private GenericPaymentValidation paymentValidation = new GenericPaymentValidation();
	private PaymentMathService paymentMathService = new PaymentMathService();
	private GenerateAccountingRecordService accountingRecordService = new GenerateAccountingRecordService();

	@Override
	public ResponseModel createRechargeSale(RechargeSaleDto rechargeSaleDto) {
		validOperativeData(rechargeSaleDto);
		OrderDto orderDto = rechargeGeneratorService.generateOrderDtoByRecharge(orderConfigurationDto, employeeDto,
				companyDto, clientDto, statusDto, orderNumber, rechargeSaleDto);
		orderDto = orderMathOperationService.getTotalOrderValue(orderDto, idOperation);
		orderDto.setOrderTotal(rechargeSaleDto.getCompanyRecharge().getAmount());
		orderDto.setPendingPayment(rechargeSaleDto.getCompanyRecharge().getAmount());
		PaymentDto paymentDto = rechargeGeneratorService.generatePaymentDtoByRecharge(openingOperation, orderDto,
				rechargeSaleDto);
		PaymentCustomParams paymentCustomParams = new PaymentCustomParams(paymentJDEServicePort,
				accountingRecordPersistencePort, null, companyPersistencePort, creditCardConfigurationPersistencePort,
				null,companyCode, idOperation);
		PaymentDto paymentProcess = paymentMathService.processPayment(paymentCustomParams, paymentDto);
		String validPendingPayment = paymentValidation.validPendingPayment(paymentProcess.getPendingPayment().doubleValue());
		if (!validPendingPayment.isEmpty()) {
			LOG.warn(String.format("%s BAD VALIDATIONS: %s", idOperation, validPendingPayment));
			throw new ValidationError(validPendingPayment);
		}
		SeliaRequest seliaRequest = generateSeliaRequest(rechargeSaleDto);
		ResponseModel responseRecharge = rechargeSalePersistencePort
				.sendRechargeRequestToSELIAByCompanyCode(seliaRequest, companyCode, idOperation);
		SeliaResponse seliaResponse = (SeliaResponse) responseRecharge.getData();
		if (!seliaResponse.getResponseCode().equals("0")) {
			LOG.warn(String.format("%s ERROR IN EXTERNAL SERVICE: %s", idOperation,
					seliaResponse.getResponseDescription()));
			throw new ValidationError("ERROR IN EXTERNAL SERVICE: " + seliaResponse.getResponseDescription());
		}
		RechargeRequestDto rechargeRequestDto = new RechargeRequestDto(orderDto.getOrderNumber(),
				orderDto.getOrderCode(), rechargeSaleDto, seliaResponse);
		rechargeSalePersistencePort.saveRechargeRequestByCompanyCode(rechargeRequestDto, companyCode, idOperation);
		LOG.info("SAVE RECHARGE REQUEST");
		Long batchFolio = (Long) paymentJDEServicePort.getBathcFolioByCompanyCode("47", companyCode, idOperation)
				.getData();
		orderDto.setBatchFolio(batchFolio);
		OrderDto orderDtoCreated = (OrderDto) orderPersistencePort.createOrder(orderDto, companyCode, idOperation)
				.getData();
		paymentProcess = (PaymentDto) paymentPersistencePort.createPayment(paymentDto, companyCode, idOperation)
				.getData();

		String errorMessage = "";
		createAccountingRecords(paymentProcess, openingOperation, employeeDto.getUserId(), companyCode, errorMessage,
				idOperation);
		saveChangeInOrderHistory(paymentPersistencePort, companyCode, idOperation, employeeDto, orderDtoCreated,
				paymentProcess);

		PaymentOrderJDE paymentOrderJDE = new PaymentOrderJDE(orderDto, paymentProcess);
		paymentJDEServicePort.sendOrderToSave(paymentOrderJDE, companyCode, idOperation);
		if (!errorMessage.isEmpty()) {
			LOG.warn(String.format("%s ERROR IN PROCESS: %s", idOperation, errorMessage));
			throw new SemiFullFunction(errorMessage, paymentProcess);
		}
		return new ResponseModel(paymentProcess);
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

	private void saveChangeInOrderHistory(PaymentPersistencePort paymentPersistencePort, String companyCode,
			String idOperation, EmployeeDto employeeDto, OrderDto orderDto, PaymentDto paymentProcess) {
		LOG.info(String.format("%s NEW RECORD IN ORDER HISTORY", idOperation));
		OrderHistoryDto orderHistoryDto = new OrderHistoryDto(employeeDto.getBranchCode(),
				paymentProcess.getOrderCode(), paymentProcess.getOrderNumber(), new Date(),
				ActionOrder.UPDATE_PYMENT.name(), paymentProcess.getEmployeEmail(), employeeDto.getUserNumber(), "",
				orderDto.getIsRetentionOrder());
		paymentPersistencePort.saveRecordInOrderHistory(orderHistoryDto, companyCode, idOperation);
	}

	/**
	 * Método que gestiona las validaciones de los datos de entrada y del sistema
	 * para la generación de recargas de tiempo aire
	 * 
	 * @param rechargeSaleDto
	 */
	private void validOperativeData(RechargeSaleDto rechargeSaleDto) {
		LOG.info(String.format("%s INIT VALIDATION INPUT PARAMS", idOperation));
		String validInputData = rechargeSaleValidation.validOperativeDataToCreate(rechargeSaleDto);
		if (!validInputData.isEmpty()) {
			LOG.info(String.format("%s BAD INPUT PARAMS: %s", idOperation, validInputData));
			throw new ValidationError(validInputData);
		}
		LOG.info(String.format("%s INIT GENERATE SYSTEM DATA", idOperation));
		generateSystemOperativeData(rechargeSaleDto);
		LOG.info(String.format("%s INIT VALIDATION SYSTEM DATA", idOperation));
		String validSystemData = rechargeSaleValidation.validSystemOperativeDataToCreate(orderConfigurationDto,
				employeeDto, openingOperation, orderNumber);
		if (!validSystemData.isEmpty()) {
			LOG.info(String.format("%s BAD SYSTEM PARAMS: %s", idOperation, validInputData));
			throw new ValidationError(validSystemData);
		}
	}

	/**
	 * Metodo para generar el modelo de solicitud para el servicio externo de
	 * recargas
	 * 
	 * @param rechargeSaleDto
	 * @return SeliaRequest
	 */
	private SeliaRequest generateSeliaRequest(RechargeSaleDto rechargeSaleDto) {
		String companyCode = companyRechargeConfiguration.getCompanyCode();
		String originatorCode = companyRechargeConfiguration.getOriginatorCode();
		String saleCashNumber = String.valueOf(employeeDto.getUserNumber().longValue());

		LocalDateTime localDateTime = LocalDateTime.now();
		DateTimeFormatter formatterLocalDateTime = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
		String requestDate = formatterLocalDateTime.format(localDateTime);

		String orderNumber = String.valueOf(this.orderNumber.longValue());
		String generateBoucher = "N";
		String user = companyRechargeConfiguration.getUser();
		String password = companyRechargeConfiguration.getPassword();

		String companyPhone = String.valueOf(rechargeSaleDto.getCompanyRecharge().getCompanyPhoe());
		String companyPhoneCode = String.valueOf(rechargeSaleDto.getCompanyRecharge().getCompanyPhoneCode());
		String amount = String.valueOf((rechargeSaleDto.getCompanyRecharge().getAmount().longValue() * 100));
		String phoneNumber = rechargeSaleDto.getPhoneNumber();

		return new SeliaRequest(companyCode, originatorCode, saleCashNumber, requestDate, orderNumber, generateBoucher,
				user, password, companyPhone, companyPhoneCode, amount, phoneNumber);
	}

	/**
	 * Método para la recuperación de los datos operativos del sistema
	 * 
	 * @param rechargeSaleDto
	 */
	private void generateSystemOperativeData(RechargeSaleDto rechargeSaleDto) {
		ResponseModel responseOrderConfig = orderPersistencePort.getOrderConfigurationByOrderCodeAndCompanyCode(
				rechargeSaleDto.getOrderCode(), companyCode, idOperation);
		orderConfigurationDto = (OrderConfigurationDto) responseOrderConfig.getData();

		ResponseModel responseEmployee = paymentPersistencePort.getEmployeConfigurationByEmailAndCompanyCode(
				rechargeSaleDto.getEmployeeEmail(), companyCode, idOperation);
		employeeDto = (EmployeeDto) responseEmployee.getData();

		ResponseModel responseOpening = paymentPersistencePort
				.getOpeningOperationByEmailAndCompanyCode(rechargeSaleDto.getEmployeeEmail(), companyCode, idOperation);
		openingOperation = (OpeningOperationDto) responseOpening.getData();

		ResponseModel responseCompany = companyPersistencePort.findByCode(companyCode, idOperation);
		companyDto = (CompanyDto) responseCompany.getData();

		orderNumber = (BigDecimal) orderJdeServicePort.getConsecutiveOrderNumberByCompanyCode(companyCode, null,
				companyDto.getCompanyNumber(), idOperation, rechargeSaleDto.getOrderCode()).getData();

		companyRechargeConfiguration = (CompanyRechargeConfigurationDto) rechargeSalePersistencePort
				.getCompanyConfigurationByRecharge(companyCode, idOperation).getData();

		statusDto = (StatusDto) orderPersistencePort
				.getStatusByCode(companyCode, employeeDto.getBranchCode(), statusCode, idOperation).getData();

		clientDto = (ClientIdDto) clientPersistencePort.recoverClient(companyCode, 1L, idOperation).getData();

	}

	@Override
	public ResponseModel generateTicketRecharge(String orderCode, BigDecimal orderNumber) {
		LOG.info(String.format("%s INIT generateTicketRecharge", idOperation));

		ResponseModel responseRechargeRequest = rechargeSalePersistencePort
				.getRechargeRequestByOrderNumberAndCodeAndCompanyCode(orderNumber, orderCode, companyCode, idOperation);
		RechargeRequestDto rechargeRequest = (RechargeRequestDto) responseRechargeRequest.getData();
		LOG.info(rechargeRequest.toString());

		ResponseModel responseGetOrder = paymentPersistencePort.getOrderByCompanyCodeAndParams(orderNumber, orderCode,
				companyCode, idOperation);
		OrderDto orderDto = (OrderDto) responseGetOrder.getData();

		ResponseModel responseGetBranch = paymentPersistencePort
				.getBranchDetailByBranchCodeAndCompanyCode(orderDto.getBranchCode(), companyCode, idOperation);
		BranchDto branchDto = (BranchDto) responseGetBranch.getData();

		RechargeTickteDto rechargeTickteDto = new RechargeTickteDto(rechargeRequest, branchDto);

		ResponseModel responseModel = rechargeSalePersistencePort
				.generateTicketRechargeSaleByCompanmyCode(rechargeTickteDto, companyCode, idOperation);

		return responseModel;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ResponseModel getCompanyPhoneList() {
		LOG.info(String.format("%s INIT getCompanyPhoneList", idOperation));
		ResponseModel responseCompanyPhoneList = rechargeSalePersistencePort
				.getCompanyPhoneListByCompanyCode(companyCode, idOperation);
		if (responseCompanyPhoneList.getData() == null) {
			LOG.warn("EMPTY CONFIGURATION");
			return new ResponseModel(new ArrayList<>());
		}
		List<CompanyPhoneDto> companyPhoneDtoList = (List<CompanyPhoneDto>) responseCompanyPhoneList.getData();
		return new ResponseModel(companyPhoneDtoList);
	}

}
