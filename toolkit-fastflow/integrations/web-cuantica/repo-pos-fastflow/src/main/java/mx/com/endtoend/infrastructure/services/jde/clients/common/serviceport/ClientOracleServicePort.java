package mx.com.endtoend.infrastructure.services.jde.clients.common.serviceport;

import mx.com.endtoend.domain.clients.dto.FiltersClientDto;
import mx.com.endtoend.smart.bussiness.model.clients.dto.ClientDto;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

public interface ClientOracleServicePort {

	ResponseModel getListClientOracle(FiltersClientDto filtersClientDto, String companyCode, String idOperation);

	ResponseModel getClientByClientNumber(Long clientNumber, String companyCode, String idOperation);

	ResponseModel getClientNumber(String companyCode, String idOperation);
	
	ResponseModel getClientReferenceNumber(String companyCode, String idOperation);
	
	ResponseModel createClient(ClientDto clientDto,  String companyCode, String tnac, String idOperation);
	
	ResponseModel updateClient(ClientDto clientDto, String companyCode, String idOperation);
}
