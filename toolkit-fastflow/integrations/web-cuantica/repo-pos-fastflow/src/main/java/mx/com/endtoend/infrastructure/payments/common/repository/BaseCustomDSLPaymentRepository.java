package mx.com.endtoend.infrastructure.payments.common.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import mx.com.endtoend.domain.payments.dto.GenericSearchPaymentDto;
import mx.com.endtoend.domain.payments.dto.PaidOrderSummaryDto;
import mx.com.endtoend.domain.reports.sales.branch.dto.GenericSearchSaleReportParamsDto;
import mx.com.endtoend.domain.reports.sales.models.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.repository.NoRepositoryBean;
import mx.com.endtoend.infrastructure.catalogue.orders.common.entities.QStatusEntity;
import mx.com.endtoend.infrastructure.client.common.entities.QClientEntity;
import mx.com.endtoend.infrastructure.openings.common.entities.QOpeningOperationEntity;
import mx.com.endtoend.infrastructure.orders.common.entities.QOrderEntity;
import mx.com.endtoend.infrastructure.payments.common.entities.QCreditPaymentEntity;
import mx.com.endtoend.infrastructure.payments.common.entities.QCheckPaymentEntity;
import mx.com.endtoend.infrastructure.payments.common.entities.QCreditCardPaymentEntity;
import mx.com.endtoend.infrastructure.payments.common.entities.QCreditNotePaymentEntity;
import mx.com.endtoend.infrastructure.payments.common.entities.QInvoiceRerefenceEntity;
import mx.com.endtoend.infrastructure.payments.common.entities.QPaymentCashEntity;
import mx.com.endtoend.infrastructure.payments.common.entities.QPaymentEntity;
import mx.com.endtoend.infrastructure.payments.common.entities.QTransferPaymentEntity;
import mx.com.endtoend.infrastructure.cash.creditCardReference.common.entities.QCreditCardEntity;


import javax.persistence.EntityManager;
import javax.persistence.MappedSuperclass;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@NoRepositoryBean
@MappedSuperclass
public class BaseCustomDSLPaymentRepository extends QuerydslRepositorySupport {

    private final EntityManager em;
    private final Logger LOG;
    public BaseCustomDSLPaymentRepository(Class<?> loggerClass, EntityManager em) {
        super(PaidOrderSummaryDto.class);
        this.em = em;
        LOG = LoggerFactory.getLogger(loggerClass);
    }

    public List<PaidOrderSummaryDto> searhPaidSummaryByParams(GenericSearchPaymentDto searchParamsPayment) {

        LOG.info("INIT searhPaidSummaryByParams()");

        JPAQuery<PaidOrderSummaryDto> query = new JPAQuery<>(em);

        QPaymentEntity payment = QPaymentEntity.paymentEntity;
        QOrderEntity order = QOrderEntity.orderEntity;
        QStatusEntity status = QStatusEntity.statusEntity;

        /**
         * Start evaluation of tables
         */
        query.from(payment).join(order).on(payment.orderNumber.eq(order.orderNumber))
                .on(payment.orderCode.eq(order.orderCode));
        query.join(status).on(order.status.id.eq(status.id));
        query.where(order.branchCode.eq(searchParamsPayment.getBranchCode()));
        /**
         * Start evaluation of search parameters
         */
        if (searchParamsPayment.getEmployeeEmail() != null) {
            if (!searchParamsPayment.getEmployeeEmail().isEmpty()) {
                query.where(payment.employeeEmail.eq(searchParamsPayment.getEmployeeEmail()));
            }
        }

        if (searchParamsPayment.getOrderCode() != null) {
            if (!searchParamsPayment.getOrderCode().isEmpty()) {
                query.where(payment.orderCode.eq(searchParamsPayment.getOrderCode()));
            }
        }
        if (searchParamsPayment.getOrderNumber() != null) {
            query.where(payment.orderNumber.eq(searchParamsPayment.getOrderNumber()));
        }
        if (searchParamsPayment.getFrom() != null) {
            Date from = searchParamsPayment.getFrom();
            Calendar cal = Calendar.getInstance();
            cal.setTime(searchParamsPayment.getFrom());
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            from = cal.getTime();
            LOG.info("SERCH BY DATE FROM: " + from.toString());
            query.where(payment.paymentDate.goe(from));
        }
        if (searchParamsPayment.getTo() != null) {
            Date to = searchParamsPayment.getTo();
            Calendar cal = Calendar.getInstance();
            cal.setTime(searchParamsPayment.getTo());
            cal.set(Calendar.HOUR_OF_DAY, 23);
            cal.set(Calendar.MINUTE, 59);
            cal.set(Calendar.SECOND, 59);
            cal.set(Calendar.MILLISECOND, 59);
            to = cal.getTime();
            LOG.info("SERCH BY DATE TO: " + to.toString());
            query.where(payment.paymentDate.loe(to));
        }

        // Sort by paymentDate and limit to one result
        query.orderBy(payment.paymentDate.asc()).limit(1);

        /**
         * Select fields
         */
        query.select(Projections.constructor(PaidOrderSummaryDto.class, order.orderNumber, order.orderCode,
                order.branchCode, order.currency, order.ivaTotal, order.subTotal, order.orderTotal,
                order.pendingPayment, payment.paymentDate, status.code, status.description, payment.userNumber));
        /**
         * Execute and return data
         */
        return query.fetch();
    }

    public List<SummarySaleCreditPaymentDto> findSummaryCreditPaymentByParams(
            GenericSearchSaleReportParamsDto saleReportParamsDto) {

        LOG.info("INIT findSummaryCreditPaymentByParams()");

        JPAQuery<SummarySaleCreditPaymentDto> query = new JPAQuery<>(em);

        QPaymentEntity payment = QPaymentEntity.paymentEntity;
        QOpeningOperationEntity openingOperation = QOpeningOperationEntity.openingOperationEntity;
        QCreditPaymentEntity credit = QCreditPaymentEntity.creditPaymentEntity;
        QClientEntity client = QClientEntity.clientEntity;
        QInvoiceRerefenceEntity invoiceReference = QInvoiceRerefenceEntity.invoiceRerefenceEntity;

        /**
         * Start evaluation of tables
         */
        query.from(payment).join(openingOperation).on(payment.openingCashId.eq(openingOperation.openingId));
        query.join(credit).on(payment.paymentId.eq(credit.paymentId));
        query.join(client).on(payment.clientNumber.eq(client.noClient));
        query.leftJoin(invoiceReference).on(payment.paymentId.eq(invoiceReference.paymentId));
        /**
         * Start evaluation of search parameters
         */
        query.where(openingOperation.branchCode.eq(saleReportParamsDto.getBranchCode()));

        if (saleReportParamsDto.getStartDate() != null) {
            Date from = saleReportParamsDto.getStartDate();
            Calendar cal = Calendar.getInstance();
            cal.setTime(saleReportParamsDto.getStartDate());
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            from = cal.getTime();
            LOG.info("SERCH BY DATE FROM: " + from.toString());
            query.where(payment.paymentDate.goe(from));
        }

        if (saleReportParamsDto.getEndDate() != null) {
            Date to = saleReportParamsDto.getEndDate();
            Calendar cal = Calendar.getInstance();
            cal.setTime(saleReportParamsDto.getEndDate());
            cal.set(Calendar.HOUR_OF_DAY, 23);
            cal.set(Calendar.MINUTE, 59);
            cal.set(Calendar.SECOND, 59);
            cal.set(Calendar.MILLISECOND, 59);
            to = cal.getTime();
            LOG.info("SERCH BY DATE TO: " + to.toString());
            query.where(payment.paymentDate.loe(to));
        }

        /**
         * Select fields
         */
        query.select(Projections.constructor(SummarySaleCreditPaymentDto.class, payment.paymentId, payment.paymentDate,
                payment.orderNumber, payment.orderCode, payment.clientNumber, client.businessName, client.name,
                client.fatherSurname, client.motherSurname, credit.amountApplied,credit.currency, credit.referenceId,
                invoiceReference.invoiceNumber, invoiceReference.invoiceCode));

        return query.fetch();
    }

    public List<SummarySaleCashPaymentDto> findSummaryCashPaymentByParams(
            GenericSearchSaleReportParamsDto saleReportParamsDto) {

        LOG.info("INIT findSummaryCashPaymentByParams()");

        JPAQuery<SummarySaleCashPaymentDto> query = new JPAQuery<>(em);

        QPaymentEntity payment = QPaymentEntity.paymentEntity;
        QOpeningOperationEntity openingOperation = QOpeningOperationEntity.openingOperationEntity;
        QPaymentCashEntity paymentCash = QPaymentCashEntity.paymentCashEntity;
        QClientEntity client = QClientEntity.clientEntity;
        QInvoiceRerefenceEntity invoiceReference = QInvoiceRerefenceEntity.invoiceRerefenceEntity;

        /**
         * Start evaluation of tables
         */
        query.from(payment).join(openingOperation).on(payment.openingCashId.eq(openingOperation.openingId));
        query.join(paymentCash).on(payment.paymentId.eq(paymentCash.paymentId));
        query.join(client).on(payment.clientNumber.eq(client.noClient));
        query.leftJoin(invoiceReference).on(payment.paymentId.eq(invoiceReference.paymentId));
        /**
         * Start evaluation of search parameters
         */
        query.where(openingOperation.branchCode.eq(saleReportParamsDto.getBranchCode()));

        if (saleReportParamsDto.getStartDate() != null) {
            Date from = saleReportParamsDto.getStartDate();
            Calendar cal = Calendar.getInstance();
            cal.setTime(saleReportParamsDto.getStartDate());
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            from = cal.getTime();
            LOG.info("SERCH BY DATE FROM: " + from.toString());
            query.where(payment.paymentDate.goe(from));
        }

        if (saleReportParamsDto.getEndDate() != null) {
            Date to = saleReportParamsDto.getEndDate();
            Calendar cal = Calendar.getInstance();
            cal.setTime(saleReportParamsDto.getEndDate());
            cal.set(Calendar.HOUR_OF_DAY, 23);
            cal.set(Calendar.MINUTE, 59);
            cal.set(Calendar.SECOND, 59);
            cal.set(Calendar.MILLISECOND, 59);
            to = cal.getTime();
            LOG.info("SERCH BY DATE TO: " + to.toString());
            query.where(payment.paymentDate.loe(to));
        }

        /**
         * Select fields
         */
        query.select(Projections.constructor(SummarySaleCashPaymentDto.class, payment.paymentId, payment.paymentDate,
                payment.orderNumber, payment.orderCode, payment.clientNumber, client.businessName, client.name,
                client.fatherSurname, client.motherSurname, paymentCash.amountApplied, paymentCash.currency,
                invoiceReference.invoiceNumber, invoiceReference.invoiceCode));

        return query.fetch();
    }

    public List<SummarySaleCreditCardPaymentDto> findSummaryCreditCardPaymentByParams(
            GenericSearchSaleReportParamsDto saleReportParamsDto) {

        LOG.info("INIT findSummaryCreditCardPaymentByParams()");

        JPAQuery<SummarySaleCreditCardPaymentDto> query = new JPAQuery<>(em);

        QPaymentEntity payment = QPaymentEntity.paymentEntity;
        QOpeningOperationEntity openingOperation = QOpeningOperationEntity.openingOperationEntity;
        QCreditCardPaymentEntity creditCard = QCreditCardPaymentEntity.creditCardPaymentEntity;
        QClientEntity client = QClientEntity.clientEntity;
        QCreditCardEntity creditCardRef = QCreditCardEntity.creditCardEntity;
        QInvoiceRerefenceEntity invoiceReference = QInvoiceRerefenceEntity.invoiceRerefenceEntity;

        /**
         * Start evaluation of tables
         */
        query.from(payment).join(openingOperation).on(payment.openingCashId.eq(openingOperation.openingId));
        query.join(creditCard).on(payment.paymentId.eq(creditCard.paymentId));
        query.join(client).on(payment.clientNumber.eq(client.noClient));
        query.leftJoin(creditCardRef).on(creditCard.code.eq(creditCardRef.code));
        query.leftJoin(invoiceReference).on(payment.paymentId.eq(invoiceReference.paymentId));
        /**
         * Start evaluation of search parameters
         */
        query.where(openingOperation.branchCode.eq(saleReportParamsDto.getBranchCode()));

        if (saleReportParamsDto.getStartDate() != null) {
            Date from = saleReportParamsDto.getStartDate();
            Calendar cal = Calendar.getInstance();
            cal.setTime(saleReportParamsDto.getStartDate());
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            from = cal.getTime();
            LOG.info("SERCH BY DATE FROM: " + from.toString());
            query.where(payment.paymentDate.goe(from));
        }

        if (saleReportParamsDto.getEndDate() != null) {
            Date to = saleReportParamsDto.getEndDate();
            Calendar cal = Calendar.getInstance();
            cal.setTime(saleReportParamsDto.getEndDate());
            cal.set(Calendar.HOUR_OF_DAY, 23);
            cal.set(Calendar.MINUTE, 59);
            cal.set(Calendar.SECOND, 59);
            cal.set(Calendar.MILLISECOND, 59);
            to = cal.getTime();
            LOG.info("SERCH BY DATE TO: " + to.toString());
            query.where(payment.paymentDate.loe(to));
        }

        /**
         * Select fields
         */
        query.select(Projections.constructor(SummarySaleCreditCardPaymentDto.class, payment.paymentId,
                payment.paymentDate, payment.orderNumber, payment.orderCode, payment.clientNumber, client.businessName,
                client.name, client.fatherSurname, client.motherSurname, invoiceReference.invoiceNumber,
                invoiceReference.invoiceCode, creditCard.amountApplied, creditCard.bankingInstitution,
                creditCard.cardNumber, creditCard.code, creditCardRef.type));

        return query.fetch();
    }

    public List<SummarySaleCreditNotePaymentDto> findSummaryCreditNotePaymentByParams(
            GenericSearchSaleReportParamsDto saleReportParamsDto) {

        LOG.info("INIT findSummaryCreditNotePaymentByParams()");

        JPAQuery<SummarySaleCreditNotePaymentDto> query = new JPAQuery<>(em);

        QPaymentEntity payment = QPaymentEntity.paymentEntity;
        QOpeningOperationEntity openingOperation = QOpeningOperationEntity.openingOperationEntity;
        QCreditNotePaymentEntity creditNote = QCreditNotePaymentEntity.creditNotePaymentEntity;
        QClientEntity client = QClientEntity.clientEntity;
        QInvoiceRerefenceEntity invoiceReference = QInvoiceRerefenceEntity.invoiceRerefenceEntity;

        /**
         * Start evaluation of tables
         */
        query.from(payment).join(openingOperation).on(payment.openingCashId.eq(openingOperation.openingId));
        query.join(creditNote).on(payment.paymentId.eq(creditNote.paymentId));
        query.join(client).on(payment.clientNumber.eq(client.noClient));
        query.leftJoin(invoiceReference).on(payment.paymentId.eq(invoiceReference.paymentId));
        /**
         * Start evaluation of search parameters
         */
        query.where(openingOperation.branchCode.eq(saleReportParamsDto.getBranchCode()));

        if (saleReportParamsDto.getStartDate() != null) {
            Date from = saleReportParamsDto.getStartDate();
            Calendar cal = Calendar.getInstance();
            cal.setTime(saleReportParamsDto.getStartDate());
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            from = cal.getTime();
            LOG.info("SERCH BY DATE FROM: " + from.toString());
            query.where(payment.paymentDate.goe(from));
        }

        if (saleReportParamsDto.getEndDate() != null) {
            Date to = saleReportParamsDto.getEndDate();
            Calendar cal = Calendar.getInstance();
            cal.setTime(saleReportParamsDto.getEndDate());
            cal.set(Calendar.HOUR_OF_DAY, 23);
            cal.set(Calendar.MINUTE, 59);
            cal.set(Calendar.SECOND, 59);
            cal.set(Calendar.MILLISECOND, 59);
            to = cal.getTime();
            LOG.info("SERCH BY DATE TO: " + to.toString());
            query.where(payment.paymentDate.loe(to));
        }

        /**
         * Select fields
         */
        query.select(Projections.constructor(SummarySaleCreditNotePaymentDto.class, payment.paymentId,
                payment.paymentDate, payment.orderNumber, payment.orderCode, payment.clientNumber, client.businessName,
                client.name, client.fatherSurname, client.motherSurname, invoiceReference.invoiceNumber,
                invoiceReference.invoiceCode, creditNote.amountApplied, creditNote.folio, creditNote.creditNoteCode));

        return query.fetch();
    }

    public List<SummarySaleTransferPaymentDto> findSummaryTransferPaymentByParams(
            GenericSearchSaleReportParamsDto saleReportParamsDto) {

        LOG.info("INIT findSummaryTransferPaymentByParams()");

        JPAQuery<SummarySaleTransferPaymentDto> query = new JPAQuery<>(em);

        QPaymentEntity payment = QPaymentEntity.paymentEntity;
        QOpeningOperationEntity openingOperation = QOpeningOperationEntity.openingOperationEntity;
        QTransferPaymentEntity transfer = QTransferPaymentEntity.transferPaymentEntity;
        QClientEntity client = QClientEntity.clientEntity;
        QInvoiceRerefenceEntity invoiceReference = QInvoiceRerefenceEntity.invoiceRerefenceEntity;

        /**
         * Start evaluation of tables
         */
        query.from(payment).join(openingOperation).on(payment.openingCashId.eq(openingOperation.openingId));
        query.join(transfer).on(payment.paymentId.eq(transfer.paymentId));
        query.join(client).on(payment.clientNumber.eq(client.noClient));
        query.leftJoin(invoiceReference).on(payment.paymentId.eq(invoiceReference.paymentId));
        /**
         * Start evaluation of search parameters
         */
        query.where(openingOperation.branchCode.eq(saleReportParamsDto.getBranchCode()));

        if (saleReportParamsDto.getReferenceTransfer() != null) {
            if (!saleReportParamsDto.getReferenceTransfer().isEmpty()) {
                query.where(transfer.bankingInstitution.eq(saleReportParamsDto.getReferenceTransfer()));
            }
        }

        if (saleReportParamsDto.getStartDate() != null) {
            Date from = saleReportParamsDto.getStartDate();
            Calendar cal = Calendar.getInstance();
            cal.setTime(saleReportParamsDto.getStartDate());
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            from = cal.getTime();
            LOG.info("SERCH BY DATE FROM: " + from.toString());
            query.where(payment.paymentDate.goe(from));
        }

        if (saleReportParamsDto.getEndDate() != null) {
            Date to = saleReportParamsDto.getEndDate();
            Calendar cal = Calendar.getInstance();
            cal.setTime(saleReportParamsDto.getEndDate());
            cal.set(Calendar.HOUR_OF_DAY, 23);
            cal.set(Calendar.MINUTE, 59);
            cal.set(Calendar.SECOND, 59);
            cal.set(Calendar.MILLISECOND, 59);
            to = cal.getTime();
            LOG.info("SERCH BY DATE TO: " + to.toString());
            query.where(payment.paymentDate.loe(to));
        }

        /**
         * Select fields
         */
        query.select(Projections.constructor(SummarySaleTransferPaymentDto.class, payment.paymentId,
                payment.paymentDate, payment.orderNumber, payment.orderCode, payment.clientNumber, client.businessName,
                client.name, client.fatherSurname, client.motherSurname, invoiceReference.invoiceNumber,
                invoiceReference.invoiceCode, transfer.amountApplied, transfer.bankingInstitution,
                transfer.referenceNumber, transfer.trackingNumber));

        return query.fetch();
    }

    public List<SummarySaleCheckPaymentDto> findSummaryCheckPaymentByParams(
            GenericSearchSaleReportParamsDto saleReportParamsDto) {

        LOG.info("INIT findSummaryCheckPaymentByParams()");

        JPAQuery<SummarySaleCheckPaymentDto> query = new JPAQuery<>(em);

        QPaymentEntity payment = QPaymentEntity.paymentEntity;
        QOpeningOperationEntity openingOperation = QOpeningOperationEntity.openingOperationEntity;
        QCheckPaymentEntity check = QCheckPaymentEntity.checkPaymentEntity;
        QClientEntity client = QClientEntity.clientEntity;
        QInvoiceRerefenceEntity invoiceReference = QInvoiceRerefenceEntity.invoiceRerefenceEntity;

        /**
         * Start evaluation of tables
         */
        query.from(payment).join(openingOperation).on(payment.openingCashId.eq(openingOperation.openingId));
        query.join(check).on(payment.paymentId.eq(check.paymentId));
        query.join(client).on(payment.clientNumber.eq(client.noClient));
        query.leftJoin(invoiceReference).on(payment.paymentId.eq(invoiceReference.paymentId));
        /**
         * Start evaluation of search parameters
         */
        query.where(openingOperation.branchCode.eq(saleReportParamsDto.getBranchCode()));

        if (saleReportParamsDto.getReferenceCheck() != null) {
            if (!saleReportParamsDto.getReferenceCheck().isEmpty()) {
                query.where(check.bankingInstitution.eq(saleReportParamsDto.getReferenceCheck()));
            }
        }

        if (saleReportParamsDto.getStartDate() != null) {
            Date from = saleReportParamsDto.getStartDate();
            Calendar cal = Calendar.getInstance();
            cal.setTime(saleReportParamsDto.getStartDate());
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            from = cal.getTime();
            LOG.info("SERCH BY DATE FROM: " + from.toString());
            query.where(payment.paymentDate.goe(from));
        }

        if (saleReportParamsDto.getEndDate() != null) {
            Date to = saleReportParamsDto.getEndDate();
            Calendar cal = Calendar.getInstance();
            cal.setTime(saleReportParamsDto.getEndDate());
            cal.set(Calendar.HOUR_OF_DAY, 23);
            cal.set(Calendar.MINUTE, 59);
            cal.set(Calendar.SECOND, 59);
            cal.set(Calendar.MILLISECOND, 59);
            to = cal.getTime();
            LOG.info("SERCH BY DATE TO: " + to.toString());
            query.where(payment.paymentDate.loe(to));
        }

        /**
         * Select fields
         */
        query.select(Projections.constructor(SummarySaleCheckPaymentDto.class, payment.paymentId, payment.paymentDate,
                payment.orderNumber, payment.orderCode, payment.clientNumber, client.businessName, client.name,
                client.fatherSurname, client.motherSurname, invoiceReference.invoiceNumber,
                invoiceReference.invoiceCode, check.amountApplied, check.bankingInstitution, check.checkNumber));

        return query.fetch();
    }
}
