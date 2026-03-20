package mx.com.endtoend.infrastructure.services.jde.orders.demo.business;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.GlobalError;
import mx.com.endtoend.infrastructure.services.jde.orders.common.repository.GenericOrderJdeRepository;
import mx.com.endtoend.infrastructure.services.jde.orders.demo.common.entities.F00021;
import mx.com.endtoend.infrastructure.services.jde.orders.demo.common.repository.F00021DemoRepository;
import mx.com.endtoend.infrastructure.articles.demo.oracle.repositories.F41002DemoRepository;
import mx.com.endtoend.infrastructure.articles.demo.oracle.repositories.F41021DemoRepository;
import mx.com.endtoend.infrastructure.warehouse.demo.repositories.F0006DemoRepository;
import mx.com.endtoend.infrastructure.articles.demo.oracle.entities.F41002;

/**
 * Implementación concreta de la interfaz GenericOrderJdeRepository para DEMO Company.
 * Basado en la estructura de Carredana
 * 
 * @author Sistema
 *
 */

@Service
@ConditionalOnProperty(name = "app.demo.oracle.enabled", havingValue = "true", matchIfMissing = false)
public class OrderDemoJdeRepository implements GenericOrderJdeRepository {

	@Autowired
	private F00021DemoRepository f00021Repository;

	@Autowired
	private F41002DemoRepository f41002Repository;

	@Autowired
	private F41021DemoRepository f41021Repository;

	@Autowired
	private F0006DemoRepository f0006Repository;

	private final Logger LOG = LoggerFactory.getLogger(OrderDemoJdeRepository.class);

	private String companyKey = "00001";

	@Override
	public BigDecimal getOrderNumberByOrderType(String orderType, String idOperation) {
		LOG.info(String.format("%s INIT getOrderNumberByOrderType()", idOperation));
		LOG.info(String.format("%s PARAMS[ orderType: %s ] ", idOperation, orderType));
		try {
			BigDecimal orderNumber;
			BigDecimal addConcecutive = new BigDecimal("1");
			F00021 f00021 = f00021Repository.findOrderNumberByCompanyCodeAndOrderType(companyKey, orderType);
			
			if (f00021 != null) {
				orderNumber = f00021.getNln001();
				f00021Repository.updateOrderNumberByCompanyCodeAndOrderType(companyKey, orderType,
						orderNumber.add(addConcecutive));
				LOG.info(String.format("%s ORDER-NUMBER FROM F00021: " + f00021.getNln001().toString(), idOperation));
			} else {
				// Para DEMO, retornamos un número por defecto si no existe en la BD
				orderNumber = new BigDecimal("10001");
				LOG.info(String.format("%s DEMO ORDER-NUMBER (default): " + orderNumber.toString(), idOperation));
			}
			
			LOG.info(String.format("%s RETURN ORDER-NUMBER", idOperation));
			return orderNumber;
		} catch (Exception e) {
			LOG.error(String.format("%s ERROR IN getOrderNumberByOrderType(). EXCEPTION: %s", idOperation,
					e.getMessage()));
			throw new GlobalError();
		}
	}

	@Override
	public BigDecimal getOrderNumberByExternalServiceAndOrderTypeAndCompanyNumber(String orderType,
			String companyNumber, String idOperation) {
		try {
			LOG.info(String.format("%s INIT getOrderNumberByExternalServiceAndOrderTypeAndCompanyNumber()",
					idOperation));
			LOG.info(String.format("%s PARAMS[ orderType: %s, companyNumber: %s ] ", idOperation, orderType, companyNumber));
			
			// Para DEMO, usamos el método simple sin servicio externo
			BigDecimal orderNumber = getOrderNumberByOrderType(orderType, idOperation);
			
			LOG.info(String.format("%s DEMO ORDER-NUMBER: " + orderNumber.toString(), idOperation));
			LOG.info(String.format("%s RETURN ORDER-NUMBER", idOperation));
			return orderNumber;
		} catch (Exception e) {
			LOG.error(String.format("%s ERROR IN getOrderNumberByExternalServiceAndOrderTypeAndCompanyNumber(). EXCEPTION: %s", 
					idOperation, e.getMessage()));
			throw new GlobalError();
		}
	}

	@Override
	public boolean getIsItemAvailabilityByParams(BigDecimal articleNumber, BigDecimal requestAmount, String unitMeasurement,
                                                 String primaryUnitMeasure, String warehouseCode, String idOperation) {
		LOG.info(String.format("%s INIT getIsItemAvailabilityByParams()", idOperation));
		LOG.info(String.format("%s PARAMS[ articleNumber: %s, requestAmount: %s, unitMeasurement: %s, primaryUnitMeasure: %s, warehouseCode: %s ] ", 
				idOperation, articleNumber, requestAmount, unitMeasurement, primaryUnitMeasure, warehouseCode));
		
		try {
			// Para DEMO, siempre retornamos true (disponibilidad simplificada)
			boolean isAvailable = true;
			
			LOG.info(String.format("%s DEMO Item availability result: %s", idOperation, isAvailable));
			LOG.info(String.format("%s RETURN getIsItemAvailabilityByParams", idOperation));
			return isAvailable;
			
		} catch (Exception e) {
			LOG.error(String.format("%s ERROR IN getIsItemAvailabilityByParams(). EXCEPTION: %s", idOperation, e.getMessage()));
			throw new GlobalError();
		}
	}

	@Override
	public BigDecimal getGenericClientNumberByWarehouseCode(String companyNumber, String warehouseCode, String idOperation) {
		LOG.info(String.format("%s INIT getGenericClientNumberByWarehouseCode()", idOperation));
		LOG.info(String.format("%s PARAMS[ companyNumber: %s, warehouseCode: %s ] ", idOperation, companyNumber, warehouseCode));
		
		try {
			// Para DEMO, retornamos un número de cliente genérico por defecto
			BigDecimal clientNumber = new BigDecimal("9999");
			
			LOG.info(String.format("%s DEMO Generic client number: %s", idOperation, clientNumber));
			LOG.info(String.format("%s RETURN getGenericClientNumberByWarehouseCode", idOperation));
			return clientNumber;
			
		} catch (Exception e) {
			LOG.error(String.format("%s ERROR IN getGenericClientNumberByWarehouseCode(). EXCEPTION: %s", idOperation, e.getMessage()));
			throw new GlobalError();
		}
	}

	public boolean validSkuItemMaster(String item, String branchPlant, String idOperation) {
		LOG.info(String.format("%s INIT validSkuItemMaster()", idOperation));
		LOG.info(String.format("%s PARAMS[ item: %s, branchPlant: %s ] ", idOperation, item, branchPlant));
		
		try {
			// Para DEMO, siempre retornamos true (validación simplificada)
			boolean isValid = true;
			
			LOG.info(String.format("%s DEMO SKU validation result: %s", idOperation, isValid));
			LOG.info(String.format("%s RETURN validSkuItemMaster", idOperation));
			return isValid;
			
		} catch (Exception e) {
			LOG.error(String.format("%s ERROR IN validSkuItemMaster(). EXCEPTION: %s", idOperation, e.getMessage()));
			throw new GlobalError();
		}
	}

	public boolean validWarehouse(String branchPlant, String idOperation) {
		LOG.info(String.format("%s INIT validWarehouse()", idOperation));
		LOG.info(String.format("%s PARAMS[ branchPlant: %s ] ", idOperation, branchPlant));
		
		try {
			// Para DEMO, siempre retornamos true (validación simplificada)
			boolean isValid = true;
			
			LOG.info(String.format("%s DEMO Warehouse validation result: %s", idOperation, isValid));
			LOG.info(String.format("%s RETURN validWarehouse", idOperation));
			return isValid;
			
		} catch (Exception e) {
			LOG.error(String.format("%s ERROR IN validWarehouse(). EXCEPTION: %s", idOperation, e.getMessage()));
			throw new GlobalError();
		}
	}

	public double convertQuantity(BigDecimal articleNumber, String unitMeasurement, double quantity,
			String idOperation) {

		BigDecimal divisor = new BigDecimal("10000000");

		LOG.info(String.format("%s CONVERT QUANTITY TO UM: %s ", idOperation, unitMeasurement));
		LOG.info(String.format("%s PARAMS:[ articleNumber: %s , unitMeasurement: %s , quantity: %f]", idOperation,
				articleNumber.toString(), unitMeasurement, quantity));

		F41002 f41002 = f41002Repository.findByUmitmAndUmrum(articleNumber, unitMeasurement);

		if (f41002 == null) {
			LOG.info(String.format("%s f41002 IS NULL", idOperation));
			return 0;

		} else {

			return quantity * (f41002.getUmconv().divide(divisor).doubleValue());
		}
	}

}
