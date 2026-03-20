package mx.com.endtoend.domain.closings.business;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.com.endtoend.domain.accountingRecord.ports.AccountingRecordPersistencePort;
import mx.com.endtoend.domain.cash.emailReport.ports.EmailCashConfigurationPersistencePort;
import mx.com.endtoend.domain.commons.constants.GenericIdentifyMethods;
import mx.com.endtoend.domain.openings.ports.OpeningOperationPersistencePort;

public class ClosingOperationFactory {

	private final Logger LOG = LoggerFactory.getLogger(ClosingOperationFactory.class);

	public ClosingOperationInterface getImplementationByCode(
			AccountingRecordPersistencePort accountingRecordPersistencePort,
			OpeningOperationPersistencePort openingOperationPersistencePort,
			EmailCashConfigurationPersistencePort emailCashConfigurationPersistencePort, String code) {
		try {
			LOG.info(String.format("INIT getImplementationByCode() "));
			LOG.info(String.format(
					"PARAMS: [accountingRecordPersistencePort, openingOperationPersistencePort, code: %s ]", code));
			GenericIdentifyMethods value = GenericIdentifyMethods.valueOf(code);
			switch (value) {
			case CLOSING_OPERATION_ONE:
				LOG.info("RETURN ClosingOperationMethodOne()");
				return new ClosingOperationMethodOne(accountingRecordPersistencePort, openingOperationPersistencePort,
						emailCashConfigurationPersistencePort);

			default:
				return null;
			}
		} catch (IllegalArgumentException | NullPointerException ex) {
			LOG.error("ERROR GETTING METHOD TYPE FOR CLOSING OPERATION MODULE");
			return null;
		}
	}
}