package mx.com.endtoend.infrastructure.warehouse.demo.business;

import mx.com.endtoend.infrastructure.warehouse.demo.repositories.F0006DemoRepository;
import mx.com.endtoend.infrastructure.warehouse.common.business.BaseWarehouseRepository;
import mx.com.endtoend.infrastructure.warehouse.common.converters.BaseWarehouseConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

/**
 * Implementación concreta de warehouse para DEMO Company
 * Basado en la estructura de Carredana
 * 
 * @author Sistema
 *
 */

@Service
@ConditionalOnProperty(name = "app.demo.oracle.enabled", havingValue = "true", matchIfMissing = false)
public class WarehouseDemoRepository extends BaseWarehouseRepository {

	private static final String warehouse = "00001";
	
	@Autowired
	public WarehouseDemoRepository(F0006DemoRepository baseF0006Repository,
								   BaseWarehouseConverter baseWarehouseConverter){
		super(WarehouseDemoRepository.class, warehouse,
				baseF0006Repository, baseWarehouseConverter);
	}
}
