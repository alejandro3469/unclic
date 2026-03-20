package mx.com.endtoend.domain.orderConfigurations.ports.api;

import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;
import mx.com.endtoend.smart.bussiness.model.orders.dto.OrderConfigurationDto;

public interface OrderConfigurationServicePort {

	ResponseModel createOrderConfigurationByCompanyCode(OrderConfigurationDto orderConfigurationDto, String companyCode,
			String method, String idOperation);

	ResponseModel updateOrderConfigurationByCompanyCode(OrderConfigurationDto orderConfigurationDto, String companyCode,
			String method, String idOperation);

	ResponseModel viewOrderConfigurationDetailByIdAndCompanyCode(Long id, String companyCode, String method,
			String idOperation);

	ResponseModel viewOrderConfigurationDetailByOredrCodeAndCompanyCode(String orderCode, String companyCode,
			String method, String idOperation);

	ResponseModel getOrderConfigurationListByCompanyCode(String companyCode, String method, String idOperation);

}
