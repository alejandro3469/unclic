package mx.com.endtoend.infrastructure.creditNote.common.repository;

import mx.com.endtoend.infrastructure.creditNote.common.entities.CreditNoteHeaderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import javax.persistence.MappedSuperclass;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Optional;

@MappedSuperclass
@NoRepositoryBean
public interface BaseCreditNoteHeaderRepository extends JpaRepository<CreditNoteHeaderEntity, Long> {

    Optional<CreditNoteHeaderEntity> findByFolioAndCreditNoteCode(BigDecimal folio, String creditNoteCode);

    @Transactional
    @Modifying
    @Query("UPDATE CreditNoteHeaderEntity cnh "
            + "SET "
            + "cnh.usedAmount=:usedAmount, "
            + "cnh.pendingAmount=:pendingAmount "
            + "WHERE "
            + "cnh.folio=:folio "
            + "AND "
            + "cnh.creditNoteCode=:code ")
    int updteBalanceByFolioAndCode(@Param("usedAmount") Double usedAmount, @Param("pendingAmount") Double pendingAmount,
                                   @Param("folio") BigDecimal folio, @Param("code") String code);

    @Transactional
    @Modifying
    @Query("UPDATE CreditNoteHeaderEntity cnh SET cnh.isPrinted=:status WHERE cnh.id=:id")
    int updatePrintStatusById(@Param("status") boolean status, @Param("id") Long id);

}
