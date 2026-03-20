package mx.com.endtoend.domain.catalogue.bussiness.address;

import mx.com.endtoend.domain.catalogue.dto.address.GenericSearchDirectionDto;
import mx.com.endtoend.domain.catalogue.ports.spi.CatalogueAddressPersistencePort;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

public interface CatalogueAddressInterface {

    ResponseModel getCoordinateByCompanyCode(CatalogueAddressPersistencePort catalogueAddressPersistencePort,
                                             String companyCode, String idOperation);

    ResponseModel getFlatByCompanyCode(CatalogueAddressPersistencePort catalogueAddressPersistencePort,
                                       String companyCode, String idOperation);

    ResponseModel getCountryByCompanyCode(CatalogueAddressPersistencePort catalogueAddressPersistencePort,
                                          String companyCode, String idOperation);

    ResponseModel getStateByCompanyCode(CatalogueAddressPersistencePort catalogueAddressPersistencePort,
                                        String companyCode, String idOperation);

    ResponseModel getColonyByCompanyCodeAndParams(CatalogueAddressPersistencePort catalogueAddressPersistencePort,
                                                  String companyCode, GenericSearchDirectionDto genericSearchDirectionDto, String idOperation);

    ResponseModel getMunicipalityByCompanyCode(CatalogueAddressPersistencePort catalogueAddressPersistencePort,
                                               String stateCode, String companyCode, String idOperation);

    ResponseModel updateCatalogueByCompanyCodeAndCatalogueType(
            CatalogueAddressPersistencePort catalogueAddressPersistencePort, String companyCode, String catalogueType,
            String idOperation);
}
