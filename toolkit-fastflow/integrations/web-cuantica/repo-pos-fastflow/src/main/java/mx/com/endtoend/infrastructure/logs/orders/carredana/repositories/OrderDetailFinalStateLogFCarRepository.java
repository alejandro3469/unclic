package mx.com.endtoend.infrastructure.logs.orders.carredana.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.com.endtoend.infrastructure.logs.orders.entities.OrderDetailFinalStateEntity;

@Repository
public interface OrderDetailFinalStateLogFCarRepository extends JpaRepository<OrderDetailFinalStateEntity, Long> {

}
