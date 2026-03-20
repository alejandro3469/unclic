package mx.com.endtoend.domain.cash.closingInstruments.business;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.com.endtoend.domain.commons.constants.GenericIdentifyMethods;

public class ClosingInstrumentFactory{

	private final Logger LOG = LoggerFactory.getLogger(ClosingInstrumentFactory.class);
	
	public ClosingInstrumentInterface getImplementationByCode(String factoryName) {
		try {
			
			LOG.info(String.format("INIT getImplementationByCode() "));
			LOG.info(String.format("PARAMS: [ factoryName: %s ]", factoryName));

			GenericIdentifyMethods value = GenericIdentifyMethods.valueOf(factoryName);

			switch (value) {
			case CLOSING_INSTRUMENT_ONE:
				LOG.info("RETURN ClosingInstrumentMethodOne()");
				return new ClosingInstrumentMethodOne();
				
			default:
				return null;
			}
			
		} catch (IllegalArgumentException | NullPointerException ex) {
			LOG.error("ERROR GETTING METHOD TYPE FOR CLOSING INSTRUMENT MODULE");
			return null;
		}
		
	}

}
