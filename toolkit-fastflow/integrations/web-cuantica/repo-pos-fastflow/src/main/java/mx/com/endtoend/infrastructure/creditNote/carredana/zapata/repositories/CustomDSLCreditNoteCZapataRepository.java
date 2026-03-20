package mx.com.endtoend.infrastructure.creditNote.carredana.zapata.repositories;

import javax.persistence.EntityManager;

import mx.com.endtoend.infrastructure.creditNote.common.repository.BaseCustomDSLCreditNoteRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class CustomDSLCreditNoteCZapataRepository extends BaseCustomDSLCreditNoteRepository {

	public CustomDSLCreditNoteCZapataRepository(@Qualifier("zapataDataEntityManagerFactory")
												EntityManager em) {
		super(CustomDSLCreditNoteCZapataRepository.class, em);
	}

}
