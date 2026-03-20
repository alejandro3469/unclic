package mx.com.endtoend.infrastructure.services.jde.payments.demo.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.com.endtoend.infrastructure.services.jde.payments.demo.common.entities.F47011;
import mx.com.endtoend.infrastructure.services.jde.payments.demo.common.entities.F47011Id;

@Repository
public interface F47011DemoRepository extends JpaRepository<F47011, F47011Id> {
}
