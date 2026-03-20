package mx.com.endtoend.infrastructure.cash.emailReport.common.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import mx.com.endtoend.infrastructure.cash.emailReport.common.entities.EmailReportCashDEntity;

@NoRepositoryBean
public interface BaseEmailReportCashRepository extends JpaRepository<EmailReportCashDEntity, Long>{

}
