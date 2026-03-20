package mx.com.endtoend.infrastructure.catalogue.article.common.factory;

import mx.com.endtoend.infrastructure.catalogue.article.common.repository.GenericCatalogueArticleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mx.com.endtoend.infrastructure.catalogue.article.calzada.business.CatalogueArticleCalzadaRepository;
import mx.com.endtoend.infrastructure.catalogue.article.calzada.fragua.business.CatalogueArticleFraguaRepository;
import mx.com.endtoend.infrastructure.catalogue.article.carredana.business.CatalogueArticleFCarredanaRepository;
import mx.com.endtoend.infrastructure.catalogue.article.ferresamano.business.CatalogueArticleFSamanoRepository;
import mx.com.endtoend.infrastructure.catalogue.article.carredana.zapata.business.CatalogueArticleZapataRepository;
import mx.com.endtoend.infrastructure.catalogue.article.demo.business.CatalogueArticleDemoRepository;
import mx.com.endtoend.infrastructure.commons.constants.CompanyCodes;

@Component
public class CatalogueArticleRepositoryFactory {

	@Autowired(required = false)
	private CatalogueArticleCalzadaRepository catalogueArticleCalzadaRepository;

	@Autowired(required = false)
	private CatalogueArticleFraguaRepository catalogueArticleFraguaRepository;

	@Autowired(required = false)
	private CatalogueArticleFCarredanaRepository articleFCarredanaRepository;

	@Autowired(required = false)
	private CatalogueArticleZapataRepository catalogueArticleZapataRepository;

	@Autowired(required = false)
	private CatalogueArticleFSamanoRepository catalogueArticleFSamanoRepository;

	@Autowired(required = false)
	private CatalogueArticleDemoRepository catalogueArticleDemoRepository;

	private final Logger LOG = LoggerFactory.getLogger(CatalogueArticleRepositoryFactory.class);

	public GenericCatalogueArticleRepository getRepository(String companyCode) {
		try {

			CompanyCodes value = CompanyCodes.valueOf(companyCode);

			switch (value) {

			case FCAL:
				LOG.info("RETURN catalogueArticleCalzadaRepository()");
				return catalogueArticleCalzadaRepository;

			case CFRA:
				LOG.info("RETURN catalogueArticleFraguaRepository()");
				return catalogueArticleFraguaRepository;

			case FCAR:
				LOG.info("RETURN articleFCarredanaRepository()");
				return articleFCarredanaRepository;

			case CZAP:
				LOG.info("RETURN catalogueArticleZapataRepository()");
				return catalogueArticleZapataRepository;

			case CFSA:
				LOG.info("RETURN catalogueArticleFSamanoRepository()");
				return catalogueArticleFSamanoRepository;

			case DEMO:
				LOG.info("RETURN catalogueArticleDemoRepository()");
				return catalogueArticleDemoRepository;

			default:
				LOG.error("ERROR GETTING REPOSITORY TO COMPANY - > " + companyCode);
				return null;
			}

		} catch (Exception e) {
			LOG.error("ERROR GETTING REPOSITORY TO COMPANY - > " + companyCode);
			return null;
		}
	}

}
