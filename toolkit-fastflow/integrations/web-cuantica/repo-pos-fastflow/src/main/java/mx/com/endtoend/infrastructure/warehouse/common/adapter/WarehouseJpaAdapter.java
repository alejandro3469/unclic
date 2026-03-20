package mx.com.endtoend.infrastructure.warehouse.common.adapter;

import java.util.List;

import mx.com.endtoend.infrastructure.warehouse.common.repository.WarehouseRepository;
import mx.com.endtoend.infrastructure.warehouse.common.factory.WarehouseRepositoryFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.com.endtoend.domain.warehouses.dto.WarehouseDto;
import mx.com.endtoend.domain.warehouses.ports.spi.WarehousePersistencePort;

@Service
public class WarehouseJpaAdapter implements WarehousePersistencePort{

	@Autowired
	private WarehouseRepositoryFactory factory;
	
	private final static Logger LOG = LoggerFactory.getLogger(WarehouseJpaAdapter.class);
	
	@Override
	public List<WarehouseDto> findByCompanyCode(String companyCode) {
		
		LOG.info(String.format(" INIT findAllByCompanyCode()"));
		LOG.info(String.format(" PARAMS: [ companyCode: %s ]", companyCode));
	
		WarehouseRepository repository = factory.getRepository(companyCode);
		
		if (repository == null) {
			LOG.error(String.format(" AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION FOR COMPANY: %s", companyCode));
			throw new mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.GlobalError();
		}
		
		List<WarehouseDto> warehouseDtoList = repository.findAll();
		
		if(warehouseDtoList != null ) {
			return warehouseDtoList;
			
		}else {
			
			return null;
		}
	}

}