package mx.com.endtoend.domain.orders.services;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.com.endtoend.domain.orders.dto.CustomInterfaceOrderParams;
import mx.com.endtoend.domain.orders.factories.OrderFactory;
import mx.com.endtoend.domain.orders.factories.OrderInterface;
import mx.com.endtoend.domain.orders.ports.OrderPersistencePort;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.GlobalError;

public abstract  class BaseOrderService {
    private static final String ERROR_GETTING_IMPLEMENTATION_LOG = "AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ";
    private static final Logger LOG = LoggerFactory.getLogger(BaseOrderService.class);

    protected final OrderPersistencePort orderPersistencePort;
    protected final OrderFactory orderBusinessFactory = new OrderFactory();

    protected BaseOrderService(OrderPersistencePort orderPersistencePort) {
        this.orderPersistencePort = orderPersistencePort;
    }

    protected OrderInterface getOrderBusiness(String method, CustomInterfaceOrderParams customInterfaceOrderParams) {
        customInterfaceOrderParams.setOrderPersistencePort(orderPersistencePort);
        OrderInterface orderBusiness = orderBusinessFactory.getImplementationByCode(method, customInterfaceOrderParams);
        if (orderBusiness == null) {
            LOG.error(ERROR_GETTING_IMPLEMENTATION_LOG);
            throw new GlobalError();
        }
        return orderBusiness;
    }
}
