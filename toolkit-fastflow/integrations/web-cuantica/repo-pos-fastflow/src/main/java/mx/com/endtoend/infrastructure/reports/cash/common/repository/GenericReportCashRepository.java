package mx.com.endtoend.infrastructure.reports.cash.common.repository;

import java.util.List;

import mx.com.endtoend.domain.accountingRecord.dto.AccountingRecordDto;
import mx.com.endtoend.domain.branch.dto.BranchDto;
import mx.com.endtoend.domain.cash.emailReport.dto.EmailReportCashDto;
import mx.com.endtoend.domain.closings.dto.ClosingOperationDto;
import mx.com.endtoend.domain.openings.dto.OpeningOperationDto;
import mx.com.endtoend.domain.reports.cash.dto.ClosingOperationReport;
import mx.com.endtoend.domain.reports.cash.dto.OpeningOperationReport;
import mx.com.endtoend.domain.users.dto.UserDto;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

public interface GenericReportCashRepository {

	ResponseModel generateOpeningOperationReport(OpeningOperationReport openingOperationReport, String idOperation);

	ResponseModel generateClosingOperationReport(ClosingOperationReport closingOperationRepor, String idOperation);

	UserDto getUserInformationByEmail(String email, String idOperation);

	BranchDto getBranchInformationByCodeAndCompanyCode(String branchCode, String companyCode, String idOperation);

	OpeningOperationDto getOpeningOperationByEmail(String email, String idOperation);

	OpeningOperationDto getLastClosingOpeningOperationByEmail(String email, String idOperation);

	ClosingOperationDto getClosingOperationById(Long idClosing, String idOperation);

	List<AccountingRecordDto> getAccountingRecordListByOpeningId(Long idOpening, String idOperation);

	EmailReportCashDto getEmailReport(String idOperation);

}
