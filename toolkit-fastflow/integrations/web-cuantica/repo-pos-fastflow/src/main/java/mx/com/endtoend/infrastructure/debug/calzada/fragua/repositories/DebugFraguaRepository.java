package mx.com.endtoend.infrastructure.debug.calzada.fragua.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.com.endtoend.infrastructure.debug.calzada.mysql.entities.DebugEntity;

@Repository
public interface DebugFraguaRepository extends JpaRepository<DebugEntity, Long>{

}
