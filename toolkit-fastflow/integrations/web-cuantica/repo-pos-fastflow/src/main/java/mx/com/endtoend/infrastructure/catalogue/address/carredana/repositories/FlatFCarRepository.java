package mx.com.endtoend.infrastructure.catalogue.address.carredana.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.com.endtoend.infrastructure.catalogue.address.entities.FlatEntity;

@Repository
public interface FlatFCarRepository extends JpaRepository<FlatEntity, Long> {
}
