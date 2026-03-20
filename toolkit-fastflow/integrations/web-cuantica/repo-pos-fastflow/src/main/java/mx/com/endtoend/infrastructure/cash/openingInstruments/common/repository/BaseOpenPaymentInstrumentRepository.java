package mx.com.endtoend.infrastructure.cash.openingInstruments.common.repository;

import mx.com.endtoend.infrastructure.cash.openingInstruments.common.entities.OpenPaymentInstrumentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface BaseOpenPaymentInstrumentRepository extends JpaRepository<OpenPaymentInstrumentEntity, Long> {

    @Query("SELECT opi FROM OpenPaymentInstrumentEntity opi WHERE code=:code OR name=:name ")
    List<OpenPaymentInstrumentEntity> findByCodeOrName(@Param("code") String code, @Param("name") String name);

    @Query("SELECT opi FROM OpenPaymentInstrumentEntity opi WHERE (code=:code OR name=:name) AND id !=:id ")
    List<OpenPaymentInstrumentEntity> findByCodeOrNameAndIdNot(@Param("code") String code, @Param("name") String name,
                                                               @Param("id") Long id);

    @Query("SELECT opi FROM OpenPaymentInstrumentEntity opi WHERE opi.isEnabled=:enabled")
    List<OpenPaymentInstrumentEntity> findAllByEnable(@Param("enabled") boolean enabled);

    @Query("SELECT opi FROM OpenPaymentInstrumentEntity opi WHERE opi.id=:id")
    Optional<OpenPaymentInstrumentEntity> findById(@Param("id") Long id);

}
