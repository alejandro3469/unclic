package mx.com.endtoend.infrastructure.services.posLegacy.clients.common.repository;

import mx.com.endtoend.smart.bussiness.model.clients.dto.ClientDto;

public interface ClientLegacyRepository {
	
	boolean createClient(ClientDto clientDto, String idOperation);
	
	boolean updateClient(ClientDto clientDto, String idOperation);

}
