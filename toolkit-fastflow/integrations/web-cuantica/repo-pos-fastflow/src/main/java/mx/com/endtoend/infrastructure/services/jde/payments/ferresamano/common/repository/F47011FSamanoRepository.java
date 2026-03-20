package mx.com.endtoend.infrastructure.services.jde.payments.ferresamano.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.com.endtoend.infrastructure.services.jde.payments.ferresamano.common.entities.F47011;
import mx.com.endtoend.infrastructure.services.jde.payments.ferresamano.common.entities.F47011Id;

@Repository
public interface F47011FSamanoRepository extends JpaRepository<F47011, F47011Id> {
}

