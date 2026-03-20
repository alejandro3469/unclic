package mx.com.endtoend.infrastructure.catalogue.article.common.repository;


import mx.com.endtoend.infrastructure.catalogue.article.common.entities.ArticleFamilyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import javax.persistence.MappedSuperclass;

@NoRepositoryBean
@MappedSuperclass
public interface BaseArticleFamilyRepository extends JpaRepository<ArticleFamilyEntity, Long> {
}
