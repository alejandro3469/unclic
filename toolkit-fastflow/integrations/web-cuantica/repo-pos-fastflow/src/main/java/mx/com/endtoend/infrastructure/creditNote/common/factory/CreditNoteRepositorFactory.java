package mx.com.endtoend.infrastructure.creditNote.common.factory;

import mx.com.endtoend.infrastructure.creditNote.common.persistence.GenericCreditNotePersistenceInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mx.com.endtoend.infrastructure.commons.constants.CompanyCodes;
import mx.com.endtoend.infrastructure.creditNote.calzada.business.CreditNoteCalzadaRepository;
import mx.com.endtoend.infrastructure.creditNote.calzada.fragua.business.CreditNoteFraguaRepository;
import mx.com.endtoend.infrastructure.creditNote.carredana.business.CreditNoteFCarredanaRepository;
import mx.com.endtoend.infrastructure.creditNote.ferresamano.business.CreditNoteCFSamanoRepository;
import mx.com.endtoend.infrastructure.creditNote.carredana.zapata.business.CreditNoteZapataRepository;
import mx.com.endtoend.infrastructure.creditNote.demo.business.CreditNoteDemoBusinessRepository;

@Component
public class CreditNoteRepositorFactory {

	@Autowired
	private CreditNoteCalzadaRepository creditNoteCalzadaRepository;

	@Autowired
	private CreditNoteFraguaRepository creditNoteFraguaRepository;

	@Autowired
	private CreditNoteFCarredanaRepository creditNoteFCarredanaRepository;

	@Autowired
	private CreditNoteZapataRepository creditNoteZapataRepository;

	@Autowired
	private CreditNoteCFSamanoRepository creditNoteCFSamanoRepository;

	@Autowired
	private CreditNoteDemoBusinessRepository creditNoteDemoBusinessRepository;

	private final Logger LOG = LoggerFactory.getLogger(CreditNoteRepositorFactory.class);

	public GenericCreditNotePersistenceInterface getRepositoryByCompanyCode(String companyCode) {
		try {
			CompanyCodes value = CompanyCodes.valueOf(companyCode);
			switch (value) {

			case FCAL:
				LOG.info("RETURN creditNoteCalzadaRepository");
				return creditNoteCalzadaRepository;

			case CFRA:
				LOG.info("RETURN creditNoteFraguaRepository");
				return creditNoteFraguaRepository;

			case FCAR:
				LOG.info("RETURN creditNoteFCarredanaRepository");
				return creditNoteFCarredanaRepository;

			case CZAP:
				LOG.info("RETURN creditNoteZapataRepository");
				return creditNoteZapataRepository;

			case CFSA:
				LOG.info("RETURN creditNoteCFSamanoRepository");
				return creditNoteCFSamanoRepository;

			case DEMO:
				LOG.info("RETURN creditNoteDemoBusinessRepository");
				return creditNoteDemoBusinessRepository;

			default:
				LOG.error("ERROR GETTING REPOSITORY TO COMPANY - > " + companyCode);
				return null;
			}
		} catch (IllegalArgumentException | NullPointerException exeption) {
			return null;
		}
	}
}