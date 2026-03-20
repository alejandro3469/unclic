package mx.com.endtoend.domain.catalogue.bussiness;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.com.endtoend.domain.catalogue.dto.CatalogueJdeDTO;
import mx.com.endtoend.domain.catalogue.dto.address.CoordinateDto;
import mx.com.endtoend.domain.catalogue.dto.address.CountryDto;
import mx.com.endtoend.domain.catalogue.dto.address.FlatDto;
import mx.com.endtoend.domain.catalogue.dto.address.StateDto;
import mx.com.endtoend.domain.catalogue.dto.client.CFDIDto;
import mx.com.endtoend.domain.catalogue.dto.client.ClientTypeDto;
import mx.com.endtoend.domain.catalogue.dto.client.ContactMethodDto;
import mx.com.endtoend.domain.catalogue.dto.client.RegimeFiscalDto;
import mx.com.endtoend.domain.catalogue.dto.client.WorkTypeDto;
import mx.com.endtoend.domain.catalogue.ports.spi.CataloguePersistencePort;
import mx.com.endtoend.domain.commons.constants.GenericIdentifyMethods;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.GlobalError;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.ValidationError;
import mx.com.endtoend.infrastructure.services.jde.catalogue.common.serviceport.CatalogueJdeServicePort;
import mx.com.endtoend.smart.bussiness.model.catalogue.dto.StatusDto;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

public class CatalogueMethodTow implements CatalogueInterface {

    private final static Logger LOG = LoggerFactory.getLogger(CatalogueMethodTow.class);
    private CatalogueJdeServicePort catalogueOracleServicePort;
    private GenericIdentifyMethods method = GenericIdentifyMethods.CAT_CONF_TWO;

    public CatalogueMethodTow(CatalogueJdeServicePort catalogueOracleServicePort) {
        this.catalogueOracleServicePort = catalogueOracleServicePort;
    }

    /**
     * Método que recupera la lista de tipo de clientes del JDE.
     *
     * @param companyCode            código de compañía
     * @param catalogPersistencePort repositorio principal del sistema
     * @param idOperation            identificador de traza
     * @return ResponseModel objeto con el código de resultado de la operación, en
     * caso exitoso contiene embebida la información recuperada
     */
    @Override
    public ResponseModel getClientTypeByCompanyCode(String companyCode, CataloguePersistencePort catalogPersistencePort,
                                                    String idOperation) {

        LOG.info(String.format("%s INIT getClientTypeByCompanyCode() ", idOperation));
        LOG.info(String.format("%s PARAMS: [companyCode: %s ]", idOperation, companyCode));

        ResponseModel responseModel = catalogueOracleServicePort.getClientTypeByCompanyCode(companyCode, idOperation);

        if (responseModel.getResponseCode() == 100) {
            LOG.info("BÚSQUEDA DE CATALOGO DE TIPO DE CLIENTE");

            List<CatalogueJdeDTO> catalogueJdeDtoList = new ArrayList<CatalogueJdeDTO>();

            List<ClientTypeDto> clientTypeDtoList = (List<ClientTypeDto>) responseModel.getData();

            for (ClientTypeDto ClientTypeDto : clientTypeDtoList) {
                CatalogueJdeDTO catalogueJdeDTO = new CatalogueJdeDTO();
                catalogueJdeDTO.setCode(ClientTypeDto.getCode());
                catalogueJdeDTO.setName(ClientTypeDto.getValue());
                catalogueJdeDtoList.add(catalogueJdeDTO);
            }

            return new ResponseModel(catalogueJdeDtoList);


        } else {
            LOG.error("ERROR AL RECUPERAR EL CATALOGO DE TIPO DE CLIENTE");
            throw new GlobalError();
        }
    }

    /**
     * Método que recupera la lista de coordenadas del JDE.
     *
     * @param companyCode            código de compañía
     * @param catalogPersistencePort repositorio principal del sistema
     * @param idOperation            identificador de traza
     * @return ResponseModel objeto con el código de resultado de la operación, en
     * caso exitoso contiene embebida la información recuperada
     */
    @Override
    public ResponseModel getCoordinateByCompanyCode(String companyCode, CataloguePersistencePort catalogPersistencePort,
                                                    String idOperation) {
        LOG.info(String.format("%s INIT getCoordinateByCompanyCode() ", idOperation));
        LOG.info(String.format("%s PARAMS: [companyCode: %s ]", idOperation, companyCode));
        ResponseModel responseModel = catalogueOracleServicePort.getCoordinateByCompanyCode(companyCode, idOperation);
        if (responseModel.getResponseCode() == 100) {
            LOG.info("BÚSQUEDA DE CATALOGO DE COORDENADAS");
            List<CatalogueJdeDTO> catalogueJdeDtoList = new ArrayList<CatalogueJdeDTO>();
            List<CoordinateDto> coordinateDtoList = (List<CoordinateDto>) responseModel.getData();
            for (CoordinateDto coordinateDto : coordinateDtoList) {
                CatalogueJdeDTO catalogueJdeDTO = new CatalogueJdeDTO();
                catalogueJdeDTO.setCode(coordinateDto.getCode());
                catalogueJdeDTO.setName(coordinateDto.getName());
                catalogueJdeDtoList.add(catalogueJdeDTO);
            }
            return new ResponseModel(catalogueJdeDtoList);
        } else {
            LOG.error("ERROR AL RECUPERAR EL CATALOGO DE CORDENADAS");
            throw new GlobalError();
        }
    }

    /**
     * Método que recupera la lista de planos del JDE.
     *
     * @param companyCode            código de compañía
     * @param catalogPersistencePort repositorio principal del sistema
     * @param idOperation            identificador de traza
     * @return ResponseModel objeto con el código de resultado de la operación, en
     * caso exitoso contiene embebida la información recuperada
     */
    @Override
    public ResponseModel getFlatByCompanyCode(String companyCode, CataloguePersistencePort catalogPersistencePort,
                                              String idOperation) {

        LOG.info(String.format("%s INIT getFlatByCompanyCode() ", idOperation));
        LOG.info(String.format("%s PARAMS: [companyCode: %s ]", idOperation, companyCode));

        ResponseModel responseModel = catalogueOracleServicePort.getFlatByCompanyCode(companyCode, idOperation);

        if (responseModel.getResponseCode() == 100) {
            LOG.info("BÚSQUEDA DE CATALOGO DE PLANO");
            List<CatalogueJdeDTO> catalogueJdeDtoList = new ArrayList<CatalogueJdeDTO>();
            List<FlatDto> flatDtoList = (List<FlatDto>) responseModel.getData();
            for (FlatDto flatDto : flatDtoList) {
                CatalogueJdeDTO catalogueJdeDTO = new CatalogueJdeDTO();
                catalogueJdeDTO.setCode(flatDto.getCode());
                catalogueJdeDTO.setName(flatDto.getName());
                catalogueJdeDtoList.add(catalogueJdeDTO);
            }
            return new ResponseModel(catalogueJdeDtoList);
        } else {
            LOG.error("ERROR AL RECUPERAR EL CATALOGO DE PLANO");
            throw new GlobalError();
        }
    }

    /**
     * Método que recupera la lista de país del JDE.
     *
     * @param companyCode            código de compañía
     * @param catalogPersistencePort repositorio principal del sistema
     * @param idOperation            identificador de traza
     * @return ResponseModel objeto con el código de resultado de la operación, en
     * caso exitoso contiene embebida la información recuperada
     */
    @Override
    public ResponseModel getCountryByCompanyCode(String companyCode, CataloguePersistencePort catalogPersistencePort,
                                                 String idOperation) {
        LOG.info(String.format("%s INIT getCountryByCompanyCode() ", idOperation));
        LOG.info(String.format("%s PARAMS: [companyCode: %s ]", idOperation, companyCode));
        ResponseModel responseModel = catalogueOracleServicePort.getCountryByCompanyCode(companyCode, idOperation);
        if (responseModel.getResponseCode() == 100) {
            LOG.info("BÚSQUEDA DE CATALOGO DE PAÍS");
            List<CatalogueJdeDTO> catalogueJdeDtoList = new ArrayList<CatalogueJdeDTO>();
            List<CountryDto> countryDtoList = (List<CountryDto>) responseModel.getData();
            for (CountryDto countryDto : countryDtoList) {
                CatalogueJdeDTO catalogueJdeDTO = new CatalogueJdeDTO();
                catalogueJdeDTO.setCode(countryDto.getCode());
                catalogueJdeDTO.setName(countryDto.getName());
                catalogueJdeDtoList.add(catalogueJdeDTO);
            }
            return new ResponseModel(catalogueJdeDtoList);
        } else {
            LOG.error("ERROR AL RECUPERAR EL CATALOGO DE PAÍS");
            throw new GlobalError();
        }
    }

    /**
     * Método que recupera la lista de estado del JDE.
     *
     * @param companyCode            código de compañía
     * @param catalogPersistencePort repositorio principal del sistema
     * @param idOperation            identificador de traza
     * @return ResponseModel objeto con el código de resultado de la operación, en
     * caso exitoso contiene embebida la información recuperada
     */
    @Override
    public ResponseModel getStateByCompanyCode(String companyCode, CataloguePersistencePort catalogPersistencePort,
                                               String idOperation) {

        LOG.info(String.format("%s INIT getStateByCompanyCode() ", idOperation));
        LOG.info(String.format("%s PARAMS: [companyCode: %s ]", idOperation, companyCode));
        ResponseModel responseModel = catalogueOracleServicePort.getStateByCompanyCode(companyCode, idOperation);
        if (responseModel.getResponseCode() == 100) {
            LOG.info("BÚSQUEDA DE CATALOGO DE ESTADO");
            List<CatalogueJdeDTO> catalogueJdeDtoList = new ArrayList<CatalogueJdeDTO>();
            List<StateDto> stateDtoList = (List<StateDto>) responseModel.getData();
            for (StateDto stateDto : stateDtoList) {
                CatalogueJdeDTO catalogueJdeDTO = new CatalogueJdeDTO();
                catalogueJdeDTO.setCode(stateDto.getCode());
                catalogueJdeDTO.setName(stateDto.getName());
                catalogueJdeDtoList.add(catalogueJdeDTO);
            }
            return new ResponseModel(catalogueJdeDtoList);
        } else {
            LOG.error("ERROR AL RECUPERAR EL CATALOGO DE ESTADO");
            throw new GlobalError();
        }
    }

    /**
     * Método que recupera la lista de delegacion del JDE.
     *
     * @param companyCode            código de compañía
     * @param catalogPersistencePort repositorio principal del sistema
     * @param idOperation            identificador de traza
     * @return ResponseModel objeto con el código de resultado de la operación, en
     * caso exitoso contiene embebida la información recuperada
     */
    @Override
    public ResponseModel getDelegationByCompanyCode(String companyCode, CataloguePersistencePort catalogPersistencePort,
                                                    String idOperation) {

        LOG.info(String.format("%s INIT getDelegationByCompanyCode() ", idOperation));
        LOG.info(String.format("%s PARAMS: [companyCode: %s ]", idOperation, companyCode));

        ResponseModel responseModel = catalogueOracleServicePort.getDelegationByCompanyCode(companyCode, idOperation);

        if (responseModel.getResponseCode() == 100) {
            LOG.info("BÚSQUEDA DE CATALOGO DE DELEGACION");
            return responseModel;
        } else {
            LOG.error("ERROR AL RECUPERAR EL CATALOGO DE DELEGACION");
            throw new GlobalError();
        }
    }

    /**
     * Método que recupera la lista de como nos contacto del JDE.
     *
     * @param companyCode            código de compañía
     * @param catalogPersistencePort repositorio principal del sistema
     * @param idOperation            identificador de traza
     * @return ResponseModel objeto con el código de resultado de la operación, en
     * caso exitoso contiene embebida la información recuperada
     */
    @Override
    public ResponseModel getHowToContactByCompanyCode(String companyCode,
                                                      CataloguePersistencePort catalogPersistencePort, String idOperation) {

        LOG.info(String.format("%s INIT getHowToContactByCompanyCode() ", idOperation));
        LOG.info(String.format("%s PARAMS: [companyCode: %s ]", idOperation, companyCode));

        ResponseModel responseModel = catalogueOracleServicePort.getHowToContactByCompanyCode(companyCode, idOperation);

        if (responseModel.getResponseCode() == 100) {
            LOG.info("BÚSQUEDA DE CATALOGO DE COMO NOS CONTACTO");

            List<CatalogueJdeDTO> catalogueJdeDtoList = new ArrayList<CatalogueJdeDTO>();

            List<ContactMethodDto> contactMethodDtoList = (List<ContactMethodDto>) responseModel.getData();

            for (ContactMethodDto contactMethodDto : contactMethodDtoList) {
                CatalogueJdeDTO catalogueJdeDTO = new CatalogueJdeDTO();
                catalogueJdeDTO.setCode(contactMethodDto.getCode());
                catalogueJdeDTO.setName(contactMethodDto.getValue());
                catalogueJdeDtoList.add(catalogueJdeDTO);
            }

            return new ResponseModel(catalogueJdeDtoList);

        } else {
            LOG.error("ERROR AL RECUPERAR EL CATALOGO DE COMO NOS CONTACTO");
            throw new GlobalError();
        }
    }

    /**
     * Método que recupera la lista de tipo de obra del JDE.
     *
     * @param companyCode            código de compañía
     * @param catalogPersistencePort repositorio principal del sistema
     * @param idOperation            identificador de traza
     * @return ResponseModel objeto con el código de resultado de la operación, en
     * caso exitoso contiene embebida la información recuperada
     */
    @Override
    public ResponseModel getWorkTypeByCompanyCode(String companyCode, CataloguePersistencePort catalogPersistencePort,
                                                  String idOperation) {

        LOG.info(String.format("%s INIT getWorkTypeByCompanyCode() ", idOperation));
        LOG.info(String.format("%s PARAMS: [companyCode: %s ]", idOperation, companyCode));

        ResponseModel responseModel = catalogueOracleServicePort.getWorkTypeByCompanyCode(companyCode, idOperation);

        if (responseModel.getResponseCode() == 100) {
            LOG.info("BÚSQUEDA DE CATALOGO DE TIPO DE OBRA");

            List<CatalogueJdeDTO> catalogueJdeDtoList = new ArrayList<CatalogueJdeDTO>();

            List<WorkTypeDto> workTypeDtoList = (List<WorkTypeDto>) responseModel.getData();

            for (WorkTypeDto workTypeDto : workTypeDtoList) {
                CatalogueJdeDTO catalogueJdeDTO = new CatalogueJdeDTO();
                catalogueJdeDTO.setCode(workTypeDto.getCode());
                catalogueJdeDTO.setName(workTypeDto.getValue());
                catalogueJdeDtoList.add(catalogueJdeDTO);
            }

            return new ResponseModel(catalogueJdeDtoList);

        } else {
            LOG.error("ERROR AL RECUPERAR EL CATALOGO DE TIPO DE OBRA");
            throw new GlobalError();
        }
    }


    @Override
    public ResponseModel updateCatalogsByCompanyCode(String companyCode,
                                                     CataloguePersistencePort catalogPersistencePort, String idOperation) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Método que recupera la lista de colonia del JDE
     *
     * @param companyCode            código de compañía
     * @param catalogPersistencePort repositorio principal del sistema
     * @param idOperation            identificador de traza
     * @return ResponseModel objeto con el código de resultado de la operación, en
     * caso exitoso contiene embebida la información recuperada
     */
    @Override
    public ResponseModel getAddressColony(String companyCode, CataloguePersistencePort catalogPersistencePort,
                                          String cp, String idOperation) {

        LOG.info(String.format("%s INIT getAddressColony() ", idOperation));
        LOG.info(String.format("%s PARAMS: [companyCode: %s ]", idOperation, companyCode));

        ResponseModel responseModel = catalogueOracleServicePort.getAddressColony(companyCode, cp, idOperation);

        if (responseModel.getResponseCode() == 100) {
            LOG.info("BÚSQUEDA DE CATALOGO DE DIRECCION");
            return responseModel;
        } else {
            LOG.error("ERROR AL RECUPERAR EL CATALOGO DE DIRECCION");
            throw new GlobalError();
        }
    }

    /**
     * Método que recupera la lista de dirección del JDE
     *
     * @param companyCode            código de compañía
     * @param catalogPersistencePort repositorio principal del sistema
     * @param idOperation            identificador de traza
     * @return ResponseModel objeto con el código de resultado de la operación, en
     * caso exitoso contiene embebida la información recuperada
     */
    @Override
    public ResponseModel getAddress(String companyCode, CataloguePersistencePort catalogPersistencePort, String colony,
                                    String cp, String idOperation) {

        LOG.info(String.format("%s INIT getAddress() ", idOperation));
        LOG.info(String.format("%s PARAMS: [companyCode: %s ]", idOperation, companyCode));

        ResponseModel responseModel = catalogueOracleServicePort.getAddress(companyCode, colony, cp, idOperation);

        if (responseModel.getResponseCode() == 100) {
            LOG.info("BÚSQUEDA DE CATALOGO DE DIRECCION");
            return responseModel;
        } else {
            LOG.error("ERROR AL RECUPERAR EL CATALOGO DE DIRECCION");
            throw new GlobalError();
        }
    }

    /**
     * Método que recupera la lista de delegacion del JDE
     *
     * @param companyCode            código de compañía
     * @param catalogPersistencePort repositorio principal del sistema
     * @param idOperation            identificador de traza
     * @return ResponseModel objeto con el código de resultado de la operación, en
     * caso exitoso contiene embebida la información recuperada
     */
    @Override
    public ResponseModel getAddressDelegation(String companyCode, CataloguePersistencePort catalogPersistencePort,
                                              String state, String idOperation) {

        LOG.info(String.format("%s INIT getAddressDelegation() ", idOperation));
        LOG.info(String.format("%s PARAMS: [companyCode: %s , state: %s ]", idOperation, companyCode, state));

        ResponseModel responseModel = catalogueOracleServicePort.getAddressDelegation(companyCode, state, idOperation);

        if (responseModel.getResponseCode() == 100) {
            LOG.info("BÚSQUEDA DE CATALOGO DE DIRECCION");
            return responseModel;
        } else {
            LOG.error("ERROR AL RECUPERAR EL CATALOGO DE DIRECCION");
            throw new GlobalError();
        }
    }

    /**
     * Método que recupera la lista de cfi del JDE
     *
     * @param companyCode            código de compañía
     * @param catalogPersistencePort repositorio principal del sistema
     * @param idOperation            identificador de traza
     * @return ResponseModel objeto con el código de resultado de la operación, en
     * caso exitoso contiene embebida la información recuperada
     */
    @SuppressWarnings("unchecked")
    @Override
    public ResponseModel getCfdi(String companyCode, CataloguePersistencePort catalogPersistencePort,
                                 String idOperation) {

        LOG.info(String.format("%s INIT getCfdi() ", idOperation));
        LOG.info(String.format("%s PARAMS: [companyCode: %s ]", idOperation, companyCode));

        ResponseModel responseModel = new ResponseModel();

        responseModel = catalogueOracleServicePort.getCfdi(companyCode, idOperation);

        if (responseModel.getResponseCode() == 100) {

            List<CatalogueJdeDTO> catalogueJdeDtoList = new ArrayList<CatalogueJdeDTO>();

            List<CFDIDto> cfdiDtoList = (List<CFDIDto>) responseModel.getData();

            for (CFDIDto cfdiDto : cfdiDtoList) {
                CatalogueJdeDTO catalogueJdeDTO = new CatalogueJdeDTO();
                catalogueJdeDTO.setCode(cfdiDto.getCode());
                catalogueJdeDTO.setName(cfdiDto.getValue());
                catalogueJdeDtoList.add(catalogueJdeDTO);
            }

            LOG.info(
                    String.format("%s RESULT FROM PERSISTENCE PORT: %d", idOperation, responseModel.getResponseCode()));
            return new ResponseModel(catalogueJdeDtoList);

        } else {

            LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
            throw new GlobalError();
        }

    }

    /**
     * Método que recupera la lista de régimen fiscal del JDE
     *
     * @param companyCode            código de compañía
     * @param catalogPersistencePort repositorio principal del sistema
     * @param idOperation            identificador de traza
     * @return ResponseModel objeto con el código de resultado de la operación, en
     * caso exitoso contiene embebida la información recuperada
     */
    @Override
    public ResponseModel getFiscalRegimeCataloge(CataloguePersistencePort catalogPersistencePort, String companyCode,
                                                 String idOperation) {

        LOG.info(String.format("%s INIT getFiscalRegimeCataloge() ", idOperation));
        LOG.info(String.format("%s PARAMS: [companyCode: %s ]", idOperation, companyCode));

        ResponseModel responseFromPersistencePort = catalogueOracleServicePort
                .getFiscalRegimeCatalogeByCompanyCode(companyCode, idOperation);

        if (responseFromPersistencePort.getData() == null) {
            LOG.error(String.format("%s ERROR IN GET FISCAL REGIME CATALOGE TO COMPANY %s ", idOperation, companyCode));
            throw new GlobalError();
        }

        List<CatalogueJdeDTO> catalogueJdeDtoList = new ArrayList<CatalogueJdeDTO>();

        List<RegimeFiscalDto> regimeFiscalDtoList = (List<RegimeFiscalDto>) responseFromPersistencePort.getData();

        for (RegimeFiscalDto regimeFiscalDto : regimeFiscalDtoList) {
            CatalogueJdeDTO catalogueJdeDTO = new CatalogueJdeDTO();
            catalogueJdeDTO.setCode(regimeFiscalDto.getCode());
            catalogueJdeDTO.setName(regimeFiscalDto.getValue());
            catalogueJdeDtoList.add(catalogueJdeDTO);
        }

        return new ResponseModel(catalogueJdeDtoList);

    }

    /**
     * Método que para crear estados en el catalogo.
     *
     * @param companyCode            código de compañía
     * @param catalogPersistencePort repositorio principal del sistema
     * @param idOperation            identificador de traza
     * @return ResponseModel objeto con el código de resultado de la operación, en
     * caso exitoso contiene embebida la información recuperada
     */
    @Override
    public ResponseModel createStatus(String companyCode, StatusDto statusDto,
                                      CataloguePersistencePort catalogPersistencePort, String idOperation) {

        LOG.info(String.format("%s INIT createStatus() ", idOperation));
        LOG.info(String.format("%s PARAMS: [companyCode: %s , statusDto: %s ]", idOperation, companyCode,
                statusDto.toString()));

        ResponseModel responseModel = new ResponseModel();
        boolean existsStatus = catalogPersistencePort.existsStatus(statusDto, companyCode, method.toString(),
                idOperation);

        if (existsStatus) {
            LOG.info("Ya existe");
            throw new ValidationError("Ya existe");
        } else {

            catalogPersistencePort.createStatus(companyCode, statusDto, method.toString(), idOperation);
            LOG.info(
                    String.format("%s RESULT FROM PERSISTENCE PORT: %d", idOperation, responseModel.getResponseCode()));
            responseModel = new ResponseModel("OK");
        }

        return responseModel;

    }

    @Override
    public ResponseModel getStatusListByCompanyCode(CataloguePersistencePort cataloguePersistencePort,
                                                    String companyCode, String idOperation) {

        LOG.info(String.format("%s INIT getStatusListByCompanyCode() ", idOperation));
        LOG.info(String.format("%s PARAMS: [companyCode: %s ]", idOperation, companyCode));

        ResponseModel responseFromPersistencePort = cataloguePersistencePort.getStatusListByCompanyCode(companyCode,
                method.toString(), idOperation);

        if (responseFromPersistencePort.getData() == null) {
            LOG.error(String.format("%s ERROR IN GET STATUS-LIST TO COMPANY %s ", idOperation, companyCode));
            throw new GlobalError();
        }

        LOG.info(String.format("%s RETURN STATUS-LIST", idOperation));

        return responseFromPersistencePort;
    }

    @Override
    public ResponseModel getCategoryListByCodeAndCompanyCode(CataloguePersistencePort catalogPersistencePort,
                                                             String companyCode, String method, String categoryCode, String idOperation) {

        LOG.info(String.format("%s INIT getCategoryListByCodeAndCompanyCode() ", idOperation));
        LOG.info(String.format("%s PARAMS: [companyCode: %s , method: %s , categoryCode: %s ]", idOperation,
                companyCode, method, categoryCode));

        ResponseModel responseFromPersistencePort = catalogueOracleServicePort
                .getCategoryListByCompanyCodeAndCategoryCode(companyCode, categoryCode, idOperation);

        if (responseFromPersistencePort.getData() == null) {
            LOG.error(String.format("%s ERROR IN GET CATEGORY LIST BY CODE: %s AND COMPANY: ", idOperation,
                    categoryCode, companyCode));
            throw new GlobalError();
        }

        LOG.info(String.format("%s RETURN CATEGORY LIST", idOperation));

        return responseFromPersistencePort;
    }

    @Override
    public ResponseModel getStatus(String companyCode, Long id, CataloguePersistencePort catalogPersistencePort,
                                   String idOperation) {
        LOG.info(String.format("%s INIT getStatus() ", idOperation));
        LOG.info(String.format("%s PARAMS: [companyCode: %s , id: %s ]", idOperation, companyCode, id));
        ResponseModel responseModel = new ResponseModel();
        responseModel = catalogPersistencePort.getStatus(companyCode, id, method.toString(), idOperation);
        if (responseModel.getResponseCode() == 100) {
            LOG.info(
                    String.format("%s RESULT FROM PERSISTENCE PORT: %d", idOperation, responseModel.getResponseCode()));
            return responseModel;
        } else {
            LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
            throw new GlobalError();
        }
    }
}