	package mx.com.endtoend.domain.orders.dto;

import mx.com.endtoend.domain.company.ports.spi.CompanyPersistencePort;
import mx.com.endtoend.domain.logs.orders.ports.OrderLogServicePort;
import mx.com.endtoend.domain.orders.ports.OrderPersistencePort;
import mx.com.endtoend.domain.roles.ports.spi.PermissionPersistencePort;
import mx.com.endtoend.domain.roles.ports.spi.RolePersistencePort;
import mx.com.endtoend.domain.userConfigurations.ports.spi.UserConfigurationPersistencePort;
import mx.com.endtoend.infrastructure.services.email.EmailServicePort;
import mx.com.endtoend.infrastructure.services.jde.orders.common.serviceport.OrderJdeServicePort;
import mx.com.endtoend.infrastructure.services.posLegacy.orders.common.serviceport.OrderPosLegacyServicePort;

	/**
	 * Clase de comunicación de interfaces a capa de persisteia y variables comunes
	 * a la administrción de ordenes del sistema
	 * 
	 * @author ddcasas
	 */
	public class CustomInterfaceOrderParams {

		private OrderJdeServicePort orderJdeServicePort;
		private OrderPosLegacyServicePort orderPosLegacyServicePort;
		private EmailServicePort emailServicePort;
		private OrderPersistencePort orderPersistencePort;
		private UserConfigurationPersistencePort userConfigurationPersistencePort;
		private CompanyPersistencePort companyPersistencePort;
		private OrderLogServicePort orderLogServicePort;
		private RolePersistencePort rolePersistencePort;
		private PermissionPersistencePort permissionPersistencePort;
		private String userLogged;
		private String companyCode;
		private String idOperation;

		public CustomInterfaceOrderParams(Builder builder) {
			this.orderJdeServicePort = builder.orderJdeServicePort;
			this.orderPosLegacyServicePort = builder.orderPosLegacyServicePort;
			this.emailServicePort = builder.emailServicePort;
			this.orderPersistencePort = builder.orderPersistencePort;
			this.userConfigurationPersistencePort = builder.userConfigurationPersistencePort;
			this.companyPersistencePort = builder.companyPersistencePort;
			this.orderLogServicePort = builder.orderLogServicePort;
			this.userLogged = builder.userLogged;
			this.companyCode = builder.companyCode;
			this.idOperation = builder.idOperation;
			this.rolePersistencePort = builder.rolePersistencePort;
			this.permissionPersistencePort = builder.permissionPersistencePort;
		}

		public OrderJdeServicePort getOrderJdeServicePort() {
			return orderJdeServicePort;
		}

		public OrderPosLegacyServicePort getOrderPosLegacyServicePort() {
			return orderPosLegacyServicePort;
		}

		public EmailServicePort getEmailServicePort() {
			return emailServicePort;
		}

		public OrderPersistencePort getOrderPersistencePort() {
			return orderPersistencePort;
		}

		public UserConfigurationPersistencePort getUserConfigurationPersistencePort() {
			return userConfigurationPersistencePort;
		}

		public CompanyPersistencePort getCompanyPersistencePort() {
			return companyPersistencePort;
		}

		public RolePersistencePort getRolePersistencePort() {
			return rolePersistencePort;
		}

		public PermissionPersistencePort getPermissionPersistencePort() {
			return permissionPersistencePort;
		}

		public String getCompanyCode() {
			return companyCode;
		}

		public String getIdOperation() {
			return idOperation;
		}

		public OrderLogServicePort getOrderLogServicePort() {
			return orderLogServicePort;
		}

		public String getUserLogged() {
			return userLogged;
		}

		public void setOrderPersistencePort(OrderPersistencePort orderPersistencePort) {
			this.orderPersistencePort = orderPersistencePort;
		}

		public static class Builder {
			public OrderLogServicePort orderLogServicePort;
			public OrderJdeServicePort orderJdeServicePort;
			public OrderPosLegacyServicePort orderPosLegacyServicePort;
			public EmailServicePort emailServicePort;
			public OrderPersistencePort orderPersistencePort;
			public UserConfigurationPersistencePort userConfigurationPersistencePort;
			public CompanyPersistencePort companyPersistencePort;
			public RolePersistencePort rolePersistencePort;
			public PermissionPersistencePort permissionPersistencePort;
			public String userLogged;
			public String companyCode;
			public String idOperation;

			public Builder setOrderLogServicePort(OrderLogServicePort orderLogServicePort) {
				this.orderLogServicePort = orderLogServicePort;
				return this;
			}

			public Builder setOrderJdeServicePort(OrderJdeServicePort orderJdeServicePort) {
				this.orderJdeServicePort = orderJdeServicePort;
				return this;
			}

			public Builder setOrderPosLegacyServicePort(OrderPosLegacyServicePort orderPosLegacyServicePort) {
				this.orderPosLegacyServicePort = orderPosLegacyServicePort;
				return this;
			}

			public Builder setEmailServicePort(EmailServicePort emailServicePort) {
				this.emailServicePort = emailServicePort;
				return this;
			}

			public Builder setOrderPersistencePort(OrderPersistencePort orderPersistencePort) {
				this.orderPersistencePort = orderPersistencePort;
				return this;
			}

			public Builder setUserConfigurationPersistencePort(
					UserConfigurationPersistencePort userConfigurationPersistencePort) {
				this.userConfigurationPersistencePort = userConfigurationPersistencePort;
				return this;
			}

			public Builder setCompanyPersistencePort(CompanyPersistencePort companyPersistencePort) {
				this.companyPersistencePort = companyPersistencePort;
				return this;
			}

			public Builder setRolePersistencePort(RolePersistencePort rolePersistencePort) {
				this.rolePersistencePort = rolePersistencePort;
				return this;
			}

			public Builder setPermissionPersistencePort(PermissionPersistencePort permissionPersistencePort) {
				this.permissionPersistencePort = permissionPersistencePort;
				return this;
			}

			public Builder setCompanyCode(String companyCode) {
				this.companyCode = companyCode;
				return this;
			}

			public Builder setIdOperation(String idOperation) {
				this.idOperation = idOperation;
				return this;
			}

			public Builder setUserLogged(String userLogged) {
				this.userLogged = userLogged;
				return this;
			}

			public CustomInterfaceOrderParams build() {
				return new CustomInterfaceOrderParams(this);
			}
		}
	}
