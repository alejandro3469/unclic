package mx.com.endtoend.infrastructure.warehouse.carredana.business;

import mx.com.endtoend.infrastructure.warehouse.carredana.repositories.F0006CarredanaRepository;
import mx.com.endtoend.infrastructure.warehouse.common.business.BaseWarehouseRepository;
import mx.com.endtoend.infrastructure.warehouse.common.converters.BaseWarehouseConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

/**
 * 
 * @author ddcasas
 *
 */

@Service
@ConditionalOnProperty(name = "app.carredana.oracle.enabled", havingValue = "true", matchIfMissing = false)
public class WarehouseCarredanaRepository extends BaseWarehouseRepository {

	private static final String warehouse = "00003";
	@Autowired
	public WarehouseCarredanaRepository(F0006CarredanaRepository baseF0006Repository,
										BaseWarehouseConverter baseWarehouseConverter){
		super(WarehouseCarredanaRepository.class,warehouse,
				baseF0006Repository,baseWarehouseConverter);
	}
}