package mx.com.endtoend.domain.articles.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonFormat;
import mx.com.smartbussiness.generic.utils.DecimalPrecisionUtils;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

/**
 * 
 * @author ddcasas
 *
 */

public class ArticleDto {

	private BigDecimal articleNumber;

	private String warehouseCode;

	private String articleCode;

	private String priceType;

	private String catalogNumber;

	private String articleDescriptionOne;

	private String articleDescriptionTwo;

	private String subCategoryClassification;

	private String inputUnitMeasure;

	@NotNull(message = "El precio es obligatorio")
	@DecimalMin(value = "0.0", message = "El precio debe ser mayor o igual a 0")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private BigDecimal price;

	@NotNull(message = "El precio con impuesto es obligatorio")
	@DecimalMin(value = "0.0", message = "El precio con impuesto debe ser mayor o igual a 0")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private BigDecimal priceTax;

	private String storageType;

	private String primaryUnitMeasure;

	private String division;

	private String family;

	private String categoryCode;

	private String brand;

	private String alternateDescription;

	private String unitVolume;

	private String priceGroup;

	private String lineType;

	private String supplierNumber;

	private Integer totalQuantity;

	private String subCategoryDescription;

	private String divisionDescription;

	private String familyDescription;

	private String categoryCodeDescription;

	private String brandDescription;

	@NotNull(message = "El código de conversión es obligatorio")
	@DecimalMin(value = "0.0", message = "El código de conversión debe ser mayor o igual a 0")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private BigDecimal conversionCode;

	private Integer unitMeasurement;

	private Integer aviableQuantity;

	private Integer reservedQuantity;

	@NotNull(message = "El factor de conversión es obligatorio")
	@DecimalMin(value = "0.0", message = "El factor de conversión debe ser mayor o igual a 0")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private BigDecimal conversionFactor;

	private String applyTax;

	@NotNull(message = "El valor de impuesto uno es obligatorio")
	@DecimalMin(value = "0.0", message = "El valor de impuesto uno debe ser mayor o igual a 0")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private BigDecimal taxValueOne;

	@NotNull(message = "El valor de impuesto dos es obligatorio")
	@DecimalMin(value = "0.0", message = "El valor de impuesto dos debe ser mayor o igual a 0")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private BigDecimal taxValueTwo;

	@NotNull(message = "El valor de impuesto tres es obligatorio")
	@DecimalMin(value = "0.0", message = "El valor de impuesto tres debe ser mayor o igual a 0")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private BigDecimal taxValueThree;

	@NotNull(message = "El valor de impuesto cuatro es obligatorio")
	@DecimalMin(value = "0.0", message = "El valor de impuesto cuatro debe ser mayor o igual a 0")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private BigDecimal taxValueFour;

	@NotNull(message = "El valor de impuesto cinco es obligatorio")
	@DecimalMin(value = "0.0", message = "El valor de impuesto cinco debe ser mayor o igual a 0")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private BigDecimal taxValueFive;

	@NotNull(message = "El valor de impuesto por defecto es obligatorio")
	@DecimalMin(value = "0.0", message = "El valor de impuesto por defecto debe ser mayor o igual a 0")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private BigDecimal taxValueByDefault;

	public ArticleDto(BigDecimal priceType, BigDecimal articleNumber, String warehouseCode, Integer supplierNumber,
			String catalogNumber, String priceGroup, String lineType, String articleDescriptionOne,
			String articleDescriptionTwo, String articleCode, String subCategoryClassification, String storageType,
			String division, String family, String categoryCode, String brand, String alternateDescription,
			String primaryUnitMeasure, String unitVolume, String inputUnitMeasure, BigDecimal price) {

		super();
		this.priceType = priceType.toString();
		this.articleNumber = articleNumber;
		this.warehouseCode = warehouseCode;
		this.supplierNumber = Integer.toString(supplierNumber);
		this.catalogNumber = catalogNumber;
		this.priceGroup = priceGroup;
		this.lineType = lineType;
		this.articleDescriptionOne = articleDescriptionOne;
		this.articleDescriptionTwo = articleDescriptionTwo;
		this.articleCode = articleCode;
		this.subCategoryClassification = subCategoryClassification;
		this.storageType = storageType;
		this.division = division;
		this.family = family;
		this.categoryCode = categoryCode;
		this.brand = brand;
		this.alternateDescription = alternateDescription;
		this.primaryUnitMeasure = primaryUnitMeasure;
		this.unitVolume = unitVolume;
		this.inputUnitMeasure = inputUnitMeasure;
		this.price = DecimalPrecisionUtils.roundToTwoDecimals(price);

	}

	public String getPriceType() {
		return priceType;
	}

	public void setPriceType(String priceType) {
		this.priceType = priceType;
	}

	public ArticleDto() {

	}

	public BigDecimal getArticleNumber() {
		return articleNumber;
	}

	public String getWarehouseCode() {
		return warehouseCode;
	}

	public String getArticleCode() {
		return articleCode;
	}

	public String getCatalogNumber() {
		return catalogNumber;
	}

	public String getArticleDescriptionOne() {
		return articleDescriptionOne;
	}

	public String getArticleDescriptionTwo() {
		return articleDescriptionTwo;
	}

	public String getSubCategoryClassification() {
		return subCategoryClassification;
	}

	public String getInputUnitMeasure() {
		return inputUnitMeasure;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public String getStorageType() {
		return storageType;
	}

	public String getPrimaryUnitMeasure() {
		return primaryUnitMeasure;
	}

	public String getDivision() {
		return division;
	}

	public String getFamily() {
		return family;
	}

	public String getCategoryCode() {
		return categoryCode;
	}

	public String getAlternateDescription() {
		return alternateDescription;
	}

	public String getPriceGroup() {
		return priceGroup;
	}

	public String getLineType() {
		return lineType;
	}

	public String getSupplierNumber() {
		return supplierNumber;
	}

	public Integer getTotalQuantity() {
		return totalQuantity;
	}

	public String getSubCategoryDescription() {
		return subCategoryDescription;
	}

	public String getDivisionDescription() {
		return divisionDescription;
	}

	public String getFamilyDescription() {
		return familyDescription;
	}

	public String getCategoryCodeDescription() {
		return categoryCodeDescription;
	}

	public BigDecimal getConversionCode() {
		return conversionCode;
	}

	public Integer getUnitMeasurement() {
		return unitMeasurement;
	}

	public Integer getAviableQuantity() {
		return aviableQuantity;
	}

	public Integer getReservedQuantity() {
		return reservedQuantity;
	}

	public BigDecimal getConversionFactor() {
		return conversionFactor;
	}

	public void setArticleNumber(BigDecimal articleNumber) {
		this.articleNumber = articleNumber;
	}

	public void setWarehouseCode(String warehouseCode) {
		this.warehouseCode = warehouseCode;
	}

	public void setArticleCode(String articleCode) {
		this.articleCode = articleCode;
	}

	public void setCatalogNumber(String catalogNumber) {
		this.catalogNumber = catalogNumber;
	}

	public void setArticleDescriptionOne(String articleDescriptionOne) {
		this.articleDescriptionOne = articleDescriptionOne;
	}

	public void setArticleDescriptionTwo(String articleDescriptionTwo) {
		this.articleDescriptionTwo = articleDescriptionTwo;
	}

	public void setSubCategoryClassification(String subCategoryClassification) {
		this.subCategoryClassification = subCategoryClassification;
	}

	public void setInputUnitMeasure(String inputUnitMeasure) {
		this.inputUnitMeasure = inputUnitMeasure;
	}

	public void setPrice(BigDecimal price) {
		this.price = DecimalPrecisionUtils.roundToTwoDecimals(price);
	}

	public void setStorageType(String storageType) {
		this.storageType = storageType;
	}

	public void setPrimaryUnitMeasure(String primaryUnitMeasure) {
		this.primaryUnitMeasure = primaryUnitMeasure;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public void setFamily(String family) {
		this.family = family;
	}

	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}

	public void setAlternateDescription(String alternateDescription) {
		this.alternateDescription = alternateDescription;
	}

	public void setPriceGroup(String priceGroup) {
		this.priceGroup = priceGroup;
	}

	public void setLineType(String lineType) {
		this.lineType = lineType;
	}

	public void setSupplierNumber(String supplierNumber) {
		this.supplierNumber = supplierNumber;
	}

	public void setTotalQuantity(Integer totalQuantity) {
		this.totalQuantity = totalQuantity;
	}

	public void setSubCategoryDescription(String subCategoryDescription) {
		this.subCategoryDescription = subCategoryDescription;
	}

	public void setDivisionDescription(String divisionDescription) {
		this.divisionDescription = divisionDescription;
	}

	public void setFamilyDescription(String familyDescription) {
		this.familyDescription = familyDescription;
	}

	public void setCategoryCodeDescription(String categoryCodeDescription) {
		this.categoryCodeDescription = categoryCodeDescription;
	}

	public void setConversionCode(BigDecimal conversionCode) {
		this.conversionCode = DecimalPrecisionUtils.roundToTwoDecimals(conversionCode);
	}

	public void setUnitMeasurement(Integer unitMeasurement) {
		this.unitMeasurement = unitMeasurement;
	}

	public void setAviableQuantity(Integer aviableQuantity) {
		this.aviableQuantity = aviableQuantity;
	}

	public void setReservedQuantity(Integer reservedQuantity) {
		this.reservedQuantity = reservedQuantity;
	}

	public void setConversionFactor(BigDecimal conversionFactor) {
		this.conversionFactor = DecimalPrecisionUtils.roundToTwoDecimals(conversionFactor);
	}

	public String getApplyTax() {
		return applyTax;
	}

	public BigDecimal getTaxValueOne() {
		return taxValueOne;
	}

	public BigDecimal getTaxValueTwo() {
		return taxValueTwo;
	}

	public BigDecimal getTaxValueThree() {
		return taxValueThree;
	}

	public BigDecimal getTaxValueFour() {
		return taxValueFour;
	}

	public BigDecimal getTaxValueFive() {
		return taxValueFive;
	}

	public void setApplyTax(String applyTax) {
		this.applyTax = applyTax;
	}

	public void setTaxValueOne(BigDecimal taxValueOne) {
		this.taxValueOne = DecimalPrecisionUtils.roundToTwoDecimals(taxValueOne);
	}

	public void setTaxValueTwo(BigDecimal taxValueTwo) {
		this.taxValueTwo = DecimalPrecisionUtils.roundToTwoDecimals(taxValueTwo);
	}

	public void setTaxValueThree(BigDecimal taxValueThree) {
		this.taxValueThree = DecimalPrecisionUtils.roundToTwoDecimals(taxValueThree);
	}

	public void setTaxValueFour(BigDecimal taxValueFour) {
		this.taxValueFour = DecimalPrecisionUtils.roundToTwoDecimals(taxValueFour);
	}

	public void setTaxValueFive(BigDecimal taxValueFive) {
		this.taxValueFive = DecimalPrecisionUtils.roundToTwoDecimals(taxValueFive);
	}

	public String getUnitVolume() {
		return unitVolume;
	}

	public void setUnitVolume(String unitVolume) {
		this.unitVolume = unitVolume;
	}

	public BigDecimal getTaxValueByDefault() {
		return taxValueByDefault;
	}

	public void setTaxValueByDefault(BigDecimal taxValueByDefault) {
		this.taxValueByDefault = DecimalPrecisionUtils.roundToTwoDecimals(taxValueByDefault);
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getBrandDescription() {
		return brandDescription;
	}

	public void setBrandDescription(String brandDescription) {
		this.brandDescription = brandDescription;
	}

	public BigDecimal getPriceTax() {
		return priceTax;
	}

	public void setPriceTax(BigDecimal priceTax) {
		this.priceTax = DecimalPrecisionUtils.roundToTwoDecimals(priceTax);
	}

	@Override
	public String toString() {
		return "ArticleDto [articleNumber=" + articleNumber + ", warehouseCode=" + warehouseCode + ", articleCode="
				+ articleCode + ", priceType=" + priceType + ", catalogNumber=" + catalogNumber
				+ ", articleDescriptionOne=" + articleDescriptionOne + ", articleDescriptionTwo="
				+ articleDescriptionTwo + ", subCategoryClassification=" + subCategoryClassification
				+ ", inputUnitMeasure=" + inputUnitMeasure + ", price=" + price + ", priceTax=" + priceTax
				+ ", storageType=" + storageType + ", primaryUnitMeasure=" + primaryUnitMeasure + ", division="
				+ division + ", family=" + family + ", categoryCode=" + categoryCode + ", brand=" + brand
				+ ", alternateDescription=" + alternateDescription + ", unitVolume=" + unitVolume + ", priceGroup="
				+ priceGroup + ", lineType=" + lineType + ", supplierNumber=" + supplierNumber + ", totalQuantity="
				+ totalQuantity + ", subCategoryDescription=" + subCategoryDescription + ", divisionDescription="
				+ divisionDescription + ", familyDescription=" + familyDescription + ", categoryCodeDescription="
				+ categoryCodeDescription + ", brandDescription=" + brandDescription + ", conversionCode="
				+ conversionCode + ", unitMeasurement=" + unitMeasurement + ", aviableQuantity=" + aviableQuantity
				+ ", reservedQuantity=" + reservedQuantity + ", conversionFactor=" + conversionFactor + ", applyTax="
				+ applyTax + ", taxValueOne=" + taxValueOne + ", taxValueTwo=" + taxValueTwo + ", taxValueThree="
				+ taxValueThree + ", taxValueFour=" + taxValueFour + ", taxValueFive=" + taxValueFive
				+ ", taxValueByDefault=" + taxValueByDefault + "]";
	}

}