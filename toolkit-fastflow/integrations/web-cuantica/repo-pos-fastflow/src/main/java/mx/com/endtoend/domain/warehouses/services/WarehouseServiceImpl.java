package mx.com.endtoend.domain.warehouses.services;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.com.endtoend.domain.warehouses.dto.WarehouseDto;
import mx.com.endtoend.domain.warehouses.ports.api.WarehouseServicePort;
import mx.com.endtoend.domain.warehouses.ports.spi.WarehousePersistencePort;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

public class WarehouseServiceImpl implements WarehouseServicePort{

	private WarehousePersistencePort warehousePersistencePort;
	
	public WarehouseServiceImpl(WarehousePersistencePort warehousePersistencePort) {
		this.warehousePersistencePort=warehousePersistencePort;
	}
	
	private final static Logger LOG = LoggerFactory.getLogger(WarehouseServiceImpl.class);

	@Override
	public ResponseModel findAllByCompanyCode(String companyCode) {
		LOG.info(String.format(" INIT findAllByCompanyCode()"));
		LOG.info(String.format(" PARAMS: [ companyCode: %s ]", companyCode));
		
		List<WarehouseDto> warehouseDtoList = warehousePersistencePort.findByCompanyCode(companyCode);
		
		if(warehouseDtoList!=null) {
			LOG.info("SE RECUPERA LISTA DE ALMACENES PARA LA COMPAÑIA: " + companyCode);
			return new ResponseModel(warehouseDtoList);
		}else {
			LOG.error("ERROR AL  RECUPERA LISTA DE ALMACENES PARA LA COMPAÑIA: " + companyCode);
			List<WarehouseDto> rep = new ArrayList<WarehouseDto>();
			return new ResponseModel(rep);
			
		}
		

	}	
}