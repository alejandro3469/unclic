package mx.com.endtoend.domain.payments.business.paymentProcesses;

import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.ValidationError;
import mx.com.endtoend.infrastructure.services.bds.BDSServicePort;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;
import mx.com.endtoend.smart.bussiness.model.payments.CreditPaymentDto;
import mx.com.endtoend.smart.bussiness.model.payments.PaymentDto;
import mx.com.smartbussiness.generic.utils.DecimalPrecisionUtils;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Clase encargada de realizar las operaciones matemáticas para la generación de
 * cobros con crédito
 * 
 * @author ddcasas
 */

public class CreditProcess {

	private static final String VALID_STATUS_SALE = "TERMINADO";

	/**
	 * Método que realiza la suma de los montos registrados como crédito para el
	 * ingreso al saldo por pagar
	 * 
	 * @param paymentDto
	 * @param pendingPayment
	 * @return
	 */
	public PaymentDto processPaymentCredit(PaymentDto paymentDto, BigDecimal pendingPayment,
			BDSServicePort bdsServicePort) {
		// Validar que el pago pendiente no sea nulo
		if (pendingPayment == null) {
			throw new IllegalArgumentException("PENDING PAYMENT CANNOT BE NULL");
		}
		
		// Validar que el pago pendiente sea positivo o cero
		if (pendingPayment.compareTo(BigDecimal.ZERO) < 0) {
			throw new IllegalArgumentException("PENDING PAYMENT CANNOT BE NEGATIVE");
		}
		
		BigDecimal totalCredit = BigDecimal.ZERO;
		int lineNumber = 1;
		for (CreditPaymentDto creditPayment : paymentDto.getCreditPaymentList()) {
			
			ResponseModel responseStatus = bdsServicePort.validStatusByTransactionId(creditPayment.getReferenceId());
			String status = (String) responseStatus.getData();
			if (!status.equalsIgnoreCase(VALID_STATUS_SALE))
				throw new ValidationError("INAVLID STATUS: " + status);
			
			creditPayment.setLine(lineNumber);
			
			// Validar que los montos no sean nulos
			if (creditPayment.getExchangeRate() == null) {
				throw new IllegalArgumentException("EXCHANGE RATE CANNOT BE NULL");
			}
			if (creditPayment.getAmountApplied() == null) {
				throw new IllegalArgumentException("AMOUNT APPLIED CANNOT BE NULL");
			}
			
			// Validar que los montos sean positivos
			if (creditPayment.getExchangeRate().compareTo(BigDecimal.ZERO) <= 0) {
				throw new IllegalArgumentException("EXCHANGE RATE MUST BE GREATER THAN ZERO");
			}
			if (creditPayment.getAmountApplied().compareTo(BigDecimal.ZERO) <= 0) {
				throw new IllegalArgumentException("AMOUNT APPLIED MUST BE GREATER THAN ZERO");
			}
			
			BigDecimal exchangeRate = creditPayment.getExchangeRate();
			BigDecimal amountApplied = creditPayment.getAmountApplied();
			BigDecimal creditAmount = exchangeRate.multiply(amountApplied);
			totalCredit = totalCredit.add(creditAmount);
			lineNumber++;
		}
		
		pendingPayment = pendingPayment.subtract(totalCredit);
		pendingPayment = DecimalPrecisionUtils.roundToTwoDecimals(pendingPayment);
		paymentDto.setPendingPayment(pendingPayment);
		return paymentDto;
	}
}
