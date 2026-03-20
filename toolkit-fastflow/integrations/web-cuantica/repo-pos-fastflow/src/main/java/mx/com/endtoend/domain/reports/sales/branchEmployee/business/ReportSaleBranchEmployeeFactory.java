package mx.com.endtoend.domain.reports.sales.branchEmployee.business;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.com.endtoend.domain.commons.constants.GenericIdentifyMethods;
import mx.com.endtoend.domain.reports.sales.models.SaleReportInterfaceService;

public class ReportSaleBranchEmployeeFactory {

	private final Logger LOG = LoggerFactory.getLogger(ReportSaleBranchEmployeeFactory.class);

	public ReportSaleBranchEmployeeInterface getImplementationByCode(String method,
			SaleReportInterfaceService saleReportInterfaceService) {

		try {
			LOG.info(String.format("INIT getImplementationByCode() "));
			LOG.info(String.format("PARAMS: [method: %s ]", method));

			GenericIdentifyMethods value = GenericIdentifyMethods.valueOf(method);

			switch (value) {
			case REPORT_SALE_BRANCH_EMPLOYEE_ONE:
				LOG.info("RETURN ReportSaleBranchEmployeeMethodOne()");
				return new ReportSaleBranchEmployeeMethodOne(saleReportInterfaceService);

			default:
				return null;
			}

		} catch (IllegalArgumentException | NullPointerException ex) {
			LOG.error("ERROR GETTING METHOD TYPE FOR SALE REPORT BRANCH EMPLOYEE MODULE");
			return null;
		}
	}

}
