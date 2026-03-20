package mx.com.endtoend.infrastructure.services.jde.clients.demo.repository;

import java.util.HashMap;
import java.util.List;

import mx.com.endtoend.domain.clients.dto.FiltersClientDto;
import mx.com.endtoend.infrastructure.services.jde.clients.demo.entities.F0101;

public interface CustomF0101DemoRepository {

	List<F0101> getClient(HashMap<String, Object> conditions);
	List<F0101> findByParams(FiltersClientDto filtersClientDto);
}