package mx.com.endtoend.infrastructure.warehouse.calzada.fragua.business;

import mx.com.endtoend.infrastructure.warehouse.calzada.fragua.repositories.F0006FraguaRepository;
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
public class WarehouseFraguaRepository extends BaseWarehouseRepository {

	private static final String warehouse = "00009";
	@Autowired
	public WarehouseFraguaRepository(F0006FraguaRepository _baseF0006Repository,
									 BaseWarehouseConverter _warehouseConverter){
		super(WarehouseFraguaRepository.class,warehouse,
				_baseF0006Repository,_warehouseConverter);
	}
}
