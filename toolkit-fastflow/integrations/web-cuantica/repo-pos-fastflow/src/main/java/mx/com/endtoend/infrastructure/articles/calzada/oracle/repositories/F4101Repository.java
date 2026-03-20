package mx.com.endtoend.infrastructure.articles.calzada.oracle.repositories;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.com.endtoend.infrastructure.articles.calzada.oracle.entities.F4101;

@Repository
public interface F4101Repository extends JpaRepository<F4101, BigDecimal>{

}
