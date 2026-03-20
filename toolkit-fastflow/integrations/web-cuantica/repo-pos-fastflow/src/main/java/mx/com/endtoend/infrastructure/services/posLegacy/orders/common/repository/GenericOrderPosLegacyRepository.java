package mx.com.endtoend.infrastructure.services.posLegacy.orders.common.repository;

import java.math.BigDecimal;

import mx.com.endtoend.smart.bussiness.model.orders.dto.OrderDto;
import mx.com.endtoend.smart.bussiness.model.orders.dto.SaleOrderDto;

public interface GenericOrderPosLegacyRepository {

	void createOrder(String companyCode, OrderDto orderDto, String idOperation);
	
	void updateOrder(String companyCode, OrderDto orderDto, String idOperation);
	
	//IMPLEMENTACION CON RABBITMQ
	void sendOrderToSave(OrderDto orderDto);
	
	void updateStatusOrderActive(BigDecimal orderNumber, boolean status, String orderType, String idOperation);
	
	//IMPLEMENTACION CON RABBITMQ	
	void sendOrderToChangeStatus(BigDecimal orderNumber, boolean status, String orderType, String idOperation);

	void cancelOrder(BigDecimal orderNumber, String orderType, String idOperation);
	
	//IMPLEMENTACION CON RABBITMQ	
	void sendOrderToCancelProcess(BigDecimal orderNumber, String orderType, String idOperation);

	void approveOrder(String statusOrder, BigDecimal orderNumber, String orderType, String idOperation);
	
	//IMPLEMENTACION CON RABBITMQ	
	void sendOrderToApproveProcess(String statusOrder, BigDecimal orderNumber, String orderType, String idOperation);

	OrderDto getOrderDetailByOrderNumberAndOrderType(BigDecimal orderNumber, String orderType, String idOperation);

	void createSaleOD(SaleOrderDto saleOrderDto, String idOperation);

	void updateSaleOD(SaleOrderDto saleOrderDto, String idOperation);
	
	//IMPLEMENTACION CON RABBITMQ	
	void sendOrderODToSave(SaleOrderDto saleOrderDto);

}
