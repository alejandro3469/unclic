package mx.com.endtoend.infrastructure.payments.carredana.zapata.repositories;

import javax.persistence.EntityManager;
import mx.com.endtoend.infrastructure.payments.common.repository.BaseCustomDSLPaymentRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class CustomDSLPaymentCZapataRepository extends BaseCustomDSLPaymentRepository {

	public CustomDSLPaymentCZapataRepository(@Qualifier("zapataDataEntityManagerFactory")
											 EntityManager em){
		super(CustomDSLPaymentCZapataRepository.class, em);
	}
}
