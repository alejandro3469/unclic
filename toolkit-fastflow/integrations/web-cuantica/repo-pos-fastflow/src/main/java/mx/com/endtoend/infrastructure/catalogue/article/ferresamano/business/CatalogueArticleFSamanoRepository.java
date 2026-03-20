package mx.com.endtoend.infrastructure.catalogue.article.ferresamano.business;

import mx.com.endtoend.infrastructure.catalogue.article.common.business.BaseCatalogueArticleBusinessRepository;
import org.springframework.stereotype.Service;

import mx.com.endtoend.infrastructure.catalogue.article.ferresamano.repositories.ArticleBrandFSamanoRepository;
import mx.com.endtoend.infrastructure.catalogue.article.ferresamano.repositories.ArticleCategoryFSamanoRepository;
import mx.com.endtoend.infrastructure.catalogue.article.ferresamano.repositories.ArticleDivisionFSamanoRepository;
import mx.com.endtoend.infrastructure.catalogue.article.ferresamano.repositories.ArticleFamilyFSamanoRepository;
import mx.com.endtoend.infrastructure.catalogue.article.common.converters.ArticleBrandConverter;
import mx.com.endtoend.infrastructure.catalogue.article.common.converters.ArticleCategoryConverter;
import mx.com.endtoend.infrastructure.catalogue.article.common.converters.ArticleDivisionConverter;
import mx.com.endtoend.infrastructure.catalogue.article.common.converters.ArticleFamilyConverter;

@Service
public class CatalogueArticleFSamanoRepository extends BaseCatalogueArticleBusinessRepository {

	public CatalogueArticleFSamanoRepository(ArticleBrandFSamanoRepository articleBrandRepository,
											ArticleBrandConverter articleBrandConverter,
											ArticleFamilyFSamanoRepository articleFamilyRepository,
											ArticleFamilyConverter articleFamilyConverter,
											ArticleCategoryFSamanoRepository articleCategoryRepository,
											ArticleCategoryConverter articleCategoryConverter,
											ArticleDivisionFSamanoRepository articleDivisionRepository,
											ArticleDivisionConverter articleDivisionConverter){
		super(CatalogueArticleFSamanoRepository.class,
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
