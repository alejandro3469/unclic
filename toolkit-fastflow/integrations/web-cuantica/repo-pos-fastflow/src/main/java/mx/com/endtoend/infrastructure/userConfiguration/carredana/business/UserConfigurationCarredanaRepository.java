package mx.com.endtoend.infrastructure.userConfiguration.carredana.business;

import mx.com.endtoend.infrastructure.userConfiguration.common.business.BaseUserConfigurationRepository;
import org.springframework.stereotype.Service;

import mx.com.endtoend.infrastructure.userConfiguration.carredana.repositories.CreditNoteTypeCarredanaRepositoty;
import mx.com.endtoend.infrastructure.userConfiguration.carredana.repositories.EmployeeCarredanaRepository;
import mx.com.endtoend.infrastructure.userConfiguration.carredana.repositories.PriceTypeCarredanaRepository;
import mx.com.endtoend.infrastructure.userConfiguration.carredana.repositories.RoleJobTypeCarredanaRepository;
import mx.com.endtoend.infrastructure.userConfiguration.carredana.repositories.SaleTypeCarredanaRepository;
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
 * GenericUserConfigurationRepository para la compañía Carredana
 * 
 * @author ddcasas
 *
 */
@Service
public class UserConfigurationCarredanaRepository extends BaseUserConfigurationRepository {

	public UserConfigurationCarredanaRepository(CreditNoteTypeCarredanaRepositoty _creditNoteTypeRepositoty,
												EmployeeCarredanaRepository _employeeRepository,
												RoleJobTypeCarredanaRepository _roleJobTypeRepository,
												PriceTypeCarredanaRepository _priceTypeRepository,
												SaleTypeCarredanaRepository _saleTypeRepository,
												CreditNoteTypeConverter _creditNoteTypeConverter,
												EmployeeConverter _employeeConverter,
												WarehouseOptionConverter _warehouseOptionConverter,
												UserConfigurationConverter _userConfigurationConverter,
												UserRepository _userRepository,
												RoleJobTypeConverter _roleJobTypeConverter,
												SaleTypeConverter _saleTypeConverter,
												PriceTypeConverter _priceTypeConverter){
		super(UserConfigurationCarredanaRepository.class,
				_creditNoteTypeRepositoty,
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