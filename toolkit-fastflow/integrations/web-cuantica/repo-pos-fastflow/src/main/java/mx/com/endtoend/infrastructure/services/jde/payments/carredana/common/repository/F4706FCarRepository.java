package mx.com.endtoend.infrastructure.services.jde.payments.carredana.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.com.endtoend.infrastructure.services.jde.payments.carredana.common.entities.F4706;
import mx.com.endtoend.infrastructure.services.jde.payments.carredana.common.entities.F4706Id;

@Repository
public interface F4706FCarRepository extends JpaRepository<F4706, F4706Id> {

}
