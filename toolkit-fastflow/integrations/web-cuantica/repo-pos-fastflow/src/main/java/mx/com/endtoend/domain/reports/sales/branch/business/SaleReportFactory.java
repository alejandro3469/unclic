package mx.com.endtoend.domain.reports.sales.branch.business;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.com.endtoend.domain.commons.constants.GenericIdentifyMethods;
import mx.com.endtoend.domain.reports.sales.models.SaleReportInterfaceService;

public class SaleReportFactory {

	private final Logger LOG = LoggerFactory.getLogger(SaleReportFactory.class);

	public ReportSaleInterface getImplementationByCode(String method,
			SaleReportInterfaceService saleReportInterfaceService) {

		try {
			LOG.info(String.format("INIT getImplementationByCode() "));
			LOG.info(String.format("PARAMS: [method: %s ]", method));

			GenericIdentifyMethods value = GenericIdentifyMethods.valueOf(method);

			switch (value) {
			case REPORT_SALE_ONE:
				LOG.info("RETURN SaleReportMethodOne()");
				return new SaleReportMethodOne(saleReportInterfaceService);

			default:
				return null;
			}

		} catch (IllegalArgumentException | NullPointerException ex) {
			LOG.error("ERROR GETTING METHOD TYPE FOR SALE REPORT MODULE");
			return null;
		}

	}

}
