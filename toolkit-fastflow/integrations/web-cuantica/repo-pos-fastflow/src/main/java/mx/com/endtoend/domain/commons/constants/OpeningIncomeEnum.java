package mx.com.endtoend.domain.commons.constants;

public enum OpeningIncomeEnum {

	CASH, CREDIT_NOTE, CHECK;

	public static boolean isValid(String value) {
		OpeningIncomeEnum[] openingIncomeEnumList = OpeningIncomeEnum.values();
		for (OpeningIncomeEnum openingIncomeEnum : openingIncomeEnumList)
			if (openingIncomeEnum.toString().equals(value))
				return true;
		return false;
	}
}
