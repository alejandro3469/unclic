package mx.com.endtoend.domain.commons.constants;

public enum CatalogueClientTypeEnum {

	CLIENT_TYPE,
	WORK_TYPE, 
	CONTACT_METHOD, 
	CFDI, 
	REGIME_FISCAL;

	public static boolean isValid(String value) {
		CatalogueClientTypeEnum[] CatalogueClientTypeEnumList = CatalogueClientTypeEnum.values();
		for (CatalogueClientTypeEnum clientTypeEnum : CatalogueClientTypeEnumList) 	
			if (clientTypeEnum.toString().equals(value))
				return true;		
		return false;
	}
}
