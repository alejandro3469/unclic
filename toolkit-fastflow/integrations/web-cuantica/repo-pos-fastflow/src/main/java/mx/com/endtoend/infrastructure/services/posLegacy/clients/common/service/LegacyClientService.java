package mx.com.endtoend.infrastructure.services.posLegacy.clients.common.service;

import mx.com.endtoend.infrastructure.services.posLegacy.clients.common.serviceport.ClientLegacyServicePort;
import mx.com.endtoend.infrastructure.services.posLegacy.clients.common.factory.ClientLegacyFactory;
import mx.com.endtoend.infrastructure.services.posLegacy.clients.common.repository.ClientLegacyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.com.endtoend.smart.bussiness.model.clients.dto.ClientDto;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

@Service
public class LegacyClientService implements ClientLegacyServicePort {
	
	@Autowired
	private ClientLegacyFactory clientLegacyFactory;
	
	private final Logger LOG = LoggerFactory.getLogger(LegacyClientService.class);

	/**
	 * Método que recuepra el repositorio de un clinete por código de compañía para
	 * la creación de un cliente
	 * 
	 * @param clientDto   objeto con la información del clinete a almacenar
	 * @param companyCode código de compañia
	 * 
	 * @return ResponseModel objeto que contiene la información recuperada, en caso
	 *         de ser exitoso, el código de respuesta será con un valor igual a 100
	 */
	@Override
	public ResponseModel createClient(ClientDto clientDto, String companyCode, String idOperation) {
		
		LOG.info(String.format("%s INIT createClient()", idOperation));
		LOG.info(String.format("%s PARAMS: [ clientDto: %s , companyCode: %s] ", idOperation,
				clientDto.toString(), companyCode));
		
		ClientLegacyRepository clientRepository = clientLegacyFactory.getClientRepository(companyCode);
		
		boolean createOk = clientRepository.createClient(clientDto, idOperation);
		
		return new ResponseModel(createOk);
	} 

	/**
	 * Método que recuepra el repositorio de un clinete por código de compañía para
	 * la modificacion de un cliente
	 * 
	 * @param clientDto   objeto con la información del clinete a almacenar
	 * @param companyCode código de compañia
	 * 
	 * @return ResponseModel objeto que contiene la información recuperada, en caso
	 *         de ser exitoso, el código de respuesta será con un valor igual a 100
	 */
	@Override
	public ResponseModel updateClient(ClientDto clientDto, String companyCode, String idOperation) {
		LOG.info(String.format("%s INIT updateClient()", idOperation));
		LOG.info(String.format("%s PARAMS: [ clientDto: %s , companyCode: %s] ", idOperation,
				clientDto.toString(),companyCode));
		
		ClientLegacyRepository clientRepository = clientLegacyFactory.getClientRepository(companyCode);
		
		boolean createOk = clientRepository.updateClient(clientDto, idOperation);
		
		return new ResponseModel(createOk);
		
	}

}
