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

import mx.com.endtoend.domain.cash.closingInstruments.dto.ClosePaymentInstrumentDto;
import mx.com.endtoend.domain.cash.closingInstruments.ports.api.ClosingInstrumentServicePort;
import mx.com.endtoend.domain.company.dto.MethodDto;
import mx.com.endtoend.domain.company.ports.api.CompanyServicePort;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.ValidationError;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

/**
 * Controlador encargado de la administración de los instrumetos de cierres
 * 
 * @author ddcasas
 *
 */
@RestController
@RequestMapping("/closing-instrument")
public class ClosingInstrumentController {

	@Autowired
	private CompanyServicePort companyServicePort;

	@Autowired
	private ClosingInstrumentServicePort closingInstrumentServicePort;

	private String module = "CLOSING_INSTRUMENT";
	public String idOperation = "";

	private final Logger LOG = LoggerFactory.getLogger(ClosingInstrumentController.class);

	@PostMapping("/create/{companyCode}/{branchCode}")
	public ResponseEntity<?> createClosePaymentInstrumentByCompanyCode(
			@RequestBody ClosePaymentInstrumentDto closePaymentInstrumentDto, @PathVariable String companyCode,
			@PathVariable String branchCode) {
		idOperation = generateIdOperation(companyCode, branchCode, module);
		LOG.info(String.format("%s INIT createClosePaymentInstrumentByCompanyCode()", idOperation));
		LOG.info(String.format("%s PARAMS: [closePaymentInstrumentDto: %s , companyCode: %s , branchCode: %s ]",
				idOperation, closePaymentInstrumentDto.toString(), companyCode, branchCode));
		MethodDto method = (MethodDto) companyServicePort
				.findMethodByCompanyCodeAndModule(companyCode, module, idOperation).getData();
		if (method == null) {
			LOG.info(String.format("%s ERROR IN ASSIGNATION METHODS OF THE COMPANY %s ", idOperation, companyCode));
			throw new ValidationError("COMPANY CODE NOT EXISTS");
		}
		ResponseModel responseModel = closingInstrumentServicePort.createClosePaymentInstrumentByCompanyCode(
				closePaymentInstrumentDto, method.getCode(), companyCode, idOperation);
		return new ResponseEntity<>(responseModel, HttpStatus.OK);
	}

	@PutMapping("/update/{id}/{companyCode}/{branchCode}")
	public ResponseEntity<?> updateClosePaymentInstrumentByCompanyCodeAndId(
			@RequestBody ClosePaymentInstrumentDto closePaymentInstrumentDto, @PathVariable Long id,
			@PathVariable String companyCode, @PathVariable String branchCode) {
		idOperation = generateIdOperation(companyCode, branchCode, module);
		LOG.info(String.format("%s INIT updateClosePaymentInstrumentByCompanyCodeAndId()", idOperation));
		LOG.info(
				String.format("%s PARAMS: [closePaymentInstrumentDto: %s , id: %s , companyCode: %s , branchCode: %s ]",
						idOperation, closePaymentInstrumentDto.toString(), id.toString(), companyCode, branchCode));
		closePaymentInstrumentDto.setId(id);
		MethodDto method = (MethodDto) companyServicePort
				.findMethodByCompanyCodeAndModule(companyCode, module, idOperation).getData();
		if (method == null) {
			LOG.info(String.format("%s ERROR IN ASSIGNATION METHODS OF THE COMPANY %s ", idOperation, companyCode));
			throw new ValidationError("COMPANY CODE NOT EXISTS");
		}
		ResponseModel responseModel = closingInstrumentServicePort.updateClosePaymentInstrumentByCompanyCodeAndId(
				closePaymentInstrumentDto, method.getCode(), companyCode, idOperation);
		return new ResponseEntity<>(responseModel, HttpStatus.OK);
	}

	@GetMapping("/view/{id}/{companyCode}/{branchCode}")
	public ResponseEntity<?> viewClosePaymentInstrumentByIdAndCompanyCode(@PathVariable Long id,
			@PathVariable String companyCode, @PathVariable String branchCode) {
		idOperation = generateIdOperation(companyCode, branchCode, module);
		LOG.info(String.format("%s INIT viewClosePaymentInstrumentByIdAndCompanyCode()", idOperation));
		LOG.info(String.format("%s PARAMS: [id: %s , companyCode: %s , branchCode: %s ]", idOperation, id.toString(),
				companyCode, branchCode));
		MethodDto method = (MethodDto) companyServicePort
				.findMethodByCompanyCodeAndModule(companyCode, module, idOperation).getData();
		if (method == null) {
			LOG.info(String.format("%s ERROR IN ASSIGNATION METHODS OF THE COMPANY %s ", idOperation, companyCode));
			throw new ValidationError("COMPANY CODE NOT EXISTS");
		}
		ResponseModel responseModel = closingInstrumentServicePort.viewClosePaymentInstrumentByIdAndCompanyCode(id,
				method.getCode(), companyCode, idOperation);
		return new ResponseEntity<>(responseModel, HttpStatus.OK);
	}

	@GetMapping("/view/active/{companyCode}/{branchCode}")
	public ResponseEntity<?> viewActiveClosePaymentInstrumentListByCompanyCode(@PathVariable String companyCode,
			@PathVariable String branchCode) {
		idOperation = generateIdOperation(companyCode, branchCode, module);
		LOG.info(String.format("%s INIT viewActiveClosePaymentInstrumentListByCompanyCode()", idOperation));
		LOG.info(String.format("%s PARAMS: [companyCode: %s , branchCode: %s ]", idOperation, companyCode, branchCode));
		MethodDto method = (MethodDto) companyServicePort
				.findMethodByCompanyCodeAndModule(companyCode, module, idOperation).getData();
		if (method == null) {
			LOG.info(String.format("%s ERROR IN ASSIGNATION METHODS OF THE COMPANY %s ", idOperation, companyCode));
			throw new ValidationError("COMPANY CODE NOT EXISTS");
		}
		ResponseModel responseModel = closingInstrumentServicePort
				.viewClosePaymentInstrumentListByCompanyCodeAndEnable(true, method.getCode(), companyCode, idOperation);
		return new ResponseEntity<>(responseModel, HttpStatus.OK);
	}

	@GetMapping("/view/inactive/{companyCode}/{branchCode}")
	public ResponseEntity<?> viewInactiveClosePaymentInstrumentListByCompanyCode(@PathVariable String companyCode,
			@PathVariable String branchCode) {
		idOperation = generateIdOperation(companyCode, branchCode, module);
		LOG.info(String.format("%s INIT viewInactiveClosePaymentInstrumentListByCompanyCode()", idOperation));
		LOG.info(String.format("%s PARAMS: [companyCode: %s , branchCode: %s ]", idOperation, companyCode, branchCode));
		MethodDto method = (MethodDto) companyServicePort
				.findMethodByCompanyCodeAndModule(companyCode, module, idOperation).getData();
		if (method == null) {
			LOG.info(String.format("%s ERROR IN ASSIGNATION METHODS OF THE COMPANY %s ", idOperation, companyCode));
			throw new ValidationError("COMPANY CODE NOT EXISTS");
		}
		ResponseModel responseModel = closingInstrumentServicePort.viewClosePaymentInstrumentListByCompanyCodeAndEnable(
				false, method.getCode(), companyCode, idOperation);
		return new ResponseEntity<>(responseModel, HttpStatus.OK);
	}
}
