package mx.com.endtoend.domain.payments.business.paymentProcesses;

import mx.com.endtoend.smart.bussiness.model.payments.CreditNotePaymentDto;
import mx.com.endtoend.smart.bussiness.model.payments.PaymentDto;
import mx.com.smartbussiness.generic.utils.DecimalPrecisionUtils;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Clase encargada de realizar las operaciones matemáticas para la gestión de
 * cobros en el sistema con notas de crédito
 * 
 * @author ddcasas
 */
public class CeditNoteProcess {

	/**
	 * Método que realiza la suma de los montos registrados como notas de crédito
	 * empleadas para ingreso al saldo por pagar
	 * 
	 **/
	public PaymentDto processPaymentCreditNote(PaymentDto paymentDto, BigDecimal pendingPayment) {
		// Validar que el pago pendiente no sea nulo
		if (pendingPayment == null) {
			throw new IllegalArgumentException("PENDING PAYMENT CANNOT BE NULL");
		}
		
		// Validar que el pago pendiente sea positivo o cero
		if (pendingPayment.compareTo(BigDecimal.ZERO) < 0) {
			throw new IllegalArgumentException("PENDING PAYMENT CANNOT BE NEGATIVE");
		}
		
		BigDecimal totalCreditNote = BigDecimal.ZERO;
		int lineNumber = 1;
		for (CreditNotePaymentDto creditNotePaymentDto : paymentDto.getCreditNotePaymentList()) {
			creditNotePaymentDto.setLine(lineNumber);
			
			// Validar que los montos no sean nulos
			if (creditNotePaymentDto.getExchangeRate() == null) {
				throw new IllegalArgumentException("EXCHANGE RATE CANNOT BE NULL");
			}
			if (creditNotePaymentDto.getAmountApplied() == null) {
				throw new IllegalArgumentException("AMOUNT APPLIED CANNOT BE NULL");
			}
			
			// Validar que los montos sean positivos
			if (creditNotePaymentDto.getExchangeRate().compareTo(BigDecimal.ZERO) <= 0) {
				throw new IllegalArgumentException("EXCHANGE RATE MUST BE GREATER THAN ZERO");
			}
			if (creditNotePaymentDto.getAmountApplied().compareTo(BigDecimal.ZERO) <= 0) {
				throw new IllegalArgumentException("AMOUNT APPLIED MUST BE GREATER THAN ZERO");
			}
			
			BigDecimal exchangeRate = creditNotePaymentDto.getExchangeRate();
			BigDecimal amountApplied = creditNotePaymentDto.getAmountApplied();
			BigDecimal creditNoteAmount = exchangeRate.multiply(amountApplied);
			totalCreditNote = totalCreditNote.add(creditNoteAmount);
			lineNumber++;
		}
		
		pendingPayment = pendingPayment.subtract(totalCreditNote);
		pendingPayment = DecimalPrecisionUtils.roundToTwoDecimals(pendingPayment);
		paymentDto.setPendingPayment(pendingPayment);
		return paymentDto;
	}

}
