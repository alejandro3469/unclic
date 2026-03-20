package mx.com.endtoend.infrastructure.client.carredana.repositories;

import mx.com.endtoend.infrastructure.client.common.repository.BaseClientRepositoryImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import javax.persistence.EntityManager;

public class ClientFCarRepositoryImpl extends BaseClientRepositoryImpl {

	public ClientFCarRepositoryImpl(@Qualifier("calzadaDataEntityManagerFactory")
									EntityManager entityManager) {
		super(entityManager);
	}
}
