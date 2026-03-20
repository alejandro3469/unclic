package mx.com.endtoend.domain.reports.cash.business.validations;

import mx.com.endtoend.domain.openings.dto.OpeningOperationDto;

public class CashReportValidation {

	public String validExistsOpeningOperation(OpeningOperationDto openingOperation) {

		String validations = "";

		validations = openingOperation != null
				? !openingOperation.getIsActive() ? " -OPENING OPERATION IS NOT ACTIVE- " : validations
				: " -OPENING OPERATION NOT FOUND- ";

		return validations;
	}

	public String validOpeninigOperationStatus(OpeningOperationDto openingOperation) {
		String validations = "";
		validations = openingOperation == null ? " -OPERATION NOT FOUND- " : "";
		validations = openingOperation != null
				? openingOperation.getClosingId() == null ? validations + " -CLOSING OPERATION NOT EXISTS- "
						: validations
				: validations;
		return validations;
	}

}
