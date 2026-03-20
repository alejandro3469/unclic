package mx.com.endtoend.infrastructure.creditNote.calzada.fragua.repositories;

import javax.persistence.EntityManager;

import mx.com.endtoend.infrastructure.creditNote.common.repository.BaseCustomDSLCreditNoteRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class CustomDSLCreditNoteCFraguaRepository extends BaseCustomDSLCreditNoteRepository {

	public CustomDSLCreditNoteCFraguaRepository(@Qualifier("fraguaDataEntityManagerFactory")
												EntityManager em) {
		super(CustomDSLCreditNoteCFraguaRepository.class,em);
	}

}
