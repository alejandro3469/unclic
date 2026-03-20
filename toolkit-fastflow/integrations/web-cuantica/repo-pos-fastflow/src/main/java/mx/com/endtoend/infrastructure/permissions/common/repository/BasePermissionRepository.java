package mx.com.endtoend.infrastructure.permissions.common.repository;

import mx.com.endtoend.infrastructure.commons.constants.PermissionEnum;
import mx.com.endtoend.infrastructure.permissions.common.entities.PermissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

@Repository
public interface BasePermissionRepository  extends JpaRepository<PermissionEntity, Serializable> {

    PermissionEntity findById(Long id);

    PermissionEntity findByName(PermissionEnum name);

    PermissionEntity findByName(String name);

    @Query("SELECT DISTINCT new PermissionEntity(p.id, p.name, p.module, p.type) FROM PermissionEntity p INNER JOIN p.roles r INNER JOIN r.users u WHERE u.id=:id")
    public List<PermissionEntity> findAllByUserId(@Param("id") Long id);

    @Query("SELECT DISTINCT new PermissionEntity(p.id, p.name, p.module, p.type) FROM PermissionEntity p INNER JOIN p.roles r WHERE r.id=:id")
    public List<PermissionEntity> findAllByRoleId(@Param("id") Long id);

    @Query("SELECT DISTINCT new PermissionEntity(p.id, p.name, p.module, p.type) FROM PermissionEntity p WHERE p.module != :module")
    public List<PermissionEntity> findAllExcepModule(@Param("module") String module);

    @Query("SELECT DISTINCT new PermissionEntity(p.id, p.name, p.module, p.type) FROM PermissionEntity p WHERE p.module = :module")
    public List<PermissionEntity> findAllByModule(@Param("module") String module);

    @Query("SELECT DISTINCT new PermissionEntity(p.id, p.name, p.module, p.type) "
            + "FROM PermissionEntity p "
            + "INNER JOIN PermissionCompanyEntity pc "
            + "ON "
            + "p.id=pc.permissionId "
            + "WHERE pc.companyId=:companyId")
    public List<PermissionEntity> findAllByCompanyId(@Param("companyId") Long companyId);


}
