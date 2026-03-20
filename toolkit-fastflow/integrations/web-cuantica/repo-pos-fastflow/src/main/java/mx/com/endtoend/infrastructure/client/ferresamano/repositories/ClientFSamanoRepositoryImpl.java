package mx.com.endtoend.infrastructure.client.ferresamano.repositories;

import mx.com.endtoend.infrastructure.client.common.repository.BaseClientRepositoryImpl;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.persistence.EntityManager;

public class ClientFSamanoRepositoryImpl extends BaseClientRepositoryImpl {

	public ClientFSamanoRepositoryImpl(@Qualifier("zapataDataEntityManagerFactory")
									   EntityManager entityManager){
		super(entityManager);
	}
}
