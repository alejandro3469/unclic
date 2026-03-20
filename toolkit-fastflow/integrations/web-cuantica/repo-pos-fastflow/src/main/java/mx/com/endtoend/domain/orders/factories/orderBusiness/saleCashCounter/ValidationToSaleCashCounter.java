package mx.com.endtoend.domain.orders.factories.orderBusiness.saleCashCounter;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import mx.com.endtoend.smart.bussiness.model.orders.dto.OrderDetailDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.com.endtoend.domain.orders.ports.OrderPersistencePort;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.ValidationError;
import mx.com.endtoend.infrastructure.services.jde.orders.common.serviceport.OrderJdeServicePort;
import mx.com.endtoend.smart.bussiness.model.orders.dto.OrderConfigurationDto;
import mx.com.endtoend.smart.bussiness.model.orders.dto.OrderDto;

public class ValidationToSaleCashCounter {

	private final Logger LOG = LoggerFactory.getLogger(ValidationToSaleCashCounter.class);

	/**
	 * Función principal para las validaciones del método uno de las ordenes de
	 * venta de contado mostrador. Invoca los métodos con las validacioes
	 * correspondientes a este método y concatena los resultados de cada una como
	 * una sola respuesta separadas por ;
	 * 
	 * Retorna una cadena vacia si todas las validaciones son correctas, en caso
	 * contario, devuelva la cadena con los resultados de las validaciones
	 * incorrectas
	 * 
	 * @param orderPersistencePort
	 * @param orderDto
	 * @param idOperation
	 * @param companyCode
	 * @param branchCode
	 * @return String
	 */
	public String validParamsMethodOne(OrderPersistencePort orderPersistencePort,
			OrderJdeServicePort orderJdeServicePort, OrderConfigurationDto orderConfigurationDto, OrderDto orderDto,
			String idOperation, String companyCode, String branchCode) {

		LOG.info("{} INIT validParamsMethodOne()", idOperation);
		LOG.info("{} PARAMS: [ orderConfigurationDto: {}, orderDto: {}, companyCode: {}, branchCode: {} ]", idOperation,
				orderConfigurationDto, orderDto, companyCode, branchCode);

		StringBuilder validations = new StringBuilder();

		String validationQuantities = "";
		String validationAvailability = "";
		String validClient = "";

		if (orderDto.getIsInvoiceTop()) {
			LOG.info("{} START VALIDATION: AMOUNT QUANTITIES TO INVOICE-TOP", idOperation);
			validationQuantities = validInvoiceTopDetail(orderDto, orderConfigurationDto, idOperation);
			validClient = validClientToInvoiceTop(orderDto, idOperation);
		} else {
			LOG.info("{} START VALIDATION: AMOUNT QUANTITIES IN ORDER-DETAIL", idOperation);
			validationQuantities = validOrderDetail(orderDto.getOrderDetail(), idOperation);
		}

		LOG.info("{} START VALIDATION: ITEM AVAILABILITY", idOperation);
		validationAvailability = validAvailabilityByArticle(orderPersistencePort, orderJdeServicePort,
				orderDto.getOrderDetail(), idOperation, companyCode, branchCode);

		appendValidation(validations, "INVALID REQUEST AMOUNT TO ITEMS: ", validationQuantities);
		appendValidation(validations, "ITEMS OUT OF STOCK: ", validationAvailability);
		appendValidation(validations, "INVALID CLIENT: ", validClient);

		return validations.toString();
	}

	private void appendValidation(StringBuilder validations, String messagePrefix, String validationMessage) {
		if (!validationMessage.isEmpty()) {
			validations.append(messagePrefix).append(validationMessage).append("; ");
		}
	}

	/**
	 * Método que valida que las cantiades solicitdas no sean mayor que 0 y que la
	 * lista no este vacia
	 * 
	 * En caso de que la cantidades sean validas, retorna una cadena vacia, de lo
	 * contarria retorna la lista de los artículos que no cumplen con la cantidad
	 * minima solicidata o si la lista esta vacia.
	 * 
	 * @param orderDetailDtoList
	 * @param idOperation
	 * @return String
	 */
	public String validOrderDetail(List<OrderDetailDto> orderDetailDtoList, String idOperation) {
		LOG.info("{} INIT validOrderDetail() ", idOperation);
		LOG.info("{} PARAMS: [ orderDetailDtoList: {} ]", idOperation, orderDetailDtoList);

		if (orderDetailDtoList == null || orderDetailDtoList.isEmpty()) {
			LOG.warn("{} ORDER DO NOT HAVE ORDER DETAIL LIST", idOperation);
			return "EMPTY ORDER DETAIL LIST";
		}

		return orderDetailDtoList.stream().filter(detail -> detail.getRequestAmount().compareTo(BigDecimal.ZERO) <= 0).map(detail -> {
			LOG.warn("{} AMOUNT REQUIRED NOT VALID TO ARTICLE {}", idOperation, detail.getArticleNumber());
			return detail.getArticleCode().trim() + "- ";
		}).collect(Collectors.joining());
	}

	/**
	 * Método que valida que la orden generada cumpla con las condiciones necesarias
	 * para la creación de ordenes con tope de facturas
	 * 
	 * @param orderDto
	 * @param idOperation
	 * @return
	 */
	public String validInvoiceTopDetail(OrderDto orderDto, OrderConfigurationDto orderConfigurationDto,
			String idOperation) {
		LOG.info("{} INIT validInvoiceTopDetail() ", idOperation);
		LOG.info("{} PARAMS: [ orderDto: {} , orderConfigurationDto: {} ]", idOperation, orderDto,
				orderConfigurationDto);

		if (!orderConfigurationDto.getIsApplyTopInvoice()) {
			LOG.warn("{} ORDER DOES NOT APPLY INVOICE TOP", idOperation);
			throw new ValidationError("ORDER DOES NOT APPLY INVOICE TOP");
		}

		boolean sameArticle = orderDto.getOrderDetail().stream().map(OrderDetailDto::getArticleCode).distinct()
				.count() == 1;

		return sameArticle ? "" : "THERE IS MORE THAN ONE SKU ON THE LIST";
	}

	/**
	 * Método que valida que el cliente en facturas con tope no sea genérico
	 * 
	 * @param orderDto
	 * @param idOperation
	 * @return
	 */
	public String validClientToInvoiceTop(OrderDto orderDto, String idOperation) {
		LOG.info("{} INIT validInvoiceTopDetail() ", idOperation);
		return orderDto.getClient().getId() == 1 ? "GENERIC CLIENT NOT ALLOWED" : "";
	}

	/**
	 * Método que valida la cantidad de artículos solicitados para las ventas con
	 * tope de facturación si la orden esta configurada para tope de factura, en
	 * caso de que no cuente con esta configuración se lanzara una excepción
	 * 
	 * @param orderDetailDtoList
	 * @param idOperation
	 * @return
	 */
	public String validInvoiceTopRequestAmount(List<OrderDetailDto> orderDetailDtoList,
			OrderConfigurationDto orderConfigurationDto, String idOperation) {
		LOG.info("{} INIT validInvoiceTopRequestAmount() ", idOperation);
		LOG.info("{} PARAMS: [ orderDetailDtoList: {} , orderConfigurationDto: {} ]", idOperation, orderDetailDtoList,
				orderConfigurationDto);

		if (!orderConfigurationDto.getIsApplyTopInvoice()) {
			LOG.warn("{} ORDER DOES NOT APPLY INVOICE TOP", idOperation);
			throw new ValidationError("ORDER DOES NOT APPLY INVOICE TOP");
		}

		if (orderDetailDtoList != null && !orderDetailDtoList.isEmpty() && orderDetailDtoList.size() > 1) {
			return "INVALID REQUEST AMOUNT USING INVOICE TOP";
		}
		return "";
	}

	/**
	 * Método que valida la existencia de los artículos ingresados en la orden.
	 * 
	 * Retorna una cadena vacía si todos los artículos tienen disponibilidad, en
	 * caso contrario, retorna una cadena con los códigos de los artículos no
	 * disponibles
	 * 
	 * @param orderPersistencePort
	 * @param orderDetailDtoList
	 * @param idOperation
	 * @param companyCode
	 * @param branchCode
	 * @return String
	 */
	public String validAvailabilityByArticle(OrderPersistencePort orderPersistencePort,
			OrderJdeServicePort orderJdeServicePort, List<OrderDetailDto> orderDetailDtoList, String idOperation,
			String companyCode, String branchCode) {

		LOG.info("{} INIT validAvailabilityByArticle()", idOperation);
		LOG.info("{} PARAMS: [ orderDetailDtoList: {}, companyCode: {}, branchCode: {} ]", idOperation,
				orderDetailDtoList, companyCode, branchCode);

		StringBuilder articlesNotAvailabilities = new StringBuilder();

		for (OrderDetailDto orderDetailDto : orderDetailDtoList) {
			if (!orderDetailDto.getIsCustumArticle()) {
				boolean isAvailability = (boolean) orderJdeServicePort.getIsItemAvailabilityByParams(
						orderDetailDto.getArticleNumber(), companyCode, branchCode, orderDetailDto.getWarehouseCode(),
						orderDetailDto.getRequestAmount(), orderDetailDto.getUnitMeasurement(),
						orderDetailDto.getPrimaryUnitMeasure(), idOperation).getData();

				if (!isAvailability) {
					articlesNotAvailabilities.append(orderDetailDto.getArticleCode().trim()).append(", ");
				}
			}
		}

		if (articlesNotAvailabilities.length() > 0) {
			LOG.warn("{} UNAVAILABLE ITEMS: {}", idOperation, articlesNotAvailabilities);
			articlesNotAvailabilities.append(" ; ");
		}

		return articlesNotAvailabilities.toString();
	}
}