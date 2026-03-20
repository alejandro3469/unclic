package mx.com.endtoend.infrastructure.orderConfigurations.carredana.zapata.business;

import mx.com.endtoend.infrastructure.orderConfigurations.common.business.BaseOrderConfiguration;
import org.springframework.stereotype.Service;

import mx.com.endtoend.infrastructure.orderConfigurations.carredana.zapata.repositories.OrderConfigurationCZapataRepository;

/**
 * 
 * @author ddcasas
 *
 */

@Service
public class OrderConfigurationZapataRepository extends BaseOrderConfiguration {
	public OrderConfigurationZapataRepository(OrderConfigurationCZapataRepository _orderConfigurationCZapataRepository) {
		super(OrderConfigurationZapataRepository.class,_orderConfigurationCZapataRepository);
	}
}
