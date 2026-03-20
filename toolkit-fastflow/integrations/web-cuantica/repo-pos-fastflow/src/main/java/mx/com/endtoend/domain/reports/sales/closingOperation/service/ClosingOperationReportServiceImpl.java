package mx.com.endtoend.domain.reports.sales.closingOperation.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.com.endtoend.domain.reports.sales.closingOperation.business.ClosingOperationReportArticleFactory;
import mx.com.endtoend.domain.reports.sales.closingOperation.business.ClosingOperationReportInterface;
import mx.com.endtoend.domain.reports.sales.closingOperation.dto.ClosingOperationReportParamsDto;
import mx.com.endtoend.domain.reports.sales.closingOperation.ports.ReportClosingOperationPersistencePort;
import mx.com.endtoend.domain.reports.sales.closingOperation.ports.ReportClosingOperationServicePort;
import mx.com.endtoend.domain.reports.sales.models.SaleReportInterfaceService;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.GlobalError;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

public class ClosingOperationReportServiceImpl implements ReportClosingOperationServicePort {

	private ReportClosingOperationPersistencePort reportClosingOperationPersistencePort;

	public ClosingOperationReportServiceImpl(
			ReportClosingOperationPersistencePort reportClosingOperationPersistencePort) {
		this.reportClosingOperationPersistencePort = reportClosingOperationPersistencePort;
	}

	private final Logger LOG = LoggerFactory.getLogger(ClosingOperationReportServiceImpl.class);

	private ClosingOperationReportArticleFactory factory = new ClosingOperationReportArticleFactory();

	@Override
	public ResponseModel generateReportClosingOperationByParamsAndCompanyCode(
			ClosingOperationReportParamsDto closingOperationReportParamsDto,
			SaleReportInterfaceService saleReportInterfaceService, String method, String companyCode,
			String idOperation) {

		LOG.info(String.format("%s INIT generateReportClosingOperationByParamsAndCompanyCode()", idOperation));
		saleReportInterfaceService.setReportClosingOperationPersistencePort(reportClosingOperationPersistencePort);
		ClosingOperationReportInterface reportSale = factory.getImplementationByCode(method,
				saleReportInterfaceService);
		if (reportSale == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		ResponseModel response = reportSale.generateReportClosingOperationByParamsAndCompanyCode(
				closingOperationReportParamsDto, companyCode, idOperation);
		return response;
	}

}
