package mx.com.endtoend.domain.orders.services;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import mx.com.endtoend.domain.orders.dto.CustomInterfaceOrderParams;
import mx.com.endtoend.domain.orders.dto.GenericActionControllOrderDto;
import mx.com.endtoend.domain.orders.dto.GenericSerchParamsOrderDto;
import mx.com.endtoend.domain.orders.factories.OrderInterface;
import mx.com.endtoend.domain.orders.ports.OrderPersistencePort;
import mx.com.endtoend.domain.orders.ports.OrderV2ServicePort;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.GlobalError;
import mx.com.endtoend.infrastructure.services.jde.payments.demo.business.PaymentDemoJdeRepository;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;
import mx.com.endtoend.smart.bussiness.model.orders.dto.OrderDto;
import mx.com.smartbussiness.generic.utils.DecimalPrecisionUtils;
import mx.com.smartbussiness.generic.validators.PrecisionValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Clase encargada de obtener la implementación concreta de la interfaz
 * {@link OrderInterface} según la configuración de cada compañía registrada en
 * el sistema
 * 
 * @author ddcasas
 * 
 */


public class OrderServiceImpl extends BaseOrderService implements OrderV2ServicePort {

	public OrderServiceImpl(OrderPersistencePort orderPersistencePort) {
		super(orderPersistencePort);
	}
	private final Logger LOG = LoggerFactory.getLogger(PaymentDemoJdeRepository.class);

	/**
	 * Valida la precisión decimal de un OrderDto según estándares SAT
	 * 
	 * @param orderDto DTO de orden a validar
	 * @param idOperation ID de operación para logging
	 */
	private void validateOrderPrecision(OrderDto orderDto, String idOperation) {
		LOG.info(String.format("%s INIT validateOrderPrecision()", idOperation));
		
		// Validar orderTotal
		if (orderDto.getOrderTotal() != null && orderDto.getOrderTotal().compareTo(BigDecimal.ZERO) != 0) {
			if (!PrecisionValidator.isValidMonetaryRange(orderDto.getOrderTotal())) {
				LOG.error(String.format("%s ERROR: orderTotal fuera de rango válido: %s", idOperation, orderDto.getOrderTotal()));
				throw new GlobalError();
			}
			if (!PrecisionValidator.isValidScale(orderDto.getOrderTotal(), 2)) {
				LOG.error(String.format("%s ERROR: orderTotal con escala incorrecta: %s", idOperation, orderDto.getOrderTotal()));
				throw new GlobalError();
			}
		}
		
		// Validar pendingPayment
		if (orderDto.getPendingPayment() != null && orderDto.getPendingPayment().compareTo(BigDecimal.ZERO) != 0) {
			if (!PrecisionValidator.isValidMonetaryRange(orderDto.getPendingPayment())) {
				LOG.error(String.format("%s ERROR: pendingPayment fuera de rango válido: %s", idOperation, orderDto.getPendingPayment()));
				throw new GlobalError();
			}
			if (!PrecisionValidator.isValidScale(orderDto.getPendingPayment(), 2)) {
				LOG.error(String.format("%s ERROR: pendingPayment con escala incorrecta: %s", idOperation, orderDto.getPendingPayment()));
				throw new GlobalError();
			}
		}
		
		// Validar subTotal
		if (orderDto.getSubTotal() != null && orderDto.getSubTotal().compareTo(BigDecimal.ZERO) != 0) {
			if (!PrecisionValidator.isValidMonetaryRange(orderDto.getSubTotal())) {
				LOG.error(String.format("%s ERROR: subTotal fuera de rango válido: %s", idOperation, orderDto.getSubTotal()));
				throw new GlobalError();
			}
			if (!PrecisionValidator.isValidScale(orderDto.getSubTotal(), 2)) {
				LOG.error(String.format("%s ERROR: subTotal con escala incorrecta: %s", idOperation, orderDto.getSubTotal()));
				throw new GlobalError();
			}
		}
		
		// Validar ivaTotal
		if (orderDto.getIvaTotal() != null && orderDto.getIvaTotal().compareTo(BigDecimal.ZERO) != 0) {
			if (!PrecisionValidator.isValidMonetaryRange(orderDto.getIvaTotal())) {
				LOG.error(String.format("%s ERROR: ivaTotal fuera de rango válido: %s", idOperation, orderDto.getIvaTotal()));
				throw new GlobalError();
			}
			if (!PrecisionValidator.isValidScale(orderDto.getIvaTotal(), 2)) {
				LOG.error(String.format("%s ERROR: ivaTotal con escala incorrecta: %s", idOperation, orderDto.getIvaTotal()));
				throw new GlobalError();
			}
		}
		
		LOG.info(String.format("%s SUCCESS: OrderDto validado correctamente", idOperation));
	}

	/**
	 * Aplica redondeo SAT a un OrderDto
	 * 
	 * @param orderDto DTO de orden a redondear
	 * @param idOperation ID de operación para logging
	 * @return OrderDto con valores redondeados según SAT
	 */
	private OrderDto applySATRounding(OrderDto orderDto, String idOperation) {
		LOG.info(String.format("%s INIT applySATRounding()", idOperation));
		
		// Aplicar redondeo SAT a orderTotal
		if (orderDto.getOrderTotal() != null && orderDto.getOrderTotal().compareTo(BigDecimal.ZERO) != 0) {
			BigDecimal roundedOrderTotal = DecimalPrecisionUtils.roundToTwoDecimals(orderDto.getOrderTotal());
			orderDto.setOrderTotal(roundedOrderTotal);
			LOG.info(String.format("%s orderTotal redondeado: %s", idOperation, roundedOrderTotal));
		}
		
		// Aplicar redondeo SAT a pendingPayment
		if (orderDto.getPendingPayment() != null && orderDto.getPendingPayment().compareTo(BigDecimal.ZERO) != 0) {
			BigDecimal roundedPendingPayment = DecimalPrecisionUtils.roundToTwoDecimals(orderDto.getPendingPayment());
			orderDto.setPendingPayment(roundedPendingPayment);
			LOG.info(String.format("%s pendingPayment redondeado: %s", idOperation, roundedPendingPayment));
		}
		
		// Aplicar redondeo SAT a subTotal
		if (orderDto.getSubTotal() != null && orderDto.getSubTotal().compareTo(BigDecimal.ZERO) != 0) {
			BigDecimal roundedSubTotal = DecimalPrecisionUtils.roundToTwoDecimals(orderDto.getSubTotal());
			orderDto.setSubTotal(roundedSubTotal);
			LOG.info(String.format("%s subTotal redondeado: %s", idOperation, roundedSubTotal));
		}
		
		// Aplicar redondeo SAT a ivaTotal
		if (orderDto.getIvaTotal() != null && orderDto.getIvaTotal().compareTo(BigDecimal.ZERO) != 0) {
			BigDecimal roundedIvaTotal = DecimalPrecisionUtils.roundToTwoDecimals(orderDto.getIvaTotal());
			orderDto.setIvaTotal(roundedIvaTotal);
			LOG.info(String.format("%s ivaTotal redondeado: %s", idOperation, roundedIvaTotal));
		}
		
		LOG.info(String.format("%s SUCCESS: OrderDto redondeado según SAT", idOperation));
		return orderDto;
	}

	@Override
	public ResponseModel createOrder(CustomInterfaceOrderParams customInterfaceOrderParams, OrderDto orderDto,
			String method, Boolean isConvertion, Boolean mailUsuario) {
		
		// Validar precisión decimal del OrderDto
		validateOrderPrecision(orderDto, customInterfaceOrderParams.getIdOperation());
		
		// Aplicar redondeo SAT
		orderDto = applySATRounding(orderDto, customInterfaceOrderParams.getIdOperation());
		
		return getOrderBusiness(method, customInterfaceOrderParams)
				.createOrder(orderDto, isConvertion, mailUsuario);
	}

	@Override
	public ResponseModel updateOrder(CustomInterfaceOrderParams customInterfaceOrderParams, OrderDto orderDto,
			String method, Boolean mailUsuario) {
		
		// Validar precisión decimal del OrderDto
		validateOrderPrecision(orderDto, customInterfaceOrderParams.getIdOperation());
		
		// Aplicar redondeo SAT
		orderDto = applySATRounding(orderDto, customInterfaceOrderParams.getIdOperation());
		
		return getOrderBusiness(method, customInterfaceOrderParams)
				.updateOrder(orderDto, mailUsuario);
	}

	@Override
	public ResponseModel getOrderToUpdate(CustomInterfaceOrderParams customInterfaceOrderParams, BigDecimal orderNumber,
			String orderType, String method) {
		return getOrderBusiness(method, customInterfaceOrderParams)
				.getOrderToUpdate(orderNumber, orderType);
	}

	@Override
	public ResponseModel cancelUpdateOrder(CustomInterfaceOrderParams customInterfaceOrderParams,
			BigDecimal orderNumber, String email, String orderType, String method) {
		return getOrderBusiness(method, customInterfaceOrderParams)
				.cancelUpdateOrder(orderNumber, email, orderType);
	}

	@Override
	public ResponseModel approveOrder(CustomInterfaceOrderParams customInterfaceOrderParams,
			GenericActionControllOrderDto genericActionControllOrderDto, String method) {
		return getOrderBusiness(method, customInterfaceOrderParams)
				.approveOrder(genericActionControllOrderDto);
	}

	@Override
	public ResponseModel cancelOrder(CustomInterfaceOrderParams customInterfaceOrderParams,
			GenericActionControllOrderDto genericActionControllOrderDto, String method) {
		return getOrderBusiness(method, customInterfaceOrderParams)
				.cancelOrder(genericActionControllOrderDto);
	}

	@Override
	public ResponseModel getOrderListByParams(CustomInterfaceOrderParams customInterfaceOrderParams,
			GenericSerchParamsOrderDto params, String email, String method) {
		return getOrderBusiness(method, customInterfaceOrderParams)
				.getOrderListByParams(params, email);
	}

	@Override
	public ResponseModel getAllOrdersListByCompany(CustomInterfaceOrderParams customInterfaceOrderParams,
												   GenericSerchParamsOrderDto params, String email, String method) {
		return getOrderBusiness(method, customInterfaceOrderParams)
				.getAllOrdersListByCompany(params, email);
	}

	@Override
	public ResponseModel viewOderDetailByOrderNumber(CustomInterfaceOrderParams customInterfaceOrderParams,
			BigDecimal orderNumber, String orderType, String method) {
		return getOrderBusiness(method, customInterfaceOrderParams)
			    .viewOderDetailByOrderNumber(orderNumber, orderType);
	}

	@Override
	public ResponseModel viewHistoricalOrderDetailByOrderNumber(CustomInterfaceOrderParams customInterfaceOrderParams,
			BigDecimal orderNumber, String orderType, String method) {
		return getOrderBusiness(method, customInterfaceOrderParams)
				.viewHistoricalOrderDetailByOrderNumber(orderNumber, orderType);
	}

	@Override
	public ResponseModel sendDocumentByEmailAndCompanyCode(CustomInterfaceOrderParams customInterfaceOrderParams,
			List<String> emails, BigDecimal orderNumber, String orderType, String method) {
		return getOrderBusiness(method, customInterfaceOrderParams)
				.sendDocumentByEmailAndCompanyCode(emails, orderNumber, orderType);
	}

	@Override
	public ResponseModel generateTicketByOrderNumberAndOrderType(CustomInterfaceOrderParams customInterfaceOrderParams,
			BigDecimal orderNumber, String orderType, String method) {
		return getOrderBusiness(method, customInterfaceOrderParams)
				.generateTicketByOrderNumberAndOrderType(orderNumber, orderType);
	}

	@Override
	public ResponseModel generateDocumentByOrderNumberAndOrderType(
			CustomInterfaceOrderParams customInterfaceOrderParams, BigDecimal orderNumber, String orderType,
			String method) {
		return getOrderBusiness(method, customInterfaceOrderParams)
				.generateDocumentByOrderNumberAndOrderType(orderNumber, orderType);
	}

	@Override
	public ResponseModel convertQuoteOrderToSaleOrder(CustomInterfaceOrderParams customInterfaceOrderParams,
			OrderDto orderDto, String orderCode, String method, Boolean mailUsuario) {
		return getOrderBusiness(method, customInterfaceOrderParams)
				.convertQuoteOrderToSaleOrder(orderDto, orderCode, mailUsuario);
	}
}