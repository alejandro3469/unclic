package mx.com.endtoend.infrastructure.reports.sales.branchEmployee.common.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import mx.com.endtoend.domain.reports.sales.branchEmployee.dto.ReportNotesDto;
import mx.com.endtoend.domain.reports.sales.branchEmployee.dto.ReportSalesDto;
import mx.com.endtoend.domain.reports.sales.branchEmployee.dto.SaleAndNotesReportParamsDto;
import mx.com.endtoend.domain.reports.sales.branchEmployee.dto.SaleReportBranchEmployeeParamsDto;
import mx.com.endtoend.domain.reports.sales.models.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import mx.com.endtoend.infrastructure.cash.creditCardReference.common.entities.QCreditCardEntity;
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
import mx.com.endtoend.infrastructure.userConfiguration.common.entities.QEmployeeEntity;
import mx.com.endtoend.infrastructure.creditNote.common.entities.QCreditNoteEntity;
import mx.com.endtoend.infrastructure.creditNote.common.entities.QCreditNoteHeaderEntity;



import org.springframework.data.repository.NoRepositoryBean;

import javax.persistence.EntityManager;
import javax.persistence.MappedSuperclass;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@NoRepositoryBean
@MappedSuperclass
public class BaseCustomDSLPaymentBranchEmployeeRepository {

    private final EntityManager em;
    private final Logger LOG;

    public BaseCustomDSLPaymentBranchEmployeeRepository(
            Class<?> loggerClass, EntityManager _em) {
        em = _em;
        LOG = LoggerFactory.getLogger(loggerClass);
    }

    public List<SummarySaleCreditPaymentDto> findSummaryCreditPaymentByEmployeeAndParams(
            SaleReportBranchEmployeeParamsDto reportBranchEmployeeParamsDto) {

        LOG.info("INIT findSummaryCreditPaymentByEmployeeAndParams()");

        JPAQuery<SummarySaleCreditPaymentDto> query = new JPAQuery<>(em);

        QPaymentEntity payment = QPaymentEntity.paymentEntity;
        QOpeningOperationEntity openingOperation = QOpeningOperationEntity.openingOperationEntity;
        QCreditPaymentEntity credit = QCreditPaymentEntity.creditPaymentEntity;
        QClientEntity client = QClientEntity.clientEntity;
        QOrderEntity order = QOrderEntity.orderEntity;
        QInvoiceRerefenceEntity invoiceReference = QInvoiceRerefenceEntity.invoiceRerefenceEntity;

        /**
         * Start evaluation of tables
         */
        query.from(payment).join(openingOperation).on(payment.openingCashId.eq(openingOperation.openingId));
        query.join(credit).on(payment.paymentId.eq(credit.paymentId));
        query.join(client).on(payment.clientNumber.eq(client.noClient));
        query.join(order).on(payment.orderNumber.eq(order.orderNumber)).on(payment.orderCode.eq(order.orderCode));
        query.leftJoin(invoiceReference).on(payment.paymentId.eq(invoiceReference.paymentId));
        /**
         * Start evaluation of search parameters
         */
        query.where(openingOperation.branchCode.eq(reportBranchEmployeeParamsDto.getBranchCode()));
        query.where(order.userNumber.eq(reportBranchEmployeeParamsDto.getEmployeeNumber()));

        if (reportBranchEmployeeParamsDto.getStartDate() != null) {
            Date from = reportBranchEmployeeParamsDto.getStartDate();
            Calendar cal = Calendar.getInstance();
            cal.setTime(reportBranchEmployeeParamsDto.getStartDate());
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            from = cal.getTime();
            LOG.info("SERCH BY DATE FROM: " + from.toString());
            query.where(payment.paymentDate.goe(from));
        }

        if (reportBranchEmployeeParamsDto.getEndDate() != null) {
            Date to = reportBranchEmployeeParamsDto.getEndDate();
            Calendar cal = Calendar.getInstance();
            cal.setTime(reportBranchEmployeeParamsDto.getEndDate());
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
                client.fatherSurname, client.motherSurname, credit.amountApplied, credit.currency, credit.referenceId,
                invoiceReference.invoiceNumber, invoiceReference.invoiceCode));

        return query.fetch();
    }

    public List<ReportSalesDto> findSummaryCreditPaymentByEmployeeAndParamsV2(
            SaleAndNotesReportParamsDto reportBranchEmployeeParamsDto) {
		LOG.info("INIT findSummaryCreditPaymentByEmployeeAndParamsV2()");

		JPAQuery<ReportSalesDto> query = new JPAQuery<>(em);

		QClientEntity client = QClientEntity.clientEntity;
        QCreditCardPaymentEntity creditCardPayment = QCreditCardPaymentEntity.creditCardPaymentEntity;
        QCreditNotePaymentEntity creditNotePayment = QCreditNotePaymentEntity.creditNotePaymentEntity;
        QCreditPaymentEntity creditPayment = QCreditPaymentEntity.creditPaymentEntity;
        QEmployeeEntity employee = QEmployeeEntity.employeeEntity;
        QOrderEntity order = QOrderEntity.orderEntity;
        QPaymentCashEntity paymentCash = QPaymentCashEntity.paymentCashEntity;
        QPaymentEntity payment = QPaymentEntity.paymentEntity;
        QTransferPaymentEntity transferPayment = QTransferPaymentEntity.transferPaymentEntity;

		/**
		 * Start evaluation of tables
		 */
		query.from(order).join(client).on(order.clientId.eq(client.id));
		query.join(employee).on(order.userNumber.eq(employee.userNumber));
		query.join(payment).on(Expressions.stringTemplate("concat({0}, {1})", order.orderNumber, order.orderCode)
				.eq(Expressions.stringTemplate("concat({0}, {1})", payment.orderNumber, payment.orderCode)));
		query.leftJoin(paymentCash).on(payment.paymentId.eq(paymentCash.paymentId));
		query.leftJoin(transferPayment).on(payment.paymentId.eq(transferPayment.paymentId));
		query.leftJoin(creditCardPayment).on(payment.paymentId.eq(creditCardPayment.paymentId));
		query.leftJoin(creditNotePayment).on(payment.paymentId.eq(creditNotePayment.paymentId));
		query.leftJoin(creditPayment).on(payment.paymentId.eq(creditPayment.paymentId));

		/**
		 * Start evaluation of search parameters
		 */
		if (reportBranchEmployeeParamsDto.getStartDate() != null && reportBranchEmployeeParamsDto.getEndDate() != null) {
			Calendar calStart = Calendar.getInstance();
            calStart.setTime(reportBranchEmployeeParamsDto.getStartDate());
            calStart.set(Calendar.HOUR_OF_DAY, 0);
            calStart.set(Calendar.MINUTE, 0);
            calStart.set(Calendar.SECOND, 0);
            calStart.set(Calendar.MILLISECOND, 0);
            Date from = calStart.getTime();

            Calendar calEnd = Calendar.getInstance();
            calEnd.setTime(reportBranchEmployeeParamsDto.getEndDate());
            calEnd.set(Calendar.HOUR_OF_DAY, 23);
            calEnd.set(Calendar.MINUTE, 59);
            calEnd.set(Calendar.SECOND, 59);
            calEnd.set(Calendar.MILLISECOND, 59);
            Date to = calEnd.getTime();

            LOG.info("SEARCH BY DATE FROM: " + from.toString() + " TO: " + to.toString());
            query.where(order.creationDate.between(from, to)
					.and(payment.paymentDate.between(from, to)));
        }

		if (reportBranchEmployeeParamsDto.getEmployeeNumber() != null) {
            LOG.info("SEARCH BY EMPLOYEE NUMBER: " + reportBranchEmployeeParamsDto.getEmployeeNumber());
            query.where(order.userNumber.eq(reportBranchEmployeeParamsDto.getEmployeeNumber()));
		}

		if (reportBranchEmployeeParamsDto.getBranchCode() != null && reportBranchEmployeeParamsDto.getBranchCode() != "") {
   		    LOG.info("SEARCH BY BRANCH CODE: " + reportBranchEmployeeParamsDto.getBranchCode());
    		query.where(order.branchCode.eq(reportBranchEmployeeParamsDto.getBranchCode()));
		}


        /**
         * Select fields
         */
        query.select(Projections.constructor(ReportSalesDto.class,
                Expressions.stringTemplate("date_format({0}, '%Y-%m-%d %H:%i:%s')", order.creationDate),
                order.branchCode,
                order.userNumber,
                employee.employeeEmail,
                payment.employeeEmail,
                order.orderCode,
                order.orderNumber,
                order.orderTotal,
                client.id,
                client.businessName,
                Expressions.stringTemplate("date_format({0}, '%Y-%m-%d %H:%i:%s')", payment.paymentDate),
                paymentCash.amountApplied,
                transferPayment.amountApplied,
                creditCardPayment.amountApplied,
                creditNotePayment.amountApplied,
                creditPayment.amountApplied
        ));

        List<ReportSalesDto> result = query.fetch();
        return result;
    }

    public List<ReportNotesDto> findCreditNotesByParams(SaleAndNotesReportParamsDto reportParamsDto) {

		LOG.info("INIT findCreditNotesByParams()");

		JPAQuery<ReportNotesDto> query = new JPAQuery<>(em);

		QClientEntity client = QClientEntity.clientEntity;
		QCreditNoteEntity creditNote = QCreditNoteEntity.creditNoteEntity;
		QCreditNoteHeaderEntity creditNoteHeader = QCreditNoteHeaderEntity.creditNoteHeaderEntity;
		QOrderEntity order = QOrderEntity.orderEntity;

		/**
		 * Start evaluation of tables
		 */
		query.from(creditNote)
				.join(order).on(Expressions.stringTemplate("concat({0}, {1})", creditNote.orderNumber, creditNote.orderCode)
						.eq(Expressions.stringTemplate("concat({0}, {1})", order.orderNumber, order.orderCode)))
				.join(creditNoteHeader).on(creditNote.id.eq(creditNoteHeader.creditNote.id))
				.join(client).on(creditNoteHeader.clientId.eq(client.id));

		/**
		 * Start evaluation of search parameters
		 */
		if (reportParamsDto.getStartDate() != null && reportParamsDto.getEndDate() != null) {
			Calendar calStart = Calendar.getInstance();
			calStart.setTime(reportParamsDto.getStartDate());
			calStart.set(Calendar.HOUR_OF_DAY, 0);
			calStart.set(Calendar.MINUTE, 0);
			calStart.set(Calendar.SECOND, 0);
			calStart.set(Calendar.MILLISECOND, 0);
			Date from = calStart.getTime();

			Calendar calEnd = Calendar.getInstance();
			calEnd.setTime(reportParamsDto.getEndDate());
			calEnd.set(Calendar.HOUR_OF_DAY, 23);
			calEnd.set(Calendar.MINUTE, 59);
			calEnd.set(Calendar.SECOND, 59);
			calEnd.set(Calendar.MILLISECOND, 59);
			Date to = calEnd.getTime();

			LOG.info("SEARCH BY DATE FROM: " + from.toString() + " TO: " + to.toString());
			query.where(order.creationDate.between(from, to));
		}

		if (reportParamsDto.getEmployeeNumber() != null) {
			LOG.info("SEARCH BY EMPLOYEE NUMBER: " + reportParamsDto.getEmployeeNumber());
			query.where(order.userNumber.eq(reportParamsDto.getEmployeeNumber()));
		}

		if (reportParamsDto.getBranchCode() != null) {
			LOG.info("SEARCH BY BRANCH CODE: " + reportParamsDto.getBranchCode());
			query.where(order.branchCode.eq(reportParamsDto.getBranchCode()));
		}

		/**
		 * Select fields
		 */
		query.select(Projections.constructor(ReportNotesDto.class,
				Expressions.stringTemplate("date_format({0}, '%Y-%m-%d %H:%i:%s')", order.creationDate).as("ordenCreacion"),
				Expressions.stringTemplate("date_format({0}, '%Y-%m-%d %H:%i:%s')", creditNoteHeader.creationDate).as("notaCreacion"),
				creditNoteHeader.creditNoteCode.as("notaTipo"),
				creditNoteHeader.folio.as("notaFolio"),
				creditNote.creditNoteTotal.as("notaTotal"),
				creditNoteHeader.pendingAmount.as("notaMontoPendiente"),
				creditNoteHeader.usedAmount.as("notaMontoUsado"),
				order.branchCode.as("sucursal"),
				creditNote.orderCode.as("ordenCodigo"),
				creditNote.orderNumber.as("ordenNumero"),
				client.id.as("clienteId"),
				client.businessName.as("clienteNombre")
		));

		List<ReportNotesDto> result = query.fetch();
		return result;
	}

    public List<SummarySaleCashPaymentDto> findSummaryCashPaymentByEmployeeAndParams(
            SaleReportBranchEmployeeParamsDto reportBranchEmployeeParamsDto) {

        LOG.info("INIT findSummaryCashPaymentByEmployeeAndParams()");

        JPAQuery<SummarySaleCashPaymentDto> query = new JPAQuery<>(em);

        QPaymentEntity payment = QPaymentEntity.paymentEntity;
        QOpeningOperationEntity openingOperation = QOpeningOperationEntity.openingOperationEntity;
        QPaymentCashEntity paymentCash = QPaymentCashEntity.paymentCashEntity;
        QClientEntity client = QClientEntity.clientEntity;
        QOrderEntity order = QOrderEntity.orderEntity;
        QInvoiceRerefenceEntity invoiceReference = QInvoiceRerefenceEntity.invoiceRerefenceEntity;

        /**
         * Start evaluation of tables
         */
        query.from(payment).join(openingOperation).on(payment.openingCashId.eq(openingOperation.openingId));
        query.join(paymentCash).on(payment.paymentId.eq(paymentCash.paymentId));
        query.join(client).on(payment.clientNumber.eq(client.noClient));
        query.join(order).on(payment.orderNumber.eq(order.orderNumber)).on(payment.orderCode.eq(order.orderCode));
        query.leftJoin(invoiceReference).on(payment.paymentId.eq(invoiceReference.paymentId));
        /**
         * Start evaluation of search parameters
         */
        query.where(openingOperation.branchCode.eq(reportBranchEmployeeParamsDto.getBranchCode()));
        query.where(order.userNumber.eq(reportBranchEmployeeParamsDto.getEmployeeNumber()));

        if (reportBranchEmployeeParamsDto.getStartDate() != null) {
            Date from = reportBranchEmployeeParamsDto.getStartDate();
            Calendar cal = Calendar.getInstance();
            cal.setTime(reportBranchEmployeeParamsDto.getStartDate());
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            from = cal.getTime();
            LOG.info("SERCH BY DATE FROM: " + from.toString());
            query.where(payment.paymentDate.goe(from));
        }

        if (reportBranchEmployeeParamsDto.getEndDate() != null) {
            Date to = reportBranchEmployeeParamsDto.getEndDate();
            Calendar cal = Calendar.getInstance();
            cal.setTime(reportBranchEmployeeParamsDto.getEndDate());
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

    public List<SummarySaleCreditCardPaymentDto> findSummaryCreditCardPaymentByEmployeeAndParams(
            SaleReportBranchEmployeeParamsDto reportBranchEmployeeParamsDto) {

        LOG.info("INIT findSummaryCreditCardPaymentByParams()");

        JPAQuery<SummarySaleCreditCardPaymentDto> query = new JPAQuery<>(em);

        QPaymentEntity payment = QPaymentEntity.paymentEntity;
        QOpeningOperationEntity openingOperation = QOpeningOperationEntity.openingOperationEntity;
        QCreditCardPaymentEntity creditCard = QCreditCardPaymentEntity.creditCardPaymentEntity;
        QClientEntity client = QClientEntity.clientEntity;
        QCreditCardEntity creditCardRef = QCreditCardEntity.creditCardEntity;
        QOrderEntity order = QOrderEntity.orderEntity;
        QInvoiceRerefenceEntity invoiceReference = QInvoiceRerefenceEntity.invoiceRerefenceEntity;

        /**
         * Start evaluation of tables
         */
        query.from(payment).join(openingOperation).on(payment.openingCashId.eq(openingOperation.openingId));
        query.join(creditCard).on(payment.paymentId.eq(creditCard.paymentId));
        query.join(client).on(payment.clientNumber.eq(client.noClient));
        query.join(order).on(payment.orderNumber.eq(order.orderNumber)).on(payment.orderCode.eq(order.orderCode));
        query.leftJoin(creditCardRef).on(creditCard.code.eq(creditCardRef.code));
        query.leftJoin(invoiceReference).on(payment.paymentId.eq(invoiceReference.paymentId));
        /**
         * Start evaluation of search parameters
         */
        query.where(openingOperation.branchCode.eq(reportBranchEmployeeParamsDto.getBranchCode()));
        query.where(order.userNumber.eq(reportBranchEmployeeParamsDto.getEmployeeNumber()));

        if (reportBranchEmployeeParamsDto.getStartDate() != null) {
            Date from = reportBranchEmployeeParamsDto.getStartDate();
            Calendar cal = Calendar.getInstance();
            cal.setTime(reportBranchEmployeeParamsDto.getStartDate());
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            from = cal.getTime();
            LOG.info("SERCH BY DATE FROM: " + from.toString());
            query.where(payment.paymentDate.goe(from));
        }

        if (reportBranchEmployeeParamsDto.getEndDate() != null) {
            Date to = reportBranchEmployeeParamsDto.getEndDate();
            Calendar cal = Calendar.getInstance();
            cal.setTime(reportBranchEmployeeParamsDto.getEndDate());
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

    public List<SummarySaleCreditNotePaymentDto> findSummaryCreditNotePaymentByEmployeeAndParams(
            SaleReportBranchEmployeeParamsDto reportBranchEmployeeParamsDto) {

        LOG.info("INIT findSummaryCreditNotePaymentByParams()");

        JPAQuery<SummarySaleCreditNotePaymentDto> query = new JPAQuery<>(em);

        QPaymentEntity payment = QPaymentEntity.paymentEntity;
        QOpeningOperationEntity openingOperation = QOpeningOperationEntity.openingOperationEntity;
        QCreditNotePaymentEntity creditNote = QCreditNotePaymentEntity.creditNotePaymentEntity;
        QClientEntity client = QClientEntity.clientEntity;
        QOrderEntity order = QOrderEntity.orderEntity;
        QInvoiceRerefenceEntity invoiceReference = QInvoiceRerefenceEntity.invoiceRerefenceEntity;

        /**
         * Start evaluation of tables
         */
        query.from(payment).join(openingOperation).on(payment.openingCashId.eq(openingOperation.openingId));
        query.join(creditNote).on(payment.paymentId.eq(creditNote.paymentId));
        query.join(client).on(payment.clientNumber.eq(client.noClient));
        query.join(order).on(payment.orderNumber.eq(order.orderNumber)).on(payment.orderCode.eq(order.orderCode));
        query.leftJoin(invoiceReference).on(payment.paymentId.eq(invoiceReference.paymentId));
        /**
         * Start evaluation of search parameters
         */
        query.where(openingOperation.branchCode.eq(reportBranchEmployeeParamsDto.getBranchCode()));
        query.where(order.userNumber.eq(reportBranchEmployeeParamsDto.getEmployeeNumber()));

        if (reportBranchEmployeeParamsDto.getStartDate() != null) {
            Date from = reportBranchEmployeeParamsDto.getStartDate();
            Calendar cal = Calendar.getInstance();
            cal.setTime(reportBranchEmployeeParamsDto.getStartDate());
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            from = cal.getTime();
            LOG.info("SERCH BY DATE FROM: " + from.toString());
            query.where(payment.paymentDate.goe(from));
        }

        if (reportBranchEmployeeParamsDto.getEndDate() != null) {
            Date to = reportBranchEmployeeParamsDto.getEndDate();
            Calendar cal = Calendar.getInstance();
            cal.setTime(reportBranchEmployeeParamsDto.getEndDate());
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

    public List<SummarySaleTransferPaymentDto> findSummaryTransferPaymentByEmployeeAndParams(
            SaleReportBranchEmployeeParamsDto reportBranchEmployeeParamsDto) {

        LOG.info("INIT findSummaryTransferPaymentByParams()");

        JPAQuery<SummarySaleTransferPaymentDto> query = new JPAQuery<>(em);

        QPaymentEntity payment = QPaymentEntity.paymentEntity;
        QOpeningOperationEntity openingOperation = QOpeningOperationEntity.openingOperationEntity;
        QTransferPaymentEntity transfer = QTransferPaymentEntity.transferPaymentEntity;
        QClientEntity client = QClientEntity.clientEntity;
        QOrderEntity order = QOrderEntity.orderEntity;
        QInvoiceRerefenceEntity invoiceReference = QInvoiceRerefenceEntity.invoiceRerefenceEntity;

        /**
         * Start evaluation of tables
         */
        query.from(payment).join(openingOperation).on(payment.openingCashId.eq(openingOperation.openingId));
        query.join(transfer).on(payment.paymentId.eq(transfer.paymentId));
        query.join(client).on(payment.clientNumber.eq(client.noClient));
        query.join(order).on(payment.orderNumber.eq(order.orderNumber)).on(payment.orderCode.eq(order.orderCode));
        query.leftJoin(invoiceReference).on(payment.paymentId.eq(invoiceReference.paymentId));
        /**
         * Start evaluation of search parameters
         */
        query.where(openingOperation.branchCode.eq(reportBranchEmployeeParamsDto.getBranchCode()));
        query.where(order.userNumber.eq(reportBranchEmployeeParamsDto.getEmployeeNumber()));

        if (reportBranchEmployeeParamsDto.getReferenceTransfer() != null) {
            if (!reportBranchEmployeeParamsDto.getReferenceTransfer().isEmpty()) {
                query.where(transfer.bankingInstitution.eq(reportBranchEmployeeParamsDto.getReferenceTransfer()));
            }
        }

        if (reportBranchEmployeeParamsDto.getStartDate() != null) {
            Date from = reportBranchEmployeeParamsDto.getStartDate();
            Calendar cal = Calendar.getInstance();
            cal.setTime(reportBranchEmployeeParamsDto.getStartDate());
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            from = cal.getTime();
            LOG.info("SERCH BY DATE FROM: " + from.toString());
            query.where(payment.paymentDate.goe(from));
        }

        if (reportBranchEmployeeParamsDto.getEndDate() != null) {
            Date to = reportBranchEmployeeParamsDto.getEndDate();
            Calendar cal = Calendar.getInstance();
            cal.setTime(reportBranchEmployeeParamsDto.getEndDate());
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

    public List<SummarySaleCheckPaymentDto> findSummaryCheckPaymentByEmployeeAndParams(
            SaleReportBranchEmployeeParamsDto reportBranchEmployeeParamsDto) {

        LOG.info("INIT findSummaryCheckPaymentByParams()");

        JPAQuery<SummarySaleCheckPaymentDto> query = new JPAQuery<>(em);

        QPaymentEntity payment = QPaymentEntity.paymentEntity;
        QOpeningOperationEntity openingOperation = QOpeningOperationEntity.openingOperationEntity;
        QCheckPaymentEntity check = QCheckPaymentEntity.checkPaymentEntity;
        QClientEntity client = QClientEntity.clientEntity;
        QOrderEntity order = QOrderEntity.orderEntity;
        QInvoiceRerefenceEntity invoiceReference = QInvoiceRerefenceEntity.invoiceRerefenceEntity;

        /**
         * Start evaluation of tables
         */
        query.from(payment).join(openingOperation).on(payment.openingCashId.eq(openingOperation.openingId));
        query.join(check).on(payment.paymentId.eq(check.paymentId));
        query.join(client).on(payment.clientNumber.eq(client.noClient));
        query.join(order).on(payment.orderNumber.eq(order.orderNumber)).on(payment.orderCode.eq(order.orderCode));
        query.leftJoin(invoiceReference).on(payment.paymentId.eq(invoiceReference.paymentId));
        /**
         * Start evaluation of search parameters
         */
        query.where(openingOperation.branchCode.eq(reportBranchEmployeeParamsDto.getBranchCode()));
        query.where(order.userNumber.eq(reportBranchEmployeeParamsDto.getEmployeeNumber()));

        if (reportBranchEmployeeParamsDto.getReferenceCheck() != null) {
            if (!reportBranchEmployeeParamsDto.getReferenceCheck().isEmpty()) {
                query.where(check.bankingInstitution.eq(reportBranchEmployeeParamsDto.getReferenceCheck()));
            }
        }

        if (reportBranchEmployeeParamsDto.getStartDate() != null) {
            Date from = reportBranchEmployeeParamsDto.getStartDate();
            Calendar cal = Calendar.getInstance();
            cal.setTime(reportBranchEmployeeParamsDto.getStartDate());
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            from = cal.getTime();
            LOG.info("SERCH BY DATE FROM: " + from.toString());
            query.where(payment.paymentDate.goe(from));
        }

        if (reportBranchEmployeeParamsDto.getEndDate() != null) {
            Date to = reportBranchEmployeeParamsDto.getEndDate();
            Calendar cal = Calendar.getInstance();
            cal.setTime(reportBranchEmployeeParamsDto.getEndDate());
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

