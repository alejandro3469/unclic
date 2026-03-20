package mx.com.endtoend.domain.reports.cash.ports;

import mx.com.endtoend.domain.reports.cash.dto.ClosingOperationReport;
import mx.com.endtoend.domain.reports.cash.dto.OpeningOperationReport;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

public interface ReportCashPersistencePort {

	ResponseModel generateOpeningOperationReportByCompanyCode(OpeningOperationReport openingOperationReport,
			String companyCode, String idOperation);

	ResponseModel generateClosingOperationReportByCompanyCode(ClosingOperationReport closingOperationRepor, String companyCode,
			String idOperation);

	ResponseModel getUserInformationByEmailAndCompanyCode(String email, String companyCode, String idOperation);

	ResponseModel getBranchInformationByCodeAndCompanyCode(String branchCode, String companyCode, String idOperation);

	ResponseModel getOpeningOperationByEmailAndCompanyCode(String email, String companyCode, String idOperation);

	ResponseModel getLastClosingOpeningOperationByEmailAndCompanyCode(String email, String companyCode,
			String idOperation);

	ResponseModel getClosingOperationByIdAndCompanyCpde(Long idClosing, String companyCode, String idOperation);

	ResponseModel getAccountingRecordListByOpeningIdAndCompanyCode(Long idOpening, String companyCode,
			String idOperation);
	
	ResponseModel getEmailReportByCompanyCode(String companyCode, String idOperation);
}
