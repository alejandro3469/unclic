package mx.com.endtoend.domain.orders.factories.orderBusiness.saleCash;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.com.endtoend.domain.commons.constants.StatusOrder;
import mx.com.endtoend.domain.company.dto.CompanyDto;
import mx.com.endtoend.domain.company.ports.spi.CompanyPersistencePort;
import mx.com.endtoend.domain.orders.dto.CustomInterfaceOrderParams;
import mx.com.endtoend.domain.orders.factories.OrderBusinessInterface;
import mx.com.endtoend.domain.orders.ports.OrderPersistencePort;
import mx.com.endtoend.domain.orders.services.OrderUtilityService;
import mx.com.endtoend.domain.userConfigurations.dto.EmployeeDto;
import mx.com.endtoend.domain.userConfigurations.ports.spi.UserConfigurationPersistencePort;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.ValidationError;
import mx.com.endtoend.infrastructure.services.jde.orders.common.serviceport.OrderJdeServicePort;
import mx.com.endtoend.smart.bussiness.functions.orders.OrderMathOperationService;
import mx.com.endtoend.smart.bussiness.model.catalogue.dto.StatusDto;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;
import mx.com.endtoend.smart.bussiness.model.orders.dto.OrderConfigurationDto;
import mx.com.endtoend.smart.bussiness.model.orders.dto.OrderDto;

public class SaleCashMethodOne implements OrderBusinessInterface {

	private static final String DISCOUNT_NOT_ALLOWED = "DISCOUNT NOT ALLOWED";

	private OrderJdeServicePort orderJdeServicePort;

	private OrderPersistencePort orderPersistencePort;

	private UserConfigurationPersistencePort userConfigurationPersistencePort;

	private CompanyPersistencePort companyPersistencePort;

	private String companyCode;

	private String idOperation;

	public SaleCashMethodOne(CustomInterfaceOrderParams customInterfaceOrderParams) {
		this.orderJdeServicePort = customInterfaceOrderParams.getOrderJdeServicePort();
		this.orderPersistencePort = customInterfaceOrderParams.getOrderPersistencePort();
		this.userConfigurationPersistencePort = customInterfaceOrderParams.getUserConfigurationPersistencePort();
		this.companyPersistencePort = customInterfaceOrderParams.getCompanyPersistencePort();
		this.companyCode = customInterfaceOrderParams.getCompanyCode();
		this.idOperation = customInterfaceOrderParams.getIdOperation();
	}

	private final static Logger LOG = LoggerFactory.getLogger(SaleCashMethodOne.class);

	private ValidationToSaleCashOrder validationToSaleCashOrder = new ValidationToSaleCashOrder();
	private OrderUtilityService orderUtilityService = new OrderUtilityService();
	private OrderMathOperationService orderMathOperationService = new OrderMathOperationService();

	@Override
	public OrderDto createOrder(OrderDto orderDto) {

		LOG.info("{} SYSTEM DATA COLLECTION", idOperation);

		String orderCode = orderDto.getOrderCode();
		String employeeEmail = orderDto.getEmployeeEmail();
		String branchCode = orderDto.getBranchCode();

		CompanyDto companyDto = getValidatedConfiguration(
				() -> companyPersistencePort.findByCode(companyCode, idOperation),
				"COMPANY CONFIGURATION INCOMPLETE. CONTACT YOUR ADMINISTRATOR");

		OrderConfigurationDto orderConfigurationDto = getValidatedConfiguration(() -> orderPersistencePort
				.getOrderConfigurationByOrderCodeAndCompanyCode(orderCode, companyCode, idOperation),
				"ORDER CONFIGUATION INCOMPLETE. CONTACT YOUR ADMINISTRATOR");

		EmployeeDto employeeDto = getValidatedConfiguration(
				() -> userConfigurationPersistencePort.findUserConfigurationByEmailAndBranchCode(employeeEmail,
						companyCode, branchCode, idOperation),
				"EMPLOYEE CONFIGUATION INCOMPLETE. CONTACT YOUR ADMINISTRATOR");

		String statusCode = StatusOrder.CREATE_SALE_ORDER.getValue();
		StatusDto statusDto = getValidatedConfiguration(
				() -> orderPersistencePort.getStatusByCode(companyCode, branchCode, statusCode, idOperation),
				"STATUS CONFIGUATION INCOMPLETE. CONTACT YOUR ADMINISTRATOR");

		String validations = validationToSaleCashOrder.validParamsMethodOne(orderPersistencePort, orderJdeServicePort,
				orderConfigurationDto, orderDto, idOperation, companyCode, branchCode);
		if (!validations.isEmpty())
			throw new ValidationError(validations);

		LOG.info("{} RECALCULATION OF TOTALS", idOperation);
		orderDto = orderMathOperationService.getTotalOrderValue(orderDto, idOperation);

		// Validar totales con BigDecimal según reglas SAT
		validateOrderTotals(orderDto);

		if (orderDto.getIsRetentionOrder() && !orderDto.getIsConverted()) {
			LOG.warn("{} ORDER RETENTION DUE TO DISCOUNT NOT ALLOWED", idOperation);
			throw new ValidationError(DISCOUNT_NOT_ALLOWED);
		}

		LOG.info("{} GET ORDER NUMBER", idOperation);
		BigDecimal orderNumber = (BigDecimal) orderJdeServicePort.getConsecutiveOrderNumberByCompanyCode(companyCode,
				branchCode, companyDto.getCompanyNumber(), idOperation, orderCode).getData();

		orderDto = orderUtilityService.setOrderConfigurationOnCreate(orderDto, employeeDto, orderConfigurationDto,
				idOperation);
		orderDto = orderUtilityService.setOperativeDataOnCreate(orderDto, companyDto, employeeDto, statusDto,
				orderNumber);

		OrderDto orderDtoCreated = (OrderDto) orderPersistencePort.createOrder(orderDto, companyCode, idOperation)
				.getData();

		return orderDtoCreated;
	}

	@Override
	public OrderDto updateOrder(OrderDto orderDto, OrderDto orderSaved) {

		LOG.info("{} VALID STATUS ORDER", idOperation);
		StatusDto statusSaved = orderSaved.getStatus();
		if (statusSaved.getCode().equals(StatusOrder.PARTIAL_PAYMENT.getValue())
				|| statusSaved.getCode().equals(StatusOrder.FULL_PAYMENT.getValue())) {
			orderPersistencePort.cancelUpdateOrder(orderSaved.getOrderNumber(), orderDto.getOrderCode(),
					orderSaved.getBranchCode(), companyCode, idOperation).getData();
			throw new ValidationError("ORDER HAS PAYMENT");
		}

		LOG.info("{} SYSTEM DATA COLLECTION", idOperation);

		String orderCode = orderDto.getOrderCode();
		String employeeEmail = orderDto.getEmployeeEmail();
		String branchCode = orderDto.getBranchCode();

		OrderConfigurationDto orderConfigurationDto = getValidatedConfiguration(() -> orderPersistencePort
				.getOrderConfigurationByOrderCodeAndCompanyCode(orderCode, companyCode, idOperation),
				"ORDER CONFIGUATION INCOMPLETE. CONTACT YOUR ADMINISTRATOR");

		EmployeeDto employeeDto = getValidatedConfiguration(
				() -> userConfigurationPersistencePort.findUserConfigurationByEmailAndBranchCode(employeeEmail,
						companyCode, branchCode, idOperation),
				"EMPLOYEE CONFIGUATION INCOMPLETE. CONTACT YOUR ADMINISTRATOR");

		String statusCode = StatusOrder.CREATE_SALE_ORDER.getValue();
		StatusDto statusDto = getValidatedConfiguration(
				() -> orderPersistencePort.getStatusByCode(companyCode, branchCode, statusCode, idOperation),
				"STATUS CONFIGUATION INCOMPLETE. CONTACT YOUR ADMINISTRATOR");
		orderDto.setStatus(statusDto);

		String validations = validationToSaleCashOrder.validParamsMethodOne(orderPersistencePort, orderJdeServicePort,
				orderConfigurationDto, orderDto, idOperation, companyCode, branchCode);
		if (!validations.isEmpty()) {
			orderPersistencePort.cancelUpdateOrder(orderDto.getOrderNumber(), orderDto.getOrderCode(),
					orderDto.getBranchCode(), companyCode, idOperation).getData();
			throw new ValidationError(validations);
		}

		LOG.info("{} RECALCULATION OF TOTALS", idOperation);
		orderDto = orderMathOperationService.getTotalOrderValue(orderDto, idOperation);

		// Validar totales con BigDecimal según reglas SAT
		validateOrderTotals(orderDto);

		if (orderDto.getIsRetentionOrder()) {
			LOG.warn("{} ORDER RETENTION DUE TO DISCOUNT NOT ALLOWED", idOperation);
			throw new ValidationError(DISCOUNT_NOT_ALLOWED);
		}
		
		orderDto = orderUtilityService.setOrderConfigurationOnCreate(orderDto, employeeDto, orderConfigurationDto,
				idOperation);

		OrderDto orderDtoUpdated = (OrderDto) orderPersistencePort.createOrder(orderDto, companyCode, idOperation)
				.getData();

		return orderDtoUpdated;
	}

	/**
	 * Valida que los totales de la orden cumplan con las reglas de precisión SAT
	 * @param orderDto Orden a validar
	 * @throws ValidationError Si los totales no son válidos
	 */
	private void validateOrderTotals(OrderDto orderDto) {
		LOG.info("{} VALIDATING ORDER TOTALS WITH BIGDECIMAL PRECISION", idOperation);
		
		// Validar que los totales no sean nulos
		if (orderDto.getSubTotal() == null) {
			throw new ValidationError("ORDER SUBTOTAL CANNOT BE NULL");
		}
		if (orderDto.getIvaTotal() == null) {
			throw new ValidationError("ORDER IVA TOTAL CANNOT BE NULL");
		}
		if (orderDto.getOrderTotal() == null) {
			throw new ValidationError("ORDER TOTAL CANNOT BE NULL");
		}
		
		// Validar que los totales sean positivos
		if (orderDto.getSubTotal().compareTo(BigDecimal.ZERO) <= 0) {
			throw new ValidationError("ORDER SUBTOTAL MUST BE GREATER THAN ZERO");
		}
		if (orderDto.getIvaTotal().compareTo(BigDecimal.ZERO) < 0) {
			throw new ValidationError("ORDER IVA TOTAL CANNOT BE NEGATIVE");
		}
		if (orderDto.getOrderTotal().compareTo(BigDecimal.ZERO) <= 0) {
			throw new ValidationError("ORDER TOTAL MUST BE GREATER THAN ZERO");
		}
		
		// Validar precisión decimal (máximo 2 decimales según SAT)
		if (orderDto.getSubTotal().scale() > 2) {
			throw new ValidationError("ORDER SUBTOTAL CANNOT HAVE MORE THAN 2 DECIMAL PLACES");
		}
		if (orderDto.getIvaTotal().scale() > 2) {
			throw new ValidationError("ORDER IVA TOTAL CANNOT HAVE MORE THAN 2 DECIMAL PLACES");
		}
		if (orderDto.getOrderTotal().scale() > 2) {
			throw new ValidationError("ORDER TOTAL CANNOT HAVE MORE THAN 2 DECIMAL PLACES");
		}
		
		// Validar consistencia matemática: orderTotal = subTotal + ivaTotal
		BigDecimal calculatedTotal = orderDto.getSubTotal().add(orderDto.getIvaTotal());
		BigDecimal roundedCalculatedTotal = calculatedTotal.setScale(2, RoundingMode.HALF_UP);
		BigDecimal roundedOrderTotal = orderDto.getOrderTotal().setScale(2, RoundingMode.HALF_UP);
		
		if (roundedCalculatedTotal.compareTo(roundedOrderTotal) != 0) {
			LOG.warn("{} ORDER TOTAL INCONSISTENCY: Calculated={}, Stored={}", 
					idOperation, roundedCalculatedTotal, roundedOrderTotal);
			throw new ValidationError("ORDER TOTAL INCONSISTENCY: CALCULATED TOTAL DOES NOT MATCH STORED TOTAL");
		}
		
		LOG.info("{} ORDER TOTALS VALIDATION SUCCESSFUL", idOperation);
	}

	@SuppressWarnings("unchecked")
	private <T> T getValidatedConfiguration(Supplier<ResponseModel> responseSupplier, String errorMessage) {
		ResponseModel response = responseSupplier.get();
		T data = (T) response.getData();
		if (data == null) {
			throw new ValidationError(errorMessage);
		}
		return data;
	}
}