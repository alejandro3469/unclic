package mx.com.endtoend.infrastructure.services.jde.clients.demo.repository;

import java.util.HashMap;
import java.util.List;

import mx.com.endtoend.infrastructure.services.jde.clients.demo.entities.F0101Z2;

public interface CustomF0101Z2DemoRepository {

	List<F0101Z2> getClient(HashMap<String,Object> conditions);
}
