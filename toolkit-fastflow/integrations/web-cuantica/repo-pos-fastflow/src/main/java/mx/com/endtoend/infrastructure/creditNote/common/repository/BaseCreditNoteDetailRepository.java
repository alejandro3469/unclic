package mx.com.endtoend.infrastructure.creditNote.common.repository;

import mx.com.endtoend.infrastructure.creditNote.common.entities.CreditNoteDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
@NoRepositoryBean
public interface BaseCreditNoteDetailRepository extends JpaRepository<CreditNoteDetailEntity, Long> {

}