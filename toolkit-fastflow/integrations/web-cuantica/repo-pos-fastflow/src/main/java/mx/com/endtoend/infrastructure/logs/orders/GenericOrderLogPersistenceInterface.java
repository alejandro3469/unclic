package mx.com.endtoend.infrastructure.logs.orders;

import mx.com.endtoend.domain.logs.orders.dto.OrderSummaryLogDto;

public interface GenericOrderLogPersistenceInterface {

	void saveOrderLog(OrderSummaryLogDto oderOrderSummaryLogDto);

}
