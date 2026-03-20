package mx.com.endtoend.domain.cash.creditCardReference.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import mx.com.smartbussiness.generic.utils.DecimalPrecisionUtils;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

public class CreditCardDto {

	private Long id;

	private String bankingInstitution;

	private String code;

	private boolean isEnable;

	private String type;

	@NotNull(message = "El monto mínimo es obligatorio")
	@DecimalMin(value = "0.0", message = "El monto mínimo debe ser mayor o igual a 0")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private BigDecimal minimumAmount;

	private Boolean isCommissionApply;

	private List<PaymentOptionDto> paymentOptionDetail;

	public CreditCardDto() {
		super();
	}

	public CreditCardDto(Long id, String bankingInstitution, String code, boolean isEnable, String type,
			BigDecimal minimumAmount, Boolean isCommissionApply, List<PaymentOptionDto> paymentOptionDetail) {
		super();
		this.id = id;
		this.bankingInstitution = bankingInstitution;
		this.code = code;
		this.isEnable = isEnable;
		this.type = type;
		this.paymentOptionDetail = paymentOptionDetail;
		this.minimumAmount = DecimalPrecisionUtils.roundToTwoDecimals(minimumAmount);
		this.isCommissionApply = isCommissionApply;
	}

	public Long getId() {
		return id;
	}

	public String getBankingInstitution() {
		return bankingInstitution;
	}

	public String getCode() {
		return code;
	}

	public boolean getIsEnable() {
		return isEnable;
	}

	public List<PaymentOptionDto> getPaymentOptionDetail() {
		return paymentOptionDetail;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setBankingInstitution(String bankingInstitution) {
		this.bankingInstitution = bankingInstitution;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setIsEnable(boolean isEnable) {
		this.isEnable = isEnable;
	}

	public void setPaymentOptionDetail(List<PaymentOptionDto> paymentOptionDetail) {
		this.paymentOptionDetail = paymentOptionDetail;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public BigDecimal getMinimumAmount() {
		return minimumAmount;
	}

	public void setMinimumAmount(BigDecimal minimumAmount) {
		this.minimumAmount = DecimalPrecisionUtils.roundToTwoDecimals(minimumAmount);
	}

	public Boolean getIsCommissionApply() {
		return isCommissionApply;
	}

	public void setIsCommissionApply(Boolean isCommissionApply) {
		this.isCommissionApply = isCommissionApply;
	}

	@Override
	public String toString() {
		return "CreditCardDto [id=" + id + ", bankingInstitution=" + bankingInstitution + ", code=" + code
				+ ", isEnable=" + isEnable + ", type=" + type + ", minimumAmount=" + minimumAmount
				+ ", isCommissionApply=" + isCommissionApply + ", paymentOptionDetail=" + paymentOptionDetail + "]";
	}

}