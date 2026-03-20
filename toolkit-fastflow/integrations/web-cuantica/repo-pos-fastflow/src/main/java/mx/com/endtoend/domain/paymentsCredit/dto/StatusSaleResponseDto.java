package mx.com.endtoend.domain.paymentsCredit.dto;

import java.math.BigDecimal;
import java.util.Date;

public class StatusSaleResponseDto {

	private BigDecimal orderNumber;

	private String orderCode;

	private String code;

	private String name;

	private StatusSaleDetailDto preventa;

	private String message;

	private String type;

	private Date creationDate;

	public StatusSaleResponseDto() {
		super();
	}

	public StatusSaleResponseDto(BigDecimal orderNumber, String orderCode, String code, String name,
			StatusSaleDetailDto preventa, String type) {
		super();
		this.orderNumber = orderNumber;
		this.orderCode = orderCode;
		this.code = code;
		this.name = name;
		this.preventa = preventa;
		this.type = type;
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public StatusSaleDetailDto getPreventa() {
		return preventa;
	}

	public void setPreventa(StatusSaleDetailDto preventa) {
		this.preventa = preventa;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "StatusSaleResponseDto [orderNumber=" + orderNumber + ", orderCode=" + orderCode + ", code=" + code
				+ ", name=" + name + ", preventa=" + preventa + ", message=" + message + ", type=" + type
				+ ", creationDate=" + creationDate + "]";
	}

}
