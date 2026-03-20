package mx.com.endtoend.infrastructure.services.jde.clients.ferresamano.repository;

import java.util.HashMap;
import java.util.List;

import mx.com.endtoend.infrastructure.services.jde.clients.ferresamano.entities.F0101Z2;

public interface CustomF0101Z2FSamanoRepository {

	List<F0101Z2> getClient(HashMap<String,Object> conditions);
}
