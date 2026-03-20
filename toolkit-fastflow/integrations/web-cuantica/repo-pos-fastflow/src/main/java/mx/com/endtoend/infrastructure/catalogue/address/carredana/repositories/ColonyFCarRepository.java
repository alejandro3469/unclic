package mx.com.endtoend.infrastructure.catalogue.address.carredana.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.com.endtoend.infrastructure.catalogue.address.entities.ColonyEntity;

import java.util.List;

@Repository
public interface ColonyFCarRepository extends JpaRepository<ColonyEntity, Long> {

    @Query("SELECT c FROM ColonyEntity c WHERE c.stateCode =:stateCode")
    List<ColonyEntity> findByStateCode(@Param("stateCode") String stateCode);
}
