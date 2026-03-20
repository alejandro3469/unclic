package mx.com.endtoend.infrastructure.orderConfigurations.calzada.business;

import mx.com.endtoend.infrastructure.orderConfigurations.calzada.fragua.repositories.OrderConfigurationFraguaRepository;
import mx.com.endtoend.infrastructure.orderConfigurations.common.business.BaseOrderConfiguration;
import org.springframework.stereotype.Service;

import mx.com.endtoend.infrastructure.orderConfigurations.calzada.repositories.OrderConfigurationRepository;

/**
 * 
 * @author ddcasas
 *
 */

@Service
public class OrderConfigurationCalzadaRepository extends BaseOrderConfiguration {

	public OrderConfigurationCalzadaRepository(OrderConfigurationRepository _orderConfigurationCalzadaRepository){
		super(OrderConfigurationCalzadaRepository.class,
				_orderConfigurationCalzadaRepository);
	}

}
