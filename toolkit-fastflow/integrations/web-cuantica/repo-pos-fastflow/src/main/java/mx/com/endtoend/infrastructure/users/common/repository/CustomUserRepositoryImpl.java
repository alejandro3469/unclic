package mx.com.endtoend.infrastructure.users.common.repository;


import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class CustomUserRepositoryImpl extends BaseCustomUserRepositoryImpl {

	public CustomUserRepositoryImpl(EntityManager entityManager){
		super(entityManager, CustomUserRepositoryImpl.class);
	}
}
