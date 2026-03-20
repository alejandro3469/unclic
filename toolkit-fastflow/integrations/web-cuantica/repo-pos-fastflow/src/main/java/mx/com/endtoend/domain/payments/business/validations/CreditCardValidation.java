package mx.com.endtoend.domain.payments.business.validations;

import java.math.BigDecimal;

import mx.com.endtoend.domain.cash.creditCardReference.dto.CreditCardDto;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.ValidationError;
import mx.com.endtoend.smart.bussiness.constants.PaymentPeriodEnum;
import mx.com.endtoend.smart.bussiness.model.payments.CreditCardPaymentDto;

/**
 * Clase de control para las validaciones generales en cobros con tarjetas de
 * crédito
 */
public class CreditCardValidation {

	public void validAmountMinumunByPeriod(CreditCardPaymentDto creditCardPaymentDto, CreditCardDto creditCard) {
		String validations = "";
		if (!creditCardPaymentDto.getPeriod().equals(PaymentPeriodEnum.DIRECT.toString())) {
		validations += creditCardPaymentDto.getAmountApplied().compareTo(creditCard.getMinimumAmount()) < 0
				? " -THE MINIMUM TRANSACTION AMOUNT IS " + creditCard.getMinimumAmount()
				: "";
		}

		if (!validations.isEmpty())
			throw new ValidationError(validations);

	}

}
