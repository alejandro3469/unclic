package mx.com.endtoend.domain.recharges.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import mx.com.smartbussiness.generic.utils.DecimalPrecisionUtils;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class CompanyPhoneDto {

	private String companyPhoneName;

	private Integer companyPhoneCode;

	private Integer companyPhoe;

	private String lineType;

	private String articleCode;

	private BigDecimal articleNumber;

	private String storageType;

	private String description;

	@NotNull(message = "El monto es obligatorio")
	@DecimalMin(value = "0.0", message = "El monto debe ser mayor o igual a 0")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private BigDecimal amount;

	public String getCompanyPhoneName() {
		return companyPhoneName;
	}

	public void setCompanyPhoneName(String companyPhoneName) {
		this.companyPhoneName = companyPhoneName;
	}

	public Integer getCompanyPhoneCode() {
		return companyPhoneCode;
	}

	public void setCompanyPhoneCode(Integer companyPhoneCode) {
		this.companyPhoneCode = companyPhoneCode;
	}

	public Integer getCompanyPhoe() {
		return companyPhoe;
	}

	public void setCompanyPhoe(Integer companyPhoe) {
		this.companyPhoe = companyPhoe;
	}

	public String getLineType() {
		return lineType;
	}

	public void setLineType(String lineType) {
		this.lineType = lineType;
	}

	public String getArticleCode() {
		return articleCode;
	}

	public void setArticleCode(String articleCode) {
		this.articleCode = articleCode;
	}

	public BigDecimal getArticleNumber() {
		return articleNumber;
	}

	public void setArticleNumber(BigDecimal articleNumber) {
		this.articleNumber = articleNumber;
	}

	public String getStorageType() {
		return storageType;
	}

	public void setStorageType(String storageType) {
		this.storageType = storageType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = DecimalPrecisionUtils.roundToTwoDecimals(amount);
	}

	@Override
	public String toString() {
		return "CompanyPhoneDto [companyPhoneName=" + companyPhoneName + ", companyPhoneCode=" + companyPhoneCode
				+ ", companyPhoe=" + companyPhoe + ", lineType=" + lineType + ", articleCode=" + articleCode
				+ ", articleNumber=" + articleNumber + ", storageType=" + storageType + ", description=" + description
				+ ", amount=" + amount + "]";
	}

}
