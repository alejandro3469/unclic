package mx.com.endtoend.infrastructure.orders.common.repository;

import java.math.BigDecimal;
import java.util.List;

import mx.com.endtoend.domain.branch.dto.BranchDto;
import mx.com.endtoend.domain.orders.dto.GenericSerchParamsOrderDto;
import mx.com.endtoend.domain.userConfigurations.dto.EmployeeDto;
import mx.com.endtoend.domain.users.dto.UserDto;
import mx.com.endtoend.smart.bussiness.model.catalogue.dto.StatusDto;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;
import mx.com.endtoend.smart.bussiness.model.orders.dto.OrderConfigurationDto;
import mx.com.endtoend.smart.bussiness.model.orders.dto.OrderDto;
import mx.com.endtoend.smart.bussiness.model.orders.dto.OrderHistoryDto;
import mx.com.endtoend.smart.bussiness.model.orders.dto.SaleOrderDto;

import javax.transaction.Transactional;

public interface GenericOrderRepositoryInterface {

	/**
	 * CONJUNTO DE OPERACIONES COMUNES A TODAS LAS ORDENES
	 * 
	 */

	OrderDto createOrder(OrderDto orderDto, String idOperation);

	OrderDto updateOrder(OrderDto orderDto, String idOperation);

	OrderDto updateStatusOrderByOrderNumberAndOrderCode(BigDecimal orderNumber, String orderCode, StatusDto status,
			BigDecimal pendingPayment, String idOperation);

	OrderDto cancelOrder(OrderDto orderDto, String idOperation);

	OrderDto approveOrder(String orderCode, BigDecimal orderNumber, String idOperation);

	OrderDto viewOrderDetailByOrderNumber(BigDecimal orderNumber, String orderCode, String companyCode, String branch,
			String idOperation);

	List<OrderHistoryDto> viewHistoricalDetailOrderByOrderNumber(BigDecimal orderNumber, String companyCode,
			String branch, String idOperation);

	List<OrderDto> getOrderList(GenericSerchParamsOrderDto params, String companyCode, String branch,
			String idOperation);

    @Transactional
    List<OrderDto> getAllOrdersListByCompany(GenericSerchParamsOrderDto params, String companyCode, String branch,
                                             String idOperation);

    EmployeeDto getUserConfiguration(String branchCode, String email, String idOperation);

	UserDto getPersonalUserConfigurationById(Long id, String idOperation);

	StatusDto getStatusByCode(String branchCode, String statusCode, String idOperation);

	OrderHistoryDto createRecordToOrderHistory(OrderHistoryDto orderHistoryDto, String idOperation);

	boolean getOrderToUpdate(BigDecimal orderNumber, String orderType, String idOperation);

	boolean cancelUpdateOrder(BigDecimal orderNumber, String orderType, String idOperation);

	OrderConfigurationDto getOrderConfigurationByOrderCode(String orderCode, String idOperation);

	ResponseModel generateTicket(OrderDto orderDto, UserDto userDto, String branch, String orderType,
			String idOperation);

	ResponseModel generateDocument(OrderDto orderDto, BranchDto branchDto, UserDto userDto, String idOperation);

	ResponseModel generateQuoteDocument(OrderDto orderDto, BranchDto branchDto, UserDto userDto, String idOperation);

	BranchDto getBranchByCodeAndCompanyCode(String branchCode, String companyCode, String idOperation);

	boolean updateConvertionStatusByOrderNumber(String orderCode, BigDecimal orderNumber, String idOperation);

	/**
	 * Conjunto de operaciones relacionadas a ordenes de venta directa
	 * 
	 */

	SaleOrderDto createOrderDitect(SaleOrderDto saleOrderDto, String idOperation);

	SaleOrderDto updateOrderDirect(SaleOrderDto saleOrderDto, String idOperation);

	SaleOrderDto cancelOrderDirect(BigDecimal orderNumber, String companyCode, String brancheCode, String idOperation);

	SaleOrderDto viewOrderDirectByOrderNumber(BigDecimal orderNumber, String companyCode, String brancheCode,
			String idOperation);

}
