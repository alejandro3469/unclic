package mx.com.endtoend.domain.paymentsCredit.business;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.com.endtoend.domain.commons.constants.GenericIdentifyMethods;
import mx.com.endtoend.domain.paymentsCredit.dto.CreditPaymentInterfaceService;

public class PaymentCreditFactory {

	private final Logger LOG = LoggerFactory.getLogger(PaymentCreditFactory.class);

	public PaymentCreditInterface getImplementationByMethodCode(
			CreditPaymentInterfaceService creditPaymentInterfaceService, String method) {
		try {
			LOG.info(String.format("INIT getImplementationByMethodCode() "));
			LOG.info(String.format("PARAMS: [ methodCode: %s ]", method));
			GenericIdentifyMethods value = GenericIdentifyMethods.valueOf(method);
			switch (value) {
			case PAYMENT_CREDIT_ONE:
				LOG.info("RETURN PaymentCreditMethodOne()");
				return new PaymentCreditMethodOne(creditPaymentInterfaceService);
			default:
				return null;
			}
		} catch (IllegalArgumentException | NullPointerException ex) {
			LOG.error("ERROR GETTING METHOD TYPE FOR PAYMENT MODULE");
			return null;
		}
	}

}
