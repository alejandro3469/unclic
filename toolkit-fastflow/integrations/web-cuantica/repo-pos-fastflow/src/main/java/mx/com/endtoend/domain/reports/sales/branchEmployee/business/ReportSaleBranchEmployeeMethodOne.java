package mx.com.endtoend.domain.reports.sales.branchEmployee.business;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import mx.com.endtoend.domain.reports.sales.branchEmployee.dto.*;
import mx.com.endtoend.domain.reports.sales.models.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.com.endtoend.domain.branch.dto.BranchDto;
import mx.com.endtoend.domain.branch.ports.spi.BranchPersistencePort;
import mx.com.endtoend.domain.reports.sales.branchEmployee.business.validations.ReportSaleBranchEmployeeValidation;
import mx.com.endtoend.domain.reports.sales.branchEmployee.ports.ReportSaleBranchEmployeePersistencePort;
import mx.com.endtoend.domain.users.dto.UserDto;
import mx.com.endtoend.domain.users.ports.spi.UserPersistencePort;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.ValidationError;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

public class ReportSaleBranchEmployeeMethodOne implements ReportSaleBranchEmployeeInterface {

	private BranchPersistencePort branchPersistencePort;

	private UserPersistencePort userPersistencePort;

	private ReportSaleBranchEmployeePersistencePort reportSaleBranchEmployeePersistencePort;

	public ReportSaleBranchEmployeeMethodOne(SaleReportInterfaceService saleReportInterfaceService) {

		this.branchPersistencePort = saleReportInterfaceService.getBranchPersistencePort();

		this.userPersistencePort = saleReportInterfaceService.getUserPersistencePort();

		this.reportSaleBranchEmployeePersistencePort = saleReportInterfaceService
				.getReportSaleBranchEmployeePersistencePort();

	}

	private ReportSaleBranchEmployeeValidation reportValidation = new ReportSaleBranchEmployeeValidation();

	private final Logger LOG = LoggerFactory.getLogger(ReportSaleBranchEmployeeMethodOne.class);

	@SuppressWarnings("unchecked")
	@Override
	public ResponseModel generateReportBranchEmployee(SaleReportBranchEmployeeParamsDto reportBranchEmployeeParamsDto,
			String companyCode, String idOperation) {

		LOG.info(String.format("%s INIT generateReportBranchEmployee()", idOperation));
		String validations = reportValidation.validOperativeData(reportBranchEmployeeParamsDto);
		if (!validations.isEmpty()) {
			LOG.warn(String.format("%s BAD PARAMS", idOperation));
			throw new ValidationError(validations);
		}

		List<BranchDto> branchList = new ArrayList<>();
		if (!reportBranchEmployeeParamsDto.getBranchCode().isEmpty()) {
			ResponseModel responseModel = branchPersistencePort.getBranchDetailByBranchCodeAndCompanyCode(
					reportBranchEmployeeParamsDto.getBranchCode(), companyCode, idOperation);
			BranchDto branch = (BranchDto) responseModel.getData();
			branchList.add(branch);
		} else {
			ResponseModel responseBranchList = branchPersistencePort.getBranchListByCompanyCode(companyCode,
					idOperation);
			branchList = (List<BranchDto>) responseBranchList.getData();
		}

		List<SaleBranchEmployeeDto> saleBranchEmployeeList = new ArrayList<>();
		for (BranchDto branchDto : branchList) {

			List<UserDto> employeeList = new ArrayList<>();
			reportBranchEmployeeParamsDto = generateSearchParamsByRoleJob(reportBranchEmployeeParamsDto, branchDto);
			getEmployeeNumberList(reportBranchEmployeeParamsDto, idOperation, employeeList);

			List<SummaryEmployeeSaleDto> summaryEmployeeSaleList = new ArrayList<>();
			for (UserDto userDto : employeeList) {
				List<SummarySaleBranchEmployeeDto> branchEmployeeList = new ArrayList<>();
				reportBranchEmployeeParamsDto.setEmployeeNumber(userDto.getUserNumber());
				generateOperativeDataByEmployee(reportBranchEmployeeParamsDto, companyCode, idOperation,
						branchEmployeeList);
				if (branchEmployeeList.size() > 0) {
					SummaryEmployeeSaleDto summaryEmployeeSale = new SummaryEmployeeSaleDto(
							userDto.getUserNumber() + "-" + userDto.getName() + " " + userDto.getFirstSurname() + " "
									+ userDto.getSecondSurname(),
							branchEmployeeList);
					summaryEmployeeSaleList.add(summaryEmployeeSale);
				}
			}

			if (summaryEmployeeSaleList.size() > 0)
				saleBranchEmployeeList.add(
						new SaleBranchEmployeeDto(branchDto, reportBranchEmployeeParamsDto, summaryEmployeeSaleList));
		}
		ReportBranchEmployeeDto reportBranchEmployee = new ReportBranchEmployeeDto(reportBranchEmployeeParamsDto,
				saleBranchEmployeeList);

		ResponseModel report = reportSaleBranchEmployeePersistencePort.generateSaleBranchEmployeeReportByCompanyCode(
				reportBranchEmployee, reportBranchEmployeeParamsDto.getFormat(), companyCode, idOperation);

		return report;
	}

    @Override
    public ResponseModel generateReportBranchEmployeeV2(SaleAndNotesReportParamsDto reportBranchEmployeeParamsDto,
                                                    String companyCode, String idOperation) {

        LOG.info(String.format("%s INIT generateReportBranchEmployeeV2()", idOperation));

        ResponseModel responseModel = reportSaleBranchEmployeePersistencePort
            .searchSaleCreditByEmployeeAndParamsAndCompanyCodeV2(reportBranchEmployeeParamsDto, companyCode, idOperation);

        String format = reportBranchEmployeeParamsDto.getFormat();
        ResponseModel report;

        if ("notas".equals(format)) {
            List<ReportNotesDto> summaryReportList = (List<ReportNotesDto>) responseModel.getData();
            report = reportSaleBranchEmployeePersistencePort.generateSCreditNotesReportByParams(
                    summaryReportList, format, companyCode, idOperation);
        } else {
            List<ReportSalesDto> summaryReportList = (List<ReportSalesDto>) responseModel.getData();
            report = reportSaleBranchEmployeePersistencePort.generateSaleBranchEmployeeReportByCompanyCodeV2(
					summaryReportList, format, companyCode, idOperation);
        }

        return report;
    }

	/**
	 * Método que realiza las consultas de los datos operativos del reporte por
	 * empleado y genera la lista resumen de los mismos
	 * 
	 * @param reportBranchEmployeeParamsDto
	 * @param companyCode
	 * @param idOperation
	 * @param branchEmployeeList
	 */
	@SuppressWarnings("unchecked")
	private void generateOperativeDataByEmployee(SaleReportBranchEmployeeParamsDto reportBranchEmployeeParamsDto,
			String companyCode, String idOperation, List<SummarySaleBranchEmployeeDto> branchEmployeeList) {

		ResponseModel responseSummaryCash = reportSaleBranchEmployeePersistencePort
				.searchSaleCashByEmployeeAndParamsAndCompanyCode(reportBranchEmployeeParamsDto, companyCode,
						idOperation);
		List<SummarySaleCashPaymentDto> summaryCashList = (List<SummarySaleCashPaymentDto>) responseSummaryCash
				.getData();
		for (SummarySaleCashPaymentDto summarySaleCashPayment : summaryCashList) {
			branchEmployeeList.add(new SummarySaleBranchEmployeeDto(summarySaleCashPayment));
		}

		ResponseModel responseSummaryCreditCard = reportSaleBranchEmployeePersistencePort
				.searchSaleCreditCardByEmployeeAndParamsAndCompanyCode(reportBranchEmployeeParamsDto, companyCode,
						idOperation);
		List<SummarySaleCreditCardPaymentDto> summaryCreditCardList = (List<SummarySaleCreditCardPaymentDto>) responseSummaryCreditCard
				.getData();
		for (SummarySaleCreditCardPaymentDto summarySaleCreditCardPayment : summaryCreditCardList) {
			branchEmployeeList.add(new SummarySaleBranchEmployeeDto(summarySaleCreditCardPayment));
		}

		ResponseModel responseSummaryCreditNote = reportSaleBranchEmployeePersistencePort
				.searchSaleCredittNoteByEmployeeAndParamsAndCompanyCode(reportBranchEmployeeParamsDto, companyCode,
						idOperation);
		List<SummarySaleCreditNotePaymentDto> summaryCreditNoteList = (List<SummarySaleCreditNotePaymentDto>) responseSummaryCreditNote
				.getData();
		for (SummarySaleCreditNotePaymentDto summarySaleCreditNotePayment : summaryCreditNoteList) {
			branchEmployeeList.add(new SummarySaleBranchEmployeeDto(summarySaleCreditNotePayment));
		}

		ResponseModel responseSummaryTransfer = reportSaleBranchEmployeePersistencePort
				.searchSaleTransferByEmployeeAndParamsAndCompanyCode(reportBranchEmployeeParamsDto, companyCode,
						idOperation);
		List<SummarySaleTransferPaymentDto> summaryTransferList = (List<SummarySaleTransferPaymentDto>) responseSummaryTransfer
				.getData();
		for (SummarySaleTransferPaymentDto summarySaleTransferPayment : summaryTransferList) {
			branchEmployeeList.add(new SummarySaleBranchEmployeeDto(summarySaleTransferPayment));
		}

		ResponseModel responseSummaryCheck = reportSaleBranchEmployeePersistencePort
				.searchSaleCheckByEmployeeAndParamsAndCompanyCode(reportBranchEmployeeParamsDto, companyCode,
						idOperation);
		List<SummarySaleCheckPaymentDto> summaryCheckList = (List<SummarySaleCheckPaymentDto>) responseSummaryCheck
				.getData();
		for (SummarySaleCheckPaymentDto summarySaleCheckPayment : summaryCheckList) {
			branchEmployeeList.add(new SummarySaleBranchEmployeeDto(summarySaleCheckPayment));
		}

        //  List<SummarySaleCreditPaymentDto>
		ResponseModel responseSummaryCredit = reportSaleBranchEmployeePersistencePort
				.searchSaleCreditByEmployeeAndParamsAndCompanyCode(reportBranchEmployeeParamsDto, companyCode,
						idOperation);
		List<SummarySaleCreditPaymentDto> summaryCreditList = (List<SummarySaleCreditPaymentDto>) responseSummaryCredit
				.getData();
		for (SummarySaleCreditPaymentDto summarySaleCreditPayment : summaryCreditList) {
			branchEmployeeList.add(new SummarySaleBranchEmployeeDto(summarySaleCreditPayment));
		}
	}


	/**
	 * Método que genera la lista de identificadores de empleados a consultar para
	 * la generación del reporte
	 * 
	 * @param reportBranchEmployeeParamsDto
	 * @param idOperation
	 * @param employeeNumberList
	 */
	@SuppressWarnings("unchecked")
	private void getEmployeeNumberList(SaleReportBranchEmployeeParamsDto reportBranchEmployeeParamsDto,
			String idOperation, List<UserDto> employeeList) {
		if (reportBranchEmployeeParamsDto.getEmployeeNumber() != null) {
			ResponseModel responseUser = userPersistencePort.findByUserNumberAndBranchCode(
					reportBranchEmployeeParamsDto.getEmployeeNumber(), reportBranchEmployeeParamsDto.getBranchCode(),
					idOperation);
			if (responseUser.getData() != null) {
				UserDto user = (UserDto) responseUser.getData();
				employeeList.add(user);
			}
		} else {

			List<UserDto> userActiveList = (List<UserDto>) userPersistencePort
					.findAllByEnableAndBranchCode(true, reportBranchEmployeeParamsDto.getBranchCode(), idOperation)
					.getData();
			employeeList.addAll(userActiveList);

			List<UserDto> userInactiveList = (List<UserDto>) userPersistencePort
					.findAllByEnableAndBranchCode(false, reportBranchEmployeeParamsDto.getBranchCode(), idOperation)
					.getData();
			employeeList.addAll(userInactiveList);
		}
	}

    private void getEmployeeNumberListV2(SaleAndNotesReportParamsDto reportBranchEmployeeParamsDto,
                                       String idOperation, List<UserDto> employeeList) {
          List<UserDto> userActiveList = (List<UserDto>) userPersistencePort
                  .findAllByEnableAndBranchCode(true, reportBranchEmployeeParamsDto.getBranchCode(), idOperation)
                  .getData();
          employeeList.addAll(userActiveList);
          List<UserDto> userInactiveList = (List<UserDto>) userPersistencePort
                  .findAllByEnableAndBranchCode(false, reportBranchEmployeeParamsDto.getBranchCode(), idOperation)
                  .getData();
          employeeList.addAll(userInactiveList);
    }

	/**
	 * Método que evalua el rol operativa para la asinganación de valores de
	 * busqueda en rango de fechas y parámetros por defecto.
	 * 
	 * @param reportBranchEmployeeParamsDto
	 * @param branchDto
	 * @return
	 */
	private SaleReportBranchEmployeeParamsDto generateSearchParamsByRoleJob(
			SaleReportBranchEmployeeParamsDto reportBranchEmployeeParamsDto, BranchDto branchDto) {

		if (reportBranchEmployeeParamsDto.getStartDate() == null)
			reportBranchEmployeeParamsDto.setStartDate(getDefautlStartDate());

		if (reportBranchEmployeeParamsDto.getEndDate() == null)
			reportBranchEmployeeParamsDto.setEndDate(getDefaultEndDate());

		reportBranchEmployeeParamsDto.setBranchCode(branchDto.getCode());

		return reportBranchEmployeeParamsDto;
	}

    /**
     * Método que evalua el rol operativa para la asinganación de valores de
     * busqueda en rango de fechas y parámetros por defecto.
     *
     * @param reportBranchEmployeeParamsDto
     * @param branchDto
     * @return
     */
    private SaleAndNotesReportParamsDto generateSearchParamsByRoleJobV2(
            SaleAndNotesReportParamsDto reportBranchEmployeeParamsDto, BranchDto branchDto) {

        if (reportBranchEmployeeParamsDto.getStartDate() == null)
            reportBranchEmployeeParamsDto.setStartDate(getDefautlStartDate());

        if (reportBranchEmployeeParamsDto.getEndDate() == null)
            reportBranchEmployeeParamsDto.setEndDate(getDefaultEndDate());

        reportBranchEmployeeParamsDto.setBranchCode(branchDto.getCode());

        return reportBranchEmployeeParamsDto;
    }

	/***
	 * Método que retorna la fecha de inicio para la busqueda de datos, se toma el
	 * día primero del año en curso
	 * 
	 * @return
	 */
	private Date getDefautlStartDate() {
		Date from = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.set(Calendar.MONTH, 0);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		from = cal.getTime();
		return from;
	}

	/**
	 * Método que retorna la fecha de fin para la busqueda de datos, se toma el día
	 * actual en que se genera el reporte
	 * 
	 * @return
	 */
	@SuppressWarnings("deprecation")
	private Date getDefaultEndDate() {
		Date to = new Date();
		Calendar calTo = Calendar.getInstance();
		calTo.setTime(new Date());
		calTo.set(Calendar.MONTH, to.getMonth());
		calTo.set(Calendar.HOUR_OF_DAY, 23);
		calTo.set(Calendar.MINUTE, 59);
		calTo.set(Calendar.SECOND, 59);
		calTo.set(Calendar.MILLISECOND, 59);
		to = calTo.getTime();
		return to;
	}
}
