package mx.com.endtoend.infrastructure.creditNote.demo.repositories;

import javax.persistence.EntityManager;

import mx.com.endtoend.infrastructure.creditNote.common.repository.BaseCustomDSLCreditNoteRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class CustomDSLCreditNoteDemoRepository extends BaseCustomDSLCreditNoteRepository {

    public CustomDSLCreditNoteDemoRepository(@Qualifier("demoDataEntityManagerFactory")
                                             EntityManager em) {
        super(CustomDSLCreditNoteDemoRepository.class, em);
    }

}
