package mx.com.endtoend.infrastructure.catalogue.article.calzada.fragua.business;

import mx.com.endtoend.infrastructure.catalogue.article.common.business.BaseCatalogueArticleBusinessRepository;
import org.springframework.stereotype.Service;

import mx.com.endtoend.infrastructure.catalogue.article.calzada.fragua.repositories.ArticleBrandFraguaRepository;
import mx.com.endtoend.infrastructure.catalogue.article.calzada.fragua.repositories.ArticleCategoryFraguaRepository;
import mx.com.endtoend.infrastructure.catalogue.article.calzada.fragua.repositories.ArticleDivisionFraguaRepository;
import mx.com.endtoend.infrastructure.catalogue.article.calzada.fragua.repositories.ArticleFamilyFraguaRepository;
import mx.com.endtoend.infrastructure.catalogue.article.common.converters.ArticleBrandConverter;
import mx.com.endtoend.infrastructure.catalogue.article.common.converters.ArticleCategoryConverter;
import mx.com.endtoend.infrastructure.catalogue.article.common.converters.ArticleDivisionConverter;
import mx.com.endtoend.infrastructure.catalogue.article.common.converters.ArticleFamilyConverter;

@Service
public class CatalogueArticleFraguaRepository extends BaseCatalogueArticleBusinessRepository {

	 public CatalogueArticleFraguaRepository(ArticleBrandFraguaRepository articleBrandRepository,
											 ArticleBrandConverter articleBrandConverter,
											 ArticleFamilyFraguaRepository articleFamilyRepository,
											 ArticleFamilyConverter articleFamilyConverter,
											 ArticleCategoryFraguaRepository articleCategoryRepository,
											 ArticleCategoryConverter articleCategoryConverter,
											 ArticleDivisionFraguaRepository articleDivisionRepository,
											 ArticleDivisionConverter articleDivisionConverter){
		 super(CatalogueArticleFraguaRepository.class,
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
