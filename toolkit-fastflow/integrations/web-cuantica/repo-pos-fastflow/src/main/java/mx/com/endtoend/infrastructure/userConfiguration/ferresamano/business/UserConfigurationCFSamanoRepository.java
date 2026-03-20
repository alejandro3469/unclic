package mx.com.endtoend.infrastructure.userConfiguration.ferresamano.business;


import mx.com.endtoend.infrastructure.userConfiguration.common.business.BaseUserConfigurationRepository;
import org.springframework.stereotype.Service;

import mx.com.endtoend.infrastructure.userConfiguration.ferresamano.repositories.CreditNoteTypeFSamanoRepositoty;
import mx.com.endtoend.infrastructure.userConfiguration.ferresamano.repositories.EmployeeFSamanoRepository;
import mx.com.endtoend.infrastructure.userConfiguration.ferresamano.repositories.PriceTypeFSamanoRepository;
import mx.com.endtoend.infrastructure.userConfiguration.ferresamano.repositories.RoleJobTypeFSamanoRepository;
import mx.com.endtoend.infrastructure.userConfiguration.ferresamano.repositories.SaleTypeFSamanoRepository;
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
public class UserConfigurationCFSamanoRepository extends BaseUserConfigurationRepository {

	public UserConfigurationCFSamanoRepository(CreditNoteTypeFSamanoRepositoty _creditNoteTypeRepositoty,
											   EmployeeFSamanoRepository _employeeRepository,
											   RoleJobTypeFSamanoRepository _roleJobTypeRepository,
											   PriceTypeFSamanoRepository _priceTypeRepository,
												SaleTypeFSamanoRepository _saleTypeRepository,
											   CreditNoteTypeConverter _creditNoteTypeConverter,
											   EmployeeConverter _employeeConverter,
											   WarehouseOptionConverter _warehouseOptionConverter,
											   UserConfigurationConverter _userConfigurationConverter,
											   UserRepository _userRepository,
											   RoleJobTypeConverter _roleJobTypeConverter,
											   SaleTypeConverter _saleTypeConverter,
											   PriceTypeConverter _priceTypeConverter){
		super(UserConfigurationCFSamanoRepository.class,
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