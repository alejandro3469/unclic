package mx.com.endtoend.infrastructure.orders.ferresamano.repositories;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import mx.com.endtoend.infrastructure.orders.common.repository.BaseOrderRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.com.endtoend.infrastructure.orders.common.entities.OrderEntity;

@Repository
public interface OrderFSamanoRepository extends BaseOrderRepository {

	@Query("FROM OrderEntity o WHERE o.orderCode=:code")
	List<OrderEntity> findaLLByOrderCode(@Param("code") String code);
	
	@Query("FROM OrderEntity o WHERE o.orderCode=:code AND o.creationDate > :date")
	List<OrderEntity> findaLLByOrderCodeAndDate(@Param("code") String code, @Param("date") Date date);

	@Transactional
	@Modifying
	@Query("UPDATE OrderEntity o set o.status.id=:statusId WHERE o.orderNumber=:orderNumber AND o.orderCode =:orderCode")
	int changeStatusOrderByOrderNumberAndCode(@Param("orderNumber") BigDecimal orderNumber,
			@Param("orderCode") String orderCode, @Param("statusId") Long statusId);
	
	@Transactional
	@Modifying
	@Query("UPDATE OrderEntity o set o.isUpdated=:status WHERE o.orderNumber=:orderNumber AND o.orderCode =:orderCode")
	int changeStatusActiveByOrderNumberAndOrderCode(@Param("orderNumber") BigDecimal orderNumber,
			@Param("orderCode") String orderCode, @Param("status") boolean status);
	
	@Transactional
	@Modifying
	@Query("UPDATE OrderEntity o "
			+ "SET o.status.id=:statusId, o.pendingPayment=:pendingPayment, o.batchFolio=:batchFolio, o.isUpdated=:status "
			+ "WHERE o.orderNumber=:orderNumber AND o.orderCode =:orderCode")
	int updateStatusPaymentByOrderNumberAndOrderCode(@Param("orderNumber") BigDecimal orderNumber,
			@Param("orderCode") String orderCode, @Param("statusId") Long statusId,
			@Param("pendingPayment") double pendingPayment, @Param("batchFolio") Long batchFolio,
			@Param("status") boolean status);
	
}
