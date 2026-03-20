package mx.com.endtoend.infrastructure.logs.orders.calzada.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.com.endtoend.infrastructure.logs.orders.entities.OrderFinalStateEntity;

@Repository
public interface OrderFinalStateLogFCalRepository extends JpaRepository<OrderFinalStateEntity, Long>{

}
