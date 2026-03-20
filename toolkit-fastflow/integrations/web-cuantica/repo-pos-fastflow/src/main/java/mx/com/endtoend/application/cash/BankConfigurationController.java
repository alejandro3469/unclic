package mx.com.endtoend.application.cash;

import static mx.com.endtoend.genericCommonsFileds.utilities.ResponseLog.generateIdOperation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import mx.com.endtoend.domain.cash.bankReference.dto.BankDto;
import mx.com.endtoend.domain.cash.bankReference.ports.BankConfigurationServicePort;
import mx.com.endtoend.domain.company.dto.MethodDto;
import mx.com.endtoend.domain.company.ports.api.CompanyServicePort;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.ValidationError;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

@Controller
@RequestMapping("/bank-reference")
public class BankConfigurationController {

	@Autowired
	private CompanyServicePort companyServicePort;

	@Autowired
	private BankConfigurationServicePort bankConfigurationServicePort;

	private String module = "BANK_REFERENCE_CONFIGURATION";
	public String idOperation = "";

	private final Logger LOG = LoggerFactory.getLogger(BankConfigurationController.class);

	/**
	 * EndPoint para la creación de referencias bancarias por código de compañia
	 * 
	 * @param bankDto     datos operativos
	 * @param companyCode código de compañía
	 * @param branchCode  código de sucursal
	 * 
	 * @return ResponseModel, objeto con los datos operativos procesados y código de
	 *         respuesta
	 */
	@PostMapping("/create/{companyCode}/{branchCode}")
	public ResponseEntity<?> createBankByCompanyCode(@RequestBody BankDto bankDto, @PathVariable String companyCode,
			@PathVariable String branchCode) {
		idOperation = generateIdOperation(companyCode, branchCode, module);
		LOG.info(String.format("%s INIT createBankByCompanyCode()", idOperation));
		LOG.info(String.format("%s PARAMS: [bankDto: %s , companyCode: %s , branchCode: %s ]", idOperation,
				bankDto.toString(), companyCode, branchCode));
		MethodDto method = (MethodDto) companyServicePort
				.findMethodByCompanyCodeAndModule(companyCode, module, idOperation).getData();
		if (method == null) {
			LOG.info(String.format("%s ERROR IN ASSIGNATION METHODS OF THE COMPANY %s ", idOperation, companyCode));
			throw new ValidationError("COMPANY CODE NOT EXISTS");
		}
		ResponseModel responseModel = bankConfigurationServicePort.createBankByCompanyCode(bankDto, method.getCode(),
				companyCode, idOperation);
		return new ResponseEntity<>(responseModel, HttpStatus.OK);
	}

	/**
	 * Endpoint par la actualización de datos operativos de referencias bancarias
	 * por código de compañía
	 * 
	 * @param bankDto     datos operativos
	 * @param id          identificador de la entidad a modificar
	 * @param companyCode código de compañia
	 * @param branchCode  código de sucursal
	 * @return ResponseModel, objeto con los datos operativos procesados y código de
	 *         respuesta
	 */
	@PutMapping("/update/{id}/{companyCode}/{branchCode}")
	public ResponseEntity<?> updateBankByCompanyCodeAndId(@RequestBody BankDto bankDto, @PathVariable Long id,
			@PathVariable String companyCode, @PathVariable String branchCode) {
		idOperation = generateIdOperation(companyCode, branchCode, module);
		LOG.info(String.format("%s INIT updateBankByCompanyCodeAndId()", idOperation));
		LOG.info(String.format("%s PARAMS: [bankDto: %s , id: %s , companyCode: %s , branchCode: %s ]", idOperation,
				bankDto.toString(), id.toString(), companyCode, branchCode));
		MethodDto method = (MethodDto) companyServicePort
				.findMethodByCompanyCodeAndModule(companyCode, module, idOperation).getData();
		if (method == null) {
			LOG.info(String.format("%s ERROR IN ASSIGNATION METHODS OF THE COMPANY %s ", idOperation, companyCode));
			throw new ValidationError("COMPANY CODE NOT EXISTS");
		}
		bankDto.setId(id);
		ResponseModel responseModel = bankConfigurationServicePort.updateBankByCompanyCodeAndId(bankDto,
				method.getCode(), companyCode, idOperation);
		return new ResponseEntity<>(responseModel, HttpStatus.OK);
	}

	/**
	 * EndPoint para ver el detalle de refencias bancarias por id y por código de
	 * compañía
	 * 
	 * @param id          identificador de la entidad a modificar
	 * @param companyCode código de compañia
	 * @param branchCode  código de sucursal
	 * @return ResponseModel, objeto con los datos operativos procesados y código de
	 *         respuesta
	 */
	@GetMapping("/view/{id}/{companyCode}/{branchCode}")
	public ResponseEntity<?> viewBankByIdAndCompanyCode(@PathVariable Long id, @PathVariable String companyCode,
			@PathVariable String branchCode) {
		idOperation = generateIdOperation(companyCode, branchCode, module);
		LOG.info(String.format("%s INIT viewBankByIdAndCompanyCode()", idOperation));
		LOG.info(String.format("%s PARAMS: [id: %s , companyCode: %s , branchCode: %s ]", idOperation, id.toString(),
				companyCode, branchCode));
		MethodDto method = (MethodDto) companyServicePort
				.findMethodByCompanyCodeAndModule(companyCode, module, idOperation).getData();
		if (method == null) {
			LOG.info(String.format("%s ERROR IN ASSIGNATION METHODS OF THE COMPANY %s ", idOperation, companyCode));
			throw new ValidationError("COMPANY CODE NOT EXISTS");
		}
		ResponseModel responseModel = bankConfigurationServicePort.viewBankByIdAndCompanyCode(id, method.getCode(),
				companyCode, idOperation);
		return new ResponseEntity<>(responseModel, HttpStatus.OK);
	}

	/**
	 * EndPoint para visualizar las referecnias bancarias activas por código de
	 * compañía
	 * 
	 * @param companyCode código de compañia
	 * @param branchCode  código de sucursal
	 * @return ResponseModel, objeto con los datos operativos procesados y código de
	 *         respuesta
	 */
	@GetMapping("/view/active/{companyCode}/{branchCode}")
	public ResponseEntity<?> viewBankActiveListByCompanyCode(@PathVariable String companyCode,
			@PathVariable String branchCode) {
		idOperation = generateIdOperation(companyCode, branchCode, module);
		LOG.info(String.format("%s INIT viewBankActiveListByCompanyCode()", idOperation));
		LOG.info(String.format("%s PARAMS: [companyCode: %s , branchCode: %s ]", idOperation, companyCode, branchCode));
		MethodDto method = (MethodDto) companyServicePort
				.findMethodByCompanyCodeAndModule(companyCode, module, idOperation).getData();
		if (method == null) {
			LOG.info(String.format("%s ERROR IN ASSIGNATION METHODS OF THE COMPANY %s ", idOperation, companyCode));
			throw new ValidationError("COMPANY CODE NOT EXISTS");
		}
		ResponseModel responseModel = bankConfigurationServicePort.viewBankListByEnableByCompanyCode(true,
				method.getCode(), companyCode, idOperation);
		return new ResponseEntity<>(responseModel, HttpStatus.OK);
	}

	/**
	 * EndPoint para visualizar las referecnias bancarias inactivas por código de
	 * compañía
	 * 
	 * @param companyCode código de compañia
	 * @param branchCode  código de sucursal
	 * @return ResponseModel, objeto con los datos operativos procesados y código de
	 *         respuesta
	 */
	@GetMapping("/view/inactive/{companyCode}/{branchCode}")
	public ResponseEntity<?> viewBankInactiveListByCompanyCode(@PathVariable String companyCode,
			@PathVariable String branchCode) {
		idOperation = generateIdOperation(companyCode, branchCode, module);
		LOG.info(String.format("%s INIT viewBankInactiveListByCompanyCode()", idOperation));
		LOG.info(String.format("%s PARAMS: [companyCode: %s , branchCode: %s ]", idOperation, companyCode, branchCode));
		MethodDto method = (MethodDto) companyServicePort
				.findMethodByCompanyCodeAndModule(companyCode, module, idOperation).getData();
		if (method == null) {
			LOG.info(String.format("%s ERROR IN ASSIGNATION METHODS OF THE COMPANY %s ", idOperation, companyCode));
			throw new ValidationError("COMPANY CODE NOT EXISTS");
		}
		ResponseModel responseModel = bankConfigurationServicePort.viewBankListByEnableByCompanyCode(false,
				method.getCode(), companyCode, idOperation);
		return new ResponseEntity<>(responseModel, HttpStatus.OK);
	}

	@GetMapping("/view/use-type/{useType}/{companyCode}/{branchCode}")
	public ResponseEntity<?> viewBankActiveListByUseTypeByCompanyCode(@PathVariable String useType,
			@PathVariable String companyCode, @PathVariable String branchCode) {
		idOperation = generateIdOperation(companyCode, branchCode, module);
		LOG.info(String.format("%s INIT viewBankInactiveListByCompanyCode()", idOperation));
		LOG.info(String.format("%s PARAMS: [useType: %s , companyCode: %s , branchCode: %s ]", idOperation, useType,
				companyCode, branchCode));
		MethodDto method = (MethodDto) companyServicePort
				.findMethodByCompanyCodeAndModule(companyCode, module, idOperation).getData();
		if (method == null) {
			LOG.info(String.format("%s ERROR IN ASSIGNATION METHODS OF THE COMPANY %s ", idOperation, companyCode));
			throw new ValidationError("COMPANY CODE NOT EXISTS");
		}
		ResponseModel responseModel = bankConfigurationServicePort
				.viewActuveBankListByUseTypeAndEnableByCompanyCode(useType, method.getCode(), companyCode, idOperation);
		return new ResponseEntity<>(responseModel, HttpStatus.OK);
	}
}
