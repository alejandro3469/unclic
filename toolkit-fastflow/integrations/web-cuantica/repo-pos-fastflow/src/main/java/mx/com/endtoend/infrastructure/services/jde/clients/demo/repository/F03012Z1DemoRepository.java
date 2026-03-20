package mx.com.endtoend.infrastructure.services.jde.clients.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.com.endtoend.infrastructure.services.jde.clients.demo.entities.F03012Z1;
import mx.com.endtoend.infrastructure.services.jde.clients.demo.entities.F03012Z1Id;

@Repository
public interface F03012Z1DemoRepository extends JpaRepository<F03012Z1, F03012Z1Id> {

	Optional<F03012Z1> findByNoClient(String noClient);
}
