package mx.com.endtoend.infrastructure.articles.carredana.customArticles.repositories;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.com.endtoend.infrastructure.articles.common.entities.CustomArticleEntity;

@Repository
public interface CustomArticleFCarRepository extends JpaRepository<CustomArticleEntity, Long>{
	
	@Query("SELECT ca FROM CustomArticleEntity ca WHERE ca.name=:articleName")
	Optional<CustomArticleEntity> findByName(@Param("articleName") String name);
	
	@Query("SELECT ca FROM CustomArticleEntity ca WHERE ca.name=:articleName  AND ca.id!=:id")
	Optional<CustomArticleEntity> findByNameAndIdNot(@Param("articleName") String name, @Param("id") Long id);
	
	@Query("SELECT ca FROM CustomArticleEntity ca WHERE ca.articleNumber=:articleNumber")
	Optional<CustomArticleEntity> findByArticleNumber(@Param("articleNumber") BigDecimal articleNumber);
	
	@Query("SELECT ca FROM CustomArticleEntity ca WHERE ca.articleNumber=:articleNumber AND ca.id!=:id")
	Optional<CustomArticleEntity> findByArticleNumberAndIdNot(@Param("articleNumber") BigDecimal articleNumber, @Param("id") Long id);
	
	@Transactional
	@Modifying
	@Query("UPDATE CustomArticleEntity ca set ca.enable =:enabled WHERE ca.id=:id")
	int enableById(@Param("id") Long id, @Param("enabled") boolean enabled);
	
	@Query("SELECT ca FROM CustomArticleEntity ca WHERE ca.enable =:enabled ORDER BY ca.name ASC")
	List<CustomArticleEntity> findAllByEnable(@Param("enabled") boolean enabled);
	
	@Query("SELECT ca FROM CustomArticleEntity ca WHERE ca.enable =:enabled AND ca.saleType=:saleType ORDER BY ca.name ASC")
	List<CustomArticleEntity> findAllByEnableAndSaleType(@Param("enabled") boolean enabled, @Param("saleType") String saleType);
}
