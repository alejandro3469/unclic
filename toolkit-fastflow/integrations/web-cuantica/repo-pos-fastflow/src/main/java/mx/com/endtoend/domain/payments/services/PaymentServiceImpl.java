package mx.com.endtoend.domain.payments.services;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.com.endtoend.domain.creditNote.dto.AuthorizationParmasDto;
import mx.com.endtoend.domain.orders.dto.GenericSerchParamsOrderDto;
import mx.com.endtoend.domain.payments.business.PaymentFactory;
import mx.com.endtoend.domain.payments.business.PaymentInterface;
import mx.com.endtoend.domain.payments.dto.GenericSearchPaymentDto;
import mx.com.endtoend.domain.payments.dto.PaymentCustomParams;
import mx.com.endtoend.domain.payments.ports.PaymentPersistencePort;
import mx.com.endtoend.domain.payments.ports.PaymentServicePort;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.GlobalError;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.ValidationError;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;
import mx.com.endtoend.smart.bussiness.model.payments.PaymentDto;
import mx.com.smartbussiness.generic.utils.DecimalPrecisionUtils;
import mx.com.smartbussiness.generic.validators.PrecisionValidator;

public class PaymentServiceImpl implements PaymentServicePort {

	private PaymentPersistencePort paymentPersistencePort;

	public PaymentServiceImpl(PaymentPersistencePort paymentPersistencePort) {
		this.paymentPersistencePort = paymentPersistencePort;
	}

	private PaymentFactory paymentFactory = new PaymentFactory();

	private final Logger LOG = LoggerFactory.getLogger(PaymentServiceImpl.class);

	/**
	 * Valida la precisión decimal de un PaymentDto según estándares SAT
	 * 
	 * @param paymentDto DTO de pago a validar
	 * @param idOperation ID de operación para logging
	 */
	private void validatePaymentPrecision(PaymentDto paymentDto, String idOperation) {
		LOG.info(String.format("%s INIT validatePaymentPrecision()", idOperation));
		
		// Validar orderTotal
		if (paymentDto.getOrderTotal() != null) {
			if (!PrecisionValidator.isValidMonetaryRange(paymentDto.getOrderTotal())) {
				LOG.error(String.format("%s ERROR: orderTotal fuera de rango válido: %s", idOperation, paymentDto.getOrderTotal()));
				throw new GlobalError();
			}
			if (!PrecisionValidator.isValidScale(paymentDto.getOrderTotal(), 2)) {
				LOG.error(String.format("%s ERROR: orderTotal con escala incorrecta: %s", idOperation, paymentDto.getOrderTotal()));
				throw new GlobalError();
			}
		}
		
		// Validar pendingPayment
		if (paymentDto.getPendingPayment() != null) {
			if (!PrecisionValidator.isValidMonetaryRange(paymentDto.getPendingPayment())) {
				LOG.error(String.format("%s ERROR: pendingPayment fuera de rango válido: %s", idOperation, paymentDto.getPendingPayment()));
				throw new GlobalError();
			}
			if (!PrecisionValidator.isValidScale(paymentDto.getPendingPayment(), 2)) {
				LOG.error(String.format("%s ERROR: pendingPayment con escala incorrecta: %s", idOperation, paymentDto.getPendingPayment()));
				throw new GlobalError();
			}
		}
		
		LOG.info(String.format("%s SUCCESS: PaymentDto validado correctamente", idOperation));
	}

	/**
	 * Aplica redondeo SAT a un PaymentDto
	 * 
	 * @param paymentDto DTO de pago a redondear
	 * @param idOperation ID de operación para logging
	 * @return PaymentDto con valores redondeados según SAT
	 */
	private PaymentDto applySATRounding(PaymentDto paymentDto, String idOperation) {
		LOG.info(String.format("%s INIT applySATRounding()", idOperation));
		
		// Aplicar redondeo SAT a orderTotal
		if (paymentDto.getOrderTotal() != null) {
			BigDecimal roundedOrderTotal = DecimalPrecisionUtils.roundToTwoDecimals(paymentDto.getOrderTotal());
			paymentDto.setOrderTotal(roundedOrderTotal);
			LOG.info(String.format("%s orderTotal redondeado: %s", idOperation, roundedOrderTotal));
		}
		
		// Aplicar redondeo SAT a pendingPayment
		if (paymentDto.getPendingPayment() != null) {
			BigDecimal roundedPendingPayment = DecimalPrecisionUtils.roundToTwoDecimals(paymentDto.getPendingPayment());
			paymentDto.setPendingPayment(roundedPendingPayment);
			LOG.info(String.format("%s pendingPayment redondeado: %s", idOperation, roundedPendingPayment));
		}
		
		LOG.info(String.format("%s SUCCESS: PaymentDto redondeado según SAT", idOperation));
		return paymentDto;
	}

	/**
	 * Valida que al menos uno de los arrays de pagos tenga elementos
	 * 
	 * @param paymentDto DTO de pago a validar
	 * @param idOperation ID de operación para logging
	 * @return true si hay al menos un pago, false en caso contrario
	 */
	private boolean validatePaymentArrays(PaymentDto paymentDto, String idOperation) {
		LOG.info(String.format("%s INIT validatePaymentArrays()", idOperation));
		int totalCount = 0;
		if (paymentDto.getPaymentCashList() != null) {
			totalCount += paymentDto.getPaymentCashList().size();
		}
		if (paymentDto.getCreditCardPaymentList() != null) {
			totalCount += paymentDto.getCreditCardPaymentList().size();
		}
		if (paymentDto.getTransferPaymentList() != null) {
			totalCount += paymentDto.getTransferPaymentList().size();
		}
		if (paymentDto.getCheckPaymentList() != null) {
			totalCount += paymentDto.getCheckPaymentList().size();
		}
		if (paymentDto.getCreditPaymentList() != null) {
			totalCount += paymentDto.getCreditPaymentList().size();
		}
		if (paymentDto.getCreditNotePaymentList() != null) {
			totalCount += paymentDto.getCreditNotePaymentList().size();
		}
		boolean hasPayments = totalCount > 0;
		LOG.info(String.format("%s validatePaymentArrays result: %b (total payments: %d)", idOperation, hasPayments, totalCount));
		return hasPayments;
	}

	/**
	 * Método que se encarga de obtener la implementación concreta para la
	 * recuperación del estado de las órdenes de venta con base en la configuración
	 * de la compañía correspondiente
	 */
	@Override
	public ResponseModel getOrderByCompanyCodeAndParams(PaymentCustomParams paymentCustomParams,
			GenericSerchParamsOrderDto genericSerchParamsOrderDto, String method) {
		LOG.info(String.format("%s INIT getOrderByCompanyCodeAndParams()", paymentCustomParams.getIdOperation()));
		PaymentInterface payment = paymentFactory.getImplementationByCode(method, paymentCustomParams);
		if (payment == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ",
					paymentCustomParams.getIdOperation()));
			throw new GlobalError();
		}
		ResponseModel responseFromImplementation = payment.getOrderByCompanyCodeAndParams(paymentPersistencePort,
				paymentCustomParams, genericSerchParamsOrderDto);
		return responseFromImplementation;
	} // @saveAllOrders


	@Override
	public void saveAllOrders(PaymentCustomParams paymentCustomParams) {
		PaymentInterface payment = paymentFactory.getImplementationByCode("PAYMENT_ONE", paymentCustomParams);
		ResponseModel responseFromImplementation = payment.saveAllOrders(paymentPersistencePort);
	}

	/**
	 * Método que se encarga de obtener la implementación concreta para el registro
	 * de cobros en el sistema con base en la configuración de la compañía
	 * correspondiente
	 */
	@Override
	public ResponseModel createPayment(PaymentCustomParams paymentCustomParams, String method, PaymentDto paymentDto) {
		LOG.info(String.format("%s INIT createPayment()", paymentCustomParams.getIdOperation()));
		
		// Validar precisión decimal del PaymentDto
		validatePaymentPrecision(paymentDto, paymentCustomParams.getIdOperation());
		
		// Aplicar redondeo SAT
		paymentDto = applySATRounding(paymentDto, paymentCustomParams.getIdOperation());
		
		// Validación: Debe incluir al menos una forma de pago
		boolean hasPayments = validatePaymentArrays(paymentDto, paymentCustomParams.getIdOperation());
		if (!hasPayments) {
			LOG.error(String.format("%s ERROR: No se encontraron formas de pago", paymentCustomParams.getIdOperation()));
			throw new ValidationError("Debe incluir al menos una forma de pago para realizar el cobro.");
		}
		
		PaymentInterface payment = paymentFactory.getImplementationByCode(method, paymentCustomParams);
		if (payment == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ",
					paymentCustomParams.getIdOperation()));
			throw new GlobalError();
		}
		ResponseModel responseFromImplementation = payment.createPayment(paymentPersistencePort, paymentCustomParams,
				paymentDto);
		return responseFromImplementation;
	}

	/**
	 * Método que se encarga de obtener la implementación concreta para la
	 * cancelación del proceso de cobros en el sistema con base en la configuración
	 * de la compañía correspondiente
	 */
	@Override
	public ResponseModel cancelPaymentByCompanyCodeAndParams(PaymentCustomParams paymentCustomParams, String method,
			GenericSerchParamsOrderDto genericSerchParamsOrderDto) {
		LOG.info(String.format("%s INIT cancelPaymentByCompanyCodeAndParams()", paymentCustomParams.getIdOperation()));
		PaymentInterface payment = paymentFactory.getImplementationByCode(method, paymentCustomParams);
		if (payment == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ",
					paymentCustomParams.getIdOperation()));
			throw new GlobalError();
		}
		ResponseModel responseFromImplementation = payment.cancelPaymentByParams(paymentPersistencePort,
				paymentCustomParams, genericSerchParamsOrderDto);
		return responseFromImplementation;
	}

	/**
	 * Método que se encarga de obtener la implementación concreta para la
	 * generación de tickets de cobro con base en la configuración de la compañía
	 * correspondiente
	 */
	@Override
	public ResponseModel createPaymentTicketByOrderCodeAndType(PaymentCustomParams paymentCustomParams, String method,
			BigDecimal orderNumber, String orderCode) {
		LOG.info(
				String.format("%s INIT createPaymentTicketByOrderCodeAndType()", paymentCustomParams.getIdOperation()));
		PaymentInterface payment = paymentFactory.getImplementationByCode(method, paymentCustomParams);
		if (payment == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ",
					paymentCustomParams.getIdOperation()));
			throw new GlobalError();
		}
		ResponseModel responseFromImplementation = payment.createPaymentTicketByOrderCodeAndType(paymentPersistencePort,
				paymentCustomParams, orderNumber, orderCode);
		return responseFromImplementation;
	}

	/**
	 * Método que se encarga de obtener la implementación concreta para la ontención
	 * del resumen de ordenes cobradas en el sistema
	 */
	@Override
	public ResponseModel searchPaidOrderSummaryByCompanyCode(PaymentCustomParams paymentCustomParams,
			GenericSearchPaymentDto genericSearchPaymentDto, String method) {
		LOG.info(String.format("%s INIT searchPaidOrderSummaryByCompanyCode()", paymentCustomParams.getIdOperation()));
		PaymentInterface payment = paymentFactory.getImplementationByCode(method, paymentCustomParams);
		if (payment == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ",
					paymentCustomParams.getIdOperation()));
			throw new GlobalError();
		}
		ResponseModel responseFromImplementation = payment.searchPaidOrderSummaryByCompanyCode(paymentPersistencePort,
				paymentCustomParams, genericSearchPaymentDto);
		return responseFromImplementation;
	}

	/**
	 * Método que se encarga de obtener la implementación concreta para la obtención
	 * del detalle de ordenes cobradas en el sistema
	 */
	@Override
	public ResponseModel searchPaidOrderDetailByCompanyCode(PaymentCustomParams paymentCustomParams,
			GenericSearchPaymentDto genericSearchPaymentDto, String method) {
		LOG.info(String.format("%s INIT searchPaidOrderDetailByCompanyCode()", paymentCustomParams.getIdOperation()));
		PaymentInterface payment = paymentFactory.getImplementationByCode(method, paymentCustomParams);
		if (payment == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ",
					paymentCustomParams.getIdOperation()));
			throw new GlobalError();
		}
		ResponseModel responseFromImplementation = payment.searchPaidOrderDetailByCompanyCode(paymentPersistencePort,
				paymentCustomParams, genericSearchPaymentDto);
		return responseFromImplementation;
	}

	@Override
	public ResponseModel approvalPaymentByParamsAndCompanyCode(PaymentCustomParams paymentCustomParams,
			AuthorizationParmasDto authorizationParmasDto, String method) {
		LOG.info(
				String.format("%s INIT approvalPaymentByParamsAndCompanyCode()", paymentCustomParams.getIdOperation()));
		PaymentInterface payment = paymentFactory.getImplementationByCode(method, paymentCustomParams);
		if (payment == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ",
					paymentCustomParams.getIdOperation()));
			throw new GlobalError();
		}
		ResponseModel responseFromImplementation = payment.approvalPaymentByParamsAndCompanyCode(paymentPersistencePort,
				paymentCustomParams, authorizationParmasDto);
		return responseFromImplementation;
	}

}