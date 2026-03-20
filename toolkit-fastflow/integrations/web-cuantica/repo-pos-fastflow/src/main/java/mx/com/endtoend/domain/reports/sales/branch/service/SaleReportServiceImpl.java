package mx.com.endtoend.domain.reports.sales.branch.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.com.endtoend.domain.reports.sales.branch.business.ReportSaleInterface;
import mx.com.endtoend.domain.reports.sales.branch.business.SaleReportFactory;
import mx.com.endtoend.domain.reports.sales.branch.dto.GenericSearchSaleReportParamsDto;
import mx.com.endtoend.domain.reports.sales.branch.ports.SaleReportPersistencePort;
import mx.com.endtoend.domain.reports.sales.branch.ports.SaleReportServicePort;
import mx.com.endtoend.domain.reports.sales.models.SaleReportInterfaceService;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.GlobalError;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

public class SaleReportServiceImpl implements SaleReportServicePort {

	private SaleReportPersistencePort saleReportPersistencePort;

	public SaleReportServiceImpl(SaleReportPersistencePort saleReportPersistencePort) {
		this.saleReportPersistencePort = saleReportPersistencePort;
	}

	private SaleReportFactory saleReportFactory = new SaleReportFactory();

	private final Logger LOG = LoggerFactory.getLogger(SaleReportServiceImpl.class);

	@Override
	public ResponseModel generateReportBySaleBranchAndCompanyCode(GenericSearchSaleReportParamsDto saleReportParams,
			SaleReportInterfaceService saleReportInterfaceService, String method, String companyCode,
			String idOperation) {
		LOG.info(String.format("%s INIT generateReportBySaleBranchAndCompanyCode()", idOperation));
		saleReportInterfaceService.setSaleReportPersistencePort(saleReportPersistencePort);

		ReportSaleInterface reportSale = saleReportFactory.getImplementationByCode(method, saleReportInterfaceService);
		if (reportSale == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}

		ResponseModel response = reportSale.generateReportBySaleBranchAndCompanyCode(saleReportParams, companyCode,
				idOperation);
		return response;
	}

}
