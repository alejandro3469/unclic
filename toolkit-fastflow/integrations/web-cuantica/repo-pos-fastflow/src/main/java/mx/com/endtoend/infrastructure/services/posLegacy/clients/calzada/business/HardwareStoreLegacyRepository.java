package mx.com.endtoend.infrastructure.services.posLegacy.clients.calzada.business;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import mx.com.endtoend.infrastructure.client.Calzada.repositories.ClientDirectionRepository;
import mx.com.endtoend.infrastructure.client.Calzada.repositories.ClientMailRepository;
import mx.com.endtoend.infrastructure.client.Calzada.repositories.ShippingAddressRepository;
import mx.com.endtoend.infrastructure.client.common.entities.ClientDirectionEntity;
import mx.com.endtoend.infrastructure.client.common.entities.ClientMailEntity;
import mx.com.endtoend.infrastructure.client.common.entities.ShippingAddressEntity;
import mx.com.endtoend.infrastructure.services.posLegacy.clients.common.repository.ClientLegacyRepository;
import mx.com.endtoend.infrastructure.services.posLegacy.clients.common.entities.ClientSqlEntity;
import mx.com.endtoend.infrastructure.services.posLegacy.clients.common.entities.ShippingAddressSqlEntity;
import mx.com.endtoend.infrastructure.services.posLegacy.clients.common.repository.ClientSqlRepository;
import mx.com.endtoend.infrastructure.services.posLegacy.clients.common.repository.ShippingAddressSqlRepository;
import mx.com.endtoend.smart.bussiness.model.clients.dto.ClientDto;

@ConditionalOnProperty(name = "app.calzadaSQL.enabled", havingValue = "true", matchIfMissing = false)
@Service
public class HardwareStoreLegacyRepository implements ClientLegacyRepository {

	@Autowired(required = false)
	private ClientSqlRepository clientSqlRepository;

	@Autowired(required = false)
	private ShippingAddressSqlRepository addressSqlRepository;

	@Autowired
	private ClientMailRepository clientMailRepository;

	@Autowired
	private ClientDirectionRepository clientDirectionRepository;

	@Autowired
	private ShippingAddressRepository shippingAddressRepository;

	private final Logger LOG = LoggerFactory.getLogger(HardwareStoreLegacyRepository.class);

	/**
	 * Método para la creación de un cliente
	 * 
	 * @param clientDto   objeto con la información del cliente a almacenar
	 * @param idOperation identificador de traza
	 * 
	 * @return boolean indicador para resultado de la operación, true proceso
	 *         completo y false proceso incompleto
	 */
	@Override
	public boolean createClient(ClientDto clientDto, String idOperation) {

		try {

			LOG.info(String.format("%s INIT createClient()", idOperation));
			LOG.info(String.format("%s PARAMS: [ clientDto: %s]", idOperation, clientDto.toString()));

			ClientSqlEntity clientSqlEntity = new ClientSqlEntity();

			clientSqlEntity.setNoClient(clientDto.getNoClient());
			clientSqlEntity.setBusinessName(clientDto.getBusinessName());

			if (clientDto.getCustomerType() != null && !clientDto.getCustomerType().isEmpty()) {
				clientSqlEntity.setCustomerType(clientDto.getCustomerType());
			}
			clientSqlEntity.setIva("IV" + clientDto.getIva());
			clientSqlEntity.setMoney("MXN");
			clientSqlEntity.setRfc(clientDto.getRfc());

			ClientMailEntity clientMailEntity = clientMailRepository.findFirstByIdClientId(clientDto.getId());
			clientSqlEntity.setMail(clientMailEntity.getMail());

			clientSqlEntity.setPhone(clientDto.getPhone());
			clientSqlEntity.setCell(clientDto.getCell());
			clientSqlEntity.setContact(clientDto.getContact());

			ClientDirectionEntity clientDirectionEntity = clientDirectionRepository.findByIdClientId(clientDto.getId());
			clientSqlEntity.setStreet(clientDirectionEntity.getStreet());
			clientSqlEntity.setStateCode(clientDirectionEntity.getStateCode());
			clientSqlEntity.setNoOutdoor(clientDirectionEntity.getNoOutdoor());
			clientSqlEntity.setCp(clientDirectionEntity.getCp());
			clientSqlEntity.setCity(clientDirectionEntity.getCity());
			clientSqlEntity.setColony(clientDirectionEntity.getColony());

			clientSqlEntity.setDelegationCode(
					clientDirectionEntity.getDelegationCode() != null ? clientDirectionEntity.getDelegationCode()
							: " ");

			clientSqlEntity.setNoInterior(
					clientDirectionEntity.getNoInterior() != null ? clientDirectionEntity.getNoInterior() : " ");

			clientSqlEntity.setFlatCode(
					clientDirectionEntity.getFlatCode() != null ? clientDirectionEntity.getFlatCode() : " ");

			clientSqlEntity.setCoordinatesCode(
					clientDirectionEntity.getCoordinatesCode() != null ? clientDirectionEntity.getCoordinatesCode()
							: " ");

			clientSqlEntity.setWorkType(clientDto.getWorkType());
			if (clientDto.getHowToContact() != null && !clientDto.getHowToContact().isEmpty()) {
				clientSqlEntity.setHowToContact(clientDto.getHowToContact());
			}
			clientSqlEntity.setFatherSurname(clientDto.getFatherSurname());
			clientSqlEntity.setMotherSurname(clientDto.getMotherSurname());
			if (clientDto.getName().length() > 40) {
				clientSqlEntity.setName(clientDto.getName().substring(0, 40));
			} else {
				clientSqlEntity.setName(clientDto.getName());
			}
			if (clientDto.getName() != null && !clientDto.getName().isEmpty()) {
				clientSqlEntity.setBusinessName(
						clientDto.getName() + " " + clientDto.getFatherSurname() + " " + clientDto.getMotherSurname());
			}
			clientSqlEntity.setTaxpayer(clientDto.getTaxpayer());
			clientSqlEntity.setEstatusEnvio("ENVIADO");
			clientSqlEntity.setTipoIVA("1");
			clientSqlEntity.setListaPrecios("OFERTAS");
			clientSqlEntity.setAsn("OFERTAS");
			clientSqlEntity.setPuntos("0");
			clientSqlEntity.setCodigoBarras(" ");
			clientSqlEntity.setRegimenFiscal(clientDto.getTaxRegime());
			clientSqlRepository.save(clientSqlEntity);

			ShippingAddressEntity addressEntity = shippingAddressRepository.findFirstByIdClientId(clientDto.getId());
			ShippingAddressSqlEntity addressSqlEntity = new ShippingAddressSqlEntity();

			addressSqlEntity.setNoClient(clientDto.getNoClient());
			addressSqlEntity.setStreet(addressEntity.getStreet());
			addressSqlEntity.setNoOutdoor(addressEntity.getNoOutdoor());
			addressSqlEntity.setNoInterior(addressEntity.getNoInterior() != null ? addressEntity.getNoInterior() : " ");
			addressSqlEntity.setCp(addressEntity.getCp());
			addressSqlEntity.setCity(addressEntity.getCity());
			addressSqlEntity.setColony(addressEntity.getColony());
			addressSqlEntity.setStateCode(addressEntity.getStateCode() != null
					? addressEntity.getStateCode().length() > 3 ? addressEntity.getStateCode().substring(0, 2)
							: addressEntity.getStateCode()
					: " ");
			addressSqlEntity.setDelegationCode(addressEntity.getDelegationCode());
			addressSqlEntity.setFlatCode(addressEntity.getFlatCode() != null
					? addressEntity.getFlatCode().length() > 3 ? addressEntity.getFlatCode().substring(0, 2)
							: addressEntity.getFlatCode()
					: " ");
			addressSqlEntity.setCoordinatesCode(
					addressEntity.getCoordinatesCode() != null ? addressEntity.getCoordinatesCode().length() > 3
							? addressEntity.getCoordinatesCode().substring(0, 2)
							: addressEntity.getCoordinatesCode() : " ");
			addressSqlRepository.save(addressSqlEntity);
			return true;

		} catch (Exception e) {
			LOG.error(String.format("%s ERROR IN SAVE DATA, EXCEPTION: %s ", idOperation, e.getMessage()));
			return false;
		}
	}

	/**
	 * Método para la modificación de un cliente
	 * 
	 * @param clientDto   cliente
	 * @param idOperation identificador de la operación
	 * 
	 * @return boolean indicador para resultado de la operación, true proceso
	 *         completo y false proceso incompleto
	 */
	@Override
	public boolean updateClient(ClientDto clientDto, String idOperation) {
		try {
			LOG.info("METHOD: deleteClientSql() --PARAM: client=" + clientDto);
			Optional<ClientSqlEntity> clientSqlEntity = clientSqlRepository.findByNoClient(clientDto.getNoClient());
			if (clientSqlEntity.isPresent()) {
				clientSqlRepository.delete(clientSqlEntity.get());
			}
			Optional<ShippingAddressSqlEntity> shippingAddressSqlEntity = addressSqlRepository
					.findByNoClient(clientDto.getNoClient());
			if (shippingAddressSqlEntity.isPresent()) {
				addressSqlRepository.delete(shippingAddressSqlEntity.get());
			}

			boolean createOk = createClient(clientDto, idOperation);

			return createOk;

		} catch (Exception e) {

			LOG.error(String.format("%s ERROR IN UPDATE DATA, EXCEPTION: %s ", idOperation, e.getMessage()));
			return false;

		}
	}
}
