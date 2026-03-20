package mx.com.endtoend.domain.articles.dto;

public class GenericSerchParamsArticleDto {

	private String articleCode;

	private String warehouseCode;

	private String division;

	private String category;

	private String family;

	private String categoryCode;

	private String brand;

	private String priceType;

	private String articleDescription;

	private String catalogNumber;

	private String alternativeDescription;

	private boolean isAvailable;

	private String barcode;

	private String inputUnitMeasure;

	public GenericSerchParamsArticleDto() {
		super();
	}

	public String getArticleCode() {
		return articleCode;
	}

	public void setArticleCode(String articleCode) {
		this.articleCode = articleCode;
	}

	public String getWarehouseCode() {
		return warehouseCode;
	}

	public void setWarehouseCode(String warehouseCode) {
		this.warehouseCode = warehouseCode;
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getFamily() {
		return family;
	}

	public void setFamily(String family) {
		this.family = family;
	}

	public String getCategoryCode() {
		return categoryCode;
	}

	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}

	public String getPriceType() {
		return priceType;
	}

	public void setPriceType(String priceType) {
		this.priceType = priceType;
	}

	public String getCatalogNumber() {
		return catalogNumber;
	}

	public void setCatalogNumber(String catalogNumber) {
		this.catalogNumber = catalogNumber;
	}

	public String getAlternativeDescription() {
		return alternativeDescription;
	}

	public void setAlternativeDescription(String alternativeDescription) {
		this.alternativeDescription = alternativeDescription;
	}

	public boolean getIsAvailable() {
		return isAvailable;
	}

	public void setIsAvailable(boolean available) {
		this.isAvailable = available;
	}

	public String getArticleDescription() {
		return articleDescription;
	}

	public void setArticleDescription(String articleDescription) {
		this.articleDescription = articleDescription;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getInputUnitMeasure() {
		return inputUnitMeasure;
	}

	public void setInputUnitMeasure(String inputUnitMeasure) {
		this.inputUnitMeasure = inputUnitMeasure;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	@Override
	public String toString() {
		return "GenericSerchParamsArticleDto [articleCode=" + articleCode + ", warehouseCode=" + warehouseCode
				+ ", division=" + division + ", category=" + category + ", family=" + family + ", categoryCode="
				+ categoryCode + ", brand=" + brand + ", priceType=" + priceType + ", articleDescription="
				+ articleDescription + ", catalogNumber=" + catalogNumber + ", alternativeDescription="
				+ alternativeDescription + ", isAvailable=" + isAvailable + ", barcode=" + barcode + ", inputUnitMeasure="
				+ inputUnitMeasure + "]";
	}



}