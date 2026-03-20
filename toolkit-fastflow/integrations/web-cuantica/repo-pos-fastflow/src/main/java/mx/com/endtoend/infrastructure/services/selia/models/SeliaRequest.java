package mx.com.endtoend.infrastructure.services.selia.models;

public class SeliaRequest {

	private static final String ID_COMPANY_CODE = "161";

	private static final String ID_ORIGINATOR_CODE = "1";

	private static final String ID_SALE_CASH_NUMBER = "2";

	private static final String ID_REQUEST_DATE = "3";

	private static final String ID_COMPANY_PHONE = "7";

	private static final String ID_COMPANY_PHONE_CODE = "56";

	private static final String ID_AMOUNT = "45";

	private static final String ID_PHONE_NUMBER = "82";

	private static final String ID_ORDER_NUMBER = "19";

	private static final String ID_GENERATE_BOUCHR = "163";

	private static final String ID_USER = "54";

	private static final String ID_PASSWORD = "219";

	private String companyCode;

	private String originatorCode;

	private String saleCashNumber;

	private String requestDate;

	private String companyPhone;

	private String companyPhoneCode;

	private String amount;

	private String phoneNumber;

	private String orderNumber;

	private String generateBoucher;

	private String user;

	private String password;

	public SeliaRequest() {
		super();
	}

	public SeliaRequest(String companyCode, String originatorCode, String saleCashNumber, String requestDate,
			String orderNumber, String generateBoucher, String user, String password, String companyPhone,
			String companyPhoneCode, String amount, String phoneNumber) {
		super();
		this.companyCode = companyCode;
		this.originatorCode = originatorCode;
		this.saleCashNumber = saleCashNumber;
		this.requestDate = requestDate;
		this.orderNumber = orderNumber;
		this.generateBoucher = generateBoucher;
		this.user = user;
		this.password = password;

		this.companyPhone = companyPhone;
		this.companyPhoneCode = companyPhoneCode;
		this.amount = amount;
		this.phoneNumber = phoneNumber;
	}

	public String generateRequest() {
		return "{" + getCompanyCode() + ";" + getOriginatorCode() + ";" + getSaleCashNumber() + ";" + getRequestDate()
				+ ";" + getCompanyPhone() + ";" + getCompanyPhoneCode() + ";" + getAmount() + ";" + getPhoneNumber()
				+ ";" + getOrderNumber() + ";" + getGenerateBoucher() + ";" + getUser() + ";" + getPassword() + "}";
	}

	public String getCompanyCode() {
		return ID_COMPANY_CODE + ":" + companyCode;
	}

	public String getOriginatorCode() {
		return ID_ORIGINATOR_CODE + ":" + originatorCode;
	}

	public String getSaleCashNumber() {
		return ID_SALE_CASH_NUMBER + ":" + saleCashNumber;
	}

	public String getRequestDate() {

		return ID_REQUEST_DATE + ":" + requestDate;
	}

	public String getCompanyPhone() {
		return ID_COMPANY_PHONE + ":" + companyPhone;
	}

	public String getCompanyPhoneCode() {
		return ID_COMPANY_PHONE_CODE + ":" + companyPhoneCode;
	}

	public String getAmount() {
		return ID_AMOUNT + ":" + amount;
	}

	public String getPhoneNumber() {
		return ID_PHONE_NUMBER + ":" + phoneNumber;
	}

	public String getOrderNumber() {
		return ID_ORDER_NUMBER + ":" + orderNumber;
	}

	public String getGenerateBoucher() {
		return ID_GENERATE_BOUCHR + ":" + generateBoucher;
	}

	public String getUser() {
		return ID_USER + ":" + user;
	}

	public String getPassword() {
		return ID_PASSWORD + ":" + password;
	}

	@Override
	public String toString() {
		return "SeliaRequest [companyCode=" + companyCode + ", originatorCode=" + originatorCode + ", saleCashNumber="
				+ saleCashNumber + ", requestDate=" + requestDate + ", companyPhone=" + companyPhone
				+ ", companyPhoneCode=" + companyPhoneCode + ", amount=" + amount + ", phoneNumber=" + phoneNumber
				+ ", orderNumber=" + orderNumber + ", generateBoucher=" + generateBoucher + ", user=" + user
				+ ", password=" + password + "]";
	}

}
