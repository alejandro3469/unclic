package mx.com.endtoend.domain.creditNote.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import mx.com.smartbussiness.generic.utils.DecimalPrecisionUtils;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

public class CreditNoteDto {

	private Long id;

	private BigDecimal orderNumber;

	private String orderCode;

	private Long orderId;

	@NotNull(message = "El total de la orden es obligatorio")
	@DecimalMin(value = "0.0", message = "El total de la orden debe ser mayor o igual a 0")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private BigDecimal orderTotal;

	@NotNull(message = "El total de la nota de crédito es obligatorio")
	@DecimalMin(value = "0.0", message = "El total de la nota de crédito debe ser mayor o igual a 0")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private BigDecimal creditNoteTotal;

	private boolean isTotal;

	private Long employeeId;

	private List<CreditNoteHeaderDto> creditNoteHeaderList;

	private Date headerCreationDate;

	public CreditNoteDto() {
		super();
	}

	public CreditNoteDto(Long employeeId, BigDecimal orderNumber, String orderCode, Long orderId, BigDecimal orderTotal,
			BigDecimal creditNoteTotal, List<CreditNoteHeaderDto> creditNoteHeaderList) {
		super();
		this.id = null;
		this.employeeId = employeeId;
		this.orderNumber = orderNumber;
		this.orderCode = orderCode;
		this.orderId = orderId;
		this.orderTotal = DecimalPrecisionUtils.roundToTwoDecimals(orderTotal);
		this.creditNoteTotal = DecimalPrecisionUtils.roundToTwoDecimals(creditNoteTotal);
		/**
		 * Si la orden es menor por 0.01 centavos, se toma como orden completa 
		 */
		this.isTotal = (orderTotal.subtract(creditNoteTotal).compareTo(BigDecimal.valueOf(0.01)) <= 0);
		this.creditNoteHeaderList = creditNoteHeaderList;
	}

	public Date getHeaderCreationDate() {
		return headerCreationDate;
	}

	public BigDecimal getOrderNumber() {
		return orderNumber;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public Long getOrderId() {
		return orderId;
	}

	public BigDecimal getOrderTotal() {
		return orderTotal;
	}

	public BigDecimal getCreditNoteTotal() {
		return creditNoteTotal;
	}

	public boolean getIsTotal() {
		return isTotal;
	}

	public List<CreditNoteHeaderDto> getCreditNoteHeaderList() {
		return creditNoteHeaderList;
	}

	public void setOrderNumber(BigDecimal orderNumber) {
		this.orderNumber = orderNumber;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public void setOrderTotal(BigDecimal orderTotal) {
		this.orderTotal = DecimalPrecisionUtils.roundToTwoDecimals(orderTotal);
	}

	public void setCreditNoteTotal(BigDecimal creditNoteTotal) {
		this.creditNoteTotal = DecimalPrecisionUtils.roundToTwoDecimals(creditNoteTotal);
	}

	public void setIsTotal(boolean isTotal) {
		this.isTotal = isTotal;
	}

	public void setCreditNoteHeaderList(List<CreditNoteHeaderDto> creditNoteHeaderList) {
		this.creditNoteHeaderList = creditNoteHeaderList;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	public void setHeaderCreationDate(Date headerCreationDate) {
		this.headerCreationDate = headerCreationDate;
	}

	@Override
	public String toString() {
		return "CreditNoteDto [id=" + id + ", orderNumber=" + orderNumber + ", orderCode=" + orderCode + ", orderId="
				+ orderId + ", orderTotal=" + orderTotal + ", creditNoteTotal=" + creditNoteTotal + ", isTotal="
				+ isTotal + ", employeeId=" + employeeId + ", creditNoteHeaderList=" + creditNoteHeaderList + ", headerCreationDate=" + headerCreationDate + "]";
	}
}