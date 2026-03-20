package mx.com.endtoend.domain.clients.ports.spi;

import mx.com.endtoend.domain.clients.dto.FiltersClientDto;
import mx.com.endtoend.smart.bussiness.model.clients.dto.ClientDto;
import mx.com.endtoend.smart.bussiness.model.clients.dto.ShippingAddressDto;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

public interface ClientPersistencePort {

	ResponseModel getListClient(FiltersClientDto filtersClientDto, String companyCode, String method,
			String idOperation);

	ResponseModel recoverClient(String companyCode, Long id, String idOperation);

	boolean existsClient(ClientDto clientDto, String companyCode, String method, String idOperation);

	boolean existsClientUpdate(ClientDto clientDto, String companyCode, String method, String idOperation);

	ResponseModel createClient(ClientDto clientDto, String companyCode, String idOperation);

	ResponseModel updateClient(ClientDto clientDto, String companyCode, String idOperation);

	ResponseModel getShippingAddressCatalog(String companyCode, String method, Long id, String idOperation);

	ResponseModel getShippingAddress(String companyCode, String method, Long id, String idOperation);

	ResponseModel getShippingAddressCreate(ShippingAddressDto shippingAddressDto, String companyCode, String method,
			Long id, String idOperation);

	ResponseModel getShippingAddressUpdate(ShippingAddressDto shippingAddressDto, String companyCode, String method,
			Long id, String idOperation);
	
	ResponseModel getListEmails(String companyCode,String method, Long id, String idOperation);

	// VALIDACIONES

}
