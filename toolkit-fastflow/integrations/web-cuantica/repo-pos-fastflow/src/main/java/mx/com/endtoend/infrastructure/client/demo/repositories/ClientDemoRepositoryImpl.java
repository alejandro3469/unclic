package mx.com.endtoend.infrastructure.client.demo.repositories;

import mx.com.endtoend.infrastructure.client.common.repository.BaseClientRepositoryImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import javax.persistence.EntityManager;

public class ClientDemoRepositoryImpl extends BaseClientRepositoryImpl {

	public ClientDemoRepositoryImpl(@Qualifier("demoDataEntityManagerFactory")
									EntityManager entityManager) {
		super(entityManager);
	}
}
