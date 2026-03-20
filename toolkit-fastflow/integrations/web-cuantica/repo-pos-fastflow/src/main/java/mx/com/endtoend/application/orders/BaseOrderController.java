package mx.com.endtoend.application.orders;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.com.endtoend.domain.company.dto.MethodDto;
import mx.com.endtoend.domain.company.ports.api.CompanyServicePort;
import mx.com.endtoend.domain.company.ports.spi.CompanyPersistencePort;
import mx.com.endtoend.domain.orders.dto.CustomInterfaceOrderParams;
import mx.com.endtoend.domain.userConfigurations.ports.spi.UserConfigurationPersistencePort;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.GlobalError;
import mx.com.endtoend.infrastructure.services.email.EmailServicePort;
import mx.com.endtoend.infrastructure.services.jde.orders.common.serviceport.OrderJdeServicePort;
import mx.com.endtoend.infrastructure.services.posLegacy.orders.common.serviceport.OrderPosLegacyServicePort;

import java.util.HashMap;
import java.util.Map;


public class BaseOrderController {
    protected final Logger LOG = LoggerFactory.getLogger(this.getClass());
    protected final UserConfigurationPersistencePort userConfigurationPersistencePort;
    protected final CompanyPersistencePort companyPersistencePort;
    protected final OrderPosLegacyServicePort orderPosLegacyServicePort;
    protected final EmailServicePort emailServicePort;
    protected final CompanyServicePort companyServicePort;
    protected final OrderJdeServicePort orderJdeServicePort;
    protected final String module = "ORDERS";

    protected BaseOrderController(UserConfigurationPersistencePort userConfigurationPersistencePort,
                                  CompanyPersistencePort companyPersistencePort,
                                  OrderPosLegacyServicePort orderPosLegacyServicePort,
                                  EmailServicePort emailServicePort,
                                  CompanyServicePort companyServicePort,
                                  OrderJdeServicePort orderJdeServicePort) {
        this.userConfigurationPersistencePort = userConfigurationPersistencePort;
        this.companyPersistencePort = companyPersistencePort;
        this.orderPosLegacyServicePort = orderPosLegacyServicePort;
        this.emailServicePort = emailServicePort;
        this.companyServicePort = companyServicePort;
        this.orderJdeServicePort = orderJdeServicePort;
    }

    protected String generateIdOperation(String companyCode, String branchCode , boolean hasModule) {
        return mx.com.endtoend.genericCommonsFileds.utilities.ResponseLog.generateIdOperation(companyCode, branchCode, hasModule ? module : null);
    }

    protected void logInit(String methodName, String idOperation, Object params) {
        LOG.info("{} INIT {}()", idOperation, methodName);
        LOG.info("{} PARAMS: {}", idOperation, params);
    }

    protected MethodDto getMethod(String companyCode, String idOperation) {
        MethodDto method = (MethodDto) companyServicePort.findMethodByCompanyCodeAndModule(companyCode, module, idOperation).getData();
        if (method == null) {
            LOG.error("{} ERROR IN ASSIGNATION METHODS OF THE COMPANY {}", idOperation, companyCode);
            throw new GlobalError();
        }
        return method;
    }

    protected CustomInterfaceOrderParams.Builder buildCustomParams(String companyCode, String idOperation) {
        return new CustomInterfaceOrderParams.Builder()
                .setOrderJdeServicePort(orderJdeServicePort)
                .setOrderPosLegacyServicePort(orderPosLegacyServicePort)
                .setEmailServicePort(emailServicePort)
                .setUserConfigurationPersistencePort(userConfigurationPersistencePort)
                .setCompanyPersistencePort(companyPersistencePort)
                .setCompanyCode(companyCode)
                .setIdOperation(idOperation);
    }

    protected Map<String, Object> createLogMap(Object... keyValues) {
        Map<String, Object> mapa = new HashMap<>();
        for (int i = 0; i < keyValues.length; i += 2) {
            mapa.put((String) keyValues[i], keyValues[i + 1]);
        }
        return mapa;
    }
}
