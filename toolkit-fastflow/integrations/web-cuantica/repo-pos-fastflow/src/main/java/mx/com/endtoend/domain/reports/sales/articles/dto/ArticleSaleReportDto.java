package mx.com.endtoend.domain.reports.sales.articles.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import mx.com.smartbussiness.generic.utils.DecimalPrecisionUtils;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ArticleSaleReportDto {

	private String dateFrom;

	private String dateTo;

	private String generateDate;

	private List<ArticleBranchSummaryDto> articleBranchDetail;

	@NotNull(message = "El monto total es obligatorio")
	@DecimalMin(value = "0.0", message = "El monto total debe ser mayor o igual a 0")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private BigDecimal amountTotal;

	public ArticleSaleReportDto(SaleReportArticleParamsDto saleReportArticleParams,
			List<ArticleBranchSummaryDto> articleBranchDetail) {

		this.dateFrom = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(saleReportArticleParams.getStartDate());
		this.dateTo = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(saleReportArticleParams.getEndDate());
		this.generateDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());

		this.articleBranchDetail = articleBranchDetail;

		BigDecimal total = BigDecimal.ZERO;

		for (ArticleBranchSummaryDto articleBranchSummaryDto : articleBranchDetail) {
			total = total.add(DecimalPrecisionUtils.roundToTwoDecimals(articleBranchSummaryDto.getBranchTotal()));
		}

		this.amountTotal = DecimalPrecisionUtils.roundToTwoDecimals(total);

	}

	public String getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(String dateFrom) {
		this.dateFrom = dateFrom;
	}

	public String getDateTo() {
		return dateTo;
	}

	public void setDateTo(String dateTo) {
		this.dateTo = dateTo;
	}

	public String getGenerateDate() {
		return generateDate;
	}

	public void setGenerateDate(String generateDate) {
		this.generateDate = generateDate;
	}

	public List<ArticleBranchSummaryDto> getArticleBranchDetail() {
		return articleBranchDetail;
	}

	public void setArticleBranchDetail(List<ArticleBranchSummaryDto> articleBranchDetail) {
		this.articleBranchDetail = articleBranchDetail;
	}

	public BigDecimal getAmountTotal() {
		return amountTotal;
	}

	public void setAmountTotal(BigDecimal amountTotal) {
		this.amountTotal = DecimalPrecisionUtils.roundToTwoDecimals(amountTotal);
	}

	@Override
	public String toString() {
		return "ArticleSaleReportDto [dateFrom=" + dateFrom + ", dateTo=" + dateTo + ", generateDate=" + generateDate
				+ ", articleBranchDetail=" + articleBranchDetail + ", amountTotal=" + amountTotal + "]";
	}

}
