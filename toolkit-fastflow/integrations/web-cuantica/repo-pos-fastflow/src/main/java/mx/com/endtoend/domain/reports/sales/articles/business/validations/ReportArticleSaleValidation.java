package mx.com.endtoend.domain.reports.sales.articles.business.validations;

import mx.com.endtoend.domain.reports.sales.articles.dto.SaleReportArticleParamsDto;

/**
 * Clase para validación de datos operativos en la generación del reporte venta
 * de artículos
 * 
 * @author ddcasas
 *
 */
public class ReportArticleSaleValidation {

	/**
	 * Método que valida que los datos de busqueda para la generación del reporte
	 * sean correctos
	 * 
	 * @param saleReportArticleParamsDto
	 * @return
	 */
	public String validOperativeDataToGenerateBranchSaleReport(SaleReportArticleParamsDto saleReportArticleParamsDto) {

		String validations = "";

		validations = saleReportArticleParamsDto.getEmployeeEmail() == null ? validations += " INVALID EMAIL "
				: saleReportArticleParamsDto.getEmployeeEmail().isEmpty() ? validations += " INVALID EMAIL "
						: validations;

		validations = saleReportArticleParamsDto.getBranchCode() == null ? validations += " INVALID BRANCH "
				: validations;

		return validations;
	}

}
