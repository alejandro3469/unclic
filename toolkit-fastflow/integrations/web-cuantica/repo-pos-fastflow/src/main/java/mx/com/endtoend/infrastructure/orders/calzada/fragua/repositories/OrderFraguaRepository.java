package mx.com.endtoend.infrastructure.orders.calzada.fragua.repositories;

import java.math.BigDecimal;

import javax.transaction.Transactional;

import mx.com.endtoend.infrastructure.orders.common.repository.BaseOrderRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderFraguaRepository extends BaseOrderRepository {

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


}
