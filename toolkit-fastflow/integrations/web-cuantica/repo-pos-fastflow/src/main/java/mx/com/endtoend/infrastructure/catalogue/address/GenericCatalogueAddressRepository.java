package mx.com.endtoend.infrastructure.catalogue.address;

import java.util.List;

import mx.com.endtoend.domain.catalogue.dto.address.ColonyDto;
import mx.com.endtoend.domain.catalogue.dto.address.CoordinateDto;
import mx.com.endtoend.domain.catalogue.dto.address.CountryDto;
import mx.com.endtoend.domain.catalogue.dto.address.FlatDto;
import mx.com.endtoend.domain.catalogue.dto.address.GenericSearchDirectionDto;
import mx.com.endtoend.domain.catalogue.dto.address.MunicipalityCPDto;
import mx.com.endtoend.domain.catalogue.dto.address.MunicipalityDto;
import mx.com.endtoend.domain.catalogue.dto.address.StateDto;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

public interface GenericCatalogueAddressRepository {

	ResponseModel getCoordinateByCompanyCode(String idOperation);

	ResponseModel getFlatByCompanyCode(String idOperation);

	ResponseModel getCountryByCompanyCode(String idOperation);

	ResponseModel getStateByCompanyCode(String idOperation);

	ResponseModel getColonyByStateCode(String stateCode, String idOperation);

	ResponseModel getColonyByParamsAndCompanyCode(GenericSearchDirectionDto genericSearchDirectionDto,
			String idOperation);

	ResponseModel getMunicipalityByStateCodeAndCompanyCode(String stateCode, String idOperation);

	ResponseModel getMunicipalityCpByStateCode(String stateCode, String idOperation);

	ResponseModel getMunicipalityCpByStateCodeAndCp(String stateCode, String cp, String idOperation);

	ResponseModel updateCatalogueCoordinateByCompanyCodeAndList(List<CoordinateDto> coordinateDtoList,
			String idOperation);

	ResponseModel updateCatalogueFlatByCompanyCodeAndList(List<FlatDto> flatDtoList, String idOperation);

	ResponseModel updateCatalogueCountryByCompanyCodeAndList(List<CountryDto> countryDtoList, String idOperation);

	ResponseModel updateCatalogueStateByCompanyCodeAndList(List<StateDto> stateDtoList, String idOperation);

	ResponseModel updateCatalogueColonyByCompanyCodeAndList(List<ColonyDto> colonyDtoList, String idOperation);

	ResponseModel updateCatalogueMunicipalityByCompanyCodeAndList(List<MunicipalityDto> municipalityDtoList,
			String idOperation);

	ResponseModel updateCatalogueMunicipalityCpByList(List<MunicipalityCPDto> municipalityCpDtoList,
			String idOperation);
}
