package mx.com.endtoend.infrastructure.catalogue.address.demo.business;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
import mx.com.endtoend.infrastructure.catalogue.address.GenericCatalogueAddressRepository;
import mx.com.endtoend.infrastructure.catalogue.address.demo.repositories.ColonyDemoRepository;
import mx.com.endtoend.infrastructure.catalogue.address.demo.repositories.CoordinateDemoRepository;
import mx.com.endtoend.infrastructure.catalogue.address.demo.repositories.CountryDemoRepository;
import mx.com.endtoend.infrastructure.catalogue.address.demo.repositories.CustomColonyDemoRepositoryImpl;
import mx.com.endtoend.infrastructure.catalogue.address.demo.repositories.FlatDemoRepository;
import mx.com.endtoend.infrastructure.catalogue.address.demo.repositories.MunicipalityCPDemoRepository;
import mx.com.endtoend.infrastructure.catalogue.address.demo.repositories.MunicipalityDemoRepository;
import mx.com.endtoend.infrastructure.catalogue.address.demo.repositories.StateDemoRepository;
import mx.com.endtoend.infrastructure.catalogue.address.converters.ColonyConverter;
import mx.com.endtoend.infrastructure.catalogue.address.converters.CoordianteConverter;
import mx.com.endtoend.infrastructure.catalogue.address.converters.CountryConverter;
import mx.com.endtoend.infrastructure.catalogue.address.converters.FlatConverter;
import mx.com.endtoend.infrastructure.catalogue.address.converters.MunicipalityCPConverter;
import mx.com.endtoend.infrastructure.catalogue.address.converters.MunicipalityConverter;
import mx.com.endtoend.infrastructure.catalogue.address.converters.StateConverter;
import mx.com.endtoend.infrastructure.catalogue.address.entities.ColonyEntity;
import mx.com.endtoend.infrastructure.catalogue.address.entities.CoordinateEntity;
import mx.com.endtoend.infrastructure.catalogue.address.entities.CountryEntity;
import mx.com.endtoend.infrastructure.catalogue.address.entities.FlatEntity;
import mx.com.endtoend.infrastructure.catalogue.address.entities.MunicipalityCPEntity;
import mx.com.endtoend.infrastructure.catalogue.address.entities.MunicipalityEntity;
import mx.com.endtoend.infrastructure.catalogue.address.entities.StateEntity;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.GlobalError;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

@Service
public class CatalogueAddressDemoRepository implements GenericCatalogueAddressRepository {

	private final Logger LOG = LoggerFactory.getLogger(CatalogueAddressDemoRepository.class);

	@Autowired
	private ColonyDemoRepository colonyDemoRepository;

	@Autowired
	private CoordinateDemoRepository coordinateDemoRepository;

	@Autowired
	private CountryDemoRepository countryDemoRepository;

	@Autowired
	private FlatDemoRepository flatDemoRepository;

	@Autowired
	private MunicipalityCPDemoRepository municipalityCPDemoRepository;

	@Autowired
	private MunicipalityDemoRepository municipalityDemoRepository;

	@Autowired
	private StateDemoRepository stateDemoRepository;

	@Autowired
	private CustomColonyDemoRepositoryImpl customColonyDemoRepositoryImpl;

	// Converters
	@Autowired
	private ColonyConverter colonyConverter;

	@Autowired
	private CoordianteConverter coordianteConverter;

	@Autowired
	private CountryConverter countryConverter;

	@Autowired
	private FlatConverter flatConverter;

	@Autowired
	private MunicipalityCPConverter municipalityCPConverter;

	@Autowired
	private MunicipalityConverter municipalityConverter;

	@Autowired
	private StateConverter stateConverter;

	@Override
	public ResponseModel getCountryByCompanyCode(String idOperation) {
		try {
			LOG.info("{} INIT getCountryByCompanyCode() for DEMO", idOperation);
			
			List<CountryEntity> countryEntities = countryDemoRepository.findAll();
			List<CountryDto> countryDtos = countryConverter.countryEntityListToCountryDtoList(countryEntities);
			
			LOG.info("{} DEMO - Retrieved {} countries", idOperation, countryDtos.size());
			return new ResponseModel(countryDtos);
			
		} catch (Exception e) {
			LOG.error("{} ERROR IN getCountryByCompanyCode() for DEMO. EXCEPTION: {}", idOperation, e.getMessage());
			throw new GlobalError();
		}
	}

	@Override
	public ResponseModel getStateByCompanyCode(String idOperation) {
		try {
			LOG.info("{} INIT getStateByCompanyCode() for DEMO", idOperation);
			
			List<StateEntity> stateEntities = stateDemoRepository.findAll();
			List<StateDto> stateDtos = stateConverter.stateEntityListToStateDtoList(stateEntities);
			
			LOG.info("{} DEMO - Retrieved {} states", idOperation, stateDtos.size());
			return new ResponseModel(stateDtos);
			
		} catch (Exception e) {
			LOG.error("{} ERROR IN getStateByCompanyCode() for DEMO. EXCEPTION: {}", idOperation, e.getMessage());
			throw new GlobalError();
		}
	}

	@Override
	public ResponseModel getMunicipalityByStateCodeAndCompanyCode(String stateCode, String idOperation) {
		try {
			LOG.info("{} INIT getMunicipalityByStateCodeAndCompanyCode() for DEMO", idOperation);
			LOG.info("{} PARAMS[ stateCode: {} ]", idOperation, stateCode);
			
			List<MunicipalityEntity> municipalityEntities = municipalityDemoRepository.findAll();
			List<MunicipalityDto> municipalityDtos = municipalityConverter.municipalityEntityListToMunicipalityDtoList(municipalityEntities);
			
			LOG.info("{} DEMO - Retrieved {} municipalities", idOperation, municipalityDtos.size());
			return new ResponseModel(municipalityDtos);
			
		} catch (Exception e) {
			LOG.error("{} ERROR IN getMunicipalityByStateCodeAndCompanyCode() for DEMO. EXCEPTION: {}", idOperation, e.getMessage());
			throw new GlobalError();
		}
	}

	@Override
	public ResponseModel getMunicipalityCpByStateCode(String stateCode, String idOperation) {
		try {
			LOG.info("{} INIT getMunicipalityCpByStateCode() for DEMO", idOperation);
			LOG.info("{} PARAMS[ stateCode: {} ]", idOperation, stateCode);
			
			List<MunicipalityCPEntity> municipalityCPEntities = municipalityCPDemoRepository.findAll();
			List<MunicipalityCPDto> municipalityCPDtos = municipalityCPConverter.municipalityCpEntityListToMunicipalityCpDtoLits(municipalityCPEntities);
			
			LOG.info("{} DEMO - Retrieved {} municipalities CP", idOperation, municipalityCPDtos.size());
			return new ResponseModel(municipalityCPDtos);
			
		} catch (Exception e) {
			LOG.error("{} ERROR IN getMunicipalityCpByStateCode() for DEMO. EXCEPTION: {}", idOperation, e.getMessage());
			throw new GlobalError();
		}
	}

	@Override
	public ResponseModel getMunicipalityCpByStateCodeAndCp(String stateCode, String cp, String idOperation) {
		try {
			LOG.info("{} INIT getMunicipalityCpByStateCodeAndCp() for DEMO", idOperation);
			LOG.info("{} PARAMS[ stateCode: {}, cp: {} ]", idOperation, stateCode, cp);
			
			List<MunicipalityCPEntity> municipalityCPEntities = municipalityCPDemoRepository.findAll();
			List<MunicipalityCPDto> municipalityCPDtos = municipalityCPConverter.municipalityCpEntityListToMunicipalityCpDtoLits(municipalityCPEntities);
			
			LOG.info("{} DEMO - Retrieved {} municipalities CP", idOperation, municipalityCPDtos.size());
			return new ResponseModel(municipalityCPDtos);
			
		} catch (Exception e) {
			LOG.error("{} ERROR IN getMunicipalityCpByStateCodeAndCp() for DEMO. EXCEPTION: {}", idOperation, e.getMessage());
			throw new GlobalError();
		}
	}

	@Override
	public ResponseModel getColonyByStateCode(String stateCode, String idOperation) {
		try {
			LOG.info("{} INIT getColonyByStateCode() for DEMO", idOperation);
			LOG.info("{} PARAMS[ stateCode: {} ]", idOperation, stateCode);
			
			List<ColonyEntity> colonyEntities = colonyDemoRepository.findByStateCode(stateCode);
			List<ColonyDto> colonyDtos = colonyConverter.colonyEntityListToColonyDtoList(colonyEntities);
			
			LOG.info("{} DEMO - Retrieved {} colonies for state: {}", idOperation, colonyDtos.size(), stateCode);
			return new ResponseModel(colonyDtos);
			
		} catch (Exception e) {
			LOG.error("{} ERROR IN getColonyByStateCode() for DEMO. EXCEPTION: {}", idOperation, e.getMessage());
			throw new GlobalError();
		}
	}

	@Override
	public ResponseModel getColonyByParamsAndCompanyCode(GenericSearchDirectionDto genericSearchDirectionDto, String idOperation) {
		try {
			LOG.info("{} INIT getColonyByParamsAndCompanyCode() for DEMO", idOperation);
			LOG.info("{} PARAMS[ genericSearchDirectionDto: {} ]", idOperation, genericSearchDirectionDto);
			
			List<ColonyEntity> colonyEntities = customColonyDemoRepositoryImpl.findByParams(genericSearchDirectionDto);
			List<ColonyDto> colonyDtos = colonyConverter.colonyEntityListToColonyDtoList(colonyEntities);
			
			LOG.info("{} DEMO - Retrieved {} colonies by params", idOperation, colonyDtos.size());
			return new ResponseModel(colonyDtos);
			
		} catch (Exception e) {
			LOG.error("{} ERROR IN getColonyByParamsAndCompanyCode() for DEMO. EXCEPTION: {}", idOperation, e.getMessage());
			throw new GlobalError();
		}
	}

	@Override
	public ResponseModel getFlatByCompanyCode(String idOperation) {
		try {
			LOG.info("{} INIT getFlatByCompanyCode() for DEMO", idOperation);
			
			List<FlatEntity> flatEntities = flatDemoRepository.findAll();
			List<FlatDto> flatDtos = flatConverter.flatEntityListToFlatDtoList(flatEntities);
			
			LOG.info("{} DEMO - Retrieved {} flats", idOperation, flatDtos.size());
			return new ResponseModel(flatDtos);
			
		} catch (Exception e) {
			LOG.error("{} ERROR IN getFlatByCompanyCode() for DEMO. EXCEPTION: {}", idOperation, e.getMessage());
			throw new GlobalError();
		}
	}

	@Override
	public ResponseModel getCoordinateByCompanyCode(String idOperation) {
		try {
			LOG.info("{} INIT getCoordinateByCompanyCode() for DEMO", idOperation);
			
			List<CoordinateEntity> coordinateEntities = coordinateDemoRepository.findAll();
			List<CoordinateDto> coordinateDtos = coordianteConverter.coordinateEntityListToCoordianteDtoList(coordinateEntities);
			
			LOG.info("{} DEMO - Retrieved {} coordinates", idOperation, coordinateDtos.size());
			return new ResponseModel(coordinateDtos);
			
		} catch (Exception e) {
			LOG.error("{} ERROR IN getCoordinateByCompanyCode() for DEMO. EXCEPTION: {}", idOperation, e.getMessage());
			throw new GlobalError();
		}
	}

	// Update methods - Para DEMO implementamos con return success simple
	@Override
	public ResponseModel updateCatalogueCoordinateByCompanyCodeAndList(List<CoordinateDto> coordinateDtoList, String idOperation) {
		LOG.info("{} INIT updateCatalogueCoordinateByCompanyCodeAndList() for DEMO", idOperation);
		LOG.info("{} DEMO - Update coordinates: {} items", idOperation, coordinateDtoList.size());
		return new ResponseModel("Update successful for DEMO");
	}

	@Override
	public ResponseModel updateCatalogueFlatByCompanyCodeAndList(List<FlatDto> flatDtoList, String idOperation) {
		LOG.info("{} INIT updateCatalogueFlatByCompanyCodeAndList() for DEMO", idOperation);
		LOG.info("{} DEMO - Update flats: {} items", idOperation, flatDtoList.size());
		return new ResponseModel("Update successful for DEMO");
	}

	@Override
	public ResponseModel updateCatalogueCountryByCompanyCodeAndList(List<CountryDto> countryDtoList, String idOperation) {
		LOG.info("{} INIT updateCatalogueCountryByCompanyCodeAndList() for DEMO", idOperation);
		LOG.info("{} DEMO - Update countries: {} items", idOperation, countryDtoList.size());
		return new ResponseModel("Update successful for DEMO");
	}

	@Override
	public ResponseModel updateCatalogueStateByCompanyCodeAndList(List<StateDto> stateDtoList, String idOperation) {
		LOG.info("{} INIT updateCatalogueStateByCompanyCodeAndList() for DEMO", idOperation);
		LOG.info("{} DEMO - Update states: {} items", idOperation, stateDtoList.size());
		return new ResponseModel("Update successful for DEMO");
	}

	@Override
	public ResponseModel updateCatalogueColonyByCompanyCodeAndList(List<ColonyDto> colonyDtoList, String idOperation) {
		LOG.info("{} INIT updateCatalogueColonyByCompanyCodeAndList() for DEMO", idOperation);
		LOG.info("{} DEMO - Update colonies: {} items", idOperation, colonyDtoList.size());
		return new ResponseModel("Update successful for DEMO");
	}

	@Override
	public ResponseModel updateCatalogueMunicipalityByCompanyCodeAndList(List<MunicipalityDto> municipalityDtoList, String idOperation) {
		LOG.info("{} INIT updateCatalogueMunicipalityByCompanyCodeAndList() for DEMO", idOperation);
		LOG.info("{} DEMO - Update municipalities: {} items", idOperation, municipalityDtoList.size());
		return new ResponseModel("Update successful for DEMO");
	}

	@Override
	public ResponseModel updateCatalogueMunicipalityCpByList(List<MunicipalityCPDto> municipalityCpDtoList, String idOperation) {
		LOG.info("{} INIT updateCatalogueMunicipalityCpByList() for DEMO", idOperation);
		LOG.info("{} DEMO - Update municipalities CP: {} items", idOperation, municipalityCpDtoList.size());
		return new ResponseModel("Update successful for DEMO");
	}

}
