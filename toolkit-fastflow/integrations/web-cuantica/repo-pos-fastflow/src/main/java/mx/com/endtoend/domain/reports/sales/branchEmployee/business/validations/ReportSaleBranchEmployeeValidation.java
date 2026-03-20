package mx.com.endtoend.domain.reports.sales.branchEmployee.business.validations;

import mx.com.endtoend.domain.reports.sales.branchEmployee.dto.SaleReportBranchEmployeeParamsDto;

/**
 * Clase para validar los datos operativos en la generación del reporte sucursal
 * y empleados
 * 
 * @author ddcasas
 *
 */
public class ReportSaleBranchEmployeeValidation {

	public String validOperativeData(SaleReportBranchEmployeeParamsDto reportBranchEmployeeParamsDto) {

		String validations = "";

		validations = reportBranchEmployeeParamsDto.getEmployeeEmail() == null ? validations += " INVALID EMAIL "
				: reportBranchEmployeeParamsDto.getEmployeeEmail().isEmpty() ? validations += " INVALID EMAIL "
						: validations;

		validations = reportBranchEmployeeParamsDto.getBranchCode() == null ? validations += " INVALID BRANCH "
				: validations;

		return validations;

	}

}
