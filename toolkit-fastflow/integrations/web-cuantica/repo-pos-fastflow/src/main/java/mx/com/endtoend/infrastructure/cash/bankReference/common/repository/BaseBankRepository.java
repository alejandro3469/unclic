package mx.com.endtoend.infrastructure.cash.bankReference.common.repository;

import mx.com.endtoend.infrastructure.cash.bankReference.common.entities.BankEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import javax.persistence.MappedSuperclass;
import java.util.List;
import java.util.Optional;

@NoRepositoryBean
@MappedSuperclass
public interface BaseBankRepository extends JpaRepository<BankEntity, Long> {

    @Query("SELECT b FROM BankEntity b WHERE b.id=:id")
    Optional<BankEntity> findById(@Param("id") Long id);

    @Query("SELECT b FROM BankEntity b WHERE b.bankingInstitution=:banking")
    Optional<BankEntity> findByBankingInstitution(@Param("banking") String banking);

    @Query("SELECT b FROM BankEntity b WHERE b.bankingInstitution=:banking AND b.id!=:id")
    Optional<BankEntity> findByBankingInstitutionAndIdNot(@Param("banking") String banking, @Param("id") Long id);

    @Query("SELECT b FROM BankEntity b WHERE b.isEnable=:enable")
    List<BankEntity> findAllByEnable(@Param("enable") boolean enable);

    @Query("SELECT b FROM BankEntity b WHERE b.isEnable=:enable AND b.useType=:useType")
    List<BankEntity> findAllByEnableAndUseType(@Param("enable") boolean enable, @Param("useType") String useType);

}
