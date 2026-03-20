package mx.com.endtoend.infrastructure.articles.common.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mx.com.endtoend.infrastructure.articles.calzada.business.CustomArticleCalzadaRepository;
import mx.com.endtoend.infrastructure.articles.calzada.fragua.business.CustArticleFraguaRepository;
import mx.com.endtoend.infrastructure.articles.carredana.customArticles.business.CustArticleFCarRepository;
import mx.com.endtoend.infrastructure.articles.ferresamano.customArticles.business.CustArticleCFSamanoRepository;
import mx.com.endtoend.infrastructure.articles.carredana.zapata.business.CustArticleCZapataRepository;
import mx.com.endtoend.infrastructure.articles.demo.customArticles.business.CustArticleDemoRepository;
import mx.com.endtoend.infrastructure.commons.constants.CompanyCodes;

/**
 * 
 * @author ddcasas
 *
 */

@Component
public class CustomArticleRepositoryFactory {

	@Autowired(required = false)
	private CustomArticleCalzadaRepository customArticleCalzadaRepository;

	@Autowired(required = false)
	private CustArticleFraguaRepository articleFraguaRepository;

	@Autowired(required = false)
	private CustArticleFCarRepository custArticleFCarRepository;

	@Autowired(required = false)
	private CustArticleCZapataRepository custArticleCZapataRepository;

	@Autowired(required = false)
	private CustArticleCFSamanoRepository custArticleCFSamanoRepository;

	@Autowired(required = false)
	private CustArticleDemoRepository custArticleDemoRepository;

	private final Logger LOG = LoggerFactory.getLogger(CustomArticleRepositoryFactory.class);

	public GenericCustomArticleRepository getRepository(String companyCode) {

		try {

			CompanyCodes value = CompanyCodes.valueOf(companyCode);

			switch (value) {

			case FCAL:
				LOG.info("RETURN CUSTOM-ARTICLE-CALZADA-REPOSITORY");
				return customArticleCalzadaRepository;

			case CFRA:
				LOG.info("RETURN articleFraguaRepository");
				return articleFraguaRepository;

			case FCAR:
				LOG.info("RETURN custArticleFCarRepository");
				return custArticleFCarRepository;

			case CZAP:
				LOG.info("RETURN custArticleCZapataRepository");
				return custArticleCZapataRepository;

			case CFSA:
				LOG.info("RETURN custArticleCFSamanoRepository");
				return custArticleCFSamanoRepository;

			case DEMO:
				LOG.info("RETURN custArticleDemoRepository");
				return custArticleDemoRepository;

			default:
				LOG.error("ERROR GETTING REPOSITORY TO COMPANY - > " + companyCode);
				return null;
			}

		} catch (IllegalArgumentException | NullPointerException exeption) {
			return null;
		}

	}

}
