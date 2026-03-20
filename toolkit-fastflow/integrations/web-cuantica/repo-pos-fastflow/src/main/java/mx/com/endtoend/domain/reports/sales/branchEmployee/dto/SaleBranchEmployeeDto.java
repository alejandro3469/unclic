package mx.com.endtoend.domain.reports.sales.branchEmployee.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import mx.com.smartbussiness.generic.utils.DecimalPrecisionUtils;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

import mx.com.endtoend.domain.branch.dto.BranchDto;

public class SaleBranchEmployeeDto {

	private String branchName;

	private String branchCode;

	private List<SummaryEmployeeSaleDto> employeeDetail;

	@NotNull(message = "El total de la sucursal es obligatorio")
	@DecimalMin(value = "0.0", message = "El total de la sucursal debe ser mayor o igual a 0")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private BigDecimal branchTotal;

	public SaleBranchEmployeeDto(BranchDto branchDto, SaleReportBranchEmployeeParamsDto reportBranchEmployeeParamsDto,
			List<SummaryEmployeeSaleDto> summaryEmployeeSaleList) {
		this.branchName = branchDto != null ? branchDto.getName() : "";
		this.branchCode = branchDto != null ? branchDto.getCode() : reportBranchEmployeeParamsDto.getBranchCode();
		this.employeeDetail = summaryEmployeeSaleList;

		BigDecimal total = BigDecimal.ZERO;
		for (SummaryEmployeeSaleDto summaryEmployeeSaleDto : summaryEmployeeSaleList) {
			total = total.add(DecimalPrecisionUtils.roundToTwoDecimals(summaryEmployeeSaleDto.getTotalAmount()));
		}
		this.branchTotal = DecimalPrecisionUtils.roundToTwoDecimals(total);
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public List<SummaryEmployeeSaleDto> getEmployeeDetail() {
		return employeeDetail;
	}

	public void setEmployeeDetail(List<SummaryEmployeeSaleDto> employeeDetail) {
		this.employeeDetail = employeeDetail;
	}

	public BigDecimal getBranchTotal() {
		return branchTotal;
	}

	public void setBranchTotal(BigDecimal branchTotal) {
		this.branchTotal = DecimalPrecisionUtils.roundToTwoDecimals(branchTotal);
	}

	@Override
	public String toString() {
		return "SaleBranchEmployeeDto [branchName=" + branchName + ", branchCode=" + branchCode + ", employeeDetail="
				+ employeeDetail + ", branchTotal=" + branchTotal + "]";
	}

}
