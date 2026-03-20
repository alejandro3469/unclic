package mx.com.endtoend.infrastructure.userConfiguration.common.repository;

import mx.com.endtoend.infrastructure.userConfiguration.common.entities.UserConfigurationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import javax.persistence.MappedSuperclass;

@NoRepositoryBean
@MappedSuperclass
public interface BaseUserConfigurationRepository extends JpaRepository<UserConfigurationEntity, Long> {
}
