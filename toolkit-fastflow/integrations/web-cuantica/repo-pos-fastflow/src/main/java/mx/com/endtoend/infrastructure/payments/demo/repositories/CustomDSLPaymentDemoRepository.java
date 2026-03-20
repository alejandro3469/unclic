package mx.com.endtoend.infrastructure.payments.demo.repositories;

import mx.com.endtoend.infrastructure.payments.common.repository.BaseCustomDSLPaymentRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class CustomDSLPaymentDemoRepository extends BaseCustomDSLPaymentRepository {

    public CustomDSLPaymentDemoRepository(@Qualifier("demoDataEntityManagerFactory") EntityManager entityManager) {
        super(CustomDSLPaymentDemoRepository.class, entityManager);
    }

}
