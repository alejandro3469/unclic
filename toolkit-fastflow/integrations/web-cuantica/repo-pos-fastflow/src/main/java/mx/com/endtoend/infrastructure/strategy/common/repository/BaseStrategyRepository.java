package mx.com.endtoend.infrastructure.strategy.common.repository;

import mx.com.endtoend.infrastructure.strategy.common.entities.StrategyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository
public interface BaseStrategyRepository extends JpaRepository<StrategyEntity, Serializable> {
}
