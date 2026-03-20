package mx.com.endtoend.domain.reports.sales.branch.business.validations;

import mx.com.endtoend.domain.reports.sales.branch.dto.GenericSearchSaleReportParamsDto;

/**
 * Clase para validación de datos operativos en la generación de reportes de
 * ventas
 * 
 * @author ddcasas
 *
 */
public class ReportSaleValidation {

	/**
	 * Método que valida que los datos de busqueda para la generación del reporte
	 * sean correctos
	 * 
	 * @param saleReportParams
	 * @return
	 */
	public String validOperativeDataToGenerateBranchSaleReport(GenericSearchSaleReportParamsDto saleReportParams) {

		String validations = "";

		validations += saleReportParams.getBranchCode() == null ? "BRANCH IS REQUIRED" : "";

		validations = saleReportParams.getEmployeeEmail() == null ? validations += " INVALID EMAIL "
				: saleReportParams.getEmployeeEmail().isEmpty() ? validations += " INVALID EMAIL " : validations;

		return validations;
	}

}
