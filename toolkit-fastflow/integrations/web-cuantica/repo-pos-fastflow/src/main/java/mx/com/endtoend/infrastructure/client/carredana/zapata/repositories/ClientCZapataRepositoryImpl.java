package mx.com.endtoend.infrastructure.client.carredana.zapata.repositories;

import mx.com.endtoend.infrastructure.client.common.repository.BaseClientRepositoryImpl;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.persistence.EntityManager;

public class ClientCZapataRepositoryImpl extends BaseClientRepositoryImpl
{

	public ClientCZapataRepositoryImpl(@Qualifier("zapataDataEntityManagerFactory")
									   EntityManager entityManager){
		super(entityManager);
	}
}
