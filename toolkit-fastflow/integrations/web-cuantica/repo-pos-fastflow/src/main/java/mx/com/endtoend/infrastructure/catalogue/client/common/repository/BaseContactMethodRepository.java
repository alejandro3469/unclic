package mx.com.endtoend.infrastructure.catalogue.client.common.repository;


import mx.com.endtoend.infrastructure.catalogue.client.common.entities.ContactMethodEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import javax.persistence.MappedSuperclass;

@NoRepositoryBean
@MappedSuperclass
public interface BaseContactMethodRepository extends JpaRepository<ContactMethodEntity, Long> {

}

