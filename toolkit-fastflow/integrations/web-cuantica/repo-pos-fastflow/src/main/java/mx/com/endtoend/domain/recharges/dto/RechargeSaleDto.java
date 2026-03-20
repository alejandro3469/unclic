package mx.com.endtoend.domain.recharges.dto;

public class RechargeSaleDto {

	private Long paymentId;

	private String orderCode;

	private Long clientNumber;

	private String employeeEmail;

	private String phoneNumber;

	private CompanyPhoneDto companyRecharge;

	private PaymentSummaryDto payments;

	public RechargeSaleDto() {
		super();
	}

	public RechargeSaleDto(String orderCode, Long clientNumber, String employeeEmail, String phoneNumber,
			CompanyPhoneDto companyRecharge, PaymentSummaryDto payments) {
		super();
		this.orderCode = orderCode;
		this.clientNumber = clientNumber;
		this.employeeEmail = employeeEmail;
		this.phoneNumber = phoneNumber;
		this.companyRecharge = companyRecharge;
		this.payments = payments;
	}

	public Long getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(Long paymentId) {
		this.paymentId = paymentId;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public Long getClientNumber() {
		return clientNumber;
	}

	public void setClientNumber(Long clientNumber) {
		this.clientNumber = clientNumber;
	}

	public String getEmployeeEmail() {
		return employeeEmail;
	}

	public void setEmployeeEmail(String employeeEmail) {
		this.employeeEmail = employeeEmail;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public CompanyPhoneDto getCompanyRecharge() {
		return companyRecharge;
	}

	public void setCompanyRecharge(CompanyPhoneDto companyRecharge) {
		this.companyRecharge = companyRecharge;
	}

	public PaymentSummaryDto getPayments() {
		return payments;
	}

	public void setPayments(PaymentSummaryDto payments) {
		this.payments = payments;
	}

	@Override
	public String toString() {
		return "RechargeSaleDto [paymentId=" + paymentId + ", orderCode=" + orderCode + ", clientNumber=" + clientNumber
				+ ", employeeEmail=" + employeeEmail + ", phoneNumber=" + phoneNumber + ", companyRecharge="
				+ companyRecharge + ", payments=" + payments + "]";
	}

}
