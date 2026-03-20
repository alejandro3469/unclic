package mx.com.endtoend.domain.reports.sales.branch.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import mx.com.smartbussiness.generic.utils.DecimalPrecisionUtils;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

import mx.com.endtoend.domain.branch.dto.BranchDto;

/**
 * Clase modelo para la generación del reporte de ventas por sucursal y por
 * instrumento de cobro
 * 
 * @author ddcasas
 *
 */
public class BranchSaleSummaryDto {

	private String branchName;

	private String branchCode;

	private List<SummaryInstrumentSaleDto> instrumentDetail;

	@NotNull(message = "El total de la sucursal es obligatorio")
	@DecimalMin(value = "0.0", message = "El total de la sucursal debe ser mayor o igual a 0")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private BigDecimal branchTotal;

	public BranchSaleSummaryDto(BranchDto branchDto, List<SummaryInstrumentSaleDto> instrumentDetail) {

		this.branchName = branchDto != null ? branchDto.getName() : "";
		this.branchCode = branchDto != null ? branchDto.getCode() : "";
		this.instrumentDetail = instrumentDetail;
		
		BigDecimal total = BigDecimal.ZERO;
		
		for (SummaryInstrumentSaleDto summaryInstrumentSaleDto : instrumentDetail) {
			total = total.add(DecimalPrecisionUtils.roundToTwoDecimals(summaryInstrumentSaleDto.getTotalAmount()));
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

	public List<SummaryInstrumentSaleDto> getInstrumentDetail() {
		return instrumentDetail;
	}

	public void setInstrumentDetail(List<SummaryInstrumentSaleDto> instrumentDetail) {
		this.instrumentDetail = instrumentDetail;
	}

	public BigDecimal getBranchTotal() {
		return branchTotal;
	}

	public void setBranchTotal(BigDecimal branchTotal) {
		this.branchTotal = DecimalPrecisionUtils.roundToTwoDecimals(branchTotal);
	}

	@Override
	public String toString() {
		return "BranchSaleSummaryDto [branchName=" + branchName + ", branchCode=" + branchCode + ", instrumentDetail="
				+ instrumentDetail + ", branchTotal=" + branchTotal + "]";
	}

}
