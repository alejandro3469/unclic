package mx.com.endtoend.infrastructure.catalogue.address.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.com.endtoend.infrastructure.catalogue.address.entities.CoordinateEntity;

@Repository
public interface CoordinateDemoRepository extends JpaRepository<CoordinateEntity, Long> {
}
