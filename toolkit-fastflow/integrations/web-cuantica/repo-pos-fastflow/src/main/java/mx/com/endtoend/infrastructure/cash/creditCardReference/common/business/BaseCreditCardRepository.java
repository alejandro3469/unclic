package mx.com.endtoend.infrastructure.cash.creditCardReference.common.business;

import mx.com.endtoend.infrastructure.cash.creditCardReference.common.entities.CreditCardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import javax.persistence.MappedSuperclass;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@NoRepositoryBean
@MappedSuperclass
public interface BaseCreditCardRepository extends JpaRepository<CreditCardEntity, Long> {

    @Transactional
    @Query("FROM CreditCardEntity cc WHERE cc.id =:id")
    Optional<CreditCardEntity> findById(@Param("id") Long id);

    @Query("FROM CreditCardEntity cc WHERE cc.isEnable=:enable")
    List<CreditCardEntity> findAllByEnable(@Param("enable") boolean enable);

    @Query("FROM CreditCardEntity cc WHERE cc.code=:code OR cc.bankingInstitution=:institution")
    Optional<CreditCardEntity> findByCodeOrInstitution(@Param("code") String code,
                                                       @Param("institution") String institution);

    @Query("FROM CreditCardEntity cc WHERE (cc.code=:code OR cc.type=:type) AND cc.bankingInstitution=:institution ")
    Optional<CreditCardEntity> findByCodeOrInstitutionOrType(@Param("code") String code, @Param("type") String type,
                                                             @Param("institution") String institution);

    @Query("FROM CreditCardEntity cc WHERE (cc.code=:code OR cc.bankingInstitution=:institution) AND cc.id !=:id ")
    Optional<CreditCardEntity> findByCodeOrInstitutionAndIdNot(@Param("code") String code,
                                                               @Param("institution") String institution, @Param("id") Long id);

    @Query("FROM CreditCardEntity cc WHERE (cc.code=:code OR cc.bankingInstitution=:institution ) AND cc.type=:type AND cc.id !=:id ")
    Optional<CreditCardEntity> findByCodeOrInstitutionOrTypeAndIdNot(@Param("code") String code, @Param("type") String type,
                                                                     @Param("institution") String institution, @Param("id") Long id);
}
