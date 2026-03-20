package mx.com.endtoend.infrastructure.logs.security.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.com.endtoend.infrastructure.logs.security.entities.EmployeePreviousStateEntity;

@Repository
public interface EmployeePreviousStateRepository extends JpaRepository<EmployeePreviousStateEntity, Long> {

}
