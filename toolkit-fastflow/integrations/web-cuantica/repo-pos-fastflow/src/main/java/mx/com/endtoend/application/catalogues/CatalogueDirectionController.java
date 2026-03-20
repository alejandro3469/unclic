package mx.com.endtoend.application.catalogues;

import static mx.com.endtoend.genericCommonsFileds.utilities.ResponseLog.generateIdOperation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mx.com.endtoend.domain.catalogue.dto.address.GenericSearchDirectionDto;
import mx.com.endtoend.domain.catalogue.ports.api.CatalogueAddressServicePort;
import mx.com.endtoend.domain.company.dto.MethodDto;
import mx.com.endtoend.domain.company.ports.api.CompanyServicePort;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.ValidationError;
import mx.com.endtoend.infrastructure.services.jde.catalogue.common.serviceport.CatalogueJdeServicePort;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

@RestController
@RequestMapping("/catalogue-direction")
public class CatalogueDirectionController {

    private static final String module = "CATALOGUE-ADDRESS";
    private final Logger LOG = LoggerFactory.getLogger(CatalogueDirectionController.class);
    public String idOperation;
    @Autowired
    private CompanyServicePort companyServicePort;
    @Autowired
    private CatalogueJdeServicePort catalogueOracleServicePort;
    @Autowired
    private CatalogueAddressServicePort catalogueAddressServicePort;

    /**
     * EndPoint para la recuperación de la lista de coordenadas pertenecientes a una compañía
     *
     * @param companyCode código de compañía
     * @param branchCode  código de sucursal
     * @return ResponseModel, objeto que contiene el código de resultado de la
     * operación y los datos solicitados
     */
    @GetMapping("/coordinate/{companyCode}/{branchCode}")
    public ResponseEntity<ResponseModel> getCoordinateList(@PathVariable String companyCode,
                                                           @PathVariable String branchCode) {
        idOperation = generateIdOperation(companyCode, branchCode, module);
        LOG.info(String.format("%s INIT getCoordinateList()", idOperation));
        LOG.info(
                String.format("%s PARAMS: [ companyCode: %s , branchCode: %s ]", idOperation, companyCode, branchCode));
        MethodDto method = (MethodDto) companyServicePort
                .findMethodByCompanyCodeAndModule(companyCode, module, idOperation).getData();
        if (method == null) {
            LOG.info(String.format("%s ERROR IN ASSIGNATION METHODS OF THE COMPANY %s ", idOperation, companyCode));
            throw new ValidationError("ERROR - CONTACT YOUR ADMINISTRATOR");
        }
        ResponseModel responseModel = catalogueAddressServicePort.getCoordinateByCompanyCode(catalogueOracleServicePort,
                method.getCode(), companyCode, idOperation);
        return new ResponseEntity<>(responseModel, HttpStatus.OK);
    }

    /**
     * EndPoint para la recuperación de la lista de planos pertenecientes a una compañía
     *
     * @param companyCode código de compañía
     * @param branchCode  código de sucursal
     * @return ResponseModel, objeto que contiene el código de resultado de la
     * operación y los datos solicitados
     */
    @GetMapping("/flat/{companyCode}/{branchCode}")
    public ResponseEntity<ResponseModel> getFlatList(@PathVariable String companyCode,
                                                     @PathVariable String branchCode) {
        idOperation = generateIdOperation(companyCode, branchCode, module);
        LOG.info(String.format("%s INIT getFlatList()", idOperation));
        LOG.info(
                String.format("%s PARAMS: [ companyCode: %s , branchCode: %s ]", idOperation, companyCode, branchCode));
        MethodDto method = (MethodDto) companyServicePort
                .findMethodByCompanyCodeAndModule(companyCode, module, idOperation).getData();
        if (method == null) {
            LOG.info(String.format("%s ERROR IN ASSIGNATION METHODS OF THE COMPANY %s ", idOperation, companyCode));
            throw new ValidationError("ERROR - CONTACT YOUR ADMINISTRATOR");
        }
        ResponseModel responseModel = catalogueAddressServicePort.getFlatByCompanyCode(catalogueOracleServicePort,
                method.getCode(), companyCode, idOperation);
        return new ResponseEntity<>(responseModel, HttpStatus.OK);
    }

    /**
     * EndPoint para la recuperación de la lista de países pertenecientes a una compañía
     *
     * @param companyCode código de compañía
     * @param branchCode  código de sucursal
     * @return ResponseModel, objeto que contiene el código de resultado de la
     * operación y los datos solicitados
     */
    @GetMapping("/country/{companyCode}/{branchCode}")
    public ResponseEntity<ResponseModel> getCountryList(@PathVariable String companyCode,
                                                        @PathVariable String branchCode) {
        idOperation = generateIdOperation(companyCode, branchCode, module);
        LOG.info(String.format("%s INIT getCountryList()", idOperation));
        LOG.info(
                String.format("%s PARAMS: [ companyCode: %s , branchCode: %s ]", idOperation, companyCode, branchCode));
        MethodDto method = (MethodDto) companyServicePort
                .findMethodByCompanyCodeAndModule(companyCode, module, idOperation).getData();
        if (method == null) {
            LOG.info(String.format("%s ERROR IN ASSIGNATION METHODS OF THE COMPANY %s ", idOperation, companyCode));
            throw new ValidationError("ERROR - CONTACT YOUR ADMINISTRATOR");
        }
        ResponseModel responseModel = catalogueAddressServicePort.getCountryByCompanyCode(catalogueOracleServicePort,
                method.getCode(), companyCode, idOperation);
        return new ResponseEntity<>(responseModel, HttpStatus.OK);
    }

    /**
     * EndPoint para la recuperación de la lista de estados pertenecientes a una compañía
     *
     * @param companyCode código de compañía
     * @param branchCode  código de sucursal
     * @return ResponseModel, objeto que contiene el código de resultado de la
     * operación y los datos solicitados
     */
    @GetMapping("/state/{companyCode}/{branchCode}")
    public ResponseEntity<ResponseModel> getStateList(@PathVariable String companyCode,
                                                      @PathVariable String branchCode) {
        idOperation = generateIdOperation(companyCode, branchCode, module);
        LOG.info(String.format("%s INIT getStateList()", idOperation));
        LOG.info(
                String.format("%s PARAMS: [ companyCode: %s , branchCode: %s ]", idOperation, companyCode, branchCode));
        MethodDto method = (MethodDto) companyServicePort
                .findMethodByCompanyCodeAndModule(companyCode, module, idOperation).getData();
        if (method == null) {
            LOG.info(String.format("%s ERROR IN ASSIGNATION METHODS OF THE COMPANY %s ", idOperation, companyCode));
            throw new ValidationError("ERROR - CONTACT YOUR ADMINISTRATOR");
        }
        ResponseModel responseModel = catalogueAddressServicePort.getStateByCompanyCode(catalogueOracleServicePort,
                method.getCode(), companyCode, idOperation);
        return new ResponseEntity<>(responseModel, HttpStatus.OK);
    }

    /**
     * EndPoint para la recuperación de colonias asociadas a un estado o código postal y que pertenecen a una compañía
     *
     * @param companyCode código de compañía
     * @param branchCode  código de sucursal
     * @return ResponseModel, objeto que contiene el código de resultado de la
     * operación y los datos solicitados
     */
    @PostMapping("/colony/{companyCode}/{branchCode}")
    public ResponseEntity<ResponseModel> getColonyListByParams(@PathVariable String companyCode,
                                                               @PathVariable String branchCode,
                                                               @RequestBody GenericSearchDirectionDto genericSearchDirectionDto) {
        idOperation = generateIdOperation(companyCode, branchCode, module);
        LOG.info(String.format("%s INIT getColonyListByParams()", idOperation));
        LOG.info(
                String.format("%s PARAMS: [ genericSearchDirectionDto: %s , companyCode: %s , branchCode: %s ]",
                        idOperation, genericSearchDirectionDto.toString(), companyCode, branchCode));
        MethodDto method = (MethodDto) companyServicePort
                .findMethodByCompanyCodeAndModule(companyCode, module, idOperation).getData();
        if (method == null) {
            LOG.info(String.format("%s ERROR IN ASSIGNATION METHODS OF THE COMPANY %s ", idOperation, companyCode));
            throw new ValidationError("ERROR - CONTACT YOUR ADMINISTRATOR");
        }
        ResponseModel responseModel = catalogueAddressServicePort
                .getColonyByCompanyCodeAndParams(catalogueOracleServicePort, method.getCode(), companyCode,
                        genericSearchDirectionDto, idOperation);
        return new ResponseEntity<>(responseModel, HttpStatus.OK);
    }

    /**
     * EndPoint para la recuperación de municipios asociados a un estado y que pertenecen a una compañía
     *
     * @param companyCode código de compañía
     * @param branchCode  código de sucursal
     * @return ResponseModel, objeto que contiene el código de resultado de la
     * operación y los datos solicitados
     */
    @GetMapping("/municipality/{stateCode}/{companyCode}/{branchCode}")
    public ResponseEntity<ResponseModel> getMunicipalityListByStateCode(
            @PathVariable String stateCode, @PathVariable String companyCode, @PathVariable String branchCode) {
        idOperation = generateIdOperation(companyCode, branchCode, module);
        LOG.info(String.format("%s INIT getMunicipalityListByStateCode()", idOperation));
        LOG.info(String.format("%s PARAMS: [ stateCode: %s ,companyCode: %s , branchCode: %s ]",
                        idOperation, stateCode, companyCode, branchCode));
        MethodDto method = (MethodDto) companyServicePort
                .findMethodByCompanyCodeAndModule(companyCode, module, idOperation).getData();
        if (method == null) {
            LOG.info(String.format("%s ERROR IN ASSIGNATION METHODS OF THE COMPANY %s ", idOperation, companyCode));
            throw new ValidationError("ERROR - CONTACT YOUR ADMINISTRATOR");
        }
        ResponseModel responseModel = catalogueAddressServicePort.getMunicipalityByCompanyCode(
                catalogueOracleServicePort, method.getCode(), stateCode, companyCode, idOperation);
        return new ResponseEntity<>(responseModel, HttpStatus.OK);
    }

    /**
     * EndPoint para la actualización global de los catálogos por tipo especificado
     * y compañía ingresada
     *
     * @param catalogType tipo de catálogo a actualizar
     * @param companyCode código de compañía registrada en el sistema
     * @param branchCode  código de la sucursal donde se realiza la consulta
     * @return ResponseModel, objeto que contiene el código de resultado de la
     * operación y los datos solicitados
     */
    @PutMapping("/update/{catalogType}/{companyCode}/{branchCode}")
    public ResponseEntity<ResponseModel> updateCatalogByTypeAndCompanyCode(@PathVariable String catalogType,
                                                                           @PathVariable String companyCode, @PathVariable String branchCode) {

        idOperation = generateIdOperation(companyCode, branchCode, module);
        LOG.info(String.format("%s INIT updateCatalogByTypeAndCompanyCode()", idOperation));
        LOG.info(String.format("%s PARAMS: [ catalogType: %s ,companyCode: %s , branchCode: %s ]", idOperation,
                catalogType, companyCode, branchCode));
        MethodDto method = (MethodDto) companyServicePort
                .findMethodByCompanyCodeAndModule(companyCode, module, idOperation).getData();
        if (method == null) {
            LOG.info(String.format("%s ERROR IN ASSIGNATION METHODS OF THE COMPANY %s ", idOperation, companyCode));
            throw new ValidationError("ERROR - CONTACT YOUR ADMINISTRATOR");
        }
        ResponseModel responseModel = catalogueAddressServicePort.updateCatalogueByCompanyCodeAndCatalogueType(
                catalogueOracleServicePort, method.getCode(), companyCode, catalogType, idOperation);
        return new ResponseEntity<>(responseModel, HttpStatus.OK);
    }
}