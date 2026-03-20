package mx.com.endtoend.infrastructure.cash.creditCardReference.common.business;

import mx.com.endtoend.infrastructure.cash.creditCardReference.common.entities.CreditCardHistoryChangeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import javax.persistence.MappedSuperclass;

@NoRepositoryBean
@MappedSuperclass
public interface BaseCreditCardHistoryChangeRepository extends JpaRepository<CreditCardHistoryChangeEntity, Long> {
}
