package mx.com.endtoend.domain.orders.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class GenericSerchParamsOrderDto {

	private String branchCode;

	private String orderCode;

	private String statusCode;

	private BigDecimal orderNumber;

	private Date datefrom;

	private Date dateto;

	private Long clientNumber;

	private String clientName;

	private boolean isValidity;

	private Long idUser;

	private String employeeEmail;

	private List<Long> idEmployees;

	public Date getDatefrom() {
		return datefrom;
	}

	public Date getDateto() {
		return dateto;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public Long getClientNumber() {
		return clientNumber;
	}

	public BigDecimal getOrderNumber() {
		return orderNumber;
	}

	public boolean getIsValidity() {
		return isValidity;
	}

	public void setDatefrom(Date datefrom) {
		this.datefrom = datefrom;
	}

	public void setDateto(Date dateto) {
		this.dateto = dateto;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public void setClientNumber(Long clientNumber) {
		this.clientNumber = clientNumber;
	}

	public void setOrderNumber(BigDecimal orderNumber) {
		this.orderNumber = orderNumber;
	}

	public void setIsValidity(boolean isValidity) {
		this.isValidity = isValidity;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public Long getIdUser() {
		return idUser;
	}

	public List<Long> getIdEmployees() {
		return idEmployees;
	}

	public void setIdUser(Long idUser) {
		this.idUser = idUser;
	}

	public void setIdEmployees(List<Long> idEmployees) {
		this.idEmployees = idEmployees;
	}

	public String getEmployeeEmail() {
		return employeeEmail;
	}

	public void setEmployeeEmail(String employeeEmail) {
		this.employeeEmail = employeeEmail;
	}

	@Override
	public String toString() {
		return "GenericSerchParamsOrderDto [branchCode=" + branchCode + ", orderCode=" + orderCode + ", statusCode="
				+ statusCode + ", orderNumber=" + orderNumber + ", datefrom=" + datefrom + ", dateto=" + dateto
				+ ", clientNumber=" + clientNumber + ", clientName=" + clientName + ", isValidity=" + isValidity
				+ ", idUser=" + idUser + ", employeeEmail=" + employeeEmail + ", idEmployees=" + idEmployees + "]";
	}

}
