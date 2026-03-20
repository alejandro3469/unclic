package mx.com.endtoend.infrastructure.articles.common.factory;

import mx.com.endtoend.infrastructure.articles.common.repository.GenericArticleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mx.com.endtoend.infrastructure.articles.calzada.business.ArticleCalzadaRepository;
import mx.com.endtoend.infrastructure.articles.calzada.fragua.business.ArticleFraguaRepository;
import mx.com.endtoend.infrastructure.articles.carredana.customArticles.business.ArticleFCarredanaRepository;
import mx.com.endtoend.infrastructure.articles.ferresamano.customArticles.business.ArticleCFSamanoRepository;
import mx.com.endtoend.infrastructure.articles.carredana.zapata.business.ArticleCZapataRepository;
import mx.com.endtoend.infrastructure.articles.demo.customArticles.business.ArticleDemoRepository;
import mx.com.endtoend.infrastructure.commons.constants.CompanyCodes;

@Component
public class ArticleRepositoryFactory {

	@Autowired(required = false)
	private ArticleCalzadaRepository articleCalzadaRepository;

	@Autowired(required = false)
	private ArticleFraguaRepository articleFraguaRepository;

	@Autowired(required = false)
	private ArticleFCarredanaRepository articleFCarredanaRepository;

	@Autowired(required = false)
	private ArticleCZapataRepository articleCZapataRepository;

	@Autowired(required = false)
	private ArticleCFSamanoRepository articleCFSamanoRepository;

	@Autowired(required = false)
	private ArticleDemoRepository articleDemoRepository;

	private final Logger LOG = LoggerFactory.getLogger(ArticleRepositoryFactory.class);

	public GenericArticleRepository getRepository(String companyCode) {

		try {

			CompanyCodes value = CompanyCodes.valueOf(companyCode);

			switch (value) {

			case FCAL:
				LOG.info("RETURN articleCalzadaRepository");
				return articleCalzadaRepository;

			case CFRA:
				LOG.info("articleFraguaRepository");
				return articleFraguaRepository;

			case FCAR:
				LOG.info("RETURN articleFCarredanaRepository");
				return articleFCarredanaRepository;

			case CZAP:
				LOG.info("RETURN articleCZapataRepository");
				return articleCZapataRepository;

					case CFSA:
			LOG.info("RETURN articleCFSamanoRepository");
			return articleCFSamanoRepository;

		case DEMO:
			LOG.info("RETURN articleDemoRepository");
			return articleDemoRepository;

		default:
			LOG.error("ERROR GETTING REPOSITORY TO COMPANY - > " + companyCode);
			return null;
			}

		} catch (IllegalArgumentException | NullPointerException exeption) {
			return null;
		}

	}
}