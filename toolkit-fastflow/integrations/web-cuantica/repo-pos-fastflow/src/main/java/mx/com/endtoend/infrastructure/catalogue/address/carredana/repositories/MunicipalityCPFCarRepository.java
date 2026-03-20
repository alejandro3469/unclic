package mx.com.endtoend.infrastructure.catalogue.address.carredana.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.com.endtoend.infrastructure.catalogue.address.entities.MunicipalityCPEntity;

@Repository
public interface MunicipalityCPFCarRepository extends JpaRepository<MunicipalityCPEntity, Long>{
	
    @Query("SELECT mc FROM MunicipalityCPEntity mc WHERE mc.stateCode = :stateCode ORDER BY mc.name ASC")
    List<MunicipalityCPEntity> findByStateCode(@Param("stateCode") String stateCode);
    
    @Query("SELECT mc FROM MunicipalityCPEntity mc WHERE mc.stateCode = :stateCode AND mc.cp = :cp")
    Optional<MunicipalityCPEntity> findByStateCodeAndCp(@Param("stateCode") String stateCode, @Param("cp") String cp);

}
