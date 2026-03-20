package mx.com.endtoend.application.cash;

import static mx.com.endtoend.genericCommonsFileds.utilities.ResponseLog.generateIdOperation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mx.com.endtoend.domain.cash.creditCardReference.dto.CreditCardDto;
import mx.com.endtoend.domain.cash.creditCardReference.ports.CreditCardConfigurationServicePort;
import mx.com.endtoend.domain.company.dto.MethodDto;
import mx.com.endtoend.domain.company.ports.api.CompanyServicePort;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.ValidationError;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

@RestController
@RequestMapping("/credit-card/reference")
public class CreditCardConfigurationController {

	@Autowired
	private CompanyServicePort companyServicePort;

	@Autowired
	private CreditCardConfigurationServicePort creditCardConfigurationServicePort;

	private String module = "CREDIT_CARD_REFERENCE";
	public String idOperation = "";

	private final Logger LOG = LoggerFactory.getLogger(CreditCardConfigurationController.class);

	@PostMapping("/create/{companyCode}/{branchCode}")
	public ResponseEntity<?> createCreditCardReferenceByCompanyCode(@RequestBody CreditCardDto creditCardDto,
			@PathVariable String companyCode, @PathVariable String branchCode) {
		idOperation = generateIdOperation(companyCode, branchCode, module);
		LOG.info(String.format("%s INIT createCreditCardReferenceByCompanyCode()", idOperation));
		LOG.info(String.format("%s PARAMS: [creditCardDto: %s , companyCode: %s , branchCode: %s ]", idOperation,
				creditCardDto.toString(), companyCode, branchCode));
		MethodDto method = (MethodDto) companyServicePort
				.findMethodByCompanyCodeAndModule(companyCode, module, idOperation).getData();
		if (method == null) {
			LOG.info(String.format("%s ERROR IN ASSIGNATION METHODS OF THE COMPANY %s ", idOperation, companyCode));
			throw new ValidationError("COMPANY CODE NOT EXISTS");
		}
		Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
		String username = loggedInUser.getName();
		ResponseModel responseModel = creditCardConfigurationServicePort.createCreditCardReferenceByCompanyCode(
				creditCardDto, method.getCode(), companyCode, username, idOperation);
		return new ResponseEntity<>(responseModel, HttpStatus.OK);
	}

	@GetMapping("/view/{id}/{companyCode}/{branchCode}")
	public ResponseEntity<?> viewCreditCardReferenceByIdAndCompanyCode(@PathVariable Long id,
			@PathVariable String companyCode, @PathVariable String branchCode) {
		idOperation = generateIdOperation(companyCode, branchCode, module);
		LOG.info(String.format("%s INIT viewCreditCardReferenceByIdAndCompanyCode()", idOperation));
		LOG.info(String.format("%s PARAMS: [id: %s , companyCode: %s , branchCode: %s ]", idOperation, id.toString(),
				companyCode, branchCode));
		MethodDto method = (MethodDto) companyServicePort
				.findMethodByCompanyCodeAndModule(companyCode, module, idOperation).getData();
		if (method == null) {
			LOG.info(String.format("%s ERROR IN ASSIGNATION METHODS OF THE COMPANY %s ", idOperation, companyCode));
			throw new ValidationError("COMPANY CODE NOT EXISTS");
		}
		ResponseModel responseModel = creditCardConfigurationServicePort.viewCreditCardReferenceByIdAndCompanyCode(id,
				method.getCode(), companyCode, idOperation);
		return new ResponseEntity<>(responseModel, HttpStatus.OK);
	}

	@PutMapping("/update/{id}/{companyCode}/{branchCode}")
	public ResponseEntity<?> updateCreditCardReferenceByCompanyCodeAndId(@RequestBody CreditCardDto creditCardDto,
			@PathVariable Long id, @PathVariable String companyCode, @PathVariable String branchCode) {
		idOperation = generateIdOperation(companyCode, branchCode, module);
		LOG.info(String.format("%s INIT updateCreditCardReferenceByCompanyCodeAndId()", idOperation));
		LOG.info(String.format("%s PARAMS: [creditCardDto: %s , companyCode: %s , branchCode: %s ]", idOperation,
				creditCardDto.toString(), companyCode, branchCode));
		MethodDto method = (MethodDto) companyServicePort
				.findMethodByCompanyCodeAndModule(companyCode, module, idOperation).getData();
		if (method == null) {
			LOG.info(String.format("%s ERROR IN ASSIGNATION METHODS OF THE COMPANY %s ", idOperation, companyCode));
			throw new ValidationError("COMPANY CODE NOT EXISTS");
		}
		Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
		String username = loggedInUser.getName();
		creditCardDto.setId(id);

		ResponseModel responseModel = creditCardConfigurationServicePort.updateCreditCardReferenceByCompanyCodeAndId(
				creditCardDto, method.getCode(), companyCode, username, idOperation);
		return new ResponseEntity<>(responseModel, HttpStatus.OK);
	}

	@GetMapping("/view/active/{companyCode}/{branchCode}")
	public ResponseEntity<?> viewCreditCardReferenceActiveByCompanyCode(@PathVariable String companyCode,
			@PathVariable String branchCode) {
		idOperation = generateIdOperation(companyCode, branchCode, module);
		LOG.info(String.format("%s INIT viewCreditCardReferenceActiveByCompanyCode()", idOperation));
		LOG.info(String.format("%s PARAMS: [companyCode: %s , branchCode: %s ]", idOperation, companyCode, branchCode));
		MethodDto method = (MethodDto) companyServicePort
				.findMethodByCompanyCodeAndModule(companyCode, module, idOperation).getData();
		if (method == null) {
			LOG.info(String.format("%s ERROR IN ASSIGNATION METHODS OF THE COMPANY %s ", idOperation, companyCode));
			throw new ValidationError("COMPANY CODE NOT EXISTS");
		}
		ResponseModel responseModel = creditCardConfigurationServicePort
				.viewCreditCardReferenceListByEnableByCompanyCode(true, method.getCode(), companyCode, idOperation);
		return new ResponseEntity<>(responseModel, HttpStatus.OK);
	}

	@GetMapping("/view/inactive/{companyCode}/{branchCode}")
	public ResponseEntity<?> viewCreditCardReferenceInactiveByCompanyCode(@PathVariable String companyCode,
			@PathVariable String branchCode) {
		idOperation = generateIdOperation(companyCode, branchCode, module);
		LOG.info(String.format("%s INIT viewCreditCardReferenceInactiveByCompanyCode()", idOperation));
		LOG.info(String.format("%s PARAMS: [companyCode: %s , branchCode: %s ]", idOperation, companyCode, branchCode));
		MethodDto method = (MethodDto) companyServicePort
				.findMethodByCompanyCodeAndModule(companyCode, module, idOperation).getData();
		if (method == null) {
			LOG.info(String.format("%s ERROR IN ASSIGNATION METHODS OF THE COMPANY %s ", idOperation, companyCode));
			throw new ValidationError("COMPANY CODE NOT EXISTS");
		}
		ResponseModel responseModel = creditCardConfigurationServicePort
				.viewCreditCardReferenceListByEnableByCompanyCode(false, method.getCode(), companyCode, idOperation);
		return new ResponseEntity<>(responseModel, HttpStatus.OK);
	}

}
