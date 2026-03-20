package mx.com.endtoend.infrastructure.orders.calzada.repositories;

import java.util.Date;
import java.util.List;

import mx.com.endtoend.infrastructure.orders.common.repository.BaseOrderRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.com.endtoend.infrastructure.orders.common.entities.OrderEntity;

@Repository
public interface OrderRepository extends BaseOrderRepository {

	@Query("FROM OrderEntity o WHERE o.orderCode=:code")
	List<OrderEntity> findaLLByOrderCode(@Param("code") String code);

	@Query("FROM OrderEntity o WHERE o.orderCode=:code AND o.creationDate > :date")
	List<OrderEntity> findaLLByOrderCodeAndDate(@Param("code") String code, @Param("date") Date date);

}
