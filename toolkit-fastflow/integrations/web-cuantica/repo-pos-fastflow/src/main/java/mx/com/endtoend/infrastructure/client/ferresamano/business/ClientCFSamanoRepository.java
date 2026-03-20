package mx.com.endtoend.infrastructure.client.ferresamano.business;

import mx.com.endtoend.infrastructure.client.common.business.BaseClientBusinessRepository;
import org.springframework.stereotype.Service;
import mx.com.endtoend.infrastructure.client.ferresamano.repositories.ClientDirectionFSamanoRepository;
import mx.com.endtoend.infrastructure.client.ferresamano.repositories.ClientFSamanoRepository;
import mx.com.endtoend.infrastructure.client.ferresamano.repositories.ClientMailFSamanoRepository;
import mx.com.endtoend.infrastructure.client.ferresamano.repositories.CustomDslClientFSamanoRepository;
import mx.com.endtoend.infrastructure.client.ferresamano.repositories.ShippingAddressFSamanoRepository;
import mx.com.endtoend.infrastructure.client.common.converters.ClientConverter;
import mx.com.endtoend.infrastructure.client.common.converters.ClientDirectionConverter;
import mx.com.endtoend.infrastructure.client.common.converters.ClientMailConverter;
import mx.com.endtoend.infrastructure.client.common.converters.ShippingAddressConverter;
import mx.com.endtoend.infrastructure.company.common.repositories.CompanyRepository;

@Service
public class ClientCFSamanoRepository extends BaseClientBusinessRepository {

    public ClientCFSamanoRepository(ClientFSamanoRepository clientRepository,
            ClientConverter clientConverter,
            ClientDirectionFSamanoRepository clientDirectionRepository,
            ClientMailFSamanoRepository clientMailRepository,
            ClientDirectionConverter clientDirectionConverter,
            ClientMailConverter clientMailConverter,
            ShippingAddressFSamanoRepository shippingAddressRepository,
            ShippingAddressConverter shippingAddressConverter,
            CompanyRepository companyRepository,
            CustomDslClientFSamanoRepository customDslClientRepository){
        super(ClientCFSamanoRepository.class,
                clientRepository,
                clientConverter,
                clientDirectionRepository,
                clientMailRepository,
                clientDirectionConverter,
                clientMailConverter,
                shippingAddressRepository,
                shippingAddressConverter,
                companyRepository,
                customDslClientRepository );
    }

}
