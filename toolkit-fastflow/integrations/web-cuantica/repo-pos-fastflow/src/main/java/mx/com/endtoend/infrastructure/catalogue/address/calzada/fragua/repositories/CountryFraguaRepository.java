package mx.com.endtoend.infrastructure.catalogue.address.calzada.fragua.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.com.endtoend.infrastructure.catalogue.address.entities.CountryEntity;

@Repository
public interface CountryFraguaRepository extends JpaRepository<CountryEntity, Long> {
}
