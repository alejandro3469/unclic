package mx.com.endtoend.domain.orders.factories.validations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.com.endtoend.domain.commons.constants.StatusOrder;
import mx.com.endtoend.infrastructure.services.posLegacy.orders.common.serviceport.OrderPosLegacyServicePort;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;
import mx.com.endtoend.smart.bussiness.model.orders.dto.OrderDto;

/**
 * Clase con validaciones comunes a todas las órdenes de venta y cotizaciones
 * del sistema
 * 
 * @author ddcasas
 *
 */
public class GenericOrderValidation {

	private final Logger LOG = LoggerFactory.getLogger(GenericOrderValidation.class);

	/**
	 * Método que valida el estadod de la orden en el sistema POSLegacy
	 * 
	 * @param orderPosLegacyServicePort interfaz de comunicación hacia el sistema
	 *                                  POSLegacy
	 * @param orderDto                  datos operativos de la orden
	 * @param companyCode               código de compañía
	 * @param idOperation               identificador de traza de operación
	 * 
	 * @return cadena de tipo String, si la cadena esta vacía pasó todas las
	 *         validaciones, en caso contrario se indican los apartados invalidos
	 */
	public String validExternalStatusOrder(OrderPosLegacyServicePort orderPosLegacyServicePort, OrderDto orderDto,
			String companyCode, String idOperation) {

		LOG.info(String.format("%s INIT validExternalStatusOrder", idOperation));

		ResponseModel responseModel = orderPosLegacyServicePort.getOrderDetailByOrderNumberAndOrderType(
				orderDto.getOrderNumber(), orderDto.getOrderCode(), companyCode, idOperation);
		OrderDto orderDtoLegacy = (OrderDto) responseModel.getData();
		String orderStatus = "";
		if (orderDtoLegacy != null) {
			if (!orderDtoLegacy.getStatus().getCode().equals(StatusOrder.CREATE_SALE_ORDER.getValue()))
				orderStatus = "INVALID EXTERNAL STATUS ORDER: " + orderDtoLegacy.getStatus().getDescription();
		}
		return orderStatus;
	}

	/**
	 * Método que valida si la orden esta siendo editada en el sistema POSLegacy al
	 * recuperar el detalle de la orden
	 * 
	 * @param orderPosLegacyServicePort interfaz de comunicación hacia el sistema
	 *                                  POSLegacy
	 * @param orderDto                  datos operativos de la orden
	 * @param companyCode               código de compañía
	 * @param idOperation               identificador de traza de operación
	 * 
	 * @return cadena de tipo String, si la cadena esta vacía pasó todas las
	 *         validaciones, en caso contrario se indican los apartados invalidos
	 */
	public String validExternalActiveOrder(OrderPosLegacyServicePort orderPosLegacyServicePort, OrderDto orderDto,
			String companyCode, String idOperation) {
		LOG.info(String.format("%s INIT validExternalStatusOrder", idOperation));
		ResponseModel responseModel = orderPosLegacyServicePort.getOrderDetailByOrderNumberAndOrderType(
				orderDto.getOrderNumber(), orderDto.getOrderCode(), companyCode, idOperation);
		OrderDto orderDtoLegacy = (OrderDto) responseModel.getData();
		String activeStatus = "";
		if (orderDtoLegacy != null) {
			if (orderDtoLegacy.getIsUpdated())
				activeStatus = "ORDER ACTIVE IN EXTERNAL SYSTEM - ";
		}
		return activeStatus;
	}

}
