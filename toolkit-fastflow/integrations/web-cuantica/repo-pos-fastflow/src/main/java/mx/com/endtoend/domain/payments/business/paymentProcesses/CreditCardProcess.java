package mx.com.endtoend.domain.payments.business.paymentProcesses;

import mx.com.endtoend.domain.cash.creditCardReference.dto.CreditCardDto;
import mx.com.endtoend.domain.cash.creditCardReference.ports.CreditCardConfigurationPersistencePort;
import mx.com.endtoend.domain.payments.business.validations.CreditCardValidation;
import mx.com.endtoend.domain.payments.dto.PaymentCustomParams;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;
import mx.com.endtoend.smart.bussiness.model.payments.CreditCardPaymentDto;
import mx.com.endtoend.smart.bussiness.model.payments.PaymentDto;
import mx.com.smartbussiness.generic.utils.DecimalPrecisionUtils;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Clase encargada de realizar las operaciones matemáticas para la gestión de
 * cobros en el sistema con tarjetas de crédito
 * 
 * @author ddcasas
 */
public class CreditCardProcess {

	private CreditCardConfigurationPersistencePort creditCardConfigurationPersistencePort;
	
	private CreditCardValidation creditCardValidation = new CreditCardValidation();

	/**
	 * Método que realiza la suma de los montos registrados como pago con tarjeta
	 * empleados para ingreso al saldo por pagar
	 * 
	 */
	public PaymentDto processPaymentCreditCard(PaymentCustomParams paymentCustomParams, PaymentDto paymentDto,
			BigDecimal pendingPayment) {

		// Validar que el pago pendiente no sea nulo
		if (pendingPayment == null) {
			throw new IllegalArgumentException("PENDING PAYMENT CANNOT BE NULL");
		}
		
		// Validar que el pago pendiente sea positivo o cero
		if (pendingPayment.compareTo(BigDecimal.ZERO) < 0) {
			throw new IllegalArgumentException("PENDING PAYMENT CANNOT BE NEGATIVE");
		}

		creditCardConfigurationPersistencePort = paymentCustomParams.getCreditCardConfigurationPersistencePort();

		BigDecimal totalCreditCard = BigDecimal.ZERO;
		int lineNumber = 1;
		for (CreditCardPaymentDto creditCardPaymentDto : paymentDto.getCreditCardPaymentList()) {
			creditCardPaymentDto.setLine(lineNumber);
			
			// Validar que los montos no sean nulos
			if (creditCardPaymentDto.getExchangeRate() == null) {
				throw new IllegalArgumentException("EXCHANGE RATE CANNOT BE NULL");
			}
			if (creditCardPaymentDto.getAmountApplied() == null) {
				throw new IllegalArgumentException("AMOUNT APPLIED CANNOT BE NULL");
			}
			
			// Validar que los montos sean positivos
			if (creditCardPaymentDto.getExchangeRate().compareTo(BigDecimal.ZERO) <= 0) {
				throw new IllegalArgumentException("EXCHANGE RATE MUST BE GREATER THAN ZERO");
			}
			if (creditCardPaymentDto.getAmountApplied().compareTo(BigDecimal.ZERO) <= 0) {
				throw new IllegalArgumentException("AMOUNT APPLIED MUST BE GREATER THAN ZERO");
			}
			
			BigDecimal exchangeRate = creditCardPaymentDto.getExchangeRate();
			BigDecimal amountApplied = creditCardPaymentDto.getAmountApplied();
			BigDecimal paymentAmount = exchangeRate.multiply(amountApplied);
			totalCreditCard = totalCreditCard.add(paymentAmount);

			ResponseModel responseCreditCard = creditCardConfigurationPersistencePort
					.getCreditCardReferenceByIdAndCompanyCode(creditCardPaymentDto.getCreditCardId(),
							paymentCustomParams.getCompanyCode(), paymentCustomParams.getIdOperation());
			CreditCardDto creditCard = (CreditCardDto) responseCreditCard.getData();
			BigDecimal commission = BigDecimal.ZERO;
			creditCardPaymentDto.setApplyComission(creditCard.getIsCommissionApply());
			if (creditCard.getIsCommissionApply()) {
				creditCardValidation.validAmountMinumunByPeriod(creditCardPaymentDto, creditCard);
				
				// Validar que la comisión aplicada no sea nula
				if (creditCardPaymentDto.getCommissionApplied() == null) {
					throw new IllegalArgumentException("COMMISSION APPLIED CANNOT BE NULL");
				}
				
				// Validar que la comisión aplicada sea positiva
				if (creditCardPaymentDto.getCommissionApplied().compareTo(BigDecimal.ZERO) <= 0) {
					throw new IllegalArgumentException("COMMISSION APPLIED MUST BE GREATER THAN ZERO");
				}
				
				BigDecimal commissionRate = creditCardPaymentDto.getCommissionApplied().divide(new BigDecimal("100"));
				BigDecimal amountAppliedBD = creditCardPaymentDto.getAmountApplied();
				BigDecimal exchangeRateBD = creditCardPaymentDto.getExchangeRate();
				commission = commissionRate.multiply(amountAppliedBD).multiply(exchangeRateBD);
				commission = DecimalPrecisionUtils.roundToTwoDecimals(commission);
			}
			creditCardPaymentDto.setCommission(commission);

			lineNumber++;
		}
		
		pendingPayment = pendingPayment.subtract(totalCreditCard);
		pendingPayment = DecimalPrecisionUtils.roundToTwoDecimals(pendingPayment);
		paymentDto.setPendingPayment(pendingPayment);
		return paymentDto;
	}
}
