package mx.com.endtoend.domain.recharges.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import mx.com.smartbussiness.generic.utils.DecimalPrecisionUtils;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import mx.com.endtoend.domain.branch.dto.BranchDto;

public class RechargeTickteDto {

	private String branchName;

	private String branchCP;

	private String branchColony;

	private String branchStreet;

	private String branchPhone;

	private String branchExternalNumber;

	private String dateTime;

	private String orderNumber;

	private String companyPhone;

	private String phoneNumber;

	@NotNull(message = "El monto es obligatorio")
	@DecimalMin(value = "0.0", message = "El monto debe ser mayor o igual a 0")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private BigDecimal amount;

	private String authCode;

	private String reference;

	public RechargeTickteDto(RechargeRequestDto rechargeRequest, BranchDto branchDto) {

		this.branchName = branchDto.getCode().trim() + " - " + branchDto.getName();
		this.branchCP = branchDto.getCp().trim();
		this.branchStreet = branchDto.getStreet();
		this.branchPhone = branchDto.getPhoneNumber().trim();
		this.branchExternalNumber = branchDto.getOutsideNumber().trim();
		this.branchColony = branchDto.getColony().trim();

		this.orderNumber = String.valueOf(rechargeRequest.getOrderNumber().longValue());
		this.companyPhone = rechargeRequest.getCompanyPhone();
		this.phoneNumber = rechargeRequest.getPhoneNumber();
		this.amount = DecimalPrecisionUtils.roundToTwoDecimals(rechargeRequest.getAmount());
		this.authCode = rechargeRequest.getAuthCode();
		this.reference = rechargeRequest.getReference();

		String formatDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
		this.dateTime = formatDate;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getBranchCP() {
		return branchCP;
	}

	public void setBranchCP(String branchCP) {
		this.branchCP = branchCP;
	}

	public String getBranchStreet() {
		return branchStreet;
	}

	public void setBranchStreet(String branchStreet) {
		this.branchStreet = branchStreet;
	}

	public String getBranchPhone() {
		return branchPhone;
	}

	public void setBranchPhone(String branchPhone) {
		this.branchPhone = branchPhone;
	}

	public String getBranchExternalNumber() {
		return branchExternalNumber;
	}

	public void setBranchExternalNumber(String branchExternalNumber) {
		this.branchExternalNumber = branchExternalNumber;
	}

	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getCompanyPhone() {
		return companyPhone;
	}

	public void setCompanyPhone(String companyPhone) {
		this.companyPhone = companyPhone;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = DecimalPrecisionUtils.roundToTwoDecimals(amount);
	}

	public String getAuthCode() {
		return authCode;
	}

	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public String getBranchColony() {
		return branchColony;
	}

	public void setBranchColony(String branchColony) {
		this.branchColony = branchColony;
	}

	@Override
	public String toString() {
		return "RechargeTickteDto [branchName=" + branchName + ", branchCP=" + branchCP + ", branchColony="
				+ branchColony + ", branchStreet=" + branchStreet + ", branchPhone=" + branchPhone
				+ ", branchExternalNumber=" + branchExternalNumber + ", dateTime=" + dateTime + ", orderNumber="
				+ orderNumber + ", companyPhone=" + companyPhone + ", phoneNumber=" + phoneNumber + ", amount=" + amount
				+ ", authCode=" + authCode + ", reference=" + reference + "]";
	}

}
