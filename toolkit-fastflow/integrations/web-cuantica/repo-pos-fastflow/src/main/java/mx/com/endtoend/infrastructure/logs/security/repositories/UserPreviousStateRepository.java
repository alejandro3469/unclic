package mx.com.endtoend.infrastructure.logs.security.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.com.endtoend.infrastructure.logs.security.entities.UserPreviousStateEntity;

@Repository
public interface UserPreviousStateRepository extends JpaRepository<UserPreviousStateEntity, Long>{

}
