package mx.com.endtoend.domain.advertising.business;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.com.endtoend.domain.advertising.dto.SaleAdvertisingInterfaceService;
import mx.com.endtoend.domain.commons.constants.GenericIdentifyMethods;

public class SaleAdvertisingFactory {

	private final Logger LOG = LoggerFactory.getLogger(SaleAdvertisingFactory.class);

	public SaleAdvertisingInterface getImplementationByCode(SaleAdvertisingInterfaceService advertisingInterfaceService,
			String method) {
		try {
			LOG.info(String.format("INIT getImplementationByCode() "));

			GenericIdentifyMethods value = GenericIdentifyMethods.valueOf(method);
			switch (value) {
			case ADVERTISING_ONE:
				LOG.info("SaleAdvertisingMethodOne()");
				return new SaleAdvertisingMethodOne(advertisingInterfaceService);
			default:
				return null;
			}
		} catch (IllegalArgumentException | NullPointerException ex) {
			LOG.error("ERROR GETTING METHOD TYPE FOR ADVERTISING MODULE");
			return null;
		}

	}

}
