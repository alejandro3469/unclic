package mx.com.endtoend.domain.recharges.dto;

import java.util.List;

import mx.com.endtoend.smart.bussiness.model.payments.CheckPaymentDto;
import mx.com.endtoend.smart.bussiness.model.payments.CreditCardPaymentDto;
import mx.com.endtoend.smart.bussiness.model.payments.PaymentCashDto;
import mx.com.endtoend.smart.bussiness.model.payments.TransferPaymentDto;

public class PaymentSummaryDto {

	private List<PaymentCashDto> paymentCashList;

	private List<CreditCardPaymentDto> creditCardPaymentList;

	private List<TransferPaymentDto> transferPaymentList;

	private List<CheckPaymentDto> checkPaymentList;

	public List<PaymentCashDto> getPaymentCashList() {
		return paymentCashList;
	}

	public void setPaymentCashList(List<PaymentCashDto> paymentCashList) {
		this.paymentCashList = paymentCashList;
	}

	public List<CreditCardPaymentDto> getCreditCardPaymentList() {
		return creditCardPaymentList;
	}

	public void setCreditCardPaymentList(List<CreditCardPaymentDto> creditCardPaymentList) {
		this.creditCardPaymentList = creditCardPaymentList;
	}

	public List<TransferPaymentDto> getTransferPaymentList() {
		return transferPaymentList;
	}

	public void setTransferPaymentList(List<TransferPaymentDto> transferPaymentList) {
		this.transferPaymentList = transferPaymentList;
	}

	public List<CheckPaymentDto> getCheckPaymentList() {
		return checkPaymentList;
	}

	public void setCheckPaymentList(List<CheckPaymentDto> checkPaymentList) {
		this.checkPaymentList = checkPaymentList;
	}

}
