package mx.com.endtoend.domain.reports.sales.branchEmployee.service;

import mx.com.endtoend.domain.reports.sales.branchEmployee.dto.SaleAndNotesReportParamsDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.com.endtoend.domain.reports.sales.branchEmployee.business.ReportSaleBranchEmployeeFactory;
import mx.com.endtoend.domain.reports.sales.branchEmployee.business.ReportSaleBranchEmployeeInterface;
import mx.com.endtoend.domain.reports.sales.branchEmployee.dto.SaleReportBranchEmployeeParamsDto;
import mx.com.endtoend.domain.reports.sales.branchEmployee.ports.ReportSaleBranchEmployeePersistencePort;
import mx.com.endtoend.domain.reports.sales.branchEmployee.ports.ReportSaleBranchEmployeeServicePort;
import mx.com.endtoend.domain.reports.sales.models.SaleReportInterfaceService;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.GlobalError;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

public class SaleBranchEmlpoyeeReportServiceImpl implements ReportSaleBranchEmployeeServicePort {

	private ReportSaleBranchEmployeePersistencePort reportSaleBranchEmployeePersistencePort;

	public SaleBranchEmlpoyeeReportServiceImpl(
			ReportSaleBranchEmployeePersistencePort reportSaleBranchEmployeePersistencePort) {
		this.reportSaleBranchEmployeePersistencePort = reportSaleBranchEmployeePersistencePort;
	}
	
	private ReportSaleBranchEmployeeFactory reportSaleBranchEmployeeFactory = new ReportSaleBranchEmployeeFactory();

	private final Logger LOG = LoggerFactory.getLogger(SaleBranchEmlpoyeeReportServiceImpl.class);

	@Override
	public ResponseModel generateReportByParamsAndCompanyCode(
			SaleReportBranchEmployeeParamsDto reportBranchEmployeeParamsDto,
			SaleReportInterfaceService reportInterfaceService, String method, String companyCode, String idOperation) {
		LOG.info(String.format("%s INIT generateReportByParamsAndCompanyCode()", idOperation));
		reportInterfaceService.setReportSaleBranchEmployeePersistencePort(reportSaleBranchEmployeePersistencePort);
		ReportSaleBranchEmployeeInterface reportSale = reportSaleBranchEmployeeFactory
				.getImplementationByCode(method, reportInterfaceService);
		if (reportSale == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		ResponseModel response = reportSale.generateReportBranchEmployee(reportBranchEmployeeParamsDto, companyCode,
				idOperation);
		return response;
	}

    @Override
    public ResponseModel generateReportByParamsAndCompanyCodeV2(
            SaleAndNotesReportParamsDto reportBranchEmployeeParamsDto,
            SaleReportInterfaceService reportInterfaceService, String method, String companyCode, String idOperation) {
        LOG.info(String.format("%s INIT generateReportByParamsAndCompanyCodeV2()", idOperation));
        reportInterfaceService.setReportSaleBranchEmployeePersistencePort(reportSaleBranchEmployeePersistencePort);
        ReportSaleBranchEmployeeInterface reportSale = reportSaleBranchEmployeeFactory
                .getImplementationByCode(method, reportInterfaceService);
        if (reportSale == null) {
            LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
            throw new GlobalError();
        }
        ResponseModel response = reportSale.generateReportBranchEmployeeV2(reportBranchEmployeeParamsDto, companyCode,
                idOperation);
        return response;
    }

}
