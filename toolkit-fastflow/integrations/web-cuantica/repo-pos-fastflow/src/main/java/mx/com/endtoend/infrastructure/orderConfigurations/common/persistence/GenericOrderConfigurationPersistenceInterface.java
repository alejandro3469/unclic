package mx.com.endtoend.infrastructure.orderConfigurations.common.persistence;

import java.util.List;

import mx.com.endtoend.smart.bussiness.model.orders.dto.OrderConfigurationDto;

public interface GenericOrderConfigurationPersistenceInterface {

	OrderConfigurationDto createOrderConfigurationByCompanyCode(OrderConfigurationDto orderConfigurationDto,
			String idOperation);

	boolean existsOrderConfigurationByOrderTypeAndCompanyCode(String orderCode, String idOperation);

	OrderConfigurationDto updateOrderConfigurationByCompanyCode(OrderConfigurationDto orderConfigurationDto,
			String idOperation);

	boolean existsOrderConfigurationByOrderTypeAndCompanyCodeAndIdNot(String orderCode, Long id, String idOperation);

	OrderConfigurationDto viewOrderConfigurationDetailByIdAndCompanyCode(Long id, String idOperation);

	OrderConfigurationDto viewOrderConfigurationDetailByOrderCodeAndCompanyCode(String orderCode, String idOperation);

	List<OrderConfigurationDto> getOrderConfigurationListByCompanyCode(String idOperation);

}
