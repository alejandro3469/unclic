package mx.com.endtoend.infrastructure.reports.sales.closingOperation.common.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import mx.com.endtoend.domain.commons.constants.AccountingConceptEnum;
import mx.com.endtoend.domain.commons.constants.MovementConceptEnum;
import mx.com.endtoend.domain.reports.sales.closingOperation.dto.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.repository.NoRepositoryBean;

import javax.persistence.EntityManager;
import javax.persistence.MappedSuperclass;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import mx.com.endtoend.infrastructure.accountingRecord.common.entities.QAccountingRecordEntity;
import mx.com.endtoend.infrastructure.cash.closingInstruments.common.entities.QClosePaymentInstrumentEntity;
import mx.com.endtoend.infrastructure.closings.common.entities.QClosingOperationDetailEntity;
import mx.com.endtoend.infrastructure.closings.common.entities.QClosingOperationEntity;
import mx.com.endtoend.infrastructure.openings.common.entities.QOpeningOperationEntity;


@NoRepositoryBean
@MappedSuperclass
public class BaseCustomDSLClosingOperationRepository extends QuerydslRepositorySupport {

    private final EntityManager em;
    private final Logger LOG;

    public BaseCustomDSLClosingOperationRepository(Class<?> loggerClass, EntityManager _em) {
        super(ClosingOperationSummarySeach.class);
        em = _em;
        LOG = LoggerFactory.getLogger(loggerClass);
    }

    public List<ClosingOperationSummarySeach> findCloringOperationRecordByParams(
            ClosingOperationReportParamsDto closingOperationReportParamsDto) {

        LOG.info("INIT findCloringOperationRecordByParams()");

        JPAQuery<ClosingOperationSummarySeach> query = new JPAQuery<>(em);

        QOpeningOperationEntity openingOperation = QOpeningOperationEntity.openingOperationEntity;
        QClosingOperationEntity closingOperation = QClosingOperationEntity.closingOperationEntity;

        /**
         * Start evaluation of tables
         */
        query.from(openingOperation).join(closingOperation)
                .on(openingOperation.closingId.eq(closingOperation.closingId));

        query.where(openingOperation.branchCode.eq(closingOperationReportParamsDto.getBranchCode()));

        /**
         * Start evaluation of search parameters
         */
        if (closingOperationReportParamsDto.getStartDate() != null) {
            Date from = closingOperationReportParamsDto.getStartDate();
            Calendar cal = Calendar.getInstance();
            cal.setTime(closingOperationReportParamsDto.getStartDate());
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            from = cal.getTime();
            LOG.info("SERCH BY DATE FROM: " + from.toString());
            query.where(closingOperation.creationDate.goe(from));
        }
        if (closingOperationReportParamsDto.getEndDate() != null) {
            Date to = closingOperationReportParamsDto.getEndDate();
            Calendar cal = Calendar.getInstance();
            cal.setTime(closingOperationReportParamsDto.getEndDate());
            cal.set(Calendar.HOUR_OF_DAY, 23);
            cal.set(Calendar.MINUTE, 59);
            cal.set(Calendar.SECOND, 59);
            cal.set(Calendar.MILLISECOND, 59);
            to = cal.getTime();
            LOG.info("SERCH BY DATE TO: " + to.toString());
            query.where(closingOperation.creationDate.loe(to));
        }
        /**
         * Select fields
         */
        query.select(Projections.constructor(ClosingOperationSummarySeach.class, openingOperation.branchCode,
                openingOperation.openingId, closingOperation.closingId, closingOperation.creationDate,
                openingOperation.employeeEmail));
        /**
         * Execute and return data
         */
        return query.fetch();

    }

    public List<ClosingOperationReported> findClosingOperationDetailByClosingId(Long closingId) {

        LOG.info("INIT findCloringOperationRecordByParams()");

        JPAQuery<ClosingOperationReported> query = new JPAQuery<>(em);

        QClosingOperationDetailEntity closingOperationDetail = QClosingOperationDetailEntity.closingOperationDetailEntity;
        QClosePaymentInstrumentEntity closePaymentinstrument = QClosePaymentInstrumentEntity.closePaymentInstrumentEntity;

        /**
         * Start evaluation of tables
         */
        query.from(closingOperationDetail).join(closePaymentinstrument)
                .on(closingOperationDetail.closePaymentInstrument.id.eq(closePaymentinstrument.id));
        /**
         * Start evaluation of search parameters
         */
        query.where(closingOperationDetail.closingOperation.closingId.eq(closingId));

        /**
         * Select fields
         */
        query.select(Projections.constructor(ClosingOperationReported.class, closingOperationDetail.amount,
                closePaymentinstrument.incomeType));

        return query.fetch();
    }

    public List<AccountingRecordReported> findAccountinrecordDetailByOpeninigId(Long openingId) {

        LOG.info("INIT findAccountinrecordDetailByOpeninigId()");

        JPAQuery<AccountingRecordReported> query = new JPAQuery<>(em);

        QAccountingRecordEntity accountingRecord = QAccountingRecordEntity.accountingRecordEntity;

        /**
         * Start evaluation of tables
         */
        query.from(accountingRecord);
        query.where(accountingRecord.openingId.eq(openingId));
        /**
         * Start evaluation of search parameters
         */
        query.where(accountingRecord.accountingConcept.eq(AccountingConceptEnum.INCOME.toString()));
        /**
         * Select fields
         */
        query.select(Projections.constructor(AccountingRecordReported.class, accountingRecord.amountApplied,
                accountingRecord.movementType));

        return query.fetch();
    }

    public List<ClosingOperationReported> findAccountingClosingRecordByOpeningId(Long openingId) {
        LOG.info("INIT findAccountingClosingRecordByOpeningId()");

        JPAQuery<ClosingOperationReported> query = new JPAQuery<>(em);

        QAccountingRecordEntity accountingRecord = QAccountingRecordEntity.accountingRecordEntity;

        /**
         * Start evaluation of tables
         */
        query.from(accountingRecord);
        query.where(accountingRecord.openingId.eq(openingId));
        /**
         * Start evaluation of search parameters
         */
        query.where(accountingRecord.movementConcept.eq(MovementConceptEnum.CLOSING.toString()));

        /**
         * Select fields
         */
        query.select(Projections.constructor(ClosingOperationReported.class, accountingRecord.amountApplied,
                accountingRecord.movementType));

        return query.fetch();

    }

    public List<AccountingTicketRecord> findAccountingTicketRecordByOpeningId(Long openingId) {
        LOG.info("INIT findAccountingTicketRecordByOpeningId()");
        JPAQuery<AccountingTicketRecord> query = new JPAQuery<>(em);
        QAccountingRecordEntity accountingRecord = QAccountingRecordEntity.accountingRecordEntity;
        /**
         * Start evaluation of tables
         */
        query.from(accountingRecord);
        query.where(accountingRecord.openingId.eq(openingId));
        /**
         * Start evaluation of search parameters
         */
        query.where(accountingRecord.movementConcept.eq(MovementConceptEnum.SALE.toString()));
        /**
         * Select fields
         */
        query.select(Projections.constructor(AccountingTicketRecord.class, accountingRecord.transactionId,
                accountingRecord.movementType));
        return query.fetch();
    }
}

