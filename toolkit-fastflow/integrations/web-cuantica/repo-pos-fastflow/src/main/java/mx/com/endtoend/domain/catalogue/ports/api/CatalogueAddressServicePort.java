package mx.com.endtoend.domain.catalogue.ports.api;

import mx.com.endtoend.domain.catalogue.dto.address.GenericSearchDirectionDto;
import mx.com.endtoend.infrastructure.services.jde.catalogue.common.serviceport.CatalogueJdeServicePort;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

public interface CatalogueAddressServicePort {
    ResponseModel getCoordinateByCompanyCode(CatalogueJdeServicePort catalogueOracleServicePort, String method,
                                             String companyCode, String idOperation);

    ResponseModel getFlatByCompanyCode(CatalogueJdeServicePort catalogueOracleServicePort, String method,
                                       String companyCode, String idOperation);

    ResponseModel getCountryByCompanyCode(CatalogueJdeServicePort catalogueOracleServicePort, String method,
                                          String companyCode, String idOperation);

    ResponseModel getStateByCompanyCode(CatalogueJdeServicePort catalogueOracleServicePort, String method,
                                        String companyCode, String idOperation);

    ResponseModel getColonyByCompanyCodeAndParams(CatalogueJdeServicePort catalogueOracleServicePort, String method,
                                                  String companyCode, GenericSearchDirectionDto genericSearchDirectionDto,
                                                  String idOperation);

    ResponseModel getMunicipalityByCompanyCode(CatalogueJdeServicePort catalogueOracleServicePort, String method,
                                               String stateCode, String companyCode, String idOperation);

    ResponseModel updateCatalogueByCompanyCodeAndCatalogueType(CatalogueJdeServicePort catalogueOracleServicePort,
                                                               String method, String companyCode, String catalogueType,
                                                               String idOperation);

}
