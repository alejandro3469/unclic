package mx.com.endtoend.infrastructure.branch.common.repositories;

import mx.com.endtoend.infrastructure.branch.common.entities.BranchEntity;
import mx.com.endtoend.infrastructure.commons.constants.CompanyCodes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BaseBranchRepository extends JpaRepository<BranchEntity, Long> {
    @Query("SELECT b FROM BranchEntity b JOIN b.company c WHERE b.name =:name AND c.code =:companyCode")
    Optional<BranchEntity> findByNameAndCompanyCode(@Param("name") String name, @Param("companyCode") CompanyCodes CompanyCode);

    @Query("SELECT b FROM BranchEntity b JOIN b.company c WHERE b.code =:code AND c.code =:companyCode")
    Optional<BranchEntity> findByCodeAndCompanyCode(@Param("code") String code, @Param("companyCode") CompanyCodes CompanyCode);

    @Query("SELECT b FROM BranchEntity b JOIN b.company c WHERE b.name =:name AND c.code =:companyCode AND b.id !=:id")
    Optional<BranchEntity> findByNameAndCompanyCodeAndIdNot(@Param("name") String name, @Param("companyCode") CompanyCodes CompanyCode, @Param("id") Long id);

    @Query("SELECT b FROM BranchEntity b JOIN b.company c WHERE b.code =:code AND c.code =:companyCode AND b.id !=:id")
    Optional<BranchEntity> findByCodeAndCompanyCodeAndIdNot(@Param("code") String code, @Param("companyCode") CompanyCodes CompanyCode, @Param("id") Long id);

    @Query("SELECT b FROM BranchEntity b JOIN b.company c WHERE c.code =:companyCode ORDER BY b.name ASC")
    List<BranchEntity> findAllByCompanyCode(@Param("companyCode") CompanyCodes companyCode);

    @Query("SELECT b FROM BranchEntity b JOIN b.company c WHERE b.id =:id")
    Optional<BranchEntity> findById(@Param("id") Long id);
}
