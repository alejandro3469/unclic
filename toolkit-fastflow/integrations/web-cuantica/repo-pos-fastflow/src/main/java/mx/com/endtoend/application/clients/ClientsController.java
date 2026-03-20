package mx.com.endtoend.application.clients;

import static mx.com.endtoend.genericCommonsFileds.utilities.ResponseLog.generateIdOperation;

import javax.validation.Valid;

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

import mx.com.endtoend.domain.clients.dto.FiltersClientDto;
import mx.com.endtoend.domain.clients.ports.api.ClientServicePort;
import mx.com.endtoend.domain.company.dto.MethodDto;
import mx.com.endtoend.domain.company.ports.api.CompanyServicePort;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.GlobalError;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.ValidationError;
import mx.com.endtoend.infrastructure.services.jde.clients.common.serviceport.ClientOracleServicePort;
import mx.com.endtoend.infrastructure.services.posLegacy.clients.common.serviceport.ClientLegacyServicePort;
import mx.com.endtoend.smart.bussiness.model.clients.dto.ClientDto;
import mx.com.endtoend.smart.bussiness.model.clients.dto.ShippingAddressDto;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

@RestController
@RequestMapping("/client")
public class ClientsController {

    private static String module = "CLIENTS";
    private final Logger LOG = LoggerFactory.getLogger(ClientsController.class);
    String idOperation;
    @Autowired
    private ClientServicePort clientServicePort;
    @Autowired
    private CompanyServicePort companyServicePort;
    @Autowired
    private ClientOracleServicePort clientOracleServicePort;
    @Autowired
    private ClientLegacyServicePort clientPosLegacyServicePort;

    /**
     * EndPoint que recupera una lista de clientes por los parámetros introducidos y
     * el código de la compañía
     *
     * @param filtersClientDto parámetros de búsqueda y paginación
     * @param companyCode      compañía de la que se obtendrá la información
     * @return ResponseModel, objeto que contiene la información del objeto
     * actualizado y el código de respuesta
     */
    @PostMapping("/find-by-params/{companyCode}/{branchCode}")
    public ResponseEntity<ResponseModel> getListClient(@RequestBody FiltersClientDto filtersClientDto, @PathVariable String companyCode, @PathVariable String branchCode) {
        idOperation = generateIdOperation(companyCode, branchCode, module);
        LOG.info(String.format("%s INIT getListClient()", idOperation));
        LOG.info(String.format("%s PARAMS: [filtersClientDto: %s , companyCode: %s ]", idOperation, filtersClientDto.toString(), companyCode));
        MethodDto method = (MethodDto) companyServicePort.findMethodByCompanyCodeAndModule(companyCode, module, idOperation).getData();
        if (method == null) {
            throw new ValidationError("method");
        }
        ResponseModel responseModel = clientServicePort.getListClient(filtersClientDto, companyCode, method.getCode(), idOperation, clientOracleServicePort, clientPosLegacyServicePort);
        return ResponseEntity.status(HttpStatus.OK).body(responseModel);
    }

    /**
     * EndPoint que retorna el detalle de un cliente por su id para todos los que se encuentren almacenados en el sistema local,
     * y por su número de cliente para todos los que pertenescan a una fuente externa
     *
     * @param companyCode  compañía de la que se obtendrá la información.
     * @param branchCode   código de sucursal
     * @param id           identificador de cliente
     * @param clientNumber número de cliente
     * @param isExternal   indicador de origen del cliente
     * @return ResponseModel, objeto que contiene la información del objeto
     * actualizado y el código de respuesta
     */
    @GetMapping("/view/{id}/{clientNumber}/{isExternal}/{companyCode}/{branchCode}")
    public ResponseEntity<ResponseModel> getClientById(@PathVariable String companyCode, @PathVariable String branchCode,
                                                       @PathVariable Long id, @PathVariable Long clientNumber,
                                                       @PathVariable boolean isExternal) {
        idOperation = generateIdOperation(companyCode, branchCode, module);
        LOG.info(String.format("%s INIT getClient()", idOperation));
        LOG.info(String.format(
                "%s PARAMS: [id: %s ,  clientNumber: %d ,  isExternal: %b  companyCode: %s , branchCode: %s ]",
                idOperation, id.toString(), clientNumber, isExternal, companyCode, branchCode));
        MethodDto method = (MethodDto) companyServicePort.findMethodByCompanyCodeAndModule(companyCode, module, idOperation).getData();
        if (method == null) throw new ValidationError("method");
        ResponseModel responseModel = clientServicePort.recoverClient(companyCode, id, clientNumber, isExternal,
                method.getCode(), idOperation, clientOracleServicePort, clientPosLegacyServicePort);
        return ResponseEntity.status(HttpStatus.OK).body(responseModel);
    }

    /**
     * EndPoint para la creación de clientes por código de compañía
     *
     * @param companyCode compañía de la que se obtendrá la información.
     * @param clientDto   modelo de datos de la información del cliente
     * @param branchCode  código de sucursal
     * @return ResponseModel, objeto que contiene la información del objeto
     * actualizado y el código de respuesta
     */
    @PostMapping("/create/{companyCode}/{branchCode}")
    public ResponseEntity<ResponseModel> createClientByCompanyCode(@RequestBody @Valid ClientDto clientDto, @PathVariable String companyCode, @PathVariable String branchCode) {
        idOperation = generateIdOperation(companyCode, branchCode, module);
        LOG.info(String.format("%s INIT saveClient()", idOperation));
        LOG.info(String.format("%s PARAMS: [clientDto: %s , companyCode: %s , branchCode: %s ]", idOperation, clientDto.toString(), companyCode, branchCode));
        MethodDto method = (MethodDto) companyServicePort.findMethodByCompanyCodeAndModule(companyCode, module, idOperation).getData();
        if (method == null) {
            throw new ValidationError("method");
        }
        ResponseModel responseModel = clientServicePort.saveClient(clientDto, companyCode, method.getCode(), idOperation, clientOracleServicePort, clientPosLegacyServicePort);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseModel);
    }

    /**
     * EndPoint para la actualización de los datos de un cliente por su id y código
     * de compañía
     *
     * @param clientDto   modelo de datos de la información del cliente
     * @param companyCode compañía de la que se obtendrá la información.
     * @param branchCode  código de sucursal
     * @param id          identificador del cliente
     * @return ResponseModel, objeto que contiene la información del objeto
     * actualizado y el código de respuesta
     */
    @PutMapping("/update/{id}/{companyCode}/{branchCode}")
    public ResponseEntity<ResponseModel> updateClientByIdAndCompanyCode(@RequestBody @Valid ClientDto clientDto, @PathVariable String companyCode, @PathVariable String branchCode, @PathVariable Long id) {

        idOperation = generateIdOperation(companyCode, branchCode, module);

        LOG.info(String.format("%s INIT updateClient()", idOperation));
        LOG.info(String.format("%s PARAMS: [ id: %s , clientDto: %s , companyCode: %s , branchCode: %s ]", idOperation, id.toString(), clientDto.toString(), companyCode, branchCode));

        ResponseModel responseModel = new ResponseModel();

        MethodDto method = (MethodDto) companyServicePort.findMethodByCompanyCodeAndModule(companyCode, module, idOperation).getData();

        if (method != null) {

            clientDto.setId(id);
            responseModel = clientServicePort.updateClient(clientDto, companyCode, method.getCode(), idOperation, clientOracleServicePort, clientPosLegacyServicePort);

        } else {

            throw new ValidationError("method");
        }

        return ResponseEntity.status(HttpStatus.OK).body(responseModel);
    }

    /**
     * EndPoint que retorna el catalogo de direcciones de un cliente por su id y
     * código de compañía
     *
     * @param companyCode compañía de la que se obtendrá la información.
     * @param branchCode  código de sucursal
     * @param id          identificador de cliente
     * @return ResponseModel, objeto que contiene la información del objeto
     * actualizado y el código de respuesta
     */
    @GetMapping("/view/list/shipping-address/{id}/{companyCode}/{branchCode}")
    public ResponseEntity<ResponseModel> getShippingAddressCatalog(@PathVariable String companyCode, @PathVariable String branchCode, @PathVariable Long id) {

        idOperation = generateIdOperation(companyCode, branchCode, module);
        LOG.info(String.format("%s INIT getShippingAddressCatalog()", idOperation));
        LOG.info(String.format("%s PARAMS: [ id: %s , companyCode: %s , branchCode: %s ]", idOperation, id.toString(), companyCode, branchCode));

        ResponseModel responseModel = new ResponseModel();

        MethodDto method = (MethodDto) companyServicePort.findMethodByCompanyCodeAndModule(companyCode, module, idOperation).getData();

        if (method != null) {

            responseModel = clientServicePort.getShippingAddressCatalog(companyCode, method.getCode(), id, idOperation, clientOracleServicePort, clientPosLegacyServicePort);

        } else {

            throw new ValidationError("method");
        }

        return ResponseEntity.status(HttpStatus.OK).body(responseModel);
    }

    /**
     * EndPoint que recupera los datos de una dirección por su id y código de
     * compañía
     *
     * @param companyCode código de compañía
     * @param branchCode  código de sucursal
     * @param id          identificador de la dirección solicitada
     * @return ResponseModel, objeto que contiene la información del objeto
     * actualizado y el código de respuesta
     */
    @GetMapping("/shipping-address/{id}/{companyCode}/{branchCode}")
    public ResponseEntity<ResponseModel> getShippingAddressById(@PathVariable String companyCode, @PathVariable String branchCode, @PathVariable Long id) {

        idOperation = generateIdOperation(companyCode, branchCode, module);
        LOG.info(String.format("%s INIT getShippingAddress()", idOperation));
        LOG.info(String.format("%s PARAMS: [ id: %s , companyCode: %s , branchCode: %s ]", idOperation, id.toString(), companyCode, branchCode));

        ResponseModel responseModel = new ResponseModel();

        MethodDto method = (MethodDto) companyServicePort.findMethodByCompanyCodeAndModule(companyCode, module, idOperation).getData();

        if (method != null) {

            responseModel = clientServicePort.getShippingAddress(companyCode, method.getCode(), id, idOperation, clientOracleServicePort, clientPosLegacyServicePort);

        } else {

            throw new ValidationError("method");
        }

        return ResponseEntity.status(HttpStatus.OK).body(responseModel);
    }

    /**
     * EndPoint para la creación de direcciones por código de compañía e id del
     * cliente
     *
     * @param companyCode        código de compañía
     * @param shippingAddressDto modelo de datos de la dirección
     * @param branchCode         código de sucursal
     * @param id                 identificador del cliente
     * @return ResponseModel, objeto que contiene la información del objeto
     * actualizado y el código de respuesta
     */
    @PostMapping("/create/shipping-address/{id}/{companyCode}/{branchCode}")
    public ResponseEntity<ResponseModel> createShippingAddressById(@RequestBody ShippingAddressDto shippingAddressDto, @PathVariable String companyCode, @PathVariable String branchCode, @PathVariable Long id) {

        idOperation = generateIdOperation(companyCode, branchCode, module);
        LOG.info(String.format("%s INIT getShippingAddressCreate()", idOperation));
        LOG.info(String.format("%s PARAMS: [ shippingAddressDto: %s , id: %s , companyCode: %s , branchCode: %s ]", idOperation, shippingAddressDto.toString(), id.toString(), companyCode, branchCode));

        ResponseModel responseModel = new ResponseModel();

        MethodDto method = (MethodDto) companyServicePort.findMethodByCompanyCodeAndModule(companyCode, module, idOperation).getData();

        if (method != null) {

            responseModel = clientServicePort.getShippingAddressCreate(shippingAddressDto, companyCode, method.getCode(), id, idOperation, clientOracleServicePort, clientPosLegacyServicePort);

        } else {

            throw new ValidationError("method");
        }

        return ResponseEntity.status(HttpStatus.OK).body(responseModel);
    }

    /**
     * EndPoint para la actualización de una dirección por código de compañía e id
     * del cliente
     *
     * @param companyCode        código de compañía
     * @param shippingAddressDto modelo de datos de la dirección
     * @param branchCode         código de sucursal
     * @param id                 identificafor del cliente
     * @return ResponseModel, objeto que contiene la información del objeto
     * actualizado y el código de respuesta
     */
    @PutMapping("/update/shipping-address/{id}/{companyCode}/{branchCode}")
    public ResponseEntity<ResponseModel> updateShippingAddressById(@RequestBody ShippingAddressDto shippingAddressDto, @PathVariable String companyCode, @PathVariable String branchCode, @PathVariable Long id) {

        idOperation = generateIdOperation(companyCode, branchCode, module);
        LOG.info(String.format("%s INIT getShippingAddressUpdate()", idOperation));
        LOG.info(String.format("%s PARAMS: [ shippingAddressDto: %s , id: %s , companyCode: %s , branchCode: %s ]", idOperation, shippingAddressDto.toString(), id.toString(), companyCode, branchCode));

        ResponseModel responseModel = new ResponseModel();

        MethodDto method = (MethodDto) companyServicePort.findMethodByCompanyCodeAndModule(companyCode, module, idOperation).getData();

        if (method != null) {
            responseModel = clientServicePort.getShippingAddressUpdate(shippingAddressDto, companyCode, method.getCode(), id, idOperation, clientOracleServicePort, clientPosLegacyServicePort);

        } else {

            throw new ValidationError("method");
        }

        return ResponseEntity.status(HttpStatus.OK).body(responseModel);
    }

    /**
     * EndPoint que retorna la lista de emails de un cliente por su id
     *
     * @param companyCode código de compañía
     * @param branchCode  código de sucursal
     * @param id          identificador del cliente
     * @return ResponseModel, objeto que contiene el código de resultado de la
     * operación y los datos solicitados
     */
    @GetMapping("/view/all/email/{id}/{companyCode}/{branchCode}")
    public ResponseEntity<ResponseModel> getListEmailsByClientId(@PathVariable String companyCode, @PathVariable String branchCode, @PathVariable Long id) {

        idOperation = generateIdOperation(companyCode, branchCode, module);
        LOG.info(String.format("%s INIT getListEmails()", idOperation));
        LOG.info(String.format("%s PARAMS: [ id: %s , companyCode: %s , branchCode: %s ]", idOperation, id.toString(), companyCode, branchCode));

        ResponseModel responseModel = new ResponseModel();

        MethodDto method = (MethodDto) companyServicePort.findMethodByCompanyCodeAndModule(companyCode, module, idOperation).getData();

        if (method != null) {

            responseModel = clientServicePort.getListEmails(companyCode, method.getCode(), id, idOperation, clientOracleServicePort, clientPosLegacyServicePort);


        } else {

            throw new ValidationError("method");
        }

        return ResponseEntity.status(HttpStatus.OK).body(responseModel);
    }

    /**
     * EndpPoint para la recuperación del clinete universal por código de compañia
     *
     * @param companyCode código de compañía
     * @param branchCode  código de sucursal
     * @return ResponseModel, objeto que contiene el código de resultado de la
     * operación y los datos solicitados
     */
    @GetMapping("/global-client/{companyCode}/{branchCode}")
    public ResponseEntity<?> getGlobalClientByCompanyCode(@PathVariable String companyCode, @PathVariable String branchCode) {

        idOperation = generateIdOperation(companyCode, branchCode, module);
        LOG.info(String.format("%s INIT getGlobalClientByCompanyCode()", idOperation));
        LOG.info(String.format("%s PARAMS: [ companyCode: %s , branchCode: %s ]", idOperation, companyCode, branchCode));

        MethodDto method = (MethodDto) companyServicePort.findMethodByCompanyCodeAndModule(companyCode, module, idOperation).getData();

        if (method == null) {

            LOG.info(String.format("%s ERROR IN ASSIGNATION METHODS OF THE COMPANY %s ", idOperation, companyCode));
            throw new GlobalError();
        }

        ResponseModel responseModel = clientServicePort.recoverClient(companyCode, 1L, 0L, false,
                method.getCode(), idOperation, clientOracleServicePort, clientPosLegacyServicePort);

        return new ResponseEntity<>(responseModel, HttpStatus.OK);
    }

}
