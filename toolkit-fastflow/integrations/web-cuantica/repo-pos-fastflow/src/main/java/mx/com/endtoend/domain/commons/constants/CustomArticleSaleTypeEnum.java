package mx.com.endtoend.domain.commons.constants;

/**
 * Constantes para el tipo de venta con la que se relacionan los artículos
 * personalizados del sistema
 * 
 * @author ddcasas
 *
 */
public enum CustomArticleSaleTypeEnum {
	
	GENERAL,
	SALE_ADVERSITING;
	
	
	 public static boolean isValid(String value) {
		 CustomArticleSaleTypeEnum[] customArticleSaleTypeList = CustomArticleSaleTypeEnum.values();
	        for (CustomArticleSaleTypeEnum customArticleSaleType : customArticleSaleTypeList)
	            if (customArticleSaleType.toString().equals(value))
	                return true;
	        return false;
	    }

}
