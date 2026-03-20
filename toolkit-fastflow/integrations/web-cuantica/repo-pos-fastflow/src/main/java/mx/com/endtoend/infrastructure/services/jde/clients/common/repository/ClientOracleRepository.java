package mx.com.endtoend.infrastructure.services.jde.clients.common.repository;


import mx.com.endtoend.domain.clients.dto.FiltersClientDto;
import mx.com.endtoend.domain.clients.dto.TotalClientsDto;
import mx.com.endtoend.smart.bussiness.model.clients.dto.ClientDto;

public interface ClientOracleRepository {

	TotalClientsDto getClientList(FiltersClientDto filtersClientDto, String idOperation);

	ClientDto getClientByClientNumber(Long clientNumber, String idOperation);

	Long getClientNumber(String idOperation);

	String getClientReferenceNumber(String idOperation);

	boolean createClient(ClientDto clientDto, String tnac, String companyCode, String idOperation);

	boolean updateClient(ClientDto clientDto, String companyCode, String idOperation);

}
