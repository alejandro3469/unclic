package mx.com.endtoend.infrastructure.articles.carredana.oracle.converters;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import mx.com.endtoend.domain.articles.dto.ArticleConvertionFactor;
import mx.com.endtoend.infrastructure.articles.carredana.oracle.entities.F41002;

@Component
public class ArticleConvertionFactorFcarConverter {

	public ArticleConvertionFactor F41002ToArticleConvertionFactor(F41002 f41002) {

		ArticleConvertionFactor articleConvertionFactor = new ArticleConvertionFactor();
		BigDecimal divisor = new BigDecimal("10000000");

		articleConvertionFactor.setPrimaryUnitMeasure(f41002.getId().getUmum());
		articleConvertionFactor.setConvertionFactor(f41002.getUmconv().divide(divisor));
		articleConvertionFactor.setEquivalentUnitMeasure(f41002.getId().getUmrum());

		return articleConvertionFactor;
	}

	public List<ArticleConvertionFactor> f41002ListToArticleConvertionFactorList(List<F41002> f41002List) {

		List<ArticleConvertionFactor> articleConvertionFactorList = new ArrayList<ArticleConvertionFactor>();

		for (F41002 f41002 : f41002List) {
			articleConvertionFactorList.add(F41002ToArticleConvertionFactor(f41002));
		}

		return articleConvertionFactorList;
	}
}
