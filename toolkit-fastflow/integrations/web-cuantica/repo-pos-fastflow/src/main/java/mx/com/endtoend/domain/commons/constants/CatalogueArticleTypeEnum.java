package mx.com.endtoend.domain.commons.constants;

public enum CatalogueArticleTypeEnum {

	BRAND, 
	CATEGORY, 
	DIVISION, 
	FAMILY;
	
	public static boolean isValid(String value) {
		CatalogueArticleTypeEnum[] catalogueArticleTypeEnumList = CatalogueArticleTypeEnum.values();
		for (CatalogueArticleTypeEnum articleTypeEnum : catalogueArticleTypeEnumList) 	
			if (articleTypeEnum.toString().equals(value))
				return true;		
		return false;
	}
	
}
