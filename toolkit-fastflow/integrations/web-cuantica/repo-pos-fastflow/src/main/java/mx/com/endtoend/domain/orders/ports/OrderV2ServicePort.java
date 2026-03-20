package mx.com.endtoend.domain.orders.ports;

import java.math.BigDecimal;
import java.util.List;

import mx.com.endtoend.domain.orders.dto.CustomInterfaceOrderParams;
import mx.com.endtoend.domain.orders.dto.GenericActionControllOrderDto;
import mx.com.endtoend.domain.orders.dto.GenericSerchParamsOrderDto;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;
import mx.com.endtoend.smart.bussiness.model.orders.dto.OrderDto;

public interface OrderV2ServicePort {

	ResponseModel createOrder(CustomInterfaceOrderParams customInterfaceOrderParams, OrderDto orderDto, String method,
			Boolean isConvertion, Boolean mailUsuario);

	ResponseModel updateOrder(CustomInterfaceOrderParams customInterfaceOrderParams, OrderDto orderDto, String method,
			Boolean mailUsuario);

	ResponseModel getOrderToUpdate(CustomInterfaceOrderParams customInterfaceOrderParams, BigDecimal orderNumber,
			String orderType, String method);

	ResponseModel cancelUpdateOrder(CustomInterfaceOrderParams customInterfaceOrderParams, BigDecimal orderNumber,
			String email, String orderType, String method);

	ResponseModel approveOrder(CustomInterfaceOrderParams customInterfaceOrderParams,
			GenericActionControllOrderDto genericActionControllOrderDto, String method);

	ResponseModel cancelOrder(CustomInterfaceOrderParams customInterfaceOrderParams,
			GenericActionControllOrderDto genericActionControllOrderDto, String method);

	ResponseModel getOrderListByParams(CustomInterfaceOrderParams customInterfaceOrderParams,
			GenericSerchParamsOrderDto params, String email, String method);

    ResponseModel getAllOrdersListByCompany(CustomInterfaceOrderParams customInterfaceOrderParams,
                                            GenericSerchParamsOrderDto params, String email, String method);

    ResponseModel viewOderDetailByOrderNumber(CustomInterfaceOrderParams customInterfaceOrderParams,
                                              BigDecimal orderNumber, String orderType, String method);

	ResponseModel viewHistoricalOrderDetailByOrderNumber(CustomInterfaceOrderParams customInterfaceOrderParams,
			BigDecimal orderNumber, String orderType, String method);

	ResponseModel sendDocumentByEmailAndCompanyCode(CustomInterfaceOrderParams customInterfaceOrderParams,
			List<String> emails, BigDecimal orderNumber, String orderType, String method);

	ResponseModel generateTicketByOrderNumberAndOrderType(CustomInterfaceOrderParams customInterfaceOrderParams,
			BigDecimal orderNumber, String orderType, String method);

	ResponseModel generateDocumentByOrderNumberAndOrderType(CustomInterfaceOrderParams customInterfaceOrderParams,
			BigDecimal orderNumber, String orderType, String method);

	ResponseModel convertQuoteOrderToSaleOrder(CustomInterfaceOrderParams customInterfaceOrderParams, OrderDto orderDto,
			String orderCode, String method, Boolean mailUsuario);

}
