package mx.com.endtoend.infrastructure.warehouse.common.repository;

import mx.com.endtoend.infrastructure.warehouse.common.entities.F0006;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.MappedSuperclass;
import java.util.List;
import java.util.Optional;

@MappedSuperclass
public interface BaseF0006Repository extends JpaRepository<F0006, String>{
    @Query("SELECT f6 FROM F0006 f6 WHERE MCCO=:mcco")
    List<F0006> findAllWarehouseByMCCO(@Param("mcco") String mcco);

    @Query("SELECT f6 FROM F0006 f6 WHERE TRIM(f6.mcco)=:mcco AND TRIM(f6.mcmcu)=:mcu")
    F0006 findWarehouseByCodeAndCompanyNumber(@Param("mcco") String mcco, @Param("mcu") String mcu);

    @Query("SELECT f6 FROM F0006 f6 WHERE f6.mcco=:mcco AND f6.mcstyl=:mcstyl ORDER BY f6.mcdl01 ASC")
    List<F0006> findAllWarehouseByMCCOAndMCSTYL(@Param("mcco") String mcco, @Param("mcstyl") String mcstyl);

    @Query("SELECT f6 FROM F0006 f6 WHERE f6.mcco=:mcco AND f6.mcstyl=:mcstyl AND TRIM(f6.mcmcu)=:mcmcu")
    Optional<F0006> getGenericClientNumberByMccoAndMcstylAndMcmcu(@Param("mcco") String mcco, @Param("mcstyl") String mcstyl, @Param("mcmcu") String mcmcu);

    @Query("SELECT f6 FROM F0006 f6 WHERE TRIM(f6.mcmcu)=:mcu")
    F0006 findByMcu(@Param("mcu") String mcu);
}
