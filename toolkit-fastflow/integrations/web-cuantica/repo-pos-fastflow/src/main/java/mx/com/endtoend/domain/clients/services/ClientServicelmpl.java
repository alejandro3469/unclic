package mx.com.endtoend.domain.clients.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.com.endtoend.domain.clients.dto.FiltersClientDto;
import mx.com.endtoend.domain.clients.factory.ClientFactory;
import mx.com.endtoend.domain.clients.factory.ClientInterface;
import mx.com.endtoend.domain.clients.ports.api.ClientServicePort;
import mx.com.endtoend.domain.clients.ports.spi.ClientPersistencePort;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.GlobalError;
import mx.com.endtoend.infrastructure.services.jde.clients.common.serviceport.ClientOracleServicePort;
import mx.com.endtoend.infrastructure.services.posLegacy.clients.common.serviceport.ClientLegacyServicePort;
import mx.com.endtoend.smart.bussiness.model.clients.dto.ClientDto;
import mx.com.endtoend.smart.bussiness.model.clients.dto.ShippingAddressDto;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

public class ClientServicelmpl implements ClientServicePort {

    private final static Logger LOG = LoggerFactory.getLogger(ClientServicelmpl.class);
    public ClientFactory factory = new ClientFactory();
    private ClientPersistencePort clientPersistencePort;

    public ClientServicelmpl(ClientPersistencePort clientPersistencePort) {
        this.clientPersistencePort = clientPersistencePort;
    }

    @Override
    public ResponseModel getListClient(FiltersClientDto filtersClientDto, String companyCode, String method,
                                       String idOperation, ClientOracleServicePort clientOracleServicePort,
                                       ClientLegacyServicePort clientPosLegacyServicePort) {

        LOG.info(String.format("%s INIT getListClient()", idOperation));
        LOG.info(String.format("%s PARAMS: [filtersClientDto: %s , companyCode: %s , method: %s ]", idOperation,
                filtersClientDto.toString(), companyCode, method));

        ClientInterface clientInterface = factory.createFactory(method, clientOracleServicePort,
                clientPosLegacyServicePort);

        if (clientInterface == null) {
            LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
            throw new GlobalError();
        }

        ResponseModel responseModel = clientInterface.getListClient(filtersClientDto, companyCode,
                clientPersistencePort, idOperation);

        return responseModel;
    }

    @Override
    public ResponseModel recoverClient(String companyCode, Long id, Long clientNumber, boolean isExternal, String method,
                                       String idOperation, ClientOracleServicePort clientOracleServicePort,
                                       ClientLegacyServicePort clientPosLegacyServicePort) {
        LOG.info(String.format("%s INIT recoverClient()", idOperation));
        LOG.info(String.format("%s PARAMS: [ companyCode: %s , id: %s , method: %s ]", idOperation, companyCode,
                id.toString(), method));
        ClientInterface clientInterface = factory.createFactory(method, clientOracleServicePort,
                clientPosLegacyServicePort);
        if (clientInterface == null) {
            LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
            throw new GlobalError();
        }
        ResponseModel responseModel = clientInterface.recoverClient(companyCode, id, clientNumber, isExternal,
                clientPersistencePort, idOperation);
        return responseModel;
    }

    @Override
    public ResponseModel saveClient(ClientDto clientDto, String companyCode, String method, String idOperation,
                                    ClientOracleServicePort clientOracleServicePort, ClientLegacyServicePort clientPosLegacyServicePort) {
        LOG.info(String.format("%s INIT saveClient()", idOperation));
        LOG.info(String.format("%s PARAMS: [ clientDto: %s , companyCode: %s , method: %s ]", idOperation,
                clientDto.toString(), companyCode, method));

        ClientInterface clientInterface = factory.createFactory(method, clientOracleServicePort,
                clientPosLegacyServicePort);

        if (clientInterface == null) {
            LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
            throw new GlobalError();
        }

        ResponseModel responseModel = clientInterface.createClient(clientDto, companyCode, clientPersistencePort,
                idOperation);

        return responseModel;
    }

    @Override
    public ResponseModel updateClient(ClientDto clientDto, String companyCode, String method, String idOperation,
                                      ClientOracleServicePort clientOracleServicePort, ClientLegacyServicePort clientPosLegacyServicePort) {

        LOG.info(String.format("%s INIT updateClient()", idOperation));
        LOG.info(String.format("%s PARAMS: [ clientDto: %s , companyCode: %s , method: %s ]", idOperation,
                clientDto.toString(), companyCode, method));

        ClientInterface clientInterface = factory.createFactory(method, clientOracleServicePort,
                clientPosLegacyServicePort);

        if (clientInterface == null) {
            LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
            throw new GlobalError();
        }

        ResponseModel responseModel = clientInterface.updateClient(clientDto, companyCode, clientPersistencePort,
                idOperation);

        return responseModel;
    }

    @Override
    public ResponseModel getShippingAddressCatalog(String companyCode, String method, Long id, String idOperation,
                                                   ClientOracleServicePort clientOracleServicePort, ClientLegacyServicePort clientPosLegacyServicePort) {

        LOG.info(String.format("%s INIT getShippingAddressCatalog()", idOperation));
        LOG.info(String.format("%s PARAMS: [ id: %s , companyCode: %s , method: %s ]", idOperation, id.toString(),
                companyCode, method));

        ClientInterface clientInterface = factory.createFactory(method, clientOracleServicePort,
                clientPosLegacyServicePort);

        if (clientInterface == null) {
            LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
            throw new GlobalError();
        }

        ResponseModel responseModel = clientInterface.getShippingAddressCatalog(companyCode, clientPersistencePort, id,
                idOperation);

        return responseModel;
    }

    @Override
    public ResponseModel getShippingAddress(String companyCode, String method, Long id, String idOperation,
                                            ClientOracleServicePort clientOracleServicePort, ClientLegacyServicePort clientPosLegacyServicePort) {
        LOG.info(String.format("%s INIT getShippingAddress()", idOperation));
        LOG.info(String.format("%s PARAMS: [ id: %s , companyCode: %s , method: %s ]", idOperation, id.toString(),
                companyCode, method));

        ClientInterface clientInterface = factory.createFactory(method, clientOracleServicePort,
                clientPosLegacyServicePort);

        if (clientInterface == null) {
            LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
            throw new GlobalError();
        }

        ResponseModel responseModel = clientInterface.getShippingAddress(companyCode, clientPersistencePort, id,
                idOperation);

        return responseModel;
    }

    @Override
    public ResponseModel getShippingAddressCreate(ShippingAddressDto shippingAddressDto, String companyCode,
                                                  String method, Long id, String idOperation, ClientOracleServicePort clientOracleServicePort,
                                                  ClientLegacyServicePort clientPosLegacyServicePort) {

        LOG.info(String.format("%s INIT getShippingAddressCreate()", idOperation));
        LOG.info(String.format("%s PARAMS: [ shippingAddressDto: %s , id: %s , companyCode: %s , method: %s ]",
                idOperation, shippingAddressDto.toString(), id.toString(), companyCode, method));

        ClientInterface clientInterface = factory.createFactory(method, clientOracleServicePort,
                clientPosLegacyServicePort);

        if (clientInterface == null) {
            LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
            throw new GlobalError();
        }

        ResponseModel responseModel = clientInterface.getShippingAddressCreate(shippingAddressDto, companyCode,
                clientPersistencePort, id, idOperation);

        return responseModel;
    }

    @Override
    public ResponseModel getShippingAddressUpdate(ShippingAddressDto shippingAddressDto, String companyCode,
                                                  String method, Long id, String idOperation, ClientOracleServicePort clientOracleServicePort,
                                                  ClientLegacyServicePort clientPosLegacyServicePort) {

        LOG.info(String.format("%s INIT getShippingAddressUpdate()", idOperation));
        LOG.info(String.format("%s PARAMS: [ shippingAddressDto: %s , id: %s , companyCode: %s , method: %s ]",
                idOperation, shippingAddressDto.toString(), id.toString(), companyCode, method));

        ClientInterface clientInterface = factory.createFactory(method, clientOracleServicePort,
                clientPosLegacyServicePort);

        if (clientInterface == null) {
            LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
            throw new GlobalError();
        }

        ResponseModel responseModel = clientInterface.getShippingAddressUpdate(shippingAddressDto, companyCode,
                clientPersistencePort, id, idOperation);

        return responseModel;
    }

    @Override
    public ResponseModel getListEmails(String companyCode, String method, Long id, String idOperation,
                                       ClientOracleServicePort clientOracleServicePort, ClientLegacyServicePort clientPosLegacyServicePort) {

        LOG.info(String.format("%s INIT getListEmails()", idOperation));
        LOG.info(String.format("%s PARAMS: [ id: %s , companyCode: %s , method: %s ]", idOperation, id.toString(),
                companyCode, method));

        ClientInterface clientInterface = factory.createFactory(method, clientOracleServicePort,
                clientPosLegacyServicePort);

        if (clientInterface == null) {
            LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
            throw new GlobalError();
        }

        ResponseModel responseModel = clientInterface.getListEmails(companyCode, clientPersistencePort, id,
                idOperation);

        return responseModel;
    }

}
