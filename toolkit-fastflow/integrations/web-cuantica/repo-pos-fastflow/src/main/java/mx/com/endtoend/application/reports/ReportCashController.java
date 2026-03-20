package mx.com.endtoend.application.reports;

import static mx.com.endtoend.genericCommonsFileds.utilities.ResponseLog.generateIdOperation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mx.com.endtoend.domain.company.dto.MethodDto;
import mx.com.endtoend.domain.company.ports.api.CompanyServicePort;
import mx.com.endtoend.domain.reports.cash.ports.ReportCashServicePort;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.GlobalError;
import mx.com.endtoend.infrastructure.services.email.EmailServicePort;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

@RestController
@RequestMapping("/report-cash")
public class ReportCashController {

	@Autowired
	private CompanyServicePort companyServicePort;

	@Autowired
	private ReportCashServicePort reportCashServicePort;
	
	@Autowired
	private EmailServicePort emailServicePort;

	public String idOperation = "";
	private String module = "REPORT_CASH";
	private final Logger LOG = LoggerFactory.getLogger(ReportCashController.class);

	@GetMapping("/opening-operation/{emnployeeEmail}/{companyCode}/{branchCode}")
	public ResponseEntity<?> generateOpeningOperationReportByEmailAndBrachCode(@PathVariable String emnployeeEmail,
			@PathVariable String companyCode, @PathVariable String branchCode) {
		idOperation = generateIdOperation(companyCode, branchCode, module);
		LOG.info(String.format("%s INIT generateOpeningOperationReportByEmailAndBrachCode() ", idOperation));
		LOG.info(String.format("%s PARAMS: [emnployeeEmail: %s , companyCode: %s , branchCode: %s ] ", idOperation,
				emnployeeEmail, companyCode, branchCode));
		MethodDto method = (MethodDto) companyServicePort
				.findMethodByCompanyCodeAndModule(companyCode, module, idOperation).getData();
		if (method == null) {
			LOG.info(String.format("%s ERROR IN ASSIGNATION METHODS OF THE COMPANY %s ", idOperation, companyCode));
			throw new GlobalError();
		}
		ResponseModel responseModel = reportCashServicePort.generateOpeningReportByEmailAndCompanyCode(emailServicePort, emnployeeEmail,
				method.getCode(), companyCode, idOperation);
		
		byte[] reporte = (byte[]) responseModel.getData();

		ContentDisposition contentDisposition = ContentDisposition.builder("attachment")
				.filename(emnployeeEmail + "-opening.pdf").build();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentDisposition(contentDisposition);

		return ResponseEntity.ok().contentLength((long) reporte.length).contentType(MediaType.APPLICATION_PDF)
				.headers(headers).body(new ByteArrayResource(reporte));
	}

	@GetMapping("/closing-operation/{emnployeeEmail}/{companyCode}/{branchCode}")
	public ResponseEntity<?> generateClosingOperationReportByEmailAndBrachCode(@PathVariable String emnployeeEmail,
			@PathVariable String companyCode, @PathVariable String branchCode) {

		idOperation = generateIdOperation(companyCode, branchCode, module);
		LOG.info(String.format("%s INIT generateClosingOperationReportByEmailAndBrachCode() ", idOperation));
		LOG.info(String.format("%s PARAMS: [emnployeeEmail: %s , companyCode: %s , branchCode: %s ] ", idOperation,
				emnployeeEmail, companyCode, branchCode));

		MethodDto method = (MethodDto) companyServicePort
				.findMethodByCompanyCodeAndModule(companyCode, module, idOperation).getData();

		if (method == null) {
			LOG.info(String.format("%s ERROR IN ASSIGNATION METHODS OF THE COMPANY %s ", idOperation, companyCode));
			throw new GlobalError();
		}

		ResponseModel responseModel = reportCashServicePort.generateClosingReportByEmailAndCompanyCode(emailServicePort, emnployeeEmail,
				method.getCode(), companyCode, idOperation);
		
		byte[] reporte = (byte[]) responseModel.getData();

		ContentDisposition contentDisposition = ContentDisposition.builder("attachment")
				.filename(emnployeeEmail + "-closing.pdf").build();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentDisposition(contentDisposition);

		return ResponseEntity.ok().contentLength((long) reporte.length).contentType(MediaType.APPLICATION_PDF)
				.headers(headers).body(new ByteArrayResource(reporte));
		
	}

}
