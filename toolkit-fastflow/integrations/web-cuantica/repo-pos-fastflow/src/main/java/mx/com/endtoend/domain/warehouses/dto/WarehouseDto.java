package mx.com.endtoend.domain.warehouses.dto;

public class WarehouseDto {
	
	private String code;
	
	private String name;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "WarehouseDto [ " + "code=" + code + ", name=" + name + "]";
	}
	
}