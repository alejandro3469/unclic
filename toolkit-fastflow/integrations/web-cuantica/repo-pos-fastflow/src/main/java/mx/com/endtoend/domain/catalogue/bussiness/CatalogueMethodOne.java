package mx.com.endtoend.domain.catalogue.bussiness;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.com.endtoend.domain.catalogue.ports.spi.CataloguePersistencePort;
import mx.com.endtoend.domain.commons.constants.GenericIdentifyMethods;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.GlobalError;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.ValidationError;
import mx.com.endtoend.infrastructure.services.jde.catalogue.common.serviceport.CatalogueJdeServicePort;
import mx.com.endtoend.smart.bussiness.model.catalogue.dto.StatusDto;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

public class CatalogueMethodOne implements CatalogueInterface {

	private CatalogueJdeServicePort catalogueOracleServicePort;

	public CatalogueMethodOne(CatalogueJdeServicePort catalogueOracleServicePort) {
		this.catalogueOracleServicePort = catalogueOracleServicePort;
	}

	private GenericIdentifyMethods method = GenericIdentifyMethods.CAT_CONF_ONE;

	private final static Logger LOG = LoggerFactory.getLogger(CatalogueMethodOne.class);

	/**
	 * Método que recupera la lista de tipo de clientes del sistema.
	 * 
	 * @param companyCode           código de compañía
	 * @param clientPersistencePort repositorio principal del sistema
	 * @param idoperation           identificador de traza
	 * 
	 * @return ResponseModel objeto con el código de resultado de la operación, en
	 *         caso exitoso contiene embebida la información recuperada
	 */
	@Override
	public ResponseModel getClientTypeByCompanyCode(String companyCode, CataloguePersistencePort catalogPersistencePort,
			String idOperation) {
		LOG.info(String.format("%s INIT getClientTypeByCompanyCode() ", idOperation));
		LOG.info(String.format("%s PARAMS: [companyCode: %s ]", idOperation, companyCode));

		ResponseModel responseModel = catalogPersistencePort.getClientTypeByCompanyCode(companyCode, method.toString(),
				idOperation);

		if (responseModel.getResponseCode() == 100) {
			LOG.info("BÚSQUEDA DE CATALOGO DE TIPO DE CLIENTE");
			return responseModel;
		} else {
			LOG.error("ERROR AL RECUPERAR EL CATALOGO DE TIPO DE CLIENTE");
			throw new GlobalError();
		}
	}

	/**
	 * Método que recupera la lista de coordenadas del sistema.
	 * 
	 * @param companyCode           código de compañía
	 * @param clientPersistencePort repositorio principal del sistema
	 * @param idoperation           identificador de traza
	 * 
	 * @return ResponseModel objeto con el código de resultado de la operación, en
	 *         caso exitoso contiene embebida la información recuperada
	 */
	@Override
	public ResponseModel getCoordinateByCompanyCode(String companyCode, CataloguePersistencePort catalogPersistencePort,
			String idOperation) {

		LOG.info(String.format("%s INIT getCoordinateByCompanyCode() ", idOperation));
		LOG.info(String.format("%s PARAMS: [companyCode: %s ]", idOperation, companyCode));

		ResponseModel responseModel = catalogPersistencePort.getCoordinateByCompanyCode(companyCode, method.toString(),
				idOperation);

		if (responseModel.getResponseCode() == 100) {
			LOG.info("BÚSQUEDA DE CATALOGO DE CORDENADAS");
			return responseModel;
		} else {
			LOG.error("ERROR AL RECUPERAR EL CATALOGO DE CORDENADAS");
			throw new GlobalError();
		}
	}

	/**
	 * Método que recupera la lista de delegacion del sistema.
	 * 
	 * @param companyCode           código de compañía
	 * @param clientPersistencePort repositorio principal del sistema
	 * @param idoperation           identificador de traza
	 * 
	 * @return ResponseModel objeto con el código de resultado de la operación, en
	 *         caso exitoso contiene embebida la información recuperada
	 */
	@Override
	public ResponseModel getDelegationByCompanyCode(String companyCode, CataloguePersistencePort catalogPersistencePort,
			String idOperation) {

		LOG.info(String.format("%s INIT getDelegationByCompanyCode() ", idOperation));
		LOG.info(String.format("%s PARAMS: [companyCode: %s ]", idOperation, companyCode));

		ResponseModel responseModel = catalogPersistencePort.getDelegationByCompanyCode(companyCode, method.toString(),
				idOperation);

		if (responseModel.getResponseCode() == 100) {
			LOG.info("BÚSQUEDA DE CATALOGO DE DELEGACION");
			return responseModel;
		} else {
			LOG.error("ERROR AL RECUPERAR EL CATALOGO DE DELEGACION");
			throw new GlobalError();
		}
	}

	/**
	 * Método que recupera la lista de planos del sistema.
	 * 
	 * @param companyCode           código de compañía
	 * @param clientPersistencePort repositorio principal del sistema
	 * @param idoperation           identificador de traza
	 * 
	 * @return ResponseModel objeto con el código de resultado de la operación, en
	 *         caso exitoso contiene embebida la información recuperada
	 */
	@Override
	public ResponseModel getFlatByCompanyCode(String companyCode, CataloguePersistencePort catalogPersistencePort,
			String idOperation) {

		LOG.info(String.format("%s INIT getFlatByCompanyCode() ", idOperation));
		LOG.info(String.format("%s PARAMS: [companyCode: %s ]", idOperation, companyCode));

		ResponseModel responseModel = catalogPersistencePort.getFlatByCompanyCode(companyCode, method.toString(),
				idOperation);

		if (responseModel.getResponseCode() == 100) {
			LOG.info("BÚSQUEDA DE CATALOGO DE PLANO");
			return responseModel;
		} else {
			LOG.error("ERROR AL RECUPERAR EL CATALOGO DE PLANO");
			throw new GlobalError();
		}
	}

	/**
	 * Método que recupera la lista de como nos contacto del sistema.
	 * 
	 * @param companyCode           código de compañía
	 * @param clientPersistencePort repositorio principal del sistema
	 * @param idoperation           identificador de traza
	 * 
	 * @return ResponseModel objeto con el código de resultado de la operación, en
	 *         caso exitoso contiene embebida la información recuperada
	 */
	@Override
	public ResponseModel getHowToContactByCompanyCode(String companyCode,
			CataloguePersistencePort catalogPersistencePort, String idOperation) {

		LOG.info(String.format("%s INIT getHowToContactByCompanyCode() ", idOperation));
		LOG.info(String.format("%s PARAMS: [companyCode: %s ]", idOperation, companyCode));

		ResponseModel responseModel = catalogPersistencePort.getHowToContactByCompanyCode(companyCode,
				method.toString(), idOperation);

		if (responseModel.getResponseCode() == 100) {
			LOG.info("BÚSQUEDA DE CATALOGO DE COMO NOS CONTACTO");
			return responseModel;
		} else {
			LOG.error("ERROR AL RECUPERAR EL CATALOGO DE COMO NOS CONTACTO");
			throw new GlobalError();
		}
	}

	/**
	 * Método que recupera la lista de estado del sistema.
	 * 
	 * @param companyCode           código de compañía
	 * @param clientPersistencePort repositorio principal del sistema
	 * @param idoperation           identificador de traza
	 * 
	 * @return ResponseModel objeto con el código de resultado de la operación, en
	 *         caso exitoso contiene embebida la información recuperada
	 */
	@Override
	public ResponseModel getStateByCompanyCode(String companyCode, CataloguePersistencePort catalogPersistencePort,
			String idOperation) {

		LOG.info(String.format("%s INIT getStateByCompanyCode() ", idOperation));
		LOG.info(String.format("%s PARAMS: [companyCode: %s ]", idOperation, companyCode));

		ResponseModel responseModel = catalogPersistencePort.getStateByCompanyCode(companyCode, method.toString(),
				idOperation);

		if (responseModel.getResponseCode() == 100) {
			LOG.info("BÚSQUEDA DE CATALOGO DE ESTADO");
			return responseModel;
		} else {
			LOG.error("ERROR AL RECUPERAR EL CATALOGO DE ESTADO");
			throw new GlobalError();
		}
	}

	/**
	 * Método que recupera la lista de tipo de obra del sistema.
	 * 
	 * @param companyCode           código de compañía
	 * @param clientPersistencePort repositorio principal del sistema
	 * @param idoperation           identificador de traza
	 * 
	 * @return ResponseModel objeto con el código de resultado de la operación, en
	 *         caso exitoso contiene embebida la información recuperada
	 */
	@Override
	public ResponseModel getWorkTypeByCompanyCode(String companyCode, CataloguePersistencePort catalogPersistencePort,
			String idOperation) {

		LOG.info(String.format("%s INIT getWorkTypeByCompanyCode() ", idOperation));
		LOG.info(String.format("%s PARAMS: [companyCode: %s ]", idOperation, companyCode));

		ResponseModel responseModel = catalogPersistencePort.getCoordinateByCompanyCode(companyCode, method.toString(),
				idOperation);

		if (responseModel.getResponseCode() == 100) {
			LOG.info("BÚSQUEDA DE CATALOGO DE TIPO DE OBRA");
			return responseModel;
		} else {
			LOG.error("ERROR AL RECUPERAR EL CATALOGO DE TIPO DE OBRA");
			throw new GlobalError();
		}
	}

	/**
	 * Método que recupera la lista de país del sistema.
	 * 
	 * @param companyCode           código de compañía
	 * @param clientPersistencePort repositorio principal del sistema
	 * @param idoperation           identificador de traza
	 * 
	 * @return ResponseModel objeto con el código de resultado de la operación, en
	 *         caso exitoso contiene embebida la información recuperada
	 */
	@Override
	public ResponseModel getCountryByCompanyCode(String companyCode, CataloguePersistencePort catalogPersistencePort,
			String idOperation) {

		LOG.info(String.format("%s INIT getCountryByCompanyCode() ", idOperation));
		LOG.info(String.format("%s PARAMS: [companyCode: %s ]", idOperation, companyCode));

		ResponseModel responseModel = catalogPersistencePort.getCountryByCompanyCode(companyCode, method.toString(),
				idOperation);

		if (responseModel.getResponseCode() == 100) {
			LOG.info("BÚSQUEDA DE CATALOGO DE PAÍS");
			return responseModel;
		} else {
			LOG.error("ERROR AL RECUPERAR EL CATALOGO DE PAÍS");
			throw new GlobalError();
		}
	}

	/**
	 * Método que recupera los catalogos de JDE y los almacena en MYSql.
	 * 
	 * @param companyCode           código de compañía
	 * @param clientPersistencePort repositorio principal del sistema
	 * @param idoperation           identificador de traza
	 * 
	 * @return ResponseModel objeto con el código de resultado de la operación, en
	 *         caso exitoso contiene embebida la información recuperada
	 */
	@Override
	public ResponseModel updateCatalogsByCompanyCode(String companyCode,
			CataloguePersistencePort catalogPersistencePort, String idOperation) {

		LOG.info(String.format("%s INIT updateCatalogsByCompanyCode() ", idOperation));
		LOG.info(String.format("%s PARAMS: [companyCode: %s ]", idOperation, companyCode));

		ResponseModel responseModel = new ResponseModel();

		catalogueOracleServicePort.updateCatalogsByCompanyCode(companyCode, idOperation);

		LOG.info("SE ACTUALIZAN LOS CATALOGOS DE LA COMPANIA: " + companyCode);
		responseModel = new ResponseModel("OK");

		return responseModel;
	}

	/**
	 * Método que recupera la lista de colonia del sistema
	 * 
	 * @param companyCode           código de compañía
	 * @param clientPersistencePort repositorio principal del sistema
	 * @param idoperation           identificador de traza
	 * 
	 * @return ResponseModel objeto con el código de resultado de la operación, en
	 *         caso exitoso contiene embebida la información recuperada
	 */
	@Override
	public ResponseModel getAddressColony(String companyCode, CataloguePersistencePort catalogPersistencePort,
			String cp, String idOperation) {

		LOG.info(String.format("%s INIT getAddressColony() ", idOperation));
		LOG.info(String.format("%s PARAMS: [companyCode: %s ]", idOperation, companyCode));

		ResponseModel responseModel = catalogueOracleServicePort.getAddressColony(companyCode, cp, idOperation);

		if (responseModel.getResponseCode() == 100) {
			LOG.info("BÚSQUEDA DE CATALOGO DE DIRECCION");
			return responseModel;
		} else {
			LOG.error("ERROR AL RECUPERAR EL CATALOGO DE DIRECCION");
			throw new GlobalError();
		}
	}

	/**
	 * Método que recupera la lista de dirección del sistema
	 * 
	 * @param companyCode           código de compañía
	 * @param clientPersistencePort repositorio principal del sistema
	 * @param idoperation           identificador de traza
	 * 
	 * @return ResponseModel objeto con el código de resultado de la operación, en
	 *         caso exitoso contiene embebida la información recuperada
	 */
	@Override
	public ResponseModel getAddress(String companyCode, CataloguePersistencePort catalogPersistencePort, String colony,
			String cp, String idOperation) {

		LOG.info(String.format("%s INIT getAddress() ", idOperation));
		LOG.info(String.format("%s PARAMS: [companyCode: %s ]", idOperation, companyCode));

		ResponseModel responseModel = catalogueOracleServicePort.getAddress(companyCode, colony, cp, idOperation);

		if (responseModel.getResponseCode() == 100) {
			LOG.info("BÚSQUEDA DE CATALOGO DE DIRECCION");
			return responseModel;
		} else {
			LOG.error("ERROR AL RECUPERAR EL CATALOGO DE DIRECCION");
			throw new GlobalError();
		}
	}

	/**
	 * Método que recupera la lista de delegacion del sistema
	 * 
	 * @param companyCode           código de compañía
	 * @param clientPersistencePort repositorio principal del sistema
	 * @param idoperation           identificador de traza
	 * 
	 * @return ResponseModel objeto con el código de resultado de la operación, en
	 *         caso exitoso contiene embebida la información recuperada
	 */
	@Override
	public ResponseModel getAddressDelegation(String companyCode, CataloguePersistencePort catalogPersistencePort,
			String state, String idOperation) {

		LOG.info(String.format("%s INIT getAddressDelegation() ", idOperation));
		LOG.info(String.format("%s PARAMS: [companyCode: %s , state: %s ]", idOperation, companyCode, state));

		ResponseModel responseModel = catalogueOracleServicePort.getAddressDelegation(companyCode, state, idOperation);

		if (responseModel.getResponseCode() == 100) {
			LOG.info("BÚSQUEDA DE CATALOGO DE DIRECCION");
			return responseModel;
		} else {
			LOG.error("ERROR AL RECUPERAR EL CATALOGO DE DIRECCION");
			throw new GlobalError();
		}
	}

	/**
	 * Método que recupera la lista de estado del sistema
	 * 
	 * @param companyCode           código de compañía
	 * @param clientPersistencePort repositorio principal del sistema
	 * @param idoperation           identificador de traza
	 * 
	 * @return ResponseModel objeto con el código de resultado de la operación, en
	 *         caso exitoso contiene embebida la información recuperada
	 */
	@Override
	public ResponseModel getStatus(String companyCode, Long id, CataloguePersistencePort catalogPersistencePort,
			String idOperation) {
		LOG.info(String.format("%s INIT getStatus() ", idOperation));
		LOG.info(String.format("%s PARAMS: [companyCode: %s , id: %s ]", idOperation, companyCode, id));

		ResponseModel responseModel = new ResponseModel();

		responseModel = catalogPersistencePort.getStatus(companyCode, id, method.toString(), idOperation);

		if (responseModel.getResponseCode() == 100) {

			LOG.info(
					String.format("%s RESULT FROM PERSISTENCE PORT: %d", idOperation, responseModel.getResponseCode()));
			return responseModel;
		} else {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}

	}

	/**
	 * Método que para crear estados en el catalogo.
	 * 
	 * @param companyCode           código de compañía
	 * @param clientPersistencePort repositorio principal del sistema
	 * @param idoperation           identificador de traza
	 * 
	 * @return ResponseModel objeto con el código de resultado de la operación, en
	 *         caso exitoso contiene embebida la información recuperada
	 */
	@Override
	public ResponseModel createStatus(String companyCode, StatusDto statusDto,
			CataloguePersistencePort catalogPersistencePort, String idOperation) {

		LOG.info(String.format("%s INIT createStatus() ", idOperation));
		LOG.info(String.format("%s PARAMS: [companyCode: %s , statusDto: %s ]", idOperation, companyCode,
				statusDto.toString()));

		ResponseModel responseModel = new ResponseModel();
		boolean existsStatus = catalogPersistencePort.existsStatus(statusDto, companyCode, method.toString(),
				idOperation);

		if (existsStatus) {
			LOG.info("Ya existe");
			throw new ValidationError("Ya existe");
		} else {

			catalogPersistencePort.createStatus(companyCode, statusDto, method.toString(), idOperation);
			LOG.info(
					String.format("%s RESULT FROM PERSISTENCE PORT: %d", idOperation, responseModel.getResponseCode()));
			responseModel = new ResponseModel("OK");
		}

		return responseModel;

	}

	/**
	 * Método que recupera la lista de cfi del sistema
	 * 
	 * @param companyCode           código de compañía
	 * @param clientPersistencePort repositorio principal del sistema
	 * @param idoperation           identificador de traza
	 * 
	 * @return ResponseModel objeto con el código de resultado de la operación, en
	 *         caso exitoso contiene embebida la información recuperada
	 */
	@Override
	public ResponseModel getCfdi(String companyCode, CataloguePersistencePort catalogPersistencePort,
			String idOperation) {

		LOG.info(String.format("%s INIT getCfdi() ", idOperation));
		LOG.info(String.format("%s PARAMS: [companyCode: %s ]", idOperation, companyCode));

		ResponseModel responseModel = new ResponseModel();

		responseModel = catalogPersistencePort.getCfdi(companyCode, method.toString(), idOperation);

		if (responseModel.getResponseCode() == 100) {

			LOG.info(
					String.format("%s RESULT FROM PERSISTENCE PORT: %d", idOperation, responseModel.getResponseCode()));
			return responseModel;

		} else {

			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}

	}

	/**
	 * Método que recupera la lista de régimen fiscal del sistema
	 * 
	 * @param companyCode           código de compañía
	 * @param clientPersistencePort repositorio principal del sistema
	 * @param idoperation           identificador de traza
	 * 
	 * @return ResponseModel objeto con el código de resultado de la operación, en
	 *         caso exitoso contiene embebida la información recuperada
	 */
	@Override
	public ResponseModel getFiscalRegimeCataloge(CataloguePersistencePort catalogPersistencePort, String companyCode,
			String idOperation) {

		LOG.info(String.format("%s INIT getFiscalRegimeCataloge() ", idOperation));
		LOG.info(String.format("%s PARAMS: [companyCode: %s ]", idOperation, companyCode));

		ResponseModel responseFromPersistencePort = catalogPersistencePort
				.getFiscalRegimeCatalogeByCompanyCode(companyCode, method.toString(), idOperation);

		if (responseFromPersistencePort.getData() == null) {
			LOG.error(String.format("%s ERROR IN GET FISCAL REGIME CATALOGE TO COMPANY %s ", idOperation, companyCode));
			throw new GlobalError();
		}

		LOG.info(String.format("%s RETURN FISCAL REGIME CATALOGE", idOperation));

		return responseFromPersistencePort;
	}

	/**
	 * Método que recupera la lista de categoría del sistema
	 * 
	 * @param companyCode           código de compañía
	 * @param clientPersistencePort repositorio principal del sistema
	 * @param idoperation           identificador de traza
	 * 
	 * @return ResponseModel objeto con el código de resultado de la operación, en
	 *         caso exitoso contiene embebida la información recuperada
	 */
	@Override
	public ResponseModel getCategoryListByCodeAndCompanyCode(CataloguePersistencePort catalogPersistencePort,
			String companyCode, String method, String categoryCode, String idOperation) {

		LOG.info(String.format("%s INIT getCategoryListByCodeAndCompanyCode() ", idOperation));
		LOG.info(String.format("%s PARAMS: [companyCode: %s , method: %s , categoryCode: %s ]", idOperation,
				companyCode, method, categoryCode));

		ResponseModel responseFromPersistencePort = catalogPersistencePort
				.getCategoryListByCompanyCodeAndCategoryCode(companyCode, method, categoryCode, idOperation);

		if (responseFromPersistencePort.getData() == null) {
			LOG.error(String.format("%s ERROR IN GET CATEGORY LIST BY CODE: %s AND COMPANY: ", idOperation,
					categoryCode, companyCode));
			throw new GlobalError();
		}

		LOG.info(String.format("%s RETURN CATEGORY LIST", idOperation));

		return responseFromPersistencePort;

	}

	/**
	 * Método que recupera la lista de Estado del sistema
	 * 
	 * @param companyCode           código de compañía
	 * @param clientPersistencePort repositorio principal del sistema
	 * @param idoperation           identificador de traza
	 * 
	 * @return ResponseModel objeto con el código de resultado de la operación, en
	 *         caso exitoso contiene embebida la información recuperada
	 */
	@Override
	public ResponseModel getStatusListByCompanyCode(CataloguePersistencePort cataloguePersistencePort,
			String companyCode, String idOperation) {

		LOG.info(String.format("%s INIT getStatusListByCompanyCode() ", idOperation));
		LOG.info(String.format("%s PARAMS: [companyCode: %s ]", idOperation, companyCode));

		ResponseModel responseFromPersistencePort = cataloguePersistencePort.getStatusListByCompanyCode(companyCode,
				method.toString(), idOperation);

		if (responseFromPersistencePort.getData() == null) {
			LOG.error(String.format("%s ERROR IN GET STATUS-LIST TO COMPANY %s ", idOperation, companyCode));
			throw new GlobalError();
		}

		LOG.info(String.format("%s RETURN STATUS-LIST", idOperation));

		return responseFromPersistencePort;

	}

}
