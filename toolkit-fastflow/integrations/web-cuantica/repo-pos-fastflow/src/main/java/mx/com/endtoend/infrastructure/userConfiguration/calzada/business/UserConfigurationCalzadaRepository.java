package mx.com.endtoend.infrastructure.userConfiguration.calzada.business;

import mx.com.endtoend.infrastructure.userConfiguration.common.business.BaseUserConfigurationRepository;
import mx.com.endtoend.infrastructure.userConfiguration.common.converters.*;
import mx.com.endtoend.infrastructure.users.common.repository.UserRepository;
import org.springframework.stereotype.Service;
import mx.com.endtoend.infrastructure.userConfiguration.calzada.repositories.CreditNoteTypeRepositoty;
import mx.com.endtoend.infrastructure.userConfiguration.calzada.repositories.EmployeeRepository;
import mx.com.endtoend.infrastructure.userConfiguration.calzada.repositories.PriceTypeRepository;
import mx.com.endtoend.infrastructure.userConfiguration.calzada.repositories.RoleJobTypeRepository;
import mx.com.endtoend.infrastructure.userConfiguration.calzada.repositories.SaleTypeRepository;

/**
 * Clase con la implementación concreta de la interfaz
 * GenericUserConfigurationRepository para la compañía Calzada
 * 
 * @author ddcasas
 *
 */
@Service
public class UserConfigurationCalzadaRepository extends BaseUserConfigurationRepository {

	public UserConfigurationCalzadaRepository(CreditNoteTypeRepositoty _creditNoteTypeCalzadaRepositoty,
											  EmployeeRepository _employeeCalzadaRepository,
											  RoleJobTypeRepository _roleJobTypeCalzadaRepository,
											  PriceTypeRepository _priceTypeCalzadaRepository,
											  SaleTypeRepository _saleTypeCalzadaRepository,
											  CreditNoteTypeConverter _creditNoteTypeConverter,
											  EmployeeConverter _employeeConverter,
											  WarehouseOptionConverter _warehouseOptionConverter,
											  UserConfigurationConverter _userConfigurationConverter,
											  UserRepository _userRepository,
											  RoleJobTypeConverter _roleJobTypeConverter,
											  SaleTypeConverter _saleTypeConverter,
											  PriceTypeConverter _priceTypeConverter) {
		super(UserConfigurationCalzadaRepository.class,
				_creditNoteTypeCalzadaRepositoty,
				_employeeCalzadaRepository,
				_roleJobTypeCalzadaRepository,
				_priceTypeCalzadaRepository,
				_saleTypeCalzadaRepository,
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