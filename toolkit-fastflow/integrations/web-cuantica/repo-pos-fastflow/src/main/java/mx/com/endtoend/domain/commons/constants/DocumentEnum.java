package mx.com.endtoend.domain.commons.constants;

public enum DocumentEnum {

	REMISSION_TICKET, 
	PAYMENT_TICKET, 
	ORDER_DOCUMENT;

	public static boolean isValid(String value) {
		DocumentEnum[] DocumentEnumList = DocumentEnum.values();
		for (DocumentEnum documentEnum : DocumentEnumList)
			if (documentEnum.toString().equals(value))
				return true;
		return false;
	}
}
