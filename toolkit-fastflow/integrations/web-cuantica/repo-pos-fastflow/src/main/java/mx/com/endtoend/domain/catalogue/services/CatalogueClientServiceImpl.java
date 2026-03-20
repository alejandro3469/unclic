package mx.com.endtoend.domain.catalogue.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.com.endtoend.domain.catalogue.bussiness.client.CatalogueClientFactory;
import mx.com.endtoend.domain.catalogue.bussiness.client.CatalogueClientInterface;
import mx.com.endtoend.domain.catalogue.ports.api.CatalogueClientServicePort;
import mx.com.endtoend.domain.catalogue.ports.spi.CatalogueClientPersistencePort;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.GlobalError;
import mx.com.endtoend.infrastructure.services.jde.catalogue.common.serviceport.CatalogueJdeServicePort;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

public class CatalogueClientServiceImpl implements CatalogueClientServicePort {

	private CatalogueClientPersistencePort catalogueClientPersistencePort;

	public CatalogueClientServiceImpl(CatalogueClientPersistencePort catalogueClientPersistencePort) {
		this.catalogueClientPersistencePort = catalogueClientPersistencePort;
	}

	private final static Logger LOG = LoggerFactory.getLogger(CatalogueClientServiceImpl.class);

	private CatalogueClientFactory catalogueClientFactory = new CatalogueClientFactory();

	@Override
	public ResponseModel getCfdiByCompanyCode(CatalogueJdeServicePort catalogueOracleServicePort, String method,
			String companyCode, String idOperation) {

		LOG.info(String.format("%s INIT getCfdiByCompanyCode() ", idOperation));
		LOG.info(String.format("%s PARAMS: [ method: %s , companyCode: %s ]", idOperation, method, companyCode));

		CatalogueClientInterface catalogueClient = catalogueClientFactory.getImplementationByCode(method,
				catalogueOracleServicePort);

		if (catalogueClient == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}

		ResponseModel responseModel = catalogueClient.getCfdiByCompanyCode(catalogueClientPersistencePort, companyCode,
				idOperation);

		return responseModel;
	}

	@Override
	public ResponseModel getRegimeFiscalCompanyCode(CatalogueJdeServicePort catalogueOracleServicePort,
			String method, String companyCode, String idOperation) {

		LOG.info(String.format("%s INIT getRegimeFiscalCompanyCode() ", idOperation));
		LOG.info(String.format("%s PARAMS: [ method: %s , companyCode: %s ]", idOperation, method, companyCode));

		CatalogueClientInterface catalogueClient = catalogueClientFactory.getImplementationByCode(method,
				catalogueOracleServicePort);

		if (catalogueClient == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}

		ResponseModel responseModel = catalogueClient.getRegimeFiscalCompanyCode(catalogueClientPersistencePort,
				companyCode, idOperation);

		return responseModel;
	}

	@Override
	public ResponseModel getClientTypeCompanyCode(CatalogueJdeServicePort catalogueOracleServicePort, String method,
			String companyCode, String idOperation) {

		LOG.info(String.format("%s INIT getClientTypeCompanyCode() ", idOperation));
		LOG.info(String.format("%s PARAMS: [ method: %s , companyCode: %s ]", idOperation, method, companyCode));

		CatalogueClientInterface catalogueClient = catalogueClientFactory.getImplementationByCode(method,
				catalogueOracleServicePort);

		if (catalogueClient == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}

		ResponseModel responseModel = catalogueClient.getClientTypeCompanyCode(catalogueClientPersistencePort,
				companyCode, idOperation);

		return responseModel;
	}

	@Override
	public ResponseModel getContectMethodCompanyCode(CatalogueJdeServicePort catalogueOracleServicePort,
			String method, String companyCode, String idOperation) {

		LOG.info(String.format("%s INIT getContectMethodCompanyCode() ", idOperation));
		LOG.info(String.format("%s PARAMS: [ method: %s , companyCode: %s ]", idOperation, method, companyCode));

		CatalogueClientInterface catalogueClient = catalogueClientFactory.getImplementationByCode(method,
				catalogueOracleServicePort);

		if (catalogueClient == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}

		ResponseModel responseModel = catalogueClient.getContectMethodCompanyCode(catalogueClientPersistencePort,
				companyCode, idOperation);

		return responseModel;
	}

	@Override
	public ResponseModel getWorkTypeByCompanyCode(CatalogueJdeServicePort catalogueOracleServicePort, String method,
			String companyCode, String idOperation) {

		LOG.info(String.format("%s INIT getWorkTypeByCompanyCode() ", idOperation));
		LOG.info(String.format("%s PARAMS: [ method: %s , companyCode: %s ]", idOperation, method, companyCode));

		CatalogueClientInterface catalogueClient = catalogueClientFactory.getImplementationByCode(method,
				catalogueOracleServicePort);

		if (catalogueClient == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}

		ResponseModel responseModel = catalogueClient.getWorkTypeByCompanyCode(catalogueClientPersistencePort,
				companyCode, idOperation);

		return responseModel;
	}

	@Override
	public ResponseModel updateCatalogueCompanyCodeAndCatalogueType(
			CatalogueJdeServicePort catalogueOracleServicePort, String method, String companyCode,
			String catalogueType, String idOperation) {

		LOG.info(String.format("%s INIT updateCatalogueCompanyCodeAndCatalogueType() ", idOperation));
		LOG.info(String.format("%s PARAMS: [ method: %s , companyCode: %s , catalogueType: %s ]", idOperation, method,
				companyCode, catalogueType));

		CatalogueClientInterface catalogueClient = catalogueClientFactory.getImplementationByCode(method,
				catalogueOracleServicePort);

		if (catalogueClient == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}

		ResponseModel responseModel = catalogueClient.updateCatalogueCompanyCodeAndCatalogueType(
				catalogueClientPersistencePort, companyCode, catalogueType, idOperation);

		return responseModel;
	}

}
