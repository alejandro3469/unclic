package mx.com.endtoend.infrastructure.client.Calzada.repositories;

import javax.persistence.EntityManager;
import mx.com.endtoend.infrastructure.client.common.repository.BaseCustomDslClientRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class CustomDslClientRepository extends BaseCustomDslClientRepository {

    public CustomDslClientRepository(@Qualifier("calzadaDataEntityManagerFactory")
                                     EntityManager em) {
        super(CustomDslClientRepository.class, em);
    }
}
