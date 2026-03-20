package mx.com.endtoend.infrastructure.catalogue.address.calzada.business;

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
import mx.com.endtoend.infrastructure.catalogue.address.calzada.repositories.ColonyRepository;
import mx.com.endtoend.infrastructure.catalogue.address.calzada.repositories.CoordinateRepository;
import mx.com.endtoend.infrastructure.catalogue.address.calzada.repositories.CountryRepository;
import mx.com.endtoend.infrastructure.catalogue.address.calzada.repositories.FlatRepository;
import mx.com.endtoend.infrastructure.catalogue.address.calzada.repositories.MunicipalityCPRepository;
import mx.com.endtoend.infrastructure.catalogue.address.calzada.repositories.MunicipalityRepository;
import mx.com.endtoend.infrastructure.catalogue.address.calzada.repositories.StateRepository;
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
public class CatalogueAddressCalzadaRepository implements GenericCatalogueAddressRepository {

	private final Logger LOG = LoggerFactory.getLogger(CatalogueAddressCalzadaRepository.class);

	@Autowired
	private CoordinateRepository coordinateRepository;
	@Autowired
	private CoordianteConverter coordianteConverter;
	@Autowired
	private FlatRepository flatRepository;
	@Autowired
	private FlatConverter flatConverter;
	@Autowired
	private CountryRepository countryRepository;
	@Autowired
	private CountryConverter countryConverter;
	@Autowired
	private StateRepository stateRepository;
	@Autowired
	private StateConverter stateConverter;
	@Autowired
	private ColonyRepository colonyRepository;
	@Autowired
	private ColonyConverter colonyConverter;
	@Autowired
	private MunicipalityRepository municipalityRepository;
	@Autowired
	private MunicipalityConverter municipalityConverter;
	@Autowired
	private MunicipalityCPRepository municipalityCPRepository;
	@Autowired
	private MunicipalityCPConverter municipalityCPConverter;

	@Override
	public ResponseModel getCoordinateByCompanyCode(String idOperation) {
		try {
			LOG.info(String.format("%s INIT getCoordinateByCompanyCode()", idOperation));
			List<CoordinateDto> coordinateDtoList = new ArrayList<>();
			List<CoordinateEntity> coordinateEntityList = coordinateRepository.findAll();
			if (coordinateEntityList.size() != 0) {
				LOG.info(String.format("%s CONVERT CATALOGUE LIST", idOperation));
				coordinateDtoList = coordianteConverter.coordinateEntityListToCoordianteDtoList(coordinateEntityList);
			}
			coordinateEntityList = null;
			return new ResponseModel(coordinateDtoList);
		} catch (Exception e) {
			LOG.error(String.format("%s ERROR IN getCoordinateByCompanyCode(). EXCEPTION: %s", idOperation,
					e.getMessage()));
			throw new GlobalError();
		}
	}

	@Override
	public ResponseModel getFlatByCompanyCode(String idOperation) {
		try {
			LOG.info(String.format("%s INIT getFlatByCompanyCode()", idOperation));
			List<FlatDto> flatDtoList = new ArrayList<>();
			List<FlatEntity> flatEntityList = flatRepository.findAll();
			if (flatEntityList.size() != 0) {
				LOG.info(String.format("%s CONVERT CATALOGUE LIST", idOperation));
				flatDtoList = flatConverter.flatEntityListToFlatDtoList(flatEntityList);
			}
			return new ResponseModel(flatDtoList);
		} catch (Exception e) {
			LOG.error(String.format("%s ERROR IN getFlatByCompanyCode(). EXCEPTION: %s", idOperation, e.getMessage()));
			throw new GlobalError();
		}
	}

	@Override
	public ResponseModel getCountryByCompanyCode(String idOperation) {
		try {
			LOG.info(String.format("%s INIT getCountryByCompanyCode()", idOperation));
			List<CountryDto> countryDtoList = new ArrayList<>();
			List<CountryEntity> countryEntityList = countryRepository.findAll();
			if (countryEntityList.size() != 0) {
				LOG.info(String.format("%s CONVERT CATALOGUE LIST", idOperation));
				countryDtoList = countryConverter.countryEntityListToCountryDtoList(countryEntityList);
			}
			return new ResponseModel(countryDtoList);
		} catch (Exception e) {
			LOG.error(
					String.format("%s ERROR IN getCountryByCompanyCode(). EXCEPTION: %s", idOperation, e.getMessage()));
			throw new GlobalError();
		}
	}

	@Override
	public ResponseModel getStateByCompanyCode(String idOperation) {
		try {
			LOG.info(String.format("%s INIT getStateByCompanyCode()", idOperation));
			List<StateDto> stateDtoList = new ArrayList<>();
			List<StateEntity> stateEntityList = stateRepository.findAll();
			if (stateEntityList.size() != 0) {
				LOG.info(String.format("%s CONVERT CATALOGUE LIST", idOperation));
				stateDtoList = stateConverter.stateEntityListToStateDtoList(stateEntityList);
			}
			return new ResponseModel(stateDtoList);
		} catch (Exception e) {
			LOG.error(String.format("%s ERROR IN getStateByCompanyCode(). EXCEPTION: %s", idOperation, e.getMessage()));
			throw new GlobalError();
		}
	}

	@Override
	public ResponseModel getColonyByStateCode(String stateCode, String idOperation) {
		try {
			LOG.info(String.format("%s INIT getColonyByStateCode()", idOperation));
			List<ColonyDto> colonyDtoList = new ArrayList<>();
			List<ColonyEntity> colonyEntityList = colonyRepository.findByStateCode(stateCode);
			if (colonyEntityList.size() != 0) {
				LOG.info(String.format("%s CONVERT CATALOGUE LIST", idOperation));
				colonyDtoList = colonyConverter.colonyEntityListToColonyDtoList(colonyEntityList);
			}
			return new ResponseModel(colonyDtoList);
		} catch (Exception e) {
			LOG.error(String.format("%s ERROR IN getColonyByStateCode(). EXCEPTION: %s", idOperation, e.getMessage()));
			throw new GlobalError();
		}
	}

	@Override
	public ResponseModel getColonyByParamsAndCompanyCode(GenericSearchDirectionDto genericSearchDirectionDto,
			String idOperation) {
		try {
			LOG.info(String.format("%s INIT getColonyByParamsAndCompanyCode()", idOperation));
			List<ColonyDto> colonyDtoList = new ArrayList<>();
			List<ColonyEntity> colonyEntityList = colonyRepository.findByParams(genericSearchDirectionDto);
			if (colonyEntityList.size() != 0) {
				LOG.info(String.format("%s CONVERT CATALOGUE LIST. SIZE: %d ", idOperation, colonyEntityList.size()));
				colonyDtoList = colonyConverter.colonyEntityListToColonyDtoList(colonyEntityList);
			}
			return new ResponseModel(colonyDtoList);
		} catch (Exception e) {
			LOG.error(String.format("%s ERROR IN getColonyByParamsAndCompanyCode(). EXCEPTION: %s", idOperation,
					e.getMessage()));
			throw new GlobalError();
		}
	}

	@Override
	public ResponseModel getMunicipalityByStateCodeAndCompanyCode(String stateCode, String idOperation) {
		try {
			LOG.info(String.format("%s INIT getMunicipalityByStateCodeAndCompanyCode()", idOperation));
			List<MunicipalityDto> municipalityDtoList = new ArrayList<>();
			List<MunicipalityEntity> municipalityEntityList = municipalityRepository.findByStateCode(stateCode);
			if (municipalityEntityList.size() != 0) {
				LOG.info(String.format("%s CONVERT CATALOGUE LIST", idOperation));
				municipalityDtoList = municipalityConverter
						.municipalityEntityListToMunicipalityDtoList(municipalityEntityList);
			}
			return new ResponseModel(municipalityDtoList);
		} catch (Exception e) {
			LOG.error(String.format("%s ERROR IN getMunicipalityByStateCodeAndCompanyCode(). EXCEPTION: %s",
					idOperation, e.getMessage()));
			throw new GlobalError();
		}
	}

	@Override
	public ResponseModel getMunicipalityCpByStateCode(String stateCode, String idOperation) {
		try {
			LOG.info(String.format("%s INIT getMunicipalityCpByStateCode()", idOperation));
			List<MunicipalityCPDto> municipalityCpDtoList = new ArrayList<>();
			List<MunicipalityCPEntity> municipalityCpEntityList = municipalityCPRepository.findByStateCode(stateCode);
			if (municipalityCpEntityList.size() != 0) {
				LOG.info(String.format("%s CONVERT CATALOGUE LIST", idOperation));
				municipalityCpDtoList = municipalityCPConverter
						.municipalityCpEntityListToMunicipalityCpDtoLits(municipalityCpEntityList);
			}

			return new ResponseModel(municipalityCpDtoList);

		} catch (Exception e) {
			LOG.error(String.format("%s ERROR IN getMunicipalityCpByStateCode(). EXCEPTION: %s", idOperation,
					e.getMessage()));
			throw new GlobalError();
		}
	}

	@Override
	public ResponseModel getMunicipalityCpByStateCodeAndCp(String stateCode, String cp, String idOperation) {
		try {
			LOG.info(String.format("%s INIT getMunicipalityCpByStateCodeAndCp()", idOperation));
			LOG.info(String.format("%s PARAMS [stateCode: %s , cp: %s  ] ", idOperation, stateCode, cp));
			Optional<MunicipalityCPEntity> municipalityCpOptional = municipalityCPRepository
					.findByStateCodeAndCp(stateCode, cp);
			MunicipalityCPDto municipalityCpDto = null;
			if (municipalityCpOptional.isPresent()) {
				municipalityCpDto = municipalityCPConverter
						.municipalityCpEntityToMunicipalityCpDto(municipalityCpOptional.get());
			}
			return new ResponseModel(municipalityCpDto);

		} catch (Exception e) {
			LOG.error(String.format("%s ERROR IN getMunicipalityCpByStateCodeAndCp(). EXCEPTION: %s", idOperation,
					e.getMessage()));
			throw new GlobalError();
		}
	}

	@Override
	public ResponseModel updateCatalogueCoordinateByCompanyCodeAndList(List<CoordinateDto> coordinateDtoList,
			String idOperation) {
		try {
			LOG.info(String.format("%s INIT updateCatalogueCoordinateByCompanyCodeAndList()", idOperation));
			int totalRecord = 0;
			List<CoordinateEntity> coordinateEntityList = coordianteConverter
					.coordinateDtoListToCoordinateEntityList(coordinateDtoList);
			for (CoordinateEntity coordinateEntity : coordinateEntityList) {
				try {
					coordinateRepository.save(coordinateEntity);
					totalRecord++;
				} catch (Exception e) {
					LOG.error(String.format("%s ERROR IN UPDATE DATA: %s", idOperation, coordinateEntity.toString()));
				}
			}
			return new ResponseModel(totalRecord);
		} catch (Exception e) {
			LOG.error(String.format("%s ERROR IN updateCatalogueCoordinateByCompanyCodeAndList(). EXCEPTION: %s",
					idOperation, e.getMessage()));
			throw new GlobalError();
		}
	}

	@Override
	public ResponseModel updateCatalogueFlatByCompanyCodeAndList(List<FlatDto> flatDtoList, String idOperation) {
		try {
			LOG.info(String.format("%s INIT updateCatalogueFlatByCompanyCodeAndList()", idOperation));
			int totalRecord = 0;
			List<FlatEntity> flatEntityList = flatConverter.flatDtoListToFlatEntityList(flatDtoList);
			for (FlatEntity flatEntity : flatEntityList) {
				try {
					flatRepository.save(flatEntity);
					totalRecord++;
				} catch (Exception e) {
					LOG.error(String.format("%s ERROR IN UPDATE DATA: %s", idOperation, flatEntity.toString()));
				}
			}
			return new ResponseModel(totalRecord);
		} catch (Exception e) {
			LOG.error(String.format("%s ERROR IN updateCatalogueFlatByCompanyCodeAndList(). EXCEPTION: %s", idOperation,
					e.getMessage()));
			throw new GlobalError();
		}
	}

	@Override
	public ResponseModel updateCatalogueCountryByCompanyCodeAndList(List<CountryDto> countryDtoList,
			String idOperation) {
		try {
			LOG.info(String.format("%s INIT updateCatalogueCountryByCompanyCodeAndList()", idOperation));
			int totalRecord = 0;
			List<CountryEntity> countryEntityList = countryConverter.countryDtoListToCountryEntityList(countryDtoList);
			for (CountryEntity countryEntity : countryEntityList) {
				try {
					countryRepository.save(countryEntity);
					totalRecord++;
				} catch (Exception e) {
					LOG.error(String.format("%s ERROR IN UPDATE DATA: %s", idOperation, countryEntity.toString()));
				}
			}
			return new ResponseModel(totalRecord);
		} catch (Exception e) {
			LOG.error(String.format("%s ERROR IN updateCatalogueCountryByCompanyCodeAndList(). EXCEPTION: %s",
					idOperation, e.getMessage()));
			throw new GlobalError();
		}
	}

	@Override
	public ResponseModel updateCatalogueStateByCompanyCodeAndList(List<StateDto> stateDtoList, String idOperation) {
		try {
			LOG.info(String.format("%s INIT updateCatalogueStateByCompanyCodeAndList()", idOperation));
			int totalRecord = 0;
			List<StateEntity> stateEntityList = stateConverter.stateDtoListToStateEntityList(stateDtoList);
			for (StateEntity stateEntity : stateEntityList) {
				try {
					stateRepository.save(stateEntity);
					totalRecord++;
				} catch (Exception e) {
					LOG.error(String.format("%s ERROR IN UPDATE DATA: %s", idOperation, stateEntity.toString()));
				}
			}
			return new ResponseModel(totalRecord);
		} catch (Exception e) {
			LOG.error(String.format("%s ERROR IN updateCatalogueStateByCompanyCodeAndList(). EXCEPTION: %s",
					idOperation, e.getMessage()));
			throw new GlobalError();
		}
	}

	@Override
	public ResponseModel updateCatalogueColonyByCompanyCodeAndList(List<ColonyDto> colonyDtoList, String idOperation) {
		try {
			LOG.info(String.format("%s INIT updateCatalogueColonyByCompanyCodeAndList()", idOperation));
			int totalRecord = 0;
			List<ColonyEntity> colonyEntityList = colonyConverter.colonyDtoListToColonyEntityList(colonyDtoList);
			for (ColonyEntity colonyEntity : colonyEntityList) {
				try {
					colonyRepository.save(colonyEntity);
					totalRecord++;
				} catch (Exception e) {
					LOG.error(String.format("%s ERROR IN UPDATE DATA: %s", idOperation, colonyEntity.toString()));
				}
			}
			return new ResponseModel(totalRecord);
		} catch (Exception e) {
			LOG.error(String.format("%s ERROR IN updateCatalogueColonyByCompanyCodeAndList(). EXCEPTION: %s",
					idOperation, e.getMessage()));
			throw new GlobalError();
		}
	}

	@Override
	public ResponseModel updateCatalogueMunicipalityByCompanyCodeAndList(List<MunicipalityDto> municipalityDtoList,
			String idOperation) {
		try {
			LOG.info(String.format("%s INIT updateCatalogueMunicipalityByCompanyCodeAndList()", idOperation));
			int totalRecord = 0;
			List<MunicipalityEntity> municipalityEntityList = municipalityConverter
					.municipalityDtoListToMunicipalityEntityList(municipalityDtoList);
			for (MunicipalityEntity municipalityEntity : municipalityEntityList) {
				try {
					municipalityRepository.save(municipalityEntity);
					totalRecord++;
				} catch (Exception e) {
					LOG.error(String.format("%s ERROR IN UPDATE DATA: %s", idOperation, municipalityEntity.toString()));
				}
			}
			return new ResponseModel(totalRecord);
		} catch (Exception e) {
			LOG.error(String.format("%s ERROR IN updateCatalogueMunicipalityByCompanyCodeAndList(). EXCEPTION: %s",
					idOperation, e.getMessage()));
			throw new GlobalError();
		}
	}

	@Override
	public ResponseModel updateCatalogueMunicipalityCpByList(List<MunicipalityCPDto> municipalityCpDtoList,
			String idOperation) {
		try {
			LOG.info(String.format("%s INIT updateCatalogueMunicipalityCpByList()", idOperation));
			int totalRecord = 0;
			List<MunicipalityCPEntity> municipalityCpEntityList = municipalityCPConverter
					.municipalityCpDtoListToMunicipalityCpEntityLits(municipalityCpDtoList);

			for (MunicipalityCPEntity municipalityCPEntity : municipalityCpEntityList) {
				try {
					municipalityCPRepository.save(municipalityCPEntity);
					totalRecord++;
				} catch (Exception e) {
					LOG.error(
							String.format("%s ERROR IN UPDATE DATA: %s", idOperation, municipalityCPEntity.toString()));
				}
			}
			return new ResponseModel(totalRecord);
		} catch (Exception e) {
			LOG.error(String.format("%s ERROR IN updateCatalogueMunicipalityCpByList(). EXCEPTION: %s", idOperation,
					e.getMessage()));
			throw new GlobalError();
		}
	}

}