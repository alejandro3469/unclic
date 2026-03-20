package mx.com.endtoend.domain.cash.creditCardReference.business;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.com.endtoend.domain.commons.constants.GenericIdentifyMethods;

public class CreditCardConfigurationFactory {

	private final Logger LOG = LoggerFactory.getLogger(CreditCardConfigurationFactory.class);

	public CreditCardConfigurationInterface getImplementationByCode(String method) {
		try {

			LOG.info(String.format("INIT getImplementationByCode() "));
			LOG.info(String.format("PARAMS: [ method: %s ]", method));

			GenericIdentifyMethods value = GenericIdentifyMethods.valueOf(method);

			switch (value) {
			case CREDIT_CARD_REFERENCE_ONE:
				LOG.info("RETURN CreditCardConfiguratioMethodOne()");
				return new CreditCardConfiguratioMethodOne();

			default:
				return null;
			}

		} catch (IllegalArgumentException | NullPointerException ex) {
			LOG.error("ERROR GETTING METHOD TYPE FOR CREDIT CARD CONFIGURATION MODULE");
			return null;
		}
	}

}
