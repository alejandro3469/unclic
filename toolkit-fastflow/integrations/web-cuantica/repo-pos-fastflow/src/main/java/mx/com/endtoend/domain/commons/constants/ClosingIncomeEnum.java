package mx.com.endtoend.domain.commons.constants;

public enum ClosingIncomeEnum {

	CASH, CREDIT_NOTE, CHECK, CREDIT, CREDIT_CARD, TRANSFER;

	public static boolean isValid(String value) {
		ClosingIncomeEnum[] closingIncomeEnumList = ClosingIncomeEnum.values();
		for (ClosingIncomeEnum closingIncomeEnum : closingIncomeEnumList)
			if (closingIncomeEnum.toString().equals(value))
				return true;
		return false;
	}
}
