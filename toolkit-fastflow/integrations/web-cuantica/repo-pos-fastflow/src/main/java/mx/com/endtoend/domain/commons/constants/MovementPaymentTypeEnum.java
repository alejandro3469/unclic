package mx.com.endtoend.domain.commons.constants;

public enum MovementPaymentTypeEnum {

	CASH, 
	CREDIT_NOTE,
	CREDIT_CARD,
	CREDIT,
	TRANSFER,
	CHECK;
	
	
	public static boolean isValid(String value) {
		MovementPaymentTypeEnum[] movementPaymentTypeList = MovementPaymentTypeEnum.values();
		for (MovementPaymentTypeEnum movementPaymentTypeEnum : movementPaymentTypeList)
			if (movementPaymentTypeEnum.toString().equals(value))
				return true;
		return false;
	}
}
