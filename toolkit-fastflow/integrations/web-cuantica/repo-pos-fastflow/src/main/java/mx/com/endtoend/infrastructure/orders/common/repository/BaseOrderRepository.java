package mx.com.endtoend.infrastructure.orders.common.repository;

import mx.com.endtoend.infrastructure.orders.common.entities.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import javax.persistence.MappedSuperclass;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@NoRepositoryBean
@MappedSuperclass
public interface BaseOrderRepository extends JpaRepository<OrderEntity, Long> {

	@Query("FROM OrderEntity o WHERE o.orderNumber IN :orderNumbers")
    List<OrderEntity> getOrdersToSendToQueue(@Param("orderNumbers") List<BigDecimal> orderNumbers);


    @Query("FROM OrderEntity o WHERE o.orderNumber=:orderNumber")
    Optional<OrderEntity> findByOrderNumber(@Param("orderNumber") BigDecimal orderNumber);

    @Query("FROM OrderEntity o WHERE o.orderNumber=:orderNumber AND o.orderCode=:orderCode")
    Optional<OrderEntity> findByOrderCodeAndOrderNumber(@Param("orderNumber") BigDecimal orderNumber,
                                                        @Param("orderCode") String orderCode);

    @Transactional
    @Modifying
    @Query("UPDATE OrderEntity o set o.isRetentionOrder=:approve WHERE o.orderId=:id AND o.orderNumber=:orderNumber")
    int approveOrderByIdAndOrderNumber(@Param("id") Long id, @Param("approve") boolean approve,
                                       @Param("orderNumber") BigDecimal orderNumber);

    @Transactional
    @Modifying
    @Query("UPDATE OrderEntity o set o.isUpdated=true WHERE o.orderNumber=:orderNumber AND o.orderCode =:orderCode")
    int changeStatusOrderToActiveUpdate(@Param("orderNumber") BigDecimal orderNumber,
                                        @Param("orderCode") String orderCode);

    @Transactional
    @Modifying
    @Query("UPDATE OrderEntity o set o.isUpdated=:status WHERE o.orderNumber=:orderNumber AND o.orderCode =:orderCode")
    int changeStatusActiveByOrderNumberAndOrderCode(@Param("orderNumber") BigDecimal orderNumber,
                                                    @Param("orderCode") String orderCode, @Param("status") boolean status);

    @Transactional
    @Modifying
    @Query("UPDATE OrderEntity o set o.isUpdated=false WHERE o.orderNumber=:orderNumber AND o.orderCode =:orderCode")
    int changeStatusOrderToInactiveUpdate(@Param("orderNumber") BigDecimal orderNumber,
                                          @Param("orderCode") String orderCode);


    @Transactional
    @Modifying
    @Query("UPDATE OrderEntity o set o.isConverted=:converterd WHERE o.orderCode=:orderCode AND o.orderNumber=:orderNumber")
    int updateConvertionStatusByOrderNumberAndOrderCode(@Param("orderCode") String orderCode,
                                                        @Param("converterd") boolean converterd, @Param("orderNumber") BigDecimal orderNumber);

    @Transactional
    @Modifying
    @Query("UPDATE OrderEntity o set o.status.id=:statusId WHERE o.orderNumber=:orderNumber AND o.orderCode =:orderCode")
    int changeStatusOrderByOrderNumberAndCode(@Param("orderNumber") BigDecimal orderNumber,
                                              @Param("orderCode") String orderCode, @Param("statusId") Long statusId);

    OrderEntity findByOrderId(Long id);

    OrderEntity findByOrderNumber(Long orderNumber);

    @Transactional
    @Modifying
    @Query("UPDATE OrderEntity o "
            + "SET o.status.id=:statusId, o.pendingPayment=:pendingPayment, o.batchFolio=:batchFolio, o.isUpdated=:status "
            + "WHERE o.orderNumber=:orderNumber AND o.orderCode =:orderCode")
    int updateStatusPaymentByOrderNumberAndOrderCode(@Param("orderNumber") BigDecimal orderNumber,
                                                     @Param("orderCode") String orderCode, @Param("statusId") Long statusId,
                                                     @Param("pendingPayment") BigDecimal pendingPayment, @Param("batchFolio") Long batchFolio,
                                                     @Param("status") boolean status);

}
