package mx.com.endtoend.infrastructure.warehouse.ferresamano.business;

import mx.com.endtoend.infrastructure.warehouse.ferresamano.repositories.F0006FsamaoRepository;
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
@ConditionalOnProperty(name = "app.ferresamano.oracle.enabled", havingValue = "true", matchIfMissing = false)
public class WarehouseCFSamanoRepository extends BaseWarehouseRepository {

	private static final String warehouse = "00010";
	@Autowired
	public WarehouseCFSamanoRepository(F0006FsamaoRepository _f0006Repository,
									   BaseWarehouseConverter _warehouseConverter){
		super(WarehouseCFSamanoRepository.class,warehouse,
				_f0006Repository,_warehouseConverter);
	}

}