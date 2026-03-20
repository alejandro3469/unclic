package mx.com.endtoend.domain.logs.orders.dto;

import java.math.BigDecimal;
import java.util.Date;

import mx.com.endtoend.smart.bussiness.model.orders.dto.OrderDto;

public class OrderSummaryLogDto {

	private String user;

	private Date updatedDate;

	private BigDecimal orderNumber;

	private String orderCode;

	private OrderDto orderSaved;

	private OrderDto orderUpdated;

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
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

	public OrderDto getOrderSaved() {
		return orderSaved;
	}

	public void setOrderSaved(OrderDto orderSaved) {
		this.orderSaved = orderSaved;
	}

	public OrderDto getOrderUpdated() {
		return orderUpdated;
	}

	public void setOrderUpdated(OrderDto orderUpdated) {
		this.orderUpdated = orderUpdated;
	}

	@Override
	public String toString() {
		return "OrderSummaryLogDto [user=" + user + ", updatedDate=" + updatedDate + ", orderNumber=" + orderNumber
				+ ", orderCode=" + orderCode + ", orderSaved=" + orderSaved + ", orderUpdated=" + orderUpdated + "]";
	}

}
