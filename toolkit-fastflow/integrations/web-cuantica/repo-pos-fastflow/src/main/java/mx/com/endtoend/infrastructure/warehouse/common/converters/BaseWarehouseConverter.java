package mx.com.endtoend.infrastructure.warehouse.common.converters;

import mx.com.endtoend.domain.warehouses.dto.WarehouseDto;
import mx.com.endtoend.infrastructure.warehouse.common.entities.F0006;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BaseWarehouseConverter {
    public WarehouseDto F0006EntityToWarehouseDto(F0006 f0006) {

        WarehouseDto warehouseDto = new WarehouseDto();

        warehouseDto.setCode(f0006.getMcmcu().trim());
        warehouseDto.setName(f0006.getMcdl01());

        return warehouseDto;
    }

    public List<WarehouseDto> F0006EntityListToWarehouseDtoList(List<F0006> f0006EntityList){
        List<WarehouseDto> warehouseDtoList = new ArrayList<WarehouseDto>();
        for (F0006 f0006 : f0006EntityList) {
            warehouseDtoList.add(F0006EntityToWarehouseDto(f0006));
        }
        return warehouseDtoList;
    }
}
