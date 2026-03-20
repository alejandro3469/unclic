package mx.com.endtoend.infrastructure.orders.demo.repositories;

import mx.com.endtoend.infrastructure.orders.common.repository.BaseCustomDSLFOrderRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class CustomDSLDemoOrderRepository extends BaseCustomDSLFOrderRepository {

    public CustomDSLDemoOrderRepository(@Qualifier("demoDataEntityManagerFactory") EntityManager entityManager) {
        super(CustomDSLDemoOrderRepository.class, entityManager);
    }

}
