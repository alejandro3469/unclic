package mx.com.endtoend.infrastructure.roles.common.repository;

import mx.com.endtoend.infrastructure.roles.common.entities.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Repository
public interface BaseRoleRepository extends JpaRepository<RoleEntity, Serializable> {

    Optional<RoleEntity> findById(Long id);

    @Query("SELECT r FROM RoleEntity r WHERE r.name=:name AND r.companyKey =:companyKey")
    Optional<RoleEntity> findByNameAndCompanyKey(@Param("name") String name, @Param("companyKey") String companyKey);

    @Query("SELECT r FROM RoleEntity r WHERE r.enabled=:enabled AND r.companyKey =:companyKey")
    public List<RoleEntity> findAllByEnabledAndCompanyKey(@Param("enabled") boolean enabled, @Param("companyKey") String companyKey);

    @Query("SELECT r FROM RoleEntity r WHERE r.name = :name AND r.companyKey =:companyKey")
    Optional<RoleEntity> existsByNameAndCompanyKey(@Param("name") String name,@Param("companyKey") String companyKey);

    public boolean existsByNameAndCompanyKeyAndIdNot(String name, String companyKey, Long id);

    @Transactional
    @Modifying
    @Query("UPDATE RoleEntity r SET r.enabled=:enable WHERE r.id=:id")
    int enableById(@Param("id") Long id, @Param("enable") boolean enable);

    @Query("SELECT new RoleEntity(r.id, r.name) FROM RoleEntity r INNER JOIN r.users u WHERE u.id=:id")
    public List<RoleEntity> findAllByUserId(@Param("id") Long id);
}
