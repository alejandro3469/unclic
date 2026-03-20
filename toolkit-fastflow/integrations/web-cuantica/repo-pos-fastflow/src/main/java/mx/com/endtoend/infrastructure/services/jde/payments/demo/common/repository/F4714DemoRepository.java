package mx.com.endtoend.infrastructure.services.jde.payments.demo.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.com.endtoend.infrastructure.services.jde.payments.demo.common.entities.F4714;
import mx.com.endtoend.infrastructure.services.jde.payments.demo.common.entities.F4714Id;

@Repository
public interface F4714DemoRepository extends JpaRepository<F4714, F4714Id> {

}
