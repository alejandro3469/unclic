package mx.com.endtoend.infrastructure.warehouse.carredana.zapata.business;

import mx.com.endtoend.infrastructure.warehouse.carredana.zapata.repositories.F0006ZapataRepository;
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
public class WarehouseCZapataRepository extends BaseWarehouseRepository {

	private static final String warehouse = "00006";

	@Autowired
	public WarehouseCZapataRepository(F0006ZapataRepository _f0006Repository,
									  BaseWarehouseConverter _warehouseConverter){
		super(WarehouseCZapataRepository.class,warehouse,
				_f0006Repository,_warehouseConverter);
	}
}