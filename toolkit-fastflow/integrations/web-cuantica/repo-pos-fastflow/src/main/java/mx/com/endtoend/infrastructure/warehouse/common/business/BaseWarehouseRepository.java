package mx.com.endtoend.infrastructure.warehouse.common.business;

import mx.com.endtoend.domain.warehouses.dto.WarehouseDto;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.GlobalError;
import mx.com.endtoend.infrastructure.warehouse.common.repository.WarehouseRepository;
import mx.com.endtoend.infrastructure.warehouse.common.converters.BaseWarehouseConverter;
import mx.com.endtoend.infrastructure.warehouse.common.repository.BaseF0006Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class BaseWarehouseRepository implements WarehouseRepository {

    protected final BaseF0006Repository f0006Repository;
    protected final BaseWarehouseConverter warehouseConverter;
    private final Logger LOG;

    private final String warehouse;
    private static final String warehouseType = "OP";

    public BaseWarehouseRepository(Class<?> loggerClass, String _warehouse,
                                   BaseF0006Repository f0006Repository, BaseWarehouseConverter warehouseConverter) {
        LOG = LoggerFactory.getLogger(loggerClass);
        warehouse = _warehouse;
        this.f0006Repository = f0006Repository;
        this.warehouseConverter = warehouseConverter;
    }

    @Override
    public List<WarehouseDto> findAll() {
        LOG.info("INIT findAll()");
        LOG.info("PARAMS: [warehouse={}, warehouseType={}]", warehouse, warehouseType);
        try {
            List<WarehouseDto> warehouseDtoList = warehouseConverter.F0006EntityListToWarehouseDtoList(
                    f0006Repository.findAllWarehouseByMCCOAndMCSTYL(warehouse, warehouseType));
            LOG.info("WAREHOUSE-LIST size: {}", warehouseDtoList.size());
            return warehouseDtoList;
        } catch (Exception e) {
            LOG.error("ERROR in findAll() - Exception: {}", e.getMessage(), e);
            throw new GlobalError();
        }

    }
}
