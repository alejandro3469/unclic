package mx.com.endtoend.infrastructure.catalogue.article.demo.business;

import mx.com.endtoend.infrastructure.catalogue.article.common.business.BaseCatalogueArticleBusinessRepository;
import org.springframework.stereotype.Service;

import mx.com.endtoend.infrastructure.catalogue.article.demo.repositories.ArticleBrandDemoRepository;
import mx.com.endtoend.infrastructure.catalogue.article.demo.repositories.ArticleCategoryDemoRepository;
import mx.com.endtoend.infrastructure.catalogue.article.demo.repositories.ArticleDivisionDemoRepository;
import mx.com.endtoend.infrastructure.catalogue.article.demo.repositories.ArticleFamilyDemoRepository;
import mx.com.endtoend.infrastructure.catalogue.article.common.converters.ArticleBrandConverter;
import mx.com.endtoend.infrastructure.catalogue.article.common.converters.ArticleCategoryConverter;
import mx.com.endtoend.infrastructure.catalogue.article.common.converters.ArticleDivisionConverter;
import mx.com.endtoend.infrastructure.catalogue.article.common.converters.ArticleFamilyConverter;

@Service
public class CatalogueArticleDemoRepository extends BaseCatalogueArticleBusinessRepository {

 	public CatalogueArticleDemoRepository(ArticleBrandDemoRepository articleBrandRepository,
											  ArticleBrandConverter articleBrandConverter,
											  ArticleFamilyDemoRepository articleFamilyRepository,
											  ArticleFamilyConverter articleFamilyConverter,
											  ArticleCategoryDemoRepository articleCategoryRepository,
											  ArticleCategoryConverter articleCategoryConverter,
											  ArticleDivisionDemoRepository articleDivisionRepository,
											  ArticleDivisionConverter articleDivisionConverter){
		 super(CatalogueArticleDemoRepository.class,
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
