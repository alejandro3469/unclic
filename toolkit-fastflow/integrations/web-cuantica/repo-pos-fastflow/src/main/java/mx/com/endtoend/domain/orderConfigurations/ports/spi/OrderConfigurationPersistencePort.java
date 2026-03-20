package mx.com.endtoend.domain.orderConfigurations.ports.spi;

import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;
import mx.com.endtoend.smart.bussiness.model.orders.dto.OrderConfigurationDto;

public interface OrderConfigurationPersistencePort {

	ResponseModel createOrderConfigurationByCompanyCode(OrderConfigurationDto orderConfigurationDto, String companyCode,
			String idOperation);

	ResponseModel existsOrderConfigurationByOrderTypeAndCompanyCode(String orderCode, String companyCode,
			String idOperation);

	ResponseModel updateOrderConfigurationByCompanyCode(OrderConfigurationDto orderConfigurationDto, String companyCode,
			String idOperation);
	
	ResponseModel existsOrderConfigurationByOrderTypeAndCompanyCodeAndIdNot(String orderCode, Long id,
			String companyCode, String idOperation);

	ResponseModel viewOrderConfigurationDetailByIdAndCompanyCode(Long id, String companyCode, String idOperation);
	
	ResponseModel viewOrderConfigurationDetailByOrderCodeAndCompanyCode(String orderCode, String companyCode, String idOperation);

	ResponseModel getOrderConfigurationListByCompanyCode(String companyCode, String idOperation);

}
