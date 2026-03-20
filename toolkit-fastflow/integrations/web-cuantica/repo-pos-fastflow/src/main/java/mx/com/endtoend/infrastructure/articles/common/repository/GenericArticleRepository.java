package mx.com.endtoend.infrastructure.articles.common.repository;

import java.math.BigDecimal;
import java.util.List;

import mx.com.endtoend.domain.articles.dto.ArticleConvertionFactor;
import mx.com.endtoend.domain.articles.dto.ArticleDto;
import mx.com.endtoend.domain.articles.dto.GenericSerchParamsArticleDto;

public interface GenericArticleRepository {

	List<ArticleDto> findArticlesByParams(GenericSerchParamsArticleDto params, String branchCode, String idOperation,
			boolean debug);

	GenericSerchParamsArticleDto findArticleCodeByBarcodeAndWarehouseCode(
			GenericSerchParamsArticleDto genericSerchParamsArticleDto, String barcode, String idOperation);

	List<ArticleConvertionFactor> findConvertionFactorByArticleNumber(BigDecimal articleNumber, String idOperation);

}
