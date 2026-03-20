package mx.com.endtoend.infrastructure.userConfiguration.demo.business;

import mx.com.endtoend.infrastructure.userConfiguration.common.business.BaseUserConfigurationRepository;
import org.springframework.stereotype.Service;

import mx.com.endtoend.infrastructure.userConfiguration.demo.repositories.CreditNoteTypeDemoRepository;
import mx.com.endtoend.infrastructure.userConfiguration.demo.repositories.EmployeeDemoRepository;
import mx.com.endtoend.infrastructure.userConfiguration.demo.repositories.PriceTypeDemoRepository;
import mx.com.endtoend.infrastructure.userConfiguration.demo.repositories.RoleJobTypeDemoRepository;
import mx.com.endtoend.infrastructure.userConfiguration.demo.repositories.SaleTypeDemoRepository;
import mx.com.endtoend.infrastructure.userConfiguration.common.converters.CreditNoteTypeConverter;
import mx.com.endtoend.infrastructure.userConfiguration.common.converters.EmployeeConverter;
import mx.com.endtoend.infrastructure.userConfiguration.common.converters.PriceTypeConverter;
import mx.com.endtoend.infrastructure.userConfiguration.common.converters.RoleJobTypeConverter;
import mx.com.endtoend.infrastructure.userConfiguration.common.converters.SaleTypeConverter;
import mx.com.endtoend.infrastructure.userConfiguration.common.converters.UserConfigurationConverter;
import mx.com.endtoend.infrastructure.userConfiguration.common.converters.WarehouseOptionConverter;
import mx.com.endtoend.infrastructure.users.common.repository.UserRepository;

/**
 * Clase con la implementación concreta de la interfaz
 * GenericUserConfigurationRepository para la compañía DEMO
 * Basado en la estructura de Carredana
 * 
 * @author Sistema
 *
 */
@Service
public class UserConfigurationDemoBusinessRepository extends BaseUserConfigurationRepository {

	public UserConfigurationDemoBusinessRepository(CreditNoteTypeDemoRepository _creditNoteTypeDemoRepository,
												   EmployeeDemoRepository _employeeRepository,
												   RoleJobTypeDemoRepository _roleJobTypeRepository,
												   PriceTypeDemoRepository _priceTypeRepository,
												   SaleTypeDemoRepository _saleTypeRepository,
												   CreditNoteTypeConverter _creditNoteTypeConverter,
												   EmployeeConverter _employeeConverter,
												   WarehouseOptionConverter _warehouseOptionConverter,
												   UserConfigurationConverter _userConfigurationConverter,
												   UserRepository _userRepository,
												   RoleJobTypeConverter _roleJobTypeConverter,
												   SaleTypeConverter _saleTypeConverter,
												   PriceTypeConverter _priceTypeConverter){
		super(UserConfigurationDemoBusinessRepository.class,
				_creditNoteTypeDemoRepository,
				_employeeRepository,
				_roleJobTypeRepository,
				_priceTypeRepository,
				_saleTypeRepository,
				_creditNoteTypeConverter,
				_employeeConverter,
				_warehouseOptionConverter,
				_userConfigurationConverter,
				_userRepository,
				_roleJobTypeConverter,
				_saleTypeConverter,
				_priceTypeConverter
			 	);
	}
}
