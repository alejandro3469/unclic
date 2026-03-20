package mx.com.endtoend.infrastructure.client.Calzada.business;

import mx.com.endtoend.infrastructure.client.common.business.BaseClientBusinessRepository;
import org.springframework.stereotype.Service;
import mx.com.endtoend.infrastructure.client.Calzada.repositories.ClientDirectionRepository;
import mx.com.endtoend.infrastructure.client.Calzada.repositories.ClientMailRepository;
import mx.com.endtoend.infrastructure.client.Calzada.repositories.ClientRepository;
import mx.com.endtoend.infrastructure.client.Calzada.repositories.CustomDslClientRepository;
import mx.com.endtoend.infrastructure.client.Calzada.repositories.ShippingAddressRepository;
import mx.com.endtoend.infrastructure.client.common.converters.ClientConverter;
import mx.com.endtoend.infrastructure.client.common.converters.ClientDirectionConverter;
import mx.com.endtoend.infrastructure.client.common.converters.ClientMailConverter;
import mx.com.endtoend.infrastructure.client.common.converters.ShippingAddressConverter;
import mx.com.endtoend.infrastructure.company.common.repositories.CompanyRepository;

@Service
public class ClientCalzadaRepository extends BaseClientBusinessRepository {

    public ClientCalzadaRepository(ClientRepository clientRepository,
                                   ClientConverter clientConverter,
                                   ClientDirectionRepository clientDirectionRepository,
                                   ClientMailRepository clientMailRepository,
                                   ClientDirectionConverter clientDirectionConverter,
                                   ClientMailConverter clientMailConverter,
                                   ShippingAddressRepository shippingAddressRepository,
                                    ShippingAddressConverter shippingAddressConverter,
                                    CompanyRepository companyRepository,
                                    CustomDslClientRepository customDslClientRepository){
        super(ClientCalzadaRepository.class,
                clientRepository,
                clientConverter,
                clientDirectionRepository,
                clientMailRepository,
                clientDirectionConverter,
                clientMailConverter,
                shippingAddressRepository,
                shippingAddressConverter,
                companyRepository,
                customDslClientRepository);
    }
}