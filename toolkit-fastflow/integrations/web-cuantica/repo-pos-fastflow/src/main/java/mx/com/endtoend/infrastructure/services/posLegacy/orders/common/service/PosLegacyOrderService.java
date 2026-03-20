package mx.com.endtoend.infrastructure.services.posLegacy.orders.common.service;

import java.math.BigDecimal;

import mx.com.endtoend.infrastructure.services.posLegacy.orders.common.repository.GenericOrderPosLegacyRepository;
import mx.com.endtoend.infrastructure.services.posLegacy.orders.common.factory.OrderPosLegacyRepositoryFactory;
import mx.com.endtoend.infrastructure.services.posLegacy.orders.common.serviceport.OrderPosLegacyServicePort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.GlobalError;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;
import mx.com.endtoend.smart.bussiness.model.orders.dto.OrderDto;
import mx.com.endtoend.smart.bussiness.model.orders.dto.SaleOrderDto;

/**
 * Servicio de PosLegacy para la inserción de datos y recuperación de el estado
 * de cobro de ordenes en el sistema.
 * 
 * 
 * @author ddcasas
 *
 */

@Service
public class PosLegacyOrderService implements OrderPosLegacyServicePort {

	@Autowired
	private OrderPosLegacyRepositoryFactory orderPosLegacyRepositoryFactory;

	private final Logger LOG = LoggerFactory.getLogger(PosLegacyOrderService.class);

	/**
	 * Método que recupera el repositorio de un cliente por código de compañia para
	 * la creación de ordenes del sistema.
	 * 
	 * @param orderDto    modelo de datos con la información de ordenes del sistema
	 * @param companyCode código de compañía
	 * @param idOperation traza de identificación de operación
	 * 
	 */
	@Override
	public void createOrder(OrderDto orderDto, String companyCode, String idOperation) {
		LOG.info(String.format("%s INIT createOrder()", idOperation));
		LOG.info(String.format("%s PARAMS: [orderDto: %s , companyCode: %s  ]", idOperation, orderDto.toString(),
				companyCode));
		GenericOrderPosLegacyRepository orderRepository = orderPosLegacyRepositoryFactory
				.getRepositoryByCompanyCode(companyCode);
		if (orderRepository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		LOG.info(String.format("%s CREATE ORDER", idOperation));
		orderDto.setIsNewOrder(true);
		orderRepository.sendOrderToSave(orderDto);
	}

	/**
	 * Método que recupera el repositorio de un cliente por código de compañia
	 * parala actualización de ordenes del sistema.
	 * 
	 * @param orderDto    modelo de datos con la información de ordenes del sistema
	 * @param companyCode código de compañia
	 * @param idOperation traza de identificación de operación
	 */
	@Async
	@Override
	public void updateOrder(OrderDto orderDto, String companyCode, String idOperation) {
		LOG.info(String.format("%s INIT updateOrder()", idOperation));
		LOG.info(String.format("%s PARAMS: [orderDto: %s , companyCode: %s  ]", idOperation, orderDto.toString(),
				companyCode));
		GenericOrderPosLegacyRepository orderRepository = orderPosLegacyRepositoryFactory
				.getRepositoryByCompanyCode(companyCode);
		if (orderRepository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		LOG.info(String.format("%s UPDATE ORDER", idOperation));
		orderDto.setIsNewOrder(false);
		orderRepository.sendOrderToSave(orderDto);

	}

	/**
	 * Método que recupera el repositorio de un cliente por código de compañia para
	 * la actualización del estado de la orden y bloquear su edición.
	 * 
	 * @param orderNumber número de identificación de orden
	 * @param orderType   tipo de orden del sistema
	 * @param companyCode código de compañía
	 * @param idOperation traza de identificación de operación
	 * 
	 * @return ResponseModel, objeto con el código del resultado de la operación y
	 *         la información generada
	 */
	@Override
	public void updateStatusOrderActive(BigDecimal orderNumber, boolean status, String orderType,
			String companyCode, String idOperation) {

		LOG.info(String.format("%s INIT updateStatusOrderActive()", idOperation));
		LOG.info(String.format("%s PARAMS: [orderNumber: %s , orderType: %s  companyCode: %s ]", idOperation,
				orderNumber.toString(), orderType, companyCode));
		GenericOrderPosLegacyRepository orderRepository = orderPosLegacyRepositoryFactory
				.getRepositoryByCompanyCode(companyCode);
		if (orderRepository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		LOG.info(String.format("%s UPDATE STATUS ORDER", idOperation));
		orderRepository.sendOrderToChangeStatus(orderNumber, status, orderType, idOperation);

	}

	/**
	 * Método que recupera el repositorio de un cliente por código de compañia para
	 * la cancelación de ordenes del sistema.
	 * 
	 * @param statusOrder código indicador del estado de la orden
	 * @param orderNumber número de identificación de orden
	 * @param orderType   tipo de orden del sistema
	 * @param companyCode código de compañía
	 * @param idOperation traza de identificador de operación
	 * 
	 * @return ResponseModel, objeto con el código del resultado de la operación y
	 *         la información generada
	 */
	@Override
	public void cancelOrder(BigDecimal orderNumber, String orderType, String companyCode, String idOperation) {
		LOG.info(String.format("%s INIT cancelOrder()", idOperation));
		LOG.info(String.format("%s PARAMS: [orderNumber: %s  orderType: %s , companyCode: %s ]", idOperation,
				orderNumber.toString(), orderType, companyCode));
		GenericOrderPosLegacyRepository orderRepository = orderPosLegacyRepositoryFactory
				.getRepositoryByCompanyCode(companyCode);
		if (orderRepository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		LOG.info(String.format("%s CANCEL ORDER ", idOperation));
		orderRepository.sendOrderToCancelProcess(orderNumber, orderType, idOperation);
	}

	/**
	 * Método que recupera el repositorio de un cliente por código de compañía para
	 * la aprobación de ordenes retenidas en el sistema
	 * 
	 * @param statusOrder código de estado de orden
	 * @param orderNumber número de identificación de orden
	 * @param orderType   tipo de orden del sistema
	 * @param companyCode código de compañía
	 * @param idOperation traza de identificación de operación
	 */
	@Override
	public void approveOrder(String statusOrder, BigDecimal orderNumber, String orderType, String companyCode,
			String idOperation) {
		LOG.info(String.format("%s INIT approveOrder()", idOperation));
		LOG.info(String.format("%s PARAMS: [statusOrder: %s , orderNumber: %s  orderType: %s , companyCode: %s ]",
				idOperation, statusOrder, orderNumber, orderType, companyCode));
		GenericOrderPosLegacyRepository orderRepository = orderPosLegacyRepositoryFactory
				.getRepositoryByCompanyCode(companyCode);
		if (orderRepository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		LOG.info(String.format("%s APPROVE ORDER ", idOperation));
		orderRepository.sendOrderToApproveProcess(statusOrder, orderNumber, orderType, idOperation);
	}

	/**
	 * Metodo que recupera el repositrio de un cliente por código de compañía para
	 * obtener el estado de cobro de las ordenes del sistema.
	 * 
	 * @param orderNumber número de identificación de orden
	 * @param orderType   tipo de orden del sistema
	 * @param companyCode código de compañía
	 * @param idOperation traza de identificación de operación
	 * 
	 * @return ResponseModel, objeto con el código del resultado de la operación y
	 *         la información generada
	 */
	@Override
	public ResponseModel getOrderDetailByOrderNumberAndOrderType(BigDecimal orderNumber, String orderType,
			String companyCode, String idOperation) {

		LOG.info(String.format("%s INIT getOrderDetailByOrderNumberAndOrderType()", idOperation));
		LOG.info(String.format("%s PARAMS: [orderNumber: %s , orderType: %s , companyCode: %s  ]", idOperation,
				orderNumber.toString(), orderType, companyCode));

		GenericOrderPosLegacyRepository orderRepository = orderPosLegacyRepositoryFactory
				.getRepositoryByCompanyCode(companyCode);

		if (orderRepository == null) {

			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}

		LOG.info(String.format("%s FIND ORDER ", idOperation));
		OrderDto orderDtoDetail = orderRepository.getOrderDetailByOrderNumberAndOrderType(orderNumber, orderType,
				idOperation);

		LOG.info(String.format("%s RETURN OPERATION RESULT", idOperation));
		return new ResponseModel(orderDtoDetail);

	}

	/**
	 * Método que recupera el repositorio de un cliente por código de compañía para
	 * la creación de ordenes de compra directa.
	 * 
	 * @param saleOrderDto módelo de datos para las ordenes de venta directa
	 * @param companyCode  código de compañía
	 * @param idOperation  traza de identificación de operación
	 * 
	 * @return ResponseModel, objeto con el código del resultado de la operación y
	 *         la información generada
	 */
	@Override
	public void createSaleOD(SaleOrderDto saleOrderDto, String companyCode, String idOperation) {

		LOG.info(String.format("%s INIT createSaleOD()", idOperation));
		LOG.info(String.format("%s PARAMS: [saleOrderDto: %s , companyCode: %s ]", idOperation, saleOrderDto.toString(),
				companyCode));
		GenericOrderPosLegacyRepository orderRepository = orderPosLegacyRepositoryFactory
				.getRepositoryByCompanyCode(companyCode);
		if (orderRepository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		LOG.info(String.format("%s CREATE ORDER DIRECT", idOperation));
		saleOrderDto.setIsNewOrder(true);
		orderRepository.sendOrderODToSave(saleOrderDto);

	}

	/**
	 * Método que recupera el repositorio de un cliente por código de compañía para
	 * la actualización de ordenes de compra directa.
	 * 
	 * @param saleOrderDto módelo de datos para las ordenes de venta directa
	 * @param companyCode  código de compañía
	 * @param idOperation  traza de identificación de operación
	 * 
	 * @return ResponseModel, objeto con el código del resultado de la operación y
	 *         la información generada
	 */
	@Override
	public void updteSaleOD(SaleOrderDto saleOrderDto, String companyCode, String idOperation) {

		LOG.info(String.format("%s INIT updteSaleOD()", idOperation));
		LOG.info(String.format("%s PARAMS: [saleOrderDto: %s , companyCode: %s ]", idOperation, saleOrderDto.toString(),
				companyCode));
		GenericOrderPosLegacyRepository orderRepository = orderPosLegacyRepositoryFactory
				.getRepositoryByCompanyCode(companyCode);

		if (orderRepository == null) {

			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		LOG.info(String.format("%s CREATE ORDER DIRECT", idOperation));
		saleOrderDto.setIsNewOrder(false);
		orderRepository.sendOrderODToSave(saleOrderDto);

	}

}
