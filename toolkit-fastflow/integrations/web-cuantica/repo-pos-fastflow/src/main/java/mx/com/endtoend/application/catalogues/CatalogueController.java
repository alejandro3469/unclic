package mx.com.endtoend.application.catalogues;

import static mx.com.endtoend.genericCommonsFileds.utilities.ResponseLog.generateIdOperation;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mx.com.endtoend.domain.catalogue.ports.api.CatalogueServicePort;
import mx.com.endtoend.domain.company.dto.MethodDto;
import mx.com.endtoend.domain.company.ports.api.CompanyServicePort;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.GlobalError;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.ValidationError;
import mx.com.endtoend.infrastructure.services.jde.catalogue.common.serviceport.CatalogueJdeServicePort;
import mx.com.endtoend.smart.bussiness.model.catalogue.dto.StatusDto;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

@RestController
@RequestMapping("/catalogue")
public class CatalogueController {

	@Autowired
	private CatalogueServicePort catalogueServicePort;

	@Autowired
	private CompanyServicePort companyServicePort;

	@Autowired
	private CatalogueJdeServicePort catalogueOracleServicePort;

	private static String module = "CATALOGUE";

	public String idOperation;

	private final Logger LOG = LoggerFactory.getLogger(CatalogueController.class);
	

	/**
	 * Devuelve una lista de estado-
	 * 
	 * @param companyCode compañía de la que se obtendrá la información.
	 * @return lista de estado-
	 */
	@GetMapping("/state/{companyCode}/{branchCode}")
	public ResponseEntity<ResponseModel> getState(@PathVariable String companyCode, @PathVariable String branchCode) {
		idOperation = generateIdOperation(companyCode, branchCode, module);

		LOG.info(String.format("%s INIT getState()", idOperation));
		LOG.info(
				String.format("%s PARAMS: [ companyCode: %s , branchCode: %s ]", idOperation, companyCode, branchCode));

		LOG.info("ACTION: SEE THE CATALOG OF ClientType");
		ResponseModel responseModel = new ResponseModel();

		MethodDto method = (MethodDto) companyServicePort
				.findMethodByCompanyCodeAndModule(companyCode, module, idOperation).getData();

		if (method != null) {

			responseModel = catalogueServicePort.getStateByCompanyCode(companyCode, method.getCode(), idOperation,
					catalogueOracleServicePort);
		} else {
			throw new ValidationError("method");
		}

		return ResponseEntity.status(HttpStatus.OK).body(responseModel);
	}



	/**
	 * Devuelve una lista de país-
	 * 
	 * @param companyCode compañía de la que se obtendrá la información.
	 * @return lista de país-
	 */
	@GetMapping("/country/{companyCode}/{branchCode}")
	public ResponseEntity<ResponseModel> getCountry(@PathVariable String companyCode, @PathVariable String branchCode) {
		idOperation = generateIdOperation(companyCode, branchCode, module);

		LOG.info(String.format("%s INIT getCountry()", idOperation));
		LOG.info(
				String.format("%s PARAMS: [ companyCode: %s , branchCode: %s ]", idOperation, companyCode, branchCode));

		LOG.info("ACTION: SEE THE CATALOG OF Country");
		ResponseModel responseModel = new ResponseModel();

		MethodDto method = (MethodDto) companyServicePort
				.findMethodByCompanyCodeAndModule(companyCode, module, idOperation).getData();
		if (method != null) {

			responseModel = catalogueServicePort.getCountryByCompanyCode(companyCode, method.getCode(), idOperation,
					catalogueOracleServicePort);
		} else {
			throw new ValidationError("method");
		}

		return ResponseEntity.status(HttpStatus.OK).body(responseModel);
	}

	

	/**
	 * Devuelve una lista de colonias por el código postal -
	 * 
	 * @param companyCode compañía de la que se obtendrá la información.
	 * @return lista de dirección-
	 */
	@GetMapping("/address-colony/{companyCode}/{branchCode}/{cp}")
	public ResponseEntity<ResponseModel> getAddressColony(@PathVariable String cp, @PathVariable String companyCode,
			@PathVariable String branchCode) {
		idOperation = generateIdOperation(companyCode, branchCode, module);

		LOG.info(String.format("%s INIT getAddressColony()", idOperation));
		LOG.info(String.format("%s PARAMS: [ cp: %s companyCode: %s , branchCode: %s ]", idOperation, cp, companyCode,
				branchCode));

		LOG.info("ACTION: SEE THE CATALOG OF ADDRESS");
		ResponseModel responseModel = new ResponseModel();

		MethodDto method = (MethodDto) companyServicePort
				.findMethodByCompanyCodeAndModule(companyCode, module, idOperation).getData();

		if (method != null) {

			responseModel = catalogueServicePort.getAddressColony(companyCode, method.getCode(), cp, idOperation,
					catalogueOracleServicePort);
		} else {
			throw new ValidationError("method");
		}

		return ResponseEntity.status(HttpStatus.OK).body(responseModel);
	}

	/**
	 * Devuelve una dirección por el código postal y la colonia -
	 * 
	 * @param companyCode compañía de la que se obtendrá la información.
	 * @return lista de dirección-
	 */
	@GetMapping("/address/{companyCode}/{branchCode}/{colony}/{cp}")
	public ResponseEntity<ResponseModel> getAddress(@PathVariable String colony, @PathVariable String cp,
			@PathVariable String companyCode, @PathVariable String branchCode) {

		idOperation = generateIdOperation(companyCode, branchCode, module);

		LOG.info(String.format("%s INIT getAddress()", idOperation));
		LOG.info(String.format("%s PARAMS: [ colony: %s , cp: %s , companyCode: %s , branchCode: %s ]", idOperation,
				colony, cp, companyCode, branchCode));

		LOG.info("ACTION: SEE THE CATALOG OF ADDRESS");
		ResponseModel responseModel = new ResponseModel();

		MethodDto method = (MethodDto) companyServicePort
				.findMethodByCompanyCodeAndModule(companyCode, module, idOperation).getData();

		if (method != null) {

			responseModel = catalogueServicePort.getAddress(companyCode, method.getCode(), colony, cp, idOperation,
					catalogueOracleServicePort);
		} else {
			throw new ValidationError("method");
		}

		return ResponseEntity.status(HttpStatus.OK).body(responseModel);
	}

	/**
	 * Devuelve el municipio por el id del estado -
	 * 
	 * @param companyCode compañía de la que se obtendrá la información.
	 * @return lista de dirección-
	 */
	@GetMapping("/address-delegation/{companyCode}/{branchCode}/{state}")
	public ResponseEntity<ResponseModel> getAddressDelegation(@PathVariable String state,
			@PathVariable String companyCode, @PathVariable String branchCode) {

		idOperation = generateIdOperation(companyCode, branchCode, module);

		LOG.info(String.format("%s INIT getAddressDelegation()", idOperation));
		LOG.info(String.format("%s PARAMS: [ state: %s , companyCode: %s , branchCode: %s ]", idOperation, state,
				companyCode, branchCode));

		LOG.info("ACTION: SEE THE CATALOG OF ADDRESS");
		ResponseModel responseModel = new ResponseModel();

		MethodDto method = (MethodDto) companyServicePort
				.findMethodByCompanyCodeAndModule(companyCode, module, idOperation).getData();

		if (method != null) {

			responseModel = catalogueServicePort.getAddressDelegation(companyCode, method.getCode(), state, idOperation,
					catalogueOracleServicePort);
		} else {
			throw new ValidationError("method");
		}

		return ResponseEntity.status(HttpStatus.OK).body(responseModel);
	}
	
	/**
	 * Devuelve una lista de coordenadar-
	 * 
	 * @param companyCode compañía de la que se obtendrá la información.
	 * @return lista de coordenadas-
	 */
	@GetMapping("/coordinate/{companyCode}/{branchCode}")
	public ResponseEntity<ResponseModel> getCoordinate(@PathVariable String companyCode,
			@PathVariable String branchCode) {
		idOperation = generateIdOperation(companyCode, branchCode, module);

		LOG.info(String.format("%s INIT getCoordinate()", idOperation));
		LOG.info(
				String.format("%s PARAMS: [ companyCode: %s , branchCode: %s ]", idOperation, companyCode, branchCode));

		LOG.info("ACTION: SEE THE CATALOG OF ClientType");
		ResponseModel responseModel = new ResponseModel();

		MethodDto method = (MethodDto) companyServicePort
				.findMethodByCompanyCodeAndModule(companyCode, module, idOperation).getData();

		if (method != null) {

			responseModel = catalogueServicePort.getCoordinateByCompanyCode(companyCode, method.getCode(), idOperation,
					catalogueOracleServicePort);
		} else {
			throw new ValidationError("method");
		}

		return ResponseEntity.status(HttpStatus.OK).body(responseModel);
	}

	/**
	 * Devuelve una lista de delegacioón-
	 * 
	 * @param companyCode compañía de la que se obtendrá la información.
	 * @return lista de delegacioón-
	 */
	@GetMapping("/delegation/{companyCode}/{branchCode}")
	public ResponseEntity<ResponseModel> getDelegation(@PathVariable String companyCode,
			@PathVariable String branchCode) {

		idOperation = generateIdOperation(companyCode, branchCode, module);

		LOG.info(String.format("%s INIT getDelegation()", idOperation));
		LOG.info(
				String.format("%s PARAMS: [ companyCode: %s , branchCode: %s ]", idOperation, companyCode, branchCode));

		LOG.info("ACTION: SEE THE CATALOG OF ClientType");
		ResponseModel responseModel = new ResponseModel();

		MethodDto method = (MethodDto) companyServicePort
				.findMethodByCompanyCodeAndModule(companyCode, module, idOperation).getData();

		if (method != null) {

			responseModel = catalogueServicePort.getDelegationByCompanyCode(companyCode, method.getCode(), idOperation,
					catalogueOracleServicePort);
		} else {
			throw new ValidationError("method");
		}

		return ResponseEntity.status(HttpStatus.OK).body(responseModel);
	}

	/**
	 * Devuelve una lista de plano-
	 * 
	 * @param companyCode compañía de la que se obtendrá la información.
	 * @return lista de plano-
	 */
	@GetMapping("/flat/{companyCode}/{branchCode}")
	public ResponseEntity<ResponseModel> getFlat(@PathVariable String companyCode, @PathVariable String branchCode) {
		idOperation = generateIdOperation(companyCode, branchCode, module);

		LOG.info(String.format("%s INIT getFlat()", idOperation));
		LOG.info(
				String.format("%s PARAMS: [ companyCode: %s , branchCode: %s ]", idOperation, companyCode, branchCode));

		LOG.info("ACTION: SEE THE CATALOG OF ClientType");
		ResponseModel responseModel = new ResponseModel();

		MethodDto method = (MethodDto) companyServicePort
				.findMethodByCompanyCodeAndModule(companyCode, module, idOperation).getData();

		if (method != null) {

			responseModel = catalogueServicePort.getFlatByCompanyCode(companyCode, method.getCode(), idOperation,
					catalogueOracleServicePort);
		} else {
			throw new ValidationError("method");
		}

		return ResponseEntity.status(HttpStatus.OK).body(responseModel);
	}


	/**
	 * Devuelve un estatus-
	 * 
	 * @param companyCode compañía de la que se obtendrá la información.
	 * @return estatus-
	 */
	@GetMapping("/status/{companyCode}/{branchCode}/{id}")
	public ResponseEntity<?> getStatus(@PathVariable String companyCode, @PathVariable String branchCode,
			@PathVariable Long id) {
		idOperation = generateIdOperation(companyCode, branchCode, module);
		LOG.info(String.format("%s INIT getStatus()", idOperation));
		LOG.info(String.format("%s PARAMS: [ companyCode: %s , branchCode: %s , id: %s ]", idOperation, companyCode,
				branchCode, id));
		ResponseModel responseModel = new ResponseModel();
		MethodDto method = (MethodDto) companyServicePort
				.findMethodByCompanyCodeAndModule(companyCode, module, idOperation).getData();
		if (method != null) {
			responseModel = catalogueServicePort.getStatus(companyCode, id, method.getCode(), idOperation,
					catalogueOracleServicePort);
		} else {
			throw new ValidationError("method");
		}
		return ResponseEntity.status(HttpStatus.OK).body(responseModel);
	}

	/**
	 * Crea un estatus-
	 * 
	 * @param companyCode compañía de la que se obtendrá la información.
	 * @return -
	 */
	@PostMapping("/status/create/{companyCode}/{branchCode}")
	public ResponseEntity<?> createStatus(@PathVariable String companyCode, @RequestBody @Valid StatusDto statusDto,
			@PathVariable String branchCode) {

		idOperation = generateIdOperation(companyCode, branchCode, module);

		LOG.info(String.format("%s INIT createStatus()", idOperation));
		LOG.info(String.format("%s PARAMS: [ companyCode: %s , branchCode: %s , statusDto: %s ]", idOperation,
				companyCode, branchCode, statusDto.toString()));

		ResponseModel responseModel = new ResponseModel();
		MethodDto method = (MethodDto) companyServicePort
				.findMethodByCompanyCodeAndModule(companyCode, module, idOperation).getData();

		if (method != null) {
			responseModel = catalogueServicePort.createStatus(companyCode, statusDto, method.getCode(), idOperation,
					catalogueOracleServicePort);
			LOG.info(idOperation + "Resultado de operacion" + responseModel.getResponseCode());
		} else {
			throw new ValidationError("method");
		}

		return ResponseEntity.status(HttpStatus.CREATED).body(responseModel);

	}

	@GetMapping("/status/list/{companyCode}/{branchCode}")
	public ResponseEntity<?> getStatusListByCompanyCode(@PathVariable String companyCode,
			@PathVariable String branchCode) {

		idOperation = generateIdOperation(companyCode, branchCode, module);

		LOG.info(String.format("%s INIT getStatusListByCompanyCode()", idOperation));
		LOG.info(
				String.format("%s PARAMS: [ companyCode: %s , branchCode: %s ]", idOperation, companyCode, branchCode));

		MethodDto method = (MethodDto) companyServicePort
				.findMethodByCompanyCodeAndModule(companyCode, module, idOperation).getData();

		if (method == null) {
			LOG.info(String.format("%s ERROR IN ASSIGNATION METHODS OF THE COMPANY %s ", idOperation, companyCode));
			throw new GlobalError();
		}

		ResponseModel responseModel = catalogueServicePort.getStatusListByCompanyCode(companyCode, method.getCode(),
				idOperation, catalogueOracleServicePort);

		return new ResponseEntity<>(responseModel, HttpStatus.OK);

	}
	


	



}