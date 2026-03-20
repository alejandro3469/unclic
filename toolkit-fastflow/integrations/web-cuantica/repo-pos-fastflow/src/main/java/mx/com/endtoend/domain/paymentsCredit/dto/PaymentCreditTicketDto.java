package mx.com.endtoend.domain.paymentsCredit.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import mx.com.smartbussiness.generic.utils.DecimalPrecisionUtils;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;

import mx.com.endtoend.domain.branch.dto.BranchDto;
import mx.com.endtoend.domain.users.dto.UserDto;
import mx.com.endtoend.smart.bussiness.model.payments.PaymentDto;

public class PaymentCreditTicketDto {

	private InputStream logo;

	private String branchName;

	private String creationDate;

	private String creationTime;

	private String employeeName;

	@NotNull(message = "El monto del pago es obligatorio")
	@DecimalMin(value = "0.0", message = "El monto del pago debe ser mayor o igual a 0")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private BigDecimal paymentAmount;

	private String orderSummary;

	public PaymentCreditTicketDto(PaymentDto paymentDto, BranchDto branchDto, UserDto user, BigDecimal paymentAmount) {
		this.branchName = branchDto.getCode().trim() + " - " + branchDto.getName();
		String fomatDate = new SimpleDateFormat("dd/MM/yyyy").format(paymentDto.getPaymentDate());
		this.creationDate = fomatDate;
		String fomatTime = new SimpleDateFormat("HH:mm:ss").format(paymentDto.getPaymentDate());
		this.creationTime = fomatTime;
		this.employeeName = user.getName() + " " + user.getFirstSurname() + " " + user.getSecondSurname();
		this.paymentAmount = DecimalPrecisionUtils.roundToTwoDecimals(paymentAmount);
		this.orderSummary = paymentDto.getOrderNumber().longValue() + "-" + paymentDto.getOrderCode();
	}

	public InputStream getLogo() {
		return logo;
	}

	public void setLogo(InputStream logo) {
		this.logo = logo;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public String getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(String creationTime) {
		this.creationTime= creationTime;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public BigDecimal getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(BigDecimal paymentAmount) {
		this.paymentAmount = DecimalPrecisionUtils.roundToTwoDecimals(paymentAmount);
	}

	public String getOrderSummary() {
		return orderSummary;
	}

	public void setOrderSummary(String orderSummary) {
		this.orderSummary = orderSummary;
	}

	@Override
	public String toString() {
		return "PaymentCreditTicketDto [logo=" + logo + ", branchName=" + branchName + ", creationDate=" + creationDate
				+ ", creationTime=" + creationTime + ", employeeName=" + employeeName + ", paymentAmount="
				+ paymentAmount + ", orderSummary=" + orderSummary + "]";
	}

}
