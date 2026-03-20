package mx.com.endtoend.domain.orders.factories;

import mx.com.endtoend.smart.bussiness.model.orders.dto.OrderDto;

public interface OrderBusinessInterface {

	OrderDto createOrder(OrderDto orderDto);

	OrderDto updateOrder(OrderDto orderDto, OrderDto orderSaved);

}
