package mx.com.endtoend.domain.advertising.dto;

import mx.com.endtoend.domain.advertising.ports.AdvertisingPersistencePort;
import mx.com.endtoend.domain.company.ports.spi.CompanyPersistencePort;
import mx.com.endtoend.domain.orderConfigurations.ports.spi.OrderConfigurationPersistencePort;
import mx.com.endtoend.domain.userConfigurations.ports.spi.UserConfigurationPersistencePort;
import mx.com.endtoend.infrastructure.services.jde.payments.common.serviceport.PaymentJDEServicePort;

public class SaleAdvertisingInterfaceService {

	private AdvertisingPersistencePort advertisingPersistencePort;

	private OrderConfigurationPersistencePort orderConfigurationPersistencePort;

	private UserConfigurationPersistencePort userConfigurationPersistencePort;

	private PaymentJDEServicePort jdePaymentJDEServicePort;

	private CompanyPersistencePort companyPersistencePort;

	public SaleAdvertisingInterfaceService() {
		super();
	}

	public SaleAdvertisingInterfaceService(OrderConfigurationPersistencePort orderConfigurationPersistencePort,
			UserConfigurationPersistencePort userConfigurationPersistencePort,
			PaymentJDEServicePort jdePaymentJDEServicePort, CompanyPersistencePort companyPersistencePort) {
		super();
		this.orderConfigurationPersistencePort = orderConfigurationPersistencePort;
		this.userConfigurationPersistencePort = userConfigurationPersistencePort;
		this.jdePaymentJDEServicePort = jdePaymentJDEServicePort;
		this.companyPersistencePort = companyPersistencePort;
	}

	public AdvertisingPersistencePort getAdvertisingPersistencePort() {
		return advertisingPersistencePort;
	}

	public OrderConfigurationPersistencePort getOrderConfigurationPersistencePort() {
		return orderConfigurationPersistencePort;
	}

	public UserConfigurationPersistencePort getUserConfigurationPersistencePort() {
		return userConfigurationPersistencePort;
	}

	public void setAdvertisingPersistencePort(AdvertisingPersistencePort advertisingPersistencePort) {
		this.advertisingPersistencePort = advertisingPersistencePort;
	}

	public void setOrderConfigurationPersistencePort(
			OrderConfigurationPersistencePort orderConfigurationPersistencePort) {
		this.orderConfigurationPersistencePort = orderConfigurationPersistencePort;
	}

	public void setUserConfigurationPersistencePort(UserConfigurationPersistencePort userConfigurationPersistencePort) {
		this.userConfigurationPersistencePort = userConfigurationPersistencePort;
	}

	public PaymentJDEServicePort getJdePaymentJDEServicePort() {
		return jdePaymentJDEServicePort;
	}

	public CompanyPersistencePort getCompanyPersistencePort() {
		return companyPersistencePort;
	}

	public void setJdePaymentJDEServicePort(PaymentJDEServicePort jdePaymentJDEServicePort) {
		this.jdePaymentJDEServicePort = jdePaymentJDEServicePort;
	}

	public void setCompanyPersistencePort(CompanyPersistencePort companyPersistencePort) {
		this.companyPersistencePort = companyPersistencePort;
	}

}
