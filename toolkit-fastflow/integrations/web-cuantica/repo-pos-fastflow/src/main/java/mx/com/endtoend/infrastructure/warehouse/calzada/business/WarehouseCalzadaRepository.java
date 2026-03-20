package mx.com.endtoend.infrastructure.warehouse.calzada.business;

import mx.com.endtoend.infrastructure.warehouse.calzada.repositories.F0006CalzadaRepository;
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
@ConditionalOnProperty(name = "app.calzadaOracle.enabled", havingValue = "true", matchIfMissing = false)
public class WarehouseCalzadaRepository extends BaseWarehouseRepository {

	private static final String warehouse = "00001";
	@Autowired
	public WarehouseCalzadaRepository(F0006CalzadaRepository _f0006Repository,
									  BaseWarehouseConverter _baseWarehouseConverter){
		super(WarehouseCalzadaRepository.class,warehouse,
				_f0006Repository,_baseWarehouseConverter);
	}

}
