package mx.com.endtoend.domain.payments.business.paymentProcesses;

import mx.com.endtoend.smart.bussiness.model.payments.PaymentDto;
import mx.com.endtoend.smart.bussiness.model.payments.TransferPaymentDto;
import mx.com.smartbussiness.generic.utils.DecimalPrecisionUtils;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Clase encargada de realizar las operaciones matemáticas para la gestión de
 * cobros en el sistema con transferencias
 * 
 * @author ddcasas
 */
public class TransferProcess {
	
	/**
	 * Método que realiza la suma de los montos registrados como transferencias
	 * empleados para ingreso al saldo por pagar
	 */
	public PaymentDto processPaymentTransfer(PaymentDto paymentDto, BigDecimal pendingPayment) {
		// Validar que el pago pendiente no sea nulo
		if (pendingPayment == null) {
			throw new IllegalArgumentException("PENDING PAYMENT CANNOT BE NULL");
		}
		
		// Validar que el pago pendiente sea positivo o cero
		if (pendingPayment.compareTo(BigDecimal.ZERO) < 0) {
			throw new IllegalArgumentException("PENDING PAYMENT CANNOT BE NEGATIVE");
		}
		
		BigDecimal totalTransfer = BigDecimal.ZERO;
		int lineNumber = 1;
		for (TransferPaymentDto transferPaymentDto : paymentDto.getTransferPaymentList()) {
			transferPaymentDto.setLine(lineNumber);
			
			// Validar que los montos no sean nulos
			if (transferPaymentDto.getExchangeRate() == null) {
				throw new IllegalArgumentException("EXCHANGE RATE CANNOT BE NULL");
			}
			if (transferPaymentDto.getAmountApplied() == null) {
				throw new IllegalArgumentException("AMOUNT APPLIED CANNOT BE NULL");
			}
			
			// Validar que los montos sean positivos
			if (transferPaymentDto.getExchangeRate().compareTo(BigDecimal.ZERO) <= 0) {
				throw new IllegalArgumentException("EXCHANGE RATE MUST BE GREATER THAN ZERO");
			}
			if (transferPaymentDto.getAmountApplied().compareTo(BigDecimal.ZERO) <= 0) {
				throw new IllegalArgumentException("AMOUNT APPLIED MUST BE GREATER THAN ZERO");
			}
			
			BigDecimal exchangeRate = transferPaymentDto.getExchangeRate();
			BigDecimal amountApplied = transferPaymentDto.getAmountApplied();
			BigDecimal transferAmount = exchangeRate.multiply(amountApplied);
			totalTransfer = totalTransfer.add(transferAmount);
			lineNumber++;
		}
		
		pendingPayment = pendingPayment.subtract(totalTransfer);
		pendingPayment = DecimalPrecisionUtils.roundToTwoDecimals(pendingPayment);
		paymentDto.setPendingPayment(pendingPayment);
		return paymentDto;
	}

}
