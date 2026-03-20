package mx.com.endtoend.infrastructure.payments.calzada.repositories;

import javax.persistence.EntityManager;
import mx.com.endtoend.infrastructure.payments.common.repository.BaseCustomDSLPaymentRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class CustomDSLPaymentRepository extends BaseCustomDSLPaymentRepository {

	public CustomDSLPaymentRepository(@Qualifier("calzadaDataEntityManagerFactory")
									  EntityManager em) {
		super(CustomDSLPaymentRepository.class, em);
	}

}
