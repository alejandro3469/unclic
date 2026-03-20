package mx.com.endtoend.domain.clients.ports.api;

import mx.com.endtoend.domain.clients.dto.FiltersClientDto;
import mx.com.endtoend.infrastructure.services.jde.clients.common.serviceport.ClientOracleServicePort;
import mx.com.endtoend.infrastructure.services.posLegacy.clients.common.serviceport.ClientLegacyServicePort;
import mx.com.endtoend.smart.bussiness.model.clients.dto.ClientDto;
import mx.com.endtoend.smart.bussiness.model.clients.dto.ShippingAddressDto;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

public interface ClientServicePort {

    ResponseModel getListClient(FiltersClientDto filtersClientDto, String companyCode, String method, String idOperation, ClientOracleServicePort clientOracleServicePort, ClientLegacyServicePort clientLegacyServicePort);

    ResponseModel recoverClient(String companyCode, Long id, Long clientNumber, boolean isExternal, String method,
                                String idOperation, ClientOracleServicePort clientOracleServicePort,
                                ClientLegacyServicePort clientLegacyServicePort);

    ResponseModel saveClient(ClientDto clientDto, String companyCode, String method, String idOperation, ClientOracleServicePort clientOracleServicePort, ClientLegacyServicePort clientLegacyServicePort);

    ResponseModel updateClient(ClientDto clientDto, String companyCode, String method, String idOperation, ClientOracleServicePort clientOracleServicePort, ClientLegacyServicePort clientLegacyServicePort);

    ResponseModel getShippingAddressCatalog(String companyCode, String method, Long id, String idOperation, ClientOracleServicePort clientOracleServicePort, ClientLegacyServicePort clientLegacyServicePort);

    ResponseModel getShippingAddress(String companyCode, String method, Long id, String idOperation, ClientOracleServicePort clientOracleServicePort, ClientLegacyServicePort clientLegacyServicePort);

    ResponseModel getShippingAddressCreate(ShippingAddressDto shippingAddressDto, String companyCode, String method, Long id, String idOperation, ClientOracleServicePort clientOracleServicePort, ClientLegacyServicePort clientLegacyServicePort);

    ResponseModel getShippingAddressUpdate(ShippingAddressDto shippingAddressDto, String companyCode, String method, Long id, String idOperation, ClientOracleServicePort clientOracleServicePort, ClientLegacyServicePort clientLegacyServicePort);

    ResponseModel getListEmails(String companyCode, String method, Long id, String idOperation, ClientOracleServicePort clientOracleServicePort, ClientLegacyServicePort clientPosLegacyServicePort);

}
