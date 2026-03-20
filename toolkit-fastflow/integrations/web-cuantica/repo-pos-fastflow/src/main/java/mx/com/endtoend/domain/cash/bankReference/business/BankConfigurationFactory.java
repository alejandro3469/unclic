package mx.com.endtoend.domain.cash.bankReference.business;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.com.endtoend.domain.commons.constants.GenericIdentifyMethods;

public class BankConfigurationFactory {

	private final Logger LOG = LoggerFactory.getLogger(BankConfigurationFactory.class);

	public BankConfigurationInterface getImplementationByCode(String code) {

		try {

			LOG.info(String.format("INIT getImplementationByCode() "));
			LOG.info(String.format("PARAMS: [ factoryName: %s ]", code));

			GenericIdentifyMethods value = GenericIdentifyMethods.valueOf(code);

			switch (value) {
			case BANK_REFERENCE_ONE:
				LOG.info("RETURN BankConfigurationOne()");
				return new BankConfigurationOne();

			default:
				return null;
			}

		} catch (IllegalArgumentException | NullPointerException ex) {
			LOG.error("ERROR GETTING METHOD TYPE FOR BANK REFERENCE MODULE");
			return null;
		}
	}

}
