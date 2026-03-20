package mx.com.endtoend.infrastructure.articles.common.repository;

import java.math.BigDecimal;
import java.util.List;

import mx.com.endtoend.domain.articles.dto.CustomArticleDto;

public interface GenericCustomArticleRepository {

	CustomArticleDto createCustomArticle(CustomArticleDto customArticleDto, String idOperation);

	CustomArticleDto updateCustomArticle(CustomArticleDto customArticleDto, String idOperation);

	boolean existsByName(String name, String idOperation);

	boolean existsByNameAndIdNot(String name, Long id, String idOperation);

	boolean existsByArticleNumber(BigDecimal articleNumber, String idOperation);

	boolean existsByArticleNumberAndIdNot(BigDecimal articleNumber, Long id, String idOperation);

	CustomArticleDto enableById(Long id, boolean enable, String idOperation);

	CustomArticleDto findById(Long id, String idOperation);

	List<CustomArticleDto> findAllByEnable(boolean enable, String idOperation);

	List<CustomArticleDto> findAllActiveBySaleType(String saleType, String idOperation);
}
