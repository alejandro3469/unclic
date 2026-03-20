package mx.com.endtoend.domain.accountingRecord.services;

import java.util.ArrayList;
import java.util.List;

import mx.com.endtoend.domain.accountingRecord.dto.AccountingRecordDto;
import mx.com.endtoend.domain.closings.dto.ClosingOperationDetailDto;
import mx.com.endtoend.domain.closings.dto.ClosingOperationDto;
import mx.com.endtoend.domain.commons.constants.AccountingConceptEnum;
import mx.com.endtoend.domain.commons.constants.MovementConceptEnum;
import mx.com.endtoend.domain.commons.constants.MovementPaymentTypeEnum;
import mx.com.endtoend.domain.openings.dto.OpeningOperationDetailDto;
import mx.com.endtoend.domain.openings.dto.OpeningOperationDto;
import mx.com.endtoend.smart.bussiness.model.payments.CheckPaymentDto;
import mx.com.endtoend.smart.bussiness.model.payments.CreditCardPaymentDto;
import mx.com.endtoend.smart.bussiness.model.payments.CreditNotePaymentDto;
import mx.com.endtoend.smart.bussiness.model.payments.CreditPaymentDto;
import mx.com.endtoend.smart.bussiness.model.payments.PaymentCashDto;
import mx.com.endtoend.smart.bussiness.model.payments.PaymentDto;
import mx.com.endtoend.smart.bussiness.model.payments.TransferPaymentDto;

/**
 * Clase para generar los registros contables con base en el tipo de operación
 * del sistema
 * 
 * @author ddcasas
 *
 */
public class GenerateAccountingRecordService {

	/**
	 * Método para la generación de registros contables a partir de un cierre de
	 * operación
	 * 
	 * @param closingOperationDto
	 * @param employeeId
	 * @param openingId
	 * @return List<AccountingRecordDto>
	 */
	public List<AccountingRecordDto> generateByClosingOperation(ClosingOperationDto closingOperationDto,
			Long employeeId, Long openingId) {
		List<AccountingRecordDto> accountingRecordList = new ArrayList<>();
		for (ClosingOperationDetailDto closingOperationDetail : closingOperationDto.getClosingOperationDetail()) {
			accountingRecordList.add(new AccountingRecordDto(employeeId, closingOperationDto.getBranchCode(), openingId,
					closingOperationDto.getClosingId(), AccountingConceptEnum.EXPENSE.toString(),
					closingOperationDetail.getClosePaymentInstrument().getIncomeType(),
					closingOperationDetail.getAmount(), MovementConceptEnum.CLOSING.toString()));
		}

		return accountingRecordList;
	}

	/**
	 * Método para la generación de registros contables a partir de una apertura de
	 * operación
	 * 
	 * @param openingOperationDto
	 * @param employeeId
	 * @return List<AccountingRecordDto>
	 */
	public List<AccountingRecordDto> generateByOpeningOperation(OpeningOperationDto openingOperationDto,
			Long employeeId) {
		List<AccountingRecordDto> accountingRecordList = new ArrayList<>();
		for (OpeningOperationDetailDto openingOperationDetail : openingOperationDto.getOpeningOperationDetail()) {
			accountingRecordList.add(new AccountingRecordDto(employeeId, openingOperationDto.getBranchCode(),
					openingOperationDto.getOpeningId(), openingOperationDto.getOpeningId(),
					AccountingConceptEnum.INCOME.toString(),
					openingOperationDetail.getOpenPaymentInstrument().getIncomeType(),
					openingOperationDetail.getAmount(), MovementConceptEnum.OPENING.toString()));
		}

		return accountingRecordList;
	}

	/**
	 * Método para la generación de registros contables a partir de una lista de
	 * cobros con cheques
	 * 
	 * @param paymentDto
	 * @param openingOperationDto
	 * @param employeeId
	 * @return List<AccountingRecordDto>
	 */
	public List<AccountingRecordDto> generateByPaymentCheckList(PaymentDto paymentDto,
			OpeningOperationDto openingOperationDto, Long employeeId,
			List<AccountingRecordDto> accountingRecordFinalList) {
		List<AccountingRecordDto> accountingRecordList = new ArrayList<>();
		for (CheckPaymentDto checkPayment : paymentDto.getCheckPaymentList()) {
			accountingRecordList.add(new AccountingRecordDto(employeeId, openingOperationDto.getBranchCode(),
					openingOperationDto.getOpeningId(), paymentDto.getPaymentId(),
					AccountingConceptEnum.INCOME.toString(), MovementPaymentTypeEnum.CHECK.toString(),
					checkPayment.getAmountApplied(), MovementConceptEnum.SALE.toString()));
		}
		accountingRecordFinalList.addAll(accountingRecordList);
		return accountingRecordFinalList;
	}

	/**
	 * Método para la generación de registros contables a partir de una lista de
	 * cobros en efectivo
	 * 
	 * @param paymentDto
	 * @param openingOperationDto
	 * @param employeeId
	 * @return List<AccountingRecordDto>
	 */
	public List<AccountingRecordDto> generateByPaymentCashList(PaymentDto paymentDto,
			OpeningOperationDto openingOperationDto, Long employeeId,
			List<AccountingRecordDto> accountingRecordFinalList) {
		List<AccountingRecordDto> accountingRecordList = new ArrayList<>();
		for (PaymentCashDto paymentCash : paymentDto.getPaymentCashList()) {
			accountingRecordList.add(new AccountingRecordDto(employeeId, openingOperationDto.getBranchCode(),
					openingOperationDto.getOpeningId(), paymentDto.getPaymentId(),
					AccountingConceptEnum.INCOME.toString(), MovementPaymentTypeEnum.CASH.toString(),
					paymentCash.getAmountApplied(), MovementConceptEnum.SALE.toString()));
		}
		accountingRecordFinalList.addAll(accountingRecordList);
		return accountingRecordFinalList;
	}

	/**
	 * Método para la generación de registros contables a partir una lista de cobros
	 * con notas de crédito
	 * 
	 * @param paymentDto
	 * @param openingOperationDto
	 * @param employeeId
	 * @param accountingRecordFinalList
	 * @return List<AccountingRecordDto>
	 */
	public List<AccountingRecordDto> generateByPaymentCreditNoteList(PaymentDto paymentDto,
			OpeningOperationDto openingOperationDto, Long employeeId,
			List<AccountingRecordDto> accountingRecordFinalList) {
		List<AccountingRecordDto> accountingRecordList = new ArrayList<>();
		for (CreditNotePaymentDto creditNotePaymentDto : paymentDto.getCreditNotePaymentList()) {
			accountingRecordList.add(new AccountingRecordDto(employeeId, openingOperationDto.getBranchCode(),
					openingOperationDto.getOpeningId(), paymentDto.getPaymentId(),
					AccountingConceptEnum.INCOME.toString(), MovementPaymentTypeEnum.CREDIT_NOTE.toString(),
					creditNotePaymentDto.getAmountApplied(), MovementConceptEnum.SALE.toString()));
		}
		accountingRecordFinalList.addAll(accountingRecordList);
		return accountingRecordFinalList;
	}

	/**
	 * Método para la generación de registros contables a partir de una lista de
	 * cobros con transferencia
	 * 
	 * @param paymentDto
	 * @param openingOperationDto
	 * @param employeeId
	 * @return List<AccountingRecordDto>
	 */
	public List<AccountingRecordDto> generateByPaymentTransferList(PaymentDto paymentDto,
			OpeningOperationDto openingOperationDto, Long employeeId,
			List<AccountingRecordDto> accountingRecordFinalList) {
		List<AccountingRecordDto> accountingRecordList = new ArrayList<>();
		for (TransferPaymentDto trasnferPayment : paymentDto.getTransferPaymentList()) {
			accountingRecordList.add(new AccountingRecordDto(employeeId, openingOperationDto.getBranchCode(),
					openingOperationDto.getOpeningId(), paymentDto.getPaymentId(),
					AccountingConceptEnum.INCOME.toString(), MovementPaymentTypeEnum.TRANSFER.toString(),
					trasnferPayment.getAmountApplied(), MovementConceptEnum.SALE.toString()));
		}
		accountingRecordFinalList.addAll(accountingRecordList);
		return accountingRecordFinalList;
	}

	/**
	 * Método para la generación de registros contables a partir de una lista de
	 * cobros con tarjetas de crédito
	 * 
	 * @param paymentDto
	 * @param openingOperationDto
	 * @param employeeId
	 * @return List<AccountingRecordDto>
	 */
	public List<AccountingRecordDto> generateByCreditCardPaymentList(PaymentDto paymentDto,
			OpeningOperationDto openingOperationDto, Long employeeId,
			List<AccountingRecordDto> accountingRecordFinalList) {
		List<AccountingRecordDto> accountingRecordList = new ArrayList<>();
		for (CreditCardPaymentDto creditCardPayment : paymentDto.getCreditCardPaymentList()) {
			accountingRecordList.add(new AccountingRecordDto(employeeId, openingOperationDto.getBranchCode(),
					openingOperationDto.getOpeningId(), paymentDto.getPaymentId(),
					AccountingConceptEnum.INCOME.toString(), MovementPaymentTypeEnum.CREDIT_CARD.toString(),
					creditCardPayment.getAmountApplied(), MovementConceptEnum.SALE.toString()));
			accountingRecordList.add(new AccountingRecordDto(employeeId, openingOperationDto.getBranchCode(),
					openingOperationDto.getOpeningId(), paymentDto.getPaymentId(),
					AccountingConceptEnum.INCOME.toString(), MovementPaymentTypeEnum.CREDIT_CARD.toString(),
					creditCardPayment.getCommission(), MovementConceptEnum.COMMISSION.toString()));
		}
		accountingRecordFinalList.addAll(accountingRecordList);
		return accountingRecordFinalList;
	}

	/**
	 * Método para la generación de registros contables a partir de una lista de
	 * cobros con crédito
	 * 
	 * @param paymentDto
	 * @param openingOperationDto
	 * @param employeeId
	 * @param accountingRecordFinalList
	 * @return
	 */
	public List<AccountingRecordDto> generateByCreditPaymentList(PaymentDto paymentDto,
			OpeningOperationDto openingOperationDto, Long employeeId,
			List<AccountingRecordDto> accountingRecordFinalList) {
		List<AccountingRecordDto> accountingRecordList = new ArrayList<>();
		for (CreditPaymentDto creditPayment : paymentDto.getCreditPaymentList()) {
			accountingRecordList.add(new AccountingRecordDto(employeeId, openingOperationDto.getBranchCode(),
					openingOperationDto.getOpeningId(), paymentDto.getPaymentId(),
					AccountingConceptEnum.INCOME.toString(), MovementPaymentTypeEnum.CREDIT.toString(),
					creditPayment.getAmountApplied(), MovementConceptEnum.SALE.toString()));
		}
		accountingRecordFinalList.addAll(accountingRecordList);
		return accountingRecordFinalList;
	}

}
