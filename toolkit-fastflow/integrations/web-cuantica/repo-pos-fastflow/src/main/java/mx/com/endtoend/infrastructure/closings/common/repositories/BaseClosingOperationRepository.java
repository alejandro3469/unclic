package mx.com.endtoend.infrastructure.closings.common.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import mx.com.endtoend.infrastructure.closings.common.entities.ClosingOperationEntity;

@NoRepositoryBean
public interface BaseClosingOperationRepository extends JpaRepository<ClosingOperationEntity, Long> {

	@Query("SELECT co FROM ClosingOperationEntity co WHERE co.closingId=:closingId")
	Optional<ClosingOperationEntity> findById(@Param("closingId") Long closingId);

}
