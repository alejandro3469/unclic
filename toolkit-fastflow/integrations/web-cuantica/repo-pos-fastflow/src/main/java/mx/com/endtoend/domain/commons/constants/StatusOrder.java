package mx.com.endtoend.domain.commons.constants;

public enum StatusOrder {
	
	CREATE_QUOTE_ORDER("5"),
	CREATE_SALE_ORDER("10"),
	PARTIAL_PAYMENT("33"),
	FULL_PAYMENT("50"),
	MANUAL_CANCELLATION("98"),
	AUTOMATIC_CANCELLATION("99");
	
	private String code;
	
	StatusOrder(String value){
		this.code = value;
	}
	
	public String getValue() {
		return code;
	}

}
