package mx.com.endtoend.infrastructure.payments.calzada.fragua.repositories;

import javax.persistence.EntityManager;

import mx.com.endtoend.infrastructure.payments.common.repository.BaseCustomDSLPaymentRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class CustomDSLPaymentFraguaRepository extends BaseCustomDSLPaymentRepository {

	public CustomDSLPaymentFraguaRepository(@Qualifier("fraguaDataEntityManagerFactory")
											EntityManager em) {
		super(CustomDSLPaymentFraguaRepository.class, em);
	}
}
