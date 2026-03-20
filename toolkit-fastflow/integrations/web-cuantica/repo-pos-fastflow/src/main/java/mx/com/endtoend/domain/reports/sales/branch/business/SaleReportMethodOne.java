package mx.com.endtoend.domain.reports.sales.branch.business;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.com.endtoend.domain.branch.dto.BranchDto;
import mx.com.endtoend.domain.branch.ports.spi.BranchPersistencePort;
import mx.com.endtoend.domain.reports.sales.branch.business.validations.ReportSaleValidation;
import mx.com.endtoend.domain.reports.sales.branch.dto.BranchSaleReportDto;
import mx.com.endtoend.domain.reports.sales.branch.dto.BranchSaleSummaryDto;
import mx.com.endtoend.domain.reports.sales.branch.dto.GenericSearchSaleReportParamsDto;
import mx.com.endtoend.domain.reports.sales.branch.dto.SummaryInstrumentSaleDto;
import mx.com.endtoend.domain.reports.sales.branch.dto.SummarySaleBranchDto;
import mx.com.endtoend.domain.reports.sales.branch.ports.SaleReportPersistencePort;
import mx.com.endtoend.domain.reports.sales.models.SaleReportInterfaceService;
import mx.com.endtoend.domain.reports.sales.models.SummarySaleCashPaymentDto;
import mx.com.endtoend.domain.reports.sales.models.SummarySaleCheckPaymentDto;
import mx.com.endtoend.domain.reports.sales.models.SummarySaleCreditCardPaymentDto;
import mx.com.endtoend.domain.reports.sales.models.SummarySaleCreditNotePaymentDto;
import mx.com.endtoend.domain.reports.sales.models.SummarySaleCreditPaymentDto;
import mx.com.endtoend.domain.reports.sales.models.SummarySaleTransferPaymentDto;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.ValidationError;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

public class SaleReportMethodOne implements ReportSaleInterface {

	private static final String CREDIT = "CRÉDITO";

	private static final String CHEQUE = "CHEQUE";

	private static final String NOTAS_DE_CREDITO = "NOTAS DE CRÉDITO";

	private static final String TRANSFERENCIA = "TRANSFERENCIA";

	private static final String TARJETAS = "TARJETAS";

	private static final String EFECTIVO = "EFECTIVO";

	private BranchPersistencePort branchPersistencePort;

	private SaleReportPersistencePort saleReportPersistencePort;

	public SaleReportMethodOne(SaleReportInterfaceService saleReportInterfaceService) {

		this.branchPersistencePort = saleReportInterfaceService.getBranchPersistencePort();
		this.saleReportPersistencePort = saleReportInterfaceService.getSaleReportPersistencePort();

	}

	private ReportSaleValidation reportSaleValidation = new ReportSaleValidation();

	private final Logger LOG = LoggerFactory.getLogger(SaleReportMethodOne.class);

	@SuppressWarnings("unchecked")
	@Override
	public ResponseModel generateReportBySaleBranchAndCompanyCode(GenericSearchSaleReportParamsDto saleReportParams,
			String companyCode, String idOperation) {

		LOG.info(String.format("%s INIT generateReportBySaleBranchAndCompanyCode()", idOperation));

		String validations = reportSaleValidation.validOperativeDataToGenerateBranchSaleReport(saleReportParams);
		if (!validations.isEmpty()) {
			LOG.warn(String.format("%s BAD PARAMS", idOperation));
			throw new ValidationError(validations);
		}

		List<BranchDto> branchList = new ArrayList<>();
		if (!saleReportParams.getBranchCode().isEmpty()) {
			ResponseModel responseModel = branchPersistencePort.getBranchDetailByBranchCodeAndCompanyCode(
					saleReportParams.getBranchCode(), companyCode, idOperation);
			BranchDto branch = (BranchDto) responseModel.getData();
			branchList.add(branch);
		} else {
			ResponseModel responseBranchList = branchPersistencePort.getBranchListByCompanyCode(companyCode,
					idOperation);
			branchList = (List<BranchDto>) responseBranchList.getData();
		}

		List<BranchSaleSummaryDto> branchSalSummaryList = new ArrayList<>();
		for (BranchDto branchDto : branchList) {
			saleReportParams = generateSearchParamsByRoleJob(saleReportParams, branchDto);
			BranchSaleSummaryDto branchSaleReport = generateReportSummaryByBrach(saleReportParams, companyCode,
					idOperation, branchDto);
			if (branchSaleReport.getInstrumentDetail().size() > 0)
				branchSalSummaryList.add(branchSaleReport);
		}

		BranchSaleReportDto branchSaleReport = new BranchSaleReportDto(saleReportParams, branchSalSummaryList);
		LOG.info(branchSaleReport.toString());
		return saleReportPersistencePort.generateReportBySaleBranchAndCompanyCode(branchSaleReport,
				saleReportParams.getFormat(), companyCode, idOperation);
	}

	private BranchSaleSummaryDto generateReportSummaryByBrach(GenericSearchSaleReportParamsDto saleReportParams,
			String companyCode, String idOperation, BranchDto branch) {

		SummaryInstrumentSaleDto summaryInstrumentSaleCash = generatePaymentCashSummary(saleReportParams, companyCode,
				idOperation);

		SummaryInstrumentSaleDto summaryInstrumentSaleCreditCard = generatePaymentCreditCardSummary(saleReportParams,
				companyCode, idOperation);

		SummaryInstrumentSaleDto summaryInstrumentSaleCreditNote = generatePaymentCreditNoteSummary(saleReportParams,
				companyCode, idOperation);

		SummaryInstrumentSaleDto summaryInstrumentSaleTransfer = generatePaymentTransferSummary(saleReportParams,
				companyCode, idOperation);

		SummaryInstrumentSaleDto summaryInstrumentSaleCheck = generatePaymentCheckSummary(saleReportParams, companyCode,
				idOperation);

		SummaryInstrumentSaleDto summaryIstrumentCredit = generatePaymentCreditSummary(saleReportParams, companyCode,
				idOperation);

		List<SummaryInstrumentSaleDto> summaryInstrumentSaleList = new ArrayList<>();
		if (summaryInstrumentSaleCash.getSummaryDetail().size() > 0)
			summaryInstrumentSaleList.add(summaryInstrumentSaleCash);
		if (summaryInstrumentSaleCreditCard.getSummaryDetail().size() > 0)
			summaryInstrumentSaleList.add(summaryInstrumentSaleCreditCard);
		if (summaryInstrumentSaleCreditNote.getSummaryDetail().size() > 0)
			summaryInstrumentSaleList.add(summaryInstrumentSaleCreditNote);
		if (summaryInstrumentSaleTransfer.getSummaryDetail().size() > 0)
			summaryInstrumentSaleList.add(summaryInstrumentSaleTransfer);
		if (summaryInstrumentSaleCheck.getSummaryDetail().size() > 0)
			summaryInstrumentSaleList.add(summaryInstrumentSaleCheck);
		if (summaryIstrumentCredit.getSummaryDetail().size() > 0)
			summaryInstrumentSaleList.add(summaryIstrumentCredit);

		BranchSaleSummaryDto branchSaleReport = new BranchSaleSummaryDto(branch, summaryInstrumentSaleList);
		return branchSaleReport;
	}

	/**
	 * Método que genera los datos operativos con el resumen de las ventas con
	 * crédito por sucursal
	 * 
	 * @param saleReportParams
	 * @param companyCode
	 * @param idOperation
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private SummaryInstrumentSaleDto generatePaymentCreditSummary(GenericSearchSaleReportParamsDto saleReportParams,
			String companyCode, String idOperation) {

		ResponseModel responseSaleCreditSummary = saleReportPersistencePort
				.searchPaymentCreditSummaryByCompanyCodeAndParams(saleReportParams, companyCode, idOperation);
		List<SummarySaleCreditPaymentDto> summarySaleCreditPaymentList = (List<SummarySaleCreditPaymentDto>) responseSaleCreditSummary
				.getData();

		List<SummarySaleBranchDto> summarySaleBranchByCashList = new ArrayList<>();
		for (SummarySaleCreditPaymentDto summarySaleCreditPaymentDto : summarySaleCreditPaymentList) {
			summarySaleBranchByCashList.add(new SummarySaleBranchDto(summarySaleCreditPaymentDto));
		}

		SummaryInstrumentSaleDto summaryInstrumentSale = new SummaryInstrumentSaleDto(CREDIT,
				summarySaleBranchByCashList);
		return summaryInstrumentSale;
	}

	/**
	 * Método que genera los datos operativos con el resumen de las ventas en
	 * efectivo por sucursa
	 * 
	 * @param saleReportParams
	 * @param companyCode
	 * @param idOperation
	 * @return SummaryInstrumentSaleDto
	 */
	@SuppressWarnings("unchecked")
	private SummaryInstrumentSaleDto generatePaymentCashSummary(GenericSearchSaleReportParamsDto saleReportParams,
			String companyCode, String idOperation) {

		ResponseModel responseSaleCashSummary = saleReportPersistencePort
				.searchPaymentCashSummaryByCompanyCodeAndParams(saleReportParams, companyCode, idOperation);
		List<SummarySaleCashPaymentDto> summaryCashPaymentList = (List<SummarySaleCashPaymentDto>) responseSaleCashSummary
				.getData();

		List<SummarySaleBranchDto> summarySaleBranchByCashList = new ArrayList<>();
		for (SummarySaleCashPaymentDto summarySaleCashPayment : summaryCashPaymentList) {
			summarySaleBranchByCashList.add(new SummarySaleBranchDto(summarySaleCashPayment));
		}

		SummaryInstrumentSaleDto summaryInstrumentSale = new SummaryInstrumentSaleDto(EFECTIVO,
				summarySaleBranchByCashList);
		return summaryInstrumentSale;
	}

	/**
	 * Método que genera los datos operativos con el resumen de las ventas de
	 * tarjetas de crédito por sucursa
	 * 
	 * @param saleReportParams
	 * @param companyCode
	 * @param idOperation
	 * @return SummaryInstrumentSaleDto
	 */
	@SuppressWarnings("unchecked")
	private SummaryInstrumentSaleDto generatePaymentCreditCardSummary(GenericSearchSaleReportParamsDto saleReportParams,
			String companyCode, String idOperation) {

		ResponseModel responseSaleCreditCardSummary = saleReportPersistencePort
				.searchPaymentCreditCardSummaryByCompanyCodeAndParams(saleReportParams, companyCode, idOperation);

		List<SummarySaleCreditCardPaymentDto> summaryCreditCardPaymentList = (List<SummarySaleCreditCardPaymentDto>) responseSaleCreditCardSummary
				.getData();

		List<SummarySaleBranchDto> summarySaleBranchByCashList = new ArrayList<>();
		for (SummarySaleCreditCardPaymentDto summarySaleCreditCardPayment : summaryCreditCardPaymentList) {
			summarySaleBranchByCashList.add(new SummarySaleBranchDto(summarySaleCreditCardPayment));
		}

		SummaryInstrumentSaleDto summaryInstrumentSale = new SummaryInstrumentSaleDto(TARJETAS,
				summarySaleBranchByCashList);
		return summaryInstrumentSale;
	}

	/**
	 * Método que genera los datos operativos con el resumen de las ventas de notas
	 * de crédito por sucursa
	 * 
	 * @param saleReportParams
	 * @param companyCode
	 * @param idOperation
	 * @return SummaryInstrumentSaleDto
	 */
	@SuppressWarnings("unchecked")
	private SummaryInstrumentSaleDto generatePaymentCreditNoteSummary(GenericSearchSaleReportParamsDto saleReportParams,
			String companyCode, String idOperation) {

		ResponseModel responseSaleCreditNoteSummary = saleReportPersistencePort
				.searchPaymentCreditNoteSummaryByCompanyCodeAndParams(saleReportParams, companyCode, idOperation);

		List<SummarySaleCreditNotePaymentDto> summaryCreditNotePaymentList = (List<SummarySaleCreditNotePaymentDto>) responseSaleCreditNoteSummary
				.getData();

		List<SummarySaleBranchDto> summarySaleBranchByCashList = new ArrayList<>();
		for (SummarySaleCreditNotePaymentDto summarySaleCreditNotePayment : summaryCreditNotePaymentList) {
			summarySaleBranchByCashList.add(new SummarySaleBranchDto(summarySaleCreditNotePayment));
		}

		SummaryInstrumentSaleDto summaryInstrumentSale = new SummaryInstrumentSaleDto(NOTAS_DE_CREDITO,
				summarySaleBranchByCashList);
		return summaryInstrumentSale;
	}

	/**
	 * Método que genera los datos operativos con el resumen de las ventas con
	 * transferencias por sucursa
	 * 
	 * @param saleReportParams
	 * @param companyCode
	 * @param idOperation
	 * @return SummaryInstrumentSaleDto
	 */
	@SuppressWarnings("unchecked")
	private SummaryInstrumentSaleDto generatePaymentTransferSummary(GenericSearchSaleReportParamsDto saleReportParams,
			String companyCode, String idOperation) {

		ResponseModel responseSaleTransferSummary = saleReportPersistencePort
				.searchPaymentTransferSummaryByCompanyCodeAndParams(saleReportParams, companyCode, idOperation);

		List<SummarySaleTransferPaymentDto> summaryTransferPaymentList = (List<SummarySaleTransferPaymentDto>) responseSaleTransferSummary
				.getData();

		List<SummarySaleBranchDto> summarySaleBranchByCashList = new ArrayList<>();
		for (SummarySaleTransferPaymentDto summarySaleTransferPayment : summaryTransferPaymentList) {
			summarySaleBranchByCashList.add(new SummarySaleBranchDto(summarySaleTransferPayment));
		}

		SummaryInstrumentSaleDto summaryInstrumentSale = new SummaryInstrumentSaleDto(TRANSFERENCIA,
				summarySaleBranchByCashList);
		return summaryInstrumentSale;
	}

	/**
	 * Método que genera los datos operativos con el resumen de las ventas con
	 * cheque por sucursa
	 * 
	 * @param saleReportParams
	 * @param companyCode
	 * @param idOperation
	 * @return SummaryInstrumentSaleDto
	 */
	@SuppressWarnings("unchecked")
	private SummaryInstrumentSaleDto generatePaymentCheckSummary(GenericSearchSaleReportParamsDto saleReportParams,
			String companyCode, String idOperation) {

		ResponseModel responseSaleCheckSummary = saleReportPersistencePort
				.searchPaymentCheckSummaryByCompanyCodeAndParams(saleReportParams, companyCode, idOperation);

		List<SummarySaleCheckPaymentDto> summaryCheckPaymentList = (List<SummarySaleCheckPaymentDto>) responseSaleCheckSummary
				.getData();

		List<SummarySaleBranchDto> summarySaleBranchByCashList = new ArrayList<>();
		for (SummarySaleCheckPaymentDto summarySaleCheckPayment : summaryCheckPaymentList) {
			summarySaleBranchByCashList.add(new SummarySaleBranchDto(summarySaleCheckPayment));
		}

		SummaryInstrumentSaleDto summaryInstrumentSale = new SummaryInstrumentSaleDto(CHEQUE,
				summarySaleBranchByCashList);
		return summaryInstrumentSale;
	}

	/**
	 * Método que evalua el rol operativa para la asinganación de valores de
	 * busqueda en rango de fechas y parámetros por defecto. Solo el rol operativo
	 * OPERATIONAL estará limitado a los valores que asigne el sistema
	 * 
	 * @param saleReportParams
	 * @param employeeDto
	 * @return
	 */
	private GenericSearchSaleReportParamsDto generateSearchParamsByRoleJob(
			GenericSearchSaleReportParamsDto saleReportParams, BranchDto branch) {

		saleReportParams.setBranchCode(branch.getCode());

		if (saleReportParams.getStartDate() == null)
			saleReportParams.setStartDate(getDefautlStartDate());

		if (saleReportParams.getEndDate() == null)
			saleReportParams.setEndDate(getDefaultEndDate());

		return saleReportParams;
	}

	/***
	 * Método que retorna la fecha de inicio para la busqueda de datos, se toma el
	 * día primero del año en curso
	 * 
	 * @return
	 */
	private Date getDefautlStartDate() {
		Date from = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.set(Calendar.MONTH, 0);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		from = cal.getTime();
		return from;
	}

	/**
	 * Método que retorna la fecha de fin para la busqueda de datos, se toma el día
	 * actual en que se genera el reporte
	 * 
	 * @return
	 */
	@SuppressWarnings("deprecation")
	private Date getDefaultEndDate() {
		Date to = new Date();
		Calendar calTo = Calendar.getInstance();
		calTo.setTime(new Date());
		calTo.set(Calendar.MONTH, to.getMonth());
		calTo.set(Calendar.HOUR_OF_DAY, 23);
		calTo.set(Calendar.MINUTE, 59);
		calTo.set(Calendar.SECOND, 59);
		calTo.set(Calendar.MILLISECOND, 59);
		to = calTo.getTime();
		return to;
	}

}
