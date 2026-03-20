package mx.com.endtoend.infrastructure.client.Calzada.fragua.business;

import mx.com.endtoend.infrastructure.client.common.business.BaseClientBusinessRepository;
import org.springframework.stereotype.Service;
import mx.com.endtoend.infrastructure.client.Calzada.fragua.repositories.ClientDirectionFraguaRepository;
import mx.com.endtoend.infrastructure.client.Calzada.fragua.repositories.ClientFraguaRepository;
import mx.com.endtoend.infrastructure.client.Calzada.fragua.repositories.ClientMailFraguaRepository;
import mx.com.endtoend.infrastructure.client.Calzada.fragua.repositories.ShippingAddressFraguaRepository;
import mx.com.endtoend.infrastructure.client.Calzada.fragua.repositories.CustomDslFraguaClientRepository;
import mx.com.endtoend.infrastructure.client.common.converters.ClientConverter;
import mx.com.endtoend.infrastructure.client.common.converters.ClientDirectionConverter;
import mx.com.endtoend.infrastructure.client.common.converters.ClientMailConverter;
import mx.com.endtoend.infrastructure.client.common.converters.ShippingAddressConverter;
import mx.com.endtoend.infrastructure.company.common.repositories.CompanyRepository;

@Service
public class ClientSFraguaRepository extends BaseClientBusinessRepository {

    public ClientSFraguaRepository(ClientFraguaRepository clientRepository,
            ClientConverter clientConverter,
            ClientDirectionFraguaRepository clientDirectionRepository,
            ClientMailFraguaRepository clientMailRepository,
            ClientDirectionConverter clientDirectionConverter,
            ClientMailConverter clientMailConverter,
            ShippingAddressFraguaRepository shippingAddressRepository,
            ShippingAddressConverter shippingAddressConverter,
            CompanyRepository companyRepository,
            CustomDslFraguaClientRepository customDslClientRepository){
        super(ClientSFraguaRepository.class,
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
