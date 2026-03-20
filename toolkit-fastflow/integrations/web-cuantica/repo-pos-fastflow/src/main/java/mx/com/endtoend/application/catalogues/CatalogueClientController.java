package mx.com.endtoend.application.catalogues;

import static mx.com.endtoend.genericCommonsFileds.utilities.ResponseLog.generateIdOperation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mx.com.endtoend.domain.catalogue.ports.api.CatalogueClientServicePort;
import mx.com.endtoend.domain.company.dto.MethodDto;
import mx.com.endtoend.domain.company.ports.api.CompanyServicePort;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.ValidationError;
import mx.com.endtoend.infrastructure.services.jde.catalogue.common.serviceport.CatalogueJdeServicePort;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

@RestController
@RequestMapping("/catalogue-client")
public class CatalogueClientController {

	@Autowired
	private CompanyServicePort companyServicePort;

	@Autowired
	private CatalogueClientServicePort catalogueClientServicePort;

	@Autowired
	private CatalogueJdeServicePort catalogueOracleServicePort;

	private static final String module = "CATALOGUE-CLIENT";

	public String idOperation;

	private final Logger LOG = LoggerFactory.getLogger(CatalogueClientController.class);

	/**
	 * EndPoint para la recuperación de la lista de CFDI configurados a una
	 * compañía
	 * 
	 * @param companyCode código de compañía registrada en el sistema
	 * @param branchCode  código de la sucursal donde se realiza la consulta
	 * 
	 * @return ResponseModel, objeto que contiene el código de resultado de la
	 *         operación y los datos solicitados
	 */
	@GetMapping("/cfdi/{companyCode}/{branchCode}")
	public ResponseEntity<ResponseModel> getCfdiList(@PathVariable String companyCode,
			@PathVariable String branchCode) {
		
		idOperation = generateIdOperation(companyCode, branchCode, module);
		LOG.info(String.format("%s INIT getCfdiList()", idOperation));
		LOG.info(
				String.format("%s PARAMS: [ companyCode: %s , branchCode: %s ]", idOperation, companyCode, branchCode));
		
		MethodDto method = (MethodDto) companyServicePort
				.findMethodByCompanyCodeAndModule(companyCode, module, idOperation).getData();
		
		if (method == null) {
			LOG.info(String.format("%s ERROR IN ASSIGNATION METHODS OF THE COMPANY %s ", idOperation, companyCode));
			throw new ValidationError("ERROR - CONTACT YOUR ADMINISTRATOR");
		}
		
		ResponseModel responseModel = catalogueClientServicePort.getCfdiByCompanyCode(catalogueOracleServicePort,
				method.getCode(), companyCode, branchCode);
		
		return new ResponseEntity<>(responseModel, HttpStatus.OK);
	}

	/**
	 * EndPoint para la recuperación de la lista de Regímenes Fiscales configurados
	 * a una compañía
	 * 
	 * @param companyCode código de compañía registrada en el sistema
	 * @param branchCode  código de la sucursal donde se realiza la consulta
	 * 
	 * @return ResponseModel, objeto que contiene el código de resultado de la
	 *         operación y los datos solicitados
	 */
	@GetMapping("/regime-fiscal/{companyCode}/{branchCode}")
	public ResponseEntity<ResponseModel> getFiscalRegimeList(@PathVariable String companyCode,
			@PathVariable String branchCode) {

		idOperation = generateIdOperation(companyCode, branchCode, module);
		LOG.info(String.format("%s INIT getFiscalRegimeList()", idOperation));
		LOG.info(
				String.format("%s PARAMS: [ companyCode: %s , branchCode: %s ]", idOperation, companyCode, branchCode));

		MethodDto method = (MethodDto) companyServicePort
				.findMethodByCompanyCodeAndModule(companyCode, module, idOperation).getData();

		if (method == null) {
			LOG.info(String.format("%s ERROR IN ASSIGNATION METHODS OF THE COMPANY %s ", idOperation, companyCode));
			throw new ValidationError("ERROR - CONTACT YOUR ADMINISTRATOR");
		}

		ResponseModel responseModel = catalogueClientServicePort.getRegimeFiscalCompanyCode(catalogueOracleServicePort,
				method.getCode(), companyCode, branchCode);

		return new ResponseEntity<>(responseModel, HttpStatus.OK);

	}

	/**
	 * EndPoint para la recuperación de la lista de tipos de clientes configurados a
	 * una compañía
	 * 
	 * @param companyCode código de compañía registrada en el sistema
	 * @param branchCode  código de la sucursal donde se realiza la consulta
	 * 
	 * @return ResponseModel, objeto que contiene el código de resultado de la
	 *         operación y los datos solicitados
	 */
	@GetMapping("/client-type/{companyCode}/{branchCode}")
	public ResponseEntity<ResponseModel> getClientTypeList(@PathVariable String companyCode,
			@PathVariable String branchCode) {

		idOperation = generateIdOperation(companyCode, branchCode, module);
		LOG.info(String.format("%s INIT getClientTypeList()", idOperation));
		LOG.info(
				String.format("%s PARAMS: [ companyCode: %s , branchCode: %s ]", idOperation, companyCode, branchCode));

		MethodDto method = (MethodDto) companyServicePort
				.findMethodByCompanyCodeAndModule(companyCode, module, idOperation).getData();

		if (method == null) {
			LOG.info(String.format("%s ERROR IN ASSIGNATION METHODS OF THE COMPANY %s ", idOperation, companyCode));
			throw new ValidationError("ERROR - CONTACT YOUR ADMINISTRATOR");
		}

		ResponseModel responseModel = catalogueClientServicePort.getClientTypeCompanyCode(catalogueOracleServicePort,
				method.getCode(), companyCode, branchCode);

		return new ResponseEntity<>(responseModel, HttpStatus.OK);
	}

	/**
	 * EndPoint para la recuperación de la lista de tipos de clientes configurados a
	 * una compañía
	 * 
	 * @param companyCode código de compañía registrada en el sistema
	 * @param branchCode  código de la sucursal donde se realiza la consulta
	 * 
	 * @return ResponseModel, objeto que contiene el código de resultado de la
	 *         operación y los datos solicitados
	 */
	@GetMapping("/contact-method/{companyCode}/{branchCode}")
	public ResponseEntity<ResponseModel> getContactMethodList(@PathVariable String companyCode,
			@PathVariable String branchCode) {

		idOperation = generateIdOperation(companyCode, branchCode, module);
		LOG.info(String.format("%s INIT getContactMethodList()", idOperation));
		LOG.info(
				String.format("%s PARAMS: [ companyCode: %s , branchCode: %s ]", idOperation, companyCode, branchCode));

		MethodDto method = (MethodDto) companyServicePort
				.findMethodByCompanyCodeAndModule(companyCode, module, idOperation).getData();

		if (method == null) {
			LOG.info(String.format("%s ERROR IN ASSIGNATION METHODS OF THE COMPANY %s ", idOperation, companyCode));
			throw new ValidationError("ERROR - CONTACT YOUR ADMINISTRATOR");
		}

		ResponseModel responseModel = catalogueClientServicePort.getContectMethodCompanyCode(catalogueOracleServicePort,
				method.getCode(), companyCode, branchCode);

		return new ResponseEntity<>(responseModel, HttpStatus.OK);
	}

	/**
	 * EndPoint para la recuperación de la lista de tipos de trabajo configurados a
	 * una compañía
	 * 
	 * @param companyCode código de compañía registrada en el sistema
	 * @param branchCode  código de la sucursal donde se realiza la consulta
	 * 
	 * @return ResponseModel, objeto que contiene el código de resultado de la
	 *         operación y los datos solicitados
	 */
	@GetMapping("/work-type/{companyCode}/{branchCode}")
	public ResponseEntity<ResponseModel> getWorkTypeList(@PathVariable String companyCode,
			@PathVariable String branchCode) {

		idOperation = generateIdOperation(companyCode, branchCode, module);
		LOG.info(String.format("%s INIT getWorkTypeList()", idOperation));
		LOG.info(
				String.format("%s PARAMS: [ companyCode: %s , branchCode: %s ]", idOperation, companyCode, branchCode));

		MethodDto method = (MethodDto) companyServicePort
				.findMethodByCompanyCodeAndModule(companyCode, module, idOperation).getData();

		if (method == null) {
			LOG.info(String.format("%s ERROR IN ASSIGNATION METHODS OF THE COMPANY %s ", idOperation, companyCode));
			throw new ValidationError("ERROR - CONTACT YOUR ADMINISTRATOR");
		}

		ResponseModel responseModel = catalogueClientServicePort.getWorkTypeByCompanyCode(catalogueOracleServicePort,
				method.getCode(), companyCode, branchCode);

		return new ResponseEntity<>(responseModel, HttpStatus.OK);
	}

	/**
	 * EndPoint para la actualización global de los catálogos por tipo especificado
	 * y compañía ingresada
	 * 
	 * @param catalogType tipo de catálogo a actualizar
	 * @param companyCode código de compañía registrada en el sistema
	 * @param branchCode  código de la sucursal donde se realiza la consulta
	 * 
	 * @return ResponseModel, objeto que contiene el código de resultado de la
	 *         operación y los datos solicitados
	 */
	@PutMapping("/update/{catalogType}/{companyCode}/{branchCode}")
	public ResponseEntity<ResponseModel> updateCatalogByTypeAndCompanyCode(@PathVariable String catalogType,
			@PathVariable String companyCode, @PathVariable String branchCode) {

		idOperation = generateIdOperation(companyCode, branchCode, module);
		LOG.info(String.format("%s INIT updateCatalogByTypeAndCompanyCode()", idOperation));
		LOG.info(String.format("%s PARAMS: [ catalogType: %s ,companyCode: %s , branchCode: %s ]", idOperation,
				catalogType, companyCode, branchCode));

		MethodDto method = (MethodDto) companyServicePort
				.findMethodByCompanyCodeAndModule(companyCode, module, idOperation).getData();

		if (method == null) {
			LOG.info(String.format("%s ERROR IN ASSIGNATION METHODS OF THE COMPANY %s ", idOperation, companyCode));
			throw new ValidationError("ERROR - CONTACT YOUR ADMINISTRATOR");
		}

		ResponseModel responseModel = catalogueClientServicePort.updateCatalogueCompanyCodeAndCatalogueType(
				catalogueOracleServicePort, method.getCode(), companyCode, catalogType, branchCode);
		
		return new ResponseEntity<>(responseModel, HttpStatus.OK);
	}

}
