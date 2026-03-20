package mx.com.endtoend.infrastructure.services.jde.clients.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.com.endtoend.infrastructure.services.jde.clients.demo.entities.F01151;
import mx.com.endtoend.infrastructure.services.jde.clients.demo.entities.F01151Id;

@Repository
public interface F01151DemoRepository extends JpaRepository<F01151, F01151Id> {

	F01151 findFirstByIdNoClientAndIdEarck7(Long noClient, String earck7);
}
