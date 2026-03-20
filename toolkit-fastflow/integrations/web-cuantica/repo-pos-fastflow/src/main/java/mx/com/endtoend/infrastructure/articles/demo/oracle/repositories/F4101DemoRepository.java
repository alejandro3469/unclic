package mx.com.endtoend.infrastructure.articles.demo.oracle.repositories;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.com.endtoend.infrastructure.articles.demo.oracle.entities.F4101;

@Repository
public interface F4101DemoRepository extends JpaRepository<F4101, BigDecimal>{

}
