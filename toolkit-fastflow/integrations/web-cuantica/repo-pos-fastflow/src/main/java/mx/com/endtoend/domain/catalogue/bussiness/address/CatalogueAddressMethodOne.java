package mx.com.endtoend.domain.catalogue.bussiness.address;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.com.endtoend.domain.catalogue.dto.CatalogueUpdateResult;
import mx.com.endtoend.domain.catalogue.dto.address.ColonyDto;
import mx.com.endtoend.domain.catalogue.dto.address.CoordinateDto;
import mx.com.endtoend.domain.catalogue.dto.address.CountryDto;
import mx.com.endtoend.domain.catalogue.dto.address.FlatDto;
import mx.com.endtoend.domain.catalogue.dto.address.GenericSearchDirectionDto;
import mx.com.endtoend.domain.catalogue.dto.address.MunicipalityCPDto;
import mx.com.endtoend.domain.catalogue.dto.address.MunicipalityDto;
import mx.com.endtoend.domain.catalogue.dto.address.StateDto;
import mx.com.endtoend.domain.catalogue.ports.spi.CatalogueAddressPersistencePort;
import mx.com.endtoend.domain.commons.constants.CatalogueAddressTypeEnum;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.GlobalError;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.ValidationError;
import mx.com.endtoend.infrastructure.services.jde.catalogue.common.serviceport.CatalogueJdeServicePort;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

public class CatalogueAddressMethodOne implements CatalogueAddressInterface {

	private final Logger LOG = LoggerFactory.getLogger(CatalogueAddressMethodOne.class);
	private final CatalogueJdeServicePort catalogueOracleServicePort;

	public CatalogueAddressMethodOne(CatalogueJdeServicePort catalogueOracleServicePort) {
		this.catalogueOracleServicePort = catalogueOracleServicePort;

	}

	@Override
	public ResponseModel getCoordinateByCompanyCode(CatalogueAddressPersistencePort catalogueAddressPersistencePort,
			String companyCode, String idOperation) {
		LOG.info(String.format("%s INIT getCoordinateByCompanyCode() ", idOperation));
		LOG.info(String.format("%s PARAMS: [ companyCode: %s ]", idOperation, companyCode));
		ResponseModel responseModel = catalogueAddressPersistencePort.getCoordinateByCompanyCode(companyCode,
				idOperation);
		LOG.info(String.format("%s RETURN CATALOGUE LIST ", idOperation));
		return responseModel;
	}

	@Override
	public ResponseModel getFlatByCompanyCode(CatalogueAddressPersistencePort catalogueAddressPersistencePort,
			String companyCode, String idOperation) {
		LOG.info(String.format("%s INIT getFlatByCompanyCode() ", idOperation));
		LOG.info(String.format("%s PARAMS: [ companyCode: %s ]", idOperation, companyCode));
		ResponseModel responseModel = catalogueAddressPersistencePort.getFlatByCompanyCode(companyCode, idOperation);
		LOG.info(String.format("%s RETURN CATALOGUE LIST ", idOperation));
		return responseModel;
	}

	@Override
	public ResponseModel getCountryByCompanyCode(CatalogueAddressPersistencePort catalogueAddressPersistencePort,
			String companyCode, String idOperation) {
		LOG.info(String.format("%s INIT getCountryByCompanyCode() ", idOperation));
		LOG.info(String.format("%s PARAMS: [ companyCode: %s ]", idOperation, companyCode));
		ResponseModel responseModel = catalogueAddressPersistencePort.getCountryByCompanyCode(companyCode, idOperation);
		LOG.info(String.format("%s RETURN CATALOGUE LIST ", idOperation));
		return responseModel;
	}

	@Override
	public ResponseModel getStateByCompanyCode(CatalogueAddressPersistencePort catalogueAddressPersistencePort,
			String companyCode, String idOperation) {
		LOG.info(String.format("%s INIT getStateByCompanyCode() ", idOperation));
		LOG.info(String.format("%s PARAMS: [ companyCode: %s ]", idOperation, companyCode));
		ResponseModel responseModel = catalogueAddressPersistencePort.getStateByCompanyCode(companyCode, idOperation);
		LOG.info(String.format("%s RETURN CATALOGUE LIST ", idOperation));
		return responseModel;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ResponseModel getColonyByCompanyCodeAndParams(
			CatalogueAddressPersistencePort catalogueAddressPersistencePort, String companyCode,
			GenericSearchDirectionDto genericSearchDirectionDto, String idOperation) {
		LOG.info(String.format("%s INIT getColonyByCompanyCodeAndParams() ", idOperation));
		LOG.info(String.format("%s PARAMS: [ companyCode: %s , genericSearchDirectionDto: %s ]", idOperation,
				companyCode, genericSearchDirectionDto.toString()));
		
		
		List<ColonyDto> colonyDtoList = (List<ColonyDto>) catalogueAddressPersistencePort
				.getColonyByParamsAndCompanyCode(companyCode, genericSearchDirectionDto, idOperation).getData();

		LOG.info(String.format("%s colonyDtoList SIZE: %d  ", idOperation, colonyDtoList.size()));
		
		for (ColonyDto colonyDto : colonyDtoList) {
			MunicipalityCPDto municipalityCPDto = (MunicipalityCPDto) catalogueAddressPersistencePort
					.getMunicipalityCpByStateCodeAndCpAndCompanyCode(colonyDto.getStateCode(),
							colonyDto.getCp(), companyCode, idOperation)
					.getData();
			String municipalityName = (municipalityCPDto != null) ? municipalityCPDto.getName() : " ";
			colonyDto.setMunicipality(municipalityName);
		}

		LOG.info(String.format("%s RETURN CATALOGUE LIST ", idOperation));

		return new ResponseModel(colonyDtoList);
	}

	@Override
	public ResponseModel getMunicipalityByCompanyCode(CatalogueAddressPersistencePort catalogueAddressPersistencePort,
			String stateCode, String companyCode, String idOperation) {
		LOG.info(String.format("%s INIT getMunicipalityByCompanyCode() ", idOperation));
		LOG.info(String.format("%s PARAMS: [ companyCode: %s ]", idOperation, companyCode));
		ResponseModel responseModel = catalogueAddressPersistencePort
				.getMunicipalityByStateCodeAndCompanyCode(stateCode, companyCode, idOperation);
		LOG.info(String.format("%s RETURN CATALOGUE LIST ", idOperation));
		return responseModel;
	}

	@Override
	@SuppressWarnings("unchecked")
	public ResponseModel updateCatalogueByCompanyCodeAndCatalogueType(
			CatalogueAddressPersistencePort catalogueAddressPersistencePort, String companyCode, String catalogueType,
			String idOperation) {
		LOG.info(String.format("%s INIT updateCatalogueByCompanyCodeAndCatalogueType() ", idOperation));
		LOG.info(String.format("%s PARAMS: [ companyCode: %s , catalogueType: %s ]", idOperation, companyCode,
				catalogueType));
		List<StateDto> stateDtoList;
		HashMap<String, Integer> result;
		int updateRecord = 0, insertRecord = 0;
		ResponseModel responseModel = new ResponseModel();
		boolean validCatalogueType = CatalogueAddressTypeEnum.isValid(catalogueType);
		if (!validCatalogueType) {
			LOG.warn(String.format("%s INVALID ARTICLE-TYPE", idOperation));
			throw new ValidationError("WRONG CATALOGUE TYPE SELECTED");
		}
		CatalogueAddressTypeEnum catalogueTypeValue = CatalogueAddressTypeEnum.valueOf(catalogueType);

		switch (catalogueTypeValue) {

		case COORDINATE:
			LOG.info(String.format("%s INIT UPDATE CATALOGUE COORDINATE", idOperation));
			responseModel = this.updateCatalogueCoordinate(catalogueAddressPersistencePort, companyCode, idOperation);
			break;

		case FLAT:
			LOG.info(String.format("%s INIT UPDATE CATALOGUE FLAT", idOperation));
			responseModel = this.updateCatalogueFlat(catalogueAddressPersistencePort, companyCode, idOperation);
			break;

		case COUNTRY:
			LOG.info(String.format("%s INIT UPDATE CATALOGUE COUNTRY", idOperation));
			responseModel = this.updateCatalogueCountry(catalogueAddressPersistencePort, companyCode, idOperation);
			break;

		case STATE:
			LOG.info(String.format("%s INIT UPDATE CATALOGUE STATE", idOperation));
			responseModel = this.updateCatalogueState(catalogueAddressPersistencePort, companyCode, idOperation);
			break;

		case COLONY:
			LOG.info(String.format("%s INIT UPDATE CATALOGUE COLONY", idOperation));
			stateDtoList = (List<StateDto>) catalogueAddressPersistencePort
					.getStateByCompanyCode(companyCode, idOperation).getData();
			CatalogueUpdateResult catalogueUpdateResult = new CatalogueUpdateResult();
			updateRecord = 0;
			insertRecord = 0;
			for (StateDto stateDto : stateDtoList) {
				LOG.info(String.format("%s STATE: %s", idOperation, stateDto.getCode()));
				result = this.updateCatalogueColonyByStateCode(catalogueAddressPersistencePort, companyCode,
						stateDto.getCode(), idOperation);
				updateRecord = updateRecord + result.get("updateRecord");
				insertRecord = insertRecord + result.get("newRecord");
			}
			catalogueUpdateResult.setNewRecord(updateRecord);
			catalogueUpdateResult.setNewRecord(insertRecord);
			catalogueUpdateResult.setCatalogueType(CatalogueAddressTypeEnum.COLONY.toString());
			responseModel = new ResponseModel(catalogueUpdateResult);
			break;

		case MUNICIPALITY:
			LOG.info(String.format("%s INIT UPDATE CATALOGUE MUNICIPALITY", idOperation));
			stateDtoList = (List<StateDto>) catalogueAddressPersistencePort
					.getStateByCompanyCode(companyCode, idOperation).getData();
			List<CatalogueUpdateResult> catalogueUpdateResults = new ArrayList<>();
			CatalogueUpdateResult catalogueUpdateResultMuni = new CatalogueUpdateResult();
			CatalogueUpdateResult catalogueUpdateResultMuniCp = new CatalogueUpdateResult();
			updateRecord = 0;
			insertRecord = 0;
			for (StateDto stateDto : stateDtoList) {
				LOG.info(String.format("%s STATE: %s", idOperation, stateDto.getCode()));
				result = this.updateCatalogueMunicipalityByStateCode(catalogueAddressPersistencePort,
						stateDto.getCode(), companyCode, idOperation);
				updateRecord = updateRecord + result.get("updateRecord");
				insertRecord = insertRecord + result.get("newRecord");
			}
			catalogueUpdateResultMuni.setUpdateRecord(updateRecord);
			catalogueUpdateResultMuni.setNewRecord(insertRecord);
			catalogueUpdateResultMuni.setCatalogueType(CatalogueAddressTypeEnum.MUNICIPALITY.toString());

			insertRecord = 0;
			catalogueUpdateResults.add(catalogueUpdateResultMuni);

			for (StateDto stateDto : stateDtoList) {

				LOG.info(String.format("%s STATE: %s", idOperation, stateDto.getCode()));
				result = this.updateCatalogueMunicipalityAndCpByStateCode(catalogueAddressPersistencePort,
						stateDto.getCode(), companyCode, idOperation);
				insertRecord = insertRecord + result.get("newRecord");
			}

			catalogueUpdateResultMuniCp.setUpdateRecord(0);
			catalogueUpdateResultMuniCp.setNewRecord(insertRecord);
			catalogueUpdateResultMuniCp.setCatalogueType(CatalogueAddressTypeEnum.MUNICIPALITY.toString());

			catalogueUpdateResults.add(catalogueUpdateResultMuniCp);

			responseModel = new ResponseModel(catalogueUpdateResults);

			break;

		default:
			LOG.error(String.format("%s ERROR IN UPDATE CATALOGUE-ARTICLE", idOperation));
			throw new GlobalError();
		}

		return responseModel;
	}

	@SuppressWarnings("unchecked")
	public HashMap<String, Integer> updateCatalogueMunicipalityAndCpByStateCode(
			CatalogueAddressPersistencePort catalogueAddressPersistencePort, String stateCode, String companyCode,
			String idOperation) {
		boolean exists = false;
		int insertRecord = 0;
		HashMap<String, Integer> resultUpateCatalogue = new HashMap<>();
		List<MunicipalityCPDto> newRecordMunicipalityCpList = new ArrayList<>();

		List<MunicipalityCPDto> localMunicipalityCpList = (List<MunicipalityCPDto>) catalogueAddressPersistencePort
				.getMunicipalityCpByStateCodeAndCompanyCode(stateCode, companyCode, idOperation).getData();

		List<MunicipalityCPDto> remoteMunicipaCplityList = (List<MunicipalityCPDto>) catalogueOracleServicePort
				.getMunicipalityCpListBySatetCodeAndCompanyCode(stateCode, companyCode, idOperation).getData();

		if (localMunicipalityCpList.size() == 0) {
			LOG.info(
					String.format("%s CATALOGUE MUNICIPALITY CP BY STATE-CODE %s IS EMPTY, INSERT FROM EXTERNAL SOURCE",
							idOperation, stateCode));
			insertRecord = (int) catalogueAddressPersistencePort.updateCatalogueMunicipalityCpByCompanyCodeAndList(
					companyCode, remoteMunicipaCplityList, idOperation).getData();
		} else {
			LOG.info(
					String.format("%s VALIDATE LOCAL RECORDS TO INSERT NEW RECORDS FROM EXTERNAL SOURCE", idOperation));
			for (MunicipalityCPDto remoteMunicipalityCp : remoteMunicipaCplityList) {
				exists = false;
				for (MunicipalityCPDto localMunicipalityCp : localMunicipalityCpList) {
					if (remoteMunicipalityCp.getCp().trim().equals(localMunicipalityCp.getCp().trim())) {
						exists = true;
						break;
					}
				}
				if (!exists) {
					newRecordMunicipalityCpList.add(remoteMunicipalityCp);
				}
			}

			LOG.info(String.format("%s INSERT NEW RECORDS", idOperation));
			insertRecord = newRecordMunicipalityCpList.size() == 0 ? 0
					: (int) catalogueAddressPersistencePort.updateCatalogueMunicipalityCpByCompanyCodeAndList(
							companyCode, newRecordMunicipalityCpList, idOperation).getData();
		}
		resultUpateCatalogue.put("newRecord", insertRecord);
		return resultUpateCatalogue;
	}

	@SuppressWarnings("unchecked")
	public HashMap<String, Integer> updateCatalogueMunicipalityByStateCode(
			CatalogueAddressPersistencePort catalogueAddressPersistencePort, String stateCode, String companyCode,
			String idOperation) {
		boolean exists = false;
		int disableRecord = 0, updateRecord = 0, insertRecord = 0;
		HashMap<String, Integer> resultUpateCatalogue = new HashMap<>();
		List<MunicipalityDto> updateMunicipalityList = new ArrayList<>();
		List<MunicipalityDto> disableMunicipalityList = new ArrayList<>();
		List<MunicipalityDto> newRecordMunicipalityList = new ArrayList<>();

		List<MunicipalityDto> localMunicipalityList = (List<MunicipalityDto>) catalogueAddressPersistencePort
				.getMunicipalityByStateCodeAndCompanyCode(stateCode, companyCode, idOperation).getData();

		List<String> municipalityList = (List<String>) catalogueOracleServicePort
				.getMunicipalityNameListByStateCode(companyCode, stateCode, idOperation).getData();

		List<MunicipalityDto> remoteMunicipalityList = (List<MunicipalityDto>) catalogueOracleServicePort
				.getMunicipalityByNameListAndCompanyCode(municipalityList, stateCode, companyCode, idOperation)
				.getData();

		if (localMunicipalityList.size() == 0) {
			LOG.info(String.format("%s CATALOGUE MUNICIPALITY BY STATE-CODE %s IS EMPTY, INSERT FROM EXTERNAL SOURCE",
					idOperation, stateCode));
			insertRecord = (int) catalogueAddressPersistencePort
					.updateCatalogueMunicipalityByCompanyCodeAndList(companyCode, remoteMunicipalityList, idOperation)
					.getData();
		} else {
			LOG.info(String.format("%s VALIDATE RECORDS TO DISABLE AND UPDATE DATA", idOperation));
			for (MunicipalityDto localMunicipality : localMunicipalityList) {
				for (MunicipalityDto remoteMunicipality : remoteMunicipalityList) {
					if (localMunicipality.getCode().trim().equals(remoteMunicipality.getCode().trim())) {
						exists = true;
						localMunicipality.setName(remoteMunicipality.getName());
						updateMunicipalityList.add(localMunicipality);
					}
				}
				if (!exists) {
					localMunicipality.setIsEnable(false);
					disableMunicipalityList.add(localMunicipality);
				}
			}
			LOG.info(
					String.format("%s VALIDATE LOCAL RECORDS TO INSERT NEW RECORDS FROM EXTERNAL SOURCE", idOperation));
			for (MunicipalityDto remoteMunicipality : remoteMunicipalityList) {
				exists = false;
				for (MunicipalityDto localMunicipality : localMunicipalityList) {
					if (remoteMunicipality.getCode().trim().equals(localMunicipality.getCode().trim())) {
						exists = true;
						break;
					}
				}
				if (!exists) {
					newRecordMunicipalityList.add(remoteMunicipality);
				}
			}
			LOG.info(String.format("%s UPDATE DISABLE-RECORDS", idOperation));
			disableRecord = disableMunicipalityList.size() == 0 ? 0
					: (int) catalogueAddressPersistencePort.updateCatalogueMunicipalityByCompanyCodeAndList(companyCode,
							disableMunicipalityList, idOperation).getData();
			LOG.info(String.format("%s UPDATE FROM EXTERNAL-DATA", idOperation));
			updateRecord = updateMunicipalityList.size() == 0 ? 0
					: (int) catalogueAddressPersistencePort.updateCatalogueMunicipalityByCompanyCodeAndList(companyCode,
							updateMunicipalityList, idOperation).getData();
			LOG.info(String.format("%s INSERT NEW RECORDS", idOperation));
			insertRecord = newRecordMunicipalityList.size() == 0 ? 0
					: (int) catalogueAddressPersistencePort.updateCatalogueMunicipalityByCompanyCodeAndList(companyCode,
							newRecordMunicipalityList, idOperation).getData();
		}
		resultUpateCatalogue.put("newRecord", insertRecord);
		resultUpateCatalogue.put("updateRecord", disableRecord + updateRecord);
		return resultUpateCatalogue;
	}

	@SuppressWarnings("unchecked")
	public HashMap<String, Integer> updateCatalogueColonyByStateCode(
			CatalogueAddressPersistencePort catalogueAddressPersistencePort, String companyCode, String stateCode,
			String idOperation) {
		boolean exists = false;
		int disableRecord = 0, updateRecord = 0, insertRecord = 0;
		HashMap<String, Integer> resultUpateCatalogue = new HashMap<>();
		List<ColonyDto> updateColonyList = new ArrayList<>();
		List<ColonyDto> disableColonyList = new ArrayList<>();
		List<ColonyDto> newRecordColonyList = new ArrayList<>();

		List<ColonyDto> localColonyList = (List<ColonyDto>) catalogueAddressPersistencePort
				.getColonyByStateCodeAndCompanyCode(companyCode, stateCode, idOperation).getData();

		List<ColonyDto> remoteColonyList = (List<ColonyDto>) catalogueOracleServicePort
				.getColonyByCompanyCodeAndStateCode(companyCode, stateCode, idOperation).getData();

		if (localColonyList.size() == 0) {
			LOG.info(String.format("%s CATALOGUE COLONY BY STATE-CODE %s IS EMPTY, INSERT FROM EXTERNAL SOURCE",
					idOperation, stateCode));
			insertRecord = (int) catalogueAddressPersistencePort
					.updateCatalogueColonyByCompanyCodeAndList(companyCode, remoteColonyList, idOperation).getData();
		} else {
			LOG.info(String.format("%s VALIDATE RECORDS TO DISABLE AND UPDATE DATA", idOperation));
			for (ColonyDto localColony : localColonyList) {
				for (ColonyDto remoteColony : remoteColonyList) {
					if (localColony.getCp().trim().equals(remoteColony.getCp().trim())) {
						exists = true;
						localColony.setName(remoteColony.getName());
						localColony.setCity(remoteColony.getCity());
						updateColonyList.add(localColony);
					}
				}
				if (!exists) {
					localColony.setIsEnable(false);
					disableColonyList.add(localColony);
				}
			}
			LOG.info(
					String.format("%s VALIDATE LOCAL RECORDS TO INSERT NEW RECORDS FROM EXTERNAL SOURCE", idOperation));
			for (ColonyDto remoteColony : remoteColonyList) {
				exists = false;
				for (ColonyDto localColony : localColonyList) {
					if (remoteColony.getCp().trim().equals(localColony.getCp().trim())) {
						exists = true;
						break;
					}
				}
				if (!exists) {
					newRecordColonyList.add(remoteColony);
				}
			}
			LOG.info(String.format("%s UPDATE DISABLE-RECORDS", idOperation));
			disableRecord = disableColonyList.size() == 0 ? 0
					: (int) catalogueAddressPersistencePort
							.updateCatalogueColonyByCompanyCodeAndList(companyCode, disableColonyList, idOperation)
							.getData();
			LOG.info(String.format("%s UPDATE FROM EXTERNAL-DATA", idOperation));
			updateRecord = updateColonyList.size() == 0 ? 0
					: (int) catalogueAddressPersistencePort
							.updateCatalogueColonyByCompanyCodeAndList(companyCode, updateColonyList, idOperation)
							.getData();
			LOG.info(String.format("%s INSERT NEW RECORDS", idOperation));
			insertRecord = newRecordColonyList.size() == 0 ? 0
					: (int) catalogueAddressPersistencePort
							.updateCatalogueColonyByCompanyCodeAndList(companyCode, newRecordColonyList, idOperation)
							.getData();
		}
		resultUpateCatalogue.put("newRecord", insertRecord);
		resultUpateCatalogue.put("updateRecord", disableRecord + updateRecord);
		return resultUpateCatalogue;
	}

	@SuppressWarnings("unchecked")
	public ResponseModel updateCatalogueCoordinate(CatalogueAddressPersistencePort catalogueAddressPersistencePort,
			String companyCode, String idOperation) {
		CatalogueUpdateResult catalogueUpdateResult = new CatalogueUpdateResult();
		boolean exists = false;
		int disableRecord = 0, updateRecord = 0, insertRecord = 0;
		List<CoordinateDto> updateCoordinateList = new ArrayList<>();
		List<CoordinateDto> disableCoordinateList = new ArrayList<>();
		List<CoordinateDto> newRecordCoordinateList = new ArrayList<>();

		List<CoordinateDto> localCoordinateList = (List<CoordinateDto>) catalogueAddressPersistencePort
				.getCoordinateByCompanyCode(companyCode, idOperation).getData();

		List<CoordinateDto> remoteCoordinateList = (List<CoordinateDto>) catalogueOracleServicePort
				.getCoordinateByCompanyCode(companyCode, idOperation).getData();

		if (localCoordinateList.size() == 0) {
			LOG.info(String.format("%s CATALOGUE COORDINATE EMPTY, INSERT FROM EXTERNAL SOURCE", idOperation));
			insertRecord = (int) catalogueAddressPersistencePort
					.updateCatalogueCoordinateByCompanyCodeAndList(companyCode, remoteCoordinateList, idOperation)
					.getData();
			catalogueUpdateResult.setUpdateRecord(0);

		} else {
			LOG.info(String.format("%s VALIDATE RECORDS TO DISABLE AND UPDATE DATA", idOperation));
			for (CoordinateDto localCoordinate : localCoordinateList) {
				for (CoordinateDto remoteCoordinate : remoteCoordinateList) {
					if (localCoordinate.getCode().trim().equals(remoteCoordinate.getCode().trim())) {
						exists = true;
						localCoordinate.setName(remoteCoordinate.getName());
						updateCoordinateList.add(localCoordinate);
					}
				}
				if (!exists) {
					localCoordinate.setIsEnable(false);
					disableCoordinateList.add(localCoordinate);
				}
			}
			LOG.info(
					String.format("%s VALIDATE LOCAL RECORDS TO INSERT NEW RECORDS FROM EXTERNAL SOURCE", idOperation));
			for (CoordinateDto remoteCoordinate : remoteCoordinateList) {
				exists = false;
				for (CoordinateDto localCoordinate : localCoordinateList) {
					if (remoteCoordinate.getCode().trim().equals(localCoordinate.getCode().trim())) {
						exists = true;
						break;
					}
				}
				if (!exists) {
					newRecordCoordinateList.add(remoteCoordinate);
				}
			}
			LOG.info(String.format("%s UPDATE DISABLE-RECORDS", idOperation));
			disableRecord = disableCoordinateList.size() == 0 ? 0
					: (int) catalogueAddressPersistencePort.updateCatalogueCoordinateByCompanyCodeAndList(companyCode,
							disableCoordinateList, idOperation).getData();
			LOG.info(String.format("%s UPDATE FROM EXTERNAL-DATA", idOperation));
			updateRecord = updateCoordinateList.size() == 0 ? 0
					: (int) catalogueAddressPersistencePort.updateCatalogueCoordinateByCompanyCodeAndList(companyCode,
							updateCoordinateList, idOperation).getData();
			LOG.info(String.format("%s INSERT NEW RECORDS", idOperation));
			insertRecord = newRecordCoordinateList.size() == 0 ? 0
					: (int) catalogueAddressPersistencePort.updateCatalogueCoordinateByCompanyCodeAndList(companyCode,
							newRecordCoordinateList, idOperation).getData();
			catalogueUpdateResult.setUpdateRecord(disableRecord + updateRecord);

		}
		catalogueUpdateResult.setNewRecord(insertRecord);
		catalogueUpdateResult.setCatalogueType(CatalogueAddressTypeEnum.COORDINATE.toString());
		return new ResponseModel(catalogueUpdateResult);
	}

	@SuppressWarnings("unchecked")
	public ResponseModel updateCatalogueFlat(CatalogueAddressPersistencePort catalogueAddressPersistencePort,
			String companyCode, String idOperation) {
		CatalogueUpdateResult catalogueUpdateResult = new CatalogueUpdateResult();
		boolean exists = false;
		int disableRecord = 0, updateRecord = 0, insertRecord = 0;
		List<FlatDto> updateFlatList = new ArrayList<>();
		List<FlatDto> disableFlatList = new ArrayList<>();
		List<FlatDto> newRecordFlatList = new ArrayList<>();

		List<FlatDto> localFlatList = (List<FlatDto>) catalogueAddressPersistencePort
				.getFlatByCompanyCode(companyCode, idOperation).getData();

		List<FlatDto> remoteFlatList = (List<FlatDto>) catalogueOracleServicePort
				.getFlatByCompanyCode(companyCode, idOperation).getData();

		if (localFlatList.size() == 0) {
			LOG.info(String.format("%s CATALOGUE FLAT EMPTY, INSERT FROM EXTERNAL SOURCE", idOperation));
			insertRecord = (int) catalogueAddressPersistencePort
					.updateCatalogueFlatByCompanyCodeAndList(companyCode, remoteFlatList, idOperation).getData();
			catalogueUpdateResult.setUpdateRecord(0);
		} else {
			LOG.info(String.format("%s VALIDATE RECORDS TO DISABLE AND UPDATE DATA", idOperation));
			for (FlatDto localFlat : localFlatList) {
				for (FlatDto remoteFlat : remoteFlatList) {
					if (localFlat.getCode().trim().equals(remoteFlat.getCode().trim())) {
						exists = true;
						localFlat.setName(remoteFlat.getName());
						updateFlatList.add(localFlat);
					}
				}
				if (!exists) {
					localFlat.setIsEnable(false);
					disableFlatList.add(localFlat);
				}
			}
			LOG.info(
					String.format("%s VALIDATE LOCAL RECORDS TO INSERT NEW RECORDS FROM EXTERNAL SOURCE", idOperation));
			for (FlatDto remoteFlatDto : remoteFlatList) {
				exists = false;
				for (FlatDto localFlatDto : localFlatList) {
					if (remoteFlatDto.getCode().trim().equals(localFlatDto.getCode().trim())) {
						exists = true;
						break;
					}
				}
				if (!exists) {
					newRecordFlatList.add(remoteFlatDto);
				}
			}
			LOG.info(String.format("%s UPDATE DISABLE-RECORDS", idOperation));
			disableRecord = disableFlatList.size() == 0 ? 0
					: (int) catalogueAddressPersistencePort
							.updateCatalogueFlatByCompanyCodeAndList(companyCode, disableFlatList, idOperation)
							.getData();
			LOG.info(String.format("%s UPDATE FROM EXTERNAL-DATA", idOperation));
			updateRecord = updateFlatList.size() == 0 ? 0
					: (int) catalogueAddressPersistencePort
							.updateCatalogueFlatByCompanyCodeAndList(companyCode, updateFlatList, idOperation)
							.getData();
			LOG.info(String.format("%s INSERT NEW RECORDS", idOperation));
			insertRecord = newRecordFlatList.size() == 0 ? 0
					: (int) catalogueAddressPersistencePort
							.updateCatalogueFlatByCompanyCodeAndList(companyCode, newRecordFlatList, idOperation)
							.getData();
			catalogueUpdateResult.setUpdateRecord(disableRecord + updateRecord);

		}
		catalogueUpdateResult.setNewRecord(insertRecord);
		catalogueUpdateResult.setCatalogueType(CatalogueAddressTypeEnum.FLAT.toString());
		return new ResponseModel(catalogueUpdateResult);
	}

	@SuppressWarnings("unchecked")
	public ResponseModel updateCatalogueCountry(CatalogueAddressPersistencePort catalogueAddressPersistencePort,
			String companyCode, String idOperation) {
		CatalogueUpdateResult catalogueUpdateResult = new CatalogueUpdateResult();
		boolean exists = false;
		int disableRecord = 0, updateRecord = 0, insertRecord = 0;
		List<CountryDto> updateCountryList = new ArrayList<>();
		List<CountryDto> disableCountryList = new ArrayList<>();
		List<CountryDto> newRecordCountryList = new ArrayList<>();

		List<CountryDto> localCountryList = (List<CountryDto>) catalogueAddressPersistencePort
				.getCountryByCompanyCode(companyCode, idOperation).getData();

		List<CountryDto> remoteCountryList = (List<CountryDto>) catalogueOracleServicePort
				.getCountryByCompanyCode(companyCode, idOperation).getData();

		if (localCountryList.size() == 0) {
			LOG.info(String.format("%s CATALOGUE COUNTRY EMPTY, INSERT FROM EXTERNAL SOURCE", idOperation));
			insertRecord = (int) catalogueAddressPersistencePort
					.updateCatalogueCountryByCompanyCodeAndList(companyCode, remoteCountryList, idOperation).getData();
			catalogueUpdateResult.setUpdateRecord(0);

		} else {
			LOG.info(String.format("%s VALIDATE RECORDS TO DISABLE AND UPDATE DATA", idOperation));
			for (CountryDto localCountry : localCountryList) {
				for (CountryDto remoteCountry : remoteCountryList) {
					if (localCountry.getCode().trim().equals(remoteCountry.getCode().trim())) {
						exists = true;
						localCountry.setName(remoteCountry.getName());
						updateCountryList.add(localCountry);
					}
				}
				if (!exists) {
					localCountry.setIsEnable(false);
					disableCountryList.add(localCountry);
				}
			}
			LOG.info(
					String.format("%s VALIDATE LOCAL RECORDS TO INSERT NEW RECORDS FROM EXTERNAL SOURCE", idOperation));
			for (CountryDto remoteCountry : remoteCountryList) {
				exists = false;
				for (CountryDto localCountry : localCountryList) {
					if (remoteCountry.getCode().trim().equals(localCountry.getCode().trim())) {
						exists = true;
						break;
					}
				}
				if (!exists) {
					newRecordCountryList.add(remoteCountry);
				}
			}
			LOG.info(String.format("%s UPDATE DISABLE-RECORDS", idOperation));
			disableRecord = disableCountryList.size() == 0 ? 0
					: (int) catalogueAddressPersistencePort
							.updateCatalogueCountryByCompanyCodeAndList(companyCode, disableCountryList, idOperation)
							.getData();
			LOG.info(String.format("%s UPDATE FROM EXTERNAL-DATA", idOperation));
			updateRecord = updateCountryList.size() == 0 ? 0
					: (int) catalogueAddressPersistencePort
							.updateCatalogueCountryByCompanyCodeAndList(companyCode, updateCountryList, idOperation)
							.getData();
			LOG.info(String.format("%s INSERT NEW RECORDS", idOperation));
			insertRecord = newRecordCountryList.size() == 0 ? 0
					: (int) catalogueAddressPersistencePort
							.updateCatalogueCountryByCompanyCodeAndList(companyCode, newRecordCountryList, idOperation)
							.getData();
			catalogueUpdateResult.setUpdateRecord(disableRecord + updateRecord);

		}
		catalogueUpdateResult.setNewRecord(insertRecord);
		catalogueUpdateResult.setCatalogueType(CatalogueAddressTypeEnum.COUNTRY.toString());
		return new ResponseModel(catalogueUpdateResult);
	}

	@SuppressWarnings("unchecked")
	public ResponseModel updateCatalogueState(CatalogueAddressPersistencePort catalogueAddressPersistencePort,
			String companyCode, String idOperation) {
		CatalogueUpdateResult catalogueUpdateResult = new CatalogueUpdateResult();
		boolean exists = false;
		int disableRecord = 0, updateRecord = 0, insertRecord = 0;
		List<StateDto> updateStateList = new ArrayList<>();
		List<StateDto> disableStateList = new ArrayList<>();
		List<StateDto> newRecordStateList = new ArrayList<>();

		List<StateDto> localStateList = (List<StateDto>) catalogueAddressPersistencePort
				.getStateByCompanyCode(companyCode, idOperation).getData();

		List<StateDto> remoteStateList = (List<StateDto>) catalogueOracleServicePort
				.getStateByCompanyCode(companyCode, idOperation).getData();

		if (localStateList.size() == 0) {
			LOG.info(String.format("%s CATALOGUE STATE EMPTY, INSERT FROM EXTERNAL SOURCE", idOperation));
			insertRecord = (int) catalogueAddressPersistencePort
					.updateCatalogueStateByCompanyCodeAndList(companyCode, remoteStateList, idOperation).getData();
			catalogueUpdateResult.setUpdateRecord(0);

		} else {
			LOG.info(String.format("%s VALIDATE RECORDS TO DISABLE AND UPDATE DATA", idOperation));
			for (StateDto localState : localStateList) {
				for (StateDto remoteState : remoteStateList) {
					if (localState.getCode().trim().equals(remoteState.getCode().trim())) {
						exists = true;
						localState.setName(remoteState.getName());
						updateStateList.add(localState);
					}
				}
				if (!exists) {
					localState.setIsEnable(false);
					disableStateList.add(localState);
				}
			}
			LOG.info(
					String.format("%s VALIDATE LOCAL RECORDS TO INSERT NEW RECORDS FROM EXTERNAL SOURCE", idOperation));
			for (StateDto remoteState : remoteStateList) {
				exists = false;
				for (StateDto localState : localStateList) {
					if (remoteState.getCode().trim().equals(localState.getCode().trim())) {
						exists = true;
						break;
					}
				}
				if (!exists) {
					newRecordStateList.add(remoteState);
				}
			}
			LOG.info(String.format("%s UPDATE DISABLE-RECORDS", idOperation));
			disableRecord = disableStateList.size() == 0 ? 0
					: (int) catalogueAddressPersistencePort
							.updateCatalogueStateByCompanyCodeAndList(companyCode, disableStateList, idOperation)
							.getData();
			LOG.info(String.format("%s UPDATE FROM EXTERNAL-DATA", idOperation));
			updateRecord = updateStateList.size() == 0 ? 0
					: (int) catalogueAddressPersistencePort
							.updateCatalogueStateByCompanyCodeAndList(companyCode, updateStateList, idOperation)
							.getData();
			LOG.info(String.format("%s INSERT NEW RECORDS", idOperation));
			insertRecord = newRecordStateList.size() == 0 ? 0
					: (int) catalogueAddressPersistencePort
							.updateCatalogueStateByCompanyCodeAndList(companyCode, newRecordStateList, idOperation)
							.getData();
			catalogueUpdateResult.setUpdateRecord(disableRecord + updateRecord);

		}
		catalogueUpdateResult.setNewRecord(insertRecord);
		catalogueUpdateResult.setCatalogueType(CatalogueAddressTypeEnum.STATE.toString());
		return new ResponseModel(catalogueUpdateResult);
	}
}