package mx.com.endtoend.infrastructure.orders.ferresamano.repositories;

import javax.persistence.EntityManager;

import mx.com.endtoend.infrastructure.orders.common.repository.BaseCustomDSLFOrderRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class CustomDSLFSamanoOrderRepository extends BaseCustomDSLFOrderRepository {

	public CustomDSLFSamanoOrderRepository(@Qualifier("ferresamanoDataEntityManagerFactory")
										   EntityManager em) {
		super(CustomDSLFSamanoOrderRepository.class, em);
	}

}
