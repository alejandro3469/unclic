package mx.com.endtoend.domain.catalogue.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.com.endtoend.domain.catalogue.bussiness.address.CatalogueAddressFactory;
import mx.com.endtoend.domain.catalogue.bussiness.address.CatalogueAddressInterface;
import mx.com.endtoend.domain.catalogue.dto.address.GenericSearchDirectionDto;
import mx.com.endtoend.domain.catalogue.ports.api.CatalogueAddressServicePort;
import mx.com.endtoend.domain.catalogue.ports.spi.CatalogueAddressPersistencePort;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.GlobalError;
import mx.com.endtoend.infrastructure.services.jde.catalogue.common.serviceport.CatalogueJdeServicePort;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

public class CatalogueAddressServiceImpl implements CatalogueAddressServicePort {
    private final Logger LOG = LoggerFactory.getLogger(CatalogueAddressServiceImpl.class);
    private final CatalogueAddressPersistencePort catalogueAddressPersistencePort;
    private final CatalogueAddressFactory addressFactory = new CatalogueAddressFactory();

    public CatalogueAddressServiceImpl(CatalogueAddressPersistencePort catalogueAddressPersistencePort) {
        this.catalogueAddressPersistencePort = catalogueAddressPersistencePort;
    }

    @Override
    public ResponseModel getCoordinateByCompanyCode(CatalogueJdeServicePort catalogueOracleServicePort, String method,
                                                    String companyCode, String idOperation) {
        LOG.info(String.format("%s INIT getCoordinateByCompanyCode() ", idOperation));
        LOG.info(String.format("%s PARAMS: [ method: %s , companyCode: %s ]", idOperation, method, companyCode));
        CatalogueAddressInterface catalogueAddress = addressFactory.getImplementationByCode(method,
                catalogueOracleServicePort);
        if (catalogueAddress == null) {
            LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
            throw new GlobalError();
        }
        ResponseModel responseModel = catalogueAddress.getCoordinateByCompanyCode(catalogueAddressPersistencePort,
                companyCode, idOperation);
        return responseModel;
    }

    @Override
    public ResponseModel getFlatByCompanyCode(CatalogueJdeServicePort catalogueOracleServicePort, String method,
                                              String companyCode, String idOperation) {
        LOG.info(String.format("%s INIT getFlatByCompanyCode() ", idOperation));
        LOG.info(String.format("%s PARAMS: [ method: %s , companyCode: %s ]", idOperation, method, companyCode));
        CatalogueAddressInterface catalogueAddress = addressFactory.getImplementationByCode(method,
                catalogueOracleServicePort);
        if (catalogueAddress == null) {
            LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
            throw new GlobalError();
        }
        ResponseModel responseModel = catalogueAddress.getFlatByCompanyCode(catalogueAddressPersistencePort,
                companyCode, idOperation);
        return responseModel;
    }

    @Override
    public ResponseModel getCountryByCompanyCode(CatalogueJdeServicePort catalogueOracleServicePort, String method,
                                                 String companyCode, String idOperation) {
        LOG.info(String.format("%s INIT getCountryByCompanyCode() ", idOperation));
        LOG.info(String.format("%s PARAMS: [ method: %s , companyCode: %s ]", idOperation, method, companyCode));
        CatalogueAddressInterface catalogueAddress = addressFactory.getImplementationByCode(method,
                catalogueOracleServicePort);
        if (catalogueAddress == null) {
            LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
            throw new GlobalError();
        }
        ResponseModel responseModel = catalogueAddress.getCountryByCompanyCode(catalogueAddressPersistencePort,
                companyCode, idOperation);
        return responseModel;
    }

    @Override
    public ResponseModel getStateByCompanyCode(CatalogueJdeServicePort catalogueOracleServicePort, String method,
                                               String companyCode, String idOperation) {
        LOG.info(String.format("%s INIT getStateByCompanyCode() ", idOperation));
        LOG.info(String.format("%s PARAMS: [ method: %s , companyCode: %s ]", idOperation, method, companyCode));
        CatalogueAddressInterface catalogueAddress = addressFactory.getImplementationByCode(method,
                catalogueOracleServicePort);
        if (catalogueAddress == null) {
            LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
            throw new GlobalError();
        }
        ResponseModel responseModel = catalogueAddress.getStateByCompanyCode(catalogueAddressPersistencePort,
                companyCode, idOperation);
        return responseModel;
    }

    @Override
    public ResponseModel getColonyByCompanyCodeAndParams(CatalogueJdeServicePort catalogueOracleServicePort, String method,
                                                         String companyCode, GenericSearchDirectionDto genericSearchDirectionDto,
                                                         String idOperation) {
        LOG.info(String.format("%s INIT getColonyByCompanyCodeAndParams() ", idOperation));
        LOG.info(String.format("%s PARAMS: [ genericSearchDirectionDto: %s , method: %s , companyCode: %s ]",
                idOperation, genericSearchDirectionDto.toString(), method, companyCode));
        CatalogueAddressInterface catalogueAddress = addressFactory.getImplementationByCode(method,
                catalogueOracleServicePort);
        if (catalogueAddress == null) {
            LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
            throw new GlobalError();
        }
        ResponseModel responseModel = catalogueAddress
                .getColonyByCompanyCodeAndParams(catalogueAddressPersistencePort, companyCode,
                        genericSearchDirectionDto, idOperation);
        return responseModel;
    }

    @Override
    public ResponseModel getMunicipalityByCompanyCode(CatalogueJdeServicePort catalogueOracleServicePort, String method,
                                                      String stateCode, String companyCode, String idOperation) {
        LOG.info(String.format("%s INIT getMunicipalityByCompanyCode() ", idOperation));
        LOG.info(String.format("%s PARAMS: [ method: %s , companyCode: %s ]", idOperation, method, companyCode));
        CatalogueAddressInterface catalogueAddress = addressFactory.getImplementationByCode(method,
                catalogueOracleServicePort);
        if (catalogueAddress == null) {
            LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
            throw new GlobalError();
        }
        ResponseModel responseModel = catalogueAddress.getMunicipalityByCompanyCode(catalogueAddressPersistencePort,
                stateCode, companyCode, idOperation);
        return responseModel;
    }

    @Override
    public ResponseModel updateCatalogueByCompanyCodeAndCatalogueType(
            CatalogueJdeServicePort catalogueOracleServicePort, String method, String companyCode, String catalogueType,
            String idOperation) {
        LOG.info(String.format("%s INIT updateCatalogueByCompanyCodeAndCatalogueType() ", idOperation));
        LOG.info(String.format("%s PARAMS: [ method: %s , companyCode: %s ]", idOperation, method, companyCode));
        CatalogueAddressInterface catalogueAddress = addressFactory.getImplementationByCode(method,
                catalogueOracleServicePort);
        if (catalogueAddress == null) {
            LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
            throw new GlobalError();
        }
        ResponseModel responseModel = catalogueAddress.updateCatalogueByCompanyCodeAndCatalogueType(
                catalogueAddressPersistencePort, companyCode, catalogueType, idOperation);
        return responseModel;
    }
}