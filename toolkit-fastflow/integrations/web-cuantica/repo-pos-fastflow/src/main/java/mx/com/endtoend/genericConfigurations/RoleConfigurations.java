package mx.com.endtoend.genericConfigurations;

import mx.com.endtoend.infrastructure.permissions.common.coverters.PermissionConverter;
import mx.com.endtoend.infrastructure.roles.common.converters.RoleConverter;
import mx.com.endtoend.infrastructure.roles.common.repository.RoleRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import mx.com.endtoend.domain.roles.ports.api.RoleServicePort;
import mx.com.endtoend.domain.roles.ports.spi.RolePersistencePort;
import mx.com.endtoend.domain.roles.services.RoleServiceImpl;
import mx.com.endtoend.infrastructure.roles.common.adapters.RoleJpaAdapter;

@Configuration
public class RoleConfigurations {

	private final RoleConverter roleConverter;
	private final RoleRepository roleRepository;
	private final PermissionConverter permissionConverter;

	public RoleConfigurations(RoleConverter _roleConverter, RoleRepository _roleRepository, PermissionConverter _permissionConverter) {
		roleConverter = _roleConverter;
		roleRepository = _roleRepository;
		permissionConverter = _permissionConverter;
	}
	
	@Bean
	public RolePersistencePort rolePercistence() {
		return new RoleJpaAdapter(roleConverter, roleRepository, permissionConverter);
	}
	
	@Bean 
	public RoleServicePort roleServicePort() {
		return new RoleServiceImpl(rolePercistence());
	}
}
