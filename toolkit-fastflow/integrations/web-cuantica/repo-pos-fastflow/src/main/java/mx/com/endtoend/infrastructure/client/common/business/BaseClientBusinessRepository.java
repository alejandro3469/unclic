package mx.com.endtoend.infrastructure.client.common.business;

import mx.com.endtoend.domain.clients.dto.*;
import mx.com.endtoend.infrastructure.client.Calzada.repositories.*;
import mx.com.endtoend.infrastructure.client.common.converters.ClientConverter;
import mx.com.endtoend.infrastructure.client.common.converters.ClientDirectionConverter;
import mx.com.endtoend.infrastructure.client.common.converters.ClientMailConverter;
import mx.com.endtoend.infrastructure.client.common.converters.ShippingAddressConverter;
import mx.com.endtoend.infrastructure.client.common.entities.ClientDirectionEntity;
import mx.com.endtoend.infrastructure.client.common.entities.ClientEntity;
import mx.com.endtoend.infrastructure.client.common.entities.ClientMailEntity;
import mx.com.endtoend.infrastructure.client.common.entities.ShippingAddressEntity;
import mx.com.endtoend.infrastructure.client.common.repository.*;
import mx.com.endtoend.infrastructure.commons.constants.CompanyCodes;
import mx.com.endtoend.infrastructure.company.common.entities.CompanyEntity;
import mx.com.endtoend.infrastructure.company.common.repositories.CompanyRepository;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.GlobalError;
import mx.com.endtoend.smart.bussiness.model.clients.dto.ClientDto;
import mx.com.endtoend.smart.bussiness.model.clients.dto.ClientMailDto;
import mx.com.endtoend.smart.bussiness.model.clients.dto.ShippingAddressDto;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.repository.NoRepositoryBean;

import javax.persistence.MappedSuperclass;
import java.util.ArrayList;
import java.util.List;

@MappedSuperclass
@NoRepositoryBean
public class BaseClientBusinessRepository implements GenericClientRepository {

    private final Logger LOG;
    private final BaseClientRepository clientRepository;
    private final ClientConverter clientConverter;
    private final BaseClientDirectionRepository clientDirectionRepository;
    private final BaseClientMailRepository clientMailRepository;
    private final ClientDirectionConverter clientDirectionConverter;
    private final ClientMailConverter clientMailConverter;
    private final BaseShippingAddressRepository shippingAddressRepository;
    private final ShippingAddressConverter shippingAddressConverter;
    private final CompanyRepository companyRepository;
    private final BaseCustomDslClientRepository customDslClientRepository;

    public BaseClientBusinessRepository(Class<?> loggerClass,
        BaseClientRepository _clientRepository,
        ClientConverter _clientConverter,
        BaseClientDirectionRepository _clientDirectionRepository,
        BaseClientMailRepository _clientMailRepository,
        ClientDirectionConverter _clientDirectionConverter,
        ClientMailConverter _clientMailConverter,
        BaseShippingAddressRepository _shippingAddressRepository,
        ShippingAddressConverter _shippingAddressConverter,
        CompanyRepository _companyRepository,
        BaseCustomDslClientRepository _customDslClientRepository) {
        LOG = LoggerFactory.getLogger(loggerClass);
        clientRepository = _clientRepository;
        clientConverter = _clientConverter;
        clientDirectionRepository = _clientDirectionRepository;
        clientMailRepository = _clientMailRepository;
        clientDirectionConverter = _clientDirectionConverter;
        clientMailConverter = _clientMailConverter;
        shippingAddressRepository = _shippingAddressRepository;
        shippingAddressConverter = _shippingAddressConverter;
        companyRepository = _companyRepository;
        customDslClientRepository = _customDslClientRepository;
    }

    @Override
    public ResponseModel getListClient(FiltersClientDto filtersClientDto, String idOperation) {
        LOG.info(String.format("%s INIT getListClient() ", idOperation));
        LOG.info(String.format("%s PARAMS: [ filtersClientDto: %s  ] ", idOperation, filtersClientDto.toString()));
        try {
            List<ClientEntity> clientList = customDslClientRepository.findClientListByParams(filtersClientDto);
            if (!clientList.isEmpty()) {
                List<ClientListDto> list = new ArrayList<>();
                PagedListHolder<ClientEntity> page = new PagedListHolder<>(clientList);
                page.setPageSize(filtersClientDto.getRow());
                page.setPage(filtersClientDto.getPage() - 1);

                for (ClientEntity client : page.getPageList()) {
                    ClientMailEntity clientMailEntity = clientMailRepository.findFirstByIdClientId(client.getId());
                    ClientListDto clientListDto;
                    if (clientMailEntity != null) {
                        clientListDto = ClientListDto.ClientDtoBuilder.build(client.getId(), client.getNoClient(),
                                client.getTaxpayer(), client.getCustomerType(), client.getRfc(),
                                clientMailEntity.getMail(), client.getName(), client.getFatherSurname(),
                                client.getMotherSurname(), client.getBusinessName(), client.getContact(),
                                client.getPhone(), client.getCell());
                        clientListDto.setIsExternal(false);
                    } else {
                        clientListDto = ClientListDto.ClientDtoBuilder.build(client.getId(), client.getNoClient(),
                                client.getTaxpayer(), client.getCustomerType(), client.getRfc(), null, client.getName(),
                                client.getFatherSurname(), client.getMotherSurname(), client.getBusinessName(),
                                client.getContact(), client.getPhone(), client.getCell());
                        clientListDto.setIsExternal(false);
                    }
                    list.add(clientListDto);
                }
                return new ResponseModel(new TotalClientsDto(page.getPageCount(), list));
            } else {
                LOG.info(String.format("%s RETURN EMPTY LIST TO SERCH PARAMS", idOperation));
                return new ResponseModel(new TotalClientsDto(0, new ArrayList<>()));
            }
        } catch (Exception e) {
            LOG.error(String.format("%s ERROR IN getListClient(). EXCEPTION: %s", idOperation, e.getMessage()));
            throw new GlobalError();
        }
    }

    @Override
    public ResponseModel getShippingAddressCatalog(Long id, String idOperation) {
        LOG.info(String.format("%s INIT getShippingAddressCatalog() ", idOperation));
        LOG.info(String.format("%s PARAMS: [ id: %s  ] ", idOperation, id.toString()));
        try {
            List<ShippingAddressListDto> shippingAddress = shippingAddressConverter.shippingAddressCatalogList(
                    shippingAddressRepository.findByIdClientId(id));
            if (shippingAddress != null) {
                LOG.info(idOperation + "BÚSQUEDA DE DIRECCION DE ENVIO");
                return new ResponseModel(shippingAddress);
            }
            List<ShippingAddressListDto> res = new ArrayList<>();
            return new ResponseModel(res);
        } catch (Exception e) {
            LOG.error(String.format("%s ERROR IN getShippingAddressCatalog(). EXCEPTION: %s", idOperation,
                    e.getMessage()));
            throw new GlobalError();
        }
    }

    @Override
    public ResponseModel getShippingAddress(Long id, String idOperation) {
        LOG.info(String.format("%s INIT getShippingAddress() ", idOperation));
        LOG.info(String.format("%s PARAMS: [ id: %s  ] ", idOperation, id.toString()));
        try {
            ShippingAddressDto shippingAddress = shippingAddressConverter.shippingAddressEntityToShippingAddressDto(
                    shippingAddressRepository.findById(id));
            if (shippingAddress != null)
                LOG.info(idOperation + "BÚSQUEDA DE DIRECCION DE ENVIO");
            return new ResponseModel(shippingAddress);
        } catch (Exception e) {
            LOG.error(idOperation + "-" + e.getMessage());
            throw new GlobalError();
        }
    }

    @Override
    public ResponseModel getShippingAddressCreate(ShippingAddressDto shippingAddressDto, Long id, String idOperation) {
        LOG.info(String.format("%s INIT getShippingAddress() ", idOperation));
        LOG.info(String.format("%s PARAMS: [ shippingAddressDto: %s , id: %s  ] ", idOperation,
                shippingAddressDto.toString(), id.toString()));
        try {
            ClientEntity clientEntity = clientRepository.findById(id);
            ShippingAddressEntity shippingAddressEntity = shippingAddressConverter
                    .shippingAddressDtoToShippingAddressEntityClient(shippingAddressDto, clientEntity);
            shippingAddressEntity = shippingAddressRepository.save(shippingAddressEntity);
            shippingAddressDto = shippingAddressConverter
                    .shippingAddressEntityToShippingAddressDto(shippingAddressEntity);
            LOG.info(idOperation + "SHIPPING ADDRESS IS CREATED");
            return new ResponseModel(shippingAddressDto);
        } catch (Exception e) {
            LOG.error(String.format("%s ERROR IN getShippingAddressCreate(). EXCEPTION: %s", idOperation,
                    e.getMessage()));
            throw new GlobalError();
        }
    }

    @Override
    public ResponseModel getShippingAddressUpdate(ShippingAddressDto shippingAddressDto, Long id, String idOperation) {

        LOG.info(String.format("%s INIT getShippingAddressUpdate() ", idOperation));
        LOG.info(String.format("%s PARAMS: [ shippingAddressDto: %s , id: %s  ] ", idOperation,
                shippingAddressDto.toString(), id.toString()));

        try {

            ClientEntity clientEntity = clientRepository.findById(shippingAddressDto.getIdClient());

            ShippingAddressEntity shippingAddressEntity = shippingAddressConverter
                    .shippingAddressDtoToShippingAddressEntityClient(shippingAddressDto, clientEntity);

            shippingAddressEntity.setId(id);

            shippingAddressEntity = shippingAddressRepository.save(shippingAddressEntity);

            shippingAddressDto = shippingAddressConverter
                    .shippingAddressEntityToShippingAddressDto(shippingAddressEntity);

            LOG.info(idOperation + "SHIPPING ADDRESS IS UPDATE");
            return new ResponseModel(shippingAddressDto);

        } catch (Exception e) {

            LOG.error(String.format("%s ERROR IN getShippingAddressUpdate(). EXCEPTION: %s", idOperation,
                    e.getMessage()));
            throw new GlobalError();
        }
    }

    @Override
    public ResponseModel recoverClient(Long id, String idOperation) {
        LOG.info(String.format("%s INIT recoverClient()", idOperation));
        LOG.info(String.format("%s PARAMS: [id: %s]", idOperation, id.toString()));
        try {
            LOG.info(String.format("%s FIN CLIENT", idOperation));
            ClientEntity clientEntity = clientRepository.findById(id);
            if (clientEntity == null) {
                List<ClientIdDto> res = new ArrayList<>();
                return new ResponseModel(res);
            }
            LOG.info(String.format("%s FIN CLIENT-DIRECTION", idOperation));
            ClientDirectionEntity clientDirectionEntity = clientDirectionRepository.findByIdClientId(id);

            LOG.info(String.format("%s FIN CLIENT-SHIPPING-ADDRESS", idOperation));
            List<ShippingAddressEntity> shippingAddressEntity = shippingAddressRepository.findByIdClientId(id);

            LOG.info(String.format("%s FIN CLIENT-EMAIL", idOperation));
            List<ClientMailEntity> clientMailEntity = clientMailRepository.findByIdClientId(id);

            LOG.info(String.format("%s START CONVERSION FROM ENTITY TO DTO", idOperation));
            ClientIdDto clientDto = clientConverter.clientEntitytoDirectionEntityToShippingAddressEntitytoList(
                    clientEntity, clientDirectionEntity, shippingAddressEntity, clientMailEntity);

            LOG.info(String.format("%s RETURN CLIENT", idOperation));
            return new ResponseModel(clientDto);

        } catch (Exception e) {
            LOG.error(String.format("%s ERROR IN recoverClient(). EXCEPTION: %s", idOperation, e.getMessage()));
            throw new GlobalError();
        }
    }

    @Override
    public boolean existsClient(ClientDto clientDto, String idOperation) {

        LOG.info(String.format("%s INIT existsClient() ", idOperation));
        LOG.info(String.format("%s PARAMS: [ clientDto: %s ] ", idOperation, clientDto.toString()));

        try {

            boolean exists = true;

            if (clientDto.getBusinessName().isEmpty()) {

                exists = clientRepository.existsByNameAndFatherSurnameAndMotherSurnameAndRfc(clientDto.getName(),
                        clientDto.getFatherSurname(), clientDto.getMotherSurname(), clientDto.getRfc());

                if (!exists) {

                    if (clientDto.getName().isEmpty() && clientDto.getFatherSurname().isEmpty()
                            && clientDto.getMotherSurname().isEmpty()) {

                        exists = clientRepository.existsByBusinessNameAndRfc(clientDto.getBusinessName(),
                                clientDto.getRfc());

                    }

                }
            } else {

                if (clientDto.getName().isEmpty() && clientDto.getFatherSurname().isEmpty()
                        && clientDto.getMotherSurname().isEmpty()) {

                    exists = clientRepository.existsByBusinessNameAndRfc(clientDto.getBusinessName(),
                            clientDto.getRfc());

                }

            }

            return exists;

        } catch (Exception e) {

            LOG.error(String.format("%s ERROR IN existsClient(). EXCEPTION: %s", idOperation, e.getMessage()));
            throw new GlobalError();
        }

    }

    @Override
    public boolean existsClientUpdate(ClientDto clientDto, String idOperation) {

        LOG.info(String.format("%s INIT existsClientUpdate() ", idOperation));
        LOG.info(String.format("%s PARAMS: [ clientDto: %s ] ", idOperation, clientDto.toString()));

        try {

            boolean exists = true;

            ClientEntity clientEntity = clientRepository.findById(clientDto.getId());

            if (clientDto.getBusinessName() == null || clientDto.getBusinessName().isEmpty()) {

                exists = clientRepository.existsByNameAndFatherSurnameAndMotherSurnameAndRfc(clientDto.getName(),
                        clientDto.getFatherSurname(), clientDto.getMotherSurname(), clientDto.getRfc());

                if (clientEntity.getRfc().equals(clientDto.getRfc())) {

                    exists = false;
                }

                if (!exists) {

                    if (clientDto.getName().isEmpty() && clientDto.getFatherSurname().isEmpty()
                            && clientDto.getMotherSurname().isEmpty()) {

                        exists = clientRepository.existsByBusinessNameAndRfc(clientDto.getBusinessName(),
                                clientDto.getRfc());

                    }

                }
            } else {

                if ((clientDto.getName() == null || clientDto.getName().isEmpty())
                        && (clientDto.getFatherSurname() == null || clientDto.getFatherSurname().isEmpty())
                        && (clientDto.getMotherSurname() == null || clientDto.getMotherSurname().isEmpty())) {

                    exists = clientRepository.existsByBusinessNameAndRfc(clientDto.getBusinessName(),
                            clientDto.getRfc());

                    if (clientEntity.getRfc().equals(clientDto.getRfc())) {

                        exists = false;
                    }

                }

            }

            return exists;

        } catch (Exception e) {

            LOG.error(String.format("%s ERROR IN existsClientUpdate(). EXCEPTION: %s", idOperation, e.getMessage()));
            throw new GlobalError();
        }

    }

    @Override
    public ResponseModel createClient(ClientDto clientDto, String idOperation, String companyCode) {

        LOG.info(String.format("%s INIT createClient() ", idOperation));
        LOG.info(String.format("%s PARAMS: [ clientDto: %s , companyCode: %s ] ", idOperation, clientDto.toString(),
                companyCode));

        try {

            CompanyCodes company = CompanyCodes.valueOf(companyCode);

            CompanyEntity companyEntity = companyRepository.findByCode(company);

            clientDto.setCompany(companyEntity.getId());

            ClientEntity clientEntity = clientRepository.save(clientConverter.clientDtoToClientEntity(clientDto));

            clientDto.setId(clientEntity.getId());

            ClientDirectionEntity clientDirectionEntity = clientDirectionConverter
                    .clientDirectionDtoToClientDirectionEntityClient(clientDto.getDirection(), clientEntity);
            clientDirectionEntity.getIdClient().setId(clientDto.getId());

            clientDirectionEntity = clientDirectionRepository.save(clientDirectionEntity);

            for (ShippingAddressDto shippingAddressDto : clientDto.getShippingAddressList()) {

                ShippingAddressEntity shippingAddressEntity;
                if (shippingAddressDto == null) {
                    shippingAddressEntity = shippingAddressConverter.clientDirectionDtoToShippingAddressEntity(
                            clientDto.getDirection(), clientEntity, clientDirectionEntity);
                    if (!clientDto.getName().isEmpty()) {
                        shippingAddressEntity.setUvicationName(clientDto.getName());
                    } else {
                        shippingAddressEntity.setUvicationName(clientDto.getBusinessName());
                    }
                } else {
                    shippingAddressEntity = shippingAddressConverter
                            .shippingAddressDtoToShippingAddressEntityClient(shippingAddressDto, clientEntity);
                }

                shippingAddressEntity.setIdClient(clientEntity);

                shippingAddressRepository.save(shippingAddressEntity);

            }

            if (clientDto.getMailList() != null) {

                for (ClientMailDto clientMailDto : clientDto.getMailList()) {

                    ClientMailEntity clientMailEntity = clientMailConverter
                            .clientMailDtoToClientMailEntityClient(clientMailDto, clientEntity);
                    clientMailEntity.getIdClient().setId(clientDto.getId());

                    clientMailRepository.save(clientMailEntity);
                }
            }


            return new ResponseModel(clientConverter.clientEntitytoDirectionEntityToShippingAddressEntitytoList(
                    clientRepository.findById(clientDto.getId()),
                    clientDirectionRepository.findByIdClientId(clientDto.getId()),
                    shippingAddressRepository.findByIdClientId(clientDto.getId()),
                    clientMailRepository.findByIdClientId(clientDto.getId())));

        } catch (Exception e) {

            LOG.error(String.format("%s ERROR IN createClient(). EXCEPTION: %s", idOperation, e.getMessage()));
            throw new GlobalError();
        }
    }

    @Override
    public ResponseModel updateClient(ClientDto clientDto, String idOperation) {

        LOG.info(String.format("%s INIT updateClient() ", idOperation));
        LOG.info(String.format("%s PARAMS: [ clientDto: %s ] ", idOperation, clientDto.toString()));

        try {

            ClientEntity client = clientRepository.findById(clientDto.getId());
            clientDto.setNoClient(client.getNoClient());
            clientDto.setCompany(client.getCompany());
            clientDto.setIva(client.getIva());

            ClientEntity clientEntity = clientRepository.save(clientConverter.clientDtoToClientEntity(clientDto));

            clientDirectionRepository.save(clientDirectionConverter
                    .clientDirectionDtoToClientDirectionEntityClient(clientDto.getDirection(), clientEntity));

            shippingAddressRepository.save(shippingAddressConverter
                    .shippingAddressDtoToShippingAddressEntityClient(clientDto.getShippingAddress(), clientEntity));

            List<Long> id = new ArrayList<>();

            for (ClientMailDto clientMailDto : clientDto.getMailList()) {

                ClientMailEntity clientMailEntity = clientMailConverter
                        .clientMailDtoToClientMailEntityClient(clientMailDto, clientEntity);
                clientMailEntity.getIdClient().setId(clientDto.getId());

                clientMailRepository.save(clientMailEntity);

                id.add(clientMailDto.getId());
            }

            clientMailRepository.deleteByIdClientIdAndIdNotIn(clientDto.getId(), id);

            return new ResponseModel(clientConverter.clientEntitytoDirectionEntityToShippingAddressEntitytoList(
                    clientRepository.findById(clientDto.getId()),
                    clientDirectionRepository.findByIdClientId(clientDto.getId()),
                    shippingAddressRepository.findByIdClientId(clientDto.getId()),
                    clientMailRepository.findByIdClientId(clientDto.getId())));

        } catch (Exception e) {

            LOG.error(String.format("%s ERROR IN updateClient(). EXCEPTION: %s", idOperation, e.getMessage()));
            throw new GlobalError();
        }
    }

    @Override
    public ResponseModel getListEmails(Long id, String idOperation) {

        LOG.info(String.format("%s INIT getListEmails() ", idOperation));
        LOG.info(String.format("%s PARAMS: [ id: %s ] ", idOperation, id.toString()));

        try {

            List<ClientMailDto> clientMailDto = clientMailConverter.clientMailEntityListclientMailDtoListCalzada(
                    clientMailRepository.findByIdClientId(id));

            if (clientMailDto != null) {
                LOG.info(idOperation + "LISTA DE CORREOS");
                return new ResponseModel(clientMailDto);
            }

            List<ClientMailDto> res = new ArrayList<>();
            return new ResponseModel(res);

        } catch (Exception e) {

            LOG.error(String.format("%s ERROR IN getListEmails(). EXCEPTION: %s", idOperation, e.getMessage()));
            throw new GlobalError();
        }
    }
}

