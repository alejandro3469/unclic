package mx.com.endtoend.infrastructure.client.demo.repositories;

import mx.com.endtoend.infrastructure.client.common.repository.BaseCustomDslClientRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class CustomDslClientDemoRepository extends BaseCustomDslClientRepository {

    public CustomDslClientDemoRepository(@Qualifier("demoDataEntityManagerFactory") EntityManager entityManager) {
        super(CustomDslClientDemoRepository.class, entityManager);
    }


}
