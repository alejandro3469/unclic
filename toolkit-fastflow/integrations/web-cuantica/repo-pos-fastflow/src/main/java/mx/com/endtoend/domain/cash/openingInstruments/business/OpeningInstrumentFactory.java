package mx.com.endtoend.domain.cash.openingInstruments.business;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.com.endtoend.domain.commons.constants.GenericIdentifyMethods;

public class OpeningInstrumentFactory {

	private final Logger LOG = LoggerFactory.getLogger(OpeningInstrumentFactory.class);

	public OpeningInstrumentInterface getImplementationByCode(String methodCode) {

		try {

			LOG.info(String.format("INIT getImplementationByCode() "));
			LOG.info(String.format("PARAMS: [ methodCode: %s ]", methodCode));

			GenericIdentifyMethods value = GenericIdentifyMethods.valueOf(methodCode);

			switch (value) {
			case OPENING_INSTRUMENT_ONE:
				LOG.info("RETURN OpeningInstrumentMethodOne()");
				return new OpeningInstrumentMethodOne();
				
			default:
				return null;
			}

		} catch (IllegalArgumentException | NullPointerException ex) {
			LOG.error("ERROR GETTING METHOD TYPE FOR OPENING INSTRUMENT MODULE");
			return null;
		}

	}

}
