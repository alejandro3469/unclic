package mx.com.endtoend.infrastructure.creditNote.ferresamano.repositories;

import javax.persistence.EntityManager;

import mx.com.endtoend.infrastructure.creditNote.common.repository.BaseCustomDSLCreditNoteRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class CustomDSLCreditNoteFSamanoRepository extends BaseCustomDSLCreditNoteRepository {

	public CustomDSLCreditNoteFSamanoRepository(@Qualifier("ferresamanoDataEntityManagerFactory")
												EntityManager em) {
		super(CustomDSLCreditNoteFSamanoRepository.class, em);
	}

}

