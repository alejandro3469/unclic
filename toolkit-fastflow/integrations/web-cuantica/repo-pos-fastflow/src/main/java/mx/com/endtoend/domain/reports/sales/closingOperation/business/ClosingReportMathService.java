package mx.com.endtoend.domain.reports.sales.closingOperation.business;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import mx.com.endtoend.domain.commons.constants.MovementPaymentTypeEnum;
import mx.com.endtoend.domain.reports.sales.closingOperation.dto.AccountingRecordReported;
import mx.com.endtoend.domain.reports.sales.closingOperation.dto.AccountingTicketRecord;
import mx.com.endtoend.domain.reports.sales.closingOperation.dto.ClosingOperationReported;
import mx.com.endtoend.domain.reports.sales.closingOperation.dto.ClosingOperationSummarySeach;
import mx.com.endtoend.domain.reports.sales.closingOperation.dto.report.BranchClosingDetail;
import mx.com.endtoend.domain.reports.sales.closingOperation.dto.report.GeneralClosingDetail;

/**
 * Clase encargada de realizar las oepraciones matemáticas para genración de
 * datos en los reportes de cierre de operaiones
 * 
 * @author ddcasas
 */
public class ClosingReportMathService {

	/**
	 * Método que genera la lista de los datos de ciere contable global por
	 * instrumento de cobro registrado en el sistema
	 * 
	 * @param closingOperationReportedList
	 * @param accountingTicketRecordList
	 * @return
	 */
	public List<GeneralClosingDetail> generateGlobalOperativeData(
			List<ClosingOperationReported> closingOperationReportedList,
			List<AccountingTicketRecord> accountingTicketRecordList) {

		List<GeneralClosingDetail> generalClosingDetail = new ArrayList<>();

		GeneralClosingDetail generalClosingDetailCash = generateGlobalClosingDetail(closingOperationReportedList,
				accountingTicketRecordList, MovementPaymentTypeEnum.CASH.toString());
		if (generalClosingDetailCash != null) {
			generalClosingDetail.add(generalClosingDetailCash);
		}

		GeneralClosingDetail generalClosingDetailCreditCard = generateGlobalClosingDetail(closingOperationReportedList,
				accountingTicketRecordList, MovementPaymentTypeEnum.CREDIT_CARD.toString());
		if (generalClosingDetailCreditCard != null) {
			generalClosingDetail.add(generalClosingDetailCreditCard);
		}

		GeneralClosingDetail generalClosingDetailCheck = generateGlobalClosingDetail(closingOperationReportedList,
				accountingTicketRecordList, MovementPaymentTypeEnum.CHECK.toString());
		if (generalClosingDetailCheck != null) {
			generalClosingDetail.add(generalClosingDetailCheck);
		}
		GeneralClosingDetail generalClosingDetailCredit = generateGlobalClosingDetail(closingOperationReportedList,
				accountingTicketRecordList, MovementPaymentTypeEnum.CREDIT.toString());
		if (generalClosingDetailCredit != null) {
			generalClosingDetail.add(generalClosingDetailCredit);
		}
		GeneralClosingDetail generalClosingDetailTransfer = generateGlobalClosingDetail(closingOperationReportedList,
				accountingTicketRecordList, MovementPaymentTypeEnum.TRANSFER.toString());
		if (generalClosingDetailTransfer != null) {
			generalClosingDetail.add(generalClosingDetailTransfer);
		}
		GeneralClosingDetail generalClosingDetailCreditNote = generateGlobalClosingDetail(closingOperationReportedList,
				accountingTicketRecordList, MovementPaymentTypeEnum.CREDIT_NOTE.toString());
		if (generalClosingDetailCreditNote != null) {
			generalClosingDetail.add(generalClosingDetailCreditNote);
		}
		return generalClosingDetail;
	}

	private GeneralClosingDetail generateGlobalClosingDetail(
			List<ClosingOperationReported> closingOperationReportedList,
			List<AccountingTicketRecord> accountingTicketRecordList, String paymentMethod) {

		String movementType = getMovementDescription(paymentMethod);
		Long ticketTotal = 0L;
		BigDecimal amountTotal = BigDecimal.ZERO;
		boolean existsMethod = false;

		for (AccountingTicketRecord accountingTicketRecord : accountingTicketRecordList) {
			if (accountingTicketRecord.getMovementType().equalsIgnoreCase(paymentMethod)) {
				ticketTotal++;
				existsMethod = true;
			}
		}

		for (ClosingOperationReported closingOperationReported : closingOperationReportedList) {
			if (closingOperationReported.getMovementType().equalsIgnoreCase(paymentMethod)) {
				amountTotal = amountTotal.add(closingOperationReported.getAmountReported());
				existsMethod = true;
			}
		}

		GeneralClosingDetail globalClosingDetail = new GeneralClosingDetail(movementType, ticketTotal, amountTotal);

		return existsMethod ? globalClosingDetail : null;

	}

	private String getMovementDescription(String movementType) {
		MovementPaymentTypeEnum key = MovementPaymentTypeEnum.valueOf(movementType);
		switch (key) {
		case CASH:
			return "EFECTIVO";
		case CHECK:
			return "CHEQUE";
		case CREDIT:
			return "CRÉDITO";
		case CREDIT_CARD:
			return "TARJETA DE CRÉDITO";
		case CREDIT_NOTE:
			return "NOTA DE CRÉDITO";
		case TRANSFER:
			return "TRANSFERENCIA";
		default:
			return "---";
		}
	}

	/**
	 * Método que genera la lista de los datos de cierre contable por cada
	 * instrumento de cobro en el sistema
	 * 
	 * @param closingOperationSummarySeach
	 * @param closingOperationReportedList
	 * @param accountingRecordReportedList
	 * @return
	 */
	public List<BranchClosingDetail> generateOperativeData(ClosingOperationSummarySeach closingOperationSummarySeach,
			List<ClosingOperationReported> closingOperationReportedList,
			List<AccountingRecordReported> accountingRecordReportedList) {

		List<BranchClosingDetail> globalBranchClosingDetail = new ArrayList<>();

		BranchClosingDetail branchClosingDetailCash = generateBranchClosingDetail(closingOperationSummarySeach,
				closingOperationReportedList, accountingRecordReportedList, MovementPaymentTypeEnum.CASH.toString());
		if (branchClosingDetailCash != null) {
			globalBranchClosingDetail.add(branchClosingDetailCash);
		}

		BranchClosingDetail branchClosingDetailCreditNote = generateBranchClosingDetail(closingOperationSummarySeach,
				closingOperationReportedList, accountingRecordReportedList,
				MovementPaymentTypeEnum.CREDIT_NOTE.toString());
		if (branchClosingDetailCreditNote != null) {
			globalBranchClosingDetail.add(branchClosingDetailCreditNote);
		}

		BranchClosingDetail branchClosingDetailCreditCard = generateBranchClosingDetail(closingOperationSummarySeach,
				closingOperationReportedList, accountingRecordReportedList,
				MovementPaymentTypeEnum.CREDIT_CARD.toString());
		if (branchClosingDetailCreditCard != null) {
			globalBranchClosingDetail.add(branchClosingDetailCreditCard);
		}

		BranchClosingDetail branchClosingDetailTransfer = generateBranchClosingDetail(closingOperationSummarySeach,
				closingOperationReportedList, accountingRecordReportedList,
				MovementPaymentTypeEnum.TRANSFER.toString());
		if (branchClosingDetailTransfer != null) {
			globalBranchClosingDetail.add(branchClosingDetailTransfer);
		}

		BranchClosingDetail branchClosingDetailCheck = generateBranchClosingDetail(closingOperationSummarySeach,
				closingOperationReportedList, accountingRecordReportedList, MovementPaymentTypeEnum.CHECK.toString());
		if (branchClosingDetailCheck != null) {
			globalBranchClosingDetail.add(branchClosingDetailCheck);
		}
		
		BranchClosingDetail branchClosingDetailCredit = generateBranchClosingDetail(closingOperationSummarySeach,
				closingOperationReportedList, accountingRecordReportedList, MovementPaymentTypeEnum.CREDIT.toString());
		if (branchClosingDetailCredit != null) {
			globalBranchClosingDetail.add(branchClosingDetailCredit);
		}

		return globalBranchClosingDetail;
	}

	/**
	 * Método que genera los datos operativos del cierre contable y teórico por
	 * metodo de cobro registrado en el sistema
	 * 
	 * @param closingOperationSummarySeach
	 * @param closingOperationReportedList
	 * @param accountingRecordReportedList
	 * @param paymentMethod
	 * @return
	 */
	public BranchClosingDetail generateBranchClosingDetail(ClosingOperationSummarySeach closingOperationSummarySeach,
			List<ClosingOperationReported> closingOperationReportedList,
			List<AccountingRecordReported> accountingRecordReportedList, String paymentMethod) {

		BigDecimal sumTheoreticalAmount = BigDecimal.ZERO;
		BigDecimal sumClosingAmount = BigDecimal.ZERO;
		boolean existeMethod = false;

		for (AccountingRecordReported accountingRecordReported : accountingRecordReportedList) {
			if (accountingRecordReported.getMovementType().equalsIgnoreCase(paymentMethod)) {
				sumTheoreticalAmount = sumTheoreticalAmount.add(accountingRecordReported.getAmountReported());
				existeMethod = true;
			}
		}

		for (ClosingOperationReported closingOperationReported : closingOperationReportedList) {
			if (closingOperationReported.getMovementType().equalsIgnoreCase(paymentMethod)) {
				sumClosingAmount = sumClosingAmount.add(closingOperationReported.getAmountReported());
				existeMethod = true;
			}
		}

		BranchClosingDetail branchClosingDetail = new BranchClosingDetail("MNX",
				closingOperationSummarySeach.getClosingDate(), paymentMethod, sumTheoreticalAmount, sumClosingAmount);

		return !existeMethod ? null : branchClosingDetail;
	}
}
