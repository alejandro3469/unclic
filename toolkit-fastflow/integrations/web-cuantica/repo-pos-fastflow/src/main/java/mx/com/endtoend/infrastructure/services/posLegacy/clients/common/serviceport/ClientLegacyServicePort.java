package mx.com.endtoend.infrastructure.services.posLegacy.clients.common.serviceport;

import mx.com.endtoend.smart.bussiness.model.clients.dto.ClientDto;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

public interface ClientLegacyServicePort {
	
	ResponseModel createClient(ClientDto clientDto,  String companyCode, String idOperation);
	
	ResponseModel updateClient(ClientDto clientDto, String companyCode, String idOperation);

}
