package mx.com.endtoend.infrastructure.orders.calzada.repositories;
import javax.persistence.EntityManager;

import mx.com.endtoend.infrastructure.orders.common.repository.BaseCustomDSLFOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;


@Repository
public class CustomDSLFCalzadaOrderRepository extends BaseCustomDSLFOrderRepository {

    @Autowired
    public CustomDSLFCalzadaOrderRepository(@Qualifier("calzadaDataEntityManagerFactory") EntityManager emCalzada) {
        super(CustomDSLFCalzadaOrderRepository.class, emCalzada);
    }
}
