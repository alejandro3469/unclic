package mx.com.endtoend.infrastructure.orderConfigurations.ferresamano.business;

import mx.com.endtoend.infrastructure.orderConfigurations.common.business.BaseOrderConfiguration;
import org.springframework.stereotype.Service;
import mx.com.endtoend.infrastructure.orderConfigurations.ferresamano.repositories.OrderConfigurationFSamanoRepository;
/**
 * 
 * @author ddcasas
 *
 */

@Service
public class OrderConfigurationCFSamanoRepository extends BaseOrderConfiguration {

	public OrderConfigurationCFSamanoRepository(OrderConfigurationFSamanoRepository _orderConfigurationFSamanoRepository) {
		super(OrderConfigurationCFSamanoRepository.class, _orderConfigurationFSamanoRepository);
	}

}
