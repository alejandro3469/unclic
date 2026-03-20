package mx.com.endtoend.infrastructure.services.jde.clients.calzada.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.com.endtoend.infrastructure.services.jde.clients.calzada.entities.F0111;
import mx.com.endtoend.infrastructure.services.jde.clients.calzada.entities.F0111Id;

@Repository
public interface F0111Repository extends JpaRepository<F0111, F0111Id>{

	F0111 findFirstByIdNoClient(Long noClient);
}
