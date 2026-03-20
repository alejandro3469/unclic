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

import mx.com.endtoend.domain.catalogue.ports.api.CatalogueArticleServicePort;
import mx.com.endtoend.domain.company.dto.MethodDto;
import mx.com.endtoend.domain.company.ports.api.CompanyServicePort;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.ValidationError;
import mx.com.endtoend.infrastructure.services.jde.catalogue.common.serviceport.CatalogueJdeServicePort;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

@RestController
@RequestMapping("/catalogue-article")
public class CatalogueArticleController {

	@Autowired
	private CompanyServicePort companyServicePort;

	@Autowired
	private CatalogueJdeServicePort catalogueOracleServicePort;

	@Autowired
	private CatalogueArticleServicePort catalogueArticleServicePort;

	private static String module = "CATALOGUE-ARTICLE";

	public String idOperation;

	private final Logger LOG = LoggerFactory.getLogger(CatalogueArticleController.class);

	/**
	 * EndPoint para la recuperación de la lista de marcas de artículos configurados
	 * a una compañía
	 * 
	 * @param companyCode código de compañía registrada en el sistema
	 * @param branchCode  código de la sucursal donde se realiza la consulta
	 * @return ResponseModel, objeto que contiene el código de resultado de la
	 *         operación y los datos solicitados
	 */
	@GetMapping("/brand/{companyCode}/{branchCode}")
	public ResponseEntity<ResponseModel> getBrandList(@PathVariable String companyCode,
			@PathVariable String branchCode) {

		idOperation = generateIdOperation(companyCode, branchCode, module);
		LOG.info(String.format("%s INIT getBrandList()", idOperation));
		LOG.info(
				String.format("%s PARAMS: [ companyCode: %s , branchCode: %s ]", idOperation, companyCode, branchCode));

		MethodDto method = (MethodDto) companyServicePort
				.findMethodByCompanyCodeAndModule(companyCode, module, idOperation).getData();

		if (method == null) {
			LOG.info(String.format("%s ERROR IN ASSIGNATION METHODS OF THE COMPANY %s ", idOperation, companyCode));
			throw new ValidationError("ERROR - CONTACT YOUR ADMINISTRATOR");
		}

		ResponseModel responseModel = catalogueArticleServicePort.getBrandByCompanyCode(catalogueOracleServicePort,
				method.getCode(), companyCode, idOperation);

		return new ResponseEntity<>(responseModel, HttpStatus.OK);
	}

	/**
	 * EndPoint para la recuperación de la lista de categorias de artículos
	 * configurados a una compañía
	 * 
	 * @param companyCode código de compañía registrada en el sistema
	 * @param branchCode  código de la sucursal donde se realiza la consulta
	 * @return ResponseModel, objeto que contiene el código de resultado de la
	 *         operación y los datos solicitados
	 */
	@GetMapping("/category/{companyCode}/{branchCode}")
	public ResponseEntity<ResponseModel> getCategoryList(@PathVariable String companyCode,
			@PathVariable String branchCode) {

		idOperation = generateIdOperation(companyCode, branchCode, module);
		LOG.info(String.format("%s INIT getCategoryList()", idOperation));
		LOG.info(
				String.format("%s PARAMS: [ companyCode: %s , branchCode: %s ]", idOperation, companyCode, branchCode));

		MethodDto method = (MethodDto) companyServicePort
				.findMethodByCompanyCodeAndModule(companyCode, module, idOperation).getData();

		if (method == null) {
			LOG.info(String.format("%s ERROR IN ASSIGNATION METHODS OF THE COMPANY %s ", idOperation, companyCode));
			throw new ValidationError("ERROR - CONTACT YOUR ADMINISTRATOR");
		}

		ResponseModel responseModel = catalogueArticleServicePort.getCategoryByCompanyCode(catalogueOracleServicePort,
				method.getCode(), companyCode, idOperation);

		return new ResponseEntity<>(responseModel, HttpStatus.OK);
	}

	/**
	 * EndPoint para la recuperación de la lista de divisiones de artículos
	 * configurados a una compañía
	 * 
	 * @param companyCode código de compañía registrada en el sistema
	 * @param branchCode  código de la sucursal donde se realiza la consulta
	 * @return ResponseModel, objeto que contiene el código de resultado de la
	 *         operación y los datos solicitados
	 */
	@GetMapping("/division/{companyCode}/{branchCode}")
	public ResponseEntity<ResponseModel> getDivisionList(@PathVariable String companyCode,
			@PathVariable String branchCode) {

		idOperation = generateIdOperation(companyCode, branchCode, module);
		LOG.info(String.format("%s INIT getDivisionList()", idOperation));
		LOG.info(
				String.format("%s PARAMS: [ companyCode: %s , branchCode: %s ]", idOperation, companyCode, branchCode));

		MethodDto method = (MethodDto) companyServicePort
				.findMethodByCompanyCodeAndModule(companyCode, module, idOperation).getData();

		if (method == null) {
			LOG.info(String.format("%s ERROR IN ASSIGNATION METHODS OF THE COMPANY %s ", idOperation, companyCode));
			throw new ValidationError("ERROR - CONTACT YOUR ADMINISTRATOR");
		}

		ResponseModel responseModel = catalogueArticleServicePort.getDivisionByCompanyCode(catalogueOracleServicePort,
				method.getCode(), companyCode, idOperation);

		return new ResponseEntity<>(responseModel, HttpStatus.OK);
	}

	/**
	 * EndPoint para la recuperación de la lista de familias de artículos
	 * configurados a una compañía
	 * 
	 * @param companyCode código de compañía registrada en el sistema
	 * @param branchCode  código de la sucursal donde se realiza la consulta
	 * @return ResponseModel, objeto que contiene el código de resultado de la
	 *         operación y los datos solicitados
	 */
	@GetMapping("/family/{companyCode}/{branchCode}")
	public ResponseEntity<ResponseModel> getFamilyList(@PathVariable String companyCode,
			@PathVariable String branchCode) {

		idOperation = generateIdOperation(companyCode, branchCode, module);
		LOG.info(String.format("%s INIT getFamilyList()", idOperation));
		LOG.info(
				String.format("%s PARAMS: [ companyCode: %s , branchCode: %s ]", idOperation, companyCode, branchCode));

		MethodDto method = (MethodDto) companyServicePort
				.findMethodByCompanyCodeAndModule(companyCode, module, idOperation).getData();

		if (method == null) {
			LOG.info(String.format("%s ERROR IN ASSIGNATION METHODS OF THE COMPANY %s ", idOperation, companyCode));
			throw new ValidationError("ERROR - CONTACT YOUR ADMINISTRATOR");
		}

		ResponseModel responseModel = catalogueArticleServicePort.getFamilyByCompanyCode(catalogueOracleServicePort,
				method.getCode(), companyCode, idOperation);

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
		ResponseModel responseModel = catalogueArticleServicePort.updateCatalogueByCompanyCodeAndCatalogueType(
				catalogueOracleServicePort, method.getCode(), companyCode, catalogType, idOperation);
		return new ResponseEntity<>(responseModel, HttpStatus.OK);
	}
}
