package mx.com.endtoend.domain.orderConfigurations.factories;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.com.endtoend.domain.commons.constants.GenericIdentifyMethods;

public class OrderConfigurationFactory {

	private final static Logger LOG = LoggerFactory.getLogger(OrderConfigurationFactory.class);

	
	public OrderConfigurationInterface getImplementation(String factoryName) {

		try {

			GenericIdentifyMethods value = GenericIdentifyMethods.valueOf(factoryName);

			switch (value) {

			case ORDER_CONFIGURATION_ONE:
				LOG.info("RETURN ORDER_CONFIGURATION_ONE");
				return new OrderConfigurationMethodOne();

			default:
				return null;
			}

		} catch (IllegalArgumentException | NullPointerException ex) {

			LOG.error("ERROR GETTING METHOD TYPE FOR ORDER-CONFIGURATION MODULE");
			return null;
		}
	}

}
