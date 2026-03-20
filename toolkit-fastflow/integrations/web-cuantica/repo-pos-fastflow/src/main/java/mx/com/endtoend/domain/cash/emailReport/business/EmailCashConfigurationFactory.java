package mx.com.endtoend.domain.cash.emailReport.business;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.com.endtoend.domain.commons.constants.GenericIdentifyMethods;

public class EmailCashConfigurationFactory {

	private final Logger LOG = LoggerFactory.getLogger(EmailCashConfigurationFactory.class);
	
	public EmailCashConfigurationInterface getImplementationByCode(String method) {
		try {

			LOG.info(String.format("INIT getImplementationByCode() "));
			LOG.info(String.format("PARAMS: [ method: %s ]", method));

			GenericIdentifyMethods value = GenericIdentifyMethods.valueOf(method);

			switch (value) {
			case EMAIL_CASH_CONFIGURATION_ONE:
				LOG.info("RETURN EmailCashConfiguratioMethodOne()");
				return new EmailCashConfiguratioMethodOne();

			default:
				return null;
			}

		} catch (IllegalArgumentException | NullPointerException ex) {
			LOG.error("ERROR GETTING METHOD TYPE FOR EMAIL CONFIGURATION MODULE");
			return null;
		}
	}
	

}
