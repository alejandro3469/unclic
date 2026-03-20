package mx.com.endtoend.domain.reports.sales.closingOperation.business;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.com.endtoend.domain.commons.constants.GenericIdentifyMethods;
import mx.com.endtoend.domain.reports.sales.models.SaleReportInterfaceService;

public class ClosingOperationReportArticleFactory {

	private final Logger LOG = LoggerFactory.getLogger(ClosingOperationReportArticleFactory.class);

	public ClosingOperationReportInterface getImplementationByCode(String method,
			SaleReportInterfaceService saleReportInterfaceService) {
		try {
			LOG.info(String.format("INIT getImplementationByCode() "));
			LOG.info(String.format("PARAMS: [method: %s ]", method));
			GenericIdentifyMethods value = GenericIdentifyMethods.valueOf(method);
			switch (value) {
			case REPORT_CLOSING_ONE:
				LOG.info("RETURN ReportClosingOperationMethodOne()");
				return new ReportClosingOperationMethodOne(saleReportInterfaceService);

			default:
				return null;
			}
		} catch (IllegalArgumentException | NullPointerException ex) {
			LOG.error("ERROR GETTING METHOD TYPE FOR CLOSING OPERATION REPORT MODULE");
			return null;
		}
	}
}