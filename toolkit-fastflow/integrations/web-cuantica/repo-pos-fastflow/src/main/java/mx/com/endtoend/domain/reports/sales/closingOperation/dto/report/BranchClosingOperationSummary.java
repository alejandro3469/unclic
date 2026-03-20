package mx.com.endtoend.domain.reports.sales.closingOperation.dto.report;

import com.fasterxml.jackson.annotation.JsonFormat;
import mx.com.smartbussiness.generic.utils.DecimalPrecisionUtils;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

import mx.com.endtoend.domain.users.dto.UserDto;

public class BranchClosingOperationSummary {

	private String empooyeeName;

	private String employeeNumber;

	private List<BranchClosingDetail> closingReportDetail;

	@NotNull(message = "El total teórico es obligatorio")
	@DecimalMin(value = "0.0", message = "El total teórico debe ser mayor o igual a 0")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private BigDecimal theoreticalTotal;

	@NotNull(message = "El total de cierre es obligatorio")
	@DecimalMin(value = "0.0", message = "El total de cierre debe ser mayor o igual a 0")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private BigDecimal closingTotal;

	@NotNull(message = "El total de diferencia es obligatorio")
	@DecimalMin(value = "0.0", message = "El total de diferencia debe ser mayor o igual a 0")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private BigDecimal differenceTotal;

	@NotNull(message = "El monto total es obligatorio")
	@DecimalMin(value = "0.0", message = "El monto total debe ser mayor o igual a 0")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private BigDecimal amountTotal;

	public BranchClosingOperationSummary(UserDto user, List<BranchClosingDetail> closingReportDetail) {
		super();
		this.empooyeeName = user.getName() + " " + user.getFirstSurname() + " " + user.getSecondSurname();
		this.employeeNumber = user.getUserNumber().toString();
		this.closingReportDetail = closingReportDetail;

		BigDecimal theoreticalTotal = BigDecimal.ZERO;
		BigDecimal closingTotal = BigDecimal.ZERO;
		BigDecimal differenceTotal = BigDecimal.ZERO;
		BigDecimal amountTotal = BigDecimal.ZERO;
		
		for (BranchClosingDetail branchClosingDetail : closingReportDetail) {

			theoreticalTotal = theoreticalTotal.add(DecimalPrecisionUtils.roundToTwoDecimals(branchClosingDetail.getTheoreticalAmount()));
			closingTotal = closingTotal.add(DecimalPrecisionUtils.roundToTwoDecimals(branchClosingDetail.getClosingAmount()));
			differenceTotal = differenceTotal.add(DecimalPrecisionUtils.roundToTwoDecimals(branchClosingDetail.getDifferenceAmount()));
			amountTotal = amountTotal.add(DecimalPrecisionUtils.roundToTwoDecimals(branchClosingDetail.getAmountTotal()));

		}
		this.theoreticalTotal = DecimalPrecisionUtils.roundToTwoDecimals(theoreticalTotal);
		this.closingTotal = DecimalPrecisionUtils.roundToTwoDecimals(closingTotal);
		this.differenceTotal = DecimalPrecisionUtils.roundToTwoDecimals(differenceTotal);
		this.amountTotal = DecimalPrecisionUtils.roundToTwoDecimals(amountTotal);
		
	}

	public String getEmpooyeeName() {
		return empooyeeName;
	}

	public void setEmpooyeeName(String empooyeeName) {
		this.empooyeeName = empooyeeName;
	}

	public String getEmployeeNumber() {
		return employeeNumber;
	}

	public void setEmployeeNumber(String employeeNumber) {
		this.employeeNumber = employeeNumber;
	}

	public List<BranchClosingDetail> getClosingReportDetail() {
		return closingReportDetail;
	}

	public void setClosingReportDetail(List<BranchClosingDetail> closingReportDetail) {
		this.closingReportDetail = closingReportDetail;
	}

	public BigDecimal getTheoreticalTotal() {
		return theoreticalTotal;
	}

	public void setTheoreticalTotal(BigDecimal theoreticalTotal) {
		this.theoreticalTotal = DecimalPrecisionUtils.roundToTwoDecimals(theoreticalTotal);
	}

	public BigDecimal getClosingTotal() {
		return closingTotal;
	}

	public void setClosingTotal(BigDecimal closingTotal) {
		this.closingTotal = DecimalPrecisionUtils.roundToTwoDecimals(closingTotal);
	}

	public BigDecimal getDifferenceTotal() {
		return differenceTotal;
	}

	public void setDifferenceTotal(BigDecimal differenceTotal) {
		this.differenceTotal = DecimalPrecisionUtils.roundToTwoDecimals(differenceTotal);
	}

	public BigDecimal getAmountTotal() {
		return amountTotal;
	}

	public void setAmountTotal(BigDecimal amountTotal) {
		this.amountTotal = DecimalPrecisionUtils.roundToTwoDecimals(amountTotal);
	}

	@Override
	public String toString() {
		return "BranchClosingOperationSummary [empooyeeName=" + empooyeeName + ", employeeNumber=" + employeeNumber
				+ ", closingReportDetail=" + closingReportDetail + ", theoreticalTotal=" + theoreticalTotal
				+ ", closingTotal=" + closingTotal + ", differenceTotal=" + differenceTotal + ", amountTotal="
				+ amountTotal + "]";
	}

}
