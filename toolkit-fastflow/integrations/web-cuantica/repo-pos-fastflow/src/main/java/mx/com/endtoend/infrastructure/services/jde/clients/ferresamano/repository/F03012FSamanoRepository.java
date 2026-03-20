package mx.com.endtoend.infrastructure.services.jde.clients.ferresamano.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.com.endtoend.infrastructure.services.jde.clients.ferresamano.entities.F03012;
import mx.com.endtoend.infrastructure.services.jde.clients.ferresamano.entities.F03012Id;

@Repository
public interface F03012FSamanoRepository extends JpaRepository<F03012, F03012Id> {

	F03012 findByIdNoClient(Long noClient);
}
