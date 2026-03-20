package mx.com.endtoend.infrastructure.warehouse.common.repository;

import java.util.List;

import mx.com.endtoend.domain.warehouses.dto.WarehouseDto;

public interface WarehouseRepository {
	
	List<WarehouseDto> findAll();

}
