package mx.com.endtoend.domain.userConfigurations.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import mx.com.endtoend.smart.bussiness.model.users.dto.SaleTypeDto;

/**
 * 
 * @author ddcasas
 *
 */

public class UserConfigurationDto {

	private Long id;

	private BigDecimal percentageAuthorized;

	private String authorizationCode;

	private Date creationDate;

	private Date updatedDate;

	private String modifyBy;

	private List<SaleTypeDto> saleTypes;
	
	private List<CreditNoteTypeDto> creditNoteTypes;

	private List<PriceTypeDto> priceTypes;

	private List<WarehouseOptionsDto> warehouseOptions;

	public Long getId() {
		return id;
	}

	public BigDecimal getPercentageAuthorized() {
		return percentageAuthorized;
	}

	public String getAuthorizationCode() {
		return authorizationCode;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public String getModifyBy() {
		return modifyBy;
	}

	public List<SaleTypeDto> getSaleTypes() {
		return saleTypes;
	}

	public List<PriceTypeDto> getPriceTypes() {
		return priceTypes;
	}

	public List<WarehouseOptionsDto> getWarehouseOptions() {
		return warehouseOptions;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setPercentageAuthorized(BigDecimal percentageAuthorized) {
		this.percentageAuthorized = percentageAuthorized;
	}

	public void setAuthorizationCode(String authorizationCode) {
		this.authorizationCode = authorizationCode;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public void setModifyBy(String modifyBy) {
		this.modifyBy = modifyBy;
	}

	public void setSaleTypes(List<SaleTypeDto> saleTypes) {
		this.saleTypes = saleTypes;
	}

	public void setPriceTypes(List<PriceTypeDto> priceTypes) {
		this.priceTypes = priceTypes;
	}

	public void setWarehouseOptions(List<WarehouseOptionsDto> warehouseOptions) {
		this.warehouseOptions = warehouseOptions;
	}

	public List<CreditNoteTypeDto> getCreditNoteTypes() {
		return creditNoteTypes;
	}

	public void setCreditNoteTypes(List<CreditNoteTypeDto> creditNoteTypes) {
		this.creditNoteTypes = creditNoteTypes;
	}

	@Override
	public String toString() {
		return "UserConfigurationDto [id=" + id + ", percentageAuthorized=" + percentageAuthorized
				+ ", authorizationCode=" + authorizationCode + ", creationDate=" + creationDate + ", updatedDate="
				+ updatedDate + ", modifyBy=" + modifyBy + ", saleTypes=" + saleTypes + ", creditNoteTypes="
				+ creditNoteTypes + ", priceTypes=" + priceTypes + ", warehouseOptions=" + warehouseOptions + "]";
	}


}