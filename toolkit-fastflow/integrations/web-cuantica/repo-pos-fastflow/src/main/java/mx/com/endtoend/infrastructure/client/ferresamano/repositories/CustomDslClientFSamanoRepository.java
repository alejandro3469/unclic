package mx.com.endtoend.infrastructure.client.ferresamano.repositories;

import mx.com.endtoend.infrastructure.client.common.repository.BaseCustomDslClientRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class CustomDslClientFSamanoRepository extends BaseCustomDslClientRepository {

    public CustomDslClientFSamanoRepository(@Qualifier("ferresamanoDataEntityManagerFactory")
                                            EntityManager em) {
        super(CustomDslClientFSamanoRepository.class, em);
    }
}
