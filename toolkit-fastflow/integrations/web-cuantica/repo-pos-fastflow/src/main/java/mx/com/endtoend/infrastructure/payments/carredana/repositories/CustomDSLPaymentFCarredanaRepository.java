package mx.com.endtoend.infrastructure.payments.carredana.repositories;

import javax.persistence.EntityManager;
import mx.com.endtoend.infrastructure.payments.common.repository.BaseCustomDSLPaymentRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class CustomDSLPaymentFCarredanaRepository extends BaseCustomDSLPaymentRepository {

	public CustomDSLPaymentFCarredanaRepository(@Qualifier("carredanaDataEntityManagerFactory")
												EntityManager em) {
		super(CustomDSLPaymentFCarredanaRepository.class, em);
	}
}
