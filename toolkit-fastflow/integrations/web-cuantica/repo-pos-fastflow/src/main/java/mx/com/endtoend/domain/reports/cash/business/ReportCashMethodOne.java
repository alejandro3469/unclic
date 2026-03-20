package mx.com.endtoend.domain.reports.cash.business;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.com.endtoend.domain.accountingRecord.dto.AccountingRecordDto;
import mx.com.endtoend.domain.branch.dto.BranchDto;
import mx.com.endtoend.domain.cash.emailReport.dto.EmailReportCashDto;
import mx.com.endtoend.domain.closings.dto.AccountingOperatingSummaryDto;
import mx.com.endtoend.domain.closings.dto.ClosingOperationDto;
import mx.com.endtoend.domain.commons.constants.ClosingIncomeEnum;
import mx.com.endtoend.domain.openings.dto.OpeningOperationDetailDto;
import mx.com.endtoend.domain.openings.dto.OpeningOperationDto;
import mx.com.endtoend.domain.reports.cash.business.validations.CashReportValidation;
import mx.com.endtoend.domain.reports.cash.dto.ClosingOperationReport;
import mx.com.endtoend.domain.reports.cash.dto.ClosingOperationReportDetail;
import mx.com.endtoend.domain.reports.cash.dto.OpeningOperationReport;
import mx.com.endtoend.domain.reports.cash.dto.OpeningOperationReportDetail;
import mx.com.endtoend.domain.reports.cash.ports.ReportCashPersistencePort;
import mx.com.endtoend.domain.users.dto.UserDto;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.GlobalError;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.ValidationError;
import mx.com.endtoend.infrastructure.services.email.EmailServicePort;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

public class ReportCashMethodOne implements ReportCashInterface {

	private EmailServicePort emailServicePort;

	public ReportCashMethodOne(EmailServicePort emailServicePort) {
		this.emailServicePort = emailServicePort;
	}

	private CashReportValidation cashReportValidation = new CashReportValidation();

	private final Logger LOG = LoggerFactory.getLogger(ReportCashMethodOne.class);

	@Override
	public ResponseModel generateOpeningReportByEmail(ReportCashPersistencePort reportCashPersistencePort,
			String employeeEmail, String companyCode, String idOperation) {

		LOG.info(String.format("%s INIT generateOpeningReportByEmail()", idOperation));
		ResponseModel responseGetOpeninig = reportCashPersistencePort
				.getOpeningOperationByEmailAndCompanyCode(employeeEmail, companyCode, idOperation);
		OpeningOperationDto openingOperation = (OpeningOperationDto) responseGetOpeninig.getData();

		String validations = cashReportValidation.validExistsOpeningOperation(openingOperation);

		if (!validations.isEmpty()) {
			LOG.warn(String.format("%s ERROR IN OPENINIG OPERATION DATA", idOperation));
			throw new ValidationError(validations);
		}

		ResponseModel responseGetUser = reportCashPersistencePort.getUserInformationByEmailAndCompanyCode(employeeEmail,
				companyCode, idOperation);
		UserDto user = (UserDto) responseGetUser.getData();

		ResponseModel responseGetBranch = reportCashPersistencePort
				.getBranchInformationByCodeAndCompanyCode(openingOperation.getBranchCode(), companyCode, idOperation);
		BranchDto branch = (BranchDto) responseGetBranch.getData();

		if (user == null || branch == null) {
			LOG.warn(String.format("%s ERROR IN SEARCH USER OR BRANCH DATA", idOperation));
			throw new GlobalError();
		}

		OpeningOperationReport openingOperationReport = generateOpeningReport(openingOperation, user, branch);

		ResponseModel responseReportGenerated = reportCashPersistencePort
				.generateOpeningOperationReportByCompanyCode(openingOperationReport, companyCode, idOperation);

		try {

			ResponseModel responseEmailReport = reportCashPersistencePort.getEmailReportByCompanyCode(companyCode,
					idOperation);
			EmailReportCashDto emailReportCashDto = (EmailReportCashDto) responseEmailReport.getData();

			if (emailReportCashDto != null) {
				System.out.println("SEND TO: " + emailReportCashDto.getEmail());
				List<String> emailLis = new ArrayList<>();
				emailLis.add(emailReportCashDto.getEmail());
				File reportToSend = new File("Opening_Operation.pdf");
				FileUtils.writeByteArrayToFile(reportToSend, (byte[]) responseReportGenerated.getData());

				emailServicePort.sendDocumentUnconfirmedByCompanyCode(emailLis, reportToSend, companyCode, idOperation);
			}

		} catch (IOException e) {
			LOG.warn(String.format("%s ERROR IN GENERATE FILE TO SEND EMAIL", idOperation));
			e.printStackTrace();
		}

		return responseReportGenerated;
	}

	private OpeningOperationReport generateOpeningReport(OpeningOperationDto openingOperation, UserDto user,
			BranchDto branch) {
		OpeningOperationReport openingOperationReport = new OpeningOperationReport(openingOperation.getCreationDate(),
				branch.getName(), user.getName() + " " + user.getFirstSurname() + " " + user.getSecondSurname(),
				openingOperation.getTotalAmount());
		List<OpeningOperationReportDetail> detail = new ArrayList<>();
		Long lineNumber = 1L;
		for (OpeningOperationDetailDto openingOperationDetail : openingOperation.getOpeningOperationDetail()) {
			detail.add(new OpeningOperationReportDetail(lineNumber,
					openingOperationDetail.getOpenPaymentInstrument().getName(),
					openingOperationDetail.getOpenPaymentInstrument().getIncomeType(),
					openingOperationDetail.getAmount()));
			lineNumber++;
		}
		openingOperationReport.setDetail(detail);
		return openingOperationReport;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ResponseModel generateClosingReportByEmail(ReportCashPersistencePort reportCashPersistencePort,
			String employeeEmail, String companyCode, String idOperation) {

		LOG.info(String.format("%s INIT generateClosingReportByEmail()", idOperation));
		ResponseModel responseGetOpeninig = reportCashPersistencePort
				.getLastClosingOpeningOperationByEmailAndCompanyCode(employeeEmail, companyCode, idOperation);
		OpeningOperationDto openingOperation = (OpeningOperationDto) responseGetOpeninig.getData();

		String validations = cashReportValidation.validOpeninigOperationStatus(openingOperation);

		if (!validations.isEmpty()) {
			LOG.warn(String.format("%s ERROR IN OPENINIG OPERATION DATA", idOperation));
			throw new ValidationError(validations);
		}

		ResponseModel responseGetUser = reportCashPersistencePort.getUserInformationByEmailAndCompanyCode(employeeEmail,
				companyCode, idOperation);
		UserDto user = (UserDto) responseGetUser.getData();

		ResponseModel responseGetBranch = reportCashPersistencePort
				.getBranchInformationByCodeAndCompanyCode(openingOperation.getBranchCode(), companyCode, idOperation);
		BranchDto branch = (BranchDto) responseGetBranch.getData();

		ResponseModel responseGetAccounting = reportCashPersistencePort
				.getAccountingRecordListByOpeningIdAndCompanyCode(openingOperation.getOpeningId(), companyCode,
						idOperation);
		List<AccountingRecordDto> accountingRecordList = (List<AccountingRecordDto>) responseGetAccounting.getData();

		ResponseModel responseGetClosingOperation = reportCashPersistencePort
				.getClosingOperationByIdAndCompanyCpde(openingOperation.getClosingId(), companyCode, idOperation);
		ClosingOperationDto closingOperation = (ClosingOperationDto) responseGetClosingOperation.getData();

		if (user == null || branch == null || closingOperation == null || accountingRecordList == null) {
			LOG.warn(String.format("%s ERROR IN SEARCH DATA TO GENERATE CLOSE REPORT", idOperation));
			throw new GlobalError();
		}

		ClosingOperationReport closingOperationReport = generateClosingReport(openingOperation, closingOperation, user,
				branch, accountingRecordList);

		ResponseModel responseClosingReport = reportCashPersistencePort
				.generateClosingOperationReportByCompanyCode(closingOperationReport, companyCode, idOperation);

		try {

			ResponseModel responseEmailReport = reportCashPersistencePort.getEmailReportByCompanyCode(companyCode,
					idOperation);
			EmailReportCashDto emailReportCashDto = (EmailReportCashDto) responseEmailReport.getData();

			if (emailReportCashDto != null) {
				List<String> emailLis = new ArrayList<>();
				emailLis.add(emailReportCashDto.getEmail());
				File reportToSend = new File("Closing_Operation.pdf");
				FileUtils.writeByteArrayToFile(reportToSend, (byte[]) responseClosingReport.getData());

				emailServicePort.sendDocumentUnconfirmedByCompanyCode(emailLis, reportToSend, companyCode, idOperation);
			}

		} catch (IOException e) {
			LOG.warn(String.format("%s ERROR IN GENERATE FILE TO SEND EMAIL", idOperation));
			e.printStackTrace();
		}

		return responseClosingReport;
	}

	private ClosingOperationReport generateClosingReport(OpeningOperationDto openingOperation,
			ClosingOperationDto closingOperation, UserDto user, BranchDto branch,
			List<AccountingRecordDto> accountingRecordList) {

		ClosingOperationReport closingOperationReport = new ClosingOperationReport(branch.getName(),
				openingOperation.getCreationDate(), closingOperation.getCreationDate(),
				user.getName() + " " + user.getFirstSurname() + " " + user.getSecondSurname(),
				closingOperation.getTotalAmount());

		AccountingOperatingSummaryDto theoreticalRecords = new AccountingOperatingSummaryDto();
		Double summaryCash;
		Double summaryCredit;
		Double summaryCreditNote;
		Double summaryCreditCard;
		Double summaryCheck;
		Double summaryTransfer;

		AccountingOperatingSummaryDto physicalRecords = AccountingOperatingSummaryDto
				.generateByClosingOperation(closingOperation.getClosingOperationDetail());

		ClosingIncomeEnum[] closingIncomeEnumList = ClosingIncomeEnum.values();
		for (ClosingIncomeEnum closingIncomeEnum : closingIncomeEnumList) {
			Double summary = 0.0;
			String incomeType = closingIncomeEnum.toString();
			summary = theoreticalRecords.getTotalIncomeTotalByMovementPayment(incomeType, accountingRecordList).doubleValue();

			if (ClosingIncomeEnum.CASH.toString().equals(incomeType)) {
				summaryCash = summary;
				theoreticalRecords.setSummaryCash(BigDecimal.valueOf(summaryCash));
			}

			if (ClosingIncomeEnum.CREDIT_NOTE.toString().equals(incomeType)) {
				summaryCreditNote = summary;
				theoreticalRecords.setSummaryCreditNote(BigDecimal.valueOf(summaryCreditNote));
			}

			if (ClosingIncomeEnum.CHECK.toString().equals(incomeType)) {
				summaryCheck = summary;
				theoreticalRecords.setSummaryCheck(BigDecimal.valueOf(summaryCheck));
			}

			if (ClosingIncomeEnum.CREDIT.toString().equals(incomeType)) {
				summaryCredit = summary;
				theoreticalRecords.setSummaryCredit(BigDecimal.valueOf(summaryCredit));
			}

			if (ClosingIncomeEnum.CREDIT_CARD.toString().equals(incomeType)) {
				summaryCreditCard = summary;
				theoreticalRecords.setSummaryCreditCard(BigDecimal.valueOf(summaryCreditCard));
			}

			if (ClosingIncomeEnum.TRANSFER.toString().equals(incomeType)) {
				summaryTransfer = summary;
				theoreticalRecords.setSummaryTransfer(BigDecimal.valueOf(summaryTransfer));
			}

			summary = 0.0;
		}

		List<ClosingOperationReportDetail> closingOperationReportDetails = new ArrayList<>();

		Double diferencesCash = theoreticalRecords.getSummaryCash().subtract(physicalRecords.getSummaryCash()).doubleValue();
		diferencesCash = (double) Math.round(diferencesCash * 100) / 100;
		closingOperationReportDetails.add(new ClosingOperationReportDetail(ClosingIncomeEnum.CASH.toString(), "MXN",
				theoreticalRecords.getSummaryCash(), physicalRecords.getSummaryCash(), BigDecimal.valueOf(diferencesCash)));

		Double diferencesCreditCard = theoreticalRecords.getSummaryCreditCard()
				.subtract(physicalRecords.getSummaryCreditCard()).doubleValue();
		diferencesCreditCard = (double) Math.round(diferencesCreditCard * 100) / 100;
		closingOperationReportDetails.add(new ClosingOperationReportDetail(ClosingIncomeEnum.CREDIT_CARD.toString(),
				"MXN", theoreticalRecords.getSummaryCreditCard(), physicalRecords.getSummaryCreditCard(),
				BigDecimal.valueOf(diferencesCreditCard)));

		Double diferencesTransfer = theoreticalRecords.getSummaryTransfer().subtract(physicalRecords.getSummaryTransfer()).doubleValue();
		diferencesTransfer = (double) Math.round(diferencesTransfer * 100) / 100;
		closingOperationReportDetails.add(new ClosingOperationReportDetail(ClosingIncomeEnum.TRANSFER.toString(), "MXN",
				theoreticalRecords.getSummaryTransfer(), physicalRecords.getSummaryTransfer(), BigDecimal.valueOf(diferencesTransfer)));

		Double diferencesCheck = theoreticalRecords.getSummaryCheck().subtract(physicalRecords.getSummaryCheck()).doubleValue();
		diferencesCheck = (double) Math.round(diferencesCheck * 100) / 100;
		closingOperationReportDetails.add(new ClosingOperationReportDetail(ClosingIncomeEnum.CHECK.toString(), "MXN",
				theoreticalRecords.getSummaryCheck(), physicalRecords.getSummaryCheck(), BigDecimal.valueOf(diferencesCheck)));
		
		Double diferencesCreditNote = theoreticalRecords.getSummaryCreditNote().subtract(physicalRecords.getSummaryCreditNote()).doubleValue();
		diferencesCreditNote = (double) Math.round(diferencesCreditNote * 100) / 100;
		closingOperationReportDetails.add(new ClosingOperationReportDetail(ClosingIncomeEnum.CREDIT_NOTE.toString(), "MXN",
				theoreticalRecords.getSummaryCreditNote(), physicalRecords.getSummaryCreditNote(), BigDecimal.valueOf(diferencesCreditNote)));

		if (theoreticalRecords.getSummaryCredit().compareTo(BigDecimal.ZERO) > 0 && physicalRecords.getSummaryCredit().compareTo(BigDecimal.ZERO) > 0) {
			Double diferencesCredit = theoreticalRecords.getSummaryCredit().subtract(physicalRecords.getSummaryCredit()).doubleValue();
			diferencesCredit = (double) Math.round(diferencesCredit * 100) / 100;
			closingOperationReportDetails.add(new ClosingOperationReportDetail(ClosingIncomeEnum.CREDIT.toString(),
					"MXN", theoreticalRecords.getSummaryCredit(), physicalRecords.getSummaryCredit(),
					BigDecimal.valueOf(diferencesCredit)));
		}

		closingOperationReport.setDetail(closingOperationReportDetails);

		return closingOperationReport;
	}

}
