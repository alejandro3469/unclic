package mx.com.endtoend.domain.orders.dto;

import java.util.List;

public class OrderPdfDto {

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

	public List<AddressListDto> getAddress() {
		return address;
	}

	public void setAddress(List<AddressListDto> address) {
		this.address = address;
	}

	public List<ProductListDto> getProducts() {
		return products;
	}

	public void setProducts(List<ProductListDto> products) {
		this.products = products;
	}
	
	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	
	private String orderType;

	private String orderCode;

	private String orderNumber;

    private String date;

    private String userNumber;

    private String mail;

    private String noClient;

    private String name;

    private String phone;

    private String cell;

    private String observations;

    private String discount;

    private String subtotal;

    private String ivaTotal;

    private String orderTotal;
    
    private List<AddressListDto> address;

    private List<ProductListDto> products;
    
	
}
