package mx.com.endtoend.domain.payments.business;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.com.endtoend.domain.commons.constants.GenericIdentifyMethods;
import mx.com.endtoend.domain.payments.dto.PaymentCustomParams;

public class PaymentFactory {

	private final Logger LOG = LoggerFactory.getLogger(PaymentFactory.class);

	public PaymentInterface getImplementationByCode(String methodCode, PaymentCustomParams paymentCustomParams) {
		try {
			LOG.info(String.format("INIT getImplementationByCode() "));
			LOG.info(String.format("PARAMS: [ methodCode: %s ]", methodCode));
			GenericIdentifyMethods value = GenericIdentifyMethods.valueOf(methodCode);
			switch (value) {
			case PAYMENT_ONE:
				LOG.info("RETURN PaymentMethodOne()");
				return new PaymentMethodOne(paymentCustomParams);
			default:
				return null;
			}
		} catch (IllegalArgumentException | NullPointerException ex) {
			LOG.error("ERROR GETTING METHOD TYPE FOR PAYMENT MODULE");
			return null;
		}
	}
}