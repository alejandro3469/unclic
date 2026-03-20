package mx.com.endtoend.infrastructure.services.jde.payments.demo.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.com.endtoend.infrastructure.services.jde.payments.demo.common.entities.F4706;
import mx.com.endtoend.infrastructure.services.jde.payments.demo.common.entities.F4706Id;

@Repository
public interface F4706DemoRepository extends JpaRepository<F4706, F4706Id> {

}
