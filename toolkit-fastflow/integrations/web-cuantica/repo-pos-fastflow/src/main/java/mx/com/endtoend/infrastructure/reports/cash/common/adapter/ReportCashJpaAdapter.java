package mx.com.endtoend.infrastructure.reports.cash.common.adapter;

import java.util.List;

import mx.com.endtoend.infrastructure.reports.cash.common.repository.GenericReportCashRepository;
import mx.com.endtoend.infrastructure.reports.cash.common.factory.ReportCashRepositoryFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import mx.com.endtoend.domain.accountingRecord.dto.AccountingRecordDto;
import mx.com.endtoend.domain.branch.dto.BranchDto;
import mx.com.endtoend.domain.cash.emailReport.dto.EmailReportCashDto;
import mx.com.endtoend.domain.closings.dto.ClosingOperationDto;
import mx.com.endtoend.domain.openings.dto.OpeningOperationDto;
import mx.com.endtoend.domain.reports.cash.dto.ClosingOperationReport;
import mx.com.endtoend.domain.reports.cash.dto.OpeningOperationReport;
import mx.com.endtoend.domain.reports.cash.ports.ReportCashPersistencePort;
import mx.com.endtoend.domain.users.dto.UserDto;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.GlobalError;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

public class ReportCashJpaAdapter implements ReportCashPersistencePort {

	@Autowired
	private ReportCashRepositoryFactory reportCashRepositoryFactory;

	private final Logger LOG = LoggerFactory.getLogger(ReportCashJpaAdapter.class);

	@Override
	public ResponseModel generateOpeningOperationReportByCompanyCode(OpeningOperationReport openingOperationReport,
			String companyCode, String idOperation) {
		LOG.info(String.format("%s INIT generateOpeningOperationReportByCompanyCode()", idOperation));
		LOG.info(String.format("%s PARAMS:[ openingOperationReport: %s , companyCode: %s ] ", idOperation,
				openingOperationReport.toString(), companyCode));
		GenericReportCashRepository repository = reportCashRepositoryFactory.getRepositoryByCompanyCode(companyCode);
		if (repository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		return repository.generateOpeningOperationReport(openingOperationReport, idOperation);
	}

	@Override
	public ResponseModel generateClosingOperationReportByCompanyCode(ClosingOperationReport closingOperationRepor,
			String companyCode, String idOperation) {
		LOG.info(String.format("%s INIT generateClosingOperationReportByCompanyCode()", idOperation));
		LOG.info(String.format("%s PARAMS:[ openingOperationReport: , companyCode: %s ] ", idOperation, companyCode));
		GenericReportCashRepository repository = reportCashRepositoryFactory.getRepositoryByCompanyCode(companyCode);
		if (repository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		return repository.generateClosingOperationReport(closingOperationRepor, idOperation);
	}

	@Override
	public ResponseModel getUserInformationByEmailAndCompanyCode(String email, String companyCode, String idOperation) {
		LOG.info(String.format("%s INIT getUserInformationByEmailAndCompanyCode()", idOperation));
		LOG.info(String.format("%s PARAMS:[ email: %s , companyCode: %s ] ", idOperation, email, companyCode));
		GenericReportCashRepository repository = reportCashRepositoryFactory.getRepositoryByCompanyCode(companyCode);
		if (repository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		UserDto user = repository.getUserInformationByEmail(email, idOperation);
		return new ResponseModel(user);
	}

	@Override
	public ResponseModel getBranchInformationByCodeAndCompanyCode(String branchCode, String companyCode,
			String idOperation) {
		LOG.info(String.format("%s INIT getBranchInformationByCodeAndCompanyCode()", idOperation));
		LOG.info(
				String.format("%s PARAMS:[ branchCode: %s , companyCode: %s ] ", idOperation, branchCode, companyCode));
		GenericReportCashRepository repository = reportCashRepositoryFactory.getRepositoryByCompanyCode(companyCode);
		if (repository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		BranchDto branch = repository.getBranchInformationByCodeAndCompanyCode(branchCode, companyCode, idOperation);
		return new ResponseModel(branch);
	}

	@Override
	public ResponseModel getOpeningOperationByEmailAndCompanyCode(String email, String companyCode,
			String idOperation) {
		LOG.info(String.format("%s INIT getOpeningOperationByEmailAndCompanyCode()", idOperation));
		LOG.info(String.format("%s PARAMS:[ email: %s , companyCode: %s ] ", idOperation, email, companyCode));
		GenericReportCashRepository repository = reportCashRepositoryFactory.getRepositoryByCompanyCode(companyCode);
		if (repository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		OpeningOperationDto openingOperation = repository.getOpeningOperationByEmail(email, idOperation);
		return new ResponseModel(openingOperation);
	}

	@Override
	public ResponseModel getLastClosingOpeningOperationByEmailAndCompanyCode(String email, String companyCode,
			String idOperation) {
		LOG.info(String.format("%s INIT getLastClosingOpeningOperationByEmailAndCompanyCode()", idOperation));
		LOG.info(String.format("%s PARAMS:[ email: %s , companyCode: %s ] ", idOperation, email, companyCode));
		GenericReportCashRepository repository = reportCashRepositoryFactory.getRepositoryByCompanyCode(companyCode);
		if (repository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		OpeningOperationDto openingOperation = repository.getLastClosingOpeningOperationByEmail(email, idOperation);
		return new ResponseModel(openingOperation);
	}

	@Override
	public ResponseModel getClosingOperationByIdAndCompanyCpde(Long idClosing, String companyCode, String idOperation) {
		LOG.info(String.format("%s INIT getClosingOperationByIdAndCompanyCpde()", idOperation));
		LOG.info(String.format("%s PARAMS:[ idClosing: %s , companyCode: %s ] ", idOperation, idClosing.toString(),
				companyCode));
		GenericReportCashRepository repository = reportCashRepositoryFactory.getRepositoryByCompanyCode(companyCode);
		if (repository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		ClosingOperationDto closingOperation = repository.getClosingOperationById(idClosing, idOperation);
		return new ResponseModel(closingOperation);
	}

	@Override
	public ResponseModel getAccountingRecordListByOpeningIdAndCompanyCode(Long idOpening, String companyCode,
			String idOperation) {
		LOG.info(String.format("%s INIT getAccountingRecordListByOpeningIdAndCompanyCode()", idOperation));
		LOG.info(String.format("%s PARAMS:[ idOpening: %s , companyCode: %s ] ", idOperation, idOpening.toString(),
				companyCode));
		GenericReportCashRepository repository = reportCashRepositoryFactory.getRepositoryByCompanyCode(companyCode);
		if (repository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		List<AccountingRecordDto> accountingRecordList = repository.getAccountingRecordListByOpeningId(idOpening,
				idOperation);
		return new ResponseModel(accountingRecordList);
	}

	@Override
	public ResponseModel getEmailReportByCompanyCode(String companyCode, String idOperation) {
		LOG.info(String.format("%s INIT getEmailReportByCompanyCode()", idOperation));
		LOG.info(String.format("%s PARAMS:[ companyCode: %s ] ", idOperation, companyCode));
		GenericReportCashRepository repository = reportCashRepositoryFactory.getRepositoryByCompanyCode(companyCode);
		if (repository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		EmailReportCashDto emailReportCashDto = repository.getEmailReport(idOperation);
		return new ResponseModel(emailReportCashDto);
	}

}
