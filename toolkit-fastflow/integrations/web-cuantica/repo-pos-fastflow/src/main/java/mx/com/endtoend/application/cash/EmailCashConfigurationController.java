package mx.com.endtoend.application.cash;

import static mx.com.endtoend.genericCommonsFileds.utilities.ResponseLog.generateIdOperation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mx.com.endtoend.domain.cash.emailReport.dto.EmailReportCashDto;
import mx.com.endtoend.domain.cash.emailReport.ports.EmailCashConfigurationServicePort;
import mx.com.endtoend.domain.company.dto.MethodDto;
import mx.com.endtoend.domain.company.ports.api.CompanyServicePort;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.ValidationError;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

@RestController
@RequestMapping("/email-cash")
public class EmailCashConfigurationController {

	@Autowired
	private CompanyServicePort companyServicePort;

	@Autowired
	private EmailCashConfigurationServicePort cashConfigurationServicePort;

	private String module = "EMAIL_CASH_CONFIGURATION";
	public String idOperation = "";

	private final Logger LOG = LoggerFactory.getLogger(EmailCashConfigurationController.class);

	@PostMapping("/create/{companyCode}/{branchCode}")
	ResponseEntity<?> createEmailReportCashByCompanyCode(@RequestBody EmailReportCashDto emailReportCashDto,
			@PathVariable String companyCode, @PathVariable String branchCode) {
		idOperation = generateIdOperation(companyCode, branchCode, module);
		LOG.info(String.format("%s INIT createEmailReportCashByCompanyCode()", idOperation));
		LOG.info(String.format("%s PARAMS: [emailReportCashDto: %s , companyCode: %s , branchCode: %s ]", idOperation,
				emailReportCashDto.toString(), companyCode, branchCode));
		MethodDto method = (MethodDto) companyServicePort
				.findMethodByCompanyCodeAndModule(companyCode, module, idOperation).getData();
		if (method == null) {
			LOG.info(String.format("%s ERROR IN ASSIGNATION METHODS OF THE COMPANY %s ", idOperation, companyCode));
			throw new ValidationError("COMPANY CODE NOT EXISTS");
		}
		ResponseModel responseModel = cashConfigurationServicePort.createEmailReportByCompanyCode(emailReportCashDto,
				method.getCode(), companyCode, idOperation);
		return new ResponseEntity<>(responseModel, HttpStatus.OK);
	}

	@PutMapping("/update/{id}/{companyCode}/{branchCode}")
	ResponseEntity<?> updateEmailReportCashByCompanyCode(@RequestBody EmailReportCashDto emailReportCashDto,
			@PathVariable Long id, @PathVariable String companyCode, @PathVariable String branchCode) {
		idOperation = generateIdOperation(companyCode, branchCode, module);
		LOG.info(String.format("%s INIT updateEmailReportCashByCompanyCode()", idOperation));
		LOG.info(String.format("%s PARAMS: [emailReportCashDto: %s , id: %s , companyCode: %s , branchCode: %s ]",
				idOperation, emailReportCashDto.toString(), id.toString(), companyCode, branchCode));
		MethodDto method = (MethodDto) companyServicePort
				.findMethodByCompanyCodeAndModule(companyCode, module, idOperation).getData();
		if (method == null) {
			LOG.info(String.format("%s ERROR IN ASSIGNATION METHODS OF THE COMPANY %s ", idOperation, companyCode));
			throw new ValidationError("COMPANY CODE NOT EXISTS");
		}
		emailReportCashDto.setId(id);
		ResponseModel responseModel = cashConfigurationServicePort.updateEmailReportByCompanyCode(emailReportCashDto,
				method.getCode(), companyCode, idOperation);
		return new ResponseEntity<>(responseModel, HttpStatus.OK);
	}

	@GetMapping("/view/{companyCode}/{branchCode}")
	ResponseEntity<?> viewEmailReportCashByCompanyCode(@PathVariable String companyCode,
			@PathVariable String branchCode) {
		idOperation = generateIdOperation(companyCode, branchCode, module);
		LOG.info(String.format("%s INIT viewEmailReportCashByCompanyCode()", idOperation));
		LOG.info(String.format("%s PARAMS: [companyCode: %s , branchCode: %s ]", idOperation, companyCode, branchCode));
		MethodDto method = (MethodDto) companyServicePort
				.findMethodByCompanyCodeAndModule(companyCode, module, idOperation).getData();
		if (method == null) {
			LOG.info(String.format("%s ERROR IN ASSIGNATION METHODS OF THE COMPANY %s ", idOperation, companyCode));
			throw new ValidationError("COMPANY CODE NOT EXISTS");
		}
		ResponseModel responseModel = cashConfigurationServicePort.viewEmailReportByCompanyCode(method.getCode(),
				companyCode, idOperation);
		return new ResponseEntity<>(responseModel, HttpStatus.OK);
	}

}
