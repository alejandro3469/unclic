package mx.com.endtoend.infrastructure.debug.ferresamano.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.com.endtoend.infrastructure.debug.calzada.mysql.entities.DebugConfigurationEntity;

@Repository
public interface DebugConfigurationFerresamanoRepository extends JpaRepository<DebugConfigurationEntity, Long>{

    @Query("SELECT dc FROM DebugConfigurationEntity dc WHERE dc.module =:module ")
    Optional<DebugConfigurationEntity> findByModule(@Param("module") String module);

}

