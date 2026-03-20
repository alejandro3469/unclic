package mx.com.endtoend.domain.orders.factories.orderBusiness.saleCounter;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import mx.com.endtoend.smart.bussiness.model.orders.dto.OrderDetailDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.com.endtoend.domain.orders.ports.OrderPersistencePort;
import mx.com.endtoend.genericCommonsFileds.utilities.DateUtil;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.ValidationError;
import mx.com.endtoend.infrastructure.services.jde.orders.common.serviceport.OrderJdeServicePort;
import mx.com.endtoend.smart.bussiness.model.orders.dto.OrderConfigurationDto;
import mx.com.endtoend.smart.bussiness.model.orders.dto.OrderDto;

/**
 * 
 * @author ddcasas
 *
 */

public class ValidationToSaleCounterOrder {

	private final Logger LOG = LoggerFactory.getLogger(ValidationToSaleCounterOrder.class);

	public String validParamsMethodOne(OrderPersistencePort orderPersistencePort,
			OrderJdeServicePort orderJdeServicePort, OrderConfigurationDto orderConfigurationDto, OrderDto orderDto,
			String idOperation, String companyCode, String branchCode) {

		LOG.info("{} VALIDATION TO METHOD ONE", idOperation);

		StringBuilder validations = new StringBuilder();

		if (orderDto.getIsInvoiceTop()) {
			LOG.info("{} START VALIDATION: AMOUNT QUANTITIES TO INVOICE-TOP", idOperation);
			String validationQuantities = validInvoiceTopDetail(orderDto, orderConfigurationDto, idOperation);
			String validClient = validClientToInvoiceTop(orderDto, idOperation);

			if (!validationQuantities.isEmpty()) {
				validations.append("INVALID REQUEST AMOUNTO TO ITEMS: ").append(validationQuantities).append("; ");
			}
			if (!validClient.isEmpty()) {
				validations.append("INVALID CLIENT: ").append(validClient).append("; ");
			}
		} else {
			LOG.info("{} START VALIDATION: AMOUNT QUANTITIES IN ORDER-DETAIL", idOperation);
			String validationQuantities = validOrderDetail(orderDto.getOrderDetail(), idOperation);

			if (!validationQuantities.isEmpty()) {
				validations.append("INVALID REQUEST AMOUNTO TO ITEMS: ").append(validationQuantities).append("; ");
			}
		}

		LOG.info("{} START VALIDATION: ITEM AVAILABILITY", idOperation);
		String validationAvailability = validAvailabilityByArticle(orderPersistencePort, orderJdeServicePort,
				orderDto.getOrderDetail(), idOperation, companyCode, branchCode);
		if (!validationAvailability.isEmpty()) {
			validations.append("ITEMS OUT OF STOCK: ").append(validationAvailability).append("; ");
		}

		LOG.info("{} START VALIDATION: REQUIRED FIELDS IN ORDER", idOperation);
		String validationRequiredFields = validateRequiredFields(orderDto, idOperation);
		if (!validationRequiredFields.isEmpty()) {
			validations.append("REQUIRED PARAMETERS: ").append(validationRequiredFields).append("; ");
		}

		return validations.toString();
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
	 * Método que valida que las cantiades solicitdas no sean menor que 0 y que la
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

	public String validateRequiredFields(OrderDto orderDto, String idOperation) {
		LOG.info("{} INIT validateRequiredFields() ", idOperation);
		LOG.info("{} PARAMS: [ orderDto: {} ]", idOperation, orderDto);

		StringBuilder valid = new StringBuilder();

		appendIfEmpty(valid, orderDto.getBranchCode(), "branchCode");
		appendIfNull(valid, orderDto.getClient(), "client");
		appendIfNull(valid, orderDto.getAddresses(), "address");
		appendIfEmpty(valid, orderDto.getOrderCode(), "order type");
		appendIfEmpty(valid, orderDto.getCfdiType(), "cfid");
		appendIfEmpty(valid, orderDto.getEmployeeEmail(), "employeeEmail");

		if (DateUtil.isBeforeToCurrentDay(orderDto.getRequestDate())) {
			valid.append(" - REQUEST DATE CAN NOT BE BEFORE TO CURRENT DAY");
		}

		return valid.toString().trim();
	}

	private void appendIfEmpty(StringBuilder sb, String field, String fieldName) {
		if (field == null || field.isEmpty()) {
			sb.append(" - ").append(fieldName).append(" IS REQUIRED");
		}
	}

	private void appendIfNull(StringBuilder sb, Object field, String fieldName) {
		if (field == null) {
			sb.append(" - ").append(fieldName).append(" IS REQUIRED");
		}
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
		}

		return articlesNotAvailabilities.toString().isEmpty() ? "" : articlesNotAvailabilities.toString() + " ; ";
	}

	/**
	 * Método que valida que el porcentaje de descuento aplicada a cada línea de una
	 * orden retenida, no supere el permitido
	 * 
	 * @param orderDetailDtoList   detalle de la orden
	 * @param percentageAuthorized porcentaje de descuento configurado a un empleado
	 * @return cadena vacia si el porcentaje de descuento es valido para cada línea,
	 *         en caso contrario se indicará el SKU de los artículos que tienen un
	 *         descuento superior al configurado
	 */
	public String validDiscountToApprobeOrder(List<OrderDetailDto> orderDetailDtoList, BigDecimal percentageAuthorized) {
		String validation = "";
		for (OrderDetailDto orderDetailDto : orderDetailDtoList) {
			if ((percentageAuthorized.multiply(BigDecimal.valueOf(100)).compareTo(orderDetailDto.getDiscountSeller()) < 0)) {
				System.out.println((percentageAuthorized.multiply(BigDecimal.valueOf(100))) + " - " + orderDetailDto.getDiscountSeller());
				validation = validation + orderDetailDto.getArticleCode().trim() + " ";
			}
		}
		return validation;
	}
}
