package mx.com.endtoend.infrastructure.creditNote.calzada.repositories;

import javax.persistence.EntityManager;

import mx.com.endtoend.infrastructure.creditNote.common.repository.BaseCustomDSLCreditNoteRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import mx.com.endtoend.domain.creditNote.dto.CreditNoteSummary;

@Service
public class CustomDSLCreditNoteRepository extends BaseCustomDSLCreditNoteRepository {

	public CustomDSLCreditNoteRepository(@Qualifier("calzadaDataEntityManagerFactory")
										 EntityManager em) {
		super(CreditNoteSummary.class,  em);
	}

}
