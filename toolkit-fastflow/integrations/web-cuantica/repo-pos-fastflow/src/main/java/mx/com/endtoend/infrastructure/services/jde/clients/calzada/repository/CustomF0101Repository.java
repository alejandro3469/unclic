package mx.com.endtoend.infrastructure.services.jde.clients.calzada.repository;

import mx.com.endtoend.domain.clients.dto.FiltersClientDto;
import mx.com.endtoend.infrastructure.services.jde.clients.calzada.entities.F0101;

import java.util.List;

public interface CustomF0101Repository {

    List<F0101> findByParams(FiltersClientDto filtersClientDto);
}
