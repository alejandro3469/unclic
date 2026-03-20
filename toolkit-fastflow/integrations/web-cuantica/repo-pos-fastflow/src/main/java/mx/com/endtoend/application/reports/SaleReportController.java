package mx.com.endtoend.application.reports;

import static mx.com.endtoend.genericCommonsFileds.utilities.ResponseLog.generateIdOperation;

import mx.com.endtoend.domain.reports.sales.branchEmployee.dto.SaleAndNotesReportParamsDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mx.com.endtoend.domain.branch.ports.spi.BranchPersistencePort;
import mx.com.endtoend.domain.clients.ports.spi.ClientPersistencePort;
import mx.com.endtoend.domain.commons.constants.DocumentFormatEnum;
import mx.com.endtoend.domain.company.dto.MethodDto;
import mx.com.endtoend.domain.company.ports.api.CompanyServicePort;
import mx.com.endtoend.domain.payments.ports.PaymentPersistencePort;
import mx.com.endtoend.domain.reports.sales.articles.dto.SaleReportArticleParamsDto;
import mx.com.endtoend.domain.reports.sales.articles.ports.ReportSaleArticleServicePort;
import mx.com.endtoend.domain.reports.sales.branch.dto.GenericSearchSaleReportParamsDto;
import mx.com.endtoend.domain.reports.sales.branch.ports.SaleReportServicePort;
import mx.com.endtoend.domain.reports.sales.branchEmployee.dto.SaleReportBranchEmployeeParamsDto;
import mx.com.endtoend.domain.reports.sales.branchEmployee.ports.ReportSaleBranchEmployeeServicePort;
import mx.com.endtoend.domain.reports.sales.closingOperation.dto.ClosingOperationReportParamsDto;
import mx.com.endtoend.domain.reports.sales.closingOperation.ports.ReportClosingOperationServicePort;
import mx.com.endtoend.domain.reports.sales.models.SaleReportInterfaceService;
import mx.com.endtoend.domain.users.ports.spi.UserPersistencePort;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.GlobalError;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.ValidationError;
import mx.com.endtoend.infrastructure.services.jde.clients.common.serviceport.ClientOracleServicePort;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

@RestController
@RequestMapping("/report-sale")
public class SaleReportController {

	@Autowired
	private CompanyServicePort companyServicePort;

	@Autowired
	private SaleReportServicePort saleReportServicePort;

	@Autowired
	private ReportSaleArticleServicePort reportSaleArticleServicePort;

	@Autowired
	private ReportSaleBranchEmployeeServicePort reportSaleBranchEmployeeServicePort;

	@Autowired
	private ReportClosingOperationServicePort reportClosingOperationServicePort;

	@Autowired
	private PaymentPersistencePort paymentPersistencePort;

	@Autowired
	private ClientPersistencePort clientPersistencePort;

	@Autowired
	private ClientOracleServicePort clientOracleServicePort;

	@Autowired
	private BranchPersistencePort branchPersistencePort;

    @Autowired
	private UserPersistencePort userPersistencePort;

	public String idOperation = "";

	private String module = "REPORT_SALE";
	private String module_branch_employee = "REPORT_SALE_BRANCH_EMPLOYEE";
	private String module_sale_article = "REPORT_SALE_ARTICLE";
	private String module_closing = "REPORT_CLOSING_OPERATION";

	private final Logger LOG = LoggerFactory.getLogger(SaleReportController.class);

	@PostMapping("/branch/{companyCode}/{branchCode}")
	public ResponseEntity<?> generateReportBySaleBranchAndCompanyCode(@PathVariable String companyCode,
			@PathVariable String branchCode, @RequestBody GenericSearchSaleReportParamsDto saleReportParamsDto) {

		idOperation = generateIdOperation(companyCode, branchCode, module);
		LOG.info(String.format("%s INIT generateReportBySaleBranchAndCompanyCode() ", idOperation));
		LOG.info(String.format("%s PARAMS: [saleReportParamsDto: %s , companyCode: %s , branchCode: %s ] ", idOperation,
				saleReportParamsDto.toString(), companyCode, branchCode));

		MethodDto method = (MethodDto) companyServicePort
				.findMethodByCompanyCodeAndModule(companyCode, module, idOperation).getData();
		if (method == null) {
			LOG.info(String.format("%s ERROR IN ASSIGNATION METHODS OF THE COMPANY %s ", idOperation, companyCode));
			throw new GlobalError();
		}

		SaleReportInterfaceService saleReportInterface = new SaleReportInterfaceService(paymentPersistencePort,
				clientPersistencePort, clientOracleServicePort, branchPersistencePort);

		ResponseModel responseModel = saleReportServicePort.generateReportBySaleBranchAndCompanyCode(
				saleReportParamsDto, saleReportInterface, method.getCode(), companyCode, idOperation);

		byte[] reporte = (byte[]) responseModel.getData();

		if (saleReportParamsDto.getFormat().equalsIgnoreCase(DocumentFormatEnum.PDF.toString())) {
			ContentDisposition contentDisposition = ContentDisposition.builder("attachment")
					.filename("sale-branch-report.pdf").build();
			HttpHeaders headers = new HttpHeaders();
			headers.setContentDisposition(contentDisposition);

			return ResponseEntity.ok().contentLength((long) reporte.length).contentType(MediaType.APPLICATION_PDF)
					.headers(headers).body(new ByteArrayResource(reporte));
		}

		else if (saleReportParamsDto.getFormat().equalsIgnoreCase(DocumentFormatEnum.XLS.toString())) {
			LOG.info(String.format("%s SEND FROM CONTROLLER - EXCEL", idOperation));
			HttpHeaders headers = new HttpHeaders();
			headers.set("Content-Type",
					"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet; charset=UTF-8");
			ContentDisposition contentDisposition = ContentDisposition.builder("attachment")
					.filename("sale-branch-report.xls").build();
			headers.setContentDisposition(contentDisposition);
			return ResponseEntity.ok().headers(headers).body(reporte);
		}

		else {
			LOG.warn(String.format("%s ERROR IN FORMAT", idOperation));
			throw new ValidationError("INVALID DOCUMENT FORMAT");
		}
	}

	@PostMapping("/branch-employee/{companyCode}/{branchCode}")
	public ResponseEntity<?> generateReportBySaleBranchEmployeeAndCompanyCode(@PathVariable String companyCode,
			@PathVariable String branchCode, @RequestBody SaleReportBranchEmployeeParamsDto employeeParamsDto) {

		idOperation = generateIdOperation(companyCode, branchCode, module_branch_employee);
		LOG.info(String.format("%s INIT generateReportBySaleBranchEmployeeAndCompanyCode() ", idOperation));
		LOG.info(String.format("%s PARAMS: [employeeParamsDto: %s , companyCode: %s , branchCode: %s ] ", idOperation,
				employeeParamsDto.toString(), companyCode, branchCode));

		MethodDto method = (MethodDto) companyServicePort
				.findMethodByCompanyCodeAndModule(companyCode, module_branch_employee, idOperation).getData();
		if (method == null) {
			LOG.info(String.format("%s ERROR IN ASSIGNATION METHODS OF THE COMPANY %s ", idOperation, companyCode));
			throw new GlobalError();
		}

		SaleReportInterfaceService saleReportInterface = new SaleReportInterfaceService(paymentPersistencePort,
				clientPersistencePort, clientOracleServicePort, branchPersistencePort, userPersistencePort);

		ResponseModel responseModel = reportSaleBranchEmployeeServicePort.generateReportByParamsAndCompanyCode(
				employeeParamsDto, saleReportInterface, method.getCode(), companyCode, idOperation);

		byte[] reporte = (byte[]) responseModel.getData();

		if (employeeParamsDto.getFormat().equalsIgnoreCase(DocumentFormatEnum.PDF.toString())) {
			ContentDisposition contentDisposition = ContentDisposition.builder("attachment")
					.filename("sale-branch-employee-report.pdf").build();
			HttpHeaders headers = new HttpHeaders();
			headers.setContentDisposition(contentDisposition);

			return ResponseEntity.ok().contentLength((long) reporte.length).contentType(MediaType.APPLICATION_PDF)
					.headers(headers).body(new ByteArrayResource(reporte));
		}

		else if (employeeParamsDto.getFormat().equalsIgnoreCase(DocumentFormatEnum.XLS.toString())) {
			LOG.info(String.format("%s SEND FROM CONTROLLER - EXCEL", idOperation));
			HttpHeaders headers = new HttpHeaders();
			headers.set("Content-Type",
					"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet; charset=UTF-8");
			ContentDisposition contentDisposition = ContentDisposition.builder("attachment")
					.filename("sale-branch-employee-report.xls").build();
			headers.setContentDisposition(contentDisposition);
			return ResponseEntity.ok().headers(headers).body(reporte);
		}

		else {
			LOG.warn(String.format("%s ERROR IN FORMAT", idOperation));
			throw new ValidationError("INVALID DOCUMENT FORMAT");
		}

	}

    @PostMapping("/branch-employee/{companyCode}/{branchCode}/v2")
    public ResponseEntity<?> generateReportBySaleBranchEmployeeAndCompanyCodeV2(@PathVariable String companyCode,
                                                                              @PathVariable String branchCode, @RequestBody SaleAndNotesReportParamsDto employeeParamsDto) {

        idOperation = generateIdOperation(companyCode, branchCode, module_branch_employee);
        LOG.info(String.format("%s INIT generateReportBySaleBranchEmployeeAndCompanyCodeV2() ", idOperation));
        LOG.info(String.format("%s PARAMS v2: [employeeParamsDto: %s , companyCode: %s , branchCode: %s ] ", idOperation,
                employeeParamsDto.toString(), companyCode, branchCode));

        MethodDto method = (MethodDto) companyServicePort
                .findMethodByCompanyCodeAndModule(companyCode, module_branch_employee, idOperation).getData();
        if (method == null) {
            LOG.info(String.format("%s ERROR IN ASSIGNATION METHODS OF THE COMPANY %s ", idOperation, companyCode));
            throw new GlobalError();
        }

        SaleReportInterfaceService saleReportInterface = new SaleReportInterfaceService(paymentPersistencePort,
                clientPersistencePort, clientOracleServicePort, branchPersistencePort, userPersistencePort);

        ResponseModel responseModel = reportSaleBranchEmployeeServicePort.generateReportByParamsAndCompanyCodeV2(
                employeeParamsDto, saleReportInterface, method.getCode(), companyCode, idOperation);

        byte[] reporte = (byte[]) responseModel.getData();

        LOG.info(String.format("%s SEND FROM CONTROLLER - EXCEL", idOperation));
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type",
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet; charset=UTF-8");
        ContentDisposition contentDisposition = ContentDisposition.builder("attachment")
                .filename("sale-branch-employee-report.xls").build();
        headers.setContentDisposition(contentDisposition);
        return ResponseEntity.ok().headers(headers).body(reporte);
    }

	@PostMapping("/article/{companyCode}/{branchCode}")
	public ResponseEntity<?> generateReportSaleArticleByCompanyCode(@PathVariable String companyCode,
			@PathVariable String branchCode, @RequestBody SaleReportArticleParamsDto saleReportArticleParamsDto) {

		idOperation = generateIdOperation(companyCode, branchCode, module_sale_article);
		LOG.info(String.format("%s INIT generateReportSaleArticleByCompanyCode() ", idOperation));
		LOG.info(String.format("%s PARAMS: [saleReportArticleParamsDto: %s , companyCode: %s , branchCode: %s ] ",
				idOperation, saleReportArticleParamsDto.toString(), companyCode, branchCode));

		MethodDto method = (MethodDto) companyServicePort
				.findMethodByCompanyCodeAndModule(companyCode, module_sale_article, idOperation).getData();
		if (method == null) {
			LOG.info(String.format("%s ERROR IN ASSIGNATION METHODS OF THE COMPANY %s ", idOperation, companyCode));
			throw new GlobalError();
		}

		SaleReportInterfaceService saleReportInterface = new SaleReportInterfaceService(paymentPersistencePort,
				clientPersistencePort, clientOracleServicePort, branchPersistencePort, userPersistencePort);

		ResponseModel responseModel = reportSaleArticleServicePort.generateReportSaleArticleByParamsAndCompanyCode(
				saleReportArticleParamsDto, saleReportInterface, method.getCode(), companyCode, idOperation);

		byte[] reporte = (byte[]) responseModel.getData();

		if (saleReportArticleParamsDto.getFormat().equalsIgnoreCase(DocumentFormatEnum.PDF.toString())) {
			LOG.info(String.format("%s SEND FROM CONTROLLER - PDF", idOperation));
			ContentDisposition contentDisposition = ContentDisposition.builder("attachment")
					.filename("sale-article-report.pdf").build();
			HttpHeaders headers = new HttpHeaders();
			headers.setContentDisposition(contentDisposition);

			return ResponseEntity.ok().contentLength((long) reporte.length).contentType(MediaType.APPLICATION_PDF)
					.headers(headers).body(new ByteArrayResource(reporte));
		}

		else if (saleReportArticleParamsDto.getFormat().equalsIgnoreCase(DocumentFormatEnum.XLS.toString())) {
			LOG.info(String.format("%s SEND FROM CONTROLLER - EXCEL", idOperation));
			HttpHeaders headers = new HttpHeaders();
			headers.set("Content-Type",
					"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet; charset=UTF-8");
			ContentDisposition contentDisposition = ContentDisposition.builder("attachment")
					.filename("sale-article-report.xls").build();
			headers.setContentDisposition(contentDisposition);
			return ResponseEntity.ok().headers(headers).body(reporte);
		}

		else {
			LOG.warn(String.format("%s ERROR IN FORMAT", idOperation));
			throw new ValidationError("INVALID DOCUMENT FORMAT");
		}

	}

	@PostMapping("/closing-operation/{companyCode}/{branchCode}")
	public ResponseEntity<?> generateClosingReportByCompanyCode(
			@RequestBody ClosingOperationReportParamsDto closingOperationReportParamsDto,
			@PathVariable String companyCode, @PathVariable String branchCode) {
		idOperation = generateIdOperation(companyCode, branchCode, module_closing);
		LOG.info(String.format("%s INIT generateClosingReportByCompanyCode() ", idOperation));
		LOG.info(String.format("%s PARAMS: [closingOperationReportParamsDto: %s , companyCode: %s , branchCode: %s ] ",
				idOperation, closingOperationReportParamsDto.toString(), companyCode, branchCode));

		MethodDto method = (MethodDto) companyServicePort
				.findMethodByCompanyCodeAndModule(companyCode, module_closing, idOperation).getData();
		if (method == null) {
			LOG.info(String.format("%s ERROR IN ASSIGNATION METHODS OF THE COMPANY %s ", idOperation, companyCode));
			throw new GlobalError();
		}

		SaleReportInterfaceService saleReportInterface = new SaleReportInterfaceService(paymentPersistencePort,
				clientPersistencePort, clientOracleServicePort, branchPersistencePort, userPersistencePort);

		ResponseModel responseModel = reportClosingOperationServicePort
				.generateReportClosingOperationByParamsAndCompanyCode(closingOperationReportParamsDto,
						saleReportInterface, method.getCode(), companyCode, idOperation);

		byte[] reporte = (byte[]) responseModel.getData();

		if (closingOperationReportParamsDto.getFormat().equalsIgnoreCase(DocumentFormatEnum.PDF.toString())) {
			LOG.info(String.format("%s SEND FROM CONTROLLER - PDF", idOperation));
			ContentDisposition contentDisposition = ContentDisposition.builder("attachment")
					.filename("closing-operation-report.pdf").build();
			HttpHeaders headers = new HttpHeaders();
			headers.setContentDisposition(contentDisposition);

			return ResponseEntity.ok().contentLength((long) reporte.length).contentType(MediaType.APPLICATION_PDF)
					.headers(headers).body(new ByteArrayResource(reporte));
		}

		else if (closingOperationReportParamsDto.getFormat().equalsIgnoreCase(DocumentFormatEnum.XLS.toString())) {
			LOG.info(String.format("%s SEND FROM CONTROLLER - EXCEL", idOperation));
			HttpHeaders headers = new HttpHeaders();
			headers.set("Content-Type",
					"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet; charset=UTF-8");
			ContentDisposition contentDisposition = ContentDisposition.builder("attachment")
					.filename("closing-operation-report.xls").build();
			headers.setContentDisposition(contentDisposition);
			return ResponseEntity.ok().headers(headers).body(reporte);
		}

		else {
			LOG.warn(String.format("%s ERROR IN FORMAT", idOperation));
			throw new ValidationError("INVALID DOCUMENT FORMAT");
		}
	}

}
