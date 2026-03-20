package mx.com.endtoend.infrastructure.orderConfigurations.common.repository;

import mx.com.endtoend.infrastructure.orderConfigurations.common.entities.DocumentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseDocumentRepository extends JpaRepository<DocumentEntity, Long> {
}
