package mx.com.endtoend.infrastructure.services.jde.clients.ferresamano.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.com.endtoend.infrastructure.services.jde.clients.ferresamano.entities.F0111Z1;
import mx.com.endtoend.infrastructure.services.jde.clients.ferresamano.entities.F0111Z1Id;

@Repository
public interface F0111Z1FSamanoRepository extends JpaRepository<F0111Z1, F0111Z1Id> {

	List<F0111Z1> findByNoClient(Long noClient);
}
