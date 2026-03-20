package mx.com.endtoend.infrastructure.payments.ferresamano.repositories;

import javax.persistence.EntityManager;
import mx.com.endtoend.infrastructure.payments.common.repository.BaseCustomDSLPaymentRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class CustomDSLPaymentFSamanoRepository extends BaseCustomDSLPaymentRepository {

	public CustomDSLPaymentFSamanoRepository(@Qualifier("ferresamanoDataEntityManagerFactory")
											 EntityManager em) {
		super(CustomDSLPaymentFSamanoRepository.class, em);
	}

}
