package mx.com.endtoend.infrastructure.catalogue.address;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.com.endtoend.domain.catalogue.dto.address.ColonyDto;
import mx.com.endtoend.domain.catalogue.dto.address.CoordinateDto;
import mx.com.endtoend.domain.catalogue.dto.address.CountryDto;
import mx.com.endtoend.domain.catalogue.dto.address.FlatDto;
import mx.com.endtoend.domain.catalogue.dto.address.GenericSearchDirectionDto;
import mx.com.endtoend.domain.catalogue.dto.address.MunicipalityCPDto;
import mx.com.endtoend.domain.catalogue.dto.address.MunicipalityDto;
import mx.com.endtoend.domain.catalogue.dto.address.StateDto;
import mx.com.endtoend.domain.catalogue.ports.spi.CatalogueAddressPersistencePort;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.GlobalError;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

@Service
public class CatalogueAddressJpaAdapter implements CatalogueAddressPersistencePort {

	private final Logger LOG = LoggerFactory.getLogger(CatalogueAddressJpaAdapter.class);
	@Autowired
	private CatalogueAddressRepositoryFactory catalogueAddressRepositoryFactory;

	@Override
	public ResponseModel getCoordinateByCompanyCode(String companyCode, String idOperation) {
		LOG.info(String.format("%s INIT getCoordinateByCompanyCode()", idOperation));
		LOG.info(String.format("%s PARAMS [companyCode: %s]", idOperation, companyCode));
		GenericCatalogueAddressRepository repository = catalogueAddressRepositoryFactory.getRepository(companyCode);
		if (repository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		ResponseModel responseModel = repository.getCoordinateByCompanyCode(idOperation);
		return responseModel;
	}

	@Override
	public ResponseModel getFlatByCompanyCode(String companyCode, String idOperation) {
		LOG.info(String.format("%s INIT getFlatByCompanyCode()", idOperation));
		LOG.info(String.format("%s PARAMS [companyCode: %s]", idOperation, companyCode));
		GenericCatalogueAddressRepository repository = catalogueAddressRepositoryFactory.getRepository(companyCode);
		if (repository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		ResponseModel responseModel = repository.getFlatByCompanyCode(idOperation);
		return responseModel;
	}

	@Override
	public ResponseModel getCountryByCompanyCode(String companyCode, String idOperation) {
		LOG.info(String.format("%s INIT getCountryByCompanyCode()", idOperation));
		LOG.info(String.format("%s PARAMS [companyCode: %s]", idOperation, companyCode));
		GenericCatalogueAddressRepository repository = catalogueAddressRepositoryFactory.getRepository(companyCode);
		if (repository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		ResponseModel responseModel = repository.getCountryByCompanyCode(idOperation);
		return responseModel;
	}

	@Override
	public ResponseModel getStateByCompanyCode(String companyCode, String idOperation) {
		LOG.info(String.format("%s INIT getStateByCompanyCode()", idOperation));
		LOG.info(String.format("%s PARAMS [companyCode: %s]", idOperation, companyCode));
		GenericCatalogueAddressRepository repository = catalogueAddressRepositoryFactory.getRepository(companyCode);
		if (repository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		ResponseModel responseModel = repository.getStateByCompanyCode(idOperation);
		return responseModel;
	}

	@Override
	public ResponseModel getColonyByStateCodeAndCompanyCode(String companyCode, String stateCode, String idOperation) {
		LOG.info(String.format("%s INIT getColonyByCompanyCode()", idOperation));
		LOG.info(String.format("%s PARAMS [stateCode: %s , companyCode: %s]", stateCode, idOperation, companyCode));
		GenericCatalogueAddressRepository repository = catalogueAddressRepositoryFactory.getRepository(companyCode);
		if (repository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		ResponseModel responseModel = repository.getColonyByStateCode(stateCode, idOperation);
		return responseModel;
	}

	@Override
	public ResponseModel getColonyByParamsAndCompanyCode(String companyCode,
			GenericSearchDirectionDto genericSearchDirectionDto, String idOperation) {
		LOG.info(String.format("%s INIT getColonyByParamsAndCompanyCode()", idOperation));
		LOG.info(String.format("%s PARAMS [ companyCode: %s , genericSearchDirectionDto: %s ]", idOperation,
				companyCode, genericSearchDirectionDto.toString()));
		GenericCatalogueAddressRepository repository = catalogueAddressRepositoryFactory.getRepository(companyCode);
		if (repository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		ResponseModel responseModel = repository.getColonyByParamsAndCompanyCode(genericSearchDirectionDto,
				idOperation);
		return responseModel;
	}

	@Override
	public ResponseModel getMunicipalityByStateCodeAndCompanyCode(String stateCode, String companyCode,
			String idOperation) {
		LOG.info(String.format("%s INIT getMunicipalityByStateCodeAndCompanyCode()", idOperation));
		LOG.info(String.format("%s PARAMS [stateCode: %s , companyCode: %s]", stateCode, idOperation, companyCode));
		GenericCatalogueAddressRepository repository = catalogueAddressRepositoryFactory.getRepository(companyCode);
		if (repository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		ResponseModel responseModel = repository.getMunicipalityByStateCodeAndCompanyCode(stateCode, idOperation);
		return responseModel;
	}

	@Override
	public ResponseModel getMunicipalityCpByStateCodeAndCompanyCode(String stateCode, String companyCode,
			String idOperation) {
		LOG.info(String.format("%s INIT getMunicipalityCpByStateCodeAndCompanyCode()", idOperation));
		LOG.info(String.format("%s PARAMS [stateCode: %s , companyCode: %s]", stateCode, idOperation, companyCode));
		GenericCatalogueAddressRepository repository = catalogueAddressRepositoryFactory.getRepository(companyCode);
		if (repository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		ResponseModel responseModel = repository.getMunicipalityCpByStateCode(stateCode, idOperation);
		return responseModel;
	}

	@Override
	public ResponseModel getMunicipalityCpByStateCodeAndCpAndCompanyCode(String stateCode, String cp,
			String companyCode, String idOperation) {

		LOG.info(String.format("%s INIT getMunicipalityCpByStateCodeAndCpAndCompanyCode()", idOperation));
		LOG.info(String.format("%s PARAMS [stateCode: %s , cp: %s , companyCode: %s]", stateCode, cp, idOperation,
				companyCode));
		
		GenericCatalogueAddressRepository repository = catalogueAddressRepositoryFactory.getRepository(companyCode);
		
		if (repository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		ResponseModel responseModel = repository.getMunicipalityCpByStateCodeAndCp(stateCode, cp, idOperation);
		return responseModel;
	}

	@Override
	public ResponseModel updateCatalogueCoordinateByCompanyCodeAndList(String companyCode,
			List<CoordinateDto> coordinateDtoList, String idOperation) {
		LOG.info(String.format("%s INIT updateCatalogueCoordinateByCompanyCodeAndList()", idOperation));
		LOG.info(String.format("%s PARAMS [companyCode: %s , coordinateDtoList: %d ]", idOperation, companyCode,
				coordinateDtoList.size()));
		GenericCatalogueAddressRepository repository = catalogueAddressRepositoryFactory.getRepository(companyCode);
		if (repository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		ResponseModel responseModel = repository.updateCatalogueCoordinateByCompanyCodeAndList(coordinateDtoList,
				idOperation);
		return responseModel;
	}

	@Override
	public ResponseModel updateCatalogueFlatByCompanyCodeAndList(String companyCode, List<FlatDto> flatDtoList,
			String idOperation) {
		LOG.info(String.format("%s INIT updateCatalogueFlatByCompanyCodeAndList()", idOperation));
		LOG.info(String.format("%s PARAMS [companyCode: %s , flatDtoList: %d ]", idOperation, companyCode,
				flatDtoList.size()));
		GenericCatalogueAddressRepository repository = catalogueAddressRepositoryFactory.getRepository(companyCode);
		if (repository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		ResponseModel responseModel = repository.updateCatalogueFlatByCompanyCodeAndList(flatDtoList, idOperation);
		return responseModel;
	}

	@Override
	public ResponseModel updateCatalogueCountryByCompanyCodeAndList(String companyCode, List<CountryDto> countryDtoList,
			String idOperation) {
		LOG.info(String.format("%s INIT updateCatalogueCountryByCompanyCodeAndList()", idOperation));
		LOG.info(String.format("%s PARAMS [companyCode: %s , countryDtoList: %d ]", idOperation, companyCode,
				countryDtoList.size()));
		GenericCatalogueAddressRepository repository = catalogueAddressRepositoryFactory.getRepository(companyCode);
		if (repository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		ResponseModel responseModel = repository.updateCatalogueCountryByCompanyCodeAndList(countryDtoList,
				idOperation);
		return responseModel;
	}

	@Override
	public ResponseModel updateCatalogueStateByCompanyCodeAndList(String companyCode, List<StateDto> stateDtoList,
			String idOperation) {
		LOG.info(String.format("%s INIT updateCatalogueStateByCompanyCodeAndList()", idOperation));
		LOG.info(String.format("%s PARAMS [companyCode: %s , stateDtoList: %d ]", idOperation, companyCode,
				stateDtoList.size()));
		GenericCatalogueAddressRepository repository = catalogueAddressRepositoryFactory.getRepository(companyCode);
		if (repository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		return repository.updateCatalogueStateByCompanyCodeAndList(stateDtoList, idOperation);
	}

	@Override
	public ResponseModel updateCatalogueColonyByCompanyCodeAndList(String companyCode, List<ColonyDto> colonyDtoList,
			String idOperation) {
		LOG.info(String.format("%s INIT updateCatalogueColonyByCompanyCodeAndList()", idOperation));
		LOG.info(String.format("%s PARAMS [companyCode: %s , colonyDtoList: %d ]", idOperation, companyCode,
				colonyDtoList.size()));
		GenericCatalogueAddressRepository repository = catalogueAddressRepositoryFactory.getRepository(companyCode);
		if (repository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		return repository.updateCatalogueColonyByCompanyCodeAndList(colonyDtoList, idOperation);
	}

	@Override
	public ResponseModel updateCatalogueMunicipalityByCompanyCodeAndList(String companyCode,
			List<MunicipalityDto> municipalityDtoList, String idOperation) {
		LOG.info(String.format("%s INIT updateCatalogueMunicipalityByCompanyCodeAndList()", idOperation));
		LOG.info(String.format("%s PARAMS [companyCode: %s , municipalityDtoList: %d ]", idOperation, companyCode,
				municipalityDtoList.size()));
		GenericCatalogueAddressRepository repository = catalogueAddressRepositoryFactory.getRepository(companyCode);
		if (repository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		return repository.updateCatalogueMunicipalityByCompanyCodeAndList(municipalityDtoList, idOperation);
	}

	@Override
	public ResponseModel updateCatalogueMunicipalityCpByCompanyCodeAndList(String companyCode,
			List<MunicipalityCPDto> municipalityCpDtoList, String idOperation) {
		LOG.info(String.format("%s INIT updateCatalogueMunicipalityCpByCompanyCodeAndList()", idOperation));
		LOG.info(String.format("%s PARAMS [companyCode: %s , municipalityDtoList: %d ]", idOperation, companyCode,
				municipalityCpDtoList.size()));
		GenericCatalogueAddressRepository repository = catalogueAddressRepositoryFactory.getRepository(companyCode);
		if (repository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		return repository.updateCatalogueMunicipalityCpByList(municipalityCpDtoList, idOperation);
	}

}
