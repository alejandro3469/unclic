package mx.com.endtoend.domain.clients.factory;

import mx.com.endtoend.domain.clients.dto.FiltersClientDto;
import mx.com.endtoend.domain.clients.ports.spi.ClientPersistencePort;
import mx.com.endtoend.smart.bussiness.model.clients.dto.ClientDto;
import mx.com.endtoend.smart.bussiness.model.clients.dto.ShippingAddressDto;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

public interface ClientInterface {
	
	ResponseModel getListClient(FiltersClientDto filtersClientDto,String companyCode,ClientPersistencePort clientPersistencePort, String idOperation);
	
	ResponseModel recoverClient(String companyCode,Long id, Long clientNumber, boolean isExternal,
								ClientPersistencePort clientPersistencePort,String idOperation);
	
	ResponseModel createClient(ClientDto clientDto, String companyCode, ClientPersistencePort clientPersistencePort,String idOperation);
	
	ResponseModel updateClient(ClientDto clientDto, String companyCode, ClientPersistencePort clientPersistencePort,String idOperation);
	
	ResponseModel getShippingAddressCatalog(String companyCode,ClientPersistencePort clientPersistencePort, Long id,String idOperation);
	
	ResponseModel getShippingAddress(String companyCode,ClientPersistencePort clientPersistencePort, Long id,String idOperation);
	
	ResponseModel getShippingAddressCreate(ShippingAddressDto shippingAddressDto, String companyCode,ClientPersistencePort clientPersistencePort, Long id, String idOperation);
	
	ResponseModel getShippingAddressUpdate(ShippingAddressDto shippingAddressDto,String companyCode,ClientPersistencePort clientPersistencePort, Long id, String idOperation);
	
	ResponseModel getListEmails(String companyCode,ClientPersistencePort clientPersistencePort, Long id, String idOperation);
}
