package mx.com.endtoend.genericConfigurations;

import lombok.RequiredArgsConstructor;
import mx.com.endtoend.infrastructure.roles.common.converters.RoleConverter;
import mx.com.endtoend.infrastructure.users.common.repository.UserRepository;
import mx.com.endtoend.infrastructure.users.common.converters.UserConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import mx.com.endtoend.domain.users.ports.api.UserServicePort;
import mx.com.endtoend.domain.users.ports.spi.UserPersistencePort;
import mx.com.endtoend.domain.users.services.UserServiceImpl;
import mx.com.endtoend.infrastructure.users.common.adapter.UserJpaAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class UserConfigurations {
	private final UserRepository userRepository;
	private final UserConverter userConverter;
	private final RoleConverter roleConverter;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	@Bean
	public UserPersistencePort userPersistence() {
		return new UserJpaAdapter(userRepository, userConverter, roleConverter, bCryptPasswordEncoder);
	}

	@Bean
	public UserServicePort userServicePort() {
		return new UserServiceImpl(userPersistence());
	}
}