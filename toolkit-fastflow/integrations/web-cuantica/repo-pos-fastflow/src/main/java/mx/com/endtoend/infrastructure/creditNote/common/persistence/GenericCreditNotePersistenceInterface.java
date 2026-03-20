package mx.com.endtoend.infrastructure.creditNote.common.persistence;

import java.math.BigDecimal;
import java.util.List;

import mx.com.endtoend.domain.creditNote.dto.CreditNoteDto;
import mx.com.endtoend.domain.creditNote.dto.CreditNoteHeaderDto;
import mx.com.endtoend.domain.creditNote.dto.CreditNoteSearchParamsDto;
import mx.com.endtoend.domain.creditNote.dto.CreditNoteSummary;
import mx.com.endtoend.domain.creditNote.dto.ticket.CreditNoteTickteDto;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;
import mx.com.endtoend.smart.bussiness.model.orders.dto.OrderDto;

import javax.transaction.Transactional;

public interface GenericCreditNotePersistenceInterface {

	CreditNoteDto createCreditNote(CreditNoteDto creditNoteDto, String idOperation);

    @Transactional
	OrderDto getOrderByParams(BigDecimal orderNumber, String orderCode, String companyCode, String idOperation);

    CreditNoteHeaderDto searchCreditNoteHeaderByFolioAndCodeAndCompanyCode(BigDecimal folio, String creditNoteCode,
                                                                           String idOperation);

	CreditNoteDto searchCreditNoteByOrderNumberAndCodeAndCompanyCode(BigDecimal orderNumber, String orderCode,
			String idOperation);

	List<CreditNoteSummary> searchCreditNoteListByParams(CreditNoteSearchParamsDto noteSearchParamsDto,
			String idOperation);

	ResponseModel generateTicket(CreditNoteTickteDto creditNoteTickteDto, String idOperation);

	CreditNoteDto searchCreditNoteById(Long creditNoteId, String idOperation);

	void updateCreditNoteBalanceByParams(CreditNoteDto creditNoteDto, String idOperation);

	void updteCreditNoteHeaderBalanceByParams(CreditNoteHeaderDto creditNoteHeaderDto, String idOperation);

	void updatePrintStatusById(Long id, boolean printStatus, String idOperation);

}
