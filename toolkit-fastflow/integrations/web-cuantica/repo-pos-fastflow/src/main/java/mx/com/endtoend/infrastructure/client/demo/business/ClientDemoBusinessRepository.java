package mx.com.endtoend.infrastructure.client.demo.business;

import mx.com.endtoend.infrastructure.client.common.business.BaseClientBusinessRepository;
import org.springframework.stereotype.Service;
import mx.com.endtoend.infrastructure.client.demo.repositories.ClientDirectionDemoRepository;
import mx.com.endtoend.infrastructure.client.demo.repositories.ClientDemoRepository;
import mx.com.endtoend.infrastructure.client.demo.repositories.ClientMailDemoRepository;
import mx.com.endtoend.infrastructure.client.demo.repositories.CustomDslClientDemoRepository;
import mx.com.endtoend.infrastructure.client.demo.repositories.ShippingAddressDemoRepository;
import mx.com.endtoend.infrastructure.client.common.converters.ClientConverter;
import mx.com.endtoend.infrastructure.client.common.converters.ClientDirectionConverter;
import mx.com.endtoend.infrastructure.client.common.converters.ClientMailConverter;
import mx.com.endtoend.infrastructure.client.common.converters.ShippingAddressConverter;
import mx.com.endtoend.infrastructure.company.common.repositories.CompanyRepository;

@Service
public class ClientDemoBusinessRepository extends BaseClientBusinessRepository {

    public ClientDemoBusinessRepository(ClientDemoRepository clientRepository,
                                      ClientConverter clientConverter,
                                      ClientDirectionDemoRepository clientDirectionRepository,
                                      ClientMailDemoRepository clientMailRepository,
                                      ClientDirectionConverter clientDirectionConverter,
                                      ClientMailConverter clientMailConverter,
                                      ShippingAddressDemoRepository shippingAddressRepository,
                                      ShippingAddressConverter shippingAddressConverter,
                                      CompanyRepository companyRepository,
                                      CustomDslClientDemoRepository customDslClientRepository) {
        super(ClientDemoBusinessRepository.class,
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