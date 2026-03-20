package mx.com.endtoend.infrastructure.client.Calzada.repositories;


import mx.com.endtoend.infrastructure.client.common.repository.BaseClientRepositoryImpl;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.persistence.EntityManager;

public class ClientRepositoryImpl extends BaseClientRepositoryImpl {

	public ClientRepositoryImpl(@Qualifier("calzadaDataEntityManagerFactory") EntityManager _entityManager) {
		super(_entityManager);
	}

}
