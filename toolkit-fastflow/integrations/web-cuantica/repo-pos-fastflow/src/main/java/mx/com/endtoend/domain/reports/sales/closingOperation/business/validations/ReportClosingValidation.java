package mx.com.endtoend.domain.reports.sales.closingOperation.business.validations;

import mx.com.endtoend.domain.reports.sales.closingOperation.dto.ClosingOperationReportParamsDto;

public class ReportClosingValidation {

	public String validOperativeDataToGenerateReport(ClosingOperationReportParamsDto closingReportParamsDto) {

		String validations = "";

		validations += closingReportParamsDto.getBranchCode() == null ? " -BRANCH OPTION IS REQUIRED" : "";

		validations += closingReportParamsDto.getFormat() == null ? " -FORMART IS REQUIRED" : "";
		validations += closingReportParamsDto.getStartDate() != null
				? closingReportParamsDto.getFormat().isEmpty() ? " -FORMAT IS REQUIRED" : ""
				: "";

		validations += closingReportParamsDto.getStartDate() == null ? " -START DATE IS REQUIRED" : "";

		validations += closingReportParamsDto.getEndDate() == null ? " -END DATE IS REQUIRED" : "";

		return validations;
	}

}
