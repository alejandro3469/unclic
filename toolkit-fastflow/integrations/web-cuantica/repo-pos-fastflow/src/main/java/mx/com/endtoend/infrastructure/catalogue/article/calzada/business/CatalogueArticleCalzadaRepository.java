package mx.com.endtoend.infrastructure.catalogue.article.calzada.business;

import mx.com.endtoend.infrastructure.catalogue.article.common.business.BaseCatalogueArticleBusinessRepository;
import org.springframework.stereotype.Service;

import mx.com.endtoend.infrastructure.catalogue.article.calzada.repositories.ArticleBrandRepository;
import mx.com.endtoend.infrastructure.catalogue.article.calzada.repositories.ArticleCategoryRepository;
import mx.com.endtoend.infrastructure.catalogue.article.calzada.repositories.ArticleDivisionRepository;
import mx.com.endtoend.infrastructure.catalogue.article.calzada.repositories.ArticleFamilyRepository;
import mx.com.endtoend.infrastructure.catalogue.article.common.converters.ArticleBrandConverter;
import mx.com.endtoend.infrastructure.catalogue.article.common.converters.ArticleCategoryConverter;
import mx.com.endtoend.infrastructure.catalogue.article.common.converters.ArticleDivisionConverter;
import mx.com.endtoend.infrastructure.catalogue.article.common.converters.ArticleFamilyConverter;

@Service
public class CatalogueArticleCalzadaRepository extends BaseCatalogueArticleBusinessRepository {

	public CatalogueArticleCalzadaRepository(ArticleBrandRepository articleBrandRepository,
											 ArticleBrandConverter articleBrandConverter,
											 ArticleFamilyRepository articleFamilyRepository,
											 ArticleFamilyConverter articleFamilyConverter,
											 ArticleCategoryRepository articleCategoryRepository,
											 ArticleCategoryConverter articleCategoryConverter,
											 ArticleDivisionRepository articleDivisionRepository,
											 ArticleDivisionConverter articleDivisionConverter){
		super(CatalogueArticleCalzadaRepository.class,
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
