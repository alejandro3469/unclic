package mx.com.endtoend.domain.orderConfigurations.factories;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.com.endtoend.domain.orderConfigurations.ports.spi.OrderConfigurationPersistencePort;
import mx.com.endtoend.domain.orderConfigurations.services.GenericOrderConfigurationValidation;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.GlobalError;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.ValidationError;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;
import mx.com.endtoend.smart.bussiness.model.orders.dto.OrderConfigurationDto;

/**
 * Clase contenedorea de las operaciones de creación, actualización y
 * visualización de las configuraciónes de los datos operativos de las ordenes
 * pertenecientes al método ORDER_CONFIGURATION_ONE del módulo
 * ORDER_CONFIGURATION.
 * 
 * @author ddcasas
 *
 */

public class OrderConfigurationMethodOne implements OrderConfigurationInterface {

	private final static Logger LOG = LoggerFactory.getLogger(OrderConfigurationMethodOne.class);

	private GenericOrderConfigurationValidation orderConfigurationValidation = new GenericOrderConfigurationValidation();

	/**
	 * Método para la creación de la configuración de los datos operacionales de las
	 * ordenes del sistema.
	 * 
	 * @param orderConfigurationPersistencePort
	 * @param orderConfigurationDto
	 * @param companyCode
	 * @param idOperation
	 * 
	 * @return ResponseModel
	 */
	@Override
	public ResponseModel createOrderConfigurationByCompanyCode(
			OrderConfigurationPersistencePort orderConfigurationPersistencePort,
			OrderConfigurationDto orderConfigurationDto, String companyCode, String idOperation) {

		LOG.info(String.format("INIT createOrderConfigurationByCompanyCode()", idOperation));
		LOG.info(String.format(
				"%s PARAMS [orderConfigurationPersistencePort , orderConfigurationDto: %s ,  companyCode: %s]",
				idOperation, orderConfigurationDto.toString(), companyCode));

		LOG.info(String.format("%s INIT VALIDATION", idOperation));
		String validations = orderConfigurationValidation.getValidationsToMethodOneOnCreate(
				orderConfigurationPersistencePort, orderConfigurationDto, companyCode, idOperation);

		if (validations.length() > 0) {
			LOG.warn(String.format("%s VALIDATION ERROR: %s", idOperation, validations));
			throw new ValidationError(validations);
		}


		OrderConfigurationDto orderConfigurationCreated = (OrderConfigurationDto) orderConfigurationPersistencePort
				.createOrderConfigurationByCompanyCode(orderConfigurationDto, companyCode, idOperation).getData();

		if (orderConfigurationCreated == null) {
			LOG.error(String.format("%s ERROR IN SAVE ORDER-CONFIGURATION, RETURN NULL VALUE", idOperation));
			throw new GlobalError();
		}

		LOG.info(String.format("%s END METHOD createOrderConfigurationByCompanyCode()", idOperation));

		return new ResponseModel(orderConfigurationCreated);
	}

	/**
	 * Método para la actualización de la configuración de los datos operacionales
	 * de una orden del sistema por código de compañia.
	 * 
	 * @param orderConfigurationPersistencePort
	 * @param orderConfigurationDto
	 * @param companyCode
	 * @param idOperation
	 * 
	 * @return ResponseModel
	 */
	@Override
	public ResponseModel updateOrderConfigurationByCompanyCode(
			OrderConfigurationPersistencePort orderConfigurationPersistencePort,
			OrderConfigurationDto orderConfigurationDto, String companyCode, String idOperation) {

		LOG.info(String.format("INIT updateOrderConfigurationByCompanyCode()", idOperation));
		LOG.info(String.format(
				"%s PARAMS [orderConfigurationPersistencePort , orderConfigurationDto: %s ,  companyCode: %s]",
				idOperation, orderConfigurationDto.toString(), companyCode));

		LOG.info(String.format("%s INIT VALIDATION", idOperation));
		String validations = orderConfigurationValidation.getValidationsToMethodOneOnUpdate(
				orderConfigurationPersistencePort, orderConfigurationDto, companyCode, idOperation);

		if (validations.length() > 0) {
			LOG.warn(String.format("%s VALIDATION ERROR: %s", idOperation, validations));
			throw new ValidationError(validations);
		}

		LOG.info(String.format("%s INIT UPDATE ORDER-CONFIGURATION", idOperation));

		OrderConfigurationDto orderConfigurationUpdated = (OrderConfigurationDto) orderConfigurationPersistencePort
				.updateOrderConfigurationByCompanyCode(orderConfigurationDto, companyCode, idOperation).getData();

		if (orderConfigurationUpdated == null) {
			LOG.error(String.format("%s ERROR IN UPDATE ORDER-CONFIGURATION, RETURN NULL VALUE", idOperation));
			throw new GlobalError();
		}

		LOG.info(String.format("%s END METHOD updateOrderConfigurationByCompanyCode()", idOperation));

		return new ResponseModel(orderConfigurationUpdated);
	}

	/**
	 * Método que obtiene el detalle de la configuración de una orden por ID y
	 * código de compañia.
	 * 
	 * @param orderConfigurationPersistencePort
	 * @param id
	 * @param companyCode
	 * @param idOperation
	 * 
	 * @return ResponseModel
	 */
	@Override
	public ResponseModel viewOrderConfigurationDetailByIdAndCompanyCode(
			OrderConfigurationPersistencePort orderConfigurationPersistencePort, Long id, String companyCode,
			String idOperation) {

		LOG.info(String.format("INIT viewOrderConfigurationDetailByIdAndCompanyCode()", idOperation));
		LOG.info(String.format("%s PARAMS [orderConfigurationPersistencePort , id: %s ,  companyCode: %s]", idOperation,
				id.toString(), companyCode));

		OrderConfigurationDto orderConfigurationDto = (OrderConfigurationDto) orderConfigurationPersistencePort
				.viewOrderConfigurationDetailByIdAndCompanyCode(id, companyCode, idOperation).getData();

		if (orderConfigurationDto == null) {
			LOG.error(String.format("%s ERROR IN GET ORDER-CONFIGURATION BY ID: %s , RETURN NULL VALUE", idOperation,
					id.toString()));
			return new ResponseModel("");
		}

		LOG.info(String.format("%s END METHOD viewOrderConfigurationDetailByIdAndCompanyCode()", idOperation));

		return new ResponseModel(orderConfigurationDto);
	}

	/**
	 * Método que obtiene el detalle de la configuración de una orden por el código
	 * de orden ingresado y código de compañia
	 * 
	 * @param orderConfigurationPersistencePort
	 * @param orderCode
	 * @param companyCode
	 * @param idOperation
	 * 
	 * @return ResponseModel
	 */
	@Override
	public ResponseModel viewOrderConfigurationDetailByOrderCodeAndCompanyCode(
			OrderConfigurationPersistencePort orderConfigurationPersistencePort, String orderCode, String companyCode,
			String idOperation) {

		LOG.info(String.format("INIT viewOrderConfigurationDetailByOrderCodeAndCompanyCode()", idOperation));
		LOG.info(String.format("%s PARAMS [orderConfigurationPersistencePort , orderCode: %s ,  companyCode: %s]",
				idOperation, orderCode, companyCode));

		OrderConfigurationDto orderConfigurationDto = (OrderConfigurationDto) orderConfigurationPersistencePort
				.viewOrderConfigurationDetailByOrderCodeAndCompanyCode(orderCode, companyCode, idOperation).getData();

		if (orderConfigurationDto == null) {
			LOG.error(String.format("%s ERROR IN GET ORDER-CONFIGURATION BY ORDER CODE: %s , RETURN NULL VALUE",
					idOperation, orderCode));
			return new ResponseModel("");
		}

		LOG.info(String.format("%s END METHOD viewOrderConfigurationDetailByOrderCodeAndCompanyCode()", idOperation));

		return new ResponseModel(orderConfigurationDto);
	}

	/**
	 * Método que obtiene la lista de todas las configuraciones de ordenes por
	 * código de compañía.
	 * 
	 * @param orderConfigurationPersistencePort
	 * @param id
	 * @param companyCode
	 * @param idOperation
	 * 
	 * @return ResponseModel
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ResponseModel getOrderConfigurationListByCompanyCode(
			OrderConfigurationPersistencePort orderConfigurationPersistencePort, String companyCode,
			String idOperation) {

		LOG.info(String.format("INIT getOrderConfigurationListByCompanyCode()", idOperation));
		LOG.info(String.format("%s PARAMS [orderConfigurationPersistencePort ,  companyCode: %s]", idOperation,
				companyCode));

		List<OrderConfigurationDto> orderConfigurationDtoList = (List<OrderConfigurationDto>) orderConfigurationPersistencePort
				.getOrderConfigurationListByCompanyCode(companyCode, idOperation).getData();

		if (orderConfigurationDtoList == null) {
			LOG.error(String.format("%s ERROR IN GET ORDER-CONFIGURATION LIST BY COMPANY-CODE: %s , RETURN NULL VALUE",
					idOperation, companyCode));

			List<OrderConfigurationDto> res = new ArrayList<OrderConfigurationDto>();

			return new ResponseModel(res);
		}

		LOG.info(String.format("%s END METHOD getOrderConfigurationListByCompanyCode()", idOperation));

		return new ResponseModel(orderConfigurationDtoList);

	}

}
