package mx.com.endtoend.infrastructure.client.Calzada.fragua.repositories;

import mx.com.endtoend.infrastructure.client.common.repository.BaseClientRepositoryImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import javax.persistence.EntityManager;

public class ClientFraguaRepositoryImpl extends BaseClientRepositoryImpl {

	public ClientFraguaRepositoryImpl(@Qualifier("calzadaDataEntityManagerFactory") EntityManager _entityManager) {
		super(_entityManager);
	}
}
