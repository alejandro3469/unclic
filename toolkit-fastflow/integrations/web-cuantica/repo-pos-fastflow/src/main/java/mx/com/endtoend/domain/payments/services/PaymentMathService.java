package mx.com.endtoend.domain.payments.services;

import mx.com.endtoend.domain.payments.business.paymentProcesses.CashProcess;
import mx.com.endtoend.domain.payments.business.paymentProcesses.CeditNoteProcess;
import mx.com.endtoend.domain.payments.business.paymentProcesses.CheckProcess;
import mx.com.endtoend.domain.payments.business.paymentProcesses.CreditCardProcess;
import mx.com.endtoend.domain.payments.business.paymentProcesses.CreditProcess;
import mx.com.endtoend.domain.payments.business.paymentProcesses.TransferProcess;
import mx.com.endtoend.domain.payments.dto.PaymentCustomParams;
import mx.com.endtoend.smart.bussiness.model.payments.PaymentDto;

// Imports para precisión decimal SAT
import mx.com.smartbussiness.generic.utils.DecimalPrecisionUtils;
import mx.com.smartbussiness.generic.constants.TaxConstants;
import mx.com.smartbussiness.generic.validators.PrecisionValidator;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Clase encargada de coordinar los proceso para generar el resumen operativo de
 * los distintos cobros configurados en el sistema
 * 
 * @author ddcasas
 *
 */
public class PaymentMathService {

	private CheckProcess checkProcess = new CheckProcess();
	private CeditNoteProcess creCeditNoteProcess = new CeditNoteProcess();
	private TransferProcess transferProcess = new TransferProcess();
	private CreditCardProcess creditCardProcess = new CreditCardProcess();
	private CashProcess cashProcess = new CashProcess();
	private CreditProcess creditProcess = new CreditProcess();

	/**
	 * Valida que los pagos cumplan con las reglas de precisión SAT
	 * @param paymentDto Pago a validar
	 * @throws IllegalArgumentException Si los pagos no son válidos
	 */
	private void validatePaymentPrecision(PaymentDto paymentDto) {
		// Validar que el pago pendiente no sea nulo
		if (paymentDto.getPendingPayment() == null) {
			throw new IllegalArgumentException("PENDING PAYMENT CANNOT BE NULL");
		}
		
		// Validar que el pago pendiente sea positivo o cero
		if (paymentDto.getPendingPayment().compareTo(BigDecimal.ZERO) < 0) {
			throw new IllegalArgumentException("PENDING PAYMENT CANNOT BE NEGATIVE");
		}
		
		// Validar precisión decimal (máximo 2 decimales según SAT)
		if (paymentDto.getPendingPayment().scale() > 2) {
			throw new IllegalArgumentException("PENDING PAYMENT CANNOT HAVE MORE THAN 2 DECIMAL PLACES");
		}
		
		// Validar que el total de la orden no sea nulo
		if (paymentDto.getOrderTotal() == null) {
			throw new IllegalArgumentException("ORDER TOTAL CANNOT BE NULL");
		}
		
		// Validar que el total de la orden sea positivo
		if (paymentDto.getOrderTotal().compareTo(BigDecimal.ZERO) <= 0) {
			throw new IllegalArgumentException("ORDER TOTAL MUST BE GREATER THAN ZERO");
		}
		
		// Validar precisión decimal del total de la orden
		if (paymentDto.getOrderTotal().scale() > 2) {
			throw new IllegalArgumentException("ORDER TOTAL CANNOT HAVE MORE THAN 2 DECIMAL PLACES");
		}
		
		// Validar consistencia: pendingPayment <= orderTotal
		if (paymentDto.getPendingPayment().compareTo(paymentDto.getOrderTotal()) > 0) {
			throw new IllegalArgumentException("PENDING PAYMENT CANNOT BE GREATER THAN ORDER TOTAL");
		}
	}

	/**
	 * Aplica redondeo SAT a los montos del pago
	 * @param paymentDto Pago a redondear
	 * @return Pago con montos redondeados según reglas SAT
	 */
	private PaymentDto applySATRoundingToPayment(PaymentDto paymentDto) {
		// Redondear pago pendiente a 2 decimales con HALF_UP
		if (paymentDto.getPendingPayment() != null) {
			paymentDto.setPendingPayment(
				DecimalPrecisionUtils.roundToTwoDecimals(paymentDto.getPendingPayment())
			);
		}
		
		// Redondear total de la orden a 2 decimales con HALF_UP
		if (paymentDto.getOrderTotal() != null) {
			paymentDto.setOrderTotal(
				DecimalPrecisionUtils.roundToTwoDecimals(paymentDto.getOrderTotal())
			);
		}
		
		return paymentDto;
	}

	/**
	 * Método que toma los registros de los pagos ingresados para liquidar el monto
	 * total de la orden
	 * 
	 * @param paymentDto          objeto con los datos operativos del pago
	 * @param paymentCustomParams objeto con las interfaces de comunicación hacia
	 *                            capa de persistencia
	 * 
	 * @return objeto con el valor del saldo pendiente por pagar
	 */
	public PaymentDto processPayment(PaymentCustomParams paymentCustomParams, PaymentDto paymentDto) {

		// Validar precisión de pagos con BigDecimal según reglas SAT
		validatePaymentPrecision(paymentDto);

		if (paymentDto.getPaymentCashList() != null)
			if (!paymentDto.getPaymentCashList().isEmpty())
				paymentDto = cashProcess.processPaymentCash(paymentDto, paymentDto.getPendingPayment());

		if (paymentDto.getCreditCardPaymentList() != null)
			if (!paymentDto.getCreditCardPaymentList().isEmpty())
				paymentDto = creditCardProcess.processPaymentCreditCard(paymentCustomParams, paymentDto,
						paymentDto.getPendingPayment());

		if (paymentDto.getTransferPaymentList() != null) {
			if (!paymentDto.getTransferPaymentList().isEmpty())
				paymentDto = transferProcess.processPaymentTransfer(paymentDto, paymentDto.getPendingPayment());
		}

		if (paymentDto.getCreditNotePaymentList() != null)
			if (!paymentDto.getCreditNotePaymentList().isEmpty())
				paymentDto = creCeditNoteProcess.processPaymentCreditNote(paymentDto, paymentDto.getPendingPayment());

		if (paymentDto.getCheckPaymentList() != null)
			if (!paymentDto.getCheckPaymentList().isEmpty())
				paymentDto = checkProcess.processPaymentCheck(paymentDto, paymentDto.getPendingPayment());

		if (paymentDto.getCreditPaymentList() != null)
			if (!paymentDto.getCreditPaymentList().isEmpty())
				paymentDto = creditProcess.processPaymentCredit(paymentDto, paymentDto.getPendingPayment(),paymentCustomParams.getBdsServicePort());

		// Aplicar redondeo SAT al resultado final
		paymentDto = applySATRoundingToPayment(paymentDto);

		return paymentDto;
	}
}