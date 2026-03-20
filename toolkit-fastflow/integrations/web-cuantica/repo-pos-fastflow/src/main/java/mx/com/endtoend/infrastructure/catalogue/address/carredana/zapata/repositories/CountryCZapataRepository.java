package mx.com.endtoend.infrastructure.catalogue.address.carredana.zapata.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.com.endtoend.infrastructure.catalogue.address.entities.CountryEntity;

@Repository
public interface CountryCZapataRepository extends JpaRepository<CountryEntity, Long> {
}
