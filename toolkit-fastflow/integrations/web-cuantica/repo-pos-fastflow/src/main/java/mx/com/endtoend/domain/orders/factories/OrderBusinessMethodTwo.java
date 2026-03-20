package mx.com.endtoend.domain.orders.factories;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import mx.com.endtoend.smart.bussiness.model.orders.dto.OrderDetailDto;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.com.endtoend.domain.branch.dto.BranchDto;
import mx.com.endtoend.domain.commons.constants.ActionOrder;
import mx.com.endtoend.domain.commons.constants.OperationalLevelEnum;
import mx.com.endtoend.domain.commons.constants.StatusOrder;
import mx.com.endtoend.domain.company.dto.MethodDto;
import mx.com.endtoend.domain.company.ports.spi.CompanyPersistencePort;
import mx.com.endtoend.domain.orders.dto.CustomInterfaceOrderParams;
import mx.com.endtoend.domain.orders.dto.GenericActionControllOrderDto;
import mx.com.endtoend.domain.orders.dto.GenericSerchParamsOrderDto;
import mx.com.endtoend.domain.orders.factories.validations.GenericOrderValidation;
import mx.com.endtoend.domain.orders.ports.OrderPersistencePort;
import mx.com.endtoend.domain.orders.services.OrderUtilityService;
import mx.com.endtoend.domain.userConfigurations.dto.EmployeeDto;
import mx.com.endtoend.domain.userConfigurations.ports.spi.UserConfigurationPersistencePort;
import mx.com.endtoend.domain.users.dto.UserDto;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.GlobalError;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.ValidationError;
import mx.com.endtoend.infrastructure.services.email.EmailServicePort;
import mx.com.endtoend.infrastructure.services.jde.orders.common.serviceport.OrderJdeServicePort;
import mx.com.endtoend.infrastructure.services.posLegacy.orders.common.serviceport.OrderPosLegacyServicePort;
import mx.com.endtoend.smart.bussiness.model.catalogue.dto.StatusDto;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;
import mx.com.endtoend.smart.bussiness.model.orders.dto.OrderConfigurationDto;
import mx.com.endtoend.smart.bussiness.model.orders.dto.OrderDto;
import mx.com.endtoend.smart.bussiness.model.orders.dto.OrderHistoryDto;
import mx.com.endtoend.smart.bussiness.model.users.dto.SaleTypeDto;

/**
 * Clase administradora de los processo generales de las órdenes del sistema y
 * su relación con el sistema JD Edwards, POS Legacy y el sistema principal
 * Smart Business
 * 
 * @author ddcasas
 */
public class OrderBusinessMethodTwo implements OrderInterface {

	private static final String QUOTE_MODULE = "QUOTE";

	private OrderJdeServicePort orderJdeServicePort;

	private OrderPosLegacyServicePort orderPosLegacyServicePort;

	private EmailServicePort emailServicePort;

	private OrderPersistencePort orderPersistencePort;

	private UserConfigurationPersistencePort userConfigurationPersistencePort;

	private CompanyPersistencePort companyPersistencePort;

	private String companyCode;

	private String idOperation;

	public OrderBusinessMethodTwo(CustomInterfaceOrderParams customInterfaceOrderParams) {

		this.orderJdeServicePort = customInterfaceOrderParams.getOrderJdeServicePort();
		this.orderPosLegacyServicePort = customInterfaceOrderParams.getOrderPosLegacyServicePort();
		this.emailServicePort = customInterfaceOrderParams.getEmailServicePort();
		this.orderPersistencePort = customInterfaceOrderParams.getOrderPersistencePort();
		this.userConfigurationPersistencePort = customInterfaceOrderParams.getUserConfigurationPersistencePort();
		this.companyPersistencePort = customInterfaceOrderParams.getCompanyPersistencePort();
		this.companyCode = customInterfaceOrderParams.getCompanyCode();
		this.idOperation = customInterfaceOrderParams.getIdOperation();
	}

	private final static Logger LOG = LoggerFactory.getLogger(OrderBusinessMethodOne.class);

	private GenericOrderValidation genericOrderValidation = new GenericOrderValidation();
	private OrderBusinessFactoty orderBusinessFactoty = new OrderBusinessFactoty();
	private OrderUtilityService orderUtilityService = new OrderUtilityService();

	@SuppressWarnings("unchecked")
	@Override
	public ResponseModel createOrder(OrderDto orderDto, Boolean isConvertion, Boolean mailUsuario) {
		LOG.info("{} INIT createOrder", idOperation);
		ResponseModel responseGetSaleList = userConfigurationPersistencePort.findAllSalesByCompanyCode(companyCode,
				idOperation);
		List<SaleTypeDto> saleTypeDtoList = (List<SaleTypeDto>) responseGetSaleList.getData();

		String moduleId = "";
		for (SaleTypeDto saleTypeDto : saleTypeDtoList) {
			if (orderDto.getOrderCode().equals(saleTypeDto.getCode())) {
				moduleId = saleTypeDto.getModuleId();
				break;
			}
		}
		MethodDto method = (MethodDto) companyPersistencePort
				.findByCompanyCodeAndModule(companyCode, moduleId, idOperation).getData();
		if (method == null)
			throw new GlobalError();

		CustomInterfaceOrderParams customInterfaceOrderParams = new CustomInterfaceOrderParams.Builder()
				.setOrderJdeServicePort(orderJdeServicePort).setOrderPersistencePort(orderPersistencePort)
				.setUserConfigurationPersistencePort(userConfigurationPersistencePort).setCompanyCode(companyCode)
				.setIdOperation(idOperation).setCompanyPersistencePort(companyPersistencePort).build();

		OrderBusinessInterface orderBusiness = orderBusinessFactoty.getOderBusinessByMethod(method.getCode(),
				customInterfaceOrderParams);
		if (orderBusiness == null)
			throw new GlobalError();

		LOG.info("{} CREATE ORDER BY BUSINESS METHOD", idOperation);
		OrderDto orderCreated = orderBusiness.createOrder(orderDto);

		LOG.info("{} START SAVE ORDER IN POS-LEGACY", idOperation);
		orderPosLegacyServicePort.createOrder(orderDto, companyCode, idOperation);

		LOG.info("{} SEND EMAIL WITH ORDER DOCUMENT", idOperation);
		if (!orderCreated.getIsRetentionOrder())
			orderUtilityService.sendOrderDocument(orderPersistencePort, emailServicePort, orderDto,
					orderCreated.getOrderNumber(), companyCode, mailUsuario, idOperation);

		LOG.info("{} NEW RECORD IN ORDER HISTORY", idOperation);
		OrderHistoryDto orderHistoryDto = new OrderHistoryDto(orderCreated.getBranchCode(), orderCreated.getOrderCode(),
				orderCreated.getOrderNumber(), new Date(), ActionOrder.CREATE_ORDER.name(),
				orderCreated.getEmployeeEmail(), orderCreated.getUserNumber(), "", orderCreated.getIsRetentionOrder());
		orderPersistencePort.saveRecordInOrderHistory(orderHistoryDto, companyCode, idOperation);

		return new ResponseModel(orderCreated);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ResponseModel updateOrder(OrderDto orderDto, Boolean mailUsuario) {

		LOG.info("{} INIT updateOrder", idOperation);

		ResponseModel responseGetSaleList = userConfigurationPersistencePort.findAllSalesByCompanyCode(companyCode,
				idOperation);
		List<SaleTypeDto> saleTypeDtoList = (List<SaleTypeDto>) responseGetSaleList.getData();

		String moduleId = "";
		for (SaleTypeDto saleTypeDto : saleTypeDtoList) {
			if (orderDto.getOrderCode().equals(saleTypeDto.getCode())) {
				moduleId = saleTypeDto.getModuleId();
				break;
			}
		}
		MethodDto method = (MethodDto) companyPersistencePort
				.findByCompanyCodeAndModule(companyCode, moduleId, idOperation).getData();
		if (method == null)
			throw new GlobalError();

		CustomInterfaceOrderParams customInterfaceOrderParams = new CustomInterfaceOrderParams.Builder()
				.setOrderJdeServicePort(orderJdeServicePort).setOrderPersistencePort(orderPersistencePort)
				.setUserConfigurationPersistencePort(userConfigurationPersistencePort).setCompanyCode(companyCode)
				.setIdOperation(idOperation).setCompanyPersistencePort(companyPersistencePort)
				.setOrderPosLegacyServicePort(orderPosLegacyServicePort).build();

		OrderBusinessInterface orderBusiness = orderBusinessFactoty.getOderBusinessByMethod(method.getCode(),
				customInterfaceOrderParams);
		if (orderBusiness == null)
			throw new GlobalError();

		OrderDto orderSaved = (OrderDto) orderPersistencePort.vieworderDetailByOrderNumber(orderDto.getOrderNumber(),
				orderDto.getOrderCode(), companyCode, idOperation).getData();

		String validExternalStatus = genericOrderValidation.validExternalStatusOrder(orderPosLegacyServicePort,
				orderDto, companyCode, idOperation);

		if (!validExternalStatus.isEmpty()) {
			orderPersistencePort.cancelUpdateOrder(orderSaved.getOrderNumber(), orderDto.getOrderCode(),
					orderSaved.getBranchCode(), companyCode, idOperation).getData();
			throw new ValidationError(validExternalStatus);
		}

		if (orderSaved.getIsInvoiceTop()) {
			orderPersistencePort.cancelUpdateOrder(orderSaved.getOrderNumber(), orderDto.getOrderCode(),
					orderSaved.getBranchCode(), companyCode, idOperation).getData();
			throw new ValidationError("INVOICE TOP CAN NOT BE EDITED");
		}

		LOG.info("{} UPDATE ORDER BY BUSINESS METHOD", idOperation);
		OrderDto orderUpdated = orderBusiness.updateOrder(orderDto, orderSaved);

		LOG.info("{} START SAVE ORDER IN POS-LEGACY", idOperation);
		orderPosLegacyServicePort.updateOrder(orderDto, companyCode, idOperation);

		LOG.info("{} SEND EMAIL WITH ORDER DOCUMENT", idOperation);
		if (!orderUpdated.getIsRetentionOrder())
			orderUtilityService.sendOrderDocument(orderPersistencePort, emailServicePort, orderDto,
					orderUpdated.getOrderNumber(), companyCode, mailUsuario, idOperation);

		LOG.info("{} NEW RECORD IN ORDER HISTORY", idOperation);
		OrderHistoryDto orderHistoryDto = new OrderHistoryDto(orderUpdated.getBranchCode(), orderUpdated.getOrderCode(),
				orderUpdated.getOrderNumber(), new Date(), ActionOrder.UPDATE_ORDER.name(),
				orderUpdated.getEmployeeEmail(), orderUpdated.getUserNumber(), "", orderUpdated.getIsRetentionOrder());
		orderPersistencePort.saveRecordInOrderHistory(orderHistoryDto, companyCode, idOperation);

		orderPersistencePort.cancelUpdateOrder(orderSaved.getOrderNumber(), orderDto.getOrderCode(),
				orderSaved.getBranchCode(), companyCode, idOperation).getData();
		return new ResponseModel(orderUpdated);
	}

	@Override
	public ResponseModel getOrderToUpdate(BigDecimal orderNumber, String orderType) {
		LOG.info("{} INIT getOrderToUpdate", idOperation);
		OrderDto orderDto = (OrderDto) orderPersistencePort
				.vieworderDetailByOrderNumber(orderNumber, orderType, companyCode, idOperation).getData();

		if (orderDto == null) {
			LOG.warn("{} ERROR IN SERCH SALE DIRECT ORDER {} ", idOperation, orderNumber.toString());
			throw new ValidationError("INVALID ORDER NUMBER");
		}

		if (orderDto.getIsUpdated()) {
			LOG.warn("{} ERROR IN SERCH ORDER {} ", idOperation, orderNumber.toString());
			throw new ValidationError("ORDER IS ACTIVE UPDATE");
		}

		boolean isUpdated = (boolean) orderPersistencePort
				.getOrderToUpdate(orderNumber, orderType, orderDto.getBranchCode(), companyCode, idOperation).getData();
		if (!isUpdated) {
			LOG.warn("{} ERROR IN CHANGE STATUS ORDER TO UPDATE {} ", idOperation, orderNumber.toString());
			throw new GlobalError();
		}

		orderPosLegacyServicePort.updateStatusOrderActive(orderNumber, true, orderType, companyCode, idOperation);

		return new ResponseModel(orderDto);
	}

	@Override
	public ResponseModel cancelUpdateOrder(BigDecimal orderNumber, String email, String orderType) {
		LOG.info("{} INIT cancelUpdateSaleDirectOrder()", idOperation);
		LOG.info("{} PARAMS: [ orderNumber: {} , orderType: {} , email: {} , companyCode: {} ]", idOperation,
				orderNumber, orderType, email, companyCode);

		OrderDto orderDto = getOrderDto(orderNumber, orderType);
		EmployeeDto employeeDto = getEmployeeDto(orderDto.getBranchCode(), email);

		if (orderDto.getTimeActive() != null) {
			return handleActiveTime(orderDto, employeeDto, orderNumber, orderType, orderDto.getBranchCode());
		} else {
			return attemptCancelUpdate(orderNumber, orderType, orderDto.getBranchCode());
		}
	}

	private OrderDto getOrderDto(BigDecimal orderNumber, String orderType) {
		LOG.info("{} GET ORDER INFORMATION", idOperation);
		OrderDto orderDto = (OrderDto) orderPersistencePort
				.vieworderDetailByOrderNumber(orderNumber, orderType, companyCode, idOperation).getData();

		if (orderDto == null) {
			LOG.warn("{} ERROR IN SEARCH ORDER: {}", idOperation, orderNumber);
			throw new ValidationError("INVALID-ORDER-NUMBER");
		}
		return orderDto;
	}

	private EmployeeDto getEmployeeDto(String branchCode, String email) {
		LOG.info("{} GET EMPLOYEE CONFIGURATION", idOperation);
		EmployeeDto employeeDto = (EmployeeDto) orderPersistencePort
				.getUserConfigurationByBranchAndEmail(branchCode, email, companyCode, idOperation).getData();

		if (employeeDto == null) {
			LOG.warn("{} USER CONFIGURATION NOT FOUND", idOperation);
			throw new ValidationError("USER-CONFIGURATION-NOT-FOUND");
		}
		return employeeDto;
	}

	private ResponseModel handleActiveTime(OrderDto orderDto, EmployeeDto employeeDto, BigDecimal orderNumber,
			String orderType, String branchCode) {
		LOG.info("{} ORDER HAVE ACTIVE TIME RECORD", idOperation);
		long validTime = 30 * 1000;
		Calendar currentTime = Calendar.getInstance();
		Calendar lastOrderTimeActive = Calendar.getInstance();
		lastOrderTimeActive.setTime(orderDto.getTimeActive());

		LOG.info("{} GET REST BY CURRENT-TIME: {} AND LAST-ORDER-TIME-ACTIVE: {}", idOperation,
				currentTime.getTimeInMillis(), lastOrderTimeActive.getTimeInMillis());

		long difTime = currentTime.getTimeInMillis() - lastOrderTimeActive.getTimeInMillis();
		LOG.info("{} RESULT-REST TIME: {}", idOperation, difTime);

		if (difTime > validTime || employeeDto.getUserId() == orderDto.getIdUser()) {
			LOG.info("{} UNBLOCK ATTEMPTED", idOperation);
			boolean cancelUpdate = (boolean) orderPersistencePort
					.cancelUpdateOrder(orderNumber, orderType, branchCode, companyCode, idOperation).getData();

			if (!cancelUpdate) {
				LOG.warn("{} ERROR CANCEL ORDER TO UPDATE {}", idOperation, orderNumber);
				throw new GlobalError();
			}

			orderPosLegacyServicePort.updateStatusOrderActive(orderNumber, false, orderType, companyCode, idOperation);

			return new ResponseModel(cancelUpdate);
		} else {
			throw new ValidationError("ORDER-IN-USE");
		}
	}

	private ResponseModel attemptCancelUpdate(BigDecimal orderNumber, String orderType, String branchCode) {
		LOG.info("{} ORDER HAVE NOT ACTIVE TIME, UNBLOCK ATTEMPTED", idOperation);
		boolean cancelUpdate = (boolean) orderPersistencePort
				.cancelUpdateOrder(orderNumber, orderType, branchCode, companyCode, idOperation).getData();
		if (!cancelUpdate) {
			LOG.warn("{} ERROR CANCEL ORDER TO UPDATE {}", idOperation, orderNumber);
			throw new GlobalError();
		}
		orderPosLegacyServicePort.updateStatusOrderActive(orderNumber, false, orderType, companyCode, idOperation);
		return new ResponseModel(cancelUpdate);
	}

	@Override
	public ResponseModel approveOrder(GenericActionControllOrderDto genericActionControllOrderDto) {
		LOG.info("{} INIT approveOrder() ", idOperation);

		LOG.info("{} FIND OPERATIONAL DATA", idOperation);
		OrderDto orderDetail = (OrderDto) orderPersistencePort
				.vieworderDetailByOrderNumber(genericActionControllOrderDto.getOrderNumber(),
						genericActionControllOrderDto.getOrderType(), companyCode, idOperation)
				.getData();
		if (orderDetail == null)
			throw new ValidationError("ORDER NOT FOUND");

		EmployeeDto employeeDto = (EmployeeDto) orderPersistencePort
				.getUserConfigurationByBranchAndEmail(orderDetail.getBranchCode(),
						genericActionControllOrderDto.getEmployeeEmail(), companyCode, idOperation)
				.getData();
		if (employeeDto == null)
			throw new ValidationError("USER-CONFIGURATION-INCOMPLETE");

		LOG.info("{} INIT VALIDATIONS ", idOperation);
		validOperativeData(genericActionControllOrderDto, orderDetail, employeeDto);

		LOG.info("{} APPROVE ORDER ", idOperation);
		OrderDto orderDto = (OrderDto) orderPersistencePort
				.approveOrderByOrderNumberAndOrderType(genericActionControllOrderDto.getOrderType(),
						genericActionControllOrderDto.getOrderNumber(), companyCode, "", idOperation)
				.getData();
		if (orderDto == null)
			throw new GlobalError();

		LOG.info("{} UPDATE EXTERNAL SYSTEM", idOperation);
		orderPosLegacyServicePort.approveOrder(" ", orderDto.getOrderNumber(), orderDto.getOrderCode(), companyCode,
				idOperation);

		LOG.info("{} NEW RECORD IN ORDER HISTORY", idOperation);
		OrderHistoryDto orderHistoryDto = new OrderHistoryDto(orderDto.getBranchCode(),
				genericActionControllOrderDto.getOrderType(), genericActionControllOrderDto.getOrderNumber(),
				new Date(), ActionOrder.APPROVE_ORDER.toString(), genericActionControllOrderDto.getEmployeeEmail(),
				employeeDto.getUserNumber(), genericActionControllOrderDto.personalToString(),
				orderDto.getIsRetentionOrder());
		orderPersistencePort.saveRecordInOrderHistory(orderHistoryDto, companyCode, idOperation);

		return new ResponseModel(orderDto);
	}

	private void validOperativeData(GenericActionControllOrderDto genericActionControllOrderDto, OrderDto orderDetail,
			EmployeeDto employeeDto) {
		if (orderDetail.getStatus().getCode().equals(StatusOrder.MANUAL_CANCELLATION.getValue())
				|| orderDetail.getStatus().getCode().equals(StatusOrder.AUTOMATIC_CANCELLATION.getValue()))
			throw new ValidationError("INVALID STATUS ORDER, STATUS: " + orderDetail.getStatus().getCode().toString());

		if (!employeeDto.getUserConfiguration().getAuthorizationCode()
				.equals(genericActionControllOrderDto.getAuthorizationCode()))
			throw new ValidationError("INVALID AUTHORIZATION CODE");

		String validationDiscount = validDiscountToApprobeOrder(orderDetail.getOrderDetail(),
				employeeDto.getUserConfiguration().getPercentageAuthorized());
		if (!validationDiscount.isEmpty())
			throw new ValidationError("PERCENTAGE OF AUTHORIZATION NOT ALLOWED BY ARTICLES: " + validationDiscount);
	}

	private String validDiscountToApprobeOrder(List<OrderDetailDto> orderDetailDtoList, BigDecimal percentageAuthorized) {
		return orderDetailDtoList.stream().filter(detail -> (percentageAuthorized.multiply(BigDecimal.valueOf(100))).compareTo(detail.getDiscountSeller()) < 0)
				.map(detail -> detail.getArticleCode().trim() + " ").collect(Collectors.joining()).trim();
	}

	@SuppressWarnings("unchecked")
	@Override
	public ResponseModel cancelOrder(GenericActionControllOrderDto genericActionControllOrderDto) {

		LOG.info("{} INIT cancelOrder() ", idOperation);
		String statusCode = StatusOrder.MANUAL_CANCELLATION.getValue();

		OrderDto orderDto = Optional
				.ofNullable(
						(OrderDto) orderPersistencePort
								.vieworderDetailByOrderNumber(genericActionControllOrderDto.getOrderNumber(),
										genericActionControllOrderDto.getOrderType(), companyCode, idOperation)
								.getData())
				.orElseThrow(() -> new ValidationError("INVALID ORDER NUMBER"));

		ResponseModel responseGetSaleList = userConfigurationPersistencePort.findAllSalesByCompanyCode(companyCode,
				idOperation);
		List<SaleTypeDto> saleTypeDtoList = (List<SaleTypeDto>) responseGetSaleList.getData();

		String moduleId = saleTypeDtoList.stream()
				.filter(saleTypeDto -> orderDto.getOrderCode().equals(saleTypeDto.getCode()))
				.map(SaleTypeDto::getModuleId).findFirst().orElse("");

		if (orderDto.getIsConverted() && QUOTE_MODULE.equals(moduleId)) {
			throw new ValidationError("THE ORDER WAS CONVERTED");
		}

		if (orderDto.getStatus().getCode().equals(StatusOrder.PARTIAL_PAYMENT.getValue())
				|| orderDto.getStatus().getCode().equals(StatusOrder.FULL_PAYMENT.getValue())) {
			throw new ValidationError("ORDER HAS PAYMENT");
		}

		EmployeeDto employeeDto = Optional
				.ofNullable(
						(EmployeeDto) orderPersistencePort
								.getUserConfigurationByBranchAndEmail(orderDto.getBranchCode(),
										genericActionControllOrderDto.getEmployeeEmail(), companyCode, idOperation)
								.getData())
				.orElseThrow(() -> new ValidationError("INCOMPLETE-USER-CONFIGURATION"));

		LOG.info("{} INIT PROPERTY VALIDATION", idOperation);
		if (!orderDto.getIdUser().equals(employeeDto.getUserId())
				&& !employeeDto.getRoleJob().getCode().contains("SUPERVISION")) {
			throw new ValidationError("USER NOT OWNER OF THE ORDER");
		}

		LOG.info("{} GET STATUS BY CODE {}", idOperation, statusCode);
		StatusDto statusDto = (StatusDto) orderPersistencePort
				.getStatusByCode(companyCode, orderDto.getBranchCode(), statusCode, idOperation).getData();
		orderDto.setStatus(statusDto);

		OrderDto orderDtoCanceled = Optional.ofNullable(
				(OrderDto) orderPersistencePort.cancelOrder(orderDto, companyCode, "", "", idOperation).getData())
				.orElseThrow(GlobalError::new);

		LOG.info("{} UPDATE EXTERNAL SYSTEM", idOperation);
		orderPosLegacyServicePort.cancelOrder(orderDto.getOrderNumber(), orderDto.getOrderCode(), companyCode,
				idOperation);

		LOG.info("{} NEW RECORD IN ORDER HISTORY", idOperation);
		OrderHistoryDto orderHistoryDto = new OrderHistoryDto(orderDto.getBranchCode(),
				genericActionControllOrderDto.getOrderType(), genericActionControllOrderDto.getOrderNumber(),
				new Date(), ActionOrder.CANCEL_ORDER.toString(), genericActionControllOrderDto.getEmployeeEmail(),
				employeeDto.getUserNumber(), genericActionControllOrderDto.personalToString(),
				orderDtoCanceled.getIsRetentionOrder());
		orderPersistencePort.saveRecordInOrderHistory(orderHistoryDto, companyCode, idOperation);

		return new ResponseModel(orderDto);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ResponseModel getOrderListByParams(GenericSerchParamsOrderDto params, String email) {
		LOG.info("{} INIT getOrderListByParams() ", idOperation);

		LOG.info("{} GET USER-CONFIGURATION BY USERNAME: {} ", idOperation, email);
		EmployeeDto employeeDto = (EmployeeDto) orderPersistencePort
				.getUserConfigurationByBranchAndEmail(params.getBranchCode(), email, companyCode, idOperation)
				.getData();
		if (employeeDto == null)
			throw new ValidationError("USER CONFIGURATION INCOMPLETE");

		LOG.info("{} PROCESS BY OPERATIVE ROLE JOB ", idOperation);
		OperationalLevelEnum employeeRoleJob = OperationalLevelEnum.valueOf(employeeDto.getRoleJob().getCode());
		try {
			switch (employeeRoleJob) {
			case OPERATIONAL:
				LOG.info("{} USER HAS ROLE OPERATIONAL", idOperation);
				params.setIdUser(employeeDto.getUserId());
				break;

			case SUPERVISION_I:
				LOG.info("{} USER HAS ROLE SUPERVISION_I", idOperation);
				List<Long> idEmployeeList = new ArrayList<>();
				idEmployeeList.add(employeeDto.getUserId());
				if (employeeDto.getEmployees() != null && !employeeDto.getEmployees().isEmpty()) {
					idEmployeeList.addAll(employeeDto.getEmployees().stream().map(EmployeeDto::getUserId)
							.collect(Collectors.toList()));
				}
				params.setIdEmployees(idEmployeeList);
				break;

			case SUPERVISION_II:
			case SUPERVISION_III:
				LOG.info("{} USER HAS ROLE SUPERVISION_II or SUPERVISION_III", idOperation);
				params.setIdEmployees(null);
				break;

			default:
				throw new ValidationError("INVALID ROLE JOB");
			}
		} catch (IllegalArgumentException | NullPointerException ex) {
			LOG.error("ERROR GETTING OPERATIONAL ROLE JOB");
			throw new ValidationError("INVALID ROLE JOB");
		}

		params.setBranchCode(params.getBranchCode());

		List<OrderDto> orderDtoList = (List<OrderDto>) orderPersistencePort
				.getOrderListByParams(params, companyCode, "", idOperation).getData();

		if (orderDtoList == null)
			return new ResponseModel(new ArrayList<>());

		return new ResponseModel(orderDtoList);
	}

	@Override
	public ResponseModel getAllOrdersListByCompany(GenericSerchParamsOrderDto params, String email) {
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ResponseModel viewOderDetailByOrderNumber(BigDecimal orderNumber, String orderType) {
		LOG.info("{} INIT viewDetailSaleCashOrder() ", idOperation);
		OrderDto orderDto = (OrderDto) orderPersistencePort
				.vieworderDetailByOrderNumber(orderNumber, orderType, companyCode, idOperation).getData();
		if (orderDto == null) {
			LOG.error("{} ERROR IN SERCH ", idOperation);
			return new ResponseModel("");
		}

		ResponseModel responseGetSaleList = userConfigurationPersistencePort.findAllSalesByCompanyCode(companyCode,
				idOperation);
		List<SaleTypeDto> saleTypeDtoList = (List<SaleTypeDto>) responseGetSaleList.getData();

		String moduleId = "";
		for (SaleTypeDto saleTypeDto : saleTypeDtoList) {
			if (orderDto.getOrderCode().equals(saleTypeDto.getCode())) {
				moduleId = saleTypeDto.getModuleId();
				break;
			}
		}

		OrderDto externalOrder = (OrderDto) orderPosLegacyServicePort
				.getOrderDetailByOrderNumberAndOrderType(orderNumber, orderDto.getOrderCode(), companyCode, idOperation)
				.getData();

		if (externalOrder != null && !moduleId.equals(QUOTE_MODULE)) {
			orderUtilityService.updateStatusFromExternalSource(orderPersistencePort, orderDto, externalOrder,
					companyCode, externalOrder.getBranchCode(), idOperation);
			LOG.info("{} SEARCH ORDER WITH CHANGES", idOperation);
			orderDto = (OrderDto) orderPersistencePort
					.vieworderDetailByOrderNumber(orderNumber, orderType, companyCode, idOperation).getData();
		}
		return new ResponseModel(orderDto);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ResponseModel viewHistoricalOrderDetailByOrderNumber(BigDecimal orderNumber, String orderType) {
		LOG.info("{} INIT viewHistoricalDetailQuoteOrder() ", idOperation);
		List<OrderHistoryDto> orderHistoricalDto = (List<OrderHistoryDto>) orderPersistencePort
				.viewHistoricalOrderDetailByOrderNumber(orderNumber, companyCode, "", idOperation).getData();
		return new ResponseModel(orderHistoricalDto);
	}

	@Override
	public ResponseModel generateTicketByOrderNumberAndOrderType(BigDecimal orderNumber, String orderType) {
		LOG.info(String.format("%s INIT generateTicket() ", idOperation));
		OrderDto orderDto = (OrderDto) orderPersistencePort
				.vieworderDetailByOrderNumber(orderNumber, orderType, companyCode, idOperation).getData();
		if (orderDto == null)
			throw new ValidationError("INVALID ORDER NUMBER");

		UserDto userDto = (UserDto) orderPersistencePort
				.getPersonalUserConfigurationByUserIdAndCompanyCode(orderDto.getIdUser(), companyCode, idOperation)
				.getData();
		if (userDto == null)
			throw new ValidationError("ERROR IN EMPLOYEE CONFIGURATION ON THE ORDER");

		ResponseModel reponseFromPersistencePort = orderPersistencePort.generateTicket(orderDto, userDto, companyCode,
				orderDto.getBranchCode(), orderType, idOperation);
		return reponseFromPersistencePort;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ResponseModel generateDocumentByOrderNumberAndOrderType(BigDecimal orderNumber, String orderType) {
		LOG.info("{} INIT generateDocument() ", idOperation);
		OrderDto orderDto = Optional
				.ofNullable((OrderDto) orderPersistencePort
						.vieworderDetailByOrderNumber(orderNumber, orderType, companyCode, idOperation).getData())
				.orElseThrow(() -> new ValidationError("INVALID ORDER NUMBER"));

		BranchDto branchDto = Optional
				.ofNullable((BranchDto) orderPersistencePort.getBranchConfigurationByBranchCodeAndCompanyCode(
						orderDto.getBranchCode(), companyCode, idOperation).getData())
				.orElseThrow(() -> new ValidationError("INVALID BRANCH-CODE"));

		UserDto userDto = Optional
				.ofNullable((UserDto) orderPersistencePort.getPersonalUserConfigurationByUserIdAndCompanyCode(
						orderDto.getIdUser(), companyCode, idOperation).getData())
				.orElseThrow(() -> new ValidationError("ERROR IN EMPLOYEE CONFIGURATION ON THE ORDER"));

		ResponseModel responseGetSaleList = userConfigurationPersistencePort.findAllSalesByCompanyCode(companyCode,
				idOperation);
		List<SaleTypeDto> saleTypeDtoList = (List<SaleTypeDto>) responseGetSaleList.getData();
		String moduleId = saleTypeDtoList.stream()
				.filter(saleTypeDto -> orderDto.getOrderCode().equals(saleTypeDto.getCode()))
				.map(SaleTypeDto::getModuleId).findFirst().orElse("");

		ResponseModel responseFromPersistencePort;
		if (QUOTE_MODULE.equals(moduleId)) {
			responseFromPersistencePort = orderPersistencePort.generateQuoteDocument(orderDto, branchDto, userDto,
					companyCode, orderType, idOperation);
		} else {
			responseFromPersistencePort = orderPersistencePort.generateDocument(orderDto, branchDto, userDto,
					companyCode, orderType, idOperation);
		}
		return responseFromPersistencePort;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ResponseModel sendDocumentByEmailAndCompanyCode(List<String> emailList, BigDecimal orderNumber,
			String orderType) {
		LOG.info("{} INIT sendDocumentByEmail()", idOperation);

		if (emailList.isEmpty()) {
			throw new ValidationError("EMAIL LIST IS EMPTY");
		}

		OrderDto orderDto = Optional
				.ofNullable((OrderDto) orderPersistencePort
						.vieworderDetailByOrderNumber(orderNumber, orderType, companyCode, idOperation).getData())
				.orElseThrow(() -> new ValidationError("INVALID ORDER NUMBER"));

		BranchDto branchDto = Optional
				.ofNullable((BranchDto) orderPersistencePort.getBranchConfigurationByBranchCodeAndCompanyCode(
						orderDto.getBranchCode(), companyCode, idOperation).getData())
				.orElseThrow(() -> new ValidationError("INVALID BRANCH-CODE"));

		UserDto userDto = Optional
				.ofNullable((UserDto) orderPersistencePort.getPersonalUserConfigurationByUserIdAndCompanyCode(
						orderDto.getIdUser(), companyCode, idOperation).getData())
				.orElseThrow(() -> new ValidationError("ERROR IN EMPLOYEE CONFIGURATION ON THE ORDER"));

		LOG.info("{} GENERATE REPORT BY MODULE ID", idOperation);

		ResponseModel responseGetSaleList = userConfigurationPersistencePort.findAllSalesByCompanyCode(companyCode,
				idOperation);
		List<SaleTypeDto> saleTypeDtoList = (List<SaleTypeDto>) responseGetSaleList.getData();
		String moduleId = saleTypeDtoList.stream()
				.filter(saleTypeDto -> orderDto.getOrderCode().equals(saleTypeDto.getCode()))
				.map(SaleTypeDto::getModuleId).findFirst().orElse("");

		ResponseModel responseFromPersistencePort;
		if (QUOTE_MODULE.equals(moduleId)) {
			responseFromPersistencePort = orderPersistencePort.generateQuoteDocument(orderDto, branchDto, userDto,
					companyCode, orderType, idOperation);
		} else {
			responseFromPersistencePort = orderPersistencePort.generateDocument(orderDto, branchDto, userDto,
					companyCode, orderType, idOperation);
		}

		byte[] reporte = Optional.ofNullable((byte[]) responseFromPersistencePort.getData())
				.orElseThrow(GlobalError::new);

		File reportToSend = new File(orderDto.getOrderType() + "-" + orderDto.getOrderNumber().longValue() + ".pdf");
		try {
			FileUtils.writeByteArrayToFile(reportToSend, reporte);
		} catch (IOException e) {
			LOG.error("{} ", e.getMessage());
		}

		LOG.info("{} SEND DOCUMENT", idOperation);
		return emailServicePort.sendDocumentByCompanyCode(emailList, reportToSend, companyCode, idOperation);
	}

	@Override
	public ResponseModel convertQuoteOrderToSaleOrder(OrderDto orderDto, String orderCode, Boolean mailUsuario) {

		LOG.info("{} INIT convertQuoteOrderToSaleOrder() ", idOperation);

		BigDecimal quoteOrderNumber = orderDto.getOrderNumber();
		String quoteOrderCode = orderDto.getOrderCode();

		OrderDto quoteOrder = (OrderDto) orderPersistencePort
				.vieworderDetailByOrderNumber(quoteOrderNumber, quoteOrderCode, companyCode, idOperation).getData();
		if (quoteOrder == null)
			throw new ValidationError("QUOTE ORDER NOT FOUND");

		LOG.info("{} FIND ORDER-CONFIGURATION", idOperation);
		OrderConfigurationDto orderConfiguration = (OrderConfigurationDto) orderPersistencePort
				.getOrderConfigurationByOrderCodeAndCompanyCode(orderCode, companyCode, idOperation).getData();
		if (orderConfiguration == null) {
			throw new ValidationError("ORDER CONFIGURATION INCOMPLETE");
		}

		LOG.info("{} ORDER-CONFIGURATION FOUND, VALID CONFIGURATION TO CONVERT", idOperation);
		if (!orderConfiguration.getIsApplyConversion())
			throw new ValidationError("ORDER NOT APPLY CONVERSION");

		if (quoteOrder.getIsConverted())
			throw new ValidationError("ORDER WAS CONVERTER");

		LOG.info("{} INIT CONVERT QUOTE AND SAVE SALE ", idOperation);
		cleanIdToConvertQuoteToSale(quoteOrder, idOperation);

		quoteOrder.setOrderCode(orderCode);
		OrderDto orderConverted = (OrderDto) this.createOrder(quoteOrder, true, mailUsuario).getData();

		LOG.info("{} FINISH CONVERTION, UPDATE AND  SAVE HISTORICAL", idOperation);
		orderPersistencePort.updateQuoteOrderToConverted(quoteOrderCode, quoteOrderNumber, companyCode, idOperation);

		OrderHistoryDto orderHistoryDto = new OrderHistoryDto(orderConverted.getBranchCode(), quoteOrderCode,
				quoteOrderNumber, new Date(), ActionOrder.CONVERT_ORDER.toString(), orderConverted.getEmployeeEmail(),
				orderConverted.getUserNumber(),
				"SALE ORDER NUMBER: " + orderConverted.getOrderNumber() + "-" + orderConverted.getOrderCode(),
				orderConverted.getIsRetentionOrder());
		orderPersistencePort.saveRecordInOrderHistory(orderHistoryDto, companyCode, idOperation);

		return new ResponseModel(orderConverted);
	}

	private void cleanIdToConvertQuoteToSale(OrderDto quoteOrder, String idOperation) {
		LOG.info("{} INIT cleanIdToConvertQuoteToSale() ", idOperation);
		quoteOrder.setOrderId(null);
		quoteOrder.setIsConverted(true);
		quoteOrder.getOrderDetail().stream().forEach(orderDetail -> {
			orderDetail.setId(null);
		});
		quoteOrder.getAddresses().stream().forEach(address -> {
			address.setId(null);
		});
		quoteOrder.getTaxes().stream().forEach(tax -> {
			tax.setId(null);
		});
	}
}