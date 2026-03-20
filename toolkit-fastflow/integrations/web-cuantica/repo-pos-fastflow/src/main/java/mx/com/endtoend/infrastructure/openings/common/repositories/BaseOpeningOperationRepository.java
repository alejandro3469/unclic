package mx.com.endtoend.infrastructure.openings.common.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;
import mx.com.endtoend.infrastructure.openings.common.entities.OpeningOperationEntity;

@NoRepositoryBean
public interface BaseOpeningOperationRepository extends JpaRepository<OpeningOperationEntity, Long> {

	@Query("SELECT oo FROM OpeningOperationEntity oo WHERE oo.employeeEmail=:employeeEmail AND oo.isActive=:isActive")
	Optional<OpeningOperationEntity> findByEmployeeEmailAndIsActive(@Param("employeeEmail") String employeeEmail,
			@Param("isActive") boolean isActive);

	@Query("SELECT oo FROM OpeningOperationEntity oo WHERE oo.employeeEmail=:employeeEmail AND oo.closingId IS NOT NULL ORDER BY oo.openingId DESC")
	List<OpeningOperationEntity> findClosingOpeningOperationByEmail(@Param("employeeEmail") String employeeEmail);

	@Query("SELECT oo FROM OpeningOperationEntity oo WHERE oo.openingId=:openingId ")
	Optional<OpeningOperationEntity> findById(@Param("openingId") Long openingId);
}
