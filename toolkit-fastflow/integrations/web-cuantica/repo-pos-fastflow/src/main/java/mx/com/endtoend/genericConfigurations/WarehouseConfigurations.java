package mx.com.endtoend.genericConfigurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import mx.com.endtoend.domain.warehouses.ports.api.WarehouseServicePort;
import mx.com.endtoend.domain.warehouses.ports.spi.WarehousePersistencePort;
import mx.com.endtoend.domain.warehouses.services.WarehouseServiceImpl;
import mx.com.endtoend.infrastructure.warehouse.common.adapter.WarehouseJpaAdapter;

/**
 * 
 * @author ddcasas
 *
 */

@Configuration
public class WarehouseConfigurations {

	@Bean
	public WarehousePersistencePort warehousePersistence() {
		return new WarehouseJpaAdapter();
	}
	
	@Bean
	public WarehouseServicePort warehouseServicePort() {
		return new WarehouseServiceImpl(warehousePersistence());
	}
	
}
