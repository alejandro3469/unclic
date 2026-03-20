package mx.com.endtoend.infrastructure.services.posLegacy.orders.common.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.com.endtoend.infrastructure.services.posLegacy.orders.common.entities.Usuarios;

@Repository
public interface UsuariosRepository extends JpaRepository<Usuarios, String>{

	@Query("FROM Usuarios u WHERE u.An8 =:an8")
	Optional<Usuarios> findByAn8(@Param("an8") double an8);
}
