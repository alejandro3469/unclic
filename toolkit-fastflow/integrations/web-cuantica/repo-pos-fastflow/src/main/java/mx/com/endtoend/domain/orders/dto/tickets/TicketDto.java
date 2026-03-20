package mx.com.endtoend.domain.orders.dto.tickets;

import java.io.InputStream;
import java.util.List;

public class TicketDto {

	private String order;

	private String clientPhone;

	private String clientNumber;

	private String employeName;

	private String barcodeNumber;

	private String date;

	private InputStream image;

	private String orderNumber;

	private List<OrderDetail> detail;

	private Integer requestTotal;

	public InputStream getImage() {
		return image;
	}

	public void setImage(InputStream image) {
		this.image = image;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getBarcodeNumber() {
		return barcodeNumber;
	}

	public void setBarcodeNumber(String barcodeNumber) {
		this.barcodeNumber = barcodeNumber;
	}

	public String getClientPhone() {
		return clientPhone;
	}

	public String getClientNumber() {
		return clientNumber;
	}

	public String getEmployeName() {
		return employeName;
	}

	public void setClientPhone(String clientPhone) {
		this.clientPhone = clientPhone;
	}

	public void setClientNumber(String clientNumber) {
		this.clientNumber = clientNumber;
	}

	public void setEmployeName(String employeName) {
		this.employeName = employeName;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public List<OrderDetail> getDetail() {
		return detail;
	}

	public void setDetail(List<OrderDetail> detail) {
		this.detail = detail;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public Integer getRequestTotal() {
		return requestTotal;
	}

	public void setRequestTotal(Integer requestTotal) {
		this.requestTotal = requestTotal;
	}

}
