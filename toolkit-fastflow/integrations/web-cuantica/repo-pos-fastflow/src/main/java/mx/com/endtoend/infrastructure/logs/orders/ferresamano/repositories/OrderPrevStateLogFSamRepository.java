package mx.com.endtoend.infrastructure.logs.orders.ferresamano.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.com.endtoend.infrastructure.logs.orders.entities.OrderPreviousStateEntity;

@Repository
public interface OrderPrevStateLogFSamRepository extends JpaRepository<OrderPreviousStateEntity, Long> {

}
