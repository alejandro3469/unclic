package mx.com.endtoend.domain.cash.creditCardReference.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import mx.com.smartbussiness.generic.utils.DecimalPrecisionUtils;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

public class CreditCardHistoryChangeDto {

	private Long id;

	private Long creditCardId;

	private String bankingInstitution;

	private String type;

	private String period;

	@NotNull(message = "La comisión es obligatoria")
	@DecimalMin(value = "0.0", message = "La comisión debe ser mayor o igual a 0")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private BigDecimal commission;

	private boolean isEnable;

	private Date updatedDate;

	private String modifiedBy;

	public CreditCardHistoryChangeDto() {
		super();
	}

	public CreditCardHistoryChangeDto(Long id, Long creditCardId, String bankingInstitution, String type, String period,
			BigDecimal commission, boolean isEnable, Date updatedDate, String modifiedBy) {
		super();
		this.id = id;
		this.creditCardId = creditCardId;
		this.bankingInstitution = bankingInstitution;
		this.type = type;
		this.period = period;
		this.commission = DecimalPrecisionUtils.roundToTwoDecimals(commission);
		this.isEnable = isEnable;
		this.updatedDate = updatedDate;
		this.modifiedBy = modifiedBy;
	}

	public Long getId() {
		return id;
	}

	public Long getCreditCardId() {
		return creditCardId;
	}

	public String getBankingInstitution() {
		return bankingInstitution;
	}

	public String getType() {
		return type;
	}

	public String getPeriod() {
		return period;
	}

	public BigDecimal getCommission() {
		return commission;
	}

	public boolean getIsEnable() {
		return isEnable;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setCreditCardId(Long creditCardId) {
		this.creditCardId = creditCardId;
	}

	public void setBankingInstitution(String bankingInstitution) {
		this.bankingInstitution = bankingInstitution;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public void setCommission(BigDecimal commission) {
		this.commission = DecimalPrecisionUtils.roundToTwoDecimals(commission);
	}

	public void setIsEnable(boolean isEnable) {
		this.isEnable = isEnable;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	@Override
	public String toString() {
		return "CreditCardHistoryChangeDto [id=" + id + ", creditCardId=" + creditCardId + ", bankingInstitution="
				+ bankingInstitution + ", type=" + type + ", period=" + period + ", commission=" + commission
				+ ", isEnable=" + isEnable + ", updatedDate=" + updatedDate + ", modifiedBy=" + modifiedBy + "]";
	}

}
