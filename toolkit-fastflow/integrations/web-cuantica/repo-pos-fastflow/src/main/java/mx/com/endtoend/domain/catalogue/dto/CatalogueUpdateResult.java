package mx.com.endtoend.domain.catalogue.dto;

public class CatalogueUpdateResult {

	private int updateRecord;

	private int newRecord;

	private String catalogueType;

	public int getUpdateRecord() {
		return updateRecord;
	}

	public int getNewRecord() {
		return newRecord;
	}

	public String getCatalogueType() {
		return catalogueType;
	}

	public void setUpdateRecord(int updateRecord) {
		this.updateRecord = updateRecord;
	}

	public void setNewRecord(int newRecord) {
		this.newRecord = newRecord;
	}

	public void setCatalogueType(String catalogueType) {
		this.catalogueType = catalogueType;
	}

	@Override
	public String toString() {
		return "CatalogueUpdateResult [updateRecord=" + updateRecord + ", newRecord=" + newRecord + ", catalogueType="
				+ catalogueType + "]";
	}

}
