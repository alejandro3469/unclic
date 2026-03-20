package mx.com.endtoend.infrastructure.orderConfigurations.demo.business;

import mx.com.endtoend.infrastructure.orderConfigurations.common.business.BaseOrderConfiguration;
import mx.com.endtoend.infrastructure.orderConfigurations.demo.repositories.OrderConfigurationDemoRepository;
import org.springframework.stereotype.Service;


@Service
public class OrderConfigurationFDemoRepository extends BaseOrderConfiguration {

    public OrderConfigurationFDemoRepository(OrderConfigurationDemoRepository _orderConfigurationDemoRepository) {
        super(OrderConfigurationFDemoRepository.class,
                _orderConfigurationDemoRepository);
    }


}