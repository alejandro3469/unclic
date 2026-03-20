package mx.com.endtoend.domain.openings.business;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.com.endtoend.domain.accountingRecord.ports.AccountingRecordPersistencePort;
import mx.com.endtoend.domain.commons.constants.GenericIdentifyMethods;

public class OpeningOperationFactory {

	private final Logger LOG = LoggerFactory.getLogger(OpeningOperationFactory.class);

	public OpeningOperationInterface getImplementationByCode(
			AccountingRecordPersistencePort accountingRecordPersistencePort, String code) {

		try {
			LOG.info(String.format("INIT getImplementationByCode() "));
			LOG.info(String.format("PARAMS: [accountingRecordPersistencePort, code: %s ]", code));

			GenericIdentifyMethods value = GenericIdentifyMethods.valueOf(code);

			switch (value) {
			case OPENING_OPERATION_ONE:
				LOG.info("RETURN OpeningOperationMethodOne()");
				return new OpeningOperationMethodOne(accountingRecordPersistencePort);

			default:
				return null;
			}

		} catch (IllegalArgumentException | NullPointerException ex) {
			LOG.error("ERROR GETTING METHOD TYPE FOR OPENING OPERATION MODULE");
			return null;
		}

	}
}
