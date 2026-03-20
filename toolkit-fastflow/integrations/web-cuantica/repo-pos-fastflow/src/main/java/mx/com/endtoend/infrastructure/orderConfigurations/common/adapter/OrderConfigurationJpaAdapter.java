package mx.com.endtoend.infrastructure.orderConfigurations.common.adapter;

import java.util.List;

import mx.com.endtoend.infrastructure.orderConfigurations.common.persistence.GenericOrderConfigurationPersistenceInterface;
import mx.com.endtoend.infrastructure.orderConfigurations.common.factory.OrderConfigurationRepositoryFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import mx.com.endtoend.domain.orderConfigurations.ports.spi.OrderConfigurationPersistencePort;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.GlobalError;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;
import mx.com.endtoend.smart.bussiness.model.orders.dto.OrderConfigurationDto;

public class OrderConfigurationJpaAdapter implements OrderConfigurationPersistencePort {

	@Autowired
	private OrderConfigurationRepositoryFactory orderConfigurationRepositoryFactory;

	private final Logger LOG = LoggerFactory.getLogger(OrderConfigurationJpaAdapter.class);

	@Override
	public ResponseModel createOrderConfigurationByCompanyCode(OrderConfigurationDto orderConfigurationDto,
			String companyCode, String idOperation) {

		LOG.info(String.format("%s INIT createOrderConfigurationByCompanyCode()", idOperation));
		LOG.info(String.format("%s PARAMS [orderConfigurationDto: %s ,  companyCode: %s]", idOperation,
				orderConfigurationDto.toString(), companyCode));

		GenericOrderConfigurationPersistenceInterface orderConfigurationRepository = orderConfigurationRepositoryFactory
				.getRepository(companyCode);

		if (orderConfigurationRepository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}

		OrderConfigurationDto orderConfigurationCreated = orderConfigurationRepository
				.createOrderConfigurationByCompanyCode(orderConfigurationDto, idOperation);

		return new ResponseModel(orderConfigurationCreated);
	}

	@Override
	public ResponseModel existsOrderConfigurationByOrderTypeAndCompanyCode(String orderCode, String companyCode,
			String idOperation) {

		LOG.info(String.format("%s INIT existsOrderConfigurationByOrderTypeAndCompanyCode()", idOperation));
		LOG.info(String.format("%s PARAMS [orderCode: %s ,  companyCode: %s]", idOperation, orderCode, companyCode));

		GenericOrderConfigurationPersistenceInterface orderConfigurationRepository = orderConfigurationRepositoryFactory
				.getRepository(companyCode);

		if (orderConfigurationRepository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}

		boolean existsOrder = orderConfigurationRepository.existsOrderConfigurationByOrderTypeAndCompanyCode(orderCode,
				idOperation);

		return new ResponseModel(existsOrder);

	}

	@Override
	public ResponseModel updateOrderConfigurationByCompanyCode(OrderConfigurationDto orderConfigurationDto,
			String companyCode, String idOperation) {

		LOG.info(String.format("%s INIT updateOrderConfigurationByCompanyCode()", idOperation));
		LOG.info(String.format("%s PARAMS [orderConfigurationDto: %s ,  companyCode: %s]", idOperation,
				orderConfigurationDto.toString(), companyCode));

		GenericOrderConfigurationPersistenceInterface orderConfigurationRepository = orderConfigurationRepositoryFactory
				.getRepository(companyCode);

		if (orderConfigurationRepository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}

		OrderConfigurationDto orderConfigurationUpdated = orderConfigurationRepository
				.updateOrderConfigurationByCompanyCode(orderConfigurationDto, idOperation);

		return new ResponseModel(orderConfigurationUpdated);

	}

	@Override
	public ResponseModel existsOrderConfigurationByOrderTypeAndCompanyCodeAndIdNot(String orderCode, Long id,
			String companyCode, String idOperation) {

		LOG.info(String.format("%s INIT existsOrderConfigurationByOrderTypeAndCompanyCodeAndIdNot()", idOperation));
		LOG.info(String.format("%s PARAMS [orderCode: %s , id: %s , companyCode: %s]", idOperation, orderCode,
				id.toString(), companyCode));

		GenericOrderConfigurationPersistenceInterface orderConfigurationRepository = orderConfigurationRepositoryFactory
				.getRepository(companyCode);

		if (orderConfigurationRepository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}

		boolean existsOrder = orderConfigurationRepository
				.existsOrderConfigurationByOrderTypeAndCompanyCodeAndIdNot(orderCode, id, idOperation);

		return new ResponseModel(existsOrder);

	}

	@Override
	public ResponseModel viewOrderConfigurationDetailByIdAndCompanyCode(Long id, String companyCode,
			String idOperation) {

		LOG.info(String.format("%s INIT viewOrderConfigurationDetailByIdAndCompanyCode()", idOperation));
		LOG.info(String.format("%s PARAMS [id: %s ,  companyCode: %s]", idOperation, id.toString(), companyCode));

		GenericOrderConfigurationPersistenceInterface orderConfigurationRepository = orderConfigurationRepositoryFactory
				.getRepository(companyCode);

		if (orderConfigurationRepository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}

		OrderConfigurationDto orderConfiguration = orderConfigurationRepository
				.viewOrderConfigurationDetailByIdAndCompanyCode(id, idOperation);

		return new ResponseModel(orderConfiguration);

	}

	@Override
	public ResponseModel viewOrderConfigurationDetailByOrderCodeAndCompanyCode(String orderCode, String companyCode,
			String idOperation) {

		LOG.info(String.format("%s INIT viewOrderConfigurationDetailByOrderCodeAndCompanyCode()", idOperation));
		LOG.info(String.format("%s PARAMS [orderCode: %s ,  companyCode: %s]", idOperation, orderCode, companyCode));

		GenericOrderConfigurationPersistenceInterface orderConfigurationRepository = orderConfigurationRepositoryFactory
				.getRepository(companyCode);

		if (orderConfigurationRepository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}

		OrderConfigurationDto orderConfiguration = orderConfigurationRepository
				.viewOrderConfigurationDetailByOrderCodeAndCompanyCode(orderCode, idOperation);

		return new ResponseModel(orderConfiguration);
	}

	@Override
	public ResponseModel getOrderConfigurationListByCompanyCode(String companyCode, String idOperation) {

		LOG.info(String.format("%s INIT getOrderConfigurationListByCompanyCode()", idOperation));
		LOG.info(String.format("%s PARAMS [ companyCode: %s]", idOperation, companyCode));

		GenericOrderConfigurationPersistenceInterface orderConfigurationRepository = orderConfigurationRepositoryFactory
				.getRepository(companyCode);

		if (orderConfigurationRepository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}

		List<OrderConfigurationDto> orderConfigurationList = orderConfigurationRepository
				.getOrderConfigurationListByCompanyCode(idOperation);

		return new ResponseModel(orderConfigurationList);

	}

}
