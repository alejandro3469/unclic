package mx.com.endtoend.infrastructure.orders.carredana.repositories;

import javax.persistence.EntityManager;

import mx.com.endtoend.infrastructure.orders.common.repository.BaseCustomDSLFOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;


@Repository
public class CustomDSLFCarredanaOrderRepository extends BaseCustomDSLFOrderRepository {

    @Autowired
    public CustomDSLFCarredanaOrderRepository(@Qualifier("carredanaDataEntityManagerFactory") EntityManager emCarredana) {
        super(CustomDSLFCarredanaOrderRepository.class, emCarredana);
    }
}
