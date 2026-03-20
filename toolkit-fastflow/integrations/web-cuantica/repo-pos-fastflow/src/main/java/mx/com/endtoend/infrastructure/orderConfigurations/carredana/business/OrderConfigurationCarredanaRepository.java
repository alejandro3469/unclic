package mx.com.endtoend.infrastructure.orderConfigurations.carredana.business;

import mx.com.endtoend.infrastructure.orderConfigurations.calzada.fragua.business.OrderConfigurationSFraguaRepository;
import mx.com.endtoend.infrastructure.orderConfigurations.common.business.BaseOrderConfiguration;
import org.springframework.stereotype.Service;

import mx.com.endtoend.infrastructure.orderConfigurations.carredana.repositories.OrderConfigurationFCarRepository;

/**
 * 
 * @author ddcasas
 *
 */

@Service
public class OrderConfigurationCarredanaRepository extends BaseOrderConfiguration {

	public OrderConfigurationCarredanaRepository(OrderConfigurationFCarRepository _orderConfigurationFCarRepository) {
		super(OrderConfigurationCarredanaRepository.class,
				_orderConfigurationFCarRepository);
	}


}
