package mx.com.endtoend.infrastructure.catalogue.orders.common.adapter;

import java.util.List;

import mx.com.endtoend.infrastructure.catalogue.orders.common.factory.CatalogueStoreFactory;
import mx.com.endtoend.infrastructure.catalogue.orders.common.enums.CatalogueType;
import mx.com.endtoend.infrastructure.catalogue.orders.common.service.GenericCatalogueService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.com.endtoend.domain.catalogue.ports.spi.CataloguePersistencePort;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.GlobalError;
import mx.com.endtoend.infrastructure.users.common.adapter.UserJpaAdapter;
import mx.com.endtoend.smart.bussiness.model.catalogue.dto.StatusDto;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

@Service
public class CatalogueJpaAdapter implements CataloguePersistencePort {

	@Autowired
	private CatalogueStoreFactory catalogueStoreFactory;

	private final Logger LOG = LoggerFactory.getLogger(UserJpaAdapter.class);

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
	public ResponseModel getClientTypeByCompanyCode(String companyCode, String method, String idOperation) {
		LOG.info(String.format("%s INIT getClientTypeByCompanyCode() ", idOperation));
		LOG.info(String.format("%s PARAMS: [companyCode: %s method: %s ]", idOperation, companyCode, method));

		GenericCatalogueService catalogueRepository = catalogueStoreFactory.createFactory(companyCode);

		if (catalogueRepository == null) {

			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();

		}

		ResponseModel responseModel = catalogueRepository.getCatalogueByDrsyAndDrrt(CatalogueType.ClientType.toString(),
				idOperation);

		return responseModel;

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
	public ResponseModel getCoordinateByCompanyCode(String companyCode, String method, String idOperation) {
		LOG.info(String.format("%s INIT getCoordinateByCompanyCode() ", idOperation));
		LOG.info(String.format("%s PARAMS: [companyCode: %s method: %s ]", idOperation, companyCode, method));

		GenericCatalogueService catalogueRepository = catalogueStoreFactory.createFactory(companyCode);

		if (catalogueRepository == null) {

			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();

		}

		ResponseModel responseModel = catalogueRepository.getCatalogueByDrsyAndDrrt(CatalogueType.Coordinate.toString(),
				idOperation);

		return responseModel;

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
	public ResponseModel getDelegationByCompanyCode(String companyCode, String method, String idOperation) {
		LOG.info(String.format("%s INIT getDelegationByCompanyCode() ", idOperation));
		LOG.info(String.format("%s PARAMS: [companyCode: %s method: %s ]", idOperation, companyCode, method));

		GenericCatalogueService catalogueRepository = catalogueStoreFactory.createFactory(companyCode);

		if (catalogueRepository == null) {

			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();

		}

		ResponseModel responseModel = catalogueRepository.getCatalogueByDrsyAndDrrt(CatalogueType.Delegation.toString(),
				idOperation);

		return responseModel;

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
	public ResponseModel getFlatByCompanyCode(String companyCode, String method, String idOperation) {

		LOG.info(String.format("%s INIT getFlatByCompanyCode() ", idOperation));
		LOG.info(String.format("%s PARAMS: [companyCode: %s method: %s ]", idOperation, companyCode, method));

		GenericCatalogueService catalogueRepository = catalogueStoreFactory.createFactory(companyCode);

		if (catalogueRepository == null) {

			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();

		}

		ResponseModel responseModel = catalogueRepository.getCatalogueByDrsyAndDrrt(CatalogueType.Flat.toString(),
				idOperation);

		return responseModel;

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
	public ResponseModel getHowToContactByCompanyCode(String companyCode, String method, String idOperation) {

		LOG.info(String.format("%s INIT getHowToContactByCompanyCode() ", idOperation));
		LOG.info(String.format("%s PARAMS: [companyCode: %s method: %s ]", idOperation, companyCode, method));

		GenericCatalogueService catalogueRepository = catalogueStoreFactory.createFactory(companyCode);

		if (catalogueRepository == null) {

			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();

		}
		ResponseModel responseModel = catalogueRepository
				.getCatalogueByDrsyAndDrrt(CatalogueType.HowToContact.toString(), idOperation);

		return responseModel;
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
	public ResponseModel getStateByCompanyCode(String companyCode, String method, String idOperation) {
		LOG.info(String.format("%s INIT getStateByCompanyCode() ", idOperation));
		LOG.info(String.format("%s PARAMS: [companyCode: %s method: %s ]", idOperation, companyCode, method));

		GenericCatalogueService catalogueRepository = catalogueStoreFactory.createFactory(companyCode);

		if (catalogueRepository == null) {

			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();

		}

		ResponseModel responseModel = catalogueRepository.getCatalogueByDrsyAndDrrt(CatalogueType.State.toString(),
				idOperation);

		return responseModel;

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
	public ResponseModel getWorkTypeByCompanyCode(String companyCode, String method, String idOperation) {

		LOG.info(String.format("%s INIT getWorkTypeByCompanyCode() ", idOperation));
		LOG.info(String.format("%s PARAMS: [companyCode: %s method: %s ]", idOperation, companyCode, method));

		GenericCatalogueService catalogueRepository = catalogueStoreFactory.createFactory(companyCode);

		if (catalogueRepository == null) {

			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();

		}

		ResponseModel responseModel = catalogueRepository.getCatalogueByDrsyAndDrrt(CatalogueType.WorkType.toString(),
				idOperation);

		return responseModel;

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
	public ResponseModel getCountryByCompanyCode(String companyCode, String method, String idOperation) {

		LOG.info(String.format("%s INIT getCountryByCompanyCode() ", idOperation));
		LOG.info(String.format("%s PARAMS: [companyCode: %s method: %s ]", idOperation, companyCode, method));

		GenericCatalogueService catalogueRepository = catalogueStoreFactory.createFactory(companyCode);

		if (catalogueRepository == null) {

			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();

		}

		ResponseModel responseModel = catalogueRepository.getCatalogueByDrsyAndDrrt(CatalogueType.Country.toString(),
				idOperation);

		return responseModel;
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
//	@Override
//	public void updateCatalogsByCompanyCode(String companyCode, String method, String idOperation) {
//
//		GenericCatalogueService catalogueRepository = catalogueStoreFactory.createFactory(companyCode);
//
//		if (catalogueRepository == null) {
//
//			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
//			throw new GlobalError();
//
//		}
//
//		List<CatalogueDto> catalogClientType = null;
//
//		catalogClientType = catalogueRepository.getCatalogueClientType();
//
//		for (CatalogueDto catalog : catalogClientType) {
//
//			CatalogueEntity code = catalogueRepository.getCatalogByCode(catalog, idOperation);
//
//			if (code == null) {
//
//				CatalogueEntity catalogs = new CatalogueEntity();
//
//				catalogs.setCode(catalog.getCode());
//				catalogs.setName(catalog.getName());
//				catalogs.setType("ClientType");
//
//				catalogueRepository.catalogsSave(catalogs);
//
//			}
//		}
//
//		catalogClientType = catalogueRepository.getCatalogueCoordinate();
//
//		for (CatalogueDto catalog : catalogClientType) {
//
//			CatalogueEntity code = catalogueRepository.getCatalogByCode(catalog, idOperation);
//
//			if (code == null) {
//
//				CatalogueEntity catalogs = new CatalogueEntity();
//
//				catalogs.setCode(catalog.getCode());
//				catalogs.setName(catalog.getName());
//				catalogs.setType("Coordinate");
//
//				catalogueRepository.catalogsSave(catalogs);
//
//			}
//		}
//
//		catalogClientType = catalogueRepository.getCatalogueDelegation();
//
//		for (CatalogueDto catalog : catalogClientType) {
//
//			CatalogueEntity code = catalogueRepository.getCatalogByCode(catalog, idOperation);
//
//			if (code == null) {
//
//				CatalogueEntity catalogs = new CatalogueEntity();
//
//				catalogs.setCode(catalog.getCode());
//				catalogs.setName(catalog.getName());
//				catalogs.setType("Delegation");
//
//				catalogueRepository.catalogsSave(catalogs);
//
//			}
//		}
//
//		catalogClientType = catalogueRepository.getCatalogueFlat();
//
//		for (CatalogueDto catalog : catalogClientType) {
//
//			CatalogueEntity code = catalogueRepository.getCatalogByCode(catalog, idOperation);
//
//			if (code == null) {
//
//				CatalogueEntity catalogs = new CatalogueEntity();
//
//				catalogs.setCode(catalog.getCode());
//				catalogs.setName(catalog.getName());
//				catalogs.setType("Flat");
//
//				catalogueRepository.catalogsSave(catalogs);
//
//			}
//		}
//
//		catalogClientType = catalogueRepository.getCatalogueHowToContact();
//
//		for (CatalogueDto catalog : catalogClientType) {
//
//			CatalogueEntity code = catalogueRepository.getCatalogByCode(catalog, idOperation);
//
//			if (code == null) {
//
//				CatalogueEntity catalogs = new CatalogueEntity();
//
//				catalogs.setCode(catalog.getCode());
//				catalogs.setName(catalog.getName());
//				catalogs.setType("HowToContact");
//
//				catalogueRepository.catalogsSave(catalogs);
//
//			}
//		}
//
//		catalogClientType = catalogueRepository.getCatalogueState();
//
//		for (CatalogueDto catalog : catalogClientType) {
//
//			CatalogueEntity code = catalogueRepository.getCatalogByCode(catalog, idOperation);
//
//			if (code == null) {
//
//				CatalogueEntity catalogs = new CatalogueEntity();
//
//				catalogs.setCode(catalog.getCode());
//				catalogs.setName(catalog.getName());
//				catalogs.setType("State");
//
//				catalogueRepository.catalogsSave(catalogs);
//
//			}
//		}
//
//		catalogClientType = catalogueRepository.getCatalogueWorkType();
//
//		for (CatalogueDto catalog : catalogClientType) {
//
//			CatalogueEntity code = catalogueRepository.getCatalogByCode(catalog, idOperation);
//
//			if (code == null) {
//
//				CatalogueEntity catalogs = new CatalogueEntity();
//
//				catalogs.setCode(catalog.getCode());
//				catalogs.setName(catalog.getName());
//				catalogs.setType("WorkType");
//
//				catalogueRepository.catalogsSave(catalogs);
//
//			}
//		}
//
//		catalogClientType = catalogueRepository.getCatalogueCountry();
//
//		for (CatalogueDto catalog : catalogClientType) {
//
//			CatalogueEntity code = catalogueRepository.getCatalogByCode(catalog, idOperation);
//
//			if (code == null) {
//
//				CatalogueEntity catalogs = new CatalogueEntity();
//
//				catalogs.setCode(catalog.getCode());
//				catalogs.setName(catalog.getName());
//				catalogs.setType("Country");
//
//				catalogueRepository.catalogsSave(catalogs);
//
//			}
//		}
//
//		catalogClientType = catalogueRepository.getCatalogueCfdi();
//
//		for (CatalogueDto catalog : catalogClientType) {
//
//			CatalogueEntity code = catalogueRepository.getCatalogByCode(catalog, idOperation);
//
//			if (code == null) {
//
//				CatalogueEntity catalogs = new CatalogueEntity();
//
//				catalogs.setCode(catalog.getCode());
//				catalogs.setName(catalog.getName());
//				catalogs.setType("CFDI");
//
//				catalogueRepository.catalogsSave(catalogs);
//
//			}
//		}
//		
//		catalogClientType = catalogueRepository.getFiscalRegime();
//
//		for (CatalogueDto catalog : catalogClientType) {
//
//			CatalogueEntity code = catalogueRepository.getCatalogByCode(catalog, idOperation);
//
//			if (code == null) {
//
//				CatalogueEntity catalogs = new CatalogueEntity();
//
//				catalogs.setCode(catalog.getCode());
//				catalogs.setName(catalog.getName());
//				catalogs.setType("Regime_fiscal");
//
//				catalogueRepository.catalogsSave(catalogs);
//
//			}
//		}
//		
//		catalogClientType = catalogueRepository.getBrand();
//
//		for (CatalogueDto catalog : catalogClientType) {
//
//			CatalogueEntity code = catalogueRepository.getCatalogByCode(catalog, idOperation);
//
//			if (code == null) {
//
//				CatalogueEntity catalogs = new CatalogueEntity();
//
//				catalogs.setCode(catalog.getCode());
//				catalogs.setName(catalog.getName());
//				catalogs.setType("BRAND");
//
//				catalogueRepository.catalogsSave(catalogs);
//
//			}
//		}
//		
//		catalogClientType = catalogueRepository.getCategory();
//
//		for (CatalogueDto catalog : catalogClientType) {
//
//			CatalogueEntity code = catalogueRepository.getCatalogByCode(catalog, idOperation);
//
//			if (code == null) {
//
//				CatalogueEntity catalogs = new CatalogueEntity();
//
//				catalogs.setCode(catalog.getCode());
//				catalogs.setName(catalog.getName());
//				catalogs.setType("CATEGORY");
//
//				catalogueRepository.catalogsSave(catalogs);
//
//			}
//		}
//		
//		catalogClientType = catalogueRepository.getDivision();
//
//		for (CatalogueDto catalog : catalogClientType) {
//
//			CatalogueEntity code = catalogueRepository.getCatalogByCode(catalog, idOperation);
//
//			if (code == null) {
//
//				CatalogueEntity catalogs = new CatalogueEntity();
//
//				catalogs.setCode(catalog.getCode());
//				catalogs.setName(catalog.getName());
//				catalogs.setType("DIVISION");
//
//				catalogueRepository.catalogsSave(catalogs);
//
//			}
//		}
//		
//		catalogClientType = catalogueRepository.getFamily();
//
//		for (CatalogueDto catalog : catalogClientType) {
//
//			CatalogueEntity code = catalogueRepository.getCatalogByCode(catalog, idOperation);
//
//			if (code == null) {
//
//				CatalogueEntity catalogs = new CatalogueEntity();
//
//				catalogs.setCode(catalog.getCode());
//				catalogs.setName(catalog.getName());
//				catalogs.setType("FAMILY");
//
//				catalogueRepository.catalogsSave(catalogs);
//
//			}
//		}
//
//	}

//	/**
//	 * Método que recupera la lista de colonia del sistema
//	 * 
//	 * @param companyCode           código de compañía
//	 * @param clientPersistencePort repositorio principal del sistema
//	 * @param idoperation           identificador de traza
//	 * 
//	 * @return ResponseModel objeto con el código de resultado de la operación, en
//	 *         caso exitoso contiene embebida la información recuperada
//	 */
//	@Override
//	public ResponseModel getAddressColony(String companyCode, String method, String cp, String idOperation) {
//		LOG.info(idOperation + "INICIA BÚSQUEDA DEL CATALOGOS JpaAdapte");
//
//		GenericCatalogueService catalogueRepository = catalogueStoreFactory.createFactory(companyCode);
//
//		if (catalogueRepository == null) {
//
//			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
//			throw new GlobalError();
//
//		}
//
//			ResponseModel responseModel = catalogueRepository.getAddressColony(CatalogueType.Country.toString(), cp,
//					idOperation);
//
//			return responseModel;
//
//	}
//
//	/**
//	 * Método que recupera la lista de dirección del sistema
//	 * 
//	 * @param companyCode           código de compañía
//	 * @param clientPersistencePort repositorio principal del sistema
//	 * @param idoperation           identificador de traza
//	 * 
//	 * @return ResponseModel objeto con el código de resultado de la operación, en
//	 *         caso exitoso contiene embebida la información recuperada
//	 */
//	@Override
//	public ResponseModel getAddress(String companyCode, String method, String colony, String cp, String idOperation) {
//		LOG.info(idOperation + "INICIA BÚSQUEDA DEL CATALOGOS JpaAdapte");
//
//		GenericCatalogueService catalogueRepository = catalogueStoreFactory.createFactory(companyCode);
//
//		if (catalogueRepository == null) {
//
//			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
//			throw new GlobalError();
//
//		}
//
//			ResponseModel responseModel = catalogueRepository.getAddress(CatalogueType.Country.toString(), colony, cp,
//					idOperation);
//
//			return responseModel;
//
//	}
//
//	/**
//	 * Método que recupera la lista de delegacion del sistema
//	 * 
//	 * @param companyCode           código de compañía
//	 * @param clientPersistencePort repositorio principal del sistema
//	 * @param idoperation           identificador de traza
//	 * 
//	 * @return ResponseModel objeto con el código de resultado de la operación, en
//	 *         caso exitoso contiene embebida la información recuperada
//	 */
//	@Override
//	public ResponseModel getAddressDelegation(String companyCode, String method, String state, String idOperation) {
//		LOG.info(idOperation + "INICIA BÚSQUEDA DEL CATALOGOS JpaAdapte");
//
//		GenericCatalogueService catalogueRepository = catalogueStoreFactory.createFactory(companyCode);
//
//		if (catalogueRepository == null) {
//
//			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
//			throw new GlobalError();
//
//		}
//
//			ResponseModel responseModel = catalogueRepository.getAddressDelegation(CatalogueType.Country.toString(), state,
//					idOperation);
//
//			return responseModel;
//
//	}

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
	public ResponseModel getStatus(String companyCode, Long id, String method, String idOperation) {
		LOG.info(String.format("%s INIT getStatus() ", idOperation));
		LOG.info(String.format("%s PARAMS: [  id: %s , companyCode: %s , method: %s ]", idOperation, id, companyCode,
				method));
		GenericCatalogueService catalogueRepository = catalogueStoreFactory.createFactory(companyCode);
		if (catalogueRepository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		ResponseModel responseModel = catalogueRepository.getStatus(id, idOperation);
		return responseModel;
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
	public void createStatus(String companyCode, StatusDto statusDto, String method, String idOperation) {
		LOG.info(String.format("%s INIT createStatus() ", idOperation));
		LOG.info(String.format("%s PARAMS: [  statusDto: %s , companyCode: %s , method: %s ]", idOperation,
				statusDto.toString(), companyCode, method));

		GenericCatalogueService catalogueRepository = catalogueStoreFactory.createFactory(companyCode);

		if (catalogueRepository == null) {

			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();

		}

		catalogueRepository.createStatus(statusDto, idOperation);
	}

	/**
	 * Método que corroborar si existe el estado.
	 * 
	 * @param companyCode           código de compañía
	 * @param clientPersistencePort repositorio principal del sistema
	 * @param idoperation           identificador de traza
	 * 
	 * @return ResponseModel objeto con el código de resultado de la operación, en
	 *         caso exitoso contiene embebida la información recuperada
	 */
	@Override
	public boolean existsStatus(StatusDto statusDto, String companyCode, String method, String idOperation) {

		LOG.info(String.format("%s INIT existsStatus() ", idOperation));
		LOG.info(String.format("%s PARAMS: [  statusDto: %s , companyCode: %s , method: %s ]", idOperation,
				statusDto.toString(), companyCode, method));

		boolean exists = false;

		GenericCatalogueService catalogueRepository = catalogueStoreFactory.createFactory(companyCode);

		if (catalogueRepository == null) {

			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();

		}

		exists = catalogueRepository.existsStatus(statusDto, idOperation);

		return exists;

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
	public ResponseModel getCfdi(String companyCode, String method, String idOperation) {
		LOG.info(String.format("%s INIT getCfdi() ", idOperation));
		LOG.info(String.format("%s PARAMS: [ companyCode: %s , method: %s ]", idOperation, companyCode, method));

		GenericCatalogueService catalogueRepository = catalogueStoreFactory.createFactory(companyCode);

		if (catalogueRepository == null) {

			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();

		}

		ResponseModel responseModel = catalogueRepository.getCfdi(CatalogueType.CFDI.toString(), idOperation);

		return responseModel;
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
	public ResponseModel getFiscalRegimeCatalogeByCompanyCode(String companyCode, String method, String idOperation) {

		LOG.info(String.format("%s INIT getFiscalRegimeCatalogeByCompanyCode() ", idOperation));
		LOG.info(String.format("%s PARAMS: [ companyCode: %s , method: %s ]", idOperation, companyCode, method));

		GenericCatalogueService catalogueRepository = catalogueStoreFactory.createFactory(companyCode);

		if (catalogueRepository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}

		ResponseModel fiscalRegimeCatalogue = catalogueRepository
				.getFiscalRegimeCatalogue(CatalogueType.Regime_fiscal.toString(), idOperation);

		LOG.info(String.format("%s RETURN FISCAL REGIME CATALOGUE", idOperation));

		return fiscalRegimeCatalogue;
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
	public ResponseModel getCategoryListByCompanyCodeAndCategoryCode(String companyCode, String method,
			String categoryCode, String idOperation) {

		LOG.info(String.format("%s INIT getCategoryListByCompanyCodeAndCategoryCode() ", idOperation));
		LOG.info(String.format("%s PARAMS: [ companyCode: %s , categoryCode: %s , method: %s ]", idOperation,
				companyCode, categoryCode, method));

		GenericCatalogueService catalogueRepository = catalogueStoreFactory.createFactory(companyCode);

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
	 * @param companyCode           código de compañía
	 * @param clientPersistencePort repositorio principal del sistema
	 * @param idoperation           identificador de traza
	 * 
	 * @return ResponseModel objeto con el código de resultado de la operación, en
	 *         caso exitoso contiene embebida la información recuperada
	 */
	@Override
	public ResponseModel getStatusListByCompanyCode(String companyCode, String method, String idOperation) {

		LOG.info(String.format("%s INIT getStatusListByCompanyCode() ", idOperation));
		LOG.info(String.format("%s PARAMS: [ companyCode: %s , method: %s ]", idOperation, companyCode, method));

		GenericCatalogueService catalogueRepository = catalogueStoreFactory.createFactory(companyCode);

		if (catalogueRepository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}

		List<StatusDto> statusDtoList = catalogueRepository.getStatusList(idOperation);

		LOG.info(String.format("%s RETURN STATUS-LIST", idOperation));

		return new ResponseModel(statusDtoList);

	}

}