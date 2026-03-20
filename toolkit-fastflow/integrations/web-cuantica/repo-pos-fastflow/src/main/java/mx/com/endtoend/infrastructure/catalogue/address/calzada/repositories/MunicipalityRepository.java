package mx.com.endtoend.infrastructure.catalogue.address.calzada.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.com.endtoend.infrastructure.catalogue.address.entities.MunicipalityEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface MunicipalityRepository extends JpaRepository<MunicipalityEntity,Long> {
    @Query("SELECT m FROM MunicipalityEntity m WHERE m.stateCode = :stateCode ORDER BY m.name ASC")
    List<MunicipalityEntity> findByStateCode(@Param("stateCode") String stateCode);
    
    Optional<MunicipalityEntity> findByCode(String code);
    
    @Query("SELECT m FROM MunicipalityEntity m WHERE m.code =:code AND m.stateCode=:stateCode")
    Optional<MunicipalityEntity> findByCodeAndStateCode(@Param("code") String code, @Param("stateCode") String stateCode);
}
