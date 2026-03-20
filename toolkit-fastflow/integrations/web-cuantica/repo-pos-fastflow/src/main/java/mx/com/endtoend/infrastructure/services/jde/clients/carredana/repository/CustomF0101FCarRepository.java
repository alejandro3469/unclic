package mx.com.endtoend.infrastructure.services.jde.clients.carredana.repository;

import java.util.List;

import mx.com.endtoend.domain.clients.dto.FiltersClientDto;
import mx.com.endtoend.infrastructure.services.jde.clients.carredana.entities.F0101;

public interface CustomF0101FCarRepository {

	List<F0101> findByParams(FiltersClientDto filtersClientDto);
}
