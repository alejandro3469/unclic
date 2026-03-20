package mx.com.endtoend.infrastructure.orders.carredana.zapata.repositories;

import javax.persistence.EntityManager;

import mx.com.endtoend.infrastructure.orders.common.repository.BaseCustomDSLFOrderRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class CustomDSLCZapataOrderRepository extends BaseCustomDSLFOrderRepository {


	public CustomDSLCZapataOrderRepository(@Qualifier("zapataDataEntityManagerFactory")
										   EntityManager em) {
		super(CustomDSLCZapataOrderRepository.class, em);
	}

}
