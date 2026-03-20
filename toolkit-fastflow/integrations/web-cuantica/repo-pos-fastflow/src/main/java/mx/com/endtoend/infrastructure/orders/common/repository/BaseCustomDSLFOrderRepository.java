package mx.com.endtoend.infrastructure.orders.common.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import mx.com.endtoend.domain.orders.dto.GenericSerchParamsOrderDto;
import mx.com.endtoend.infrastructure.orders.calzada.fragua.repositories.CustomDSLCFraguaOrderRepository;
import mx.com.endtoend.infrastructure.orders.calzada.repositories.CustomDSLFCalzadaOrderRepository;
import mx.com.endtoend.infrastructure.orders.demo.repositories.CustomDSLDemoOrderRepository;
import mx.com.endtoend.infrastructure.orders.carredana.repositories.CustomDSLFCarredanaOrderRepository;
import mx.com.endtoend.smart.bussiness.model.orders.dto.OrderSummary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import mx.com.endtoend.infrastructure.catalogue.orders.common.entities.QStatusEntity;
import mx.com.endtoend.infrastructure.client.common.entities.QClientEntity;
import mx.com.endtoend.infrastructure.orders.common.entities.QOrderEntity;
import org.springframework.data.repository.NoRepositoryBean;

import javax.persistence.EntityManager;
import javax.persistence.MappedSuperclass;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@MappedSuperclass
@NoRepositoryBean
public class BaseCustomDSLFOrderRepository extends QuerydslRepositorySupport {

    private final Logger LOG;
    private final EntityManager em;
    private  final Class<?> _typeEntity;

    public BaseCustomDSLFOrderRepository(Class<?> loggerClass, EntityManager _em) {
        super(OrderSummary.class);
        LOG = LoggerFactory.getLogger(loggerClass);
        _typeEntity = loggerClass;
        em = _em;
    }

    public List<OrderSummary> getOrderByParams(GenericSerchParamsOrderDto genericParams) {

        JPAQuery<OrderSummary> query = new JPAQuery<>(em);

        QOrderEntity order = QOrderEntity.orderEntity;
        QClientEntity client = QClientEntity.clientEntity;
        QStatusEntity status = QStatusEntity.statusEntity;

        /**
         * Start evaluation of table
         */
        query.from(order);
        query.innerJoin(client).on(order.clientId.eq(client.id));
        query.innerJoin(status).on(order.status.id.eq(status.id));

        /**
         * Start evaluation of search parameters
         */

        if (genericParams.getIdEmployees() != null) {
            if (!genericParams.getIdEmployees().isEmpty()) {
                query.where(order.idUser.in(genericParams.getIdEmployees()));
            }
        }

        if (genericParams.getBranchCode() != null) {
            if (!genericParams.getBranchCode().isEmpty()) {
                LOG.info("SERCH BY BRANCH-CODE:" + genericParams.getBranchCode());
                query.where(order.branchCode.eq(genericParams.getBranchCode()));
            }
        }

        if (genericParams.getOrderCode() != null) {
            if (!genericParams.getOrderCode().isEmpty()) {
                LOG.info("SERCH BY ORDER-CODE: " + genericParams.getOrderCode());
                query.where(order.orderCode.eq(genericParams.getOrderCode()));
            }
        }

        if (genericParams.getOrderNumber() != null) {
            if (genericParams.getOrderNumber().compareTo(new BigDecimal(0)) != 0) {
                LOG.info("SERCH BY ORDER-NUMBER: " + genericParams.getOrderNumber());
                query.where(order.orderNumber.eq(genericParams.getOrderNumber()));
            }
        }

        if (genericParams.getDatefrom() != null) {

            Date from = genericParams.getDatefrom();
            Calendar cal = Calendar.getInstance();
            cal.setTime(genericParams.getDatefrom());
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            from = cal.getTime();
            LOG.info("SERCH BY DATE FROM: " + from.toString());
            query.where(order.creationDate.goe(from));
        }

        if (genericParams.getDateto() != null) {
            Date to = genericParams.getDatefrom();
            Calendar cal = Calendar.getInstance();
            cal.setTime(genericParams.getDateto());
            cal.set(Calendar.HOUR_OF_DAY, 23);
            cal.set(Calendar.MINUTE, 59);
            cal.set(Calendar.SECOND, 59);
            cal.set(Calendar.MILLISECOND, 59);
            to = cal.getTime();
            LOG.info("SERCH BY DATE TO: " + to.toString());
            query.where(order.creationDate.loe(to));
        }

        if (genericParams.getClientNumber() != null) {
            LOG.info("SERCH BY CLIENT-NUMBER: " + genericParams.getClientNumber());
            query.where(client.noClient.eq(genericParams.getClientNumber()));
        }

        if (genericParams.getClientName() != null) {
            if (!genericParams.getClientName().isEmpty()) {
                LOG.info("SERCH BY CLIENT-NAME: " + genericParams.getClientName());
                query.where(client.name.like("%" + genericParams.getClientName() + "%"));
            }
        }

        if (genericParams.getStatusCode() != null) {
            if (!genericParams.getStatusCode().isEmpty()) {
                LOG.info("SERCH BY STATUS-ORDER: " + genericParams.getStatusCode());
                query.where(status.code.eq(genericParams.getStatusCode()));
            }
        }

        if (genericParams.getIdUser() != null) {
            LOG.info("SERCH BY EMPLOYE-ID: " + genericParams.getIdUser());
            query.where(order.idUser.eq(genericParams.getIdUser()));
        }

        /**
         * Select fields
         */
        if(_typeEntity == CustomDSLCFraguaOrderRepository.class){
            query.select(Projections.constructor(OrderSummary.class, order.orderId, order.orderNumber, order.orderCode,
                    order.branchCode, order.companyNumber, order.currency, order.exchangeRate, order.creationDate,
                    order.requestDate, order.validityDate, order.clientTax, order.userNumber, order.idUser,
                    order.employeeEmail, order.subTotal, order.ivaTotal, order.orderTotal, order.pendingPayment,
                    order.discountTotal, status.code, status.description, order.retentionCode, order.orderType,
                    order.tempMigStatus, order.observations, order.cfdiType

            ));
        }else if(_typeEntity == CustomDSLFCalzadaOrderRepository.class){
            query.select(Projections.constructor(OrderSummary.class, order.orderId, order.orderNumber, order.orderCode,
                    order.branchCode, order.companyNumber, order.currency, order.exchangeRate, order.creationDate,
                    order.requestDate, order.validityDate, order.clientTax, order.userNumber, order.idUser,
                    order.employeeEmail, order.subTotal, order.ivaTotal, order.orderTotal, order.pendingPayment,
                    order.discountTotal, status.code, status.description, order.retentionCode, order.orderType,
                    order.tempMigStatus, order.observations, order.cfdiType, order.isUpdated, order.clientId, order.isRetentionOrder

            ));
        }
        else if(_typeEntity == CustomDSLDemoOrderRepository.class){
            query.select(Projections.constructor(OrderSummary.class, order.orderId, order.orderNumber, order.orderCode,
                    order.branchCode, order.companyNumber, order.currency, order.exchangeRate, order.creationDate,
                    order.requestDate, order.validityDate, order.clientTax, order.userNumber, order.idUser,
                    order.employeeEmail, order.subTotal, order.ivaTotal, order.orderTotal, order.pendingPayment,
                    order.discountTotal, status.code, status.description, order.retentionCode, order.orderType,
                    order.tempMigStatus, order.observations, order.cfdiType, order.isUpdated, order.clientId, order.isRetentionOrder

            ));
        }
        else if(_typeEntity == CustomDSLFCarredanaOrderRepository.class){
            query.select(Projections.constructor(OrderSummary.class, order.orderId, order.orderNumber, order.orderCode,
                    order.branchCode, order.companyNumber, order.currency, order.exchangeRate, order.creationDate,
                    order.requestDate, order.validityDate, order.clientTax, order.userNumber, order.idUser,
                    order.employeeEmail, order.subTotal, order.ivaTotal, order.orderTotal, order.pendingPayment,
                    order.discountTotal, status.code, status.description, order.retentionCode, order.orderType,
                    order.tempMigStatus, order.observations, order.cfdiType, order.isUpdated, order.clientId, order.isRetentionOrder

            ));
        }
        // ADD_COMPANY

        return query.fetch();
    }


    public List<OrderSummary> getOrderByCompany(GenericSerchParamsOrderDto genericParams) {

        JPAQuery<OrderSummary> query = new JPAQuery<>(em);

        QOrderEntity order = QOrderEntity.orderEntity;
        QClientEntity client = QClientEntity.clientEntity;
        QStatusEntity status = QStatusEntity.statusEntity;

        /**
         * Start evaluation of table
         */
        query.from(order);
        query.innerJoin(client).on(order.clientId.eq(client.id));
        query.innerJoin(status).on(order.status.id.eq(status.id));

        /**
         * Start evaluation of search parameters
         */
        if (genericParams.getOrderCode() != null) {
            if (!genericParams.getOrderCode().isEmpty()) {
                LOG.info("SERCH BY ORDER-CODE: " + genericParams.getOrderCode());
                query.where(order.orderCode.eq(genericParams.getOrderCode()));
            }
        }

        if (genericParams.getOrderNumber() != null) {
            if (genericParams.getOrderNumber().compareTo(new BigDecimal(0)) != 0) {
                LOG.info("SERCH BY ORDER-NUMBER: " + genericParams.getOrderNumber());
                query.where(order.orderNumber.eq(genericParams.getOrderNumber()));
            }
        }

        if (genericParams.getDatefrom() != null) {

            Date from = genericParams.getDatefrom();
            Calendar cal = Calendar.getInstance();
            cal.setTime(genericParams.getDatefrom());
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            from = cal.getTime();
            LOG.info("SERCH BY DATE FROM: " + from.toString());
            query.where(order.creationDate.goe(from));
        }

        if (genericParams.getDateto() != null) {
            Date to = genericParams.getDatefrom();
            Calendar cal = Calendar.getInstance();
            cal.setTime(genericParams.getDateto());
            cal.set(Calendar.HOUR_OF_DAY, 23);
            cal.set(Calendar.MINUTE, 59);
            cal.set(Calendar.SECOND, 59);
            cal.set(Calendar.MILLISECOND, 59);
            to = cal.getTime();
            LOG.info("SERCH BY DATE TO: " + to.toString());
            query.where(order.creationDate.loe(to));
        }

        if (genericParams.getClientNumber() != null) {
            LOG.info("SERCH BY CLIENT-NUMBER: " + genericParams.getClientNumber());
            query.where(client.noClient.eq(genericParams.getClientNumber()));
        }

        if (genericParams.getClientName() != null) {
            if (!genericParams.getClientName().isEmpty()) {
                LOG.info("SERCH BY CLIENT-NAME: " + genericParams.getClientName());
                query.where(client.name.like("%" + genericParams.getClientName() + "%"));
            }
        }

        if (genericParams.getStatusCode() != null) {
            if (!genericParams.getStatusCode().isEmpty()) {
                LOG.info("SERCH BY STATUS-ORDER: " + genericParams.getStatusCode());
                query.where(status.code.eq(genericParams.getStatusCode()));
            }
        }
        /**
         * Select fields
         */
        query.select(Projections.constructor(OrderSummary.class, order.orderId, order.orderNumber, order.orderCode,
                order.branchCode, order.companyNumber, order.currency, order.exchangeRate, order.creationDate,
                order.requestDate, order.validityDate, order.clientTax, order.userNumber, order.idUser,
                order.employeeEmail, order.subTotal, order.ivaTotal, order.orderTotal, order.pendingPayment,
                order.discountTotal, status.code, status.description, order.retentionCode, order.orderType,
                order.tempMigStatus, order.observations, order.cfdiType, order.isUpdated, order.clientId, order.isRetentionOrder
        ));

        return query.fetch();
    }

    public List<OrderSummary> getAllOrders(GenericSerchParamsOrderDto genericParams, List<BigDecimal> orderNumbers) {
        JPAQuery<OrderSummary> query = new JPAQuery<>(em);

        QOrderEntity order = QOrderEntity.orderEntity;
        QClientEntity client = QClientEntity.clientEntity;
        QStatusEntity status = QStatusEntity.statusEntity;

        query.from(order);
        query.innerJoin(client).on(order.clientId.eq(client.id));
        query.innerJoin(status).on(order.status.id.eq(status.id));

        if (orderNumbers != null && !orderNumbers.isEmpty()) {
            query.where(order.orderNumber.in(orderNumbers));
        }

        query.select(Projections.constructor(OrderSummary.class,
                order.orderId, order.orderNumber, order.orderCode, order.branchCode, order.companyNumber,
                order.currency, order.exchangeRate, order.creationDate, order.requestDate, order.validityDate,
                order.clientTax, order.userNumber, order.idUser, order.employeeEmail, order.subTotal, order.ivaTotal,
                order.orderTotal, order.pendingPayment, order.discountTotal, status.code, status.description,
                order.retentionCode, order.orderType, order.tempMigStatus, order.observations, order.cfdiType,
                order.isUpdated, order.clientId
        ));

        return query.fetch();
    }

}
