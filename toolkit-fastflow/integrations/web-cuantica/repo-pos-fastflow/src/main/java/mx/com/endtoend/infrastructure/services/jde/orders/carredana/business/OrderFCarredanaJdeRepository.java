package mx.com.endtoend.infrastructure.services.jde.orders.carredana.business;

import java.math.BigDecimal;
import java.util.Optional;

import mx.com.endtoend.infrastructure.warehouse.carredana.repositories.F0006CarredanaRepository;
import mx.com.endtoend.infrastructure.warehouse.common.entities.F0006;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import mx.com.endtoend.infrastructure.articles.carredana.oracle.entities.F41002;
import mx.com.endtoend.infrastructure.articles.carredana.oracle.repositories.F41002FCarRepository;
import mx.com.endtoend.infrastructure.articles.carredana.oracle.repositories.F41021FCarRepository;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.GlobalError;
import mx.com.endtoend.infrastructure.services.jde.orders.common.repository.GenericOrderJdeRepository;
import mx.com.endtoend.infrastructure.services.jde.orders.carredana.common.entities.F00021;
import mx.com.endtoend.infrastructure.services.jde.orders.carredana.common.repository.F00021FCarRepository;
import mx.com.smartbussiness.generic.utils.DecimalPrecisionUtils;
import mx.com.smartbussiness.generic.validators.PrecisionValidator;

/**
 * Implementación concreta de la interfaz GenericOrderJdeRepository para el
 * cliente Caredana.
 * 
 * @author ddcasas
 *
 */

@Service
@ConditionalOnProperty(name = "app.carredana.oracle.enabled", havingValue = "true", matchIfMissing = false)
public class OrderFCarredanaJdeRepository implements GenericOrderJdeRepository {

	@Autowired
	private F00021FCarRepository f00021Repository;

	@Autowired
	private F41002FCarRepository f41002Repository;

	@Autowired
	private F41021FCarRepository f41021Repository;

	@Autowired
	private F0006CarredanaRepository f0006Repository;

	private final Logger LOG = LoggerFactory.getLogger(OrderFCarredanaJdeRepository.class);

	/**
	 * Valida la precisión decimal de datos monetarios antes de enviar a servicios externos
	 * @param amount valor monetario a validar
	 * @throws IllegalArgumentException si la validación falla
	 */
	private void validateMonetaryPrecision(java.math.BigDecimal amount) {
		if (amount == null) {
			throw new IllegalArgumentException("Amount cannot be null");
		}
		
		if (!PrecisionValidator.isValidMonetaryRange(amount)) {
			throw new IllegalArgumentException("Amount out of SAT range: " + amount);
		}
		
		if (!PrecisionValidator.isValidScale(amount, 2)) {
			throw new IllegalArgumentException("Amount must have exactly 2 decimal places: " + amount);
		}
		
		LOG.debug("Monetary precision validation passed for amount: " + amount);
	}

	/**
	 * Aplica redondeo SAT a valores monetarios antes de enviar a servicios externos
	 * @param amount valor monetario a procesar
	 * @return valor con redondeo SAT aplicado
	 */
	private java.math.BigDecimal applySATRounding(java.math.BigDecimal amount) {
		if (amount == null) {
			return amount;
		}
		
		java.math.BigDecimal roundedAmount = DecimalPrecisionUtils.roundToTwoDecimals(amount);
		LOG.debug("SAT rounding applied to amount: " + amount + " -> " + roundedAmount);
		return roundedAmount;
	}

	private String companyKey = "00003";

	@Override
	public BigDecimal getOrderNumberByOrderType(String orderType, String idOperation) {
		LOG.info(String.format("%s INIT getOrderNumberByOrderType()", idOperation));
		LOG.info(String.format("%s PARAMS[ orderType: %s ] ", idOperation, orderType));
		try {
			BigDecimal orderNumber;
			BigDecimal addConcecutive = new BigDecimal("1");
			F00021 f00021 = f00021Repository.findOrderNumberByCompanyCodeAndOrderType(companyKey, orderType);
			orderNumber = f00021.getNln001();
			f00021Repository.updateOrderNumberByCompanyCodeAndOrderType(companyKey, orderType,
					orderNumber.add(addConcecutive));
			LOG.info(String.format("%s ORDER-NUMBER FROM F00021: " + f00021.getNln001().toString(), idOperation));
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
			
			BigDecimal orderNumber = getOrderNumberByOrderType(orderType, idOperation);
			
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
		LOG.info(String.format(
				"%s PARAMS: [articleNumber: %s , requestAmount: %f , unitMeasurement: %s , primaryUnitMeasure: %s , warehouseCode:%s ]",
				idOperation, articleNumber, requestAmount, unitMeasurement, primaryUnitMeasure, warehouseCode));
		try {
			BigDecimal quantity = f41021Repository.getAvailabilityByArticleNumberAndWarehouseCode(articleNumber,
					warehouseCode);
			if (!primaryUnitMeasure.equals(unitMeasurement)) {
				quantity = convertQuantity(articleNumber, unitMeasurement, quantity, idOperation);
			}
			if (quantity.compareTo(BigDecimal.ZERO) > 0 && quantity.compareTo(requestAmount) >= 0) {
				LOG.info(String.format("%s ARTICLE HAS AVAILABILITY: %f", idOperation, quantity));
				return true;
			} else {
				LOG.warn(String.format("%s ITEM IS OUT OF AVAILABILITY: %f", idOperation, quantity));
				return false;
			}

		} catch (Exception e) {
			LOG.error(String.format("%s ERROR IN getIsItemAvailabilityByArticleNumberAndWarehouseCode(). EXCEPTION: %s",
					idOperation, e.getMessage()));
			throw new GlobalError();
		}
	}

	@Override
	public BigDecimal getGenericClientNumberByWarehouseCode(String companyNumber, String warehouseCode,
			String idOperation) {
		try {
			LOG.info(String.format("%s INIT getGenericClientNumberByWarehouseCode()", idOperation));
			LOG.info(String.format("%s PARAMS: [warehouseCode: %s ]", idOperation, warehouseCode));
			Optional<F0006> f0006Optional = f0006Repository.getGenericClientNumberByMccoAndMcstylAndMcmcu(companyNumber,
					"OP", warehouseCode);
			if (f0006Optional.isPresent()) {
				LOG.info(String.format("%s RETURN GENERRIC CLIENT: %s ", idOperation,
						f0006Optional.get().getMcan8().toString()));
				return f0006Optional.get().getMcan8();
			} else {
				LOG.warn(String.format("%s WAREHOUSE DO NOT HAVE GENERRIC CLIENT", idOperation));
				return BigDecimal.ZERO;
			}

		} catch (Exception e) {
			LOG.error(String.format("%s ERROR IN getIsItemAvailabilityByArticleNumberAndWarehouseCode(). EXCEPTION: %s",
					idOperation, e.getMessage()));
			throw new GlobalError();
		}
	}

	public BigDecimal convertQuantity(BigDecimal articleNumber, String unitMeasurement, BigDecimal quantity,
			String idOperation) {
		BigDecimal divisor = new BigDecimal("10000000");
		LOG.info(String.format("%s CONVERT QUANTITY TO UM: %s ", idOperation, unitMeasurement));
		LOG.info(String.format("%s PARAMS:[ articleNumber: %s , unitMeasurement: %s , quantity: %f]", idOperation,
				articleNumber.toString(), unitMeasurement, quantity));
		F41002 f41002 = f41002Repository.findByUmitmAndUmrum(articleNumber, unitMeasurement);
		if (f41002 == null) {
			LOG.info(String.format("%s f41002 IS NULL", idOperation));
			return BigDecimal.ZERO;
		} else {
			return quantity.multiply(f41002.getUmconv().divide(divisor));
		}
	}

}
