package mx.com.endtoend.domain.catalogue.ports.spi;

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

public interface CatalogueAddressPersistencePort {

    ResponseModel getCoordinateByCompanyCode(String companyCode, String idOperation);

    ResponseModel getFlatByCompanyCode(String companyCode, String idOperation);

    ResponseModel getCountryByCompanyCode(String companyCode, String idOperation);

    ResponseModel getStateByCompanyCode(String companyCode, String idOperation);

    ResponseModel getColonyByStateCodeAndCompanyCode(String companyCode, String stateCode, String idOperation);

    ResponseModel getColonyByParamsAndCompanyCode(
            String companyCode, GenericSearchDirectionDto genericSearchDirectionDto, String idOperation);

    ResponseModel getMunicipalityByStateCodeAndCompanyCode(String stateCode, String companyCode, String idOperation);
    
    ResponseModel getMunicipalityCpByStateCodeAndCompanyCode(String stateCode, String companyCode, String idOperation);
    
    ResponseModel getMunicipalityCpByStateCodeAndCpAndCompanyCode(String stateCode, String cp, String companyCode, String idOperation);

    ResponseModel updateCatalogueCoordinateByCompanyCodeAndList(String companyCode,
                                                                List<CoordinateDto> coordinateDtoList,
                                                                String idOperation);

    ResponseModel updateCatalogueFlatByCompanyCodeAndList(String companyCode, List<FlatDto> flatDtoList,
                                                          String idOperation);

    ResponseModel updateCatalogueCountryByCompanyCodeAndList(String companyCode, List<CountryDto> countryDtoList,
                                                          String idOperation);

    ResponseModel updateCatalogueStateByCompanyCodeAndList(String companyCode, List<StateDto> stateDtoList,
                                                             String idOperation);

    ResponseModel updateCatalogueColonyByCompanyCodeAndList(String companyCode, List<ColonyDto> colonyDtoList,
                                                           String idOperation);

    ResponseModel updateCatalogueMunicipalityByCompanyCodeAndList(
            String companyCode, List<MunicipalityDto> municipalityDtoList, String idOperation);
    
    ResponseModel updateCatalogueMunicipalityCpByCompanyCodeAndList(
            String companyCode, List<MunicipalityCPDto> municipalityCpDtoList, String idOperation);
}
