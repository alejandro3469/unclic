package mx.com.endtoend.infrastructure.logs.security.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.com.endtoend.infrastructure.logs.security.entities.EmployeeLogEntity;

@Repository
public interface EmployeeLogRepository extends JpaRepository<EmployeeLogEntity, Long> {

}
