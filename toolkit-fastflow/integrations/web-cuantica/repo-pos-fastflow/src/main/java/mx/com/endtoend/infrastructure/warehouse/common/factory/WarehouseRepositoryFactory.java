package mx.com.endtoend.infrastructure.warehouse.common.factory;

import mx.com.endtoend.infrastructure.warehouse.common.repository.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mx.com.endtoend.infrastructure.commons.constants.CompanyCodes;
import mx.com.endtoend.infrastructure.warehouse.calzada.business.WarehouseCalzadaRepository;
import mx.com.endtoend.infrastructure.warehouse.calzada.fragua.business.WarehouseFraguaRepository;
import mx.com.endtoend.infrastructure.warehouse.carredana.business.WarehouseCarredanaRepository;
import mx.com.endtoend.infrastructure.warehouse.ferresamano.business.WarehouseCFSamanoRepository;
import mx.com.endtoend.infrastructure.warehouse.carredana.zapata.business.WarehouseCZapataRepository;
import mx.com.endtoend.infrastructure.warehouse.demo.business.WarehouseDemoRepository;

@Component
public class WarehouseRepositoryFactory {

	@Autowired(required = false)
	private WarehouseCalzadaRepository warehouseRepositoryCalzada;

	@Autowired(required = false)
	private WarehouseFraguaRepository warehouseFraguaRepository;

	@Autowired(required = false)
	private WarehouseCarredanaRepository warehouseCarredanaRepository;

	@Autowired(required = false)
	private WarehouseCZapataRepository warehouseCZapataRepository;

	@Autowired(required = false)
	private WarehouseCFSamanoRepository warehouseCFSamanoRepository;

	@Autowired(required = false)
	private WarehouseDemoRepository warehouseDemoRepository;

	public WarehouseRepository getRepository(String companyCode) {

		CompanyCodes value = CompanyCodes.valueOf(companyCode);
		switch (value) {

		case FCAL:
			return warehouseRepositoryCalzada;

		case CFRA:
			return warehouseFraguaRepository;

		case FCAR:
			return warehouseCarredanaRepository;

		case CZAP:
			return warehouseCZapataRepository;

		case CFSA:
			return warehouseCFSamanoRepository;

		case DEMO:
			return warehouseDemoRepository;

		default:
			return null;
		}
	}

}
