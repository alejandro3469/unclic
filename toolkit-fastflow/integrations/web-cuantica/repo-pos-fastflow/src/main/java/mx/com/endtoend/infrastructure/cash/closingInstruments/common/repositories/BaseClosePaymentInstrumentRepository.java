package mx.com.endtoend.infrastructure.cash.closingInstruments.common.repositories;

import mx.com.endtoend.infrastructure.cash.closingInstruments.common.entities.ClosePaymentInstrumentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import javax.persistence.MappedSuperclass;
import java.util.List;
import java.util.Optional;

@NoRepositoryBean
@MappedSuperclass
public interface BaseClosePaymentInstrumentRepository extends JpaRepository<ClosePaymentInstrumentEntity, Long> {

    @Query("SELECT cpi FROM ClosePaymentInstrumentEntity cpi WHERE cpi.code=:code OR cpi.name=:name ")
    List<ClosePaymentInstrumentEntity> findByCodeOrName(@Param("code") String code, @Param("name") String name);

    @Query("SELECT cpi FROM ClosePaymentInstrumentEntity cpi WHERE (cpi.code=:code OR cpi.name=:name) AND cpi.id !=:id ")
    List<ClosePaymentInstrumentEntity> findByCodeOrNameAndIdNot(@Param("code") String code, @Param("name") String name,
                                                                @Param("id") Long id);

    @Query("SELECT cpi FROM ClosePaymentInstrumentEntity cpi WHERE cpi.isEnabled=:enabled")
    List<ClosePaymentInstrumentEntity> findAllByEnable(@Param("enabled") boolean enabled);

    @Query("SELECT cpi FROM ClosePaymentInstrumentEntity cpi WHERE cpi.id=:id")
    Optional<ClosePaymentInstrumentEntity> findById(@Param("id") Long id);
}
