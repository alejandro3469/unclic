package mx.com.endtoend.domain.orders.factories.orderBusiness.saleDirect;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import mx.com.endtoend.smart.bussiness.model.orders.dto.OrderDetailDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.com.endtoend.genericCommonsFileds.utilities.DateUtil;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.ValidationError;
import mx.com.endtoend.smart.bussiness.model.orders.dto.OrderConfigurationDto;
import mx.com.endtoend.smart.bussiness.model.orders.dto.OrderDto;

/**
 * 
 * @author ddcasas
 *
 */

public class ValidationToSaleDirectOrder {

	private final Logger LOG = LoggerFactory.getLogger(ValidationToSaleDirectOrder.class);

	/**
	 * Método que llama a todas las funciones de validación asociadas al método
	 * SALE_DIRECT_ONE:
	 * 
	 * Retorna un String con los resultados de las validaciones
	 * 
	 * @param orderDto
	 * @param idOperation
	 * @return String
	 */
	public String validParamsMethodOne(OrderDto orderDto, OrderConfigurationDto orderConfigurationDto,
			String idOperation) {
		LOG.info("{} VALIDATION TO METHOD ONE", idOperation);

		StringBuilder validations = new StringBuilder();

		if (orderDto.getIsInvoiceTop()) {
			LOG.info("{} START VALIDATION: AMOUNT QUANTITIES TO INVOICE-TOP", idOperation);
			appendValidation(validations, "INVALID REQUEST AMOUNTO TO ITEMS: ",
					validInvoiceTopDetail(orderDto, orderConfigurationDto, idOperation));
			appendValidation(validations, "INVALID CLIENT: ", validClientToInvoiceTop(orderDto, idOperation));
		} else {
			LOG.info("{} START VALIDATION: AMOUNT QUANTITIES IN ORDER-DETAIL", idOperation);
			appendValidation(validations, "INVALID REQUEST AMOUNTO TO ITEMS: ",
					validOrderDetail(orderDto.getOrderDetail(), idOperation));
		}

		LOG.info("{} START VALIDATION: REQUIRED FIELDS IN ORDER", idOperation);
		appendValidation(validations, "REQUIRED PARAMETERS: ", validateRequiredFields(orderDto, idOperation));

		return validations.toString();
	}

	private void appendValidation(StringBuilder validations, String prefix, String validation) {
		if (validation != null && !validation.isEmpty()) {
			validations.append(prefix).append(validation).append("; ");
		}
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

		if (!orderConfigurationDto.getIsApplyTopInvoice()) {
			LOG.warn("{} ORDER DOES NOT APPLY INVOICE TOP", idOperation);
			throw new ValidationError("ORDER DOES NOT APPLY INVOICE TOP");
		}

		boolean sameArticle = orderDto.getOrderDetail().stream().map(OrderDetailDto::getArticleCode).distinct()
				.count() == 1;

		return sameArticle ? "" : "THERE IS MORE THAN ONE SKU ON THE LIST";
	}

	/**
	 * Valida que la lista de los artículos ingresados no este vacia y la cantidad
	 * solicitada sea mayor que 0, retorna la lista de los artículos invalidos en
	 * caso de existir
	 * 
	 * @param orderDetailDtoList
	 * @param idOperation
	 * @return String
	 */
	public String validOrderDetail(List<OrderDetailDto> orderDetailDtoList, String idOperation) {
		LOG.info("{} INIT validOrderDetail() ", idOperation);

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
	 * Valida los campos obligatorios de la orden ingresada, en caso de que uno no
	 * sea válido retorna la lista de los campos incorrectos en caso de existir
	 * 
	 * @param orderDto
	 * @param idOperation
	 * @return String
	 */
	public String validateRequiredFields(OrderDto orderDto, String idOperation) {
		LOG.info("{} INIT validateRequiredFields() ", idOperation);
		
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
			if ((percentageAuthorized.multiply(BigDecimal.valueOf(100))).compareTo(orderDetailDto.getDiscountSeller()) < 0) {
				System.out.println((percentageAuthorized.multiply(BigDecimal.valueOf(100))) + " - " + orderDetailDto.getDiscountSeller());
				validation = validation + orderDetailDto.getArticleCode().trim() + " ";
			}
		}
		return validation;
	}
}