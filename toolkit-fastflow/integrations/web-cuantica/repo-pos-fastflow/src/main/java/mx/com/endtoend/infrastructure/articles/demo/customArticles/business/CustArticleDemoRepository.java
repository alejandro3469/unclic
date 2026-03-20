package mx.com.endtoend.infrastructure.articles.demo.customArticles.business;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.com.endtoend.domain.articles.dto.CustomArticleDto;
import mx.com.endtoend.infrastructure.articles.common.repository.GenericCustomArticleRepository;
import mx.com.endtoend.infrastructure.articles.demo.customArticles.repositories.CustomArticleDemoRepository;
import mx.com.endtoend.infrastructure.articles.common.converters.CustomArticleConverter;
import mx.com.endtoend.infrastructure.articles.common.entities.CustomArticleEntity;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.GlobalError;

/**
 * Implementación del repositorio de artículos personalizados para DEMO Company
 * Basado en la estructura de Carredana
 * 
 * @author Sistema
 *
 */

@Service
public class CustArticleDemoRepository implements GenericCustomArticleRepository {

	@Autowired
	private CustomArticleConverter customArticleConverter;

	@Autowired
	private CustomArticleDemoRepository customArticleDemoRepository;

	private final Logger LOG = LoggerFactory.getLogger(CustArticleDemoRepository.class);

	@Override
	public CustomArticleDto createCustomArticle(CustomArticleDto customArticleDto, String idOperation) {
		try {
			LOG.info("{} INIT createCustomArticle() for DEMO", idOperation);
			LOG.info("{} PARAMS[ customArticleDto: {} ]", idOperation, customArticleDto);
			
			CustomArticleEntity customArticleEntity = customArticleConverter
					.customArticleDtoToCustomArticleEntity(customArticleDto);
			customArticleEntity = customArticleDemoRepository.save(customArticleEntity);
			customArticleDto = customArticleConverter.customArticleEntityToCustomArticleDto(customArticleEntity);
			
			LOG.info("{} DEMO - Custom article created successfully", idOperation);
			LOG.info("{} RETURN createCustomArticle", idOperation);
			return customArticleDto;
			
		} catch (Exception e) {
			LOG.error("{} ERROR IN createCustomArticle() for DEMO. EXCEPTION: {}", idOperation, e.getMessage());
			throw new GlobalError();
		}
	}

	@Override
	public CustomArticleDto updateCustomArticle(CustomArticleDto customArticleDto, String idOperation) {
		try {
			LOG.info("{} INIT updateCustomArticle() for DEMO", idOperation);
			LOG.info("{} PARAMS[ customArticleDto: {} ]", idOperation, customArticleDto);
			
			CustomArticleEntity customArticleEntity = customArticleConverter
					.customArticleDtoToCustomArticleEntity(customArticleDto);
			customArticleEntity = customArticleDemoRepository.save(customArticleEntity);
			customArticleDto = customArticleConverter.customArticleEntityToCustomArticleDto(customArticleEntity);
			
			LOG.info("{} DEMO - Custom article updated successfully", idOperation);
			LOG.info("{} RETURN updateCustomArticle", idOperation);
			return customArticleDto;
			
		} catch (Exception e) {
			LOG.error("{} ERROR IN updateCustomArticle() for DEMO. EXCEPTION: {}", idOperation, e.getMessage());
			throw new GlobalError();
		}
	}

	@Override
	public boolean existsByName(String name, String idOperation) {
		try {
			LOG.info("{} INIT existsByName() for DEMO", idOperation);
			LOG.info("{} PARAMS[ name: {} ]", idOperation, name);
			
			Optional<CustomArticleEntity> customArticleEntity = customArticleDemoRepository.findByName(name);
			boolean exists = customArticleEntity.isPresent();
			
			LOG.info("{} DEMO - Custom article exists by name: {}", idOperation, exists);
			LOG.info("{} RETURN existsByName", idOperation);
			return exists;
			
		} catch (Exception e) {
			LOG.error("{} ERROR IN existsByName() for DEMO. EXCEPTION: {}", idOperation, e.getMessage());
			throw new GlobalError();
		}
	}

	@Override
	public boolean existsByNameAndIdNot(String name, Long id, String idOperation) {
		try {
			LOG.info("{} INIT existsByNameAndIdNot() for DEMO", idOperation);
			LOG.info("{} PARAMS[ name: {}, id: {} ]", idOperation, name, id);
			
			Optional<CustomArticleEntity> customArticleEntity = customArticleDemoRepository.findByNameAndIdNot(name, id);
			boolean exists = customArticleEntity.isPresent();
			
			LOG.info("{} DEMO - Custom article exists by name and id not: {}", idOperation, exists);
			LOG.info("{} RETURN existsByNameAndIdNot", idOperation);
			return exists;
			
		} catch (Exception e) {
			LOG.error("{} ERROR IN existsByNameAndIdNot() for DEMO. EXCEPTION: {}", idOperation, e.getMessage());
			throw new GlobalError();
		}
	}

	@Override
	public boolean existsByArticleNumber(BigDecimal articleNumber, String idOperation) {
		try {
			LOG.info("{} INIT existsByArticleNumber() for DEMO", idOperation);
			LOG.info("{} PARAMS[ articleNumber: {} ]", idOperation, articleNumber);
			
			Optional<CustomArticleEntity> customArticleEntity = customArticleDemoRepository.findByArticleNumber(articleNumber);
			boolean exists = customArticleEntity.isPresent();
			
			LOG.info("{} DEMO - Custom article exists by article number: {}", idOperation, exists);
			LOG.info("{} RETURN existsByArticleNumber", idOperation);
			return exists;
			
		} catch (Exception e) {
			LOG.error("{} ERROR IN existsByArticleNumber() for DEMO. EXCEPTION: {}", idOperation, e.getMessage());
			throw new GlobalError();
		}
	}

	@Override
	public boolean existsByArticleNumberAndIdNot(BigDecimal articleNumber, Long id, String idOperation) {
		try {
			LOG.info("{} INIT existsByArticleNumberAndIdNot() for DEMO", idOperation);
			LOG.info("{} PARAMS[ articleNumber: {}, id: {} ]", idOperation, articleNumber, id);
			
			Optional<CustomArticleEntity> customArticleEntity = customArticleDemoRepository
					.findByArticleNumberAndIdNot(articleNumber, id);
			boolean exists = customArticleEntity.isPresent();
			
			LOG.info("{} DEMO - Custom article exists by article number and id not: {}", idOperation, exists);
			LOG.info("{} RETURN existsByArticleNumberAndIdNot", idOperation);
			return exists;
			
		} catch (Exception e) {
			LOG.error("{} ERROR IN existsByArticleNumberAndIdNot() for DEMO. EXCEPTION: {}", idOperation, e.getMessage());
			throw new GlobalError();
		}
	}

	@Override
	public CustomArticleDto enableById(Long id, boolean enable, String idOperation) {
		try {
			LOG.info("{} INIT enableById() for DEMO", idOperation);
			LOG.info("{} PARAMS[ id: {}, enable: {} ]", idOperation, id, enable);
			
			int updatedRows = customArticleDemoRepository.enableById(id, enable);
			
			if (updatedRows > 0) {
				// Retrieve the updated entity
				Optional<CustomArticleEntity> customArticleEntityOpt = customArticleDemoRepository.findById(id);
				if (customArticleEntityOpt.isPresent()) {
					CustomArticleDto customArticleDto = customArticleConverter
							.customArticleEntityToCustomArticleDto(customArticleEntityOpt.get());
					LOG.info("{} DEMO - Custom article enabled status updated successfully", idOperation);
					LOG.info("{} RETURN enableById", idOperation);
					return customArticleDto;
				}
			}
			
			LOG.warn("{} DEMO - Custom article not found or not updated", idOperation);
			LOG.info("{} RETURN enableById", idOperation);
			return null;
			
		} catch (Exception e) {
			LOG.error("{} ERROR IN enableById() for DEMO. EXCEPTION: {}", idOperation, e.getMessage());
			throw new GlobalError();
		}
	}

	@Override
	public CustomArticleDto findById(Long id, String idOperation) {
		try {
			LOG.info("{} INIT findById() for DEMO", idOperation);
			LOG.info("{} PARAMS[ id: {} ]", idOperation, id);
			
			Optional<CustomArticleEntity> customArticleEntityOpt = customArticleDemoRepository.findById(id);
			
			if (customArticleEntityOpt.isPresent()) {
				CustomArticleDto customArticleDto = customArticleConverter
						.customArticleEntityToCustomArticleDto(customArticleEntityOpt.get());
				LOG.info("{} DEMO - Custom article found successfully", idOperation);
				LOG.info("{} RETURN findById", idOperation);
				return customArticleDto;
			}
			
			LOG.warn("{} DEMO - Custom article not found with id: {}", idOperation, id);
			LOG.info("{} RETURN findById", idOperation);
			return null;
			
		} catch (Exception e) {
			LOG.error("{} ERROR IN findById() for DEMO. EXCEPTION: {}", idOperation, e.getMessage());
			throw new GlobalError();
		}
	}

	@Override
	public List<CustomArticleDto> findAllByEnable(boolean enable, String idOperation) {
		try {
			LOG.info("{} INIT findAllByEnable() for DEMO", idOperation);
			LOG.info("{} PARAMS[ enable: {} ]", idOperation, enable);
			
			List<CustomArticleEntity> customArticleEntityList = customArticleDemoRepository.findAllByEnable(enable);
			List<CustomArticleDto> customArticleDtoList = customArticleConverter
					.customArticleEntityListToCustomArticleDtoList(customArticleEntityList);
			
			LOG.info("{} DEMO - Retrieved {} custom articles with enable={}", idOperation, customArticleDtoList.size(), enable);
			LOG.info("{} RETURN findAllByEnable", idOperation);
			return customArticleDtoList;
			
		} catch (Exception e) {
			LOG.error("{} ERROR IN findAllByEnable() for DEMO. EXCEPTION: {}", idOperation, e.getMessage());
			throw new GlobalError();
		}
	}

	@Override
	public List<CustomArticleDto> findAllActiveBySaleType(String saleType, String idOperation) {
		try {
			LOG.info("{} INIT findAllActiveBySaleType() for DEMO", idOperation);
			LOG.info("{} PARAMS[ saleType: {} ]", idOperation, saleType);
			
			List<CustomArticleEntity> customArticleEntityList = customArticleDemoRepository
					.findAllByEnableAndSaleType(true, saleType);
			List<CustomArticleDto> customArticleDtoList = customArticleConverter
					.customArticleEntityListToCustomArticleDtoList(customArticleEntityList);
			
			LOG.info("{} DEMO - Retrieved {} active custom articles with saleType={}", idOperation, customArticleDtoList.size(), saleType);
			LOG.info("{} RETURN findAllActiveBySaleType", idOperation);
			return customArticleDtoList;
			
		} catch (Exception e) {
			LOG.error("{} ERROR IN findAllActiveBySaleType() for DEMO. EXCEPTION: {}", idOperation, e.getMessage());
			throw new GlobalError();
		}
	}

}
