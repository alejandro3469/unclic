package mx.com.endtoend.infrastructure.client.carredana.zapata.business;

import mx.com.endtoend.infrastructure.client.common.business.BaseClientBusinessRepository;
import org.springframework.stereotype.Service;
import mx.com.endtoend.infrastructure.client.carredana.zapata.repositories.ClientCZapataRepository;
import mx.com.endtoend.infrastructure.client.carredana.zapata.repositories.ClientDirectionCZapataRepository;
import mx.com.endtoend.infrastructure.client.carredana.zapata.repositories.ClientMailCZapataRepository;
import mx.com.endtoend.infrastructure.client.carredana.zapata.repositories.CustomDslClientCZapataRepository;
import mx.com.endtoend.infrastructure.client.carredana.zapata.repositories.ShippingAddressCZapataRepository;
import mx.com.endtoend.infrastructure.client.common.converters.ClientConverter;
import mx.com.endtoend.infrastructure.client.common.converters.ClientDirectionConverter;
import mx.com.endtoend.infrastructure.client.common.converters.ClientMailConverter;
import mx.com.endtoend.infrastructure.client.common.converters.ShippingAddressConverter;
import mx.com.endtoend.infrastructure.company.common.repositories.CompanyRepository;

@Service
public class ClientZapataRepository extends BaseClientBusinessRepository {

    public ClientZapataRepository(ClientCZapataRepository clientRepository,
                                ClientConverter clientConverter,
                                ClientDirectionCZapataRepository clientDirectionRepository,
                                ClientMailCZapataRepository clientMailRepository,
                                ClientDirectionConverter clientDirectionConverter,
                                ClientMailConverter clientMailConverter,
                                ShippingAddressCZapataRepository shippingAddressRepository,
                                ShippingAddressConverter shippingAddressConverter,
                                CompanyRepository companyRepository,
                                CustomDslClientCZapataRepository customDslClientRepository){
        super(ClientZapataRepository.class,
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
