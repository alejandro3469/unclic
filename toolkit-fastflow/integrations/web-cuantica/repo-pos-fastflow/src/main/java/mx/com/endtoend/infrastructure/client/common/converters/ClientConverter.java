package mx.com.endtoend.infrastructure.client.common.converters;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mx.com.endtoend.domain.clients.dto.ClientIdDto;
import mx.com.endtoend.domain.clients.dto.ClientListDto;
import mx.com.endtoend.infrastructure.client.common.entities.ClientDirectionEntity;
import mx.com.endtoend.infrastructure.client.common.entities.ClientEntity;
import mx.com.endtoend.infrastructure.client.common.entities.ClientMailEntity;
import mx.com.endtoend.infrastructure.client.common.entities.ShippingAddressEntity;
import mx.com.endtoend.smart.bussiness.model.clients.dto.ClientDto;

@Component
public class ClientConverter {

	@Autowired
	private ClientDirectionConverter clientDirectionConverter;

	@Autowired
	private ShippingAddressConverter shippingAddressConverter;

	@Autowired
	private ClientMailConverter clientMailConverter;

	public ClientEntity clientDtoToClientEntity(ClientDto clientDto) {
		ClientEntity clientEntity = new ClientEntity();

		clientEntity.setId(clientDto.getId());
		clientEntity.setNoClient(clientDto.getNoClient());
		clientEntity.setTaxpayer(clientDto.getTaxpayer());
		clientEntity.setCustomerType(clientDto.getCustomerType());
		clientEntity.setRfc(clientDto.getRfc());
//		clientEntity.setMail(clientDto.getMail());
		clientEntity.setName(clientDto.getName());
		clientEntity.setFatherSurname(clientDto.getFatherSurname());
		clientEntity.setMotherSurname(clientDto.getMotherSurname());
		clientEntity.setBusinessName(clientDto.getBusinessName());
		clientEntity.setContact(clientDto.getContact());
		clientEntity.setPhone(clientDto.getPhone());
		clientEntity.setCell(clientDto.getCell());
		clientEntity.setHowToContact(clientDto.getHowToContact());
		clientEntity.setWorkType(clientDto.getWorkType());
		clientEntity.setIva(clientDto.getIva());
		clientEntity.setTaxRegime(clientDto.getTaxRegime());
		clientEntity.setNnn001(clientDto.getNnn001());
		clientEntity.setCompany(clientDto.getCompany());

//		clientEntity.setDirection(clientDirectionConverter.clientDirectionDtoToClientDirectionEntity(clientDto.getDirection()));
//		if (clientDto.getShippingAddress() != null) 
//		clientEntity.setShippingAddress(shippingAddressConverter.shippingAddressDtoToShippingAddressEntity(clientDto.getShippingAddress()));

		return clientEntity;
	}

	public ClientDto clientEntityToClientDto(ClientEntity clientEntity) {

		ClientDto clientDto = new ClientDto();

		clientDto.setId(clientEntity.getId());
		clientDto.setNoClient(clientEntity.getNoClient());
		clientDto.setTaxpayer(clientEntity.getTaxpayer());
		clientDto.setCustomerType(clientEntity.getCustomerType());
		clientDto.setRfc(clientEntity.getRfc());
//		clientDto.setMail(clientEntity.getMail());
		clientDto.setName(clientEntity.getName());
		clientDto.setFatherSurname(clientEntity.getFatherSurname());
		clientDto.setMotherSurname(clientEntity.getMotherSurname());
		clientDto.setBusinessName(clientEntity.getBusinessName());
		clientDto.setContact(clientEntity.getContact());
		clientDto.setPhone(clientEntity.getPhone());
		clientDto.setCell(clientEntity.getCell());
		clientDto.setHowToContact(clientEntity.getHowToContact());
		clientDto.setWorkType(clientEntity.getWorkType());
		clientDto.setIva(clientEntity.getIva());
//		clientDto.setDirection(clientDirectionConverter.clientDirectionEntityToClientDirectionDto(clientEntity.getDirection()));
//		if (clientEntity.getShippingAddress() != null) 
//		clientDto.setShippingAddress(shippingAddressConverter.shippingAddressEntityToShippingAddressDto(clientEntity.getShippingAddress()));
//		
		return clientDto;
	}

	public List<ClientEntity> clientDtoListToClientEntityList(List<ClientDto> clientDtoList) {
		List<ClientEntity> clientEntityLits = new ArrayList<ClientEntity>();
		for (ClientDto clientDto : clientDtoList) {
			clientEntityLits.add(clientDtoToClientEntity(clientDto));
		}
		return clientEntityLits;
	}

	public List<ClientDto> clientEntityListToClientDtoList(List<ClientEntity> clientEntityList) {
		List<ClientDto> clientDtoList = new ArrayList<ClientDto>();
		for (ClientEntity clientEntity : clientEntityList) {
			clientDtoList.add(clientEntityToClientDto(clientEntity));
		}
		return clientDtoList;
	}

//	public ClientEntity clientDtoSearchToClientEntitySearch(ClientDto clientDto) {
//		ClientEntity clientEntity = new ClientEntity();
//		
//		clientEntity.setId(clientDto.getId());
//		clientEntity.setNoClient(clientDto.getNoClient());
//		clientEntity.setRfc(clientDto.getRfc());
//		clientEntity.setName(clientDto.getName());
//		clientEntity.setFatherSurname(clientDto.getFatherSurname());
//		clientEntity.setMotherSurname(clientDto.getMotherSurname());		
//		clientEntity.setBusinessName(clientDto.getBusinessName());
//		
//		return clientEntity;
//	}

	public ClientListDto clientEntitySearchToClientDtoSearch(ClientEntity clientEntity) {

		ClientListDto clientDto = new ClientListDto();

		clientDto.setId(clientEntity.getId());
		clientDto.setNoClient(clientEntity.getNoClient());
		clientDto.setTaxpayer(clientEntity.getTaxpayer());
		clientDto.setCustomerType(clientEntity.getCustomerType());
		clientDto.setRfc(clientEntity.getRfc());
//		clientDto.setMail(clientEntity.getMail());
		clientDto.setName(clientEntity.getName());
		clientDto.setFatherSurname(clientEntity.getFatherSurname());
		clientDto.setMotherSurname(clientEntity.getMotherSurname());
		clientDto.setBusinessName(clientEntity.getBusinessName());
		clientDto.setContact(clientEntity.getContact());
		clientDto.setPhone(clientEntity.getPhone());
		clientDto.setCell(clientEntity.getCell());

		return clientDto;
	}

//	public List<ClientEntity> clientDtoListSearchToClientEntityListSearch(List<ClientDto> clientDtoList){
//		List<ClientEntity> clientEntityLits = new ArrayList<ClientEntity>();
//		for (ClientDto clientDto : clientDtoList) {
//			clientEntityLits.add(clientDtoSearchToClientEntitySearch(clientDto));
//		}
//		return clientEntityLits;
//	}

	public List<ClientListDto> clientEntityListSearchToClientDtoListSearch(List<ClientEntity> clientEntityList) {
		List<ClientListDto> clientDtoList = new ArrayList<ClientListDto>();
		for (ClientEntity clientEntity : clientEntityList) {
			clientDtoList.add(clientEntitySearchToClientDtoSearch(clientEntity));
		}
		return clientDtoList;
	}

	public ClientDto clientEntityToList(ClientEntity clientEntity, ClientDirectionEntity clientDirectionEntity,
			ShippingAddressEntity shippingAddressEntity) {

		ClientDto clientDto = new ClientDto();

		clientDto.setId(clientEntity.getId());
		clientDto.setNoClient(clientEntity.getNoClient());
		clientDto.setTaxpayer(clientEntity.getTaxpayer());
		clientDto.setCustomerType(clientEntity.getCustomerType());
		clientDto.setRfc(clientEntity.getRfc());
//		clientDto.setMail(clientEntity.getMail());
		clientDto.setName(clientEntity.getName());
		clientDto.setFatherSurname(clientEntity.getFatherSurname());
		clientDto.setMotherSurname(clientEntity.getMotherSurname());
		clientDto.setBusinessName(clientEntity.getBusinessName());
		clientDto.setContact(clientEntity.getContact());
		clientDto.setPhone(clientEntity.getPhone());
		clientDto.setCell(clientEntity.getCell());
		clientDto.setHowToContact(clientEntity.getHowToContact());
		clientDto.setWorkType(clientEntity.getWorkType());
		clientDto.setDirection(
				clientDirectionConverter.clientDirectionEntityToClientDirectionDto(clientDirectionEntity));
		clientDto.setShippingAddress(
				shippingAddressConverter.shippingAddressEntityToShippingAddressDto(shippingAddressEntity));

		return clientDto;
	}

	public ClientIdDto clientEntitytoDirectionEntityToShippingAddressEntitytoList(ClientEntity clientEntity,
			ClientDirectionEntity clientDirectionEntity, List<ShippingAddressEntity> shippingAddressEntity,
			List<ClientMailEntity> clientMailEntity) {

		ClientIdDto clientDto = new ClientIdDto();

		clientDto.setId(clientEntity.getId());
		clientDto.setNoClient(clientEntity.getNoClient());
		clientDto.setTaxpayer(clientEntity.getTaxpayer());
		if (clientEntity.getCustomerType() == null) {
			clientDto.setCustomerType("");
		} else {
			clientDto.setCustomerType(clientEntity.getCustomerType());
		}

		if (clientEntity.getRfc() == null) {
			clientDto.setRfc("");
		} else {
			clientDto.setRfc(clientEntity.getRfc());
		}

		if (clientEntity.getName() == null) {
			clientDto.setName("");
		} else {
			clientDto.setName(clientEntity.getName());
		}

		if (clientEntity.getFatherSurname() == null) {
			clientDto.setFatherSurname("");
		} else {
			clientDto.setFatherSurname(clientEntity.getFatherSurname());
		}

		if (clientEntity.getMotherSurname() == null) {
			clientDto.setMotherSurname("");
		} else {
			clientDto.setMotherSurname(clientEntity.getMotherSurname());
		}

		if (clientEntity.getBusinessName() == null) {
			clientDto.setBusinessName("");
		} else {
			clientDto.setBusinessName(clientEntity.getBusinessName());
		}

		clientDto.setContact(clientEntity.getContact());
		if (clientEntity.getPhone() == null) {
			clientDto.setPhone("");
		} else {
			clientDto.setPhone(clientEntity.getPhone());
		}

		if (clientEntity.getCell() == null) {
			clientDto.setCell("");
		} else {
			clientDto.setCell(clientEntity.getCell());
		}

		clientDto.setIva(clientEntity.getIva());
		if (clientEntity.getTaxRegime() == null) {
			clientDto.setTaxRegime("");
		} else {
			clientDto.setTaxRegime(clientEntity.getTaxRegime());
		}

		if (clientEntity.getHowToContact() == null) {
			clientDto.setHowToContact("");
		} else {
			clientDto.setHowToContact(clientEntity.getHowToContact());
		}

		if (clientEntity.getWorkType() == null) {
			clientDto.setWorkType("");
		} else {
			clientDto.setWorkType(clientEntity.getWorkType());
		}

		if (clientEntity.getNnn001() == null) {
			clientDto.setNnn001("");
		} else {
			clientDto.setNnn001(clientEntity.getNnn001());
		}

		if (clientMailEntity != null) {
			clientDto.setMailList(clientMailConverter.clientMailEntityListclientMailDtoListCalzada(clientMailEntity));
		}

		if (clientDirectionEntity != null) {
			clientDto.setDirection(
					clientDirectionConverter.clientDirectionEntityToClientDirectionDto(clientDirectionEntity));
		}

		if (shippingAddressEntity != null) {
			clientDto.setShippingAddressList(shippingAddressConverter
					.shippingAddressEntityListToShippingAddressDtoListCalzada(shippingAddressEntity));
		}

		return clientDto;
	}
}
