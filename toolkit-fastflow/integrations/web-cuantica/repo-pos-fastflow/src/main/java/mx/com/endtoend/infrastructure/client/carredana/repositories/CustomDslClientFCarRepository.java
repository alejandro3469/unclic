package mx.com.endtoend.infrastructure.client.carredana.repositories;

import mx.com.endtoend.infrastructure.client.common.repository.BaseCustomDslClientRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class CustomDslClientFCarRepository extends BaseCustomDslClientRepository {

    public CustomDslClientFCarRepository(@Qualifier("carredanaDataEntityManagerFactory")
                                         EntityManager em) {
        super(CustomDslClientFCarRepository.class, em);
    }

}
