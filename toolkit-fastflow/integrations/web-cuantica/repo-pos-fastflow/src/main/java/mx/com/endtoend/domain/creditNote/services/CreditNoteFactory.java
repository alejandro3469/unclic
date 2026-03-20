package mx.com.endtoend.domain.creditNote.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.com.endtoend.domain.commons.constants.GenericIdentifyMethods;
import mx.com.endtoend.domain.creditNote.business.CreditNoteMethodOne;
import mx.com.endtoend.domain.creditNote.dto.CreditNoteCustomParams;

public class CreditNoteFactory {

	private final Logger LOG = LoggerFactory.getLogger(CreditNoteFactory.class);

	public CreditNoteInterface getImplementationByCode(String method, CreditNoteCustomParams creditNoteCustomParams) {
		try {
			LOG.info(String.format("INIT getImplementationByCode() "));
			LOG.info(String.format("PARAMS: [ method: %s ]", method));
			GenericIdentifyMethods value = GenericIdentifyMethods.valueOf(method);
			switch (value) {
			case CREDIT_NOTE_ONE:
				LOG.info("RETURN CreditNoteMethodOne()");
				return new CreditNoteMethodOne(creditNoteCustomParams);
			default:
				return null;
			}
		} catch (IllegalArgumentException | NullPointerException ex) {
			LOG.error("ERROR GETTING METHOD TYPE FOR CREDIT NOTE MODULE");
			return null;
		}
	}
}
