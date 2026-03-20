package mx.com.endtoend.domain.creditNote.services;

import java.math.BigDecimal;

import mx.com.endtoend.smart.bussiness.model.orders.dto.OrderDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.com.endtoend.domain.creditNote.dto.AuthorizationParmasDto;
import mx.com.endtoend.domain.creditNote.dto.CreditNoteCustomParams;
import mx.com.endtoend.domain.creditNote.dto.CreditNoteSearchParamsDto;
import mx.com.endtoend.domain.creditNote.ports.CreditNotePersistencePort;
import mx.com.endtoend.domain.creditNote.ports.CreditNoteServicePort;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.GlobalError;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

/**
 * Clase de servicio para la recuperación de la implementación concreta de la
 * lógica de negocio configurada a cada compañía registrada en el sistema
 * 
 * @author ddcasas
 *
 */
public class CreditNoteServiceImpl implements CreditNoteServicePort {

	private CreditNotePersistencePort creditNotePersistencePort;

	public CreditNoteServiceImpl(CreditNotePersistencePort creditNotePersistencePort) {
		this.creditNotePersistencePort = creditNotePersistencePort;
    }

	private final Logger LOG = LoggerFactory.getLogger(CreditNoteServiceImpl.class);

	private CreditNoteFactory creditNoteFactory = new CreditNoteFactory();

	@Override
	public ResponseModel creteCreditNoteByOrder(CreditNoteCustomParams noteCustomParams, String method,
			String companyCode, String idOperation) {
		LOG.info(String.format("%s INIT creteCreditNoteByOrder()", idOperation));

		noteCustomParams.setCreditNotePersistencePort(creditNotePersistencePort);
		CreditNoteInterface creditNote = creditNoteFactory.getImplementationByCode(method, noteCustomParams);

		if (creditNote == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}

		ResponseModel responseFromImplementation = creditNote.creteCreditNoteByOrder(noteCustomParams, companyCode,
				idOperation);
		return responseFromImplementation;
	}

	@Override
	public ResponseModel resendNotesToQueue(CreditNoteCustomParams noteCustomParams, String method, String companyCode, String idOperation) {
		return null;
	}


	@Override
	public ResponseModel resendNotesToQueue(CreditNoteCustomParams noteCustomParams, String method,
											String companyCode, String idOperation, BigDecimal folio) {
		LOG.info(String.format("%s INIT creteCreditNoteByOrder()", idOperation));

		noteCustomParams.setCreditNotePersistencePort(creditNotePersistencePort);
		CreditNoteInterface creditNote = creditNoteFactory.getImplementationByCode(method, noteCustomParams);

		if (creditNote == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}

		ResponseModel responseFromImplementation = creditNote.resendNotesToQueue(noteCustomParams, companyCode,
				idOperation, folio);
		return responseFromImplementation;
	}

	@Override
	public OrderDto getOrder(String orderNumber, String orderCode, String idOperation) {
		LOG.info(String.format("%s INIT creteCreditNoteByOrder()", idOperation));

		OrderDto order = (OrderDto) creditNotePersistencePort
				.getOrderByCompanyCodeAndParams( new BigDecimal(orderNumber), orderCode, "FCAL", idOperation).getData();

		if (order == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		return order;
	}

	@Override
	public ResponseModel aproveCreditNoteByParamsAndCompanyCode(CreditNoteCustomParams noteCustomParams,
			AuthorizationParmasDto authorizationParmasDto, String method, String companyCode, String idOperation) {
		LOG.info(String.format("%s INIT aproveCreditNoteByParamsAndCompanyCode()", idOperation));

		noteCustomParams.setCreditNotePersistencePort(creditNotePersistencePort);
		CreditNoteInterface creditNote = creditNoteFactory.getImplementationByCode(method, noteCustomParams);

		if (creditNote == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}

		ResponseModel responseFromImplementation = creditNote.aproveCreditNoteByParamsAndCompanyCode(noteCustomParams,
				authorizationParmasDto, companyCode, idOperation);

		return responseFromImplementation;
	}

	@Override
	public ResponseModel searchOrderSummaryByNumberAndCode(CreditNoteCustomParams noteCustomParams, String method,
			String companyCode, String idOperation) {
		LOG.info(String.format("%s INIT searchOrderSummaryByNumberAndCode()", idOperation));
		noteCustomParams.setCreditNotePersistencePort(creditNotePersistencePort);
		CreditNoteInterface creditNote = creditNoteFactory.getImplementationByCode(method, noteCustomParams);
		if (creditNote == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		ResponseModel responseFromImplementation = creditNote.searchOrderSummaryByNumberAndCode(noteCustomParams,
				companyCode, idOperation);
		return responseFromImplementation;
	}

	@Override
	public ResponseModel searchByParamsAndCompanyCode(CreditNoteCustomParams customParams,
			CreditNoteSearchParamsDto noteSearchParamsDto, String method, String companyCode, String idOperation) {
		LOG.info(String.format("%s INIT searchByParamsAndCompanyCode()", idOperation));
		customParams.setCreditNotePersistencePort(creditNotePersistencePort);
		CreditNoteInterface creditNote = creditNoteFactory.getImplementationByCode(method, customParams);
		if (creditNote == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		ResponseModel responseFromImplementation = creditNote.searchByParamsAndCompanyCode(noteSearchParamsDto,
				companyCode, idOperation);
		return responseFromImplementation;
	}

	@Override
	public ResponseModel obtainCreditNoteSummaryByNumberAndCode(CreditNoteCustomParams noteCustomParams, String method,
			String companyCode, String idOperation) {
		LOG.info(String.format("%s INIT obtainCreditNoteSummaryByNumberAndCode()", idOperation));
		noteCustomParams.setCreditNotePersistencePort(creditNotePersistencePort);
		CreditNoteInterface creditNote = creditNoteFactory.getImplementationByCode(method, noteCustomParams);
		if (creditNote == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		ResponseModel responseFromImplementation = creditNote.obtainCreditNoteSummaryByNumberAndCode(noteCustomParams,
				companyCode, idOperation);
		return responseFromImplementation;
	}

	@Override
	public ResponseModel viewDetailByCodeAndFolioAndCompanyCode(CreditNoteCustomParams noteCustomParams,
			BigDecimal folio, String creditNoteCode, String method, String companyCode, String idOperation) {
		LOG.info(String.format("%s INIT viewDetailByCodeAndFolioAndCompanyCode()", idOperation));
		noteCustomParams.setCreditNotePersistencePort(creditNotePersistencePort);
		CreditNoteInterface creditNote = creditNoteFactory.getImplementationByCode(method, noteCustomParams);
		if (creditNote == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		ResponseModel responseFromImplementation = creditNote.viewDetailByCodeAndFolioAndCompanyCode(folio,
				creditNoteCode, companyCode, idOperation);
		return responseFromImplementation;
	}

	@Override
	public ResponseModel generateTicketByFolioAndCompanyCode(CreditNoteCustomParams customParams,
			CreditNoteSearchParamsDto noteSearchParamsDto, String method, String companyCode, String idOperation) {

		LOG.info(String.format("%s INIT generateTicketByFolioAndCompanyCode()", idOperation));
		customParams.setCreditNotePersistencePort(creditNotePersistencePort);
		CreditNoteInterface creditNote = creditNoteFactory.getImplementationByCode(method, customParams);
		if (creditNote == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		ResponseModel responseFromImplementation = creditNote.generateTicketByFolioAndCompanyCode(noteSearchParamsDto,
				companyCode, idOperation);
		return responseFromImplementation;
	}

}
