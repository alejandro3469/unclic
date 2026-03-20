package mx.com.endtoend.infrastructure.logs.orders.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.com.endtoend.infrastructure.logs.orders.entities.OrderPreviousStateEntity;

@Repository
public interface OrderPrevStateLogDemoRepository extends JpaRepository<OrderPreviousStateEntity, Long>{

}
