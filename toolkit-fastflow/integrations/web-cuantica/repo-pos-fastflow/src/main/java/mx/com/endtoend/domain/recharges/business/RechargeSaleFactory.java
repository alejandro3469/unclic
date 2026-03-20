package mx.com.endtoend.domain.recharges.business;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.com.endtoend.domain.commons.constants.GenericIdentifyMethods;
import mx.com.endtoend.domain.recharges.dto.CustomRechargeSaleParams;

public class RechargeSaleFactory {

	private final Logger LOG = LoggerFactory.getLogger(RechargeSaleFactory.class);

	public RechargeSaleInterface getImplementationByMethod(CustomRechargeSaleParams customRechargeSaleParams) {
		try {
			LOG.info(String.format("INIT getImplementationByMethod() "));
			LOG.info(String.format("PARAMS: [ methodCode: %s ]", customRechargeSaleParams.getMethod()));
			GenericIdentifyMethods value = GenericIdentifyMethods.valueOf(customRechargeSaleParams.getMethod());
			switch (value) {
			case RECHARGE_ONE:
				LOG.info("RETURN RechargeSaleMethodOne()");
				return new RechargeSaleMethodOne(customRechargeSaleParams);
			default:
				return null;
			}
		} catch (IllegalArgumentException | NullPointerException ex) {
			LOG.error("ERROR GETTING METHOD TYPE FOR PAYMENT MODULE");
			return null;
		}
	}
}