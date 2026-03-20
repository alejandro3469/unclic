package mx.com.endtoend.infrastructure.userConfiguration.common.repository;

import mx.com.endtoend.infrastructure.userConfiguration.common.entities.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import javax.persistence.MappedSuperclass;
import java.util.List;
import java.util.Optional;


@NoRepositoryBean
@MappedSuperclass
public interface BaseEmployeeRepository extends JpaRepository<EmployeeEntity, Long> {

    // CONSULTA PARA VALIDACION DE CONEXION A BD
    @Query(value = "SELECT * FROM employees LIMIT 1", nativeQuery = true)
    EmployeeEntity validConnection();

    @Query("SELECT e FROM EmployeeEntity e WHERE e.id=:id")
    EmployeeEntity findByEmployeeId(@Param("id") Long id);

    @Query("SELECT e FROM EmployeeEntity e WHERE e.userId=:userId")
    Optional<EmployeeEntity> findByUserId(@Param("userId") Long userId);

    @Query("SELECT e FROM EmployeeEntity e WHERE e.userNumber=:userNumber")
    Optional<EmployeeEntity> findByUserNumber(@Param("userNumber") Long userNumber);

    @Query("SELECT e FROM EmployeeEntity e "
            + "JOIN e.roleJob rj "
            + "WHERE "
            + "e.branchCode=:branchCode "
            + "AND "
            + "rj.code in :operationalRole")
    List<EmployeeEntity> findEmployeesByOperationalRoleAndBranchCode(
            @Param("operationalRole") List<String> operationalRole, @Param("branchCode") String branchCode);

    @Query("SELECT e FROM EmployeeEntity e "
            + "JOIN e.roleJob rj "
            + "WHERE "
            + "rj.code in :operationalRole")
    List<EmployeeEntity> findEmployeesByOperationalRole(@Param("operationalRole") List<String> operationalRole);

}
