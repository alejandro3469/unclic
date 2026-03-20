package mx.com.endtoend.domain.paymentsCredit.dto;

import java.math.BigDecimal;
import java.util.Date;

public class StatusSaleRequestDto {

	private BigDecimal orderNumber;

	private String orderCode;

	private String token;

	private String transaction_id;

	private Date creationDate;

	public StatusSaleRequestDto() {
		super();
	}

	public StatusSaleRequestDto(BigDecimal orderNumber, String orderCode, String token, String transaction_id) {
		super();
		this.orderNumber = orderNumber;
		this.orderCode = orderCode;
		this.token = token;
		this.transaction_id = transaction_id;
	}

	public BigDecimal getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(BigDecimal orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getTransaction_id() {
		return transaction_id;
	}

	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Override
	public String toString() {
		return "StatusSaleRequestDto [orderNumber=" + orderNumber + ", orderCode=" + orderCode + ", token=" + token
				+ ", transaction_id=" + transaction_id + ", creationDate=" + creationDate + "]";
	}

}
