package mx.com.endtoend.domain.payments.business.paymentProcesses;

import mx.com.endtoend.smart.bussiness.model.payments.CheckPaymentDto;
import mx.com.endtoend.smart.bussiness.model.payments.PaymentDto;
import mx.com.smartbussiness.generic.utils.DecimalPrecisionUtils;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Clase encargada de realizar las operaciones matemáticas para la generación de
 * cobros del sistema con cheques
 * 
 * @author ddcasas
 */

public class CheckProcess {

	/**
	 * Método que realiza la suma de los montos registrados como cheques para el
	 * ingreso al saldo por pagar
	 * 
	 * @param paymentDto
	 * @param pendingPayment
	 * @return
	 */
	public PaymentDto processPaymentCheck(PaymentDto paymentDto, BigDecimal pendingPayment) {
		// Validar que el pago pendiente no sea nulo
		if (pendingPayment == null) {
			throw new IllegalArgumentException("PENDING PAYMENT CANNOT BE NULL");
		}
		
		// Validar que el pago pendiente sea positivo o cero
		if (pendingPayment.compareTo(BigDecimal.ZERO) < 0) {
			throw new IllegalArgumentException("PENDING PAYMENT CANNOT BE NEGATIVE");
		}
		
		BigDecimal totalCheck = BigDecimal.ZERO;
		int lineNumber = 1;
		for (CheckPaymentDto checkPayment : paymentDto.getCheckPaymentList()) {
			checkPayment.setLine(lineNumber);
			
			// Validar que los montos no sean nulos
			if (checkPayment.getExchangeRate() == null) {
				throw new IllegalArgumentException("EXCHANGE RATE CANNOT BE NULL");
			}
			if (checkPayment.getAmountApplied() == null) {
				throw new IllegalArgumentException("AMOUNT APPLIED CANNOT BE NULL");
			}
			
			// Validar que los montos sean positivos
			if (checkPayment.getExchangeRate().compareTo(BigDecimal.ZERO) <= 0) {
				throw new IllegalArgumentException("EXCHANGE RATE MUST BE GREATER THAN ZERO");
			}
			if (checkPayment.getAmountApplied().compareTo(BigDecimal.ZERO) <= 0) {
				throw new IllegalArgumentException("AMOUNT APPLIED MUST BE GREATER THAN ZERO");
			}
			
			BigDecimal exchangeRate = checkPayment.getExchangeRate();
			BigDecimal amountApplied = checkPayment.getAmountApplied();
			BigDecimal checkAmount = exchangeRate.multiply(amountApplied);
			totalCheck = totalCheck.add(checkAmount);
			lineNumber++;
		}
		
		pendingPayment = pendingPayment.subtract(totalCheck);
		pendingPayment = DecimalPrecisionUtils.roundToTwoDecimals(pendingPayment);
		paymentDto.setPendingPayment(pendingPayment);
		return paymentDto;
	}

}
