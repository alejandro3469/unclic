package mx.com.endtoend.infrastructure.services.jde.payments.calzada.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.com.endtoend.infrastructure.services.jde.payments.calzada.common.entities.F4714;
import mx.com.endtoend.infrastructure.services.jde.payments.calzada.common.entities.F4714Id;

@Repository
public interface F4714Repository extends JpaRepository<F4714, F4714Id>{

}
