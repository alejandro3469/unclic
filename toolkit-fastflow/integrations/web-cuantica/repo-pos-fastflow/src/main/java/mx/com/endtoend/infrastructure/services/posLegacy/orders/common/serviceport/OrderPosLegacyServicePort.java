package mx.com.endtoend.infrastructure.services.posLegacy.orders.common.serviceport;

import java.math.BigDecimal;

import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;
import mx.com.endtoend.smart.bussiness.model.orders.dto.OrderDto;
import mx.com.endtoend.smart.bussiness.model.orders.dto.SaleOrderDto;

public interface OrderPosLegacyServicePort {

	void createOrder(OrderDto orderDto, String companyCode, String idOperation);

	void updateOrder(OrderDto orderDto, String companyCode, String idOperation);

	void updateStatusOrderActive(BigDecimal orderNumber, boolean status, String orderType, String companyCode,
			String idOperation);

	void cancelOrder(BigDecimal orderNumber, String orderType, String companyCode, String idOperation);

	void approveOrder(String statusOrder, BigDecimal orderNumber, String orderType, String companyCode,
			String idOperation);

	ResponseModel getOrderDetailByOrderNumberAndOrderType(BigDecimal orderNumber, String orderType, String companyCode,
			String idOperation);

	void createSaleOD(SaleOrderDto saleOrderDto, String companyCode, String idOperation);

	void updteSaleOD(SaleOrderDto saleOrderDto, String companyCode, String idOperation);

}
