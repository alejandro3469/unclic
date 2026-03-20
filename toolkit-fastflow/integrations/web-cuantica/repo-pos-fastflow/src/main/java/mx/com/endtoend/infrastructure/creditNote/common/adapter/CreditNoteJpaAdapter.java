package mx.com.endtoend.infrastructure.creditNote.common.adapter;

import java.math.BigDecimal;
import java.util.List;

import mx.com.endtoend.infrastructure.creditNote.common.persistence.GenericCreditNotePersistenceInterface;
import mx.com.endtoend.infrastructure.creditNote.common.factory.CreditNoteRepositorFactory;
import mx.com.endtoend.infrastructure.creditNote.common.persistence.GenericCreditNotePersistenceInterface;
import mx.com.endtoend.smart.bussiness.model.orders.dto.OrderDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import mx.com.endtoend.domain.creditNote.dto.CreditNoteDto;
import mx.com.endtoend.domain.creditNote.dto.CreditNoteHeaderDto;
import mx.com.endtoend.domain.creditNote.dto.CreditNoteSearchParamsDto;
import mx.com.endtoend.domain.creditNote.dto.CreditNoteSummary;
import mx.com.endtoend.domain.creditNote.dto.ticket.CreditNoteTickteDto;
import mx.com.endtoend.domain.creditNote.ports.CreditNotePersistencePort;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.GlobalError;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

/**
 * Clase encargada de recuperar el repositorio de cada compañía para la
 * perssistencia y consulta de datos de notas de crédito
 * 
 * @author ddcasas
 *
 */
public class CreditNoteJpaAdapter implements CreditNotePersistencePort {

	@Autowired
	private CreditNoteRepositorFactory creditNoteRepositorFactory;

	private final Logger LOG = LoggerFactory.getLogger(CreditNoteJpaAdapter.class);

	@Override
	public ResponseModel createCreditNoteByCompanyCode(CreditNoteDto creditNoteDto, String companyCode,
			String idOperation) {
		LOG.info(String.format("%s INIT createCreditNoteByCompanyCode()", idOperation));
		GenericCreditNotePersistenceInterface creditNoteRepository = creditNoteRepositorFactory
				.getRepositoryByCompanyCode(companyCode);
		if (creditNoteRepository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		CreditNoteDto creditNote = creditNoteRepository.createCreditNote(creditNoteDto, idOperation);
		return new ResponseModel(creditNote);
	}

	@Override
	public ResponseModel getOrderByCompanyCodeAndParams(BigDecimal orderNumber, String orderCode, String companyCode,
														String idOperation) {

		LOG.info(String.format("%s INIT getOrderByCompanyCodeAndParams()", idOperation));
		GenericCreditNotePersistenceInterface creditNoteRepository = creditNoteRepositorFactory
				.getRepositoryByCompanyCode(companyCode);
		if (creditNoteRepository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}

		OrderDto orderDto = creditNoteRepository.getOrderByParams(orderNumber, orderCode, companyCode, idOperation);
		return new ResponseModel(orderDto);

	}

	@Override
	public ResponseModel searchCreditNoteHeaderByFolioAndCodeAndCompanyCode(BigDecimal folio, String creditNoteCode,
			String companyCode, String idOperation) {
		LOG.info(String.format("%s INIT searchCreditNoteHeaderByFolioAndCodeAndCompanyCode()", idOperation));
		GenericCreditNotePersistenceInterface creditNoteRepository = creditNoteRepositorFactory
				.getRepositoryByCompanyCode(companyCode);
		if (creditNoteRepository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		CreditNoteHeaderDto creditNoteHeader = creditNoteRepository
				.searchCreditNoteHeaderByFolioAndCodeAndCompanyCode(folio, creditNoteCode, idOperation);
		return new ResponseModel(creditNoteHeader);
	}

	@Override
	public ResponseModel searchCreditNoteByOrderNumberAndCodeAndCompanyCode(BigDecimal orderNumber, String orderCode,
			String companyCode, String idOperation) {
		LOG.info(String.format("%s INIT searchCreditNoteByOrderNumberAndCodeAndCompanyCode()", idOperation));
		GenericCreditNotePersistenceInterface creditNoteRepository = creditNoteRepositorFactory
				.getRepositoryByCompanyCode(companyCode);
		if (creditNoteRepository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		CreditNoteDto creditNote = creditNoteRepository.searchCreditNoteByOrderNumberAndCodeAndCompanyCode(orderNumber,
				orderCode, idOperation);
		return new ResponseModel(creditNote);
	}

	@Override
	public ResponseModel searchCreditNoteListByParamsAndCompanyCode(CreditNoteSearchParamsDto noteSearchParamsDto,
			String companyCode, String idOperation) {
		LOG.info(String.format("%s INIT searchCreditNoteListByParamsAndCompanyCode()", idOperation));
		GenericCreditNotePersistenceInterface creditNoteRepository = creditNoteRepositorFactory
				.getRepositoryByCompanyCode(companyCode);
		if (creditNoteRepository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		List<CreditNoteSummary> creditNoteSummaries = creditNoteRepository
				.searchCreditNoteListByParams(noteSearchParamsDto, idOperation);
		return new ResponseModel(creditNoteSummaries);
	}

	@Override
	public ResponseModel generateTicketByCompanyCode(CreditNoteTickteDto creditNoteTickteDto, String companyCode,
			String idOperation) {
		LOG.info(String.format("%s INIT generateTicketByCompanyCode()", idOperation));
		GenericCreditNotePersistenceInterface creditNoteRepository = creditNoteRepositorFactory
				.getRepositoryByCompanyCode(companyCode);
		if (creditNoteRepository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		ResponseModel responseReport = creditNoteRepository.generateTicket(creditNoteTickteDto, idOperation);
		return responseReport;
	}

	@Override
	public void updteCreditNoteHeaderBalanceByParamsAndCompanyCode(CreditNoteHeaderDto creditNoteHeaderDto,
			String companyCode, String idOperation) {
		LOG.info(String.format("%s INIT updteCreditNoteHeaderBalanceByParamsAndCompanyCode()", idOperation));
		LOG.info(String.format("%s PARAMS:[%s]", idOperation, creditNoteHeaderDto.toString()));
		GenericCreditNotePersistenceInterface creditNoteRepository = creditNoteRepositorFactory
				.getRepositoryByCompanyCode(companyCode);
		if (creditNoteRepository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		creditNoteRepository.updteCreditNoteHeaderBalanceByParams(creditNoteHeaderDto, idOperation);
	}

	@Override
	public void updatePrintStatusByIdAndCompanyCode(Long id, boolean printStatus, String companyCode,
			String idOperation) {
		LOG.info(String.format("%s INIT updatePrintStatusByIdAndCompanyCode()", idOperation));
		GenericCreditNotePersistenceInterface creditNoteRepository = creditNoteRepositorFactory
				.getRepositoryByCompanyCode(companyCode);
		if (creditNoteRepository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		creditNoteRepository.updatePrintStatusById(id, printStatus, idOperation);
	}

	@Override
	public void updateCreditNoteBalanceByParamsAndCompanyCode(CreditNoteDto creditNoteDto, String companyCode,
			String idOperation) {
		LOG.info(String.format("%s INIT updateCreditNoteBalanceByParamsAndCompanyCode()", idOperation));
		GenericCreditNotePersistenceInterface creditNoteRepository = creditNoteRepositorFactory
				.getRepositoryByCompanyCode(companyCode);
		if (creditNoteRepository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		creditNoteRepository.updateCreditNoteBalanceByParams(creditNoteDto, idOperation);

	}

	@Override
	public ResponseModel searchCreditNoteByIdAndCompanyCode(Long creditNoteId, String companyCode, String idOperation) {
		LOG.info(String.format("%s INIT searchCreditNoteByIdAndCompanyCode()", idOperation));
		GenericCreditNotePersistenceInterface creditNoteRepository = creditNoteRepositorFactory
				.getRepositoryByCompanyCode(companyCode);
		if (creditNoteRepository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		CreditNoteDto creditNote = creditNoteRepository.searchCreditNoteById(creditNoteId, idOperation);
		return new ResponseModel(creditNote);
	}

}
