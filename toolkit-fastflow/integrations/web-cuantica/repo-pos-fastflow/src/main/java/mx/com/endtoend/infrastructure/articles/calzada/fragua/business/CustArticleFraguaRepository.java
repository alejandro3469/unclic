package mx.com.endtoend.infrastructure.articles.calzada.fragua.business;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.com.endtoend.domain.articles.dto.CustomArticleDto;
import mx.com.endtoend.infrastructure.articles.common.repository.GenericCustomArticleRepository;
import mx.com.endtoend.infrastructure.articles.calzada.fragua.repositories.CustomArticleFraguaRepository;
import mx.com.endtoend.infrastructure.articles.common.converters.CustomArticleConverter;
import mx.com.endtoend.infrastructure.articles.common.entities.CustomArticleEntity;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.GlobalError;

/**
 * 
 * @author ddcasas
 *
 */

@Service
public class CustArticleFraguaRepository implements GenericCustomArticleRepository {

	@Autowired
	private CustomArticleConverter customArticleConverter;

	@Autowired
	private CustomArticleFraguaRepository customArticleEntityRepository;

	private final Logger LOG = LoggerFactory.getLogger(CustArticleFraguaRepository.class);

	@Override
	public CustomArticleDto createCustomArticle(CustomArticleDto customArticleDto, String idOperation) {

		LOG.info(String.format("%s INIT createCustomArticle()", idOperation));
		LOG.info(String.format("%s PARAMS: customArticleDto: %s ", idOperation, customArticleDto.toString()));

		try {

			LOG.info(String.format("%s START CONVERTION FROM DTO TO ENTITY", idOperation));
			CustomArticleEntity customArticleEntity = customArticleConverter
					.customArticleDtoToCustomArticleEntity(customArticleDto);

			customArticleEntity = customArticleEntityRepository.save(customArticleEntity);

			if (customArticleEntity != null) {

				LOG.info(String.format("%s SAVE DATA OK", idOperation));
				return customArticleConverter.customArticleEntityToCustomArticleDto(customArticleEntity);
			} else {

				LOG.error(String.format("%s ERROR IN SAVE DATA ", idOperation));
				return null;
			}

		} catch (Exception e) {
			LOG.error(String.format("%s ERROR IN createCustomArticle(). EXCEPTION: %s", idOperation, e.getMessage()));
			throw new GlobalError();
		}

	}

	@Override
	public CustomArticleDto updateCustomArticle(CustomArticleDto customArticleDto, String idOperation) {

		LOG.info(String.format("%s INIT updateCustomArticle()", idOperation));
		LOG.info(String.format("%s PARAMS: customArticleDto: %s ", idOperation, customArticleDto.toString()));

		try {
			LOG.info(String.format("%s START CONVERTION FROM DTO TO ENTITY", idOperation));
			CustomArticleEntity customArticleEntity = customArticleConverter
					.customArticleDtoToCustomArticleEntity(customArticleDto);
			customArticleEntity = customArticleEntityRepository.save(customArticleEntity);

			if (customArticleEntity != null) {

				LOG.info(String.format("%s SAVE DATA OK", idOperation));
				return customArticleConverter.customArticleEntityToCustomArticleDto(customArticleEntity);
			} else {

				LOG.error(String.format("%s ERROR IN SAVE DATA ", idOperation));
				return null;
			}

		} catch (Exception e) {
			LOG.error(String.format("%s ERROR IN updateCustomArticle(). EXCEPTION: %s", idOperation, e.getMessage()));
			throw new GlobalError();
		}

	}

	@Override
	public boolean existsByName(String name, String idOperation) {

		LOG.info(String.format("%s INIT existsByName()", idOperation));
		LOG.info(String.format("%s PARAMS: name: %s ", idOperation, name));

		try {

			Optional<CustomArticleEntity> customArticleEntity = customArticleEntityRepository.findByName(name);

			if (customArticleEntity.isPresent()) {
				LOG.info(String.format("%s CUSTOM-ARTICLE EXISTS", idOperation));
				return true;
			} else {
				LOG.info(String.format("%s CUSTOM-ARTICLE NOT EXISTS", idOperation));
				return false;
			}

		} catch (Exception e) {
			LOG.error(String.format("%s ERROR IN existsByName(). EXCEPTION: %s", idOperation, e.getMessage()));
			throw new GlobalError();
		}
	}

	@Override
	public boolean existsByNameAndIdNot(String name, Long id, String idOperation) {

		LOG.info(String.format("%s INIT existsByNameAndIdNot()", idOperation));
		LOG.info(String.format("%s PARAMS: id: %s name: %s ", idOperation, id.toString(), name));
		try {

			Optional<CustomArticleEntity> customArticleEntity = customArticleEntityRepository.findByNameAndIdNot(name,
					id);

			if (customArticleEntity.isPresent()) {
				LOG.info(String.format("%s CUSTOM-ARTICLE EXISTS", idOperation));
				return true;
			} else {
				LOG.info(String.format("%s CUSTOM-ARTICLE NOT EXISTS", idOperation));
				return false;
			}

		} catch (Exception e) {
			LOG.error(String.format("%s ERROR IN existsByNameAndIdNot(). EXCEPTION: %s", idOperation, e.getMessage()));
			throw new GlobalError();
		}

	}

	@Override
	public boolean existsByArticleNumber(BigDecimal articleNumber, String idOperation) {
		LOG.info(String.format("%s INIT existsByArticleNumber()", idOperation));
		LOG.info(String.format("%s PARAMS: articleNumber: %s ", idOperation, articleNumber.toString()));

		try {
			Optional<CustomArticleEntity> customArticleEntity = customArticleEntityRepository
					.findByArticleNumber(articleNumber);

			if (customArticleEntity.isPresent()) {
				LOG.info(String.format("%s CUSTOM-ARTICLE EXISTS", idOperation));
				return true;
			} else {
				LOG.info(String.format("%s CUSTOM-ARTICLE NOT EXISTS", idOperation));
				return false;
			}
		} catch (Exception e) {
			LOG.error(String.format("%s ERROR IN existsByArticleNumber(). EXCEPTION: %s", idOperation, e.getMessage()));
			throw new GlobalError();
		}
	}

	@Override
	public boolean existsByArticleNumberAndIdNot(BigDecimal articleNumber, Long id, String idOperation) {
		LOG.info(String.format("%s INIT existsByArticleNumberAndIdNot()", idOperation));
		LOG.info(String.format("%s PARAMS: id: %s articleNumber: %s ", idOperation, id.toString(),
				articleNumber.toString()));

		try {
			Optional<CustomArticleEntity> customArticleEntity = customArticleEntityRepository
					.findByArticleNumberAndIdNot(articleNumber, id);

			if (customArticleEntity.isPresent()) {
				LOG.info(String.format("%s CUSTOM-ARTICLE EXISTS", idOperation));
				return true;
			} else {
				LOG.info(String.format("%s CUSTOM-ARTICLE NOT EXISTS", idOperation));
				return false;
			}
		} catch (Exception e) {
			LOG.error(String.format("%s ERROR IN existsByArticleNumber(). EXCEPTION: %s", idOperation, e.getMessage()));
			throw new GlobalError();
		}
	}

	@Override
	public CustomArticleDto enableById(Long id, boolean enable, String idOperation) {
		LOG.info(String.format("%s INIT enableById()", idOperation));
		LOG.info(String.format("%s PARAMS: id: %s enable: %s ", idOperation, id.toString(), String.valueOf(enable)));

		try {
			Optional<CustomArticleEntity> customArticle = customArticleEntityRepository.findById(id);
			if (customArticle.isPresent()) {
				customArticleEntityRepository.enableById(id, enable);
				LOG.info(String.format("%s CUSTOM-ARTICLE ENABLE UPDATE OK", idOperation));
				return customArticleConverter.customArticleEntityToCustomArticleDto(customArticle.get());
			} else {
				LOG.error(String.format("%s ERROR UPDATED ENBALE STATUS TO CUSTOM-ARTICLE", idOperation));
				return null;
			}
		} catch (Exception e) {
			LOG.error(String.format("%s ERROR IN enableById(). EXCEPTION: %s", idOperation, e.getMessage()));
			throw new GlobalError();
		}

	}

	@Override
	public CustomArticleDto findById(Long id, String idOperation) {

		LOG.info(String.format("%s INIT findById()", idOperation));
		LOG.info(String.format("%s PARAMS: id: %s ", idOperation, id.toString()));

		try {
			Optional<CustomArticleEntity> customArticle = customArticleEntityRepository.findById(id);
			if (customArticle.isPresent()) {
				LOG.info(String.format("%s CUSTOM-ARTICLE FOUND", idOperation));
				return customArticleConverter.customArticleEntityToCustomArticleDto(customArticle.get());
			} else {
				LOG.error(String.format("%s ERROR IN SERCH CUSTOM-ARTICLE", idOperation));
				return null;
			}

		} catch (Exception e) {
			LOG.error(String.format("%s ERROR IN enableById(). EXCEPTION: %s", idOperation, e.getMessage()));
			throw new GlobalError();
		}

	}

	@Override
	public List<CustomArticleDto> findAllByEnable(boolean enable, String idOperation) {

		LOG.info(String.format("%s INIT findAllByEnable()", idOperation));
		LOG.info(String.format("%s PARAMS: enable: %s ", idOperation, String.valueOf(enable)));
		try {
			List<CustomArticleEntity> customArticleEntityList = customArticleEntityRepository.findAllByEnable(enable);
			if (customArticleEntityList != null) {
				LOG.info(String.format("%s RETURN CUSTOM-ARTICLE LIST", idOperation));
				return customArticleConverter.customArticleEntityListToCustomArticleDtoList(customArticleEntityList);
			} else {
				LOG.error(String.format("%s ERROR IN SERCH CUSTOM-ARTICLE LIST", idOperation));
				return null;
			}

		} catch (Exception e) {
			LOG.error(String.format("%s ERROR IN enableById(). EXCEPTION: %s", idOperation, e.getMessage()));
			throw new GlobalError();
		}
	}

	@Override
	public List<CustomArticleDto> findAllActiveBySaleType(String saleType, String idOperation) {
		LOG.info(String.format("%s INIT findAllActiveBySaleType()", idOperation));
		try {

			List<CustomArticleEntity> customArticleEntityList = customArticleEntityRepository
					.findAllByEnableAndSaleType(true, saleType);
			List<CustomArticleDto> customArticleDtoList = null;
			if (customArticleEntityList != null) {
				customArticleDtoList = customArticleConverter
						.customArticleEntityListToCustomArticleDtoList(customArticleEntityList);
			}
			return customArticleDtoList;

		} catch (Exception e) {
			LOG.error(
					String.format("%s ERROR IN findAllActiveBySaleType(). EXCEPTION: %s", idOperation, e.getMessage()));
			throw new GlobalError();
		}
	}

}
