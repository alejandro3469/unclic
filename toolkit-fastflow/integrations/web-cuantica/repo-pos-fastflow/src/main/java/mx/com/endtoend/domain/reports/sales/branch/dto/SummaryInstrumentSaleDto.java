package mx.com.endtoend.domain.reports.sales.branch.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import mx.com.smartbussiness.generic.utils.DecimalPrecisionUtils;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

/**
 * Clase modelo con el resumen operativo por instrumento de cobro y su detalle
 * de las ventas del sistema
 * 
 * @author ddcasas
 *
 */
public class SummaryInstrumentSaleDto {

	private String instrument;

	private Long ticketAmount;

	@NotNull(message = "El monto total es obligatorio")
	@DecimalMin(value = "0.0", message = "El monto total debe ser mayor o igual a 0")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private BigDecimal totalAmount;

	private List<SummarySaleBranchDto> summaryDetail;

	public SummaryInstrumentSaleDto(String instrument, List<SummarySaleBranchDto> summaryDetail) {
		super();
		this.instrument = instrument;
		this.ticketAmount = (long) summaryDetail.size();
		this.totalAmount = obtainTotalAmount(summaryDetail);
		this.summaryDetail = summaryDetail;
	}

	private BigDecimal obtainTotalAmount(List<SummarySaleBranchDto> summaryDetail) {
		BigDecimal sum = BigDecimal.ZERO;
		for (SummarySaleBranchDto summarySaleBranchDto : summaryDetail) {
			sum = sum.add(DecimalPrecisionUtils.roundToTwoDecimals(summarySaleBranchDto.getTotalAmountReceived()));
		}
		return DecimalPrecisionUtils.roundToTwoDecimals(sum);
	}

	public String getInstrument() {
		return instrument;
	}

	public Long getTicketAmount() {
		return ticketAmount;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public List<SummarySaleBranchDto> getSummaryDetail() {
		return summaryDetail;
	}

	public void setInstrument(String instrument) {
		this.instrument = instrument;
	}

	public void setTicketAmount(Long ticketAmount) {
		this.ticketAmount = ticketAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = DecimalPrecisionUtils.roundToTwoDecimals(totalAmount);
	}

	public void setSummaryDetail(List<SummarySaleBranchDto> summaryDetail) {
		this.summaryDetail = summaryDetail;
	}

	@Override
	public String toString() {
		return "SummaryInstrumentSaleDto [instrument=" + instrument + ", ticketAmount=" + ticketAmount
				+ ", totalAmount=" + totalAmount + ", summaryDetail=" + summaryDetail + "]";
	}

}
