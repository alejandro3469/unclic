package mx.com.endtoend.infrastructure.creditNote.carredana.repositories;

import javax.persistence.EntityManager;

import mx.com.endtoend.infrastructure.creditNote.common.repository.BaseCustomDSLCreditNoteRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class CustomDSLCreditNoteFcarRepository extends BaseCustomDSLCreditNoteRepository {

	public CustomDSLCreditNoteFcarRepository(@Qualifier("carredanaDataEntityManagerFactory")
											 EntityManager em) {
		super(CustomDSLCreditNoteFcarRepository.class, em);
	}

}
