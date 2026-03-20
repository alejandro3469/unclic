package mx.com.endtoend.infrastructure.users.common.adapter;

import mx.com.endtoend.infrastructure.users.common.repository.UserRepository;
import mx.com.endtoend.infrastructure.users.common.converters.UserConverter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import mx.com.endtoend.infrastructure.roles.common.converters.RoleConverter;

/**
 * Clase para el control de la persistencia y consulta de datos.
 * 
 * @author ddcasas
 *
 */
public class UserJpaAdapter extends BaseUserAdapter {


	public UserJpaAdapter(UserRepository _userRepository,
                          UserConverter _userConverter, RoleConverter _roleConverter,
                          BCryptPasswordEncoder _bCryptPasswordEncoder) {
		super(UserJpaAdapter.class, _userRepository, _userConverter, _roleConverter, _bCryptPasswordEncoder);
	}

}