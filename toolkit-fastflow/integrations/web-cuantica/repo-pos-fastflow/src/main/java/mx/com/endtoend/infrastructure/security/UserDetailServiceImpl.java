package mx.com.endtoend.infrastructure.security;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import mx.com.endtoend.domain.roles.ports.api.PermissionServicePort;
import mx.com.endtoend.domain.users.dto.UserDto;
import mx.com.endtoend.domain.users.ports.api.UserServicePort;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.ValidationError;
import mx.com.endtoend.infrastructure.permissions.common.coverters.PermissionConverter;
import mx.com.endtoend.infrastructure.permissions.common.entities.PermissionEntity;
import mx.com.endtoend.infrastructure.users.common.converters.UserConverter;
import mx.com.endtoend.infrastructure.users.common.entities.UserEntity;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

	private String module = "USER-LOGIN";

	@Autowired
	private UserServicePort userServicePort;

	@Autowired
	private UserConverter userConverter;

	@Autowired
	private PermissionServicePort permissionServicePort;

	@Autowired
	private PermissionConverter permissionConverter;

	private Logger LOG = LoggerFactory.getLogger(UserDetailServiceImpl.class);

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		UserDto userDto = (UserDto) userServicePort.findUserByEmailToLogin(username, module).getData();
		UserEntity userEntity = new UserEntity();
		List<GrantedAuthority> authorities;

		if (userDto == null) {

			LOG.warn(String.format("El usuario %s no existe o la contraseña es invalida", username));
			throw new ValidationError("Bad credentials");

		} else if (userDto.getIsSessionActive()) {

			LOG.warn("USER HAVE A SESSION ACTIVE  ");
			throw new ValidationError("SESSION ACTIVE");
		}

		userEntity = userConverter.userDtoToUserEntity(userDto, false);
		LOG.info(String.format("Inicio de sesión correcto, usuario -- %s -- ", username));
        userServicePort.changeStatusSessionByUsernameAndBranchCode(userDto.getEmail(), userDto.getBranch().getCode(), true, "USER-LOGIN");

		List<PermissionEntity> permissionList = permissionConverter
				.permissionDtoListToPermissionEntityList(permissionServicePort.findAllByUserId(userEntity.getId()));

		authorities = permissionList.stream()
				.map(permission -> new SimpleGrantedAuthority(permission.getName().toString()))
				.collect(Collectors.toList());

		var result = new User(userEntity.getEmail(), userEntity.getPassword(), userEntity.isEnabled(), true, true, true,
				authorities);
		return result;
	}

	public void ChangeSessionStatus(String username) {
		UserDto userDto = (UserDto) userServicePort.findUserByEmailToLogin(username, module).getData();
		LOG.info(String.format("Se actualiza sesión por contraseña incorrecta, usuario -- %s -- ", username));
		userServicePort.changeStatusSessionByUsernameAndBranchCode(userDto.getEmail(), userDto.getBranch().getCode(), false, "USER-LOGIN");

	}
}
