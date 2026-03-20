package mx.com.endtoend.domain.logs.orders.ports;

import mx.com.endtoend.smart.bussiness.model.orders.dto.OrderDto;

public interface OrderLogServicePort {

	Boolean generateOrderChangeLog(String companyCode, String userLogged, OrderDto orderSaved, OrderDto orderUpdated);
}
