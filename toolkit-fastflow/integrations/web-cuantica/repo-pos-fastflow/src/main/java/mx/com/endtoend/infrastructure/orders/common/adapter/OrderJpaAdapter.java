package mx.com.endtoend.infrastructure.orders.common.adapter;

import java.math.BigDecimal;
import java.util.List;

import javax.transaction.Transactional;

import mx.com.endtoend.infrastructure.orders.common.repository.GenericOrderRepositoryInterface;
import mx.com.endtoend.infrastructure.orders.common.provider.GenericOrderActionProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import mx.com.endtoend.domain.branch.dto.BranchDto;
import mx.com.endtoend.domain.orders.dto.GenericSerchParamsOrderDto;
import mx.com.endtoend.domain.orders.ports.OrderPersistencePort;
import mx.com.endtoend.domain.userConfigurations.dto.EmployeeDto;
import mx.com.endtoend.domain.users.dto.UserDto;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.GlobalError;
import mx.com.endtoend.smart.bussiness.model.catalogue.dto.StatusDto;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;
import mx.com.endtoend.smart.bussiness.model.orders.dto.OrderConfigurationDto;
import mx.com.endtoend.smart.bussiness.model.orders.dto.OrderDto;
import mx.com.endtoend.smart.bussiness.model.orders.dto.OrderHistoryDto;
import mx.com.endtoend.smart.bussiness.model.orders.dto.SaleOrderDto;

/**
 * Clase que se encarga de obtener el repositorio específico de cada cliente
 * para ejecutar las operaciones de consulta y persistencia de datos de las
 * ordenes del sistema.
 * 
 * @author ddcasas
 *
 */
public class OrderJpaAdapter implements OrderPersistencePort {

	@Autowired
	private GenericOrderActionProvider genericOrderActionProvider;

	private final Logger LOG = LoggerFactory.getLogger(OrderJpaAdapter.class);

	@Override
	public ResponseModel createOrder(OrderDto orderDto, String companyCode, String idOperation) {

		LOG.info(String.format("%s INIT createOrder()", idOperation));
		LOG.info(String.format("%s PARAMS:[ orderDto: %s , companyCode: %s ] ", idOperation, orderDto.toString(),
				companyCode));
		GenericOrderRepositoryInterface repository = genericOrderActionProvider.getRepository(companyCode);
		if (repository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		OrderDto orderCreated = repository.createOrder(orderDto, idOperation);
		return new ResponseModel(orderCreated);

	}

	@Override
	public ResponseModel updateOrder(OrderDto orderDto, String companyCode, String idOperation) {

		LOG.info(String.format("%s INIT updateOrder()", idOperation));
		LOG.info(String.format("%s PARAMS:[ orderDto: %s , companyCode: %s ] ", idOperation, orderDto.toString(),
				companyCode));

		GenericOrderRepositoryInterface repository = genericOrderActionProvider.getRepository(companyCode);

		if (repository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}

		OrderDto orderUpdated = repository.updateOrder(orderDto, idOperation);

		return new ResponseModel(orderUpdated);

	}

	@Override
	public ResponseModel updateStatusOrderByOrderNumberAndOrderCodeAndCompanyCode(BigDecimal orderNumber,
			String orderCode, StatusDto status, BigDecimal pendingPayment, String companyCode, String idOperation) {

		LOG.info(String.format("%s INIT updateStatusOrderByOrderNumberAndOrderCodeAndCompanyCode()", idOperation));
		LOG.info(String.format(
				"%s PARAMS:[ orderNumber: %s , orderCode: %s , status: %s , pendingPayment: %f , companyCode: %s ] ",
				idOperation, orderNumber.toString(), orderCode, status.toString(), pendingPayment, companyCode));

		GenericOrderRepositoryInterface repository = genericOrderActionProvider.getRepository(companyCode);

		if (repository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}

		OrderDto orderUpdated = repository.updateStatusOrderByOrderNumberAndOrderCode(orderNumber, orderCode, status,
				pendingPayment, idOperation);

		return new ResponseModel(orderUpdated);
	}

	@Override
	public ResponseModel cancelOrder(OrderDto orderDto, String companyCode, String branch, String method,
			String idOperation) {

		LOG.info(String.format("%s INIT cancelOrder()", idOperation));
		LOG.info(String.format("%s PARAMS:[ orderDto: %s , companyCode: %s ] ", idOperation, orderDto.toString(),
				companyCode));

		GenericOrderRepositoryInterface repository = genericOrderActionProvider.getRepository(companyCode);

		if (repository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}

		OrderDto orderCanceled = repository.cancelOrder(orderDto, idOperation);

		return new ResponseModel(orderCanceled);

	}

	@Override
	public ResponseModel approveOrderByOrderNumberAndOrderType(String orderType, BigDecimal orderNumber,
			String companyCode, String branch, String idOperation) {

		LOG.info(String.format("%s INIT approveOrderByOrderNumberAndOrderType()", idOperation));
		LOG.info(String.format("%s PARAMS:[ orderType: %s , orderNumber: %s , companyCode: %s ] ", idOperation,
				orderType, orderNumber.toString(), companyCode));

		GenericOrderRepositoryInterface repository = genericOrderActionProvider.getRepository(companyCode);

		if (repository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}

		OrderDto orderApproved = repository.approveOrder(orderType, orderNumber, idOperation);

		return new ResponseModel(orderApproved);

	}

	@Override
	public ResponseModel getOrderToUpdate(BigDecimal orderNumber, String orderType, String branchCode,
			String companyCode, String idOperation) {

		LOG.info(String.format("%s INIT getOrderToUpdate()", idOperation));
		LOG.info(String.format("%s PARAMS:[ orderType: %s , orderNumber: %s , companyCode: %s ] ", idOperation,
				orderType, orderNumber.toString(), companyCode));

		GenericOrderRepositoryInterface repository = genericOrderActionProvider.getRepository(companyCode);

		if (repository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}

		boolean orderUpdate = repository.getOrderToUpdate(orderNumber, orderType, idOperation);

		return new ResponseModel(orderUpdate);

	}

	@Override
	public ResponseModel cancelUpdateOrder(BigDecimal orderNumber, String orderType, String branchCode,
			String companyCode, String idOperation) {

		LOG.info(String.format("%s INIT cancelUpdateOrder()", idOperation));
		LOG.info(String.format("%s PARAMS:[ orderType: %s , orderNumber: %s , companyCode: %s , branchCode: %s ] ",
				idOperation, orderType, orderNumber.toString(), companyCode, branchCode));

		GenericOrderRepositoryInterface repository = genericOrderActionProvider.getRepository(companyCode);

		if (repository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}

		boolean cancelUpdate = repository.cancelUpdateOrder(orderNumber, orderType, idOperation);

		return new ResponseModel(cancelUpdate);

	}

	@Override
	public ResponseModel getOrderListByParams(GenericSerchParamsOrderDto params, String companyCode, String branch,
			String idOperation) {

		LOG.info(String.format("%s INIT getOrderListByParams()", idOperation));
		LOG.info(String.format("%s PARAMS:[ params: %s , companyCode: %s , branch: %s ] ", idOperation,
				params.toString(), companyCode, branch));

		GenericOrderRepositoryInterface repository = genericOrderActionProvider.getRepository(companyCode);
		if (repository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}

		List<OrderDto> orderDtoList = repository.getOrderList(params, companyCode, branch, idOperation);

		return new ResponseModel(orderDtoList);

	}

	@Override
	public ResponseModel getAllOrdersListByCompany(GenericSerchParamsOrderDto params, String companyCode, String branch,
												   String idOperation) {

		LOG.info(String.format("%s INIT getOrderListByParams()", idOperation));
		LOG.info(String.format("%s PARAMS:[ params: %s , companyCode: %s , branch: %s ] ", idOperation,
				params.toString(), companyCode, branch));

		GenericOrderRepositoryInterface repository = genericOrderActionProvider.getRepository(companyCode);
		if (repository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}

		List<OrderDto> orderDtoList = repository.getAllOrdersListByCompany(params, companyCode, branch, idOperation);

		return new ResponseModel(orderDtoList);
	}

	@Transactional
	@Override
	public ResponseModel vieworderDetailByOrderNumber(BigDecimal orderNumber, String orderCode, String companyCode,
			String idOperation) {

		LOG.info(String.format("%s INIT vieworderDetailByOrderNumber()", idOperation));
		LOG.info(String.format("%s PARAMS:[ orderNumber: %s , orderCode: %s , companyCode: %s ] ", idOperation,
				orderNumber.toString(), orderCode, companyCode));

		GenericOrderRepositoryInterface repository = genericOrderActionProvider.getRepository(companyCode);
		if (repository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}

		OrderDto orderDto = repository.viewOrderDetailByOrderNumber(orderNumber, orderCode, companyCode, "",
				idOperation);

		LOG.info(String.format("%s RETURN ORDER: %s", idOperation,
				(orderDto != null ? orderDto.toString() : "NOT FOUND")));

		return new ResponseModel(orderDto);

	}

	@Override
	public ResponseModel viewHistoricalOrderDetailByOrderNumber(BigDecimal orderNumber, String companyCode,
			String branch, String idOperation) {

		LOG.info(String.format("%s INIT viewHistoricalOrderDetailByOrderNumber()", idOperation));
		LOG.info(String.format("%s PARAMS:[ orderNumber: %s , companyCode: %s , branch: %s ] ", idOperation,
				orderNumber.toString(), companyCode, branch));

		GenericOrderRepositoryInterface repository = genericOrderActionProvider.getRepository(companyCode);
		if (repository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}

		List<OrderHistoryDto> orderHistoricalDtoList = repository.viewHistoricalDetailOrderByOrderNumber(orderNumber,
				companyCode, branch, idOperation);

		LOG.info(String.format("%s RETURN HISTORICAL-ORDER: ", idOperation));
		return new ResponseModel(orderHistoricalDtoList);

	}

	@Override
	public ResponseModel getUserConfigurationByBranchAndEmail(String branchCode, String email, String companyCode,
			String idOperation) {

		LOG.info(String.format("%s INIT getUserConfigurationByBranchAndEmail()", idOperation));
		LOG.info(String.format("%s PARAMS:[ email: %s , companyCode: %s , branchCode: %s ] ", idOperation, email,
				companyCode, branchCode));

		GenericOrderRepositoryInterface repository = genericOrderActionProvider.getRepository(companyCode);

		if (repository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}

		EmployeeDto employeeDto = repository.getUserConfiguration(branchCode, email, idOperation);

		LOG.info(String.format("%s RETURN USER-CONFIGURATION", idOperation));

		return new ResponseModel(employeeDto);

	}

	@Override
	public ResponseModel getPersonalUserConfigurationByUserIdAndCompanyCode(Long userId, String companyCode,
			String idOperation) {

		LOG.info(String.format("%s INIT getPersonalUserConfigurationByUserIdAndCompanyCode() ", idOperation));
		LOG.info(String.format("%s PARAMS: [userId: %s , companyCode: %s ]", idOperation, userId.toString(),
				companyCode));

		GenericOrderRepositoryInterface repository = genericOrderActionProvider.getRepository(companyCode);

		if (repository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}

		UserDto userDto = repository.getPersonalUserConfigurationById(userId, idOperation);

		LOG.info(String.format("%s RETURN PERSONAL-USER-CONFIGURATION", idOperation));

		return new ResponseModel(userDto);
	}

	@Override
	public ResponseModel getStatusByCode(String companyCode, String branchCode, String statusCode, String idOperation) {

		LOG.info(String.format("%s INIT getStatusByCode()", idOperation));
		LOG.info(String.format("%s PARAMS:[ statusCode: %s , companyCode: %s , branchCode: %s ] ", idOperation,
				statusCode, companyCode, branchCode));

		GenericOrderRepositoryInterface repository = genericOrderActionProvider.getRepository(companyCode);
		if (repository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		StatusDto statusDto = repository.getStatusByCode(branchCode, statusCode, idOperation);
		LOG.info(String.format("%s RETURN STATUS: %s", idOperation, statusCode.toString()));
		return new ResponseModel(statusDto);

	}

	@Override
	public ResponseModel saveRecordInOrderHistory(OrderHistoryDto orderHistoryDto, String companyCode,
			String idOperation) {

		LOG.info(String.format("%s INIT saveRecordInOrderHistory() ", idOperation));
		LOG.info(String.format("%s PARAMS: [orderHistoryDto: %s , companyCode: %s] ", idOperation,
				orderHistoryDto.getAction(), companyCode));

		GenericOrderRepositoryInterface repository = genericOrderActionProvider.getRepository(companyCode);
		if (repository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		OrderHistoryDto orderHistoryDtoCreated = repository.createRecordToOrderHistory(orderHistoryDto, idOperation);
		LOG.info(String.format("%s RETURN ORDER-HISTORY: %s", idOperation, orderHistoryDtoCreated.toString()));
		return new ResponseModel(orderHistoryDtoCreated);

	}

	@Override
	public ResponseModel generateTicket(OrderDto orderDto, UserDto userDto, String companyCode, String branch,
			String orderType, String idOperation) {

		ResponseModel responseModel = new ResponseModel();

		LOG.info(String.format("%s INIT generateTicket() ", idOperation));
		LOG.info(String.format("%s PARAMS: [orderDto: %s , companyCode: %s , branch: %s , orderType: %s ] ",
				idOperation, orderDto.toString(), companyCode, branch, orderType));

		GenericOrderRepositoryInterface repository = genericOrderActionProvider.getRepository(companyCode);

		if (repository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}

		responseModel = repository.generateTicket(orderDto, userDto, branch, orderType, idOperation);

		return responseModel;

	}

	@Override
	public ResponseModel generateDocument(OrderDto orderDto, BranchDto branchDto, UserDto userDto, String companyCode,
			String orderType, String idOperation) {

		LOG.info(String.format("%s INIT generateDocument() ", idOperation));
		LOG.info(String.format(
				"%s PARAMS: [orderDto: %s , branchDto: %s , userDto: %s , companyCode: %s , orderType: %s ] ",
				idOperation, orderDto.toString(), branchDto.toString(), userDto, companyCode, orderType));

		GenericOrderRepositoryInterface repository = genericOrderActionProvider.getRepository(companyCode);

		if (repository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}

		ResponseModel responseModel = repository.generateDocument(orderDto, branchDto, userDto, idOperation);

		return responseModel;
	}

	@Override
	public ResponseModel generateQuoteDocument(OrderDto orderDto, BranchDto branchDto, UserDto userDto,
			String companyCode, String orderType, String idOperation) {

		LOG.info(String.format("%s INIT generateQuoteDocument() ", idOperation));
		LOG.info(String.format(
				"%s PARAMS: [orderDto: %s , branchDto: %s , userDto: %s , companyCode: %s , orderType: %s ] ",
				idOperation, orderDto.toString(), branchDto.toString(), userDto, companyCode, orderType));

		GenericOrderRepositoryInterface repository = genericOrderActionProvider.getRepository(companyCode);

		if (repository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}

		ResponseModel responseModel = repository.generateQuoteDocument(orderDto, branchDto, userDto, idOperation);

		return responseModel;
	}

	@Transactional
	@Override
	public ResponseModel createOrderOD(SaleOrderDto saleOrderDto, String companyCode, String idOperation) {

		LOG.info(String.format("%s INIT createOrder()", idOperation));
		LOG.info(String.format("%s PARAMS:[ saleOrderDto: %s , companyCode: %s ] ", idOperation,
				saleOrderDto.toString(), companyCode));

		GenericOrderRepositoryInterface repository = genericOrderActionProvider.getRepository(companyCode);

		if (repository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}

		SaleOrderDto saleOrderDtoCreated = repository.createOrderDitect(saleOrderDto, idOperation);

		return new ResponseModel(saleOrderDtoCreated);

	}

	@Transactional
	@Override
	public ResponseModel viewSaleOrderDetailByOrderNumber(BigDecimal orderNumber, String companyCode, String branch,
			String idOperation) {

		LOG.info(String.format("%s INIT viewSaleOrderDetailByOrderNumber()", idOperation));
		LOG.info(String.format("%s PARAMS:[ orderNumber: %s , companyCode: %s ] ", idOperation, orderNumber.toString(),
				companyCode));

		GenericOrderRepositoryInterface repository = genericOrderActionProvider.getRepository(companyCode);

		if (repository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}

		SaleOrderDto saleOrderDto = repository.viewOrderDirectByOrderNumber(orderNumber, companyCode, branch,
				idOperation);

		return new ResponseModel(saleOrderDto);

	}

	@Transactional
	@Override
	public ResponseModel updateOrderOD(SaleOrderDto saleOrderDto, String companyCode, String idOperation) {

		LOG.info(String.format("%s INIT updateOrderOD()", idOperation));
		LOG.info(String.format("%s PARAMS:[ saleOrderDto: %s , companyCode: %s ] ", idOperation,
				saleOrderDto.toString(), companyCode));

		GenericOrderRepositoryInterface repository = genericOrderActionProvider.getRepository(companyCode);

		if (repository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}

		SaleOrderDto saleOrderUpdatedDto = repository.updateOrderDirect(saleOrderDto, idOperation);

		return new ResponseModel(saleOrderUpdatedDto);

	}

	@Transactional
	@Override
	public ResponseModel cancelDirectOrderByOrderNumber(BigDecimal orderNumber, String companyCode, String branch,
			String idOperation) {

		LOG.info(String.format("%s INIT cancelDirectOrderByOrderNumber()", idOperation));
		LOG.info(String.format("%s PARAMS:[ orderNumber: %s , companyCode: %s ] ", idOperation, orderNumber.toString(),
				companyCode));

		GenericOrderRepositoryInterface repository = genericOrderActionProvider.getRepository(companyCode);

		if (repository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}

		SaleOrderDto saleOrderDto = repository.cancelOrderDirect(orderNumber, companyCode, branch, idOperation);

		return new ResponseModel(saleOrderDto);
	}

	@Override
	public ResponseModel getOrderConfigurationByOrderCodeAndCompanyCode(String orderCode, String companyCode,
			String idOperation) {

		LOG.info(String.format("%s INIT getOrderConfigurationByOrderCodeAndCompanyCode() ", idOperation));
		LOG.info(String.format("%s PARAMS: [orderCode: %s , companyCode: %s ]", idOperation, orderCode, companyCode));

		GenericOrderRepositoryInterface repository = genericOrderActionProvider.getRepository(companyCode);

		if (repository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}

		OrderConfigurationDto orderConfigurationDto = repository.getOrderConfigurationByOrderCode(orderCode,
				idOperation);

		LOG.info(String.format("%s RETURN ORDER-CONFIGURATION", idOperation));

		return new ResponseModel(orderConfigurationDto);

	}

	@Transactional
	@Override
	public ResponseModel getBranchConfigurationByBranchCodeAndCompanyCode(String branchCode, String companyCode,
			String idOperation) {

		LOG.info(String.format("%s INIT getBranchConfigurationByBranchCodeAndCompanyCode() ", idOperation));
		LOG.info(String.format("%s PARAMS: [branchCode: %s , companyCode: %s ]", idOperation, branchCode, companyCode));

		GenericOrderRepositoryInterface repository = genericOrderActionProvider.getRepository(companyCode);

		if (repository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}

		BranchDto branchDto = repository.getBranchByCodeAndCompanyCode(branchCode, companyCode, idOperation);

		LOG.info(String.format("%s RETURN BRANCH", idOperation));

		return new ResponseModel(branchDto);

	}

	@Transactional
	@Override
	public ResponseModel updateQuoteOrderToConverted(String orderCode, BigDecimal orderNumber, String companyCode,
			String idOperation) {

		LOG.info(String.format("%s INIT updateQuoteOrderToConverted() ", idOperation));
		LOG.info(String.format("%s PARAMS: [orderCode: %s , orderNumber: %s , companyCode: %s ]", idOperation,
				orderCode, orderNumber.toString(), companyCode));

		GenericOrderRepositoryInterface repository = genericOrderActionProvider.getRepository(companyCode);

		if (repository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}

		boolean updateQuoteOrder = repository.updateConvertionStatusByOrderNumber(orderCode, orderNumber, idOperation);

		LOG.info(String.format("%s RETURN RESULT UPDATE %b", idOperation, updateQuoteOrder));

		return new ResponseModel(updateQuoteOrder);
	}

}