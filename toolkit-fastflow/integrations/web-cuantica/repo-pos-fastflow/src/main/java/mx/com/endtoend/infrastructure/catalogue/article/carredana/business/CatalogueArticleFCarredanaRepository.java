package mx.com.endtoend.infrastructure.catalogue.article.carredana.business;

import mx.com.endtoend.infrastructure.catalogue.article.common.business.BaseCatalogueArticleBusinessRepository;
import org.springframework.stereotype.Service;

import mx.com.endtoend.infrastructure.catalogue.article.carredana.repositories.ArticleBrandFCarRepository;
import mx.com.endtoend.infrastructure.catalogue.article.carredana.repositories.ArticleCategoryFCarRepository;
import mx.com.endtoend.infrastructure.catalogue.article.carredana.repositories.ArticleDivisionFCarRepository;
import mx.com.endtoend.infrastructure.catalogue.article.carredana.repositories.ArticleFamilyFCarRepository;
import mx.com.endtoend.infrastructure.catalogue.article.common.converters.ArticleBrandConverter;
import mx.com.endtoend.infrastructure.catalogue.article.common.converters.ArticleCategoryConverter;
import mx.com.endtoend.infrastructure.catalogue.article.common.converters.ArticleDivisionConverter;
import mx.com.endtoend.infrastructure.catalogue.article.common.converters.ArticleFamilyConverter;

@Service
public class CatalogueArticleFCarredanaRepository extends BaseCatalogueArticleBusinessRepository {

 	public CatalogueArticleFCarredanaRepository(ArticleBrandFCarRepository articleBrandRepository,
												  ArticleBrandConverter articleBrandConverter,
												  ArticleFamilyFCarRepository articleFamilyRepository,
												  ArticleFamilyConverter articleFamilyConverter,
												  ArticleCategoryFCarRepository articleCategoryRepository,
												  ArticleCategoryConverter articleCategoryConverter,
												  ArticleDivisionFCarRepository articleDivisionRepository,
												  ArticleDivisionConverter articleDivisionConverter){
		 super(CatalogueArticleFCarredanaRepository.class,
				  articleBrandRepository,
				  articleBrandConverter,
				  articleFamilyRepository,
				  articleFamilyConverter,
				  articleCategoryRepository,
				  articleCategoryConverter,
				  articleDivisionRepository,
				  articleDivisionConverter);
	}
}
