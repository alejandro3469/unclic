package mx.com.endtoend.infrastructure.client.common.adapter;

import mx.com.endtoend.infrastructure.client.common.factory.ClientRepositoryFactory;
import mx.com.endtoend.infrastructure.client.common.repository.GenericClientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import mx.com.endtoend.domain.clients.dto.FiltersClientDto;
import mx.com.endtoend.domain.clients.ports.spi.ClientPersistencePort;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.GlobalError;
import mx.com.endtoend.smart.bussiness.model.clients.dto.ClientDto;
import mx.com.endtoend.smart.bussiness.model.clients.dto.ShippingAddressDto;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;


public class ClientJpaAdapte implements ClientPersistencePort {

    private final Logger LOG = LoggerFactory.getLogger(ClientJpaAdapte.class);
    @Autowired
    private ClientRepositoryFactory clientRepositoryFactory;

    @Override
    public ResponseModel getListClient(FiltersClientDto filtersClientDto, String companyCode, String method,
                                       String idOperation) {
        LOG.info(String.format("%s INIT getListClient() ", idOperation));
        LOG.info(String.format("%s PARAMS: [ filtersClientDto: %s , companyCode: %s ] ", idOperation,
                filtersClientDto.toString(), companyCode));
        GenericClientRepository repository = clientRepositoryFactory.returnRepository(companyCode);
        if (repository == null) {
            LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
            throw new GlobalError();
        }
        ResponseModel responseModel = repository.getListClient(filtersClientDto, idOperation);
        return responseModel;
    }

    @Override
    public ResponseModel recoverClient(String companyCode, Long id, String idOperation) {

        LOG.info(String.format("%s INIT recoverClient() ", idOperation));
        LOG.info(String.format("%s PARAMS: [ companyCode: %s , id: %s ] ", idOperation, companyCode, id.toString()));

        GenericClientRepository repository = clientRepositoryFactory.returnRepository(companyCode);

        if (repository == null) {

            LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
            throw new GlobalError();
        }

        ResponseModel responseModel = repository.recoverClient(id, idOperation);

        return responseModel;

    }

    @Override
    public boolean existsClient(ClientDto clientDto, String companyCode, String method, String idOperation) {

        LOG.info(String.format("%s INIT existsClient() ", idOperation));
        LOG.info(String.format("%s PARAMS: [ clientDto: %s , companyCode: %s ] ", idOperation, clientDto.toString(),
                companyCode));

        GenericClientRepository repository = clientRepositoryFactory.returnRepository(companyCode);

        if (repository == null) {

            LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
            throw new GlobalError();
        }

        boolean exists = repository.existsClient(clientDto, idOperation);

        return exists;

    }

    @Override
    public boolean existsClientUpdate(ClientDto clientDto, String companyCode, String method, String idOperation) {

        LOG.info(String.format("%s INIT existsClientUpdate() ", idOperation));
        LOG.info(String.format("%s PARAMS: [ clientDto: %s , companyCode: %s ] ", idOperation, clientDto.toString(),
                companyCode));

        GenericClientRepository repository = clientRepositoryFactory.returnRepository(companyCode);

        if (repository == null) {

            LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
            throw new GlobalError();
        }

        boolean exists = repository.existsClientUpdate(clientDto, idOperation);

        return exists;

    }

    @Override
    public ResponseModel createClient(ClientDto clientDto, String companyCode, String idOperation) {

        LOG.info(String.format("%s INIT createClient() ", idOperation));
        LOG.info(String.format("%s PARAMS: [ clientDto: %s , companyCode: %s ] ", idOperation, clientDto.toString(),
                companyCode));

        GenericClientRepository repository = clientRepositoryFactory.returnRepository(companyCode);

        if (repository == null) {

            LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
            throw new GlobalError();
        }

        ResponseModel responseModel = repository.createClient(clientDto, idOperation, companyCode);

        return responseModel;

    }

    @Override
    public ResponseModel updateClient(ClientDto clientDto, String companyCode, String idOperation) {

        LOG.info(String.format("%s INIT updateClient() ", idOperation));
        LOG.info(String.format("%s PARAMS: [ clientDto: %s , companyCode: %s ] ", idOperation, clientDto.toString(),
                companyCode));

        GenericClientRepository repository = clientRepositoryFactory.returnRepository(companyCode);

        if (repository == null) {

            LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
            throw new GlobalError();
        }

        ResponseModel responseModel = repository.updateClient(clientDto, idOperation);

        return responseModel;

    }

    @Override
    public ResponseModel getShippingAddressCatalog(String companyCode, String method, Long id, String idOperation) {

        LOG.info(String.format("%s INIT getShippingAddressCatalog() ", idOperation));
        LOG.info(String.format("%s PARAMS: [ companyCode: %s , id: %s ] ", idOperation, companyCode, id.toString()));

        GenericClientRepository repository = clientRepositoryFactory.returnRepository(companyCode);

        if (repository == null) {

            LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
            throw new GlobalError();
        }

        ResponseModel responseModel = repository.getShippingAddressCatalog(id, idOperation);

        return responseModel;

    }

    @Override
    public ResponseModel getShippingAddress(String companyCode, String method, Long id, String idOperation) {

        LOG.info(String.format("%s INIT getShippingAddress() ", idOperation));
        LOG.info(String.format("%s PARAMS: [ companyCode: %s , id: %s ] ", idOperation, companyCode, id.toString()));

        GenericClientRepository repository = clientRepositoryFactory.returnRepository(companyCode);

        if (repository == null) {

            LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
            throw new GlobalError();
        }

        ResponseModel responseModel = repository.getShippingAddress(id, idOperation);

        return responseModel;

    }

    @Override
    public ResponseModel getShippingAddressCreate(ShippingAddressDto shippingAddressDto, String companyCode,
                                                  String method, Long id, String idOperation) {

        LOG.info(String.format("%s INIT getShippingAddressCreate() ", idOperation));
        LOG.info(String.format("%s PARAMS: [ shippingAddressDto: %s , companyCode: %s , id: %s ] ", idOperation,
                shippingAddressDto.toString(), companyCode, id.toString()));

        GenericClientRepository repository = clientRepositoryFactory.returnRepository(companyCode);

        if (repository == null) {

            LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
            throw new GlobalError();
        }

        ResponseModel responseModel = repository.getShippingAddressCreate(shippingAddressDto, id, idOperation);

        return responseModel;

    }

    @Override
    public ResponseModel getShippingAddressUpdate(ShippingAddressDto shippingAddressDto, String companyCode,
                                                  String method, Long id, String idOperation) {

        LOG.info(String.format("%s INIT getShippingAddressUpdate() ", idOperation));
        LOG.info(String.format("%s PARAMS: [ shippingAddressDto: %s , companyCode: %s , id: %s ] ", idOperation,
                shippingAddressDto.toString(), companyCode, id.toString()));

        GenericClientRepository repository = clientRepositoryFactory.returnRepository(companyCode);

        if (repository == null) {

            LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
            throw new GlobalError();
        }

        ResponseModel responseModel = repository.getShippingAddressUpdate(shippingAddressDto, id, idOperation);

        return responseModel;

    }

    @Override
    public ResponseModel getListEmails(String companyCode, String method, Long id, String idOperation) {

        LOG.info(String.format("%s INIT getListEmails() ", idOperation));
        LOG.info(String.format("%s PARAMS: [ companyCode: %s , id: %s ] ", idOperation, companyCode, id.toString()));

        GenericClientRepository repository = clientRepositoryFactory.returnRepository(companyCode);

        if (repository == null) {

            LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
            throw new GlobalError();
        }

        ResponseModel responseModel = repository.getListEmails(id, idOperation);

        return responseModel;

    }

}
