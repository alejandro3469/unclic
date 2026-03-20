package mx.com.endtoend.infrastructure.client.common.repository;

import mx.com.endtoend.domain.clients.dto.FiltersClientDto;
import mx.com.endtoend.smart.bussiness.model.clients.dto.ClientDto;
import mx.com.endtoend.smart.bussiness.model.clients.dto.ShippingAddressDto;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

public interface GenericClientRepository {

	ResponseModel getListClient(FiltersClientDto filtersClientDto, String idOperation);

	ResponseModel recoverClient(Long id, String idOperation);

	ResponseModel getShippingAddressCatalog(Long id, String idOperation);

	ResponseModel getShippingAddress(Long id, String idOperation);

	ResponseModel getShippingAddressCreate(ShippingAddressDto shippingAddressDto, Long id, String idOperation);

	ResponseModel getShippingAddressUpdate(ShippingAddressDto shippingAddressDto, Long id, String idOperation);

	boolean existsClient(ClientDto clientDto, String idOperation);

	boolean existsClientUpdate(ClientDto clientDto, String idOperation);

	ResponseModel createClient(ClientDto clientDto, String idOperation, String companyCode);

	ResponseModel updateClient(ClientDto clientDto, String idOperation);
	
	ResponseModel getListEmails(Long id, String idOperation);

}
