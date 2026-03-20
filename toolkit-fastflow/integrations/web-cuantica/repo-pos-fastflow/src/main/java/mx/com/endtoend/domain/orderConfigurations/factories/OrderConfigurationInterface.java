package mx.com.endtoend.domain.orderConfigurations.factories;

import mx.com.endtoend.domain.orderConfigurations.ports.spi.OrderConfigurationPersistencePort;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;
import mx.com.endtoend.smart.bussiness.model.orders.dto.OrderConfigurationDto;

public interface OrderConfigurationInterface {

	ResponseModel createOrderConfigurationByCompanyCode(
			OrderConfigurationPersistencePort orderConfigurationPersistencePort,
			OrderConfigurationDto orderConfigurationDto, String companyCode, String idOperation);

	ResponseModel updateOrderConfigurationByCompanyCode(
			OrderConfigurationPersistencePort orderConfigurationPersistencePort,
			OrderConfigurationDto orderConfigurationDto, String companyCode, String idOperation);

	ResponseModel viewOrderConfigurationDetailByIdAndCompanyCode(
			OrderConfigurationPersistencePort orderConfigurationPersistencePort, Long id, String companyCode,
			String idOperation);
	
	ResponseModel viewOrderConfigurationDetailByOrderCodeAndCompanyCode(
			OrderConfigurationPersistencePort orderConfigurationPersistencePort, String orderCode, String companyCode,
			String idOperation);

	ResponseModel getOrderConfigurationListByCompanyCode(
			OrderConfigurationPersistencePort orderConfigurationPersistencePort, String companyCode,
			String idOperation);

}
