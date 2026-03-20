package mx.com.endtoend.domain.reports.cash.business;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.com.endtoend.domain.commons.constants.GenericIdentifyMethods;
import mx.com.endtoend.infrastructure.services.email.EmailServicePort;

public class ReportCashFactory {
	
	private final Logger LOG = LoggerFactory.getLogger(ReportCashFactory.class);

	public ReportCashInterface getImplementationByCode(String method, EmailServicePort emailServicePort) {

		try {
			LOG.info(String.format("INIT getImplementationByCode() "));
			LOG.info(String.format("PARAMS: [method: %s ]", method));

			GenericIdentifyMethods value = GenericIdentifyMethods.valueOf(method);

			switch (value) {
			case REPORT_CASH_ONE:
				LOG.info("RETURN ReportCashMethodOne()");
				return new ReportCashMethodOne(emailServicePort);

			default:
				return null;
			}

		} catch (IllegalArgumentException | NullPointerException ex) {
			LOG.error("ERROR GETTING METHOD TYPE FOR CASH REPORT MODULE");
			return null;
		}
	}

}
