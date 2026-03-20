package mx.com.endtoend.infrastructure.cash.creditCardReference.common.business;


import mx.com.endtoend.infrastructure.cash.creditCardReference.common.entities.PaymentOptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import javax.persistence.MappedSuperclass;
import java.util.Optional;

@NoRepositoryBean
@MappedSuperclass
public interface BasePaymentOptionRepository extends JpaRepository<PaymentOptionEntity, Long> {

    @Query("SELECT po FROM PaymentOptionEntity po JOIN po.creditCard cc WHERE po.period=:period AND cc.bankingInstitution=:institution")
    Optional<PaymentOptionEntity> findByPeriodAndBankingInstitution(@Param("period") String period,
                                                                    @Param("institution") String institution);

    @Query("SELECT po FROM PaymentOptionEntity po JOIN po.creditCard cc WHERE po.period=:period AND cc.bankingInstitution=:institution AND cc.type=:type")
    Optional<PaymentOptionEntity> findByPeriodAndBankingInstitutionAndType(@Param("period") String period,
                                                                           @Param("type") String type, @Param("institution") String institution);

    @Query("SELECT po FROM PaymentOptionEntity po JOIN po.creditCard cc WHERE po.period=:period AND cc.bankingInstitution=:institution AND cc.id!=:idInstitution")
    Optional<PaymentOptionEntity> findByPeriodAndBankingInstitutionAndIdInstitutionNot(@Param("period") String period,
                                                                                       @Param("institution") String institution, @Param("idInstitution") Long idInstitution);

    @Query("SELECT po FROM PaymentOptionEntity po JOIN po.creditCard cc WHERE po.period=:period AND cc.bankingInstitution=:institution AND cc.id!=:idInstitution AND cc.type=:type")
    Optional<PaymentOptionEntity> findByPeriodAndBankingInstitutionAndTypeAndIdInstitutionNot(
            @Param("period") String period, @Param("institution") String institution, @Param("type") String type,
            @Param("idInstitution") Long idInstitution);

}
