package mx.com.endtoend.infrastructure.articles.carredana.oracle.repositories;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.com.endtoend.infrastructure.articles.carredana.oracle.entities.F4101;

@Repository
public interface F4101FCarRepository extends JpaRepository<F4101, BigDecimal>{

}
