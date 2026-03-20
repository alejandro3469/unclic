package mx.com.endtoend.domain.catalogue.services;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.com.endtoend.domain.catalogue.bussiness.CatalogueFactory;
import mx.com.endtoend.domain.catalogue.bussiness.CatalogueInterface;
import mx.com.endtoend.domain.catalogue.ports.api.CatalogueServicePort;
import mx.com.endtoend.domain.catalogue.ports.spi.CataloguePersistencePort;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.GlobalError;
import mx.com.endtoend.infrastructure.services.jde.catalogue.common.serviceport.CatalogueJdeServicePort;
import mx.com.endtoend.smart.bussiness.model.catalogue.dto.StatusDto;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

public class CatalogueServicelmpl implements CatalogueServicePort {

	private CataloguePersistencePort cataloguePersistencePort;

	public CatalogueServicelmpl(CataloguePersistencePort cataloguePersistencePort) {
		this.cataloguePersistencePort = cataloguePersistencePort;

	}

	CatalogueFactory catalogueFactory = new CatalogueFactory();

	private final static Logger LOG = LoggerFactory.getLogger(CatalogueServicelmpl.class);

	@Override
	public ResponseModel getClientTypeByCompanyCode(String companyCode, String method, String idOperation, CatalogueJdeServicePort catalogueOracleServicePort) {
		
		LOG.info(String.format("%s INIT getClientTypeByCompanyCode() ", idOperation));
		LOG.info(String.format("%s PARAMS: [companyCode: %s , method: %s ]", idOperation, companyCode, method));
		
		ResponseModel responseModel = new ResponseModel();

		CatalogueInterface catalogueInterface = catalogueFactory.createFactory(method,catalogueOracleServicePort);

		if (catalogueInterface != null) {

			responseModel = catalogueInterface.getClientTypeByCompanyCode(companyCode, cataloguePersistencePort,
					idOperation);

		} else {
			
			LOG.error("AN ERROR OCCURRED WHILE RETRIEVING IMPLEMENTATION TYPE FOR COMPANY:" + companyCode + "-"
					+ idOperation);
			throw new GlobalError();
		}

		return responseModel;
	}

	@Override
	public ResponseModel getCoordinateByCompanyCode(String companyCode, String method, String idOperation,CatalogueJdeServicePort catalogueOracleServicePort) {
		
		LOG.info(String.format("%s INIT getCoordinateByCompanyCode() ", idOperation));
		LOG.info(String.format("%s PARAMS: [companyCode: %s , method: %s ]", idOperation, companyCode, method));

		ResponseModel responseModel = new ResponseModel();

		CatalogueInterface catalogueInterface = catalogueFactory.createFactory(method,catalogueOracleServicePort);

		if (catalogueInterface != null) {

			responseModel = catalogueInterface.getCoordinateByCompanyCode(companyCode, cataloguePersistencePort,
					idOperation);

		} else {
			LOG.error("AN ERROR OCCURRED WHILE RETRIEVING IMPLEMENTATION TYPE FOR COMPANY:" + companyCode + "-"
					+ idOperation);
			throw new GlobalError();
		}

		return responseModel;
	}

	@Override
	public ResponseModel getDelegationByCompanyCode(String companyCode, String method, String idOperation,CatalogueJdeServicePort catalogueOracleServicePort) {
		
		LOG.info(String.format("%s INIT getDelegationByCompanyCode() ", idOperation));
		LOG.info(String.format("%s PARAMS: [companyCode: %s , method: %s ]", idOperation, companyCode, method));

		ResponseModel responseModel = new ResponseModel();

		CatalogueInterface catalogueInterface = catalogueFactory.createFactory(method,catalogueOracleServicePort);

		if (catalogueInterface != null) {

			responseModel = catalogueInterface.getDelegationByCompanyCode(companyCode, cataloguePersistencePort,
					idOperation);

		} else {
			LOG.error("AN ERROR OCCURRED WHILE RETRIEVING IMPLEMENTATION TYPE FOR COMPANY:" + companyCode + "-"
					+ idOperation);
			throw new GlobalError();
		}

		return responseModel;
	}

	@Override
	public ResponseModel getFlatByCompanyCode(String companyCode, String method, String idOperation,CatalogueJdeServicePort catalogueOracleServicePort) {
		
		LOG.info(String.format("%s INIT getFlatByCompanyCode() ", idOperation));
		LOG.info(String.format("%s PARAMS: [companyCode: %s , method: %s ]", idOperation, companyCode, method));

		ResponseModel responseModel = new ResponseModel();

		CatalogueInterface catalogueInterface = catalogueFactory.createFactory(method,catalogueOracleServicePort);

		if (catalogueInterface != null) {

			responseModel = catalogueInterface.getFlatByCompanyCode(companyCode, cataloguePersistencePort, idOperation);

		} else {
			LOG.error("AN ERROR OCCURRED WHILE RETRIEVING IMPLEMENTATION TYPE FOR COMPANY:" + companyCode + "-"
					+ idOperation);
			throw new GlobalError();
		}
		return responseModel;
	}

	@Override
	public ResponseModel getHowToContactByCompanyCode(String companyCode, String method, String idOperation,CatalogueJdeServicePort catalogueOracleServicePort) {
		
		LOG.info(String.format("%s INIT getHowToContactByCompanyCode() ", idOperation));
		LOG.info(String.format("%s PARAMS: [companyCode: %s , method: %s ]", idOperation, companyCode, method));

		ResponseModel responseModel = new ResponseModel();

		CatalogueInterface catalogueInterface = catalogueFactory.createFactory(method,catalogueOracleServicePort);

		if (catalogueInterface != null) {

			responseModel = catalogueInterface.getHowToContactByCompanyCode(companyCode, cataloguePersistencePort,
					idOperation);

		} else {
			LOG.error("AN ERROR OCCURRED WHILE RETRIEVING IMPLEMENTATION TYPE FOR COMPANY:" + companyCode + "-"
					+ idOperation);
			throw new GlobalError();
		}

		return responseModel;
	}

	@Override
	public ResponseModel getStateByCompanyCode(String companyCode, String method, String idOperation,CatalogueJdeServicePort catalogueOracleServicePort) {
		
		LOG.info(String.format("%s INIT getStateByCompanyCode() ", idOperation));
		LOG.info(String.format("%s PARAMS: [companyCode: %s , method: %s ]", idOperation, companyCode, method));

		ResponseModel responseModel = new ResponseModel();

		CatalogueInterface catalogueInterface = catalogueFactory.createFactory(method,catalogueOracleServicePort);

		if (catalogueInterface != null) {

			responseModel = catalogueInterface.getStateByCompanyCode(companyCode, cataloguePersistencePort, idOperation);

		} else {
			LOG.error("AN ERROR OCCURRED WHILE RETRIEVING IMPLEMENTATION TYPE FOR COMPANY:" + companyCode + "-"
					+ idOperation);
			throw new GlobalError();
		}

		return responseModel;
	}

	@Override
	public ResponseModel getWorkTypeByCompanyCode(String companyCode, String method, String idOperation,CatalogueJdeServicePort catalogueOracleServicePort) {
		
		LOG.info(String.format("%s INIT getWorkTypeByCompanyCode() ", idOperation));
		LOG.info(String.format("%s PARAMS: [companyCode: %s , method: %s ]", idOperation, companyCode, method));

		ResponseModel responseModel = new ResponseModel();

		CatalogueInterface catalogueInterface = catalogueFactory.createFactory(method,catalogueOracleServicePort);

		if (catalogueInterface != null) {

			responseModel = catalogueInterface.getWorkTypeByCompanyCode(companyCode, cataloguePersistencePort,
					idOperation);

		} else {
			LOG.error("AN ERROR OCCURRED WHILE RETRIEVING IMPLEMENTATION TYPE FOR COMPANY:" + companyCode + "-"
					+ idOperation);
			throw new GlobalError();
		}

		return responseModel;
	}

	@Override
	public ResponseModel getCountryByCompanyCode(String companyCode, String method, String idOperation,CatalogueJdeServicePort catalogueOracleServicePort) {
		
		LOG.info(String.format("%s INIT getCountryByCompanyCode() ", idOperation));
		LOG.info(String.format("%s PARAMS: [companyCode: %s , method: %s ]", idOperation, companyCode, method));

		ResponseModel responseModel = new ResponseModel();

		CatalogueInterface catalogueInterface = catalogueFactory.createFactory(method,catalogueOracleServicePort);

		if (catalogueInterface != null) {

			responseModel = catalogueInterface.getCountryByCompanyCode(companyCode, cataloguePersistencePort, idOperation);

		} else {
			LOG.error("AN ERROR OCCURRED WHILE RETRIEVING IMPLEMENTATION TYPE FOR COMPANY:" + companyCode + "-"
					+ idOperation);
			throw new GlobalError();
		}

		return responseModel;
	}

	@Override
	public ResponseModel updateCatalogsByCompanyCode(String companyCode, String method, String idOperation,CatalogueJdeServicePort catalogueOracleServicePort) {
		
		LOG.info(String.format("%s INIT updateCatalogsByCompanyCode() ", idOperation));
		LOG.info(String.format("%s PARAMS: [companyCode: %s , method: %s ]", idOperation, companyCode, method));

		ResponseModel responseModel = new ResponseModel();

		CatalogueInterface catalogueInterface = catalogueFactory.createFactory(method,catalogueOracleServicePort);

		if (catalogueInterface != null) {

			responseModel = catalogueInterface.updateCatalogsByCompanyCode(companyCode, cataloguePersistencePort,
					idOperation);
			;

		} else {
			LOG.error("AN ERROR OCCURRED WHILE RETRIEVING IMPLEMENTATION TYPE FOR COMPANY:" + companyCode + "-"
					+ idOperation);
			throw new GlobalError();
		}

		return responseModel;
	}

	@Override
	public ResponseModel getAddressColony(String companyCode, String method, String cp, String idOperation,CatalogueJdeServicePort catalogueOracleServicePort) {
		
		LOG.info(String.format("%s INIT getAddressColony() ", idOperation));
		LOG.info(String.format("%s PARAMS: [companyCode: %s , method: %s , cp: %s]", idOperation, companyCode, method, cp));

		ResponseModel responseModel = new ResponseModel();

		CatalogueInterface catalogueInterface = catalogueFactory.createFactory(method,catalogueOracleServicePort);
		
		if (catalogueInterface != null) {

			responseModel = catalogueInterface.getAddressColony(companyCode, cataloguePersistencePort, cp, idOperation);

		} else {
			LOG.error("AN ERROR OCCURRED WHILE RETRIEVING IMPLEMENTATION TYPE FOR COMPANY:" + companyCode + "-"
					+ idOperation);
			throw new GlobalError();
		}

		return responseModel;
	}

	@Override
	public ResponseModel getAddress(String companyCode, String method, String colony, String cp, String idOperation,CatalogueJdeServicePort catalogueOracleServicePort) {
		
		LOG.info(String.format("%s INIT getAddress() ", idOperation));
		LOG.info(String.format("%s PARAMS: [companyCode: %s , method: %s , colony:%s , cp: %s]", idOperation, companyCode, method, colony, cp));

		ResponseModel responseModel = new ResponseModel();

		CatalogueInterface catalogueInterface = catalogueFactory.createFactory(method,catalogueOracleServicePort);

		if (catalogueInterface != null) {

			responseModel = catalogueInterface.getAddress(companyCode, cataloguePersistencePort, colony, cp, idOperation);

		} else {
			LOG.error("AN ERROR OCCURRED WHILE RETRIEVING IMPLEMENTATION TYPE FOR COMPANY:" + companyCode + "-"
					+ idOperation);
			throw new GlobalError();
		}

		return responseModel;
	}

	@Override
	public ResponseModel getAddressDelegation(String companyCode, String method, String state, String idOperation,CatalogueJdeServicePort catalogueOracleServicePort) {
		
		LOG.info(String.format("%s INIT getAddressDelegation() ", idOperation));
		LOG.info(String.format("%s PARAMS: [companyCode: %s , method: %s , state: %s ]", idOperation, companyCode, method, state));

		ResponseModel responseModel = new ResponseModel();

		CatalogueInterface catalogueInterface = catalogueFactory.createFactory(method,catalogueOracleServicePort);

		if (catalogueInterface != null) {

			responseModel = catalogueInterface.getAddressDelegation(companyCode, cataloguePersistencePort, state,
					idOperation);

		} else {
			LOG.error("AN ERROR OCCURRED WHILE RETRIEVING IMPLEMENTATION TYPE FOR COMPANY:" + companyCode + "-"
					+ idOperation);
			throw new GlobalError();
		}

		return responseModel;
	}

	@Override
	public ResponseModel getStatus(String companyCode, Long id, String method, String idOperation,CatalogueJdeServicePort catalogueOracleServicePort) {
		ResponseModel responseModel = new ResponseModel();
		LOG.info(String.format("%s INIT getStatus() ", idOperation));
		LOG.info(String.format("%s PARAMS: [companyCode: %s , method: %s , id: %s ]", idOperation, companyCode, method, id));
		CatalogueInterface catalogueInterface = catalogueFactory.createFactory(method,catalogueOracleServicePort);
		if (catalogueInterface != null) {
			responseModel = catalogueInterface.getStatus(companyCode, id, cataloguePersistencePort, idOperation);
		} else {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		return responseModel;
	}

	@Override
	public ResponseModel createStatus(String companyCode, StatusDto statusDto, String method, String idOperation,CatalogueJdeServicePort catalogueOracleServicePort) {
		ResponseModel responseModel = new ResponseModel();
		
		LOG.info(String.format("%s INIT createStatus() ", idOperation));
		LOG.info(String.format("%s PARAMS: [companyCode: %s , method: %s , statusDto: %s ]", idOperation, companyCode, method, statusDto.toString()));

		CatalogueInterface catalogueInterface = catalogueFactory.createFactory(method,catalogueOracleServicePort);

		if (catalogueInterface != null) {

			responseModel = catalogueInterface.createStatus(companyCode, statusDto, cataloguePersistencePort,
					idOperation);
		} else {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}

		LOG.info(String.format("%s START getStatus() ", idOperation));
		return responseModel;

	}

	@Override
	public ResponseModel getCfdi(String companyCode, String method, String idOperation,CatalogueJdeServicePort catalogueOracleServicePort) {

		ResponseModel responseModel = new ResponseModel();
		
		LOG.info(String.format("%s INIT getCfdi() ", idOperation));
		LOG.info(String.format("%s PARAMS: [companyCode: %s , method: %s ]", idOperation, companyCode, method));

		CatalogueInterface catalogueInterface = catalogueFactory.createFactory(method,catalogueOracleServicePort);

		if (catalogueInterface != null) {

			responseModel = catalogueInterface.getCfdi(companyCode, cataloguePersistencePort, idOperation);

		} else {

			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}

		LOG.info(String.format("%s START getStatus() ", idOperation));

		return responseModel;

	}

	@Override
	public ResponseModel getFiscalRegimeCatalogeByCompanyCode(String companyCode, String method, String idOperation,CatalogueJdeServicePort catalogueOracleServicePort) {

		ResponseModel responseFromPersistencePort = new ResponseModel();

		LOG.info(String.format("%s INIT getFiscalRegimeCatalogeByCompanyCode() ", idOperation));
		LOG.info(String.format("%s PARAMS: [companyCode: %s , method: %s ]", idOperation, companyCode, method));

		CatalogueInterface catalogueInterface = catalogueFactory.createFactory(method,catalogueOracleServicePort);

		if (catalogueInterface == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}

		responseFromPersistencePort = catalogueInterface.getFiscalRegimeCataloge(cataloguePersistencePort, companyCode,
				idOperation);

		LOG.info(String.format("%s RESULT FROM PERSISTENCE PORT: %d", idOperation,
				responseFromPersistencePort.getResponseCode()));

		return responseFromPersistencePort;

	}

	@Override
	public ResponseModel getCategoryListByCode(String companyCode, String branchCode, String method,
			String categoryCode, String idOperation,CatalogueJdeServicePort catalogueOracleServicePort) {
		 
		ResponseModel responseFromPersistencePort = new ResponseModel();

		LOG.info(String.format("%s INIT getCategoryListByCode() ", idOperation));
		LOG.info(String.format("%s PARAMS: [companyCode: %s , branchCode: %s , method: %s , categoryCode: %s ]",
				idOperation, companyCode, branchCode, method, categoryCode));

		CatalogueInterface catalogueInterface = catalogueFactory.createFactory(method,catalogueOracleServicePort);

		if (catalogueInterface == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}

		responseFromPersistencePort = catalogueInterface.getCategoryListByCodeAndCompanyCode(cataloguePersistencePort,
				companyCode, method, categoryCode, idOperation);

		LOG.info(String.format("%s RESULT FROM PERSISTENCE PORT: %d", idOperation,
				responseFromPersistencePort.getResponseCode()));

		return responseFromPersistencePort;
	}

	@Override
	public ResponseModel getStatusListByCompanyCode(String companyCode, String method, String idOperation,CatalogueJdeServicePort catalogueOracleServicePort) {

		ResponseModel responseFromPersistencePort = new ResponseModel();

		LOG.info(String.format("%s INIT getStatusListByCompanyCode() ", idOperation));
		LOG.info(String.format("%s PARAMS: [companyCode: %s , method: %s ]", idOperation, companyCode, method));

		CatalogueInterface catalogueInterface = catalogueFactory.createFactory(method,catalogueOracleServicePort);

		if (catalogueInterface == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}

		responseFromPersistencePort = catalogueInterface.getStatusListByCompanyCode(cataloguePersistencePort,
				companyCode, idOperation);

		LOG.info(String.format("%s RESULT FROM PERSISTENCE PORT: %d", idOperation,
				responseFromPersistencePort.getResponseCode()));

		return responseFromPersistencePort;

	}

}
