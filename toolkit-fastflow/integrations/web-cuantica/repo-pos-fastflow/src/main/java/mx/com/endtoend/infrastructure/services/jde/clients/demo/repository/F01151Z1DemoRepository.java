package mx.com.endtoend.infrastructure.services.jde.clients.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.com.endtoend.infrastructure.services.jde.clients.demo.entities.F01151Z1;
import mx.com.endtoend.infrastructure.services.jde.clients.demo.entities.F01151Z1Id;

@Repository
public interface F01151Z1DemoRepository extends JpaRepository<F01151Z1, F01151Z1Id> {

	List<F01151Z1> findByNoClient(String noClient);
}
