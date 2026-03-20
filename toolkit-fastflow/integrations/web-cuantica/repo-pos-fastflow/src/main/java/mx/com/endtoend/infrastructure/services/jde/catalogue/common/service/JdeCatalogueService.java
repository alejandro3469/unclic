package mx.com.endtoend.infrastructure.services.jde.catalogue.common.service;

import java.util.List;

import mx.com.endtoend.infrastructure.services.jde.catalogue.common.factory.CatalogueJdeRepositoryFactory;
import mx.com.endtoend.infrastructure.services.jde.catalogue.common.repository.GenericCatalogueJdeRepository;
import mx.com.endtoend.infrastructure.services.jde.catalogue.common.serviceport.CatalogueJdeServicePort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.com.endtoend.domain.catalogue.dto.CatalogueDto;
import mx.com.endtoend.infrastructure.catalogue.orders.common.enums.CatalogueType;
import mx.com.endtoend.infrastructure.catalogue.orders.common.entities.CatalogueEntity;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.GlobalError;
import mx.com.endtoend.smart.bussiness.model.catalogue.dto.StatusDto;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

@Service
public class JdeCatalogueService implements CatalogueJdeServicePort {

	@Autowired
	private CatalogueJdeRepositoryFactory catalogueStoreFactory;

	private final Logger LOG = LoggerFactory.getLogger(JdeCatalogueService.class);

	/**
	 * Método que recupera la dirección de una sucursal del sistema JDE con base en
	 * el código de la sucursal
	 */
	@Override
	public ResponseModel getBrancgAddressByCode(String branchCode, String companyCode, String idOperation) {
		LOG.info(String.format("%s INIT getBrancgAddressByCode()", idOperation));
		LOG.info(String.format("%s PARAMS [companyCode: %s , branchCode: %s ]", idOperation, companyCode, branchCode));
		GenericCatalogueJdeRepository catalogueRepository = catalogueStoreFactory.getRepository(companyCode);
		if (catalogueRepository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		ResponseModel responseModel = catalogueRepository.getBrancgAddressByCode(branchCode, idOperation);
		return responseModel;
	}

	/**
	 * Método que recupera la lista de tipo de clientes del JDE.
	 *
	 * @param companyCode código de compañía
	 * @param idOperation identificador de traza
	 * @return ResponseModel objeto con el código de resultado de la operación, en
	 *         caso exitoso contiene embebida la información recuperada
	 */
	@Override
	public ResponseModel getClientTypeByCompanyCode(String companyCode, String idOperation) {
		LOG.info(String.format("%s INIT getClientTypeByCompanyCode()", idOperation));
		LOG.info(String.format("%s PARAMS [companyCode: %s ]", idOperation, companyCode));
		GenericCatalogueJdeRepository catalogueRepository = catalogueStoreFactory.getRepository(companyCode);
		if (catalogueRepository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		ResponseModel responseModel = catalogueRepository.getClientType(idOperation);
		return responseModel;
	}

	/**
	 * Método que recupera la lista de como nos contactó del sistema.
	 *
	 * @param companyCode código de compañía
	 * @param idOperation identificador de traza
	 * @return ResponseModel objeto con el código de resultado de la operación, en
	 *         caso exitoso contiene embebida la información recuperada
	 */
	@Override
	public ResponseModel getHowToContactByCompanyCode(String companyCode, String idOperation) {
		LOG.info(String.format("%s INIT getClientTypeByCompanyCode()", idOperation));
		LOG.info(String.format("%s PARAMS [companyCode: %s ]", idOperation, companyCode));
		GenericCatalogueJdeRepository catalogueRepository = catalogueStoreFactory.getRepository(companyCode);
		if (catalogueRepository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		ResponseModel responseModel = catalogueRepository.getHowToContactByCompanyCode(idOperation);
		return responseModel;
	}

	/**
	 * Método que recupera la lista de cfi del sistema
	 *
	 * @param companyCode código de compañía
	 * @param idOperation identificador de traza
	 * @return ResponseModel objeto con el código de resultado de la operación, en
	 *         caso exitoso contiene embebida la información recuperada
	 */
	@Override
	public ResponseModel getCfdi(String companyCode, String idOperation) {
		LOG.info(String.format("%s INIT getCfdi()", idOperation));
		LOG.info(String.format("%s PARAMS [companyCode: %s ]", idOperation, companyCode));
		GenericCatalogueJdeRepository catalogueRepository = catalogueStoreFactory.getRepository(companyCode);
		if (catalogueRepository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		ResponseModel responseModel = catalogueRepository.getCfdi(idOperation);
		return responseModel;
	}

	/**
	 * Método que recupera la lista de tipo de obra del sistema.
	 *
	 * @param companyCode código de compañía
	 * @param idOperation identificador de traza
	 * @return ResponseModel objeto con el código de resultado de la operación, en
	 *         caso exitoso contiene embebida la información recuperada
	 */
	@Override
	public ResponseModel getWorkTypeByCompanyCode(String companyCode, String idOperation) {
		LOG.info(String.format("%s INIT getWorkTypeByCompanyCode()", idOperation));
		LOG.info(String.format("%s PARAMS [companyCode: %s ]", idOperation, companyCode));
		GenericCatalogueJdeRepository catalogueRepository = catalogueStoreFactory.getRepository(companyCode);
		if (catalogueRepository == null) {

			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		ResponseModel responseModel = catalogueRepository.getWorkTypeByCompanyCode(idOperation);
		return responseModel;
	}

	/**
	 * Método que recupera la lista de régimen fiscal del sistema
	 *
	 * @param companyCode código de compañía
	 * @param idOperation identificador de traza
	 * @return ResponseModel objeto con el código de resultado de la operación, en
	 *         caso exitoso contiene embebida la información recuperada
	 */
	@Override
	public ResponseModel getFiscalRegimeCatalogeByCompanyCode(String companyCode, String idOperation) {
		LOG.info(String.format("%s INIT getFiscalRegimeCatalogeByCompanyCode() ", idOperation));
		GenericCatalogueJdeRepository catalogueRepository = catalogueStoreFactory.getRepository(companyCode);
		if (catalogueRepository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		ResponseModel fiscalRegimeCatalogue = catalogueRepository.getFiscalRegimeCatalogue(idOperation);
		return fiscalRegimeCatalogue;
	}

	/**
	 * Método que recupera el repositorio de un cliente por el código de compañia
	 * para la recuperación de la lista de marcas de artículos
	 *
	 * @param companyCode código de compañía
	 * @param idOperation identificador de traza de operación
	 * @return ResponseModel objeto con el código de resultado de la operación, en
	 *         caso exitoso contiene embebida la información recuperada
	 */
	@Override
	public ResponseModel getArticleBrandByCompanyCode(String companyCode, String idOperation) {
		LOG.info(String.format("%s INIT getArticleBrandByCompanyCode()", idOperation));
		LOG.info(String.format("%s PARAMS [companyCode: %s ]", idOperation, companyCode));
		GenericCatalogueJdeRepository catalogueRepository = catalogueStoreFactory.getRepository(companyCode);
		if (catalogueRepository == null) {

			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		ResponseModel responseModel = catalogueRepository.getArticleBrand(idOperation);
		return responseModel;
	}

	/**
	 * Método que recupera el repositorio de un cliente por el código de compañia
	 * para la recuperación de la lista de categorias de artículos
	 *
	 * @param companyCode código de compañía
	 * @param idOperation identificador de traza de operación
	 * @return ResponseModel objeto con el código de resultado de la operación, en
	 *         caso exitoso contiene embebida la información recuperada
	 */
	@Override
	public ResponseModel getArticleCategoryByCompanyCode(String companyCode, String idOperation) {
		LOG.info(String.format("%s INIT getArticleCategoryByCompanyCode()", idOperation));
		LOG.info(String.format("%s PARAMS [companyCode: %s ]", idOperation, companyCode));
		GenericCatalogueJdeRepository catalogueRepository = catalogueStoreFactory.getRepository(companyCode);
		if (catalogueRepository == null) {

			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		ResponseModel responseModel = catalogueRepository.getArticleCategory(idOperation);
		return responseModel;
	}

	/**
	 * Método que recupera el repositorio de un cliente por el código de compañia
	 * para la recuperación de la lista de familias de artículos
	 *
	 * @param companyCode código de compañía
	 * @param idOperation identificador de traza de operación
	 * @return ResponseModel objeto con el código de resultado de la operación, en
	 *         caso exitoso contiene embebida la información recuperada
	 */
	@Override
	public ResponseModel getArticleFamilyByCompanyCode(String companyCode, String idOperation) {
		LOG.info(String.format("%s INIT getArticleFamilyByCompanyCode()", idOperation));
		LOG.info(String.format("%s PARAMS [companyCode: %s ]", idOperation, companyCode));
		GenericCatalogueJdeRepository catalogueRepository = catalogueStoreFactory.getRepository(companyCode);
		if (catalogueRepository == null) {

			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		ResponseModel responseModel = catalogueRepository.getArticleFamily(idOperation);
		return responseModel;
	}

	/**
	 * Método que recupera el repositorio de un cliente por el código de compañía
	 * para la recuperación de la lista de divisiones de artículos
	 *
	 * @param companyCode código de compañía
	 * @param idOperation identificador de traza de operación
	 * @return ResponseModel objeto con el código de resultado de la operación, en
	 *         caso exitoso contiene embebida la información recuperada
	 */
	@Override
	public ResponseModel getArticleDivisionByCompanyCode(String companyCode, String idOperation) {
		LOG.info(String.format("%s INIT getArticleDivisionByCompanyCode()", idOperation));
		LOG.info(String.format("%s PARAMS [companyCode: %s ]", idOperation, companyCode));
		GenericCatalogueJdeRepository catalogueRepository = catalogueStoreFactory.getRepository(companyCode);
		if (catalogueRepository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		ResponseModel responseModel = catalogueRepository.getArticleDivision(idOperation);
		return responseModel;
	}

	/**
	 * Método que recupera el repositorio de un cliente por el código de compañía
	 * para la recuperación de la lista de coordenadas del sistema.
	 *
	 * @param companyCode código de compañía
	 * @param idOperation identificador de traza
	 * @return ResponseModel objeto con el código de resultado de la operación, en
	 *         caso exitoso contiene embebida la información recuperada
	 */
	@Override
	public ResponseModel getCoordinateByCompanyCode(String companyCode, String idOperation) {
		LOG.info(String.format("%s INIT getCoordinateByCompanyCode()", idOperation));
		LOG.info(String.format("%s PARAMS [companyCode: %s ]", idOperation, companyCode));
		GenericCatalogueJdeRepository catalogueRepository = catalogueStoreFactory.getRepository(companyCode);
		if (catalogueRepository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		return catalogueRepository.getCoordinate(idOperation);
	}

	/**
	 * Método que recupera el repositorio de un cliente por el código de compañía
	 * para la recuperación de la lista de planos del sistema.
	 *
	 * @param companyCode código de compañía
	 * @param idOperation identificador de traza
	 * @return ResponseModel objeto con el código de resultado de la operación, en
	 *         caso exitoso contiene embebida la información recuperada
	 */
	@Override
	public ResponseModel getFlatByCompanyCode(String companyCode, String idOperation) {
		LOG.info(String.format("%s INIT getFlatByCompanyCode()", idOperation));
		LOG.info(String.format("%s PARAMS [companyCode: %s ]", idOperation, companyCode));
		GenericCatalogueJdeRepository catalogueRepository = catalogueStoreFactory.getRepository(companyCode);
		if (catalogueRepository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		return catalogueRepository.getFlat(idOperation);

	}

	/**
	 * Método que recupera el repositorio de un cliente por código de compañía para
	 * la recuperación de la lista de países del sistema.
	 *
	 * @param companyCode código de compañía
	 * @param idOperation identificador de traza
	 * @return ResponseModel objeto con el código de resultado de la operación, en
	 *         caso exitoso contiene embebida la información recuperada
	 */
	@Override
	public ResponseModel getCountryByCompanyCode(String companyCode, String idOperation) {
		LOG.info(String.format("%s INIT getCountryByCompanyCode()", idOperation));
		LOG.info(String.format("%s PARAMS [companyCode: %s ]", idOperation, companyCode));
		GenericCatalogueJdeRepository catalogueRepository = catalogueStoreFactory.getRepository(companyCode);
		if (catalogueRepository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		return catalogueRepository.getCountry(idOperation);
	}

	/**
	 * Método que recupera el repositorio de un cliente por código de compañía para
	 * la recuperación de la lista de estados del sistema.
	 *
	 * @param companyCode código de compañía
	 * @param idOperation identificador de traza
	 * @return ResponseModel objeto con el código de resultado de la operación, en
	 *         caso exitoso contiene embebida la información recuperada
	 */
	@Override
	public ResponseModel getStateByCompanyCode(String companyCode, String idOperation) {
		LOG.info(String.format("%s INIT getStateByCompanyCode()", idOperation));
		LOG.info(String.format("%s PARAMS [companyCode: %s ]", idOperation, companyCode));
		GenericCatalogueJdeRepository catalogueRepository = catalogueStoreFactory.getRepository(companyCode);
		if (catalogueRepository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		return catalogueRepository.getState(idOperation);
	}

	@Override
	public ResponseModel getColonyByCompanyCodeAndStateCode(String companyCode, String stateCode, String idOperation) {
		LOG.info(String.format("%s INIT getColonyByCompanyCode()", idOperation));
		LOG.info(String.format("%s PARAMS [companyCode: %s , stateCode: %s ]", idOperation, companyCode, stateCode));
		GenericCatalogueJdeRepository catalogueRepository = catalogueStoreFactory.getRepository(companyCode);
		if (catalogueRepository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		return catalogueRepository.getColonyByStateCode(stateCode, idOperation);
	}

	@Override
	public ResponseModel getMunicipalityNameListByStateCode(String companyCode, String stateCode, String idOperation) {
		LOG.info(String.format("%s INIT getMunicipalityNameListByStateCode()", idOperation));
		LOG.info(String.format("%s PARAMS [companyCode: %s , stateCode: %s ]", idOperation, companyCode, stateCode));
		GenericCatalogueJdeRepository catalogueRepository = catalogueStoreFactory.getRepository(companyCode);
		if (catalogueRepository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		return catalogueRepository.getMunicipalityNameListByStateCode(stateCode, idOperation);
	}

	@Override
	public ResponseModel getMunicipalityByNameListAndCompanyCode(List<String> municipalityNameList, String stateCode,
			String companyCode, String idOperation) {
		LOG.info(String.format("%s INIT getMunicipalityByNameListAndCompanyCode()", idOperation));
		LOG.info(String.format("%s PARAMS [municipalityNameList: %d , stateCode: %s , companyCode: %s ]", idOperation,
				municipalityNameList.size(), stateCode, companyCode));
		GenericCatalogueJdeRepository catalogueRepository = catalogueStoreFactory.getRepository(companyCode);
		if (catalogueRepository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		return catalogueRepository.getMunicipalityByNameList(municipalityNameList, stateCode, idOperation);
	}

	@Override
	public ResponseModel getMunicipalityCpListBySatetCodeAndCompanyCode(String stateCode, String companyCode,
			String idOperation) {
		LOG.info(String.format("%s INIT getMunicipalityCpListBySatetCodeAndCompanyCode()", idOperation));
		LOG.info(String.format("%s PARAMS [stateCode: %s , companyCode: %s ]", idOperation, stateCode, companyCode));
		GenericCatalogueJdeRepository catalogueRepository = catalogueStoreFactory.getRepository(companyCode);
		if (catalogueRepository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		return catalogueRepository.getMunicipalityCpListBySatetCode(stateCode, idOperation);
	}

	/**
	 * Método que recupera los catálogos de JDE y los almacena en MYSql.
	 *
	 * @param companyCode código de compañía
	 * @param idOperation identificador de traza
	 * @return ResponseModel objeto con el código de resultado de la operación, en
	 *         caso exitoso contiene embebida la información recuperada
	 */
	@Override
	@Deprecated
	public void updateCatalogsByCompanyCode(String companyCode, String idOperation) {
		GenericCatalogueJdeRepository catalogueRepository = catalogueStoreFactory.getRepository(companyCode);

		if (catalogueRepository == null) {

			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();

		}

		List<CatalogueDto> catalogClientType = null;

		catalogClientType = catalogueRepository.getCatalogueClientType();

		for (CatalogueDto catalog : catalogClientType) {

			CatalogueEntity code = catalogueRepository.getCatalogByCode(catalog, idOperation);

			if (code == null) {

				CatalogueEntity catalogs = new CatalogueEntity();

				catalogs.setCode(catalog.getCode());
				catalogs.setName(catalog.getName());
				catalogs.setType("ClientType");

				catalogueRepository.catalogsSave(catalogs);

			}
		}

		catalogClientType = catalogueRepository.getCatalogueCoordinate();

		for (CatalogueDto catalog : catalogClientType) {

			CatalogueEntity code = catalogueRepository.getCatalogByCode(catalog, idOperation);

			if (code == null) {

				CatalogueEntity catalogs = new CatalogueEntity();

				catalogs.setCode(catalog.getCode());
				catalogs.setName(catalog.getName());
				catalogs.setType("Coordinate");

				catalogueRepository.catalogsSave(catalogs);

			}
		}

		catalogClientType = catalogueRepository.getCatalogueDelegation();

		for (CatalogueDto catalog : catalogClientType) {

			CatalogueEntity code = catalogueRepository.getCatalogByCode(catalog, idOperation);

			if (code == null) {

				CatalogueEntity catalogs = new CatalogueEntity();

				catalogs.setCode(catalog.getCode());
				catalogs.setName(catalog.getName());
				catalogs.setType("Delegation");

				catalogueRepository.catalogsSave(catalogs);

			}
		}

		catalogClientType = catalogueRepository.getCatalogueFlat();

		for (CatalogueDto catalog : catalogClientType) {

			CatalogueEntity code = catalogueRepository.getCatalogByCode(catalog, idOperation);

			if (code == null) {

				CatalogueEntity catalogs = new CatalogueEntity();

				catalogs.setCode(catalog.getCode());
				catalogs.setName(catalog.getName());
				catalogs.setType("Flat");

				catalogueRepository.catalogsSave(catalogs);

			}
		}

		catalogClientType = catalogueRepository.getCatalogueHowToContact();

		for (CatalogueDto catalog : catalogClientType) {

			CatalogueEntity code = catalogueRepository.getCatalogByCode(catalog, idOperation);

			if (code == null) {

				CatalogueEntity catalogs = new CatalogueEntity();

				catalogs.setCode(catalog.getCode());
				catalogs.setName(catalog.getName());
				catalogs.setType("HowToContact");

				catalogueRepository.catalogsSave(catalogs);

			}
		}

		catalogClientType = catalogueRepository.getCatalogueState();

		for (CatalogueDto catalog : catalogClientType) {

			CatalogueEntity code = catalogueRepository.getCatalogByCode(catalog, idOperation);

			if (code == null) {

				CatalogueEntity catalogs = new CatalogueEntity();

				catalogs.setCode(catalog.getCode());
				catalogs.setName(catalog.getName());
				catalogs.setType("State");

				catalogueRepository.catalogsSave(catalogs);

			}
		}

		catalogClientType = catalogueRepository.getCatalogueWorkType();

		for (CatalogueDto catalog : catalogClientType) {

			CatalogueEntity code = catalogueRepository.getCatalogByCode(catalog, idOperation);

			if (code == null) {

				CatalogueEntity catalogs = new CatalogueEntity();

				catalogs.setCode(catalog.getCode());
				catalogs.setName(catalog.getName());
				catalogs.setType("WorkType");

				catalogueRepository.catalogsSave(catalogs);

			}
		}

		catalogClientType = catalogueRepository.getCatalogueCountry();

		for (CatalogueDto catalog : catalogClientType) {

			CatalogueEntity code = catalogueRepository.getCatalogByCode(catalog, idOperation);

			if (code == null) {

				CatalogueEntity catalogs = new CatalogueEntity();

				catalogs.setCode(catalog.getCode());
				catalogs.setName(catalog.getName());
				catalogs.setType("Country");

				catalogueRepository.catalogsSave(catalogs);

			}
		}

		catalogClientType = catalogueRepository.getCatalogueCfdi();

		for (CatalogueDto catalog : catalogClientType) {

			CatalogueEntity code = catalogueRepository.getCatalogByCode(catalog, idOperation);

			if (code == null) {

				CatalogueEntity catalogs = new CatalogueEntity();

				catalogs.setCode(catalog.getCode());
				catalogs.setName(catalog.getName());
				catalogs.setType("CFDI");

				catalogueRepository.catalogsSave(catalogs);

			}
		}

		catalogClientType = catalogueRepository.getFiscalRegime();

		for (CatalogueDto catalog : catalogClientType) {

			CatalogueEntity code = catalogueRepository.getCatalogByCode(catalog, idOperation);

			if (code == null) {

				CatalogueEntity catalogs = new CatalogueEntity();

				catalogs.setCode(catalog.getCode());
				catalogs.setName(catalog.getName());
				catalogs.setType("Regime_fiscal");

				catalogueRepository.catalogsSave(catalogs);

			}
		}

		catalogClientType = catalogueRepository.getBrand();

		for (CatalogueDto catalog : catalogClientType) {

			CatalogueEntity code = catalogueRepository.getCatalogByCode(catalog, idOperation);

			if (code == null) {

				CatalogueEntity catalogs = new CatalogueEntity();

				catalogs.setCode(catalog.getCode());
				catalogs.setName(catalog.getName());
				catalogs.setType("BRAND");

				catalogueRepository.catalogsSave(catalogs);

			}
		}

		catalogClientType = catalogueRepository.getCategory();

		for (CatalogueDto catalog : catalogClientType) {

			CatalogueEntity code = catalogueRepository.getCatalogByCode(catalog, idOperation);

			if (code == null) {

				CatalogueEntity catalogs = new CatalogueEntity();

				catalogs.setCode(catalog.getCode());
				catalogs.setName(catalog.getName());
				catalogs.setType("CATEGORY");

				catalogueRepository.catalogsSave(catalogs);

			}
		}

		catalogClientType = catalogueRepository.getDivision();

		for (CatalogueDto catalog : catalogClientType) {

			CatalogueEntity code = catalogueRepository.getCatalogByCode(catalog, idOperation);

			if (code == null) {

				CatalogueEntity catalogs = new CatalogueEntity();

				catalogs.setCode(catalog.getCode());
				catalogs.setName(catalog.getName());
				catalogs.setType("DIVISION");

				catalogueRepository.catalogsSave(catalogs);

			}
		}

		catalogClientType = catalogueRepository.getFamily();

		for (CatalogueDto catalog : catalogClientType) {

			CatalogueEntity code = catalogueRepository.getCatalogByCode(catalog, idOperation);

			if (code == null) {

				CatalogueEntity catalogs = new CatalogueEntity();

				catalogs.setCode(catalog.getCode());
				catalogs.setName(catalog.getName());
				catalogs.setType("FAMILY");

				catalogueRepository.catalogsSave(catalogs);

			}
		}

	}

	/**
	 * Método que recupera la lista de colonia del sistema
	 *
	 * @param companyCode código de compañía
	 * @param idOperation identificador de traza
	 * @return ResponseModel objeto con el código de resultado de la operación, en
	 *         caso exitoso contiene embebida la información recuperada
	 */
	@Override
	public ResponseModel getAddressColony(String companyCode, String cp, String idOperation) {
		LOG.info(idOperation + "INICIA BÚSQUEDA DEL CATALOGOS JpaAdapte");

		GenericCatalogueJdeRepository catalogueRepository = catalogueStoreFactory.getRepository(companyCode);

		if (catalogueRepository == null) {

			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();

		}

		ResponseModel responseModel = catalogueRepository.getAddressColony(CatalogueType.Country.toString(), cp,
				idOperation);

		return responseModel;

	}

	/**
	 * Método que recupera la lista de dirección del sistema
	 *
	 * @param companyCode código de compañía
	 * @param idOperation identificador de traza
	 * @return ResponseModel objeto con el código de resultado de la operación, en
	 *         caso exitoso contiene embebida la información recuperada
	 */
	@Override
	public ResponseModel getAddress(String companyCode, String colony, String cp, String idOperation) {
		LOG.info(idOperation + "INICIA BÚSQUEDA DEL CATALOGOS JpaAdapte");

		GenericCatalogueJdeRepository catalogueRepository = catalogueStoreFactory.getRepository(companyCode);

		if (catalogueRepository == null) {

			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();

		}

		ResponseModel responseModel = catalogueRepository.getAddress(CatalogueType.Country.toString(), colony, cp,
				idOperation);

		return responseModel;

	}

	/**
	 * Método que recupera la lista de delegaciones del sistema
	 *
	 * @param companyCode código de compañía
	 * @param idOperation identificador de traza
	 * @return ResponseModel objeto con el código de resultado de la operación, en
	 *         caso exitoso contiene embebida la información recuperada
	 */
	@Override
	public ResponseModel getAddressDelegation(String companyCode, String state, String idOperation) {
		LOG.info(idOperation + "INICIA BÚSQUEDA DEL CATALOGOS JpaAdapte");

		GenericCatalogueJdeRepository catalogueRepository = catalogueStoreFactory.getRepository(companyCode);

		if (catalogueRepository == null) {

			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();

		}

		ResponseModel responseModel = catalogueRepository.getAddressDelegation(CatalogueType.Country.toString(), state,
				idOperation);

		return responseModel;

	}

	/**
	 * Método que recupera la lista de delegaciones del sistema.
	 *
	 * @param companyCode código de compañía
	 * @param idOperation identificador de traza
	 * @return ResponseModel objeto con el código de resultado de la operación, en
	 *         caso exitoso contiene embebida la información recuperada
	 */
	@Override
	public ResponseModel getDelegationByCompanyCode(String companyCode, String idOperation) {
		LOG.info(idOperation + "INICIA BÚSQUEDA DEL CATALOGOS JpaAdapte");

		GenericCatalogueJdeRepository catalogueRepository = catalogueStoreFactory.getRepository(companyCode);

		if (catalogueRepository == null) {

			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();

		}

		ResponseModel responseModel = catalogueRepository
				.getDelegationByCompanyCode(CatalogueType.Delegation.toString(), idOperation);

		return responseModel;

	}

	/**
	 * Método que recupera la lista de categoría del sistema
	 *
	 * @param companyCode código de compañía
	 * @param idOperation identificador de traza
	 * @return ResponseModel objeto con el código de resultado de la operación, en
	 *         caso exitoso contiene embebida la información recuperada
	 */
	@Override
	public ResponseModel getCategoryListByCompanyCodeAndCategoryCode(String companyCode, String categoryCode,
			String idOperation) {

		LOG.info(String.format("%s INIT getCategoryListByCompanyCodeAndCategoryCode() ", idOperation));

		GenericCatalogueJdeRepository catalogueRepository = catalogueStoreFactory.getRepository(companyCode);

		if (catalogueRepository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}

		ResponseModel fiscalRegimeCatalogue = catalogueRepository.getCategoryListByCode(categoryCode, idOperation);

		LOG.info(String.format("%s RETURN CATEGORY-LIST", idOperation));

		return fiscalRegimeCatalogue;

	}

	/**
	 * Método que recupera la lista de Estado del sistema
	 *
	 * @param companyCode código de compañía
	 * @param idOperation identificador de traza
	 * @return ResponseModel objeto con el código de resultado de la operación, en
	 *         caso exitoso contiene embebida la información recuperada
	 */
	@Override
	public ResponseModel getStatusListByCompanyCode(String companyCode, String idOperation) {

		LOG.info(String.format("%s INIT getStatusListByCompanyCode() ", idOperation));

		GenericCatalogueJdeRepository catalogueRepository = catalogueStoreFactory.getRepository(companyCode);

		if (catalogueRepository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}

		List<StatusDto> statusDtoList = catalogueRepository.getStatusList(idOperation);

		LOG.info(String.format("%s RETURN STATUS-LIST", idOperation));

		return new ResponseModel(statusDtoList);

	}

}
