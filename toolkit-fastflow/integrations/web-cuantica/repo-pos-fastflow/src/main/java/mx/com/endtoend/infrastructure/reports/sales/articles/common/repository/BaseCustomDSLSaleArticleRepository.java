package mx.com.endtoend.infrastructure.reports.sales.articles.common.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import mx.com.endtoend.domain.reports.sales.articles.dto.SaleReportArticleParamsDto;
import mx.com.endtoend.domain.reports.sales.articles.dto.SummaryArticleSaleDto;
import mx.com.endtoend.infrastructure.reports.sales.articles.calzada.repositories.CustomDSLSaleArticleCalzadaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.repository.NoRepositoryBean;
import mx.com.endtoend.infrastructure.catalogue.orders.common.entities.QStatusEntity;
import mx.com.endtoend.infrastructure.client.common.entities.QClientEntity;
import mx.com.endtoend.infrastructure.orders.common.entities.QOrderDetailEntity;
import mx.com.endtoend.infrastructure.orders.common.entities.QOrderEntity;

import javax.persistence.EntityManager;
import javax.persistence.MappedSuperclass;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@NoRepositoryBean
@MappedSuperclass
public class BaseCustomDSLSaleArticleRepository {

    private final EntityManager em;
    private final Logger LOG;

    public BaseCustomDSLSaleArticleRepository(Class<?> loggerClass, EntityManager _em) {
        em = _em;
        LOG = LoggerFactory.getLogger(loggerClass);
    }

    public List<SummaryArticleSaleDto> findSummarySaleArticleByParams(
            SaleReportArticleParamsDto saleReportArticleParams) {

        LOG.info("INIT findSummarySaleArticleByParams()");

        JPAQuery<SummaryArticleSaleDto> query = new JPAQuery<>(em);

        QOrderEntity order = QOrderEntity.orderEntity;
        QClientEntity client = QClientEntity.clientEntity;
        QStatusEntity status = QStatusEntity.statusEntity;
        QOrderDetailEntity orderDetail = QOrderDetailEntity.orderDetailEntity;

        /**
         * Start evaluation of tables
         */
        query.from(order).innerJoin(client).on(order.clientId.eq(client.id));
        query.innerJoin(status).on(order.status.id.eq(status.id));
        query.innerJoin(orderDetail).on(order.orderId.eq(orderDetail.order.orderId));
        /**
         * Start evaluation of search parameters
         */
        query.where(order.branchCode.eq(saleReportArticleParams.getBranchCode()));

        if (saleReportArticleParams.getStartDate() != null) {
            Date from = saleReportArticleParams.getStartDate();
            Calendar cal = Calendar.getInstance();
            cal.setTime(saleReportArticleParams.getStartDate());
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            from = cal.getTime();
            LOG.info("SERCH BY DATE FROM: " + from.toString());
            query.where(order.creationDate.goe(from));
        }

        if (saleReportArticleParams.getEndDate() != null) {
            Date to = saleReportArticleParams.getEndDate();
            Calendar cal = Calendar.getInstance();
            cal.setTime(saleReportArticleParams.getEndDate());
            cal.set(Calendar.HOUR_OF_DAY, 23);
            cal.set(Calendar.MINUTE, 59);
            cal.set(Calendar.SECOND, 59);
            cal.set(Calendar.MILLISECOND, 59);
            to = cal.getTime();
            LOG.info("SERCH BY DATE TO: " + to.toString());
            query.where(order.creationDate.loe(to));
        }

        /**
         * Select fields
         */
        query.select(Projections.constructor(SummaryArticleSaleDto.class,

                order.idUser, orderDetail.priceType, order.creationDate, order.orderCode, order.orderNumber,
                client.noClient, orderDetail.descriptionOne, orderDetail.descriptionTwo,
                orderDetail.alternateDescription, orderDetail.articleCode, orderDetail.requestAmount,
                orderDetail.finalUnitPrice, orderDetail.subTotal, orderDetail.subTotalTax, orderDetail.taxValueTwo,
                orderDetail.unitMeasurement, status.description

        ));

        return query.fetch();
    }
}
