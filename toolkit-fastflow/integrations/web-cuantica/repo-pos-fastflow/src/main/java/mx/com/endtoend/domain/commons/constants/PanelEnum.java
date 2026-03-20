package mx.com.endtoend.domain.commons.constants;

public enum PanelEnum {

	ORDER_MANAGEMENT, 
	ORDER_PAYMENT, 
	ORDER_MANAGEMENT_PAYMENT, 
	ORDER_CREDIT_NOTE;

	public static boolean isValid(String value) {
		PanelEnum[] PanelEnumList = PanelEnum.values();
		for (PanelEnum panelEnum : PanelEnumList) 	
			if (panelEnum.toString().equals(value))
				return true;		
		return false;
	}
}
