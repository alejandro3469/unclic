package mx.com.endtoend.domain.payments.business.paymentProcesses;

import mx.com.endtoend.smart.bussiness.model.payments.PaymentCashDto;
import mx.com.endtoend.smart.bussiness.model.payments.PaymentDto;
import mx.com.smartbussiness.generic.utils.DecimalPrecisionUtils;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Clase encargada de realizar las operaciones matemáticas para la gestión de
 * cobros en el sistema con efectivi
 * 
 * @author ddcasas
 */
public class CashProcess {

	/**
	 * Método que realiza la suma de los montos en efectivo a ocupar para ingreso al
	 * saldo por pagar
	 * 
	 */
	public PaymentDto processPaymentCash(PaymentDto paymentDto, BigDecimal pendingPayment) {
		// Validar que el pago pendiente no sea nulo
		if (pendingPayment == null) {
			throw new IllegalArgumentException("PENDING PAYMENT CANNOT BE NULL");
		}
		
		// Validar que el pago pendiente sea positivo o cero
		if (pendingPayment.compareTo(BigDecimal.ZERO) < 0) {
			throw new IllegalArgumentException("PENDING PAYMENT CANNOT BE NEGATIVE");
		}
		
		BigDecimal totalPaymentCash = BigDecimal.ZERO;
		for (PaymentCashDto paymentCashDto : paymentDto.getPaymentCashList()) {
			// Validar que los montos no sean nulos
			if (paymentCashDto.getExchangeRate() == null) {
				throw new IllegalArgumentException("EXCHANGE RATE CANNOT BE NULL");
			}
			if (paymentCashDto.getAmountApplied() == null) {
				throw new IllegalArgumentException("AMOUNT APPLIED CANNOT BE NULL");
			}
			
			// Validar que los montos sean positivos
			if (paymentCashDto.getExchangeRate().compareTo(BigDecimal.ZERO) <= 0) {
				throw new IllegalArgumentException("EXCHANGE RATE MUST BE GREATER THAN ZERO");
			}
			if (paymentCashDto.getAmountApplied().compareTo(BigDecimal.ZERO) <= 0) {
				throw new IllegalArgumentException("AMOUNT APPLIED MUST BE GREATER THAN ZERO");
			}
			
			BigDecimal exchangeRate = paymentCashDto.getExchangeRate();
			BigDecimal amountApplied = paymentCashDto.getAmountApplied();
			BigDecimal paymentAmount = exchangeRate.multiply(amountApplied);
			totalPaymentCash = totalPaymentCash.add(paymentAmount);
		}
		
		pendingPayment = pendingPayment.subtract(totalPaymentCash);
		pendingPayment = DecimalPrecisionUtils.roundToTwoDecimals(pendingPayment);
		paymentDto.setPendingPayment(pendingPayment);
		return paymentDto;
	}
}
