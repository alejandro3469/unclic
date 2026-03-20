package mx.com.endtoend.infrastructure.roles.common.adapters;

import mx.com.endtoend.infrastructure.roles.common.converters.RoleConverter;
import mx.com.endtoend.infrastructure.roles.common.repository.RoleRepository;
import mx.com.endtoend.infrastructure.permissions.common.coverters.PermissionConverter;

/**
 * Clase que controla la persistencia de datos del módulo de roles en la BD
 * principal del sistema.
 * 
 * @author ddcasas
 *
 */

public class RoleJpaAdapter extends BaseRoleJpaAdapter {

	public RoleJpaAdapter(RoleConverter _roleConverter, RoleRepository _roleRepository, PermissionConverter _permissionConverter) {
		super(RoleJpaAdapter.class, _roleConverter, _roleRepository, _permissionConverter);
	}

}