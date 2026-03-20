package mx.com.endtoend.domain.orders.factories;

import java.math.BigDecimal;
import java.util.List;

import mx.com.endtoend.domain.orders.dto.GenericActionControllOrderDto;
import mx.com.endtoend.domain.orders.dto.GenericSerchParamsOrderDto;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;
import mx.com.endtoend.smart.bussiness.model.orders.dto.OrderDto;

public interface OrderInterface {

	ResponseModel createOrder(OrderDto orderDto, Boolean isConvertion, Boolean mailUsuario);

	ResponseModel updateOrder(OrderDto orderDto, Boolean mailUsuario);

	ResponseModel getOrderToUpdate(BigDecimal orderNumber, String orderType);

	ResponseModel cancelUpdateOrder(BigDecimal orderNumber, String email, String orderType);

	ResponseModel approveOrder(GenericActionControllOrderDto genericActionControllOrderDto);

	ResponseModel cancelOrder(GenericActionControllOrderDto genericActionControllOrderDto);

	ResponseModel getOrderListByParams(GenericSerchParamsOrderDto params, String email);

	ResponseModel getAllOrdersListByCompany(GenericSerchParamsOrderDto params, String email);

	ResponseModel viewOderDetailByOrderNumber(BigDecimal orderNumber, String orderType);

	ResponseModel viewHistoricalOrderDetailByOrderNumber(BigDecimal orderNumber, String orderType);

	ResponseModel sendDocumentByEmailAndCompanyCode(List<String> emails, BigDecimal orderNumber, String orderType);

	ResponseModel generateTicketByOrderNumberAndOrderType(BigDecimal orderNumber, String orderType);

	ResponseModel generateDocumentByOrderNumberAndOrderType(BigDecimal orderNumber, String orderType);

	ResponseModel convertQuoteOrderToSaleOrder(OrderDto orderDto, String orderCode, Boolean mailUsuario);

}
