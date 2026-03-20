package mx.com.endtoend.domain.warehouses.ports.spi;

import java.util.List;

import mx.com.endtoend.domain.warehouses.dto.WarehouseDto;

public interface WarehousePersistencePort {
	
	List<WarehouseDto> findByCompanyCode(String companyCode);

}
