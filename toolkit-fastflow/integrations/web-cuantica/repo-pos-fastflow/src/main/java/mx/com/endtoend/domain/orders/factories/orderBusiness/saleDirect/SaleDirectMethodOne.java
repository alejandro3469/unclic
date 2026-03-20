package mx.com.endtoend.domain.orders.factories.orderBusiness.saleDirect;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import mx.com.endtoend.smart.bussiness.model.orders.dto.*;
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
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.GlobalError;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.ValidationError;
import mx.com.endtoend.infrastructure.services.jde.orders.common.serviceport.OrderJdeServicePort;
import mx.com.endtoend.infrastructure.services.posLegacy.orders.common.serviceport.OrderPosLegacyServicePort;
import mx.com.endtoend.smart.bussiness.functions.orders.OrderMathOperationService;
import mx.com.endtoend.smart.bussiness.model.catalogue.dto.StatusDto;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

public class SaleDirectMethodOne implements OrderBusinessInterface {

	private OrderJdeServicePort orderJdeServicePort;

	private OrderPersistencePort orderPersistencePort;

	private UserConfigurationPersistencePort userConfigurationPersistencePort;

	private OrderPosLegacyServicePort orderPosLegacyServicePort;

	private CompanyPersistencePort companyPersistencePort;

	private String companyCode;

	private String idOperation;

	public SaleDirectMethodOne(CustomInterfaceOrderParams customInterfaceOrderParams) {
		this.orderJdeServicePort = customInterfaceOrderParams.getOrderJdeServicePort();
		this.orderPersistencePort = customInterfaceOrderParams.getOrderPersistencePort();
		this.userConfigurationPersistencePort = customInterfaceOrderParams.getUserConfigurationPersistencePort();
		this.companyPersistencePort = customInterfaceOrderParams.getCompanyPersistencePort();
		this.companyCode = customInterfaceOrderParams.getCompanyCode();
		this.idOperation = customInterfaceOrderParams.getIdOperation();
		this.orderPosLegacyServicePort = customInterfaceOrderParams.getOrderPosLegacyServicePort() != null
				? customInterfaceOrderParams.getOrderPosLegacyServicePort()
				: null;
	}

	private final static Logger LOG = LoggerFactory.getLogger(SaleDirectMethodOne.class);
	private String orderCodeOD = "OD";
	private ValidationToSaleDirectOrder validationToSaleDirectOrder = new ValidationToSaleDirectOrder();
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

		String validations = validationToSaleDirectOrder.validParamsMethodOne(orderDto, orderConfigurationDto,
				idOperation);
		if (!validations.isEmpty())
			throw new ValidationError(validations);

		LOG.info("{} RECALCULATION OF TOTALS", idOperation);
		orderDto = orderMathOperationService.getTotalOrderValue(orderDto, idOperation);

		// Validar totales con BigDecimal según reglas SAT
		validateOrderTotals(orderDto);

		LOG.info("{} GET ORDER NUMBER", idOperation);
		BigDecimal orderNumber = (BigDecimal) orderJdeServicePort.getConsecutiveOrderNumberByCompanyCode(companyCode,
				branchCode, companyDto.getCompanyNumber(), idOperation, orderCode).getData();

		LOG.info("{} GET ORDER NUMBER TO OD", idOperation);
		BigDecimal orderNumberOD = (BigDecimal) orderJdeServicePort.getConsecutiveOrderNumberByCompanyCode(companyCode,
				branchCode, companyDto.getCompanyNumber(), idOperation, orderCodeOD).getData();

		orderDto = orderUtilityService.setOrderConfigurationOnCreate(orderDto, employeeDto, orderConfigurationDto,
				idOperation);
		orderDto = orderUtilityService.setOperativeDataOnCreate(orderDto, companyDto, employeeDto, statusDto,
				orderNumber);

		updateLineCodeInOrderDetail(orderDto);

		LOG.info("{} START SAVE ORDER", idOperation);
		OrderDto orderDtoCreated = (OrderDto) orderPersistencePort.createOrder(orderDto, companyCode, idOperation)
				.getData();
		if (orderDtoCreated == null) {
			LOG.error("{} ERROR IN CREATE ORDER", idOperation);
			throw new GlobalError();
		}

		LOG.info("{} GENERATE OBJECT TO OD ", idOperation);
		orderDto.setOrderId(orderDtoCreated.getOrderId());

		List<SaleOrderDetailDto> saleOrderDetailDtoList = new ArrayList<SaleOrderDetailDto>();
		SaleOrderDto saleOrderDto = new SaleOrderDto();
		saleOrderDto = generateSaleOrderDto(orderDto, orderNumberOD, saleOrderDto, saleOrderDetailDtoList,
				orderDto.getOrderDetail(), idOperation);

		orderPersistencePort.createOrderOD(saleOrderDto, companyCode, idOperation).getData();

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

		String validations = validationToSaleDirectOrder.validParamsMethodOne(orderDto, orderConfigurationDto,
				idOperation);
		if (!validations.isEmpty()) {
			orderPersistencePort.cancelUpdateOrder(orderSaved.getOrderNumber(), orderDto.getOrderCode(),
					orderSaved.getBranchCode(), companyCode, idOperation).getData();
			throw new ValidationError(validations);
		}

		LOG.info("{} RECALCULATION OF TOTALS", idOperation);
		orderDto = orderMathOperationService.getTotalOrderValue(orderDto, idOperation);

		// Validar totales con BigDecimal según reglas SAT
		validateOrderTotals(orderDto);

		orderDto = orderUtilityService.setOrderConfigurationOnCreate(orderDto, employeeDto, orderConfigurationDto,
				idOperation);

		updateLineCodeInOrderDetail(orderDto);

		SaleOrderDto saleOrderDtoUpdate = (SaleOrderDto) orderPersistencePort.viewSaleOrderDetailByOrderNumber(
				orderDto.getOrderNumber(), companyCode, orderDto.getOrderCode(), idOperation).getData();

		if (saleOrderDtoUpdate == null) {
			orderPersistencePort.cancelUpdateOrder(orderSaved.getOrderNumber(), orderDto.getOrderCode(),
					orderSaved.getBranchCode(), companyCode, idOperation).getData();
			throw new ValidationError("OD ORDER NOT FOUND. CONTACT YOUR ADMINISTRATOR");
		}

		List<SaleOrderDetailDto> saleOrderDetailDtoList = new ArrayList<SaleOrderDetailDto>();
		saleOrderDtoUpdate = generateSaleOrderDto(orderDto, saleOrderDtoUpdate.getOrderNumber(), saleOrderDtoUpdate,
				saleOrderDetailDtoList, orderDto.getOrderDetail(), idOperation);

		LOG.info("{} START SAVE ORDER", idOperation);
		OrderDto orderDtoUpdated = (OrderDto) orderPersistencePort.updateOrder(orderDto, companyCode, idOperation)
				.getData();
		if (orderDtoUpdated == null) {
			LOG.error("{} ERROR IN CREATE ORDER", idOperation);
			throw new GlobalError();
		}

		orderPersistencePort.updateOrderOD(saleOrderDtoUpdate, companyCode, idOperation);

		if (orderPosLegacyServicePort != null)
			orderPosLegacyServicePort.updteSaleOD(saleOrderDtoUpdate, companyCode, idOperation);

		return orderDtoUpdated;
	}

	public SaleOrderDto generateSaleOrderDto(OrderDto orderDtoCreated, BigDecimal saleOrderNumber,
			SaleOrderDto saleOrderDto, List<SaleOrderDetailDto> saleOrderDetailDtoList,
			List<OrderDetailDto> orderDetailDtoList, String idOperation) {

		LOG.info("{} INIT generateSaleOrderDto()", idOperation);
		LOG.info("{} PARAMS: [ orderDtoCreated: {} , saleOrderNumber: {} , saleOrderDto: {} ]", idOperation,
				orderDtoCreated, saleOrderNumber, saleOrderDto);

		saleOrderDto.setOrderCode(orderCodeOD);
		saleOrderDto.setCompanyNumber(orderDtoCreated.getCompanyNumber());
		saleOrderDto.setOrderType("Orden de Compra");
		saleOrderDto.setStatus(orderDtoCreated.getStatus());
		saleOrderDto.setOrderNumber(saleOrderNumber);
		saleOrderDto.setBranchCode(orderDtoCreated.getBranchCode());
		saleOrderDto.setCurrency(orderDtoCreated.getCurrency());
		saleOrderDto.setExchangeRate(orderDtoCreated.getExchangeRate());
		saleOrderDto.setCreationDate(orderDtoCreated.getCreationDate());
		saleOrderDto.setRequestDate(orderDtoCreated.getRequestDate());
		saleOrderDto.setClient(orderDtoCreated.getClient());
		saleOrderDto.setUserNumber(orderDtoCreated.getUserNumber());
		saleOrderDto.setUsername(orderDtoCreated.getEmployeeEmail());
		saleOrderDto.setClientTax(orderDtoCreated.getClientTax());
		saleOrderDto.setSubTotal(orderDtoCreated.getSubTotal());
		saleOrderDto.setIvaTotal(orderDtoCreated.getIvaTotal());
		saleOrderDto.setOrderTotal(orderDtoCreated.getOrderTotal());

		LOG.info("{} START GENERATE SaleOrderDetailDtoList", idOperation);

		saleOrderDetailDtoList = orderDetailDtoList.stream()
				.map(orderDetailDto -> orderDetailDtoToSaleOrderDetailDto(orderDetailDto, orderDtoCreated))
				.collect(Collectors.toList());

		saleOrderDto.setSaleOrderDetail(saleOrderDetailDtoList);
		saleOrderDto.setOrder(orderDtoCreated);

		return saleOrderDto;
	}

	public SaleOrderDetailDto orderDetailDtoToSaleOrderDetailDto(OrderDetailDto orderDetailDto, OrderDto orderDto) {

		LOG.info("INIT orderDetailDtoToSaleOrderDetailDto()");
		LOG.info("PARAMS: [ orderDetailDto: {} , orderDto: {} ]", orderDetailDto, orderDto);

		SaleOrderDetailDto saleOrderDetailDto = new SaleOrderDetailDto();

		saleOrderDetailDto.setLineNumber(orderDetailDto.getLineNumber());
		saleOrderDetailDto.setLineType(orderDetailDto.getIsCustumArticle() ? "N" : "D");
		saleOrderDetailDto.setWarehouseCode(orderDetailDto.getWarehouseCode());
		saleOrderDetailDto.setArticleNumber(orderDetailDto.getArticleNumber());
		saleOrderDetailDto
				.setArticleDescription(orderDetailDto.getDescriptionOne() + " - " + orderDetailDto.getDescriptionTwo());
		saleOrderDetailDto.setUnitMeasurement(orderDetailDto.getUnitMeasurement());
		saleOrderDetailDto.setRequestAmount(orderDetailDto.getRequestAmount());
		saleOrderDetailDto.setArticlePrice(orderDetailDto.getFinalUnitPrice());
		saleOrderDetailDto.setSubTotal(orderDetailDto.getSubTotalTax());
		saleOrderDetailDto.setCurrency(orderDto.getCurrency());
		saleOrderDetailDto.setExchangeRate(orderDto.getExchangeRate());
		saleOrderDetailDto.setLineCodeOne("520");
		saleOrderDetailDto.setLineCodeTwo("530");
		saleOrderDetailDto.setWeight(0);
		saleOrderDetailDto.setWeightFactor(0);

		return saleOrderDetailDto;
	}

	public void updateLineCodeInOrderDetail(OrderDto orderDto) {
		LOG.info("INIT updateLineCodeInOrderDetail()");
		orderDto.getOrderDetail()
				.forEach(orderDetailDto -> orderDetailDto.setLineType(orderDetailDto.getIsCustumArticle() ? "N" : "D"));
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
