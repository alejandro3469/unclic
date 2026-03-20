package mx.com.endtoend.domain.clients.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.com.endtoend.domain.clients.dto.ClientIdDto;
import mx.com.endtoend.domain.clients.dto.FiltersClientDto;
import mx.com.endtoend.domain.clients.dto.TotalClientsDto;
import mx.com.endtoend.domain.clients.factory.validations.GenericValidationToClient;
import mx.com.endtoend.domain.clients.ports.spi.ClientPersistencePort;
import mx.com.endtoend.domain.commons.constants.GenericIdentifyMethods;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.SemiFullFunction;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.ValidationError;
import mx.com.endtoend.infrastructure.services.jde.clients.common.serviceport.ClientOracleServicePort;
import mx.com.endtoend.infrastructure.services.posLegacy.clients.common.serviceport.ClientLegacyServicePort;
import mx.com.endtoend.smart.bussiness.model.clients.dto.ClientDto;
import mx.com.endtoend.smart.bussiness.model.clients.dto.ShippingAddressDto;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

public class ClientMethodTwo implements ClientInterface {

	private final static Logger LOG = LoggerFactory.getLogger(ClientMethodTwo.class);
	private final ClientLegacyServicePort clientPosLegacyServicePort;
	private final ClientOracleServicePort clientOracleServicePort;
	private final GenericIdentifyMethods method = GenericIdentifyMethods.CLI_CONF_TWO;
	public GenericValidationToClient genericValidationToClient = new GenericValidationToClient();

	public ClientMethodTwo(ClientLegacyServicePort clientPosLegacyServicePort,
			ClientOracleServicePort clientOracleServicePort) {
		this.clientPosLegacyServicePort = clientPosLegacyServicePort;
		this.clientOracleServicePort = clientOracleServicePort;
	}

	/**
	 * Método que recupera la lsta de clientes del sistema principal. En caso de no
	 * tener registros con los parámetros de entrada, realizará búsqueda en el
	 * sistema JDE y en caso de recuperar algún registro, los insertara en la base
	 * principal.
	 *
	 * @param filtersClientDto      objeto con parámetros de búsqueda
	 * @param companyCode           código de compañía
	 * @param clientPersistencePort repositorio principal del sistema
	 * @param idOperation           identificador de traza
	 * @return ResponseModel objeto con el código de resultado de la operación, en
	 *         caso exitoso contiene embebida la información recuperada
	 */
	@Override
	public ResponseModel getListClient(FiltersClientDto filtersClientDto, String companyCode,
			ClientPersistencePort clientPersistencePort, String idOperation) {
		LOG.info(String.format("%s INIT getListClient()", idOperation));
		LOG.info(String.format("%s PARAMS: [filtersClientDto: %s , companyCode: %s ]", idOperation,
				filtersClientDto.toString(), companyCode));
		TotalClientsDto totalClientsDto;
		String validInputParams = genericValidationToClient.vaidInputParamsToSerchClient(filtersClientDto, idOperation);
		if (!validInputParams.isEmpty()) {
			LOG.warn(String.format("%s EMPTY PARAMS TO SEARCH CLIENTS", idOperation));
			throw new ValidationError(validInputParams);
		}
		ResponseModel responseModel = clientPersistencePort.getListClient(filtersClientDto, companyCode,
				method.toString(), idOperation);
		totalClientsDto = (TotalClientsDto) responseModel.getData();

		if (totalClientsDto.getTotal() == 0) {
			LOG.info(String.format("%s FIND CLIENTS USING JDE SERVICE", idOperation));
			totalClientsDto = (TotalClientsDto) clientOracleServicePort
					.getListClientOracle(filtersClientDto, companyCode, idOperation).getData();
		}
		return new ResponseModel(totalClientsDto);
	}

	/**
	 * Método que recupera el detalle de un clinete por id y código de compañía.
	 *
	 * @param companyCode           código de compañía
	 * @param id                    identificador del cliente
	 * @param clientPersistencePort repositorio del sistema principal
	 * @param idOperation           identificador de traza
	 * @return ResponseModel objeto con el código de resultado de la operación, en
	 *         caso exitoso contiene embebida la información recuperada
	 */
	@Override
	public ResponseModel recoverClient(String companyCode, Long id, Long clientNumber, boolean isExternal,
			ClientPersistencePort clientPersistencePort, String idOperation) {
		LOG.info(String.format("%s INIT recoverClient()", idOperation));
		LOG.info(String.format("%s PARAMS: [id: %s , clientNumber: %d , isExternal: %b , companyCode: %s ]",
				idOperation, id.toString(), clientNumber, isExternal, companyCode));
		ResponseModel responseModel;
		if (!isExternal) {
			LOG.info(String.format("%s SEARCH LOCAL CLIENT", idOperation));
			responseModel = clientPersistencePort.recoverClient(companyCode, id, idOperation);
		} else {
			LOG.info(String.format("%s SEARCH EXTERNAL CLIENT", idOperation));
			responseModel = clientOracleServicePort.getClientByClientNumber(clientNumber, companyCode, idOperation);
			ClientIdDto clientIdDto = clientDtoToClientIdDto((ClientDto) responseModel.getData());
			responseModel = new ResponseModel(clientIdDto);
		}
		LOG.info(String.format("%s RETURN DATA", idOperation));
		return responseModel;
	}

	/**
	 * Método que crea el cliente.
	 *
	 * @param companyCode           código de compañía
	 * @param clientDto             objeto con parámetros del cliente
	 * @param clientPersistencePort repositorio del sistema principal
	 * @param idOperation           identificador de traza
	 * @return ResponseModel objeto con el código de resultado de la operación, en
	 *         caso exitoso contiene embebida la información recuperada
	 */
	@Override
	public ResponseModel createClient(ClientDto clientDto, String companyCode,
			ClientPersistencePort clientPersistencePort, String idOperation) {
		LOG.info(String.format("%s INIT createClient() ", idOperation));
		LOG.info(String.format("%s PARAMS: [clientDto: %s , companyCode: %s ]", idOperation, clientDto.toString(),
				companyCode));
		boolean isExternal = clientDto.getNoClient() == null ? false : true;
		boolean createOk = false;
		ResponseModel responseModel = new ResponseModel();
		boolean existsClient = clientPersistencePort.existsClient(clientDto, companyCode, method.toString(),
				idOperation);
		if (!clientDto.getRfc().equals("XAXX010101000") && existsClient) {
			LOG.warn(String.format("%s CLIENT ALREADY EXISTS", idOperation));
			throw new ValidationError("CLIENT ALREADY EXISTS");
		}
		if (clientDto.getNoClient() == null) {
			LOG.info(String.format("%s GET CLIENT NUMBER FROM JDE SERVICE", idOperation));
			Long clineteNumber = (Long) clientOracleServicePort.getClientNumber(companyCode, idOperation).getData();
			clientDto.setNoClient(clineteNumber);
		}
		if (clientDto.getNnn001() == null) {
			LOG.info(String.format("%s GET CLIENTE REFERENCE NUMBER FROM JDE SERVICE", idOperation));
			String clientReferenceNumber = (String) clientOracleServicePort
					.getClientReferenceNumber(companyCode, idOperation).getData();
			clientDto.setNnn001(clientReferenceNumber);
		}
		clientDto.setIva("16");
		LOG.info(String.format("%s SEND CLIENT TO SAVE", idOperation));
		responseModel = clientPersistencePort.createClient(clientDto, companyCode, idOperation);
		LOG.info("SE CREA EL CLIENTE LOCAL: " + clientDto.getName() + clientDto.getFatherSurname()
				+ clientDto.getMotherSurname() + clientDto.getBusinessName());

		if (isExternal) {
			createOk = (boolean) clientOracleServicePort.updateClient(clientDto, companyCode, idOperation).getData();
		} else {
			LOG.info(String.format("%s CALL JDE SERVICE TO SAVE NEW CLIENT", idOperation));
			createOk = (boolean) clientOracleServicePort.createClient(clientDto, companyCode, "A", idOperation)
					.getData();
		}

		LOG.info(String.format("%s CALL LEGACY SERVICE TO SAVE NEW CLIENT", idOperation));
		boolean createlegacyOk = (boolean) clientPosLegacyServicePort.createClient(clientDto, companyCode, idOperation)
				.getData();
		if (!createOk) {
			LOG.warn(String.format("%s ERROR IN SERVICE JDE", idOperation));
			throw new SemiFullFunction("ERROR-IN-SERVICE-JDE", responseModel.getData());
		} else if (!createlegacyOk) {
			LOG.warn(String.format("%s ERROR IN SERVICE LEGACY", idOperation));
			throw new SemiFullFunction("ERROR-IN-SERVICE-LEGACY", responseModel.getData());
		} else if (!createOk && !createlegacyOk) {
			LOG.warn(String.format("%s ERROR IN SERVICE LEGACY AND SERVICE JDE", idOperation));
			throw new SemiFullFunction("ERROR-IN-SERVICE-LEGACY-AND-SERVICE-JDE", responseModel.getData());
		}
		return responseModel;
	}

	/**
	 * Método que modifica el cliente.
	 *
	 * @param companyCode           código de compañía
	 * @param clientDto             objeto con parámetros del cliente
	 * @param clientPersistencePort repositorio del sistema principal
	 * @param idOperation           identificador de traza
	 * @return ResponseModel objeto con el código de resultado de la operación, en
	 *         caso exitoso contiene embebida la información recuperada
	 */
	@Override
	public ResponseModel updateClient(ClientDto clientDto, String companyCode,
			ClientPersistencePort clientPersistencePort, String idOperation) {

		LOG.info(String.format("%s UPDATE updateClient() ", idOperation));
		LOG.info(String.format("%s PARAMS: [clientDto: %s , companyCode: %s ]", idOperation, clientDto.toString(),
				companyCode));
		ResponseModel responseModel = new ResponseModel();
		boolean existsClient = clientPersistencePort.existsClientUpdate(clientDto, companyCode, method.toString(),
				idOperation);
		if (!clientDto.getRfc().equals("XAXX010101000") && existsClient) {
			LOG.warn(String.format("%s CLIENT ALREADY EXISTS", idOperation));
			throw new ValidationError("CLIENT ALREADY EXISTS");
		}
		if (clientDto.getNnn001() == null || clientDto.getNnn001().isEmpty()) {
			LOG.info(String.format("%s GET CLIENTE REFERENCE NUMBER FROM JDE SERVICE", idOperation));
			String clientReferenceNumber = (String) clientOracleServicePort
					.getClientReferenceNumber(companyCode, idOperation).getData();
			clientDto.setNnn001(clientReferenceNumber);
		}
		LOG.info(String.format("%s SEND CLIENTE TO UPDATE", idOperation));
		responseModel = clientPersistencePort.updateClient(clientDto, companyCode, idOperation);
		LOG.info("SE MODIFICA EL CLIENTE LOCAL: " + clientDto.getName() + clientDto.getFatherSurname()
				+ clientDto.getMotherSurname() + clientDto.getBusinessName());

		LOG.info(String.format("%s CALL JDE SERVICE TO UPDATE NEW CLIENT: %s ", idOperation,
				clientDto.getNoClient().toString()));
		boolean createOk = (boolean) clientOracleServicePort.updateClient(clientDto, companyCode, idOperation)
				.getData();

		LOG.info(String.format("%s CALL LEGACY SERVICE TO UPDATE NEW CLIENT", idOperation));
		boolean createlegacyOk = (boolean) clientPosLegacyServicePort.updateClient(clientDto, companyCode, idOperation)
				.getData();

		if (!createOk) {
			LOG.warn(String.format("%s ERROR IN SERVICE JDE", idOperation));
			throw new SemiFullFunction("ERROR-IN-SERVICE-JDE", responseModel.getData());
		} else if (!createlegacyOk) {
			LOG.warn(String.format("%s ERROR IN SERVICE LEGACY", idOperation));
			throw new SemiFullFunction("ERROR-IN-SERVICE-LEGACY", responseModel.getData());
		} else if (!createOk && !createlegacyOk) {
			LOG.warn(String.format("%s ERROR IN SERVICE LEGACY AND SERVICE JDE", idOperation));
			throw new SemiFullFunction("ERROR-IN-SERVICE-LEGACY-AND-SERVICE-JDE", responseModel.getData());
		}
		return responseModel;
	}

	/**
	 * Método que recupera las direcciones de envio con información genérica por id
	 * de cliente y código de compañía.
	 *
	 * @param companyCode           código de la compañia
	 * @param clientPersistencePort repositorio del sistema principal
	 * @param id                    identificador de cliente
	 * @param idOperation           identificador de traza
	 * @return ResponseModel objeto con el código de resultado de la operación, en
	 *         caso exitoso contiene embebida la información recuperada
	 */
	@Override
	public ResponseModel getShippingAddressCatalog(String companyCode, ClientPersistencePort clientPersistencePort,
			Long id, String idOperation) {
		LOG.info(String.format("%s INIT getShippingAddressCatalog()", idOperation));
		LOG.info(String.format("%s PARAMS: [id: %s , companyCode: %s ]", idOperation, id.toString(), companyCode));
		ResponseModel responseModel = clientPersistencePort.getShippingAddressCatalog(companyCode, method.toString(),
				id, idOperation);
		LOG.info(String.format("%s RETURN DATA", idOperation));
		return responseModel;
	}

	/**
	 * Método que recupera el detalle de la dirección de envío por id y código de
	 * compañía.
	 *
	 * @param companyCode           código de compañía
	 * @param clientPersistencePort repositorio del sistema principal
	 * @param id                    identificador de la dirección de envío
	 * @param idOperation           identaficador de traza
	 * @return ResponseModel objeto con el código de resultado de la operación, en
	 *         caso exitoso contiene embebida la información recuperada
	 */

	@Override
	public ResponseModel getShippingAddress(String companyCode, ClientPersistencePort clientPersistencePort, Long id,
			String idOperation) {
		LOG.info(String.format("%s INIT getShippingAddress()", idOperation));
		LOG.info(String.format("%s PARAMS: [id: %s , companyCode: %s ]", idOperation, id.toString(), companyCode));
		ResponseModel responseModel = clientPersistencePort.getShippingAddress(companyCode, method.toString(), id,
				idOperation);
		LOG.info(String.format("%s RETURN DATA", idOperation));
		return responseModel;
	}

	/**
	 * Método que almacena en el sistema principal una dirección de envío a un
	 * cliente por id.
	 *
	 * @param shippingAddressDto    objeto con la información de dirección de envío
	 * @param companyCode           código de compañía
	 * @param clientPersistencePort repositorio del sistema principal
	 * @param id                    identificador del cliente
	 * @param idOperation           identificador de traza
	 * @return ResponseModel objeto con el código de resultado de la operación, en
	 *         caso exitoso contiene embebida la información recuperada
	 */
	@Override
	public ResponseModel getShippingAddressCreate(ShippingAddressDto shippingAddressDto, String companyCode,
			ClientPersistencePort clientPersistencePort, Long id, String idOperation) {
		LOG.info(String.format("%s INIT getShippingAddressCreate()", idOperation));
		LOG.info(String.format("%s PARAMS: [ shippingAddressDto: %s , id: %s , companyCode: %s ]", idOperation,
				shippingAddressDto.toString(), id.toString(), companyCode));
		ResponseModel responseModel = clientPersistencePort.getShippingAddressCreate(shippingAddressDto, companyCode,
				method.toString(), id, idOperation);
		LOG.info(String.format("%s RETURN DATA", idOperation));
		return responseModel;
	}

	/**
	 * Método que actualiza una dirección de envio en el sistema principal por id de
	 * la dirección de envío y codigo de compañía.
	 *
	 * @param shippingAddressDto    objeto con la información de dirección de envío
	 * @param companyCode           código de compañía
	 * @param clientPersistencePort repositorio del sistema principal
	 * @param id                    identificador de la dirección de envío
	 * @param idOperation           identificador de traza
	 * @return ResponseModel objeto con el código de resultado de la operación, en
	 *         caso exitoso contiene embebida la información recuperada
	 */
	@Override
	public ResponseModel getShippingAddressUpdate(ShippingAddressDto shippingAddressDto, String companyCode,
			ClientPersistencePort clientPersistencePort, Long id, String idOperation) {
		LOG.info(String.format("%s INIT getShippingAddressUpdate()", idOperation));
		LOG.info(String.format("%s PARAMS: [ shippingAddressDto: %s , id: %s , companyCode: %s ]", idOperation,
				shippingAddressDto.toString(), id.toString(), companyCode));
		ResponseModel responseModel = clientPersistencePort.getShippingAddressUpdate(shippingAddressDto, companyCode,
				method.toString(), id, idOperation);
		LOG.info(String.format("%s RETURN DATA", idOperation));
		return responseModel;
	}

	/**
	 * Método que recupera la lista de correos del cliente.
	 *
	 * @param companyCode           código de compañía
	 * @param clientPersistencePort repositorio del sistema principal
	 * @param id                    identificador del cliente
	 * @param idOperation           identificador de traza
	 * @return ResponseModel objeto con el código de resultado de la operación, en
	 *         caso exitoso contiene embebida la información recuperada
	 */
	@Override
	public ResponseModel getListEmails(String companyCode, ClientPersistencePort clientPersistencePort, Long id,
			String idOperation) {
		LOG.info(String.format("%s INIT getListEmails()", idOperation));
		LOG.info(String.format("%s PARAMS: [ id: %s , companyCode: %s ]", idOperation, id.toString(), companyCode));
		ResponseModel responseModel = clientPersistencePort.getListEmails(companyCode, method.toString(), id,
				idOperation);
		LOG.info(String.format("%s RETURN DATA", idOperation));
		return responseModel;
	}

	private ClientIdDto clientDtoToClientIdDto(ClientDto clientDto) {

		ClientIdDto clientIdDto = new ClientIdDto();

		clientIdDto.setId(clientDto.getId());
		clientIdDto.setBusinessName(clientDto.getBusinessName());
		clientIdDto.setCell(clientDto.getCell());
		clientIdDto.setContact(clientDto.getContact());
		clientIdDto.setCustomerType(clientDto.getCustomerType());
		clientIdDto.setDirection(clientDto.getDirection());
		clientIdDto.setEnabled(clientDto.isEnabled());
		clientIdDto.setFatherSurname(clientDto.getFatherSurname());
		clientIdDto.setHowToContact(clientDto.getHowToContact());
		clientIdDto.setIva(clientDto.getIva());
		clientIdDto.setMailList(clientDto.getMailList());
		clientIdDto.setMotherSurname(clientDto.getMotherSurname());
		clientIdDto.setName(clientDto.getName());
		clientIdDto.setNnn001(clientDto.getNnn001());
		clientIdDto.setNoClient(clientDto.getNoClient());
		clientIdDto.setPhone(clientDto.getPhone());
		clientIdDto.setRfc(clientDto.getRfc());
		clientIdDto.setShippingAddressList(clientDto.getShippingAddressList());
		clientIdDto.setTaxRegime(clientDto.getTaxRegime());
		clientIdDto.setTaxpayer(clientDto.getTaxpayer());
		clientIdDto.setWorkType(clientDto.getWorkType());

		return clientIdDto;
	}
}