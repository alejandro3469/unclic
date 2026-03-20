package mx.com.endtoend.infrastructure.creditNote.common.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import mx.com.endtoend.domain.creditNote.dto.CreditNoteSearchParamsDto;
import mx.com.endtoend.domain.creditNote.dto.CreditNoteSummary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import mx.com.endtoend.infrastructure.creditNote.common.entities.QCreditNoteEntity;
import mx.com.endtoend.infrastructure.creditNote.common.entities.QCreditNoteHeaderEntity;

import javax.persistence.EntityManager;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class BaseCustomDSLCreditNoteRepository extends QuerydslRepositorySupport {

    private final EntityManager em;
    private final Logger LOG;

    public BaseCustomDSLCreditNoteRepository(Class<?> loggerClass, EntityManager _em) {
        super(CreditNoteSummary.class);
        em = _em;
        LOG = LoggerFactory.getLogger(loggerClass);
    }

    public List<CreditNoteSummary> findCreditNoteSummaryByParams(CreditNoteSearchParamsDto noteSearchParamsDto) {

        JPAQuery<CreditNoteSummary> query = new JPAQuery<>(em);

        QCreditNoteEntity creditNote = QCreditNoteEntity.creditNoteEntity;
        QCreditNoteHeaderEntity creditNoteHeader = QCreditNoteHeaderEntity.creditNoteHeaderEntity;

        /**
         * Start evaluation of tables
         */
        query.from(creditNote).join(creditNoteHeader).on(creditNote.id.eq(creditNoteHeader.creditNote.id));

        query.where(creditNote.employeeId.in(noteSearchParamsDto.getUserIdLits()));

        /**
         * Start evaluation of search parameters
         */

        if (noteSearchParamsDto.getCreditNoteCode() != null) {
            if (!noteSearchParamsDto.getCreditNoteCode().isEmpty()) {
                query.where(creditNoteHeader.creditNoteCode.eq(noteSearchParamsDto.getCreditNoteCode()));
            }
        }

        if (noteSearchParamsDto.getFolio() != null) {
            query.where(creditNoteHeader.folio.eq(noteSearchParamsDto.getFolio()));
        }

        if (noteSearchParamsDto.getOrderCode() != null) {
            if (!noteSearchParamsDto.getOrderCode().isEmpty()) {
                query.where(creditNote.orderCode.eq(noteSearchParamsDto.getOrderCode()));
            }
        }

        if (noteSearchParamsDto.getOrderNumber() != null) {
            query.where(creditNote.orderNumber.eq(noteSearchParamsDto.getOrderNumber()));
        }

        if (noteSearchParamsDto.getFrom() != null) {
            Date from = noteSearchParamsDto.getFrom();
            Calendar cal = Calendar.getInstance();
            cal.setTime(noteSearchParamsDto.getFrom());
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            from = cal.getTime();
            LOG.info("SERCH BY DATE FROM: " + from.toString());
            query.where(creditNoteHeader.creationDate.goe(from));
        }

        if (noteSearchParamsDto.getTo() != null) {
            Date to = noteSearchParamsDto.getTo();
            Calendar cal = Calendar.getInstance();
            cal.setTime(noteSearchParamsDto.getTo());
            cal.set(Calendar.HOUR_OF_DAY, 23);
            cal.set(Calendar.MINUTE, 59);
            cal.set(Calendar.SECOND, 59);
            cal.set(Calendar.MILLISECOND, 59);
            to = cal.getTime();
            LOG.info("SERCH BY DATE TO: " + to.toString());
            query.where(creditNoteHeader.creationDate.loe(to));
        }

        /**
         * End evaluation
         */

        /**
         * Select fields
         */
        query.select(Projections.constructor(CreditNoteSummary.class, creditNoteHeader.folio,
                creditNoteHeader.creditNoteCode, creditNoteHeader.totalAmount, creditNoteHeader.usedAmount,
                creditNoteHeader.pendingAmount, creditNoteHeader.creationDate, creditNoteHeader.clientNumber,
                creditNoteHeader.currency, creditNoteHeader.exchangeRate, creditNote.orderNumber,
                creditNote.orderCode));

        /**
         * Execute and return data
         */
        return query.fetch();
    }
}
