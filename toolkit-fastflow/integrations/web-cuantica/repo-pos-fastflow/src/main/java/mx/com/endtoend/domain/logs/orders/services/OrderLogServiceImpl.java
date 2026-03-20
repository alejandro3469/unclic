package mx.com.endtoend.domain.logs.orders.services;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.com.endtoend.domain.logs.orders.dto.OrderSummaryLogDto;
import mx.com.endtoend.domain.logs.orders.ports.OrderLogPersistencePort;
import mx.com.endtoend.domain.logs.orders.ports.OrderLogServicePort;
import mx.com.endtoend.smart.bussiness.model.orders.dto.OrderDto;

public class OrderLogServiceImpl implements OrderLogServicePort {

	private OrderLogPersistencePort orderLogPersistencePort;

	public OrderLogServiceImpl(OrderLogPersistencePort orderLogPersistencePort) {
		this.orderLogPersistencePort = orderLogPersistencePort;
	}

	private final static Logger LOG = LoggerFactory.getLogger(OrderLogServiceImpl.class);

	@Override
	public Boolean generateOrderChangeLog(String companyCode, String userLogged, OrderDto orderSaved,
			OrderDto orderUpdated) {
		try {
			LOG.info("INIT generateOrderChangeLog()");
			OrderSummaryLogDto orderSummaryLogDto = new OrderSummaryLogDto();
			orderSummaryLogDto.setUser(userLogged);
			orderSummaryLogDto.setUpdatedDate(new Date());
			orderSummaryLogDto.setOrderNumber(orderSaved.getOrderNumber());
			orderSummaryLogDto.setOrderCode(orderSaved.getOrderCode());
			orderSummaryLogDto.setOrderSaved(orderSaved);
			orderSummaryLogDto.setOrderUpdated(orderUpdated);
			orderLogPersistencePort.saveOrderLog(orderSummaryLogDto, companyCode);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
