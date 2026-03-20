package mx.com.endtoend.domain.reports.sales.closingOperation.business;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.com.endtoend.domain.branch.dto.BranchDto;
import mx.com.endtoend.domain.branch.ports.spi.BranchPersistencePort;
import mx.com.endtoend.domain.reports.sales.closingOperation.business.validations.ReportClosingValidation;
import mx.com.endtoend.domain.reports.sales.closingOperation.dto.AccountingRecordReported;
import mx.com.endtoend.domain.reports.sales.closingOperation.dto.AccountingTicketRecord;
import mx.com.endtoend.domain.reports.sales.closingOperation.dto.ClosingOperationReportParamsDto;
import mx.com.endtoend.domain.reports.sales.closingOperation.dto.ClosingOperationReported;
import mx.com.endtoend.domain.reports.sales.closingOperation.dto.ClosingOperationSummarySeach;
import mx.com.endtoend.domain.reports.sales.closingOperation.dto.report.BranchClosingDetail;
import mx.com.endtoend.domain.reports.sales.closingOperation.dto.report.BranchClosingOperationReport;
import mx.com.endtoend.domain.reports.sales.closingOperation.dto.report.BranchClosingOperationSummary;
import mx.com.endtoend.domain.reports.sales.closingOperation.dto.report.GeneralClosingDetail;
import mx.com.endtoend.domain.reports.sales.closingOperation.dto.report.GeneralClosingOperationReport;
import mx.com.endtoend.domain.reports.sales.closingOperation.dto.report.GenralClosingOperationSummary;
import mx.com.endtoend.domain.reports.sales.closingOperation.ports.ReportClosingOperationPersistencePort;
import mx.com.endtoend.domain.reports.sales.models.SaleReportInterfaceService;
import mx.com.endtoend.domain.users.dto.UserDto;
import mx.com.endtoend.domain.users.ports.spi.UserPersistencePort;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.ValidationError;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

public class ReportClosingOperationMethodOne implements ClosingOperationReportInterface {

	private BranchPersistencePort branchPersistencePort;

	private UserPersistencePort userPersistencePort;

	private ReportClosingOperationPersistencePort reportClosingOperationPersistencePort;

	public ReportClosingOperationMethodOne(SaleReportInterfaceService saleReportInterfaceService) {
		this.branchPersistencePort = saleReportInterfaceService.getBranchPersistencePort();
		this.userPersistencePort = saleReportInterfaceService.getUserPersistencePort();
		this.reportClosingOperationPersistencePort = saleReportInterfaceService
				.getReportClosingOperationPersistencePort();
	}

	private ReportClosingValidation reportClosingValidation = new ReportClosingValidation();

	private ClosingReportMathService closingReportMathService = new ClosingReportMathService();

	private final Logger LOG = LoggerFactory.getLogger(ReportClosingOperationMethodOne.class);

	@Override
	public ResponseModel generateReportClosingOperationByParamsAndCompanyCode(
			ClosingOperationReportParamsDto closingReportParamsDto, String companyCode, String idOperation) {

		LOG.info(String.format("%s INIT generateReportClosingOperationByParamsAndCompanyCode()", idOperation));

		LOG.info(String.format("%s INIT generateReportBranchEmployee()", idOperation));
		String validations = reportClosingValidation.validOperativeDataToGenerateReport(closingReportParamsDto);
		if (!validations.isEmpty()) {
			LOG.warn(String.format("%s BAD PARAMS", idOperation));
			throw new ValidationError(validations);
		}
		ResponseModel report = null;
		if (closingReportParamsDto.getBranchCode().isEmpty()) {
			LOG.info(String.format("%s GENERATE GLOBAL REPORT", idOperation));
			report = generateGlobalClosingReport(closingReportParamsDto, companyCode, idOperation);
			return report;
		}

		LOG.info(String.format("%s GENERATE REPORT TO BRANCH %s", idOperation, closingReportParamsDto.getBranchCode()));
		report = generateBranhClosingReport(closingReportParamsDto, companyCode, idOperation);

		return report;
	}

	/**
	 * Método para la generación del reporte de cierre de operación para una
	 * sucursal
	 * 
	 * @param closingReportParamsDto
	 * @param companyCode
	 * @param idOperation
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private ResponseModel generateBranhClosingReport(ClosingOperationReportParamsDto closingReportParamsDto,
			String companyCode, String idOperation) {

		ResponseModel responseClosingSummarySearch = reportClosingOperationPersistencePort
				.findClosingRecordsByParamsAndCompanyCode(closingReportParamsDto, companyCode, idOperation);
		List<ClosingOperationSummarySeach> closingOperationSummaryList = (List<ClosingOperationSummarySeach>) responseClosingSummarySearch
				.getData();

		List<BranchClosingOperationSummary> branchClosingOperationSummaryList = new ArrayList<>();
		for (ClosingOperationSummarySeach closingOperationSummarySeach : closingOperationSummaryList) {

			List<AccountingRecordReported> accountingRecordReportedList = (List<AccountingRecordReported>) reportClosingOperationPersistencePort
					.findIncomeAccountingRecordsByParamsAndCompanyCode(closingOperationSummarySeach, companyCode,
							idOperation)
					.getData();

			List<ClosingOperationReported> closingOperationReportedList = (List<ClosingOperationReported>) reportClosingOperationPersistencePort
					.findClosingRecordsByParamsAndCompanyCode(closingOperationSummarySeach, companyCode, idOperation)
					.getData();

			List<BranchClosingDetail> branchClosingDetailList = closingReportMathService.generateOperativeData(
					closingOperationSummarySeach, closingOperationReportedList, accountingRecordReportedList);

			ResponseModel responseUser = userPersistencePort
					.findByEmailToLogin(closingOperationSummarySeach.getEmployeeEmail(), idOperation);
			UserDto user = (UserDto) responseUser.getData();

			if (branchClosingDetailList.size() > 0) {
				BranchClosingOperationSummary branchClosingOperationSummary = new BranchClosingOperationSummary(user,
						branchClosingDetailList);
				branchClosingOperationSummaryList.add(branchClosingOperationSummary);
			}

		}

		ResponseModel searchBranch = branchPersistencePort.getBranchDetailByBranchCodeAndCompanyCode(
				closingReportParamsDto.getBranchCode(), companyCode, idOperation);
		BranchDto branchDto = (BranchDto) searchBranch.getData();

		BranchClosingOperationReport branchClosingOperationReport = new BranchClosingOperationReport(branchDto,
				closingReportParamsDto, branchClosingOperationSummaryList);

		return reportClosingOperationPersistencePort.generateReportByBranchClosingAndCompanyCode(
				branchClosingOperationReport, closingReportParamsDto.getFormat(), companyCode, idOperation);
	}

	/**
	 * Método para la generación de reporte de cierre de operacion para todas las
	 * sucursales de la compañía
	 * 
	 * @param closingReportParamsDto
	 * @param companyCode
	 * @param idOperation
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private ResponseModel generateGlobalClosingReport(ClosingOperationReportParamsDto closingReportParamsDto,
			String companyCode, String idOperation) {

		List<BranchDto> branchList = new ArrayList<>();
		ResponseModel responseBranchList = branchPersistencePort.getBranchListByCompanyCode(companyCode, idOperation);
		branchList = (List<BranchDto>) responseBranchList.getData();

		List<GenralClosingOperationSummary> closingOperationSummary = new ArrayList<>();
		for (BranchDto branchDto : branchList) {

			closingReportParamsDto.setBranchCode(branchDto.getCode());
			ResponseModel responseClosingSummarySearch = reportClosingOperationPersistencePort
					.findClosingRecordsByParamsAndCompanyCode(closingReportParamsDto, companyCode, idOperation);
			List<ClosingOperationSummarySeach> closingOperationSummaryList = (List<ClosingOperationSummarySeach>) responseClosingSummarySearch
					.getData();

			List<ClosingOperationReported> globalClosingOperationReported = new ArrayList<>();
			List<AccountingTicketRecord> globalAccountingTicketRecord = new ArrayList<>();

			for (ClosingOperationSummarySeach closingOperationSummarySeach : closingOperationSummaryList) {

				List<ClosingOperationReported> closingOperationReportedList = (List<ClosingOperationReported>) reportClosingOperationPersistencePort
						.findClosingAccountingRecordsByParamsAndCompanyCode(closingOperationSummarySeach, companyCode,
								idOperation)
						.getData();
				globalClosingOperationReported.addAll(closingOperationReportedList);

				List<AccountingTicketRecord> accountingTicketRecordList = (List<AccountingTicketRecord>) reportClosingOperationPersistencePort
						.findAccountingTicketReferenceByParamsAndCompanyCode(closingOperationSummarySeach, companyCode,
								idOperation)
						.getData();
				globalAccountingTicketRecord.addAll(accountingTicketRecordList);

			}

			List<GeneralClosingDetail> globalGeneralClosingDetailList = closingReportMathService
					.generateGlobalOperativeData(globalClosingOperationReported, globalAccountingTicketRecord);

			if (globalGeneralClosingDetailList.size() > 0) {
				GenralClosingOperationSummary genralClosingOperationSummary = new GenralClosingOperationSummary(
						branchDto, globalGeneralClosingDetailList);
				closingOperationSummary.add(genralClosingOperationSummary);
			}

		}

		GeneralClosingOperationReport closingOperationReport = new GeneralClosingOperationReport(closingReportParamsDto,
				closingOperationSummary);
		
		return reportClosingOperationPersistencePort.generateReportByGeneralClosingAndCompanyCode(
				closingOperationReport, closingReportParamsDto.getFormat(), companyCode, idOperation);
	}

}
