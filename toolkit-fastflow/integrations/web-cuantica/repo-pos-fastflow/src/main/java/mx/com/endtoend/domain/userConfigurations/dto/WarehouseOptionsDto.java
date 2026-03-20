package mx.com.endtoend.domain.userConfigurations.dto;

/**
 * 
 * @author ddcasas
 *
 */

public class WarehouseOptionsDto {

	private Long id;
	
	private String warehouseCode;
	
	private String warehouseName;
	
	private boolean isDefault;
	
	private boolean isConsumption;
	
	private boolean isQuery; //TODO: REVISAR VALIDACION DE NECESIDAD DE CAMPO  
	
	
	
	public WarehouseOptionsDto() {
		super();
	}

	public WarehouseOptionsDto(Long id, String warehouseCode, String warehouseName, boolean isDefault,
			boolean isConsumption, boolean isQuery) {
		super();
		this.id = id;
		this.warehouseCode = warehouseCode;
		this.warehouseName = warehouseName;
		this.isDefault = isDefault;
		this.isConsumption = isConsumption;
		this.isQuery = isQuery;
	}

	public boolean getIsDefault() {
		return isDefault;
	}
	
	public void setIsDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}
	
	public boolean getIsConsumption() {
		return isConsumption;
	}
	
	public void setIsConsumption(boolean isConsumption) {
		this.isConsumption = isConsumption;
	}
	
	public boolean getIsQuery() {
		return isQuery;
	}
	
	public void setIsQuery(boolean isQuery) {
		this.isQuery = isQuery;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getWarehouseCode() {
		return warehouseCode;
	}

	public void setWarehouseCode(String warehouseCode) {
		this.warehouseCode = warehouseCode;
	}

	public String getWarehouseName() {
		return warehouseName;
	}

	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}

	@Override
	public String toString() {
		return "WarehouseOptionsDto [id=" + id + ", warehouseCode=" + warehouseCode + ", warehouseName=" + warehouseName
				+ ", isDefault=" + isDefault + ", isConsumption=" + isConsumption + ", isQuery=" + isQuery + "]";
	}
}