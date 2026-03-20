package mx.com.endtoend.infrastructure.debug.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.com.endtoend.infrastructure.debug.calzada.mysql.entities.DebugEntity;

@Repository
public interface DebugDemoRepository extends JpaRepository<DebugEntity, Long>{

}
