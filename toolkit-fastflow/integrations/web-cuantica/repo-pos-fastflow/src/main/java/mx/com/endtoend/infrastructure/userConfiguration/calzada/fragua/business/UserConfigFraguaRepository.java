package mx.com.endtoend.infrastructure.userConfiguration.calzada.fragua.business;

import mx.com.endtoend.infrastructure.userConfiguration.common.business.BaseUserConfigurationRepository;
import mx.com.endtoend.infrastructure.userConfiguration.common.converters.*;
import mx.com.endtoend.infrastructure.users.common.repository.UserRepository;
import org.springframework.stereotype.Service;

import mx.com.endtoend.infrastructure.userConfiguration.calzada.fragua.repositories.CreditNoteTypeFraguaRepositoty;
import mx.com.endtoend.infrastructure.userConfiguration.calzada.fragua.repositories.EmployeeFraguaRepository;
import mx.com.endtoend.infrastructure.userConfiguration.calzada.fragua.repositories.PriceTypeFraguaRepository;
import mx.com.endtoend.infrastructure.userConfiguration.calzada.fragua.repositories.RoleJobTypeFraguaRepository;
import mx.com.endtoend.infrastructure.userConfiguration.calzada.fragua.repositories.SaleTypeFraguaRepository;

/**
 * Clase con la implementación concreta de la interfaz
 * GenericUserConfigurationRepository para la compañía Fragua
 * 
 * @author ddcasas
 *
 */
@Service
public class UserConfigFraguaRepository extends BaseUserConfigurationRepository  {

	public UserConfigFraguaRepository(CreditNoteTypeFraguaRepositoty _creditNoteTypeFraguaRepositoty,
									  EmployeeFraguaRepository _employeeFraguaRepository,
									  RoleJobTypeFraguaRepository _roleJobTypeFraguaRepository,
									  PriceTypeFraguaRepository _priceTypeFraguaRepository,
									  SaleTypeFraguaRepository _saleTypeFraguaRepository,
									  CreditNoteTypeConverter _creditNoteTypeConverter,
									  EmployeeConverter _employeeConverter,
									  WarehouseOptionConverter _warehouseOptionConverter,
									  UserConfigurationConverter _userConfigurationConverter,
									  UserRepository _userRepository,
									  RoleJobTypeConverter _roleJobTypeConverter,
									  SaleTypeConverter _saleTypeConverter,
									  PriceTypeConverter _priceTypeConverter){
		super(UserConfigFraguaRepository.class,
				_creditNoteTypeFraguaRepositoty,
				_employeeFraguaRepository,
				_roleJobTypeFraguaRepository,
				_priceTypeFraguaRepository,
				_saleTypeFraguaRepository,
				_creditNoteTypeConverter,
				_employeeConverter,
				_warehouseOptionConverter,
				_userConfigurationConverter,
				_userRepository,
				_roleJobTypeConverter,
				_saleTypeConverter,
				_priceTypeConverter);
	}
}