package mx.com.endtoend.infrastructure.creditNote.common.repository;

import mx.com.endtoend.infrastructure.creditNote.common.entities.CreditNoteEntity;
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
public interface BaseCreditNoteRepository extends JpaRepository<CreditNoteEntity, Long> {

    Optional<CreditNoteEntity> findByOrderNumberAndOrderCode(BigDecimal orderNumber, String orderCode);

    @Transactional
    @Modifying
    @Query("UPDATE CreditNoteEntity cn SET cn.isTotal=:isTotal WHERE cn.id=:id")
    int updateTotalById(@Param("isTotal") boolean isTotal, @Param("id") Long id);


}
