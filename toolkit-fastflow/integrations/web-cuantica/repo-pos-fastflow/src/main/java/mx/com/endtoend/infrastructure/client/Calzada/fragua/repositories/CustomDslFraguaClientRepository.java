package mx.com.endtoend.infrastructure.client.Calzada.fragua.repositories;

import mx.com.endtoend.infrastructure.client.common.repository.BaseCustomDslClientRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class CustomDslFraguaClientRepository extends BaseCustomDslClientRepository {

    public CustomDslFraguaClientRepository( @Qualifier("fraguaDataEntityManagerFactory")
                                            EntityManager em) {
        super(CustomDslFraguaClientRepository.class, em);
    }

}
