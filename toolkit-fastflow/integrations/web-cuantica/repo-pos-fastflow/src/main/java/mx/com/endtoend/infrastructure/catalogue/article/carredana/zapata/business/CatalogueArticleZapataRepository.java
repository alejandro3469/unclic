package mx.com.endtoend.infrastructure.catalogue.article.carredana.zapata.business;

import mx.com.endtoend.infrastructure.catalogue.article.common.business.BaseCatalogueArticleBusinessRepository;
import org.springframework.stereotype.Service;

import mx.com.endtoend.infrastructure.catalogue.article.carredana.zapata.repositories.ArticleBrandCZapataRepository;
import mx.com.endtoend.infrastructure.catalogue.article.carredana.zapata.repositories.ArticleCategoryCZapataRepository;
import mx.com.endtoend.infrastructure.catalogue.article.carredana.zapata.repositories.ArticleDivisionCZapataRepository;
import mx.com.endtoend.infrastructure.catalogue.article.carredana.zapata.repositories.ArticleFamilyCZapataRepository;
import mx.com.endtoend.infrastructure.catalogue.article.common.converters.ArticleBrandConverter;
import mx.com.endtoend.infrastructure.catalogue.article.common.converters.ArticleCategoryConverter;
import mx.com.endtoend.infrastructure.catalogue.article.common.converters.ArticleDivisionConverter;
import mx.com.endtoend.infrastructure.catalogue.article.common.converters.ArticleFamilyConverter;

@Service
public class CatalogueArticleZapataRepository extends BaseCatalogueArticleBusinessRepository {

	public CatalogueArticleZapataRepository(ArticleBrandCZapataRepository articleBrandRepository,
											ArticleBrandConverter articleBrandConverter,
											ArticleFamilyCZapataRepository articleFamilyRepository,
											ArticleFamilyConverter articleFamilyConverter,
											ArticleCategoryCZapataRepository articleCategoryRepository,
											ArticleCategoryConverter articleCategoryConverter,
											ArticleDivisionCZapataRepository articleDivisionRepository,
											ArticleDivisionConverter articleDivisionConverter){
		super(CatalogueArticleZapataRepository.class,
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
