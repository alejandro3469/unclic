package mx.com.endtoend.domain.logs.orders.ports;

import mx.com.endtoend.domain.logs.orders.dto.OrderSummaryLogDto;

public interface OrderLogPersistencePort {

	void saveOrderLog(OrderSummaryLogDto oderOrderSummaryLogDto, String companyCode);

}
