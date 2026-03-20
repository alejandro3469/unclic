package mx.com.endtoend.domain.orders.factories.orderBusiness.saleHomeDelivery;

import java.math.BigDecimal;
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

public class SaleHomeDeliveryMethodOne implements OrderBusinessInterface {

	private OrderJdeServicePort orderJdeServicePort;

	private OrderPersistencePort orderPersistencePort;

	private UserConfigurationPersistencePort userConfigurationPersistencePort;

	private CompanyPersistencePort companyPersistencePort;

	private String companyCode;

	private String idOperation;

	public SaleHomeDeliveryMethodOne(CustomInterfaceOrderParams customInterfaceOrderParams) {
		this.orderJdeServicePort = customInterfaceOrderParams.getOrderJdeServicePort();
		this.orderPersistencePort = customInterfaceOrderParams.getOrderPersistencePort();
		this.userConfigurationPersistencePort = customInterfaceOrderParams.getUserConfigurationPersistencePort();
		this.companyPersistencePort = customInterfaceOrderParams.getCompanyPersistencePort();
		this.companyCode = customInterfaceOrderParams.getCompanyCode();
		this.idOperation = customInterfaceOrderParams.getIdOperation();
	}

	private final static Logger LOG = LoggerFactory.getLogger(SaleHomeDeliveryMethodOne.class);

	private ValidationToSaleHomeDelivery validationToSaleHomeDelivery = new ValidationToSaleHomeDelivery();
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

		String validations = validationToSaleHomeDelivery.validParamsMethodOne(orderDto, orderConfigurationDto,
				idOperation);
		if (!validations.isEmpty())
			throw new ValidationError(validations);

		LOG.info("{} RECALCULATION OF TOTALS", idOperation);
		orderDto = orderMathOperationService.getTotalOrderValue(orderDto, idOperation);

		String validProductAvailability = validationToSaleHomeDelivery.validAvailabilityByArticleAnStorageType(
				orderPersistencePort, orderJdeServicePort, orderDto.getOrderDetail(), idOperation, companyCode,
				branchCode);
		if (!validProductAvailability.isEmpty())
			throw new ValidationError(validProductAvailability);

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

		LOG.info("{} VALID ORDER RETENTION", idOperation);
		if (orderSaved.getIsRetentionOrder()) {
			orderPersistencePort.cancelUpdateOrder(orderSaved.getOrderNumber(), orderDto.getOrderCode(),
					orderSaved.getBranchCode(), companyCode, idOperation).getData();
			throw new ValidationError("ORDER RETAINED");
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
		
		String validations = validationToSaleHomeDelivery.validParamsMethodOne(orderDto, orderConfigurationDto,
				idOperation);
		if (!validations.isEmpty()) {
			orderPersistencePort.cancelUpdateOrder(orderSaved.getOrderNumber(), orderDto.getOrderCode(),
					orderSaved.getBranchCode(), companyCode, idOperation).getData();
			throw new ValidationError(validations);
		}

		LOG.info("{} RECALCULATION OF TOTALS", idOperation);
		orderDto = orderMathOperationService.getTotalOrderValue(orderDto, idOperation);

		String validProductAvailability = validationToSaleHomeDelivery.validAvailabilityByArticleAnStorageType(
				orderPersistencePort, orderJdeServicePort, orderDto.getOrderDetail(), idOperation, companyCode,
				branchCode);
		if (!validProductAvailability.isEmpty()) {
			orderPersistencePort.cancelUpdateOrder(orderSaved.getOrderNumber(), orderDto.getOrderCode(),
					orderSaved.getBranchCode(), companyCode, idOperation).getData();
			throw new ValidationError(validProductAvailability);
		}
			
		orderDto = orderUtilityService.setOrderConfigurationOnCreate(orderDto, employeeDto, orderConfigurationDto,
				idOperation);
		
		OrderDto orderDtoUpdated = (OrderDto) orderPersistencePort.createOrder(orderDto, companyCode, idOperation)
				.getData();

		return orderDtoUpdated;
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
