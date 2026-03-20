package mx.com.endtoend.domain.reports.sales.articles.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import mx.com.smartbussiness.generic.utils.DecimalPrecisionUtils;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

import mx.com.endtoend.domain.branch.dto.BranchDto;

public class ArticleBranchSummaryDto {

	private String branchName;

	private String branchCode;

	private List<ArticleSaleDetailDto> articleDetail;

	@NotNull(message = "El total de la sucursal es obligatorio")
	@DecimalMin(value = "0.0", message = "El total de la sucursal debe ser mayor o igual a 0")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private BigDecimal branchTotal;

	public ArticleBranchSummaryDto(BranchDto branchDto, SaleReportArticleParamsDto saleReportArticleParams,
			List<ArticleSaleDetailDto> articleDetail) {
		this.branchName = branchDto != null ? branchDto.getName() : "";
		this.branchCode = branchDto != null ? branchDto.getCode() : saleReportArticleParams.getBranchCode();
		this.articleDetail = articleDetail;

		BigDecimal total = BigDecimal.ZERO;

		for (ArticleSaleDetailDto articleSaleDetailDto : articleDetail) {
			total = total.add(DecimalPrecisionUtils.roundToTwoDecimals(articleSaleDetailDto.getSubTotalTax()));
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

	public List<ArticleSaleDetailDto> getArticleDetail() {
		return articleDetail;
	}

	public void setArticleDetail(List<ArticleSaleDetailDto> articleDetail) {
		this.articleDetail = articleDetail;
	}

	public BigDecimal getBranchTotal() {
		return branchTotal;
	}

	public void setBranchTotal(BigDecimal branchTotal) {
		this.branchTotal = DecimalPrecisionUtils.roundToTwoDecimals(branchTotal);
	}

	@Override
	public String toString() {
		return "ArticleBranchSummaryDto [branchName=" + branchName + ", branchCode=" + branchCode + ", articleDetail="
				+ articleDetail + ", branchTotal=" + branchTotal + "]";
	}

}
