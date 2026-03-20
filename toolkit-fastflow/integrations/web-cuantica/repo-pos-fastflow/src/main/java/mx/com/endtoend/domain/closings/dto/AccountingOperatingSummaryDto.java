package mx.com.endtoend.domain.closings.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import mx.com.smartbussiness.generic.utils.DecimalPrecisionUtils;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

import mx.com.endtoend.domain.accountingRecord.dto.AccountingRecordDto;
import mx.com.endtoend.domain.commons.constants.AccountingConceptEnum;
import mx.com.endtoend.domain.commons.constants.ClosingIncomeEnum;
import mx.com.endtoend.domain.commons.constants.MovementPaymentTypeEnum;

public class AccountingOperatingSummaryDto {

	@NotNull(message = "El resumen de efectivo es obligatorio")
	@DecimalMin(value = "0.0", message = "El resumen de efectivo debe ser mayor o igual a 0")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private BigDecimal summaryCash;

	@NotNull(message = "El resumen de crédito es obligatorio")
	@DecimalMin(value = "0.0", message = "El resumen de crédito debe ser mayor o igual a 0")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private BigDecimal summaryCredit;

	@NotNull(message = "El resumen de nota de crédito es obligatorio")
	@DecimalMin(value = "0.0", message = "El resumen de nota de crédito debe ser mayor o igual a 0")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private BigDecimal summaryCreditNote;

	@NotNull(message = "El resumen de tarjeta de crédito es obligatorio")
	@DecimalMin(value = "0.0", message = "El resumen de tarjeta de crédito debe ser mayor o igual a 0")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private BigDecimal summaryCreditCard;

	@NotNull(message = "El resumen de cheque es obligatorio")
	@DecimalMin(value = "0.0", message = "El resumen de cheque debe ser mayor o igual a 0")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private BigDecimal summaryCheck;

	@NotNull(message = "El resumen de transferencia es obligatorio")
	@DecimalMin(value = "0.0", message = "El resumen de transferencia debe ser mayor o igual a 0")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private BigDecimal summaryTransfer;

	public BigDecimal getSummaryCash() {
		return summaryCash;
	}

	public BigDecimal getSummaryCredit() {
		return summaryCredit;
	}

	public BigDecimal getSummaryCreditNote() {
		return summaryCreditNote;
	}

	public BigDecimal getSummaryCreditCard() {
		return summaryCreditCard;
	}

	public BigDecimal getSummaryCheck() {
		return summaryCheck;
	}

	public BigDecimal getSummaryTransfer() {
		return summaryTransfer;
	}

	public void setSummaryCash(BigDecimal summaryCash) {
		this.summaryCash = DecimalPrecisionUtils.roundToTwoDecimals(summaryCash);
	}

	public void setSummaryCredit(BigDecimal summaryCredit) {
		this.summaryCredit = DecimalPrecisionUtils.roundToTwoDecimals(summaryCredit);
	}

	public void setSummaryCreditNote(BigDecimal summaryCreditNote) {
		this.summaryCreditNote = DecimalPrecisionUtils.roundToTwoDecimals(summaryCreditNote);
	}

	public void setSummaryCreditCard(BigDecimal summaryCreditCard) {
		this.summaryCreditCard = DecimalPrecisionUtils.roundToTwoDecimals(summaryCreditCard);
	}

	public void setSummaryCheck(BigDecimal summaryCheck) {
		this.summaryCheck = DecimalPrecisionUtils.roundToTwoDecimals(summaryCheck);
	}

	public void setSummaryTransfer(BigDecimal summaryTransfer) {
		this.summaryTransfer = DecimalPrecisionUtils.roundToTwoDecimals(summaryTransfer);
	}

	@Override
	public String toString() {
		return "AccountingOperatingSummaryDto [summaryCash=" + summaryCash + ", summaryCredit=" + summaryCredit
				+ ", summaryCreditNote=" + summaryCreditNote + ", summaryCreditCard=" + summaryCreditCard
				+ ", summaryCheck=" + summaryCheck + ", summaryTransfer=" + summaryTransfer + "]";
	}

	public AccountingOperatingSummaryDto() {
		super();
	}

	public AccountingOperatingSummaryDto(BigDecimal summaryCash, BigDecimal summaryCredit, BigDecimal summaryCreditNote,
			BigDecimal summaryCreditCard, BigDecimal summaryCheck, BigDecimal summaryTransfer) {
		super();
		this.summaryCash = DecimalPrecisionUtils.roundToTwoDecimals(summaryCash);
		this.summaryCredit = DecimalPrecisionUtils.roundToTwoDecimals(summaryCredit);
		this.summaryCreditNote = DecimalPrecisionUtils.roundToTwoDecimals(summaryCreditNote);
		this.summaryCreditCard = DecimalPrecisionUtils.roundToTwoDecimals(summaryCreditCard);
		this.summaryCheck = DecimalPrecisionUtils.roundToTwoDecimals(summaryCheck);
		this.summaryTransfer = DecimalPrecisionUtils.roundToTwoDecimals(summaryTransfer);
	}

	public AccountingOperatingSummaryDto(List<AccountingRecordDto> accountingRecordList) {
		super();
		this.summaryCash = DecimalPrecisionUtils.roundToTwoDecimals(getTotalSummaryCash(accountingRecordList));
		this.summaryCredit = DecimalPrecisionUtils.roundToTwoDecimals(getTotalSummaryCredit(accountingRecordList));
		this.summaryCreditNote = DecimalPrecisionUtils.roundToTwoDecimals(getTotalSummaryCreditNote(accountingRecordList));
		this.summaryCreditCard = DecimalPrecisionUtils.roundToTwoDecimals(getTotalSummaryCreditCard(accountingRecordList));
		this.summaryCheck = DecimalPrecisionUtils.roundToTwoDecimals(getTotalSummaryCheck(accountingRecordList));
		this.summaryTransfer = DecimalPrecisionUtils.roundToTwoDecimals(getTotalSummaryTransfer(accountingRecordList));
	}

	public BigDecimal getTotalIncomeTotalByMovementPayment(String movementType,
			List<AccountingRecordDto> accountingRecordList) {
		BigDecimal summary = BigDecimal.ZERO;
		for (AccountingRecordDto accountingRecordDto : accountingRecordList) {
			if (accountingRecordDto.getAccountingConcept().equals(AccountingConceptEnum.INCOME.toString())
					&& accountingRecordDto.getMovementType().equals(movementType)) {
				summary = summary.add(accountingRecordDto.getAmountApplied());
			}
		}
		return DecimalPrecisionUtils.roundToTwoDecimals(summary);
	}

	private BigDecimal getTotalSummaryCash(List<AccountingRecordDto> accountingRecordList) {
		BigDecimal summary = BigDecimal.ZERO;
		for (AccountingRecordDto accountingRecordDto : accountingRecordList) {

			if (accountingRecordDto.getAccountingConcept().equals(AccountingConceptEnum.INCOME.toString())
					&& accountingRecordDto.getMovementType().equals(MovementPaymentTypeEnum.CASH.toString())) {
				summary = summary.add(accountingRecordDto.getAmountApplied());
			}
			if (accountingRecordDto.getAccountingConcept().equals(AccountingConceptEnum.EXPENSE.toString())
					&& accountingRecordDto.getMovementType().equals(MovementPaymentTypeEnum.CASH.toString())) {
				summary = summary.subtract(accountingRecordDto.getAmountApplied());
			}
		}
		return DecimalPrecisionUtils.roundToTwoDecimals(summary);
	}

	private BigDecimal getTotalSummaryCredit(List<AccountingRecordDto> accountingRecordList) {
		BigDecimal summary = BigDecimal.ZERO;
		for (AccountingRecordDto accountingRecordDto : accountingRecordList) {

			if (accountingRecordDto.getAccountingConcept().equals(AccountingConceptEnum.INCOME.toString())
					&& accountingRecordDto.getMovementType().equals(MovementPaymentTypeEnum.CREDIT.toString())) {
				summary = summary.add(accountingRecordDto.getAmountApplied());
			}
			if (accountingRecordDto.getAccountingConcept().equals(AccountingConceptEnum.EXPENSE.toString())
					&& accountingRecordDto.getMovementType().equals(MovementPaymentTypeEnum.CREDIT.toString())) {
				summary = summary.subtract(accountingRecordDto.getAmountApplied());
			}
		}
		return DecimalPrecisionUtils.roundToTwoDecimals(summary);
	}

	private BigDecimal getTotalSummaryCreditNote(List<AccountingRecordDto> accountingRecordList) {
		BigDecimal summary = BigDecimal.ZERO;
		for (AccountingRecordDto accountingRecordDto : accountingRecordList) {

			if (accountingRecordDto.getAccountingConcept().equals(AccountingConceptEnum.INCOME.toString())
					&& accountingRecordDto.getMovementType().equals(MovementPaymentTypeEnum.CREDIT_NOTE.toString())) {
				summary = summary.add(accountingRecordDto.getAmountApplied());
			}
			if (accountingRecordDto.getAccountingConcept().equals(AccountingConceptEnum.EXPENSE.toString())
					&& accountingRecordDto.getMovementType().equals(MovementPaymentTypeEnum.CREDIT_NOTE.toString())) {
				summary = summary.subtract(accountingRecordDto.getAmountApplied());
			}
		}
		return DecimalPrecisionUtils.roundToTwoDecimals(summary);
	}

	private BigDecimal getTotalSummaryCreditCard(List<AccountingRecordDto> accountingRecordList) {
		BigDecimal summary = BigDecimal.ZERO;
		for (AccountingRecordDto accountingRecordDto : accountingRecordList) {

			if (accountingRecordDto.getAccountingConcept().equals(AccountingConceptEnum.INCOME.toString())
					&& accountingRecordDto.getMovementType().equals(MovementPaymentTypeEnum.CREDIT_CARD.toString())) {
				summary = summary.add(accountingRecordDto.getAmountApplied());
			}
			if (accountingRecordDto.getAccountingConcept().equals(AccountingConceptEnum.EXPENSE.toString())
					&& accountingRecordDto.getMovementType().equals(MovementPaymentTypeEnum.CREDIT_CARD.toString())) {
				summary = summary.subtract(accountingRecordDto.getAmountApplied());
			}
		}
		return DecimalPrecisionUtils.roundToTwoDecimals(summary);
	}

	private BigDecimal getTotalSummaryCheck(List<AccountingRecordDto> accountingRecordList) {
		BigDecimal summary = BigDecimal.ZERO;
		for (AccountingRecordDto accountingRecordDto : accountingRecordList) {

			if (accountingRecordDto.getAccountingConcept().equals(AccountingConceptEnum.INCOME.toString())
					&& accountingRecordDto.getMovementType().equals(MovementPaymentTypeEnum.CHECK.toString())) {
				summary = summary.add(accountingRecordDto.getAmountApplied());
			}
			if (accountingRecordDto.getAccountingConcept().equals(AccountingConceptEnum.EXPENSE.toString())
					&& accountingRecordDto.getMovementType().equals(MovementPaymentTypeEnum.CHECK.toString())) {
				summary = summary.subtract(accountingRecordDto.getAmountApplied());
			}
		}
		return DecimalPrecisionUtils.roundToTwoDecimals(summary);
	}

	private BigDecimal getTotalSummaryTransfer(List<AccountingRecordDto> accountingRecordList) {
		BigDecimal summary = BigDecimal.ZERO;
		for (AccountingRecordDto accountingRecordDto : accountingRecordList) {

			if (accountingRecordDto.getAccountingConcept().equals(AccountingConceptEnum.INCOME.toString())
					&& accountingRecordDto.getMovementType().equals(MovementPaymentTypeEnum.TRANSFER.toString())) {
				summary = summary.add(accountingRecordDto.getAmountApplied());
			}
			if (accountingRecordDto.getAccountingConcept().equals(AccountingConceptEnum.EXPENSE.toString())
					&& accountingRecordDto.getMovementType().equals(MovementPaymentTypeEnum.TRANSFER.toString())) {
				summary = summary.subtract(accountingRecordDto.getAmountApplied());
			}
		}
		return DecimalPrecisionUtils.roundToTwoDecimals(summary);
	}

	public static AccountingOperatingSummaryDto generateByClosingOperation(
			List<ClosingOperationDetailDto> closingOperationDetailList) {

		AccountingOperatingSummaryDto accountingOperatingSummaryDto = new AccountingOperatingSummaryDto();

		accountingOperatingSummaryDto.setSummaryCash(getTotalSummaryCashByClosingOperation(closingOperationDetailList));
		accountingOperatingSummaryDto
				.setSummaryCredit(getTotalSummaryCreditByClosingOperation(closingOperationDetailList));
		accountingOperatingSummaryDto
				.setSummaryCreditNote(getTotalSummaryCreditNoteByClosingOperation(closingOperationDetailList));
		accountingOperatingSummaryDto
				.setSummaryCreditCard(getTotalSummaryCreditCardByClosingOperation(closingOperationDetailList));
		accountingOperatingSummaryDto
				.setSummaryCheck(getTotalSummaryCheckByClosingOperation(closingOperationDetailList));
		accountingOperatingSummaryDto
				.setSummaryTransfer(getTotalSummaryTransferByClosingOperation(closingOperationDetailList));

		return accountingOperatingSummaryDto;
	}

	private static BigDecimal getTotalSummaryCashByClosingOperation(
			List<ClosingOperationDetailDto> closingOperationDetailList) {
		BigDecimal summary = BigDecimal.ZERO;
		for (ClosingOperationDetailDto closingOperationDetail : closingOperationDetailList) {
			if (closingOperationDetail.getClosePaymentInstrument().getIncomeType()
					.equals(ClosingIncomeEnum.CASH.toString())) {
				summary = summary.add(closingOperationDetail.getAmount());
			}
		}
		return DecimalPrecisionUtils.roundToTwoDecimals(summary);
	}

	private static BigDecimal getTotalSummaryCreditByClosingOperation(
			List<ClosingOperationDetailDto> closingOperationDetailList) {
		BigDecimal summary = BigDecimal.ZERO;
		for (ClosingOperationDetailDto closingOperationDetail : closingOperationDetailList) {
			if (closingOperationDetail.getClosePaymentInstrument().getIncomeType()
					.equals(ClosingIncomeEnum.CREDIT.toString())) {
				summary = summary.add(closingOperationDetail.getAmount());
			}
		}
		return DecimalPrecisionUtils.roundToTwoDecimals(summary);
	}

	private static BigDecimal getTotalSummaryCreditNoteByClosingOperation(
			List<ClosingOperationDetailDto> closingOperationDetailList) {
		BigDecimal summary = BigDecimal.ZERO;
		for (ClosingOperationDetailDto closingOperationDetail : closingOperationDetailList) {
			if (closingOperationDetail.getClosePaymentInstrument().getIncomeType()
					.equals(ClosingIncomeEnum.CREDIT_NOTE.toString())) {
				summary = summary.add(closingOperationDetail.getAmount());
			}
		}
		return DecimalPrecisionUtils.roundToTwoDecimals(summary);
	}

	private static BigDecimal getTotalSummaryCreditCardByClosingOperation(
			List<ClosingOperationDetailDto> closingOperationDetailList) {
		BigDecimal summary = BigDecimal.ZERO;
		for (ClosingOperationDetailDto closingOperationDetail : closingOperationDetailList) {
			if (closingOperationDetail.getClosePaymentInstrument().getIncomeType()
					.equals(ClosingIncomeEnum.CREDIT_CARD.toString())) {
				summary = summary.add(closingOperationDetail.getAmount());
			}
		}
		return DecimalPrecisionUtils.roundToTwoDecimals(summary);
	}

	private static BigDecimal getTotalSummaryCheckByClosingOperation(
			List<ClosingOperationDetailDto> closingOperationDetailList) {
		BigDecimal summary = BigDecimal.ZERO;
		for (ClosingOperationDetailDto closingOperationDetail : closingOperationDetailList) {
			if (closingOperationDetail.getClosePaymentInstrument().getIncomeType()
					.equals(ClosingIncomeEnum.CHECK.toString())) {
				summary = summary.add(closingOperationDetail.getAmount());
			}
		}
		return DecimalPrecisionUtils.roundToTwoDecimals(summary);
	}

	private static BigDecimal getTotalSummaryTransferByClosingOperation(
			List<ClosingOperationDetailDto> closingOperationDetailList) {
		BigDecimal summary = BigDecimal.ZERO;
		for (ClosingOperationDetailDto closingOperationDetail : closingOperationDetailList) {
			if (closingOperationDetail.getClosePaymentInstrument().getIncomeType()
					.equals(ClosingIncomeEnum.TRANSFER.toString())) {
				summary = summary.add(closingOperationDetail.getAmount());
			}
		}
		return DecimalPrecisionUtils.roundToTwoDecimals(summary);
	}

}
