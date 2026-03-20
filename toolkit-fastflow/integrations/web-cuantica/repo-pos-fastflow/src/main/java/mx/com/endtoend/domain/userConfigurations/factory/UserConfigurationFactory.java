package mx.com.endtoend.domain.userConfigurations.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.com.endtoend.domain.commons.constants.GenericIdentifyMethods;

public class UserConfigurationFactory {

	private final static Logger LOG = LoggerFactory.getLogger(UserConfigurationFactory.class);

	public UserConfigurationInterface getImplementation(String method) {

		GenericIdentifyMethods value = GenericIdentifyMethods.valueOf(method);

		switch (value) {
		case USR_CONF_ONE:
			return new UserConfigurationMethodOne();

		default:
			LOG.error("ERROR GETTING METHOD TYPE FOR EMPLOYEE CONFIGURATION MODULE");
			return null;
		}
	}
}