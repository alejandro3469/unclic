package mx.com.endtoend.domain.orders.dto;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;

public class PdfDto {

	private String branchName;

	private String orderType;

	private String orderCode;

	private String orderNumber;

	private String date;

	private String employeeName;

	private String userNumber;

	private String mail;

	private String noClient;

	private String name;

	private String state;

	private String outdoorNumber;

	private String interiorNumber;

	private String cp;

	private String colony;

	private String delegation;

	private String street;

	private String phone;

	private String cell;

	private String stateShipping;

	private String outdoorNumberShipping;

	private String interiorNumberShipping;

	private String cpShipping;

	private String colonyShipping;

	private String delegationShipping;

	private String streetShipping;

	private String observations;

	private String discount;

	private String subtotal;

	private String ivaTotal;

	private String orderTotal;

	private String currency;

	private BigDecimal freeTax;

	private BigDecimal nationalTax;

	private BigDecimal iepsTax;

	private List<ProductListDto> products;

	private InputStream logo;

	private InputStream watermark;
	
	private String amountLetter;

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getUserNumber() {
		return userNumber;
	}

	public void setUserNumber(String userNumber) {
		this.userNumber = userNumber;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getNoClient() {
		return noClient;
	}

	public void setNoClient(String noClient) {
		this.noClient = noClient;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getOutdoorNumber() {
		return outdoorNumber;
	}

	public void setOutdoorNumber(String outdoorNumber) {
		this.outdoorNumber = outdoorNumber;
	}

	public String getInteriorNumber() {
		return interiorNumber;
	}

	public void setInteriorNumber(String interiorNumber) {
		this.interiorNumber = interiorNumber;
	}

	public String getCp() {
		return cp;
	}

	public void setCp(String cp) {
		this.cp = cp;
	}

	public String getColony() {
		return colony;
	}

	public void setColony(String colony) {
		this.colony = colony;
	}

	public String getDelegation() {
		return delegation;
	}

	public void setDelegation(String delegation) {
		this.delegation = delegation;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCell() {
		return cell;
	}

	public void setCell(String cell) {
		this.cell = cell;
	}

	public String getStateShipping() {
		return stateShipping;
	}

	public void setStateShipping(String stateShipping) {
		this.stateShipping = stateShipping;
	}

	public String getOutdoorNumberShipping() {
		return outdoorNumberShipping;
	}

	public void setOutdoorNumberShipping(String outdoorNumberShipping) {
		this.outdoorNumberShipping = outdoorNumberShipping;
	}

	public String getInteriorNumberShipping() {
		return interiorNumberShipping;
	}

	public void setInteriorNumberShipping(String interiorNumberShipping) {
		this.interiorNumberShipping = interiorNumberShipping;
	}

	public String getCpShipping() {
		return cpShipping;
	}

	public void setCpShipping(String cpShipping) {
		this.cpShipping = cpShipping;
	}

	public String getColonyShipping() {
		return colonyShipping;
	}

	public void setColonyShipping(String colonyShipping) {
		this.colonyShipping = colonyShipping;
	}

	public String getDelegationShipping() {
		return delegationShipping;
	}

	public void setDelegationShipping(String delegationShipping) {
		this.delegationShipping = delegationShipping;
	}

	public String getStreetShipping() {
		return streetShipping;
	}

	public void setStreetShipping(String streetShipping) {
		this.streetShipping = streetShipping;
	}

	public String getObservations() {
		return observations;
	}

	public void setObservations(String observations) {
		this.observations = observations;
	}

	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}

	public String getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(String subtotal) {
		this.subtotal = subtotal;
	}

	public String getIvaTotal() {
		return ivaTotal;
	}

	public void setIvaTotal(String ivaTotal) {
		this.ivaTotal = ivaTotal;
	}

	public String getOrderTotal() {
		return orderTotal;
	}

	public void setOrderTotal(String orderTotal) {
		this.orderTotal = orderTotal;
	}

	public List<ProductListDto> getProducts() {
		return products;
	}

	public void setProducts(List<ProductListDto> products) {
		this.products = products;
	}

	public InputStream getWatermark() {
		return watermark;
	}

	public void setWatermark(InputStream watermark) {
		this.watermark = watermark;
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

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public BigDecimal getFreeTax() {
		return freeTax;
	}

	public BigDecimal getNationalTax() {
		return nationalTax;
	}

	public BigDecimal getIepsTax() {
		return iepsTax;
	}

	public void setFreeTax(BigDecimal freeTax) {
		this.freeTax = freeTax;
	}

	public void setNationalTax(BigDecimal nationalTax) {
		this.nationalTax = nationalTax;
	}

	public void setIepsTax(BigDecimal iepsTax) {
		this.iepsTax = iepsTax;
	}

	public String getAmountLetter() {
		return amountLetter;
	}

	public void setAmountLetter(String amountLetter) {
		this.amountLetter = amountLetter;
	}
	
	

}
