package mx.com.endtoend.domain.recharges.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonFormat;
import mx.com.smartbussiness.generic.utils.DecimalPrecisionUtils;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import mx.com.endtoend.infrastructure.services.selia.models.SeliaResponse;

public class RechargeRequestDto {

	private BigDecimal orderNumber;

	private String orderCode;

	private String phoneNumber;

	private String companyPhone;

	@NotNull(message = "El monto es obligatorio")
	@DecimalMin(value = "0.0", message = "El monto debe ser mayor o igual a 0")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private BigDecimal amount;

	private String authCode;

	private String reference;

	public RechargeRequestDto() {
		super();
	}

	public RechargeRequestDto(BigDecimal orderNumber, String orderCode, RechargeSaleDto rechargeSaleDto,
			SeliaResponse seliaResponse) {

		this.orderNumber = orderNumber;
		this.orderCode = orderCode;
		this.phoneNumber = rechargeSaleDto.getPhoneNumber();
		this.companyPhone = rechargeSaleDto.getCompanyRecharge().getCompanyPhoneName();
		this.amount = DecimalPrecisionUtils.roundToTwoDecimals(rechargeSaleDto.getCompanyRecharge().getAmount());
		this.authCode = seliaResponse.getAuthCode();
		this.reference = seliaResponse.getReference() == null ? " " : seliaResponse.getReference();
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

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getCompanyPhone() {
		return companyPhone;
	}

	public void setCompanyPhone(String companyPhone) {
		this.companyPhone = companyPhone;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = DecimalPrecisionUtils.roundToTwoDecimals(amount);
	}

	public String getAuthCode() {
		return authCode;
	}

	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	@Override
	public String toString() {
		return "RechargeRequestDto [orderNumber=" + orderNumber + ", orderCode=" + orderCode + ", phoneNumber="
				+ phoneNumber + ", companyPhone=" + companyPhone + ", amount=" + amount + ", authCode=" + authCode
				+ ", reference=" + reference + "]";
	}

}
