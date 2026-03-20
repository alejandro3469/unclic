package mx.com.endtoend.infrastructure.catalogue.article.common.business;

import mx.com.endtoend.domain.catalogue.dto.article.ArticleBrandDto;
import mx.com.endtoend.domain.catalogue.dto.article.ArticleCategoryDto;
import mx.com.endtoend.domain.catalogue.dto.article.ArticleDivisionDto;
import mx.com.endtoend.domain.catalogue.dto.article.ArticleFamilyDto;
import mx.com.endtoend.infrastructure.catalogue.article.common.converters.ArticleBrandConverter;
import mx.com.endtoend.infrastructure.catalogue.article.common.converters.ArticleCategoryConverter;
import mx.com.endtoend.infrastructure.catalogue.article.common.converters.ArticleDivisionConverter;
import mx.com.endtoend.infrastructure.catalogue.article.common.converters.ArticleFamilyConverter;
import mx.com.endtoend.infrastructure.catalogue.article.common.entities.ArticleBrandEntity;
import mx.com.endtoend.infrastructure.catalogue.article.common.entities.ArticleCategoryEntity;
import mx.com.endtoend.infrastructure.catalogue.article.common.entities.ArticleDivisionEntity;
import mx.com.endtoend.infrastructure.catalogue.article.common.entities.ArticleFamilyEntity;
import mx.com.endtoend.infrastructure.catalogue.article.common.repository.*;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.GlobalError;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.MappedSuperclass;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@MappedSuperclass
public class BaseCatalogueArticleBusinessRepository implements GenericCatalogueArticleRepository {

    private final BaseArticleBrandRepository articleBrandRepository;
    private final ArticleBrandConverter articleBrandConverter;
    private final BaseArticleFamilyRepository articleFamilyRepository;
    private final ArticleFamilyConverter articleFamilyConverter;
    private final BaseArticleCategoryRepository articleCategoryRepository;
    private final ArticleCategoryConverter articleCategoryConverter;
    private final BaseArticleDivisionRepository articleDivisionRepository;
    private final ArticleDivisionConverter articleDivisionConverter;
    private final Logger LOG;

    public BaseCatalogueArticleBusinessRepository(Class<?> loggerName,
                        BaseArticleBrandRepository _articleBrandRepository,
                        ArticleBrandConverter _articleBrandConverter,
                        BaseArticleFamilyRepository _articleFamilyRepository,
                        ArticleFamilyConverter _articleFamilyConverter,
                        BaseArticleCategoryRepository _articleCategoryRepository,
                        ArticleCategoryConverter _articleCategoryConverter,
                        BaseArticleDivisionRepository _articleDivisionRepository,
                        ArticleDivisionConverter _articleDivisionConverter){
        LOG = LoggerFactory.getLogger(loggerName);
        articleBrandRepository = _articleBrandRepository;
        articleBrandConverter = _articleBrandConverter;
        articleFamilyRepository = _articleFamilyRepository;
        articleFamilyConverter = _articleFamilyConverter;
        articleCategoryRepository = _articleCategoryRepository;
        articleCategoryConverter = _articleCategoryConverter;
        articleDivisionRepository = _articleDivisionRepository;
        articleDivisionConverter = _articleDivisionConverter;
    }

    @Transactional
    @Override
    public ResponseModel getBrandByCompanyCode(String idOperation) {
        try {
            LOG.info(String.format("%s INIT getBrandByCompanyCode()", idOperation));
            List<ArticleBrandDto> articleBrandDtoList = new ArrayList<>();
            List<ArticleBrandEntity> articleBrandEntityList = articleBrandRepository.findAll();
            if (articleBrandEntityList.size() != 0) {
                LOG.info(String.format("%s CONVERT CATALOGUE LIST", idOperation));
                articleBrandDtoList = articleBrandConverter
                        .articleBrandEntityListToArticleBrandDtoList(articleBrandEntityList);
            }
            return new ResponseModel(articleBrandDtoList);
        } catch (Exception e) {
            LOG.error(String.format("%s ERROR IN getBrandByCompanyCode(). EXCEPTION: %s", idOperation, e.getMessage()));
            throw new GlobalError();
        }
    }

    @Transactional
    @Override
    public ResponseModel getCategoryByCompanyCode(String idOperation) {
        try {
            LOG.info(String.format("%s INIT getCategoryByCompanyCode()", idOperation));
            List<ArticleCategoryDto> articleCategoryDtoList = new ArrayList<>();
            List<ArticleCategoryEntity> articleCategoryEntityList = articleCategoryRepository.findAll();
            if (articleCategoryEntityList.size() != 0) {
                LOG.info(String.format("%s CONVERT CATALOGUE LIST", idOperation));
                articleCategoryDtoList = articleCategoryConverter
                        .articleCategoryEntityListToArticleCategoryDtoList(articleCategoryEntityList);
            }
            return new ResponseModel(articleCategoryDtoList);
        } catch (Exception e) {
            LOG.error(String.format("%s ERROR IN getCategoryByCompanyCode(). EXCEPTION: %s", idOperation,
                    e.getMessage()));
            throw new GlobalError();
        }
    }

    @Transactional
    @Override
    public ResponseModel getDivisionByCompanyCode(String idOperation) {
        try {
            LOG.info(String.format("%s INIT getDivisionByCompanyCode()", idOperation));
            List<ArticleDivisionDto> articleDivisionDtoList = new ArrayList<>();
            List<ArticleDivisionEntity> articleDivisionEntityList = articleDivisionRepository.findAll();
            if (articleDivisionEntityList.size() != 0) {
                LOG.info(String.format("%s CONVERT CATALOGUE LIST", idOperation));
                articleDivisionDtoList = articleDivisionConverter
                        .articleDivisionEntityListToArticleDivisionDtoList(articleDivisionEntityList);
            }
            return new ResponseModel(articleDivisionDtoList);
        } catch (Exception e) {
            LOG.error(String.format("%s ERROR IN getDivisionByCompanyCode(). EXCEPTION: %s", idOperation,
                    e.getMessage()));
            throw new GlobalError();
        }
    }

    @Transactional
    @Override
    public ResponseModel getFamilyByCompanyCode(String idOperation) {
        try {
            LOG.info(String.format("%s INIT getFamilyByCompanyCode()", idOperation));
            List<ArticleFamilyDto> articleFamilyDtoList = new ArrayList<>();
            List<ArticleFamilyEntity> articleFamilyEntityList = articleFamilyRepository.findAll();
            if (articleFamilyEntityList.size() != 0) {
                LOG.info(String.format("%s CONVERT CATALOGUE LIST", idOperation));
                articleFamilyDtoList = articleFamilyConverter
                        .articleFamilyEntityListToArticleFamilyDtoList(articleFamilyEntityList);
            }
            return new ResponseModel(articleFamilyDtoList);
        } catch (Exception e) {
            LOG.error(
                    String.format("%s ERROR IN getFamilyByCompanyCode(). EXCEPTION: %s", idOperation, e.getMessage()));
            throw new GlobalError();
        }
    }

    @Transactional
    @Override
    public ResponseModel updateCatalogueBrandByCompanyCodeAndList(List<ArticleBrandDto> articleBrandDtoList,
                                                                  String idOperation) {
        try {
            LOG.info(String.format("%s INIT updateCatalogueBrandByCompanyCodeAndList()", idOperation));
            int totalRecord = 0;
            List<ArticleBrandEntity> articleBrandEntityList = articleBrandConverter
                    .articleBrandDtoListToArticleBrandEntityList(articleBrandDtoList);
            for (ArticleBrandEntity articleBrandEntity : articleBrandEntityList) {
                try {
                    articleBrandRepository.save(articleBrandEntity);
                    totalRecord++;
                } catch (Exception e) {
                    LOG.error(String.format("%s ERROR IN UPDATE DATA: %s", idOperation, articleBrandEntity.toString()));
                }
            }
            return new ResponseModel(totalRecord);
        } catch (Exception e) {
            LOG.error(String.format("%s ERROR IN updateCatalogueBrandByCompanyCodeAndList(). EXCEPTION: %s",
                    idOperation, e.getMessage()));
            throw new GlobalError();
        }
    }

    @Transactional
    @Override
    public ResponseModel updateCatalogueCategoryByCompanyCodeAndList(List<ArticleCategoryDto> articleCategoryDtoList,
                                                                     String idOperation) {
        try {
            LOG.info(String.format("%s INIT updateCatalogueCategoryByCompanyCodeAndList()", idOperation));
            int totalRecord = 0;
            List<ArticleCategoryEntity> articleCategoryEntityList = articleCategoryConverter
                    .articleCategoryDtoListToArticleCategoryEntityList(articleCategoryDtoList);
            for (ArticleCategoryEntity articleCategoryEntity : articleCategoryEntityList) {
                try {
                    articleCategoryRepository.save(articleCategoryEntity);
                    totalRecord++;
                } catch (Exception e) {
                    LOG.error(String.format("%s ERROR IN UPDATE DATA: %s", idOperation,
                            articleCategoryEntity.toString()));
                }
            }
            return new ResponseModel(totalRecord);
        } catch (Exception e) {
            LOG.error(String.format("%s ERROR IN updateCatalogueCategoryByCompanyCodeAndList(). EXCEPTION: %s",
                    idOperation, e.getMessage()));
            throw new GlobalError();
        }
    }

    @Transactional
    @Override
    public ResponseModel updateCatalogueDivisionByCompanyCodeAndList(List<ArticleDivisionDto> articleDivisionDtoList,
                                                                     String idOperation) {
        try {
            LOG.info(String.format("%s INIT updateCatalogueDivisionByCompanyCodeAndList()", idOperation));
            int totalRecord = 0;
            List<ArticleDivisionEntity> articleDivisionEntityList = articleDivisionConverter
                    .articleDivisionDtoListToArticleDivisionEntityList(articleDivisionDtoList);
            for (ArticleDivisionEntity articleDivisionEntity : articleDivisionEntityList) {
                try {
                    articleDivisionRepository.save(articleDivisionEntity);
                    totalRecord++;
                } catch (Exception e) {
                    LOG.error(String.format("%s ERROR IN UPDATE DATA: %s", idOperation,
                            articleDivisionEntity.toString()));
                }
            }
            return new ResponseModel(totalRecord);
        } catch (Exception e) {
            LOG.error(String.format("%s ERROR IN updateCatalogueDivisionByCompanyCodeAndList(). EXCEPTION: %s",
                    idOperation, e.getMessage()));
            throw new GlobalError();
        }
    }

    @Transactional
    @Override
    public ResponseModel updateCatalogueFamilyByCompanyCodeAndList(List<ArticleFamilyDto> articleFamilyDtoList,
                                                                   String idOperation) {
        try {
            LOG.info(String.format("%s INIT updateCatalogueFamilyByCompanyCodeAndList()", idOperation));
            int totalRecord = 0;
            List<ArticleFamilyEntity> articleFamilyEntityList = articleFamilyConverter
                    .articleFamilyDtoListToArticleFamilyEntityList(articleFamilyDtoList);
            for (ArticleFamilyEntity articleFamilyEntity : articleFamilyEntityList) {
                try {
                    articleFamilyRepository.save(articleFamilyEntity);
                    totalRecord++;
                } catch (Exception e) {
                    LOG.error(
                            String.format("%s ERROR IN UPDATE DATA: %s", idOperation, articleFamilyEntity.toString()));
                }
            }
            return new ResponseModel(totalRecord);
        } catch (Exception e) {
            LOG.error(String.format("%s ERROR IN updateCatalogueFamilyByCompanyCodeAndList(). EXCEPTION: %s",
                    idOperation, e.getMessage()));
            throw new GlobalError();
        }
    }

}

