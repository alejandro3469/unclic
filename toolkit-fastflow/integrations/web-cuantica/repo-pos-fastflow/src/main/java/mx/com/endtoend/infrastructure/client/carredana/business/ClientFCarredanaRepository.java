package mx.com.endtoend.infrastructure.client.carredana.business;

import mx.com.endtoend.infrastructure.client.common.business.BaseClientBusinessRepository;
import org.springframework.stereotype.Service;
import mx.com.endtoend.infrastructure.client.carredana.repositories.ClientDirectionFCarRepository;
import mx.com.endtoend.infrastructure.client.carredana.repositories.ClientFCarRepository;
import mx.com.endtoend.infrastructure.client.carredana.repositories.ClientMailFCarRepository;
import mx.com.endtoend.infrastructure.client.carredana.repositories.CustomDslClientFCarRepository;
import mx.com.endtoend.infrastructure.client.carredana.repositories.ShippingAddressFCarRepository;
import mx.com.endtoend.infrastructure.client.common.converters.ClientConverter;
import mx.com.endtoend.infrastructure.client.common.converters.ClientDirectionConverter;
import mx.com.endtoend.infrastructure.client.common.converters.ClientMailConverter;
import mx.com.endtoend.infrastructure.client.common.converters.ShippingAddressConverter;
import mx.com.endtoend.infrastructure.company.common.repositories.CompanyRepository;

@Service
public class ClientFCarredanaRepository extends BaseClientBusinessRepository {

    public ClientFCarredanaRepository(ClientFCarRepository clientRepository,
                                      ClientConverter clientConverter,
                                      ClientDirectionFCarRepository clientDirectionRepository,
                                      ClientMailFCarRepository clientMailRepository,
                                      ClientDirectionConverter clientDirectionConverter,
                                      ClientMailConverter clientMailConverter,
                                      ShippingAddressFCarRepository shippingAddressRepository,
                                      ShippingAddressConverter shippingAddressConverter,
                                      CompanyRepository companyRepository,
                                      CustomDslClientFCarRepository customDslClientRepository) {
        super(ClientFCarredanaRepository.class,
                clientRepository,
                clientConverter,
                clientDirectionRepository,
                clientMailRepository,
                clientDirectionConverter,
                clientMailConverter,
                shippingAddressRepository,
                shippingAddressConverter,
                companyRepository,
                customDslClientRepository  );
    }

}
