package mx.com.endtoend.infrastructure.logs.orders;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import mx.com.endtoend.domain.logs.orders.dto.OrderSummaryLogDto;
import mx.com.endtoend.domain.logs.orders.ports.OrderLogPersistencePort;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.GlobalError;

public class OrderLogPersistenceImpl implements OrderLogPersistencePort {

	@Autowired
	private OrderLogRepositoryFactory orderLogRepositoryFactory;

	private final Logger LOG = LoggerFactory.getLogger(OrderLogPersistenceImpl.class);

	@Override
	public void saveOrderLog(OrderSummaryLogDto oderOrderSummaryLogDto, String companyCode) {
		LOG.info("INIT saveOrderLog()");
		GenericOrderLogPersistenceInterface orderLogRepository = orderLogRepositoryFactory
				.getRepositoryByCompanyCode(companyCode);
		if (orderLogRepository == null) {
			LOG.error("AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ");
			throw new GlobalError();
		}
		orderLogRepository.saveOrderLog(oderOrderSummaryLogDto);
	}

}
