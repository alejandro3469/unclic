package mx.com.endtoend.infrastructure.client.carredana.zapata.repositories;

import mx.com.endtoend.infrastructure.client.common.repository.BaseCustomDslClientRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class CustomDslClientCZapataRepository extends BaseCustomDslClientRepository {

    public CustomDslClientCZapataRepository(@Qualifier("zapataDataEntityManagerFactory")
                                            EntityManager em) {
        super(CustomDslClientCZapataRepository.class, em);
    }

}
