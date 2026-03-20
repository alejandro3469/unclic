package mx.com.endtoend.infrastructure.catalogue.article.common.repository;

import mx.com.endtoend.infrastructure.catalogue.article.common.entities.ArticleDivisionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import javax.persistence.MappedSuperclass;

@NoRepositoryBean
@MappedSuperclass
public interface BaseArticleDivisionRepository extends JpaRepository<ArticleDivisionEntity, Long> {
}
