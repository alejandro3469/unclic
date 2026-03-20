package mx.com.endtoend.infrastructure.orderConfigurations.calzada.fragua.business;

import mx.com.endtoend.infrastructure.orderConfigurations.common.business.BaseOrderConfiguration;
import org.springframework.stereotype.Service;

import mx.com.endtoend.infrastructure.orderConfigurations.calzada.fragua.repositories.OrderConfigurationFraguaRepository;

/**
 * 
 * @author ddcasas
 *
 */

@Service
public class OrderConfigurationSFraguaRepository extends BaseOrderConfiguration {

	public OrderConfigurationSFraguaRepository(OrderConfigurationFraguaRepository _orderConfigurationFraguaRepository) {
		super(OrderConfigurationSFraguaRepository.class,
				_orderConfigurationFraguaRepository);
	}
}
