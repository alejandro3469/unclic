package mx.com.endtoend.infrastructure.services.jde.orders.common.service;

import java.math.BigDecimal;

import mx.com.endtoend.infrastructure.services.jde.orders.common.factory.OrderJdeRepositoryFactory;
import mx.com.endtoend.infrastructure.services.jde.orders.common.serviceport.OrderJdeServicePort;
import mx.com.endtoend.infrastructure.services.jde.orders.common.repository.GenericOrderJdeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.GlobalError;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;
import mx.com.smartbussiness.generic.utils.DecimalPrecisionUtils;
import mx.com.smartbussiness.generic.validators.PrecisionValidator;

/**
 * Servicio de JDE para la validación/obtención de datos operaciones de las
 * ordenes del sistema.
 * 
 * @author ddcasas
 *
 */

@Service
public class JdeOrderService implements OrderJdeServicePort {

	@Autowired
	private OrderJdeRepositoryFactory orderJdeRepositoryFactory;

	private final Logger LOG = LoggerFactory.getLogger(JdeOrderService.class);

	/**
	 * Valida la precisión decimal de un requestAmount según estándares SAT
	 * 
	 * @param requestAmount cantidad solicitada a validar
	 * @param idOperation ID de operación para logging
	 */
	private void validateRequestAmountPrecision(BigDecimal requestAmount, String idOperation) {
		LOG.info(String.format("%s INIT validateRequestAmountPrecision()", idOperation));
		
		if (requestAmount == null) {
			throw new IllegalArgumentException("RequestAmount cannot be null");
		}
		
		if (!PrecisionValidator.isValidMonetaryRange(requestAmount)) {
			LOG.error(String.format("%s ERROR: requestAmount fuera de rango válido: %s", idOperation, requestAmount));
			throw new GlobalError();
		}
		
		if (!PrecisionValidator.isValidScale(requestAmount, 2)) {
			LOG.error(String.format("%s ERROR: requestAmount con escala incorrecta: %s", idOperation, requestAmount));
			throw new GlobalError();
		}
		
		LOG.info(String.format("%s SUCCESS: requestAmount validado correctamente", idOperation));
	}

	/**
	 * Aplica redondeo SAT a un requestAmount
	 * 
	 * @param requestAmount cantidad solicitada a redondear
	 * @param idOperation ID de operación para logging
	 * @return requestAmount con redondeo SAT aplicado
	 */
	private BigDecimal applySATRounding(BigDecimal requestAmount, String idOperation) {
		LOG.info(String.format("%s INIT applySATRounding()", idOperation));
		
		if (requestAmount == null) {
			return requestAmount;
		}
		
		BigDecimal roundedRequestAmount = DecimalPrecisionUtils.roundToTwoDecimals(requestAmount);
		LOG.info(String.format("%s requestAmount redondeado: %s", idOperation, roundedRequestAmount));
		
		LOG.info(String.format("%s SUCCESS: requestAmount redondeado según SAT", idOperation));
		return roundedRequestAmount;
	}

	/**
	 * Método que recupera el repositorio de un cliente por código de compañia para
	 * obtener el número de orden consecutivo con base al código de la compañía y el
	 * tipo de orden
	 * 
	 * @param companyCode   código de compañia
	 * @param branchCode    código de sucursal
	 * @param companyNumber número de compañía
	 * @param idOperation   identificador de traza
	 * @param orderType     código del tipo de orden
	 * 
	 * @return ResponseModel, objeto con el código del resultado de la operación y
	 *         la información generada
	 */
	@Override
	public ResponseModel getConsecutiveOrderNumberByCompanyCode(String companyCode, String branchCode,
			String companyNumber, String idOperation, String orderType) {

		LOG.info(String.format("%s INIT getConsecutiveOrderNumberByCompanyCode()", idOperation));
		LOG.info(String.format("%s PARAMS: [companyCode: %s , branchCode: %s , orderType:%s ]", idOperation,
				companyCode, branchCode, orderType));

		GenericOrderJdeRepository orderRepository = orderJdeRepositoryFactory.getRepositoryByCompanyCode(companyCode);

		if (orderRepository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}

		LOG.info(String.format("%s GET ORDER-NUMBER", idOperation));
		BigDecimal orderNumber = orderRepository.getOrderNumberByExternalServiceAndOrderTypeAndCompanyNumber(orderType,
				companyNumber, idOperation);

		LOG.info(String.format("%s RETURN ORDER-NUMBER", idOperation));
		return new ResponseModel(orderNumber);
	}

	@Override
	public ResponseModel getIsItemAvailabilityByParams(BigDecimal articleNumber, String companyCode, String branchCode, String warehouseCode, double requestAmount, String unitMeasurement, String primaryUnitMeasure, String idOperation) {
		return null;
	}

	/**
	 * Método que recupera el repositorio de un cliente por código de compañia para
	 * la validación de la disponiblidad de un artpiculo por número corto, almacen y
	 * cantidad solicitada.
	 * 
	 * @param articleNumber número corto del artículo
	 * @param companyCode   código de compañia
	 * @param branchCode    código de sucursal
	 * @param warehouseCode código de almacen/unidad de negocio
	 * @param requestAmount cantidad solicitdad
	 * @param idOperation   traza de identificación de operación
	 * 
	 * @return ResponseModel, objeto con el código del resultado de la operación y
	 *         la información generada
	 * 
	 */

	@Override
	public ResponseModel getIsItemAvailabilityByParams(BigDecimal articleNumber, String companyCode, String branchCode,
													   String warehouseCode, BigDecimal requestAmount, String unitMeasurement, String primaryUnitMeasure,
													   String idOperation) {

		LOG.info(String.format("%s INIT getIsItemAvailabilityByParams()", idOperation));
		LOG.info(String.format(
				"%s PARAMS: [articleNumber: %s , companyCode: %s , branchCode:%s , warehouseCode: %s, requestAmount: %s ]",
				idOperation, articleNumber, companyCode, branchCode, warehouseCode, requestAmount));

		// Validar precisión decimal del requestAmount
		validateRequestAmountPrecision(requestAmount, idOperation);
		
		// Aplicar redondeo SAT
		requestAmount = applySATRounding(requestAmount, idOperation);

		GenericOrderJdeRepository orderRepository = orderJdeRepositoryFactory.getRepositoryByCompanyCode(companyCode);

		if (orderRepository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}

		boolean itemAvailable = orderRepository.getIsItemAvailabilityByParams(articleNumber, requestAmount,
				unitMeasurement, primaryUnitMeasure, warehouseCode, idOperation);

		LOG.info(String.format("%s RETURN VALIDATION", idOperation));
		return new ResponseModel(itemAvailable);
	}

	@Override
	public ResponseModel getGenericClientNumberByCompanyCodeAndWarehouseCode(String companyCode, String companyNumber,
			String warehouseCode, String idOperation) {

		LOG.info(String.format("%s INIT getGenericClientNumberByCompanyCodeAndWarehouseCode()", idOperation));
		LOG.info(String.format("%s PARAMS: [companyCode: %s , companyNumber: %s , warehouseCode: %s ]", idOperation,
				companyCode, companyNumber, warehouseCode));

		GenericOrderJdeRepository orderRepository = orderJdeRepositoryFactory.getRepositoryByCompanyCode(companyCode);

		if (orderRepository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}

		BigDecimal clinetNumber = orderRepository.getGenericClientNumberByWarehouseCode(companyNumber, warehouseCode,
				idOperation);

		LOG.info(String.format("%s RETURN CLIENTE NUMBER", idOperation));
		return new ResponseModel(clinetNumber);
	}

}
