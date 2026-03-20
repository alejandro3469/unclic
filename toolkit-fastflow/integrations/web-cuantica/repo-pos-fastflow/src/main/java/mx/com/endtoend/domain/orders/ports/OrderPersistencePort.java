package mx.com.endtoend.domain.orders.ports;

import java.math.BigDecimal;

import mx.com.endtoend.domain.branch.dto.BranchDto;
import mx.com.endtoend.domain.orders.dto.GenericSerchParamsOrderDto;
import mx.com.endtoend.domain.users.dto.UserDto;
import mx.com.endtoend.smart.bussiness.model.catalogue.dto.StatusDto;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;
import mx.com.endtoend.smart.bussiness.model.orders.dto.OrderDto;
import mx.com.endtoend.smart.bussiness.model.orders.dto.OrderHistoryDto;
import mx.com.endtoend.smart.bussiness.model.orders.dto.SaleOrderDto;

public interface OrderPersistencePort {

	ResponseModel createOrder(OrderDto orderDto, String companyCode, String idOperation);

	ResponseModel updateOrder(OrderDto orderDto, String companyCode, String idOperation);

	ResponseModel updateStatusOrderByOrderNumberAndOrderCodeAndCompanyCode(BigDecimal orderNumber, String orderCode,
			StatusDto status, BigDecimal pendingPayment, String companyCode, String idOperation);

	ResponseModel getOrderToUpdate(BigDecimal orderNumber, String orderType, String branchCode, String companyCode,
			String idOperation);

	ResponseModel cancelUpdateOrder(BigDecimal orderNumber, String orderType, String branchCode, String companyCode,
			String idOperation);

	ResponseModel cancelOrder(OrderDto orderDto, String companyCode, String branch, String method, String idOperation);

	ResponseModel approveOrderByOrderNumberAndOrderType(String orderType, BigDecimal orderNumber, String companyCode,
			String branch, String idOperation);

	ResponseModel getOrderListByParams(GenericSerchParamsOrderDto params, String companyCode, String branch,
			String idOperation);

    ResponseModel getAllOrdersListByCompany(GenericSerchParamsOrderDto params, String companyCode, String branch,
                                            String idOperation);

    ResponseModel vieworderDetailByOrderNumber(BigDecimal orderNumber, String orderCode, String companyCode,
                                               String idOperation);

	ResponseModel viewHistoricalOrderDetailByOrderNumber(BigDecimal orderNumber, String companyCode, String branch,
			String idOperation);

	ResponseModel getUserConfigurationByBranchAndEmail(String branchCode, String email, String companyCode,
			String idOperation);

	ResponseModel getPersonalUserConfigurationByUserIdAndCompanyCode(Long userId, String companyCode,
			String idOperation);

	ResponseModel saveRecordInOrderHistory(OrderHistoryDto orderHistoryDto, String companyCode, String idOperation);

	ResponseModel getStatusByCode(String companyCode, String branchCode, String statusCode, String idOperation);

	ResponseModel generateTicket(OrderDto orderDto, UserDto userDto, String companyCode, String branch,
			String orderType, String idOperation);

	ResponseModel generateDocument(OrderDto orderDto, BranchDto branchDto, UserDto userDto, String companyCode,
			String orderType, String idOperation);

	ResponseModel generateQuoteDocument(OrderDto orderDto, BranchDto branchDto, UserDto userDto, String companyCode,
			String orderType, String idOperation);

	ResponseModel getOrderConfigurationByOrderCodeAndCompanyCode(String orderCode, String companyCode,
			String idOperation);

	ResponseModel getBranchConfigurationByBranchCodeAndCompanyCode(String branchCode, String companyCode,
			String idOperation);

	ResponseModel updateQuoteOrderToConverted(String orderCode, BigDecimal orderNumber, String companyCode,
			String idOperation);

	// ADMINISTRACION DE ORDEN DIRECTA
	ResponseModel createOrderOD(SaleOrderDto saleOrderDto, String companyCode, String idOperation);

	ResponseModel updateOrderOD(SaleOrderDto saleOrderDto, String companyCode, String idOperation);

	ResponseModel cancelDirectOrderByOrderNumber(BigDecimal orderNumber, String companyCode, String branch,
			String idOperation);

	ResponseModel viewSaleOrderDetailByOrderNumber(BigDecimal orderNumber, String companyCode, String branch,
			String idOperation);

}
