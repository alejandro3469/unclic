package mx.com.endtoend.infrastructure.logs.orders.carredana.zapata.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.com.endtoend.infrastructure.logs.orders.entities.OrderDetailPreviousStateEntity;

@Repository
public interface OrderDetailPrevStateLogCZapRepository extends JpaRepository<OrderDetailPreviousStateEntity, Long> {

}
