package mx.com.endtoend.domain.paymentsCredit.dto;

import java.math.BigDecimal;

public class CreditSaleStausDto {

	private BigDecimal orderNumber;

	private String orderCode;

	private CreditSaleRequestDto lastCreditSaleRequest;

	private CreditSaleResponseDto lastCreditSaleResponse;

	private StatusSaleRequestDto lastStatusSaleRequest;

	private StatusSaleResponseDto lastStatusSaleResponse;

	public CreditSaleStausDto(BigDecimal orderNumber, String orderCode, CreditSaleRequestDto lastCreditSaleRequest,
			CreditSaleResponseDto lastCreditSaleResponse, StatusSaleRequestDto lastStatusSaleRequest,
			StatusSaleResponseDto lastStatusSaleResponse) {
		super();
		this.orderNumber = orderNumber;
		this.orderCode = orderCode;
		this.lastCreditSaleRequest = lastCreditSaleRequest;
		this.lastCreditSaleResponse = lastCreditSaleResponse;
		this.lastStatusSaleRequest = lastStatusSaleRequest;
		this.lastStatusSaleResponse = lastStatusSaleResponse;
	}

	public BigDecimal getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(BigDecimal orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public CreditSaleRequestDto getLastCreditSaleRequest() {
		return lastCreditSaleRequest;
	}

	public void setLastCreditSaleRequest(CreditSaleRequestDto lastCreditSaleRequest) {
		this.lastCreditSaleRequest = lastCreditSaleRequest;
	}

	public CreditSaleResponseDto getLastCreditSaleResponse() {
		return lastCreditSaleResponse;
	}

	public void setLastCreditSaleResponse(CreditSaleResponseDto lastCreditSaleResponse) {
		this.lastCreditSaleResponse = lastCreditSaleResponse;
	}

	public StatusSaleRequestDto getLastStatusSaleRequest() {
		return lastStatusSaleRequest;
	}

	public void setLastStatusSaleRequest(StatusSaleRequestDto lastStatusSaleRequest) {
		this.lastStatusSaleRequest = lastStatusSaleRequest;
	}

	public StatusSaleResponseDto getLastStatusSaleResponse() {
		return lastStatusSaleResponse;
	}

	public void setLastStatusSaleResponse(StatusSaleResponseDto lastStatusSaleResponse) {
		this.lastStatusSaleResponse = lastStatusSaleResponse;
	}

	@Override
	public String toString() {
		return "CreditSaleStausDto [orderNumber=" + orderNumber + ", orderCode=" + orderCode
				+ ", lastCreditSaleRequest=" + lastCreditSaleRequest + ", lastCreditSaleResponse="
				+ lastCreditSaleResponse + ", lastStatusSaleRequest=" + lastStatusSaleRequest
				+ ", lastStatusSaleResponse=" + lastStatusSaleResponse + "]";
	}

}
