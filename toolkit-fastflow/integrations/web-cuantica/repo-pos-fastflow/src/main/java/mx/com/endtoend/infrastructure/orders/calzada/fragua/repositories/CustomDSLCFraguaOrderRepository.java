package mx.com.endtoend.infrastructure.orders.calzada.fragua.repositories;

import mx.com.endtoend.infrastructure.orders.common.repository.BaseCustomDSLFOrderRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class CustomDSLCFraguaOrderRepository extends BaseCustomDSLFOrderRepository {

	public CustomDSLCFraguaOrderRepository(@Qualifier("fraguaDataEntityManagerFactory") EntityManager em) {
		super(CustomDSLCFraguaOrderRepository.class, em);
	}
}
