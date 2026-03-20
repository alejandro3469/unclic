package mx.com.endtoend.genericConfigurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import mx.com.endtoend.domain.creditNote.ports.CreditNotePersistencePort;
import mx.com.endtoend.domain.creditNote.ports.CreditNoteServicePort;
import mx.com.endtoend.domain.creditNote.services.CreditNoteServiceImpl;
import mx.com.endtoend.infrastructure.creditNote.common.adapter.CreditNoteJpaAdapter;

@Configuration
public class CreditNoteConfiguration {

	@Bean
	public CreditNotePersistencePort creditNotePersistencePort() {
		return new CreditNoteJpaAdapter();
	}
	
	@Bean
	public CreditNoteServicePort creditNoteServicePort() {
		return new CreditNoteServiceImpl(creditNotePersistencePort());
	}
}
