package mx.com.endtoend.domain.orderConfigurations.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.com.endtoend.domain.orderConfigurations.factories.OrderConfigurationFactory;
import mx.com.endtoend.domain.orderConfigurations.factories.OrderConfigurationInterface;
import mx.com.endtoend.domain.orderConfigurations.ports.api.OrderConfigurationServicePort;
import mx.com.endtoend.domain.orderConfigurations.ports.spi.OrderConfigurationPersistencePort;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.GlobalError;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;
import mx.com.endtoend.smart.bussiness.model.orders.dto.OrderConfigurationDto;

/**
 * Esta clase se encarga de obtener la implementación concreta acorde al método
 * configurado a la compañia.
 * 
 * @author ddcasas
 *
 */
public class OrderConfigurationServiceImpl implements OrderConfigurationServicePort {

	private OrderConfigurationPersistencePort orderConfigurationPersistencePort;

	public OrderConfigurationServiceImpl(OrderConfigurationPersistencePort orderConfigurationPersistencePort) {
		this.orderConfigurationPersistencePort = orderConfigurationPersistencePort;
	}

	private final static Logger LOG = LoggerFactory.getLogger(OrderConfigurationServiceImpl.class);

	OrderConfigurationFactory orderConfigurationFactory = new OrderConfigurationFactory();

	/**
	 * Método que inicia el proceso para la creación de la configuración de datos
	 * operativos de las ordenes del sistema. Obtiene el método con la lógica
	 * configurada al cliente.
	 */

	@Override
	public ResponseModel createOrderConfigurationByCompanyCode(OrderConfigurationDto orderConfigurationDto,
			String companyCode, String method, String idOperation) {

		LOG.info(String.format("%s INIT METHOD createOrderConfigurationByCompanyCode()", idOperation));
		LOG.info(String.format("%s PARAMS: [ orderConfigurationDto: %s , companyCode: %s , method: %s ]", idOperation,
				orderConfigurationDto.toString(), companyCode, method));

		OrderConfigurationInterface orderConfiguration = orderConfigurationFactory.getImplementation(method);

		if (orderConfiguration == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}

		ResponseModel responseFromPersistencePort = orderConfiguration.createOrderConfigurationByCompanyCode(
				orderConfigurationPersistencePort, orderConfigurationDto, companyCode, idOperation);

		LOG.info(String.format("%s RESULT FROM PERSISTENCE PORT: %d", idOperation,
				responseFromPersistencePort.getResponseCode()));

		return responseFromPersistencePort;

	}

	@Override
	public ResponseModel updateOrderConfigurationByCompanyCode(OrderConfigurationDto orderConfigurationDto,
			String companyCode, String method, String idOperation) {

		LOG.info(String.format("%s INIT METHOD updateOrderConfigurationByCompanyCode()", idOperation));
		LOG.info(String.format("%s PARAMS: [ orderConfigurationDto: %s , companyCode: %s , method: %s ]", idOperation,
				orderConfigurationDto.toString(), companyCode, method));

		OrderConfigurationInterface orderConfiguration = orderConfigurationFactory.getImplementation(method);

		if (orderConfiguration == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}

		ResponseModel responseFromPersistencePort = orderConfiguration.updateOrderConfigurationByCompanyCode(
				orderConfigurationPersistencePort, orderConfigurationDto, companyCode, idOperation);

		LOG.info(String.format("%s RESULT FROM PERSISTENCE PORT: %d", idOperation,
				responseFromPersistencePort.getResponseCode()));

		return responseFromPersistencePort;

	}

	@Override
	public ResponseModel viewOrderConfigurationDetailByIdAndCompanyCode(Long id, String companyCode, String method,
			String idOperation) {

		LOG.info(String.format("%s INIT METHOD viewOrderConfigurationDetailByIdAndCompanyCode()", idOperation));
		LOG.info(String.format("%s PARAMS: [ id: %s , companyCode: %s , method: %s ]", idOperation, id.toString(),
				companyCode, method));

		OrderConfigurationInterface orderConfiguration = orderConfigurationFactory.getImplementation(method);

		if (orderConfiguration == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}

		ResponseModel responseFromPersistencePort = orderConfiguration.viewOrderConfigurationDetailByIdAndCompanyCode(
				orderConfigurationPersistencePort, id, companyCode, idOperation);

		LOG.info(String.format("%s RESULT FROM PERSISTENCE PORT: %d", idOperation,
				responseFromPersistencePort.getResponseCode()));

		return responseFromPersistencePort;

	}

	@Override
	public ResponseModel viewOrderConfigurationDetailByOredrCodeAndCompanyCode(String orderCode, String companyCode,
			String method, String idOperation) {

		LOG.info(String.format("%s INIT METHOD viewOrderConfigurationDetailByOredrCodeAndCompanyCode()", idOperation));
		LOG.info(String.format("%s PARAMS: [ orderCode: %s , companyCode: %s , method: %s ]", idOperation, orderCode,
				companyCode, method));

		OrderConfigurationInterface orderConfiguration = orderConfigurationFactory.getImplementation(method);

		if (orderConfiguration == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}

		ResponseModel responseFromPersistencePort = orderConfiguration
				.viewOrderConfigurationDetailByOrderCodeAndCompanyCode(orderConfigurationPersistencePort, orderCode,
						companyCode, idOperation);

		return responseFromPersistencePort;

	}

	@Override
	public ResponseModel getOrderConfigurationListByCompanyCode(String companyCode, String method, String idOperation) {

		LOG.info(String.format("%s INIT METHOD getOrderConfigurationListByCompanyCode()", idOperation));
		LOG.info(String.format("%s PARAMS: [ companyCode: %s , method: %s ]", idOperation, companyCode, method));

		OrderConfigurationInterface orderConfiguration = orderConfigurationFactory.getImplementation(method);

		if (orderConfiguration == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}

		ResponseModel responseFromPersistencePort = orderConfiguration
				.getOrderConfigurationListByCompanyCode(orderConfigurationPersistencePort, companyCode, idOperation);

		LOG.info(String.format("%s RESULT FROM PERSISTENCE PORT: %d", idOperation,
				responseFromPersistencePort.getResponseCode()));

		return responseFromPersistencePort;

	}

}
